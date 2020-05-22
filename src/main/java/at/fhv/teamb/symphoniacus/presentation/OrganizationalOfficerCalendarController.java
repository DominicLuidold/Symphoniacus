package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import at.fhv.teamb.symphoniacus.domain.AdministrativeAssistant;
import at.fhv.teamb.symphoniacus.presentation.internal.CustomCalendarButtonEvent;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.TabPaneEntry;
import at.fhv.teamb.symphoniacus.presentation.internal.popover.CustomDutyPopoverNode;
import at.fhv.teamb.symphoniacus.presentation.internal.skin.OrganizationalOfficerCalendarSkin;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.FindAllInRangeTask;
import com.calendarfx.model.Calendar;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrganizationalOfficerCalendarController extends CalendarController {
    private static final Logger LOG =
        LogManager.getLogger(OrganizationalOfficerCalendarController.class);
    private TabPaneController parentController;
    private AdministrativeAssistant administrativeAssistant; // not used YET - still to come
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

        if (loginUserType.equals(DomainUserType.DOMAIN_ADMINISTRATIVE_ASSISTANT)) {
            LOG.info("Current user type is Administrative Assistant");
            this.administrativeAssistant = mainController.getCurrentAssistant();
        } else {
            LOG.error("Current user type is unsupported for this view");
            return;
        }

        // Fetch duties from database
        FindAllInRangeTask task = this.loadDuties(DEFAULT_INTERVAL_START, DEFAULT_INTERVAL_END);

        task.setOnSucceeded(event -> {
            // Create calendar
            Calendar calendar = this.createCalendar(
                this.bundle.getString("oo.calendar.name"),
                this.bundle.getString("oo.calendar.shortname"),
                true
            );

            // Fill calendar
            this.fillCalendar(calendar, task.getValue());

            // Make Calendar ready to display
            this.calendarView.getCalendarSources().setAll(
                this.prepareCalendarSource(
                    bundle.getString("oo.calendar.source"),
                    calendar
                )
            );
            this.calendarView.addEventHandler(
                CustomCalendarButtonEvent.ADD_SERIES_OF_PERFORMANCES,
                addDutyEvent -> this.openNewSopTab()
            );
            this.calendarView.addEventHandler(
                CustomCalendarButtonEvent.ADD_DUTY,
                addDutyEvent -> this.parentController.addTab(TabPaneEntry.ADD_DUTY, this)
            );
            LOG.debug("Initialized OrganizationalOfficerCalendarController with parent");
        });
        new Thread(task).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.bundle = resources;
        this.calendarView.setEntryDetailsPopOverContentCallback(param ->
            new CustomDutyPopoverNode(
                param.getPopOver(),
                param.getDateControl(),
                param.getNode(),
                param.getEntry()
            )
        );
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
    protected FindAllInRangeTask loadDuties(LocalDate start, LocalDate end) {
        return new FindAllInRangeTask(
            this.dutyManager,
            start,
            end,
            this.calendarPane
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

    private void openNewSopTab() {
        Optional<Parentable<?>> childController = this.getParentController()
            .addTab(TabPaneEntry.ADD_SOP);

        childController.ifPresentOrElse(
            controller -> {
                SeriesOfPerformancesController sopController =
                    (SeriesOfPerformancesController) controller;
                sopController.getStartingDate().setValue(
                    this.calendarView.getDate()
                );
            }, () -> LOG.error("No SOP controller found")
        );
    }

}
