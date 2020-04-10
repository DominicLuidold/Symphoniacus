package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.business.DutyManager;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class CalendarController implements Initializable {
    /**
     * Default interval start date represents {@link LocalDate#now()}.
     */
    private static final LocalDate DEFAULT_INTERVAL_START = LocalDate.now();
    /**
     * Default interval end date represents {@link #DEFAULT_INTERVAL_START} plus 13 days.
     */
    private static final LocalDate DEFAULT_INTERVAL_END = DEFAULT_INTERVAL_START.plusDays(13);

    @FXML
    private CalendarView calendarView;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO - Wait for UserController and method that returns all accessible sections
        Section test = new Section();
        test.setDescription("Test Test");
        test.setSectionId(1);
        test.setSectionShortcut("T");

        Section test2 = new Section();
        test2.setDescription("Another Test");
        test2.setSectionId(2);
        test2.setSectionShortcut("B");

        List<Section> sections = new LinkedList<>(Arrays.asList(test, test2));
        calendarView.getCalendarSources().setAll(
            prepareCalendarSource(
                resources.getString("sections"),
                sections // TODO
            )
        );
    }

    /**
     * Prepares a {@link CalendarSource} by creating {@link Calendar}s and subsequently filling
     * them with {@link Entry} objects.
     *
     * @param name     Name of the CalendarSource
     * @param sections List of sections
     * @return A CalendarSource containing Calendars and Entries
     */
    public CalendarSource prepareCalendarSource(String name, List<Section> sections) {
        CalendarSource calendarSource = new CalendarSource(name);
        List<Calendar> calendars = createCalendars(sections);
        for (Calendar calendar : calendars) {
            for (Section section : sections) {
                if (calendar.getName().equals(section.getDescription())
                    && calendar.getShortName().equals(section.getSectionShortcut())
                ) {
                    fillCalendar(calendar, section);
                    calendarSource.getCalendars().add(calendar);
                }
            }
        }
        return calendarSource;
    }

    /**
     * Creates {@link Calendar}s based on specified {@link Section}s.
     *
     * <p>Each calendar will have a {@link Calendar.Style} assigned and will be marked as
     * {@code readOnly}.
     *
     * @param sections A List of sections
     * @return A List of Calendars
     */
    public List<Calendar> createCalendars(List<Section> sections) {
        List<Calendar> calendars = new LinkedList<>();
        int i = 0;
        for (Section section : sections) {
            Calendar calendar = new Calendar(section.getDescription());
            calendar.setShortName(section.getSectionShortcut());
            calendar.setReadOnly(true);
            calendar.setStyle(Calendar.Style.getStyle(i++));
            calendars.add(calendar);
        }
        return calendars;
    }

    /**
     * Fills a {@link Calendar} with {@link Entry} objects.
     *
     * @param calendar The calendar to fill
     * @param entries  A List of Entries
     */
    public void fillCalendar(Calendar calendar, List<Entry<Duty>> entries) {
        for (Entry<Duty> entry : entries) {
            entry.setCalendar(calendar);
        }
    }

    /**
     * Fills a {@link Calendar} with {@link Entry} objects based on specified {@link Section}.
     *
     * <p>Having no interval defined, the default interval start and end date (13 days from
     * {@link LocalDate#now()} are getting used.
     *
     * @param calendar The calendar to fill
     * @param section  The section used to determine required {@link Duty} objects
     * @see #DEFAULT_INTERVAL_START
     * @see #DEFAULT_INTERVAL_END
     */
    public void fillCalendar(Calendar calendar, Section section) {
        fillCalendar(calendar, createDutyCalendarEntries(
            new DutyManager().findAllInRange(
                section,
                DEFAULT_INTERVAL_START,
                DEFAULT_INTERVAL_END
            )
        ));
    }

    /**
     * Fills a {@link Calendar} with {@link Entry} objects based on supplied {@link Section} and
     * dates.
     *
     * @param calendar  The calendar to fill
     * @param section   The section used to determine which {@link Duty} objects
     * @param startDate Start date of the desired interval
     * @param endDate   End date of the desired interval
     */
    public void fillCalendar(
        Calendar calendar,
        Section section,
        LocalDate startDate,
        LocalDate endDate
    ) {
        fillCalendar(calendar, createDutyCalendarEntries(
            new DutyManager().findAllInRange(
                section,
                startDate,
                endDate
            )
        ));
    }

    /**
     * Returns a list of CalendarFX {@link Entry} objects based on {@link Duty} objects.
     *
     * @param duties A List of Duties
     * @return A list of Entries
     */
    public List<Entry<Duty>> createDutyCalendarEntries(List<Duty> duties) {
        List<Entry<Duty>> calendarEntries = new LinkedList<>();
        for (Duty duty : duties) {
            Interval interval = new Interval(
                duty.getStart().toLocalDate(),
                duty.getStart().toLocalTime(),
                duty.getEnd().toLocalDate(),
                duty.getEnd().toLocalTime()
            );
            Entry<Duty> entry = new Entry<>(duty.getDescription(), interval);
            calendarEntries.add(entry);
        }
        return calendarEntries;
    }
}
