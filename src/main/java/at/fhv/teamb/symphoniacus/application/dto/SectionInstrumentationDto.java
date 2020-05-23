package at.fhv.teamb.symphoniacus.application.dto;

/**
 * DTO for SectionInstrumentations.
 *
 * @author Nino Heinzle
 **/
public class SectionInstrumentationDto {
    private final int sectionInstrumentationId;
    private final MusicalPieceDto musicalPiece;
    private final String predefinedSectionInstrumentation;

    /**
     * Creates  a new SectionInstrumentationDto.
     *
     * @param sectionInstrumentationId         given SectionInstrumentation
     * @param musicalPiece                     given Musical Piece
     * @param predefinedSectionInstrumentation given Predefined Section Inst.
     */
    private SectionInstrumentationDto(
        int sectionInstrumentationId,
        MusicalPieceDto musicalPiece,
        String predefinedSectionInstrumentation
    ) {
        this.sectionInstrumentationId = sectionInstrumentationId;
        this.musicalPiece = musicalPiece;
        this.predefinedSectionInstrumentation = predefinedSectionInstrumentation;
    }

    public int getSectionInstrumentationId() {
        return this.sectionInstrumentationId;
    }

    public MusicalPieceDto getMusicalPiece() {
        return this.musicalPiece;
    }

    public String getPredefinedSectionInstrumentation() {
        return this.predefinedSectionInstrumentation;
    }

    public static class SectionInstrumentationDtoBuilder {
        private final int sectionInstrumentationId;
        private MusicalPieceDto musicalPiece;
        private String predefinedSectionInstrumentation;

        public SectionInstrumentationDtoBuilder(int sectionInstrumentationId) {
            this.sectionInstrumentationId = sectionInstrumentationId;
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

        /**
         * Constructs a new SectionInstrumentationDto with the previously
         * set options in the builder.
         *
         * @return Constructed UserDTO.
         */
        public SectionInstrumentationDto build() {
            return new SectionInstrumentationDto(
                this.sectionInstrumentationId,
                this.musicalPiece,
                this.predefinedSectionInstrumentation
            );
        }
    }
}
