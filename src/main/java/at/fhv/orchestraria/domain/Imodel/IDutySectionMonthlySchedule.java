package at.fhv.orchestraria.domain.Imodel;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IDutySectionMonthlySchedule {
     int getDutySectionMonthlyScheduleId();
     IDuty getDuty();
     ISectionMonthlySchedule getSectionMonthlySchedule();
}
