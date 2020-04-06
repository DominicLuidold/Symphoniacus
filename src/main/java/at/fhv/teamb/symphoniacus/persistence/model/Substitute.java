package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "substitute")
public class Substitute {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "substituteId")
    private Integer substituteId;

    @Column(name = "musicianId")
    private Integer musicianId;

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


    public Integer getSubstituteId() {
        return this.substituteId;
    }

    public void setSubstituteId(Integer substituteId) {
        this.substituteId = substituteId;
    }

    public Integer getMusicianId() {
        return this.musicianId;
    }

    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
