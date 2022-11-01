package controller;

import helper.Redirect;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerForm implements Initializable {
    /**
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Handle Cancel button clicked.
     *
     * @param event The event that was triggered from the login page.
     */
    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        Redirect.getInstance().to(event, "/view/customers.fxml");
    }
}