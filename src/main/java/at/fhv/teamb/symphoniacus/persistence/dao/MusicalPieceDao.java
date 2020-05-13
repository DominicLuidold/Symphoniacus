package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;

public class MusicalPieceDao extends BaseDao<MusicalPieceEntity> {

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
}
