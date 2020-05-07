package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DutyCategoryDao extends BaseDao<DutyCategoryEntity> {

    @Override
    public Optional<DutyCategoryEntity> find(Integer key) {
        return this.find(DutyCategoryEntity.class, key);
    }

    /**
     * Returns all duty categories.
     *
     * @return A List of duty categories
     */
    public List<DutyCategoryEntity> getAll() {
        TypedQuery<DutyCategoryEntity> query = entityManager.createQuery(
            "SELECT dC FROM DutyCategoryEntity dC "
                + "JOIN FETCH dC.dutyCategoryChangelogs",
            DutyCategoryEntity.class
        );

        return query.getResultList();
    }

    @Override
    public Optional<DutyCategoryEntity> persist(DutyCategoryEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<DutyCategoryEntity> update(DutyCategoryEntity elem) {
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
    public boolean remove(DutyCategoryEntity elem) {
        return false;
    }
}
