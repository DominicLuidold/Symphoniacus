package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface INegativeDateMonthlySchedule {
     int getNegativeDateMonthlyScheduleId();
     IMonthlySchedule getMonthlySchedule();
     INegativeDateWish getNegativeDateWish();
}
