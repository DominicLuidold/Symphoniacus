package at.fhv.orchestraria.persistence.dao;

import at.fhv.orchestraria.domain.model.DutyEntity;
import at.fhv.orchestraria.domain.model.DutySectionMonthlyScheduleEntity;
import at.fhv.orchestraria.domain.model.SectionMonthlyScheduleEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Team C
 */

public class DutyDAO implements DaoBase<DutyEntity> {



    private SessionManager _sessionManager;

    public DutyDAO(SessionManager sessionManager) {
        _sessionManager = sessionManager;
    }

    @Override
    public synchronized Optional<DutyEntity> get(int id) {
        Session s = _sessionManager.openConnection();
        Transaction tx =s.beginTransaction();
        Optional<DutyEntity> duty = Optional.ofNullable(s.get(DutyEntity.class, id));
        tx.commit();
        return duty;
    }

    @Override
    public synchronized List<DutyEntity> getAll() {
        Session s = _sessionManager.openConnection();
        Transaction tx =s.beginTransaction();
        List<DutyEntity> duties = s.createQuery("SELECT a FROM DutyEntity a", DutyEntity.class).getResultList();
        List<DutyEntity> wrappedDuties = new ArrayList<>(duties.size());
        for(DutyEntity d :duties){
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
    public synchronized List<DutyEntity> getDutiesBySectionID(int sectionID){
        List<DutyEntity> fittingDuties = new ArrayList<>();

        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        List<SectionMonthlyScheduleEntity> sectionMonthlyScheduleEntities =  s.createQuery("SELECT a FROM SectionMonthlyScheduleEntity a WHERE a.section.sectionId ="+ sectionID, SectionMonthlyScheduleEntity.class).getResultList();
        for(SectionMonthlyScheduleEntity smse : sectionMonthlyScheduleEntities){
            for(DutySectionMonthlyScheduleEntity dsmse : smse.getDutySectionMonthlySchedules()){
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
    public synchronized void save(DutyEntity entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        s.saveOrUpdate(entity);
        tx.commit();
    }

    @Override
    public synchronized DutyEntity update(DutyEntity entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        DutyEntity de =  (DutyEntity) s.merge(entity);
        tx.commit();
        return de;
    }

    @Override
    public synchronized void delete(DutyEntity entity) {
        Session s = _sessionManager.openConnection();
        Transaction tx = s.beginTransaction();
        s.delete(entity);
        tx.commit();
    }
}