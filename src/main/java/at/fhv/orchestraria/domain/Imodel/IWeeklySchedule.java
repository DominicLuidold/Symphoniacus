package at.fhv.orchestraria.domain.Imodel;


import java.util.Collection;
import java.util.Date;

/**
 * only getters for read only access declared
 * @author Team C
 */

  public interface IWeeklySchedule {
      int getWeeklyScheduleId();
      Date getStartDate();
      Date getEndDate();
      int getYear();
      Date getPublishDate();
      boolean isConfirmed();
      IMonthlySchedule getMonthlySchedule();

      /**
       * @return Returns unmodifiable collection of duties by weekly schedule ID.
       */
      Collection<IDuty> getIDuties();
  }


