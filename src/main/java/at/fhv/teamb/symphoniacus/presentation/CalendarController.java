package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.tasks.LoadingAnimationTask;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * This controller is responsible for handling CalendarFX related actions such as
 * creating Calendar {@link Entry} objects, filling a {@link Calendar} and preparing it
 * for display.
 *
 * @author Dominic Luidold
 */
public abstract class CalendarController implements Initializable, Parentable<TabPaneController> {
    /**
     * Default interval start date represents {@link LocalDate#now()}.
     */
    protected static final LocalDate DEFAULT_INTERVAL_START = LocalDate.now().minusMonths(2);

    /**
     * Default interval end date represents {@link LocalDate#now()} plus two months.
     */
    protected static final LocalDate DEFAULT_INTERVAL_END = LocalDate.now().plusMonths(2);

    protected DutyManager dutyManager;

    @FXML
    protected AnchorPane calendarPane;

    @FXML
    protected CalendarView calendarView;

    protected Calendar calendar;

    public CalendarController() {
        this.dutyManager = new DutyManager();
    }

    /**
     * Sets the calendar skin according to use case specific needs.
     */
    protected abstract void setCalendarSkin();

    /**
     * Sets the entry details callback according to use case specific needs.
     */
    protected abstract void setEntryDetailsCallback();

    /**
     * Returns a task containing the requested {@link Duty} objects.
     *
     * @param start A LocalDate representing the start
     * @param end   A LocalDate representing the end
     * @return A subclass of {@link LoadingAnimationTask}
     */
    protected abstract LoadingAnimationTask<?> loadDuties(LocalDate start, LocalDate end);

    /**
     * Creates a {@link Calendar}.
     *
     * <p>The calendar will have a {@link Calendar.Style} assigned
     *
     * @param name      The long name for the section to use
     * @param shortName The abbreviation of the section to use
     * @param readOnly  Indicator whether calendar should be read only
     * @return A Calendar
     */
    protected Calendar createCalendar(String name, String shortName, boolean readOnly) {
        Calendar calendar = new Calendar(name);
        calendar.setShortName(shortName);
        calendar.setReadOnly(readOnly);
        calendar.setStyle(Calendar.Style.STYLE1);
        this.calendar = calendar;
        return calendar;
    }

    /**
     * Fills a {@link Calendar} with {@link Entry} objects.
     *
     * @param calendar The calendar to fill
     * @param duties   A List of Duties
     */
    protected void fillCalendar(Calendar calendar, List<Duty> duties) {
        for (Entry<Duty> entry : this.createDutyCalendarEntries(duties)) {
            entry.setCalendar(calendar);
        }
    }

    /**
     * Add a new Duty as Entry to the Calendar.
     *
     * @param duty to add to Calendar
     */
    protected void addDuty(Duty duty) {
        this.calendar.addEntry(createDutyCalendarEntry(duty));
    }

    /**
     * Prepares a {@link CalendarSource} by filling it with a {@link Calendar}.
     *
     * @param name     The name of the CalendarSource
     * @param calendar The calendar to use
     * @return A CalendarSource
     */
    protected CalendarSource prepareCalendarSource(String name, Calendar calendar) {
        CalendarSource calendarSource = new CalendarSource(name);
        calendarSource.getCalendars().add(calendar);
        return calendarSource;
    }

    /**
     * Returns a list of CalendarFX {@link Entry} objects based on {@link DutyEntity} objects.
     *
     * @param duties A List of Duties
     * @return A list of Entries
     */
    private List<Entry<Duty>> createDutyCalendarEntries(List<Duty> duties) {
        List<Entry<Duty>> calendarEntries = new LinkedList<>();
        for (Duty duty : duties) {
            calendarEntries.add(createDutyCalendarEntry(duty));
        }
        return calendarEntries;
    }

    /**
     * Returns a CalendarFX {@link Entry} object of a Duty.
     *
     * @param duty to convert to Entry
     * @return Entry of the given Duty
     */
    private Entry<Duty> createDutyCalendarEntry(Duty duty) {
        Interval interval = new Interval(
            duty.getEntity().getStart().toLocalDate(),
            duty.getEntity().getStart().toLocalTime(),
            duty.getEntity().getEnd().toLocalDate(),
            duty.getEntity().getEnd().toLocalTime()
        );
        Entry<Duty> entry = new Entry<>(duty.getTitle(), interval);
        entry.setUserObject(duty);
        return entry;
    }
}
