package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import java.util.Optional;
import javax.persistence.EntityTransaction;

public class SectionMonthlyScheduleDao extends BaseDao<SectionMonthlyScheduleEntity> {

    @Override
    public Optional<SectionMonthlyScheduleEntity> find(Object key) {
        return Optional.empty();
    }

    @Override
    public Optional<SectionMonthlyScheduleEntity> persist(SectionMonthlyScheduleEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<SectionMonthlyScheduleEntity> update(SectionMonthlyScheduleEntity elem) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(elem);
            transaction.commit();
            return Optional.of(elem);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        }
        return Optional.empty();
    }

    @Override
    public Boolean remove(SectionMonthlyScheduleEntity elem) {
        return false;
    }
}
