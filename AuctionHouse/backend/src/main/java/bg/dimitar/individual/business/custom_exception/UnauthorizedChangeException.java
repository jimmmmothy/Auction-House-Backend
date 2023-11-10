package bg.dimitar.individual.business.custom_exception;

import lombok.Getter;

@Getter
public class UnauthorizedChangeException extends Exception {
    private final String message;

    public UnauthorizedChangeException(String message) {
        this.message = message;
    }
}
