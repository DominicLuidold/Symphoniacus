package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.roleEnum.MusicianRoleEnum;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "musicianRole")
public class MusicianRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "musicianRoleId")
    private Integer musicianRoleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "description")
    private MusicianRoleEnum description;

    @ManyToMany(cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    })
    @JoinTable(name = "musician",
        joinColumns = @JoinColumn(name = "musicianId"),
        inverseJoinColumns = @JoinColumn(name = "musicianRoleId"))
    @Column(name = "musicianId")
    private List<Musician> musicians;

    @Column(name = "authorization")
    private String authorization;


    public MusicianRole() {
        this.musicians = new LinkedList<>();
    }

    public Integer getMusicianRoleId() {
        return this.musicianRoleId;
    }

    public void setMusicianRoleId(Integer musicianRoleId) {
        this.musicianRoleId = musicianRoleId;
    }

    public MusicianRoleEnum getDescription() {
        return this.description;
    }

    public void setDescription(MusicianRoleEnum description) {
        this.description = description;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
