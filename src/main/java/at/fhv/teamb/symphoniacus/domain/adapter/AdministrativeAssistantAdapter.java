package at.fhv.teamb.symphoniacus.domain.adapter;

import at.fhv.orchestraria.domain.Imodel.IAdministrativeAssistant;
import at.fhv.orchestraria.domain.Imodel.IUser;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;

public class AdministrativeAssistantAdapter implements
    IAdministrativeAssistant {
    private final IAdministrativeAssistantEntity assistant;
    private IUser user;


    public AdministrativeAssistantAdapter(
        IUser user,
        IAdministrativeAssistantEntity assistant
    ) {
        this.user = user;
        this.assistant = assistant;
    }

    @Override
    public int getUserId() {
        return this.user.getUserId();
    }

    @Override
    public String getDescription() {
        return this.assistant.getDescription().toString();
    }

    @Override
    public IUser getUser() {
        return this.user;
    }
}
