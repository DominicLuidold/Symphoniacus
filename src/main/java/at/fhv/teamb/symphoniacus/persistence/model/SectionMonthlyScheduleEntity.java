package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
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
public class SectionMonthlyScheduleEntity implements ISectionMonthlyScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionMonthlyScheduleId")
    private Integer sectionMonthlyScheduleId;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = MonthlyScheduleEntity.class)
    @JoinColumn(name = "monthlyScheduleId")
    private IMonthlyScheduleEntity monthlySchedule;

    @Column(name = "isReadyForDutyScheduler")
    private boolean isReadyForDutyScheduler;

    @Column(name = "isReadyForOrganisationManager")
    private boolean isReadyForOrganisationManager;

    @Column(name = "isPublished")
    private boolean isPublished;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = SectionEntity.class)
    @JoinColumn(name = "sectionId")
    private ISectionEntity section;

    @ManyToMany(mappedBy = "sectionMonthlySchedules", targetEntity = DutyEntity.class)
    private List<IDutyEntity> duties = new LinkedList<>();

    @Override
    public Integer getSectionMonthlyScheduleId() {
        return this.sectionMonthlyScheduleId;
    }

    @Override
    public void setSectionMonthlyScheduleId(Integer sectionMonthlyScheduleId) {
        this.sectionMonthlyScheduleId = sectionMonthlyScheduleId;
    }

    @Override
    public IMonthlyScheduleEntity getMonthlySchedule() {
        return this.monthlySchedule;
    }

    @Override
    public void setMonthlySchedule(IMonthlyScheduleEntity monthlySchedule) {
        this.monthlySchedule = monthlySchedule;
    }

    @Override
    public boolean isReadyForDutyScheduler() {
        return this.isReadyForDutyScheduler;
    }

    @Override
    public void setReadyForDutyScheduler(boolean readyForDutyScheduler) {
        this.isReadyForDutyScheduler = readyForDutyScheduler;
    }

    @Override
    public boolean isReadyForOrganisationManager() {
        return this.isReadyForOrganisationManager;
    }

    @Override
    public void setReadyForOrganisationManager(boolean readyForOrganisationManager) {
        this.isReadyForOrganisationManager = readyForOrganisationManager;
    }

    @Override
    public boolean isPublished() {
        return this.isPublished;
    }

    @Override
    public void setPublished(boolean published) {
        this.isPublished = published;
    }

    @Override
    public ISectionEntity getSection() {
        return this.section;
    }

    @Override
    public void setSection(ISectionEntity section) {
        this.section = section;
    }

    @Override
    public List<IDutyEntity> getDuties() {
        return this.duties;
    }

    @Override
    public void addDuty(IDutyEntity duty) {
        this.duties.add(duty);
    }

    @Override
    public void removeDuty(IDutyEntity duty) {
        this.duties.remove(duty);
    }
}
