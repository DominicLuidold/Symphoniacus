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
 * @param <T> Generic type for Duty Request (Negative, Positive)
 * @author Valentin Goronjic
 */
public class Wish<T extends IWishEntryEntity> {

    private static final Logger LOG = LogManager.getLogger(Wish.class);
    private static final int MAX_LENGTH_REASON = 45;

    private Integer wishId;
    private Musician musician;
    private WishType wishType;
    private WishTargetType target;
    private String reason;
    private T dutyRequest;
    private INegativeDateWishEntity negativeDateRequest;
    private List<MusicalPieceApiDto> musicalPieces;

    /**
     * Checks whether this wish is valid or not.
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
        // DutyWish is in History
        if (LocalDate.now().isAfter(this.dutyRequest.getNegativeDutyWish().getEndDate())) {
            LOG.debug("DutyWish is in history, not valid anymore");
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
        if (!this.dutyRequest.getNegativeDutyWish().getMusician().getMusicianId().equals(
            this.musician.getEntity().getMusicianId())
        ) {
            LOG.debug("DateWish is for different Musician");
            return false;
        }

        return true;
    }

    private boolean validateDutyRequest() {
        // Not the correct Musician set
        if (!this.dutyRequest.getNegativeDutyWish().getMusician().getMusicianId().equals(
            this.musician.getEntity().getMusicianId())
        ) {
            LOG.debug("DateWish is for different Musician");
            return false;
        }

        // musical pieces check
        if (this.musicalPieces.isEmpty()) {
            LOG.debug("No Musical Piece defined for duty request");
            return false;
        }

        // Check Musician instrument
        LOG.debug("Checking if Musician has an instrument category in this duty");
        IDutyEntity duty = this.dutyRequest.getDuty();
        Set<IDutyPositionEntity> dutyPositions = duty.getDutyPositions();
        for (IDutyPositionEntity dp : dutyPositions) {
            LOG.debug(
                "Checking DutyPosition {} for Musician {}",
                dp.getDutyPositionId(),
                this.musician.getFullName()
            );
            IInstrumentationPositionEntity ip = dp.getInstrumentationPosition();
            List<IInstrumentCategoryEntity> instrumentCategories =
                this.musician.getEntity().getInstrumentCategories();

            if (!instrumentCategories.contains(ip.getInstrumentCategory())) {
                LOG.debug("Duty does not contain Musician's instrument category");
                return false;
            } else {
                LOG.debug("Contains Musician's instrument category");
                break;
            }
        }

        return true;
    }

    public static class WishBuilder<T extends IWishEntryEntity> {

        private final Integer wishId;
        private final WishTargetType wtt;
        private final WishType wishType;
        private final Musician musician;
        private String reason;
        private T dutyWish;
        private INegativeDateWishEntity negativeDateWish;
        private List<MusicalPieceApiDto> musicalPieces;

        /**
         * Constructs a new WishBuilder.
         * @param id Wish Id
         * @param wt WishType (Positive, Negative)
         * @param wtt WishTargetType (Duty, Date)
         * @param m Musician for this wish
         */
        public WishBuilder(Integer id, WishType wt, WishTargetType wtt, Musician m) {
            this.wishId = id;
            this.wtt = wtt;
            this.wishType = wt;
            this.musician = m;
        }

        public WishBuilder<T> withReason(String reason) {
            this.reason = reason;
            return this;
        }

        public WishBuilder<T> withDutyWish(T dutyWish) {
            this.dutyWish = dutyWish;
            return this;
        }

        public WishBuilder<T> withNegativeDateWish(INegativeDateWishEntity negativeDateWish) {
            this.negativeDateWish = negativeDateWish;
            return this;
        }

        public WishBuilder<T> withMusicalPieces(List<MusicalPieceApiDto> musicalPieces) {
            this.musicalPieces = musicalPieces;
            return this;
        }

        /**
         * Builds the wish.
         * @return Built with
         */
        public Wish<T> build() {
            Wish<T> wish = new Wish<>();

            if (this.wtt.equals(WishTargetType.DUTY) && this.dutyWish == null) {
                throw new IllegalStateException("No DutyWish supplied");
            }
            if (this.wtt.equals(WishTargetType.DATE) && this.negativeDateWish == null) {
                throw new IllegalStateException("No NegativeDateWish supplied");
            }

            wish.wishType = this.wishType;
            wish.target = this.wtt;
            wish.dutyRequest = this.dutyWish;
            wish.negativeDateRequest = this.negativeDateWish;
            wish.musician = this.musician;
            wish.reason = this.reason;
            wish.wishId = this.wishId;
            wish.musicalPieces = this.musicalPieces;

            return wish;
        }
    }
}
