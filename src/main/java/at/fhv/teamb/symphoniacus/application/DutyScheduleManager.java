package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for obtaining and handling all instances of actual instrumentations
 * for given {@link Duty} objects.
 *
 * @author Dominic Luidold
 */
public class DutyScheduleManager {
    private static final Logger LOG = LogManager.getLogger(DutyScheduleManager.class);
    private DutyPositionDao dutyPositionDao;

    public DutyScheduleManager() {
        this.dutyPositionDao = new DutyPositionDao();
    }

    /**
     * Returns a {@link ActualSectionInstrumentation} domain object containing all available
     * information about the actual section instrumentation for the given {@link Duty} and
     * {@link Section}.
     *
     * @param duty    The duty to use
     * @param section The section to use
     * @return A domain object containing all instrumentation information
     */
    public Optional<ActualSectionInstrumentation> getInstrumentationDetails(
        Duty duty,
        Section section
    ) {
        if (duty == null || section == null) {
            LOG.error(
                "Fetching instrumentation details not possible - either duty or section is null"
            );
            return Optional.empty();
        }

        // Get all DutyPosition entities from database
        List<DutyPositionEntity> dutyPositionEntities =
            this.dutyPositionDao.findCorrespondingPositions(duty.getEntity(), section.getEntity());

        // Create DutyPosition domain objects
        List<DutyPosition> dutyPositions = new LinkedList<>();
        for (DutyPositionEntity dutyPosition : dutyPositionEntities) {
            dutyPositions.add(new DutyPosition(dutyPosition));
        }

        // Fill Duty with available information
        Duty dutyWithInformation = new Duty(duty.getEntity(), dutyPositions);

        return Optional.of(new ActualSectionInstrumentation(dutyWithInformation));
    }

    public List<Musician> getMusiciansAvailableForPosition(DutyPosition position) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setMusicianForPosition(Musician musician, DutyPosition position) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void unsetMusicianForPosition(Musician musician, DutyPosition position) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
