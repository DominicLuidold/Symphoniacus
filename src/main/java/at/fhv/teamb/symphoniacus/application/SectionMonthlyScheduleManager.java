package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.SectionDto;
import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SectionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SectionMonthlyScheduleDao;
import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import java.time.Month;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for obtaining and handling all instances of section monthly
 * schedules.
 *
 * @author Dominic Luidold
 */
public class SectionMonthlyScheduleManager {
    private static final Logger LOG = LogManager.getLogger(SectionMonthlyScheduleManager.class);
    private final DutyPositionDao dutyPositionDao;
    private final SectionDao sectionDao;
    private final SectionMonthlyScheduleDao smsDao;

    /**
     * Initialize the SectionMonthlyScheduleManager.
     */
    public SectionMonthlyScheduleManager() {
        this.dutyPositionDao = new DutyPositionDao();
        this.sectionDao = new SectionDao();
        this.smsDao = new SectionMonthlyScheduleDao();
    }

    /**
     * Returns a list of section monthly schedules based on provided year.
     *
     * @param section The section to use
     * @param year    The year to use
     * @return A List of section monthly schedules of this year
     */
    public Set<SectionMonthlySchedule> getSectionMonthlySchedules(SectionDto section, Year year) {
        Set<SectionMonthlySchedule> sectionMonthlySchedules = new HashSet<>();

        SectionEntity sectionEntity = new SectionEntity();
        sectionEntity.setSectionId(section.getSectionId());
        sectionEntity.setSectionShortcut(section.getSectionShortcut());
        sectionEntity.setDescription(section.getDescription());

        // Fetch section monthly schedules from database
        for (SectionMonthlyScheduleEntity smsEntity : this.smsDao
            .findAllInYear(sectionEntity, year)
        ) {
            // Convert entity to domain object
            SectionMonthlySchedule sms = new SectionMonthlySchedule(smsEntity);
            setPersistenceState(sms);
            sectionMonthlySchedules.add(sms);
        }

        return sectionMonthlySchedules;
    }

    /**
     * Returns the section monthly schedule based in provided year, month and {@link Section}.
     *
     * @param section The section to use
     * @param year    The year to use
     * @param month   The month to use
     * @return A section monthly schedule
     */
    public SectionMonthlySchedule getSectionMonthlySchedule(
        Section section,
        Year year,
        Month month
    ) {
        // Fetch section monthly schedule from database
        SectionMonthlyScheduleEntity smsEntity =
            this.smsDao.findAllInYearAndMonth(section.getEntity(), year, month);

        // Convert entity to domain object
        SectionMonthlySchedule sms = new SectionMonthlySchedule(smsEntity);
        setPersistenceState(sms);

        return sms;
    }

    /**
     * Makes the given {@link SectionMonthlySchedule} available for the organisation manager,
     * provided that all requirements are met.
     *
     * @param sectionMonthlySchedule The section monthly schedule to publish
     */
    public void makeAvailableForOrganisationManager(SectionMonthlySchedule sectionMonthlySchedule) {
        // Fetch database to get amount of positions without musicians
        Long dutyPositionsWithoutMusicians = this.dutyPositionDao
            .findCorrespondingPositionsWithoutMusician(sectionMonthlySchedule.getEntity());

        // Abort if domain object assesses that publishing is not possible
        if (!sectionMonthlySchedule.isReadyForPublishing(dutyPositionsWithoutMusicians)) {
            return;
        }

        // Update section monthly schedule internals
        sectionMonthlySchedule.getEntity().setReadyForDutyScheduler(false);
        sectionMonthlySchedule.getEntity().setReadyForOrganisationManager(true);

        // Write update to database and set publish state
        Optional<SectionMonthlyScheduleEntity> persisted =
            this.smsDao.update(sectionMonthlySchedule.getEntity());
        if (persisted.isPresent()) {
            sectionMonthlySchedule.setPublishState(
                SectionMonthlySchedule.PublishState.READY_FOR_ORGANISATION_MANAGER
            );
            LOG.debug(
                "Persisted section monthly schedule '{}'",
                sectionMonthlySchedule.getEntity().getSectionMonthlyScheduleId()
            );
        } else {
            LOG.error(
                "Could not persist section monthly schedule '{}'",
                sectionMonthlySchedule.getEntity().getSectionMonthlyScheduleId()
            );
        }
    }

    /**
     * Returns a List of section monthly schedules for the given month and year, should any exist.
     * If no section monthly schedules exists for the requested time, new ones are created.
     *
     * @param year            The year to use
     * @param month           The month to use
     * @param monthlySchedule The monthly schedule to set
     * @return A List of section monthly schedules
     */
    public List<SectionMonthlyScheduleEntity> createIfNotExist(
        int year,
        int month,
        MonthlyScheduleEntity monthlySchedule
    ) {
        // Fetch section monthly schedules from database
        List<SectionMonthlyScheduleEntity> sectionMonthlySchedules =
            this.smsDao.findAllInYearAndMonth(Year.of(year), Month.of(month));

        // Create section monthly schedules for every section
        if (sectionMonthlySchedules.isEmpty()) {
            for (SectionEntity section : this.sectionDao.getAll()) {
                // Create new section monthly schedule
                SectionMonthlyScheduleEntity sms = new SectionMonthlyScheduleEntity();
                sms.setMonthlySchedule(monthlySchedule);
                sms.setSection(section);
                sms.setReadyForDutyScheduler(false);
                sms.setReadyForOrganisationManager(false);
                sms.setPublished(false);

                sectionMonthlySchedules.add(sms);
            }
        }

        return sectionMonthlySchedules;
    }

    /**
     * Sets the {@link PersistenceState} attribute in a {@link SectionMonthlySchedule} object
     * according to the properties set in the database.
     *
     * @param sms The section monthly schedule to use
     */
    private void setPersistenceState(SectionMonthlySchedule sms) {
        // Get entity from domain object
        SectionMonthlyScheduleEntity entity = sms.getEntity();

        // Set appropriate PublishState
        if (entity.isReadyForDutyScheduler()) {
            sms.setPublishState(SectionMonthlySchedule.PublishState.READY_FOR_DUTY_SCHEDULER);
        } else if (entity.isReadyForOrganisationManager()) {
            sms.setPublishState(SectionMonthlySchedule.PublishState.READY_FOR_ORGANISATION_MANAGER);
        } else if (entity.isPublished()) {
            sms.setPublishState(SectionMonthlySchedule.PublishState.PUBLISHED);
        }
    }
}
