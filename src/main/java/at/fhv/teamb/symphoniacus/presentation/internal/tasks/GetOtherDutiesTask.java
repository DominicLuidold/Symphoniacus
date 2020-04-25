package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.DutyScheduleManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javafx.concurrent.Task;
import javafx.scene.chart.XYChart;

/**
 * Async Task to load all musicians for a position.
 * Used in {@link at.fhv.teamb.symphoniacus.presentation.DutyScheduleController}
 *
 * @author Valentin Goronjic
 */
public class GetOtherDutiesTask extends Task<Optional<List<Duty>>> {
    private DutyManager dutyManager;
    private Duty duty;
    private SeriesOfPerformancesEntity sop;
    private Section section;
    private Integer numberOfMaxDuties;

    /**
     * TODO JAVADOC.
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
        Optional<List<Duty>> list = this.dutyManager.getOtherDutiesForSopOrSection(
            this.duty,
            this.section,
            this.numberOfMaxDuties
        );
        return list;
    }
}
