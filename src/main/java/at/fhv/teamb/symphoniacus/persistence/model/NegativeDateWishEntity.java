package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
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
public class NegativeDateWishEntity implements INegativeDateWishEntity, WishRequestable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "negativeDateId")
    private Integer negativeDateId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MusicianEntity.class)
    @JoinColumn(name = "musicianId")
    private IMusicianEntity musician;

    @Column(name = "description")
    private String description;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Override
    public Integer getNegativeDateId() {
        return this.negativeDateId;
    }

    @ManyToMany(targetEntity = MonthlyScheduleEntity.class)
    @JoinTable(
        name = "negativeDate_monthlySchedule",
        joinColumns = @JoinColumn(name = "negativeDateId"),
        inverseJoinColumns = @JoinColumn(name = "monthlyScheduleId")
    )
    private List<IMonthlyScheduleEntity> monthlySchedules = new LinkedList<>();

    @Override
    public void addMonthlySchedule(IMonthlyScheduleEntity monthlyScheduleEntity) {
        this.monthlySchedules.add(monthlyScheduleEntity);
        monthlyScheduleEntity.getNegativeDateWishes().add(this);
    }

    @Override
    public void removeMonthlySchedule(IMonthlyScheduleEntity monthlyScheduleEntity) {
        this.monthlySchedules.remove(monthlyScheduleEntity);
        monthlyScheduleEntity.getNegativeDateWishes().remove(this);
    }

    @Override
    public void setNegativeDateId(Integer negativeDateId) {
        this.negativeDateId = negativeDateId;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
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
    public LocalDate getEndDate() {
        return this.endDate;
    }

    @Override
    public Integer getID() {
        return this.negativeDateId;
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public List<IMonthlyScheduleEntity> getMonthlySchedules() {
        return monthlySchedules;
    }

    @Override
    public void setMonthlySchedules(List<IMonthlyScheduleEntity> monthlySchedules) {
        this.monthlySchedules = monthlySchedules;
    }

    @Override
    public IMusicianEntity getMusician() {
        return musician;
    }

    @Override
    public void setMusician(IMusicianEntity musician) {
        this.musician = musician;
    }
}
