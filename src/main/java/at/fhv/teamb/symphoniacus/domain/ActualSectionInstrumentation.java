package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.PersistenceState;

/**
 * Domain object responsible for handling the instrumentation for a {@link Duty}.
 *
 * @author Dominic Luidold
 */
public class ActualSectionInstrumentation {
    private final Duty duty;

    public ActualSectionInstrumentation(Duty duty) {
        this.duty = duty;
    }

    /**
     * Assigns a {@link Musician} to a {@link DutyPosition}.
     *
     * @param musician     The musician to assign
     * @param dutyPosition The duty position to use
     */
    public void assignMusicianToPosition(Musician musician, DutyPosition dutyPosition) {
        musician.getEntity().addDutyPosition(dutyPosition.getEntity());
    }

    /**
     * Removes a {@link Musician} from a {@link DutyPosition}.
     *
     * @param musician     The musician to remove
     * @param dutyPosition The position to use
     */
    public void removeMusicianFromPosition(Musician musician, DutyPosition dutyPosition) {
        musician.getEntity().removeDutyPosition(dutyPosition.getEntity());
    }

    public Duty getDuty() {
        return this.duty;
    }

    public PersistenceState getPersistenceState() {
        return this.duty.getPersistenceState();
    }
}
