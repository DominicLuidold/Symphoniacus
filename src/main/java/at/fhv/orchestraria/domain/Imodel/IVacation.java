package at.fhv.orchestraria.domain.Imodel;

import java.sql.Date;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IVacation {
     int getVacationId();
     Date getStartDate();
     Date getEndDate();
     boolean isConfirmed();
     IMusician getMusician();
}
