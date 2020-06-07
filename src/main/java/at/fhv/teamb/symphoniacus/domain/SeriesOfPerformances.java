package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.application.dto.InstrumentationDto;
import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceDto;
import at.fhv.teamb.symphoniacus.application.dto.SeriesOfPerformancesDto;

import java.util.ResourceBundle;

public class SeriesOfPerformances {

    private SeriesOfPerformancesDto seriesDto;
    private ResourceBundle resources;
    private boolean doesSeriesAlreadyExist;

    public SeriesOfPerformances(
            SeriesOfPerformancesDto series,
            boolean doesAlreadyExists,
            ResourceBundle bundle
    ) {
        this.seriesDto = series;
        this.resources = bundle;
        this.doesSeriesAlreadyExist = doesAlreadyExists;
    }

    /**
     *
     * @return "VALIDATED" if validation is successful, else return error text for alert
     */
    public String validate() {
        if (doesSeriesAlreadyExist) {
            return this.resources.getString(
                    "seriesOfPerformances.error.seriesAlreadyExists.message"
            );
        } else if (seriesDto.getDescription().length() > 45) {
            return this.resources.getString(
                    "seriesOfPerformances.error.nameOfSeriesOutOfBounds.message");
        } else if (seriesDto.getEndDate().isBefore(seriesDto.getStartDate())) {
            return this.resources.getString(
                    "seriesOfPerformances.error.endingDateBeforeStartingDate.message"
            );
        } else if (seriesDto.getMusicalPieces().isEmpty()) {
            return this.resources.getString(
                    "seriesOfPerformances.error.noMusicalPieceSelected.message"
            );
        } else if (!isInstrumentationForMusicalPieceSelected()) {
            return  this.resources.getString(
                    "seriesOfPerformances.error.selectedMusicalPieceWithoutInstrumentation.message"
            );
        }
        return "VALIDATED";
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
