package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
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
public class ContractualObligationDao extends BaseDao<IContractualObligationEntity>
    implements IContractualObligationDao {

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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IContractualObligationEntity> update(IContractualObligationEntity elem) {
        return this.update(ContractualObligationEntity.class, elem);
    }

    @Override
    public boolean remove(IContractualObligationEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IContractualObligationEntity getContractualObligation(IMusicianEntity musician) {
        TypedQuery<ContractualObligationEntity> query = entityManager.createQuery(
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
