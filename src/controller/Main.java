package controller;

import db.AppointmentDAO;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {
    /**
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(AppointmentDAO.getInstance().getAll());
    }
}
