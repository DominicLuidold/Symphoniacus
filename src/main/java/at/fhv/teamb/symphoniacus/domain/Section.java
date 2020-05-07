package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;

public class Section {
    private SectionEntity entity;

    public Section(SectionEntity entity) {
        this.entity = entity;
    }

    public SectionEntity getEntity() {
        return this.entity;
    }

    public void setEntity(SectionEntity entity) {
        this.entity = entity;
    }
}
