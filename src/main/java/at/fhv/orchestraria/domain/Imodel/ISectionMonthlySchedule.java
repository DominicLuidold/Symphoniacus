package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface ISectionMonthlySchedule {
     int getSectionMonthlyScheduleId();
     boolean isReadyForDutyScheduler();
     boolean isReadyForOrganisationManager();
     boolean isPublished();
     IMonthlySchedule getMonthlySchedule();
     ISection getSection();

     /**
      * @return Returns unmodifiable collection of section monthly schedule duties by series of performances ID
      */
     Collection<IDutySectionMonthlySchedule> getIDutySectionMonthlySchedules();
}
