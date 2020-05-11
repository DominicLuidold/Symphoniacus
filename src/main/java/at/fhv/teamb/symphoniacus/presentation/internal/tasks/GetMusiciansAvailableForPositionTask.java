package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.presentation.DutyScheduleController;
import java.util.Set;
import javafx.concurrent.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Async Task to load all musicians for a position.
 * Used in {@link DutyScheduleController}
 *
 * @author Valentin Goronjic
 */
public class GetMusiciansAvailableForPositionTask extends Task<Set<Musician>> {
    private static final Logger LOG =
        LogManager.getLogger(GetMusiciansAvailableForPositionTask.class);
    private final DutyScheduleManager dutyScheduleManager;
    private final Duty duty;
    private final DutyPosition dutyPosition;

    /**
     * Constructs a new Task.
     *
     * @param dsm          DutyScheduleManager which was defined in {@link DutyScheduleController}
     * @param duty         Current duty
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
    protected Set<Musician> call() {
        return this.dutyScheduleManager.getMusiciansAvailableForPosition(
            this.duty,
            this.dutyPosition
        );
    }

    @Override
    protected void failed() {
        LOG.error("Task failed: {}", this.getClass().getName());
        Throwable t = this.getException();
        if (t != null) {
            LOG.error(t);
        }
        super.failed();
    }
}
