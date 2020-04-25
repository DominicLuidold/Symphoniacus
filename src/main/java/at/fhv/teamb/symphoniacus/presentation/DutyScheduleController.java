package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.PersistenceState;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.presentation.internal.DutyPositionMusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.ScheduleButtonTableCell;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.GetMusiciansAvailableForPositionTask;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;

public class DutyScheduleController implements Initializable, Controllable {

    private static final Logger LOG = LogManager.getLogger(DutyScheduleController.class);
    private Duty duty;
    private Section section;
    private DutyScheduleManager dutyScheduleManager;
    private ActualSectionInstrumentation actualSectionInstrumentation;
    private DutyPosition selectedDutyPosition;

    @FXML
    public Button scheduleSaveBtn;

    @FXML
    private AnchorPane dutySchedule;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Label dutyTitle;

    @FXML
    private Label labelCurrentPosition;

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

    @FXML
    private TableColumn<MusicianTableModel, Button> columnSchedule2;

    @FXML
    private TableColumn<DutyPositionMusicianTableModel, Button> columnUnsetPosition;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.registerController();
        this.dutyScheduleManager = null;

        this.dutySchedule.setVisible(false);

        this.scheduleSaveBtn.setOnAction(e -> {
            this.dutyScheduleManager.persist(this.actualSectionInstrumentation);
            if (this.actualSectionInstrumentation
                .getPersistenceState()
                .equals(PersistenceState.EDITED)
            ) {
                Notifications.create()
                    .title("Saving Faild")
                    .text("Fomething went wrong while saving.")
                    .position(Pos.CENTER)
                    .hideAfter(new Duration(4000))
                    .showError();
            }
        });

        this.scheduleBackBtn.setOnAction(e -> {
            closeDutySchedule();
        });

