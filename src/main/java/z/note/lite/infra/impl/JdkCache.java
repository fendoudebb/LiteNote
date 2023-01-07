package z.note.lite.infra.impl;

import z.note.lite.infra.Cache;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Slf4j
public class JdkCache implements Cache {

    private final Map<Object, Object> cache;

    private final DelayQueue<ExpireCache> queue;

    public JdkCache() {
        cache = new ConcurrentHashMap<>(64);
        queue = new DelayQueue<>();

        new Thread(() -> {
            while (true) {
                try {
                    ExpireCache ec = queue.take();
                    log.info("Clean Cache: {}", ec);
                    cache.remove(ec.key, ec.value);
                } catch (Exception e) {
                    log.info("Clean Cache Error: {}", e.getMessage(), e);
                }
            }
        }, "Clean-Cache").start();
    }

    @Override
    public Object get(Object key) {
        return cache.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        cache.putIfAbsent(key, value);
    }

    @Override
    public void put(Object key, Object value, long expireTime, TimeUnit timeUnit) {
        cache.put(key, value);
        queue.offer(new ExpireCache(key, value, expireTime, timeUnit));
    }

    @Override
    public void remove(Object key) {
        cache.remove(key);
    }

    private static class ExpireCache implements Delayed {
        private final Object key;

        private final Object value;

        private final long expireTime;

        private final long fixedExpireTime;

        public ExpireCache(Object key, Object value, long expireTime, TimeUnit timeUnit) {
            this.key = key;
            this.value = value;
            this.expireTime = timeUnit.toSeconds(expireTime);
            this.fixedExpireTime = TimeUnit.NANOSECONDS.convert(expireTime, timeUnit) + System.nanoTime();
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(fixedExpireTime - System.nanoTime(), unit);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.getDelay(TimeUnit.NANOSECONDS), o.getDelay(TimeUnit.NANOSECONDS));
        }

        @Override
        public String toString() {
            return String.format("key=%s, value=%s, expireTime=%ss", key, value, expireTime);
        }
    }
}
