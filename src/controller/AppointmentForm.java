package controller;

import dataAccess.AppointmentCRUD;
import dataAccess.ContactCRUD;
import dataAccess.CustomerCRUD;
import dataAccess.UserCRUD;
import enumerable.FormMode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import util.FXUtils;
import util.ListUtils;
import util.TimestampValue;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * JavaFX controller for the appointmentForm view.
 *
 * @author Jeramiah Coffey
 */
public class AppointmentForm implements Initializable {

    @FXML
    private DatePicker aptFinishDP;

    @FXML
    private Label aptFormTitle;

    @FXML
    private Label aptIdLabel;

    @FXML
    private TextField aptIdTF;

    @FXML
    private TextField aptTitleTF;

    @FXML
    private TextField aptDescTF;

    @FXML
    private TextField aptLocationTF;

    @FXML
    private TextField aptTypeTf;

    @FXML
    private ComboBox<Contact> aptContactCB;

    @FXML
    private ComboBox<Customer> aptCustCB;

    @FXML
    private ComboBox<User> aptUserCB;

    @FXML
    private DatePicker aptStartDP;

    @FXML
    private Spinner<Integer> aptStartHour;

    @FXML
    private Spinner<Integer> aptFinishMin;

    @FXML
    private Spinner<Integer> aptStartMin;

    @FXML
    private Spinner<Integer> aptFinishHour;

    @FXML
    private Label titleError;

    @FXML
    private Label descError;

    @FXML
    private Label locationError;

    @FXML
    private Label typeError;

    @FXML
    private Label contactError;

    @FXML
    private Label custError;

    @FXML
    private Label userError;

    @FXML
    private Label dateError;

    @FXML
    private Label timeError;

