package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "section")
public class Section {
    @Id
    @Column(name = "sectionId")
    private Integer sectionId;

    @Column(name = "sectionShortcut")
    private String sectionShortcut;

    @Column(name = "description")
    private String description;


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
}
