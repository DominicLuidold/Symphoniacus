package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.domain.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.presentation.internal.CustomCalendarButtonEvent;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import at.fhv.teamb.symphoniacus.presentation.internal.skin.OrganizationalOfficerCalendarSkin;
import com.calendarfx.model.Calendar;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrganizationalOfficerCalendarController extends CalendarController {
    private static final Logger LOG =
        LogManager.getLogger(OrganizationalOfficerCalendarController.class);
    private TabPaneController parentController;
    private AdministrativeAssistant administrativeAssistant;
    private ResourceBundle bundle;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeWithParent() {
        // Tell CalendarFX to use custom skin
        this.setCalendarSkin();

        MainController mainController = this.getParentController().getParentController();
        DomainUserType loginUserType = mainController.getLoginUserType();
        AdministrativeAssistant aa = null;
        if (loginUserType.equals(DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT)) {
            LOG.info("Current user type is Administrative Assistant");
            this.administrativeAssistant = mainController.getCurrentAssistant();
        } else {
            LOG.info("Current user type is unsupported for this view");
            return;
        }

        // Fetch duties from database
        List<Duty> duties = this.loadDuties(DEFAULT_INTERVAL_START, DEFAULT_INTERVAL_END);

        // Create calendar
        Calendar calendar = this.createCalendar(
            bundle.getString("oo.calendar.name"),
            bundle.getString("oo.calendar.shortname"),
            false
        );

        // Fill calendar
        this.fillCalendar(calendar, duties);

        // Make Calendar ready to display
        this.calendarView.getCalendarSources().setAll(
            this.prepareCalendarSource(
                bundle.getString("oo.calendar.source"),
                calendar
            )
        );
        this.calendarView.addEventHandler(
            CustomCalendarButtonEvent.ADD_SERIES_OF_PERFORMANCES,
            event -> {
                this.parentController.addTab(TabPaneEntry.ADD_SOP);
            }
        );
        this.calendarView.addEventHandler(
            CustomCalendarButtonEvent.ADD_DUTY,
            event -> {
                this.parentController.addTab(TabPaneEntry.ADD_DUTY);
            }
        );
        LOG.debug("Initialized OrganizationalOfficerCalendarController with parent");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        LOG.debug("Initialized OrganizationalOfficerCalendarController");
    }

    @Override
    public void setCalendarSkin() {
        this.calendarView.setSkin(new OrganizationalOfficerCalendarSkin(this.calendarView));
    }

    @Override
    public void setEntryDetailsCallback() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected List<Duty> loadDuties(LocalDate start, LocalDate end) {
        return this.dutyManager.findAllInRange(
            start,
            end
        );
    }

    @Override
    public TabPaneController getParentController() {
        return this.parentController;
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }
}
