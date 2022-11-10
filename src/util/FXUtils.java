package util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Singleton class containing custom JavaFX utility methods.
 *
 * @author Jeramiah Coffey
 */
public class FXUtils {
    /**
     * Stores single instance of this class.
     */
    private static FXUtils instance;

    /**
     * Private constructor to ensure single instance.
     */
    private FXUtils() {
    }

    /**
     * Gets the single instance of this class
     *
     * @return This class instance
     */
    public static FXUtils getInstance() {
        if (instance == null) {
            instance = new FXUtils();
        }
        return instance;
    }

    /**
     * Redirect to an FXML view.
     *
     * @param event The event that triggered the handler which called this method.
     * @param view  The path to the FXML view to redirect to.
     */
    public void redirect(ActionEvent event, String view) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Display a confirmation dialog.
     *
     * @param message The message to display in the dialog.
     * @return Whether the user confirmed or denied the dialog.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean confirm(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        return alert.getResult() == ButtonType.YES;
    }

    /**
     * Display an error dialog.
     *
     * @param message The message to display in the dialog.
     */
    public void error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.CLOSE);
        alert.setHeaderText("An issue was encountered");
        alert.showAndWait();
    }

    /**
     * Displays a generic error dialog, and exits the program on dialog close. Status: 101
     */
    public void errorAndExit() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Shutting down, please try again later.", ButtonType.CLOSE);
        alert.setHeaderText("An issue was encountered");
        alert.showAndWait();
        System.exit(101);
    }

    /**
     * Display an informative dialog.
     *
     * @param message The message to display in the dialog.
     * @param header  The dialog header text displayed to the left of the "Info" icon.
     * @param title   The dialog title displayed in the window header.
     */
    public void inform(String message, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.CLOSE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}
