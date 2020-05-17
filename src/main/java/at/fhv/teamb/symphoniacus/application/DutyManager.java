package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.DutyCategoryDto;
import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.application.dto.InstrumentationDto;
import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.application.dto.SeriesOfPerformancesDto;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.DutyCategory;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryChangeLogDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyCategoryChangeLogDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IInstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWeeklyScheduleEntity;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for finding {@link DutyEntity} objects based on a range of time and
 * {@link SectionEntity}.
 *
 * @author Nino Heinzle
 * @author Danijel Antonijevic
 * @author Dominic Luidold
 */
public class DutyManager {
    private static final Logger LOG = LogManager.getLogger(DutyManager.class);
    private final DutyPositionManager dutyPositionManager;
    private final MonthlyScheduleManager monthlyScheduleManager;
    private final SectionMonthlyScheduleManager sectionMonthlyScheduleManager;
    private final WeeklyScheduleManager weeklyScheduleManager;
    private final IDutyCategoryChangeLogDao changeLogDao;
    private final ISeriesOfPerformancesDao seriesDao;
    private final IDutyCategoryDao dutyCategoryDao;
    private final IInstrumentationDao instrumentationDao;
    protected IDutyDao dutyDao;

    /**
     * Initializes the DutyManager (usage for Team B only).
     */
    public DutyManager() {
        this.dutyPositionManager = new DutyPositionManager();
        this.monthlyScheduleManager = new MonthlyScheduleManager();
        this.sectionMonthlyScheduleManager = new SectionMonthlyScheduleManager();
        this.weeklyScheduleManager = new WeeklyScheduleManager();
        this.changeLogDao = new DutyCategoryChangeLogDao();
        this.dutyDao = new DutyDao();
        this.seriesDao = new SeriesOfPerformancesDao();
        this.dutyCategoryDao = new DutyCategoryDao();
        this.instrumentationDao = new InstrumentationDao();
    }

    /**
     * Initializes the DutyManager (usage for Team C only).
     *
     * @param dutyPositionManager           The DutyPositionManager used in this manager
     * @param monthlyScheduleManager        The MonthlyScheduleManager used in this manager
     * @param sectionMonthlyScheduleManager The SectionMonthlyScheduleManager used in this manager
     * @param weeklyScheduleManager         The WeeklyScheduleManager used in this manager
     * @param changeLogDao                  The ChangelogDao used in this manager
     * @param dutyDao                       The DutyDao used in this manager
     * @param seriesDao                     The SeriesDao used in this manager
     * @param dutyCategoryDao               The DutyCategoryDao used in this manager
     * @param instrumentationDao            THe InstrumentationDao used in this manager
     */
    public DutyManager(
        DutyPositionManager dutyPositionManager,
        MonthlyScheduleManager monthlyScheduleManager,
        SectionMonthlyScheduleManager sectionMonthlyScheduleManager,
        WeeklyScheduleManager weeklyScheduleManager,
        IDutyCategoryChangeLogDao changeLogDao,
        IDutyDao dutyDao,
        ISeriesOfPerformancesDao seriesDao,
        IDutyCategoryDao dutyCategoryDao,
        IInstrumentationDao instrumentationDao
    ) {
        this.dutyPositionManager = dutyPositionManager;
        this.monthlyScheduleManager = monthlyScheduleManager;
        this.sectionMonthlyScheduleManager = sectionMonthlyScheduleManager;
        this.weeklyScheduleManager = weeklyScheduleManager;
        this.changeLogDao = changeLogDao;
        this.dutyDao = dutyDao;
        this.seriesDao = seriesDao;
        this.dutyCategoryDao = dutyCategoryDao;
        this.instrumentationDao = instrumentationDao;
    }

    /**
     * Returns the Monday of the week based on the {@link LocalDate}.
     *
     * @param givenDate The date to determine the monday of the week
     * @return A LocalDate representing the monday of the week
     */
    public static LocalDate getLastMondayDate(LocalDate givenDate) {
        // Will always jump back to last monday
        return givenDate.with(DayOfWeek.MONDAY);
    }

    /**
     * Converts {@link DutyEntity} objects to {@link Duty} objects.
     *
     * @param entities The entities to convert
     * @return A List of Duty objects
     */
    public static List<Duty> convertEntitiesToDomainObjects(List<IDutyEntity> entities) {
        List<Duty> duties = new LinkedList<>();
        for (IDutyEntity entity : entities) {
            duties.add(new Duty(entity));
        }
        return duties;
    }

