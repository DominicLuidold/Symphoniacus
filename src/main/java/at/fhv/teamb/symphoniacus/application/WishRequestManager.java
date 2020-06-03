package at.fhv.teamb.symphoniacus.application;

import at.fhv.orchestraria.domain.Imodel.INegativeDateWish;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DateWishDto;
import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceApiDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DutyWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishStatus;
import at.fhv.teamb.symphoniacus.application.dto.DutyDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishTargetType;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishType;
import at.fhv.teamb.symphoniacus.domain.Duty;
import at.fhv.teamb.symphoniacus.domain.MusicalPiece;
import at.fhv.teamb.symphoniacus.domain.Musician;
import at.fhv.teamb.symphoniacus.domain.WishRequest;
import at.fhv.teamb.symphoniacus.persistence.dao.DutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.MusicianDao;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.NegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.PositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.UserDao;
import at.fhv.teamb.symphoniacus.persistence.dao.SeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.dao.WishEntryDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IDutyDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicalPieceDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IMusicianDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.INegativeDateWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.INegativeDutyWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IPositiveWishDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.ISeriesOfPerformancesDao;
import at.fhv.teamb.symphoniacus.persistence.dao.interfaces.IWishEntryDao;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishEntryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IUserEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * UseCase Controller responsible for wishes of a musician.
 *
 * @author Danijel Antonijevic
 * @author Nino Heinzle
 */
public class WishRequestManager {

    private static final Logger LOG = LogManager.getLogger(WishRequestManager.class);
    private final IPositiveWishDao positiveWishDao;
    private final INegativeDutyWishDao negDutyWishDao;
    private final INegativeDateWishDao negDateWishDao;
    private final IMusicianDao musicianDao;
    private final IWishEntryDao wishEntryDao;
    private final IDutyDao dutyDao;
    private final ISeriesOfPerformancesDao seriesOfPerformancesDao;
    private final IMusicalPieceDao musicalPieceDao;
    private Set<WishRequestable> allWishRequests;
    private List<IWishEntryEntity> wishEntries;
    private DutyManager dutyManager;

    /**
     * Initializes the WishRequestManager.
     */
    public WishRequestManager() {
        this.positiveWishDao = new PositiveWishDao();
        this.negDutyWishDao = new NegativeDutyWishDao();
        this.negDateWishDao = new NegativeDateWishDao();
        this.wishEntryDao = new WishEntryDao();
        this.dutyDao = new DutyDao();
        this.musicianDao = new MusicianDao();
        this.seriesOfPerformancesDao = new SeriesOfPerformancesDao();
        this.musicalPieceDao = new MusicalPieceDao();
        this.dutyManager = new DutyManager();
    }

    /**
     * Preloads all WishRequests that exist for a given duty.
     *
     * @param duty duty
     */
    public void loadAllWishRequests(IDutyEntity duty) {
        allWishRequests = new LinkedHashSet<>();
        this.allWishRequests.addAll(positiveWishDao.getAllPositiveWishes(duty));
        this.allWishRequests.addAll(negDateWishDao.getAllNegativeDateWishes(duty));
        this.allWishRequests.addAll(negDutyWishDao.getAllNegativeDutyWishes(duty));
    }


    /**
     * Sets the Attribute 'wishRequest' of the musician domain object.
     *
     * @param musician given musician domain object
     * @return the same musician
     */
    public Musician setMusicianWishRequest(Musician musician, IDutyEntity duty) {
        // Beware! if allWish isn't loaded this method will load
        // the wishes individually from the DAO
        if (this.allWishRequests == null) {
            // TODO - lösung überlegen ist nur Alternative,
            //  falls man das laden vergisst, nicht zwingend Notwendig
        } else {
            Optional<WishRequest> wishRequest =
                    WishRequest
                            .getWishRequestToMusician(musician.getEntity(), this.allWishRequests);
            if (wishRequest.isPresent()) {
                musician.setWishRequest(wishRequest.get());
            } else {
                musician.setWishRequest(null);
            }
        }
        return musician;
    }

