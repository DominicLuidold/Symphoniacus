package at.fhv.teamb.symphoniacus.application.dto.wishdtos;

/**
 * Model class for {@link WishDto}.
 *
 * @author Tobias Moser
 */
public class WishDto<T> {
    private Integer wishId;
    private WishType wishType;
    private WishTargetType target;
    private String status;
    private String reason;
    private T details;


    public WishDto(){}

    /**
     * Constructor for WishDto.
     */
    public WishDto(
            Integer wishId,
            WishType wishType,
            WishTargetType target,
            String status,
            String reason,
            T details
    ) {
        this.wishId = wishId;
        this.wishType = wishType;
        this.target = target;
        this.status = status;
        this.reason = reason;
        this.details = details;
    }

    public void setWishId(Integer wishId) {
        this.wishId = wishId;
    }

    public void setWishType(WishType wishType) {
        this.wishType = wishType;
    }

    public void setTarget(WishTargetType target) {
        this.target = target;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDetails(T details) {
        this.details = details;
    }

    public Integer getWishId() {
        return this.wishId;
    }

    public WishType getWishType() {
        return this.wishType;
    }

    public WishTargetType getTarget() {
        return this.target;
    }

    public String getStatus() {
        return this.status;
    }

    public String getReason() {
        return this.reason;
    }

    public T getDetails() {
        return this.details;
    }



    public static class WishBuilder<T> {
        private Integer wishId;
        private WishType wishtype;
        private WishTargetType target;
        private String status;
        private String reason;
        private T details;

        public WishBuilder<T> withWishId(Integer wishId) {
            this.wishId = wishId;
            return this;
        }

        public WishBuilder<T> withWishType(WishType wishType) {
            this.wishtype = wishType;
            return this;
        }

        public WishBuilder<T> withTarget(WishTargetType target) {
            this.target = target;
            return this;
        }

        public WishBuilder<T> withStatus(String status) {
            this.status = status;
            return this;
        }

        public WishBuilder<T> withReason(String reason) {
            this.reason = reason;
            return this;
        }

        public WishBuilder<T> withDetails(T details) {
            this.details = details;
            return this;
        }

        /**
         * Build a new Wish.
         *
         * @return the new Wish.
         */
        public WishDto<T> build() {
            return new WishDto<T>(
                    this.wishId,
                    this.wishtype,
                    this.target,
                    this.status,
                    this.reason,
                    this.details
                    );
        }

    }
}