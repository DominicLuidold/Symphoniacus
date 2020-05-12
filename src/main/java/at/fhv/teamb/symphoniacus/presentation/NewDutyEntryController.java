package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyCategoryManager;
import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyCategory;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import shadow.org.codehaus.plexus.util.StringUtils;

/**
 * GUI Controller responsible for creating a new Duty Entry.
 *
 * @author Theresa Gierer
 */
public class NewDutyEntryController implements Initializable, Parentable<TabPaneController> {
    private static final Logger LOG = LogManager.getLogger(NewDutyEntryController.class);
    private boolean isValid = false;
    private TabPaneController parentController;
    private ResourceBundle resources;
    private Duty duty;
    private DutyManager dutyManager;
    private DutyCategoryManager dutyCategoryManager;
    private AtomicBoolean validStartDate = new AtomicBoolean(false);
    private AtomicBoolean validEndDate = new AtomicBoolean(false);
    private AtomicBoolean validCategory = new AtomicBoolean(false);
    private AtomicBoolean validStartTime = new AtomicBoolean(false);
    private AtomicBoolean validEndTime = new AtomicBoolean(false);
    private boolean userEditedPoints;

    @FXML
    private AnchorPane newDutyEntryPane;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Button scheduleSaveBtn;

    @FXML
    private JFXTextField dutyDescriptionInput;

    @FXML
    private ComboBox<SeriesOfPerformancesEntity> seriesOfPerformancesSelect;

    @FXML
    private Button seriesOfPerformancesNewBtn;

    @FXML
    private ComboBox<DutyCategory> dutyCategorySelect;

    @FXML
    private JFXTextField dutyPointsInput;

    @FXML
    private JFXDatePicker dutyStartDateInput;

    @FXML
    private JFXTimePicker dutyStartTimeInput;

    @FXML
    private JFXDatePicker dutyEndDateInput;

    @FXML
    private JFXTimePicker dutyEndTimeInput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.dutyManager = new DutyManager();
        this.dutyCategoryManager = new DutyCategoryManager();
        initCategoryComboBox();
        initSeriesOfPerformancesComboBox();

        this.scheduleSaveBtn.setDisable(true);
        this.dutyPointsInput.setDisable(true);

        // Validate Combobox dutyCategory
        this.dutyCategorySelect.valueProperty().addListener((
            observable,
            oldValue,
            newValue) -> {
            if (!this.dutyCategorySelect.getSelectionModel().isEmpty()) {
                this.validCategory.set(true);
            } else {
                this.validCategory.set(false);
            }
            updatePointsView();
            checkButtonVisibility();
        });

        // Validate Date Input
        RequiredFieldValidator dateValidator = new RequiredFieldValidator();
        dateValidator
            .setMessage(this.resources.getString("tab.duty.new.entry.error.datemissing"));
        this.dutyStartDateInput.getValidators().add(dateValidator);
        this.dutyStartDateInput.valueProperty().addListener((
            observable,
            oldValue,
            newValue) -> {
            if (this.dutyStartDateInput.validate()) {
                this.validStartDate.set(true);
            } else {
                this.validStartDate.set(false);
            }
            updatePointsView();
            checkButtonVisibility();
        });

        this.dutyEndDateInput.getValidators().add(dateValidator);
        this.dutyEndDateInput.valueProperty().addListener((
            observable,
            oldValue,
            newValue) -> {
            if (this.dutyEndDateInput.validate()) {
                this.validEndDate.set(true);
            } else {
                this.validEndDate.set(false);
            }
            checkButtonVisibility();
        });

        RequiredFieldValidator timeValidator = new RequiredFieldValidator();
        timeValidator.setMessage(this.resources
            .getString("tab.duty.new.entry.error.timemissing"));
        this.dutyStartTimeInput.getValidators().add(timeValidator);
        this.dutyStartTimeInput.valueProperty().addListener((
            observable,
            oldValue,
            newValue) -> {
            if (this.dutyStartTimeInput.validate()) {
                this.validStartTime.set(true);
            } else {
                this.validStartTime.set(false);
            }
            checkButtonVisibility();
        });

        this.dutyEndTimeInput.getValidators().add(timeValidator);
        this.dutyEndTimeInput.valueProperty().addListener((
            observable,
            oldValue,
            newValue) -> {
            if (this.dutyEndTimeInput.validate()) {
                this.validEndTime.set(true);
            } else {
                this.validEndTime.set(false);
            }
            checkButtonVisibility();
        });

        // Save button method
        scheduleSaveBtn.setOnAction(event -> saveNewDutyEntry());

