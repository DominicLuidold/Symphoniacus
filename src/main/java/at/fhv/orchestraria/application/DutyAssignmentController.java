package at.fhv.orchestraria.application;

import at.fhv.orchestraria.domain.Imodel.*;
import at.fhv.orchestraria.persistence.dao.JPADatabaseFacade;
import at.fhv.orchestraria.persistence.dao.DutyDAO;
import at.fhv.orchestraria.persistence.dao.DutyPositionDAO;
import at.fhv.orchestraria.persistence.dao.MusicianDAO;
import at.fhv.orchestraria.domain.model.*;


import java.util.*;

/**
 * Controller responsible for the task of assigning the upcoming duties to musicians of the provided section.
 * The main functionalities are:
 * Loading of musicians and duties for the section.
 * Loading and saving of already assigned duty positions.
 * Loading of position assignments of duties in the same series of performance.
 */
public class DutyAssignmentController {

    private int _sectionID;
    private HashMap<ISeriesOfPerformances, IDuty> _previousMappings = new HashMap<>();

    /**
     *
     * Has to be a valid musician with authorization for assigning musicians
     * @param sectionID The section ID of the assigning musician.
     */
    public DutyAssignmentController(int sectionID) {
        _sectionID = sectionID;
    }


    /**
     *
     * @return All duties of the assigning musician's section.
     */
    public Collection<IDuty> getDuties() {
        DutyDAO dDAO = (DutyDAO) JPADatabaseFacade.getInstance().getDAO(DutyEntity.class);
        return Collections.unmodifiableCollection(dDAO.getDutiesBySectionID(_sectionID));
    }

    /**
     * Get all musicians for a non musical event
     * @param duty The event to check which musicians are available
     * @return All musicians available at that time of the assigning person's section
     */
    public Collection<IMusician> getSortedForDutyAvailableMusicians(IDuty duty) {
        MusicianDAO mDAO = (MusicianDAO) JPADatabaseFacade.getInstance().getDAO(MusicianEntity.class);
        Collection<MusicianEntity> availableMusicians = mDAO.getAllBySectionID(_sectionID);
        LinkedList<MusicianEntity> sortedMusicianEntities = new LinkedList<>();

        //Iterates all musicians of the section and filters the available musicians
        for(MusicianEntity musician : availableMusicians){
            if(musician.isAvailableAtDuty(duty) && musician.getMusicianId()!= MusicianEntity.EXTERNAL_MUSICIAN_ID){
                sortedMusicianEntities.add(musician);
            }
        }
        //Sort the musicians first by wish and second by points
        Collections.sort(sortedMusicianEntities, new MusicianComparator(duty));

        //Add the external musician
        sortedMusicianEntities.addLast(createExternal(sortedMusicianEntities));
        return Collections.unmodifiableCollection(sortedMusicianEntities);
    }

    /**
     * Get all musicians for a musical event
     * @param duty Event to check which musicians are available
     * @param instrumentAbbreviation Abbreviation of the Instrument subsections
     * @return All musicians available at that time of the assigning person's section playing the specified instrument
     */
    public Collection<IMusician> getSortedForDutyAvailableMusiciansByInstrument(IDuty duty, String instrumentAbbreviation){
        MusicianDAO mDAO = (MusicianDAO) JPADatabaseFacade.getInstance().getDAO(MusicianEntity.class);
        Collection<MusicianEntity> availableMusicians = mDAO.getAllBySectionID(_sectionID);
        LinkedList<MusicianEntity> sortedMusicianEntities = new LinkedList<>();

        //Iterates all musicians of the section and filters the available musicians
        for(MusicianEntity musician : availableMusicians){
            if(musician.canPlayInstrument(instrumentAbbreviation) && musician.isAvailableAtDuty(duty)
                    && musician.getMusicianId()!= MusicianEntity.EXTERNAL_MUSICIAN_ID){
                sortedMusicianEntities.add(musician);
            }
        }

        //Sort the musicians first by wish and second by points
        Collections.sort(sortedMusicianEntities, new MusicianComparator(duty));

        //Add external musician
        sortedMusicianEntities.addLast(createExternal(sortedMusicianEntities));
        return Collections.unmodifiableCollection(sortedMusicianEntities);
    }

