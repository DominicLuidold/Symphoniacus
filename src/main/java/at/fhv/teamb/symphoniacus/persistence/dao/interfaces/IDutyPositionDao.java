package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.DutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.DutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionMonthlyScheduleEntity;
import java.util.List;

public interface IDutyPositionDao extends Dao<IDutyPositionEntity> {

    /**
     * Finds all {@link DutyPositionEntity} objects based on provided {@link DutyEntity} and
     * {@link SectionEntity}.
     *
     * @param duty    The duty to use
     * @param section The section to use
     * @return A List of corresponding DutyPosition entities
     */
    List<IDutyPositionEntity> findCorrespondingPositions(
        IDutyEntity duty,
        ISectionEntity section
    );

    /**
     * Finds all {@link DutyPositionEntity} objects based on provided
     * {@link SectionMonthlyScheduleEntity} that do not have any {@link MusicianEntity} set.
     *
     * @param sms The section monthly schedule to use
     * @return A List of corresponding DutyPosition entities
     */
    Long findCorrespondingPositionsWithoutMusician(
        ISectionMonthlyScheduleEntity sms
    );
}
