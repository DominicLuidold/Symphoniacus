package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "dutyPosition")
public class DutyPosition {
    @Id
    @Column(name = "dutyPositionId")
    private Integer dutyPositionId;

    @Column(name = "description")
    private String description;

    @Column(name = "instumentionPositionId")
    private Integer instumentionPositionId;

    @Column(name = "dutyId")
    private Integer dutyId;

    @Column(name = "sectionId")
    private Integer sectionId;


    public Integer getDutyPositionId() {
        return this.dutyPositionId;
    }

    public void setDutyPositionId(Integer dutyPositionId) {
        this.dutyPositionId = dutyPositionId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInstumentionPositionId() {
        return this.instumentionPositionId;
    }

    public void setInstumentionPositionId(Integer instumentionPositionId) {
        this.instumentionPositionId = instumentionPositionId;
    }

    public Integer getDutyId() {
        return this.dutyId;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }
}
