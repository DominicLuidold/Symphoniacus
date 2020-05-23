package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        MusicalPiece that = (MusicalPiece) o;
        return Objects.equals(this.entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entity);
    }
}
