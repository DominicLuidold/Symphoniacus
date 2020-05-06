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
     * Finds a {@link AdministrativeAssistantEntity} by its key.
     *
     * @param key The key of the AdministrativeAssistant
     * @return The AdministrativeAssistant that is looked for
     */
    @Override
    public Optional<AdministrativeAssistantEntity> find(Integer key) {
        return this.find(AdministrativeAssistantEntity.class, key);
    }

    @Override
    public Optional<AdministrativeAssistantEntity> persist(AdministrativeAssistantEntity elem) {
        return Optional.empty();
    }

    @Override
    public Optional<AdministrativeAssistantEntity> update(AdministrativeAssistantEntity elem) {
        return Optional.empty();
    }

    @Override
    public Boolean remove(AdministrativeAssistantEntity elem) {
        return Boolean.FALSE;
    }

}
