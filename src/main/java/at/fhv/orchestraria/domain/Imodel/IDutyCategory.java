package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IDutyCategory {
     int getDutyCategoryId();
     String getType();
     boolean isRehearsal();
     int getPoints();

     /**
      * @return Returns unmodifiable collection of duties by duty category ID
      */
     Collection<IDuty> getIDuties();

     /**
      * @return Returns unmodifiable collection of duty category changelogs by duty category ID
      */
     Collection<IDutyCategoryChangelog> getIDutyCategoryChangelogs();
}
