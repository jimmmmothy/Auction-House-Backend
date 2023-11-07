package bg.dimitar.individual.business.customException;

import lombok.Getter;

@Getter
public class InvalidRegistrationException extends Exception {
    private final String message;

    public InvalidRegistrationException(String message) {
        this.message = message;
    }
}