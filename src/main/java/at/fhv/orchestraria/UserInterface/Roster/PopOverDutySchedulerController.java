package at.fhv.orchestraria.UserInterface.Roster;

import at.fhv.orchestraria.UserInterface.DutyAssignment.DutyAssignmentWindow;
import at.fhv.orchestraria.UserInterface.DutyAssignment.DutyAssignmentWindowController;
import at.fhv.orchestraria.application.DutyAssignmentController;
import at.fhv.orchestraria.domain.Imodel.IDuty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopOverDutySchedulerController {

    /**
     * labelStart
     * labelEnd
     * labelMusicalPiece
     * labelComposer
     * labelConductor
     * labelDutyCategory
     * labelInstrumentation
     */

    private OrchestraEntry _entry;
    private DutyAssignmentController _dutyAssController;
    private DutyAssignmentWindowController _dutyAssWindController;
    private AssignmentRosterThread _assignmentRosterThread;

    @FXML
    private Label labelStart;

    @FXML
    private Label labelEnd;

    @FXML
    private Label labelMusicalPiece;

    @FXML
    private Label labelComposer;

    @FXML
    private Label labelDutyCategory;

    @FXML
    private Label labelInstrumentation;



    /**
     *
     * @param event is the action of clicking the edit button
     */
    @FXML
    void editPopOver(ActionEvent event)  throws Exception{
        if(AssignmentRosterThread.isPreload) {
            DutyAssignmentWindow dutyAssiWindow = new DutyAssignmentWindow();
            Stage window = new Stage();

            dutyAssiWindow.setParameter(_dutyAssController, _entry, _assignmentRosterThread);
            dutyAssiWindow.start(window);

        }else{


        }
    }


    /**
     * Loads PopOverLabels
     * @param entry
     * @param dutyAssController
     * @param assignmentRosterThread
     */
    void loadPopOverLabels(OrchestraEntry entry, DutyAssignmentController dutyAssController, AssignmentRosterThread assignmentRosterThread){
        _entry = entry;
        _dutyAssController = dutyAssController;
        _assignmentRosterThread = assignmentRosterThread;

        IDuty duty = (IDuty)entry.getDuty();
        String start = entry.getStartDateTime();
        String end = entry.getEndDateTime();

        labelStart.setText(start);
        labelEnd.setText(end);
        labelMusicalPiece.setText(duty.getMusicalPieceString());
        labelComposer.setText(duty.getComposerString());
        labelDutyCategory.setText(duty.getDutyCategoryDescription());
        labelInstrumentation.setText(duty.getInstrumentationString());
    }

}
