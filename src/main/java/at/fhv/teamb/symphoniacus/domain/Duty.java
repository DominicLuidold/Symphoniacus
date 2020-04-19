package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Domain object for Duty.
 *
 * @author Valentin Goronjio
 */
public class Duty {

    private static final Logger LOG = LogManager.getLogger(Duty.class);
    private DutyEntity entity;
    private String title;

    public Duty(DutyEntity entity) {
        this.entity = entity;
    }

    public DutyEntity getEntity() {
        return entity;
    }

    public void setEntity(DutyEntity entity) {
        this.entity = entity;
    }

    /**
     * Generates a calendar-friendly title for Duty.
     * @return String that looks like this: CATEGORY for SERIES (DESCRIPTION),
     *     where the "for SERIES" "(DESCRIPTION)" parts are optional
     * @see DutyTest
     * */
    public String getTitle() {
        if (this.title == null) {
            LOG.debug("No title generated yet - generating");
            Locale locale = new Locale("en", "UK");
            Locale.setDefault(locale);
            ResourceBundle bundle = ResourceBundle.getBundle("bundles.language", locale);

            StringBuilder sb = new StringBuilder();
            // <CATEGORY>
            sb.append(this.entity.getDutyCategory().getType());

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
}
