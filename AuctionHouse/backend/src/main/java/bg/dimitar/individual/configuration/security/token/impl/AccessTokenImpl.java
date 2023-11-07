package bg.dimitar.individual.configuration.security.token.impl;

import bg.dimitar.individual.configuration.security.token.AccessToken;
import bg.dimitar.individual.persistance.entity.UserEntity;
import lombok.Getter;

@Getter
public class AccessTokenImpl implements AccessToken {
    private final String role;
    private final Long subject;

    public AccessTokenImpl(UserEntity user) {
        role = user.getRole();
        subject = user.getId();
    }
    public AccessTokenImpl(String role, Long userID) {
        this.role = role;
        this.subject = userID;
    }
}
