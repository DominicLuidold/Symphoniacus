package at.fhv.teamb.symphoniacus.persistence.model;

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
public class SectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionId")
    private Integer sectionId;

    @Column(name = "sectionShortcut")
    private String sectionShortcut;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "section", orphanRemoval = true)
    private List<SectionMonthlySchedule> sectionMonthlySchedules = new LinkedList<>();

    @OneToMany(mappedBy = "section", orphanRemoval = true)
    private List<MusicianEntity> musicians = new LinkedList<>();

    @OneToMany(mappedBy = "section", orphanRemoval = true)
    private List<DutyPositionEntity> dutyPositions = new LinkedList<>();

    @OneToMany(mappedBy = "section", orphanRemoval = true)
    private List<SectionInstrumentationEntity> sectionInstrumentations = new LinkedList<>();

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionShortcut() {
        return this.sectionShortcut;
    }

    public void setSectionShortcut(String sectionShortcut) {
        this.sectionShortcut = sectionShortcut;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SectionMonthlySchedule> getSectionMonthlySchedules() {
        return this.sectionMonthlySchedules;
    }

    public void addSectionMonthlySchedule(SectionMonthlySchedule sectionMonthlySchedule) {
        this.sectionMonthlySchedules.add(sectionMonthlySchedule);
        sectionMonthlySchedule.setSection(this);
    }

    public void removeSectionMonthlySchedule(SectionMonthlySchedule sectionMonthlySchedule) {
        this.sectionMonthlySchedules.remove(sectionMonthlySchedule);
        sectionMonthlySchedule.setMonthlySchedule(null);
    }

    public List<MusicianEntity> getMusicians() {
        return this.musicians;
    }

    public void addMusician(MusicianEntity musician) {
        this.musicians.add(musician);
        musician.setSection(this);
    }

    public void removeMusician(MusicianEntity musician) {
        this.musicians.remove(musician);
        musician.setSection(null);
    }

    public List<DutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    public void addDutyPosition(DutyPositionEntity dutyPosition) {
        this.dutyPositions.add(dutyPosition);
        dutyPosition.setSection(this);
    }

    public void removeDutyPosition(DutyPositionEntity dutyPosition) {
        this.dutyPositions.remove(dutyPosition);
        dutyPosition.setSection(null);
    }

    public List<SectionInstrumentationEntity> getSectionInstrumentations() {
        return this.sectionInstrumentations;
    }

    public void addSectionInstrumentation(SectionInstrumentationEntity sectionInstrumentation) {
        this.sectionInstrumentations.add(sectionInstrumentation);
        sectionInstrumentation.setSection(this);
    }

    public void removeSectionInstrumentation(SectionInstrumentationEntity sectionInstrumentation) {
        this.sectionInstrumentations.remove(sectionInstrumentation);
        sectionInstrumentation.setSection(null);
    }
}
