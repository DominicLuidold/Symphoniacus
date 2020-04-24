package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import java.util.Set;
import javafx.concurrent.Task;

/**
 * @author Valentin
 */
public class GetMusiciansAvailableForPositionTask extends Task<Set<Musician>> {
    private DutyScheduleManager dutyScheduleManager;
    private Duty duty;
    private DutyPosition dutyPosition;
    private Boolean withRequests;

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
