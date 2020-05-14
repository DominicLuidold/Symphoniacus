package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class SeriesOfPerformancesDao extends BaseDao<SeriesOfPerformancesEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SeriesOfPerformancesEntity> find(Integer key) {
        return this.find(SeriesOfPerformancesEntity.class, key);
    }

    /**
     * Returns all {@link SeriesOfPerformancesEntity} objects.
     *
     * @return A List of series of performances entities
     */
    public List<SeriesOfPerformancesEntity> getAll() {
        TypedQuery<SeriesOfPerformancesEntity> query = entityManager.createQuery(
            "SELECT sop FROM SeriesOfPerformancesEntity sop",
            SeriesOfPerformancesEntity.class
        );

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SeriesOfPerformancesEntity> persist(SeriesOfPerformancesEntity elem) {
        return this.persist(SeriesOfPerformancesEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SeriesOfPerformancesEntity> update(SeriesOfPerformancesEntity elem) {
        return this.update(SeriesOfPerformancesEntity.class, elem);
    }

    @Override
    public boolean remove(SeriesOfPerformancesEntity elem) {
        return false;
    }

    /**
     * Checks if a searched seriesOfPerformances already exists.
     *
     * @param title        The tile of the given seriesOfPerformance
     * @param startingDate The starting Date of the given seriesOfPerformance
     * @param endingDate   The ending Date of the given seriesOfPerformance
     * @return returns true if a seriesOfPerformance is found with the given inputs
     */
    public boolean doesSeriesAlreadyExist(
        String title,
        LocalDate startingDate,
        LocalDate endingDate
    ) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(sp) FROM SeriesOfPerformancesEntity sp "
                + "WHERE UPPER(sp.description) = :title "
                + "AND sp.startDate = :sDate "
                + "AND sp.endDate =: eDate",
            Long.class
        );

        query.setParameter("title", title.toUpperCase());
        query.setParameter("sDate", startingDate);
        query.setParameter("eDate", endingDate);

        return (query.getSingleResult() >= 1);
    }
}
