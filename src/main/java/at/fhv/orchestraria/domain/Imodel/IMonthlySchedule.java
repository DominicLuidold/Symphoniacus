package at.fhv.orchestraria.domain.Imodel;

import java.sql.Date;
import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IMonthlySchedule {
     int getMonthlyScheduleId();
     int getMonth();
     int getYear();
     Date getPublishDate();
     Date getEndDateClassification();
     boolean isPublished();
     Date getEndWish();

     /**
      * @return Returns unmodifiable collection of negative date wishes of monthly schedules by monthly schedule ID.
      */
     Collection<INegativeDateMonthlySchedule> getINegativeDateMonthlySchedules();

     /**
      * @return Returns unmodifiable collection of section monthly schedules by monthly schedule ID.
      */
     Collection<ISectionMonthlySchedule> getISectionMonthlySchedules();

     /**
      * @return Returns unmodifiable collection of weekly schedules by monthly schedule ID.
      */
     Collection<IWeeklySchedule> getIWeeklySchedules();
}
