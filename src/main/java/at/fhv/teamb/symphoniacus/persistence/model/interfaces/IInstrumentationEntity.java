package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;

public interface IInstrumentationEntity {
    Integer getInstrumentationId();

    void setInstrumentationId(int instrumentationId);

    String getName();

    void setName(String name);

    IMusicalPieceEntity getMusicalPiece();

    void setMusicalPiece(IMusicalPieceEntity musicalPiece);

    List<ISectionInstrumentationEntity> getSectionInstrumentations();

    void addSectionInstrumentation(
        ISectionInstrumentationEntity sectionInstrumentation
    );

    void removeSectionInstrumentation(
        ISectionInstrumentationEntity sectionInstrumentation
    );

    List<IInstrumentationPositionEntity> getInstrumentationPositions();

    void addInstrumentationPosition(
        IInstrumentationPositionEntity instrumentationPosition
    );

    void removeInstrumentationPosition(
        IInstrumentationPositionEntity instrumentationPosition
    );

    List<ISeriesOfPerformancesEntity> getSeriesOfPerformances();

    void setSeriesOfPerformances(
        List<ISeriesOfPerformancesEntity> seriesOfPerformances);

    void addSeriesOfPerformance(ISeriesOfPerformancesEntity series);

    void removeSeriesOfPerformance(ISeriesOfPerformancesEntity series);
}
