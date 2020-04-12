package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "section")
public class Section {
    /*
       ID1 --> 'Vl1', 'Erste Violinen');
       ID2 -->'Vl2', 'Zweite Violinen');
       ID3 -->'Vla', 'Viola oder Bratschen');
       ID4 -->'Vc', 'Violoncelli');
       ID5 -->'Kb', 'Kontrabässe');
       ID6 -->'Fl/Ob/Kl/Fg', 'Holzbläser');
       ID7 -->'Hr/Trp/Pos/Tb', 'Blechbläser');
       ID8 -->'Pk/Schlw/Hf', 'Schlagwerk');
   */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionId")
    private Integer sectionId;

    @Column(name = "sectionShortcut")
    private String sectionShortcut;

    @Column(name = "description")
    private String description;

    //One-To-Many Part for SECTIONMONTHLYSCHEDULE Table
    @OneToMany(mappedBy = "section", orphanRemoval = true)
    private Set<SectionMonthlySchedule> sectionMonthlyScheduleSet = new HashSet<>();

    public Set<SectionMonthlySchedule> getSectionMonthlyScheduleSet() {
        return this.sectionMonthlyScheduleSet;
    }

    public void setSectionMonthlyScheduleSet(
        Set<SectionMonthlySchedule> sectionMonthlyScheduleSet) {
        this.sectionMonthlyScheduleSet = sectionMonthlyScheduleSet;
    }

    public void addSectionMonthlySchedule(SectionMonthlySchedule sectionMonthlySchedule) {
        this.sectionMonthlyScheduleSet.add(sectionMonthlySchedule);
        sectionMonthlySchedule.setSection(this);
    }

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
