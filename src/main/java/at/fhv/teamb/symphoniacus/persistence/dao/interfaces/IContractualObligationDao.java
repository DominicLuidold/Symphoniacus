package at.fhv.teamb.symphoniacus.persistence.dao.interfaces;

import at.fhv.teamb.symphoniacus.persistence.Dao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;

public interface IContractualObligationDao extends Dao<IContractualObligationEntity> {

    /**
     * Finds matching ContractualObligation to a Musician.
     *
     * @param musician given musician contains musician.Id
     * @return ContractualObligationEntity
     */
    IContractualObligationEntity getContractualObligation(IMusicianEntity musician);
}
