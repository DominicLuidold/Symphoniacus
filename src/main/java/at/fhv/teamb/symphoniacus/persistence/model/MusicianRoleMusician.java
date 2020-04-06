package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "musicianRole_musician")
public class MusicianRoleMusician {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "musicianRole_musicianId")
    private Integer musicianRoleMusicianId;

    @Column(name = "musicianRoleId")
    private Integer musicianRoleId;

    @Column(name = "musicianId")
    private Integer musicianId;


    public Integer getMusicianRoleMusicianId() {
        return this.musicianRoleMusicianId;
    }

    public void setMusicianRoleMusicianId(Integer musicianRoleMusicianId) {
        this.musicianRoleMusicianId = musicianRoleMusicianId;
    }

    public Integer getMusicianRoleId() {
        return this.musicianRoleId;
    }

    public void setMusicianRoleId(Integer musicianRoleId) {
        this.musicianRoleId = musicianRoleId;
    }

    public Integer getMusicianId() {
        return this.musicianId;
    }

    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }
}
