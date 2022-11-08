package controller;

import dataAccess.AppointmentCRUD;
import dataAccess.UserCRUD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.User;
import org.jetbrains.annotations.NotNull;
import util.FXUtils;
import util.Log;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * JavaFX controller for the Login view.
 *
 * @author Jeramiah Coffey
 */
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

    public static User currentUser;

    /**
     * Initializes the view. Set ZoneID Label to the host system default.
     *
     * @param url            URL used to resolve paths, null if not known.
     * @param resourceBundle Resources used to localize the root object, null if not localized.
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
     * @param event The event that was fired from the login page.
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
            Log.loginAttempt(username, false);
            return;
        }
        if (!comparePassword(user, password)) {
            loginPasswordErrorLabel.setText(bundle.getString("creds.error"));
            loginPasswordTF.setText("");
            Log.loginAttempt(username, false);
            return;
        }
        Log.loginAttempt(username, true);
        currentUser = user;
        FXUtils.getInstance().redirect(event, "/view/appointments.fxml");
        checkForUpcoming(currentUser.getUserId());
    }

    /**
     * Check if a user has upcoming appointments that begin within 15 minutes of the time of logging in.
     *
     * @param userId The logged-in User's ID.
     */
    private void checkForUpcoming(int userId) {
        Stream<Appointment> usersApts = Objects.requireNonNull(AppointmentCRUD.getAll())
                .stream()
                .filter(apt -> apt.getUserId() == userId);

        List<Appointment> upcomingApts = usersApts
                .filter(apt -> {
                    long minRemaining = ChronoUnit.MINUTES
                            .between(LocalDateTime.now(), apt.getStartTimestamp().toLocalDateTime());
                    return minRemaining >= 0 && minRemaining <= 15;
                })
                .toList();

        if (!upcomingApts.isEmpty()) {
            String msg = "";
            // TODO: Fix message
            for (Appointment apt : upcomingApts) {
                msg += apt.toString() + "\n";
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.CLOSE);
            alert.setTitle("Upcoming Appointments");
            alert.setHeaderText("The following appointments begin in 15 minutes or less.");
            alert.showAndWait();
        }
    }
}
