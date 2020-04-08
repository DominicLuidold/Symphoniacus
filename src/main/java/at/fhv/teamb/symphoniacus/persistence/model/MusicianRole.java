package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "musicianRole")
public class MusicianRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicianRoleId")
    private Integer musicianRoleId;

    @Column(name = "description")
    private String description;

    @Column(name = "authorization")
    private String authorization;

    public Integer getMusicianRoleId() {
        return this.musicianRoleId;
    }

    public void setMusicianRoleId(Integer musicianRoleId) {
        this.musicianRoleId = musicianRoleId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
