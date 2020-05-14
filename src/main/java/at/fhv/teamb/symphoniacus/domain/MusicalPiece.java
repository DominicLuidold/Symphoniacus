package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;

/**
 * Domain object for MusicalPiece.
 *
 * @author Valentin Goronjic
 */
public class MusicalPiece {
    private MusicalPieceEntity entity;

    public MusicalPiece(MusicalPieceEntity entity) {
        this.entity = entity;
    }

    public MusicalPieceEntity getEntity() {
        return this.entity;
    }
}
