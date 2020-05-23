package at.fhv.orchestraria.domain.Imodel;

import java.sql.Date;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IDutyCategoryChangelog {
     int getDutyCategoryChangelogId();
     Date getStartDate();
     int getPoints();
     IDutyCategory getDutyCategory();
}
