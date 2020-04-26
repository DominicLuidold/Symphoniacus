package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import java.util.Set;
import javafx.concurrent.Task;

/**
 * Async Task to load all musicians for a position.
 * Used in {@link at.fhv.teamb.symphoniacus.presentation.DutyScheduleController}
 *
 * @author Valentin Goronjic
 */
public class GetMusiciansAvailableForPositionTask extends Task<Set<Musician>> {
    private DutyScheduleManager dutyScheduleManager;
    private Duty duty;
    private DutyPosition dutyPosition;

    /**
     * Constructs a new Task.
     *
     * @param dsm DutyScheduleManager which was defined in
     *      {@link at.fhv.teamb.symphoniacus.presentation.DutyScheduleController}
     * @param duty Current duty
     * @param dutyPosition Current duty position
     */
    public GetMusiciansAvailableForPositionTask(
        DutyScheduleManager dsm,
        Duty duty,
        DutyPosition dutyPosition
    ) {
        this.dutyScheduleManager = dsm;
        this.duty = duty;
        this.dutyPosition = dutyPosition;
    }

    @Override
    protected Set<Musician> call() throws Exception {
        Set<Musician> set = this.dutyScheduleManager.getMusiciansAvailableForPosition(
            this.duty,
            this.dutyPosition
        );
        return set;
    }
}
