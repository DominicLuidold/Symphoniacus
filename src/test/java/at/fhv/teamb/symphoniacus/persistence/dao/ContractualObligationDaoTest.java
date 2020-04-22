package at.fhv.teamb.symphoniacus.persistence.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Tests for ContractualObligation.
 *
 * @author Nino Heinzle
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContractualObligationDaoTest {
    private static final Logger LOG = LogManager.getLogger(ContractualObligationDaoTest.class);
    private ContractualObligationDao dao;

    @BeforeAll
    void initialize() {
        this.dao = new ContractualObligationDao();
    }

    /**
     * Tests if the Method returns a filled ContractualObligationEntity Object.
     * Tests if the ID from given musician matches with the returning ContractualObligationEntity
     * Object.
     * Working DB Connection on the Dao is required.
     */
    @Test
    void getContractualObligation_ShouldReturnMusicianMatchingObligation() {
        // Given
        MusicianEntity musicianEntity = new MusicianEntity();
        musicianEntity.setMusicianId(1);

        // When
        ContractualObligationEntity conEntity = this.dao.getContractualObligation(musicianEntity);

        // Then
        assertNotNull(conEntity);
        assertEquals(musicianEntity.getMusicianId(), conEntity.getMusician().getMusicianId());
    }
}