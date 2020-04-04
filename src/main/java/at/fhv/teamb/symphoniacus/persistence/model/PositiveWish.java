package at.fhv.teamb.symphoniacus.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "positiveWish")
public class PositiveWish {
    @Id
    @Column(name = "positiveWishId")
    private Integer positiveWishId;

    @Column(name = "seriesOfPerformancesId")
    private Integer seriesOfPerformancesId;

    @Column(name = "musicanId")
    private Integer musicanId;

    @Column(name = "description")
    private String description;


    public Integer getPositiveWishId() {
        return this.positiveWishId;
    }

    public void setPositiveWishId(Integer positiveWishId) {
        this.positiveWishId = positiveWishId;
    }

    public Integer getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    public void setSeriesOfPerformancesId(Integer seriesOfPerformancesId) {
        this.seriesOfPerformancesId = seriesOfPerformancesId;
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
}
