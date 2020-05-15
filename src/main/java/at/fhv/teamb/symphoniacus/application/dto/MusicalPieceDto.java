package at.fhv.teamb.symphoniacus.application.dto;

import java.util.List;
import java.util.Set;

/**
 * DTO for MusicalPiece.
 * @author : Danijel Antonijevic
 * @created : 15.05.20, Fr.
 **/
public class MusicalPieceDto {

    private final int musicalPieceId;
    private final String name;
    private final String category;
    private final Set<InstrumentationDto> instrumentations;
    private final List<SeriesOfPerformancesDto> seriesOfPerformances;

    private MusicalPieceDto(
        int musicalPieceId,
        String name,
        String category,
        Set<InstrumentationDto> instrumentations,
        List<SeriesOfPerformancesDto> seriesOfPerformances
    ) {
        this.musicalPieceId = musicalPieceId;
        this.name = name;
        this.category = category;
        this.instrumentations = instrumentations;
        this.seriesOfPerformances = seriesOfPerformances;
    }

    public int getMusicalPieceId() {
        return this.musicalPieceId;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public Set<InstrumentationDto> getInstrumentations() {
        return this.instrumentations;
    }

    public List<SeriesOfPerformancesDto> getSeriesOfPerformances() {
        return this.seriesOfPerformances;
    }

    public static class MusicalPieceDtoBuilder {

        private final int musicalPieceId;
        private String name;
        private String category;
        private Set<InstrumentationDto> instrumentations;
        private List<SeriesOfPerformancesDto> seriesOfPerformances;

        public MusicalPieceDtoBuilder(int musicalPieceId) {
            this.musicalPieceId = musicalPieceId;
        }

        public MusicalPieceDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public MusicalPieceDtoBuilder withCategory(String category) {
            this.category = category;
            return this;
        }

        public MusicalPieceDtoBuilder withInstrumentations(
            Set<InstrumentationDto> instrumentations) {
            this.instrumentations = instrumentations;
            return this;
        }

        public MusicalPieceDtoBuilder withSeriesOfPerformances(
            List<SeriesOfPerformancesDto> seriesOfPerformances) {
            this.seriesOfPerformances = seriesOfPerformances;
            return this;
        }

        /**
         * Constructs a new MusicalPieceDto with the previously set options in the builder.
         * @return Constructed MusicalPieceDto.
         */
        public MusicalPieceDto build() {
            return new MusicalPieceDto(
                this.musicalPieceId,
                this.name,
                this.category,
                this.instrumentations,
                this.seriesOfPerformances
            );
        }
    }
}
