package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;

/**
 * DAO for Musicians.
 *
 * @author Valentin Goronjic
 * @author Theresa Gierer
 * @author Dominic Luidold
 */
public class MusicianDao extends BaseDao<MusicianEntity> {

    /**
     * Finds a duty by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<MusicianEntity> find(Integer key) {
        return this.find(MusicianEntity.class, key);
    }

    /**
     * Finds all {@link MusicianEntity} objects with an active {@link ContractualObligationEntity}
     * based on provided {@link SectionEntity}.
     *
     * @param section The section to use
     * @return A List of active musicians belonging to the section
     */
    public List<MusicianEntity> findAllWithSectionAndActiveContract(SectionEntity section) {
        TypedQuery<MusicianEntity> query = entityManager.createQuery(
            "SELECT m FROM MusicianEntity m "
                + "JOIN FETCH m.user "
                + "LEFT JOIN FETCH m.dutyPositions "
                + "INNER JOIN m.contractualObligations c "
                + "WHERE m.section = :section "
                + "AND c.startDate <= :startDate "
                + "AND c.endDate >= :endDate",
            MusicianEntity.class
        );
        query.setParameter("section", section);
        query.setParameter("startDate", LocalDate.now());
        query.setParameter("endDate", LocalDate.now());

        return query.getResultList();
    }

    @Override
    public Optional<MusicianEntity> persist(MusicianEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<MusicianEntity> update(MusicianEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(MusicianEntity elem) {
        return null;
    }
}
