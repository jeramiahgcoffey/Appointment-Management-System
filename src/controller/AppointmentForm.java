package controller;

import dataAccess.AppointmentCRUD;
import dataAccess.ContactCRUD;
import dataAccess.CustomerCRUD;
import dataAccess.UserCRUD;
import enumerable.FormMode;
import exception.AppointmentOverlapException;
import exception.OutsideBusinessHoursException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import util.DateTimeValue;
import util.FXUtils;
import util.ListUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    private TextField aptStartHour;

    @FXML
    private TextField aptFinishMin;

    @FXML
    private TextField aptStartMin;

    @FXML
    private TextField aptFinishHour;

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
     * Initialize the view. Populate ComboBoxes. Populate fields with Appointment data if form mode is MODIFY.
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
            aptStartHour.setText(String.valueOf(appointment.getStartTimestamp().getHour()));
            aptStartMin.setText(String.valueOf(appointment.getStartTimestamp().getMinute()));
            aptFinishDP.setValue(appointment.getEndTimestamp().originalValue().toLocalDateTime().toLocalDate());
            aptFinishHour.setText(String.valueOf(appointment.getEndTimestamp().getHour()));
            aptFinishMin.setText(String.valueOf(appointment.getEndTimestamp().getMinute()));
        }
    }

    /**
     * Event handler for Save button.
     * A lambda expression is used as the Predicate argument for the forEach method, helping to increase code readability and concision.
     *
     * @param event The event which was fired from the appointmentForm page.
     */
    @FXML
    private void handleSave(ActionEvent event) throws SQLException, IOException {
        try {
            boolean hasErrors = false;

            String title = aptTitleTF.getText();
            String desc = aptDescTF.getText();
            String location = aptLocationTF.getText();
            Contact contact = aptContactCB.getValue();
            String type = aptTypeTf.getText();
            LocalDate startDate = aptStartDP.getValue();
            int startHour = Integer.parseInt(aptStartHour.getText());
            int startMin = Integer.parseInt(aptStartMin.getText());
            int endHour = Integer.parseInt(aptFinishHour.getText());
            int endMin = Integer.parseInt(aptFinishMin.getText());
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
            } else if (
                    startHour < 0
                            || startHour > 23
                            || endHour < 0
                            || endHour > 23
                            || startMin < 0
                            || startMin > 59
                            || endMin < 0
                            || endMin > 59
            ) {
                hasErrors = true;
                timeError.setText("Time values must be valid integers.");
            } else {
                timeError.setText("");
            }

            if (hasErrors) return;

            int custId = customer.getId();
            int contactId = contact.id();
            int userId = user.getUserId();
            DateTimeValue start = new DateTimeValue(Timestamp.valueOf(startDate.atTime(startHour, startMin)));
            DateTimeValue end = new DateTimeValue(Timestamp.valueOf(aptFinishDP.getValue().atTime(endHour, endMin)));
            List<Appointment> customersApts = AppointmentCRUD.getByCustomerId(custId);
            AtomicBoolean hasOverlap = new AtomicBoolean(false);
            if (customersApts != null && !customersApts.isEmpty()) {
                customersApts.forEach(apt -> {
                    if (Appointments.formMode == FormMode.MODIFY && apt.getId() == Appointments.selectedAppointment.getId())
                        return;

                    LocalDateTime selectedAptStartDate = start.toLocalDateTime();
                    LocalDateTime currAptStartDate = apt.getStartTimestamp().toLocalDateTime();

                    assert selectedAptStartDate != null;
                    assert currAptStartDate != null;

                    if (selectedAptStartDate.toLocalDate().equals(currAptStartDate.toLocalDate())) {
                        long selectedAptStart = start.originalValue().getTime();
                        long selectedAptEnd = end.originalValue().getTime();
                        long currAptStart = apt.getStartTimestamp().originalValue().getTime();
                        long currAptEnd = apt.getEndTimestamp().originalValue().getTime();

                        if (selectedAptEnd > currAptStart && selectedAptStart < currAptEnd) hasOverlap.set(true);
                    }
                });
            }
            if (hasOverlap.get()) throw new AppointmentOverlapException();

            if (!start.isValidBusinessHours() || !end.isValidBusinessHours()) throw new OutsideBusinessHoursException();

            if (Appointments.formMode == FormMode.ADD) {
                Appointment apt = new Appointment(0, title, desc, location, contactId, type, start, end, DateTimeValue.now(), DateTimeValue.now(), null, null, custId, userId);
                AppointmentCRUD.save(apt);
            } else if (Appointments.formMode == FormMode.MODIFY) {
                Appointment apt = new Appointment(Appointments.selectedAppointment.getId(), title, desc, location, contactId, type, start, end, null, DateTimeValue.now(), null, null, custId, userId);
                AppointmentCRUD.update(apt);
            }
            FXUtils.getInstance().redirect(event, "/view/appointments.fxml");
        } catch (NumberFormatException e) {
            timeError.setText("Time values must be valid integers.");
        } catch (AppointmentOverlapException | OutsideBusinessHoursException e) {
            FXUtils.getInstance().error(e.getMessage());
        }
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
     * Event handler for the Start Date Picker.
     */
    @FXML
    private void handleAptDP() {
        aptFinishDP.setValue(aptStartDP.getValue());
    }

    /**
     * Event handler for Appointment end time Text Fields.
     */
    @FXML
    private void handleEndTimeInput() {
        try {
            int startHour = Integer.parseInt(aptStartHour.getText());
            int startMin = Integer.parseInt(aptStartMin.getText());
            int finishHour = Integer.parseInt(aptFinishHour.getText());
            int finishMin = Integer.parseInt(aptFinishMin.getText());
            if (finishHour < startHour || (finishHour == startHour && finishMin < startMin)) {
                aptFinishDP.setValue(aptStartDP.getValue().plusDays(1));
            } else {
                aptFinishDP.setValue(aptStartDP.getValue());
            }
        } catch (NumberFormatException ignored) {
        }
    }
}
