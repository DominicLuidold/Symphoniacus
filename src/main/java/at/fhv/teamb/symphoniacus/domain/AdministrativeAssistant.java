package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;

public class AdministrativeAssistant {

    private  AdministrativeAssistantEntity administrativeAssistantEntity;

    public AdministrativeAssistant(AdministrativeAssistantEntity administrativeAssistantEntity) {
        this.administrativeAssistantEntity = administrativeAssistantEntity;
    }

    public AdministrativeAssistantEntity getAdministrativeAssistantEntity() {
        return administrativeAssistantEntity;
    }
}
