package controller;

import db.AppointmentCRUD;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class Schedule implements Initializable {

    @FXML
    private TableView<Appointment> aptTable;

    @FXML
    private TableColumn<Number, Integer> aptIdCol;

    @FXML
    private TableColumn<Character, String> titleCol;

    @FXML
    private TableColumn<Character, String> descCol;

    @FXML
    private TableColumn<Character, String> locationCol;

    @FXML
    private TableColumn<Number, Integer> contactCol;

    @FXML
    private TableColumn<Character, String> typeCol;

    @FXML
    private TableColumn<Date, Timestamp> startCol;

    @FXML
    private TableColumn<Date, Timestamp> endCol;

    @FXML
    private TableColumn<Number, Integer> custIdCol;

    @FXML
    private TableColumn<Number, Integer> userIdCol;

    /**
     * Initialize the view controller. Display appointments in table view.
     *
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        aptTable.setItems(FXCollections.observableList(Objects.requireNonNull(AppointmentCRUD.getAll())));
    }

    /**
     * Handle Customers button clicked.
     *
     * @param event The event that was triggered from the login page.
     */
    @FXML
    private void handleCustomers(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/customers.fxml");
    }

    /**
     * Handle Logout button clicked.
     *
     * @param event The event that was triggered from the login page.
     */
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
//        FXUtils.getInstance().redirect(event, "/view/login.fxml");
    }
}
