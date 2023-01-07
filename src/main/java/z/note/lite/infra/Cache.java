package z.note.lite.infra;

import java.util.concurrent.TimeUnit;

public interface Cache {

    Object get(Object key);

    void put(Object key, Object value);

    void put(Object key, Object value, long expireTime, TimeUnit timeUnit);

    void remove(Object key);

    interface Prefix {

        String REPLAY_ATTACKS = "ReplayAttacks_";

        String CAPTCHA = "Captcha_";

    }

}
