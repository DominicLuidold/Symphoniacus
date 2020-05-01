package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import java.util.Optional;

public class InstrumentationDao extends BaseDao<InstrumentationEntity> {
    @Override
    public Optional<InstrumentationEntity> find(Integer key) {
        return Optional.empty();
    }

    @Override
    public Optional<InstrumentationEntity> persist(InstrumentationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<InstrumentationEntity> update(InstrumentationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(InstrumentationEntity elem) {
        return null;
    }
}
