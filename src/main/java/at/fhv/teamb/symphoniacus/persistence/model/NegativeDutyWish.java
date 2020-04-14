package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "negativeDutyWish")
public class NegativeDutyWish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "negativeDutyId")
    private Integer negativeDutyId;

    @Column(name = "musicianId")
    private Integer musicianId;

    @Column(name = "description")
    private String description;

    @Column(name = "seriesOfPerformancesId")
    private Integer seriesOfPerformancesId;

    public Integer getNegativeDutyId() {
        return this.negativeDutyId;
    }

    public void setNegativeDutyId(Integer negativeDutyId) {
        this.negativeDutyId = negativeDutyId;
    }

    public Integer getMusicianId() {
        return this.musicianId;
    }

    public void setMusicianId(Integer musicianId) {
        this.musicianId = musicianId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    public void setSeriesOfPerformancesId(Integer seriesOfPerformancesId) {
        this.seriesOfPerformancesId = seriesOfPerformancesId;
    }
}