    /**
     * Creates an external musician to enable the assigning person to assign external musicians
     * @param sortedMusicianEntities The already sorted entities
     * @return External musician
     */
    private MusicianEntity createExternal(LinkedList<MusicianEntity> sortedMusicianEntities){
        //This section adds the external musician
        Optional<MusicianEntity> external =  JPADatabaseFacade.getInstance().getDAO(MusicianEntity.class).get(MusicianEntity.EXTERNAL_MUSICIAN_ID);
        MusicianEntity ext = null;
        if(external.isPresent()) {
            ext = external.get();
                ext.setNegativeDateWishes(new LinkedList<NegativeDateWishEntity>());
                ext.setNegativeDutyWishes(new LinkedList<NegativeDutyWishEntity>());
                ext.setPositiveWishes(new LinkedList<PositiveWishEntity>());
                if (!sortedMusicianEntities.isEmpty())
                    ext.setSection(sortedMusicianEntities.getFirst().getSection());
                ext.setVacations(new LinkedList<VacationEntity>());
                ext.setDutyPositions(new LinkedList<DutyPositionEntity>());
                ext.setMusicianRoleMusicians(new LinkedList<MusicianRoleMusicianEntity>());
        }
        return ext;
    }

    /**
     * Assigns the duty-positions to the mapped musicians
     * @param duty duty is the corresponding duty of the dutypositions
     * @param mapping the mapping assigns to every dutyposition either one or no musician
     */
    public void assignDutyTo(IDuty duty, Map<IDutyPosition, IMusician> mapping){
        DutyPositionDAO dpmeDAO = (DutyPositionDAO) JPADatabaseFacade.getInstance().getDAO(DutyPositionEntity.class);
        MusicianDAO mDAO = (MusicianDAO) JPADatabaseFacade.getInstance().getDAO(MusicianEntity.class);
        List<DutyPositionEntity> dp =new LinkedList<>();
        LinkedList<MusicianEntity> musicianEntities = new LinkedList<>();

        for (Map.Entry<IDutyPosition, IMusician> positionWithMusician : mapping.entrySet()) {
            DutyPositionEntity realDutyPosition = (DutyPositionEntity) positionWithMusician.getKey();
            MusicianEntity realMusician = (MusicianEntity) positionWithMusician.getValue();


            if(realDutyPosition != null) {
                realDutyPosition.setMusician(realMusician);
                dp.add(realDutyPosition);

                if(realMusician != null) {
                    //Prevents musician from getting assigned twice
                    realMusician.getDutyPositions().remove(realDutyPosition);
                    realMusician.getDutyPositions().add(realDutyPosition);

                    if (realMusician.getMusicianId() != MusicianEntity.EXTERNAL_MUSICIAN_ID) {
                        musicianEntities.add(realMusician);
                    }
                }
            }
        }
        dpmeDAO.savelist(dp);
        mDAO.updatelist(musicianEntities);

        //Enable the possibility for reassigning the previous musicians
        if(duty.getSeriesOfPerformances()!= null) {
            _previousMappings.remove(duty.getSeriesOfPerformances());
            _previousMappings.put(duty.getSeriesOfPerformances(), duty);
        }
    }

    //Add functionality later
    /**
     * Provides the possibility to assign the current assignment to every event of the series of performances.
     * @param duty A duty of the series of performance.
     * @param mapping Mapping of the assigned musicians.
     */
    public void assignToEveryEventOfSeriesOfPerformances(IDuty duty, Map<IInstrumentationPosition, IMusician> mapping) throws IncorrectAssignmentException {
        if(duty.getSeriesOfPerformances() == null){
            throw new IncorrectAssignmentException("This event is not part of a series of performances");
        }
        for(IDuty similarDuties : duty.getSeriesOfPerformances().getIDutiesBySeriesOfPerformancesId()){
            HashMap<IDutyPosition, IMusician> musicianMapping = new HashMap<>(mapping.size());
            for(IDutyPosition dutyPos : similarDuties.getIDutyPositions()){
                musicianMapping.put(dutyPos, mapping.get(dutyPos.getInstrumentationPosition()));
            }
            assignDutyTo(similarDuties, musicianMapping);
        }
    }

