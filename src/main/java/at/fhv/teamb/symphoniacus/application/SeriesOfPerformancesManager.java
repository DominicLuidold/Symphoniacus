package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SectionInstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeriesOfPerformancesManager {
    private static final Logger LOG = LogManager.getLogger(SeriesOfPerformancesManager.class);
    private MusicalPieceDao musicalPieceDao;
    private InstrumentationDao instrumentationDao;
    private SectionInstrumentationDao sectionInstrumentationDao;
    private SeriesOfPerformancesDao seriesOfPerformancesDao;

    /**
     * instantiates the attributes.
     */
    public SeriesOfPerformancesManager() {
        this.musicalPieceDao = new MusicalPieceDao();
        this.instrumentationDao = new InstrumentationDao();
        this.sectionInstrumentationDao = new SectionInstrumentationDao();
        this.seriesOfPerformancesDao = new SeriesOfPerformancesDao();
    }

    public Set<MusicalPieceEntity> getAllMusicalPieces() {
        return this.musicalPieceDao.getAllMusicalPieces();
    }

    public Set<InstrumentationEntity> getInstrumentationsToMusicalPieces(
        Set<MusicalPieceEntity> musicalPieces) {
        return this.instrumentationDao.getInstrumentationsToMusicalPieces(musicalPieces);
    }

    public static Optional<MusicalPieceEntity> getMusicalPieceFromName(String name) {
        MusicalPieceDao pieceDao = new MusicalPieceDao();
        return pieceDao.getMusicalPieceFromName(name);
    }

    public boolean save(
        String name,
        Set<MusicalPieceEntity> musicalPieces,
        Set<InstrumentationEntity> instrumentations, LocalDate startDate,
        LocalDate endDate, boolean isTour
    ) {
        SeriesOfPerformancesEntity series = new SeriesOfPerformancesEntity();
        series.setDescription(name);
        series.setMusicalPieces(musicalPieces);
        series.setInstrumentations(instrumentations);
        series.setStartDate(startDate);
        series.setEndDate(endDate);
        series.setIsTour(isTour);

        Optional<SeriesOfPerformancesEntity> result = seriesOfPerformancesDao.persist(series);
        return result.isPresent();
    }
}
