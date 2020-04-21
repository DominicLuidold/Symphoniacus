package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/**
 * Tests for the DutyCategoryDao.*
 * @author : Danijel Antonijevic
 **/

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DutyCategoryDaoTest {
    private static final Logger LOG = LogManager.getLogger(DutyCategoryEntity.class);
    private DutyCategoryDao dao;

    @BeforeAll
    void initialize() {
        this.dao = new DutyCategoryDao();
    }

    @Test
    void getCategoryFromDuty_ShouldReturnCategoryMatchingDuty() {
        // Given
        DutyEntity duty = new DutyEntity();
        duty.setDutyCategoryId(1);

        Optional<DutyCategoryEntity> category = this.dao.getCategoryFromDuty(duty);
        category.ifPresentOrElse(entity -> {
            Assertions.assertEquals(category.get(), entity);
        }, () -> {
                LOG.error("DutyCategoryEntity wasn't returned by its dao");
            });

        category.ifPresent(categoryEntity -> Assertions
            .assertEquals(categoryEntity.getDutyCategoryId(),
                duty.getDutyCategoryId()));
    }
}
