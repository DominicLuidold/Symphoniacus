package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.application.type.AdministrativeAssistantType;

public interface IAdministrativeAssistantEntity {
    IUserEntity getUser();

    void setUser(IUserEntity user);

    AdministrativeAssistantType getDescription();

    void setDescription(AdministrativeAssistantType description);
}
