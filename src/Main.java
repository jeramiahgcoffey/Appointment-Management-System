import db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Objects;

/** Main class for the application. */
public class Main extends Application {
    /**
     * Main function which gets called when the application is run.
     *
     * @param args Optional arguments passed to the main function.
     */
    public static void main(String[] args) throws SQLException {
        DBConnection.openConnection();
//        System.out.println(UserDAO.getInstance().getByUsername("test").getPassword());
        launch(args);
        DBConnection.closeConnection();
    }

    /**
     * @param stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/login.fxml")));
        stage.setTitle("Appointment Management System");
        stage.setScene(new Scene(root, 405, 250));
        stage.setResizable(false);
        stage.show();
    }
}
