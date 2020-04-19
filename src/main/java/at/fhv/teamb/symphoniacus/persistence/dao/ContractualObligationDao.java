package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for ContractualObligation.
 *
 * @author Nino Heinzle
 */
public class ContractualObligationDao extends BaseDao<ContractualObligationEntity> {

    @Override
    public Optional<ContractualObligationEntity> find(Object key) {
        return Optional.empty();
    }

    @Override
    public Optional<ContractualObligationEntity> persist(ContractualObligationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<ContractualObligationEntity> update(ContractualObligationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(ContractualObligationEntity elem) {
        return null;
    }

    /**
     * Finds matching ContractualObligation to a Musician.
     *
     * @param musician given musician contains musician.Id
     * @return ContractualObligationEntity
     */
    public Optional<ContractualObligationEntity> getContractualObligation(MusicianEntity musician) {
        this.createEntityManager();
        TypedQuery<ContractualObligationEntity> query = this.entityManager.createQuery(
            "SELECT co FROM ContractualObligationEntity co WHERE co.musicianId = :musicianId",
            ContractualObligationEntity.class);
        query.setParameter("musicianId", musician.getMusicianId());
        ContractualObligationEntity co = query.getSingleResult();
        this.tearDown();

        return Optional.of(co);
    }
}