        // add selected item click listener
        this.musicianTableWithRequests
            .getSelectionModel()
            .selectedItemProperty()
            .addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        addMusicianToPosition(
                            this.actualSectionInstrumentation,
                            newValue.getMusician(),
                            this.selectedDutyPosition
                        );
                    }
                }
            );

        this.musicianTableWithoutRequests.setOnMouseClicked((MouseEvent event) -> {
            // add selected item click listener
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {

                MusicianTableModel mtm =
                    this.musicianTableWithoutRequests.getSelectionModel().getSelectedItem();
                addMusicianToPosition(
                    this.actualSectionInstrumentation,
                    mtm.getMusician(),
                    this.selectedDutyPosition
                );
            }
        });

        this.columnSchedule2.setCellFactory(
            ScheduleButtonTableCell.<MusicianTableModel>forTableColumn(
                "Schedule",
                (MusicianTableModel mtm) -> {
                    LOG.debug("Schedule btn without requests has been pressed");
                    this.addMusicianToPosition(
                        this.actualSectionInstrumentation,
                        mtm.getMusician(),
                        this.selectedDutyPosition
                    );
                    return mtm;
                }
            )
        );

        this.columnUnsetPosition.setCellFactory(
            ScheduleButtonTableCell.<DutyPositionMusicianTableModel>forTableColumn(
                "Unset",
                (DutyPositionMusicianTableModel dpmtm) -> {
                    LOG.debug("Unset musician button has been pressed");

                    Optional<Musician> assignedMusician = dpmtm.getDutyPosition()
                        .getAssignedMusician();

                    this.selectedDutyPosition = dpmtm.getDutyPosition();

                    if (assignedMusician.isPresent()) {
                        this.removeMusicianFromPosition(assignedMusician.get());
                        this.initMusicianTableWithoutRequests();
                        this.positionsTable.refresh();
                    }
                    return dpmtm;
                }
            )
        );

        this.positionsTable
            .getSelectionModel()
            .selectedItemProperty()
            .addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setActualPosition(
                            newValue.getDutyPosition()
                        );
                    }
                }
            );
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
    }

    @Override
    public void hide() {
        this.dutySchedule.setVisible(false);
    }

    private void initMusicianTableWithoutRequests() {

        MasterController mc = MasterController.getInstance();
        mc.showStatusBarLoading();

        GetMusiciansAvailableForPositionTask task = new GetMusiciansAvailableForPositionTask(
            this.dutyScheduleManager,
            this.actualSectionInstrumentation.getDuty(),
            this.selectedDutyPosition,
            Boolean.FALSE
        );
        task.setOnSucceeded(event -> {
            Set<Musician> list = task.getValue();
            List<MusicianTableModel> guiList = new LinkedList<>();
            int i = 0;
            int selectedIndex = 0;
            MusicianTableModel selected = null;
            for (Musician domainMusician : list) {
                MusicianTableModel mtm = new MusicianTableModel(domainMusician);
                guiList.add(mtm);
                if (this.selectedDutyPosition.getAssignedMusician().isPresent()) {
                    LOG.debug("There is already a musician assigned for this position");
                    if (domainMusician.getShortcut().equals(
                        this.selectedDutyPosition.getAssignedMusician().get().getShortcut()
                    )) {
                        LOG.debug("Selecting index {}", i);
                        selectedIndex = i;
                        selected = mtm;
                    }
                }
                i++;
            }

            ObservableList<MusicianTableModel> observableListWithoutRequests =
                FXCollections.observableArrayList();
            observableListWithoutRequests.addAll(guiList);
            this.musicianTableWithoutRequests.setItems(observableListWithoutRequests);

            // auto select current musician.
            if (selected != null) {
                this.musicianTableWithoutRequests.requestFocus();
                this.musicianTableWithoutRequests.getSelectionModel().select(selected);
                this.musicianTableWithoutRequests.scrollTo(selectedIndex);
            }
            mc.showStatusBarLoaded();
        });
        new Thread(task).start();
    }

    private void initMusicianTableWithRequests() {
        Set<Musician> list = this.dutyScheduleManager.getMusiciansAvailableForPosition(
            this.actualSectionInstrumentation.getDuty(),
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
        if (this.dutyScheduleManager == null) {
            this.dutyScheduleManager = new DutyScheduleManager();
        }
        MasterController mc = MasterController.getInstance();
        mc.showStatusBarLoading();


        if (actualSectionInstrumentation == null) {
            Optional<ActualSectionInstrumentation> actualSectionInstrumentation = this
                .dutyScheduleManager
                .getInstrumentationDetails(
                    this.duty,
                    section
                );
            if (actualSectionInstrumentation.isEmpty()) {
                LOG.error("Found no asi for duty");
                return;
            } else {
                this.actualSectionInstrumentation = actualSectionInstrumentation.get();
            }
        }


        this.duty = this.actualSectionInstrumentation.getDuty();

        ObservableList<DutyPositionMusicianTableModel> observablePositionList =
            FXCollections.observableArrayList();
        List<DutyPosition> positionList =
            this.actualSectionInstrumentation.getDuty().getDutyPositions();

        for (DutyPosition dp : positionList) {
            // TODO
            observablePositionList.add(
                new DutyPositionMusicianTableModel(dp)
            );
        }
        this.positionsTable.setItems(observablePositionList);
        mc.showStatusBarLoaded();
    }

    protected void addMusicianToPosition(
        ActualSectionInstrumentation asi,
        Musician newMusician,
        DutyPosition dutyPosition
    ) {
        if (dutyPosition == null) {
            Notifications.create()
                .title("No position available")
                .text("You have to choose a position first")
                .position(Pos.CENTER)
                .hideAfter(new Duration(2000))
                .show();
            return;
        }

        if (dutyPosition.getAssignedMusician().isPresent()) {
            this.dutyScheduleManager.assignMusicianToPosition(
                asi,
                newMusician,
                dutyPosition.getAssignedMusician().get(),
                dutyPosition
            );
            LOG.debug(
                "New musician for position {} is: {} and oldMusician id {}",
                dutyPosition.getEntity().getInstrumentationPosition().getPositionDescription(),
                newMusician.getFullName(),
                dutyPosition.getAssignedMusician().get()
            );
        } else {
            this.dutyScheduleManager.assignMusicianToPosition(
                asi,
                newMusician,
                dutyPosition
            );
            LOG.debug(
                "New musician for position {} is: {}",
                dutyPosition.getEntity().getInstrumentationPosition().getPositionDescription(),
                newMusician.getFullName()
            );
        }

        Notifications.create()
            .title("Musician set")
            .text("Duty position has been updated.")
            .position(Pos.CENTER)
            .hideAfter(new Duration(2000))
            .show();

        this.positionsTable.refresh();
        this.initMusicianTableWithoutRequests();
    }

    private void removeMusicianFromPosition(Musician musician) {
        this.dutyScheduleManager.getMusiciansAvailableForPosition(
            this.duty,
            this.selectedDutyPosition,
            Boolean.FALSE
        );
        this.dutyScheduleManager.removeMusicianFromPosition(
            this.actualSectionInstrumentation,
            musician,
            this.selectedDutyPosition
        );
    }

    private void setActualPosition(DutyPosition dutyPosition) {
        LOG.debug("Current DutyPosition: " + dutyPosition
            .getEntity()
            .getInstrumentationPosition()
            .getPositionDescription() + "  Current Object: " + this
        );

        this.selectedDutyPosition = dutyPosition;
        this.initMusicianTableWithoutRequests();

        this.labelCurrentPosition.textProperty().bindBidirectional(
            new SimpleStringProperty(
                "Current position: "
                    + this.selectedDutyPosition
                        .getEntity().getInstrumentationPosition().getPositionDescription()
            )
        );
    }

    private void closeDutySchedule() {
        //TODO Abfrage SaveState(old, newold)? von actualSectionInstrumentation
        if (this.actualSectionInstrumentation
            .getPersistenceState()
            .equals(PersistenceState.EDITED)
        ) {
            ButtonType userSelection = getConfirmation();
            if ((userSelection == ButtonType.CLOSE) || (userSelection == ButtonType.CANCEL)) {
                return;
            } else if (userSelection == ButtonType.OK) {
                LOG.debug(
                    "Current dutyScheduleManager: {} ,Current actualSectionInstrumentation: {}",
                    this.dutyScheduleManager,
                    this.actualSectionInstrumentation
                );
                this.actualSectionInstrumentation = null;
                this.dutyScheduleManager = null;
                this.selectedDutyPosition = null;
                this.duty = null;
                this.section = null;
                this.musicianTableWithoutRequests.getItems().clear();
                this.positionsTable.getItems().clear();
                this.musicianTableWithoutRequests.refresh();
                this.positionsTable.refresh();
                this.hide();
                MasterController mc = MasterController.getInstance();
                CalendarController cc = (CalendarController) mc.get("CalendarController");
                cc.show();
            }
        } else {
            LOG.debug(
                "Current dutyScheduleManager: {} ,Current actualSectionInstrumentation: {}",
                this.dutyScheduleManager,
                this.actualSectionInstrumentation
            );
            this.actualSectionInstrumentation = null;
            this.dutyScheduleManager = null;
            this.selectedDutyPosition = null;
            this.duty = null;
            this.section = null;
            this.musicianTableWithoutRequests.getItems().clear();
            this.positionsTable.getItems().clear();
            this.musicianTableWithoutRequests.refresh();
            this.positionsTable.refresh();
            this.hide();
            MasterController mc = MasterController.getInstance();
            CalendarController cc = (CalendarController) mc.get("CalendarController");
            cc.show();
        }

    }

    private ButtonType getConfirmation() {
        Label label = new Label();;
        ButtonType buttonType = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Closing without saving");
        alert.setHeaderText("Are you sure want to close without saving?");

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
            buttonType = ButtonType.CLOSE;
            label.setText("No selection!");
        } else if (option.get() == ButtonType.OK) {
            buttonType = ButtonType.OK;
            label.setText("File deleted!");
        } else if (option.get() == ButtonType.CANCEL) {
            buttonType = ButtonType.CANCEL;
            label.setText("Cancelled!");
        } else {
            label.setText("-");
        }
        return buttonType;
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