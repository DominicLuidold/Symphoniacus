package at.fhv.teamb.symphoniacus.application.dto.wishdtos;

import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceApiDto;

import java.util.ArrayList;
import java.util.List;

/**
 * API class for {@link DutyWishDto}.
 *
 * @author Tobias Moser
 */
public class DutyWishDto {
    private Integer dutyId;
    private List<MusicalPieceApiDto> musicalPieces;
    private Boolean forEntireSop;

    public DutyWishDto() {
        this.musicalPieces = null;
    }

    /**
     * Constructor of DutyWishDto.
     *
     * @param dutyId Duty id of the duty which the wish is set for
     * @param forEntireSop boolean if wish is set for whole Series
     */
    public DutyWishDto(Integer dutyId, Boolean forEntireSop) {
        this.dutyId = dutyId;
        this.forEntireSop = forEntireSop;
        this.musicalPieces = null;
    }

    /**
     * Add a musical Piece to Duty wish.
     */
    public void addMusicalPiece(Integer musicalPieceId, String name) {
        if (musicalPieces == null) {
            musicalPieces = new ArrayList<>();
        }
        musicalPieces.add(new MusicalPieceApiDto(musicalPieceId, name));
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public Integer getDutyId() {
        return this.dutyId;
    }

    public List<MusicalPieceApiDto> getMusicalPieces() {
        return this.musicalPieces;
    }

    public void setMusicalPieces(List<MusicalPieceApiDto> musicalPieces) {
        this.musicalPieces = musicalPieces;
    }

    public void setForEntireSop(Boolean forEntireSop) {
        this.forEntireSop = forEntireSop;
    }

    public Boolean getForEntireSop() {
        return this.forEntireSop;
    }
}
