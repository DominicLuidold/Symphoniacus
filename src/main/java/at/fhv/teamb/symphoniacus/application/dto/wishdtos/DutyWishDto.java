package at.fhv.teamb.symphoniacus.application.dto.wishdtos;

import java.util.HashMap;
import java.util.Map;

/**
 * API class for {@link DutyWishDto}.
 *
 * @author Tobias Moser
 */
public class DutyWishDto {
    private Integer dutyId;
    private Map<Integer, String> musicalPieces;
    private Boolean forEntireSop;

    public DutyWishDto() {}

    public DutyWishDto(Integer dutyId, Boolean forEntireSop) {
        this.dutyId = dutyId;
        this.forEntireSop = forEntireSop;
    }

    /**
     * Add a muscialPiece to a Dutywish.
     *
     * @param musicalPieceId to add
     */
    public void addMusicalPiece(Integer musicalPieceId, String name) {
        if (musicalPieces == null) {
            musicalPieces = new HashMap<>();
        }
        musicalPieces.put(musicalPieceId, name);
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public void setMusicalPieces(Map<Integer, String> musicalPieces) {
        this.musicalPieces = musicalPieces;
    }

    public void setForEntireSop(Boolean forEntireSop) {
        this.forEntireSop = forEntireSop;
    }

    public Integer getDutyId() {
        return dutyId;
    }

    public Map<Integer, String> getMusicalPieces() {
        return musicalPieces;
    }

    public Boolean getForEntireSop() {
        return forEntireSop;
    }
}
