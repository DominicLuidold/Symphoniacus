package at.fhv.teamb.symphoniacus.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.DutyPositionMusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicianTableModel;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;

public class DutyScheduleController implements Initializable, Controllable {

    private static final Logger LOG = LogManager.getLogger(DutyScheduleController.class);
    private Duty duty;
    private Section section;
    private DutyScheduleManager dutyScheduleManager;
    private ActualSectionInstrumentation actualSectionInstrumentation;
    private DutyPosition selectedDutyPosition;

    @FXML
    private AnchorPane dutySchedule;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Label dutyTitle;

    @FXML
    private TableView<DutyPositionMusicianTableModel> positionsTable;

    @FXML
    private TableView<MusicianTableModel> musicianTableWithRequests;

    @FXML
    private TableView<MusicianTableModel> musicianTableWithoutRequests;

    @FXML
    private SplitPane rightSplitPane;

    @FXML
    private SplitPane leftSplitPane;

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

        this.disableDetailView();
    }

    @Override
    public void registerController() {
        MasterController mc = MasterController.getInstance();
        mc.put("DutyScheduleController", this);
    }

    @Override
    public void show() {
        this.initDutyPositionsTableWithMusicians();
        this.dutySchedule.setVisible(true);
        this.disableDetailView();
    }

    @Override
    public void hide() {
        this.dutySchedule.setVisible(false);
        //TODO remove listener positionTable
        //positionsTable.getSelectionModel().clearSelection();
    }

    private List<Musician> getMockedMusicians() {
        LOG.debug("returning mocked musicians");
        Musician m = new Musician(new MusicianEntity());
        m.getEntity().setUser(new UserEntity());
        m.getEntity().setSection(new SectionEntity());

        Musician m2 = new Musician(new MusicianEntity());
        m2.getEntity().setUser(new UserEntity());
        m2.getEntity().setSection(new SectionEntity());

        List<Musician> demoMusicianList = new LinkedList<>();
        demoMusicianList.add(m);
        demoMusicianList.add(m2);

        System.out.println(demoMusicianList);

        return demoMusicianList;
    }

    private void initMusicianTableWithoutRequests() {
        // TODO remove mocking when finished
        this.dutyScheduleManager = Mockito.mock(DutyScheduleManager.class);
        try {
            Mockito
                .when(
                    this.dutyScheduleManager.getMusiciansAvailableForPosition(
                        any(DutyPosition.class),
                        anyBoolean()
                    )
                )
                .thenReturn(
                    getMockedMusicians()
                );
        } catch (Exception e) {
            LOG.error(e);
        }

        // add selected item click listener
        this.musicianTableWithoutRequests
            .getSelectionModel()
            .selectedItemProperty()
            .addListener(
                (observable, oldValue, newValue) -> addMusicianToPosition(
                    newValue.getMusician(),
                    this.selectedDutyPosition
                )
            );

        List<Musician> list = this.dutyScheduleManager.getMusiciansAvailableForPosition(
            this.selectedDutyPosition,
            Boolean.FALSE
        );

        List<MusicianTableModel> guiList = new LinkedList<>();
        for (Musician domainMusician : list) {
            guiList.add(new MusicianTableModel(domainMusician));
        }
        ObservableList<MusicianTableModel> observableList =
            FXCollections.observableArrayList();
        observableList.addAll(guiList);
        this.musicianTableWithoutRequests.setItems(observableList);
    }

    private void initMusicianTableWithRequests() {
        // TODO remove mocking when finished
        this.dutyScheduleManager = Mockito.mock(DutyScheduleManager.class);
        try {
            Mockito
                .when(
                    this.dutyScheduleManager.getMusiciansAvailableForPosition(
                        any(DutyPosition.class),
                        anyBoolean()
                    )
                )
                .thenReturn(
                    getMockedMusicians()
                );
        } catch (Exception e) {
            LOG.error(e);
        }

        // add selected item click listener
        this.musicianTableWithRequests
            .getSelectionModel()
            .selectedItemProperty()
            .addListener(
                (observable, oldValue, newValue) -> addMusicianToPosition(
                    newValue.getMusician(),
                    this.selectedDutyPosition
                )
            );

        List<Musician> list = this.dutyScheduleManager.getMusiciansAvailableForPosition(
            this.selectedDutyPosition,
            Boolean.TRUE
        );

        System.out.println("musician available: " + list.size());

        List<MusicianTableModel> guiList = new LinkedList<>();
        for (Musician domainMusician : list) {
            guiList.add(new MusicianTableModel(domainMusician));
        }
        ObservableList<MusicianTableModel> observableList =
            FXCollections.observableArrayList();
        observableList.addAll(guiList);
        this.musicianTableWithoutRequests.setItems(observableList);
    }

    private void initDutyPositionsTableWithMusicians() {
        Optional<ActualSectionInstrumentation> actualSectionInstrumentation = this
            .dutyScheduleManager
            .getInstrumentationDetails(
                this.duty,
                section
            );

        if (!actualSectionInstrumentation.isPresent()) {
            LOG.error("Found no asi for duty");
            return;
        }

        ObservableList<DutyPositionMusicianTableModel> observablePositionList =
            FXCollections.observableArrayList();
        List<DutyPosition> positionList =
            actualSectionInstrumentation.get().getDuty().getDutyPositions();

        for (DutyPosition dp : positionList) {
            // TODO
            observablePositionList.add(new DutyPositionMusicianTableModel(dp, dp.getMusician()));
        }
        this.positionsTable.setItems(observablePositionList);

        this.positionsTable
            .getSelectionModel()
            .selectedItemProperty()
            .addListener(
                (observable, oldValue, newValue) -> setActualPosition(
                    newValue.getDutyPosition()
                )
            );
    }

    protected void addMusicianToPosition(Musician musician, DutyPosition dutyPosition) {
        LOG.debug("Selected musician changed");
        LOG.error("TODO: add musician to position here");
        this.disableDetailView();
    }

    private void setActualPosition(DutyPosition dutyPosition) {
        this.enableDetailView();

        LOG.debug("Current DutyPosition: " + dutyPosition
            .getEntity()
            .getInstrumentationPosition()
            .getPositionDescription() + "  Current Object: " + this
        );
        this.selectedDutyPosition = dutyPosition;
        this.initMusicianTableWithRequests();
        this.initMusicianTableWithoutRequests();
    }

    private void enableDetailView() {
        this.leftSplitPane.setDividerPosition(0,0.3);
    }

    private void disableDetailView() {
        this.leftSplitPane.setDividerPosition(0,0.7);
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

    public void setSection(Section section) {
        this.section = section;
    }
}