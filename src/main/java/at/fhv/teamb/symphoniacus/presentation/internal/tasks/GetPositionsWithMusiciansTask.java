package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.presentation.DutyScheduleController;
import java.util.Optional;
import java.util.Set;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Async Task to load all musicians for a position.
 * Used in {@link DutyScheduleController}
 *
 * @author Valentin Goronjic
 */
public class GetPositionsWithMusiciansTask
    extends LoadingAnimationTask<Optional<ActualSectionInstrumentation>> {

    private static final Logger LOG =
        LogManager.getLogger(GetPositionsWithMusiciansTask.class);
    private final DutyScheduleManager dutyScheduleManager;
    private final Duty duty;
    private final Section section;

    /**
     * Constructs a new Task.
     *
     * @param dsm     DutyScheduleManager which was defined in {@link DutyScheduleController}
     * @param duty    Current duty
     * @param section Section
     */
    public GetPositionsWithMusiciansTask(
        DutyScheduleManager dsm,
        Duty duty,
        Section section,
        AnchorPane pane
    ) {
        super(pane);
        this.dutyScheduleManager = dsm;
        this.duty = duty;
        this.section = section;
    }

    @Override
    protected Optional<ActualSectionInstrumentation> call() throws Exception {
        super.call();
        return this.dutyScheduleManager.getInstrumentationDetails(
            this.duty,
            this.section
        );
    }
}
