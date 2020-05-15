package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for DutyPositionEntity.
 *
 * @author Dominic Luidold
 */
public class DutyPositionDao extends BaseDao<IDutyPositionEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyPositionEntity> find(Integer key) {
        return this.find(IDutyPositionEntity.class, key);
    }

    /**
     * Finds all {@link DutyPositionEntity} objects based on provided {@link DutyEntity} and
     * {@link SectionEntity}.
     *
     * @param duty    The duty to use
     * @param section The section to use
     * @return A List of corresponding DutyPosition entities
     */
    public List<IDutyPositionEntity> findCorrespondingPositions(
        IDutyEntity duty,
        ISectionEntity section
    ) {
        TypedQuery<IDutyPositionEntity> query = entityManager.createQuery(
            "SELECT p FROM DutyPositionEntity p "
                + "JOIN FETCH p.instrumentationPosition "
                + "LEFT JOIN FETCH p.musician m "
                + "LEFT JOIN FETCH m.user "
                + "WHERE p.duty = :duty AND p.section = :section",
            IDutyPositionEntity.class
        );

        query.setParameter("duty", duty);
        query.setParameter("section", section);

        return query.getResultList();
    }

    /**
     * Finds all {@link DutyPositionEntity} objects based on provided
     * {@link SectionMonthlyScheduleEntity} that do not have any {@link MusicianEntity} set.
     *
     * @param sms The section monthly schedule to use
     * @return A List of corresponding DutyPosition entities
     */
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
        return this.persist(IDutyPositionEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyPositionEntity> update(IDutyPositionEntity elem) {
        return this.update(IDutyPositionEntity.class, elem);
    }

    @Override
    public boolean remove(IDutyPositionEntity elem) {
        return false;
    }
}
