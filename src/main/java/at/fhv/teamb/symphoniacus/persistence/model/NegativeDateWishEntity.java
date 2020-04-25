package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "negativeDateWish")
public class NegativeDateWishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "negativeDateId")
    private Integer negativeDateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "musicianId")
    private MusicianEntity musician;

    @Column(name = "description")
    private String description;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    public Integer getNegativeDateId() {
        return this.negativeDateId;
    }

    @ManyToMany
    @JoinTable(name = "negativeDate_monthlySchedule")
    private List<MonthlyScheduleEntity> monthlySchedules = new LinkedList<>();

    public void addMonthlySchedule(MonthlyScheduleEntity monthlyScheduleEntity) {
        this.monthlySchedules.add(monthlyScheduleEntity);
        monthlyScheduleEntity.getNegativeDateWishes().add(this);
    }

    public void removeMonthlySchedule(MonthlyScheduleEntity monthlyScheduleEntity) {
        this.monthlySchedules.remove(monthlyScheduleEntity);
        monthlyScheduleEntity.getNegativeDateWishes().remove(this);
    }

    public void setNegativeDateId(Integer negativeDateId) {
        this.negativeDateId = negativeDateId;
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

    public List<MonthlyScheduleEntity> getMonthlySchedules() {
        return monthlySchedules;
    }

    public void setMonthlySchedules(
        List<MonthlyScheduleEntity> monthlySchedules) {
        this.monthlySchedules = monthlySchedules;
    }

    public MusicianEntity getMusician() {
        return musician;
    }

    public void setMusician(MusicianEntity musician) {
        this.musician = musician;
    }
}
