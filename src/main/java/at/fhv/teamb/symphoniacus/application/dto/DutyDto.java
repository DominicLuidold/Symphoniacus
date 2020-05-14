package at.fhv.teamb.symphoniacus.application.dto;

import java.time.LocalDateTime;

/**
 * @author : Danijel Antonijevic
 * @author Nino Heinzle
 * @created : 15.05.20, Fr.
 **/
public class DutyDto {

    private final int dutyId;
    private final String description;
    private final String timeOfDay;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final SeriesOfPerformancesDto seriesOfPerformances;

    public DutyDto(int dutyId,
                   String description,
                   String timeOfDay,
                   LocalDateTime start,
                   LocalDateTime end,
                   SeriesOfPerformancesDto seriesOfPerformances) {
        this.dutyId = dutyId;
        this.description = description;
        this.timeOfDay = timeOfDay;
        this.start = start;
        this.end = end;
        this.seriesOfPerformances = seriesOfPerformances;
    }

    public static class DutyDtoBuilder {
        private final int dutyId;
        private String description;
        private String timeOfDay;
        private LocalDateTime start;
        private LocalDateTime end;
        private SeriesOfPerformancesDto seriesOfPerformances;

        public DutyDtoBuilder(int dutyId) {
            this.dutyId = dutyId;
        }

        public DutyDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DutyDtoBuilder withTimeOfDay(String timeOfDay) {
            this.timeOfDay = timeOfDay;
            return this;
        }

        public DutyDtoBuilder withStart(LocalDateTime start) {
            this.start = start;
            return this;
        }

        public DutyDtoBuilder withEnd(LocalDateTime end) {
            this.end = end;
            return this;
        }

        public DutyDtoBuilder withSeriesOfPerformances(
            SeriesOfPerformancesDto seriesOfPerformances) {
            this.seriesOfPerformances = seriesOfPerformances;
            return this;
        }

        /**
         * Constructs a new DutyDto with the previously set options in the builder.
         * @return Constructed DutyDto.
         */
        public DutyDto build() {
            return new DutyDto(
                this.dutyId,
                this.description,
                this.timeOfDay,
                this.start,
                this.end,
                this.seriesOfPerformances
            );
        }
    }
}
