package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.presentation.internal.DutyPositionMusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.MusicianTableModel;
import at.fhv.teamb.symphoniacus.presentation.internal.OldDutyComboView;
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
import javafx.scene.control.SplitPane;
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

public class DutyScheduleController implements Initializable, Controllable {

    private static final Logger LOG = LogManager.getLogger(DutyScheduleController.class);
    @FXML
    public Button scheduleSaveBtn;
    private Duty duty;
    private Section section;
    private DutyScheduleManager dutyScheduleManager;
    private DutyManager dutyManager;
    private ActualSectionInstrumentation actualSectionInstrumentation;
    private DutyPosition selectedDutyPosition;
    @FXML
    private AnchorPane dutySchedule;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Button getOldDuty;

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
    private TableColumn<MusicianTableModel, Button> columnSchedule;

    @FXML
    private TableColumn<MusicianTableModel, Button> columnSchedule2;

    @FXML
    private TableColumn<DutyPositionMusicianTableModel, Button> columnUnsetPosition;

    @FXML
    private ComboBox<OldDutyComboView> oldDutySelect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.registerController();
        this.dutyScheduleManager = null;
        this.dutySchedule.setVisible(false);

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
                        .title("Saving successful")
                        .text("Current Instrumentation has been saved.")
                        .position(Pos.CENTER)
                        .hideAfter(new Duration(4000))
                        .show();
                }

            } else {
                Notifications.create()
                    .title("Not saved")
                    .text("No changes were made.")
                    .position(Pos.CENTER)
                    .hideAfter(new Duration(4000))
                    .showError();
            }
            if (this.actualSectionInstrumentation
                .getPersistenceState()
                .equals(PersistenceState.EDITED)
            ) {
                Notifications.create()
                    .title("Saving failed")
                    .text("Something went wrong while saving.")
                    .position(Pos.CENTER)
                    .hideAfter(new Duration(4000))
                    .showError();
            }
        });

        this.scheduleBackBtn.setOnAction(e -> {
            closeDutySchedule();
        });

        this.getOldDuty.setOnAction(e -> {
            System.out.println("Pressed");
            loadOldDuty();
        });

        this.musicianTableWithRequests.setOnMouseClicked((MouseEvent event) -> {
            // add selected item click listener
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Musician has Negative Duty Wish");
                alert.setContentText("Really schedule Musician with Negative Wish?");
                ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                MusicianTableModel mtm =
                    this.musicianTableWithRequests.getSelectionModel().getSelectedItem();
                alert.getButtonTypes().setAll(okButton, noButton);
                alert.showAndWait().ifPresent(type -> {
                    System.out.println(type);
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
        });

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

        this.positionsTable.setOnMouseClicked((MouseEvent event) -> {
            // add selected item click listener
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {

                DutyPositionMusicianTableModel mtm =
                    this.positionsTable.getSelectionModel().getSelectedItem();

                if (mtm.getDutyPosition().getAssignedMusician().isPresent()) {
                    this.removeMusicianFromPosition(
                        mtm.getDutyPosition().getAssignedMusician().get()
                    );
                } else {
                    LOG.error("Cannot unset null musician");
                }
            }
        });

        this.columnSchedule.setCellFactory(
            ScheduleButtonTableCell.<MusicianTableModel>forTableColumn(
                "Schedule",
                (MusicianTableModel mtm) -> {
                    LOG.debug("Schedule btn with requests has been pressed");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Musician has Negative Duty Wish");
                    alert.setContentText("Really schedule Musician with Negative Wish?");
                    ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                    alert.getButtonTypes().setAll(okButton, noButton);
                    alert.showAndWait().ifPresent(type -> {
                        System.out.println(type);
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

                    return mtm;
                }
            )
        );

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

    private void initOldDutyComboView() {
        GetOtherDutiesTask task = new GetOtherDutiesTask(
            this.dutyManager,
            this.duty,
            this.section,
            5
        );
        this.oldDutySelect.setPlaceholder(new Label("No duty selected"));
        task.setOnSucceeded(event -> {
            Optional<List<Duty>> list = task.getValue();
            if (list.isPresent()) {
                List<Duty> dutyList = list.get();
                ObservableList<OldDutyComboView> observableList
                    = FXCollections.observableArrayList();
                LOG.debug("Found {} other duties", dutyList.size());
                for (Duty d : dutyList) {
                    LOG.debug("Found duty: {}, {}", d.getTitle(), d.getEntity().getStart());
                    observableList.add(
                        new OldDutyComboView(d)
                    );
                }
                this.oldDutySelect.getItems().setAll(observableList);
                this.oldDutySelect.setConverter(new StringConverter<OldDutyComboView>() {
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
                                        title.substring(0, title.lastIndexOf("|") - 1)
                                    )
                            )
                            .collect(Collectors.toList()).get(0);
                    }
                });

            } else {
                LOG.error("Cannot load other duties in GUI");
            }
        });
        new Thread(task).start();
    }

    private void loadOldDuty() {
        if (this.oldDutySelect.getSelectionModel().getSelectedItem() == null) {
            LOG.debug("No old Duty selected");
            Notifications.create()
                .title("No Duty selected")
                .text("You have to choose an old Duty")
                .position(Pos.CENTER)
                .hideAfter(new Duration(2000))
                .show();
        } else {
            LOG.debug("Load old Duty {}",
                this.oldDutySelect.getSelectionModel().getSelectedItem().getTitle());
            if (this.actualSectionInstrumentation != null) {
                Optional<ActualSectionInstrumentation> oldasi = new DutyScheduleManager()
                    .getInstrumentationDetails(
                        this.oldDutySelect.getSelectionModel().getSelectedItem().getOldDuty(),
                        this.section);

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
                                == dp.getEntity().getInstrumentationPosition()
                                .getInstrumentationPositionId()) {
                                if (odp.getAssignedMusician().isPresent()) {
                                    oldMusician = odp.getAssignedMusician();
                                }
                            }
                        }


                        if (oldMusician.isPresent()) {
                            for (Musician m : avMusicians) {
                                if (m.getEntity().getMusicianId()
                                    == oldMusician.get().getEntity().getMusicianId()) {
                                    this.addMusicianToPosition(this.actualSectionInstrumentation, m,
                                        dp);
                                }
                            }
                        }
                    }
                }
            }
        }
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
            this.selectedDutyPosition
        );
        this.dutyScheduleManager.removeMusicianFromPosition(
            this.actualSectionInstrumentation,
            musician,
            this.selectedDutyPosition
        );
        this.positionsTable.refresh();
        this.initMusicianTableWithoutRequests();
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
                "Current Position: "
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
        this.oldDutySelect.setPromptText("Load from history");
        this.oldDutySelect.setPlaceholder(new Label("No duty selected"));
        this.labelCurrentPosition.setText("Current Position: ");
        this.hide();
        MasterController mc = MasterController.getInstance();
        CalendarController cc = (CalendarController) mc.get("CalendarController");
        cc.show();
    }

    private ButtonType getConfirmation() {
        Label label = new Label();
        ButtonType buttonType = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Closing without saving");
        alert.setHeaderText("Are you sure you want to close without saving?");

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
        if (this.dutyManager == null) {
            this.dutyManager = new DutyManager();
        }
        Optional<Duty> d = this.dutyManager.loadDutyDetails(duty.getEntity().getDutyId());
        if (d.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cannot load duty");
            alert.setHeaderText("Something went wrong. Please try again.");
        }
        this.duty = d.get();

        LOG.debug("Binding duty title to: " + this.duty.getTitle());
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
            )
        );
    }

    public void setSection(Section section) {
        this.section = section;
    }
}