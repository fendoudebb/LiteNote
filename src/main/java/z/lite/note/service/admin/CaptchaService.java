package z.lite.note.service.admin;

import java.util.concurrent.ThreadLocalRandom;

public interface CaptchaService {

    default String verificationCode() {
        return String.format("%04d", ThreadLocalRandom.current().nextInt(0, 10000));
    }

    void produce(String username, String code);

    default void store(String code) {

    }

}


