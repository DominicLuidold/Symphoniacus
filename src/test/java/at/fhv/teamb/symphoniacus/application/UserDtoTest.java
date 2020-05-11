package at.fhv.teamb.symphoniacus.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import at.fhv.teamb.symphoniacus.application.dto.UserDto;
import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link UserDto}.
 *
 * @author Valentin Goronjic
 */
class UserDtoTest {

    @Test
    void userDtoBuilder_shouldReturnAFilledDto() {
        // given: this data
        int userId = -1;
        String userShortcut = "reallyCoolUsername";
        String password = "verySecurePassword";
        String fullName = "theBestFullName";
        DomainUserType type = DomainUserType.DOMAIN_MUSICIAN;

        // When: we create a Dto with this data
        UserDto result = new UserDto.UserDtoBuilder(userId).withUserShortcut(userShortcut)
            .withPassword(password)
            .withFullName(fullName)
            .withType(type)
            .build();

        // Then: all fields should have the same values
        assertEquals(userId, result.getUserId(), "UserId should be the same");
        assertEquals(
            userShortcut,
            result.getUserShortcut(),
            "userShortcut should be the same"
        );
        assertEquals(
            password,
            result.getPassword(),
            "password should be the same"
        );
        assertEquals(
            fullName,
            result.getFullName(),
            "fullName should be the same"
        );
        assertEquals(
            type,
            result.getType(),
            "type should be the same"
        );
    }

}
