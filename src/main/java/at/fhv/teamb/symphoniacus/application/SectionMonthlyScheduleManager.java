package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Section;
import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SectionMonthlyScheduleDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import java.time.Month;
import java.time.Year;
import java.util.HashSet;
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
    private final SectionMonthlyScheduleDao smsDao;

    public SectionMonthlyScheduleManager() {
        this.dutyPositionDao = new DutyPositionDao();
        this.smsDao = new SectionMonthlyScheduleDao();
    }

    /**
     * Returns a list of section monthly schedules based on provided year.
     *
     * @param section The section to use
     * @param year    The year to use
     * @return A List of section monthly schedules of this year
     */
    public Set<SectionMonthlySchedule> getSectionMonthlySchedules(Section section, Year year) {
        Set<SectionMonthlySchedule> sectionMonthlySchedules = new HashSet<>();

        // Fetch section monthly schedules from database
        for (SectionMonthlyScheduleEntity smsEntity : this.smsDao
            .findAllInYear(section.getEntity(), year)
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
     * Sets the {@link PersistenceState} attribute in a {@link SectionMonthlySchedule} object
     * according to the properties set in the database.
     *
     * @param sms The section monthly schedule to use
     */
    private void setPersistenceState(SectionMonthlySchedule sms) {
        // Get entity from domain object
        SectionMonthlyScheduleEntity entity = sms.getEntity();

        // Set appropriate PublishState
        if (Boolean.TRUE.equals(entity.isReadyForDutyScheduler())) {
            sms.setPublishState(SectionMonthlySchedule.PublishState.READY_FOR_DUTY_SCHEDULER);
        } else if (Boolean.TRUE.equals(entity.isReadyForOrganisationManager())) {
            sms.setPublishState(SectionMonthlySchedule.PublishState.READY_FOR_ORGANISATION_MANAGER);
        } else if (Boolean.TRUE.equals(entity.isPublished())) {
            sms.setPublishState(SectionMonthlySchedule.PublishState.PUBLISHED);
        }
    }
}
