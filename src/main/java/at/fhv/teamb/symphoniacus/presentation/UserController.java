package at.fhv.teamb.symphoniacus.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserController {

    @FXML
    private TextFlow txtFlowSection;

    @FXML
    private MenuItem userLogout;

    @FXML
    void handleUserLogout(ActionEvent event) {
    }
}
