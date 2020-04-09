package at.fhv.teamb.symphoniacus.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.text.TextFlow;

public class UserController {
    @FXML
    private TextFlow txtFlowSection;

    @FXML
    private MenuItem userLogout;

    @FXML
    void handleUserLogout(ActionEvent event) {
        System.out.println("test");
    }
}
