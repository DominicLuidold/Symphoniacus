package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import java.util.List;

public interface IMusicianDao extends Dao<IMusicianEntity> {

    /**
     * Finds all {@link MusicianEntity} objects with an active {@link ContractualObligationEntity}
     * based on provided {@link SectionEntity}.
     *
     * @param section The section to use
     * @return A List of active musicians belonging to the section
     */
    List<IMusicianEntity> findAllWithSectionAndActiveContract(ISectionEntity section);

    /**
     * Finds all {@link MusicianEntity} objects that represent an external musician placeholder
     * based on provided {@link SectionEntity}.
     *
     * @param section The section to use
     * @return A List of external musicians belonging to the section
     */
    List<IMusicianEntity> findExternalsWithSection(ISectionEntity section);
}
