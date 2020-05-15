package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceDto;
import at.fhv.teamb.symphoniacus.application.dto.SectionInstrumentationDto;
import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceDto;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.model.MusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionInstrumentationEntity;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.LinkedHashSet;
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
     * Returns all {@link MusicalPieceDto} objects.
     *
     * @return A Set of MusicalPieces
     */
    public Set<MusicalPieceDto> getAllMusicalPieces() {
        Set<MusicalPieceDto> pieces = new LinkedHashSet<>();
        for (MusicalPieceEntity piece : this.musicalPieceDao.getAll()) {
            pieces.add(convertMusicalPieceToDto(piece));
        }
        return pieces;
    }

    private MusicalPieceDto convertMusicalPieceToDto(MusicalPieceEntity entity) {
        return new MusicalPieceDto.MusicalPieceDtoBuilder(entity.getMusicalPieceId())
            .withName(entity.getName()).withCategory(entity.getCategory())
            .withInstrumentations(convertInstrumentationToDto(entity.getInstrumentations()))
            .build();
    }

    private Set<IInstrumentationDto> convertInstrumentationToDto(
        Set<IInstrumentationEntity> entities) {
        Set<InstrumentationDto> instrumentationDtos = new LinkedHashSet<>();
        for (InstrumentationEntity entity : entities) {
            instrumentationDtos.add(
                new InstrumentationDto.InstrumentationDtoBuilder(entity
                    .getInstrumentationId())
                    .withName(entity.getName()).build());
        }
        return instrumentationDtos;
    }

    /**
     * Returns a Set of {@link InstrumentationDto} objects for a Set
     * of {@link MusicalPieceDto}.
     *
     * @param musicalPieces The musical pieces to use
     * @return A Set of instrumentation entities
     */
    public Set<InstrumentationDto> getInstrumentations(
        Set<MusicalPieceDto> musicalPieces
    ) {

        // Convert Musical piece DTO to enities for dao query
        Set<MusicalPieceEntity> musicalPieceEntities = new LinkedHashSet<>();
        for (MusicalPieceDto piece : musicalPieces) {
            MusicalPieceEntity mpentity = new MusicalPieceEntity();
            // Andere attribute werden nicht befüllt, weil query nicht mehr als id braucen sollte
            mpentity.setMusicalPieceId(piece.getMusicalPieceId());
            musicalPieceEntities.add(mpentity);
        }

        // Fill in instrumentationDtos
        Set<InstrumentationDto> instrumentationDtos = new LinkedHashSet<>();
        for (InstrumentationEntity instEntity : this.instrumentationDao
            .getInstrumentationsToMusicalPieces(musicalPieceEntities)) {
            instrumentationDtos.add(
                new InstrumentationDto.InstrumentationDtoBuilder(instEntity
                    .getInstrumentationId())
                    .withName(instEntity.getName())
                    .withSectionInstrumentations(
                        convertSectionInstrumentationsToDto(
                            instEntity.getSectionInstrumentations()))
                    .withMusicalPiece(convertMusicalPieceToDto(instEntity.getMusicalPiece()))
                    .build());
        }
        return instrumentationDtos;
    }

    private MusicalPieceDto convertMusicalPiecesDto(MusicalPieceEntity entity) {
        MusicalPieceDto dto = new MusicalPieceDto.MusicalPieceDtoBuilder(entity.getMusicalPieceId())
            .withName(entity.getName()).withCategory(entity.getCategory()).build();

        return dto;
    }


    private List<SectionInstrumentationDto> convertSectionInstrumentationsToDto(
        List<SectionInstrumentationEntity> entities) {
        List<SectionInstrumentationDto> dtos = new LinkedList<>();
        for (SectionInstrumentationEntity entity : entities) {
            dtos.add(new SectionInstrumentationDto.SectionInstrumentationDtoBuilder(
                entity.getSectionInstrumentationId())
                .withPredefinedSectionInstrumentation(entity.getPredefinedSectionInstrumentation())
                .build());
        }
        return dtos;
    }

    /**
     * Returns a {@link MusicalPieceDto} for a given name.
     *
     * @param name The name to search for
     * @return A musical piece entity
     */
    public Optional<MusicalPieceDto> getByName(String name) {
        Optional<MusicalPieceEntity> entity = this.musicalPieceDao.getMusicalPieceFromName(name);
        if (entity.isPresent()) {
            return Optional.of(convertMusicalPieceToDto(entity.get()));
        } else {
            return Optional.empty();
        }
    }
}
