package at.fhv.teamb.symphoniacus.persistence.dao;

import at.fhv.teamb.symphoniacus.persistence.BaseDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IAdministrativeAssistantDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;
import java.util.Optional;

/**
 * DAO for AdministrativeAssistants.
 *
 * @author Tobias Moser
 */
public class AdministrativeAssistantDao extends BaseDao<IAdministrativeAssistantEntity>
    implements IAdministrativeAssistantDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IAdministrativeAssistantEntity> find(Integer key) {
        return this.find(AdministrativeAssistantEntity.class, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IAdministrativeAssistantEntity> persist(IAdministrativeAssistantEntity elem) {
        return this.persist(AdministrativeAssistantEntity.class, elem);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<IAdministrativeAssistantEntity> update(IAdministrativeAssistantEntity elem) {
        return this.update(AdministrativeAssistantEntity.class, elem);
    }

    @Override
    public boolean remove(IAdministrativeAssistantEntity elem) {
        return false;
    }
}
