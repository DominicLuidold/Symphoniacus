package at.fhv.teamb.symphoniacus.business;

import at.fhv.teamb.symphoniacus.persistence.dao.DutyDAO;
import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DutyManagerTest {
    LocalDate testDate;
    DutyManager dutyManager;
    Section section;

    @BeforeAll
    public void initalize() {
        this.testDate = LocalDate.now();
        this.dutyManager = new DutyManager();
        this.section = Mockito.mock(Section.class);
        this.dutyManager.dutyDAO = Mockito.mock(DutyDAO.class);
        when(this.dutyManager.dutyDAO.findAllInRange(any(Section.class),any(LocalDateTime.class),any(LocalDateTime.class))).thenReturn(new LinkedList<Duty>());
        when(this.section.getSectionId()).thenReturn(4711);
    }

    @Test
    void findAllWithSections_ShouldNotReturnNull() {
        assertNotNull(this.dutyManager.findAllInRange(this.section, this.testDate, this.testDate));
        assertNotNull(this.dutyManager.findAllInWeek(this.section, this.testDate));
    }

    @Test
    void getLastMondayDate_ShouldReturnMonday() {
        LocalDate monday = LocalDate.of(2020, 4, 6);
        LocalDate tuesday = LocalDate.of(2020, 4, 7);

        assertEquals(DutyManager.getLastMondayDate(monday).getDayOfWeek(), DayOfWeek.MONDAY);
        assertEquals(DutyManager.getLastMondayDate(tuesday).getDayOfWeek(), DayOfWeek.MONDAY);
    }


}