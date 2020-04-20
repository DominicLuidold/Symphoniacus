package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPosition;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import at.fhv.teamb.symphoniacus.presentation.internal.DutyPositionMusicianTableModel;
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
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DutyScheduleController implements Initializable, Controllable {

    private static final Logger LOG = LogManager.getLogger(DutyScheduleController.class);
    private Duty duty;
    private DutyScheduleManager dutyScheduleManager;

    @FXML
    private AnchorPane dutySchedule;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Label dutyTitle;

    @FXML
    private TableView<DutyPositionMusicianTableModel> positionsTable;

    @FXML
    private TableColumn<DutyPositionMusicianTableModel, String> role;

    @FXML
    private TableColumn<DutyPositionMusicianTableModel, String> musicianShortcut;

    @FXML
    private TableView<MusicianTableModel> musicianTableWithRequests;

    @FXML
    private TableView<MusicianTableModel> musicianTableWithoutRequests;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.registerController();
        this.dutyScheduleManager = new DutyScheduleManager();

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

        showDutyPositionsWithMusicians();
        this.musicianTableWithRequests.getSelectionModel().selectedItemProperty()
            .addListener(
                (observable, oldValue, newValue) -> addMusicianToPosition(
                    newValue.getMusician(),
                    null)
            );

        this.musicianTableWithoutRequests.getSelectionModel().selectedItemProperty()
            .addListener(
                (observable, oldValue, newValue) -> addMusicianToPosition(
                    newValue.getMusician(),
                    null)
            );
    }

    protected void addMusicianToPosition(Musician musician, DutyPosition dutyPosition) {
        LOG.debug("Selected musician changed");
        LOG.error("TODO: add musician to position here");
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

    private void showDutyPositionsWithMusicians() {
        ObservableList<DutyPositionMusicianTableModel> poslist =
            FXCollections.observableArrayList();

        ActualSectionInstrumentation actualSectionInstrumentation;

        Musician m = new Musician();
        DutyPosition dp = new DutyPosition();

        poslist.add(new DutyPositionMusicianTableModel(dp, m));
        this.positionsTable.setItems(poslist);
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