    /**
     * This method returns either the last mapping of the series of performances that has been created in the session,
     * or if none has been created in this session, the mapping of an event on the same series of performances.
     * @param duty Duty that is currently being mapped and may have other duties of the same series of performances that are already mapped.
     * @return Returns a Hashmap that maps every instrumentation position to a musician.
     */
    public Optional<HashMap<IInstrumentationPosition, IMusician>> getAlreadyMappedSimilarDuty(IDuty duty){
        if(duty != null && duty.getSeriesOfPerformances() != null) {
            if(_previousMappings.containsKey(duty.getSeriesOfPerformances())){
                return copyDutyPositions( _previousMappings.get(duty.getSeriesOfPerformances()));
            }else{
                return sameEventAssignment(duty);
            }
        }
        return Optional.empty();
    }


    /**
     * Looks for duties of the same series of performance as the provided one and returns, if a COMPLETELY assigned duty in the same
     * series of performances exists, the assignment of the instrumentation positions that already exists.
     * @param duty The duty the mapping should be applied to.
     * @return Returns a HashMap that maps a Musician to every IInstrumentationPosition that can be assigned via a Duty Position.
     */
    public Optional<HashMap<IInstrumentationPosition, IMusician>> sameEventAssignment(IDuty duty){
        if(duty.getSeriesOfPerformances() != null) {
            for (IDuty similarDuty : duty.getSeriesOfPerformances().getIDutiesBySeriesOfPerformancesId()) {
                if (similarDuty.isSectionCompletelyAssigned(_sectionID)) {
                    return copyDutyPositions(similarDuty);
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Copies the already assigned duty positions of the provided duty.
     * @param duty Duty to copy the assignment of.
     * @return Returns a HashMap that maps a Musician to every IInstrumentationPosition that can be assigned via a Duty Position.
     */
    private Optional<HashMap<IInstrumentationPosition, IMusician>> copyDutyPositions(IDuty duty){
        HashMap<IInstrumentationPosition, IMusician> mapping = new HashMap<>();
        for(IDutyPosition dutyPos : duty.getIDutyPositions()){
                mapping.put(dutyPos.getInstrumentationPosition(), dutyPos.getMusician());
        }
        if(mapping.size() == 0){
            return Optional.empty();
        }
        return Optional.of(mapping);
    }

    /**
     * Checks if enough section principals exist for the section type in combination of the duty type.
     * @param musicians The currently assigned musicians.
     * @param sectionEntity The section the musicians are part of.
     * @param dutyDescription The description of the duty category.
     * @return True if at least the minimum required section principals are assigned.
     */
    public boolean areEnoughSectionPrincipalsAssigned(Collection<IMusician> musicians, ISection sectionEntity, String dutyDescription) {
        return getCountOfRequiredSectionPrincipals(sectionEntity, dutyDescription) <= getCountOfAssignedSectionPrincipals(musicians);
    }

    /**
     * Calculates the count of section principals that are required by the combination of duty category and section type.
     * @param sectionEntity The section where the count is to look up.
     * @param dutyDescription The description of the duty category.
     * @return The required count.
     */
    public int getCountOfRequiredSectionPrincipals(ISection sectionEntity, String dutyDescription) {
        return sectionEntity.getCountOfRequiredSectionPrincipals(dutyDescription);
    }

    /**
     * Calculates the count of assigned section principals.
     * @param musicians The currently assigned musicians of which the count of section principals needs to be extracted.
     * @return The count of section principals in the collection of musicians.
     */
    public int getCountOfAssignedSectionPrincipals(Collection<IMusician> musicians) {
        int countOfLeaders = 0;
        for (IMusician musician : musicians) {
            if(musician.isSectionPrincipal()){
                countOfLeaders++;
            }
        }
        return countOfLeaders;
    }


    /**
     *
     * @return Returns the section ID of the musician that is currently assigning musicians.
     */
    public int getSectionID() {
        return _sectionID;
    }
}
