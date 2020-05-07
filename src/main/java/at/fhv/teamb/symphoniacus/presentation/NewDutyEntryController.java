package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private TabPaneController parentController;
    private ResourceBundle resources;
    private Duty duty;
    private DutyManager dutyManager;

    @FXML
    private AnchorPane newDutyEntryPane;

    @FXML
    private Button scheduleBackBtn;

    @FXML
    private Button scheduleSaveBtn;

    @FXML
    private TextField dutyNameInput;

    @FXML
    private ComboBox<SeriesOfPerformancesEntity> seriesOfPerformancesSelect;

    @FXML
    private Button seriesOfPerformancesNewBtn;

    @FXML
    private ComboBox<DutyCategoryEntity> dutyCategorySelect;

    @FXML
    private TextField dutyPointsInput;

    @FXML
    private JFXDatePicker dutyStartDateInput;

    @FXML
    private JFXTimePicker dutyStartTimeInput;

    @FXML
    private JFXDatePicker dutyEndDateInput;

    @FXML
    private JFXTimePicker dutyEndTimeInput;

    @FXML
    private TextField dutyPlaceInput;

    private ValidationSupport validationSupport = new ValidationSupport();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        this.scheduleBackBtn.setOnAction(e -> {
            closeNewDutyEntry();
        });

        //Todo: den schoaß anpassen

        /* this.scheduleSaveBtn.setOnAction(e -> {
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
        });*/

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

        //TODO: Discussion with Team whether SOP is needed or not.

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

        this.validationSupport.registerValidator(this.dutyStartTimeInput,
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

        this.validationSupport.registerValidator(this.dutyEndTimeInput,
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.endtimemissing"
                )
            )
        );
        this.validationSupport.registerValidator(this.dutyPlaceInput,
            Validator.createEmptyValidator(resources.getString(
                "tab.duty.new.entry.error.detailsmissing"
                )
            )
        );
    }

    /*
        Actions to be executed after clicking the 'Back' Button:
        -> check if persisted state is latest
     */
    private void closeNewDutyEntry() {
        //TODO Abfrage SaveState(old, newold)? von duty
        if (this.duty
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
        MasterController mc = MasterController.getInstance();
        DutySchedulerCalendarController cc =
            (DutySchedulerCalendarController) mc.get("CalendarController");
        cc.show();
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
