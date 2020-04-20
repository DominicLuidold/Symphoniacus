package at.fhv.teamb.symphoniacus.persistence.model;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dutyCategory")
@NamedEntityGraph(
    name = "dutyCategory-with-points",
    attributeNodes = {
        @NamedAttributeNode("dutyCategoryId"),
        @NamedAttributeNode("points"),
    }
)
public class DutyCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dutyCategoryId")
    private Integer dutyCategoryId;

    @Column(name = "type")
    private String type;

    @Column(name = "isRehearsal")
    private Boolean isRehearsal;

    @Column(name = "points")
    private Integer points;

    @OneToMany(orphanRemoval = true)
    private List<DutyCategoryChangelogEntity> dutyCategoryChangelogs = new LinkedList<>();

    @OneToMany(orphanRemoval = true)
    private List<DutyEntity> duties = new LinkedList<>();

    public List<DutyCategoryChangelogEntity> getDutyCategoryChangelogs() {
        return this.dutyCategoryChangelogs;
    }

    public void addDutyCategoryChangelog(DutyCategoryChangelogEntity dutyCategoryChangelog) {
        this.dutyCategoryChangelogs.add(dutyCategoryChangelog);
    }

    public void addAllDutyCategoryChangelogs(
        List<DutyCategoryChangelogEntity> dutyCategoryChangelogs) {
        this.dutyCategoryChangelogs.addAll(dutyCategoryChangelogs);
    }

    public List<DutyEntity> getDuties() {
        return this.duties;
    }

    public void addDuty(DutyEntity duty) {
        this.duties.add(duty);
    }

    public void addAllDuties(List<DutyEntity> duties) {
        this.duties.addAll(duties);
    }

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

    public Boolean getIsRehearsal() {
        return this.isRehearsal;
    }

    public void setIsRehearsal(Boolean isRehearsal) {
        this.isRehearsal = isRehearsal;
    }

    public Integer getPoints() {
        return this.points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
