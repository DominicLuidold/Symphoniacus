package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.application.type.MusicianRoleType;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "musicianRole")
public class MusicianRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicianRoleId")
    private Integer musicianRoleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "description")
    private MusicianRoleType description;

    @ManyToMany(mappedBy = "musicianRoles")
    private List<MusicianEntity> musicians = new LinkedList<>();

    public void addMusician(MusicianEntity m) {
        musicians.add(m);
        m.addMusicianRole(this);
    }

    public void removeMusician(MusicianEntity m) {
        musicians.remove(m);
        m.removeMusicianRole(this);
    }

    public Integer getMusicianRoleId() {
        return this.musicianRoleId;
    }

    public void setMusicianRoleId(Integer musicianRoleId) {
        this.musicianRoleId = musicianRoleId;
    }

    public MusicianRoleType getDescription() {
        return this.description;
    }

    public void setDescription(MusicianRoleType description) {
        this.description = description;
    }
}
