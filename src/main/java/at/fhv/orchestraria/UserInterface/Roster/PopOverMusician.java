package at.fhv.orchestraria.UserInterface.Roster;

import at.fhv.orchestraria.UserInterface.Login.LoginWindowController;
import at.fhv.orchestraria.domain.Imodel.IDutyPosition;
import at.fhv.orchestraria.domain.Imodel.IMusician;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableDutyPosition;
import at.fhv.orchestraria.domain.integrationInterfaces.IntegratableMusician;
import com.calendarfx.model.Entry;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.popover.EntryPopOverContentPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import org.controlsfx.control.PopOver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PopOverMusician extends EntryPopOverContentPane {

    public PopOverMusician(PopOver popOver, DateControl dateControl, Node node, Entry<?> entry) {
        super(popOver, dateControl, entry);

        if (entry instanceof OrchestraEntry) {
            OrchestraEntry orchestraEntry = (OrchestraEntry) entry;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PopOverMusician.fxml"));

            try {
                Parent root = loader.load();
                PopOverMusicianController popOverMusician = loader.getController();

                IntegratableMusician musician = (IntegratableMusician) LoginWindowController.getLoggedInUser().getMusician();

                List<String> instrumentationPositions = new LinkedList<>();
                for(IntegratableDutyPosition pos : musician.getIntegratableDutyPositions()){
                    //If different Instruments are played on the same Event.
                    if(orchestraEntry.getDuty().equals(pos.getDuty())
                        && !instrumentationPositions.contains(pos.getInstrumentationPosition().getPositionDescription())){
                        instrumentationPositions.add(pos.getInstrumentationPosition().getPositionDescription());
                    }
                }

                //param: musician --> loggedInMusician, OrchestraEntry --> selected duty
                popOverMusician.loadPopOverLabels(orchestraEntry, musician, instrumentationPositions, getPopOver());

                setHeader(null);
                setCenter(root);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
