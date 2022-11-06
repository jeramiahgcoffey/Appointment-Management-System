package controller;

import enumerable.FormMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
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
    private ComboBox aptContactCB;

    @FXML
    private ComboBox aptCustCB;

    @FXML
    private ComboBox aptUserCB;

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
     * @param resourceBundle Resources used to localize the root object, null if not localizeda
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        if (Appointments.formMode == FormMode.MODIFY) {
            aptFormTitle.setText("Modify Appointment");
            aptIdTF.setOpacity(1);
            aptIdLabel.setOpacity(1);

            Appointment appointment = Appointments.selectedAppointment;

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
