package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class DutyScheduleController implements Initializable, Controllable {

    private Duty duty;

    @FXML
    private AnchorPane dutySchedule;

    @FXML
    private Button scheduleBackBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.registerController();

        this.dutySchedule.setVisible(false);
        this.scheduleBackBtn.setOnAction(e -> {
            this.hide();
            MasterController mc = MasterController.getInstance();
            CalendarController cc = (CalendarController) mc.get("CalendarController");
            cc.show();
        });
    }

    @Override
    public void registerController() {
        MasterController mc = MasterController.getInstance();
        mc.put("DutyScheduleController", this);
    }

    @Override
    public void show() {
        this.dutySchedule.setVisible(true);
    }

    @Override
    public void hide() {
        this.dutySchedule.setVisible(false);
    }

    public void setDuty(Duty duty) {
        this.duty = duty;
    }
}
