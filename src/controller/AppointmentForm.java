package controller;

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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AppointmentForm implements Initializable {

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
    private DatePicker aptDatePicker;

    @FXML
    private Spinner aptStartHour;

    @FXML
    private Spinner aptFinishMin;

    @FXML
    private Spinner aptStartMin;

    @FXML
    private Spinner aptFinishHour;

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
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Contact> contacts = ContactCRUD.getAll();
        List<Customer> customers = CustomerCRUD.getAll();
        List<User> users = UserCRUD.getAll();

        assert contacts != null;
        Map<Integer, Contact> contactMap = Contact.toMap(contacts);
        assert customers != null;
        Map<Integer, Customer> customerMap = Customer.toMap(customers);
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
            aptContactCB.setValue(contactMap.get(appointment.getContactId()));
            aptCustCB.setValue(customerMap.get(appointment.getCustId()));
            aptUserCB.setValue(userMap.get(appointment.getUserId()));
        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/appointments.fxml");
    }
}
