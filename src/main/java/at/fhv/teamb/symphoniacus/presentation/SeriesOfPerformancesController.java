package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.SeriesOfPerformancesManager;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionInstrumentationEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.controlsfx.validation.decoration.StyleClassValidationDecoration;
import org.controlsfx.validation.decoration.ValidationDecoration;

/**
 * GUI Controller responsible for creating a new Series of Performances.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class SeriesOfPerformancesController
    implements Initializable, Parentable<TabPaneController> {

    private static final Logger LOG = LogManager.getLogger(SeriesOfPerformancesController.class);
    private boolean isValid = false;
    private final ValidationSupport validationSupport = new ValidationSupport();
    private SeriesOfPerformancesManager seriesManager;
    private boolean itemChanged;
    private TabPaneController parentController;

    @FXML
    private GridPane grid;

    @FXML
    private TextField nameOfSeries;

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
        this.resources = resources;
        this.seriesManager = new SeriesOfPerformancesManager();
        listView = new ListView<>();
        grid.add(listView, 1, 3);
        instrumentationCheckComboBox
            .setTitle(resources
                .getString("seriesOfPerformances.instrumentations.placeholder"));
        initMusicialPiecesCheckListView();

        ValidationDecoration cssDecorator = new StyleClassValidationDecoration(
            "error",
            "warning"
        );
        this.validationSupport.setValidationDecorator(cssDecorator);

        this.validationSupport.registerValidator(this.nameOfSeries,
            Validator.createEmptyValidator(resources
                .getString("seriesOfPerformances.validation.name")));

        this.validationSupport.registerValidator(musicalPieceCheckComboBox,
            Validator.createEmptyValidator(resources
                .getString("seriesOfPerformances.validation.musicialPiece")));

        this.validationSupport.registerValidator(this.instrumentationCheckComboBox,
            Validator.createEmptyValidator(resources
                .getString("seriesOfPerformances.validation.instrumentation")));

      /*  this.validationSupport.registerValidator(this.startingDate,
            Validator.createEmptyValidator(resources
                .getString("seriesOfPerformances.validation.startingDate")));

        this.validationSupport.registerValidator(this.endingDate,
            Validator.createEmptyValidator(resources
                .getString("seriesOfPerformances.validation.endingDate")));*/


        //Sets Save button disabled if form is not valid
        this.validationSupport.validationResultProperty()
            .addListener((observable, oldValue, newValue) -> {
                this.isValid = newValue.getErrors().isEmpty();
                this.saveButton.setDisable(!isValid);
            });

        // Save button method
        saveButton.setOnAction(event -> save());

        // Cancel button method
        cancelButton.setOnAction(event -> cancel());

        // Add/Modify button method
        addModifyButton.setOnAction(event -> addModify());

        setUkTimeFormat();
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
        musicalPieceCheckComboBox.setConverter(musicalConverter);
        /*
        Der schlimmste Fehler meines Lebens!:
        https://stackoverflow.com/questions/30643979/checkcombobox-choices-are-empty
         */
        musicalPieceCheckComboBox.getItems().addAll(musicalPieces);

        // Changes boolean to avoid unnecessary select statements
        musicalPieceCheckComboBox.getCheckModel().getCheckedItems().addListener(
            (ListChangeListener<MusicalPieceEntity>) c -> itemChanged = true);

        // Call init Instrumentations, when Musical Pieces have been chosen
        musicalPieceCheckComboBox.addEventHandler(ComboBoxBase.ON_HIDDEN, event -> {
            if (itemChanged) {
                loadInstrumentationsFromChosenMusicalPieces(
                    musicalPieceCheckComboBox.getCheckModel().getCheckedItems());
                itemChanged = false;
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
            instrumentationCheckComboBox.getItems();

        // Füge neu dazugekommene Instrumentations in die currentlist
        for (InstrumentationEntity instrumentation : instrumentations) {
            if (!(currentItems.contains(instrumentation))) {
                instrumentationCheckComboBox.getItems().add(instrumentation);
            }
        }

        // Alle Instrumentations die nach dem Hinzufügen nicht in der "neuen" Liste vorhanden sind
        // werden gelöscht und handling der CheckIndeces
        Iterator<InstrumentationEntity> iterator =
            instrumentationCheckComboBox.getItems().iterator();
        while (iterator.hasNext()) {
            InstrumentationEntity tempInst = iterator.next();
            if (!(instrumentations.contains(tempInst))) {
                instrumentationCheckComboBox.getCheckModel().clearCheck(tempInst);
                // Speichere aktuelle checks in zwischenliste
                List<InstrumentationEntity> tmp = new LinkedList<>(
                    instrumentationCheckComboBox.getCheckModel().getCheckedItems());
                ObservableList<InstrumentationEntity> tempList =
                    FXCollections.observableArrayList(tmp);

                instrumentationCheckComboBox.getCheckModel().clearChecks();

                iterator.remove();

                // Durch remove wurde die size der liste verändert und somit sind alle
                // checkIndices falsche -> deshalb werden nach dem clearen die checks neu gesetzt
                for (InstrumentationEntity instrumentation : tempList) {
                    if (instrumentationCheckComboBox.getItems().contains(instrumentation)) {
                        instrumentationCheckComboBox.getCheckModel().check(instrumentation);
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
                instrumentationCheckComboBox.getCheckModel().getCheckedIndices();
            for (Integer i : result) {
                instrumentationCheckComboBox.getCheckModel().check(i);
            }
        }

        instrumentationCheckComboBox.setConverter(instrumentationConverter);
        instrumentationCheckComboBox.addEventHandler(
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
            instrumentationCheckComboBox.getCheckModel().getCheckedItems();

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
            seriesManager.save(nameOfSeries.getText(),
                new LinkedHashSet<>(
                    musicalPieceCheckComboBox.getCheckModel().getCheckedItems()),
                new LinkedHashSet<>(
                    instrumentationCheckComboBox.getCheckModel().getCheckedItems()),
                startingDate.getValue(), endingDate.getValue(), isTour.isSelected()
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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (seriesManager.doesSeriesAlreadyExist(nameOfSeries.getText(), startingDate.getValue(),
            endingDate.getValue())) {
            alert.setTitle(resources.getString("seriesOfPerformances.error.title"));
            alert.setContentText(resources.getString(
                "seriesOfPerformances.error.seriesAlreadyExists.message"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);

            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else if (nameOfSeries.getText().length() > 45) {
            alert.setTitle(resources.getString("seriesOfPerformances.error.title"));
            alert.setContentText(resources.getString(
                "seriesOfPerformances.error.nameOfSeriesOutOfBounds.message"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);

            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else if (endingDate.getValue().isBefore(startingDate.getValue())) {
            alert.setTitle(resources.getString("seriesOfPerformances.error.title"));
            alert.setContentText(resources.getString(
                "seriesOfPerformances.error.endingDateBeforeStartingDate.message"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);

            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else if (!isInstrumentationForMusicalPieceSelected()) {
            alert.setTitle(resources.getString("seriesOfPerformances.error.title"));
            alert.setContentText(resources.getString(
                "seriesOfPerformances.error"
                    + ".selectedMusicalPieceWithoutInstrumentation.message"));
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

    private void cancel() {
        LOG.debug("Closing Add SOP");
        this.parentController.removeTab(TabPaneEntry.ADD_SOP);
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

        for (MusicalPieceEntity m : musicalPieceCheckComboBox.getCheckModel().getCheckedItems()) {
            boolean isSelected = false;
            for (InstrumentationEntity i : m.getInstrumentations()) {
                if (instrumentationCheckComboBox.getCheckModel().getCheckedItems().contains(i)) {
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

    private void setUkTimeFormat() {
        StringConverter converter = (new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
        startingDate.setConverter(converter);
        endingDate.setConverter(converter);
    }
}
