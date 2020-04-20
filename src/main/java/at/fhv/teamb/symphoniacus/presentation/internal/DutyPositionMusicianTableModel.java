package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import at.fhv.teamb.symphoniacus.domain.Musician;
import java.util.Optional;
import javafx.beans.property.SimpleStringProperty;

public class DutyPositionMusicianTableModel {
    private SimpleStringProperty role;
    private SimpleStringProperty musicanShortcut;
    private DutyPosition dutyPosition;
    private Musician musician;

    /**
     * Consruct a new Duty Position Musician Table Model.
     *
     * @param dutyPosition The Position Entry for which the table is for
     * @param musician     The Musician for the Position if there is one.
     */
    public DutyPositionMusicianTableModel(DutyPosition dutyPosition, Optional<Musician> musician) {
        this.dutyPosition = dutyPosition;
        this.musician = musician.get();
        this.role = new SimpleStringProperty(
            dutyPosition
                .getEntity()
                .getInstrumentationPosition()
                .getPositionDescription()
        );

        if (musician.isPresent()) {
            this.musicanShortcut = new SimpleStringProperty(musician.get().getShortcut());
        }

    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public String getMusicanShortcut() {
        return musicanShortcut.get();
    }

    public void setMusicanShortcut(String musicanShortcut) {
        this.musicanShortcut.set(musicanShortcut);
    }

    public SimpleStringProperty musicanShortcutProperty() {
        return musicanShortcut;
    }

    public DutyPosition getDutyPosition() {
        return dutyPosition;
    }

    public void setDutyPosition(DutyPosition dutyPosition) {
        this.dutyPosition = dutyPosition;
    }

    public Musician getMusician() {
        return musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }
}
