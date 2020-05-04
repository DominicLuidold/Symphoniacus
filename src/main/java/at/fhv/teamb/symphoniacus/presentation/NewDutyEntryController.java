package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.LoginManager;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.AlertHelper;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.LoginTask;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

/**
 * GUI Controller responsible for creating a new Duty Entry.
 *
 * @author Theresa Gierer
 */

public class NewDutyEntryController implements Initializable {

    private static final Logger LOG = LogManager.getLogger(NewDutyEntryController.class);

    @FXML
    private AnchorPane pane;

    private ResourceBundle resources;

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
    private TextField dutyStartTimeInput;

    @FXML
    private DatePicker dutyEndDateInput;

    @FXML
    private TextField dutyEndTimeInput;

    @FXML
    private TextField dutyPlaceInput;

    private ValidationSupport validationSupport = new ValidationSupport();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;


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
        /*
        name
        sop
        cat
        points
        date
        time
        place
         */




    }


}
