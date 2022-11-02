package controller;

import db.CustomerCRUD;
import enumerable.FormMode;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class Customers implements Initializable {

    @FXML
    private TableView<Customer> custTable;

    @FXML
    private TableColumn<Number, Integer> custIdCol;

    @FXML
    private TableColumn<Character, String> nameCol;

    @FXML
    private TableColumn<Character, String> addressCol;

    @FXML
    private TableColumn<Character, String> postalCol;

    @FXML
    private TableColumn<Character, String> stateCol;

    @FXML
    private TableColumn<Date, Date> createdAtCol;

    @FXML
    private TableColumn<Character, String> createdByCol;

    @FXML
    private TableColumn<Date, Date> updatedAtCol;

    @FXML
    private TableColumn<Character, String> updatedByCol;

    public static FormMode formMode;

    /**
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<>("postal"));
//        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        createdAtCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
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
        FXUtils.getInstance().redirect(event, "/view/schedule.fxml");
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
        formMode = FormMode.MODIFY;
        FXUtils.getInstance().redirect(event, "/view/customerForm.fxml");
    }

    /**
     * Handle Logout button clicked.
     *
     * @param event The event that was fired from the Customers page.
     */
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
//        FXUtils.getInstance().redirect(event, "/view/login.fxml");
    }
}
