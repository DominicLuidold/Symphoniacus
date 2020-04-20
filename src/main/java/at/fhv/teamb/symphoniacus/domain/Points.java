package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
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
    private int value;

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
        Optional<Points> debit = Optional.of(new Points(obligationEntity.getPointsPerMonth()));
        return debit;
    }

    public static Optional<Points> calcGainedPoints(List<DutyEntity> duties,
                                                    Set<DutyCategoryEntity> dutyCategories,
                                                    List<DutyCategoryChangelogEntity>
                                                        catChangeLogs) {
        return null;
    }

    /**
     * Calculate Balance Points (Saldo) observing the month of which the duties are in.
     *
     * @param duties List of duties
     * @param dutyCategories Set of dutyCategories (contain Points)
     * @return Points
     */
    public static Optional<Points> calcBalancePoints(List<DutyEntity> duties,
                                                     Set<DutyCategoryEntity> dutyCategories) {
        if (!duties.isEmpty()) {
            int points = 0;
            if (isGivenMonthCurrentMonth(duties.get(0).getStart())) {

                // Iterate every duty from current month
                for (DutyEntity duty : duties) {
                    if (duty.getStart().isAfter(LocalDateTime.now())) {

                        // Iterate every dutyCategory and find match to duty -> count points
                        // and break from iterator
                        for (Iterator<DutyCategoryEntity> it = dutyCategories.iterator();
                             it.hasNext(); ) {
                            DutyCategoryEntity cat = it.next();
                            if (cat.getDutyCategoryId().equals(duty.getDutyCategoryId())) {
                                points = points + cat.getPoints();
                                break;
                            }
                        }
                    }
                }
                return Optional.of(new Points(points));
            } else if (isGivenMonthBeforeCurrentMonth(duties.get(0).getStart())) {
                return Optional.of(new Points(points));
            } else if (isGivenMonthAfterCurrentMonth(duties.get(0).getStart())) {
                for (DutyEntity duty : duties) {
                    for (Iterator<DutyCategoryEntity> it = dutyCategories.iterator();
                         it.hasNext(); ) {
                        DutyCategoryEntity cat = it.next();
                        if (duty.getDutyCategoryId().equals(cat.getDutyCategoryId())) {
                            points = points + cat.getPoints();
                            break;
                        }
                    }
                }
            }
            return Optional.of(new Points(points));
        }
        LOG.debug("No duties delivered -> Points cannot be calculated");
        return Optional.empty();
    }

    private static boolean isGivenMonthBeforeCurrentMonth(LocalDateTime month) {
        if (month.getYear() < LocalDate.now().getYear()) {
            return true;
        } else if (month.getYear() == LocalDate.now().getYear()) {
            if (month.getMonthValue() < LocalDate.now().getMonthValue()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isGivenMonthAfterCurrentMonth(LocalDateTime month) {
        if (month.getYear() > LocalDate.now().getYear()) {
            return true;
        } else if (month.getYear() == LocalDate.now().getYear()) {
            if (month.getMonthValue() > LocalDate.now().getMonthValue()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isGivenMonthCurrentMonth(LocalDateTime month) {
        return (month.getYear() == LocalDate.now().getYear() &&
            (month.getMonthValue() == LocalDate.now().getMonthValue()));
    }
}
