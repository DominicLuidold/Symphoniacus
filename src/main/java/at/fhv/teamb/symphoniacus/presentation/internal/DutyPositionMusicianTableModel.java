package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import java.util.Optional;

public class DutyPositionMusicianTableModel {
    private DutyPosition dutyPosition;
    private Optional<Musician> musician;

    /**
     * Consruct a new Duty Position Musician Table Model.
     *
     * @param dutyPosition The Position Entry for which the table is for
     * @param musician     The Musician for the Position if there is one.
     */
    public DutyPositionMusicianTableModel(DutyPosition dutyPosition, Optional<Musician> musician) {
        this.dutyPosition = dutyPosition;
        this.musician = musician;
    }

    public String getRole() {
        return this.dutyPosition
            .getEntity()
            .getInstrumentationPosition()
            .getPositionDescription();
    }

    public String getMusicanShortcut() {
        if (this.musician.isPresent()) {
            return musician.get().getShortcut();
        }
        return "";
    }

    public DutyPosition getDutyPosition() {
        return dutyPosition;
    }

    public void setDutyPosition(DutyPosition dutyPosition) {
        this.dutyPosition = dutyPosition;
    }

    public Optional<Musician> getMusician() {
        return musician;
    }

    public void setMusician(Optional<Musician> musician) {
        this.musician = musician;
    }
}
