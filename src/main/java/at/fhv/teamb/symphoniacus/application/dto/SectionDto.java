package at.fhv.teamb.symphoniacus.application.dto;

import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import java.util.List;

/**
 * DTO for Section.
 *
 * @author Tobias Moser
 */
public class SectionDto {

    private final int sectionId;
    private final String sectionShortcut;
    private final String description;
    private final List<SectionMonthlyScheduleEntity> sectionMonthlySchedules;
    private final List<MusicianEntity> musicians;
    private final List<DutyPositionEntity> dutyPositions;
    private final List<SectionInstrumentationEntity> sectionInstrumentations;

    private SectionDto(
        int sectionId,
        String sectionShortcut,
        String description,
        List<SectionMonthlyScheduleEntity> sectionMonthlySchedules,
        List<MusicianEntity> musicians,
        List<DutyPositionEntity> dutyPositions,
        List<SectionInstrumentationEntity> sectionInstrumentations
    ) {
        this.sectionId = sectionId;
        this.sectionShortcut = sectionShortcut;
        this.description = description;
        this.sectionMonthlySchedules = sectionMonthlySchedules;
        this.musicians = musicians;
        this.dutyPositions = dutyPositions;
        this.sectionInstrumentations = sectionInstrumentations;
    }


    public int getSectionId() {
        return this.sectionId;
    }

    public String getSectionShortcut() {
        return this.sectionShortcut;
    }

    public String getDescription() {
        return this.description;
    }

    public List<SectionMonthlyScheduleEntity> getSectionMonthlySchedules() {
        return this.sectionMonthlySchedules;
    }

    public List<MusicianEntity> getMusicians() {
        return this.musicians;
    }

    public List<DutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    public List<SectionInstrumentationEntity> getSectionInstrumentations() {
        return this.sectionInstrumentations;
    }

    public static class SectionDtoBuilder {
        private final int sectionId;
        private String sectionShortcut;
        private String description;
        private List<SectionMonthlyScheduleEntity> sectionMonthlySchedules;
        private List<MusicianEntity> musicians;
        private List<DutyPositionEntity> dutyPositions;
        private List<SectionInstrumentationEntity> sectionInstrumentations;

        // we need this to be set
        public SectionDtoBuilder(int sectionId) {
            this.sectionId = sectionId;
        }

        public SectionDtoBuilder withSectionShortcut(String sectionShortcut) {
            this.sectionShortcut = sectionShortcut;
            return this;
        }

        public SectionDtoBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public SectionDtoBuilder withSectionMonthlySchedules(
            List<SectionMonthlyScheduleEntity> sectionMonthlySchedules
        ) {
            this.sectionMonthlySchedules = sectionMonthlySchedules;
            return this;
        }

        public SectionDtoBuilder withMusicians(List<MusicianEntity> musicians) {
            this.musicians = musicians;
            return this;
        }

        public SectionDtoBuilder withDutyPositions(List<DutyPositionEntity> dutyPositions) {
            this.dutyPositions = dutyPositions;
            return this;
        }

        public SectionDtoBuilder withSectionInstrumentations(
            List<SectionInstrumentationEntity> sectionInstrumentations
        ) {
            this.sectionInstrumentations = sectionInstrumentations;
            return this;
        }

        /**
         * Constructs a new SectionDto with the previously set options in the builder.
         * @return Constructed SectionDto.
         */
        public SectionDto build() {
            return new SectionDto(
                this.sectionId,
                this.sectionShortcut,
                this.description,
                this.sectionMonthlySchedules,
                this.musicians,
                this.dutyPositions,
                this.sectionInstrumentations
            );
        }


    }
}
