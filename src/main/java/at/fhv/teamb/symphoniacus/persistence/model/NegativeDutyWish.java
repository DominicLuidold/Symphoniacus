package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "negativeDutyWish")
public class NegativeDutyWish {
    @Id
    @Column(name = "nagativeDutyId")
    private Integer nagativeDutyId;

    @Column(name = "musicanId")
    private Integer musicanId;

    @Column(name = "description")
    private String description;

    @Column(name = "seriesOfPerformaceId")
    private Integer seriesOfPerformaceId;


    public Integer getNagativeDutyId() {
        return this.nagativeDutyId;
    }

    public void setNagativeDutyId(Integer nagativeDutyId) {
        this.nagativeDutyId = nagativeDutyId;
    }

    public Integer getMusicanId() {
        return this.musicanId;
    }

    public void setMusicanId(Integer musicanId) {
        this.musicanId = musicanId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeriesOfPerformaceId() {
        return this.seriesOfPerformaceId;
    }

    public void setSeriesOfPerformaceId(Integer seriesOfPerformaceId) {
        this.seriesOfPerformaceId = seriesOfPerformaceId;
    }
}
