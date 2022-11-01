package helper;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Redirect {
    /**
     * Stores single instance of this class.
     */
    private static Redirect instance;

    /**
     * Private constructor to ensure single instance.
     */
    private Redirect() {
    }

    /**
     * Gets the single instance of this class
     *
     * @return This class instance
     */
    public static Redirect getInstance() {
        if (instance == null) {
            instance = new Redirect();
        }
        return instance;
    }

    public void to(ActionEvent event, String view) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
