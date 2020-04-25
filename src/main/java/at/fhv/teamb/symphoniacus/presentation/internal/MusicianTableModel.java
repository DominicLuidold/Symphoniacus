package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.exception.PointsNotCalculatedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MusicianTableModel {

    private static final Logger LOG = LogManager.getLogger(MusicianTableModel.class);
    private boolean schedule;
    private Musician musician;


    /**
     * Consruct a new Musician Table Model.
     *
     * @param m The musician used for this table Model
     */
    public MusicianTableModel(Musician m) {
        this.musician = m;
    }

    public String getName() {
        return this.musician.getFullName();
    }

    /**
     * Gets the points from the Musician.
     * @return value of Points of Musician.
     */
    public Integer getPoints() {
        try {
            return this.musician.getPoints().getValue();
        } catch (PointsNotCalculatedException e) {
            return -1;
        }
    }

    public Boolean getSchedule() {
        return this.schedule;
    }

    public Musician getMusician() {
        return musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }


}
