package at.fhv.teamb.symphoniacus.domain;

import at.fhv.teamb.symphoniacus.application.type.WishStatusType;
import at.fhv.teamb.symphoniacus.application.type.WishTargetType;
import at.fhv.teamb.symphoniacus.application.type.WishType;
import at.fhv.teamb.symphoniacus.persistence.model.interfaces.IWishEntryEntity;

public class Wish<T extends IWishEntryEntity> {

    // Editable -> datewish in vergangenheit, status prüfen
    // Deletable (gleich wie editable?)
    // status
    // isValid
    private Integer wishId;
    private Musician musician;
    private WishType wishType;
    private WishTargetType target;
    private WishStatusType status;
    private String reason;
    private T entity;

    public Wish(
        Integer wishId,
        Musician musician,
        WishType wishType,
        WishTargetType target,
        WishStatusType status,
        String reason,
        T entity
    ) {
        this.wishId = wishId;
        this.musician = musician;
        this.wishType = wishType;
        this.target = target;
        this.status = status;
        this.reason = reason;
        this.entity = entity;
    }

    public boolean isValid() {
        // max 45 characters
        // 1 musical piece
        if (this.target.equals(WishTargetType.DATE)) {
            return this.validateDateWish();
        } else if (this.target.equals(WishTargetType.DUTY)) {
            return this.validateDutyWish();
        }

        return false;
    }

    private boolean validateDateWish() {
        return false;
    }
    private boolean validateDutyWish() {
        return false;
    }
}
