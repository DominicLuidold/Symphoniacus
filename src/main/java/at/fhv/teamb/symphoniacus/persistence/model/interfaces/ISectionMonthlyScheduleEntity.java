package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;

public interface ISectionMonthlyScheduleEntity {
    Integer getSectionMonthlyScheduleId();

    void setSectionMonthlyScheduleId(Integer sectionMonthlyScheduleId);

    IMonthlyScheduleEntity getMonthlySchedule();

    void setMonthlySchedule(IMonthlyScheduleEntity monthlySchedule);

    boolean isReadyForDutyScheduler();

    void setReadyForDutyScheduler(boolean readyForDutyScheduler);

    boolean isReadyForOrganisationManager();

    void setReadyForOrganisationManager(boolean readyForOrganisationManager);

    boolean isPublished();

    void setPublished(boolean published);

    ISectionEntity getSection();

    void setSection(ISectionEntity section);

    List<IDutyEntity> getDuties();

    void addDuty(IDutyEntity duty);

    void removeDuty(IDutyEntity duty);
}
