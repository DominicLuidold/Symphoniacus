package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class SeriesOfPerformancesManager {
    private MusicalPieceDao musicalPieceDao;
    private InstrumentationDao instrumentationDao;
    private SeriesOfPerformancesDao seriesOfPerformancesDao;

    /**
     * instantiates the attributes.
     *  * @author : Danijel Antonijevic
     *  * @author : Nino Heinzle
     */
    public SeriesOfPerformancesManager() {
        this.musicalPieceDao = new MusicalPieceDao();
        this.instrumentationDao = new InstrumentationDao();
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

    /**
     * Persists a seriesOfPerformances based on the entered data.
     *
     * @param name             Given name of the seriesOfPerformances
     * @param musicalPieces    Given musicalPieces
     * @param instrumentations Given instrumentations to the musicalPieces
     * @param startDate        starting Date
     * @param endDate          ending Date
     * @param isTour           boolean which represents whether it's a tour or not
     * @return boolean as a proof that the persistence was successful
     */
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

        Optional<SeriesOfPerformancesEntity> result = this.seriesOfPerformancesDao.persist(series);
        return result.isPresent();
    }

    /**
     *checks if the seriesOfPerformances exists with the given parameters or not.
     * @param title given Title of the seriesOfPerformances
     * @param startingDate given starting Date
     * @param endingDate given ending Date
     * @return a boolean whether the seriesOfPerformances exists with the given parameters or not.
     */
    public boolean doesSeriesAlreadyExist(
        String title,
        LocalDate startingDate,
        LocalDate endingDate
    ) {
        return this.seriesOfPerformancesDao.doesSeriesAlreadyExist(title, startingDate, endingDate);
    }
}
