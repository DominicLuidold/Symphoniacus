package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyCategoryManager;
import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.SeriesOfPerformancesManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyCategory;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckComboBox;
import shadow.org.codehaus.plexus.util.StringUtils;

/**
 * GUI Controller responsible for creating a new Duty Entry.
 *
 * @author Theresa Gierer
 * @author Dominic Luidold
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class NewDutyEntryController implements Initializable, Parentable<TabPaneController> {
    private static final Logger LOG = LogManager.getLogger(NewDutyEntryController.class);
    private TabPaneController parentController;
    private ResourceBundle resources;
    private Duty duty;
    private DutyManager dutyManager;
    private DutyCategoryManager dutyCategoryManager;
    private SeriesOfPerformancesManager seriesOfPerformancesManager;
    private AtomicBoolean validStartDate = new AtomicBoolean(false);
    private AtomicBoolean validEndDate = new AtomicBoolean(false);
    private AtomicBoolean validCategory = new AtomicBoolean(false);
    private AtomicBoolean validStartTime = new AtomicBoolean(false);
    private AtomicBoolean validEndTime = new AtomicBoolean(false);
    private boolean userEditedPoints;

    @FXML
    private AnchorPane newDutyEntryPane;

    @FXML
    private ComboBox<SeriesOfPerformancesEntity> seriesOfPerformancesSelect;

    @FXML
    private Button newSeriesOfPerformancesBtn;

    @FXML
    private ComboBox<DutyCategory> dutyCategorySelect;

    @FXML
    private JFXTextField dutyPointsInput;

    @FXML
    private JFXTextField dutyDescriptionInput;

    @FXML
    private JFXDatePicker dutyStartDateInput;

    @FXML
    private JFXTimePicker dutyStartTimeInput;

    @FXML
    private JFXDatePicker dutyEndDateInput;

    @FXML
    private JFXTimePicker dutyEndTimeInput;

    @FXML
    private Button scheduleSaveBtn;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private CheckComboBox<InstrumentationEntity> instrumentationsSelect;

    @FXML
    private GridPane upperGrid;

    @FXML
    private Label instrumentationsLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.dutyManager = new DutyManager();
        this.seriesOfPerformancesManager = new SeriesOfPerformancesManager();
        this.dutyCategoryManager = new DutyCategoryManager();
        //hideInstrumentationRow();
        //this.upperGrid.setVgap(10);
        this.instrumentationsSelect.setDisable(true);
        //this.upperGrid.getRowConstraints().get(1).setMaxHeight(0);

        // Init combo boxes with data
        this.initCategoryComboBox();
        this.initSeriesOfPerformancesComboBox();

        // Disable non-editable/pressable elements
        this.scheduleSaveBtn.setDisable(true);
        this.dutyPointsInput.setDisable(true);

        // Set input validators
        this.setInputValidators();

        // Set button actions
        this.setButtonActions();

        // Add event listener for updated points
        this.dutyPointsInput.textProperty().addListener(
            (observable, oldValue, newValue) -> userEditedPoints = true
        );

        // Add event handler for initializing sop combo box
        this.seriesOfPerformancesSelect.addEventHandler(
            ComboBoxBase.ON_SHOWING,
            event -> initSeriesOfPerformancesComboBox()
        );

        // Show Instrumentations of a selected series
        this.seriesOfPerformancesSelect.addEventHandler(ComboBoxBase.ON_HIDDEN, event -> {
            if (!this.seriesOfPerformancesSelect.getSelectionModel().isEmpty()) {
                initInstrumentationsCheckComboBox();
                this.instrumentationsSelect.setDisable(false);
                //showInstrumentationRow();
                //this.upperGrid.setVgap(20);
            } else {
                this.instrumentationsSelect.setDisable(true);
                //hideInstrumentationRow();
                //this.upperGrid.setVgap(10);
            }
        });
    }

    /*
    private void hideInstrumentationRow() {
        this.upperGrid.getChildren().remove(instrumentationsLabel);
        this.upperGrid.getChildren().remove(instrumentationsSelect);
    }

    private void showInstrumentationRow() {
        if (!(this.upperGrid.getChildren().contains(instrumentationsLabel)
            && this.upperGrid.getChildren().contains(instrumentationsSelect))) {
            this.upperGrid.add(instrumentationsLabel, 0, 1);
            this.upperGrid.add(instrumentationsSelect, 1, 1);
        }
    }

     */

    /**
     * Sets the actions for all buttons of the new duty view.
     */
    private void setButtonActions() {
        // Save button
        this.scheduleSaveBtn.setOnAction(event -> saveNewDutyEntry());

        // Cancel button
        this.scheduleBackBtn.setOnAction(e -> confirmTabClosure());

        // Button to open new series of performances tabs
        this.newSeriesOfPerformancesBtn.setOnAction(e -> openNewSopTab());
    }

    /**
     * Sets the validators required for the new duty view.
     */
    private void setInputValidators() {
        // Validate Combobox dutyCategory
        this.dutyCategorySelect.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.validCategory.set(!this.dutyCategorySelect.getSelectionModel().isEmpty());
            updatePointsField();
            setSaveButtonStatus();
        });

        // Validate start date
        RequiredFieldValidator dateValidator = new RequiredFieldValidator();
        dateValidator.setMessage(this.resources.getString("tab.duty.new.entry.error.datemissing"));
        this.dutyStartDateInput.getValidators().add(dateValidator);
        this.dutyStartDateInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.validStartDate.set(this.dutyStartDateInput.validate());
            updatePointsField();
            setSaveButtonStatus();
        });

        // Validate end date
        this.dutyEndDateInput.getValidators().add(dateValidator);
        this.dutyEndDateInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.validEndDate.set(this.dutyEndDateInput.validate());
            setSaveButtonStatus();
        });

        // Validate start time
        RequiredFieldValidator timeValidator = new RequiredFieldValidator();
        timeValidator.setMessage(this.resources
            .getString("tab.duty.new.entry.error.timemissing"));
        this.dutyStartTimeInput.getValidators().add(timeValidator);
        this.dutyStartTimeInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.validStartTime.set(this.dutyStartTimeInput.validate());
            setSaveButtonStatus();
        });

        // Validate end time
        this.dutyEndTimeInput.getValidators().add(timeValidator);
        this.dutyEndTimeInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.validEndTime.set(this.dutyEndTimeInput.validate());
            setSaveButtonStatus();
        });
    }

    /**
     * Initializes the {@link #seriesOfPerformancesSelect} combo box with data.
     */
    private void initSeriesOfPerformancesComboBox() {
        List<SeriesOfPerformancesEntity> seriesOfPerformancesList =
            new SeriesOfPerformancesDao().getAll();
        LOG.debug("Found {} series of performances", seriesOfPerformancesList.size());

        final ObservableList<SeriesOfPerformancesEntity> observableList =
            FXCollections.observableArrayList();

        observableList.addAll(seriesOfPerformancesList);
        this.seriesOfPerformancesSelect.getItems().setAll(observableList);
        this.seriesOfPerformancesSelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(SeriesOfPerformancesEntity seriesOfPerformancesEntity) {
                return seriesOfPerformancesEntity.getDescription()
                    + " | " + " (" + seriesOfPerformancesEntity.getStartDate().toString()
                    + ")-(" + seriesOfPerformancesEntity.getEndDate().toString() + ")";
            }

            @Override
            public SeriesOfPerformancesEntity fromString(String title) {
                return observableList.stream()
                    .filter(item -> item.getDescription().equals(title))
                    .collect(Collectors.toList()).get(0);
            }
        });
    }

    /**
     * Initializes the {@link #instrumentationsSelect} combo box with data.
     */
    private void initInstrumentationsCheckComboBox() {
        this.instrumentationsSelect.getCheckModel().clearChecks();
        final ObservableSet<InstrumentationEntity> observInstrumentations =
            FXCollections.observableSet();

        Set<InstrumentationEntity> instrumentations =
            this.seriesOfPerformancesManager.getAllInstrumentations(
                this.seriesOfPerformancesSelect.getSelectionModel().getSelectedItem());
        observInstrumentations.addAll(instrumentations);

        ObservableList<InstrumentationEntity> oldList = this.instrumentationsSelect.getItems();
        this.instrumentationsSelect.getItems().removeAll(oldList);
        this.instrumentationsSelect.getItems().addAll(instrumentations);
        this.instrumentationsSelect.setConverter(
            new StringConverter<>() {
                @Override
                public String toString(InstrumentationEntity inst) {
                    return (inst.getName() + " - "
                        + inst.getMusicalPiece().getName());
                }

                @Override
                public InstrumentationEntity fromString(String nameOfInst) {
                    LOG.error(
                        "Somehow the instrumentation couldn't get found by its"
                            + " name in the NewDutyEntryController");
                    //Should never be able to get here
                    return null;
                }
            });
    }

    /**
     * Initializes the {@link #dutyCategorySelect} combo box with data.
     */
    private void initCategoryComboBox() {
        List<DutyCategory> dutyCategoryList = this.dutyCategoryManager.getDutyCategories();
        LOG.debug("Found {} duty categories", dutyCategoryList.size());

        this.dutyCategorySelect.getItems().setAll(dutyCategoryList);
        this.dutyCategorySelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(DutyCategory dutyCategory) {
                return dutyCategory.getEntity().getType();
            }

            @Override
            public DutyCategory fromString(String title) {
                return dutyCategoryList.stream()
                    .filter(item -> item.getEntity().getType().equals(title))
                    .collect(Collectors.toList()).get(0);
            }
        });
    }

    /**
     * Updates the {@link #dutyPointsInput} field based on currently selected/inserted data.
     */
    private void updatePointsField() {
        // Check if a valid start date is set to calculate points
        if (this.validStartDate.get() && validCategory.get()) {
            this.dutyPointsInput.setDisable(false);
            this.fillPointsField();
        } else {
            this.dutyPointsInput.clear();
            this.dutyPointsInput.setDisable(true);
        }
    }

    /**
     * Fills the {@link #dutyPointsInput} field with points matching the
     * selected {@link DutyCategory}.
     */
    private void fillPointsField() {
        DutyCategoryChangelogEntity temp = null;
        int points = 0;
        for (DutyCategoryChangelogEntity dcl : this.dutyCategorySelect.getSelectionModel()
            .getSelectedItem().getEntity().getDutyCategoryChangelogs()
        ) {
            if (temp == null || (dcl.getStartDate().isAfter(temp.getStartDate())
                && this.dutyStartDateInput.getValue().isAfter(dcl.getStartDate()))
                || this.dutyStartDateInput.getValue().isEqual(dcl.getStartDate())
            ) {
                temp = dcl;
                points = temp.getPoints();
            }
        }
        this.dutyPointsInput.setText(String.valueOf(points));
        this.userEditedPoints = false;
    }

    /**
     * Validates whether the following conditions are met.
     *
     * <p>- The {@code description} has not more than 45 characters
     * - The end datetime is after the start dattime
     * - The date is within the time frame of the corresponding series of performances
     *
     * @return true when validation is successful, false oterwise
     */
    private boolean validateInputs() {
        if (dutyDescriptionInput.getText().length() > 45) {
            MainController.showErrorAlert(
                this.resources.getString("tab.duty.new.entry.error.title"),
                this.resources.getString("tab.duty.new.entry.error.nametoolong"),
                this.resources.getString("global.button.ok")
            );
            return false;
        } else if (dutyEndDateInput.getValue().atTime(dutyEndTimeInput.getValue())
            .isBefore(dutyStartDateInput.getValue().atTime(dutyStartTimeInput.getValue()))
        ) {
            MainController.showErrorAlert(
                this.resources.getString("tab.duty.new.entry.error.title"),
                this.resources.getString("tab.duty.new.entry.error.endingDateBeforeStartingDate"),
                this.resources.getString("global.button.ok")
            );
            return false;
        } else if (!this.seriesOfPerformancesSelect.getSelectionModel().isEmpty()
            && (dutyStartDateInput.getValue()
            .isBefore(seriesOfPerformancesSelect.getValue().getStartDate())
            || dutyEndDateInput.getValue()
            .isAfter(seriesOfPerformancesSelect.getValue().getEndDate()))
        ) {
            MainController.showErrorAlert(
                this.resources.getString("tab.duty.new.entry.error.title"),
                this.resources.getString("tab.duty.new.entry.error.DutyOutOfSOPTimeframe"),
                this.resources.getString("global.button.ok")
            );
            return false;
        } else if (!StringUtils.isNumeric(this.dutyPointsInput.getText())) {
            MainController.showErrorAlert(
                this.resources.getString("tab.duty.new.entry.error.inputpoints.title"),
                this.resources.getString("tab.duty.new.entry.error.inputpoints.context"),
                this.resources.getString("global.button.ok")
            );
            return false;
        } else {
            return true;
        }
    }

    /**
     * Disables the {@link #scheduleSaveBtn} if not all requirements have been met.
     * Makes the button clickable otherwise.
     */
    private void setSaveButtonStatus() {
        if (this.dutyCategorySelect.getSelectionModel().isEmpty()) {
            this.validCategory.set(false);
            this.dutyCategorySelect.setBorder(
                new Border(
                    new BorderStroke(
                        Paint.valueOf("red"),
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderWidths.DEFAULT
                    )
                )
            );
            this.scheduleSaveBtn.setDisable(true);
        } else {
            this.dutyCategorySelect.setBorder(null);
            if (this.validCategory.get() && this.validStartDate.get()
                && this.validEndDate.get() && this.validStartTime.get()
                && this.validEndTime.get()
            ) {
                this.scheduleSaveBtn.setDisable(false);
            } else {
                this.scheduleSaveBtn.setDisable(true);
            }
        }
    }

    /**
     * Persists a new duty.
     */
    private void saveNewDutyEntry() {
        if (validateInputs()) {
            // Delegate domain object creation to manager
            this.duty = this.dutyManager.createDuty(
                this.dutyCategorySelect.getValue(),
                this.dutyDescriptionInput.getText(),
                this.calculateTimeOfDay(this.dutyStartTimeInput.getValue()),
                this.dutyStartDateInput.getValue().atTime(this.dutyStartTimeInput.getValue()),
                this.dutyEndDateInput.getValue().atTime(this.dutyEndTimeInput.getValue()),
                this.seriesOfPerformancesSelect.getValue()
            );

            Set<InstrumentationEntity> instrumentations
                = new LinkedHashSet<>(
                this.instrumentationsSelect.getCheckModel().getCheckedItems());
            // Delegate saving to manager
            this.dutyManager.save(
                this.duty,
                this.userEditedPoints,
                Integer.parseInt(this.dutyPointsInput.getText()),
                instrumentations
            );

            // Show success alert
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(this.resources.getString("tab.duty.new.entry.success.title"));
            successAlert.setContentText(
                this.resources.getString("tab.duty.new.entry.success.dutySaved")
            );
            successAlert.getButtonTypes().setAll(
                new ButtonType(this.resources.getString("global.button.ok"))
            );

            // Get custom success icon
            ImageView icon = new ImageView("images/successIcon.png");
            icon.setFitHeight(48);
            icon.setFitWidth(48);
            successAlert.setGraphic(icon);
            successAlert.setHeaderText(
                this.resources.getString("tab.duty.new.entry.success.header")
            );
            successAlert.show();
        } else {
            LOG.error("New Duty could not be saved");
        }
    }

    /**
     * Closes the tab after confirming that the user really wants to do so.
     */
    private void confirmTabClosure() {
        if (this.duty == null) {
            this.closeTab();
        } else {
            if (this.duty.getPersistenceState().equals(PersistenceState.EDITED)) {
                if (this.getConfirmation() == ButtonType.OK) {
                    this.closeTab();
                }
            }
        }
        LOG.debug("Closing 'New Duty' tab");
        this.parentController.removeTab(TabPaneEntry.ADD_DUTY);
        this.parentController.selectTab(TabPaneEntry.ORG_OFFICER_CALENDAR_VIEW);
    }

    /**
     * Force user to confirm that he wants to close without saving.
     *
     * @return The {@link ButtonType} that was pressed
     */
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
     * Calculates the time of day String for a given time.
     *
     * <p>Possible times are {@code MORNING}, {@code AFTERNOON}, {@code EVENING}.
     *
     * @param startTime The start time
     * @return A String matching a predefined value for the start time
     */
    private String calculateTimeOfDay(LocalTime startTime) {
        if (startTime.isBefore(LocalTime.of(10, 1))) {
            return "MORNING";
        }
        if (startTime.isBefore(LocalTime.of(17, 1))) {
            return "AFTERNOON";
        }
        return "EVENING";
    }

    /**
     * Opens a new series of performances tab.
     */
    private void openNewSopTab() {
        this.parentController.addTab(TabPaneEntry.ADD_SOP);
    }

    /**
     * Closes the current tab.
     */
    private void closeTab() {
        LOG.debug("Closing Add Duty");
        this.parentController.removeTab(TabPaneEntry.ADD_DUTY);
        this.parentController.selectTab(TabPaneEntry.ORG_OFFICER_CALENDAR_VIEW);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeWithParent() {
        // Intentionally empty - currently not needed
    }
}
