package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.Section;
import java.util.List;
import javafx.concurrent.Task;

/**
 * Async Task to load all musicians for a position.
 * Used in {@link at.fhv.teamb.symphoniacus.presentation.DutyScheduleController}
 *
 * @author Valentin Goronjic
 */
public class GetOtherDutiesTask extends Task<List<Duty>> {
    private final DutyManager dutyManager;
    private final Duty duty;
    private final SectionDto section;
    private final Integer numberOfMaxDuties;

    /**
     * Constructs a new GetOtherDutiesTask.
     *
     * @param dutyManager       DutyManager currently used
     * @param duty              For which duty other duties should be loaded
     * @param section           Current section
     * @param numberOfMaxDuties How many old duties should be loaded
     */
    public GetOtherDutiesTask(
        DutyManager dutyManager,
        Duty duty,
        SectionDto section,
        Integer numberOfMaxDuties
    ) {
        this.dutyManager = dutyManager;
        this.duty = duty;
        this.section = section;
        this.numberOfMaxDuties = numberOfMaxDuties;
    }

    @Override
    protected List<Duty> call() {
        return this.dutyManager.getOtherDutiesForSopOrSection(
            this.duty,
            this.section,
            this.numberOfMaxDuties
        );
    }
}
