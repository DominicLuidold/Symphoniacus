package at.fhv.orchestraria.domain.Imodel;

import java.time.LocalDate;
import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface INegativeDateWish {
     int getNegativeDateId();
     String getDescription();
     LocalDate getStartDate();
     LocalDate getEndDate();
     IMusician getMusician();

     /**
      * @return Returns unmodifiable collection of negative date wishes of monthly schedules by negative date ID.
      */
     Collection<INegativeDateMonthlySchedule> getINegativeDateMonthlySchedules();
}
