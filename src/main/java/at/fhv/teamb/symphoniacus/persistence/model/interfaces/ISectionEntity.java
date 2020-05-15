package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;

public interface ISectionEntity {
    Integer getSectionId();

    void setSectionId(Integer sectionId);

    String getSectionShortcut();

    void setSectionShortcut(String sectionShortcut);

    String getDescription();

    void setDescription(String description);

    List<ISectionMonthlyScheduleEntity> getSectionMonthlySchedules();

    void addSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule);

    void removeSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule);

    List<IMusicianEntity> getMusicians();

    void addMusician(IMusicianEntity musician);

    void removeMusician(IMusicianEntity musician);

    List<IDutyPositionEntity> getDutyPositions();

    void addDutyPosition(IDutyPositionEntity dutyPosition);

    void removeDutyPosition(IDutyPositionEntity dutyPosition);

    List<ISectionInstrumentationEntity> getSectionInstrumentations();

    void addSectionInstrumentation(ISectionInstrumentationEntity sectionInstrumentation);

    void removeSectionInstrumentation(ISectionInstrumentationEntity sectionInstrumentation);
}
