package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;

public interface IInstrumentationEntity {
    String getName();
    IMusicalPieceEntity getMusicalPiece();

}
