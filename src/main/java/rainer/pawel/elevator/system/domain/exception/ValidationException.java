package rainer.pawel.elevator.system.domain.exception;

public class ValidationException extends ElevatorSystemException {

    public ValidationException(String message) {
        super(message);
    }

    public static ValidationException of(String message, Object... args) {
        return new ValidationException(String.format(message, args));
    }
}
