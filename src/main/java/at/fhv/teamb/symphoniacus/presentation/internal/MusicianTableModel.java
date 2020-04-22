package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.exception.PointsNotCalculatedException;
import at.fhv.teamb.symphoniacus.presentation.DutyScheduleController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MusicianTableModel {

    private static final Logger LOG = LogManager.getLogger(MusicianTableModel.class);
    private SimpleStringProperty name;
    private SimpleIntegerProperty points;
    private SimpleStringProperty wishType;
    private SimpleStringProperty details;
    private SimpleStringProperty date;
    private SimpleBooleanProperty schedule;
    private Musician musician;

    /**
     * Consruct a new Musician Table Model.
     *
     * @param m The musician used for this table Model
     */
    public MusicianTableModel(Musician m) {
        this.musician = m;
        this.name = new SimpleStringProperty(m.getFullName());
        try {
            this.points = new SimpleIntegerProperty(m.getPoints().getValue());
        } catch (PointsNotCalculatedException e) {
            LOG.error(e);
            this.points = new SimpleIntegerProperty(-1);
        }
        this.wishType = new SimpleStringProperty("NOP");
        this.details = new SimpleStringProperty("NOP");
        this.date = new SimpleStringProperty("NOP");
        this.schedule = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public int getPoints() {
        return points.get();
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public SimpleIntegerProperty pointsProperty() {
        return points;
    }

    public String getWishType() {
        return wishType.get();
    }

    public void setWishType(String wishType) {
        this.wishType.set(wishType);
    }

    public SimpleStringProperty wishTypeProperty() {
        return wishType;
    }

    public String getDetails() {
        return details.get();
    }

    public void setDetails(String details) {
        this.details.set(details);
    }

    public SimpleStringProperty detailsProperty() {
        return details;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public boolean isSchedule() {
        return schedule.get();
    }

    public void setSchedule(boolean schedule) {
        this.schedule.set(schedule);
    }

    public SimpleBooleanProperty scheduleProperty() {
        return schedule;
    }

    public Musician getMusician() {
        return musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }
}
