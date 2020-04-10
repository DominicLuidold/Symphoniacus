package at.fhv.teamb.symphoniacus.presentation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.spy;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
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
        String name = "Test Source";
        List<Section> sections = prepareTestSections();

        CalendarController controller = spy(CalendarController.class);
        doAnswer((Answer<Void>) invocation -> {
            controller.fillCalendar(
                invocation.getArgument(0),
                controller.createDutyCalendarEntries(prepareTestDuties())
            );
            return null;
        }).when(controller).fillCalendar(any(Calendar.class), any(Section.class));

        // When
        CalendarSource source = controller.prepareCalendarSource(name, sections);

        // Then
        assertEquals(name, source.getName(), "Given name and CalendarSource name should be equal");
        assertEquals(
            sections.size(),
            source.getCalendars().size(),
            "Amount of Sections and Calendars should be equal"
        );
    }

    @Test
    void createCalendars_ShouldCreateCalendars() {
        // Given
        List<Section> sections = prepareTestSections();

        // When
        List<Calendar> calendars = new CalendarController().createCalendars(sections);

        // Then
        assertEquals(
            sections.size(),
            calendars.size(),
            "Amount of Sections and Entries should be equal"
        );
        for (int i = 0; i < calendars.size(); i++) {
            assertEquals(
                sections.get(i).getDescription(),
                calendars.get(i).getName(),
                "Section and Calendar should have same name"
            );
            assertEquals(
                sections.get(i).getSectionShortcut(),
                calendars.get(i).getShortName(),
                "Section and Calendar should have same short name"
            );
            assertTrue(calendars.get(i).isReadOnly(), "Calendar should be marked as read only");
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
                1,
                calendar.findEntries(entry.getTitle()).size(),
                "Calendar should find exactly one Entry"
            );
        }
    }

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

    /**
     * Prepares a List of {@link Section}s with test data.
     *
     * @return A list of Sections
     */
    private List<Section> prepareTestSections() {
        // Given
        Section testSection1 = new Section();
        testSection1.setDescription("Test Section 1");
        testSection1.setSectionShortcut("TS1");

        Section testSection2 = new Section();
        testSection2.setDescription("Test Section 2");
        testSection2.setSectionShortcut("TS2");

        return new LinkedList<>(Arrays.asList(testSection1, testSection2));
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
