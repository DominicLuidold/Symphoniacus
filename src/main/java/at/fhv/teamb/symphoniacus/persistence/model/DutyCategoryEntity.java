package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
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
@Table(name = "dutyCategory")
public class DutyCategoryEntity implements IDutyCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyCategoryId")
    private Integer dutyCategoryId;

    @Column(name = "type")
    private String type;

    @Column(name = "isRehearsal")
    private boolean isRehearsal;

    @Column(name = "points")
    private Integer points;

    @OneToMany(mappedBy = "dutyCategory", orphanRemoval = true)
    private List<DutyCategoryChangelogEntity> dutyCategoryChangelogs = new LinkedList<>();

    @OneToMany(mappedBy = "dutyCategory", orphanRemoval = true)
    private List<DutyEntity> duties = new LinkedList<>();

    public Integer getDutyCategoryId() {
        return this.dutyCategoryId;
    }

    public void setDutyCategoryId(Integer dutyCategoryId) {
        this.dutyCategoryId = dutyCategoryId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsRehearsal() {
        return this.isRehearsal;
    }

    public void setIsRehearsal(boolean isRehearsal) {
        this.isRehearsal = isRehearsal;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public List<DutyCategoryChangelogEntity> getDutyCategoryChangelogs() {
        return this.dutyCategoryChangelogs;
    }

    public void addDutyCategoryChangelog(DutyCategoryChangelogEntity dutyCategoryChangelog) {
        this.dutyCategoryChangelogs.add(dutyCategoryChangelog);
        dutyCategoryChangelog.setDutyCategory(this);
    }

    public void removeDutyCategoryChangelog(DutyCategoryChangelogEntity dutyCategoryChangelog) {
        this.dutyCategoryChangelogs.remove(dutyCategoryChangelog);
        dutyCategoryChangelog.setDutyCategory(null);
    }

    public List<DutyEntity> getDuties() {
        return this.duties;
    }

    public void addDuty(DutyEntity duty) {
        this.duties.add(duty);
        duty.setDutyCategory(this);
    }

    public void removeDuty(DutyEntity duty) {
        this.duties.remove(duty);
        duty.setDutyCategory(null);
    }
}
