package bg.dimitar.individual.business.custom_exception;

import lombok.Getter;

@Getter
public class InvalidRegistrationException extends Exception {
    private final String message;

    public InvalidRegistrationException(String message) {
        this.message = message;
    }
}