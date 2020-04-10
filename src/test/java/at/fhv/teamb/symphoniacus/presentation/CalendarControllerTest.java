package at.fhv.teamb.symphoniacus.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Entry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;

class CalendarControllerTest {

    @Test
    void createDutyCalendarEntries_ShouldCreateEntries() {
        // Given
        List<Duty> duties = prepareTestDuties();

        // When
        List<Entry<Duty>> entries = new CalendarController().createDutyCalendarEntries(duties);

        // Then
        assertEquals(duties.size(), entries.size(), "Amount of Duties and Entries should be equal");
        for (int i = 0; i < duties.size(); i++) {
            assertEquals(
                duties.get(i).getDescription(),
                entries.get(i).getTitle(),
                "Duty and Entry should have same description"
            );
            assertEquals(
                duties.get(i).getStart(),
                entries.get(i).getStartAsLocalDateTime(),
                "Duty and Entry should have same start time/day"
            );
            assertEquals(
                duties.get(i).getEnd(),
                entries.get(i).getEndAsLocalDateTime(),
                "Duty and Entry should have same end time/day"
            );
        }
    }

    @Test
    void fillCalendar_ShouldFillCalendarWithEntries() {
        // Given
        CalendarController controller = new CalendarController();
        Calendar calendar = new Calendar();
        List<Entry<Duty>> entries = controller.createDutyCalendarEntries(prepareTestDuties());

        // When
        controller.fillCalendar(calendar, entries);

        // Then
        for (Entry<Duty> entry : entries) {
            assertFalse(
                calendar.findEntries(entry.getTitle()).isEmpty(),
                "Calendar should contain Entry"
            );
            assertEquals(
                calendar.findEntries(entry.getTitle()).size(),
                1,
                "Calendar should find exactly one Entry"
            );
        }
    }

    /**
     * Prepares a List of {@link Duty} objects with test data.
     *
     * @return A List of Duties
     */
    private List<Duty> prepareTestDuties() {
        Duty testDuty1 = new Duty();
        testDuty1.setDescription("A very descriptive title");
        testDuty1.setStart(LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT));
        testDuty1.setEnd(LocalDateTime.of(LocalDate.now(), LocalTime.NOON));

        Duty testDuty2 = new Duty();
        testDuty2.setDescription("Completely random something");
        testDuty2.setStart(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.NOON));
        testDuty2.setEnd(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MAX));

        return new LinkedList<>(Arrays.asList(testDuty1, testDuty2));
    }
}
