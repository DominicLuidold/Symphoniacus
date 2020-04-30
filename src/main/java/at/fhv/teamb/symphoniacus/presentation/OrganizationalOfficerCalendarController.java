package at.fhv.teamb.symphoniacus.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrganizationalOfficerCalendarController extends CalendarController {
    private static final Logger LOG =
        LogManager.getLogger(OrganizationalOfficerCalendarController.class);
    private TabPaneController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void setCalendarSkin() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void setEntryDetailsCallback() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }
}
