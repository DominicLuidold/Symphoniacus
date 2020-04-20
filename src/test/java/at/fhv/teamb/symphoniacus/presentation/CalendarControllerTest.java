package at.fhv.teamb.symphoniacus.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;

class CalendarControllerTest {

    @Test
    void prepareCalendarSource_ShouldPrepareCalendarSource() {
        // Given
        SectionEntity section = new SectionEntity();
        section.setDescription("Test Section 1");
        section.setSectionShortcut("TS1");
        String name = "Test Source";

        CalendarController controller = spy(CalendarController.class);
        doAnswer((Answer<Void>) invocation -> {
            controller.fillCalendar(
                invocation.getArgument(0),
                controller.createDutyCalendarEntries(prepareTestDuties())
            );
            return null;
        }).when(controller).fillCalendar(any(Calendar.class), any(SectionEntity.class));

        // When
        CalendarSource source = controller.prepareCalendarSource(name, section);

        // Then
        assertEquals(name, source.getName(), "Given name and CalendarSource name should be equal");
        assertEquals(1, source.getCalendars().size(), "CalendarSource should have one calendar");
    }

    @Test
    void createCalendar_ShouldCreateCalendar() {
        // Given
        SectionEntity section = new SectionEntity();
        section.setDescription("Test Section 1");
        section.setSectionShortcut("TS1");

        // When
        Calendar calendar = new CalendarController().createCalendar(section);

        // Then
        assertEquals(
            section.getDescription(),
            calendar.getName(),
            "Section and Calendar should have same name"
        );
        assertEquals(
            section.getSectionShortcut(),
            calendar.getShortName(),
            "Section and Calendar should have same short name"
        );
        assertTrue(calendar.isReadOnly(), "Calendar should be marked as read only");
        assertNotNull(calendar.getStyle(), "Calendar should have a style");
    }

    @Test
    void fillCalendar_ShouldFillCalendarWithEntries() {
        // Given
        CalendarController controller = new CalendarController();
        Calendar calendar = new Calendar();
        List<Entry<DutyEntity>> entries = controller.createDutyCalendarEntries(prepareTestDuties());

        // When
        controller.fillCalendar(calendar, entries);

        // Then
        for (Entry<DutyEntity> entry : entries) {
            assertFalse(
                calendar.findEntries(entry.getTitle()).isEmpty(),
                "Calendar should contain Entry"
            );
            assertEquals(
                1,
                calendar.findEntries(entry.getTitle()).size(),
                "Calendar should find exactly one Entry"
            );
        }
    }

    @Test
    void createDutyCalendarEntries_ShouldCreateEntries() {
        // Given
        List<DutyEntity> duties = prepareTestDuties();

        // When
        List<Entry<DutyEntity>> entries =
            new CalendarController().createDutyCalendarEntries(duties);

        // Then
        assertEquals(duties.size(), entries.size(), "Amount of Duties and Entries should be equal");
        for (int i = 0; i < duties.size(); i++) {
            assertEquals(
                duties.get(i).getTitle(),
                entries.get(i).getTitle(),
                "Duty and Entry should have same description"
            );
            assertEquals(
                duties.get(i).getEntity().getStart(),
                entries.get(i).getStartAsLocalDateTime(),
                "Duty and Entry should have same start time/day"
            );
            assertEquals(
                duties.get(i).getEntity().getEnd(),
                entries.get(i).getEndAsLocalDateTime(),
                "Duty and Entry should have same end time/day"
            );
        }
    }

    /**
     * Prepares a List of {@link DutyEntity} objects with test data.
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
