package controller;

import dataAccess.AppointmentCRUD;
import enumerable.FormMode;
import exception.ItemNotSelectedException;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import util.FXUtils;
import util.TimestampValue;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * JavaFX controller for the Appointments view.
 *
 * @author Jeramiah Coffey
 */
public class Appointments implements Initializable {
    @FXML
    private ToggleGroup view;

    @FXML
    private Label zoneIdLabel;

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

    public static FormMode formMode;
    public static Appointment selectedAppointment;


    /**
     * Initialize the view. Display appointments in table view.
     *
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIdLabel.setText(String.valueOf(ZoneId.systemDefault()));

        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
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
     * @param event The event that was fired from the Appointments page.
     */
    @FXML
    private void handleCustomers(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/customers.fxml");
    }

    /**
     * Handle Delete Appointment button clicked.
     */
    @FXML
    private void handleDeleteAppointment() {
        try {
            Appointment selectedAppointment = aptTable.getSelectionModel().getSelectedItem();

            if (selectedAppointment == null) throw new ItemNotSelectedException("Please select an appointment.");
            if (!FXUtils.getInstance().confirm("Are you sure you want to delete " + selectedAppointment.getTitle() + "?"))
                return;
            AppointmentCRUD.delete(selectedAppointment);
            aptTable.setItems(FXCollections.observableList(Objects.requireNonNull(AppointmentCRUD.getAll())));
        } catch (ItemNotSelectedException | SQLException e) {
            FXUtils.getInstance().error(e.getMessage());
        }
    }

    /**
     * Handle Add Appointment button clicked.
     *
     * @param event The event that was fired from the Appointments page.
     */
    @FXML
    private void handleAddApt(ActionEvent event) throws IOException {
        formMode = FormMode.ADD;
        FXUtils.getInstance().redirect(event, "/view/appointmentForm.fxml");
    }

    /**
     * Handle Modify Appointment button clicked.
     *
     * @param event The event that was fired from the Appointments page.
     */
    @FXML
    private void handleModifyApt(ActionEvent event) {
        try {
            if (aptTable.getSelectionModel().getSelectedItem() == null)
                throw new ItemNotSelectedException("Please select an appointment.");
            formMode = FormMode.MODIFY;
            setSelectedAppointment();
            FXUtils.getInstance().redirect(event, "/view/appointmentForm.fxml");
        } catch (ItemNotSelectedException | IOException e) {
            FXUtils.getInstance().error(e.getMessage());
        }
    }

    /**
     * Handle Logout button clicked.
     *
     * @param event The event that was fired from the Appointments page.
     */
    @FXML
    private void handleLogout(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Sets selectedAppointment to the currently selected appointment in the table view.
     */
    private void setSelectedAppointment() {
        selectedAppointment = aptTable.getSelectionModel().getSelectedItem();
    }

    /**
     * Handles View All radio button selected. This is the default value.
     */
    @FXML
    private void handleViewAll() {
        aptTable.setItems(FXCollections.observableList(Objects.requireNonNull(AppointmentCRUD.getAll())));
    }

    /**
     * Handle View by Month radio button selected. Appointments are listed if the start date belongs to the current month.
     * A lambda expression is used as the Predicate argument for the filter method, helping to increase code readability and concision.
     */
    @FXML
    private void handleViewMonth() {
        int month = Objects.requireNonNull(TimestampValue.now().toLocalDateTime()).getMonthValue();
        int year = Objects.requireNonNull(TimestampValue.now().toLocalDateTime()).getYear();

        List<Appointment> apts = AppointmentCRUD.getAll();

        assert apts != null;
        aptTable.setItems(FXCollections.observableArrayList(
                apts.stream()
                        .filter(apt -> {
                            LocalDateTime dt = Objects.requireNonNull(apt
                                    .getStartTimestamp()
                                    .toLocalDateTime());
                            return dt.getMonthValue() == month && dt.getYear() == year;
                        })
                        .collect(Collectors.toList())
        ));
    }

    /**
     * Handle View by Week radio button selected. Appointments are listed if the start date belongs to the current week.
     * A lambda expression is used as the Predicate argument for the filter method, helping to increase code readability and concision.
     */
    @FXML
    private void handleViewWeek() {
        Calendar cal = Calendar.getInstance();
        int week = cal.get(Calendar.WEEK_OF_YEAR);

        List<Appointment> apts = AppointmentCRUD.getAll();

        assert apts != null;
        aptTable.setItems(FXCollections.observableArrayList(
                apts.stream()
                        .filter(apt -> {
                            Timestamp t = Objects.requireNonNull(apt.getStartTimestamp().originalValue());
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(t.getTime());
                            int w = c.get(Calendar.WEEK_OF_YEAR);
                            return week == w;
                        })
                        .collect(Collectors.toList())
        ));
    }
}
