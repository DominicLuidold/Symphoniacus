package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.domain.ActualSectionInstrumentation;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.MusicalPiece;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicianDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
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
    private final IDutyDao dutyDao;
    private final IDutyPositionDao dutyPositionDao;
    private final IMusicianDao musicianDao;
    private final IMusicalPieceDao musicalPieceDao;
    private final PointsManager pointsManager;
    private final Set<Musician> setMusicians;
    private final Set<Musician> unsetMusicians;
    private final WishRequestManager wishRequestManager;
    private List<Duty> dutiesOfThisDay;
    private List<IMusicianEntity> externalMusicianEntities;
    private List<IMusicianEntity> sectionMusicianEntities;
    private Set<Musician> sectionMusicians;

    /**
     * Initializes the DutyScheduleManager (usage for Team B only).
     */
    public DutyScheduleManager() {
        this.dutyDao = new DutyDao();
        this.dutyPositionDao = new DutyPositionDao();
        this.musicianDao = new MusicianDao();
        this.musicalPieceDao = new MusicalPieceDao();
        this.pointsManager = new PointsManager();
        this.setMusicians = new HashSet<>();
        this.unsetMusicians = new HashSet<>();
        this.wishRequestManager = new WishRequestManager();
    }

    /**
     * Initializes the DutyScheduleManager (usage for Team C only).
     *  @param dutyDao            The DutyDao used in this manager
     * @param dutyPositionDao    The DutyPositionDao used in this manager
     * @param musicianDao        The MusicianDao used in this manager
     * @param musicalPieceDao
     * @param pointsManager      The PointsManager used in this manager
     * @param wishRequestManager The WishRequestManager used in this manager
     */
    public DutyScheduleManager(
        IDutyDao dutyDao,
        IDutyPositionDao dutyPositionDao,
        IMusicianDao musicianDao,
        IMusicalPieceDao musicalPieceDao,
        PointsManager pointsManager,
        WishRequestManager wishRequestManager
    ) {
        this.dutyDao = dutyDao;
        this.dutyPositionDao = dutyPositionDao;
        this.musicianDao = musicianDao;
        this.musicalPieceDao = musicalPieceDao;
        this.pointsManager = pointsManager;
        this.setMusicians = new HashSet<>();
        this.unsetMusicians = new HashSet<>();
        this.wishRequestManager = wishRequestManager;
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
        SectionDto section
    ) {
        if (duty == null || section == null) {
            LOG.error(
                "Fetching instrumentation details not possible - either duty or section is null"
            );
            return Optional.empty();
        }
        ISectionEntity sectionEntity = new SectionEntity();
        sectionEntity.setSectionId(section.getSectionId());
        sectionEntity.setSectionShortcut(section.getSectionShortcut());
        sectionEntity.setDescription(section.getDescription());

        // Get all DutyPosition entities from database
        List<IDutyPositionEntity> dutyPositionEntities =
            this.dutyPositionDao.findCorrespondingPositions(duty.getEntity(), sectionEntity);

        // Get all Musical Pieces
        List<IMusicalPieceEntity> musicalPieces = this.musicalPieceDao.getMusicalPiecesOfDuty(
            duty.getEntity()
        );
        List<MusicalPiece> musicalPiecesDomain = new LinkedList<>();
        for (IMusicalPieceEntity entity : musicalPieces) {
            musicalPiecesDomain.add(new MusicalPiece(entity));
        }

        // Create DutyPosition domain objects
        List<DutyPosition> dutyPositions = new LinkedList<>();
        for (IDutyPositionEntity dpEntity : dutyPositionEntities) {
            DutyPosition dp = new DutyPosition(dpEntity);

            // #00 <TEXT>
            String instPosDesc = dpEntity.getInstrumentationPosition().getPositionDescription();

            int posNumber = Integer.parseInt(instPosDesc.substring(1, 3));
            String posDescription = instPosDesc.substring(4);
            posDescription = posDescription.trim();

            dp.setPositionNumber(posNumber);
            dp.setPositionDescription(posDescription);
            dutyPositions.add(dp);
        }

        // Fill Duty with available information
        Duty dutyWithInformation = new Duty(duty.getEntity(), dutyPositions, musicalPiecesDomain);
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
        Optional<IDutyEntity> persisted = this.dutyDao.update(
            instrumentation.getDuty().getEntity()
        );

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
        this.sectionMusicianEntities.addAll(this.externalMusicianEntities);
    }

    /**
     * Adds Balance Points for a monnth to a musician.
     *
     * @param musician Musician to add points
     * @param start    Start of month
     */
    public void addBalancePointsToMusician(Musician musician, LocalDate start) {
        Points balancePoints = this.pointsManager.getBalanceFromMusician(
            musician.getEntity(),
            start
        );
        musician.setBalancePoints(balancePoints);
        LOG.debug("Balance Points set to {}", balancePoints.getValue());
    }

    /**
     * Adds Debit Points to a musician.
     *
     * @param musician Musician to add points
     */
    public void addDebitPointsToMusician(Musician musician) {
        Points debitPoints = this.pointsManager.getDebitPointsFromMusician(
            musician.getEntity()
        );
        musician.setDebitPoints(debitPoints);
        LOG.debug("Debit Points set to {}", debitPoints.getValue());
    }

    /**
     * Adds Gained Points for a month to a musician.
     *
     * @param musician Musician to add points
     * @param start    Start of month
     */
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
        for (IMusicianEntity entity : this.sectionMusicianEntities) {
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
