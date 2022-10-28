import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/FirstScene.fxml")));
        stage.setTitle("First Scene");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
}
