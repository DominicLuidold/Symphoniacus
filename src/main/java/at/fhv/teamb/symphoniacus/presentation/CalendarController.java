package at.fhv.teamb.symphoniacus.presentation;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.domain.User;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.presentation.internal.AlertHelper;
import at.fhv.teamb.symphoniacus.presentation.internal.BrutalCalendarSkin;
import at.fhv.teamb.symphoniacus.presentation.internal.Parentable;
import at.fhv.teamb.symphoniacus.presentation.internal.PublishDutyRosterEvent;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.CalendarView;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.ListSelectionView;
import org.controlsfx.control.Notifications;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.javafx.FontIcon;

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

    @FXML
    protected CalendarView calendarView;

    /**
     * Sets the calendar skin according to use case specific needs.
     */
    protected abstract void setCalendarSkin();

    /**
     * Sets the entry details callback according to use case specific needs.
     */
    protected abstract void setEntryDetailsCallback();

    /**
     * Returns a list of {@link Duty} objects based on a start and end date.
     *
     * <p>This is the default implementation that loads all duties, regardless of any state,
     * that should be overwritten in subclasses to load appropriate data.
     *
     * @return A list of Entries
     */
    protected List<Duty> loadDuties(LocalDate start, LocalDate end) {
        return new DutyManager().findAllInRange(start, end);
    }

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
    protected void fillCalendar(Calendar calendar, List<Duty> duties) {
        for (Entry<Duty> entry : this.createDutyCalendarEntries(duties)) {
            entry.setCalendar(calendar);
        }
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
