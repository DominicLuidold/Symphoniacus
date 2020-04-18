package at.fhv.teamb.symphoniacus.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class DutyScheduleController implements Initializable {

    @FXML
    private AnchorPane dutySchedule;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.dutySchedule.setVisible(false);
    }
}
