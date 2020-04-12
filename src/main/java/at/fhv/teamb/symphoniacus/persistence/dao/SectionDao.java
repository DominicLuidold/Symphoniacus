package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.Section;
import java.util.Optional;

public class SectionDao extends BaseDao<Section> {
    @Override
    public Optional<Section> find(Object key) {
        return Optional.empty();
    }

    @Override
    public Optional<Section> persist(Section elem) {
        return Optional.empty();
    }

    @Override
    public Optional<Section> update(Section elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(Section elem) {
        return null;
    }
}
