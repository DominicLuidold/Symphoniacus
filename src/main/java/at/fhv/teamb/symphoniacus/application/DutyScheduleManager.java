package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private final DutyDao dutyDao;
    private final DutyPositionDao dutyPositionDao;
    private final MusicianDao musicianDao;
    private List<Musician> setMusicians;
    private List<Musician> unsetMusicians;

    /**
     * Initialize the DutyScheduleManager.
     */
    public DutyScheduleManager() {
        this.dutyDao = new DutyDao();
        this.dutyPositionDao = new DutyPositionDao();
        this.musicianDao = new MusicianDao();
        this.setMusicians = new LinkedList<>();
        this.unsetMusicians = new LinkedList<>();
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

    /**
     * Returns a List of {@link Musician}s that are available for a given {@link DutyPosition}.
     *
     * @param duty The currently edited duty
     * @param position The position to determine musicians for
     * @param withRequests Indicator whether musicians with duty requests should be part of the list
     * @return A List of available musicians for the given duty position
     */
    public List<Musician> getMusiciansAvailableForPosition(
        Duty duty,
        DutyPosition position,
        boolean withRequests
    ) {
        if (position == null) {
            LOG.error("Fetching available musicians not possible - duty position is null");
            return new LinkedList<>();
        }
        if (withRequests) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        // Get all Musician entities from database and convert them
        List<Musician> sectionMusicians = MusicianManager.convertEntitiesToDomainObjects(
            this.musicianDao.findAllWithSection(
                position.getEntity().getSection()
            )
        );

        // Get all duties that occur at the same day
        List<Duty> dutiesOfThisDay = DutyManager.convertEntitiesToDomainObjects(
            this.dutyDao.findAllInRange(
                LocalDateTime.of(duty.getEntity().getStart().toLocalDate(), LocalTime.MIN),
                LocalDateTime.of(duty.getEntity().getStart().toLocalDate(), LocalTime.MAX)
            )
        );

        return duty.determineAvailableMusicians(
            sectionMusicians,
            dutiesOfThisDay,
            setMusicians,
            unsetMusicians
        );
    }

    public void setMusicianForPosition(Musician musician, DutyPosition position) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void unsetMusicianForPosition(Musician musician, DutyPosition position) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
