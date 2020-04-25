package at.fhv.teamb.symphoniacus.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.controlsfx.control.StatusBar;

public class TabPaneController implements Initializable {
    @FXML
    public AnchorPane calendar;

    @FXML
    private StatusBar statusBar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MasterController mc = MasterController.getInstance();
        mc.setStatusBar(this.statusBar);
    }
}
