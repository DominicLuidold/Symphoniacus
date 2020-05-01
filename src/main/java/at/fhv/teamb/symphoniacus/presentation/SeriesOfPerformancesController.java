package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.SeriesOfPerformancesManager;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import com.mysql.jdbc.log.Log;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextFlow;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.CheckModel;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

/**
 * GUI Controller responsible for creating a new Series of Performances
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class SeriesOfPerformancesController implements Initializable {
    private static final Logger LOG = LogManager.getLogger(SeriesOfPerformancesController.class);
    private boolean isValid = false;
    private ValidationSupport validationSupport = new ValidationSupport();
    private SeriesOfPerformancesManager seriesManager;

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField nameOfSeries;

    @FXML
    private CheckListView<MusicalPieceEntity> musicalPieceCheckListView;

    @FXML
    private CheckListView<InstrumentationEntity> instrumentationCheckListView;

    /*
    This one is and will not be implemented.
     */
    @FXML
    private Button addModifyButton;

    @FXML
    private TextFlow instrumentationPositionsFlow;

    @FXML
    private DatePicker startingDate;

    @FXML
    private DatePicker endingDate;

    @FXML
    private CheckBox isTour;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private ResourceBundle resources;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.seriesManager = new SeriesOfPerformancesManager();

        ValidationDecoration cssDecorator = new StyleClassValidationDecoration(
            "error",
            "warning"
        );
        this.validationSupport.setValidationDecorator(cssDecorator);

        this.validationSupport.registerValidator(this.nameOfSeries,
            Validator.createEmptyValidator("A name is required."));

        CheckModel<MusicalPieceEntity> musicalPieces = musicalPieceCheckListView.getCheckModel();
        this.validationSupport.registerValidator(musicalPieceCheckListView,
            Validator.createEmptyValidator("You must pick a musical piece."));

        this.validationSupport.registerValidator(this.instrumentationCheckListView,
            Validator.createEmptyValidator("You must pick an instrumentation."));

        this.validationSupport.registerValidator(this.startingDate,
            Validator.createEmptyValidator("A starting date is required."));

        this.validationSupport.registerValidator(this.endingDate,
            Validator.createEmptyValidator("An ending date is required."));


        //Sets Save button disabled if form is not valid
        this.validationSupport.validationResultProperty()
            .addListener((observable, oldValue, newValue) -> {
                this.isValid = newValue.getErrors().isEmpty();
                this.saveButton.setDisable(!isValid);
            });
    }

    public void initMusicialPiecesCheckListView() {

    }

    public void save() {
        System.out.println("test");
    }

    public void cancel() {

    }
}
