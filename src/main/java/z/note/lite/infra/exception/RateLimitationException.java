package z.note.lite.infra.exception;

public class RateLimitationException extends RuntimeException {

    public RateLimitationException(String message) {
        super(message);
    }
}
