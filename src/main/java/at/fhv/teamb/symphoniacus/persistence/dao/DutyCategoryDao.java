package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class DutyCategoryDao extends BaseDao<IDutyCategoryEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IDutyCategoryEntity> find(Integer key) {
        return this.find(DutyCategoryEntity.class, key);
    }

    /**
     * Returns all duty categories.
     *
     * @return A List of duty categories
     */
    public List<IDutyCategoryEntity> getAll() {
        TypedQuery<IDutyCategoryEntity> query = entityManager.createQuery(
            "SELECT dC FROM DutyCategoryEntity dC",
            DutyCategoryEntity.class
        );

        return query.getResultList();
    }


    /**
     * searches all dutyCategories for a given name.
     *
     * @param type given name of a dutyCategories
     * @return the musical piece with the same name
     */
    public Optional<IDutyCategoryEntity> getDutyCategoryFromName(String type) {
        TypedQuery<IDutyCategoryEntity> query = entityManager.createQuery(
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
