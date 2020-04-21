package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for DutyCategoryChangeLog.
 *
 * @author Nino Heinzle
 */
public class DutyCategoryChangeLogDao extends BaseDao<DutyCategoryChangelogEntity> {

    @Override
    public Optional<DutyCategoryChangelogEntity> find(Object key) {
        return Optional.empty();
    }

    @Override
    public Optional<DutyCategoryChangelogEntity> persist(DutyCategoryChangelogEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<DutyCategoryChangelogEntity> update(DutyCategoryChangelogEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(DutyCategoryChangelogEntity elem) {
        return null;
    }

    /**
     * Finds all DutyCategoryChangelogs matching the id of the given DutyCategoryEntity.
     *
     * @param categoryEntity is the given DutyCategoryEntity
     * @return A List of DutyCategoryChangelogEntity
     */
    public List<DutyCategoryChangelogEntity> getDutyCategoryChangeLog(
        DutyCategoryEntity categoryEntity) {
        this.createEntityManager();
        TypedQuery<DutyCategoryChangelogEntity> query = this.entityManager.createQuery(
            "SELECT changelog FROM DutyCategoryChangelogEntity changelog"
                + " WHERE changelog.dutyCategoryId = :givenCategory",
            DutyCategoryChangelogEntity.class);

        query.setParameter("givenCategory", categoryEntity.getDutyCategoryId());
        List<DutyCategoryChangelogEntity> result = query.getResultList();
        this.tearDown();

        return result;
    }
}
