package controller;

import db.CustomerDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

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
        custTable.setItems(FXCollections.observableList(CustomerDAO.getInstance().getAll()));
    }

    /**
     * Redirect to schedule page.
     *
     * @param event The event that was triggered from the login page.
     */
    public void redirectToSchedule(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/schedule.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleAddCustomer(ActionEvent event) throws IOException {
        this.redirectToCustomerForm(event);
    }

    private void redirectToCustomerForm(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/customerForm.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
