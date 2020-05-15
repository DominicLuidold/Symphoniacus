package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;

public interface ISectionInstrumentationEntity {
    Integer getSectionInstrumentationId();

    void setSectionInstrumentationId(Integer sectionInstrumentationId);

    IInstrumentationEntity getInstrumentation();

    void setInstrumentation(
        IInstrumentationEntity instrumentation);

    ISectionEntity getSection();

    void setSection(ISectionEntity section);

    String getPredefinedSectionInstrumentation();

    void setPredefinedSectionInstrumentation(String predefinedSectionInstrumentation);

    List<IInstrumentationPositionEntity> getInstrumentationPositions();

    void addInstrumentationPosition(IInstrumentationPositionEntity instrumentationPosition);

    void removeInstrumentationPosition(
        IInstrumentationPositionEntity instrumentationPosition
    );
}
