package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.SeriesOfPerformancesManager;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionInstrumentationEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import at.fhv.teamb.symphoniacus.presentation.internal.UkTimeFormatter;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckComboBox;

/**
 * GUI Controller responsible for creating a new Series of Performances.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class SeriesOfPerformancesController
    implements Initializable, Parentable<TabPaneController> {

    private static final Logger LOG = LogManager.getLogger(SeriesOfPerformancesController.class);
    // do not make these fields final -> validation
    private AtomicBoolean name = new AtomicBoolean(false);
    private AtomicBoolean start = new AtomicBoolean(false);
    private AtomicBoolean end = new AtomicBoolean(false);
    private SeriesOfPerformancesManager seriesManager;
    private boolean itemChanged;
    private TabPaneController parentController;
    @FXML
    private GridPane grid;

    @FXML
    private JFXTextField nameOfSeries;

    @FXML
    private CheckComboBox<MusicalPieceEntity> musicalPieceCheckComboBox;

    @FXML
    private CheckComboBox<InstrumentationEntity> instrumentationCheckComboBox;

    /*
    This one is and will not be implemented.
     */
    @FXML
    private Button addModifyButton;

    @FXML
    private JFXDatePicker startingDate;

    @FXML
    private JFXDatePicker endingDate;

    @FXML
    private CheckBox isTour;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private ResourceBundle resources;

    @FXML
    private ListView<String> listView;

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
        this.saveButton.setDisable(true);
        this.resources = resources;
        this.seriesManager = new SeriesOfPerformancesManager();
        this.listView = new ListView<>();
        this.grid.add(this.listView, 1, 3);
        this.instrumentationCheckComboBox
            .setTitle(resources
                .getString("seriesOfPerformances.instrumentations.placeholder"));
        initMusicialPiecesCheckListView();

        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage(resources
            .getString("seriesOfPerformances.validation.name"));
        this.nameOfSeries.getValidators().add(fieldValidator);

        this.nameOfSeries.focusedProperty().addListener((
            ObservableValue<? extends Boolean> observable,
            Boolean oldValue,
            Boolean newValue) -> {
            if (!newValue) {
                this.name.set(this.nameOfSeries.validate());
                checkButtonVisibility();
            }
        });

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage(this.resources.getString("seriesOfPerformances.validation.date"));
        this.startingDate.getValidators().add(validator);
        this.endingDate.getValidators().addAll(validator);

        this.startingDate.focusedProperty().addListener((
            ObservableValue<? extends Boolean> observable,
            Boolean oldValue,
            Boolean newValue)
            -> {
            if (!newValue) {
                this.start.set(this.startingDate.validate());
                checkButtonVisibility();
            }
        });

        this.endingDate.focusedProperty().addListener((
            ObservableValue<? extends Boolean> observable,
            Boolean oldValue,
            Boolean newValue) -> {
            if (!newValue) {

                this.end.set(this.endingDate.validate());
                checkButtonVisibility();
            }
        });

        // Save button method
        this.saveButton.setOnAction(event -> save());

        // Cancel button method
        this.cancelButton.setOnAction(event -> cancel());

        // Add/Modify button method
        this.addModifyButton.setOnAction(event -> addModify());

        startingDate.setConverter(UkTimeFormatter.getUkTimeConverter());
        endingDate.setConverter(UkTimeFormatter.getUkTimeConverter());
    }

    /**
     * loads all musicalPieces from the database to display them in a drop-down window.
     * When selecting one or more musicalPieces, the method
     * loadInstrumentationsFromChosenMusicalPieces is called.
     */
    public void initMusicialPiecesCheckListView() {
        final ObservableList<MusicalPieceEntity> musicalPieces =
            FXCollections.observableArrayList();
        Set<MusicalPieceEntity> mp = seriesManager.getAllMusicalPieces();
        musicalPieces.addAll(mp);

        final StringConverter<MusicalPieceEntity> musicalConverter =
            new StringConverter<>() {
                @Override
                public String toString(MusicalPieceEntity piece) {
                    return piece.getName();
                }

                @Override
                public MusicalPieceEntity fromString(String nameOfPiece) {
                    Optional<MusicalPieceEntity> piece =
                        SeriesOfPerformancesManager.getMusicalPieceFromName(nameOfPiece);
                    if (piece.isPresent()) {
                        return piece.get();
                    } else {
                        LOG.error(
                            "Somehow the musicial piece couldn't get found by its"
                                + " name in the SeriesOfPerformancesController");
                        //Should never be able to get here
                        return null;
                    }
                }
            };
        this.musicalPieceCheckComboBox.setConverter(musicalConverter);
        /*
        Der schlimmste Fehler meines Lebens!:
        https://stackoverflow.com/questions/30643979/checkcombobox-choices-are-empty
         */
        this.musicalPieceCheckComboBox.getItems().addAll(musicalPieces);

        // Changes boolean to avoid unnecessary select statements
        this.musicalPieceCheckComboBox.getCheckModel().getCheckedItems().addListener(
            (ListChangeListener<MusicalPieceEntity>) c -> this.itemChanged = true);

        // Call init Instrumentations, when Musical Pieces have been chosen
        this.musicalPieceCheckComboBox.addEventHandler(ComboBoxBase.ON_HIDDEN, event -> {
            if (this.itemChanged) {
                loadInstrumentationsFromChosenMusicalPieces(
                    this.musicalPieceCheckComboBox.getCheckModel().getCheckedItems());
                this.itemChanged = false;
            }
        });
    }

    /**
     * Loads all instrumentations for the given musicalPieces that were checked from the
     * initMusicialPiecesCheckListView method and displays them in a drop-down.
     *
     * @param musicalPieces checked musicialPieces
     */
    private void loadInstrumentationsFromChosenMusicalPieces(
        ObservableList<MusicalPieceEntity> musicalPieces
    ) {
        Set<MusicalPieceEntity> mp = new LinkedHashSet<>(musicalPieces);

        Set<InstrumentationEntity> inst = this.seriesManager.getInstrumentationsToMusicalPieces(mp);

        // All Intrumentations of the checked musical pieces
        ObservableSet<InstrumentationEntity> instrumentations = FXCollections.observableSet();
        instrumentations.addAll(inst);

        final StringConverter<InstrumentationEntity> instrumentationConverter =
            new StringConverter<>() {
                @Override
                public String toString(InstrumentationEntity instrumentation) {
                    return instrumentation.getName() + " - "
                        + instrumentation.getMusicalPiece().getName();
                }

                @Override
                public InstrumentationEntity fromString(String nameOfPiece) {
                    LOG.error(
                        "Return NULL: SeriesOfPerformancesController fromString von instrumentation"
                            + " wurde aufgerufen -> ist aber garnicht implementiert");
                    return null;
                }
            };
        ObservableList<InstrumentationEntity> currentItems =
            this.instrumentationCheckComboBox.getItems();

        // Füge neu dazugekommene Instrumentations in die currentlist
        for (InstrumentationEntity instrumentation : instrumentations) {
            if (!(currentItems.contains(instrumentation))) {
                this.instrumentationCheckComboBox.getItems().add(instrumentation);
            }
        }

        // Alle Instrumentations die nach dem Hinzufügen nicht in der "neuen" Liste vorhanden sind
        // werden gelöscht und handling der CheckIndeces
        Iterator<InstrumentationEntity> iterator =
            this.instrumentationCheckComboBox.getItems().iterator();
        while (iterator.hasNext()) {
            InstrumentationEntity tempInst = iterator.next();
            if (!(instrumentations.contains(tempInst))) {
                this.instrumentationCheckComboBox.getCheckModel().clearCheck(tempInst);
                // Speichere aktuelle checks in zwischenliste
                List<InstrumentationEntity> tmp = new LinkedList<>(
                    this.instrumentationCheckComboBox.getCheckModel().getCheckedItems());
                ObservableList<InstrumentationEntity> tempList =
                    FXCollections.observableArrayList(tmp);

                this.instrumentationCheckComboBox.getCheckModel().clearChecks();

                iterator.remove();

                // Durch remove wurde die size der liste verändert und somit sind alle
                // checkIndices falsche -> deshalb werden nach dem clearen die checks neu gesetzt
                for (InstrumentationEntity instrumentation : tempList) {
                    if (this.instrumentationCheckComboBox.getItems().contains(instrumentation)) {
                        this.instrumentationCheckComboBox.getCheckModel().check(instrumentation);
                    }
                }
                loadSectionInstrumentationDescriptions();
            }
        }

        /*
            Refresh BUG!
            https://github.com/controlsfx/controlsfx/issues/1004
            Hier ein selbstgebauter workaround.
         */
        if (!instrumentations.isEmpty()) {
            ObservableList<Integer> result =
                this.instrumentationCheckComboBox.getCheckModel().getCheckedIndices();
            for (Integer i : result) {
                this.instrumentationCheckComboBox.getCheckModel().check(i);
            }
        }

        this.instrumentationCheckComboBox.setConverter(instrumentationConverter);
        this.instrumentationCheckComboBox.addEventHandler(
            ComboBoxBase.ON_HIDDEN,
            event -> loadSectionInstrumentationDescriptions()
        );
    }

    /**
     * Loads all sectionInstrumentationDescriptions out of current Instrumentations of the
     * InstrumentationCheckComboBox fand displays them in a list view.
     */
    private void loadSectionInstrumentationDescriptions() {
        ObservableList<InstrumentationEntity> instrumentations =
            this.instrumentationCheckComboBox.getCheckModel().getCheckedItems();

        List<String> desc = new LinkedList<>();
        StringBuilder sb;
        for (InstrumentationEntity instrumentation : instrumentations) {
            sb = new StringBuilder();
            String prefix = "";
            for (SectionInstrumentationEntity sectionInstrumentation : instrumentation
                .getSectionInstrumentations()) {
                sb.append(prefix);
                sb.append(sectionInstrumentation.getPredefinedSectionInstrumentation());
                prefix = "/";
            }
            sb.append(" - ");
            sb.append(instrumentation.getName());
            sb.append(" - ");
            sb.append(instrumentation.getMusicalPiece().getName());
            desc.add(sb.toString());
        }

        ObservableList<String> descriptions = FXCollections.observableArrayList(desc);
        listView.setItems(descriptions);
    }

    /**
     * persists after validated input the new seriesOfPerformances in the database.
     */
    private void save() {
        if (validateInputs()) {
            this.seriesManager.save(nameOfSeries.getText(),
                new LinkedHashSet<>(
                    this.musicalPieceCheckComboBox.getCheckModel().getCheckedItems()),
                new LinkedHashSet<>(
                    this.instrumentationCheckComboBox.getCheckModel().getCheckedItems()),
                this.startingDate.getValue(), endingDate.getValue(), isTour.isSelected()
            );

            // After saving show success dialog
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(resources.getString("seriesOfPerformances.success.title"));
            successAlert.setContentText(resources
                .getString("seriesOfPerformances.success.seriesOfPerformanceSuccessfullySaved")
            );
            successAlert.getButtonTypes()
                .setAll(new ButtonType(resources.getString("global.button.ok")));

            // Get custom success icon
            ImageView icon = new ImageView("images/successIcon.png");
            icon.setFitHeight(48);
            icon.setFitWidth(48);
            successAlert.setGraphic(icon);
            successAlert.setHeaderText(resources
                .getString("seriesOfPerformances.success.header"));
            successAlert.show();
        } else {
            LOG.debug(
                "Series of Performances could not be saved");
        }
    }

    private void showErrorMessage(String errorText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(resources.getString("seriesOfPerformances.error.title"));
        alert.setContentText(errorText);
        ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
            ButtonBar.ButtonData.YES);

        alert.getButtonTypes().setAll(okButton);
        alert.showAndWait().ifPresent(type -> {
            if (type.equals(okButton)) {
                alert.close();
            }
        });
    }

    /**
     * validates whether or not:
     * -The title has no more than 45 characters.
     * -The end date is after the start date.
     * -whether the seriesOfPerformance already exists with the same name and time specification.
     *
     * @return a boolean whether the validation is successful or not
     */
    private boolean validateInputs() {
        //gibts die series of performance bereits -> wenn ja fehlermeldung

        if (this.seriesManager
            .doesSeriesAlreadyExist(this.nameOfSeries.getText(), this.startingDate.getValue(),
                endingDate.getValue())) {
            showErrorMessage(
                resources.getString(
                    "seriesOfPerformances.error.seriesAlreadyExists.message"
                )
            );
            return false;
        } else if (this.nameOfSeries.getText().length() > 45) {
            showErrorMessage(
                resources.getString(
                    "seriesOfPerformances.error.nameOfSeriesOutOfBounds.message")
            );
            return false;
        } else if (this.endingDate.getValue().isBefore(this.startingDate.getValue())) {
            showErrorMessage(
                resources.getString(
                    "seriesOfPerformances.error.endingDateBeforeStartingDate.message"
                )
            );
            return false;
        } else if (!isInstrumentationForMusicalPieceSelected()) {
            showErrorMessage(
                this.resources.getString(
                    "seriesOfPerformances.error"
                        + ".selectedMusicalPieceWithoutInstrumentation.message")
            );
            return false;
        } else {
            return true;
        }
    }

    private void cancel() {
        LOG.debug("Closing Add SOP");
        this.parentController.removeTab();
    }

    private void addModify() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(resources.getString("global.button.not.implemented.title"));
        alert.setHeaderText(resources.getString("global.button.not.implemented.header"));
        alert.setContentText(resources
            .getString("global.button.not.implemented.seriesOfPerf"));
        alert.getButtonTypes()
            .setAll(new ButtonType(resources.getString("global.button.ok")));
        alert.show();
    }

    private boolean isInstrumentationForMusicalPieceSelected() {

        for (MusicalPieceEntity m : this.musicalPieceCheckComboBox
            .getCheckModel().getCheckedItems()) {
            boolean isSelected = false;
            for (InstrumentationEntity i : m.getInstrumentations()) {
                if (this.instrumentationCheckComboBox
                    .getCheckModel().getCheckedItems().contains(i)) {
                    isSelected = true;
                }
            }
            if (!isSelected) {
                return false;
            }
        }
        return true;
    }

    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }

    @Override
    public void initializeWithParent() {
        // not needed here.
        LOG.debug("Initialized with parent");
    }

    private void checkButtonVisibility() {
        this.saveButton.setDisable(!this.start.get() || !this.end.get() || !this.name.get());
    }
}
