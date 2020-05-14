package at.fhv.teamb.symphoniacus.application.dto;

/**
 * DTO for Duties.
 * @author Nino Heinzle
 * @created : 15.05.20, Fr.
 **/
public class SectionInstrumentationDto {
    private final int sectionInstrumentationId;
    private final String name;
    private final MusicalPieceDto musicalPiece;
    private final String predefinedSectionInstrumentation;

    public SectionInstrumentationDto(int sectionInstrumentationId,
                                     String name,
                                     MusicalPieceDto musicalPiece,
                                     String predefinedSectionInstrumentation) {
        this.sectionInstrumentationId = sectionInstrumentationId;
        this.name = name;
        this.musicalPiece = musicalPiece;
        this.predefinedSectionInstrumentation = predefinedSectionInstrumentation;
    }

    public int getSectionInstrumentationId() {
        return this.sectionInstrumentationId;
    }

    public String getName() {
        return this.name;
    }

    public MusicalPieceDto getMusicalPiece() {
        return this.musicalPiece;
    }

    public String getPredefinedSectionInstrumentation() {
        return this.predefinedSectionInstrumentation;
    }

    public static class SectionInstrumentationDtoBuilder {
        private final int sectionInstrumentationId;
        private String name;
        private MusicalPieceDto musicalPiece;
        private String predefinedSectionInstrumentation;

        public SectionInstrumentationDtoBuilder(int sectionInstrumentationId) {
            this.sectionInstrumentationId = sectionInstrumentationId;
        }

        public SectionInstrumentationDtoBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public SectionInstrumentationDtoBuilder withMusicalPiece(
            MusicalPieceDto musicalPiece) {
            this.musicalPiece = musicalPiece;
            return this;
        }

        public SectionInstrumentationDtoBuilder withPredefinedSectionInstrumentation(
            String predefinedSectionInstrumentation) {
            this.predefinedSectionInstrumentation = predefinedSectionInstrumentation;
            return this;
        }
    }

    /**
     * Constructs a new SectionInstrugit smentationDto with the previously set options in the builder.
     * @return Constructed SectionInstrumentationDto.
     */
    public SectionInstrumentationDto build() {
        return new SectionInstrumentationDto(
            this.sectionInstrumentationId,
            this.name,
            this.musicalPiece,
            this.predefinedSectionInstrumentation
        );
    }



}
