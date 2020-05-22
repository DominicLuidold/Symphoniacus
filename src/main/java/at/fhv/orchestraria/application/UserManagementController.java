package at.fhv.orchestraria.application;

import at.fhv.orchestraria.UserInterface.Exceptions.NoValidShortcutException;
import at.fhv.orchestraria.UserInterface.Usermanagement.UserDTO;
import at.fhv.orchestraria.domain.Imodel.IMusicianRoleMusician;
import at.fhv.orchestraria.domain.model.MusicianRoleMusicianEntity;
import at.fhv.orchestraria.persistence.dao.DBFacade;
import at.fhv.orchestraria.persistence.dao.UserDAO;
import at.fhv.teamb.symphoniacus.application.adapter.MusicianRoleAdapter;
import at.fhv.teamb.symphoniacus.application.type.AdministrativeAssistantType;
import at.fhv.teamb.symphoniacus.persistence.dao.AdministrativeAssistantDao;
import at.fhv.teamb.symphoniacus.persistence.dao.ContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.InstrumentCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianRoleDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SectionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IAdministrativeAssistantDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IContractualObligationDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IInstrumentCategoryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicianDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicianRoleDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISectionDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IUserDao;
import at.fhv.teamb.symphoniacus.persistence.model.AdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.ContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.InstrumentCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.MusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.SectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.UserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IAdministrativeAssistantEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IContractualObligationEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRole;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.ISectionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Transient;

public class UserManagementController {

    private final static Logger LOGGER = Logger.getLogger(UserManagementController.class.getName());
    private DBFacade _facade;

    private IUserDao userDao;
    private IInstrumentCategoryDao categoryDao;
    private IMusicianRoleDao musicianRoleDao;
    private ISectionDao sectionDao;
    private IMusicianDao musicianDao;
    private IAdministrativeAssistantDao administrativeAssistantDao;
    private IInstrumentCategoryDao instrumentCategoryDao;
    private IContractualObligationDao contractualObligationDao;

    public UserManagementController() {
        //_facade = DBFacade.getInstance();
        this.userDao = new UserDao();
        this.categoryDao = new InstrumentCategoryDao();
        this.musicianRoleDao = new MusicianRoleDao();
        this.sectionDao = new SectionDao();
        this.musicianRoleDao = new MusicianRoleDao();
        this.musicianDao = new MusicianDao();
        this.administrativeAssistantDao = new AdministrativeAssistantDao();
        this.instrumentCategoryDao = new InstrumentCategoryDao();
        this.contractualObligationDao = new ContractualObligationDao();
    }

    public Collection<IInstrumentCategoryEntity> getIInstrumentCategory() {
        return Collections.unmodifiableCollection(this.categoryDao.getAll());
    }

    public Collection<at.fhv.orchestraria.domain.Imodel.IMusicianRole> getIMusicianRole() {
        List<MusicianRoleAdapter> result = new LinkedList<>();
        for (at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianRole role : musicianRoleDao
            .getAll()) {
            MusicianRoleAdapter adapter = new MusicianRoleAdapter(role);
            result.add(adapter);
        }
        return new LinkedList<>(result);
    }

    public Collection<ISectionEntity> getISections() {
        return Collections.unmodifiableCollection(this.sectionDao.getAll());
    }

    public Collection<IUserEntity> getUsers() {
        return Collections.unmodifiableCollection(this.userDao.getAll());
    }

    public UserEntity updateUser(UserEntity ue) {
        Optional<IUserEntity> temp = this.userDao.update(ue);
        if (temp.isPresent()) {
            return (UserEntity) temp.get();
        } else {
            LOGGER.warning("Userentity from dao is empty @ update");
            return null;
        }
    }

    public void updateMusician(IMusicianEntity me) {
        musicianDao.update(me);
    }

    public Optional<IUserEntity> saveUser(UserEntity ue) {
        return this.userDao.persist(ue);
    }

    public Optional<IMusicianEntity> saveMusician(IMusicianEntity me) {
        return musicianDao.persist(me);
    }

