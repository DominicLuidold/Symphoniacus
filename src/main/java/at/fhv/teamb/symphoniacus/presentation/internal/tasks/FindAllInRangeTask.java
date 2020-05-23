package at.fhv.teamb.symphoniacus.presentation.internal.tasks;

import at.fhv.teamb.symphoniacus.application.DutyManager;
import at.fhv.teamb.symphoniacus.domain.Duty;
import java.time.LocalDate;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Async task to load all {@link Duty} objects in range.
 *
 * @author Dominic Luidold
 */
public class FindAllInRangeTask extends LoadingAnimationTask<List<Duty>> {
    private static final Logger LOG = LogManager.getLogger(FindAllInRangeWithSectionTask.class);
    private final DutyManager dutyManager;
    private final LocalDate end;
    private final LocalDate start;

    /**
     * Constructs a new {@code FindAllInRange} task.
     *
     * @param dutyManager The duty manager to use
     * @param start       A LocalDate that represents the start
     * @param end         A LocalDate that represents the end
     * @param pane        The root anchor pane to use
     */
    public FindAllInRangeTask(
        DutyManager dutyManager,
        LocalDate start,
        LocalDate end,
        AnchorPane pane
    ) {
        super(pane);
        this.dutyManager = dutyManager;
        this.end = end;
        this.start = start;
    }

    @Override
    protected List<Duty> call() throws Exception {
        super.call();
        LOG.debug("Loading duties in range..");
        return this.dutyManager.findAllInRange(
            this.start,
            this.end
        );
    }
}