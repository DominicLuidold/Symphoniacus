package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import java.util.Optional;
import java.util.Set;

public class MusicalPieceManager {
    private final InstrumentationDao instrumentationDao;
    private final MusicalPieceDao musicalPieceDao;

    public MusicalPieceManager() {
        this.instrumentationDao = new InstrumentationDao();
        this.musicalPieceDao = new MusicalPieceDao();
    }

    /**
     * Returns all {@link MusicalPieceEntity} objects.
     *
     * @return A Set of MusicalPieces
     */
    public Set<MusicalPieceEntity> getAllMusicalPieces() {
        return this.musicalPieceDao.getAll();
    }

    /**
     * Returns a Set of {@link InstrumentationEntity} objects for a Set
     * of {@link MusicalPieceEntity}.
     *
     * @param musicalPieces The musical pieces to use
     * @return A Set of instrumentation entities
     */
    public Set<InstrumentationEntity> getInstrumentations(
        Set<MusicalPieceEntity> musicalPieces
    ) {
        return this.instrumentationDao.getInstrumentationsToMusicalPieces(musicalPieces);
    }

    /**
     * Returns a {@link MusicalPieceEntity} for a given name.
     *
     * @param name The name to search for
     * @return A musical piece entity
     */
    public Optional<MusicalPieceEntity> getByName(String name) {
        return this.musicalPieceDao.getMusicalPieceFromName(name);
    }
}
