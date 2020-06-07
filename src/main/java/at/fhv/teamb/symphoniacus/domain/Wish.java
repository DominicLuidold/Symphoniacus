package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceApiDto;
import at.fhv.teamb.symphoniacus.application.type.WishTargetType;
import at.fhv.teamb.symphoniacus.application.type.WishType;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IDutyPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentCategoryEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IInstrumentationPositionEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IMonthlyScheduleEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Domain object for all kinds of wishes.
 *
 * @author Valentin Goronjic
 */
public class Wish {

    private static final Logger LOG = LogManager.getLogger(Wish.class);
    private static final int MAX_LENGTH_REASON = 45;
    // Intentionally package modifier because of builder here
    String reason;
    IWishEntryEntity dutyRequest;
    INegativeDateWishEntity negativeDateRequest;
    List<MusicalPieceApiDto> musicalPieces;
    private Integer wishId;
    private Musician musician;
    private WishType wishType;
    private WishTargetType target;

    /**
     * Constructs a new wish.
     *
     * @param id  Wish Id
     * @param wt  Wish type
     * @param wtt Wish Target Type
     * @param m   Musician
     */
    public Wish(Integer id, WishType wt, WishTargetType wtt, Musician m) {
        this.wishId = id;
        this.wishType = wishType;
        this.target = wtt;
        this.musician = m;
    }

    /**
     * Checks whether this wish is valid or not.
     *
     * @return True if wish is valid
     */
    public boolean isValid() {
        // max 45 characters
        if (this.reason != null && this.reason.length() > MAX_LENGTH_REASON) {
            LOG.debug("Reason is too long, max 45 chars");
            return false;
        }

        // Detail check for Date / Duty specific Request
        if (this.target.equals(WishTargetType.DATE)) {
            return this.validateDateRequest();
        } else if (this.target.equals(WishTargetType.DUTY)) {
            return this.validateDutyRequest();
        }
        LOG.debug("Wish {} is not valid", this.wishId);
        return false;
    }

    /**
     * Checks whether this wish is editable or not.
     *
     * @return True if wish is editable
     */
    public boolean isEditable() {
        LOG.debug("Checking whether wish is editable or not");
        if (this.target.equals(WishTargetType.DATE)) {
            return this.isEditableDateRequest();
        } else if (this.target.equals(WishTargetType.DUTY)) {
            return this.isEditableDutyRequest();
        }
        LOG.error("Unknown Wish Target Type found");
        return false;
    }

    private boolean isEditableDateRequest() {
        if (LocalDate.now().isAfter(this.negativeDateRequest.getEndDate())) {
            LOG.debug("DateWish is in history, not valid anymore");
            return false;
        }

        // Check if one of the schedule has the endwish date already over
        // because it's too late to edit then
        LOG.debug(
            "Checking if date request {} is editable",
            this.negativeDateRequest.getNegativeDateId()
        );
        List<IMonthlyScheduleEntity> schedules = this.negativeDateRequest.getMonthlySchedules();
        for (IMonthlyScheduleEntity schedule : schedules) {
            if (LocalDate.now().isAfter(schedule.getEndWish())) {
                LOG.debug(
                    "Now is after schedule end wish date for monthly schedule {}",
                    schedule.getMonthlyScheduleId()
                );
                return false;
            }
        }
        return true;
    }

    private boolean isEditableDutyRequest() {
        // Duty is in History
        LocalDate dutyEndDate = this.dutyRequest.getDuty().getEnd().toLocalDate();
        if (LocalDate.now().isAfter(dutyEndDate)) {
            LOG.debug("Duty is in history, not valid anymore");
            return false;
        }

        // End Wish date for Monthly Schedule already over
        LocalDate endWishDate =
            this.dutyRequest.getDuty().getWeeklySchedule().getMonthlySchedule().getEndWish();
        if (LocalDate.now().isAfter(endWishDate)) {
            LOG.debug("Now is after past EndWishDate, not editable");
            return false;
        }

        return true;
    }

    private boolean validateDateRequest() {
        // Not the correct Musician set
        if (!this.negativeDateRequest.getMusician().getMusicianId().equals(
            this.musician.getEntity().getMusicianId())
        ) {
            LOG.debug("DateWish is for different Musician");
            return false;
        }

        return true;
    }

