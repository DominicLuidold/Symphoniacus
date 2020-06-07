package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.application.dto.InstrumentationDto;
import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceDto;
import at.fhv.teamb.symphoniacus.application.dto.SeriesOfPerformancesDto;
import at.fhv.teamb.symphoniacus.domain.SeriesOfPerformances;
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
import at.fhv.teamb.symphoniacus.presentation.MainController;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
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
     * Initializes the SeriesOfPerformancesManager.
     */
    public SeriesOfPerformancesManager() {
        this.instrumentationDao = new InstrumentationDao();
        this.seriesOfPerformancesDao = new SeriesOfPerformancesDao();
    }

    /**
     * Persists a seriesOfPerformances based on the entered data.
     *
     * @param seriesDto given DTO with all needed input
     * @return true if persisting was successful, false otherwise
     */
    public boolean save(SeriesOfPerformancesDto seriesDto) {
        SeriesOfPerformancesEntity series = new SeriesOfPerformancesEntity();
        series.setDescription(seriesDto.getDescription());
        series.setMusicalPieces(convertMusicalPieceDtoToEntities(seriesDto.getMusicalPieces()));
        series.setInstrumentations(convertInstrumentationDtoToEntites(
            seriesDto.getInstrumentations()));
        series.setStartDate(seriesDto.getStartDate());
        series.setEndDate(seriesDto.getEndDate());
        series.setIsTour(seriesDto.isTour());

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

    /**
     * Filters all series of performances that began either before or equal the the given Date.
     *
     * @param startedBeforeOrEqual target Date
     * @return A List of filtered SeriesOfPerformancesDto
     */
    public List<SeriesOfPerformancesDto> getFilteredSeries(LocalDate startedBeforeOrEqual) {
        List<SeriesOfPerformancesDto> allSeries = getAllSeries();
        List<SeriesOfPerformancesDto> filteredSeries = new LinkedList<>();
        for (SeriesOfPerformancesDto series : allSeries) {
            if ((series.getStartDate().isBefore(startedBeforeOrEqual)
                || series.getStartDate().isEqual(startedBeforeOrEqual))
                && (series.getEndDate().isAfter(startedBeforeOrEqual)
                || series.getEndDate().isEqual(startedBeforeOrEqual))) {
                filteredSeries.add(series);
            }
        }
        return filteredSeries;
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

    /**
     * Converts a {@link ISeriesOfPerformancesEntity} to a {@link SeriesOfPerformancesDto}.
     *
     * @param entity Entity to convert
     * @return Converted DTO
     */
    public SeriesOfPerformancesDto convertSopToDto(ISeriesOfPerformancesEntity entity) {
        return new SeriesOfPerformancesDto.SeriesOfPerformancesDtoBuilder(
            entity.getSeriesOfPerformancesId()
        )
            .withMusicalPieces(
                convertMusicalPiecesToDto(entity.getMusicalPieces())
            )
            .withDescription(entity.getDescription())
            .withStartDate(entity.getStartDate())
            .withEndDate(entity.getEndDate())
            .build();
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

    /**
     * validates the input of the form for creating a new series of performances.
     *
     * @param seriesDto seriesDto containing all input form informations
     * @param bundle    language file
     * @return "VALIDATED" if validation was successful, else return the error
     *          text for alert
     */
    public String validate(SeriesOfPerformancesDto seriesDto, ResourceBundle bundle) {
        SeriesOfPerformances series
            = new SeriesOfPerformances(seriesDto,
            doesSeriesAlreadyExist(seriesDto.getDescription(),
                seriesDto.getStartDate(), seriesDto.getEndDate()), bundle);
        return series.validate();
    }
}
