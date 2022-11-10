package controller;

import dataAccess.AppointmentCRUD;
import dataAccess.CustomerCRUD;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Appointment;
import model.Customer;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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
    private ComboBox contactCB;

    @FXML
    private TableView contactScheduleTable;

    @FXML
    private TableColumn aptIdCol;

    @FXML
    private TableColumn titleCol;

    @FXML
    private TableColumn descCol;

    @FXML
    private TableColumn typeCol;

    @FXML
    private TableColumn custIdCol;

    @FXML
    private TableColumn startCol;

    @FXML
    private TableColumn endCol;


    /**
     * @param url
     * @param resourceBundle
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
    }

    @FXML
    private void handleRunReport() {
        Month selectedMonth = monthCB.getSelectionModel().getSelectedItem();
        String selectedType = typeCB.getSelectionModel().getSelectedItem();
        Customer selectedCustomer = customerCB.getSelectionModel().getSelectedItem();

        int total = AppointmentCRUD.getTotals(selectedMonth, selectedType, selectedCustomer);
        totalLabel.setText(String.valueOf(total));
    }

    @FXML
    private void handleClose(ActionEvent event) throws IOException {
        FXUtils.getInstance().redirect(event, "/view/appointments.fxml");
    }

    @FXML
    private void handleReset() {
        monthCB.setValue(null);
        typeCB.setValue(null);
        customerCB.setValue(null);
        totalLabel.setText("0");
    }
}
