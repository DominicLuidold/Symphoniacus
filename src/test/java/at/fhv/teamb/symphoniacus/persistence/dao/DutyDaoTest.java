package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyDaoTest {
    private static final Logger LOG = LogManager.getLogger(DutyDaoTest.class);
    private DutyDao dao;

    @BeforeAll
    public void init() {
        this.dao = new DutyDao();
    }

    @Test
    public void testFindAllDutiesForWeek_shouldReturnNotNull() {
        List<Duty> list = this.dao.findAllInRange(
            LocalDateTime.of(2020, 3, 30, 0, 0, 0),
            LocalDateTime.of(2020, 4, 5, 0, 0, 0));

        Assertions.assertTrue(list != null);
    }

    @Test
    public void findAllInRangeWithSection_shouldReturnNotNull() {
        Section s = new Section();
        s.setSectionId(1);
        List<Duty> list = this.dao.findAllInRangeWithSection(s,
            LocalDateTime.of(2020, 5, 1, 0, 0, 0),
            LocalDateTime.of(2020, 5, 1, 14, 0, 0),
            true, false, false);
        LOG.debug("Result size? " + list.size());
        Assertions.assertTrue(list != null);
    }
}
