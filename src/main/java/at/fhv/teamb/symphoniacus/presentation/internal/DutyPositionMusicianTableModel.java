package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.persistence.model.DutyPosition;
import at.fhv.teamb.symphoniacus.persistence.model.Musician;
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
     * @param musician The Musician for the Position if ther is one.
     */
    public DutyPositionMusicianTableModel(DutyPosition dutyPosition, Musician musician) {
        this.dutyPosition = dutyPosition;
        this.musician = musician;
        this.role = new SimpleStringProperty("1. Vl Konzertmeister");
        this.musicanShortcut = new SimpleStringProperty("shechm");
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public String getMusicanShortcut() {
        return musicanShortcut.get();
    }

    public SimpleStringProperty musicanShortcutProperty() {
        return musicanShortcut;
    }

    public void setMusicanShortcut(String musicanShortcut) {
        this.musicanShortcut.set(musicanShortcut);
    }
}
