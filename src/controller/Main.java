package controller;

import db.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class Main implements Initializable {

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
    private TableColumn<Date, Date> startCol;

    @FXML
    private TableColumn<Date, Date> endCol;

    @FXML
    private TableColumn<Number, Integer> custIdCol;

    @FXML
    private TableColumn<Number, Integer> userIdCol;

    /**
     * Initialize the view controller. Display appointments in table view.
     * @param url URL used to resolve paths, null if not known
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
        aptTable.setItems(FXCollections.observableList(AppointmentDAO.getInstance().getAll()));
    }
}