    /**
     * Creates Dtos for all DateWishes of a specific User and returns them as a List.
     *
     * @param userId given unique User Identification Number
     * @return List of DateWishDtos
     */
    public List<WishDto<DateWishDto>> getAllDateWishesForUser(Integer userId) {
        Optional<IUserEntity> userEntity = new UserDao().find(userId);
        if (userEntity.isPresent()) {
            LinkedList<WishDto<DateWishDto>> dateWishRequests = new LinkedList<>();
            for (INegativeDateWishEntity ndwe :
                    negDateWishDao.getAllNegativeDateWishesOfUser(userEntity.get())) {
                DateWishDto dateWishDto = new DateWishDto(ndwe.getStartDate(), ndwe.getEndDate());
                WishDto<DateWishDto> wishDto = new WishDto.WishBuilder<DateWishDto>()
                        .withWishId(ndwe.getNegativeDateId())
                        .withWishType(WishType.NEGATIVE)
                        .withTarget(WishTargetType.DATE)
                        .withStatus(WishStatus.REVIEW)
                        .withReason(ndwe.getDescription())
                        .withDetails(dateWishDto)
                        .build();

                dateWishRequests.add(wishDto);
            }
            return dateWishRequests;

        }
        LOG.error("Delivered User Id:{} could not be found @getAllDateWishesForUser");
        return null;
    }

    /**
     * Creates Dto for a given DateWish with Id.
     *
     * @param dateWishId Identification Number of the DateWish
     * @return Dto Object
     */
    public WishDto<DateWishDto> getDateWish(Integer dateWishId) {
        Optional<INegativeDateWishEntity> ndwe = negDateWishDao.find(dateWishId);
        if (ndwe.isPresent()) {
            DateWishDto dateWishDto = new DateWishDto(
                    ndwe.get().getStartDate(),
                    ndwe.get().getEndDate()
            );
            WishDto<DateWishDto> wishDto = new WishDto.WishBuilder<DateWishDto>()
                    .withWishId(ndwe.get().getNegativeDateId())
                    .withWishType(WishType.NEGATIVE)
                    .withTarget(WishTargetType.DATE)
                    .withStatus(WishStatus.REVIEW)
                    .withReason(ndwe.get().getDescription())
                    .withDetails(dateWishDto)
                    .build();

            return wishDto;
        }
        LOG.error("Delivered Date Id:{} could not be found @getDateWish");
        return null;
    }

    /**
     * API - Persists a given DateWish with all dependencies to the DB.
     *
     * @param dateWish given Dto
     * @param userId   given user Id of the wish giver, musician id is needed for creating a wish
     * @return filled Dto with all Id's or empty if something went wrong.
     */
    public Optional<WishDto<DateWishDto>> addNewDateWish(
            WishDto<DateWishDto> dateWish,
            Integer userId
    ) {

        Optional<INegativeDateWishEntity> opDateWishEntity = fillInDateWish(dateWish, userId);
        INegativeDateWishEntity dateWishEntity = null;
        if (opDateWishEntity.isPresent()) {
            dateWishEntity = opDateWishEntity.get();
        } else {
            return Optional.empty();
        }
        Optional<INegativeDateWishEntity> result = negDateWishDao.persist(dateWishEntity);
        if (result.isPresent()) {
            return Optional.ofNullable(getDateWish(result.get().getNegativeDateId()));
        } else {
            LOG.error("NegativeDateWish couldn't be persisted");
            return Optional.empty();
        }
    }

