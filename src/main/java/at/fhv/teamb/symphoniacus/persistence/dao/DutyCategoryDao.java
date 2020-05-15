package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class DutyCategoryDao extends BaseDao<IDutyCategoryEntity>
    implements IDutyCategoryDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryEntity> find(Integer key) {
        return this.find(DutyCategoryEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DutyCategoryEntity> getAll() {
        TypedQuery<DutyCategoryEntity> query = entityManager.createQuery(
            "SELECT dC FROM DutyCategoryEntity dC",
            DutyCategoryEntity.class
        );

        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<DutyCategoryEntity> getDutyCategoryFromName(String type) {
        TypedQuery<DutyCategoryEntity> query = entityManager.createQuery(
            "SELECT dc FROM DutyCategoryEntity dc "
                + "WHERE dc.type = :nameOfCategory",
            DutyCategoryEntity.class
        );

        query.setParameter("nameOfCategory", type);

        return Optional.of(query.getSingleResult());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryEntity> persist(IDutyCategoryEntity elem) {
        return this.persist(DutyCategoryEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryEntity> update(IDutyCategoryEntity elem) {
        return this.update(DutyCategoryEntity.class, elem);
    }

    @Override
    public boolean remove(IDutyCategoryEntity elem) {
        return false;
    }
}
