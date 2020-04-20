package at.fhv.teamb.symphoniacus.presentation.internal;

import javafx.beans.property.SimpleStringProperty;

public class DutyPositionMusicianTableModel {
    private SimpleStringProperty role;
    private SimpleStringProperty musicanShortcut;

    public DutyPositionMusicianTableModel(
        SimpleStringProperty role,
        SimpleStringProperty musicanShortcut
    ) {
        this.role = role;
        this.musicanShortcut = musicanShortcut;
    }

    public SimpleStringProperty getRole() {
        return role;
    }

    public void setRole(SimpleStringProperty role) {
        this.role = role;
    }

    public SimpleStringProperty getMusicanShortcut() {
        return musicanShortcut;
    }

    public void setMusicanShortcut(SimpleStringProperty musicanShortcut) {
        this.musicanShortcut = musicanShortcut;
    }
}
