package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import java.util.List;
import java.util.Optional;

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

    /**
     * Returns Musician matching given userId.
     * @param id given User Id
     * @return Musician
     */
    Optional<IMusicianEntity> findMusicianByUserId(int id);

    /**
     * Returns Musician matching given userSHortcut.
     * @param userShortcut Shortcut of Musician
     * @return Musician if found, else Optional empty
     */
    Optional<IMusicianEntity> findMusicianByShortcut(String userShortcut);
}
