package at.fhv.orchestraria.UserInterface.Roster;

import at.fhv.orchestraria.application.DutyAssignmentController;
import at.fhv.orchestraria.domain.Imodel.IDuty;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.popover.EntryPopOverContentPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.PopOver;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PopOverDutyScheduler extends EntryPopOverContentPane {
    private final static Logger LOGGER = Logger.getLogger(PopOverDutyScheduler.class.getName());


    public PopOverDutyScheduler(PopOver popOver, DateControl dateControl, Node node, Entry<?> entry, DutyAssignmentController dutyAssController, AssignmentRosterThread assignmentRosterThread) {
        super(popOver, dateControl, entry);

        //
        if (entry instanceof OrchestraEntry) {
            OrchestraEntry orchestraEntry = (OrchestraEntry) entry;

            Label musicalPiece = new Label();
            Label composer = new Label();
            IDuty duty = (IDuty)orchestraEntry.getDuty();

            musicalPiece.setText(duty.getMusicalPieceString());
            composer.setText(duty.getComposerString());

            VBox headerBox;
            //only inserts musicalPiece and composer if it has content
            if (musicalPiece.getText().equals("-") && composer.getText().equals("-")) {
                headerBox = new VBox(new Label(duty.getDutyCategoryDescription()));
            } else {
                headerBox = new VBox(new Label(duty.getDutyCategoryDescription()), musicalPiece, composer);
            }

            setHeader(headerBox);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/PopOver.fxml"));

                Parent root = loader.load();
                PopOverDutySchedulerController popOverDutySchedulerController = loader.getController();
                popOverDutySchedulerController.loadPopOverLabels(orchestraEntry, dutyAssController, assignmentRosterThread);

                setCenter(root);

            } catch (Exception ex) {
                LOGGER.log(Level.INFO, "Exception ", ex);
            }
        }
    }
}
