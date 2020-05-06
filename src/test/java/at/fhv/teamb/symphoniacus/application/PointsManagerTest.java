package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.ContractualObligationDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

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
}