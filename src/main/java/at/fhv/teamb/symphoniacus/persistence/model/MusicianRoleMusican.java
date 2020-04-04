package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "musicianRole_musican")
public class MusicianRoleMusican {
    @Id
    @Column(name = "musicianRole_musicanId")
    private Integer musicianRoleMusicanId;

    @Column(name = "musicianRoleId")
    private Integer musicianRoleId;

    @Column(name = "musicanId")
    private Integer musicanId;


    public Integer getMusicianRoleMusicanId() {
        return this.musicianRoleMusicanId;
    }

    public void setMusicianRoleMusicanId(Integer musicianRoleMusicanId) {
        this.musicianRoleMusicanId = musicianRoleMusicanId;
    }

    public Integer getMusicianRoleId() {
        return this.musicianRoleId;
    }

    public void setMusicianRoleId(Integer musicianRoleId) {
        this.musicianRoleId = musicianRoleId;
    }

    public Integer getMusicanId() {
        return this.musicanId;
    }

    public void setMusicanId(Integer musicanId) {
        this.musicanId = musicanId;
    }
}
