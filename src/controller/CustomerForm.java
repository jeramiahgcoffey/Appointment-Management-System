package controller;

import db.CountryCRUD;
import db.CustomerCRUD;
import db.DivisionCRUD;
import enumerable.FormMode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Country;
import model.Customer;
import model.Division;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerForm implements Initializable {

    @FXML
    private Label customerFormTitle;

    @FXML
    private TextField custNameTF;

    @FXML
    private TextField custAddressTF;

    @FXML
    private ComboBox<Country> custCountryCB;

    @FXML
    private ComboBox<Division> custDivisionCB;

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
        custCountryCB.setItems(FXCollections.observableArrayList(CountryCRUD.getAll()));

        if (Customers.formMode == FormMode.MODIFY) {
            customerFormTitle.setText("Modify Customer");
            custIdTF.setOpacity(1);
            custIdLabel.setOpacity(1);
        }

    }


    /**
     * Handle Cancel button clicked.
     *
     * @param event The event that was fired from the CustomerForm.
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        System.out.println(Customers.formMode);
        FXUtils.getInstance().redirect(event, "/view/customers.fxml");
    }

    /**
     * Handle Country CB change
     *
     * @param event The event that was fired from the CustomerForm
     */
    @FXML
    private void handleCountryChange(ActionEvent event) throws IOException {
        int countryId = custCountryCB.getSelectionModel().getSelectedItem().id();
        custDivisionCB.setItems(FXCollections.observableArrayList(DivisionCRUD.getAllByCountry(countryId)));
    }

    /**
     * Handle Save clicked
     *
     * @param event The event that was fired from the CustomerForm.
     */
    @FXML
    private void handleSaveCustomer(ActionEvent event) throws IOException, SQLException {
        String name = custNameTF.getText();
        String address = custAddressTF.getText();
        Division division = custDivisionCB.getValue();
        String postal = custPostalTF.getText();
        String phone = custPhoneTF.getText();

        Customer newCustomer = new Customer(0, name, address, postal, phone, null, null, null, null, division.id());
        CustomerCRUD.save(newCustomer);

        FXUtils.getInstance().redirect(event, "/view/customers.fxml");
    }
}
