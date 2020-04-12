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
import javax.persistence.Transient;

@Entity
@Table(name = "musician")
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicianId")
    private Integer musicianId;

    @ManyToMany
    @JoinTable(
        name = "musicianRole_musician",
        joinColumns = {
            @JoinColumn(name = "musicianId")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "musicianRoleId")
        }
    )
    private List<MusicianRole> musicianRoles = new LinkedList<>();

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "sectionId")
    private Integer sectionId;

    @Transient
    private List<Section> section;

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

    public List<MusicianRole> getMusicianRoles() {
        return this.musicianRoles;
    }

    public void addMusicianRole(MusicianRole role) {
        this.musicianRoles.add(role);
        role.addMusician(this);
    }

    public void removeMusicianRole(MusicianRole role) {
        this.musicianRoles.remove(role);
        role.removeMusician(this);
    }

    public void setSection(List<Section> section) {
        this.section = section;
    }

    public List<Section> getSection() {
        return this.section;
    }
}
