package controller;

import dataAccess.AppointmentCRUD;
import dataAccess.CustomerCRUD;
import enumerable.FormMode;
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
import java.sql.SQLException;
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

    @FXML
    private TableColumn<Customer, String> updatedByCol;

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
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
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
            if (custTable.getSelectionModel().getSelectedItem() == null) throw new ItemNotSelectedException("NO ITEM");
            formMode = FormMode.MODIFY;
            setSelectedCustomer();
            FXUtils.getInstance().redirect(event, "/view/customerForm.fxml");
        } catch (ItemNotSelectedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle Delete Customer button clicked.
     */
    @FXML
    private void handleDeleteCustomer() {
        //  TODO: SHOW Custom Popup
        try {
            Customer selectedCustomer = custTable.getSelectionModel().getSelectedItem();
            if (selectedCustomer == null) throw new ItemNotSelectedException("NO ITEM");

            List<Appointment> customerAppointments = AppointmentCRUD.getByCustomerId(selectedCustomer.getId());
            assert customerAppointments != null;
            if (!customerAppointments.isEmpty()) {
                //  TODO: SHOW POPUP
                System.out.print("NO");
                return;
            }

            if (!FXUtils.getInstance().confirm("Are you sure you want to delete Customer " + selectedCustomer.getId() + "?"))
                return;

            CustomerCRUD.delete(selectedCustomer);
            custTable.setItems(FXCollections.observableList(Objects.requireNonNull(CustomerCRUD.getAll())));
        } catch (ItemNotSelectedException e) {
//            TODO:  Show error popup here
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}
