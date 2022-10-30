package controller;

import db.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private TextField loginUsernameTF;

    @FXML
    private PasswordField loginPasswordTF;

    private UserDAO userDAO;

    /**
     * @param url URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDAO = UserDAO.getInstance();
    }

    /**
     * Compare candidate password with fetched user's password.
     * @param user User object with password property
     * @param candidate The candidate password to check
     */
    public Boolean comparePassword(User user, String candidate) {
        return user.getPassword().equals(candidate);
    }

    /**
     * Fetch User object by username.
     * @param username Unique username
     * @return User object associated with the username
     * */
    public User fetchUser(String username) throws SQLException {
        return userDAO.getByUsername(username);
    }

    /**
     * Event handler for Login button.
     * @param event The event that triggered the handler
     */
    @FXML
    private void handleLogin(ActionEvent event) throws IOException, SQLException {
        String username = loginUsernameTF.getText();
        String password = loginPasswordTF.getText();
        User user = fetchUser(username);
        if (comparePassword(user, password)) System.out.println("logged in");
    }
}