    /**
     * Updates a Wish by given DateWishDto in DB.
     *
     * @param dateWish given DateWishDto
     * @param userId   given User Id is needed because musician id is needed
     *                 for a wish to be made or updated
     * @return Optional.empty if values are missing, optional of same dto if success
     */
    public Optional<WishDto<DateWishDto>> updateDateWish(
            WishDto<DateWishDto> dateWish,
            Integer userId
    ) {
        if (dateWish.getWishId() == null || dateWish.getWishType() == null
                || dateWish.getReason() == null
                || dateWish.getDetails().getStart() == null
                || dateWish.getDetails().getEnd() == null
        ) {
            LOG.error("Some value @update was null @updateDateWish");
            return Optional.empty();
        }
        Optional<INegativeDateWishEntity> opDateWishEntity = fillInDateWish(dateWish, userId);
        INegativeDateWishEntity dateWishEntity = null;
        if (opDateWishEntity.isPresent()) {
            dateWishEntity = opDateWishEntity.get();
        } else {
            return Optional.empty();
        }
        dateWishEntity.setNegativeDateId(dateWish.getWishId());
        negDateWishDao.update(dateWishEntity);
        return Optional.of(dateWish);
    }

    // creates of given Dto a DateWishEntity
    // BEWARE! Id of entity wont be set
    private Optional<INegativeDateWishEntity> fillInDateWish(
            WishDto<DateWishDto> dateWish,
            Integer userId
    ) {

        Optional<IMusicianEntity> opMusician = this.musicianDao.findMusicianByUserId(userId);
        Integer musicianId = null;
        if (opMusician.isPresent()) {
            musicianId = opMusician.get().getMusicianId();
        } else {
            LOG.error("There is no Musician to the given User id:{}", userId);
            return Optional.empty();
        }

        Optional<IMusicianEntity> musician = musicianDao.find(musicianId);
        if (musician.isEmpty()) {
            LOG.error("MusicianId {} did not deliver a result @addNewDateWish", musicianId);
            return Optional.empty();
        }

        if (dateWish.getTarget() != WishTargetType.DATE) {
            LOG.error("WishDto<DateWishDto> has WishTargetType.DUTY as target");
            return Optional.empty();
        }

        INegativeDateWishEntity dateWishEntity = new NegativeDateWishEntity();
        dateWishEntity.setStartDate(dateWish.getDetails().getStart());
        dateWishEntity.setEndDate(dateWish.getDetails().getEnd());
        dateWishEntity.setMusician(musician.get()); //theoretisch nullable
        dateWishEntity.setDescription(dateWish.getReason());

        return Optional.of(dateWishEntity);
    }

    public void loadAllWishEntriesForDuty(Duty duty) {
        this.wishEntries = this.wishEntryDao.findAll();
    }

