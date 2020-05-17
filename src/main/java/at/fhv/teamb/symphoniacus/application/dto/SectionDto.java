package at.fhv.teamb.symphoniacus.application.dto;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionInstrumentationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
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
    private final List<ISectionMonthlyScheduleEntity> sectionMonthlySchedules;
    private final List<IMusicianEntity> musicians;
    private final List<IDutyPositionEntity> dutyPositions;
    private final List<ISectionInstrumentationEntity> sectionInstrumentations;

    private SectionDto(
        int sectionId,
        String sectionShortcut,
        String description,
        List<ISectionMonthlyScheduleEntity> sectionMonthlySchedules,
        List<IMusicianEntity> musicians,
        List<IDutyPositionEntity> dutyPositions,
        List<ISectionInstrumentationEntity> sectionInstrumentations
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

    public List<ISectionMonthlyScheduleEntity> getSectionMonthlySchedules() {
        return this.sectionMonthlySchedules;
    }

    public List<IMusicianEntity> getMusicians() {
        return this.musicians;
    }

    public List<IDutyPositionEntity> getDutyPositions() {
        return this.dutyPositions;
    }

    public List<ISectionInstrumentationEntity> getSectionInstrumentations() {
        return this.sectionInstrumentations;
    }

    public static class SectionDtoBuilder {
        private final int sectionId;
        private String sectionShortcut;
        private String description;
        private List<ISectionMonthlyScheduleEntity> sectionMonthlySchedules;
        private List<IMusicianEntity> musicians;
        private List<IDutyPositionEntity> dutyPositions;
        private List<ISectionInstrumentationEntity> sectionInstrumentations;

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
            List<ISectionMonthlyScheduleEntity> sectionMonthlySchedules
        ) {
            this.sectionMonthlySchedules = sectionMonthlySchedules;
            return this;
        }

        public SectionDtoBuilder withMusicians(List<IMusicianEntity> musicians) {
            this.musicians = musicians;
            return this;
        }

        public SectionDtoBuilder withDutyPositions(List<IDutyPositionEntity> dutyPositions) {
            this.dutyPositions = dutyPositions;
            return this;
        }

        public SectionDtoBuilder withSectionInstrumentations(
            List<ISectionInstrumentationEntity> sectionInstrumentations
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
