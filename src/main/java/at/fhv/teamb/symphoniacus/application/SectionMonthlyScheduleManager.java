package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.SectionMonthlySchedule;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SectionMonthlyScheduleDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import java.util.Optional;
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
}
