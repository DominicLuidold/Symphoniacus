package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;

public interface IInstrumentationPositionEntity {
    Integer getInstrumentationPositionId();

    void setInstrumentationPositionId(int instrumentationPositionId);

    ISectionInstrumentationEntity getSectionInstrumentation();

    void setSectionInstrumentation(
        ISectionInstrumentationEntity sectionInstrumentation);

    IInstrumentationEntity getInstrumentation();

    void setInstrumentation(
        IInstrumentationEntity instrumentation
    );

    String getPositionDescription();

    void setPositionDescription(String positionDescription);

    List<IDutyPositionEntity> getDutyPositions();

    void addDutyPosition(IDutyPositionEntity dutyPosition);

    void removeDutyPosition(IDutyPositionEntity dutyPosition);
}
