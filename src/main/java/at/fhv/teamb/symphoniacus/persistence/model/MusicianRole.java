package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.application.type.MusicianRoleType;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRole;
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
public class MusicianRole implements IMusicianRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicianRoleId")
    private Integer musicianRoleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "description")
    private MusicianRoleType description;

    @ManyToMany(mappedBy = "musicianRoles", targetEntity = MusicianEntity.class)
    private List<IMusicianEntity> musicians = new LinkedList<>();

    @Override
    public void addMusician(IMusicianEntity m) {
        this.musicians.add(m);
        if (!m.getMusicianRoles().contains(this)) {
            m.addMusicianRole(this);
        }
    }

    @Override
    public void removeMusician(IMusicianEntity m) {
        this.musicians.remove(m);
        m.removeMusicianRole(this);
    }

    @Override
    public Integer getMusicianRoleId() {
        return this.musicianRoleId;
    }

    @Override
    public void setMusicianRoleId(Integer musicianRoleId) {
        this.musicianRoleId = musicianRoleId;
    }

    @Override
    public MusicianRoleType getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(MusicianRoleType description) {
        this.description = description;
    }
}
