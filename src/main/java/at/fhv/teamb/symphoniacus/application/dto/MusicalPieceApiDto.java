package at.fhv.teamb.symphoniacus.application.dto;

public class MusicalPieceApiDto {
    private Integer musicalPieceId;
    private String name;

    public MusicalPieceApiDto(){}

    public MusicalPieceApiDto(Integer musicalPieceId, String name) {
        this.musicalPieceId = musicalPieceId;
        this.name = name;
    }

    public Integer getMusicalPieceId() {
        return this.musicalPieceId;
    }

    public void setMusicalPieceId(Integer musicalPieceId) {
        this.musicalPieceId = musicalPieceId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
