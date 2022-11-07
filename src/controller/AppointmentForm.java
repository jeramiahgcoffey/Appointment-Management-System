package controller;

import db.AppointmentCRUD;
import db.ContactCRUD;
import db.CustomerCRUD;
import db.UserCRUD;
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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
        // TODO: Validation

        String title = aptTitleTF.getText();
        String desc = aptDescTF.getText();
        String location = aptLocationTF.getText();
        int contactId = aptContactCB.getValue().id();
        String type = aptTypeTf.getText();
        TimestampValue start = new TimestampValue(Timestamp.valueOf(aptStartDP.getValue().atTime(aptStartHour.getValue(), aptStartMin.getValue())));
        TimestampValue end = new TimestampValue(Timestamp.valueOf(aptFinishDP.getValue().atTime(aptFinishHour.getValue(), aptFinishMin.getValue())));
        int custId = aptCustCB.getValue().getId();
        int userId = aptUserCB.getValue().getUserId();

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
}