    private boolean checkSopOrMusicalPieceWish(
            IWishEntryEntity wishEntry,
            int wishMusicianId,
            int musicianId,
            IDutyEntity duty,
            IMusicalPieceEntity musicalPiece
    ) {
        LOG.debug("Checking SOP or MusicalPiece wish");
        if (wishMusicianId == musicianId) {
            LOG.debug("Wish {} has musician: {}", wishEntry.getWishEntryId(), musicianId);
            // This wish is for the given musician
            for (IMusicalPieceEntity mp : wishEntry.getMusicalPieces()) {
                // Wish for whole SOP
                boolean isSopRequest = isWishEntryDutySopSame(wishEntry, duty);
                if (isSopRequest) {
                    LOG.debug(
                            "WishEntry {} is for whole series {}",
                            wishEntry.getWishEntryId(),
                            duty.getSeriesOfPerformances().getDescription()
                    );
                    return true;
                }
                // Wish for this musical piece
                if (duty.getDutyId().equals(wishEntry.getDuty().getDutyId())
                        && mp.getMusicalPieceId().equals(musicalPiece.getMusicalPieceId())) {
                    LOG.debug(
                            "WishEntry {} is for Musical Piece {}",
                            wishEntry.getWishEntryId(),
                            mp.getName()
                    );
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a WishRequest exists for the given User, Duty and Musical Piece.
     *
     * @param m            given Musician
     * @param musicalPiece given Musical Piece
     * @param duty         given Duty
     * @return boolean     If WishRequest exists for given Duty and Musical Piece
     */
    public boolean hasWishRequestForGivenDutyAndMusicalPiece(
            Musician m,
            MusicalPiece musicalPiece,
            Duty duty
    ) {
        boolean hasRequest = false;

        for (IWishEntryEntity wishEntry : this.wishEntries) {
            int wishMusicianId = -1;
            if (wishEntry.getPositiveWish() != null) {
                wishMusicianId = wishEntry
                        .getPositiveWish()
                        .getMusician()
                        .getMusicianId();
            } else if (wishEntry.getNegativeDutyWish() != null) {
                wishMusicianId = wishEntry
                        .getNegativeDutyWish()
                        .getMusician()
                        .getMusicianId();
            }
            hasRequest = checkSopOrMusicalPieceWish(
                    wishEntry,
                    wishMusicianId,
                    m.getEntity().getMusicianId(),
                    duty.getEntity(),
                    musicalPiece.getEntity()
            );
            if (hasRequest) {
                return true;
            }
        }

        LOG.debug(
                "Does Musician {} have a wish for Duty {} with MusicalPiece {}? {}",
                m.getFullName(),
                duty.getTitle(),
                musicalPiece.getEntity().getName(),
                hasRequest
        );
        //if a wishrequest is set for a whole series of performances
        // the wish should display on every duty of the sop
        return hasRequest;
    }

    private boolean isWishEntryDutySopSame(IWishEntryEntity wishEntry, IDutyEntity duty) {
        if (wishEntry.getSeriesOfPerformances() != null
                && wishEntry.getSeriesOfPerformances().getSeriesOfPerformancesId()
                .equals(
                        duty.getSeriesOfPerformances().getSeriesOfPerformancesId()
                )
        ) {
            return true;
        }
        return false;
    }

    /**
     * get all positive and negative duty wishes for a given dutyId and a given userId.
     *
     * @param userId given User Id
     * @param dutyId given Duty Id
     * @return all dutyWishes for a given duty and musician
     */
    public Set<WishDto<DutyWishDto>> getAllDutyWishesForUserAndDuty(
            Integer userId,
            Integer dutyId
    ) {
        Optional<IDutyEntity> dutyEntity = this.dutyDao.find(dutyId);
        Optional<IMusicianEntity> musicianEntity = this.musicianDao.findMusicianByUserId(userId);

        if (dutyEntity.isPresent() && musicianEntity.isPresent()) {
            List<IPositiveWishEntity> posWishes = new LinkedList<>((this.positiveWishDao
                    .getAllPositiveWishesForMusician(
                            dutyEntity.get(), musicianEntity.get())));
            List<INegativeDutyWishEntity> negWishes = new LinkedList<>(this.negDutyWishDao
                    .getAllNegativeDutyWishesForMusician(
                            dutyEntity.get(), musicianEntity.get()));

            Set<WishDto<DutyWishDto>> wishDtos = new LinkedHashSet<>();
            //add positive Duty wishes
            for (IPositiveWishEntity wish : posWishes) {

                Optional<IWishEntryEntity> wishEntryEntity = this.positiveWishDao
                        .getWishEntryByPositiveWish(
                                wish, dutyEntity.get());

                if (wishEntryEntity.isPresent()) {
                    DutyWishDto dutyWishDto = new DutyWishDto();
                    dutyWishDto.setDutyId(dutyId);
                    dutyWishDto.setForEntireSop((wishEntryEntity.get()
                            .getSeriesOfPerformances() != null));
                    for (IMusicalPieceEntity mp : wishEntryEntity.get().getMusicalPieces()) {
                        dutyWishDto.addMusicalPiece(mp.getMusicalPieceId(), mp.getName());
                    }
                    wishDtos.add(new WishDto.WishBuilder<DutyWishDto>()
                            .withWishId(wishEntryEntity.get().getWishEntryId())
                            .withReason(wish.getDescription())
                            .withWishType(WishType.POSITIVE)
                            .withTarget(WishTargetType.DUTY)
                            .withDetails(dutyWishDto).build());
                }
            }

            //add negative Duty wishes
            for (INegativeDutyWishEntity wish : negWishes) {

                Optional<IWishEntryEntity> wishEntryEntity = this.negDutyWishDao
                        .getWishEntryByNegativeDutyWish(
                                wish, dutyEntity.get());
                if (wishEntryEntity.isPresent()) {
                    DutyWishDto dutyWishDto = new DutyWishDto();
                    dutyWishDto.setDutyId(dutyId);
                    dutyWishDto.setForEntireSop((wishEntryEntity.get()
                            .getSeriesOfPerformances() != null));
                    for (IMusicalPieceEntity mp : wishEntryEntity.get().getMusicalPieces()) {
                        dutyWishDto.addMusicalPiece(mp.getMusicalPieceId(), mp.getName());
                    }
                    wishDtos.add(new WishDto.WishBuilder<DutyWishDto>()
                            .withWishId(wishEntryEntity.get().getWishEntryId())
                            .withReason(wish.getDescription())
                            .withWishType(WishType.NEGATIVE)
                            .withTarget(WishTargetType.DUTY)
                            .withDetails(dutyWishDto).build());
                }
            }
            return wishDtos;
        }
        return new LinkedHashSet<>();
    }

    /**
     * API - Persists a given DutyWish with all dependencies to the DB.
     *
     * @param dutyWish given Dto
     * @param userId   given user Id of the wish giver, musician id is needed for creating a wish
     * @return filled Dto with all Id's or empty if something went wrong.
     */
    public Optional<WishDto<DutyWishDto>> addNewDutyWish(
            WishDto<DutyWishDto> dutyWish,
            Integer userId
    ) {

        Optional<IWishEntryEntity> opWishEntry = fillInWishEntry(dutyWish, userId);
        IWishEntryEntity wishEntry = null;
        if (opWishEntry.isPresent()) {
            wishEntry = opWishEntry.get();
        } else {
            return Optional.empty();
        }
        Optional<IWishEntryEntity> result = wishEntryDao.persist(wishEntry);
        if (result.isPresent()) {
            return Optional.ofNullable(getDutyWish(result.get().getWishEntryId()));
        } else {
            LOG.error("Wishentry couldn't be persisted");
            return Optional.empty();
        }
    }

    /**
     * Delivers a filled WishDto.
     *
     * @param wishId Id of the needed WishEntry (DB)
     * @return Dto of WishDto<DutyWishDto/>
     */
    public WishDto<DutyWishDto> getDutyWish(Integer wishId) {
        // Get the WishEntry from DB
        Optional<IWishEntryEntity> opWishEntry = wishEntryDao.find(wishId);
        IWishEntryEntity wishEntry = null;
        if (opWishEntry.isPresent()) {
            wishEntry = opWishEntry.get();
        } else {
            LOG.error("Wish entry Id:{} could not be found", wishId);
            return null;
        }

        //Build DutyWishDto
        boolean isSop = false;
        if (wishEntry.getSeriesOfPerformances() != null) {
            isSop = true;
        }
        DutyWishDto dutyWishDto = new DutyWishDto(wishEntry.getDuty().getDutyId(), isSop);
        List<MusicalPieceApiDto> musicalPieceApiDtos = new LinkedList<>();
        for (IMusicalPieceEntity musicalPiece : wishEntry.getMusicalPieces()) {
            musicalPieceApiDtos.add(
                new MusicalPieceApiDto(musicalPiece.getMusicalPieceId(), musicalPiece.getName()));
        }
        dutyWishDto.setMusicalPieces(musicalPieceApiDtos);

        WishDto.WishBuilder<DutyWishDto> result = new WishDto.WishBuilder<DutyWishDto>();
        result.withWishId(wishEntry.getWishEntryId());
        result.withDetails(dutyWishDto);
        result.withTarget(WishTargetType.DUTY);

        if (wishEntry.getPositiveWish() != null) {
            result.withWishType(WishType.POSITIVE);
            result.withReason(wishEntry.getPositiveWish().getDescription());
        } else if (wishEntry.getNegativeDutyWish() != null) {
            result.withWishType(WishType.NEGATIVE);
            result.withReason(wishEntry.getNegativeDutyWish().getDescription());
        } else {
            LOG.error(
                    "WishEntry id:{} cointaines neither a positive, nor negative wish @getDutyWish",
                    wishEntry.getWishEntryId());
        }
        return result.build();
    }


    /**
     * Updates a Wish by given DutyWishDto in DB.
     *
     * @param dutyWish given DutyWishDto !MUST! contain WishEntry Id (wishId)
     * @param userId   given User Id is needed because musician id is needed
     *                 for a wish to be made or updated
     * @return Optional.empty if values are missing, optional of same dto if success
     */
    public Optional<WishDto<DutyWishDto>> updateDutyWish(
            WishDto<DutyWishDto> dutyWish,
            Integer userId
    ) {
        if (dutyWish.getWishId() == null || dutyWish.getWishType() == null
                || dutyWish.getReason() == null
                || dutyWish.getDetails().getDutyId() == null) {
            LOG.error("Some value @update was null @updateDutyWish");
            return Optional.empty();
        }

        Optional<IWishEntryEntity> opWishEntry = fillInWishEntry(dutyWish, userId);
        IWishEntryEntity wishEntry = null;
        if (opWishEntry.isPresent()) {
            wishEntry = opWishEntry.get();
        } else {
            return Optional.empty();
        }
        wishEntry.setWishEntryId(dutyWish.getWishId());

        // Set correct Wish Id's
        Optional<IWishEntryEntity> opOriginalEntry = wishEntryDao.find(dutyWish.getWishId());
        IWishEntryEntity originalEntry = null;
        if (opOriginalEntry.isPresent()) {
            originalEntry = opOriginalEntry.get();
            if (originalEntry.getPositiveWish() != null) {
                wishEntry.getPositiveWish()
                        .setPositiveWishId(originalEntry.getPositiveWish().getPositiveWishId());
            } else if (originalEntry.getNegativeDutyWish() != null) {
                wishEntry.getNegativeDutyWish()
                        .setNegativeDutyId(originalEntry.getNegativeDutyWish().getNegativeDutyId());
            } else {
                return Optional.empty();
            }
        }
        wishEntryDao.update(wishEntry);
        return Optional.of(dutyWish);
    }

    // creates of given Dto a WishEntryEntity
    // BEWARE! Id of entity wont be set
    private Optional<IWishEntryEntity> fillInWishEntry(
            WishDto<DutyWishDto> dutyWish,
            Integer userId
    ) {

        Optional<IMusicianEntity> opMusician = this.musicianDao.findMusicianByUserId(userId);
        Integer musicianId = null;
        if (opMusician.isPresent()) {
            musicianId = opMusician.get().getMusicianId();
        } else {
            LOG.error("There is no Musician to the given User id:{}", userId);
            return Optional.empty();
        }

        Optional<IMusicianEntity> musician = musicianDao.find(musicianId);
        if (musician.isEmpty()) {
            LOG.error("MusicianId {} did not deliver a result @addNewDutyWish", musicianId);
            return Optional.empty();
        }

        if (dutyWish.getTarget() != WishTargetType.DUTY) {
            LOG.error("WishDto<DutyWishDto> has WishTragetType.DATE as target");
            return Optional.empty();
        }

        Optional<IDutyEntity> opDuty = dutyDao.find(dutyWish.getDetails().getDutyId());
        if (opDuty.isEmpty()) {
            LOG.error("Delivered Duty Id:{} could not be found @addNewDutyWish",
                    dutyWish.getDetails().getDutyId());
            return Optional.empty();
        }
        IDutyEntity duty = opDuty.get();

        IPositiveWishEntity positiveWish = null;
        INegativeDutyWishEntity negativeWish = null;

        if (dutyWish.getWishType() == WishType.POSITIVE) {
            positiveWish = new PositiveWishEntity();
            positiveWish.setMusician(musician.get());
            positiveWish.setDescription(dutyWish.getReason());
            positiveWish.setSeriesOfPerformances(duty.getSeriesOfPerformances());

        }
        if (dutyWish.getWishType() == WishType.NEGATIVE) {
            negativeWish = new NegativeDutyWishEntity();
            negativeWish.setMusician(musician.get());
            negativeWish.setDescription(dutyWish.getReason());
            negativeWish.setSeriesOfPerformances(duty.getSeriesOfPerformances());
        }

        IWishEntryEntity wishEntry = new WishEntryEntity();
        wishEntry.setDuty(duty);
        if (dutyWish.getDetails().getForEntireSop()) {
            wishEntry.setSeriesOfPerformances(duty.getSeriesOfPerformances());
        }
        if (positiveWish != null) {
            wishEntry.setPositiveWish(positiveWish);
        } else if (negativeWish != null) {
            wishEntry.setNegativeDutyWish(negativeWish);
        }

        // Iterate through musical Pieces
        for (MusicalPieceApiDto musicalPieceDto : dutyWish.getDetails().getMusicalPieces()) {
            Optional<IMusicalPieceEntity> opMusicalPiece = musicalPieceDao.find(
                    musicalPieceDto.getMusicalPieceId()
            );
            if (opMusicalPiece.isPresent()) {
                wishEntry.addMusicalPiece(opMusicalPiece.get());
            } else {
                LOG.error("Delivered MusicalPiece Id:{} could not be found @addNewDutyWish",
                        musicalPieceDto.getMusicalPieceId());
            }
        }
        return Optional.of(wishEntry);
    }

    /**
     * Removes a WishEntry to given Id.
     *
     * @param dutyWishId id of given WishEntry
     * @return true if removed, false if removing failed
     */
    public boolean removeDutyWish(Integer dutyWishId) {
        Optional<IWishEntryEntity> opWishEntry = this.wishEntryDao.find(dutyWishId);
        if (opWishEntry.isPresent()) {
            return wishEntryDao.remove(opWishEntry.get());
        } else {
            LOG.debug("Given Wish Entry Id by API does not exist in DB:{} ", dutyWishId);
            return false;
        }
    }

    /**
     * Get all future duty wishes of given user.
     *
     * @param userId Identifier of user
     * @return Set of all Duty Wishes
     */
    public Set<WishDto<DutyWishDto>> getAllFutureDutyWishesOfUser(int userId) {
        // Find all future unscheduled duties of user
        Set<DutyDto> duties = this.dutyManager.findFutureUnscheduledDutiesForMusician(userId);
        LOG.debug("Found {} future unscheduled duties for user", duties.size());

        // Store all wishes here
        Set<WishDto<DutyWishDto>> result = new HashSet<>();

        // get duty wishes from those duties
        for (DutyDto dto : duties) {
            Set<WishDto<DutyWishDto>> wishes = getAllDutyWishesForUserAndDuty(
                    userId,
                    dto.getDutyId()
            );
            LOG.debug(
                    "Found {} duty wishes duties for user {} and duty {}",
                    wishes.size(),
                    userId,
                    dto.getDutyId()
            );

            result.addAll(wishes);
        }

        LOG.debug("Found {} duty wishes for user", result.size());
        return result;
    }
}
