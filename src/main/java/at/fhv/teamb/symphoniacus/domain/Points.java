package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Points {
    private static final Logger LOG = LogManager.getLogger(Points.class);
    private int value;

    public int getValue() {
        return this.value;
    }

    public static Optional<Points> calcDebitPoints(ContractualObligationEntity obligationEntity) {
        return null;
    }

    public static Optional<Points> calcGainedPoints(List<DutyEntity> duties,
                                                    Set<DutyCategoryEntity> dutyCategories,
                                                    List<DutyCategoryChangelogEntity>
                                                        catChangeLogs) {
        return null;
    }

    public static Optional<Points> calcBalancePoints(List<DutyEntity> duties,
                                                     Set<DutyCategoryEntity> dutyCategories) {
        return null;
    }
}
