package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.util.Optional;
import javax.persistence.TypedQuery;

/**
 * DAO for DutyCategories.
 *
 * @author : Danijel Antonijevic
 **/
public class DutyCategoryDao extends BaseDao<DutyCategoryEntity> {

    @Override
    public Optional<DutyCategoryEntity> find(Object key) {
        return Optional.empty();
    }

    @Override
    public Optional<DutyCategoryEntity> persist(DutyCategoryEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<DutyCategoryEntity> update(DutyCategoryEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(DutyCategoryEntity elem) {
        return null;
    }

    /**
     * returns the corresponding dutyCategory to the corresponding duty.
     *
     * @param duty The dutyCategory of the current duty
     * @return A Optional of the corresponding dutyCategories that were found
     */
    public Optional<DutyCategoryEntity> getCategoryFromDuty(DutyEntity duty) {
        this.createEntityManager();
        TypedQuery<DutyCategoryEntity> query = this.entityManager.createQuery(
            "SELECT dc FROM DutyCategoryEntity dc WHERE dc.dutyCategoryId = :categoryId",
            DutyCategoryEntity.class);
        query.setParameter("categoryId", duty.getDutyCategoryId());
        DutyCategoryEntity dutyCategory = query.getSingleResult();
        this.tearDown();

        return Optional.of(dutyCategory);
    }
}
