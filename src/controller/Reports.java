package controller;

import dataAccess.AppointmentCRUD;
import dataAccess.ContactCRUD;
import dataAccess.CustomerCRUD;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import model.Customer;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * JavaFX controller for the Reports view.
 *
 * @author Jeramiah Coffey
 */
public class Reports implements Initializable {

    @FXML
    private ComboBox<Month> monthCB;

    @FXML
    private ComboBox<String> typeCB;

    @FXML
    private ComboBox<Customer> customerCB;

    @FXML
    private Label totalLabel;

    @FXML
    private ComboBox<Contact> contactCB;

    @FXML
    private TableView<Appointment> contactScheduleTable;

    @FXML
    private TableColumn<Appointment, String> aptIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> custIdCol;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> endCol;


    /**
     * Initializes the view. Populates ComboBoxes. Gets total appointment records on page load.
     *
     * @param url            URL used to resolve paths, null if not known.
     * @param resourceBundle Resources used to localize the root object, null if not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthCB.setItems(FXCollections.observableArrayList(Month.values()));

        List<Appointment> apts = AppointmentCRUD.getAll();
        List<String> types = new ArrayList<>();
        if (apts != null) {
            apts.forEach(apt -> {
                if (!types.contains(apt.getType())) {
                    types.add(apt.getType());
                }
            });
        }
        typeCB.setItems(FXCollections.observableArrayList(types));

        List<Customer> customers = CustomerCRUD.getAll();
        customerCB.setItems(FXCollections.observableArrayList(customers));

        totalLabel.setText(String.valueOf(AppointmentCRUD.getTotals(null, null, null)));

        List<Contact> contacts = ContactCRUD.getAll();
        contactCB.setItems(FXCollections.observableArrayList(contacts));

        aptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("custId"));

    }

    /**
     * Event handler for the Run Report button.
     */
    @FXML
    private void handleRunReport() {
        Month selectedMonth = monthCB.getSelectionModel().getSelectedItem();
        String selectedType = typeCB.getSelectionModel().getSelectedItem();
        Customer selectedCustomer = customerCB.getSelectionModel().getSelectedItem();

        int total = AppointmentCRUD.getTotals(selectedMonth, selectedType, selectedCustomer);
        totalLabel.setText(String.valueOf(total));
    }

    /**
     * Event handler for the Close button
     *
     * @param event The event which was fired from the Reports page.
     */
    @FXML
    private void handleClose(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/appointments.fxml");
    }

    /**
     * Event handler for the Reset button.
     */
    @FXML
    private void handleReset() {
        monthCB.setValue(null);
        typeCB.setValue(null);
        customerCB.setValue(null);
        totalLabel.setText("0");
    }

    /**
     * Event handler for selected a Contact from the ComboBox.
     */
    @FXML
    private void handleContactSelected() {
        Contact selectedContact = contactCB.getSelectionModel().getSelectedItem();
        List<Appointment> apts = Objects.requireNonNull(AppointmentCRUD.getAll())
                .stream()
                .filter(apt -> apt.getContact().id() == selectedContact.id())
                .toList();
        contactScheduleTable.setItems(FXCollections.observableArrayList(apts));
        contactScheduleTable.getSortOrder().setAll(startCol);
    }
}
