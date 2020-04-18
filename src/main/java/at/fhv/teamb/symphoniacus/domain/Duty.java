package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;

public class Duty {

    private DutyEntity entity;

    public Duty(DutyEntity entity) {
        this.entity = entity;
    }

    public DutyEntity getEntity() {
        return entity;
    }

    public void setEntity(DutyEntity entity) {
        this.entity = entity;
    }
    
    public String getTitle() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
