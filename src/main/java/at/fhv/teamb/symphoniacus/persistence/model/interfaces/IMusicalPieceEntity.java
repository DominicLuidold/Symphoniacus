package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;
import java.util.Set;

/**
 * Interface for MusicalPieceEntity class.
 *
 * @author Theresa Gierer
 */
public interface IMusicalPieceEntity {
    String getName();

    Integer getMusicalPieceId();

    String getCategory();

    Set<IInstrumentationEntity> getInstrumentations();
}
