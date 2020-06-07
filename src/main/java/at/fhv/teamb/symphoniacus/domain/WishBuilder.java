package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceApiDto;
import at.fhv.teamb.symphoniacus.application.type.WishTargetType;
import at.fhv.teamb.symphoniacus.application.type.WishType;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.INegativeDateWishEntity;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;

import java.util.List;

public class WishBuilder<T extends IWishEntryEntity> {

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
     *
     * @return Built wish
     */
    public Wish<T> build() {
        Wish<T> wish = new Wish<>(this.wishId, this.wishType, this.wtt, this.musician);

        if (this.wtt.equals(WishTargetType.DUTY) && this.dutyWish == null) {
            throw new IllegalStateException("No DutyWish supplied");
        }
        if (this.wtt.equals(WishTargetType.DATE) && this.negativeDateWish == null) {
            throw new IllegalStateException("No NegativeDateWish supplied");
        }

        wish.dutyRequest = this.dutyWish;
        wish.negativeDateRequest = this.negativeDateWish;
        wish.reason = this.reason;
        wish.musicalPieces = this.musicalPieces;

        return wish;
    }
}

