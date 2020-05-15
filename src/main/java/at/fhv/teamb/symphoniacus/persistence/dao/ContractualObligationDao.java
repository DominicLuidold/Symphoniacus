package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for ContractualObligation.
 *
 * @author Nino Heinzle
 */
public class ContractualObligationDao extends BaseDao<IContractualObligationEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IContractualObligationEntity> find(Integer key) {
        return this.find(ContractualObligationEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IContractualObligationEntity> persist(IContractualObligationEntity elem) {
        return this.persist(ContractualObligationEntity.class, elem);
    }

    @Override
    public Optional<IContractualObligationEntity> update(IContractualObligationEntity elem) {
        return this.update(ContractualObligationEntity.class, elem);
    }

    @Override
    public boolean remove(IContractualObligationEntity elem) {
        return false;
    }

    /**
     * Finds matching ContractualObligation to a Musician.
     *
     * @param musician given musician contains musician.Id
     * @return ContractualObligationEntity
     */
    public IContractualObligationEntity getContractualObligation(MusicianEntity musician) {
        TypedQuery<IContractualObligationEntity> query = entityManager.createQuery(
            "SELECT co FROM ContractualObligationEntity co "
                + "WHERE co.musician = :musician "
                + "AND co.startDate <= :currentDate "
                + "AND co.endDate >= :currentDate",
            ContractualObligationEntity.class
        );

        query.setParameter("musician", musician);
        query.setParameter("currentDate", LocalDate.now());

        return query.getSingleResult();
    }
}
