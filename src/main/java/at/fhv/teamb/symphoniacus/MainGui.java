package at.fhv.teamb.symphoniacus;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main GUI Controller.
 *
 * @author Valentin Goronjic
 */
public class MainGui extends Application {
    private static final Logger LOG = LogManager.getLogger(MainGui.class);

    @Override
    public void start(Stage stage) {
        try {
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"), bundle);

            Scene scene = new Scene(root);
            scene.getStylesheets().add("css/styles.css");
            stage.setTitle("Symphoniacus");
            stage.setScene(scene);
            stage.setMinHeight(768);
            stage.setMinWidth(1366);
            stage.getIcons().add(
                new Image(
                    "images/icon.png"
                )
            );
            stage.show();
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
