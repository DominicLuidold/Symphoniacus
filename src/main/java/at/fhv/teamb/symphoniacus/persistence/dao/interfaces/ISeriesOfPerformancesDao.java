package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.util.List;

/**
 * Interface for SeriesOfPerformancesDao class.
 *
 * @author Theresa Gierer
 */
public interface ISeriesOfPerformancesDao extends Dao<ISeriesOfPerformancesEntity> {
    /**
     * Returns all {@link SeriesOfPerformancesEntity} objects.
     *
     * @return A List of series of performances entities
     */
    List<ISeriesOfPerformancesEntity> getAll();
}
