package at.fhv.teamb.symphoniacus.application.dto;

import java.util.List;

/**
 * DTO for DutyCategory.
 *
 * @author Danijel Antonijevic
 **/
public class DutyCategoryDto {

    private final int dutyCategoryId;
    private final String type;
    private final int points;
    private final List<DutyCategoryChangeLogDto> changeLogs;
    private final List<DutyDto> duties;

    private DutyCategoryDto(
        int dutyCategoryId,
        String type,
        int points,
        List<DutyCategoryChangeLogDto> changeLogs,
        List<DutyDto> duties
    ) {
        this.dutyCategoryId = dutyCategoryId;
        this.type = type;
        this.points = points;
        this.changeLogs = changeLogs;
        this.duties = duties;
    }

    public int getDutyCategoryId() {
        return this.dutyCategoryId;
    }

    public String getType() {
        return this.type;
    }

    public int getPoints() {
        return this.points;
    }

    public List<DutyCategoryChangeLogDto> getChangeLogs() {
        return this.changeLogs;
    }

    public List<DutyDto> getDuties() {
        return this.duties;
    }

    public static class DutyCategoryDtoBuilder {
        private final int dutyCategoryId;
        private String type;
        private int points;
        private List<DutyCategoryChangeLogDto> changeLogs;
        private List<DutyDto> duties;

        public DutyCategoryDtoBuilder(int dutyCategoryId) {
            this.dutyCategoryId = dutyCategoryId;
        }

        public DutyCategoryDtoBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public DutyCategoryDtoBuilder withPoints(int points) {
            this.points = points;
            return this;
        }

        public DutyCategoryDtoBuilder withChangeLogs(
            List<DutyCategoryChangeLogDto> changeLogs) {
            this.changeLogs = changeLogs;
            return this;
        }

        public DutyCategoryDtoBuilder withDuties(
            List<DutyDto> duties) {
            this.duties = duties;
            return this;
        }

        /**
         * Constructs a new DutyCategoryDto with the previously set options in the builder.
         *
         * @return Constructed DutyCategoryDto.
         */
        public DutyCategoryDto build() {
            return new DutyCategoryDto(
                this.dutyCategoryId,
                this.type,
                this.points,
                this.changeLogs,
                this.duties
            );
        }
    }
}
