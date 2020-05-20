package at.fhv.orchestraria.application;

import at.fhv.orchestraria.UserInterface.Exceptions.NoValidShortcutException;
import at.fhv.orchestraria.UserInterface.Usermanagement.UserDTO;
import at.fhv.orchestraria.domain.Imodel.IAdministrativeAssistant;
import at.fhv.orchestraria.domain.Imodel.IInstrumentCategory;
import at.fhv.orchestraria.domain.Imodel.IMusicianRole;
import at.fhv.orchestraria.domain.Imodel.IMusicianRoleMusician;
import at.fhv.orchestraria.domain.Imodel.ISection;
import at.fhv.orchestraria.domain.model.AdministrativeAssistantEntity;
import at.fhv.orchestraria.domain.model.ContractualObligationEntity;
import at.fhv.orchestraria.domain.model.InstrumentCategoryEntity;
import at.fhv.orchestraria.domain.model.InstrumentCategoryMusicianEntity;
import at.fhv.orchestraria.domain.model.MusicianEntity;
import at.fhv.orchestraria.domain.model.MusicianRoleEntity;
import at.fhv.orchestraria.domain.model.MusicianRoleMusicianEntity;
import at.fhv.orchestraria.domain.model.SectionEntity;
import at.fhv.orchestraria.domain.model.UserEntity;
import at.fhv.orchestraria.persistence.dao.DBFacade;
import at.fhv.orchestraria.persistence.dao.UserDAO;
import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IUserDao;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Transient;

public class UserManagementController {

    private final static Logger LOGGER = Logger.getLogger(UserManagementController.class.getName());
    private DBFacade _facade;
    private IUserDao userDao;

    public UserManagementController() {
        //_facade = DBFacade.getInstance();
        this.userDao = new UserDao();
    }

    public Collection<IInstrumentCategory> getIInstrumentCategory() {
        return Collections
            .unmodifiableCollection(_facade.getDAO(InstrumentCategoryEntity.class).getAll());
    }

    public Collection<IMusicianRole> getIMusicianRole() {
        return Collections
            .unmodifiableCollection(_facade.getDAO(MusicianRoleEntity.class).getAll());
    }

    public Collection<ISection> getISections() {
        return Collections.unmodifiableCollection(_facade.getDAO(SectionEntity.class).getAll());
    }

    public Collection<IUserEntity> getUsers() {
        return this.userDao.getAll();
    }

    public UserEntity updateUser(UserEntity ue) {
        return _facade.getDAO(UserEntity.class).update(ue);
    }

    public MusicianEntity updateMusician(MusicianEntity me) {
        return _facade.getDAO(MusicianEntity.class).update(me);
    }

    public void saveUser(UserEntity ue) {
        _facade.getDAO(UserEntity.class).save(ue);
    }

    public void saveMusician(MusicianEntity me) {
        _facade.getDAO(MusicianEntity.class).save(me);
    }

    public IMusicianRoleMusician updateMusicianRoleMusician(MusicianRoleMusicianEntity mrme) {
        return _facade.getDAO(MusicianRoleMusicianEntity.class).update(mrme);
    }

    public IMusicianRole updateMusicianRole(MusicianRoleEntity mre) {
        return _facade.getDAO(MusicianRoleEntity.class).update(mre);
    }

    public void saveMusicianRoleMusician(MusicianRoleMusicianEntity mrme) {
        _facade.getDAO(MusicianRoleMusicianEntity.class).save(mrme);
    }

    public void saveMusicianRole(MusicianRoleEntity mre) {
        _facade.getDAO(MusicianRoleEntity.class).save(mre);
    }

    public IAdministrativeAssistant updateAdministrativeAssistant(
        AdministrativeAssistantEntity aae) {
        return _facade.getDAO(AdministrativeAssistantEntity.class).update(aae);
    }

    public void saveAdministrativeAssistant(AdministrativeAssistantEntity aae) {
        _facade.getDAO(AdministrativeAssistantEntity.class).save(aae);
    }

    public InstrumentCategoryMusicianEntity updateInstrumentCategoryMusician(
        InstrumentCategoryMusicianEntity icme) {
        return _facade.getDAO(InstrumentCategoryMusicianEntity.class).update(icme);
    }

    public void saveInstrumentCategoryMusician(InstrumentCategoryMusicianEntity icme) {
        _facade.getDAO(InstrumentCategoryMusicianEntity.class).save(icme);
    }

    public ContractualObligationEntity updateContractualObligation(
        ContractualObligationEntity coe) {
        return _facade.getDAO(ContractualObligationEntity.class).update(coe);
    }

