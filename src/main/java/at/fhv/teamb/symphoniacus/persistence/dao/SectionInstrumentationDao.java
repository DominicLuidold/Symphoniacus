package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.SectionInstrumentationEntity;
import java.util.Optional;

public class SectionInstrumentationDao extends BaseDao<SectionInstrumentationEntity> {
    @Override
    public Optional<SectionInstrumentationEntity> find(Integer key) {
        return Optional.empty();
    }

    @Override
    public Optional<SectionInstrumentationEntity> persist(SectionInstrumentationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<SectionInstrumentationEntity> update(SectionInstrumentationEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(SectionInstrumentationEntity elem) {
        return null;
    }
}
