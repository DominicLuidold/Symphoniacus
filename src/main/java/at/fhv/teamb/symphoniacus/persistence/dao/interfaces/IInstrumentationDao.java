package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.util.Set;

/**
 * Interface for InstrumentationDao class.
 *
 * @author Theresa Gierer
 */
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
}
