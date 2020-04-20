package at.fhv.teamb.symphoniacus.application;


import static org.mockito.Mockito.when;

import at.fhv.teamb.symphoniacus.persistence.dao.ContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

/**
 * Tests for PointsManager.
 *
 * @author Nino Heinzle
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PointsManagerTest {
    private PointsManager pointsManager;
    private ContractualObligationDao contractualObligationDao;

    @BeforeAll
    void initialize() {
        this.pointsManager = new PointsManager();
        this.contractualObligationDao = new ContractualObligationDao();
    }

    @Test
    void getDebitPointsFromMusician_ShouldReturnDebitPoints() {
        MusicianEntity musicianEntity = new MusicianEntity();
        musicianEntity.setMusicianId(1);
        ContractualObligationEntity mockedContractualObligation = Mockito.mock(ContractualObligationEntity.class);
        mockedContractualObligation.setMusicianId(1);

        when(this.contractualObligationDao.getContractualObligation(musicianEntity)).thenReturn(
            Optional.ofNullable(mockedContractualObligation));
    }

}