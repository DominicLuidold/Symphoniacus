package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sectionMonthlySchedule")
public class SectionMonthlyScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionMonthlyScheduleId")
    private Integer sectionMonthlyScheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monthlyScheduleId")
    private MonthlyScheduleEntity monthlySchedule;

    @Column(name = "isReadyForDutyScheduler")
    private Boolean isReadyForDutyScheduler;

    @Column(name = "isReadyForOrganisationManager")
    private Boolean isReadyForOrganisationManager;

    @Column(name = "isPublished")
    private Boolean isPublished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sectionId")
    private SectionEntity section;

    @ManyToMany(mappedBy = "sectionMonthlySchedules")
    private List<DutyEntity> duties = new LinkedList<>();

    public Integer getSectionMonthlyScheduleId() {
        return this.sectionMonthlyScheduleId;
    }

    public void setSectionMonthlyScheduleId(Integer sectionMonthlyScheduleId) {
        this.sectionMonthlyScheduleId = sectionMonthlyScheduleId;
    }

    public MonthlyScheduleEntity getMonthlySchedule() {
        return this.monthlySchedule;
    }

    public void setMonthlySchedule(MonthlyScheduleEntity monthlySchedule) {
        this.monthlySchedule = monthlySchedule;
    }

    public Boolean isReadyForDutyScheduler() {
        return this.isReadyForDutyScheduler;
    }

    public void setReadyForDutyScheduler(Boolean readyForDutyScheduler) {
        this.isReadyForDutyScheduler = readyForDutyScheduler;
    }

    public Boolean isReadyForOrganisationManager() {
        return this.isReadyForOrganisationManager;
    }

    public void setReadyForOrganisationManager(Boolean readyForOrganisationManager) {
        this.isReadyForOrganisationManager = readyForOrganisationManager;
    }

    public Boolean isPublished() {
        return this.isPublished;
    }

    public void setPublished(Boolean published) {
        this.isPublished = published;
    }

    public SectionEntity getSection() {
        return this.section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public List<DutyEntity> getDuties() {
        return duties;
    }

    public void addDuty(DutyEntity duty) {
        this.duties.add(duty);
        duty.addSectionMonthlySchedule(this);
    }

    public void removeDuty(DutyEntity duty) {
        this.duties.remove(duty);
        duty.removeSectionMonthlySchedule(this);
    }
}
