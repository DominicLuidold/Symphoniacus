package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import java.util.List;
import java.util.Set;

/**
 * Interface for InstrumentationEntity class.
 *
 * @author Theresa Gierer
 */
public interface IInstrumentationEntity {
    String getName();

    IMusicalPieceEntity getMusicalPiece();

    //TODO: find out how the fuck this works
    List<IInstrumentationPositionEntity> getInstrumentationPositions();

    Integer getInstrumentationId();

    List<ISectionInstrumentationEntity> getSectionInstrumentations();

    void setInstrumentationId(int id);

    void setName(String name);

    void addSeriesOfPerformance(ISeriesOfPerformancesEntity entity);

    void removeSeriesOfPerformance(ISeriesOfPerformancesEntity entity);
}
