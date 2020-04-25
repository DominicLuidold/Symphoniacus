package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for DutyPositionEntity.
 *
 * @author Dominic Luidold
 */
public class DutyPositionDao extends BaseDao<DutyPositionEntity> {

    /**
     * Finds a duty by its key.
     *
     * @param key The key of the duty
     * @return The duty that is looked for
     */
    @Override
    public Optional<DutyPositionEntity> find(Integer key) {
        return this.find(DutyPositionEntity.class, key);
    }

    /**
     * Finds all {@link DutyPositionEntity} objects based on provided {@link DutyEntity} and
     * {@link SectionEntity}.
     *
     * @param duty    The duty to use
     * @param section The section to use
     * @return A List of corresponding DutyPosition entities
     */
    public List<DutyPositionEntity> findCorrespondingPositions(
        DutyEntity duty,
        SectionEntity section
    ) {
        this.createEntityManager();
        TypedQuery<DutyPositionEntity> query = this.entityManager.createQuery(
            "SELECT p FROM DutyPositionEntity p "
                + "JOIN FETCH p.instrumentationPosition "
                + "JOIN FETCH p.musician m "
                + "JOIN FETCH m.user "
                + "WHERE p.duty = :duty AND p.section = :section",
            DutyPositionEntity.class
        );
        query.setParameter("duty", duty);
        query.setParameter("section", section);
        List<DutyPositionEntity> result = query.getResultList();
        this.tearDown();

        return result;
    }

    @Override
    public Optional<DutyPositionEntity> persist(DutyPositionEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<DutyPositionEntity> update(DutyPositionEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(DutyPositionEntity elem) {
        return false;
    }
}
