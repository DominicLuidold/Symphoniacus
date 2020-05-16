package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyCategoryManager;
import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.SeriesOfPerformancesManager;
import at.fhv.teamb.symphoniacus.application.dto.DutyCategoryChangeLogDto;
import at.fhv.teamb.symphoniacus.application.dto.DutyCategoryDto;
import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.application.dto.InstrumentationDto;
import at.fhv.teamb.symphoniacus.application.dto.SeriesOfPerformancesDto;
import at.fhv.teamb.symphoniacus.domain.DutyCategory;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import at.fhv.teamb.symphoniacus.presentation.internal.UkTimeFormatter;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckComboBox;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;


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
    private DutyDto duty;
    private DutyManager dutyManager;
    private DutyCategoryManager dutyCategoryManager;
    private SeriesOfPerformancesManager seriesOfPerformancesManager;
    private AtomicBoolean validStartDate = new AtomicBoolean(false);
    private AtomicBoolean validEndDate = new AtomicBoolean(false);
    private AtomicBoolean validCategory = new AtomicBoolean(false);
    private AtomicBoolean validStartTime = new AtomicBoolean(false);
    private AtomicBoolean validEndTime = new AtomicBoolean(false);
    private AtomicBoolean validSoP = new AtomicBoolean(false);
    private boolean userEditedPoints;

    @FXML
    private ComboBox<SeriesOfPerformancesDto> seriesOfPerformancesSelect;

    @FXML
    private Button newSeriesOfPerformancesBtn;

    @FXML
    private Button editDutyPointsBtn;

    @FXML
    private ComboBox<DutyCategoryDto> dutyCategorySelect;

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
    private CheckComboBox<InstrumentationDto> instrumentationsSelect;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.dutyManager = new DutyManager();
        this.seriesOfPerformancesManager = new SeriesOfPerformancesManager();
        this.dutyCategoryManager = new DutyCategoryManager();
        this.instrumentationsSelect.setDisable(true);

        // Init combo boxes with data
        this.initCategoryComboBox();

        // Disable non-editable/pressable elements
        this.scheduleSaveBtn.setDisable(true);
        this.dutyPointsInput.setDisable(true);
        this.seriesOfPerformancesSelect.setDisable(true);

        // Set input validators
        this.setInputValidators();

        // Set button actions
        this.setButtonActions();

        // Set event handlers
        this.setEventHandlers();

        // Set UK Time Format for DatePicker
        this.dutyStartDateInput.setConverter(UkTimeFormatter.getUkTimeConverter());
        this.dutyEndDateInput.setConverter(UkTimeFormatter.getUkTimeConverter());

        // Hier könnte man zukünftig das Format frei wählbar machen
        setTimeConverter(Locale.UK);

        setComboboxConverters();
    }

    private void setTimeConverter(Locale format) {
        StringConverter<LocalTime> converter =
            new LocalTimeStringConverter(FormatStyle.SHORT, format);
        this.dutyStartTimeInput.setConverter(converter);
        this.dutyEndTimeInput.setConverter(converter);
        if (format.equals(Locale.UK)) {
            this.dutyStartTimeInput.set24HourView(true);
            this.dutyEndTimeInput.set24HourView(true);
        }
    }

    /**
     * Sets the event handlers on dutyPointsInput and seriesOfPerformancesSelect.
     */
    private void setEventHandlers() {
        // Add event listener for updated points
        this.dutyPointsInput.textProperty().addListener(
            (observable, oldValue, newValue) -> userEditedPoints = true
        );

        // Show instrumentations of a selected series
        this.seriesOfPerformancesSelect.addEventHandler(ComboBoxBase.ON_HIDDEN, event -> {
            if (this.seriesOfPerformancesSelect.getSelectionModel().isEmpty()) {
                this.instrumentationsSelect.setDisable(true);
            } else {
                initInstrumentationsCheckComboBox();
                this.instrumentationsSelect.setDisable(false);
            }
        });
    }

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
        FontIcon addIcon = new FontIcon(FontAwesome.PLUS);
        addIcon.getStyleClass().addAll("button-icon");
        this.newSeriesOfPerformancesBtn.setGraphic(addIcon);

        this.editDutyPointsBtn.setOnAction(e -> setDutyPointsInputEditability());
        FontIcon editIcon = new FontIcon(FontAwesome.EDIT);
        addIcon.getStyleClass().addAll("button-icon");
        this.editDutyPointsBtn.setGraphic(editIcon);
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

        //Validate Combobox seriesOfPerformance
        this.seriesOfPerformancesSelect.valueProperty()
            .addListener((observable, oldValue, newValue) -> {
                this.validSoP.set(!this.seriesOfPerformancesSelect.getSelectionModel().isEmpty());
                setSaveButtonStatus();
            });


        // Validate start date
        RequiredFieldValidator dateValidator = new RequiredFieldValidator();
        dateValidator.setMessage(this.resources.getString("tab.duty.new.entry.error.datemissing"));
        this.dutyStartDateInput.getValidators().add(dateValidator);
        this.dutyStartDateInput.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.validStartDate.set(this.dutyStartDateInput.validate());
            updatePointsField();
            if (newValue != null) {
                initSeriesOfPerformancesComboBox();
                this.seriesOfPerformancesSelect.setDisable(false);
            } else {
                this.seriesOfPerformancesSelect.getItems().clear();
                this.seriesOfPerformancesSelect.setDisable(true);
            }
            //Instrumentations will always be reset if you choose a new date
            this.instrumentationsSelect.getItems().clear();
            this.instrumentationsSelect.setDisable(true);
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
        List<SeriesOfPerformancesDto> seriesOfPerformancesList =
            this.seriesOfPerformancesManager.getFilteredSeries(this.dutyStartDateInput.getValue());
        LOG.debug("Found {} series of performances", seriesOfPerformancesList.size());

        final ObservableList<SeriesOfPerformancesDto> observableList =
            FXCollections.observableArrayList();

        observableList.addAll(seriesOfPerformancesList);
        this.seriesOfPerformancesSelect.getItems().setAll(observableList);
    }

    /**
     * Initializes the {@link #instrumentationsSelect} combo box with data.
     */
    private void initInstrumentationsCheckComboBox() {
        this.instrumentationsSelect.getCheckModel().clearChecks();
        final ObservableSet<InstrumentationDto> observInstrumentations =
            FXCollections.observableSet();

        Set<InstrumentationDto> instrumentations =
            this.seriesOfPerformancesManager.getAllInstrumentations(
                this.seriesOfPerformancesSelect.getSelectionModel().getSelectedItem()
            );
        observInstrumentations.addAll(instrumentations);

        ObservableList<InstrumentationDto> oldList = this.instrumentationsSelect.getItems();
        this.instrumentationsSelect.getItems().removeAll(oldList);
        this.instrumentationsSelect.getItems().addAll(instrumentations);

    }

    /**
     * Initializes the {@link #dutyCategorySelect} combo box with data.
     */
    private void initCategoryComboBox() {
        List<DutyCategoryDto> dutyCategoryList = this.dutyCategoryManager.getDutyCategories();
        LOG.debug("Found {} duty categories", dutyCategoryList.size());
        this.dutyCategorySelect.getItems().setAll(dutyCategoryList);
    }

    /**
     * Set necessary String converters for each Combobox.
     */
    private void setComboboxConverters() {
        // Instrumentation Combobox
        this.instrumentationsSelect.setConverter(
            new StringConverter<>() {
                @Override
                public String toString(InstrumentationDto inst) {
                    return (inst.getName() + " - "
                        + inst.getMusicalPiece().getName());
                }

                @Override
                public InstrumentationDto fromString(String nameOfInst) {
                    LOG.error(
                        "Somehow the instrumentation couldn't get found by its"
                            + " name in the NewDutyEntryController");
                    //Should never be able to get here
                    return null;
                }
            });

        // Series of Performances Combobox
        this.seriesOfPerformancesSelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(SeriesOfPerformancesDto seriesOfPerformancesEntity) {
                return seriesOfPerformancesEntity.getDescription()
                    + " | " + " (" + seriesOfPerformancesEntity.getStartDate().toString()
                    + ")-(" + seriesOfPerformancesEntity.getEndDate().toString() + ")";
            }

            @Override
            public SeriesOfPerformancesDto fromString(String title) {
                ObservableList<SeriesOfPerformancesDto> observableList =
                    seriesOfPerformancesSelect.getItems();
                return observableList.stream()
                    .filter(item -> item.getDescription().equals(title))
                    .collect(Collectors.toList()).get(0);
            }
        });

        // Duty Category Combobox
        this.dutyCategorySelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(DutyCategoryDto dutyCategory) {
                return dutyCategory.getType();
            }

            @Override
            public DutyCategoryDto fromString(String title) {
                ObservableList<DutyCategoryDto> dutyCategoryList = dutyCategorySelect.getItems();
                DutyCategoryDto result = dutyCategoryList.stream()
                    .filter(item -> item.getType().equals(title))
                    .collect(Collectors.toList()).get(0);
                LOG.debug("Category Combobox from String -> {}",
                    result.getDutyCategoryId());
                return result;
            }
        });
    }

    /**
     * Updates the {@link #dutyPointsInput} field based on currently selected/inserted data.
     */
    private void updatePointsField() {
        // Check if a valid start date is set to calculate points
        if (this.validStartDate.get() && validCategory.get()) {
            this.fillPointsField();
        } else {
            this.dutyPointsInput.clear();
        }
    }

    /**
     * Fills the {@link #dutyPointsInput} field with points matching the
     * selected {@link DutyCategory}.
     */
    private void fillPointsField() {
        DutyCategoryChangeLogDto temp = null;
        int points = 0;
        for (DutyCategoryChangeLogDto dcl : this.dutyCategorySelect.getSelectionModel()
            .getSelectedItem().getChangeLogs()
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
        } else if (this.seriesOfPerformancesSelect.getSelectionModel().getSelectedItem() != null
            && this.instrumentationsSelect.getCheckModel().isEmpty()) {
            MainController.showErrorAlert(
                this.resources.getString("tab.duty.new.entry.error.title"),
                this.resources.getString("tab.duty.new.entry.error.instrumentation.title"),
                this.resources.getString("global.button.ok")
            );
            return false;
        } else if (this.dutyManager.doesDutyAlreadyExists(this.seriesOfPerformancesSelect
                .getSelectionModel().getSelectedItem(), this.instrumentationsSelect
                .getCheckModel().getCheckedItems(), this.dutyStartDateInput.getValue()
                .atTime(this.dutyStartTimeInput.getValue()),
            this.dutyEndDateInput.getValue().atTime(this.dutyEndTimeInput.getValue()),
            this.dutyCategorySelect.getSelectionModel().getSelectedItem())
        ) {
            MainController.showErrorAlert(
                this.resources.getString("tab.duty.new.entry.error.title"),
                this.resources.getString("tab.duty.new.entry.error.duty.title"),
                this.resources.getString("global.button.ok")
            );
            return false;
        } else if (this.instrumentationsSelect.getCheckModel().getCheckedItems().isEmpty()) {
            MainController.showErrorAlert(
                this.resources.getString("tab.duty.new.entry.error.title"),
                this.resources.getString("tab.duty.new.entry.error.instrumentation.title"),
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
            this.scheduleSaveBtn.setDisable(true);
        } else {
            if (this.validCategory.get() && this.validStartDate.get()
                && this.validEndDate.get() && this.validStartTime.get()
                && this.validEndTime.get() && this.validSoP.get()
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
            Set<InstrumentationDto> instrumentations = new LinkedHashSet<>(
                this.instrumentationsSelect.getCheckModel().getCheckedItems()
            );
            // Delegate domain object creation to manager
            this.duty = this.dutyManager.save(
                this.userEditedPoints,
                Integer.parseInt(this.dutyPointsInput.getText()),
                instrumentations,
                this.seriesOfPerformancesSelect.getValue(),
                this.dutyCategorySelect.getValue(),
                this.dutyDescriptionInput.getText(),
                this.calculateTimeOfDay(this.dutyStartTimeInput.getValue()),
                this.dutyStartDateInput.getValue().atTime(this.dutyStartTimeInput.getValue()),
                this.dutyEndDateInput.getValue().atTime(this.dutyEndTimeInput.getValue())
            );

            if (this.duty.getPersistenceState() != null) {
                if (this.duty.getPersistenceState() == PersistenceState.PERSISTED) {
                    // Show success alert
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle(this.resources
                        .getString("tab.duty.new.entry.success.title"));
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
                    MainController.showErrorAlert(
                        this.resources.getString("tab.duty.new.entry.error.title"),
                        this.resources.getString("tab.duty.new.entry.error.notPossibleSave"),
                        this.resources.getString("global.button.ok")
                    );
                }
            }

        } else {
            LOG.error("New Duty could not be saved");
        }
    }

    private void setDutyPointsInputEditability() {
        if (this.dutyPointsInput.isDisable() && this.validStartDate.get() && validCategory.get()) {
            this.dutyPointsInput.setDisable(false);
        } else {
            this.dutyPointsInput.setDisable(true);
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
     * {@inheritDoc}.
     */

    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }


    /**
     * {@inheritDoc}.
     */

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }


    /**
     * {@inheritDoc}.
     */

    @Override
    public void initializeWithParent() {
        // Intentionally empty - currently not needed
    }
}

