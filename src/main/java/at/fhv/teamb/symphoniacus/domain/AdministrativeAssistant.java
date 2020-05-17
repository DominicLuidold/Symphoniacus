package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;

public class AdministrativeAssistant {

    private IAdministrativeAssistantEntity administrativeAssistantEntity;

    public AdministrativeAssistant(IAdministrativeAssistantEntity administrativeAssistantEntity) {
        this.administrativeAssistantEntity = administrativeAssistantEntity;
    }

    public IAdministrativeAssistantEntity getAdministrativeAssistantEntity() {
        return administrativeAssistantEntity;
    }
}
