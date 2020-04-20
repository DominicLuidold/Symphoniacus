package at.fhv.teamb.symphoniacus.persistence.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.util.List;
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
class DutyCategoryChangeLogDaoTest {
    private static final Logger LOG = LogManager.getLogger(DutyCategoryChangeLogDaoTest.class);
    private DutyCategoryChangeLogDao dao;

    @BeforeAll
    void initialize() {
        dao = new DutyCategoryChangeLogDao();
    }

    /**
     * Tests if returning List is not null.
     * Tests if the given DutyCategory.Id matches with every DutyCategoryChangeLog.DutyCategoryId
     * in the returning List of DutyCategoryChangelogEntity.
     * Working DB Connection on the Dao is required.
     */
    @Test
    void getDutyCategoryChangeLog_ShouldReturnListOfCategoryChangeLog() {
        // Given
        DutyCategoryEntity categoryEntity = new DutyCategoryEntity();
        categoryEntity.setDutyCategoryId(3);

        List<DutyCategoryChangelogEntity> changelogEntityList =
            this.dao.getDutyCategoryChangeLog(categoryEntity);

        assertNotNull(changelogEntityList,"The returning List has to be not null");

        if (!changelogEntityList.isEmpty()) {
            for (DutyCategoryChangelogEntity entity : changelogEntityList) {
                assertEquals(entity.getDutyCategoryId(),
                    categoryEntity.getDutyCategoryId());
            }
        }
    }
}