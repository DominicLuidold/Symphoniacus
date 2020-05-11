package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Domain Object Points responsible for calculating correct amount of requested points.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class Points {
    private static final Logger LOG = LogManager.getLogger(Points.class);
    private final int value;

    private Points(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    /**
     * Calculate debit points (Soll) of a month.
     *
     * @param obligationEntity Contract of a musician w/ debitPoints
     * @return Points
     */
    public static Optional<Points> calcDebitPoints(ContractualObligationEntity obligationEntity) {
        return Optional.of(new Points(obligationEntity.getPointsPerMonth()));
    }

    /**
     * Calculate Gained Points (Ist-Zustand) of a musician observing the current Date.
     *
     * @param duties        All given Duties by the Points Manager
     *                      -> All of the duties are within the same month
     * @param catChangeLogs List of changelogs containing
     *                      history of changed points to dutycategories
     * @return Points
     */
    public static Optional<Points> calcGainedPoints(
        List<DutyEntity> duties,
        List<DutyCategoryChangelogEntity> catChangeLogs
    ) {
        if (duties.isEmpty()) {
            LOG.debug("No duties delivered -> Points cannot be calculated");
            return Optional.of(new Points(0));
        }
        int points = 0;
        for (DutyEntity duty : duties) {
            if (duty.getStart().isBefore(LocalDateTime.now())
                || duty.getStart().isEqual(LocalDateTime.now())
            ) {
                points += giveChangeLogPointsOfDuty(duty, catChangeLogs);
            }
        }
        return Optional.of(new Points(points));
    }

    /**
     * Calculate Balance Points (Saldo) observing the month of which the duties are in.
     *
     * @param duties         List of duties
     *                       -> All of the duties are within the same month
     * @param dutyCategories Set of dutyCategories (contain Points)
     * @return Points
     */
    public static Optional<Points> calcBalancePoints(
        List<DutyEntity> duties,
        Set<DutyCategoryEntity> dutyCategories
    ) {
        if (duties.isEmpty()) {
            LOG.debug("No duties delivered -> Points cannot be calculated");
            return Optional.of(new Points(0));
        }

        if (isGivenMonthCurrentMonth(duties.get(0).getStart())) {
            return calcBalancePointsForCurrentMonth(duties, dutyCategories);
        } else if (isGivenMonthBeforeCurrentMonth(duties.get(0).getStart())) {
            return Optional.of(new Points(0));
        } else if (isGivenMonthAfterCurrentMonth(duties.get(0).getStart())) {
            return calcBalancePointsForMonthAfterCurrent(duties, dutyCategories);
        }
        return Optional.of(new Points(0));
    }

    /**
     * Search for the correct ChangeLog (containing the changed number of Points) to a given duty.
     *
     * @param duty          Duty that delivers the startTime to get the right changelog
     * @param catChangeLogs List of DutyCategoryChangelogEntities
     * @return The number of relevant/correct points at the time of the duty
     */
    private static int giveChangeLogPointsOfDuty(
        DutyEntity duty,
        List<DutyCategoryChangelogEntity> catChangeLogs
    ) {
        int points = 0;
        DutyCategoryChangelogEntity temp = null;
        LocalDate dutyTime = duty.getStart().toLocalDate();
        for (DutyCategoryChangelogEntity catChangeLogEntity : catChangeLogs) {
            if (duty.getDutyCategory().getDutyCategoryId()
                .equals(catChangeLogEntity.getDutyCategory().getDutyCategoryId())) {
                if (temp == null
                    || (catChangeLogEntity.getStartDate().isAfter(temp.getStartDate()))
                    && dutyTime.isAfter(catChangeLogEntity.getStartDate())
                ) {
                    temp = catChangeLogEntity;
                    points = temp.getPoints();
                }
            }
        }
        return points;
    }

    /**
     * Returns 0 points for musicians.
     *
     * @return A Points Object with 0 Points (for External Musicians)
     */
    public static Optional<Points> getZeroPoints() {
        return Optional.of(new Points(0));
    }

    private static boolean isGivenMonthBeforeCurrentMonth(LocalDateTime month) {
        if (month.getYear() < LocalDate.now().getYear()) {
            return true;
        } else if (month.getYear() == LocalDate.now().getYear()) {
            return month.getMonthValue() < LocalDate.now().getMonthValue();
        }
        return false;
    }

    private static boolean isGivenMonthAfterCurrentMonth(LocalDateTime month) {
        if (month.getYear() > LocalDate.now().getYear()) {
            return true;
        } else if (month.getYear() == LocalDate.now().getYear()) {
            return month.getMonthValue() > LocalDate.now().getMonthValue();
        }
        return false;
    }

    private static boolean isGivenMonthCurrentMonth(LocalDateTime month) {
        return (month.getYear() == LocalDate.now().getYear()
            && (month.getMonthValue() == LocalDate.now().getMonthValue()));
    }

    /**
     * Calculate balance points ("Saldo") for the current month.
     *
     * @param duties         The duties to use for calculation
     * @param dutyCategories The duty categories to use for calculation
     * @return Calculated points
     */
    private static Optional<Points> calcBalancePointsForCurrentMonth(
        List<DutyEntity> duties,
        Set<DutyCategoryEntity> dutyCategories
    ) {
        int points = 0;
        // Iterate every duty from current month
        for (DutyEntity duty : duties) {
            if (duty.getStart().isAfter(LocalDateTime.now())) {
                // Iterate every dutyCategory and find match to duty -> count points
                // and break from iterator
                for (DutyCategoryEntity cat : dutyCategories) {
                    if (cat.getDutyCategoryId()
                        .equals(duty.getDutyCategory().getDutyCategoryId())
                    ) {
                        points = points + cat.getPoints();
                        break;
                    }
                }
            }
        }
        return Optional.of(new Points(points));
    }

    /**
     * Calculates balance points ("Saldo") for the month after {@link LocalDate#now()}.
     *
     * @param duties         The duties to use for calculation
     * @param dutyCategories The duty categories to use for calculation
     * @return Calculated points
     */
    private static Optional<Points> calcBalancePointsForMonthAfterCurrent(
        List<DutyEntity> duties,
        Set<DutyCategoryEntity> dutyCategories
    ) {
        int points = 0;
        for (DutyEntity duty : duties) {
            for (DutyCategoryEntity cat : dutyCategories) {
                if (duty.getDutyCategory().getDutyCategoryId().equals(cat.getDutyCategoryId())) {
                    points = points + cat.getPoints();
                    break;
                }
            }
        }
        return Optional.of(new Points(points));
    }
}
