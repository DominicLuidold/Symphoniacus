package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.MusicalPiece;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;

/**
 * This class is for Displaying the old Dutys in the Dutyscheduleview Dropdown.
 *
 * @author Tobias Moser
 */

public class MusicalPieceComboView {
    MusicalPiece musicalPiece;

    public MusicalPieceComboView(MusicalPiece musicalPiece) {
        this.musicalPiece = musicalPiece;
    }

    public String getName() {
        return musicalPiece.getEntity().getName();
    }

    public String getComposer() {
        return musicalPiece.getEntity().getComposer();
    }
}
