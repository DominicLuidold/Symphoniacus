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
    private Boolean withRequests;

    /**
     * Constructs a new Task.
     *
     * @param dsm DutyScheduleManager which was defined in
     *      {@link at.fhv.teamb.symphoniacus.presentation.DutyScheduleController}
     * @param duty Current duty
     * @param dutyPosition Current duty position
     * @param withRequests Whether to load requests for the musicians or not
     */
    public GetMusiciansAvailableForPositionTask(
        DutyScheduleManager dsm,
        Duty duty,
        DutyPosition dutyPosition,
        Boolean withRequests
    ) {
        this.dutyScheduleManager = dsm;
        this.duty = duty;
        this.dutyPosition = dutyPosition;
        this.withRequests = withRequests;
    }

    @Override
    protected Set<Musician> call() throws Exception {
        Set<Musician> set = this.dutyScheduleManager.getMusiciansAvailableForPosition(
            this.duty,
            this.dutyPosition,
            this.withRequests
        );
        return set;
    }
}
