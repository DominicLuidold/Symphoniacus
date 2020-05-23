package at.fhv.teamb.symphoniacus.domain;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for the {@link SectionMonthlySchedule} domain class.
 *
 * @author Valentin Goronjic
 */
public class SectionMonthlyScheduleTest {
    private SectionMonthlySchedule sms;
    private ISectionMonthlyScheduleEntity entity;

    /**
     * Initial setup for each test.
     */
    @BeforeEach
    public void setUp() {
        ISectionMonthlyScheduleEntity entity = new SectionMonthlyScheduleEntity();
        entity.setMonthlySchedule(new MonthlyScheduleEntity());

        SectionMonthlySchedule sms = new SectionMonthlySchedule(entity);
        this.entity = entity;
        this.sms = sms;
    }

    @Test
    public void testIsReadyForPublishing_shouldReturnTrueOrFalse() {
        assertFalse(this.sms.isReadyForPublishing(1L));
        assertTrue(this.sms.isReadyForPublishing(0L));
    }


}
