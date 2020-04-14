package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dutyCategoryChangelog")
public class DutyCategoryChangelog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyCategoryChangelogId")
    private Integer dutyCategoryChangelogId;

    @Column(name = "dutyCategoryId")
    private Integer dutyCategoryId;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "points")
    private Integer points;

    public Integer getDutyCategoryChangelogId() {
        return this.dutyCategoryChangelogId;
    }

    public void setDutyCategoryChangelogId(Integer dutyCategoryChangelogId) {
        this.dutyCategoryChangelogId = dutyCategoryChangelogId;
    }

    public Integer getDutyCategoryId() {
        return this.dutyCategoryId;
    }

    public void setDutyCategoryId(Integer dutyCategoryId) {
        this.dutyCategoryId = dutyCategoryId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
