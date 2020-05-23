package at.fhv.orchestraria.domain.Imodel;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/**
 * only getters for read only access declared
 * @author Team C
 */

public interface IMusician {
      int getMusicianId();
      IUser getUser();
      ISection getSection();

      /**
       * Checks if the musician is a Duty Scheduler.
       * @return True if the person is a Duty Scheduler.
       */
      boolean isDutyScheduler();

      /**
       * Checks whether the maximum amount of evening duties on month is reached
       * @param date The date the evening duties on month would refer to
       * @return Returns whether the maximum amount of evening duties has been reached
       */
      boolean hasReachedMaxAmountOfEveningDutiesOnMonth(LocalDate date);

      /**
       * Iterates all musician roles of the musicians and checks whether a musician is section principal (max. 15 evening duties) or not (max. 17 evening duties)
       * @return Returns the maximum amount of evening duties
       */
      int getMaxAmountOfEveningDuties();

      /**
       * @param date The date the duties would refer to
       * @return Returns whether a musician has been assigned to the maximum amount of duties on that day (e.g. brass section is nly allowed to play twice a day)
       */
      boolean hasBeenAssignedToMaxAmountOfDutiesOnDate(LocalDate date);

      /**
       * Iterates all positive wishes of the musician and checks if there is one corresponding to the provided duty.
       * @param duty The duty the positive wish would refer to.
       * @return Returns an optional that contains either the wish or is empty in case there is no wish.
       */
      Optional<IPositiveWish> getPositiveWish(IDuty duty);

      /**
       * Iterates all negative duty wishes of the musician and checks if there is one corresponding to the provided duty.
       * @param duty The duty the negative duty wish would refer to.
       * @return Returns an optional that contains either the wish or is empty in case there is no wish.
       */
      Optional<INegativeDutyWish> getNegativeDutyWish(IDuty duty);

      /**
       * Iterates all negative date wishes of the musician and checks if there is one corresponding to the provided duty.
       * @param duty The duty in which timespan the negative date wish may be in.
       * @return Returns an optional that contains either the wish or is empty in case there is no wish.
       */
      Optional<INegativeDateWish> getNegativeDateWish(IDuty duty);

      /**
       * Calculates the amount of evening duties the musician is already assigned to in the month of the date.
       * @param date A date in the month the request is referring to.
       * @return Amount of evening duties the musician is assigned for in the month of the date.
       */
      int getCountOfEveningDutiesOfMonth(LocalDate date);

      /**
       * Calculates the amount of points the musician is assigned for in the month of the date.
       * @param date A date in the month the request is referring to.
       * @return Amount of points the musician is assigned for in the month of the date.
       */
      int getRequiredPointsOfMonth(LocalDate date);

      /**
       * Calculates the amount of points the musician is required to have in the month of the date.
       * @param date A date in the month the request is referring to.
       * @return Amount of points the musician is required to have in the month of the date
       */
      int getPointsOfMonth(LocalDate date);

      /**
       * Checks if the musician is on tour, on vacation or on an other event on the delivered duty.
       * @param duty: Specified duty
       * @return True if musician is available on that day
       */
      boolean isAvailableAtDuty(IDuty duty);

      /**
       * Iterates all the events the musician is assigned to and looks if any of those are in the same timespan as the provided timestamp.
       * @param providedDuty The duty where the musician may be assigned for an event.
       * @return Returns true if musician has other event at that point in time.
       */
      boolean isOnEventAtDate(IDuty providedDuty) ;

      /**
       * Iterates all the duties the musician is assigned to and looks any tour is in the same timespan as the provided timestamp.
       * @param duty The duty at which date the musician may be on tour.
       * @return Returns true if a musician is assigned for a tour at that point in time.
       */
      boolean isOnTourAtDate(IDuty duty);

      /**
       * Iterates all the vacations the musician has registered  and looks if any of those are in the same timespan as the provided timestamp.
       * @param date The timestamp where musician may be on vacation.
       * @return Returns true if a musician registered a vacation at that point in time.
       */
      boolean isOnVacationAtDate(LocalDate date);

      /**
       * Checks if the musician can play the provided instrument.
       * @param instrumentName The abbreviation of the instrument category as used in the section definitions.
       * @return Returns true if the musician can play the instrument.
       */
      boolean canPlayInstrument(String instrumentName);

      /**
       * Checks if the musician is a section principal.
       * @return Returns true if the musician is a section principal.
       */
      boolean isSectionPrincipal();

      /**
       * @return Returns unmodifiable collection of contractual obligations by musicianID.
       */
      Collection<IContractualObligation> getIContractualObligations();

      /**
       * @return Returns unmodifiable collection of duty positions by musicianID.
       */
      Collection<IDutyPosition> getIDutyPositions();

      /**
       * @return Returns unmodifiable collection of instrument categories of musicians by musicianID.
       */
      Collection<IInstrumentCategoryMusician> getIInstrumentCategoryMusicians();

      /**
       * @return Returns unmodifiable collection of the musician role of musician by musicianID.
       */
      Collection<IMusicianRoleMusician> getIMusicianRoleMusicians();

      /**
       * @return Returns unmodifiable collection of negative date wishes by musicianID.
       */
      Collection<INegativeDateWish> getINegativeDateWishes();

      /**
       * @return Returns unmodifiable collection of negative duty wishes by musicianID.
       */
      Collection<INegativeDutyWish> getINegativeDutyWishes();

      /**
       * @return Returns unmodifiable collection of positive wishes by musicianID.
       */
      Collection<IPositiveWish> getIPositiveWishes();

      /**
       * @return Returns unmodifiable collection of substitutes by musicianID.
       */
      Collection<ISubstitute> getISubstitutes();

      /**
       * @return Returns unmodifiable collection of vacations by musicianID.
       */
      Collection<IVacation> getIVacations();
}
