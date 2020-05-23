package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.presentation.DutyScheduleController;
import java.util.Set;
import javafx.concurrent.Task;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Async Task to load all musicians for a position.
 * Used in {@link DutyScheduleController}
 *
 * @author Valentin Goronjic
 */
public class GetMusiciansAvailableForPositionTask extends LoadingAnimationTask<Set<Musician>> {
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
        DutyPosition dutyPosition,
        AnchorPane pane
    ) {
        super(pane);
        this.dutyScheduleManager = dsm;
        this.duty = duty;
        this.dutyPosition = dutyPosition;
    }

    @Override
    protected Set<Musician> call() throws Exception {
        super.call();
        LOG.debug("GetMusiciansAvailableForPositionTask called");
        return this.dutyScheduleManager.getMusiciansAvailableForPosition(
            this.duty,
            this.dutyPosition
        );
    }
}
