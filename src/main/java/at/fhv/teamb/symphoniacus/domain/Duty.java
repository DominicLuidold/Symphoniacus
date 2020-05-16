package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.PersistenceState;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Domain object for Duty.
 *
 * @author Valentin Goronjic
 * @author Dominic Luidold
 */
public class Duty {
    private static final Logger LOG = LogManager.getLogger(Duty.class);
    private IDutyEntity entity;
    private List<DutyPosition> dutyPositions;
    private String title;
    private PersistenceState persistenceState;
    private List<MusicalPiece> musicalPieces = new LinkedList<>(); // prevent NPE

    public Duty(IDutyEntity entity) {
        this(entity, null, null);
    }

    /**
     * Initializes the Duty object based on provided {@link DutyEntity} and List of
     * {@link DutyPosition}s.
     *
     * @param entity        The entity to use
     * @param dutyPositions The List of DutyPositions to use
     */
    public Duty(IDutyEntity entity, List<DutyPosition> dutyPositions, List<MusicalPiece> musicalPieces) {
        
        this.entity = entity;
        if (dutyPositions != null) {
            this.dutyPositions = Collections.unmodifiableList(dutyPositions);
        }
        if (musicalPieces != null) {
            this.musicalPieces = musicalPieces;
        }
    }

    /**
     * Determines which {@link Musician}s are available for a given {@link DutyPosition} that
     * belongs to this {@link Duty}.
     *
     * @param allSectionMusicians   A Set of all {@link Section} musicians
     * @param dutiesOfThisDay       A List of duties
     * @param locallySetMusicians   A Set of musicians locally assigned to a duty position
     * @param locallyUnsetMusicians A Set of musicians locally removed from a duty position
     * @return A Set of musicians that are available for a given position
     */
    public Set<Musician> determineAvailableMusicians(
        Set<Musician> allSectionMusicians,
        List<Duty> dutiesOfThisDay,
        Set<Musician> locallySetMusicians,
        Set<Musician> locallyUnsetMusicians
    ) {
        Set<Musician> availableMusicians = new HashSet<>(allSectionMusicians);

        // Remove the current duty from the list of possible conflicts
        dutiesOfThisDay.remove(this);

        // Mark all musicians as unavailable that are already assigned to another duty position
        for (DutyPosition possibleConflict : this.getDutyPositions()) {
            if (possibleConflict.getAssignedMusician().isPresent()) {
                availableMusicians.remove(possibleConflict.getAssignedMusician().get());
            }
        }

        // Remove all musicians that are assigned to other duties which happen at the same time
        for (Duty duty : dutiesOfThisDay) {
            if (this.entity.getStart().isBefore(duty.getEntity().getEnd())
                && this.entity.getEnd().isAfter(duty.getEntity().getStart())
            ) {
                for (DutyPosition possibleConflict : duty.getDutyPositions()) {
                    if (possibleConflict.getAssignedMusician().isPresent()
                        // If musician is a placeholder for an external, don't mark as unavailable
                        && !possibleConflict.getAssignedMusician().get().isExternal()
                    ) {
                        availableMusicians.remove(possibleConflict.getAssignedMusician().get());
                    }
                }
            }
        }

        // Mark all musicians as unavailable that are locally already assigned to a duty position
        availableMusicians.removeAll(locallySetMusicians);
        locallySetMusicians.clear();

        // Mark all musicians as available that are locally not assigned to a duty position anymore
        availableMusicians.addAll(locallyUnsetMusicians);
        locallyUnsetMusicians.clear();

        return availableMusicians;
    }

    /**
     * Generates a calendar-friendly title for Duty.
     *
     * @return String that looks like this: CATEGORY for SERIES (DESCRIPTION), where the
     *     "for SERIES", "(DESCRIPTION)" parts are optional.
     */
    public String getTitle() {
        if (this.title == null) {
            LOG.debug("No title generated yet - generating");
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);

            StringBuilder sb = new StringBuilder();
            if (this.entity.getDutyCategory() != null) {
                // <CATEGORY>
                sb.append(this.entity.getDutyCategory().getType());
            }

            // for <SOP>
            if (this.entity.getSeriesOfPerformances() != null) {
                sb.append(" ");
                sb.append(bundle.getString("domain.duty.title.for"));
                sb.append(" ");
                sb.append(this.entity.getSeriesOfPerformances().getDescription());
            }

            if (this.entity.getDescription() != null && !this.entity.getDescription().isEmpty()) {
                sb.append(" ");
                if (!this.entity.getDescription().startsWith("(")) {
                    sb.append("(");
                }

                sb.append(this.entity.getDescription());

                if (!this.entity.getDescription().endsWith(")")) {
                    sb.append(")");
                }
            }

            this.title = sb.toString();
        }
        return this.title;
    }

    /**
     * Returns a List of all {@link DutyPosition}s related to this duty.
     *
     * @return A List of DutyPositions
     */
    public List<DutyPosition> getDutyPositions() {
        return Objects.requireNonNullElseGet(this.dutyPositions, LinkedList::new);
    }

    public IDutyEntity getEntity() {
        return this.entity;
    }

    public PersistenceState getPersistenceState() {
        return this.persistenceState;
    }

    public void setPersistenceState(PersistenceState persistenceState) {
        this.persistenceState = persistenceState;
    }

    public List<MusicalPiece> getMusicalPieces() {
        return this.musicalPieces;
    }

    public void setMusicalPieces(List<MusicalPiece> musicalPieces) {
        this.musicalPieces = musicalPieces;
    }

    @Override
    public boolean equals(Object obj) {
        // Return true if object is compared with itself
        if (obj == this) {
            return true;
        }

        // Check if object is an instance of Duty or not
        if (!(obj instanceof Duty)) {
            return false;
        }

        // Typecast obj to Duty to compare data members
        Duty d = (Duty) obj;

        // Compare data members and return accordingly
        return this.entity.getDutyId().equals(d.getEntity().getDutyId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.entity.getDutyId());
    }
}
