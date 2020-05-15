package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.domain.Points;
import at.fhv.teamb.symphoniacus.persistence.dao.ContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyCategoryChangeLogDao;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyCategoryChangeLogDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyDao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryChangelogEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
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
    private final IContractualObligationDao conDao;
    private final IDutyCategoryChangeLogDao dutyCatChangeDao;
    private final IDutyDao dutyDao;
    private Set<IDutyEntity> allDuties;

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
    public void loadAllDutiesOfMusicians(List<IMusicianEntity> musicians, LocalDate month) {
        this.allDuties = this.dutyDao.getAllDutiesOfMusicians(musicians, month);
    }

    /**
     * Responsible for returning the debit points ("Soll-Punkte") from a given
     * {@link MusicianEntity} as a {@link Points} object.
     *
     * @param musician The musician to use
     * @return Points domain object containing the number of points
     * @see Points#getValue()
     */
    public Points getDebitPointsFromMusician(IMusicianEntity musician) {
        if (isMusicianExternal(musician)) {
            return Points.getZeroPoints();
        } else {
            // DutyDAO has been modified so that in this case, only one CO
            // should be loaded at this point. However, we need to double-check.
            if (musician.getContractualObligations().size() != 1) {
                LOG.error("More than one contractual obligation is not supported "
                    + "at this stage for musician {}", musician.getUser().getShortcut());
                return Points.getZeroPoints();
            }
            return Points.calcDebitPoints(musician.getContractualObligations().get(0));
        }
    }

    /**
     * Responsible for returning the gained points ("Ist-Punkte") from a given
     * {@link MusicianEntity} and month as a {@link Points} object.
     *
     * @param musician The musician to use
     * @param month    Any day of a month, representing the complete month
     * @return Points domain object containing the number of points
     * @see Points#getValue()
     */
    public Points getGainedPointsForMonthFromMusician(
        IMusicianEntity musician,
        LocalDate month
    ) {
        if (isMusicianExternal(musician)) {
            return Points.getZeroPoints();
        } else {
            // Beware! If allDuties isn't loaded this method will load
            // the musicians individually from the DAO
            List<IDutyEntity> listOfDutiesFromMusician = this.getDutiesFromMusician(
                musician,
                month
            );

            // Return 0 points if musician has no duties
            if (listOfDutiesFromMusician.isEmpty()) {
                return Points.getZeroPoints();
            }

            // Get all dutyCategoryChangelogs to all dutyCategories
            List<IDutyCategoryChangelogEntity> dutyCategoryChangelogs = new LinkedList<>();
            for (IDutyCategoryEntity dc : this.getAllDutyCategories(listOfDutiesFromMusician)) {
                List<IDutyCategoryChangelogEntity> changelogEntityList =
                    this.dutyCatChangeDao.getDutyCategoryChangelogs(dc);
                if (changelogEntityList != null) {
                    dutyCategoryChangelogs.addAll(changelogEntityList);
                }
            }

            return Points.calcGainedPoints(listOfDutiesFromMusician, dutyCategoryChangelogs);
        }
    }

    /**
     * Filters all duties to the given musician.
     * Utilizes local 'allDuties' attribute.
     *
     * @param musician given musician
     * @return List of DutyEntities from the given musician
     */
    private List<IDutyEntity> getAllDutiesFromMusician(IMusicianEntity musician) {
        List<IDutyEntity> duties = new LinkedList<>();
        for (IDutyEntity duty : this.allDuties) {
            for (IDutyPositionEntity dp : duty.getDutyPositions()) {
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
     * Responsible for returning the gained points ("Saldo-Punkte") from a given
     * {@link MusicianEntity} and month as a {@link Points} object.
     *
     * @param musician The musician to use
     * @param month    Any day of a month, representing the complete month
     * @return Points domain object containing the number of points
     * @see Points#getValue()
     */
    public Points getBalanceFromMusician(IMusicianEntity musician, LocalDate month) {
        if (isMusicianExternal(musician)) {
            return Points.getZeroPoints();
        } else {
            // Beware! If allDuties isn't loaded this method will load
            // the musicians individually from the DAO
            List<IDutyEntity> listOfDutiesFromMusician = this.getDutiesFromMusician(
                musician,
                month
            );

            // Return 0 points if musician has no duties
            if (listOfDutiesFromMusician.isEmpty()) {
                return Points.getZeroPoints();
            }

            return Points.calcBalancePoints(
                listOfDutiesFromMusician,
                this.getAllDutyCategories(listOfDutiesFromMusician)
            );
        }
    }

    private boolean isMusicianExternal(IMusicianEntity musician) {
        return musician.getUser().getFirstName().equals("Extern");
    }

    /**
     * Returns a List of {@link DutyEntity} objects that the musician is assigned to for
     * a given month.
     *
     * @param musician The musician to use
     * @param month    The month to use
     * @return A List of DutyEntity objects
     */
    private List<IDutyEntity> getDutiesFromMusician(IMusicianEntity musician, LocalDate month) {
        List<IDutyEntity> listOfDutiesFromMusician;
        if (this.allDuties == null) {
            listOfDutiesFromMusician = this.dutyDao.getAllDutiesInRangeFromMusician(
                musician,
                month
            );
        } else {
            listOfDutiesFromMusician = this.getAllDutiesFromMusician(musician);
        }
        return listOfDutiesFromMusician;
    }

    /**
     * Returns a List of {@link DutyCategoryEntity} objects for a given List of
     * {@link DutyEntity} objects.
     *
     * @param duties The duties to use
     * @return A List of DutyCategoryEntity objects
     */
    private Set<IDutyCategoryEntity> getAllDutyCategories(List<IDutyEntity> duties) {
        Set<IDutyCategoryEntity> dutyCategories = new LinkedHashSet<>();
        for (IDutyEntity d : duties) {
            dutyCategories.add(d.getDutyCategory());
        }
        return dutyCategories;
    }
}
