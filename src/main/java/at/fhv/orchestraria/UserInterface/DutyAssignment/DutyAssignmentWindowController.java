package at.fhv.orchestraria.UserInterface.DutyAssignment;

import at.fhv.orchestraria.UserInterface.Roster.AssignmentRosterThread;
import at.fhv.orchestraria.UserInterface.Roster.OrchestraEntry;
import at.fhv.orchestraria.application.DutyAssignmentController;
import at.fhv.orchestraria.domain.Imodel.*;
import at.fhv.orchestraria.domain.model.DutyEntityC;
import at.fhv.orchestraria.domain.model.MusicianEntityC;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class DutyAssignmentWindowController {

    /**
     * Data format identifier used as means
     * of identifying the data stored on a clipboard/dragboard.
     *
     * @since JavaFX 2.0
     */
    static final DataFormat MUSICIAN_LIST = new DataFormat("MusicianList");

    private int _sectionId;

    private HashMap<JFXListView<UIMusician>, List<JFXListView<UIMusician>>> _musViewToPosViewMapping = new HashMap<>();

    private HashMap<JFXListView<UIMusician>, JFXListView<UIMusician>> _posViewToMusViewMapping = new HashMap<>();

    private LinkedList<JFXListView<UIMusician>> _musViews;

    private HashMap<JFXListView<UIMusician>, IDutyPosition> _musicianMapping = new HashMap<>();

    private Collection<JFXListView<UIMusician>> _assignedMusicianLists = new LinkedList<>();

    private Collection<JFXListView<UIMusician>> _unassignedMusicianLists = new LinkedList<>();

    private JFXListView<UIMusician> _otherUnassignedMusicianList;

    private Collection<JFXListView<UIMusician>> _otherAssignedMusicianLists = new LinkedList<>();

    private DutyAssignmentController _dutyAssController;

    private AssignmentRosterThread _assignmentRosterThread;

    private int tabNr;

    private IDuty _duty;

    private int _warnings;

    private int _errors;

    private StringBuilder _warningMessage;

    private StringBuilder _errorMessage;

    private Tab _currentHeader;

    private MusicianCellFactory cellFactory = new MusicianCellFactory();

    @FXML
    private BorderPane borderPane;

    @FXML
    private Label _musicalPieceLabel;

    @FXML
    private Label _sectionLabel;

    @FXML
    private Label _composerLabel;

    @FXML
    private Label _instrumentationLabel;

    @FXML
    private Label _pointsOfDutyLabel;

    @FXML
    private Label _pointsOfSeriesLabel;

    @FXML
    private JFXTabPane _musicalPieceTabPane;


    @FXML
    private JFXProgressBar JFXloadingBar;


    /**
     * This Method initializes the DutyAssignmentWindow.
     * First it gets the position Descriptions out of the DB then int creates the
     * positionList, MusicianList and the positionNames.
     *
     * @param dutyAsstController mainly used to get the sectionId and other functions
     * @param entry              the entry of this specific duty
     */
    public void init(DutyAssignmentController dutyAsstController, OrchestraEntry entry, AssignmentRosterThread assignmentRosterThread) {

        //JFXloadingBar.setVisible(true);
        _duty = (IDuty) entry.getDuty();
        _dutyAssController = dutyAsstController;
        _sectionId = _dutyAssController.getSectionID();
        _assignmentRosterThread = assignmentRosterThread;


        for (ISeriesOfPerformancesInstrumentation instrumentation : entry.getDuty().getSeriesOfPerformances().getISeriesOfPerformancesInstrumentations()) {
            Tab musicalPieceTab = new Tab(instrumentation.getInstrumentation().getMusicalPiece().getName());

            //createTab();
            //createHeader();
            List<String> instruments;
            List<List<String>> positions;
            HashMap<String, IDutyPosition> stringMapping = new HashMap<>();
            List<String> positionDescriptions = new ArrayList<>();


            // In Case of non musical duty
            if (entry.getDuty().getSeriesOfPerformances() == null) {
                instruments = new ArrayList<>();
                instruments.add("Any");
                positions = new ArrayList<>(1);
                List<String> positionList = new ArrayList();
                int pos = 1;
                for (IDutyPosition position : _duty.getIDutyPositions()) {
                    if (position.getSection().getSectionId() == _sectionId) {
                        positionList.add(pos + ".");
                        String posDescription =  "Any : "+ pos + ".";
                        stringMapping.put(posDescription, position);
                        positionDescriptions.add(posDescription);
                        pos++;
                    }
                }
                positions.add(positionList);
            } else {
                //In case of Musical Duties
                for (IDutyPosition dutyposition : ((IDuty)entry.getDuty()).getIDutyPositions()) {
                    //Gets only Instrumentation of this specific section
                    if (dutyposition.getInstrumentationPosition() != null
                            && dutyposition.getInstrumentationPosition().getInstrumentation().equals(instrumentation.getInstrumentation())
                            && _sectionId == dutyposition.getInstrumentationPosition().getSectionInstrumentation().getSection().getSectionId()) {
                        if (dutyposition.getInstrumentationPosition().getPositionDescription() != null) {
                            String posDescription = dutyposition.getInstrumentationPosition().getPositionDescription();
                            stringMapping.put(posDescription, dutyposition);
                            positionDescriptions.add(posDescription);
                        }
                    }
                }
                instruments = getInstruments(positionDescriptions);
                positions = getPositions(positionDescriptions);
            }


            _musViews = new LinkedList<>();
            VBox positionMusVbox;
            JFXListView<UIMusician> musView;
            JFXTabPane tabPane = new JFXTabPane();

            int maxPos = 0;
            //For loop to create Tabs of Instruments dynamically
            for (int tabNr = 0; tabNr < instruments.size(); tabNr++) {

                TextField positionsListLabel = new TextField("Position");
                TextField musiciansListLabel = new TextField("Musicians");
                positionsListLabel.setEditable(false);
                musiciansListLabel.setEditable(false);
                TextField emptyLabel = new TextField("");
                emptyLabel.setEditable(false);
                Label infoLabel = new Label("Choose a Musician and drag him to a suitable Position.");
                positionsListLabel.setMinWidth(25);
                positionsListLabel.setMaxWidth(60);
                TextArea musicianWishes = createWishTextarea();
                musicianWishes.setEditable(false);

                //Here are the Tabs beeing generated
                Tab tab = new Tab(instruments.get(tabNr));
                VBox positionNameVbox = new VBox();
                positionNameVbox.getChildren().add(positionsListLabel);
                positionNameVbox.setSpacing(0);
                musView = createMusView(musicianWishes);

                if (instruments.get(tabNr).startsWith("Other")) {
                    _otherUnassignedMusicianList = musView;
                } else {
                    _unassignedMusicianLists.add(musView);
                }

                ObservableList<UIMusician> musiciansObsList = FXCollections.observableArrayList();
                musiciansObsList.addAll(getSortedForDutyAvailableMusiciansByInstrument(instruments.get(tabNr)));

                musView.setItems(musiciansObsList);

                musView.setCellFactory(cellFactory);


            //Doubleclickfeature
            LinkedList<JFXListView<UIMusician>> linkedPosViews = new LinkedList<>();
            _musViewToPosViewMapping.put(musView, linkedPosViews);

            positionMusVbox = new VBox();
            positionMusVbox.getChildren().add(emptyLabel);

                //Method to create each Position of this Instrument Tab
                createPositionsInTabs(positions, instruments, tabNr, musicianWishes, positionNameVbox, positionMusVbox, stringMapping, maxPos, linkedPosViews, musView);

                //Method to create all the Vboxes, Hboxes and Labels
                HBox finalBox = createBoxes(positionNameVbox, positionMusVbox, musicianWishes, infoLabel, musiciansListLabel, musView);

                tab.setContent(finalBox);

                //TODO FIX THE LOADING BAR
                //Platform.runLater( new Runnable(){
                //    @Override
                //    public void run() {
                //        try {
                //             //This was updated
                //            setHeader();
                //            _tabPane.getTabs().add(tab);
                //            _tabPane.tabMinWidthProperty().bind(_tabPane.widthProperty().divide(_tabPane.getTabs().size()).subtract(2));
                //        } catch (IllegalStateException ex) {
                //            ex.printStackTrace();
                //        }
                //    }
                //});

                tabPane.getTabs().add(tab);
            }

            tabPane.tabMinWidthProperty().bind(tabPane.widthProperty().divide(tabPane.getTabs().size()).subtract(2));
            tabPane.setId("instrumentTab");

            HBox hbox = new HBox();
            hbox.getChildren().add(tabPane);
            musicalPieceTab.setContent(hbox);
            _musicalPieceTabPane.getTabs().add(musicalPieceTab);
            _musicalPieceTabPane.setId("musicalPieceTab");

            //TODO FIX THE LOADING BAR
            //Platform.runLater(new Runnable() {
            //    @Override
            //    public void run() {
            //        loadAssignedInstruments(hasEditWindowBeenFilled());
            //    }
            //});
            JFXloadingBar.setVisible(false);


            if (_currentHeader == null) {
                changeHeader(instrumentation);
                _currentHeader = musicalPieceTab;
            }
            musicalPieceTab.setOnSelectionChanged(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    if (!musicalPieceTab.equals(_currentHeader)) {
                        changeHeader(instrumentation);
                        _currentHeader = musicalPieceTab;
                    }
                }
            });
        }
        _musicalPieceTabPane.tabMinWidthProperty().bind(_musicalPieceTabPane.widthProperty().divide(_musicalPieceTabPane.getTabs().size()).subtract(2));
        setHeader();
        if(_duty.getSeriesOfPerformances()!= null){
            loadAssignedInstruments(hasEditWindowBeenFilled());
        }else{
            loadAssignedPositionsForNonMusicalDuty();
        }


    }

    public void initThread(DutyAssignmentController dutyAsstController, IDuty duty) {

        _duty = duty;
        _dutyAssController = dutyAsstController;
        _sectionId = _dutyAssController.getSectionID();
        HashMap<String, IDutyPosition> stringMapping = new HashMap<>();

//      Get instruments for tabs and position
        List<String> positionDescriptions = new ArrayList<>();
        List<String> instruments;
        List<List<String>> positions;

        if (_duty.getIDutyPositions().iterator().next().getInstrumentationPosition() == null) {
            instruments = new ArrayList<>();
            instruments.add("Any");
            positions = new ArrayList<>(1);
            List<String> positionList = new ArrayList();
            int pos = 1;
            for (IDutyPosition position : _duty.getIDutyPositions()) {
                if (position.getSection().getSectionId() == _sectionId) {
                    positionList.add(pos + ".");
                    pos++;
                }
            }
            positions.add(positionList);
        } else {
            //In case of Musical Duties
            for (IDutyPosition dutyposition : _duty.getIDutyPositions()) {
                //Gets only Instrumentation of this specific section
                if (dutyposition.getInstrumentationPosition() != null &&
                        _sectionId == dutyposition.getInstrumentationPosition().getSectionInstrumentation().getSection().getSectionId()) {
                    if (dutyposition.getInstrumentationPosition().getPositionDescription() != null) {
                        String posDescription = dutyposition.getInstrumentationPosition().getPositionDescription();
                        stringMapping.put(posDescription, dutyposition);
                        positionDescriptions.add(posDescription);
                    }
                }
            }
            instruments = getInstruments(positionDescriptions);
            positions = getPositions(positionDescriptions);
        }
        _musViews = new LinkedList<>();

        JFXListView<UIMusician> musView;


        int maxPos = 0;
        for (int tabNr = 0; tabNr < instruments.size(); tabNr++) {

            TextArea musicianWishes = createWishTextarea();
            musicianWishes.setEditable(false);

            musView = createMusView(musicianWishes);

            if (instruments.get(tabNr).startsWith("Other")) {
                _otherUnassignedMusicianList = musView;
            } else {
                _unassignedMusicianLists.add(musView);
            }

            musView.getItems().addAll(getSortedForDutyAvailableMusiciansByInstrument(instruments.get(tabNr)));


            //For loop to create each Position of this Instrument Tab
            for (int positionNr = 0; positionNr < positions.get(tabNr).size(); positionNr++) {

                if (maxPos < positionNr) {
                    maxPos = positionNr;
                }
                //create Position View

                JFXListView<UIMusician> posView = createPosView(musicianWishes);

                _musicianMapping.put(posView, stringMapping.get(instruments.get(tabNr) + " : " + positions.get(tabNr).get(positionNr)));


                if (instruments.get(tabNr).startsWith("Other")) {
                    _otherAssignedMusicianLists.add(posView);
                } else {
                    _assignedMusicianLists.add(posView);
                }
            }
        }
        loadAssignedInstruments(hasEditWindowBeenFilled());
    }


    /**
     * Creates each specifc Position in every Tab dynamically
     *
     * @param positions        All the positions
     * @param instruments      Each Insrument Tab
     * @param tabNr            Nr to locate in which Tab we are at the moment
     * @param musicianWishes   Wishes of the musicians
     * @param positionNameVbox The VBox with the Position Names
     * @param positionMusVbox  The Vbox with the assignedMusicians
     * @param stringMapping    The whole mapping of the previously saved musicians on another duty
     * @param linkedPosViews
     * @param musView
     */
    public void createPositionsInTabs(List<List<String>> positions, List<String> instruments, int tabNr,
                                      TextArea musicianWishes, VBox positionNameVbox, VBox positionMusVbox, HashMap<String, IDutyPosition> stringMapping, int maxPos, LinkedList<JFXListView<UIMusician>> linkedPosViews, JFXListView<UIMusician> musView) {
        for (int positionNr = 0; positionNr < positions.get(tabNr).size(); positionNr++) {
            if (maxPos < positionNr) {
                maxPos = positionNr;
            }
            JFXListView<UIMusician> posView = createPosView(musicianWishes);
            _musicianMapping.put(posView, stringMapping.get(instruments.get(tabNr) + " : " + positions.get(tabNr).get(positionNr)));
            if (instruments.get(tabNr).startsWith("Other")) {
                _otherAssignedMusicianLists.add(posView);
            } else {
                _assignedMusicianLists.add(posView);
            }
            positionMusVbox.getChildren().add(posView);

            //Doubleclickfeature
            linkedPosViews.add(posView);
            _posViewToMusViewMapping.put(posView, musView);

            // add the position name
            TextField tfield = new TextField(positions.get(tabNr).get(positionNr));
            tfield.setMinWidth(25);
            tfield.setMaxWidth(60);
            tfield.setAlignment(Pos.CENTER);
            tfield.setEditable(false);
            positionNameVbox.getChildren().add(tfield);

            Stage stage = (Stage) borderPane.getScene().getWindow();
            if (maxPos > 14) {
                stage.setHeight(720);
            } else if (maxPos > 10) {
                stage.setHeight(680);
            } else if (maxPos > 4) {
                stage.setHeight(600);
            }
        }
    }

    /**
     * Creates several Boxes at the end of init method and puts them together.
     *
     * @param positionNameVbox   Is the positionNameVbox created in init method
     * @param positionMusVbox    Is the positinMusVbox created in init method
     * @param musicianWishes     Are the musicianWishes created in init method
     * @param infoLabel          Is the Infolabel created in init method
     * @param musiciansListLabel Is the musicianListLabel created in init method
     * @param musView            Is the musView created in init method
     * @return One finalBox with all the other boxes in it
     */
    public HBox createBoxes(VBox positionNameVbox, VBox positionMusVbox, TextArea musicianWishes,
                            Label infoLabel, TextField musiciansListLabel, JFXListView<UIMusician> musView) {
        Label wishDescr = new Label("Wishes of the marked Musician: ");
        VBox musBox = new VBox();
        musBox.getChildren().addAll(musiciansListLabel, musView);

        HBox positionBox = new HBox();
        positionBox.setSpacing(10);
        positionBox.getChildren().addAll(positionNameVbox, positionMusVbox);

        VBox wishPositionBox = new VBox();
        wishPositionBox.setSpacing(10);
        wishPositionBox.getChildren().addAll(positionBox, wishDescr, musicianWishes);

        HBox assigningArea = new HBox();
        assigningArea.setSpacing(10);
        assigningArea.getChildren().addAll(wishPositionBox, musBox);

        // Create the VBox
        VBox borderBox = new VBox();

        // Add the Pane and The LoggingArea to the VBox
        borderBox.getChildren().addAll(infoLabel, assigningArea);
        borderBox.setSpacing(5);
        // Set the Style of the VBox
        borderBox.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: #b491a1;");

        // adding a scrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(borderBox);
        scrollPane.setPrefViewportWidth(720);

        HBox finalBox = new HBox();
        finalBox.getChildren().add(scrollPane);
        finalBox.setAlignment(Pos.CENTER);

        return finalBox;
    }


    /**
     * This method parses the Instruments out of positionDescription in InstrumentationPosition
     * i.e. Fl, Ob, Kl,
     *
     * @param positionDescriptions get from DB in table InstrumentationPosition
     * @return an List with all the different Instrumentationtypes of this section.
     */
    public List<String> getInstruments(List<String> positionDescriptions) {
        String[] tokens = positionDescriptions.get(0).split(" ");
        String currInstrument = tokens[0];
        String newInstrument;
        List<String> outputArray = new ArrayList<>();

        if (currInstrument != null) {
            outputArray.add(currInstrument);
        }
        //Iterates through positionDescription and safes each Instrument
        for (String positionDescription : positionDescriptions) {
            tokens = positionDescription.split(" ");
            newInstrument = tokens[0];
            //if new Instrument, safe old and overwrite it
            if (currInstrument != null && !currInstrument.equals(newInstrument)) {
                outputArray.add(newInstrument);
                currInstrument = newInstrument;
            }
        }
        return outputArray;
    }


    /**
     * This method parses the specific positions out of positionDescription in InstrumentationPosition
     * i.e
     * 1.Picc 2.Fl 3.Picc
     * 1.Ob 2.Ob ..
     *
     * @param positionDescriptions
     * @return an List of Lists
     */
    public List<List<String>> getPositions(List<String> positionDescriptions) {
        List<List<String>> parentList = new ArrayList<List<String>>();
        List<String> childList = new ArrayList<>();
        String[] tokens = positionDescriptions.get(0).split(" ");
        String[] posTokens;
        String currInstrument = tokens[0];
        String newInstrument;

        for (String positionDescription : positionDescriptions) {
            tokens = positionDescription.split(" ");
            newInstrument = tokens[0];

            if (newInstrument.equals(currInstrument)) {
                posTokens = positionDescription.split(" ");
                childList.add(posTokens[2]);

            } else {
                parentList.add(childList);
                childList = new ArrayList<>();
                posTokens = positionDescription.split(" ");
                childList.add(posTokens[2]);
                currInstrument = newInstrument;
            }
        }
        parentList.add(childList);
        return parentList;
    }

    /**
     * sets a Header for the DutyAssignmentWindow which contains the
     * Instrumentation, the musical piece, the conductor and the section name.
     */
    public void setHeader() {

        int pointsOfDuty;
        int pointsOfSeries;

        //Gets the right section for this assigningMusician
        String section;
        IDutyPosition dutyPosition = null;
        for (IDutyPosition dutyPositions : _duty.getIDutyPositions()) {
            if (dutyPositions.getSection().getSectionId() == _dutyAssController.getSectionID()) {
                dutyPosition = dutyPositions;
                break;
            }
        }
        if (dutyPosition != null) {
            section = dutyPosition.getSection().getDescription();
        } else {
            section = "-";
        }

        //Gets the points for duty
        pointsOfDuty = _duty.getDutyCategory().getPoints();

        //Gets the points for seriesOfPerformance
        if (_duty.getSeriesOfPerformances() != null) {
            pointsOfSeries = _duty.getSeriesOfPerformances().getTotalAmountOfSeriesOfPerformancePoints();
        } else {
            pointsOfSeries = 0;
        }
        _pointsOfSeriesLabel.setText(Integer.toString(pointsOfSeries));
        _pointsOfDutyLabel.setText(Integer.toString(pointsOfDuty));
        _sectionLabel.setText(section);

    }

    public void changeHeader(ISeriesOfPerformancesInstrumentation instrumentation) {
        String instrmtion = "-";
        String msclPc = "-";
        String composer = "-";

        for (ISectionInstrumentation sectionInstrumentation : instrumentation.getInstrumentation().getISectionInstrumentations()) {
            if (sectionInstrumentation.getSection().getSectionId() == _sectionId) {
                instrmtion = sectionInstrumentation.getPredefinedSectionInstrumentation();
                break;
            }
        }
        msclPc = instrumentation.getInstrumentation().getMusicalPiece().getName();
        composer = instrumentation.getInstrumentation().getMusicalPiece().getComposer();


        _musicalPieceLabel.setText(msclPc);
        _instrumentationLabel.setText(instrmtion);
        _composerLabel.setText(composer);
    }

    public JFXListView<UIMusician> createPosView(TextArea musicianWishes) {
        JFXListView<UIMusician> _postitionView = new JFXListView<>();
        _postitionView.setPrefSize(300, 25);
        _postitionView.setFixedCellSize(25);
        _postitionView.setCellFactory(cellFactory);
        _postitionView.setId("_positionView");

        // Add mouse event handlers for the source

        //Clears Textarea and displays musicians wishes
        _postitionView.setOnMouseClicked(event -> {
            clearlog(musicianWishes);
            int i = _postitionView.getFocusModel().getFocusedIndex();
            if (i >= 0) {
                UIMusician musician = _postitionView.getItems().get(i);

                //Doubleclick feature
                if (event.getClickCount() >= 2) {
                    shiftMusicianBack(_postitionView, musician);
                }
                clearlog(musicianWishes);
                writelog(musicianWishes, musician);
            }

        });

        _postitionView.setOnDragDetected(event -> {
            int i = _postitionView.getFocusModel().getFocusedIndex();
            if (i >= 0) {
                UIMusician musician = _postitionView.getItems().get(i);

                clearlog(musicianWishes);
                writelog(musicianWishes, musician);
            }
            dragDetected(event, _postitionView);
        });

        _postitionView.setOnDragOver(event -> dragOver(event, _postitionView));

        _postitionView.setOnDragDropped(event -> dragDroppedPosView(event, _postitionView));

        _postitionView.setOnDragDone(event -> {
            clearlog(musicianWishes);
            dragDone(event, _postitionView, true);
        });
        // disable scrolling for the positionViews
        _postitionView.addEventFilter(ScrollEvent.ANY, Event::consume);

        return _postitionView;
    }

    /**
     * Creates JFXListView for the musicians to be assigned
     *
     * @param musicianWishes
     * @return The JFXListView where to Musicians available for assigning will be stored.
     */
    public JFXListView<UIMusician> createMusView(TextArea musicianWishes) {
        JFXListView<UIMusician> musicianView = new JFXListView<>();
        musicianView.setPrefSize(300, 300);
        musicianView.setFixedCellSize(25);

        //Clears Textarea and displays musicians wishes+
        musicianView.setOnMouseClicked(event -> {

            int i = musicianView.getFocusModel().getFocusedIndex();
            if (i >= 0) {
                UIMusician musician = musicianView.getItems().get(i);

                clearlog(musicianWishes);
                writelog(musicianWishes, musician);
            }
        });

        musicianView.setOnMouseClicked(event -> {

            int i = musicianView.getFocusModel().getFocusedIndex();
            if (i >= 0) {
                UIMusician musician = musicianView.getItems().get(i);
//                  Doubeclick feature
                if (event.getClickCount() >= 2) {
                    shiftMusician(musicianView, musician);
                }
                clearlog(musicianWishes);
                writelog(musicianWishes, musician);
            }

        });

        musicianView.setOnDragDetected(event -> {
            int i = musicianView.getFocusModel().getFocusedIndex();
            if (i >= 0) {
                UIMusician musician = musicianView.getItems().get(i);

                clearlog(musicianWishes);
                writelog(musicianWishes, musician);
            }
            dragDetected(event, musicianView);
        });

        musicianView.setOnDragOver(event -> dragOver(event, musicianView));

        musicianView.setOnDragDropped(event -> dragDropped(event, musicianView));

        musicianView.setOnDragDone(event -> {
            clearlog(musicianWishes);
            dragDone(event, musicianView, false);
        });
        return musicianView;
    }

    private void shiftMusician(JFXListView<UIMusician> musicianView, UIMusician musician) {
        List<JFXListView<UIMusician>> list = _musViewToPosViewMapping.get(musicianView);
        if (list != null) {
            for (JFXListView<UIMusician> view : list) {
                if (view.getItems().size() == 0) {
                    view.getItems().add(musician);
                    if (musician.getMusician().getMusicianId() != MusicianEntityC.EXTERNAL_MUSICIAN_ID) {
                        musicianView.getItems().remove(musician);
                    }
                    break;
                }
            }

        }
    }

    private void shiftMusicianBack(JFXListView<UIMusician> positionView, UIMusician musician) {
        positionView.getItems().remove(musician);
        if (musician.getMusician().getMusicianId() != MusicianEntityC.EXTERNAL_MUSICIAN_ID) {
            _posViewToMusViewMapping.get(positionView).getItems().add(musician);
        }
    }



    private TextArea createWishTextarea() {
        TextArea musicianWishes = new TextArea();
        musicianWishes.setMaxSize(360, 50);
        return musicianWishes;
    }

    // Helper Method for Logging
    private void writelog(TextArea musicianWishes, UIMusician musician) {
        Collection<String> wishes = getAllMusicianWishes(musician);
        for (String wish : wishes) {
            musicianWishes.appendText(wish + "\n");
        }
    }

    private void clearlog(TextArea musicianWishes) {
        musicianWishes.clear();
    }

    private ObservableList<UIMusician> getSortedForDutyAvailableMusiciansByInstrument(String instrAbbr) {
        ObservableList<UIMusician> wrappedMusicians = FXCollections.<UIMusician>observableArrayList();
        Collection<IMusician> musicians;
        if (instrAbbr.equals("Other") || instrAbbr.equals("Any")) {
            musicians = _dutyAssController.getSortedForDutyAvailableMusicians(_duty);
        } else {
            if (instrAbbr.equals("Vl1") || instrAbbr.equals("Vl2")) {
                instrAbbr = "Vl";
            }
            musicians = _dutyAssController.getSortedForDutyAvailableMusiciansByInstrument(_duty, instrAbbr);
        }
        for (IMusician musician : musicians) {
            wrappedMusicians.add(new UIMusician(musician, _duty));
        }
        return wrappedMusicians;
    }

    private void dragDetected(MouseEvent event, JFXListView<UIMusician> JFXListView) {
        // Make sure at least one item is selected
        int selectedCount = JFXListView.getSelectionModel().getSelectedIndices().size();

        if (selectedCount == 0) {
            event.consume();
            return;
        }

        // Initiate a drag-and-drop gesture
        Dragboard dragboard = JFXListView.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        // Put the the selected items to the dragboard
        List<UIMusician> selectedItems = this.getSelectedMusician(JFXListView);

        ClipboardContent content = new ClipboardContent();
        content.put(MUSICIAN_LIST, selectedItems);

        dragboard.setContent(content);
        event.consume();
    }

    private void dragOver(DragEvent event, JFXListView<UIMusician> JFXListView) {
        // If drag board has an ITEM_LIST and it is not being dragged
        // over itself, we accept the MOVE transfer mode
        Dragboard dragboard = event.getDragboard();

        if (event.getGestureSource() != JFXListView && dragboard.hasContent(MUSICIAN_LIST)) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    @SuppressWarnings("unchecked")
    private void dragDropped(DragEvent event, JFXListView<UIMusician> JFXListView) {
        boolean dragCompleted = false;

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasContent(MUSICIAN_LIST)) {
            List<UIMusician> list = (ArrayList<UIMusician>) dragboard.getContent(MUSICIAN_LIST);
            for (UIMusician musician : list) {
                if ((musician.getMusician().getMusicianId() != MusicianEntityC.EXTERNAL_MUSICIAN_ID) && (!JFXListView.getItems().contains(musician))) {
                    JFXListView.getItems().add(musician);
                }
            }


            // Data transfer is successful
            dragCompleted = true;
        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
    }

    @SuppressWarnings("unchecked")
    private void dragDroppedPosView(DragEvent event, JFXListView<UIMusician> JFXListView) {
        boolean dragCompleted = false;

        // Transfer the data to the target
        Dragboard dragboard = event.getDragboard();

        if (dragboard.hasContent(MUSICIAN_LIST)) {
            List<UIMusician> list = (ArrayList<UIMusician>) dragboard.getContent(MUSICIAN_LIST);
            if (getSizeOfJFXListView(JFXListView) < 1) {
                JFXListView.getItems().addAll(list);
                dragCompleted = true;
            }

            // Data transfer is successful
        }

        // Data transfer is not successful
        event.setDropCompleted(dragCompleted);
        event.consume();
    }

    private int getSizeOfJFXListView(JFXListView<UIMusician> JFXListView) {
        JFXListView myList = JFXListView;
        int count = 0;
        Collection<IMusician> collection = myList.getItems();
        for (IMusician ms : collection) {
            count++;
        }
        return count;
    }

    private void dragDone(DragEvent event, JFXListView<UIMusician> JFXListView, boolean isPositionView) {
        // Check how data was transfered to the target
        // If it was moved, clear the selected items
        TransferMode tm = event.getTransferMode();
        if (tm == TransferMode.MOVE) {
            removeSelectedMusician(JFXListView, isPositionView);
        }
        event.consume();
    }

    private List<UIMusician> getSelectedMusician(JFXListView<UIMusician> JFXListView) {
        // Return the list of selected Musician in an ArratyList, so it is
        // serializable and can be stored in a Dragboard.
        return new ArrayList<>(JFXListView.getSelectionModel().getSelectedItems());
    }

    private void removeSelectedMusician(JFXListView<UIMusician> JFXListView, boolean isPositionView) {
        // Get all selected Musician in a separate list to avoid the shared list issue
        List<UIMusician> selectedList = new ArrayList<>();

        if (!isPositionView) {
            for (UIMusician musician : JFXListView.getSelectionModel().getSelectedItems()) {
                if (musician.getMusician().getMusicianId() != MusicianEntityC.EXTERNAL_MUSICIAN_ID)
                    selectedList.add(musician);
            }
        } else {
            for (UIMusician musician : JFXListView.getSelectionModel().getSelectedItems()) {
                selectedList.add(musician);
            }
        }

        // Clear the selection
        JFXListView.getSelectionModel().clearSelection();
        // Remove items from the selected list
        JFXListView.getItems().removeAll(selectedList);
    }

    /**
     * Gets all wishes of this musician.
     *
     * @param uiMusician the musician to get all wishes from
     * @return all wishes of this particular musician
     */
    public Collection<String> getAllMusicianWishes(UIMusician uiMusician) {
        IMusician musician = uiMusician.getMusician();
        Collection<String> wishes = new LinkedList<>();
        //Positive Wishes
        if (musician.getPositiveWish(_duty).isPresent()) {
            wishes.add("+ Wish: " + musician.getPositiveWish(_duty).get().getDescription());
        }
        //Negative Duty Wishes
        if (musician.getNegativeDutyWish(_duty).isPresent()) {
            wishes.add("- DutyWish: " + musician.getNegativeDutyWish(_duty).get().getDescription());
        }
        //Negative Date Wishes
        if (musician.getNegativeDateWish(_duty).isPresent()) {
            wishes.add("- DateWish: " + musician.getNegativeDateWish(_duty).get().getDescription());
        }
        return wishes;
    }

    /**
     * Validates and checks some information about the assigned musicians.
     *
     * @return the warnings as a Result or if there are errors returns Optional.Empty()
     */
    public Optional<ButtonType> validateAssignment() {
        LocalDateTime date = _duty.getStart();

        Alert warningAlert = new Alert(Alert.AlertType.CONFIRMATION);
        warningAlert.setTitle("Warning Dialog");
        warningAlert.setHeaderText("Are you sure you want to save the assignment?");
        _warningMessage = new StringBuilder();
        _warnings = 0;

        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Error Dialog");
        errorAlert.setHeaderText("You have to resolve all errors before beeing able to save your Instrumentation.");
        _errorMessage = new StringBuilder();
        _errors = 0;

        Collection<IMusician> imusicians = getAllAssignedMusicians();

        checkPrincipals(imusicians);
        checkPositions(imusicians);
        checkPoints(imusicians);
        checkBrass(imusicians, date.toLocalDate());
        checkMaxDuties(imusicians, date.toLocalDate());
        checkVacation(imusicians, date.toLocalDate());
        checkOtherEvents(imusicians);
        checkTour(imusicians);

        //Output
        StringBuilder alertHeader = new StringBuilder();
        Optional<ButtonType> result;
        if (_errors > 0) {
            errorAlert.setContentText(_errorMessage.toString());
            errorAlert.showAndWait();
            return Optional.empty();
        } else if (_warnings > 0) {
            alertHeader.append(_warnings).append(" warnings\n\n");
            warningAlert.setContentText(alertHeader + _warningMessage.toString());
            result = warningAlert.showAndWait();
            return result;
        } else {
            ButtonType okButton = ButtonType.OK;
            result = Optional.of(okButton);
            return result;
        }
    }


    /**
     * Checks if the min. amount of section principals is assigned for this duty.
     *
     * @param imusicians All the assigned musicians
     */
    private void checkPrincipals(Collection<IMusician> imusicians) {
        if (_duty.getSectionInstrumentation(_dutyAssController.getSectionID()) != null && !_dutyAssController.areEnoughSectionPrincipalsAssigned(imusicians,
                _duty.getSectionInstrumentation(_dutyAssController.getSectionID()).getSection(), _duty.getDutyCategoryDescription())) {
            _warnings++;
            _warningMessage.append("- There are not enough 'Stimmfuehrer' assigned!\n");
        }
    }

    /**
     * Checks if all of the available positions are assigned.
     *
     * @param imusicians All the assigned musicians
     */
    private void checkPositions(Collection<IMusician> imusicians) {
        int countPositions = 0;
        for (JFXListView JFXListView : _assignedMusicianLists) {
            if (JFXListView.getItems().isEmpty()) {
                countPositions++;
            }
        }
        if (countPositions > 0) {
            _warnings++;
            if (countPositions == 1) {
                _warningMessage.append("- You didn't assign " + countPositions + " position!\n");
            } else {
                _warningMessage.append("- You didn't assign " + countPositions + " positions!\n");
            }
        }
    }

    /**
     * Checks if musicians with to much points are assigned.
     *
     * @param imusicians All the assigned musicians
     */
    private void checkPoints(Collection<IMusician> imusicians) {
        int countPoints = 0;
        for (IMusician imusician : imusicians) {
            if (imusician.getPointsOfMonth(_duty.getStart().toLocalDate()) + _duty.getDutyCategory().getPoints() >= imusician.getRequiredPointsOfMonth(_duty.getStart().toLocalDate())) {
                countPoints++;
            }
        }
        if (countPoints > 0) {

            if (countPoints <= 3) {
                for (IMusician imusician : imusicians) {
                    if (imusician.getPointsOfMonth(_duty.getStart().toLocalDate()) >= imusician.getRequiredPointsOfMonth(_duty.getStart().toLocalDate())) {
                        _warnings++;
                        _warningMessage.append("- " + imusician.getUser().getShortcut() + " has already reached the max amount of points for this month!\n");
                    }
                }
            } else {
                _warnings++;
                _warningMessage.append("- " + countPoints + " assigned musicians have already reached the max amount of points for this month!\n");
            }
        }
    }

    /**
     * Checks if the brass are only assigned to max 2 duties per day.
     *
     * @param imusicians All the assigned musicians
     * @param date the date on which the duty takes place
     */
    private void checkBrass(Collection<IMusician> imusicians, LocalDate date) {
        int countBrass = 0;
        for (IMusician imusician : imusicians) {
            if (imusician.hasBeenAssignedToMaxAmountOfDutiesOnDate(date)) {
                countBrass++;
            }
        }
        if (countBrass > 0) {
            if (countBrass <= 3) {
                for (IMusician imusician : imusicians) {
                    if (imusician.hasBeenAssignedToMaxAmountOfDutiesOnDate(date)) {
                        _warnings++;
                        _warningMessage.append("- " + imusician.getUser().getShortcut() + " has already been assigned at 2 duties on this day!\n");
                    }
                }
            } else {
                _warnings++;
                _warningMessage.append("- " + countBrass + " of the assigned brass are already assigned at 2 duties on this day!\n");
            }
        }
    }

    /**
     * Checks if any of the assigned musicians has already reached the max
     * amount of evening duties this month.
     *
     * @param imusicians All the assigned musicians
     * @param date       the date on which the duty takes place
     */
    private void checkMaxDuties(Collection<IMusician> imusicians, LocalDate date) {
        int countEveningDuties = 0;
        if (_duty.getTimeOfDay().equals(DutyEntityC.EVENING_DUTY_NAME)) {
            for (IMusician imusician : imusicians) {
                if (imusician.getCountOfEveningDutiesOfMonth(date) > imusician.getMaxAmountOfEveningDuties()) {
                    countEveningDuties++;
                }
            }
        }
        if (countEveningDuties > 0) {
            if (countEveningDuties <= 3) {
                for (IMusician imusician : imusicians) {
                    if (imusician.getCountOfEveningDutiesOfMonth(date) > imusician.getMaxAmountOfEveningDuties()) {
                        _warnings++;
                        _warningMessage.append("- " + imusician.getUser().getShortcut() + " has already reached the max amount of evening duties this month!\n");
                    }
                }
            } else {
                _warnings++;
                _warningMessage.append("- " + countEveningDuties + " have already reached the max amount of evening duties this month!\n");
            }
        }
    }

    /**
     * Checks if one of the assigned musicians is on holiday at this date.
     * (Only makes sense when previous instrumentation has
     * been loaded because otherwise musicians who are on
     * vacation are not even able for assignment)
     *
     * @param imusicians All the assigned musicians
     * @param date       the date on which the duty takes place
     */
    private void checkVacation(Collection<IMusician> imusicians, LocalDate date) {
        int countVacation = 0;
        for (IMusician imusician : imusicians) {
            if (imusician.isOnVacationAtDate(date)) {
                countVacation++;
            }
        }
        if (countVacation > 0) {
            if (countVacation <= 3) {
                for (IMusician imusician : imusicians) {
                    if (imusician.isOnVacationAtDate(date)) {
                        _errors++;
                        _errorMessage.append("- " + imusician.getUser().getShortcut() + " is on vacation at this date!\n");
                    }
                }
            } else {
                _errors++;
                _errorMessage.append("- " + countVacation + " of the assigned musicians are on vacation at this date!\n");
            }
        }
    }


    private void checkTour(Collection<IMusician> imusicians) {
        int countTour = 0;
        for (IMusician imusician : imusicians) {
            if (imusician.isOnTourAtDate(_duty)) {
                countTour++;
            }
        }
        if (countTour > 0) {
            if (countTour <= 3) {
                for (IMusician imusician : imusicians) {
                    if (imusician.isOnTourAtDate(_duty)) {
                        _errors++;
                        _errorMessage.append("- " + imusician.getUser().getShortcut() + " is on tour at this date!\n");
                    }
                }
            } else {
                _errors++;
                _errorMessage.append("- " + countTour + " of the assigned musicians are on tour at this date!\n");
            }
        }
    }

    private void checkOtherEvents(Collection<IMusician> imusicians) {
        int countOtherEvent = 0;
        for (IMusician imusician : imusicians) {
            if (imusician.isOnEventAtDate(_duty)) {
                countOtherEvent++;
            }
        }
        if (countOtherEvent > 0) {
            if (countOtherEvent <= 3) {
                for (IMusician imusician : imusicians) {
                    if (imusician.isOnEventAtDate(_duty)) {
                        _errors++;
                        _errorMessage.append("- " + imusician.getUser().getShortcut() + " is on another event at this date!\n");
                    }
                }
            } else {
                _errors++;
                _errorMessage.append("- " + countOtherEvent + " of the assigned musicians are on another event at this date!\n");
            }
        }
    }

    /**
     * Gets all at the moment assigned musicians to validate whether they can be saved or not
     *
     * @return A collection of all at the moment assigned musicians
     */
    public Collection<IMusician> getAllAssignedMusicians() {
        Collection<IMusician> imusicians = new LinkedList<>();
        for (JFXListView<UIMusician> uiMusicianJFXListView : _musicianMapping.keySet()) {
            for (UIMusician uiMusician : uiMusicianJFXListView.getItems()) {
                imusicians.add(uiMusician.getMusician());
            }
        }
        return imusicians;
    }

    /**
     * This Method saves the assigned duty.
     *
     * @param event Triggers by mouseclick on button
     */
    @FXML
    public void saveMusiciansAssignment(ActionEvent event) {
        Optional<ButtonType> result = validateAssignment();
        if (result.isPresent() && result.get().equals(ButtonType.OK)) {

            // user chose OK
            HashMap<IDutyPosition, IMusician> mapping = new HashMap<>(_assignedMusicianLists.size());
            for (JFXListView<UIMusician> position : _assignedMusicianLists) {
                if (!position.getItems().isEmpty()) {
                    IMusician musician = position.getItems().get(0).getMusician();
                    mapping.put(_musicianMapping.get(position), musician);
                }else{
                    mapping.put(_musicianMapping.get(position), null);
                }
            }
            if (_otherAssignedMusicianLists != null) {
                for (JFXListView<UIMusician> position : _otherAssignedMusicianLists) {
                    if (!position.getItems().isEmpty()) {
                        IMusician musician = position.getItems().get(0).getMusician();
                        mapping.put(_musicianMapping.get(position), musician);
                    }
                }
            }

            _dutyAssController.assignDutyTo(_duty, mapping);
            _assignmentRosterThread.setNewEntryStatus(_duty);

            //Close window
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    /**
     * This Method gets (ifExists) the last assigned duty of this
     * SeriesOfPerformances and loads it in the selected duty
     *
     * @param event Triggers by mouseclick on button
     */
    @FXML
    public void loadMusiciansAssignment(ActionEvent event) {
        Optional<HashMap<IInstrumentationPosition, IMusician>> assignedMapOptional = _dutyAssController.getAlreadyMappedSimilarDuty(_duty);
        if (assignedMapOptional.isPresent()) {
            loadAssignedInstruments(assignedMapOptional.get());
        }
    }

    private void loadAssignedPositionsForNonMusicalDuty() {
        for(Map.Entry<JFXListView<UIMusician>, IDutyPosition> entry : _musicianMapping.entrySet()){
            if(entry.getValue()!= null) {
                IMusician mus = entry.getValue().getMusician();
                if (mus != null) {
                    entry.getKey().getItems().add(new UIMusician(mus, _duty));
                    if(mus.getMusicianId()!= MusicianEntityC.EXTERNAL_MUSICIAN_ID) {
                        for (ListView<UIMusician> musView : _unassignedMusicianLists) {
                            for (UIMusician musician : musView.getItems()) {
                                if (musician.getMusician().equals(mus)) {
                                    musView.getItems().remove(musician);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void loadAssignedInstruments(Map<IInstrumentationPosition, IMusician> assignedMap) {
        for (JFXListView<UIMusician> posView : _musicianMapping.keySet()) {

            IDutyPosition currentDutyPosition = _musicianMapping.get(posView);
            if (currentDutyPosition != null) {
                IInstrumentationPosition key = currentDutyPosition.getInstrumentationPosition();
                IMusician alreadyAssignedmusician = assignedMap.get(key);

                if (alreadyAssignedmusician != null && posView.getItems().isEmpty()) {
                    if (alreadyAssignedmusician.getMusicianId() != MusicianEntityC.EXTERNAL_MUSICIAN_ID) {
                        if (currentDutyPosition.getInstrumentationPosition() != null
                                && currentDutyPosition.getInstrumentationPosition().getPositionDescription().startsWith("Other")) {
                            for (UIMusician musician : _otherUnassignedMusicianList.getItems()) {
                                if (musician.getMusician().getMusicianId() != MusicianEntityC.EXTERNAL_MUSICIAN_ID
                                        && musician.getMusician().equals(alreadyAssignedmusician)) {
                                    _otherUnassignedMusicianList.getItems().remove(musician);
                                    break;
                                }
                            }
                        } else {
                            for (JFXListView<UIMusician> musView : _unassignedMusicianLists) {
                                for (UIMusician musician : musView.getItems()) {
                                    if (musician.getMusician().equals(alreadyAssignedmusician)) {
                                        musView.getItems().remove(musician);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    posView.getItems().add(new UIMusician(alreadyAssignedmusician, _duty));
                }
            }
        }
    }

    public Map<IInstrumentationPosition, IMusician> hasEditWindowBeenFilled() {
        Collection<IDutyPosition> dutypositions = _duty.getIDutyPositions();
        HashMap<IInstrumentationPosition, IMusician> instrumentationPositionToMusician = new HashMap<>();
        for (IDutyPosition idp : dutypositions) {
            if (idp.getSection().getSectionId() == _sectionId && idp.getMusician() != null) {
                instrumentationPositionToMusician.put(idp.getInstrumentationPosition(), idp.getMusician());
            }
        }
        return instrumentationPositionToMusician;
    }


    public void hideLoadingBar() {
        JFXloadingBar.setVisible(false);
    }
}

