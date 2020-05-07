package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class DutyCategoryDao extends BaseDao<DutyCategoryEntity> {

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DutyCategoryEntity> persist(DutyCategoryEntity elem) {
        return this.persist(DutyCategoryEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DutyCategoryEntity> update(DutyCategoryEntity elem) {
        return this.update(DutyCategoryEntity.class, elem);
    }

    @Override
    public boolean remove(DutyCategoryEntity elem) {
        return false;
    }
}
