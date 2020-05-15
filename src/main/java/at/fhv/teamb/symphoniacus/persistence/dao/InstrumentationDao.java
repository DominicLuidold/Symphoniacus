package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IInstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;

public class InstrumentationDao extends BaseDao<IInstrumentationEntity> implements
    IInstrumentationDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentationEntity> find(Integer key) {
        return this.find(IInstrumentationEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentationEntity> persist(IInstrumentationEntity elem) {
        return this.persist(IInstrumentationEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentationEntity> update(IInstrumentationEntity elem) {
        return this.update(IInstrumentationEntity.class, elem);
    }

    @Override
    public boolean remove(IInstrumentationEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public Set<IInstrumentationEntity> getAllInstrumentationsToSeries(
        ISeriesOfPerformancesEntity series
    ) {
        TypedQuery<IInstrumentationEntity> query = entityManager.createQuery(
            "SELECT inst FROM InstrumentationEntity inst "
                + "JOIN FETCH inst.musicalPiece instm"
                + "INNER JOIN inst.seriesOfPerformances sp "
                + "WHERE sp.seriesOfPerformancesId = :seriesId",
            IInstrumentationEntity.class
        );

        query.setParameter("seriesId", series.getSeriesOfPerformancesId());

        return new LinkedHashSet<>(query.getResultList());
    }

    /**
     * Returns all {@link InstrumentationEntity} objects for a Set of
     * {@link MusicalPieceEntity} objects.
     *
     * @param musicalPieces given musicalPieces
     * @return A Set of all instrumentations to all given musicalPieces
     */
    public Set<InstrumentationEntity> getInstrumentationsToMusicalPieces(
        Set<MusicalPieceEntity> musicalPieces
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
