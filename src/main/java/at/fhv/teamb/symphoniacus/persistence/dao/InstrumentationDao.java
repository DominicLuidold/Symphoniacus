package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import javax.persistence.TypedQuery;

public class InstrumentationDao extends BaseDao<InstrumentationEntity> {

    /**
     * search all instrumentations for the given musicalPieces.
     * @param musicalPieces given musicalPieces
     * @return a Set of all instrumentations to all given musicalPieces
     */
    public Set<InstrumentationEntity> getInstrumentationsToMusicalPieces(
        Set<MusicalPieceEntity> musicalPieces) {
        TypedQuery<InstrumentationEntity> query = entityManager
            .createQuery(
                "SELECT inst FROM InstrumentationEntity inst "
                    + "WHERE inst.musicalPiece IN :musicalPieces",
                InstrumentationEntity.class);

        query.setParameter("musicalPieces", musicalPieces);

        return new LinkedHashSet<>(query.getResultList());
    }

    @Override
    public Optional<InstrumentationEntity> find(Integer key) {
        return Optional.empty();
    }

    @Override
    public Optional<InstrumentationEntity> persist(InstrumentationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<InstrumentationEntity> update(InstrumentationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(InstrumentationEntity elem) {
        return null;
    }
}
