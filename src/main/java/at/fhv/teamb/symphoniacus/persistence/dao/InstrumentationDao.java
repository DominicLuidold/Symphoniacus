package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IInstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;

public class InstrumentationDao extends BaseDao<IInstrumentationEntity>
    implements IInstrumentationDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentationEntity> find(Integer key) {
        return this.find(InstrumentationEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentationEntity> persist(IInstrumentationEntity elem) {
        return this.persist(InstrumentationEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentationEntity> update(IInstrumentationEntity elem) {
        return this.update(InstrumentationEntity.class, elem);
    }

    @Override
    public boolean remove(IInstrumentationEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<InstrumentationEntity> getAllInstrumentationsToSeries(
        ISeriesOfPerformancesEntity series
    ) {
        TypedQuery<InstrumentationEntity> query = entityManager.createQuery(
            "SELECT inst FROM InstrumentationEntity inst "
                + "JOIN FETCH inst.musicalPiece instm"
                + "INNER JOIN inst.seriesOfPerformances sp "
                + "WHERE sp.seriesOfPerformancesId = :seriesId",
            InstrumentationEntity.class
        );

        query.setParameter("seriesId", series.getSeriesOfPerformancesId());

        return new LinkedHashSet<>(query.getResultList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<InstrumentationEntity> getInstrumentationsToMusicalPieces(
        Set<IMusicalPieceEntity> musicalPieces
    ) {
        TypedQuery<InstrumentationEntity> query = entityManager.createQuery(
            "SELECT inst FROM InstrumentationEntity inst "
                + "LEFT JOIN FETCH inst.sectionInstrumentations "
                + "WHERE inst.musicalPiece IN :musicalPieces",
            InstrumentationEntity.class
        );

        query.setParameter("musicalPieces", musicalPieces);

        return new LinkedHashSet<>(query.getResultList());
    }
}
