package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "section")
public class SectionEntity implements ISectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionId")
    private Integer sectionId;

    @Column(name = "sectionShortcut")
    private String sectionShortcut;

    @Column(name = "description")
    private String description;

    @OneToMany(
        mappedBy = "section",
        orphanRemoval = true,
        targetEntity = SectionMonthlyScheduleEntity.class
    )
    private List<ISectionMonthlyScheduleEntity> sectionMonthlySchedules = new LinkedList<>();

    @OneToMany(mappedBy = "section", orphanRemoval = true, targetEntity = MusicianEntity.class)
    private List<IMusicianEntity> musicians = new LinkedList<>();

    @OneToMany(mappedBy = "section", orphanRemoval = true, targetEntity = DutyPositionEntity.class)
    private List<IDutyPositionEntity> dutyPositions = new LinkedList<>();

    @OneToMany(
        mappedBy = "section",
        orphanRemoval = true,
        targetEntity = SectionInstrumentationEntity.class
    )
    private List<ISectionInstrumentationEntity> sectionInstrumentations = new LinkedList<>();

    @Override
    public Integer getSectionId() {
        return this.sectionId;
    }

    @Override
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public String getSectionShortcut() {
        return this.sectionShortcut;
    }

    @Override
    public void setSectionShortcut(String sectionShortcut) {
        this.sectionShortcut = sectionShortcut;
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
    public List<ISectionMonthlyScheduleEntity> getSectionMonthlySchedules() {
        return this.sectionMonthlySchedules;
    }

    @Override
    public void addSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule) {
        this.sectionMonthlySchedules.add(sectionMonthlySchedule);
        sectionMonthlySchedule.setSection(this);
    }

    @Override
    public void removeSectionMonthlySchedule(ISectionMonthlyScheduleEntity sectionMonthlySchedule) {
        this.sectionMonthlySchedules.remove(sectionMonthlySchedule);
        sectionMonthlySchedule.setMonthlySchedule(null);
    }

    @Override
    public List<IMusicianEntity> getMusicians() {
        return this.musicians;
    }

    @Override
    public void addMusician(IMusicianEntity musician) {
        this.musicians.add(musician);
        musician.setSection(this);
    }

    @Override
    public void removeMusician(IMusicianEntity musician) {
        this.musicians.remove(musician);
        musician.setSection(null);
    }

    @Override
    public List<IDutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    @Override
    public void addDutyPosition(IDutyPositionEntity dutyPosition) {
        this.dutyPositions.add(dutyPosition);
        dutyPosition.setSection(this);
    }

    @Override
    public void removeDutyPosition(IDutyPositionEntity dutyPosition) {
        this.dutyPositions.remove(dutyPosition);
        dutyPosition.setSection(null);
    }

    @Override
    public List<ISectionInstrumentationEntity> getSectionInstrumentations() {
        return this.sectionInstrumentations;
    }

    @Override
    public void addSectionInstrumentation(ISectionInstrumentationEntity sectionInstrumentation) {
        this.sectionInstrumentations.add(sectionInstrumentation);
        sectionInstrumentation.setSection(this);
    }

    @Override
    public void removeSectionInstrumentation(ISectionInstrumentationEntity sectionInstrumentation) {
        this.sectionInstrumentations.remove(sectionInstrumentation);
        sectionInstrumentation.setSection(null);
    }
}
