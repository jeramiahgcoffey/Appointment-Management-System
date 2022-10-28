import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/** Main class for the application. */
public class Main extends Application {
    /**
     * Main function which gets called when the application is run.
     *
     * @param args Optional arguments passed to the main function.
     */
    public static void main(String[] args) {
        DBConnection.openConnection();
        DBConnection.closeConnection();
        launch(args);
    }

    /**
     * @param stage
     */
    @Override
    public void start(@NotNull Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login.fxml")));
        stage.setTitle("Appointment Management System");
        stage.setScene(new Scene(root, 405, 250));
        stage.setResizable(false);
        stage.show();
    }
}
