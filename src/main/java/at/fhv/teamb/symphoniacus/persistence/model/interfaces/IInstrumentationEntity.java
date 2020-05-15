package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import java.util.List;

/**
 * Interface for InstrumentationEntity class.
 *
 * @author Theresa Gierer
 */
public interface IInstrumentationEntity {
    String getName();

    IMusicalPieceEntity getMusicalPiece();

    List<IInstrumentationPositionEntity> getInstrumentationPositions(); //TODO: find out how the fuck this works

}
