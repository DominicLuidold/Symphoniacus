package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.MusicalPiece;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.AlertHelper;
import at.fhv.teamb.symphoniacus.presentation.internal.DutyPositionMusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicalPieceComboView;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicianPointsTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.OldDutyComboView;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.ScheduleButtonTableCell;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.GetMusiciansAvailableForPositionTask;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.GetOtherDutiesTask;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.GetPositionsWithMusiciansTask;
import java.net.URL;
import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;

public class DutyScheduleController
    implements Controllable, Initializable, Parentable<DutySchedulerCalendarController> {

    private static final Logger LOG = LogManager.getLogger(DutyScheduleController.class);
    @FXML
    public Button scheduleSaveBtn;
    private Duty duty;
    private SectionDto section;
    private DutyScheduleManager dutyScheduleManager;
    private DutyManager dutyManager;
    private ActualSectionInstrumentation actualSectionInstrumentation;
    private HashMap<DutyPosition, Optional<Musician>> oldMusicianOnPosition = new HashMap<>();
    private DutyPosition selectedDutyPosition;
    private ResourceBundle resources;
    private DutySchedulerCalendarController parentController;

    @FXML
    private AnchorPane dutySchedule;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Button getOldDutyBtn;

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
    private TableColumn<MusicianTableModel, Button> columnScheduleBtnWithRequests;

    @FXML
    private TableColumn<MusicianTableModel, Button> columnScheduleBtnWithoutRequests;

    @FXML
    private TableColumn<DutyPositionMusicianTableModel, Button> columnUnsetPosition;

    @FXML
    private ComboBox<OldDutyComboView> oldDutySelect;

    @FXML
    private ComboBox<MusicalPieceComboView> musicalPieceSelect;

    @FXML
    private TableColumn<MusicianPointsTableModel, String> columnPointsWithRequests;

    @FXML
    private TableColumn<MusicianPointsTableModel, String> columnPointsWithoutRequests;

    private CheckListView<MusicalPiece> scheduleMusicalPiecesChkListView = new CheckListView<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.registerController();

        this.dutyScheduleManager = null;
        this.dutySchedule.setVisible(false);
        this.resources = resources;

        // Set button actions
        this.setButtonActions();

        // Set mouse click events
        this.setMouseClickEvents();

        // set mouse hover events
        this.setMouseHoverEvents();

        // Set row factories
        this.setRowFactories();

        // Set cell factories
        this.setCellFactories();

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

        LOG.debug("Initialized DutyScheduleController");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeWithParent() {
        LOG.debug("Initialized DutyScheduleController with parent");
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
            this.dutySchedule
        );
        task.setOnSucceeded(event -> {
            Set<Musician> musiciansWithoutWishRequest = task.getValue();
            Set<Musician> musiciansWithWishRequest = new HashSet<>();

            Iterator<Musician> itr = musiciansWithoutWishRequest.iterator();
            while (itr.hasNext()) {
                Musician m = itr.next();
                if (m.getWishRequest().isPresent()) {
                    musiciansWithWishRequest.add(m);
                    itr.remove();
                }
            }

            this.initMusicianTableWithRequests(musiciansWithWishRequest);

            List<MusicianTableModel> guiList = new LinkedList<>();
            int i = 0;
            int selectedIndex = 0;
            MusicianTableModel selected = null;
            for (Musician domainMusician : musiciansWithoutWishRequest) {
                MusicianTableModel mtm = new MusicianTableModel(domainMusician);
                guiList.add(mtm);
                if (this.selectedDutyPosition != null
                    && this.selectedDutyPosition.getAssignedMusician().isPresent()
                ) {
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
        task.setOnFailed(event -> AlertHelper.showAlert(
            Alert.AlertType.ERROR,
            this.scheduleSaveBtn.getScene().getWindow(),
            this.resources.getString("alert.duty.schedule.musicians.load.failed.title"),
            this.resources.getString("alert.duty.schedule.musicians.load.failed.message")
        ));
        new Thread(task).start();
    }

    private void initMusicianTableWithRequests(Set<Musician> list) {
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

        ObservableList<MusicianTableModel> observableListWithRequests =
            FXCollections.observableArrayList();
        observableListWithRequests.addAll(guiList);
        this.musicianTableWithRequests.setItems(observableListWithRequests);

        // auto select current musician.
        if (selected != null) {
            this.musicianTableWithRequests.requestFocus();
            this.musicianTableWithRequests.getSelectionModel().select(selected);
            this.musicianTableWithRequests.scrollTo(selectedIndex);
        }
    }

    private void initMultipleMusicalPieces() {
        AlertHelper.showAlert(
            Alert.AlertType.INFORMATION,
            this.dutySchedule.getParent().getScene().getWindow(),
            this.resources.getString(
                "tab.duty.schedule.multiple.pieces.dialog.beginning.title"
            ),
            this.resources.getString("tab.duty.schedule.multiple.pieces.dialog.beginning.body")
        );

        // this one should obviously not be empty
        // add entries to combobox
        for (MusicalPiece mp : this.duty.getMusicalPieces()) {
            this.musicalPieceSelect.getItems().add(
                new MusicalPieceComboView(mp)
            );
        }
        // show pretty name
        final StringConverter<MusicalPieceComboView> converter =
            new StringConverter<>() {
                @Override
                public String toString(MusicalPieceComboView object) {
                    return object.getName();
                }

                @Override
                public MusicalPieceComboView fromString(String nameOfPiece) {
                    return null;
                }
            };
        this.musicalPieceSelect.setConverter(converter);
        this.musicalPieceSelect.visibleProperty().set(true);
    }

    /**
     * Initialize the left side of the View with the Actualsectioninstrumentation
     * and their Musicians.
     */
    private void initDutyPositionsTableWithMusicians() {
        this.initOldDutyComboView();
        if (this.dutyScheduleManager == null) {
            this.dutyScheduleManager = new DutyScheduleManager();
        }
        MasterController mc = MasterController.getInstance();
        mc.showStatusBarLoading();

        if (this.actualSectionInstrumentation == null) {

            GetPositionsWithMusiciansTask task = new GetPositionsWithMusiciansTask(
                this.dutyScheduleManager,
                this.duty,
                this.section,
                this.dutySchedule
            );
            task.setOnSucceeded(event -> {
                Optional<ActualSectionInstrumentation> currentAsi = task.getValue();
                if (currentAsi.isEmpty()) {
                    LOG.error("Found no asi for duty");
                } else {
                    this.actualSectionInstrumentation = currentAsi.get();
                    this.duty = this.actualSectionInstrumentation.getDuty();
                    LOG.debug("Musical Pieces? {}", this.duty.getMusicalPieces().size());

                    if (this.duty.getMusicalPieces().size() > 1) {
                        LOG.debug("Multiple musical piece for this duty found");
                        this.initMultipleMusicalPieces();
                    }

                    ObservableList<DutyPositionMusicianTableModel> observablePositionList =
                        FXCollections.observableArrayList();
                    List<DutyPosition> positionList =
                        this.actualSectionInstrumentation.getDuty().getDutyPositions();

                    for (DutyPosition dp : positionList) {
                        this.oldMusicianOnPosition.put(dp, dp.getAssignedMusician());
                        observablePositionList.add(
                            new DutyPositionMusicianTableModel(
                                dp
                            )
                        );
                    }

                    if (this.duty.getMusicalPieces().isEmpty()) {
                        LOG.error("No musical Pieces found for duty!");
                        AlertHelper.showAlert(
                            Alert.AlertType.ERROR,
                            this.dutySchedule.getParent().getScene().getWindow(),
                            "Error",
                            "No musical pieces found"
                        );
                        return;
                    }

                    FilteredList<DutyPositionMusicianTableModel> filteredList = new FilteredList<>(
                        observablePositionList,
                        dp -> {
                            // always select first musical piece as default
                            return dp.getDutyPosition().getEntity().getInstrumentationPosition()
                                .getInstrumentation().getMusicalPiece().getName().equals(
                                    this.duty.getMusicalPieces().get(0).getEntity().getName()
                                );
                        }
                    );
                    this.musicalPieceSelect.valueProperty().addListener(
                        (observable, oldValue, newValue) -> {
                            if (newValue != null) {
                                LOG.debug("New selected piece is {}", newValue.getName());
                                filteredList.setPredicate(dutyPosition -> {
                                    InstrumentationPositionEntity instrumentationPosition =
                                        dutyPosition
                                            .getDutyPosition()
                                            .getEntity()
                                            .getInstrumentationPosition();

                                    if (
                                        instrumentationPosition
                                            .getInstrumentation()
                                            .getMusicalPiece()
                                            .getName()
                                            .equals(
                                                newValue.getName()
                                            )
                                    ) {
                                        return true;
                                    }

                                    return false;
                                });
                            }
                        }
                    );
                    SortedList<DutyPositionMusicianTableModel> sortedList = new SortedList<>(
                        filteredList
                    );

                    this.positionsTable.setItems(sortedList);
                    mc.showStatusBarLoaded();
                }
            });
            new Thread(task).start();
        }
    }

    private void initOldDutyComboView() {
        GetOtherDutiesTask task = new GetOtherDutiesTask(
            this.dutyManager,
            this.duty,
            this.section,
            5
        );
        this.oldDutySelect.setPlaceholder(
            new Label(
                this.resources.getString("tab.duty.schedule.old.duty.select.default")
            )
        );
        task.setOnSucceeded(event -> {
            List<Duty> dutyList = task.getValue();
            ObservableList<OldDutyComboView> observableList = FXCollections.observableArrayList();
            LOG.debug("Found {} other duties", dutyList.size());
            for (Duty d : dutyList) {
                LOG.debug("Found duty: {}, {}", d.getTitle(), d.getEntity().getStart());
                observableList.add(new OldDutyComboView(d));
            }
            this.oldDutySelect.getItems().setAll(observableList);
            this.oldDutySelect.setConverter(new StringConverter<>() {
                @Override
                public String toString(OldDutyComboView duty) {
                    return duty.getType() + " | " + duty.getStart();
                }

                @Override
                public OldDutyComboView fromString(String title) {
                    return observableList.stream()
                        .filter(
                            item -> item
                                .getType()
                                .equals(
                                    title.substring(0, title.lastIndexOf('|') - 1)
                                )
                        )
                        .collect(Collectors.toList()).get(0);
                }
            });
        });
        new Thread(task).start();
    }

    private void loadOldDuty() {
        if (this.oldDutySelect.getSelectionModel().getSelectedItem() == null) {
            LOG.debug("No old Duty selected");
            Notifications.create()
                .title(this.resources.getString("notif.duty.schedule.old.duty.none.selected.title"))
                .text(
                    this.resources.getString(
                        "notif.duty.schedule.old.duty.none.selected.message"
                    )
                )
                .position(Pos.CENTER)
                .hideAfter(new Duration(2000))
                .show();
        } else {
            LOG.debug(
                "Load old Duty {}",
                this.oldDutySelect.getSelectionModel().getSelectedItem().getTitle()
            );
            if (this.actualSectionInstrumentation != null) {
                GetPositionsWithMusiciansTask task = new GetPositionsWithMusiciansTask(
                    this.dutyScheduleManager,
                    this.oldDutySelect.getSelectionModel().getSelectedItem().getOldDuty(),
                    this.section,
                    this.dutySchedule
                );
                task.setOnSucceeded(event -> {
                    Optional<ActualSectionInstrumentation> oldasi = task.getValue();

                    if (oldasi.isPresent()) {
                        for (DutyPosition dp : this.actualSectionInstrumentation.getDuty()
                            .getDutyPositions()) {
                            Set<Musician> avMusicians = this.dutyScheduleManager
                                .getMusiciansAvailableForPosition(this.duty, dp);
                            Optional<Musician> oldMusician = Optional.empty();
                            List<DutyPosition> oldDutyPositions =
                                oldasi.get().getDuty().getDutyPositions();

                            for (DutyPosition odp : oldDutyPositions) {
                                if (
                                    odp
                                        .getEntity()
                                        .getInstrumentationPosition()
                                        .getInstrumentationPositionId()
                                        .equals(
                                            dp.getEntity()
                                                .getInstrumentationPosition()
                                                .getInstrumentationPositionId()
                                        )
                                ) {
                                    if (odp.getAssignedMusician().isPresent()) {
                                        oldMusician = odp.getAssignedMusician();
                                    }
                                }
                            }

                            if (oldMusician.isPresent()) {
                                for (Musician m : avMusicians) {
                                    if (m.getEntity().getMusicianId()
                                        .equals(oldMusician.get().getEntity().getMusicianId())
                                    ) {
                                        this.addMusicianToPosition(
                                            this.actualSectionInstrumentation,
                                            m,
                                            dp
                                        );
                                    }
                                }
                            }

                        }
                    }
                });
                new Thread(task).start();
            }
        }
    }

    /**
     * Handles a wish scheduling event by notifying the user with an alert, if the selected
     * musician has a negative wish, before scheduling. Should the musician's wish be of
     * a positive type, no alert will be shown.
     *
     * @param mtm THe {@link MusicianTableModel} to use
     */
    private void handleWishScheduleClickEvent(MusicianTableModel mtm) {
        // Prevent NullPointerExceptions when no musician is selected or given
        if (mtm == null) {
            return;
        }

        // Schedule musician without alert if wish is positive, show alert otherwise
        if (mtm.isWishPositive()) {
            // Positive wish, no alert necessary
            this.addMusicianToPosition(
                this.actualSectionInstrumentation,
                mtm.getMusician(),
                this.selectedDutyPosition
            );
        } else {
            // Negative wish, show alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(this.resources.getString(
                "alert.duty.schedule.musician.wish.negative.title"
            ));
            alert.setContentText(this.resources.getString(
                "alert.duty.schedule.musician.with.negative.message"
            ));

            ButtonType okButton = new ButtonType(
                this.resources.getString("global.button.yes"),
                ButtonBar.ButtonData.YES
            );
            ButtonType noButton = new ButtonType(
                this.resources.getString("global.button.no"),
                ButtonBar.ButtonData.NO
            );
            alert.getButtonTypes().setAll(okButton, noButton);

            alert.showAndWait().ifPresent(type -> {
                if (type.equals(okButton)) {
                    this.addMusicianToPosition(
                        this.actualSectionInstrumentation,
                        mtm.getMusician(),
                        this.selectedDutyPosition
                    );
                    alert.close();
                } else if (type.equals(noButton)) {
                    alert.close();
                }
            });
        }
    }

    private void assignOnePositionToMusician(
        DutyPosition dutyPosition,
        ActualSectionInstrumentation asi,
        Musician m
    ) {
        if (dutyPosition.getAssignedMusician().isPresent()) {
            this.dutyScheduleManager.assignMusicianToPosition(
                asi,
                m,
                dutyPosition.getAssignedMusician().get(),
                dutyPosition
            );
            LOG.debug(
                "New musician for position {} is: {} and oldMusician id {}",
                dutyPosition.getEntity().getInstrumentationPosition()
                    .getPositionDescription(),
                m.getFullName(),
                dutyPosition.getAssignedMusician().get()
            );
        } else {
            this.dutyScheduleManager.assignMusicianToPosition(
                asi,
                m,
                dutyPosition
            );
            LOG.debug(
                "New musician for position {} is: {}",
                dutyPosition.getEntity().getInstrumentationPosition()
                    .getPositionDescription(),
                m.getFullName()
            );
        }
        this.positionsTable.refresh();
    }

    private void unassignOnePositionToMusician(
        DutyPosition dutyPosition,
        ActualSectionInstrumentation asi,
        Musician m
    ) {
        if (dutyPosition.getAssignedMusician().isPresent()) {
            LOG.debug("Unassigned Position {}",
                dutyPosition.getEntity().getDutyPositionId()
            );
            this.dutyScheduleManager.removeMusicianFromPosition(
                asi,
                m,
                dutyPosition
            );
            this.positionsTable.refresh();
        }
    }

    private Dialog<ButtonType> getMusicalPieceDialog() {
        ObservableList<MusicalPiece> list = FXCollections.observableList(
            this.duty.getMusicalPieces()
        );
        this.scheduleMusicalPiecesChkListView = new CheckListView<>(list);
        this.scheduleMusicalPiecesChkListView.getCheckModel().checkAll();
        Label l = new Label(
            this.resources.getString("tab.duty.schedule.multiple.pieces.dialog.body")
        );

        VBox vBox = new VBox();
        vBox.getChildren().add(l);
        vBox.getChildren().add(this.scheduleMusicalPiecesChkListView);

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(this.dutySchedule.getParent().getScene().getWindow());
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        dialog.getDialogPane().setContent(vBox);
        dialog.setResizable(true);
        dialog.getDialogPane().setPrefWidth(400);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle(this.resources.getString(
            "tab.duty.schedule.multiple.pieces.dialog.title"));

        return dialog;
    }

    private void handleMultiplePiecesDialogAction(
        DutyPosition dutyPosition,
        ActualSectionInstrumentation asi,
        Musician newMusician,
        ScheduleMusicianAction action
    ) {
        if (dutyPosition == null) {
            Notifications.create()
                .title(
                    this.resources.getString(
                        "notif.duty.schedule.position.add.musician.unavailable.title"
                    )
                )
                .text(
                    this.resources.getString(
                        "notif.duty.schedule.position.add.musician.unavailable.message"
                    )
                )
                .position(Pos.CENTER)
                .hideAfter(new Duration(2000))
                .show();
            return;
        }

        if (this.duty.getMusicalPieces().size() > 1) {
            LOG.debug("need to assign/unassign multiple positions");
            Dialog<ButtonType> dialog = getMusicalPieceDialog();

            Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setText(
                this.resources.getString("tab.duty.schedule.multiple.pieces.dialog.button.ok")
            );

            okButton.setOnAction(event -> {
                ObservableList<MusicalPiece> sel = this.scheduleMusicalPiecesChkListView
                    .getCheckModel()
                    .getCheckedItems();

                LOG.debug("Selected {} pieces to schedule", sel.size());

                for (MusicalPiece mp : sel) {
                    DutyPosition dp = getSamePositionForDifferentPiece(
                        dutyPosition,
                        mp,
                        this.duty.getDutyPositions()
                    );
                    if (dp != null) {
                        if (action.equals(ScheduleMusicianAction.ADD)) {
                            this.assignOnePositionToMusician(dp, asi, newMusician);
                        } else {
                            this.unassignOnePositionToMusician(dp, asi, newMusician);
                        }

                    }
                }
                Notifications.create()
                    .owner(this.getParentController().calendarView)
                    .title(
                        this.resources.getString(
                            "notif.duty.schedule.position.add.musician.set.message.title"
                        )
                    )
                    .text(
                        this.resources.getString(
                            "notif.duty.schedule.position.add.musician.set.message"
                        )
                    )
                    .position(Pos.CENTER)
                    .hideAfter(new Duration(2000))
                    .show();
            });

            dialog.show();
        } else {
            if (action.equals(ScheduleMusicianAction.ADD)) {
                this.assignOnePositionToMusician(dutyPosition, asi, newMusician);
            } else {
                this.unassignOnePositionToMusician(dutyPosition, asi, newMusician);
            }
            Notifications.create()
                .owner(this.getParentController().calendarView)
                .title(
                    this.resources.getString(
                        "notif.duty.schedule.position.add.musician.set.message.title"
                    )
                )
                .text(
                    this.resources.getString(
                        "notif.duty.schedule.position.add.musician.set.message"
                    )
                )
                .position(Pos.CENTER)
                .hideAfter(new Duration(2000))
                .show();
        }
    }

    protected void addMusicianToPosition(
        ActualSectionInstrumentation asi,
        Musician newMusician,
        DutyPosition dutyPosition
    ) {
        handleMultiplePiecesDialogAction(
            dutyPosition,
            asi,
            newMusician,
            ScheduleMusicianAction.ADD
        );

        this.positionsTable.refresh();
        this.initDutyPositionsTableWithMusicians();
        this.initMusicianTableWithoutRequests();
    }

    private DutyPosition getSamePositionForDifferentPiece(
        DutyPosition originalPosition,
        MusicalPiece differentPiece,
        List<DutyPosition> allPositions
    ) {
        for (DutyPosition dutyPosition : allPositions) {
            InstrumentationPositionEntity ipe = dutyPosition
                .getEntity()
                .getInstrumentationPosition();
            if (
                ipe.getPositionDescription().equals(
                    originalPosition
                        .getEntity()
                        .getInstrumentationPosition()
                        .getPositionDescription())
                    && ipe.getInstrumentation().getMusicalPiece().getName().equals(
                    differentPiece.getEntity()
                        .getName()
                )
            ) {
                return dutyPosition;
            }
        }
        return null;
    }

    private void removeMusicianFromPosition(Musician musician, DutyPosition dutyPosition) {
        handleMultiplePiecesDialogAction(
            dutyPosition,
            this.actualSectionInstrumentation,
            musician,
            ScheduleMusicianAction.REMOVE
        );

        this.positionsTable.refresh();
        this.initMusicianTableWithoutRequests();
    }

    private void setActualPosition(DutyPosition dutyPosition) {
        LOG.debug(
            "Current DutyPosition: {} Current Object: {}",
            dutyPosition
                .getEntity()
                .getInstrumentationPosition()
                .getPositionDescription(),
            this
        );

        this.selectedDutyPosition = dutyPosition;
        this.initMusicianTableWithoutRequests();

        this.labelCurrentPosition.textProperty().bindBidirectional(
            new SimpleStringProperty(
                this.resources.getString("tab.duty.schedule.current.position")
                    + this.selectedDutyPosition
                    .getEntity().getInstrumentationPosition().getPositionDescription()
            )
        );
    }

    private void closeDutySchedule() {
        //TODO Abfrage SaveState(old, newold)? von actualSectionInstrumentation
        if (this.actualSectionInstrumentation != null && this.actualSectionInstrumentation
            .getPersistenceState()
            .equals(PersistenceState.EDITED)
        ) {
            ButtonType userSelection = getConfirmation();
            if (userSelection == ButtonType.OK) {
                for (DutyPosition dp : this
                    .actualSectionInstrumentation
                    .getDuty()
                    .getDutyPositions()) {
                    Optional<Musician> oldMusician = this.oldMusicianOnPosition.get(dp);
                    if (oldMusician.isPresent()) {
                        this.actualSectionInstrumentation
                            .assignMusicianToPosition(oldMusician.get(), dp);
                    } else {
                        if (dp.getAssignedMusician().isPresent()) {
                            // TODO check if this is now broken
                            this.removeMusicianFromPosition(dp.getAssignedMusician().get(), dp);
                        }
                    }
                }
                tearDown();
            }
        } else {
            tearDown();
        }

    }

    private void tearDown() {
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
        this.musicianTableWithRequests.getItems().clear();
        this.musicianTableWithoutRequests.getItems().clear();
        this.positionsTable.setItems(FXCollections.observableArrayList());
        this.musicianTableWithRequests.refresh();
        this.musicianTableWithoutRequests.refresh();
        this.positionsTable.refresh();
        this.musicalPieceSelect.getItems().clear();
        this.musicalPieceSelect.visibleProperty().set(false);
        this.oldDutySelect.getItems().clear();
        this.oldDutySelect.setPromptText(
            this.resources.getString("tab.duty.schedule.old.duty.select.placeholder")
        );
        this.oldDutySelect.setPlaceholder(
            new Label(this.resources.getString("tab.duty.schedule.old.duty.select.default"))
        );
        this.labelCurrentPosition.setText(
            this.resources.getString("tab.duty.schedule.current.position")
        );
        this.oldMusicianOnPosition = new HashMap<>();
        this.hide();
        MasterController mc = MasterController.getInstance();
        DutySchedulerCalendarController cc =
            (DutySchedulerCalendarController) mc.get("CalendarController");
        cc.show();
    }

    private ButtonType getConfirmation() {
        Label label = new Label();
        ButtonType buttonType = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(this.resources.getString("alert.close.without.saving.title"));
        alert.setHeaderText(this.resources.getString("alert.close.without.saving.message"));

        Optional<ButtonType> option = alert.showAndWait();

        // this should be rewritten
        if (option.isEmpty()) {
            buttonType = ButtonType.CLOSE;
        } else if (option.get() == ButtonType.OK) {
            buttonType = ButtonType.OK;
        } else if (option.get() == ButtonType.CANCEL) {
            buttonType = ButtonType.CANCEL;
        } else {
            label.setText("-");
        }
        return buttonType;
    }

    /**
     * Sets the actions for all buttons of the duty schedule view.
     */
    private void setButtonActions() {
        // Save button
        this.scheduleSaveBtn.setOnAction(e -> {
            if (this.actualSectionInstrumentation
                .getPersistenceState()
                .equals(PersistenceState.EDITED)
            ) {
                this.dutyScheduleManager.persist(this.actualSectionInstrumentation);
                if (this.actualSectionInstrumentation
                    .getPersistenceState()
                    .equals(PersistenceState.PERSISTED)
                ) {
                    Notifications.create()
                        .title(this.resources.getString("notif.save.successful.title"))
                        .text(this.resources.getString("notif.save.successful.message"))
                        .position(Pos.CENTER)
                        .hideAfter(new Duration(4000))
                        .show();
                }
            } else {
                Notifications.create()
                    .title(this.resources.getString("notif.save.unedited.title"))
                    .text(this.resources.getString("notif.save.unedited.message"))
                    .position(Pos.CENTER)
                    .hideAfter(new Duration(4000))
                    .showError();
            }
            if (this.actualSectionInstrumentation
                .getPersistenceState()
                .equals(PersistenceState.EDITED)
            ) {
                Notifications.create()
                    .title(this.resources.getString("notif.save.failed.title"))
                    .text(this.resources.getString("notif.save.failed.message"))
                    .position(Pos.CENTER)
                    .hideAfter(new Duration(4000))
                    .showError();
            }
        });

        // Back button
        this.scheduleBackBtn.setOnAction(e -> closeDutySchedule());

        // Load old duties button
        this.getOldDutyBtn.setOnAction(e -> {
            LOG.debug("Load old Duty pressed");
            loadOldDuty();
        });
    }

    private TableCell<MusicianPointsTableModel, String> getPointsTableCell() {
        Label lblGainedPoints = new Label();
        Label lblBalancePoints = new Label();
        Label lblSummaryPoints = new Label();
        lblGainedPoints.setTextFill(Color.web("#000000"));
        lblBalancePoints.setTextFill(Color.web("#000000"));
        lblSummaryPoints.setTextFill(Color.web("#000000"));
        Font f = Font.getDefault();
        Font boldFont = Font.font(f.getFamily(), FontWeight.BOLD, 14);
        lblSummaryPoints.setFont(boldFont);
        Label lblMusicianName = new Label();
        lblMusicianName.setTextFill(Color.web("#000000"));
        lblMusicianName.setFont(boldFont);

        VBox vbox = new VBox(
            lblMusicianName, new Separator(),
            lblGainedPoints,
            lblBalancePoints,
            new Separator(),
            lblSummaryPoints
        );
        vbox.setPadding(new Insets(15, 15, 15, 15));
        PopOver popOver = new PopOver(vbox);


        TableCell<MusicianPointsTableModel, String> cell = new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(item);
            }
        };
        cell.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            MusicianPointsTableModel mtm = cell.getTableRow().getItem();
            if (mtm != null && mtm.getMusician() != null) {
                LOG.debug("Musician set on this position");
                this.dutyScheduleManager.addGainedPointsToMusician(
                    mtm.getMusician(),
                    this.duty.getEntity().getStart().toLocalDate()
                );

                lblMusicianName.setText(mtm.getMusician().getFullName());
                lblGainedPoints.setText(
                    MessageFormat.format(
                        this.resources.getString(
                            "tab.duty.schedule.table.musicians.gainedPoints.popup"
                        ),
                        mtm.getGainedPoints()
                    )
                );
                lblBalancePoints.setText(
                    MessageFormat.format(
                        this.resources.getString(
                            "tab.duty.schedule.table.musicians.balancePoints.popup"
                        ),
                        mtm.getBalancePoints()
                    )
                );
                lblSummaryPoints.setText(
                    MessageFormat.format(
                        this.resources.getString(
                            "tab.duty.schedule.table.musicians.summary.popup"
                        ),
                        // those are strings, so parse them as int
                        Integer.parseInt(mtm.getBalancePoints())
                            + Integer.parseInt(mtm.getGainedPoints()
                        )
                    )
                );

                popOver.show((Node) event.getSource());
                popOver.requestFocus();
            } else {
                LOG.debug("No musician set on this position");
            }
        });
        cell.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (popOver.isShowing()) {
                popOver.hide();
            }
        });
        return cell;
    }

    /**
     * Sets the mouse hover events of the duty schedule view.
     */
    private void setMouseHoverEvents() {

        this.columnPointsWithoutRequests.setCellFactory(
            column -> getPointsTableCell()
        );

        this.columnPointsWithRequests.setCellFactory(
            column -> getPointsTableCell()
        );
    }

    /**
     * Sets the mouse click events of the duty schedule view.
     */
    private void setMouseClickEvents() {
        // Musician table with requests
        this.musicianTableWithRequests.setOnMouseClicked((MouseEvent event) -> {
            // add selected item click listener
            if (event.getButton().equals(MouseButton.PRIMARY)
                && event.getClickCount() == 2) {
                this.handleWishScheduleClickEvent(
                    this.musicianTableWithRequests.getSelectionModel().getSelectedItem()
                );
            }
        });

        // Musician table without requests
        this.musicianTableWithoutRequests.setOnMouseClicked((MouseEvent event) -> {
            // add selected item click listener
            if (event.getButton().equals(MouseButton.PRIMARY)
                && event.getClickCount() == 2) {
                MusicianTableModel mtm =
                    this.musicianTableWithoutRequests.getSelectionModel()
                        .getSelectedItem();
                if (mtm != null) {
                    addMusicianToPosition(
                        this.actualSectionInstrumentation,
                        mtm.getMusician(),
                        this.selectedDutyPosition
                    );
                }
            }
        });

        // Positions table
        this.positionsTable.setOnMouseClicked((MouseEvent event) -> {
            // add selected item click listener
            if (event.getButton().equals(MouseButton.PRIMARY)
                && event.getClickCount() == 2) {

                DutyPositionMusicianTableModel mtm =
                    this.positionsTable.getSelectionModel().getSelectedItem();

                if (mtm != null
                    && mtm.getDutyPosition().getAssignedMusician().isPresent()) {
                    this.removeMusicianFromPosition(
                        mtm.getDutyPosition().getAssignedMusician().get(),
                        mtm.getDutyPosition()
                    );
                } else {
                    LOG.error("Cannot unset null musician");
                }
            }
        });
    }

    /**
     * Sets the row factories used in the duty schedule view.
     */
    private void setRowFactories() {
        // Musician with request
        this.musicianTableWithRequests.setRowFactory(row -> new TableRow<>() {
            @Override
            protected void updateItem(MusicianTableModel item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (getTableView().getSelectionModel().getSelectedItems()
                        .contains(item)) {
                        setStyle("");
                    } else if (item.isWishPositive()) {
                        setStyle("-fx-background-color: lightseagreen");
                    } else {
                        setStyle("-fx-background-color: lightcoral");
                    }
                }
            }
        });
    }

    /**
     * Sets the cell factories used in the duty schedule view.
     */
    private void setCellFactories() {
        // Add button column - with requests
        this.columnScheduleBtnWithRequests.setCellFactory(
            ScheduleButtonTableCell.forTableColumn(
                this.resources.getString("tab.duty.schedule.table.position.set.btn"),
                (MusicianTableModel mtm) -> {
                    LOG.debug("Schedule btn with requests has been pressed");
                    this.handleWishScheduleClickEvent(mtm);
                    return mtm;
                }
            )
        );

        // Add button column - without requests
        this.columnScheduleBtnWithoutRequests.setCellFactory(
            ScheduleButtonTableCell.forTableColumn(
                this.resources.getString("tab.duty.schedule.table.position.set.btn"),
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

        // Remove button column
        this.columnUnsetPosition.setCellFactory(
            ScheduleButtonTableCell.forTableColumn(
                this.resources.getString("tab.duty.schedule.table.position.unset.btn"),
                (DutyPositionMusicianTableModel dpmtm) -> {
                    LOG.debug("Unset musician button has been pressed");

                    Optional<Musician> assignedMusician = dpmtm.getDutyPosition()
                        .getAssignedMusician();

                    this.selectedDutyPosition = dpmtm.getDutyPosition();

                    if (assignedMusician.isPresent()) {
                        this.removeMusicianFromPosition(
                            dpmtm.getDutyPosition().getAssignedMusician().get(),
                            dpmtm.getDutyPosition()
                        );
                    }
                    return dpmtm;
                }
            )
        );
    }

    /**
     * Set the actual Duty for Controller.
     *
     * @param duty actual Duty.
     */
    public void setDuty(Duty duty) {
        if (this.dutyManager == null) {
            this.dutyManager = new DutyManager();
        }
        Optional<Duty> d =
            this.dutyManager.loadDutyDetails(duty.getEntity().getDutyId());
        if (d.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cannot load duty");
            alert.setHeaderText("Something went wrong. Please try again.");
            return;
        }
        this.duty = d.get();

        LOG.debug("Binding duty title to: {}", this.duty.getTitle());
        this.dutyTitle.textProperty().bind(
            new SimpleStringProperty(
                this.duty
                    .getEntity()
                    .getStart()
                    .format(
                        DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm")
                    )
                    + " - "
                    + duty.getTitle()
                    + " - "
                    + duty.getEntity().getDutyCategory().getPoints()
                    + " "
                    + this.resources.getString("tab.duty.schedule.table.musicians.points")
            )
        );
    }

    public void setSection(SectionDto section) {
        this.section = section;
    }

    @Override
    public DutySchedulerCalendarController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(DutySchedulerCalendarController controller) {
        this.parentController = controller;
    }

    private enum ScheduleMusicianAction {
        ADD, REMOVE
    }
}
