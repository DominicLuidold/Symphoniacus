package at.fhv.teamb.symphoniacus.application.dto;

import java.util.List;

/**
 * Dto for Instrumentation.
 * @author : Danijel Antonijevic
 **/
public class InstrumentationDto {
    private final int instrumentationId;
    private final String name;
    private final List<MusicalPieceDto> musicalPieces;
    private final List<SeriesOfPerformancesDto> seriesOfPerformances;
    private final List<InstrumentationPositionDto> instPositions;

    private InstrumentationDto(
        int instrumentationId,
        String name,
        List<MusicalPieceDto> musicalPieces,
        List<SeriesOfPerformancesDto> seriesOfPerformances,
        List<InstrumentationPositionDto> instPositions
    ) {
        this.instrumentationId = instrumentationId;
        this.name = name;
        this.musicalPieces = musicalPieces;
        this.seriesOfPerformances = seriesOfPerformances;
        this.instPositions = instPositions;
    }

    public int getInstrumentationId() {
        return this.instrumentationId;
    }

    public String getName() {
        return this.name;
    }

    public List<MusicalPieceDto> getMusicalPieces() {
        return this.musicalPieces;
    }

    public List<SeriesOfPerformancesDto> getSeriesOfPerformances() {
        return this.seriesOfPerformances;
    }

    public List<InstrumentationPositionDto> getInstPositions() {
        return this.instPositions;
    }

    public static class InstrumentationBuilder {
        private final int instrumentationId;
        private String name;
        private List<MusicalPieceDto> musicalPieces;
        private List<SeriesOfPerformancesDto> seriesOfPerformances;
        private List<InstrumentationPositionDto> instrumentationPositions;

        public InstrumentationBuilder(int instrumentationId) {
            this.instrumentationId = instrumentationId;
        }

        public InstrumentationBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public InstrumentationBuilder withMusicalPieces(List<MusicalPieceDto> musicalPieces) {
            this.musicalPieces = musicalPieces;
            return this;
        }

        public InstrumentationBuilder withSeriesOfPerformances(
            List<SeriesOfPerformancesDto> seriesOfPerformances
        ) {
            this.seriesOfPerformances = seriesOfPerformances;
            return this;
        }

        public InstrumentationBuilder withInstrumentationPositions(
            List<InstrumentationPositionDto> instPositions) {
            this.instrumentationPositions = instPositions;
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
                this.musicalPieces,
                this.seriesOfPerformances,
                this.instrumentationPositions
            );
        }
    }


}
