package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import java.time.LocalDate;
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
@Table(name = "dutyCategoryChangelog")
public class DutyCategoryChangelogEntity implements IDutyCategoryChangelogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyCategoryChangelogId")
    private Integer dutyCategoryChangelogId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = DutyCategoryEntity.class)
    @JoinColumn(name = "dutyCategoryId")
    private IDutyCategoryEntity dutyCategory;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "points")
    private Integer points;

    @Override
    public Integer getDutyCategoryChangelogId() {
        return this.dutyCategoryChangelogId;
    }

    @Override
    public void setDutyCategoryChangelogId(Integer dutyCategoryChangelogId) {
        this.dutyCategoryChangelogId = dutyCategoryChangelogId;
    }

    @Override
    public IDutyCategoryEntity getDutyCategory() {
        return dutyCategory;
    }

    @Override
    public void setDutyCategory(IDutyCategoryEntity dutyCategory) {
        this.dutyCategory = dutyCategory;
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDate;
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public Integer getPoints() {
        return this.points;
    }

    @Override
    public void setPoints(Integer points) {
        this.points = points;
    }
}
