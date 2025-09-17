package in.ashar.action_service.exception;

public class CannotChangeOrderException extends RuntimeException {
    public CannotChangeOrderException(String message) {
        super(message);
    }
}
