package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.presentation.internal.skin.OrganizationalOfficerCalendar;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Tell CalendarFX to use custom skin
        this.setCalendarSkin();

        // Fetch duties from database
        List<Duty> duties = this.loadDuties(DEFAULT_INTERVAL_START, DEFAULT_INTERVAL_END);

        // Create calendar
        Calendar calendar = this.createCalendar(
            "All Duties", // TODO - i18n String name convention abklären
            "D",
            true // TODO
        );

        // Fill calendar
        this.fillCalendar(calendar, duties);

        // Make Calendar ready to display
        this.calendarView.getCalendarSources().setAll(
            this.prepareCalendarSource(
                "Overview", // TODO - i18n String name convention abklären
                calendar
            )
        );
    }

    @Override
    public void setCalendarSkin() {
        this.calendarView.setSkin(new OrganizationalOfficerCalendar(this.calendarView));
    }

    @Override
    public void setEntryDetailsCallback() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected List<Duty> loadDuties(LocalDate start, LocalDate end) {
        return this.dutyManager.findAllInRange(DEFAULT_INTERVAL_START, DEFAULT_INTERVAL_END);
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }
}
