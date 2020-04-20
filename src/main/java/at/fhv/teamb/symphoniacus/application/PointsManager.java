package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.persistence.dao.ContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.time.LocalDate;
import java.util.Optional;
import javassist.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO - Nino: irgendwo muss überprüft werden, ob der musician valide ist, also zb.
//  auch eine ID gesetzt ist. Sonst kann es zu NoResultException im Dao führen.
public class PointsManager {
    private static final Logger LOG = LogManager.getLogger(PointsManager.class);

    public Optional<Points> getDebitPointsFromMusician(MusicianEntity musician) throws NotFoundException {
        if (isMusicianValid(musician)) {
            ContractualObligationDao conDao = new ContractualObligationDao();
            Optional<ContractualObligationEntity> conEntity = conDao.getContractualObligation(musician);
            if (conEntity.isPresent()) {
                return (Points.calcDebitPoints(conEntity.get()));
            }
        } else {
            LOG.debug("Given musician for getDebitPointsFromMusician is not Valid");
            throw new NotFoundException("Given musician for getDebitPointsFromMusician is not Valid");
        }
        return Optional.empty();
    }

    public Points getGainedPointsForMonthFromMusician(MusicianEntity musician, LocalDate month) {
        return null;
    }

    public Points getBalanceFromMusician(MusicianEntity musician, LocalDate month) {
        return null;
    }

    private boolean isMusicianValid(MusicianEntity musician) {
        if(musician != null && musician.getMusicianId() != null) {
            return true;
        }
        return false;
    }

}