    /**
     * Returns a loaded duty from its id.
     *
     * @param dutyId The identifier of this duty
     * @return A minimal-loaded duty
     */
    public Optional<Duty> loadDutyDetails(Integer dutyId) {
        Optional<IDutyEntity> dutyEntity = this.dutyDao.find(dutyId);
        return dutyEntity.map(Duty::new);
    }

    /**
     * Finds all duties within a full week (any Date can be given).
     * converts the given Date the last Monday
     *
     * @param start is any given Date
     * @return a List of all matching duties
     */
    public List<Duty> findAllInWeek(LocalDate start) {
        List<IDutyEntity> entityList = this.dutyDao.findAllInWeek(
            getLastMondayDate(start).atStartOfDay()
        );

        List<Duty> dutyList = new LinkedList<>();
        for (IDutyEntity entity : entityList) {
            dutyList.add(new Duty(entity));
        }

        return dutyList;
    }

    /**
     * Finds all duties in a within the week of a given Date for a section.
     *
     * @param sectionOfUser The section of the current user
     * @param start         A LocalDate that represents the start
     * @return A List of the matching duties
     */
    public List<Duty> findAllInWeekWithSection(
        SectionDto sectionOfUser,
        LocalDate start
    ) {
        SectionEntity sectionEntity = sectionDtoToSectionEntity(sectionOfUser);
        return convertEntitiesToDomainObjects(
            this.dutyDao.findAllInWeekWithSection(
                sectionEntity,
                getLastMondayDate(start).atStartOfDay(),
                false,
                false,
                false
            )
        );
    }

    /**
     * Finds all duties in a specific range of time for a section.
     *
     * @param section The section of the current user
     * @param start   A LocalDate that represents the start
     * @param end     A LocalDate that represents the end
     * @return A List of the matching duties
     */
    public List<Duty> findAllInRangeWithSection(
        SectionDto section,
        LocalDate start,
        LocalDate end
    ) {
        SectionEntity sectionEntity = sectionDtoToSectionEntity(section);
        return convertEntitiesToDomainObjects(
            this.dutyDao.findAllInRangeWithSection(
                sectionEntity,
                start.atStartOfDay(),
                end.atStartOfDay(),
                true, // TODO - Add logic to determine which parameters are true
                false,
                false
            )
        );
    }

    /**
     * Finds all duties in a specific range of time.
     *
     * @param start A LocalDate that represents the start
     * @param end   A LocalDate that represents the end
     * @return A List of the matching duties
     */
    public List<Duty> findAllInRange(LocalDate start, LocalDate end) {
        return convertEntitiesToDomainObjects(
            this.dutyDao.findAllInRange(
                start.atStartOfDay(),
                end.atStartOfDay()
            )
        );
    }

    /**
     * TODO JAVADOC.
     *
     * @return
     */
    public List<Duty> getOtherDutiesForSopOrSection(
        Duty duty,
        SectionDto section,
        Integer numberOfDuties
    ) {
        // Look whether it is a SoP or not.
        if (duty == null) {
            LOG.error("Cannot getLastDuties when duty is null");
            return new LinkedList<>();
        }

        ISeriesOfPerformancesEntity sop = duty
            .getEntity()
            .getSeriesOfPerformances();

        List<IDutyEntity> resultList;
        if (sop.getSeriesOfPerformancesId() != null) {
            // get last duties for this SoP
            resultList = this.dutyDao.getOtherDutiesForSeriesOfPerformances(
                sop,
                duty.getEntity().getStart(),
                numberOfDuties
            );
        } else {

            SectionEntity sectionEntity = sectionDtoToSectionEntity(section);
            // get last duties of section
            // TODO change this go get last 5 non-series of performances-duties
            resultList = this.dutyDao.getOtherDutiesForSection(
                duty.getEntity(),
                sectionEntity,
                numberOfDuties
            );
        }

        if (resultList == null || resultList.isEmpty()) {
            LOG.error("No results found for getOtherDutiesForSopOrSection");
            return new LinkedList<>();
        }

        return convertEntitiesToDomainObjects(resultList);
    }

