package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
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
        return this.find(IContractualObligationEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IContractualObligationEntity> persist(IContractualObligationEntity elem) {
        return this.persist(IContractualObligationEntity.class, elem);
    }

    @Override
    public Optional<IContractualObligationEntity> update(IContractualObligationEntity elem) {
        return this.update(IContractualObligationEntity.class, elem);
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
    public IContractualObligationEntity getContractualObligation(IMusicianEntity musician) {
        TypedQuery<IContractualObligationEntity> query = entityManager.createQuery(
            "SELECT co FROM ContractualObligationEntity co "
                + "WHERE co.musician = :musician "
                + "AND co.startDate <= :currentDate "
                + "AND co.endDate >= :currentDate",
            IContractualObligationEntity.class
        );

        query.setParameter("musician", musician);
        query.setParameter("currentDate", LocalDate.now());

        return query.getSingleResult();
    }
}
