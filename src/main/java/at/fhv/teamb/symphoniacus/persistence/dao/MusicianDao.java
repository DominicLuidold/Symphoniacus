package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for Musicians.
 *
 * @author Valentin Goronjic
 * @author Theresa Gierer
 * @author Dominic Luidold
 */
public class MusicianDao extends BaseDao<IMusicianEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianEntity> find(Integer key) {
        return this.find(MusicianEntity.class, key);
    }

    /**
     * Finds all {@link MusicianEntity} objects with an active {@link ContractualObligationEntity}
     * based on provided {@link SectionEntity}.
     *
     * @param section The section to use
     * @return A List of active musicians belonging to the section
     */
    public List<IMusicianEntity> findAllWithSectionAndActiveContract(ISectionEntity section) {
        TypedQuery<IMusicianEntity> query = entityManager.createQuery(
            "SELECT m FROM MusicianEntity m "
                + "JOIN FETCH m.user "
                + "LEFT JOIN FETCH m.dutyPositions "
                + "INNER JOIN m.contractualObligations c "
                + "WHERE m.section = :section "
                + "AND c.startDate <= :startDate "
                + "AND c.endDate >= :endDate",
            IMusicianEntity.class
        );
        query.setParameter("section", section);
        query.setParameter("startDate", LocalDate.now());
        query.setParameter("endDate", LocalDate.now());

        return query.getResultList();
    }

    /**
     * Finds all {@link MusicianEntity} objects that represent an external musician placeholder
     * based on provided {@link SectionEntity}.
     *
     * @param section The section to use
     * @return A List of external musicians belonging to the section
     */
    public List<IMusicianEntity> findExternalsWithSection(ISectionEntity section) {
        TypedQuery<IMusicianEntity> query = entityManager.createQuery(
            "SELECT m FROM MusicianEntity m "
                + "JOIN FETCH m.user u "
                + "WHERE u.firstName = :firstName "
                + "AND m.section = :section "
                + "AND m.contractualObligations IS EMPTY",
            IMusicianEntity.class
        );

        query.setParameter("firstName", "Extern");
        query.setParameter("section", section);

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianEntity> persist(IMusicianEntity elem) {
        return this.persist(IMusicianEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IMusicianEntity> update(IMusicianEntity elem) {
        return this.update(IMusicianEntity.class, elem);
    }

    @Override
    public boolean remove(IMusicianEntity elem) {
        return false;
    }
}
