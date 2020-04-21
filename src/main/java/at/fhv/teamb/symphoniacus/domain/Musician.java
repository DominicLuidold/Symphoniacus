package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import java.util.List;

/**
 * Domain object for Musician.
 *
 * @author Dominic Luidold
 */
public class Musician {
    private final UserEntity userEntity;
    private MusicianEntity entity;

    public Musician(MusicianEntity entity) {
        this.entity = entity;
        this.userEntity = entity.getUser();
    }

    /**
     * Returns the full name of the {@link Musician} consisting of a {@link UserEntity}'s first
     * name and last name.
     *
     * @return Full name of a musician
     */
    public String getFullName() {
        return this.userEntity.getFirstName() + " " + this.userEntity.getLastName();
    }

    public String getShortcut() {
        return this.userEntity.getShortcut();
    }

    public List<DutyPositionEntity> getAssignedDutyPositions() {
        return this.entity.getDutyPositions();
    }

    public MusicianEntity getEntity() {
        return this.entity;
    }
}
