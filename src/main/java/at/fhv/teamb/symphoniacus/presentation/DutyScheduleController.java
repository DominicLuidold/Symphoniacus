package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicianTableModel;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DutyScheduleController implements Initializable, Controllable {

    private static final Logger LOG = LogManager.getLogger(DutyScheduleController.class);
    private Duty duty;
    @FXML
    private AnchorPane dutySchedule;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Label dutyTitle;

    @FXML
    private TableView<MusicianTableModel> musicianTableWithRequests;

    @FXML
    private TableColumn<MusicianTableModel, String> columnName;

    @FXML
    private TableView<Musician> musicianTableWithoutRequests;

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
        ObservableList<MusicianTableModel> demoList = FXCollections.observableArrayList();
        Musician m = new Musician();
        m.setUserId(1);
        m.setSection(new Section());
        demoList.add(new MusicianTableModel(m));
        this.musicianTableWithRequests.setItems(demoList);
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

    /**
     * Set the actual Duty for Controller.
     *
     * @param duty actual Duty.
     */
    public void setDuty(Duty duty) {
        this.duty = duty;

        LOG.debug("Binding duty title to: " + duty.getTitle());
        this.dutyTitle.textProperty().bind(
            new SimpleStringProperty(
                duty
                    .getEntity()
                    .getStart()
                    .format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")
                    )
                    + " - "
                    + duty.getTitle()
            )
        );
    }
}