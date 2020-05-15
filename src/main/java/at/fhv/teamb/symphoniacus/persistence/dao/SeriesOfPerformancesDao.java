package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class SeriesOfPerformancesDao extends BaseDao<ISeriesOfPerformancesEntity> implements
    ISeriesOfPerformancesDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISeriesOfPerformancesEntity> find(Integer key) {
        return this.find(ISeriesOfPerformancesEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    public List<ISeriesOfPerformancesEntity> getAll() {
        TypedQuery<ISeriesOfPerformancesEntity> query = entityManager.createQuery(
            "SELECT sop FROM SeriesOfPerformancesEntity sop",
            ISeriesOfPerformancesEntity.class
        );

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISeriesOfPerformancesEntity> persist(ISeriesOfPerformancesEntity elem) {
        return this.persist(ISeriesOfPerformancesEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISeriesOfPerformancesEntity> update(ISeriesOfPerformancesEntity elem) {
        return this.update(ISeriesOfPerformancesEntity.class, elem);
    }

    @Override
    public boolean remove(ISeriesOfPerformancesEntity elem) {
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
