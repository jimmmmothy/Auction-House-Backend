package bg.dimitar.individual.business.custom_exception;

import lombok.Getter;

@Getter

public class EmailInUseException extends Exception {
    private final String message;

    public EmailInUseException() {
        this.message = "Email is already in use";
    }
}
