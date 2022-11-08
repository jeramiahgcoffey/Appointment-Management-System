package controller;

import dataAccess.CountryCRUD;
import dataAccess.CustomerCRUD;
import dataAccess.DivisionCRUD;
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
import util.TimestampValue;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the CustomerForm view.
 *
 * @author Jeramiah Coffey
 */
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
     * Initialize the view. Populate fields with Appointment data if form mode is MODIFY.
     *
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

            Customer customer = Customers.selectedCustomer;
            custNameTF.setText(customer.getName());
            custAddressTF.setText(customer.getAddress());
            custPostalTF.setText(customer.getPostal());
            custPhoneTF.setText(customer.getPhone());
            custIdTF.setText(String.valueOf(customer.getId()));

            Country currentCountry = CountryCRUD.getByDivision(customer.getDivisionId());
            assert currentCountry != null;
            custDivisionCB.setItems(FXCollections.observableArrayList(DivisionCRUD.getAllByCountry(currentCountry.id())));
            custCountryCB.setValue(CountryCRUD.getByDivision(customer.getDivisionId()));
            custDivisionCB.setValue(DivisionCRUD.get(customer.getDivisionId()));
        }
    }

    /**
     * Handle Cancel button clicked.
     *
     * @param event The event that was fired from the CustomerForm.
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/customers.fxml");
    }

    /**
     * Handle Country ComboBox change.
     *
     * @param event The event that was fired from the CustomerForm
     */
    @FXML
    private void handleCountryChange(ActionEvent event) throws IOException {
        int countryId = custCountryCB.getSelectionModel().getSelectedItem().id();
        custDivisionCB.setItems(FXCollections.observableArrayList(DivisionCRUD.getAllByCountry(countryId)));
        custDivisionCB.setValue(null);
    }

    /**
     * Handle Save button clicked.
     *
     * @param event The event that was fired from the CustomerForm.
     */
    @FXML
    private void handleSaveCustomer(ActionEvent event) throws IOException, SQLException {
        if (Objects.equals(custNameTF.getText(), "") || custDivisionCB.getValue() == null) return;

        String name = custNameTF.getText();
        String address = custAddressTF.getText();
        Division division = custDivisionCB.getValue();
        String postal = custPostalTF.getText();
        String phone = custPhoneTF.getText();

        if (Customers.formMode.equals(FormMode.ADD)) {
            Customer newCustomer = new Customer(0, name, address, postal, phone, TimestampValue.now(), TimestampValue.now(), null, null, division.id());
            CustomerCRUD.save(newCustomer);
        } else if (Customers.formMode.equals(FormMode.MODIFY)) {
            Customer updatedCustomer = new Customer(Customers.selectedCustomer.getId(), name, address, postal, phone, null, TimestampValue.now(), null, null, division.id());
            CustomerCRUD.update(updatedCustomer);
        }
        FXUtils.getInstance().redirect(event, "/view/customers.fxml");
    }
}
