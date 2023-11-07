package bg.dimitar.individual.configuration.security.token;

public interface AccessToken {
    String getRole();
    Long getSubject();
}
