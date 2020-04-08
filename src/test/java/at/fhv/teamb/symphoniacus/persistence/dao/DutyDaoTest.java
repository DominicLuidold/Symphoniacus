package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyDaoTest {
    private DutyDao dao;

    @BeforeAll
    public void init() {
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("mysqldb");
        this.dao = new DutyDao();
    }

    @Test
    public void testFindAllDutiesForWeek() {
        List<Duty> list = this.dao.findAllInRange(LocalDateTime.of(2020, 3, 30, 0, 0, 0),
            LocalDateTime.of(2020, 4, 5, 0, 0, 0));

        Assertions.assertTrue(list != null);
    }
}
