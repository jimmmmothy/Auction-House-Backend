package bg.dimitar.individual.business.custom_exception;

import lombok.Getter;

@Getter
public class NotFoundException extends Exception {
    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }
}
