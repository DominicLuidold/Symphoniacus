package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.application.ValidationResult;
import at.fhv.teamb.symphoniacus.application.dto.InstrumentationDto;
import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceDto;
import at.fhv.teamb.symphoniacus.application.dto.SeriesOfPerformancesDto;
import java.util.Locale;
import java.util.ResourceBundle;

public class SeriesOfPerformances implements Validatable {
    private final SeriesOfPerformancesDto seriesDto;
    private final ResourceBundle resources;
    private final boolean doesSeriesAlreadyExist;

    /**
     * Constructor for domain object.
     *
     * @param series            the series of performances
     * @param doesAlreadyExists boolean if series already exists
     */
    public SeriesOfPerformances(
        SeriesOfPerformancesDto series,
        boolean doesAlreadyExists
    ) {
        this.seriesDto = series;
        this.doesSeriesAlreadyExist = doesAlreadyExists;
        Locale locale = new Locale("en", "UK");
        this.resources = ResourceBundle.getBundle("bundles.language", locale);
    }

    /**
     * Input Validation of a Series of Performances.
     *
     * @return "VALIDATED" if validation is successful, else return error text for alert.
     */
    public ValidationResult<SeriesOfPerformancesDto> isValid() {
        ValidationResult<SeriesOfPerformancesDto> result;
        if (doesSeriesAlreadyExist) {
            result = new ValidationResult<>(
                this.resources.getString("seriesOfPerformances.error.seriesAlreadyExists.message"),
                false
            );
            return result;
        } else if (seriesDto.getDescription().length() > 45) {
            result = new ValidationResult<>(
                this.resources.getString(
                    "seriesOfPerformances.error.nameOfSeriesOutOfBounds.message"
                ),
                false
            );
            return result;
        } else if (seriesDto.getEndDate().isBefore(seriesDto.getStartDate())) {
            result = new ValidationResult<>(
                this.resources.getString(
                    "seriesOfPerformances.error.endingDateBeforeStartingDate.message"
                ),
                false
            );
            return result;
        } else if (seriesDto.getMusicalPieces().isEmpty()) {
            result = new ValidationResult<>(
                this.resources.getString(
                    "seriesOfPerformances.error.noMusicalPieceSelected.message"
                ),
                false
            );
            return result;
        } else if (!isInstrumentationForMusicalPieceSelected()) {
            result = new ValidationResult<>(
                this.resources.getString(
                    "seriesOfPerformances.error.selectedMusicalPieceWithoutInstrumentation.message"
                ),
                false
            );
            return result;
        }
        result = new ValidationResult<>(this.resources.getString("VALIDATED"), true);
        return result;
    }

    private boolean isInstrumentationForMusicalPieceSelected() {
        for (MusicalPieceDto m : this.seriesDto.getMusicalPieces()) {
            boolean isSelected = false;

            for (InstrumentationDto i : m.getInstrumentations()) {
                for (InstrumentationDto instDto : this.seriesDto.getInstrumentations()) {
                    if (instDto.getInstrumentationId() == (i.getInstrumentationId())) {
                        isSelected = true;
                        break;
                    }
                }
            }
            if (!isSelected) {
                return false;
            }
        }
        return true;
    }
}
