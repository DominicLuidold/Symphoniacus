package at.fhv.teamb.symphoniacus.persistence.model;

import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import java.time.LocalDate;

public interface WishRequestable {
    IMusicianEntity getMusician();

    String getDescription();

    LocalDate getStartDate();

    LocalDate getEndDate();

    Integer getID();
}
