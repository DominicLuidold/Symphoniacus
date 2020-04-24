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

    @Override
    public Optional<DutyPositionEntity> find(Object key) {
        return Optional.empty();
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
        TypedQuery<DutyPositionEntity> query = entityManager.createQuery(
            "SELECT p FROM DutyPositionEntity p "
                + "JOIN FETCH p.instrumentationPosition "
                + "LEFT JOIN FETCH p.musician m "
                + "LEFT JOIN FETCH m.user "
                + "WHERE p.duty = :duty AND p.section = :section",
            DutyPositionEntity.class
        );

        query.setParameter("duty", duty);
        query.setParameter("section", section);

        return query.getResultList();
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
