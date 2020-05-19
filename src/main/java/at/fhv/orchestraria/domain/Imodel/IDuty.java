package at.fhv.orchestraria.domain.Imodel;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IDuty {
    int getDutyId();
    LocalDateTime getStart();
    LocalDateTime getEnd();
    String getDescription();
    String getTimeOfDay();
    IWeeklySchedule getWeeklySchedule();
    IDutyCategory getDutyCategory();
    ISeriesOfPerformances getSeriesOfPerformances();

    ISectionInstrumentation getSectionInstrumentation(int sectionID);
    boolean isSectionCompletelyAssigned(int sectionID);
    boolean isRehearsal();
    String getDutyCategoryDescription();
    String getMusicalPieceString();
    String getComposerString();
    String getInstrumentationString();
    Collection<IDutyPosition> getIDutyPositions();
    Collection<IDutySectionMonthlySchedule> getIDutySectionMonthlySchedules();
}
