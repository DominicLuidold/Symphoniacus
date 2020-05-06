package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionInstrumentationEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class SectionInstrumentationDao extends BaseDao<SectionInstrumentationEntity> {

    /**
     * Finds all SectionInstrumentations matching the given Instrumentation.
     * @param instrumentation given Instrumentation
     * @return a List of all SectionInstrumentations with the same InstrumentationId
     */
    public Optional<List<SectionInstrumentationEntity>> getSectionInstrumentationToInstrumentation(
        InstrumentationEntity instrumentation) {

        TypedQuery<SectionInstrumentationEntity> query = entityManager.createQuery(
            "SELECT si from SectionInstrumentationEntity si "
                + "WHERE si.instrumentation = :inst",
            SectionInstrumentationEntity.class
        );
        query.setParameter("inst", instrumentation);
        return Optional.of(query.getResultList());
    }

    @Override
    public Optional<SectionInstrumentationEntity> find(Integer key) {
        return Optional.empty();
    }

    @Override
    public Optional<SectionInstrumentationEntity> persist(SectionInstrumentationEntity elem) {
        return this.persist(SectionInstrumentationEntity.class,elem);
    }

    @Override
    public Optional<SectionInstrumentationEntity> update(SectionInstrumentationEntity elem) {
        return Optional.empty();
    }

    @Override
    public boolean remove(SectionInstrumentationEntity elem) {
        return false;
    }
}
