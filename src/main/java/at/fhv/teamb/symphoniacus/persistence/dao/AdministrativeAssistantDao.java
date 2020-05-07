package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import java.util.Optional;

/**
 * DAO for AdministrativeAssistants.
 *
 * @author Tobias Moser
 */
public class AdministrativeAssistantDao extends BaseDao<AdministrativeAssistantEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AdministrativeAssistantEntity> find(Integer key) {
        return this.find(AdministrativeAssistantEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AdministrativeAssistantEntity> persist(AdministrativeAssistantEntity elem) {
        return this.persist(AdministrativeAssistantEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AdministrativeAssistantEntity> update(AdministrativeAssistantEntity elem) {
        return this.update(AdministrativeAssistantEntity.class, elem);
    }

    @Override
    public boolean remove(AdministrativeAssistantEntity elem) {
        return false;
    }
}
