package at.fhv.orchestraria.UserInterface.Roster;

import at.fhv.orchestraria.domain.Imodel.*;
import at.fhv.orchestraria.domain.integrationInterfaces.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import org.controlsfx.control.PopOver;

import java.util.Collection;
import java.util.List;

public class PopOverMusicianController {
    /**
     * labelStart
     * labelEnd
     * labelDescription
     * labelMusicalPiece
     * labelComposer
     * labelDutyCategory
     * buttonInstrumentation
     */

    private OrchestraEntry _entry;
    private IntegratableMusician _musician;
    private PopOver _popOver;

    @FXML
    private Label labelStart;

    @FXML
    private Label labelEnd;

    @FXML
    private Label labelDescription;

    @FXML
    private Label labelMusicalPiece;

    @FXML
    private Label labelComposer;

    @FXML
    private Label labelDutyCategory;

    @FXML
    private Label labelInstrument;

    @FXML
    private Button buttonInstrumentation;

    /**
     * Creates a TreeView of the to the duty positions assigned musicians of the selected duty
     * @param event is the action of clicking the instrumentation button
     */
    @FXML
    void showAssignedMusicians(ActionEvent event) {

        TreeItem<String> root = new TreeItem<String>((_entry.getDuty()).getInstrumentationString());

        Collection<IntegratableDutyPosition> positionAll = _musician.getSection().getIntegratableDutyPositions();

        for(IntegratableDutyPosition pos: positionAll){

            if(pos.getDuty().getDutyId()== _entry.getDuty().getDutyId()){

                IntegratableMusician mc = pos.getMusician();
                String shortcut;

                IntegratableInstrumentationPosition instrPos = pos.getInstrumentationPosition();

                String [] split = instrPos.getPositionDescription().split(" : ");
                String category = split[1];

                if(mc==null){
                    shortcut = "-------\t\t-\t\t" + category;
                }else{
                    IntegratableUser user = mc.getUser();

                    //same layout with External
                    if(!(user.getFirstName().equals("EXTERNAL"))){
                        shortcut = user.getShortcut() + " \t\t-\t\t" +category;
                    }else{
                        shortcut = user.getShortcut() + " \t-\t\t" +category;
                    }
                }
                TreeItem<String> item = new TreeItem<String>(shortcut);
                root.getChildren().add(item);
            }
        }

        Popup popup = new Popup();

        //treeView expanded from begin
        root.setExpanded(true);

        //treeView must not be collapsible
        root.addEventHandler(TreeItem.branchCollapsedEvent(), new EventHandler<TreeItem.TreeModificationEvent<String>>() {
            @Override
            public void handle(TreeItem.TreeModificationEvent<String> event) {
                popup.hide();
            }
        });

        TreeView<String> tree = new TreeView<String>(root);
        tree.setFocusTraversable(false);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_RIGHT);
        vbox.getChildren().add(tree);
        vbox.setMaxHeight(_popOver.getHeight());
        vbox.setStyle("-fx-border-color: #9D9D9D;" +
                "-fx-background-color: white"
        );

        popup.getContent().add(vbox);
        popup.setX(_popOver.getX()+((_popOver.getWidth()-250)/2));
        popup.setY(_popOver.getY());
        popup.show(_popOver);
    }

    public void loadPopOverLabels(OrchestraEntry entry, IntegratableMusician musician, List<String> instrumentationPositions, PopOver popOver) {
        _entry = entry;
        _musician = musician;
        _popOver= popOver;

        String category = "";
        for(String pos: instrumentationPositions) {
            String[] split = pos.split(" : ");
            if(category.equals("")){
                category = split[1];
            }else{
                category+= ", " + split[1];
            }
        }


        //button style
        buttonInstrumentation.setStyle(
                "-fx-focus-color: transparent;" +
                        "-fx-faint-focus-color:transparent;" +
                            "-fx-border-color: #9D9D9D"
        );

        IntegratableDuty duty = entry.getDuty();
        String start = entry.getStartDateTime();
        String end = entry.getEndDateTime();

        labelStart.setText(start);
        labelEnd.setText(end);
        labelDescription.setText(duty.getDescription());
        labelMusicalPiece.setText(duty.getMusicalPieceString());
        labelComposer.setText(duty.getComposerString());
        labelDutyCategory.setText(duty.getDutyCategoryDescription());
        labelInstrument.setText(category);
        buttonInstrumentation.setText(duty.getInstrumentationString());
    }
}