    public IMusicianRoleMusician updateMusicianRoleMusician(MusicianRoleMusicianEntity mrme) {
        return _facade.getDAO(MusicianRoleMusicianEntity.class).update(mrme);
    }

    public void updateMusicianRole(IMusicianRole mre) {
        musicianRoleDao.update(mre);
    }

    public void saveMusicianRoleMusician(MusicianRoleMusicianEntity mrme) {
        _facade.getDAO(MusicianRoleMusicianEntity.class).save(mrme);
    }

    public void saveMusicianRole(IMusicianRole mre) {
        musicianRoleDao.persist(mre);
    }

    public void updateAdministrativeAssistant(
        IAdministrativeAssistantEntity aae) {
        administrativeAssistantDao.update(aae);
    }

    public Optional<IAdministrativeAssistantEntity> saveAdministrativeAssistant(
        IAdministrativeAssistantEntity aae) {
        return administrativeAssistantDao.persist(aae);
    }

    public void updateInstrumentCategoryMusician(
        IInstrumentCategoryEntity icme) {
        instrumentCategoryDao.update(icme);
    }

    public void saveInstrumentCategoryMusician(IInstrumentCategoryEntity icme) {
        instrumentCategoryDao.persist(icme);
    }

    public void updateContractualObligation(
        IContractualObligationEntity coe) {
        contractualObligationDao.update(coe);
    }

    public Optional<IContractualObligationEntity> saveContractualObligation(
        IContractualObligationEntity coe) {
        return contractualObligationDao.persist(coe);
    }


