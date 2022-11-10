import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Main class for the application.
 */
public class Main extends Application {
    /**
     * Main method that's called when the application is run.
     *
     * @param args Optional arguments passed to the main method.
     */
    public static void main(String[] args) {
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }

    /**
     * Start the JavaFX Application. Called when launch is called from the main method.
     * Sets Login page language to French if it is the default system locale.
     *
     * @param stage The first stage to load on program start.
     */
    @Override
    public void start(@NotNull Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("strings", Locale.getDefault());
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")), bundle);
        stage.setTitle("Appointment Management System");
        stage.setScene(new Scene(root, 405, 250));
        stage.setResizable(false);
        stage.show();
    }
}
