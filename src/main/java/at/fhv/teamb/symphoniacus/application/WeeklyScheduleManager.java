package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.WeeklyScheduleDao;
import at.fhv.teamb.symphoniacus.persistence.model.WeeklyScheduleEntity;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeeklyScheduleManager {
    private static final Logger LOG = LogManager.getLogger(WeeklyScheduleManager.class);
    private final WeeklyScheduleDao weeklyScheduleDao;

    public WeeklyScheduleManager() {
        this.weeklyScheduleDao = new WeeklyScheduleDao();
    }

    /**
     * Returns a {@link WeeklyScheduleEntity} for the given month and year, should one exist.
     * If no weekly schedules exists for the requested week, a new one is created.
     *
     * @param day  The day to use
     * @param year The year to use
     * @return A weekly schedule entity
     */
    public WeeklyScheduleEntity createIfNotExists(LocalDate day, int year) {
        // Fetch weekly schedule from database
        Optional<WeeklyScheduleEntity> optional =
            this.weeklyScheduleDao.findForDayAndYear(day, year);
        if (optional.isPresent()) {
            // Return weekly schedule if present
            LOG.debug("Returning weekly schedule from database");
            return optional.get();
        } else {
            // Create new weekly schedule and return
            WeeklyScheduleEntity wsEntity = new WeeklyScheduleEntity();
            wsEntity.setYear(year);
            wsEntity.setStartDate(day.with(DayOfWeek.MONDAY));
            wsEntity.setEndDate(day.with(DayOfWeek.SUNDAY));
            wsEntity.setConfirmed(false);

            LOG.debug("Returning newly created weekly schedule for {} - {}",
                wsEntity.getStartDate(),
                wsEntity.getEndDate()
            );
            return wsEntity;
        }
    }
}
