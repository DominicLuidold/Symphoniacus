package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IInstrumentCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InstrumentCategoryDao extends BaseDao<IInstrumentCategoryEntity> implements
    IInstrumentCategoryDao {
    /**
     * Finds the object based on the provided primary key.
     *
     * @param key The primary key to use
     * @return The object
     */
    @Override
    public Optional<IInstrumentCategoryEntity> find(Integer key) {
        return this.find(InstrumentCategoryEntity.class, key);
    }

    /**
     * Persists an object.
     *
     * @param elem The object to persist
     * @return Optional.empty if persisting not possible
     */
    @Override
    public Optional<IInstrumentCategoryEntity> persist(IInstrumentCategoryEntity elem) {
        return this.persist(InstrumentCategoryEntity.class, elem);
    }

    /**
     * Updates an existing object.
     *
     * @param elem The object to update
     * @return Optional.empty if updating not possible
     */
    @Override
    public Optional<IInstrumentCategoryEntity> update(IInstrumentCategoryEntity elem) {
        return this.update(InstrumentCategoryEntity.class, elem);
    }

    @Override
    public boolean remove(IInstrumentCategoryEntity elem) {
        return false;
    }

    @Override
    public synchronized List<IInstrumentCategoryEntity> getAll() {
        return new LinkedList<IInstrumentCategoryEntity>(
            this.getAll(InstrumentCategoryEntity.class));
    }
}
