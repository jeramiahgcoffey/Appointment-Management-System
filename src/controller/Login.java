package controller;

import db.UserCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import org.jetbrains.annotations.NotNull;
import util.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
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

    /**
     * Initializes FXML controller. Sets userDAO to the singleton instance of the UserDAO class.
     *
     * @param url            URL used to resolve paths, null if not known
     * @param resourceBundle Resources used to localize the root object, null if not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zoneIdLabel.setText(String.valueOf(ZoneId.systemDefault()));
    }

    /**
     * Compare candidate password with user's password.
     *
     * @param user      User object with password property
     * @param candidate The candidate password to check
     */
    private Boolean comparePassword(@NotNull User user, String candidate) {
        return user.getPassword().equals(candidate);
    }

    /**
     * Fetch User object by username.
     *
     * @param username Unique username
     * @return User object associated with the username
     */
    public User fetchUser(String username) throws SQLException {
        return UserCRUD.getByUsername(username);
    }

    /**
     * Handle Login button clicked.
     *
     * @param event The event that triggered the handler
     */
    @FXML
    private void handleLogin(ActionEvent event) throws IOException, SQLException {
        ResourceBundle bundle = ResourceBundle.getBundle("strings", Locale.getDefault());

        loginUsernameErrorLabel.setText("");
        loginPasswordErrorLabel.setText("");

        String username = loginUsernameTF.getText();
        String password = loginPasswordTF.getText();

        if (Objects.equals(username, "")) {
            loginUsernameErrorLabel.setText(bundle.getString("username.error"));
            return;
        }
        if (Objects.equals(password, "")) {
            loginPasswordErrorLabel.setText(bundle.getString("password.error"));
            return;
        }

        User user = fetchUser(username);
        if (user == null) {
            loginPasswordErrorLabel.setText(bundle.getString("creds.error"));
            loginPasswordTF.setText("");
            return;
        }
        if (!comparePassword(user, password)) {
            loginPasswordErrorLabel.setText(bundle.getString("creds.error"));
            loginPasswordTF.setText("");
            return;
        }

        FXUtils.getInstance().redirect(event, "/view/schedule.fxml");
    }
}
