package at.fhv.orchestraria.UserInterface.Login;


import at.fhv.orchestraria.UserInterface.MainWindow.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginWindow extends Application {

    private Stage _stage;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();

        stage.setTitle("Login");
        stage.getIcons().add(new Image(MainWindow.class.getResourceAsStream(
            "/images/team-c/orchestraria_icon.png")));

        LoginWindowController loginWindowController = loader.getController();
        loginWindowController.setMain(this);

        stage.setScene( new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);

        LoginWindowStyle.allowDrag(root, stage);
        LoginWindowStyle.stageDimension(stage.getWidth(), stage.getHeight());

        _stage = stage;
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    public void close() {
        _stage.close();
    }
}
