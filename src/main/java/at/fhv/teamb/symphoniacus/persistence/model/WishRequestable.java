package at.fhv.teamb.symphoniacus.persistence.model;

import java.time.LocalDate;

public interface WishRequestable {
    MusicianEntity getMusician();

    String getDescription();

    LocalDate getStartDate();

    LocalDate getEndDate();
}
