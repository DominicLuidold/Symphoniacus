package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;

public class InstrumentationDao extends BaseDao<InstrumentationEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<InstrumentationEntity> find(Integer key) {
        return this.find(InstrumentationEntity.class, key);
    }

    /**
     * Get all instrumentations belonging to a {@link SeriesOfPerformancesEntity}.
     *
     * @param series The series of performances to use
     * @return A Set of instrumentation entities
     */
    public Set<InstrumentationEntity> getAllInstrumentationsToSeries(
        SeriesOfPerformancesEntity series
    ) {
        TypedQuery<InstrumentationEntity> query = entityManager.createQuery(
            "SELECT inst FROM InstrumentationEntity inst "
                + "INNER JOIN inst.seriesOfPerformances sp "
                + "WHERE sp.seriesOfPerformancesId = :seriesId",
            InstrumentationEntity.class
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<InstrumentationEntity> persist(InstrumentationEntity elem) {
        return this.persist(InstrumentationEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<InstrumentationEntity> update(InstrumentationEntity elem) {
        return this.update(InstrumentationEntity.class, elem);
    }

    @Override
    public boolean remove(InstrumentationEntity elem) {
        return false;
    }
}