    /**
     * Initialize the view. Populate fields with Appointment data if form mode is MODIFY.
     *
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Contact> contacts = ContactCRUD.getAll();
        List<Customer> customers = CustomerCRUD.getAll();
        List<User> users = UserCRUD.getAll();

        assert contacts != null;
        Map<Integer, Contact> contactMap = ListUtils.toContactMap(contacts);
        assert customers != null;
        Map<Integer, Customer> customerMap = ListUtils.toCustomerMap(customers);
        assert users != null;
        Map<Integer, User> userMap = User.toMap(users);

        aptContactCB.setItems(FXCollections.observableArrayList(contacts));
        aptCustCB.setItems(FXCollections.observableArrayList(customers));
        aptUserCB.setItems(FXCollections.observableArrayList(users));

        if (Appointments.formMode == FormMode.MODIFY) {
            aptFormTitle.setText("Modify Appointment");
            aptIdTF.setOpacity(1);
            aptIdLabel.setOpacity(1);

            Appointment appointment = Appointments.selectedAppointment;

            aptIdTF.setText(String.valueOf(appointment.getId()));
            aptTitleTF.setText(appointment.getTitle());
            aptDescTF.setText(appointment.getDescription());
            aptLocationTF.setText(appointment.getLocation());
            aptTypeTf.setText(appointment.getType());
            aptContactCB.setValue(contactMap.get(appointment.getContact().id()));
            aptCustCB.setValue(customerMap.get(appointment.getCustId()));
            aptUserCB.setValue(userMap.get(appointment.getUserId()));
            aptStartDP.setValue(appointment.getStartTimestamp().originalValue().toLocalDateTime().toLocalDate());
            aptStartHour.getValueFactory().setValue(appointment.getStartTimestamp().getHour());
            aptStartMin.getValueFactory().setValue(appointment.getStartTimestamp().getMinute());
            aptFinishDP.setValue(appointment.getEndTimestamp().originalValue().toLocalDateTime().toLocalDate());
            aptFinishHour.getValueFactory().setValue(appointment.getEndTimestamp().getHour());
            aptFinishMin.getValueFactory().setValue(appointment.getEndTimestamp().getMinute());
        }
    }

    /**
     * Event handler for Save button.
     *
     * @param event The event which was fired from the appointmentForm page.
     */
    @FXML
    private void handleSave(ActionEvent event) throws SQLException, IOException {
        // TODO: Business hours validation
        boolean hasErrors = false;

        String title = aptTitleTF.getText();
        String desc = aptDescTF.getText();
        String location = aptLocationTF.getText();
        Contact contact = aptContactCB.getValue();
        String type = aptTypeTf.getText();
        LocalDate startDate = aptStartDP.getValue();
        int startHour = aptStartHour.getValue();
        int startMin = aptStartMin.getValue();
        int endHour = aptFinishHour.getValue();
        int endMin = aptFinishMin.getValue();
        Customer customer = aptCustCB.getValue();
        User user = aptUserCB.getValue();

        if (title.isEmpty()) {
            hasErrors = true;
            titleError.setText("Please provide a title.");
        } else {
            titleError.setText("");
        }

        if (desc.isEmpty()) {
            hasErrors = true;
            descError.setText("Please provide a description.");
        } else {
            descError.setText("");
        }

        if (location.isEmpty()) {
            hasErrors = true;
            locationError.setText("Please provide a location.");
        } else {
            locationError.setText("");
        }

        if (type.isEmpty()) {
            hasErrors = true;
            typeError.setText("Please provide a type.");
        } else {
            typeError.setText("");
        }

        if (contact == null) {
            hasErrors = true;
            contactError.setText("Please select a contact.");
        } else {
            contactError.setText("");
        }

        if (customer == null) {
            hasErrors = true;
            custError.setText("Please select a customer.");
        } else {
            custError.setText("");
        }

        if (user == null) {
            hasErrors = true;
            userError.setText("Please select a user.");
        } else {
            userError.setText("");
        }

        if (startDate == null) {
            hasErrors = true;
            dateError.setText("Please select a date.");
        } else {
            dateError.setText("");
        }

        if (startHour == endHour && startMin == endMin) {
            hasErrors = true;
            timeError.setText("Please provide a duration.");
        } else {
            timeError.setText("");
        }

        if (hasErrors) return;

        int custId = customer.getId();
        int contactId = contact.id();
        int userId = user.getUserId();
        TimestampValue start = new TimestampValue(Timestamp.valueOf(startDate.atTime(startHour, startMin)));
        TimestampValue end = new TimestampValue(Timestamp.valueOf(aptFinishDP.getValue().atTime(endHour, endMin)));

        List<Appointment> customersApts = AppointmentCRUD.getByCustomerId(custId);
        AtomicBoolean hasOverlap = new AtomicBoolean(false);
        if (customersApts != null && !customersApts.isEmpty()) {
            customersApts.forEach(apt -> {
                if (Objects.requireNonNull(apt.getStartTimestamp().toLocalDateTime()).toLocalDate()
                        .equals(Objects.requireNonNull(start.toLocalDateTime()).toLocalDate())) {
                    if (Objects.requireNonNull(apt.getStartTimestamp().toLocalDateTime()).getHour() <= Objects.requireNonNull(start.toLocalDateTime()).getHour()
                            && Objects.requireNonNull(apt.getEndTimestamp().toLocalDateTime()).getHour() >= Objects.requireNonNull(start.toLocalDateTime()).getHour()) {
                        hasOverlap.set(true);
                    }
                }
            });
        }
        if (hasOverlap.get()) {
            // TODO: Show POPUP
            System.out.println("NO");
            return;
        }

        if (Appointments.formMode == FormMode.ADD) {
            Appointment apt = new Appointment(0, title, desc, location, contactId, type, start, end, TimestampValue.now(), TimestampValue.now(), null, null, custId, userId);
            AppointmentCRUD.save(apt);
        } else if (Appointments.formMode == FormMode.MODIFY) {
            Appointment apt = new Appointment(Appointments.selectedAppointment.getId(), title, desc, location, contactId, type, start, end, null, TimestampValue.now(), null, null, custId, userId);
            AppointmentCRUD.update(apt);
        }
        FXUtils.getInstance().redirect(event, "/view/appointments.fxml");
    }

    /**
     * Event handler for the Cancel button.
     *
     * @param event The event which was fired from the appointmentForm page.
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/appointments.fxml");
    }

    /**
     * Event handler for the Start Date Picker
     */
    @FXML
    private void handleAptDP() {
        aptFinishDP.setValue(aptStartDP.getValue());
    }
}
