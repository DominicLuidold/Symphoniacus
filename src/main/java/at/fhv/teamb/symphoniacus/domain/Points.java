package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import java.util.Optional;
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
}
