package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.time.LocalDate;
import java.util.Optional;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAO for ContractualObligation.
 *
 * @author Nino Heinzle
 */
public class ContractualObligationDao extends BaseDao<ContractualObligationEntity> {
    private static final Logger LOG = LogManager.getLogger(ContractualObligationDao.class);

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
            "SELECT co FROM ContractualObligationEntity co "
                + "WHERE co.musician = :musician "
                + "AND co.startDate <= :currentDate AND co.endDate >= :currentDate",
            ContractualObligationEntity.class
        );
        query.setParameter("musician", musician);
        query.setParameter("currentDate", LocalDate.now());
        //TODO - check if returning object is valid
        ContractualObligationEntity co = query.getSingleResult();
        this.tearDown();

        if (co.getMusician().getMusicianId().equals(musician.getMusicianId())) {
            return Optional.of(co);
        } else {
            LOG.error(
                "ContractualObligation.musicianId is not the same as givenMusician.musicianId"
            );
            return Optional.empty();
        }
    }
}
