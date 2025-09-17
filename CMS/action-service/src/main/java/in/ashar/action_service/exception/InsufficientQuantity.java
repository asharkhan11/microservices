package in.ashar.action_service.exception;

public class InsufficientQuantity extends RuntimeException {
    public InsufficientQuantity(String message) {
        super(message);
    }
}
