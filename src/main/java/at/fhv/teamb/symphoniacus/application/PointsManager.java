package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.persistence.dao.ContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryChangeLogDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import java.awt.Point;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * UseCase Controller responsible for delivering all requested Points of a musician w/ timespan.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class PointsManager {
    private final ContractualObligationDao conDao;
    private final DutyCategoryChangeLogDao dutyCatChangeDao;
    private final DutyDao dutyDao;
    private Set<DutyEntity> allDuties;

    /**
     * Initialize PointsManager.
     */
    public PointsManager() {
        this.conDao = new ContractualObligationDao();
        this.dutyCatChangeDao = new DutyCategoryChangeLogDao();
        this.dutyDao = new DutyDao();
    }

    /**
     * Is important for preloading all Duties of given musicians within a month,
     * so that it isn't necessary to fetch all duties from every musician by itself.
     *
     * @param musicians List of MusicianEntity
     * @param month     LocalDate any day of a month represents the whole month
     */
    public void loadAllDutiesOfMusicians(List<MusicianEntity> musicians, LocalDate month) {
        this.allDuties = this.dutyDao.getAllDutiesOfMusicians(musicians, month);
    }

    /**
     * Responsible for giving the Debit (Soll-Punkte)
     * Points from a given Musician as a Points Object (implements getValue).
     *
     * @param musician point of interest
     * @return Points domain object containing the number of Points (getValue)
     */
    public Optional<Points> getDebitPointsFromMusician(MusicianEntity musician) {
        if (!isMusicianExternal(musician)) {
            return Points.calcDebitPoints(this.conDao.getContractualObligation(musician));
        } else {
            return Points.getZeroPoints();
        }

    }

    /**
     * Responsible for giving the Gained (Ist-Punkte)
     * Points from a given musician+month as a Points Object (implements getValue).
     *
     * @param musician point of interest
     * @param month    LocalDate any day of a month represents the whole month
     * @return Points domain object containing the number of Points (getValue)
     */
    public Optional<Points> getGainedPointsForMonthFromMusician(
        MusicianEntity musician,
        LocalDate month
    ) {

        if (!isMusicianExternal(musician)) {
            List<DutyEntity> listOfDutiesFromMusician;
            // Beware! if allDuties isn't loaded this method will load
            // the musicians individually from the DAO
            if (this.allDuties == null) {
                listOfDutiesFromMusician =
                    this.dutyDao.getAllDutiesInRangeFromMusician(musician, month);
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
                    this.dutyCatChangeDao.getDutyCategoryChangeLog(dc);
                if (changelogEntityList != null) {
                    dutyCategoryChangelogs.addAll(changelogEntityList);
                }
            }
            return (Points.calcGainedPoints(listOfDutiesFromMusician, dutyCategoryChangelogs));
        } else {
            return Points.getZeroPoints();
        }

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
            for (DutyPositionEntity dp : duty.getDutyPositions()) {
                if (dp.getMusician() != null
                    && dp.getMusician().getMusicianId().equals(musician.getMusicianId())
                ) {
                    duties.add(duty);
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
        if (!isMusicianExternal(musician)) {
            List<DutyEntity> listOfDutiesFromMusician;
            // Beware! if allDuties isn't loaded this method will load
            // the musicians individually from the DAO
            if (this.allDuties == null) {
                listOfDutiesFromMusician =
                    this.dutyDao.getAllDutiesInRangeFromMusician(musician, month);
            } else {
                listOfDutiesFromMusician = this.getAllDutiesFromMusician(musician);
            }
            Set<DutyCategoryEntity> dutyCategories = new LinkedHashSet<>();

            // get all dutyCategories to all duties
            for (DutyEntity d : listOfDutiesFromMusician) {
                dutyCategories.add(d.getDutyCategory());
            }
            return Points.calcBalancePoints(listOfDutiesFromMusician, dutyCategories);
        } else {
            return Points.getZeroPoints();
        }

    }

    private boolean isMusicianExternal(MusicianEntity musician) {
        return (musician.getUser().getFirstName().equals("Extern"));
    }
}
