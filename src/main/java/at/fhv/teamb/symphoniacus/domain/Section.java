package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;

public class Section {
    private ISectionEntity entity;

    public Section(ISectionEntity entity) {
        this.entity = entity;
    }

    public ISectionEntity getEntity() {
        return this.entity;
    }

    public void setEntity(ISectionEntity entity) {
        this.entity = entity;
    }
}
