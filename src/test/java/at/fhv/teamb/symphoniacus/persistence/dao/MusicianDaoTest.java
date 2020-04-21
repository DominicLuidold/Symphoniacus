package at.fhv.teamb.symphoniacus.persistence.dao;

import static org.junit.jupiter.api.Assertions.assertTrue;

import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MusicianDaoTest {
    private static final Logger LOG = LogManager.getLogger(MusicianDaoTest.class);
    private MusicianDao dao;

    @BeforeAll
    void initialize() {
        this.dao = new MusicianDao();
    }

    @Test
    void testFindMusician_ShouldReturnAMusician() {
        // When
        Optional<MusicianEntity> m = this.dao.find(1);

        // Then
        assertTrue(m.isPresent());
        LOG.debug(m.get());
    }
}
