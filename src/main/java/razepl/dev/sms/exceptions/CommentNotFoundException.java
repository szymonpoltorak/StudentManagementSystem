package razepl.dev.sms.exceptions;

public class CommentNotFoundException extends IllegalArgumentException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
