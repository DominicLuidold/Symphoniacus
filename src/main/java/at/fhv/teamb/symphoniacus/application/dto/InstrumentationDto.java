package at.fhv.teamb.symphoniacus.application.dto;

import at.fhv.teamb.symphoniacus.persistence.model.InstrumentationEntity;
import java.util.List;

/**
 * Dto for Instrumentation.
 * @author : Danijel Antonijevic
 **/
public class InstrumentationDto {
    private final int instrumentationId;
    private final String name;
    private final MusicalPieceDto musicalPiece;
    private final List<SeriesOfPerformancesDto> seriesOfPerformances;
    private final List<InstrumentationPositionDto> instPositions;
    private final List<SectionInstrumentationDto> sectionInstrumentations;

    private InstrumentationDto(
        int instrumentationId,
        String name,
        MusicalPieceDto musicalPiece,
        List<SeriesOfPerformancesDto> seriesOfPerformances,
        List<InstrumentationPositionDto> instPositions,
        List<SectionInstrumentationDto> sectionInstrumentations
    ) {
        this.instrumentationId = instrumentationId;
        this.name = name;
        this.musicalPiece = musicalPiece;
        this.seriesOfPerformances = seriesOfPerformances;
        this.instPositions = instPositions;
        this.sectionInstrumentations = sectionInstrumentations;
    }

    public int getInstrumentationId() {
        return this.instrumentationId;
    }

    public String getName() {
        return this.name;
    }

    public MusicalPieceDto getMusicalPiece() {
        return this.musicalPiece;
    }

    public List<SeriesOfPerformancesDto> getSeriesOfPerformances() {
        return this.seriesOfPerformances;
    }

    public List<InstrumentationPositionDto> getInstPositions() {
        return this.instPositions;
    }

    public List<SectionInstrumentationDto> getSectionInstrumentations() {
        return this.sectionInstrumentations;
    }

    public static class InstrumentationDtoBuilder {
        private final int instrumentationId;
        private String name;
        private MusicalPieceDto musicalPiece;
        private List<SeriesOfPerformancesDto> seriesOfPerformances;
        private List<InstrumentationPositionDto> instrumentationPositions;
        private List<SectionInstrumentationDto> sectionInstrumentations;

        public InstrumentationDtoBuilder(int instrumentationId) {
            this.instrumentationId = instrumentationId;
        }

        public InstrumentationDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public InstrumentationDtoBuilder withMusicalPiece(MusicalPieceDto musicalPiece) {
            this.musicalPiece = musicalPiece;
            return this;
        }

        public InstrumentationDtoBuilder withSeriesOfPerformances(
            List<SeriesOfPerformancesDto> seriesOfPerformances
        ) {
            this.seriesOfPerformances = seriesOfPerformances;
            return this;
        }

        public InstrumentationDtoBuilder withInstrumentationPositions(
            List<InstrumentationPositionDto> instPositions) {
            this.instrumentationPositions = instPositions;
            return this;
        }

        public InstrumentationDtoBuilder withSectionInstrumentations(
            List<SectionInstrumentationDto> sectionInstrumentations) {
            this.sectionInstrumentations = sectionInstrumentations;
            return this;
        }

        /**
         * Constructs a new InstrumentationDto with the previously set options in the builder.
         * @return Constructed InstrumentationDto.
         */
        public InstrumentationDto build() {
            return new InstrumentationDto(
                this.instrumentationId,
                this.name,
                this.musicalPiece,
                this.seriesOfPerformances,
                this.instrumentationPositions,
                this.sectionInstrumentations
            );
        }
    }
}
