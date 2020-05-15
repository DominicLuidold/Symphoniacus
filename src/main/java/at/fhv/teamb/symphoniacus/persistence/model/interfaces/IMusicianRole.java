package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.application.type.MusicianRoleType;

public interface IMusicianRole {
    void addMusician(IMusicianEntity m);

    void removeMusician(IMusicianEntity m);

    Integer getMusicianRoleId();

    void setMusicianRoleId(Integer musicianRoleId);

    MusicianRoleType getDescription();

    void setDescription(MusicianRoleType description);
}
