package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
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
    private List<IDutyCategoryChangelogEntity> dutyCategoryChangelogs = new LinkedList<>();

    @OneToMany(mappedBy = "dutyCategory", orphanRemoval = true)
    private List<DutyEntity> duties = new LinkedList<>();

    @Override
    public Integer getDutyCategoryId() {
        return this.dutyCategoryId;
    }

    @Override
    public void setDutyCategoryId(Integer dutyCategoryId) {
        this.dutyCategoryId = dutyCategoryId;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean getIsRehearsal() {
        return this.isRehearsal;
    }

    @Override
    public void setIsRehearsal(boolean isRehearsal) {
        this.isRehearsal = isRehearsal;
    }

    @Override
    public Integer getPoints() {
        return this.points;
    }

    @Override
    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public List<IDutyCategoryChangelogEntity> getDutyCategoryChangelogs() {
        return this.dutyCategoryChangelogs;
    }

    @Override
    public void addDutyCategoryChangelog(IDutyCategoryChangelogEntity dutyCategoryChangelog) {
        this.dutyCategoryChangelogs.add(dutyCategoryChangelog);
        dutyCategoryChangelog.setDutyCategory(this);
    }

    @Override
    public void removeDutyCategoryChangelog(IDutyCategoryChangelogEntity dutyCategoryChangelog) {
        this.dutyCategoryChangelogs.remove(dutyCategoryChangelog);
        dutyCategoryChangelog.setDutyCategory(null);
    }

    @Override
    public List<DutyEntity> getDuties() {
        return this.duties;
    }

    @Override
    public void addDuty(DutyEntity duty) {
        this.duties.add(duty);
        duty.setDutyCategory(this);
    }

    @Override
    public void removeDuty(DutyEntity duty) {
        this.duties.remove(duty);
        duty.setDutyCategory(null);
    }
}
