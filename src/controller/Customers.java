package controller;

import dataAccess.AppointmentCRUD;
import dataAccess.CustomerCRUD;
import enumerable.FormMode;
import exception.CannotDeleteCustomerException;
import exception.ItemNotSelectedException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Customer;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the Customers view.
 *
 * @author Jeramiah Coffey
 */
public class Customers implements Initializable {

    @FXML
    private TableView<Customer> custTable;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, Integer> custIdCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> postalCol;

    @FXML
    private TableColumn<Customer, String> stateCol;

    @FXML
    private TableColumn<Customer, String> createdAtCol;

    @FXML
    private TableColumn<Customer, String> updatedAtCol;

    public static FormMode formMode;
    public static Customer selectedCustomer;

    /**
     * Initialize the view. Display Customers in TableView.
     *
     * @param url            URL used to resolve paths, null if not known.
     * @param resourceBundle Resources used to localize the root object, null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        updatedAtCol.setCellValueFactory(new PropertyValueFactory<>("updatedAt"));
        custTable.setItems(FXCollections.observableList(Objects.requireNonNull(CustomerCRUD.getAll())));
    }

    /**
     * Handle Schedule button clicked.
     *
     * @param event The event that was fired from the Customers page.
     */
    @FXML
    private void handleSchedule(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/appointments.fxml");
    }

    /**
     * Handle Add Customer button clicked.
     *
     * @param event The event that was fired from the Customers page.
     */
    @FXML
    private void handleAddCustomer(ActionEvent event) throws IOException {
        formMode = FormMode.ADD;
        FXUtils.getInstance().redirect(event, "/view/customerForm.fxml");
    }

    /**
     * Handle Add Customer button clicked.
     *
     * @param event The event that was fired from the Customers page.
     */
    @FXML
    private void handleModifyCustomer(ActionEvent event) throws IOException {
        try {
            if (custTable.getSelectionModel().getSelectedItem() == null)
                throw new ItemNotSelectedException("Please select a customer.");
            formMode = FormMode.MODIFY;
            setSelectedCustomer();
            FXUtils.getInstance().redirect(event, "/view/customerForm.fxml");
        } catch (ItemNotSelectedException e) {
            FXUtils.getInstance().error(e.getMessage());
        }
    }

    /**
     * Handle Delete Customer button clicked.
     */
    @FXML
    private void handleDeleteCustomer() {
        try {
            Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) throw new ItemNotSelectedException("Please select a customer.");

            List<Appointment> customerAppointments = AppointmentCRUD.getByCustomerId(selectedCustomer.getId());
            assert customerAppointments != null;
            if (!customerAppointments.isEmpty())
                throw new CannotDeleteCustomerException();

            if (!FXUtils.getInstance().confirm("Are you sure you want to delete Customer " + selectedCustomer.getId() + "?"))
                return;

            CustomerCRUD.delete(selectedCustomer);

            String msg = selectedCustomer.getId() + " - " + selectedCustomer.getName() + " has been deleted.";
            FXUtils.getInstance().inform(msg, "Customer deleted successfully", "Success");
            custTable.setItems(FXCollections.observableList(Objects.requireNonNull(CustomerCRUD.getAll())));
        } catch (ItemNotSelectedException | CannotDeleteCustomerException e) {
            FXUtils.getInstance().error(e.getMessage());
        }
    }

    /**
     * Handle Logout button clicked.
     *
     * @param event The event that was fired from the Customers page.
     */
    @FXML
    private void handleLogout(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Sets selectedCustomer to the currently selected customer in the table view.
     */
    private void setSelectedCustomer() {
        selectedCustomer = custTable.getSelectionModel().getSelectedItem();
    }

    public void handleReports(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/reports.fxml");
    }
}
