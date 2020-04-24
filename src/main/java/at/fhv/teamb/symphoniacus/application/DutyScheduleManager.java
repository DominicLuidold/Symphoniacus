package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final PointsManager pointsManager;
    private final Set<Musician> setMusicians;
    private final Set<Musician> unsetMusicians;
    private List<Duty> dutiesOfThisDay;
    private List<MusicianEntity> sectionMusicianEntities;
    private Set<Musician> sectionMusicians;

    /**
     * Initialize the DutyScheduleManager.
     */
    public DutyScheduleManager() {
        this.dutyDao = new DutyDao();
        this.dutyPositionDao = new DutyPositionDao();
        this.musicianDao = new MusicianDao();
        this.setMusicians = new HashSet<>();
        this.unsetMusicians = new HashSet<>();
        this.pointsManager = new PointsManager();
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
     * Returns a Set of {@link Musician}s that are available for a given {@link DutyPosition}.
     *
     * @param duty         The currently edited duty
     * @param position     The position to determine musicians for
     * @param withRequests Indicator whether musicians with duty requests should be part of the list
     * @return A Set of available musicians for the given duty position
     * @throws IllegalStateException if a Musician or Points object has an illegal state
     */
    public Set<Musician> getMusiciansAvailableForPosition(
        Duty duty,
        DutyPosition position,
        boolean withRequests
    ) throws IllegalStateException {
        if (position == null) {
            LOG.error("Fetching available musicians not possible - duty position is null");
            return new HashSet<>();
        }
        if (withRequests) {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        // Fetch section musicians from database if not present
        if (this.sectionMusicians == null) {
            // Get musician entities from database
            this.sectionMusicians = new HashSet<>();
            if (this.sectionMusicianEntities == null) {
                this.sectionMusicianEntities = this.musicianDao.findAllWithSectionAndActiveContract(
                    position.getEntity().getSection()
                );
            }

            // Tell PointsManager to cache duties locally
            this.pointsManager.loadAllDutiesOfMusicians(
                this.sectionMusicianEntities,
                duty.getEntity().getStart().toLocalDate()
            );

            // Convert musician entities to domain objects
            for (MusicianEntity entity : this.sectionMusicianEntities) {
                // Get points for musician
                Optional<Points> points = this.pointsManager.getBalanceFromMusician(
                    entity,
                    duty.getEntity().getStart().toLocalDate()
                );

                // Throw exception if points are missing
                if (points.isEmpty()) {
                    throw new IllegalStateException("Points for musician cannot be calculated");
                }

                this.sectionMusicians.add(new Musician(entity, points.get()));
            }
        }

        // Fetch duties from database if not present
        if (this.dutiesOfThisDay == null) {
            // Get all duties that occur at the same day
            this.dutiesOfThisDay = DutyManager.convertEntitiesToDomainObjects(
                this.dutyDao.findAllInRange(
                    LocalDateTime.of(duty.getEntity().getStart().toLocalDate(), LocalTime.MIN),
                    LocalDateTime.of(duty.getEntity().getStart().toLocalDate(), LocalTime.MAX)
                )
            );
        }

        return duty.determineAvailableMusicians(
            this.sectionMusicians,
            this.dutiesOfThisDay,
            this.setMusicians,
            this.unsetMusicians
        );
    }

    /**
     * Assigns a {@link Musician} to a {@link DutyPosition} and removes the old.
     *
     * @param actualSectionInstrumentation The actual section instrumentation to use
     * @param newMusician                  The musician to assign
     * @param oldMusician                  The musician to remove
     * @param position                     The duty position to use
     */
    public void assignMusicianToPosition(
        ActualSectionInstrumentation actualSectionInstrumentation,
        Musician newMusician,
        Musician oldMusician,
        DutyPosition position
    ) {
        // Update local musician list
        this.unsetMusicians.add(oldMusician);

        // Delegate to default method
        this.assignMusicianToPosition(actualSectionInstrumentation, newMusician, position);
    }

    /**
     * Assigns a {@link Musician} to a {@link DutyPosition}.
     *
     * @param actualSectionInstrumentation The actual section instrumentation to use
     * @param musician                     The musician to assign
     * @param position                     The duty position to use
     */
    public void assignMusicianToPosition(
        ActualSectionInstrumentation actualSectionInstrumentation,
        Musician musician,
        DutyPosition position
    ) {
        // Update local musician lists
        this.setMusicians.add(musician);
        this.unsetMusicians.remove(musician);

        // Delegate to domain object
        actualSectionInstrumentation.assignMusicianToPosition(musician, position);
    }

    /**
     * Removes a {@link Musician} from a {@link DutyPosition}.
     *
     * @param actualSectionInstrumentation The actual section instrumentation to use
     * @param musician                     The musician to remove
     * @param position                     The duty position to use
     */
    public void removeMusicianFromPosition(
        ActualSectionInstrumentation actualSectionInstrumentation,
        Musician musician,
        DutyPosition position
    ) {
        // Update local musician lists
        this.setMusicians.remove(musician);
        this.unsetMusicians.add(musician);

        // Delegate to domain object
        actualSectionInstrumentation.removeMusicianFromPosition(musician, position);
    }
}
