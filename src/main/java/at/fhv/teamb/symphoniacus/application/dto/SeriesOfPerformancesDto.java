package at.fhv.teamb.symphoniacus.application.dto;

import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for Series Of Performances.
 * @author : Danijel Antonijevic
 **/
public class SeriesOfPerformancesDto {

    private final int seriesOfPerformancesId;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final boolean isTour;
    private final Set<InstrumentationDto> instrumentations;
    private final Set<MusicalPieceDto> musicalPieces;
    private final Set<DutyDto> duties;

    private SeriesOfPerformancesDto(
        int seriesId,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        boolean isTour,
        Set<InstrumentationDto> instrumentations,
        Set<MusicalPieceDto> musicalPieces,
        Set<DutyDto> duties
    ) {
        this.seriesOfPerformancesId = seriesId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isTour = isTour;
        this.instrumentations = instrumentations;
        this.musicalPieces = musicalPieces;
        this.duties = duties;

    }

    public int getSeriesOfPerformancesId() {
        return this.seriesOfPerformancesId;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public boolean isTour() {
        return this.isTour;
    }

    public Set<InstrumentationDto> getInstrumentations() {
        return this.instrumentations;
    }

    public Set<MusicalPieceDto> getMusicalPieces() {
        return this.musicalPieces;
    }

    public Set<DutyDto> getDuties() {
        return this.duties;
    }

    public static class SeriesOfPerformancesDtoBuilder {
        private final int seriesOfPerformancesId;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private boolean isTour;
        private Set<InstrumentationDto> instrumentations;
        private Set<MusicalPieceDto> musicalPieces;
        private Set<DutyDto> duties;

        public SeriesOfPerformancesDtoBuilder(int seriesId) {
            this.seriesOfPerformancesId = seriesId;
        }

        public SeriesOfPerformancesDtoBuilder withDescription(String desc) {
            this.description = desc;
            return this;
        }

        public SeriesOfPerformancesDtoBuilder withStartDate(LocalDate start) {
            this.startDate = start;
            return this;
        }

        public SeriesOfPerformancesDtoBuilder withEndDate(LocalDate end) {
            this.endDate = end;
            return this;
        }

        public SeriesOfPerformancesDtoBuilder withIsTour(boolean isTour) {
            this.isTour = isTour;
            return this;
        }

        public SeriesOfPerformancesDtoBuilder withInstrumentations(
            Set<InstrumentationDto> instrumentations
        ) {
            this.instrumentations = instrumentations;
            return this;
        }

        public SeriesOfPerformancesDtoBuilder withMusicalPieces(Set<MusicalPieceDto> musicalPieces) {
            this.musicalPieces = musicalPieces;
            return this;
        }

        public SeriesOfPerformancesDtoBuilder withDuties(Set<DutyDto> duties) {
            this.duties = duties;
            return this;
        }

        /**
         * Constructs a new SeriesOfPerformancesDto with the previously set options in the builder.
         * @return Constructed SeriesOfPerformancesDto.
         */
        public SeriesOfPerformancesDto build() {
            return new SeriesOfPerformancesDto(
                this.seriesOfPerformancesId,
                this.description,
                this.startDate,
                this.endDate,
                this.isTour,
                this.instrumentations,
                this.musicalPieces,
                this.duties
            );
        }

    }
}
