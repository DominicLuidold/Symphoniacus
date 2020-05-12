package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.presentation.internal.AlertHelper;
import at.fhv.teamb.symphoniacus.presentation.internal.DutyPositionMusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.OldDutyComboView;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.ScheduleButtonTableCell;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.GetMusiciansAvailableForPositionTask;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.GetOtherDutiesTask;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;

public class DutyScheduleController
    implements Controllable, Initializable, Parentable<DutySchedulerCalendarController> {

    private static final Logger LOG = LogManager.getLogger(DutyScheduleController.class);
    @FXML
    public Button scheduleSaveBtn;
    private Duty duty;
    private Section section;
    private DutyScheduleManager dutyScheduleManager;
    private DutyManager dutyManager;
    private ActualSectionInstrumentation actualSectionInstrumentation;
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
            this.selectedDutyPosition
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
            Optional<ActualSectionInstrumentation> currentAsi = this
                .dutyScheduleManager
                .getInstrumentationDetails(
                    this.duty,
                    this.section
                );
            if (currentAsi.isEmpty()) {
                LOG.error("Found no asi for duty");
                return;
            } else {
                this.actualSectionInstrumentation = currentAsi.get();
            }
        }


        this.duty = this.actualSectionInstrumentation.getDuty();

        ObservableList<DutyPositionMusicianTableModel> observablePositionList =
            FXCollections.observableArrayList();
        List<DutyPosition> positionList =
            this.actualSectionInstrumentation.getDuty().getDutyPositions();

        for (DutyPosition dp : positionList) {
            observablePositionList.add(
                new DutyPositionMusicianTableModel(dp)
            );
        }
        this.positionsTable.setItems(observablePositionList);
        mc.showStatusBarLoaded();
    }

    private void initOldDutyComboView() {
        GetOtherDutiesTask task = new GetOtherDutiesTask(
            this.dutyManager,
            this.duty,
            this.section,
            5
        );
        this.oldDutySelect.setPlaceholder(new Label(resources.getString(
            "tab.duty.schedule.oldduty.select.default")));
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
                .title(
                    resources
                        .getString("notification.duty.schedule.oldduty.nodutyselected.titel"))
                .text(
                    resources
                        .getString("notification.duty.schedule.oldduty.nodutyselected.message"))
                .position(Pos.CENTER)
                .hideAfter(new Duration(2000))
                .show();
        } else {
            LOG.debug(
                "Load old Duty {}",
                this.oldDutySelect.getSelectionModel().getSelectedItem().getTitle()
            );
            if (this.actualSectionInstrumentation != null) {
                Optional<ActualSectionInstrumentation> oldasi = new DutyScheduleManager()
                    .getInstrumentationDetails(
                        this.oldDutySelect.getSelectionModel().getSelectedItem().getOldDuty(),
                        this.section
                    );

                if (oldasi.isPresent()) {
                    for (DutyPosition dp : this.actualSectionInstrumentation.getDuty()
                        .getDutyPositions()) {
                        Set<Musician> avMusicians = this.dutyScheduleManager
                            .getMusiciansAvailableForPosition(this.duty, dp);
                        Optional<Musician> oldMusician = Optional.empty();
                        List<DutyPosition> oldDutyPositions =
                            oldasi.get().getDuty().getDutyPositions();

                        for (DutyPosition odp : oldDutyPositions) {
                            if (odp.getEntity().getInstrumentationPosition()
                                .getInstrumentationPositionId()
                                .equals(dp.getEntity().getInstrumentationPosition()
                                    .getInstrumentationPositionId())
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
                "notification.duty.schedule.musician.withnegativewhish.title"
            ));
            alert.setContentText(this.resources.getString(
                "notification.duty.schedule.musician.withnegativewhish.message"
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

    protected void addMusicianToPosition(
        ActualSectionInstrumentation asi,
        Musician newMusician,
        DutyPosition dutyPosition
    ) {
        if (dutyPosition == null) {
            Notifications.create()
                .title(resources
                    .getString("notification.duty.schedule.addMusicianToPosition.noposition.title"))
                .text(resources.getString(
                    "notification.duty.schedule.addMusicianToPosition.noposition.message"))
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
            .owner(this.getParentController().calendarView)
            .title(resources
                .getString("notification.duty.schedule.addMusicianToPosition.musicianset.title"))
            .text(resources
                .getString("notification.duty.schedule.addMusicianToPosition.musicianset.message"))
            .position(Pos.CENTER)
            .hideAfter(new Duration(2000))
            .show();

        this.positionsTable.refresh();
        this.initMusicianTableWithoutRequests();
    }

    private void removeMusicianFromPosition(Musician musician) {
        this.dutyScheduleManager.removeMusicianFromPosition(
            this.actualSectionInstrumentation,
            musician,
            this.selectedDutyPosition
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
                resources.getString("tab.duty.schedule.currentposition")
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
            if (userSelection == ButtonType.OK) {
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
        this.positionsTable.getItems().clear();
        this.musicianTableWithRequests.refresh();
        this.musicianTableWithoutRequests.refresh();
        this.positionsTable.refresh();
        this.oldDutySelect.getItems().clear();
        this.oldDutySelect
            .setPromptText(resources.getString("tab.duty.scheudle.oldduty.select.button"));
        this.oldDutySelect.setPlaceholder(
            new Label(resources.getString("tab.duty.schedule.oldduty.select.default")));
        this.labelCurrentPosition.setText(resources.getString("tab.duty.schedule.currentposition"));
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
        alert.setTitle(resources.getString("dialog.save.closewithoutsaving.title"));
        alert.setHeaderText(resources.getString("dialog.save.closewithoutsaving.message"));

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
                        .title(resources.getString("notification.save.successful.title"))
                        .text(resources.getString("notification.save.successful.message"))
                        .position(Pos.CENTER)
                        .hideAfter(new Duration(4000))
                        .show();
                }

            } else {
                Notifications.create()
                    .title(resources.getString("notification.save.nochange.title"))
                    .text(resources.getString("notification.save.nochange.message"))
                    .position(Pos.CENTER)
                    .hideAfter(new Duration(4000))
                    .showError();
            }
            if (this.actualSectionInstrumentation
                .getPersistenceState()
                .equals(PersistenceState.EDITED)
            ) {
                Notifications.create()
                    .title(resources.getString("notification.save.failed.title"))
                    .text(resources.getString("notification.save.failed.message"))
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

    /**
     * Sets the mouse click events of the duty schedule view.
     */
    private void setMouseClickEvents() {
        // Musician table with requests
        this.musicianTableWithRequests.setOnMouseClicked((MouseEvent event) -> {
            // add selected item click listener
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                this.handleWishScheduleClickEvent(
                    this.musicianTableWithRequests.getSelectionModel().getSelectedItem()
                );
            }
        });

        // Musician table without requests
        this.musicianTableWithoutRequests.setOnMouseClicked((MouseEvent event) -> {
            // add selected item click listener
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                MusicianTableModel mtm =
                    this.musicianTableWithoutRequests.getSelectionModel().getSelectedItem();
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
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {

                DutyPositionMusicianTableModel mtm =
                    this.positionsTable.getSelectionModel().getSelectedItem();

                if (mtm != null && mtm.getDutyPosition().getAssignedMusician().isPresent()) {
                    this.removeMusicianFromPosition(
                        mtm.getDutyPosition().getAssignedMusician().get()
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
                    if (getTableView().getSelectionModel().getSelectedItems().contains(item)) {
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
                this.resources.getString("tab.duty.schedule.table.dutyposition.set.btn"),
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
                this.resources.getString("tab.duty.schedule.table.dutyposition.set.btn"),
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
                this.resources.getString("tab.duty.schedule.table.dutyposition.unset.btn"),
                (DutyPositionMusicianTableModel dpmtm) -> {
                    LOG.debug("Unset musician button has been pressed");

                    Optional<Musician> assignedMusician = dpmtm.getDutyPosition()
                        .getAssignedMusician();

                    this.selectedDutyPosition = dpmtm.getDutyPosition();

                    assignedMusician.ifPresent(this::removeMusicianFromPosition);
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
        Optional<Duty> d = this.dutyManager.loadDutyDetails(duty.getEntity().getDutyId());
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

    public void setSection(Section section) {
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
}
