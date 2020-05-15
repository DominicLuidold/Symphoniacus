package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.List;

public interface ISeriesOfPerformancesDao extends Dao<ISeriesOfPerformancesEntity> {

    /**
     * Returns all {@link SeriesOfPerformancesEntity} objects.
     *
     * @return A List of series of performances entities
     */
    List<? extends ISeriesOfPerformancesEntity> getAll();

    /**
     * Checks if a searched seriesOfPerformances already exists.
     *
     * @param title        The tile of the given seriesOfPerformance
     * @param startingDate The starting Date of the given seriesOfPerformance
     * @param endingDate   The ending Date of the given seriesOfPerformance
     * @return returns true if a seriesOfPerformance is found with the given inputs
     */
    boolean doesSeriesAlreadyExist(
        String title,
        LocalDate startingDate,
        LocalDate endingDate
    );
}
