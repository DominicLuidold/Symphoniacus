package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;
import java.util.Set;

public interface ISectionMonthlyScheduleEntity {

    void setMonthlySchedule(IMonthlyScheduleEntity entity);

    void setSection(ISectionEntity section);

    void setReadyForDutyScheduler(boolean value);

    void setReadyForOrganisationManager(boolean value);

    void setPublished(boolean value);

    List<IDutyEntity> getDuties();

}
