package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class SeriesOfPerformancesDao extends BaseDao<ISeriesOfPerformancesEntity>
    implements ISeriesOfPerformancesDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISeriesOfPerformancesEntity> find(Integer key) {
        return this.find(SeriesOfPerformancesEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    public List<ISeriesOfPerformancesEntity> getAll() {
        TypedQuery<SeriesOfPerformancesEntity> query = entityManager.createQuery(
            "SELECT sop FROM SeriesOfPerformancesEntity sop",
            SeriesOfPerformancesEntity.class
        );

        return new LinkedList<>(query.getResultList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISeriesOfPerformancesEntity> persist(ISeriesOfPerformancesEntity elem) {
        return this.persist(SeriesOfPerformancesEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISeriesOfPerformancesEntity> update(ISeriesOfPerformancesEntity elem) {
        return this.update(SeriesOfPerformancesEntity.class, elem);
    }

    @Override
    public boolean remove(ISeriesOfPerformancesEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
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

        System.out.println(query.getSingleResult());
        return (query.getSingleResult() >= 1);
    }
}
