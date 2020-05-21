package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISubstitute;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "substitute")
public class Substitute implements ISubstitute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "substituteId")
    private Integer substituteId;
/*
    @Column(name = "musicianId")
    private Integer musicianId;

 */

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MusicianEntity.class)
    @JoinColumn(name = "musicianId")
    private IMusicianEntity musician;

    public IMusicianEntity getMusician() {
        return musician;
    }

    public void setMusician(IMusicianEntity musician) {
        this.musician = musician;
    }

    @Override
    public Integer getSubstituteId() {
        return this.substituteId;
    }

    @Override
    public void setSubstituteId(Integer substituteId) {
        this.substituteId = substituteId;
    }
/*
    @Override
    public Integer getMusicianId() {
        return this.musicianId;
    }

    @Override
    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }

 */

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhone() {
        return this.phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
