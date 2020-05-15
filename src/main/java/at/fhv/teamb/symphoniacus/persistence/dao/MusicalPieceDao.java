package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;

public class MusicalPieceDao extends BaseDao<IMusicalPieceEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicalPieceEntity> find(Integer key) {
        return this.find(MusicalPieceEntity.class, key);
    }

    /**
     * Fetches all {@link MusicalPieceEntity} objects.
     *
     * @return A Set of MusicalPieceEntity
     */
    public Set<IMusicalPieceEntity> getAll() {
        TypedQuery<IMusicalPieceEntity> query = entityManager.createQuery(
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
    public Optional<IMusicalPieceEntity> getMusicalPieceFromName(String name) {
        TypedQuery<IMusicalPieceEntity> query = entityManager.createQuery(
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
    public Optional<IMusicalPieceEntity> persist(IMusicalPieceEntity elem) {
        return this.persist(MusicalPieceEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicalPieceEntity> update(IMusicalPieceEntity elem) {
        return this.update(MusicalPieceEntity.class, elem);
    }

    @Override
    public boolean remove(IMusicalPieceEntity elem) {
        return false;
    }
}