        // Cancel button method
        this.scheduleBackBtn.setOnAction(e -> closeNewDutyEntry());

        //Button to open new tab for new series of performances
        this.seriesOfPerformancesNewBtn.setOnAction(e -> openNewSOP());

        this.dutyPointsInput.textProperty().addListener((observable, oldValue, newValue) -> {
            userEditedPoints = true;
        });

        this.seriesOfPerformancesSelect.addEventHandler(ComboBoxBase.ON_SHOWING,event -> {
            initSeriesOfPerformancesComboBox();
        });
    }

    private void checkButtonVisibility() {
        if (!this.dutyCategorySelect.getSelectionModel().isEmpty()) {
            this.dutyCategorySelect.setBorder(null);
            if (this.validCategory.get() && this.validStartDate.get()
                && this.validEndDate.get() && this.validStartTime.get()
                && this.validEndTime.get()
            ) {
                this.scheduleSaveBtn.setDisable(false);
            } else {
                this.scheduleSaveBtn.setDisable(true);
            }
        } else {
            this.validCategory.set(false);
            this.dutyCategorySelect.setBorder(new Border(new BorderStroke(Paint.valueOf("red"),
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            this.scheduleSaveBtn.setDisable(true);
        }
    }


    /**
     * Actions to be executed after clicking the 'Back' Button:
     * -> check if persisted state is latest
     */
    private void closeNewDutyEntry() {
        if (this.duty != null) {
            if (this.duty
                .getPersistenceState()
                .equals(PersistenceState.EDITED)
            ) {
                ButtonType userSelection = getConfirmation();
                if (userSelection == ButtonType.OK) {
                    tearDown();
                }
            }
        } else {
            tearDown();
        }
        LOG.debug("Closing Add Duty");
        this.parentController.removeTab(TabPaneEntry.ADD_DUTY);
        this.parentController.selectTab(TabPaneEntry.ORG_OFFICER_CALENDAR_VIEW);
    }

    private ButtonType getConfirmation() {
        Label label = new Label();
        ButtonType buttonType = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resources.getString("dialog.save.closewithoutsaving.title"));
        alert.setHeaderText(resources.getString("dialog.save.closewithoutsaving.message"));

        // option != null.
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null) {
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

    private void tearDown() {
        LOG.debug(
            "Current dutyManager: {} ,Current duty: {}",
            this.dutyManager,
            this.duty
        );
        this.dutyCategorySelect.getItems().clear();
        this.duty = null;
        this.dutyManager = null;
        //Folgende 4 Statements Wirklich nötig?
        this.seriesOfPerformancesSelect.getItems().clear();
        this.seriesOfPerformancesSelect
            .setPromptText(
                resources.getString("tab.duty.new.entry.dropdown.load.seriesofperformances"));
        this.seriesOfPerformancesSelect.setPlaceholder(
            new Label(resources
                .getString("tab.duty.new.entry.dropdown.load.seriesofperformances.default"))
        );
        LOG.debug("Closing Add Duty");
        this.parentController.removeTab(TabPaneEntry.ADD_DUTY);
        this.parentController.selectTab(TabPaneEntry.ORG_OFFICER_CALENDAR_VIEW);
    }

    private void saveNewDutyEntry() {
         if (validateInputs()) {
             this.duty = this.dutyManager.createDuty(
                 this.dutyCategorySelect.getValue(),
                 this.dutyDescriptionInput.getText(),
                 calculateTimeOfDay(this.dutyStartTimeInput.getValue()),
                 this.dutyStartDateInput.getValue().atTime(this.dutyStartTimeInput.getValue()),
                 this.dutyEndDateInput.getValue().atTime(this.dutyEndTimeInput.getValue()),
                 this.seriesOfPerformancesSelect.getValue()
                 );

             this.dutyManager.save(this.duty,this.userEditedPoints,
                 Integer.parseInt(this.dutyPointsInput.getText()));

            // After saving show success dialog
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(resources.getString("tab.duty.new.entry.success.title"));
            successAlert.setContentText(resources
                .getString("tab.duty.new.entry.success.dutySaved")
            );
            successAlert.getButtonTypes()
                .setAll(new ButtonType(resources.getString("global.button.ok")));

            // Get custom success icon
            ImageView icon = new ImageView("images/successIcon.png");
            icon.setFitHeight(48);
            icon.setFitWidth(48);
            successAlert.setGraphic(icon);
            successAlert.setHeaderText(resources
                .getString("tab.duty.new.entry.success.header"));
            successAlert.show();
        } else {
            LOG.debug(
                "New Duty could not be saved");
        }
    }


    /**
     * validates whether or not:
     * -The name has no more than 45 characters.
     * -The end datetime is after the start datetime.
     * - the date is inside the timeframe of the corresponding series of performances
     *
     * @return a boolean whether the validation is successful or not
     */

    private boolean validateInputs() {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (dutyDescriptionInput.getText().length() > 45) {
            alert.setTitle(resources.getString("tab.duty.new.entry.error.title"));
            alert.setContentText(resources.getString(
                "tab.duty.new.entry.error.nametoolong"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);

            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else if (dutyEndDateInput.getValue().atTime(dutyEndTimeInput.getValue())
            .isBefore(dutyStartDateInput.getValue().atTime(dutyStartTimeInput.getValue()))) {
            alert.setTitle(resources.getString("tab.duty.new.entry.error.title"));
            alert.setContentText(resources.getString(
                "tab.duty.new.entry.error.endingDateBeforeStartingDate"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else if (!this.seriesOfPerformancesSelect.getSelectionModel().isEmpty()
            && (dutyStartDateInput.getValue()
            .isBefore(seriesOfPerformancesSelect.getValue().getStartDate()) ||
            dutyEndDateInput.getValue()
                .isAfter(seriesOfPerformancesSelect.getValue().getEndDate()))) {
            alert.setTitle(resources.getString("tab.duty.new.entry.error.title"));
            alert.setContentText(resources.getString(
                "tab.duty.new.entry.error.DutyOutOfSOPTimeframe"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else if (!StringUtils.isNumeric(this.dutyPointsInput.getText())) {
            alert.setTitle(resources.getString("tab.duty.new.entry.error.inputpoints.title"));
            alert.setContentText(resources.getString(
                "tab.duty.new.entry.error.inputpoints.context"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);
            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else {
            return true;
        }
    }

    public void initCategoryComboBox() {
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
                    .filter(
                        item -> item
                            .getEntity().getType()
                            .equals(title)
                    )
                    .collect(Collectors.toList()).get(0);
            }
        });
    }

    public void initSeriesOfPerformancesComboBox() {
        List<SeriesOfPerformancesEntity> seriesOfPerformancesList =
            new SeriesOfPerformancesDao().getAll();
        this.dutyCategoryManager.getDutyCategories();

        final ObservableList<SeriesOfPerformancesEntity> observableList =
            FXCollections.observableArrayList();
        LOG.debug("Found {} series of performances", seriesOfPerformancesList.size());

        observableList.addAll(seriesOfPerformancesList);
        this.seriesOfPerformancesSelect.getItems().setAll(observableList);
        this.seriesOfPerformancesSelect.setConverter(new StringConverter<>() {
            @Override
            public String toString(SeriesOfPerformancesEntity seriesOfPerformancesEntity) {
                return seriesOfPerformancesEntity.getDescription();
            }

            @Override
            public SeriesOfPerformancesEntity fromString(String title) {
                return observableList.stream()
                    .filter(
                        item -> item
                            .getDescription()
                            .equals(title)
                    )
                    .collect(Collectors.toList()).get(0);
            }
        });
    }

    public void openNewSOP() {
        this.parentController.addTab(TabPaneEntry.ADD_SOP);
    }

    private void setPointsInTextfield() {
        DutyCategoryChangelogEntity temp = null;
        int points = 0;
        for (DutyCategoryChangelogEntity dcl : this.dutyCategorySelect.getSelectionModel()
            .getSelectedItem().getEntity().getDutyCategoryChangelogs()) {
            if (temp == null || dcl.getStartDate().isAfter(temp.getStartDate())
                && this.dutyStartDateInput.getValue().isAfter(dcl.getStartDate())
                || this.dutyStartDateInput.getValue().isEqual(dcl.getStartDate())) {
                temp = dcl;
                points = temp.getPoints();
            }
        }
        this.dutyPointsInput.setText(Integer.valueOf(points).toString());
        userEditedPoints = false;
    }

    private void updatePointsView() {
        // Check if Dates for duty are selected -> to calculate the correct points
        if (this.validStartDate.get() && validCategory.get()) {
            this.dutyPointsInput.setDisable(false);
            setPointsInTextfield();
        } else {
            this.dutyPointsInput.clear();
            this.dutyPointsInput.setDisable(true);
        }
    }


    public String calculateTimeOfDay(LocalTime starttime) {
        if (starttime.isBefore(LocalTime.of(10, 1))) {
            return "MORNING";
        }
        if (starttime.isBefore(LocalTime.of(17, 1))) {
            return "AFTERNOON";
        }
        return "EVENING";
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }

    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    @Override
    public void initializeWithParent() {
        // Intentionally empty - currently not needed
    }
}
