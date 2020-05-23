package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IInstrumentCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class InstrumentCategoryDao extends BaseDao<IInstrumentCategoryEntity>
    implements IInstrumentCategoryDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentCategoryEntity> find(Integer key) {
        return this.find(InstrumentCategoryEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentCategoryEntity> persist(IInstrumentCategoryEntity elem) {
        return this.persist(InstrumentCategoryEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IInstrumentCategoryEntity> update(IInstrumentCategoryEntity elem) {
        return this.update(InstrumentCategoryEntity.class, elem);
    }

    @Override
    public boolean remove(IInstrumentCategoryEntity elem) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized List<IInstrumentCategoryEntity> getAll() {
        return new LinkedList<>(this.getAll(InstrumentCategoryEntity.class));
    }
}
