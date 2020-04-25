package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;

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

    private final SectionMonthlyScheduleEntity entity;
    private PublishState publishState;

    public SectionMonthlySchedule(SectionMonthlyScheduleEntity entity) {
        this.entity = entity;
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
}
