package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class SeriesOfPerformancesDao extends BaseDao<SeriesOfPerformancesEntity> {

    @Override
    public Optional<SeriesOfPerformancesEntity> find(Integer key) {
        return this.find(SeriesOfPerformancesEntity.class, key);
    }

    @Override
    public Optional<SeriesOfPerformancesEntity> persist(SeriesOfPerformancesEntity elem) {
        return this.persist(SeriesOfPerformancesEntity.class,elem);
    }

    @Override
    public Optional<SeriesOfPerformancesEntity> update(SeriesOfPerformancesEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(SeriesOfPerformancesEntity elem) {
        return null;
    }

    /**
     *Checks if a searched seriesOfPerformances already exists.
     *
     * @param title The tile of the given seriesOfPerformance
     * @param startingDate The starting Date of the given seriesOfPerformance
     * @param endingDate The ending Date of the given seriesOfPerformance
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
