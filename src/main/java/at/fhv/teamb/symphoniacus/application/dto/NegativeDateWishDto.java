package at.fhv.teamb.symphoniacus.application.dto;

import java.time.LocalDate;

/**
 * DTO for a negative Date Wish
 *
 * @author Theresa Gierer
 */

public class NegativeDateWishDto {

    private final int negativeDateWishId;
    private final MusicianDto musician;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final List<MonthlyScheduleDto> monthlySchedules;

    private NegativeDateWishDto(
        int negativeDateWishId,
        MusicianDto musician,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        List<MonthlyScheduleDto> monthlySchedules
    ) {
        this.negativeDateWishId = negativeDateWishId;
        this.musician = musician;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlySchedules = monthlySchedules;
    }

    public int getNegativeDateWishId() {
        return this.negativeDateWishId;
    }

    public MusicianDto getMusician() {
        return this.musician;
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

    public List<MonthlyScheduleDto> getMonthlySchedule() {
        return this.monthlySchedules;
    }

    public static class NegativeDateWishDtoBuilder {

        private final int negativeDateWishId;
        private MusicianDto musician;
        private String description;
        private LocalDate startDate;
        private LocalDate endDate;
        private List<MonthlyScheduleDto> monthlySchedules;

        public NegativeDateWishDtoBuilder(int negativeDateWishId) {
            this.negativeDateWishId = negativeDateWishId;
        }

        public NegativeDateWishDtoBuilder withMusician(MusicianDto musician) {
            this.musician = musician;
            return this;
        }

        public NegativeDateWishDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public NegativeDateWishDtoBuilder withStartDate(LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public NegativeDateWishDtoBuilder withEndDate(LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public NegativeDateWishDtoBuilder withMonthlySchedules(
            List<MonthlyScheduleDto> monthlySchedules) {
            this.monthlySchedules = monthlySchedules;
            return this;
        }

        /**
         * Constructs a new NegativeDateWishDto with the previously set options in the builder.
         * @return Constructed NegativeDateWishDto.
         */
        public NegativeDateWishDto build() {
            return new NegativeDateWishDto(
                this.negativeDateWishId,
                this.musician,
                this.description,
                this.startDate,
                this.endDate,
                this.monthlySchedules
            );
        }
    }
}
