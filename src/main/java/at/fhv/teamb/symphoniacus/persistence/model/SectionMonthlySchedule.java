package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sectionMonthlySchedule")
public class SectionMonthlySchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionMonthlyScheduleId")
    private Integer sectionMonthlyScheduleId;

    @Column(name = "monthlyScheduleId", insertable = false, updatable = false)
    private Integer monthlyScheduleId;

    @Column(name = "isPublished")
    private Boolean isPublished;

    @Column(name = "sectionId", updatable = false, insertable = false)
    private Integer sectionId;

    //Many-To-One Part for MONTHLYSCHEDULE Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthlyScheduleId")
    private MonthlySchedule monthlySchedule;

    //Many-To-One Part for SECTION Table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sectionId")
    private Section section;

    //One-To-Many Part for DUTY Table
    @OneToMany(mappedBy = "sectionMonthlySchedule", orphanRemoval = true)
    private Set<Duty> dutySet = new HashSet<Duty>();

    public MonthlySchedule getMonthlySchedule() {
        return this.monthlySchedule;
    }

    public void setMonthlySchedule(MonthlySchedule monthlySchedule) {
        this.monthlySchedule = monthlySchedule;
    }

    public Section getSection() {
        return this.section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Set<Duty> getDutySet() {
        return this.dutySet;
    }

    public void setDutySet(Set<Duty> dutySet) {
        this.dutySet = dutySet;
    }

    public void addDuty(Duty duty) {
        this.dutySet.add(duty);
        duty.setSectionMonthlySchedule(this);
    }

    public Integer getSectionMonthlyScheduleId() {
        return this.sectionMonthlyScheduleId;
    }

    public void setSectionMonthlyScheduleId(Integer sectionMonthlyScheduleId) {
        this.sectionMonthlyScheduleId = sectionMonthlyScheduleId;
    }

    public Integer getMonthlyScheduleId() {
        return this.monthlyScheduleId;
    }

    public void setMonthlyScheduleId(Integer monthlyScheduleId) {
        this.monthlyScheduleId = monthlyScheduleId;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}