    public UserEntity saveGeneral(IUserEntity user, UserDTO userDTO) {

        UserEntity userToEdit = (UserEntity) user;

        if (userToEdit == null) {
            userToEdit = new UserEntity();
            /*
            try {
                PasswordManager.setNewPassword(userToEdit, "PW_" + userToEdit.getShortcut()); //TODO SALT
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "Exception ", e);
            }

             */
        }

        userToEdit.setFirstName(userDTO.getFirstName());
        userToEdit.setLastName(userDTO.getLastName());
        try {
            userToEdit.setPassword("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        IAdministrativeAssistantEntity aae = null;
        if (!userDTO.isMusician()) {
            //toggle is on Admin
            if (userToEdit.getAdministrativeAssistants() != null &&
                userToEdit.getAdministrativeAssistants().size() > 0) {
                userToEdit.getAdministrativeAssistants().get(0).setDescription(
                    AdministrativeAssistantType.valueOf(userDTO.getAdminRole()));
                aae =
                    (AdministrativeAssistantEntity) userToEdit.getAdministrativeAssistants().get(0);
            } else {
                aae = new AdministrativeAssistantEntity();
                aae.setDescription(AdministrativeAssistantType.valueOf(userDTO.getAdminRole()));
                //aae.setUser(userToEdit);
            }

            /*
            if (!userDTO.isNewUser()) {
                //updateUser(userToEdit);
                userToEdit.addAdministrativeAssistant(aae);
                //updateAdministrativeAssistant(userToEdit.getAdministrativeAssistants().get(0));
            } else {
                //userToEdit = updateUser(userToEdit);
                userToEdit.addAdministrativeAssistant(aae);
                //aae.setUser(userToEdit);
                //aae.setUser((userToEdit));
                //saveAdministrativeAssistant(userToEdit.getAdministrativeAssistants().get(0));
            }
             */

            if (userDTO.isNewUser()) {
                Optional<IUserEntity> savedUser = saveUser(userToEdit);
                if (aae != null) {
                    aae.setUser(savedUser.get());
                    Optional<IAdministrativeAssistantEntity> savedAss =
                        saveAdministrativeAssistant(aae);
                    if (savedAss.isPresent()) {
                        savedUser.get().addAdministrativeAssistant(savedAss.get());
                        updateUser((UserEntity) savedUser.get());
                    }
                }
            }else {
                updateAdministrativeAssistant(aae);
            }

        } else {
            //toggle is on Musician
            IMusicianEntity me;
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
                me.setMusicianRoles(new LinkedList<>());
                me.setInstrumentCategories(new LinkedList<>());
            }


            me.setSection(findSectionByString(userDTO.getSection()));
/*
            if (!userDTO.isNewUser()) {
                updateUser(userToEdit);
                updateMusician(me);

            } else {
                for (String role : userDTO.getSelectedRoles()) {
                    if(role != null) {
                        IMusicianRole temp = (findMusicianRoleByRoleString(role));
                        if (temp != null) {
                            me.addMusicianRole(temp);
                        }
                    }
                }
                    //me.addMusicianRole(mrme);
                saveUser(userToEdit);
                saveMusician(me);
            }

            userToEdit.setMusician(me);

*/
            me.removeAllMusicianRoles();
            for (String role : userDTO.getSelectedRoles()) {
                if (role != null) {
                    IMusicianRole temp = (findMusicianRoleByRoleString(role));
                    if (temp != null) {
                        me.addMusicianRole(temp);
                    }
                }
            }
            /*
            if (!userDTO.isNewUser()) {
                updateMusician(me);
            }
*/

/*
            for (String role : userDTO.getSelectedRoles()) {
                IMusicianRole mrme = new MusicianRole();
                mrme.addMusician(me);
                // weil bidirectional
                //mrme.setMusicianRole(findMusicianRoleByRoleString(role));
                //mrme.getMusicianRole().getMusicianRoleMusicians().add(mrme);
                //me.getMusicianRoleMusicians().add(mrme);
                if (!userDTO.isNewUser()) {
                    //updateMusicianRoleMusician(mrme);
                    //updateMusicianRole(mrme);
                } else {
                    //saveMusicianRoleMusician(mrme);
                    //saveMusicianRole(mrme); //TODO WHY?
                }
            }

 */


            me.removeAllInstrumentCategories();
            me.removeAllContractualObligations();

            IContractualObligationEntity contract = null;
            for (String icme : userDTO.getSelectedInstrumentCats()) {
                IInstrumentCategoryEntity cat = findInstrumentCategoryByInstrumentString(icme);
                if (cat != null) {
                    me.addInstrumentCategory(cat);
                    if (!userDTO.isNewUser()) {

                        contract =
                            findContractualObligationByInstAndMusician(me);
                        contract.setPointsPerMonth(Integer.parseInt(userDTO.getPointsOfMonth()));
                        contract.setPosition(userDTO.getSpecial());
                        contract.setStartDate(userDTO.getStartDate());
                        contract.setEndDate(userDTO.getEndDate());
                        contract.setInstrumentCategory(cat);
                        me.addContractualObligation(contract);
                    } else {
                        contract =
                            new ContractualObligationEntity();
                        contract.setPointsPerMonth(Integer.parseInt(userDTO.getPointsOfMonth()));
                        contract.setPosition(userDTO.getSpecial());
                        contract.setStartDate(userDTO.getStartDate());
                        contract.setEndDate(userDTO.getEndDate());
                        contract.setInstrumentCategory(cat);
                        contract.setMusician(me);

                    }
                } else {
                    LOGGER.warning("returned null cat @ findInstrumentCategoryByInstrumentString");
                }
            }


            if (userDTO.isNewUser()) {
                Optional<IUserEntity> savedUser = saveUser(userToEdit);
                me.setUser(savedUser.get());

                if (userDTO.isMusician()) {
                    Optional<IMusicianEntity> savedMusician = saveMusician(me);
                    if (savedMusician.isPresent()) {
                        //userToEdit.setMusician(savedMusician.get());

                        //because contract needs to be saved after musician if musician is new
                        if (contract != null) {
                            contract.setMusician(savedMusician.get());
                            Optional<IContractualObligationEntity> savedContract =
                                saveContractualObligation(contract);
                            if (savedContract.isPresent()) {
                                savedMusician.get().addContractualObligation(savedContract.get());
                                updateMusician(savedMusician.get());
                            }
                        }
                        //me.setUser(userToEdit);
                    }
                    //userToEdit.setMusician(me);

                } else {
                    if (userToEdit.getAdministrativeAssistants().get(0) != null) {
                        saveAdministrativeAssistant(
                            userToEdit.getAdministrativeAssistants().get(0));
                    }
                }

            } else {
                if (userDTO.isMusician()) {
                    //userToEdit.setMusician(me);
                    updateMusician(me);
                }
                /*else {
                    if (userToEdit.getAdministrativeAssistants().get(0) != null) {
                        updateAdministrativeAssistant(
                            userToEdit.getAdministrativeAssistants().get(0));
                    }
                }
                 */
                updateUser(userToEdit);
            }


            /*
            for (String instrumentCat : userDTO.getSelectedInstrumentCats()) {
                //IInstrumentCategoryEntity icme = new InstrumentCategoryEntity();
                IInstrumentCategoryEntity ice =
                    findInstrumentCategoryByInstrumentString(instrumentCat);
                //icme.setMusician(me);
                //icme.setInstrumentCategory(ice);
                ice.addMusician(me);
                me.addInstrumentCategory(ice);

                if (!userDTO.isNewUser()) {
                    updateInstrumentCategoryMusician(ice);
                    //me.getInstrumentCategoryMusicians().add(updateInstrumentCategoryMusician(icme));
                } else {
                    saveInstrumentCategoryMusician(ice);
                }


                IContractualObligationEntity contract = new ContractualObligationEntity();
                contract.setMusician(me);
                contract.setPointsPerMonth(Integer.parseInt(userDTO.getPointsOfMonth()));
                contract.setPosition(userDTO.getSpecial());
                contract.setStartDate(userDTO.getStartDate());
                contract.setEndDate(userDTO.getEndDate());
                contract.setInstrumentCategory(ice);

                if (!userDTO.isNewUser()) {
                    //me.addContractualObligation(contract);
                    updateContractualObligation(contract);
                    //me.getContractualObligations().add(updateContractualObligation(contract));
                } else {
                    saveContractualObligation(contract);
                }
            }
             */
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
        /*
        UserDAO uDAO = (UserDAO) _facade.getDAO(UserEntity.class);
        if (uDAO.getByShortcut(shortcut).isPresent()) {
            return true;
        }
        return false;

         */
        IUserDao dao = new UserDao();
        if (dao.loadUser(shortcut).isPresent()) {
            return true;
        }
        return false;
    }

    public void deleteMusicianRoleMusician(IMusicianRole mrme) {
        IMusicianRoleDao dao = new MusicianRoleDao();
        dao.remove(mrme);
    }

    public void deleteInstrumentCategoryMusician(IInstrumentCategoryEntity icme) {
        instrumentCategoryDao.remove(icme);
    }

    private void deleteContractualObligation(IContractualObligationEntity obligation) {
        contractualObligationDao.remove(obligation);
    }

    /*
    For integration
     */
    public IContractualObligationEntity findContractualObligationByInstAndMusician(
        IMusicianEntity musician) {
        return contractualObligationDao.getContractualObligation(musician);
    }


    @Transient
    public IMusicianRole findMusicianRoleByRoleString(String role) {
        IMusicianRole returnme = null;
        for (IMusicianRole mre : this.musicianRoleDao.getAll()) {
            if (mre.getDescription() != null) {
                if (mre.getDescription().toString().equals(role)) {
                    returnme = mre;
                }
            } else {
                LOGGER.warning("Musician Role is null  @findMusicianRoleByRoleString ");
            }
        }
        return returnme;
    }


    @Transient
    public ISectionEntity findSectionByString(String section) {
        SectionDao sectionDao = new SectionDao();
        ISectionEntity returnme = null;
        for (ISectionEntity se : sectionDao.getAll(SectionEntity.class)) {
            if (se.getDescription().compareToIgnoreCase(section) == 0) {
                returnme = se;
                break;
            }
        }
        return returnme;
    }

    @Transient
    public IInstrumentCategoryEntity findInstrumentCategoryByInstrumentString(String instrument) {
        IInstrumentCategoryEntity returnme = null;
        InstrumentCategoryDao instrumentCategoryDao = new InstrumentCategoryDao();
        for (IInstrumentCategoryEntity ice : instrumentCategoryDao
            .getAll(InstrumentCategoryEntity.class)) {
            if (ice.getDescription().compareToIgnoreCase(instrument) == 0) {
                returnme = ice;
            }
        }
        return returnme;
    }

}
