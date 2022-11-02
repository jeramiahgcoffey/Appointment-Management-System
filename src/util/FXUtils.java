package util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
}