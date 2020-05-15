package at.fhv.teamb.symphoniacus.application.dto;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;

/**
 * LoginDTO for User.
 *
 * @author Valentin Goronjic
 */
public class LoginUserDto {

    private final int userId;
    private final String userShortcut;
    private final String password;
    private final String fullName;
    private final DomainUserType type;

    private LoginUserDto(
        int userId,
        String userShortcut,
        String password,
        String fullName,
        DomainUserType type
    ) {
        this.userId = userId;
        this.userShortcut = userShortcut;
        this.password = password;
        this.fullName = fullName;
        this.type = type;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getUserShortcut() {
        return this.userShortcut;
    }

    public String getPassword() {
        return this.password;
    }

    public DomainUserType getType() {
        return this.type;
    }

    public String getFullName() {
        return this.fullName;
    }

    public static class UserDtoBuilder {
        private final int userId;
        private String userShortcut;
        private String password;
        private String fullName;
        private DomainUserType type;

        // we need this to be set
        public UserDtoBuilder(int userId) {
            this.userId = userId;
        }

        public UserDtoBuilder withUserShortcut(String userShortcut) {
            this.userShortcut = userShortcut;
            return this;
        }

        public UserDtoBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserDtoBuilder withType(DomainUserType type) {
            this.type = type;
            return this;
        }

        public UserDtoBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        /**
         * Constructs a new UserDto with the previously set options in the builder.
         *
         * @return Constructed UserDTO.
         */
        public LoginUserDto build() {
            return new LoginUserDto(
                this.userId,
                this.userShortcut,
                this.password,
                this.fullName,
                this.type
            );
        }
    }
}
