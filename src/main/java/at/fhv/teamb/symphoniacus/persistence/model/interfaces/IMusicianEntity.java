package at.fhv.teamb.symphoniacus.persistence.model.interfaces;

import java.util.List;

public interface IMusicianEntity {
    void addPositiveWish(IPositiveWishEntity positiveWish);

    void removePositiveWish(IPositiveWishEntity positiveWish);

    void addNegativeDutyWish(INegativeDutyWishEntity negativeWish);

    void removeNegativeDutyWish(INegativeDutyWishEntity negativeWish);

    void addNegativeDateWish(INegativeDateWishEntity negativeWish);

    void removeNegativeDateWish(INegativeDateWishEntity negativeWish);

    void setContractualObligations(List<IContractualObligationEntity> contractualObligations);

    List<INegativeDutyWishEntity> getNegativeDutyWishes();

    void setNegativeDutyWishes(List<INegativeDutyWishEntity> negativeDutyWishes);

    void setMusicianRoles(List<IMusicianRoleEntity> musicianRoles);

    void setDutyPositions(List<IDutyPositionEntity> dutyPositions);

    List<IPositiveWishEntity> getPositiveWishes();

    void setPositiveWishes(List<IPositiveWishEntity> positiveWishes);

    void setVacations(List<IVacationEntity> vacations);

    Integer getMusicianId();

    void setMusicianId(Integer musicianId);

    IUserEntity getUser();

    void setUser(IUserEntity user);

    ISectionEntity getSection();

    void setSection(ISectionEntity section);

    List<IContractualObligationEntity> getContractualObligations();

    void addContractualObligation(IContractualObligationEntity contractualObligation);

    void removeContractualObligation(IContractualObligationEntity contractualObligation);

    List<IMusicianRoleEntity> getMusicianRoles();

    void addMusicianRole(IMusicianRoleEntity role);

    void removeMusicianRole(IMusicianRoleEntity role);

    List<IDutyPositionEntity> getDutyPositions();

    void addDutyPosition(IDutyPositionEntity position);

    void removeDutyPosition(IDutyPositionEntity position);

    List<IVacationEntity> getVacations();

    void addVacation(IVacationEntity vacation);

    void removeVacation(IVacationEntity vacation);

    void addInstrumentCategory(IInstrumentCategoryEntity inst);

    void removeInstrumentCategory(IInstrumentCategoryEntity inst);

    List<IInstrumentCategoryEntity> getInstrumentCategories();

    void setInstrumentCategories(List<IInstrumentCategoryEntity> instrumentCategories);

    void setNegativeDateWishes(List<INegativeDateWishEntity> negativeDateWishes);

    List<INegativeDateWishEntity> getNegativeDateWishes();

    List<ISubstitute> getSubstitutes();

    void setSubstitutes(List<ISubstitute> substitutes);

    void removeAllMusicianRoles();

    void removeAllInstrumentCategories();

    void removeAllContractualObligations();
}
