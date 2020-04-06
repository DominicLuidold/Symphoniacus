package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.converters.BooleanConverter;

import javax.persistence.*;

@Entity
@Table(name = "dutyCategory")
public class DutyCategory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "dutyCategoryId")
    private Integer dutyCategoryId;

    @Column(name = "type")
    private String type;

    @Column(name = "isRehearsal")
    @Convert(converter = BooleanConverter.class)
    private Boolean isRehearsal;

    @Column(name = "points")
    private Integer points;


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
