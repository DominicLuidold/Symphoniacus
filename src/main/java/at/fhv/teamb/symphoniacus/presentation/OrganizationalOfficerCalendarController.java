package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.presentation.internal.skin.OrganizationalOfficerCalendar;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.FindAllInRangeTask;
import com.calendarfx.model.Calendar;
import java.net.URL;
import java.time.LocalDate;
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
        FindAllInRangeTask task = this.loadDuties(DEFAULT_INTERVAL_START, DEFAULT_INTERVAL_END);
        new Thread(task).start();

        task.setOnSucceeded(event -> {
            // Create calendar
            Calendar calendar = this.createCalendar(
                resources.getString("oo.calendar.name"),
                resources.getString("oo.calendar.shortname"),
                false
            );

            // Fill calendar
            this.fillCalendar(calendar, task.getValue());

            // Make Calendar ready to display
            this.calendarView.getCalendarSources().setAll(
                this.prepareCalendarSource(
                    resources.getString("oo.calendar.source"),
                    calendar
                )
            );
        });
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
    protected FindAllInRangeTask loadDuties(LocalDate start, LocalDate end) {
        return new FindAllInRangeTask(
            this.dutyManager,
            DEFAULT_INTERVAL_START,
            DEFAULT_INTERVAL_END,
            this.calendarPane
        );
    }

    @Override
    public void setParentController(TabPaneController controller) {
        this.parentController = controller;
    }
}
