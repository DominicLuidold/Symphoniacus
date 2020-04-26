package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import java.util.Objects;

/**
 * Domain object for SectionMonthlySchedule.
 *
 * @author Dominic Luidold
 */
public class SectionMonthlySchedule {
    /**
     * The PublishState enum indicates what state a {@link SectionMonthlySchedule} has.
     */
    public enum PublishState {
        /**
         * Indicates that the section monthly schedule is ready for the duty scheduler.
         */
        READY_FOR_DUTY_SCHEDULER,

        /**
         * Indicates that the section monthly schedule is ready for the organisation manager.
         */
        READY_FOR_ORGANISATION_MANAGER,

        /**
         * Indicates that the section monthly schedule is published.
         */
        PUBLISHED
    }

    private final MonthlyScheduleEntity monthlyScheduleEntity;
    private final SectionMonthlyScheduleEntity entity;
    private PublishState publishState;

    public SectionMonthlySchedule(SectionMonthlyScheduleEntity entity) {
        this.entity = entity;
        this.monthlyScheduleEntity = entity.getMonthlySchedule();
    }

    public PublishState getPublishState() {
        return this.publishState;
    }

    /**
     * Checks whether the section monthly schedule is ready for publishing.
     *
     * <p>This method currently assesses whether all {@link DutyPosition} objects that
     * happen during this schedule have a {@link Musician} assigned.
     *
     * @param dutyPositionsWithoutAssignedMusicians Amount of positions without musicians
     * @return True if ready, false otherwise
     */
    public boolean isReadyForPublishing(Long dutyPositionsWithoutAssignedMusicians) {
        return dutyPositionsWithoutAssignedMusicians <= 0;
    }

    public void setPublishState(PublishState publishState) {
        this.publishState = publishState;
    }

    public SectionMonthlyScheduleEntity getEntity() {
        return this.entity;
    }

    @Override
    public boolean equals(Object obj) {
        // Return true if object is compared with itself
        if (obj == this) {
            return true;
        }

        // Check if object is an instance of SectionMonthlySchedules or not
        if (!(obj instanceof SectionMonthlySchedule)) {
            return false;
        }

        // Typecast obj to SectionMonthlySchedule to compare data members
        SectionMonthlySchedule sms = (SectionMonthlySchedule) obj;

        // Compare data members and return accordingly
        return this.entity.getSectionMonthlyScheduleId()
            .equals(sms.getEntity().getSectionMonthlyScheduleId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entity.getSectionMonthlyScheduleId(),
            this.monthlyScheduleEntity.getMonthlyScheduleId());
    }
}
