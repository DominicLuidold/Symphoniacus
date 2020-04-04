package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "negativeDate_monthlySchedule")
public class NegativeDateMonthlySchedule {
    @Id
    @Column(name = "negativeDate_monthlyScheduleId")
    private Integer negativeDateMonthlyScheduleId;

    @Column(name = "monthlyScheduleId")
    private Integer monthlyScheduleId;

    @Column(name = "negativeDateId")
    private Integer negativeDateId;


    public Integer getNegativeDateMonthlyScheduleId() {
        return this.negativeDateMonthlyScheduleId;
    }

    public void setNegativeDateMonthlyScheduleId(Integer negativeDateMonthlyScheduleId) {
        this.negativeDateMonthlyScheduleId = negativeDateMonthlyScheduleId;
    }

    public Integer getMonthlyScheduleId() {
        return this.monthlyScheduleId;
    }

    public void setMonthlyScheduleId(Integer monthlyScheduleId) {
        this.monthlyScheduleId = monthlyScheduleId;
    }

    public Integer getNegativeDateId() {
        return this.negativeDateId;
    }

    public void setNegativeDateId(Integer negativeDateId) {
        this.negativeDateId = negativeDateId;
    }
}
