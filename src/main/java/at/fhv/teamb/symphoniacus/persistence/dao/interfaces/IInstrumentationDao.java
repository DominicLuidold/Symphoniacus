package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.util.Set;

public interface IInstrumentationDao extends Dao<IInstrumentationEntity> {

    /**
     * Get all instrumentations belonging to a {@link SeriesOfPerformancesEntity}.
     *
     * @param series The series of performances to use
     * @return A Set of instrumentation entities
     */
    Set<IInstrumentationEntity> getAllInstrumentationsToSeries(
        ISeriesOfPerformancesEntity series
    );

    /**
     * Returns all {@link InstrumentationEntity} objects for a Set of
     * {@link MusicalPieceEntity} objects.
     *
     * @param musicalPieces given musicalPieces
     * @return A Set of all instrumentations to all given musicalPieces
     */
    Set<IInstrumentationEntity> getInstrumentationsToMusicalPieces(
        Set<IMusicalPieceEntity> musicalPieces
    );
}
