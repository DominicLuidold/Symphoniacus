package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MusicianDaoTest {
    private static final Logger LOG = LogManager.getLogger(MusicianDaoTest.class);
    private MusicianDao dao;

    @BeforeAll
    public void init() {
        this.dao = new MusicianDao();
    }

    @Test
    public void testFindMusician_shouldReturnAMusician() {
        Optional<Musician> m = this.dao.find(1);
        Assertions.assertTrue(m.isPresent());
        LOG.debug(m.get());
    }

}
