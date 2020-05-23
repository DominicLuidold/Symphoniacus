package at.fhv.orchestraria.UserInterface.MainWindow;

import at.fhv.orchestraria.UserInterface.Login.LoginWindow;
import at.fhv.orchestraria.UserInterface.Login.LoginWindowController;
import at.fhv.orchestraria.UserInterface.Roster.RosterWindow;
import at.fhv.orchestraria.domain.Imodel.IUser;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindowController {
    private final static Logger LOGGER = Logger.getLogger(MainWindowController.class.getName());

    @FXML
    private JFXButton dynamic_bttn;

    @FXML
    private  Label _loggedInUserName;

    @FXML
    private ToggleButton dynamic_navBttn;

    //Views

    @FXML
    void notAvailableAlert(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Not ready yet");
        alert.setHeaderText(null);
        alert.setContentText("Sorry still in development!");

        alert.showAndWait();
    }

    public MainWindow _mainWindow;


    public void setMain(MainWindow main){
        _mainWindow = main;

        if(LoginWindowController.getLoggedInUser().getMusician().isDutyScheduler()){
            dynamic_bttn.setText("Assignment Roster");
            dynamic_navBttn.setText("Assignment Roster");
            dynamic_navBttn.setPrefWidth(120);
        }
        setLoggedInUserName(LoginWindowController.getLoggedInUser());
    }


    /**
     *
     * @param event is the action of clicking the duty roster button
     */
    public void changeScreenDutyRoster(ActionEvent event) throws Exception {
        startRosterWindow(event, false);
    }

    /**
     * starts RosterWindow
     * @param event
     * @param isAssignment
     * @throws Exception
     */
    private void startRosterWindow(ActionEvent event, boolean isAssignment) throws Exception{
        RosterWindow rw = new RosterWindow();

        rw.setMode(isAssignment);
        //This line gets the Stage information
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        rw.start(window);

    }

    @FXML
    void redirectByMusicianRole(ActionEvent event) throws Exception{
        if(LoginWindowController.getLoggedInUser().getMusician().isDutyScheduler()){
            startRosterWindow(event, true);
        }else{
            notAvailableAlert(event);
        }
    }

    /**
     * Logout
     * @param event
     * @throws Exception
     */
    public void logout(ActionEvent event) throws Exception {
        LoginWindowController.logout();

        try {
            LoginWindow lw = new LoginWindow();
            Stage homeWindowDecorated = (Stage) ((Node) event.getSource()).getScene().getWindow();

            //make next undecorated
            Stage undecoratedWindow = new Stage();
            undecoratedWindow.initStyle(StageStyle.UNDECORATED);
            Scene scene = homeWindowDecorated.getScene();
            homeWindowDecorated.setScene(null);
            undecoratedWindow.setScene(scene);
            undecoratedWindow.hide();
            homeWindowDecorated.hide();
            lw.start(undecoratedWindow);
        }catch(Exception e){
            LOGGER.log(Level.INFO, "Exception ", e);
        }
    }


    public void setLoggedInUserName(IUser user){
        _loggedInUserName.setText(user.getFirstName() + " " + user.getLastName());
    }

}
