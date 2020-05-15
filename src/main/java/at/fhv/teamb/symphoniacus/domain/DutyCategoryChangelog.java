package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;

/**
 * Domain object for DutyCategoryChangelog.
 *
 * @author Dominic Luidold
 */
public class DutyCategoryChangelog {
    private final IDutyCategoryChangelogEntity entity;

    public DutyCategoryChangelog(IDutyCategoryChangelogEntity entity) {
        this.entity = entity;
    }

    public IDutyCategoryChangelogEntity getEntity() {
        return this.entity;
    }
}
