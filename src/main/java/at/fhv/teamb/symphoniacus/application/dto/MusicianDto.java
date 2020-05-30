package at.fhv.teamb.symphoniacus.application.dto;

import at.fhv.teamb.symphoniacus.domain.DutyPosition;
import java.util.List;

/**
 * DTO for a negative Musician
 * @author Theresa Gierer
 */

public class MusicianDto {
    private final int musicianId;
    private final UserDto user;
    private final SectionDto section;
    // private final ContractualObligationDto contractualObligations; not relevant
    // private final List<MusicianRoleDto> musicianRoles; not relevant
    private final List <InstrumentCategoryDto> instrumentCategories;
    private final List <DutyPosition> dutyPositions;
    private final List <DutyWishDto> positiveWishes; //TODO: find out how DutyWishes work.
    private final List <DutyWishDto> negativeDutyWishes;
    private final List <VacationDto> vacations;
    private final List <NegativeDateWishDto> negativeDateWishes;

    private MusicianDto(
        //Todo: fill accordingly
    ) {
        //todo: fill accordingly
    }

    //todo: generate getters

    public static class MusicianDtoBuilder {
        //Todo: fill accordingly

        public MusicianDtoBuilder(int musicianId) {
            this.musicianId = musicianId;
        }

        /**
         * Constructs a new MusicianDto with the previously set options in the builder.
         * @return Constructed MusicianDto.
         */
        public MusicianDto build() {
            return new MusicianDto(
                //todo: fill accordingly
            );
        }
    }
}
