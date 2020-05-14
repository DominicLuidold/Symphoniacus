package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;

/**
 * Domain object for DutyCategoryChangelog.
 *
 * @author Dominic Luidold
 */
public class DutyCategoryChangelog {
    private final DutyCategoryChangelogEntity entity;

    public DutyCategoryChangelog(DutyCategoryChangelogEntity entity) {
        this.entity = entity;
    }

    public DutyCategoryChangelogEntity getEntity() {
        return this.entity;
    }
}
