package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyPositionDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for DutyPositionEntity.
 *
 * @author Dominic Luidold
 */
public class DutyPositionDao extends BaseDao<IDutyPositionEntity>
    implements IDutyPositionDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyPositionEntity> find(Integer key) {
        return this.find(DutyPositionEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IDutyPositionEntity> findCorrespondingPositions(
        IDutyEntity duty,
        ISectionEntity section
    ) {
        TypedQuery<DutyPositionEntity> query = entityManager.createQuery(
            "SELECT p FROM DutyPositionEntity p "
                + "JOIN FETCH p.instrumentationPosition "
                + "LEFT JOIN FETCH p.musician m "
                + "LEFT JOIN FETCH m.user "
                + "WHERE p.duty = :duty AND p.section = :section "
                + "ORDER BY p.instrumentationPosition.positionDescription ASC",
            DutyPositionEntity.class
        );

        query.setParameter("duty", duty);
        query.setParameter("section", section);

        return new LinkedList<>(query.getResultList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long findCorrespondingPositionsWithoutMusician(
        ISectionMonthlyScheduleEntity sms
    ) {
        TypedQuery<Long> query = entityManager.createQuery(
            "SELECT COUNT(p) FROM DutyPositionEntity p "
                + "INNER JOIN p.duty d "
                + "INNER JOIN d.sectionMonthlySchedules sms "
                + "WHERE p.musician IS NULL "
                + "AND sms.sectionMonthlyScheduleId = :sectionMonthlyScheduleId "
                + "AND sms.section = p.section",
            Long.class
        );

        query.setParameter("sectionMonthlyScheduleId", sms.getSectionMonthlyScheduleId());

        return query.getSingleResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyPositionEntity> persist(IDutyPositionEntity elem) {
        return this.persist(DutyPositionEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyPositionEntity> update(IDutyPositionEntity elem) {
        return this.update(DutyPositionEntity.class, elem);
    }

    @Override
    public boolean remove(IDutyPositionEntity elem) {
        return false;
    }
}
