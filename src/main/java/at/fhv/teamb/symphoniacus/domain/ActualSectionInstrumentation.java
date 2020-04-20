package at.fhv.teamb.symphoniacus.domain;

/**
 * Domain object responsible for handling the instrumentation for a {@link Duty}.
 *
 * @author Dominic Luidold
 */
public class ActualSectionInstrumentation {
    private Duty duty;

    public ActualSectionInstrumentation(Duty duty) {
        this.duty = duty;
    }

    public void setMusicianForPosition(Musician musician, DutyPosition dutyPosition) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void unsetMusicianForPosition(Musician musician, DutyPosition dutyPosition) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Duty getDuty() {
        return this.duty;
    }
}
