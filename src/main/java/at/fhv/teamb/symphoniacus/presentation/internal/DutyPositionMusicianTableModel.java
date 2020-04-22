package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import java.util.Optional;

public class DutyPositionMusicianTableModel {
    private DutyPosition dutyPosition;

    /**
     * Consruct a new Duty Position Musician Table Model.
     *
     * @param dutyPosition The Position Entry for which the table is for
     */
    public DutyPositionMusicianTableModel(DutyPosition dutyPosition) {
        this.dutyPosition = dutyPosition;
    }

    public String getRole() {
        return this.dutyPosition
            .getEntity()
            .getInstrumentationPosition()
            .getPositionDescription();
    }

    public String getMusicanShortcut() {

        if (this.dutyPosition.getAssignedMusician().isPresent()) {
            return this.dutyPosition.getAssignedMusician().get()
                .getFullName();
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
