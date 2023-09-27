package z.note.lite.service.api;

import java.util.concurrent.ThreadLocalRandom;

public interface CaptchaService {

    default String code() {
        return String.format("%04d", ThreadLocalRandom.current().nextInt(0, 10000));
    }

    void produce(String key);

}


