package at.fhv.teamb.symphoniacus.application.dto.wishDtos;

import java.time.LocalDate;

/**
 * Model class for {@link DateWishDto}.
 *
 * @author Tobias Moser
 */
public class DateWishDto {
    private LocalDate start;
    private LocalDate end;

    public DateWishDto() {}

    public DateWishDto(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }
}