    public void saveContractualObligation(ContractualObligationEntity coe) {
        _facade.getDAO(ContractualObligationEntity.class).save(coe);
    }


    public UserEntity saveGeneral(IUserEntity user, UserDTO userDTO) {

        UserEntity userToEdit = (UserEntity) user;

        if (userToEdit == null) {
            userToEdit = new UserEntity();
            try {
                PasswordManager.setNewPassword(userToEdit, "PW_" + userToEdit.getShortcut());
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "Exception ", e);
            }
        }

        userToEdit.setFirstName(userDTO.getFirstName());
        userToEdit.setLastName(userDTO.getLastName());
        // changes shortcut if it already exists
        if (checkExistingUserShortcut(
            userDTO.getFirstName().substring(0, 3) + userDTO.getLastName().substring(0, 3))) {
            try {
                userToEdit.setShortcut(changeShortcut(userDTO.getFirstName().substring(0, 3) +
                    userDTO.getLastName().substring(0, 3)));
            } catch (NoValidShortcutException e) {
                LOGGER.log(Level.INFO, "NoValidShortcut ", e);
                return null;
            }
        } else {
            userToEdit.setShortcut(
                userDTO.getFirstName().substring(0, 3) + userDTO.getLastName().substring(0, 3));
        }


        userToEdit.setEmail(userDTO.getEmail());
        userToEdit.setPhone(userDTO.getPhone());
        userToEdit.setCountry(userDTO.getCountry());
        userToEdit.setCity(userDTO.getCity());
        userToEdit.setZipCode(userDTO.getZipCode());
        userToEdit.setStreet(userDTO.getStreet());
        userToEdit.setStreetNumber(userDTO.getStreetNumber());