    /**
     * Creates a new {@link Duty} domain object based on given data.
     *
     * @param description The description to use
     * @param timeOfDay   The time of day description
     * @param start       The start of the duty
     * @param end         The end of the duty
     * @return A duty domain object
     */
    private IDutyEntity createDuty(
        String description,
        String timeOfDay,
        LocalDateTime start,
        LocalDateTime end
    ) {
        // Get monthly schedule entity
        IMonthlyScheduleEntity monthlyScheduleEntity =
            this.monthlyScheduleManager.createIfNotExists(YearMonth.from(start.toLocalDate()));
        // Get weekly schedule entity
        IWeeklyScheduleEntity weeklyScheduleEntity =
            this.weeklyScheduleManager.createIfNotExists(start.toLocalDate(), start.getYear());

        // Add weekly schedule to monthly schedule and vice versa
        monthlyScheduleEntity.addWeeklySchedule(weeklyScheduleEntity);

        // Create duty entity
        IDutyEntity dutyEntity = new DutyEntity();

        // Add duty to weekly schedule and vice versa
        weeklyScheduleEntity.addDuty(dutyEntity);

        // Fill duty entity with data
        dutyEntity.setDescription(description);
        dutyEntity.setTimeOfDay(timeOfDay);
        dutyEntity.setStart(start);
        dutyEntity.setEnd(end);
        for (ISectionMonthlyScheduleEntity sectionMonthlySchedule :
            this.sectionMonthlyScheduleManager.createIfNotExist(
                start.getYear(),
                start.getMonthValue(),
                monthlyScheduleEntity
            )
        ) {
            dutyEntity.addSectionMonthlySchedule(sectionMonthlySchedule);
        }

        // Return domain object
        return dutyEntity;
    }

    /**
     * Persists a new {@link Duty} object to the database.
     *
     * <p>The method will subsequently change the {@link PersistenceState} of the object
     * from {@link PersistenceState#EDITED} to {@link PersistenceState#PERSISTED}, provided
     * that the database update was successful.
     */
    public DutyDto save(
        boolean userPointsChanged,
        Integer points,
        Set<InstrumentationDto> instrumentations,
        SeriesOfPerformancesDto seriesOfPerformances,
        DutyCategoryDto dutyCategory,
        String description,
        String timeOfDay,
        LocalDateTime start,
        LocalDateTime end
    ) {

        IDutyEntity duty = createDuty(description, timeOfDay, start, end);

        Optional<ISeriesOfPerformancesEntity> newSeries = this.seriesDao
            .find(seriesOfPerformances.getSeriesOfPerformancesId());
        Optional<IDutyCategoryEntity> newCategory = this.dutyCategoryDao
            .find(dutyCategory.getDutyCategoryId());
        if (newSeries.isPresent() && newCategory.isPresent()) {
            duty.setSeriesOfPerformances(newSeries.get());
            duty.setDutyCategory(newCategory.get());
        }

        this.dutyPositionManager.createDutyPositions(
            convertInstrumentationToEntityObjects(instrumentations),
            duty
        );
        Optional<IDutyEntity> persistedDuty = this.dutyDao.persist(duty);

        if (userPointsChanged) {
            if (this.changeLogDao.doesLogAlreadyExists(duty)) {
                Optional<IDutyCategoryChangelogEntity> changeLog =
                    this.changeLogDao.getChangelogByDetails(duty);
                if (changeLog.isPresent()) {
                    changeLog.get().setPoints(points);
                    changeLogDao.update(changeLog.get());
                } else {
                    LOG.error("Returned changelog is null but shouldn't be null! @save");
                }
            } else {
                IDutyCategoryChangelogEntity changeLog = new DutyCategoryChangelogEntity();
                changeLog.setDutyCategory(duty.getDutyCategory());
                changeLog.setPoints(points);
                changeLog.setStartDate(duty.getStart().toLocalDate());
                changeLogDao.persist(changeLog);
            }
        }

        DutyDto dutyDto = new DutyDto.DutyDtoBuilder()
            .withDutyId(duty.getDutyId())
            .withStart(duty.getStart())
            .withEnd(duty.getEnd())
            .withTimeOfDay(duty.getTimeOfDay())
            .withDescription(duty.getDescription())
            .build();


        if (persistedDuty.isPresent()) {
            LOG.debug(
                "Persisted duty {{}, '{}'}",
                duty.getDutyId(),
                duty.getDescription()
            );
            return fillNewDtoWithState(dutyDto, PersistenceState.PERSISTED);

        } else {
            LOG.error(
                "Could not persist duty {{}, '{}'}",
                duty.getDutyId(),
                duty.getDescription()
            );
            return fillNewDtoWithState(dutyDto, PersistenceState.EDITED);

        }
    }

    private DutyDto fillNewDtoWithState(DutyDto duty, PersistenceState state) {
        return new DutyDto.DutyDtoBuilder()
            .withDutyId(duty.getDutyId())
            .withDescription(duty.getDescription())
            .withTimeOfDay(duty.getTimeOfDay())
            .withStart(duty.getStart())
            .withPersistenceState(state)
            .build();
    }

