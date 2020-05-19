package at.fhv.orchestraria.UserInterface.MainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Orchestraria");
        primaryStage.getIcons().add(new Image(MainWindow.class.getResourceAsStream("/orchestraria_icon.png")));
        primaryStage.setScene(new Scene(root));

        MainWindowController mainWindowController = loader.getController();
        mainWindowController.setMain(this);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


}
