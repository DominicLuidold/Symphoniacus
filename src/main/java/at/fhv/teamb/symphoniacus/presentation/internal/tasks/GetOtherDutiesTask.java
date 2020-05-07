package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.Section;
import java.util.List;
import java.util.Optional;
import javafx.concurrent.Task;

/**
 * Async Task to load all musicians for a position.
 * Used in {@link at.fhv.teamb.symphoniacus.presentation.DutyScheduleController}
 *
 * @author Valentin Goronjic
 */
public class GetOtherDutiesTask extends Task<Optional<List<Duty>>> {
    private DutyManager dutyManager;
    private Duty duty;
    private Section section;
    private Integer numberOfMaxDuties;

    /**
     * Constructs a new GetOtherDuteisTask.
     * @param dutyManager DutyManager currently used
     * @param duty For which duty other duties should be loaded
     * @param section Current section
     * @param numberOfMaxDuties How many old duties should be loaded
     */
    public GetOtherDutiesTask(
        DutyManager dutyManager,
        Duty duty,
        Section section,
        Integer numberOfMaxDuties
    ) {
        this.dutyManager = dutyManager;
        this.duty = duty;
        this.section = section;
        this.numberOfMaxDuties = numberOfMaxDuties;
    }

    @Override
    protected Optional<List<Duty>> call() throws Exception {
        return this.dutyManager.getOtherDutiesForSopOrSection(
            this.duty,
            this.section,
            this.numberOfMaxDuties
        );
    }
}
