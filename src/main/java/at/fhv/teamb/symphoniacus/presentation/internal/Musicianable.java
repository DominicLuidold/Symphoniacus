package at.fhv.teamb.symphoniacus.presentation.internal;

import at.fhv.teamb.symphoniacus.domain.Musician;
import java.util.Optional;

/**
 * Get the current Musician.
 *
 * @author Valentin Goronjic
 */
public interface Musicianable {

    Musician getMusician();
}
