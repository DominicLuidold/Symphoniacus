package at.fhv.teamb.symphoniacus.application.dto;

import java.time.LocalDate;

/**
 *DTO for DutyCategoryChangeLog.
 *
 * @author : Danijel Antonijevic
 **/
public class DutyCategoryChangeLogDto {

    private final int dutyCategoryChangelogId;
    private final DutyCategoryDto dutyCategory;
    private final LocalDate startDate;
    private final int points;

    private DutyCategoryChangeLogDto(
        int dutyCategoryChangelogId,
        DutyCategoryDto dutyCategory,
        LocalDate startDate,
        int points
    ) {
        this.dutyCategoryChangelogId = dutyCategoryChangelogId;
        this.dutyCategory = dutyCategory;
        this.startDate = startDate;
        this.points = points;
    }

    public int getDutyCategoryChangelogId() {
        return this.dutyCategoryChangelogId;
    }

    public DutyCategoryDto getDutyCategory() {
        return this.dutyCategory;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public int getPoints() {
        return this.points;
    }

    public static class DutyCategoryChangeLogDtoBuilder {
        private final int dutyCategoryChangelogId;
        private DutyCategoryDto dutyCategory;
        private LocalDate startDate;
        private int points;

        public DutyCategoryChangeLogDtoBuilder(int dutyCategoryChangelogId) {
            this.dutyCategoryChangelogId = dutyCategoryChangelogId;
        }

        public DutyCategoryChangeLogDtoBuilder withDutyCategory(
            DutyCategoryDto dutyCategory) {
            this.dutyCategory = dutyCategory;
            return this;
        }

        public DutyCategoryChangeLogDtoBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public DutyCategoryChangeLogDtoBuilder withPoints(int points) {
            this.points = points;
            return this;
        }

        /**
         * Constructs a new DutyCategoryChangeLogDto with the previously set options in the builder.
         * @return Constructed DutyCategoryChangeLogDto.
         */
        public DutyCategoryChangeLogDto build() {
            return new DutyCategoryChangeLogDto(
              this.dutyCategoryChangelogId,
              this.dutyCategory,
              this.startDate,
              this.points
            );
        }
    }
}
