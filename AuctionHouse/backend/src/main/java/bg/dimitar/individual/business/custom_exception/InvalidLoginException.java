package bg.dimitar.individual.business.custom_exception;

import lombok.Getter;

@Getter
public class InvalidLoginException extends Exception {
    private final String message;

    public InvalidLoginException(String message) {
        this.message = message;
    }
}
