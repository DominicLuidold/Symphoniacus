package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.application.dto.InstrumentationDto;
import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceDto;
import at.fhv.teamb.symphoniacus.application.dto.SeriesOfPerformancesDto;
import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IInstrumentationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SeriesOfPerformancesEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISeriesOfPerformancesEntity;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
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
    private final IInstrumentationDao instrumentationDao;
    private final ISeriesOfPerformancesDao seriesOfPerformancesDao;

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
        Set<MusicalPieceDto> musicalPieces,
        Set<InstrumentationDto> instrumentations,
        LocalDate startDate,
        LocalDate endDate,
        boolean isTour
    ) {
        SeriesOfPerformancesEntity series = new SeriesOfPerformancesEntity();
        series.setDescription(name);
        series.setMusicalPieces(convertMusicalPieceDtoToEntities(musicalPieces));
        series.setInstrumentations(convertInstrumentationDtoToEntites(instrumentations));
        series.setStartDate(startDate);
        series.setEndDate(endDate);
        series.setIsTour(isTour);

        Optional<ISeriesOfPerformancesEntity> result = this.seriesOfPerformancesDao.persist(series);
        return result.isPresent();
    }

    private Set<IMusicalPieceEntity> convertMusicalPieceDtoToEntities(Set<MusicalPieceDto> dtos) {
        Set<IMusicalPieceEntity> entities = new LinkedHashSet<>();
        for (MusicalPieceDto dto : dtos) {
            IMusicalPieceEntity entity = new MusicalPieceEntity();
            entity.setMusicalPieceId(dto.getMusicalPieceId());
            entity.setCategory(dto.getCategory());
            entity.setName(dto.getName());
            entity
                .setInstrumentations(convertInstrumentationDtoToEntites(dto.getInstrumentations()));
            entities.add(entity);
        }
        return entities;
    }

    private Set<IInstrumentationEntity> convertInstrumentationDtoToEntites(
        Set<InstrumentationDto> dtos) {
        Set<IInstrumentationEntity> entities = new LinkedHashSet<>();
        for (InstrumentationDto dto : dtos) {
            IInstrumentationEntity entity = new InstrumentationEntity();
            entity.setInstrumentationId(dto.getInstrumentationId());
            entity.setName(dto.getName());
            entities.add(entity);
        }
        return entities;
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
    public Set<InstrumentationDto> getAllInstrumentations(
        SeriesOfPerformancesDto series
    ) {

        Optional<ISeriesOfPerformancesEntity> seriesOfPerf = this.seriesOfPerformancesDao
            .find(series.getSeriesOfPerformancesId());

        if (seriesOfPerf.isPresent()) {
            Set<IInstrumentationEntity> instrumentations = this.instrumentationDao
                .getAllInstrumentationsToSeries(seriesOfPerf.get());
            return convertInstrumentationsToDto(instrumentations);
        } else {
            return new LinkedHashSet<>();
        }

    }

    /**
     * Returns all Series of Performances Objects.
     *
     * @return all Series of Performances
     */
    public List<SeriesOfPerformancesDto> getAllSeries() {
        List<ISeriesOfPerformancesEntity> seriesList = this.seriesOfPerformancesDao.getAll();

        List<SeriesOfPerformancesDto> seriesDtoList = new LinkedList<>();
        for (ISeriesOfPerformancesEntity series : seriesList) {
            SeriesOfPerformancesDto seriesDto =
                new SeriesOfPerformancesDto
                    .SeriesOfPerformancesDtoBuilder(series.getSeriesOfPerformancesId())
                    .withDescription(series.getDescription())
                    .withStartDate(series.getStartDate())
                    .withEndDate(series.getEndDate())
                    .withMusicalPieces(convertMusicalPiecesToDto(series.getMusicalPieces()))
                    .build();
            seriesDtoList.add(seriesDto);
        }
        return seriesDtoList;
    }

    private Set<MusicalPieceDto> convertMusicalPiecesToDto(Set<IMusicalPieceEntity> mp) {
        Set<MusicalPieceDto> musicalPieces = new LinkedHashSet<>();
        for (IMusicalPieceEntity m : mp) {
            MusicalPieceDto musicalPieceDto = new MusicalPieceDto
                .MusicalPieceDtoBuilder(m.getMusicalPieceId())
                .withName(m.getName()).build();
            musicalPieces.add(musicalPieceDto);
        }
        return musicalPieces;
    }

    private Set<DutyDto> convertDutyToDto(Set<DutyEntity> duties) {
        Set<DutyDto> dutyDtos = new LinkedHashSet<>();
        for (DutyEntity d : duties) {
            DutyDto dt = new DutyDto.DutyDtoBuilder()
                .withDescription(d.getDescription())
                .withStart(d.getStart())
                .withEnd(d.getEnd()).build();
            dutyDtos.add(dt);
        }
        return dutyDtos;
    }

    private Set<InstrumentationDto> convertInstrumentationsToDto(Set<IInstrumentationEntity> inst) {
        Set<InstrumentationDto> instrumentations = new LinkedHashSet<>();
        for (IInstrumentationEntity i : inst) {
            InstrumentationDto instDto = new InstrumentationDto
                .InstrumentationDtoBuilder(i.getInstrumentationId())
                .withName(i.getName())
                .withMusicalPiece(new MusicalPieceDto
                    .MusicalPieceDtoBuilder(i.getMusicalPiece().getMusicalPieceId())
                    .withName(i.getMusicalPiece().getName())
                    .build()
                )
                .build();
            instrumentations.add(instDto);
        }
        return instrumentations;
    }
}
