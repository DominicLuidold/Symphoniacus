package at.fhv.teamb.symphoniacus;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", new Locale("en", "US"));
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"), bundle);

            Scene scene = new Scene(root);
            stage.setTitle("Symphoniacus");
            stage.setScene(scene);
            stage.setMinHeight(768);
            stage.setMinWidth(1366);
            stage.show();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }
}
