package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Async task to load all {@link Duty} objects in range and section.
 *
 * @author Dominic Luidold
 */
public class FindAllInRangeWithSectionTask extends LoadingAnimationTask<List<Duty>> {
    private static final Logger LOG = LogManager.getLogger(FindAllInRangeWithSectionTask.class);
    private final DutyManager dutyManager;
    private final LocalDate end;
    private final SectionDto section;
    private final LocalDate start;

    /**
     * Constructs a new {@code FindAllInRange} task.
     *
     * @param dutyManager   The duty manager to use
     * @param section The section to use
     * @param start         A LocalDate representing the start
     * @param end           A LocalDate representing the end
     * @param pane          The root anchor pane to use
     */
    public FindAllInRangeWithSectionTask(
        DutyManager dutyManager,
        SectionDto section,
        LocalDate start,
        LocalDate end,
        AnchorPane pane
    ) {
        super(pane);
        this.dutyManager = dutyManager;
        this.end = end;
        this.section = section;
        this.start = start;
    }

    @Override
    protected List<Duty> call() throws Exception {
        super.call();
        LOG.debug("Loading duties in range with section..");
        return this.dutyManager.findAllInRangeWithSection(
            this.section,
            this.start,
            this.end
        );
    }
}
