package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.application.type.AdministrativeAssistantType;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;

/**
 * @author Valentin
 */
public interface IAdministrativeAssistantEntity {
    UserEntity getUser();

    void setUser(UserEntity user);

    AdministrativeAssistantType getDescription();

    void setDescription(AdministrativeAssistantType description);
}
