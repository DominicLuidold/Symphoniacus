package at.fhv.teamb.symphoniacus.application.dto;

import at.fhv.teamb.symphoniacus.application.type.DomainUserType;

/**
 * DTO for User.
 *
 * @author Valentin Goronjic
 */
public class UserDto {

    private final int userId;
    private final String userShortcut;
    private final String password;
    private final String fullName;
    private final DomainUserType type;

    private UserDto(
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
        return userId;
    }

    public String getUserShortcut() {
        return userShortcut;
    }

    public String getPassword() {
        return password;
    }

    public DomainUserType getType() {
        return type;
    }

    public String getFullName() {
        return fullName;
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
         * @return Constructed UserDTO.
         */
        public UserDto build() {
            return new UserDto(
                this.userId,
                this.userShortcut,
                this.password,
                this.fullName,
                this.type
            );
        }
    }
}
