package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.MonthlyScheduleDao;
import at.fhv.teamb.symphoniacus.persistence.model.MonthlyScheduleEntity;
import java.time.YearMonth;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MonthlyScheduleManager {
    private static final Logger LOG = LogManager.getLogger(MonthlyScheduleManager.class);
    private final MonthlyScheduleDao monthlyScheduleDao;

    public MonthlyScheduleManager() {
        this.monthlyScheduleDao = new MonthlyScheduleDao();
    }

    /**
     * Returns the monthly schedule for the given month and year, should one exist.
     * If no monthly schedules exists for the requested time, a new one is created.
     *
     * @param yearMonth The year and month to use
     * @return A monthly schedule entity
     */
    public MonthlyScheduleEntity createIfNotExists(YearMonth yearMonth) {
        int month = yearMonth.getMonthValue();
        int year = yearMonth.getYear();

        // Fetch monthly schedule from database
        Optional<MonthlyScheduleEntity> optional =
            this.monthlyScheduleDao.findForMonthAndYear(month, year);
        if (optional.isPresent()) {
            // Return monthly schedule if present
            LOG.debug("Returning monthly schedule from database");
            return optional.get();
        } else {
            // Create new monthly schedule and return
            MonthlyScheduleEntity msEntity = new MonthlyScheduleEntity();
            msEntity.setMonth(month);
            msEntity.setYear(year);
            msEntity.setPublished(false);
            msEntity.setEndWish(yearMonth.minusMonths(2).atEndOfMonth());
            msEntity.setEndDateClassification(yearMonth.minusMonths(1).atDay(14));

            LOG.debug("Returning newly created monthly schedule for {}, {}",
                msEntity.getYear(),
                msEntity.getMonth()
            );
            return msEntity;
        }
    }
}