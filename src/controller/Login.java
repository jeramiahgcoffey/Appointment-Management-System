package controller;

import db.UserDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
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
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private ComboBox<String> langComboBox;

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

    private String language = "English";
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

        String[] languages = {"English", "French"};
        langComboBox.setItems(FXCollections.observableArrayList(languages));
        langComboBox.setValue(this.language);
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
