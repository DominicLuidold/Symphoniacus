package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.application.type.MusicianRoleType;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;

public interface IMusicianRole {
    void addMusician(MusicianEntity m);

    void removeMusician(MusicianEntity m);

    Integer getMusicianRoleId();

    void setMusicianRoleId(Integer musicianRoleId);

    MusicianRoleType getDescription();

    void setDescription(MusicianRoleType description);
}
