package bg.dimitar.individual.configuration.security.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
