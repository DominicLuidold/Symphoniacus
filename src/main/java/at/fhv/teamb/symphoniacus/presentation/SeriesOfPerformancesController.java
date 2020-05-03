package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.SeriesOfPerformancesManager;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionInstrumentationEntity;
import java.net.URL;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
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
public class SeriesOfPerformancesController implements Initializable {
    private static final Logger LOG = LogManager.getLogger(SeriesOfPerformancesController.class);
    private boolean isValid = false;
    private ValidationSupport validationSupport = new ValidationSupport();
    private SeriesOfPerformancesManager seriesManager;

    @FXML
    private AnchorPane pane;

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
        instrumentationCheckComboBox.setTitle("Choose your instrumentations");
        initMusicialPiecesCheckListView();

        ValidationDecoration cssDecorator = new StyleClassValidationDecoration(
            "error",
            "warning"
        );
        this.validationSupport.setValidationDecorator(cssDecorator);

        this.validationSupport.registerValidator(this.nameOfSeries,
            Validator.createEmptyValidator("A name is required."));

        this.validationSupport.registerValidator(musicalPieceCheckComboBox,
            Validator.createEmptyValidator("You must pick a musical piece."));

        this.validationSupport.registerValidator(this.instrumentationCheckComboBox,
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


        // Save button method
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                save();
            }
        });

        // Cancel button method
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cancel();
            }
        });

    }

    public void initMusicialPiecesCheckListView() {
        final ObservableList<MusicalPieceEntity> musicalPieces =
            FXCollections.observableArrayList();
        Set<MusicalPieceEntity> mp = seriesManager.getAllMusicalPieces();
        musicalPieces.addAll(mp);

        StringConverter<MusicalPieceEntity> musicalConverter =
            new StringConverter<MusicalPieceEntity>() {
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
        // Call init Instrumentations, when Musical Pieces have been chosen
        musicalPieceCheckComboBox.getCheckModel().getCheckedItems().addListener(
            new ListChangeListener<MusicalPieceEntity>() {
                @Override
                public void onChanged(Change<? extends MusicalPieceEntity> c) {
                    loadInstrumentationsFromChosenMusicalPieces(
                        musicalPieceCheckComboBox.getCheckModel().getCheckedItems());

                }
            });
    }

    private void loadInstrumentationsFromChosenMusicalPieces(
        ObservableList<MusicalPieceEntity> musicalPieces) {
        System.out.println("aufruf!!!");
        Set<MusicalPieceEntity> mp = new LinkedHashSet<>();
        mp.addAll(musicalPieces);

        Set<InstrumentationEntity> inst = this.seriesManager.getInstrumentationsToMusicalPieces(mp);
        for (InstrumentationEntity i : inst) {
            System.out.println(i.getName());
        }

        ObservableSet<InstrumentationEntity> instrumentations = FXCollections.observableSet();
        instrumentations.addAll(inst);


        StringConverter<InstrumentationEntity> instrumentationConverter =
            new StringConverter<InstrumentationEntity>() {
                @Override
                public String toString(InstrumentationEntity instrumentation) {
                    return instrumentation.getName() + " - "
                        + instrumentation.getMusicalPiece().getName();
                }
                @Override
                public InstrumentationEntity fromString(String nameOfPiece) {
                    /*
                Optional<MusicalPieceEntity> piece =
                SeriesOfPerformancesManager.getMusicalPieceFromName(nameOfPiece);
                if (piece.isPresent()) {
                    return piece.get();
                } else {
                    LOG.error(
                        "Somehow the musicial piece couldn't get found
                        by its name in the SeriesOfPerformancesController");
                    //Should never be able to get here
                    return null;
                }
            }
                 */
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
        // werden gelöscht
        instrumentationCheckComboBox.getItems().removeIf(
            currentInstrumentation -> !(instrumentations.contains(currentInstrumentation)));

        /*
            Refresh BUG!
            https://github.com/controlsfx/controlsfx/issues/1004
         */

        // TODO - wenn ein musical piece removed wird und neues ausgewählt wird
        //  -> ist die besetzung falls vorhin ausgewählt auch hier ausgewählt
        if (instrumentations.size() > 0) {
            ObservableList<Integer> result =
                instrumentationCheckComboBox.getCheckModel().getCheckedIndices();
            for (Integer i : result) {
                System.out.println(i + "test");
                instrumentationCheckComboBox.getCheckModel().check(i);
            }
        } else {
            // TODO - fix das verdammte broken indicesClearUp von checkcombobox
        }

        instrumentationCheckComboBox.setConverter(instrumentationConverter);
        instrumentationCheckComboBox.getCheckModel().getCheckedItems().addListener(
            new ListChangeListener<InstrumentationEntity>() {
                @Override
                public void onChanged(Change<? extends InstrumentationEntity> c) {
                    loadSectionInstrumentationDescriptions(
                        instrumentationCheckComboBox.getCheckModel().getCheckedItems());
                }
            });
    }

    private void loadSectionInstrumentationDescriptions(
        ObservableList<InstrumentationEntity> inst) {
        ObservableSet<InstrumentationEntity> instrumentations =
            FXCollections.observableSet();
        instrumentations.addAll(inst);

        List<String> desc = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (InstrumentationEntity instrumentation : instrumentations) {
            sb = new StringBuilder();
            String prefix = "";
            for (SectionInstrumentationEntity sectionInstrumentation : instrumentation
                .getSectionInstrumentations()) {
                sb.append(prefix + sectionInstrumentation.getPredefinedSectionInstrumentation());
                prefix = "/";
            }
            sb.append(" - " + instrumentation.getName());
            sb.append(" - " + instrumentation.getMusicalPiece().getName());
            desc.add(sb.toString());
        }

        ObservableList<String> descriptions = FXCollections.observableArrayList(desc);
        listView.setItems(descriptions);
    }

    private void save() {
        if (validateInputs()) {
            seriesManager.save(nameOfSeries.getText(),
                new LinkedHashSet<MusicalPieceEntity>(
                    musicalPieceCheckComboBox.getCheckModel().getCheckedItems()),
                new LinkedHashSet<InstrumentationEntity>(
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
            ImageView icon = new ImageView("images\\successIcon.png");
            icon.setFitHeight(48);
            icon.setFitWidth(48);
            successAlert.setGraphic(icon);
            successAlert.setHeaderText(resources.getString("seriesOfPerformances.success.header"));
            successAlert.show();
        } else {
            //TODO - warning something went wrong alert
        }
    }

    private boolean validateInputs() {
        //gibts die series of performance bereits -> wenn ja fehlermeldung
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (seriesManager.doesSeriesAlreadyExist(nameOfSeries.getText(), startingDate.getValue(),
            endingDate.getValue())) {
            alert.setTitle(resources
                .getString("seriesOfPerformances.error.title"));
            alert.setContentText(resources.getString(
                "seriesOfPerformances.error.seriesAlreadyExists.message"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);

            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                System.out.println(type);
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else if (nameOfSeries.getText().length() > 45) {
            alert.setTitle(resources
                .getString("seriesOfPerformances.error.title"));
            alert.setContentText(resources.getString(
                "seriesOfPerformances.error.nameOfSeriesOutOfBounds.message"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);

            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                System.out.println(type);
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else if (endingDate.getValue().isBefore(startingDate.getValue())) {
            alert.setTitle(resources
                .getString("seriesOfPerformances.error.title"));
            alert.setContentText(resources.getString(
                "seriesOfPerformances.error.endingDateBeforeStartingDate.message"));
            ButtonType okButton = new ButtonType(resources.getString("global.button.ok"),
                ButtonBar.ButtonData.YES);

            alert.getButtonTypes().setAll(okButton);
            alert.showAndWait().ifPresent(type -> {
                System.out.println(type);
                if (type.equals(okButton)) {
                    alert.close();
                }
            });
            return false;
        } else {
            return true;
        }
    }

    public void cancel() {
        //TODO - schnittstelle zur Schließung des Tabs
    }
}
