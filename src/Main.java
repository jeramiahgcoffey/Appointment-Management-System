import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Main class for the application.
 */
public class Main extends Application {
    /**
     * Main function that's called when the application is run.
     *
     * @param args Optional arguments passed to the main function.
     */
    public static void main(String[] args) throws IOException {
        DBConnection.openConnection();
        launch(args);
        DBConnection.closeConnection();
    }

    /**
     * @param stage The first stage to load on program start
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
