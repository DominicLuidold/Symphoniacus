package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.DutyEntityC;
import at.fhv.orchestraria.domain.model.DutySectionMonthlyScheduleEntityC;
import at.fhv.orchestraria.domain.model.SectionMonthlyScheduleEntityC;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class DutyDAO implements DaoBase<DutyEntityC> {



    private SessionManager _sessionManager;

    public DutyDAO(SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<DutyEntityC> get(int id) {
        Session s = _sessionManager.openConnection();
        Transaction tx =s.beginTransaction();
        Optional<DutyEntityC> duty = Optional.ofNullable(s.get(DutyEntityC.class, id));
        tx.commit();
        return duty;
    }

    @Override
    public synchronized List<DutyEntityC> getAll() {
        Session s = _sessionManager.openConnection();
        Transaction tx =s.beginTransaction();
        List<DutyEntityC> duties = s.createQuery("SELECT a FROM DutyEntity a", DutyEntityC.class).getResultList();
        List<DutyEntityC> wrappedDuties = new ArrayList<>(duties.size());
        for(DutyEntityC d :duties){
            wrappedDuties.add(d);
        }
        tx.commit();
        return wrappedDuties;
    }

    /**
     * Get all duties of a specified section
     * @param sectionID ID of the section
     * @return duties of the section
     */
    public synchronized List<DutyEntityC> getDutiesBySectionID(int sectionID){
        List<DutyEntityC> fittingDuties = new ArrayList<>();

        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        List<SectionMonthlyScheduleEntityC> sectionMonthlyScheduleEntities =  s.createQuery("SELECT a FROM SectionMonthlyScheduleEntity a WHERE a.section.sectionId ="+ sectionID, SectionMonthlyScheduleEntityC.class).getResultList();
        for(SectionMonthlyScheduleEntityC smse : sectionMonthlyScheduleEntities){
            for(DutySectionMonthlyScheduleEntityC dsmse : smse.getDutySectionMonthlySchedules()){
               fittingDuties.add(dsmse.getDuty());
            }
        }
        tx.commit();
        return fittingDuties;
    }

    //TODO: Funktionierend schreiben
//    public List<DutyEntity> getUpcomingDutiesForAssignmentBySectionId(int sectionID){
//        List<DutyEntity> fittingDuties = new ArrayList<>();
//
//        Session s = _sessionFactory.openSession();
//        Transaction tx = s.beginTransaction();
//        Query q = s.createQuery(" WHERE a.sectionBySectionId.sectionId =:sectionID  AND a.readyForDutyScheduler = true ", SectionMonthlyScheduleEntity.class);
//        q.setParameter("sectionID", sectionID);
//        List<SectionMonthlyScheduleEntity> sectionMonthlyScheduleEntities =  q.getResultList();
//        for(SectionMonthlyScheduleEntity smse : sectionMonthlyScheduleEntities){
//            for(DutySectionMonthlyScheduleEntity dsmse : smse.getDutySectionMonthlySchedulesBySectionMonthlyScheduleId()){
//                fittingDuties.add(dsmse.getDutyByDutyId());
//            }
//        }
//        tx.commit();
//        return fittingDuties;
//    }

    @Override
    public synchronized void save(DutyEntityC entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        s.saveOrUpdate(entity);
        tx.commit();
    }

    @Override
    public synchronized DutyEntityC update(DutyEntityC entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        DutyEntityC de =  (DutyEntityC) s.merge(entity);
        tx.commit();
        return de;
    }

    @Override
    public synchronized void delete(DutyEntityC entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        s.delete(entity);
        tx.commit();
    }
}