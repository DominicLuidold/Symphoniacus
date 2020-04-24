package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.persistence.dao.ContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryChangeLogDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * UseCase Controller responsible for delivering all requested Points of a musician w/ timespan.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class PointsManager {
    private static final Logger LOG = LogManager.getLogger(PointsManager.class);
    private Set<DutyEntity> allDuties;

    /**
     * Is important for preloading all Duties of given musicians within a month,
     * so that it isn't necessary to fetch all duties from every musician by itself.
     *
     * @param musicians List of MusicianEntity
     * @param month LocalDate any day of a month represents the whole month
     */
    public void loadAllDutiesOfMusicians(List<MusicianEntity> musicians, LocalDate month) {
        DutyDao dutyDao = new DutyDao();
        allDuties = dutyDao.getAllDutiesOfMusicians(musicians,month);
    }

    /**
     * Responsible for giving the Debit (Soll-Punkte)
     * Points from a given Musician as a Points Object (implements getValue).
     *
     * @param musician point of interest
     * @return Points domain object containing the number of Points (getValue)
     */
    public Optional<Points> getDebitPointsFromMusician(MusicianEntity musician) {
        ContractualObligationDao conDao = new ContractualObligationDao();
        Optional<ContractualObligationEntity> conEntity =
            conDao.getContractualObligation(musician);
        if (conEntity.isPresent()) {
            return (Points.calcDebitPoints(conEntity.get()));
        }
        return Optional.empty();
    }

    /**
     * Responsible for giving the Gained (Ist-Punkte)
     * Points from a given musician+month as a Points Object (implements getValue).
     *
     * @param musician point of interest
     * @param month    LocalDate any day of a month represents the whole monthcccccc
     * @return Points domain object containing the number of Points (getValue)
     */
    public Optional<Points> getGainedPointsForMonthFromMusician(
        MusicianEntity musician,
        LocalDate month
    ) {
        DutyDao dutyDao = new DutyDao();
        DutyCategoryChangeLogDao dutyCatChangeDao = new DutyCategoryChangeLogDao();

        List<DutyEntity> listOfDutiesFromMusician;
        // Beware! if allDuties isn't loaded this method will load
        // the musicians individually from the DAO
        if (this.allDuties == null) {
            listOfDutiesFromMusician = dutyDao.getAllDutiesInRangeFromMusician(musician,month);
        } else {
           listOfDutiesFromMusician = this.getAllDutiesFromMusician(musician);
        }
        Set<DutyCategoryEntity> dutyCategories = new LinkedHashSet<>();

        // get all dutyCategories to all duties
        for (DutyEntity d : listOfDutiesFromMusician) {
            dutyCategories.add(d.getDutyCategory());
        }

        List<DutyCategoryChangelogEntity> dutyCategoryChangelogs = new LinkedList<>();

        // get all dutyCategoryChangelogs to all dutyCategories
        for (DutyCategoryEntity dc : dutyCategories) {
            List<DutyCategoryChangelogEntity> changelogEntityList =
                dutyCatChangeDao.getDutyCategoryChangeLog(dc);
            if (changelogEntityList != null) {
                dutyCategoryChangelogs.addAll(changelogEntityList);
            }
        }
        return (Points.calcGainedPoints(listOfDutiesFromMusician, dutyCategoryChangelogs));
    }

    /**
     * Filters all duties to the given musician.
     * Utilizes local 'allDuties' attribute.
     *
     * @param musician given musician
     * @return List of DutyEntities from the given musician
     */
    private List<DutyEntity> getAllDutiesFromMusician(MusicianEntity musician) {
        List<DutyEntity> duties = new LinkedList<>();
        for (DutyEntity duty : this.allDuties) {
            for (DutyPositionEntity dpM : musician.getDutyPositions()) {
                for (DutyPositionEntity dp : duty.getDutyPositions()) {
                    if (dpM.getDutyPositionId().equals(dp.getDutyPositionId())) {
                        duties.add(duty);
                    }
                }
            }
        }
        return duties;
    }

    /**
     * Responsible for giving the Gained (Saldo-Punkte)
     * Points from a given musician+month as a Points Object (implements getValue).
     *
     * @param musician point of interest
     * @param month    LocalDate any day of a month represents the whole month
     * @return Points domain object containing the number of Points (getValue)
     */
    public Optional<Points> getBalanceFromMusician(MusicianEntity musician, LocalDate month) {
        DutyDao dutyDao = new DutyDao();

        List<DutyEntity> listOfDutiesFromMusician;
        // Beware! if allDuties isn't loaded this method will load
        // the musicians individually from the DAO
        if (this.allDuties == null) {
            listOfDutiesFromMusician = dutyDao.getAllDutiesInRangeFromMusician(musician,month);
        } else {
            listOfDutiesFromMusician = this.getAllDutiesFromMusician(musician);
        }
        Set<DutyCategoryEntity> dutyCategories = new LinkedHashSet<>();

        // get all dutyCategories to all duties
        for (DutyEntity d : listOfDutiesFromMusician) {
            dutyCategories.add(d.getDutyCategory());
        }
        return Points.calcBalancePoints(listOfDutiesFromMusician, dutyCategories);
    }

}
