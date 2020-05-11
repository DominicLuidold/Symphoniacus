package at.fhv.teamb.symphoniacus.application;

import at.fhv.teamb.symphoniacus.application.dto.UserDto;
import at.fhv.teamb.symphoniacus.application.type.DomainUserType;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(userId, result.getUserId(), "UserId should be the same");
        Assertions.assertEquals(
            userShortcut,
            result.getUserShortcut(),
            "userShortcut should be the same"
        );
        Assertions.assertEquals(
            password,
            result.getPassword(),
            "password should be the same"
        );
        Assertions.assertEquals(
            fullName,
            result.getFullName(),
            "fullName should be the same"
        );
        Assertions.assertEquals(
            type,
            result.getType(),
            "type should be the same"
        );
    }

}
