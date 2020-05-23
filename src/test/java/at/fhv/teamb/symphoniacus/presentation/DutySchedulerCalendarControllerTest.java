package at.fhv.teamb.symphoniacus.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

class DutySchedulerCalendarControllerTest {

    @Test
    void createCalendar_ShouldCreateCalendar() {
        // Given
        String name = "Test Section 1";
        String shortName = "TS1";

        // When
        Calendar calendar = new DutySchedulerCalendarController().createCalendar(
            name,
            shortName,
            true
        );

        // Then
        assertEquals(
            name,
            calendar.getName(),
            "Section and Calendar should have same name"
        );
        assertEquals(
            shortName,
            calendar.getShortName(),
            "Section and Calendar should have same short name"
        );
        assertTrue(calendar.isReadOnly(), "Calendar should be marked as read only");
        assertNotNull(calendar.getStyle(), "Calendar should have a style");
    }

    @Test
    void fillCalendar_ShouldFillCalendarWithEntries() {
        // Given
        DutySchedulerCalendarController controller = new DutySchedulerCalendarController();
        Calendar calendar = new Calendar();
        List<Duty> testDuties = this.prepareTestDuties();

        // When
        controller.fillCalendar(calendar, testDuties);

        // Then
        for (Duty duty : testDuties) {
            assertFalse(
                calendar.findEntries(duty.getTitle()).isEmpty(),
                "Calendar should contain Entry"
            );
            assertEquals(
                1,
                calendar.findEntries(duty.getTitle()).size(),
                "Calendar should find exactly one Entry"
            );
        }
    }

    @Test
    void prepareCalendarSource_ShouldPrepareCalendarSource() {
        // Given
        Calendar calendar = new Calendar();
        String name = "Test Source";

        // When
        CalendarSource source =
            new DutySchedulerCalendarController().prepareCalendarSource(name, calendar);

        // Then
        assertEquals(name, source.getName(), "Given name and CalendarSource name should be equal");
        assertEquals(1, source.getCalendars().size(), "CalendarSource should have one calendar");
    }

    /**
     * Prepares a List of {@link Duty} objects with test data.
     *
     * @return A List of Duties
     */
    private List<Duty> prepareTestDuties() {
        Duty testDuty1 = new Duty(new DutyEntity());
        testDuty1.getEntity().setDescription("A very descriptive title");
        testDuty1.getEntity().setStart(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        testDuty1.getEntity().setEnd(LocalDateTime.of(LocalDate.now(), LocalTime.NOON));

        Duty testDuty2 = new Duty(new DutyEntity());
        testDuty2.getEntity().setDescription("Completely random something");
        testDuty2.getEntity().setStart(
            LocalDateTime.of(
                LocalDate.now().plusDays(1),
                LocalTime.NOON
            )
        );
        testDuty2.getEntity().setEnd(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MAX));

        return new LinkedList<>(Arrays.asList(testDuty1, testDuty2));
    }
}
