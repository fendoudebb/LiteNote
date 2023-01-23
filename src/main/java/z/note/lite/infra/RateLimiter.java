package z.note.lite.infra;

public interface RateLimiter {

    void latch(String flag);

}
