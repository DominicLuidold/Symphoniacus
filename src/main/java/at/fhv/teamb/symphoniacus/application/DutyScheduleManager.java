package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.time.LocalDate;
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
    private final WishRequestManager wishRequestManager;
    private List<Duty> dutiesOfThisDay;
    private List<MusicianEntity> externalMusicianEntities;
    private List<MusicianEntity> sectionMusicianEntities;
    private Set<Musician> sectionMusicians;

    /**
     * Initialize the DutyScheduleManager.
     */
    public DutyScheduleManager() {
        this.dutyDao = new DutyDao();
        this.dutyPositionDao = new DutyPositionDao();
        this.musicianDao = new MusicianDao();
        this.pointsManager = new PointsManager();
        this.setMusicians = new HashSet<>();
        this.unsetMusicians = new HashSet<>();
        this.wishRequestManager = new WishRequestManager();
    }

    /**
     * Returns a {@link ActualSectionInstrumentation} domain object containing all available
     * information about the actual instrumentation for the given {@link Duty} and
     * {@link Section} objects.
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
        for (DutyPositionEntity dpEntity : dutyPositionEntities) {
            DutyPosition dp = new DutyPosition(dpEntity);
            dutyPositions.add(dp);
        }

        // Fill Duty with available information
        Duty dutyWithInformation = new Duty(duty.getEntity(), dutyPositions);
        dutyWithInformation.setPersistenceState(PersistenceState.PERSISTED);

        return Optional.of(new ActualSectionInstrumentation(dutyWithInformation));
    }

    /**
     * Returns a Set of {@link Musician}s that are available for a given {@link DutyPosition}.
     *
     * @param duty     The currently edited duty
     * @param position The position to determine musicians for
     * @return A Set of available musicians for the given duty position
     */
    public Set<Musician> getMusiciansAvailableForPosition(
        Duty duty,
        DutyPosition position
    ) {
        if (position == null) {
            LOG.error("Fetching available musicians not possible - duty position is null");
            return new HashSet<>();
        }

        // Fetch section musicians from database if not present
        if (this.sectionMusicians == null) {
            // Get musician entities from database
            this.fetchMusicians(position);

            // Tell PointsManager to cache duties locally
            this.pointsManager.loadAllDutiesOfMusicians(
                this.sectionMusicianEntities,
                duty.getEntity().getStart().toLocalDate()
            );

            // Tell WishRequestManager to cache wish requests locally
            this.wishRequestManager.loadAllWishRequests(duty.getEntity());

            // Convert musician entities to domain objects
            this.convertMusicianEntitiesToDomainObjects(duty);
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
     * @param instrumentation The instrumentation to use
     * @param newMusician     The musician to assign
     * @param oldMusician     The musician to remove
     * @param position        The duty position to use
     */
    public void assignMusicianToPosition(
        ActualSectionInstrumentation instrumentation,
        Musician newMusician,
        Musician oldMusician,
        DutyPosition position
    ) {
        // Update local musician list
        this.unsetMusicians.add(oldMusician);

        // Delegate to default method
        this.assignMusicianToPosition(instrumentation, newMusician, position);
    }

    /**
     * Assigns a {@link Musician} to a {@link DutyPosition}.
     *
     * @param instrumentation The instrumentation to use
     * @param musician        The musician to assign
     * @param position        The duty position to use
     */
    public void assignMusicianToPosition(
        ActualSectionInstrumentation instrumentation,
        Musician musician,
        DutyPosition position
    ) {
        // Update local musician lists
        this.setMusicians.add(musician);
        this.unsetMusicians.remove(musician);

        // Update object state
        instrumentation.getDuty().setPersistenceState(PersistenceState.EDITED);

        // Delegate to domain object
        instrumentation.assignMusicianToPosition(musician, position);
    }

    /**
     * Removes a {@link Musician} from a {@link DutyPosition}.
     *
     * @param instrumentation The instrumentation to use
     * @param musician        The musician to remove
     * @param position        The duty position to use
     */
    public void removeMusicianFromPosition(
        ActualSectionInstrumentation instrumentation,
        Musician musician,
        DutyPosition position
    ) {
        // Update local musician lists
        this.setMusicians.remove(musician);
        this.unsetMusicians.add(musician);

        // Update object state
        instrumentation.getDuty().setPersistenceState(PersistenceState.EDITED);

        // Delegate to domain object
        instrumentation.removeMusicianFromPosition(musician, position);
    }

    /**
     * Persists the {@link ActualSectionInstrumentation} object to the database.
     *
     * <p>The method will subsequently change the {@link PersistenceState} of the object
     * from {@link PersistenceState#EDITED} to {@link PersistenceState#PERSISTED}, provided
     * that the database update was successful.
     *
     * @param instrumentation The instrumentation to persist
     */
    public void persist(ActualSectionInstrumentation instrumentation) {
        Optional<DutyEntity> persisted = this.dutyDao.update(instrumentation.getDuty().getEntity());

        if (persisted.isPresent()) {
            instrumentation.getDuty().setPersistenceState(PersistenceState.PERSISTED);
            LOG.debug(
                "Persisted instrumentation of duty {{}, '{}'}",
                instrumentation.getDuty().getEntity().getDutyId(),
                instrumentation.getDuty().getTitle()
            );
        } else {
            LOG.error(
                "Could not persist instrumentation of duty {{}, '{}'}",
                instrumentation.getDuty().getEntity().getDutyId(),
                instrumentation.getDuty().getTitle()
            );
        }
    }

    /**
     * Fetches {@link MusicianEntity} objects from the database.
     *
     * @param position The duty position to use
     */
    private void fetchMusicians(DutyPosition position) {
        this.sectionMusicians = new HashSet<>();
        if (this.sectionMusicianEntities == null) {
            this.sectionMusicianEntities = this.musicianDao.findAllWithSectionAndActiveContract(
                position.getEntity().getSection()
            );
        }

        // Fetch external musician placeholders from database
        if (this.externalMusicianEntities == null) {
            this.externalMusicianEntities =
                this.musicianDao.findExternalsWithSection(position.getEntity().getSection());
        }
        this.sectionMusicianEntities.addAll(externalMusicianEntities);
    }

    public void addBalancePointsToMusician(Musician musician, LocalDate start) {
        Points balancePoints = this.pointsManager.getBalanceFromMusician(
            musician.getEntity(),
            start
        );
        musician.setBalancePoints(balancePoints);
        LOG.debug("Balance Points set to {}", balancePoints.getValue());
    }

    public void addDebitPointsToMusician(Musician musician) {
        Points debitPoints = this.pointsManager.getDebitPointsFromMusician(
            musician.getEntity()
        );
        musician.setDebitPoints(debitPoints);
        LOG.debug("Debit Points set to {}", debitPoints.getValue());
    }

    public void addGainedPointsToMusician(Musician musician, LocalDate start) {
        Points gainedPoints = this.pointsManager.getGainedPointsForMonthFromMusician(
            musician.getEntity(),
            start
        );
        musician.setGainedPoints(gainedPoints);
        LOG.debug("Gained Points set to {}", gainedPoints.getValue());
    }

    /**
     * Converts {@link MusicianEntity} objects to domain {@link Musician}s.
     *
     * @param duty The duty to use
     */
    private void convertMusicianEntitiesToDomainObjects(Duty duty) {
        for (MusicianEntity entity : this.sectionMusicianEntities) {
            // Get balancePoints for musician

            // Create domain object
            Musician m = new Musician(entity);

            // add points
            addBalancePointsToMusician(m, duty.getEntity().getStart().toLocalDate());
            addDebitPointsToMusician(m);
            // Gained Points are lazily loaded because of heavy performance impact

            // Fill domain object with wish requests
            this.wishRequestManager.setMusicianWishRequest(m, duty.getEntity());

            this.sectionMusicians.add(m);
        }
    }
}
