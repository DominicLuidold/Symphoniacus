package at.fhv.teamb.symphoniacus.application.dto.wishdtos;

import at.fhv.teamb.symphoniacus.application.dto.MusicalPieceDto;

import java.util.List;

/**
 * API class for {@link DutyWishDto}.
 *
 * @author Tobias Moser
 */
public class DutyWishDto {
    private Integer dutyId;
    private List<MusicalPieceDto> musicalPieces;
    private Boolean forEntireSop;

    public DutyWishDto() {}

    public DutyWishDto(Integer dutyId, Boolean forEntireSop) {
        this.dutyId = dutyId;
        this.forEntireSop = forEntireSop;
    }

    public void setDutyId(Integer dutyId) {
        this.dutyId = dutyId;
    }

    public void setForEntireSop(Boolean forEntireSop) {
        this.forEntireSop = forEntireSop;
    }

    public Integer getDutyId() {
        return dutyId;
    }

    public Boolean getForEntireSop() {
        return forEntireSop;
    }

    public List<MusicalPieceDto> getMusicalPieces() {
        return musicalPieces;
    }

    public void setMusicalPieces(List<MusicalPieceDto> musicalPieces) {
        this.musicalPieces = musicalPieces;
    }
}
