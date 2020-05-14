package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MusicalPieceDao extends BaseDao<MusicalPieceEntity> {

    private static final Logger LOG = LogManager.getLogger(MusicalPieceDao.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MusicalPieceEntity> find(Integer key) {
        return this.find(MusicalPieceEntity.class, key);
    }

    /**
     * Fetches all {@link MusicalPieceEntity} objects.
     *
     * @return A Set of MusicalPieceEntity
     */
    public Set<MusicalPieceEntity> getAll() {
        TypedQuery<MusicalPieceEntity> query = entityManager.createQuery(
            "SELECT mp FROM MusicalPieceEntity mp",
            MusicalPieceEntity.class
        );

        return new LinkedHashSet<>(query.getResultList());
    }

    /**
     * Returns all {@link MusicalPieceEntity} objects for a given name.
     *
     * @param name The name to search for
     * @return A musical piece with the same name
     */
    public Optional<MusicalPieceEntity> getMusicalPieceFromName(String name) {
        TypedQuery<MusicalPieceEntity> query = entityManager.createQuery(
            "SELECT mp FROM MusicalPieceEntity mp "
                + "WHERE mp.name = :nameOfPiece",
            MusicalPieceEntity.class
        );

        query.setParameter("nameOfPiece", name);

        return Optional.of(query.getSingleResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MusicalPieceEntity> persist(MusicalPieceEntity elem) {
        return this.persist(MusicalPieceEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<MusicalPieceEntity> update(MusicalPieceEntity elem) {
        return this.update(MusicalPieceEntity.class, elem);
    }

    @Override
    public boolean remove(MusicalPieceEntity elem) {
        return false;
    }

    /**
     * Gets the musical pieces of a duty (via Series of Performances).
     *
     * @param dutyEntity The duty for which the musical pieces should be loaded
     * @return List of {@link MusicalPieceEntity} objects (empty when no series of performances)
     */
    public List<MusicalPieceEntity> getMusicalPiecesOfDuty(DutyEntity dutyEntity) {
        TypedQuery<MusicalPieceEntity> query = entityManager.createQuery(
            "SELECT m from DutyEntity d "
            + "INNER JOIN d.seriesOfPerformances sop "
            + "INNER JOIN sop.musicalPieces m "
            + "WHERE d.dutyId = :dutyId",
            MusicalPieceEntity.class
        );
        query.setParameter("dutyId", dutyEntity.getDutyId());
        List<MusicalPieceEntity> result = query.getResultList();
        LOG.debug("{} Musical pieces for duty found", result.size());


        return result;
    }
}
