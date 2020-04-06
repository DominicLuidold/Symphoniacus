package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "negativeDateWish")
public class NegativeDateWish {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "negativeDateId")
    private Integer negativeDateId;

    @Column(name = "musicianId")
    private Integer musicianId;

    @Column(name = "description")
    private String description;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;


    public Integer getNegativeDateId() {
        return this.negativeDateId;
    }

    public void setNegativeDateId(Integer negativeDateId) {
        this.negativeDateId = negativeDateId;
    }

    public Integer getMusicianId() {
        return this.musicianId;
    }

    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
