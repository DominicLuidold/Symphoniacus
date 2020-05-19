package at.fhv.orchestraria.UserInterface.Roster;

import at.fhv.orchestraria.UserInterface.MainWindow.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;

public class RosterWindow extends Application {

    private boolean _isAssignment;

    @Override
    public void start(Stage primaryStage) throws Exception{


            Locale.setDefault(Locale.ENGLISH);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DutyRoster.fxml"));

            Parent root = loader.load();

            primaryStage.setTitle("Duty Roster");
            primaryStage.setScene(new Scene(root));
            primaryStage.setOnHidden(e -> Platform.exit());

            RosterWindowController rosterWindowController = loader.getController();
            rosterWindowController.setMain(this, _isAssignment);


            primaryStage.show();

    }



    public static void main(String[] args) {
        launch(args);
    }


    public void setMode(boolean isAssignment) {
        _isAssignment = isAssignment;
    }
}
