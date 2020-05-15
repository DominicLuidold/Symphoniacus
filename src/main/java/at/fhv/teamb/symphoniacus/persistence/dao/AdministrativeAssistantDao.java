package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;
import java.util.Optional;

/**
 * DAO for AdministrativeAssistants.
 *
 * @author Tobias Moser
 */
public class AdministrativeAssistantDao extends BaseDao<IAdministrativeAssistantEntity> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IAdministrativeAssistantEntity> find(Integer key) {
        return this.find(IAdministrativeAssistantEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IAdministrativeAssistantEntity> persist(IAdministrativeAssistantEntity elem) {
        return this.persist(IAdministrativeAssistantEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IAdministrativeAssistantEntity> update(IAdministrativeAssistantEntity elem) {
        return this.update(IAdministrativeAssistantEntity.class, elem);
    }

    @Override
    public boolean remove(IAdministrativeAssistantEntity elem) {
        return false;
    }
}
