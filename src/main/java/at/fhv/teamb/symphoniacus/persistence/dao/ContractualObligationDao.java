package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for ContractualObligation.
 *
 * @author Nino Heinzle
 */
public class ContractualObligationDao extends BaseDao<ContractualObligationEntity> {

    /**
     * Finds a {@link ContractualObligationEntity} by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<ContractualObligationEntity> find(Integer key) {
        return this.find(ContractualObligationEntity.class, key);
    }

    @Override
    public Optional<ContractualObligationEntity> persist(ContractualObligationEntity elem) {
        return this.persist(ContractualObligationEntity.class,elem);
    }

    @Override
    public Optional<ContractualObligationEntity> update(ContractualObligationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(ContractualObligationEntity elem) {
        return Boolean.FALSE;
    }

    /**
     * Finds matching ContractualObligation to a Musician.
     *
     * @param musician given musician contains musician.Id
     * @return ContractualObligationEntity
     */
    public ContractualObligationEntity getContractualObligation(MusicianEntity musician) {
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
