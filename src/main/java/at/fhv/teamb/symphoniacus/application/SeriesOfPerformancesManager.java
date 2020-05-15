package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.SeriesOfPerformancesDto;
import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This class is responsible for obtaining and handling all instances of series of performances.
 *
 * @author Nino Heinzle
 * @author Danijel Antonijevic
 */
public class SeriesOfPerformancesManager {
    private final InstrumentationDao instrumentationDao;
    private final SeriesOfPerformancesDao seriesOfPerformancesDao;

    /**
     * Initialize the SeriesOfPerformancesManager.
     */
    public SeriesOfPerformancesManager() {
        this.instrumentationDao = new InstrumentationDao();
        this.seriesOfPerformancesDao = new SeriesOfPerformancesDao();
    }

    /**
     * Persists a seriesOfPerformances based on the entered data.
     *
     * @param name             Given name of the seriesOfPerformances
     * @param musicalPieces    Given musicalPieces
     * @param instrumentations Given instrumentations to the musicalPieces
     * @param startDate        Starting Date
     * @param endDate          Ending Date
     * @param isTour           Represents whether it's a tour or not
     * @return true if persisting was successful, false otherwise
     */
    public boolean save(
        String name,
        Set<MusicalPieceEntity> musicalPieces,
        Set<InstrumentationEntity> instrumentations,
        LocalDate startDate,
        LocalDate endDate,
        boolean isTour
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
     * checks if the seriesOfPerformances exists with the given parameters or not.
     *
     * @param title        given Title of the seriesOfPerformances
     * @param startingDate given starting Date
     * @param endingDate   given ending Date
     * @return a boolean whether the seriesOfPerformances exists with the given parameters or not.
     */
    public boolean doesSeriesAlreadyExist(
        String title,
        LocalDate startingDate,
        LocalDate endingDate
    ) {
        return this.seriesOfPerformancesDao.doesSeriesAlreadyExist(title, startingDate, endingDate);
    }

    /**
     * Returns a Set of {@link InstrumentationPositionEntity} belonging to a
     * {@link SeriesOfPerformancesEntity}.
     *
     * @param series The series of performances to use
     * @return A Set of instrumentation entities
     */
    public Set<InstrumentationEntity> getAllInstrumentations(
        SeriesOfPerformancesEntity series
    ) {
        return this.instrumentationDao.getAllInstrumentationsToSeries(series);
    }

    /**
     *
     * @return
     */
    public List<SeriesOfPerformancesDto> getAllSerieses() {
        List<ISeriesOfPerformancesEntity> serieses = seriesOfPerformancesDao.getAll();

        List<SeriesOfPerformancesDto> seriesDtoList = new LinkedList<>();
        for (ISeriesOfPerformancesEntity series: serieses) {
            SeriesOfPerformancesDto seriesDto =
                new SeriesOfPerformancesDto
                    .SeriesOfPerformancesDtoBuilder(series.getSeriesOfPerformancesId())
                    .withDescription(series.getDescription())
                    .withDuties(series.getDuties())
                    .withStartDate(series.getStartDate())
                    .withEndDate(series.getEndDate())
                    .withMusicalPieces(series.getMusicalPieces())
                    .withInstrumentations(series.getInstrumentations())
                    .withIsTour(series.getIsTour()).build();
            seriesDtoList.add(seriesDto);
        }
        return seriesDtoList;
    }
}
