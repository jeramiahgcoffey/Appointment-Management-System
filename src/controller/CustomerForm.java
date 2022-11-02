package controller;

import enumerable.FormMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerForm implements Initializable {

    @FXML
    private Label customerFormTitle;

    @FXML
    private TextField custNameTF;

    @FXML
    private TextField custAddressTF;

    @FXML
    private ComboBox custCountryCB;

    @FXML
    private ComboBox custDivisionCB;

    @FXML
    private TextField custPostalTF;

    @FXML
    private TextField custPhoneTF;

    @FXML
    private TextField custIdTF;

    @FXML
    private Label custIdLabel;

    /**
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Customers.formMode == FormMode.MODIFY) {
            customerFormTitle.setText("Modify Customer");
            custIdTF.setOpacity(1);
            custIdLabel.setOpacity(1);
        }
    }

    /**
     * Handle Cancel button clicked.
     *
     * @param event The event that was triggered from the login page.
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        System.out.println(Customers.formMode);
        FXUtils.getInstance().redirect(event, "/view/customers.fxml");
    }
}
