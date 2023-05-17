package rainer.pawel.elevator.system.domain.exception;

public class ElevatorSystemException extends RuntimeException {

    public ElevatorSystemException(String message) {
        super(message);
    }

    public static ElevatorSystemException of(String message, Object... args) {
        return new ElevatorSystemException(String.format(message, args));
    }
}
