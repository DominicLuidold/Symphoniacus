package at.fhv.orchestraria.domain.Imodel;

import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IMusicianRole {
     int getMusicianRoleId();
     String getDescription();

     /**
      * @return Returns unmodifiable collection of musician roles of the musicians by musician role ID.
      */
     Collection<IMusicianRoleMusician> getIMusicianRoleMusicians();
}
