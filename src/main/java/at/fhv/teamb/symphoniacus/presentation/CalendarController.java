package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.Initializable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class CalendarController implements Initializable, Controllable {

    /**
     * Default interval start date represents {@link LocalDate#now()}.
     */
    protected static final LocalDate DEFAULT_INTERVAL_START = LocalDate.now().minusMonths(2);

    /**
     * Default interval end date represents {@link #DEFAULT_INTERVAL_START} plus two months.
     */
    protected static final LocalDate DEFAULT_INTERVAL_END = DEFAULT_INTERVAL_START.plusMonths(2);


    public abstract void setCalendarSkin();

    // Hide calendarView by clicking on Duty and load new Window for DutyScheduleView.
    public abstract void setEntryDetailsCallback();


    /**
     * Creates a {@link Calendar}.
     *
     * <p>The calendar will have a {@link Calendar.Style} assigned
     *
     * @param shortName The abbreviation of the section to use
     * @param name      The long name for the section to use
     * @param readOnly  Calendar can be marked as readOnly
     * @return A Calendar
     */
    public Calendar createCalendar(String name, String shortName, boolean readOnly) {
        Calendar calendar = new Calendar(name);
        calendar.setShortName(shortName);
        calendar.setReadOnly(readOnly);
        calendar.setStyle(Calendar.Style.STYLE1);
        return calendar;
    }

    /**
     * Fills a {@link Calendar} with {@link Entry} objects.
     *
     * @param calendar The calendar to fill
     * @param duties   A List of Duties
     */
    public void fillCalendar(Calendar calendar, List<Duty> duties) {
        for (Entry<Duty> entry : this.createDutyCalendarEntries(duties)) {
            entry.setCalendar(calendar);
        }
    }

    /**
     * Returns a list of {@link Duty} objects based on a start and enddate.
     *
     * @return A list of Entries
     */
    public List<Duty> loadDuties(LocalDate start, LocalDate end) {
        return new DutyManager().findAllInRange(start, end);
    }


    /**
     * Returns a list of CalendarFX {@link Entry} objects based on {@link DutyEntity} objects.
     *
     * @param duties A List of Duties
     * @return A list of Entries
     */
    public List<Entry<Duty>> createDutyCalendarEntries(List<Duty> duties) {
        List<Entry<Duty>> calendarEntries = new LinkedList<>();
        for (Duty duty : duties) {
            Interval interval = new Interval(
                duty.getEntity().getStart().toLocalDate(),
                duty.getEntity().getStart().toLocalTime(),
                duty.getEntity().getEnd().toLocalDate(),
                duty.getEntity().getEnd().toLocalTime()
            );
            Entry<Duty> entry = new Entry<>(duty.getTitle(), interval);
            entry.setUserObject(duty);
            calendarEntries.add(entry);
        }
        return calendarEntries;
    }

}