        if (!userDTO.isMusician()) {
            //toggle is on Admin
            AdministrativeAssistantEntity aae = null;
            if (userToEdit.getAdministrativeAssistant() != null) {
                userToEdit.getAdministrativeAssistant().setDescription(userDTO.getAdminRole());
                aae = userToEdit.getAdministrativeAssistant();
            } else {
                aae = new AdministrativeAssistantEntity();
                aae.setDescription(userDTO.getAdminRole());
                aae.setUser(userToEdit);
            }

            if (!userDTO.isNewUser()) {
                updateUser(userToEdit);
                userToEdit.setAdministrativeAssistant(aae);
                updateAdministrativeAssistant(userToEdit.getAdministrativeAssistant());
            } else {
                userToEdit = updateUser(userToEdit);
                userToEdit.setAdministrativeAssistant(aae);
                aae.setUser(userToEdit);
                aae.setUserId((userToEdit).getUserId());
                saveAdministrativeAssistant(userToEdit.getAdministrativeAssistant());
            }

        } else {
            //toggle is on Musician
            MusicianEntity me;
            if (userToEdit.getMusician() != null) {
                me = userToEdit.getMusician();
            } else {
                me = new MusicianEntity();
                me.setDutyPositions(new LinkedList<>());
                me.setVacations(new LinkedList<>());
                me.setPositiveWishes(new LinkedList<>());
                me.setNegativeDutyWishes(new LinkedList<>());
                me.setNegativeDateWishes(new LinkedList<>());
                me.setSubstitutes(new LinkedList<>());
                me.setContractualObligations(new LinkedList<>());
                me.setUser((UserEntity) userToEdit);
                me.setMusicianRoleMusicians(new LinkedList<>());
                me.setInstrumentCategoryMusicians(new LinkedList<>());
            }


            me.setSection(findSectionByString(userDTO.getSection()));


            if (!userDTO.isNewUser()) {
                updateUser(userToEdit);
                updateMusician(me);

            } else {
                saveUser(userToEdit);
                saveMusician(me);
            }

            userToEdit.setMusician(me);

            for (MusicianRoleMusicianEntity mrme : me.getMusicianRoleMusicians()) {
                deleteMusicianRoleMusician(mrme);
            }
            me.getMusicianRoleMusicians().clear();

            for (String role : userDTO.getSelectedRoles()) {
                MusicianRoleMusicianEntity mrme = new MusicianRoleMusicianEntity();
                mrme.setMusician(me);
                mrme.setMusicianRole(findMusicianRoleByRoleString(role));
                mrme.getMusicianRole().getMusicianRoleMusicians().add(mrme);
                me.getMusicianRoleMusicians().add(mrme);
                if (!userDTO.isNewUser()) {
                    updateMusicianRoleMusician(mrme);
                    updateMusicianRole(mrme.getMusicianRole());
                } else {
                    saveMusicianRoleMusician(mrme);
                    saveMusicianRole(mrme.getMusicianRole());
                }
            }


            for (InstrumentCategoryMusicianEntity icme : me.getInstrumentCategoryMusicians()) {
                deleteInstrumentCategoryMusician(icme);
            }
            me.getInstrumentCategoryMusicians().clear();


            for (ContractualObligationEntity obligation : me.getContractualObligations()) {
                deleteContractualObligation(obligation);
            }
            me.getInstrumentCategoryMusicians().clear();


            for (String instrumentCat : userDTO.getSelectedInstrumentCats()) {
                InstrumentCategoryMusicianEntity icme = new InstrumentCategoryMusicianEntity();
                InstrumentCategoryEntity ice =
                    findInstrumentCategoryByInstrumentString(instrumentCat);
                icme.setMusician(me);
                icme.setInstrumentCategory(ice);
                ice.getInstrumentCategoryMusicians().add(icme);

                if (!userDTO.isNewUser()) {
                    me.getInstrumentCategoryMusicians().add(updateInstrumentCategoryMusician(icme));
                } else {
                    saveInstrumentCategoryMusician(icme);
                }


                ContractualObligationEntity contract = new ContractualObligationEntity();
                contract.setMusician(me);
                contract.setPointsPerMonth(Integer.parseInt(userDTO.getPointsOfMonth()));
                contract.setPosition(userDTO.getSpecial());
                contract.setStartDate(userDTO.getStartDate());
                contract.setEndDate(userDTO.getEndDate());
                contract.setInstrumentCategory(ice);

                if (!userDTO.isNewUser()) {
                    me.getContractualObligations().add(updateContractualObligation(contract));
                } else {
                    saveContractualObligation(contract);
                }
            }
        }
        return userToEdit;
    }


    /**
     * changeShortcut modifies the shortcut if it already exists so that it is unique
     *
     * @param shortcut is the shortcut as string that should be changed
     * @return a unique shortcut as String
     */
    public String changeShortcut(String shortcut) throws NoValidShortcutException {
        StringBuilder sb = new StringBuilder(shortcut);

        for (int i = 0; i < 10; i++) {
            char index = (char) (i + '0');
            sb.setCharAt(5, index);
            if (!checkExistingUserShortcut(sb.toString())) {
                return sb.toString();
            }
        }
        for (int i = 0; i < 10; i++) {
            char index = (char) (i + '0');
            sb.setCharAt(4, index);
            if (!checkExistingUserShortcut(sb.toString())) {
                return sb.toString();
            }
        }
        throw new NoValidShortcutException();
    }

    /**
     * checkExistingShortcut checks if the shortcut already exists in the database
     *
     * @param shortcut is the musicians shortcut consisting of the first 3 letters of fname and lname
     * @return true if it exists, false if it doesn't
     */
    public boolean checkExistingUserShortcut(String shortcut) {
        UserDAO uDAO = (UserDAO) _facade.getDAO(UserEntity.class);
        if (uDAO.getByShortcut(shortcut).isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteMusicianRoleMusician(MusicianRoleMusicianEntity mrme) {
        _facade.getDAO(MusicianRoleMusicianEntity.class).delete(mrme);
    }

    public void deleteInstrumentCategoryMusician(InstrumentCategoryMusicianEntity icme) {
        _facade.getDAO(InstrumentCategoryMusicianEntity.class).delete(icme);
    }

    private void deleteContractualObligation(ContractualObligationEntity obligation) {
        _facade.getDAO(ContractualObligationEntity.class).delete(obligation);
    }


    @Transient
    public MusicianRoleEntity findMusicianRoleByRoleString(String role) {
        MusicianRoleEntity returnme = null;
        for (MusicianRoleEntity mre : _facade.getDAO(MusicianRoleEntity.class).getAll()) {
            if (mre.getDescription().compareToIgnoreCase(role) == 0) {
                returnme = mre;
            }
        }
        return returnme;
    }

    @Transient
    public SectionEntity findSectionByString(String section) {
        SectionEntity returnme = null;
        for (SectionEntity se : _facade.getDAO(SectionEntity.class).getAll()) {
            if (se.getDescription().compareToIgnoreCase(section) == 0) {
                returnme = se;
                break;
            }
        }
        return returnme;
    }

    @Transient
    public InstrumentCategoryEntity findInstrumentCategoryByInstrumentString(String instrument) {
        InstrumentCategoryEntity returnme = null;
        for (InstrumentCategoryEntity ice : _facade.getDAO(InstrumentCategoryEntity.class)
            .getAll()) {
            if (ice.getDescription().compareToIgnoreCase(instrument) == 0) {
                returnme = ice;
            }
        }
        return returnme;
    }

}
