package bg.dimitar.individual.configuration.security.token;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
