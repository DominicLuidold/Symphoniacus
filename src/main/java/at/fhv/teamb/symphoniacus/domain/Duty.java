package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;

public class Duty {
    private DutyEntity entity;

    public Duty(DutyEntity entity) {
        this.entity = entity;
    }

    // TODO - Domain logic

    public DutyEntity getEntity() {
        return this.entity;
    }

    public void setEntity(DutyEntity entity) {
        this.entity = entity;
    }
}
