package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceApiDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.DutyWishDto;
import at.fhv.teamb.symphoniacus.application.dto.wishdtos.WishDto;
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
import at.fhv.teamb.symphoniacus.persistence.model.NegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.PositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishEntryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.WishRequestable;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicalPieceEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMusicianEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDutyWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IPositiveWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    public Set<WishDto<DutyWishDto>> getAllDutyWishesForUser(Integer userId, Integer dutyId) {

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

                IWishEntryEntity wishEntryEntity = getWishEntryFromDuty(wish.getWishEntries(),
                    dutyEntity.get());

                if (wishEntryEntity != null) {
                    DutyWishDto dutyWishDto = new DutyWishDto();
                    dutyWishDto.setDutyId(dutyId);
                    dutyWishDto.setForEntireSop((wishEntryEntity
                        .getSeriesOfPerformances() != null));
                    for (IMusicalPieceEntity mp : wishEntryEntity.getMusicalPieces()) {
                        dutyWishDto.addMusicalPiece(mp.getMusicalPieceId(), mp.getName());
                    }
                    wishDtos.add(new WishDto.WishBuilder<DutyWishDto>()
                        .withWishId(wish.getPositiveWishId())
                        .withReason(wish.getDescription())
                        .withWishType(WishType.POSITIVE)
                        .withTarget(WishTargetType.DUTY)
                        .withDetails(dutyWishDto).build());
                }
            }

            //add negative Duty wishes
            for (INegativeDutyWishEntity wish : negWishes) {

                IWishEntryEntity wishEntryEntity = getWishEntryFromDuty(wish
                    .getWishEntries(), dutyEntity.get());
                if (wishEntryEntity != null) {
                    DutyWishDto dutyWishDto = new DutyWishDto();
                    dutyWishDto.setDutyId(dutyId);
                    dutyWishDto.setForEntireSop((wishEntryEntity
                        .getSeriesOfPerformances() != null));
                    for (IMusicalPieceEntity mp : wishEntryEntity.getMusicalPieces()) {
                        dutyWishDto.addMusicalPiece(mp.getMusicalPieceId(), mp.getName());
                    }
                    wishDtos.add(new WishDto.WishBuilder<DutyWishDto>()
                        .withWishId(wish.getNegativeDutyId())
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

    private IWishEntryEntity getWishEntryFromDuty(
        List<IWishEntryEntity> wishEntries,
        IDutyEntity duty) {
        for (IWishEntryEntity wish : wishEntries) {
            if (wish.getDuty().getDutyId().equals(duty.getDutyId())) {
                return wish;
            }
        }
        return null;
    }

    /**
     * API - Persists a given DutyWish with all dependencies to the DB.
     *
     * @param dutyWish   given Dto
     * @param UserId  given musician Id of the wish giver
     * @return filled Dto with all Id's or empty if something went wrong.
     */
    public Optional<WishDto<DutyWishDto>> addNewDutyWish(WishDto<DutyWishDto> dutyWish,
                                                         Integer userId) {

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

        Optional<IMusicalPieceEntity> opMusicalPiece = Optional.empty();
        // Iterate through musical Pieces
        for (MusicalPieceApiDto musicalPieceDto : dutyWish.getDetails().getMusicalPieces()) {
            opMusicalPiece = musicalPieceDao.find(musicalPieceDto.getMusicalPieceId());
            if (opMusicalPiece.isPresent()) {
                wishEntry.addMusicalPiece(opMusicalPiece.get());
            } else {
                LOG.error("Delivered MusicalPiece Id:{} could not be found @addNewDutyWish",
                    musicalPieceDto.getMusicalPieceId());
            }
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


    public Optional<WishDto<DutyWishDto>> updateDutyWish(WishDto<DutyWishDto> dutyWish, Integer userId) {
        if (dutyWish.getWishId() == null || dutyWish.getWishType() == null
            || dutyWish.getReason() == null
            || dutyWish.getDetails().getDutyId() == null) {
            LOG.error("Some value @update was null @updateDutyWish");
            return Optional.empty();
        }

        IWishEntryEntity wishEntry = new WishEntryEntity();
        wishEntry.setWishEntryId(dutyWish.getWishId());

    }

    public IWishEntryEntity fillInWishEntry(WishDto<DutyWishDto> dutyWish) {

    }


}
