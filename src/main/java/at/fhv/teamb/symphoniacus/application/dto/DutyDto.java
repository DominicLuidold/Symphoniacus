package at.fhv.teamb.symphoniacus.application.dto;

import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for Duty.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 **/
public class DutyDto {
    private final int dutyId;
    private final String description;
    private final String timeOfDay;
    private final DutyCategoryDto dutyCategory;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final SeriesOfPerformancesDto seriesOfPerformances;
    private final PersistenceState persistenceState;
    private final int points;
    private final Set<InstrumentationDto> instrumentations;

    private DutyDto(
        int dutyId,
        String description,
        String timeOfDay,
        DutyCategoryDto dutyCategory,
        LocalDateTime start,
        LocalDateTime end,
        SeriesOfPerformancesDto seriesOfPerformances,
        Set<InstrumentationDto> instrumentations,
        int points,
        PersistenceState persistenceState
    ) {
        this.dutyId = dutyId;
        this.description = description;
        this.timeOfDay = timeOfDay;
        this.dutyCategory = dutyCategory;
        this.start = start;
        this.end = end;
        this.seriesOfPerformances = seriesOfPerformances;
        this.instrumentations = instrumentations;
        this.points = points;
        this.persistenceState = persistenceState;
    }

    public int getDutyId() {
        return this.dutyId;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTimeOfDay() {
        return this.timeOfDay;
    }

    public DutyCategoryDto getDutyCategory() {
        return this.dutyCategory;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public SeriesOfPerformancesDto getSeriesOfPerformances() {
        return this.seriesOfPerformances;
    }

    public Set<InstrumentationDto> getInstrumentations() {
        return this.instrumentations;
    }

    public int getPoints() {
        return this.points;
    }

    public PersistenceState getPersistenceState() {
        return this.persistenceState;
    }

    public static class DutyDtoBuilder {
        private int dutyId;
        private String description;
        private String timeOfDay;
        private DutyCategoryDto dutyCategory;
        private LocalDateTime start;
        private LocalDateTime end;
        private SeriesOfPerformancesDto seriesOfPerformances;
        private Set<InstrumentationDto> instrumentations;
        private int points;
        private PersistenceState persistenceState;

        public DutyDtoBuilder withDutyId(int dutyId) {
            this.dutyId = dutyId;
            return this;
        }

        public DutyDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DutyDtoBuilder withTimeOfDay(String timeOfDay) {
            this.timeOfDay = timeOfDay;
            return this;
        }

        public DutyDtoBuilder withDutyCategory(
            DutyCategoryDto dutyCategory) {
            this.dutyCategory = dutyCategory;
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
            SeriesOfPerformancesDto seriesOfPerformances
        ) {
            this.seriesOfPerformances = seriesOfPerformances;
            return this;
        }

        public DutyDtoBuilder withInstrumentations(Set<InstrumentationDto> instrumentations) {
            this.instrumentations = instrumentations;
            return this;
        }

        public DutyDtoBuilder withPoints(int points) {
            this.points = points;
            return this;
        }

        public DutyDtoBuilder withPersistenceState(PersistenceState persistenceState) {
            this.persistenceState = persistenceState;
            return this;
        }

        /**
         * Constructs a new DutyDto with the previously set options in the builder.
         *
         * @return Constructed DutyDto.
         */
        public DutyDto build() {
            return new DutyDto(
                this.dutyId,
                this.description,
                this.timeOfDay,
                this.dutyCategory,
                this.start,
                this.end,
                this.seriesOfPerformances,
                this.instrumentations,
                this.points,
                this.persistenceState
            );
        }
    }
}
