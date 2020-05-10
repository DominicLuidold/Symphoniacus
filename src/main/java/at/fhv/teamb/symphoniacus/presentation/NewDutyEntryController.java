package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyCategoryManager;
import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.SeriesOfPerformancesManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyCategory;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.CustomCalendarButtonEvent;
import at.fhv.teamb.symphoniacus.presentation.internal.OldDutyComboView;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

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

    @FXML
    private AnchorPane newDutyEntryPane;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Button scheduleSaveBtn;

    @FXML
    private JFXTextField dutyNameInput;

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

    private ValidationSupport validationSupport = new ValidationSupport();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.dutyManager = new DutyManager();
        this.dutyCategoryManager = new DutyCategoryManager();
        initCategoryComboBox();
        initSeriesOfPerformancesComboBox();

        /*
        ValidationDecoration cssDecorator = new StyleClassValidationDecoration(
            "error",
            "warning"
        );
        this.validationSupport.setValidationDecorator(cssDecorator);

        this.validationSupport.registerValidator(this.dutyNameInput,
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.namemissing"
                )
            )
        );

/*        this.validationSupport.registerValidator(this.seriesOfPerformancesSelect,
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.seriesofperformancesmissing"
                )
            )
        );



        this.validationSupport.registerValidator(this.dutyCategorySelect,
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.categorymissing"
                )
            )
        );

        this.validationSupport.registerValidator(this.dutyPointsInput,
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.pointsmissing"
                )
            )
        );

        this.validationSupport.registerValidator(this.dutyStartDateInput,
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.startdatemissing"
                )
            )
        );

        this.validationSupport.registerValidator(this.dutyStartTimeInput,   //Todo: own Validator
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.starttimemissing"
                )
            )
        );

        this.validationSupport.registerValidator(this.dutyEndDateInput,
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.enddatemissing"
                )
            )
        );

        this.validationSupport.registerValidator(this.dutyEndTimeInput,     //Todo: own Validator
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.endtimemissing"
                )
            )
        );

        //Sets Save button disabled if form is not valid
        this.validationSupport.validationResultProperty()
            .addListener((observable, oldValue, newValue) -> {
                this.isValid = newValue.getErrors().isEmpty();
                this.scheduleSaveBtn.setDisable(!isValid);
            });
          */



        // Save button method
        scheduleSaveBtn.setOnAction(event -> saveNewDutyEntry());

        // Cancel button method
        this.scheduleBackBtn.setOnAction(e -> closeNewDutyEntry());

        //Button to open new tab for new series of performances
        this.seriesOfPerformancesNewBtn.setOnAction(e -> openNewSOP());

        this.dutyCategorySelect.setOnAction(e -> setPointsInTextfield());

    }

    /**
        Actions to be executed after clicking the 'Back' Button:
        -> check if persisted state is latest
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
        this.dutyCategorySelect.getItems().clear();
        LOG.debug("Closing Add Duty");
        this.parentController.removeTab(TabPaneEntry.ADD_DUTY);
    }


    private void saveNewDutyEntry() {   //TODO: handle edited points
        /*
         if (validateInputs()) {
            duty=dutyManager.createDuty(
                dutyCategorySelect.getValue(),
                dutyNameInput.getText(),
                calculateTimeOfDay(dutyStartTimeInput.getValue()),  //TODO: dutyManager.getTimeOfDate(dutyStartDateInput.getValue().atTime(dutyStartTimeInput.getValue()); -> returnt String.
                dutyStartDateInput.getValue().atTime(dutyStartTimeInput.getValue()),
                dutyEndDateInput.getValue().atTime(dutyEndTimeInput.getValue())
            );

             dutyManager.save(duty);

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

         */
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
        if (dutyNameInput.getText().length() > 45) {
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
        } else if (dutyStartDateInput.getValue().isBefore(seriesOfPerformancesSelect.getValue().getStartDate()) || dutyEndDateInput.getValue().isAfter(seriesOfPerformancesSelect.getValue().getEndDate())){
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

        List<SeriesOfPerformancesEntity> seriesOfPerformancesList = new SeriesOfPerformancesDao().getAll();
            this.dutyCategoryManager.getDutyCategories();

        final ObservableList<SeriesOfPerformancesEntity> observableList =
            FXCollections.observableArrayList();
        LOG.debug("Found {} series of performances", seriesOfPerformancesList.size());

        for (SeriesOfPerformancesEntity sop : seriesOfPerformancesList) {
            observableList.add(sop);
        }

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

    public void setPointsInTextfield() {
        dutyPointsInput.setText(Integer.toString(dutyCategorySelect.getValue().getPoints()));
    }



    public String calculateTimeOfDay(LocalTime starttime) {
        if (starttime.isBefore(LocalTime.of(10, 01))) {
            return "MORNING";
        } if (starttime.isBefore(LocalTime.of(17, 01))) {
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
