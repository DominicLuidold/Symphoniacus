package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.exception.PointsNotCalculatedException;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DutyPositionMusicianTableModel implements TableModel {

    private static final Logger LOG = LogManager.getLogger(DutyPositionMusicianTableModel.class);
    private DutyPosition dutyPosition;

    /**
     * Construct a new Duty Position Musician Table Model.
     *
     * @param dutyPosition The Position Entry for which the table is for
     */
    public DutyPositionMusicianTableModel(DutyPosition dutyPosition) {
        this.dutyPosition = dutyPosition;
    }

    /**
     * Returns the position description.
     * @return Description of Instrumentation Position
     */
    public String getRole() {
        return this.dutyPosition
            .getEntity()
            .getInstrumentationPosition()
            .getPositionDescription();
    }

    /**
     * Returns the musician Shortcut.
     * @return Shortcut of assigned musician if present, else empty string
     */
    public String getMusicanShortcut() {
        if (this.dutyPosition.getAssignedMusician().isPresent()) {
            return this.dutyPosition.getAssignedMusician().get()
                .getFullName();
        } else {
            LOG.debug(
                "No musician present for duty position {}",
                this.dutyPosition.getEntity().getInstrumentationPosition().getPositionDescription()
            );
        }
        return "";
    }

    public String getPointsSummary() {
        if (this.dutyPosition.getAssignedMusician().isPresent()) {
            Musician m = this.dutyPosition.getAssignedMusician().get();
            Optional<String> points = m.getPointsSummaryText();
            if (points.isPresent()) {
                return points.get();
            }
        } else {
            LOG.debug(
                "No musician present for duty position {}",
                this.dutyPosition.getEntity().getInstrumentationPosition().getPositionDescription()
            );
        }
        return "";
    }

    public DutyPosition getDutyPosition() {
        return dutyPosition;
    }

    public void setDutyPosition(DutyPosition dutyPosition) {
        this.dutyPosition = dutyPosition;
    }

    @Override
    public Integer getBalancePoints() {
        if (this.dutyPosition.getAssignedMusician().isPresent()) {
            Musician m = this.dutyPosition.getAssignedMusician().get();
            try {
                return m.getBalancePoints().getValue();
            } catch (PointsNotCalculatedException e) {
                LOG.error(e);
            }

        } else {
            LOG.debug(
                "No musician present for duty position {}",
                this.dutyPosition.getEntity().getInstrumentationPosition().getPositionDescription()
            );
        }
        return -1;
    }

    @Override
    public Integer getDebitPoints() {
        if (this.dutyPosition.getAssignedMusician().isPresent()) {
            Musician m = this.dutyPosition.getAssignedMusician().get();
            try {
                return m.getDebitPoints().getValue();
            } catch (PointsNotCalculatedException e) {
                LOG.error(e);
            }

        } else {
            LOG.debug(
                "No musician present for duty position {}",
                this.dutyPosition.getEntity().getInstrumentationPosition().getPositionDescription()
            );
        }
        return -1;
    }

    @Override
    public Integer getGainedPoints() {
        if (this.dutyPosition.getAssignedMusician().isPresent()) {
            Musician m = this.dutyPosition.getAssignedMusician().get();
            try {
                return m.getGainedPoints().getValue();
            } catch (PointsNotCalculatedException e) {
                LOG.error(e);
            }

        } else {
            LOG.debug(
                "No musician present for duty position {}",
                this.dutyPosition.getEntity().getInstrumentationPosition().getPositionDescription()
            );
        }
        return -1;
    }

    @Override
    public Musician getMusician() {
        Optional<Musician> m = this.dutyPosition.getAssignedMusician();
        if (m.isPresent()) {
            return m.get();
        }
        return null;
    }
}
