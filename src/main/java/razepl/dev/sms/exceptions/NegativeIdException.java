package razepl.dev.sms.exceptions;

public class NegativeIdException extends IllegalArgumentException {
    public NegativeIdException(String message) {
        super(message);
    }
}
