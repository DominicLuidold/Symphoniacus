package at.fhv.teamb.symphoniacus.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DutyManagerTest {
    private LocalDate testDate;
    private DutyManager dutyManager;
    private SectionEntity section;

    @BeforeAll
    void initialize() {
        this.testDate = LocalDate.now();
        this.dutyManager = new DutyManager();
        this.section = Mockito.mock(SectionEntity.class);
        this.dutyManager.dutyDao = Mockito.mock(DutyDao.class);

        when(this.dutyManager.dutyDao.findAllInRangeWithSection(
            any(SectionEntity.class),
            any(LocalDateTime.class),
            any(LocalDateTime.class),
            any(Boolean.class),
            any(Boolean.class),
            any(Boolean.class)
        )).thenReturn(new LinkedList<>());

        when(this.section.getSectionId()).thenReturn(4711);
    }

    @Test
    void findAllWithSections_ShouldNotReturnNull() {
        // When & Then
        assertNotNull(this.dutyManager.findAllInRangeWithSection(
            this.section,
            this.testDate,
            this.testDate
        ));
        assertNotNull(this.dutyManager.findAllInWeekWithSection(this.section, this.testDate));
    }

    @Test
    void getLastMondayDate_ShouldReturnMonday() {
        // Given
        LocalDate monday = LocalDate.of(2020, 4, 6);
        LocalDate tuesday = LocalDate.of(2020, 4, 7);

        // When & Then
        assertEquals(DutyManager.getLastMondayDate(monday).getDayOfWeek(), DayOfWeek.MONDAY);
        assertEquals(DutyManager.getLastMondayDate(tuesday).getDayOfWeek(), DayOfWeek.MONDAY);
    }
}
