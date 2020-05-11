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
     *
     * @param instrumentation given Instrumentation
     * @return a List of all SectionInstrumentations with the same InstrumentationId
     */
    public List<SectionInstrumentationEntity> getSectionInstrumentationToInstrumentation(
        InstrumentationEntity instrumentation) {

        TypedQuery<SectionInstrumentationEntity> query = entityManager.createQuery(
            "SELECT si from SectionInstrumentationEntity si "
                + "WHERE si.instrumentation = :inst",
            SectionInstrumentationEntity.class
        );
        query.setParameter("inst", instrumentation);
        return query.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionInstrumentationEntity> find(Integer key) {
        return this.find(SectionInstrumentationEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionInstrumentationEntity> persist(SectionInstrumentationEntity elem) {
        return this.persist(SectionInstrumentationEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SectionInstrumentationEntity> update(SectionInstrumentationEntity elem) {
        return this.update(SectionInstrumentationEntity.class, elem);
    }

    @Override
    public boolean remove(SectionInstrumentationEntity elem) {
        return false;
    }
}
