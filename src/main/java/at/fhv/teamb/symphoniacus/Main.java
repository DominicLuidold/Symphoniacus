package at.fhv.teamb.symphoniacus;

import at.fhv.teamb.symphoniacus.controllers.UserController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

//import static javafx.application.ConditionalFeature.FXML;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {


        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/basic_frame.fxml"));
            UserController controller = new UserController();
            Scene scene = new Scene(root);
            stage.setTitle("Symphoniacus");
            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            e.fillInStackTrace();
        }

    }
}