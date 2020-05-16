package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;

/**
 * Domain object for MusicalPiece.
 *
 * @author Valentin Goronjic
 */
public class MusicalPiece {
    private IMusicalPieceEntity entity;

    public MusicalPiece(IMusicalPieceEntity entity) {
        this.entity = entity;
    }

    public IMusicalPieceEntity getEntity() {
        return this.entity;
    }

    @Override
    public String toString() {
        return this.entity.getName();
    }
}