    private boolean validateDutyRequest() {
        // Not the correct Musician set
        if (this.dutyRequest.getNegativeDutyWish() != null
            && !this.dutyRequest.getNegativeDutyWish().getMusician().getMusicianId().equals(
            this.musician.getEntity().getMusicianId())
        ) {
            LOG.debug("Duty Request is for different Musician");
            return false;
        }
        if (this.dutyRequest.getPositiveWish() != null
            && !this.dutyRequest.getPositiveWish().getMusician().getMusicianId().equals(
            this.musician.getEntity().getMusicianId())
        ) {
            LOG.debug("Duty Request is for different Musician");
            return false;
        }

        // musical pieces check
        if (this.musicalPieces != null && this.musicalPieces.isEmpty()) {
            LOG.debug("No Musical Piece defined for duty request");
            return false;
        }

        // Check Musician instrument
        LOG.debug("Checking if Musician has an instrument category in this duty");
        IDutyEntity duty = this.dutyRequest.getDuty();
        Set<IDutyPositionEntity> dutyPositions = duty.getDutyPositions();
        List<IInstrumentCategoryEntity> instrumentCategories =
            this.musician.getEntity().getInstrumentCategories();

        boolean hasCategory = false;
        for (IDutyPositionEntity dp : dutyPositions) {
            LOG.debug(
                "Checking DutyPosition {} for Musician {}",
                dp.getDutyPositionId(),
                this.musician.getFullName()
            );
            IInstrumentationPositionEntity ip = dp.getInstrumentationPosition();
            if (instrumentCategories.contains(ip.getInstrumentCategory())) {
                LOG.debug("Contains Musician's instrument category");
                hasCategory = true;
                break;
            } else {
                LOG.debug("DutyPosition does not contain Musician's instrument category");
            }
        }

        return hasCategory;
    }

    public static class WishBuilder {

        private final Integer wishId;
        private final WishTargetType wtt;
        private final WishType wishType;
        private final Musician musician;
        private String reason;
        private IWishEntryEntity positiveOrNegativeDutyRequest;
        private INegativeDateWishEntity negativeDateRequest;
        private List<MusicalPieceApiDto> musicalPieces;

        /**
         * Constructs a new WishBuilder.
         *
         * @param id  Wish Id
         * @param wt  WishType (Positive, Negative)
         * @param wtt WishTargetType (Duty, Date)
         * @param m   Musician for this wish
         */
        public WishBuilder(Integer id, WishType wt, WishTargetType wtt, Musician m) {
            this.wishId = id;
            this.wtt = wtt;
            this.wishType = wt;
            this.musician = m;
        }

        public WishBuilder withReason(String reason) {
            this.reason = reason;
            return this;
        }

        public WishBuilder withDutyWish(IWishEntryEntity dutyWish) {
            this.positiveOrNegativeDutyRequest = dutyWish;
            return this;
        }

        public WishBuilder withNegativeDateWish(INegativeDateWishEntity negativeDateWish) {
            this.negativeDateRequest = negativeDateWish;
            return this;
        }

        public WishBuilder withMusicalPieces(List<MusicalPieceApiDto> musicalPieces) {
            this.musicalPieces = musicalPieces;
            return this;
        }

        /**
         * Builds the wish.
         *
         * @return Built wish
         */
        public Wish build() {
            Wish wish = new Wish(this.wishId, this.wishType, this.wtt, this.musician);

            if (
                this.wtt.equals(WishTargetType.DUTY)
                    && this.positiveOrNegativeDutyRequest == null
            ) {
                throw new IllegalStateException("No DutyWish supplied");
            }
            if (this.wtt.equals(WishTargetType.DATE) && this.negativeDateRequest == null) {
                throw new IllegalStateException("No NegativeDateWish supplied");
            }

            wish.dutyRequest = this.positiveOrNegativeDutyRequest;
            wish.negativeDateRequest = this.negativeDateRequest;
            wish.reason = this.reason;
            wish.musicalPieces = this.musicalPieces;

            return wish;
        }
    }
}
