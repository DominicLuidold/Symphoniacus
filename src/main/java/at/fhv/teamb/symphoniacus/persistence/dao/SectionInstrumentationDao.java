package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionInstrumentationEntity;
import java.util.List;
import java.util.Optional;
import javax.persistence.TypedQuery;

public class SectionInstrumentationDao extends BaseDao<ISectionInstrumentationEntity> {

    /**
     * Finds all SectionInstrumentations matching the given Instrumentation.
     *
     * @param instrumentation given Instrumentation
     * @return a List of all SectionInstrumentations with the same InstrumentationId
     */
    public List<ISectionInstrumentationEntity> getSectionInstrumentationToInstrumentation(
        IInstrumentationEntity instrumentation) {

        TypedQuery<ISectionInstrumentationEntity> query = entityManager.createQuery(
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
    public Optional<ISectionInstrumentationEntity> find(Integer key) {
        return this.find(SectionInstrumentationEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionInstrumentationEntity> persist(ISectionInstrumentationEntity elem) {
        return this.persist(SectionInstrumentationEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ISectionInstrumentationEntity> update(ISectionInstrumentationEntity elem) {
        return this.update(SectionInstrumentationEntity.class, elem);
    }

    @Override
    public boolean remove(ISectionInstrumentationEntity elem) {
        return false;
    }
}
