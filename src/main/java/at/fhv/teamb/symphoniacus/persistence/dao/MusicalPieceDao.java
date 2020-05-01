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

    public Optional<MusicalPieceEntity> getMusicalPieceFromName(String name) {
        TypedQuery<MusicalPieceEntity> query = entityManager
            .createQuery("SELECT mp FROM MusicalPieceEntity mp "
                + "WHERE mp.name = :nameOfPiece", MusicalPieceEntity.class);

        query.setParameter("nameOfPiece",name);
        Optional<MusicalPieceEntity> result = Optional.of(query.getSingleResult());
        return result;
    }

    @Override
    public Optional<MusicalPieceEntity> find(Integer key) {
        return Optional.empty();
    }

    @Override
    public Optional<MusicalPieceEntity> persist(MusicalPieceEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<MusicalPieceEntity> update(MusicalPieceEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(MusicalPieceEntity elem) {
        return null;
    }
}
