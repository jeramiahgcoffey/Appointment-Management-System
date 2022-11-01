package controller;

import db.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private Label loginPasswordErrorLabel;

    @FXML
    private Label loginUsernameErrorLabel;

    @FXML
    private TextField loginUsernameTF;

    @FXML
    private PasswordField loginPasswordTF;

    @FXML
    private Label zoneIdLabel;

    private UserDAO userDAO;


    /**
     * Initializes FXML controller. Sets userDAO to the singleton instance of the UserDAO class.
     *
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userDAO = UserDAO.getInstance();
        zoneIdLabel.setText(String.valueOf(ZoneId.systemDefault()));
    }

    /**
     * Compare candidate password with user's password.
     *
     * @param user      User object with password property
     * @param candidate The candidate password to check
     */
    public Boolean comparePassword(@NotNull User user, String candidate) {
        return user.getPassword().equals(candidate);
    }

    /**
     * Fetch User object by username.
     *
     * @param username Unique username
     * @return User object associated with the username
     */
    public User fetchUser(String username) throws SQLException {
        return userDAO.getByUsername(username);
    }

    /**
     * Event handler for Login button.
     *
     * @param event The event that triggered the handler
     */
    @FXML
    private void handleLogin(ActionEvent event) throws IOException, SQLException {
        loginUsernameErrorLabel.setText("");
        loginPasswordErrorLabel.setText("");

        String username = loginUsernameTF.getText();
        String password = loginPasswordTF.getText();

        if (Objects.equals(username, "")) {
            loginUsernameErrorLabel.setText("Please enter your username.");
            return;
        }
        if (Objects.equals(password, "")) {
            loginPasswordErrorLabel.setText("Please enter your password.");
            return;
        }

        User user = fetchUser(username);
        if (user == null) {
            loginPasswordErrorLabel.setText("Invalid credentials.");
            loginPasswordTF.setText("");
            return;
        }
        if (!comparePassword(user, password)) {
            loginPasswordErrorLabel.setText("Invalid credentials.");
            loginPasswordTF.setText("");
            return;
        }
        ;

        redirectToSchedule(event);
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
}
