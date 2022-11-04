package controller;

import db.AppointmentCRUD;
import exception.ItemNotSelectedException;
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
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Schedule implements Initializable {

    @FXML
    private TableView<Appointment> aptTable;

    @FXML
    private TableColumn<Appointment, Integer> aptIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, Integer> contactCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, Integer> custIdCol;

    @FXML
    private TableColumn<Appointment, Integer> userIdCol;

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
     * @param event The event that was fired from the Schedule page.
     */
    @FXML
    private void handleCustomers(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/customers.fxml");
    }

    /**
     * Handle Delete Appointment button clicked
     *
     * @param event The event that was fired from the Schedule page.
     */
    @FXML
    private void handleDeleteAppointment(ActionEvent event) {
        try {
            Appointment selectedAppointment = aptTable.getSelectionModel().getSelectedItem();

            if (selectedAppointment == null) throw new ItemNotSelectedException("NO ITEM");
            AppointmentCRUD.delete(selectedAppointment);
            aptTable.setItems(FXCollections.observableList(Objects.requireNonNull(AppointmentCRUD.getAll())));
        } catch (ItemNotSelectedException e) {
//            TODO: Show error popup here
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handle Logout button clicked.
     *
     * @param event The event that was fired from the Schedule page.
     */
    @FXML
    private void handleLogout(ActionEvent event) throws IOException {
        //        TODO: FIX THIS
//        FXUtils.getInstance().redirect(event, "/view/login.fxml");
    }
}
