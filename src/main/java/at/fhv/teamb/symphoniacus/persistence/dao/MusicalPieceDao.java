package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;

public class MusicalPieceDao extends BaseDao<MusicalPieceEntity> {

    /**
     * Fetch every musical piece that exists from the DB.
     *
     * @return Set of MusicalPieceEntity
     */
    public Set<MusicalPieceEntity> getAllMusicalPieces() {
        TypedQuery<MusicalPieceEntity> query = entityManager
            .createQuery("SELECT mp FROM MusicalPieceEntity mp", MusicalPieceEntity.class);

        Set<MusicalPieceEntity> musicalPieces = new LinkedHashSet<>();
        musicalPieces.addAll(query.getResultList());
        return musicalPieces;
    }

    /**
     * searches all musicalPieces for a given name.
     * @param name given name of a musicalPiece
     * @return the musical piece with the same name
     */
    public Optional<MusicalPieceEntity> getMusicalPieceFromName(String name) {
        TypedQuery<MusicalPieceEntity> query = entityManager
            .createQuery("SELECT mp FROM MusicalPieceEntity mp "
                + "WHERE mp.name = :nameOfPiece", MusicalPieceEntity.class);

        query.setParameter("nameOfPiece",name);
        return Optional.of(query.getSingleResult());
    }

    @Override
    public Optional<MusicalPieceEntity> find(Integer key) {
        return this.find(MusicalPieceEntity.class,key);
    }

    @Override
    public Optional<MusicalPieceEntity> persist(MusicalPieceEntity elem) {
        return this.persist(MusicalPieceEntity.class,elem);
    }

    @Override
    public Optional<MusicalPieceEntity> update(MusicalPieceEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(MusicalPieceEntity elem) {
        return Boolean.FALSE;
    }
}
