package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "musician")
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicianId")
    private Integer musicianId;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "musicianRole",
        joinColumns = @JoinColumn(name = "musicianRoleId"),
        inverseJoinColumns = @JoinColumn(name = "musicianId"))
    @Column(name = "musicianId")
    private List<MusicianRole> musicianRoles;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "sectionId")
    private Integer sectionId;

    public Musician() {
        this.musicianRoles = new LinkedList<>();
    }

    public Integer getMusicianId() {
        return this.musicianId;
    }

    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    private List<MusicianRole> getMusicianRoles() {
        return this.musicianRoles;
    }

    private void addMusicianRole(MusicianRole role) {
        this.musicianRoles.add(role);
    }
}
