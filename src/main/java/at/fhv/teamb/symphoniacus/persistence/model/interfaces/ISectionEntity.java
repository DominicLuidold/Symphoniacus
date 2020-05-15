package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
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

    List<MusicianEntity> getMusicians();

    void addMusician(MusicianEntity musician);

    void removeMusician(MusicianEntity musician);

    List<IDutyPositionEntity> getDutyPositions();

    void addDutyPosition(IDutyPositionEntity dutyPosition);

    void removeDutyPosition(IDutyPositionEntity dutyPosition);

    List<ISectionInstrumentationEntity> getSectionInstrumentations();

    void addSectionInstrumentation(ISectionInstrumentationEntity sectionInstrumentation);

    void removeSectionInstrumentation(ISectionInstrumentationEntity sectionInstrumentation);
}
