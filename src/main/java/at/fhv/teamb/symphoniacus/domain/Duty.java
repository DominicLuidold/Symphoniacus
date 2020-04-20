package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.util.Locale;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Domain object for Duty.
 *
 * @author Valentin Goronjic
 */
public class Duty {
    private static final Logger LOG = LogManager.getLogger(Duty.class);
    private DutyEntity entity;
    private List<DutyPosition> dutyPositions;
    private String title;

    public Duty(DutyEntity entity) {
        this(entity, null);
    }

    /**
     * Initializes the Duty object based on provided {@link DutyEntity} and List of
     * {@link DutyPosition}s.
     *
     * @param entity        The entity to use
     * @param dutyPositions The List of DutyPositions to use
     */
    public Duty(DutyEntity entity, List<DutyPosition> dutyPositions) {
        this.entity = entity;
        if (dutyPositions == null) {
            this.dutyPositions = null;
        } else {
            this.dutyPositions = Collections.unmodifiableList(dutyPositions);
        }
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

    public DutyEntity getEntity() {
        return this.entity;
    }
}