    /**
     * Persists an existing {@link Duty} object to the database.
     *
     * <p>The method will subsequently change the {@link PersistenceState} of the object
     * from {@link PersistenceState#EDITED} to {@link PersistenceState#PERSISTED}, provided
     * that the database update was successful.
     *
     * @param duty The duty to update
     */
    public void update(Duty duty) {
        Optional<IDutyEntity> persisted = this.dutyDao.update(duty.getEntity());

        if (persisted.isPresent()) {
            duty.setPersistenceState(PersistenceState.PERSISTED);
            LOG.debug(
                "Persisted duty {{}, '{}'}",
                duty.getEntity().getDutyId(),
                duty.getTitle()
            );
        } else {
            LOG.error(
                "Could not persist duty {{}, '{}'}",
                duty.getEntity().getDutyId(),
                duty.getTitle()
            );
        }
    }

    /**
     * Checks whether a duty with the given parameters exists or not.
     *
     * @param seriesOfPerformances given Series of Performances from searched Duty.
     * @param instrumentations     given instrumentation from searched Duty.
     * @param startingDate         given starting Date from the searched Duty.
     * @param endingDate           given ending Date from searched Duty.
     * @param category             given dutyCategory from searched Duty.
     * @return whether this specific duty exists or not
     */
    public boolean doesDutyAlreadyExists(
        SeriesOfPerformancesDto seriesOfPerformances,
        List<InstrumentationDto> instrumentations,
        LocalDateTime startingDate,
        LocalDateTime endingDate,
        DutyCategoryDto category) {

        DutyCategoryEntity dutyCat = new DutyCategoryEntity();

        //Convert DTO to Entity
        dutyCat.setDutyCategoryId(category.getDutyCategoryId());
        dutyCat.setType(category.getType());
        dutyCat.setPoints(category.getPoints());

        //Convert List of instDTO to instEntity
        List<IInstrumentationEntity> instrumentationEntity = new LinkedList<>();

        for (InstrumentationDto i : instrumentations) {
            IInstrumentationEntity inst = new InstrumentationEntity();
            inst.setInstrumentationId(i.getInstrumentationId());
            inst.setName(i.getName());
        }

        Optional<ISeriesOfPerformancesEntity> series = this.seriesDao.find(
            seriesOfPerformances.getSeriesOfPerformancesId()
        );
        if (series.isPresent()) {
            return this.dutyDao.doesDutyAlreadyExists(
                series.get(),
                instrumentationEntity,
                startingDate,
                endingDate,
                dutyCat
            );
        } else {
            return false;
        }

    }

    private SectionEntity sectionDtoToSectionEntity(SectionDto section) {
        SectionEntity sectionEntity = new SectionEntity();
        sectionEntity.setSectionId(section.getSectionId());
        sectionEntity.setSectionShortcut(section.getSectionShortcut());
        sectionEntity.setDescription(section.getDescription());
        return sectionEntity;
    }

    private List<DutyCategoryDto> convertCategoriesToDto(List<DutyCategory> cats) {
        List<DutyCategoryDto> dutyCategoryDtos = new LinkedList<>();
        for (DutyCategory d : cats) {
            DutyCategoryDto dc = new DutyCategoryDto
                .DutyCategoryDtoBuilder(d.getEntity().getDutyCategoryId())
                .withType(d.getEntity().getType())
                .build();
            dutyCategoryDtos.add(dc);
        }
        return dutyCategoryDtos;
    }

    private Set<IInstrumentationEntity> convertInstrumentationToEntityObjects(
        Set<InstrumentationDto> instrumentations
    ) {
        Set<IInstrumentationEntity> newInstrumentations = new LinkedHashSet<>();
        for (InstrumentationDto i : instrumentations) {
            Optional<IInstrumentationEntity> newInst = this.instrumentationDao
                .find(i.getInstrumentationId());

            if (newInst.isPresent()) {
                newInstrumentations.add(newInst.get());
            }

        }
        return newInstrumentations;
    }

    /**
     * Converts a DutyDto to Duty Domain Object.
     * @param duty given DutyDto
     * @return Duty Domain Object
     */
    public Duty getDutyByDutyDto(DutyDto duty) {
        Optional<IDutyEntity> dutyEntity = this.dutyDao.find(duty.getDutyId());

        if (dutyEntity.isPresent()) {
            return new Duty(dutyEntity.get());
        } else {
            LOG.error("A Duty with given DutyId from Dto does not exists");
            return null;
        }
    }
}
