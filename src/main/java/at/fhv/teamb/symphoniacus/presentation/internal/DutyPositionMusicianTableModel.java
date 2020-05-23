package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DutyPositionMusicianTableModel {

    private static final Logger LOG = LogManager.getLogger(DutyPositionMusicianTableModel.class);
    private DutyPosition dutyPosition;

    /**
     * Construct a new Duty Position Musician Table Model.
     *
     * @param dutyPosition The Position Entry for which the table is for
     */
    public DutyPositionMusicianTableModel(
        DutyPosition dutyPosition
    ) {
        this.dutyPosition = dutyPosition;
    }

    public int getNumber() {
        return this.dutyPosition
            .getPositionNumber();
    }

    /**
     * Returns the position description.
     *
     * @return Description of Instrumentation Position
     */
    public String getRole() {
        return this.dutyPosition
            .getPositionDescription();
    }

    /**
     * Returns the musician Shortcut.
     *
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

    public DutyPosition getDutyPosition() {
        return dutyPosition;
    }

    public void setDutyPosition(DutyPosition dutyPosition) {
        this.dutyPosition = dutyPosition;
    }
}
