package at.fhv.teamb.symphoniacus.business;

import at.fhv.teamb.symphoniacus.persistence.model.Section;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DutyManagerTest {
    LocalDate _testDate;
    DutyManager _dutyManager;
    Section _section;

    @BeforeAll
    public void initalize() {
        _testDate = LocalDate.now();
        _dutyManager = new DutyManager();
        _section = Mockito.mock(Section.class);
        when(_section.getSectionId()).thenReturn(4711);
    }


    @Test
    void findAllWithSections_ShouldNotReturnNull() {
        assertNotNull(_dutyManager.findAll(_section, _testDate, _testDate));
        assertNotNull(_dutyManager.findAllInWeek(_section, _testDate));
    }


    @Test
    void getLastMondayDate_ShouldReturnMonday() {
        LocalDate monday = LocalDate.of(2020, 4, 6);
        LocalDate tuesday = LocalDate.of(2020, 4, 7);

        assertEquals(DutyManager.getLastMondayDate(monday).getDayOfWeek(), DayOfWeek.MONDAY);
        assertEquals(DutyManager.getLastMondayDate(tuesday).getDayOfWeek(), DayOfWeek.MONDAY);
    }


}