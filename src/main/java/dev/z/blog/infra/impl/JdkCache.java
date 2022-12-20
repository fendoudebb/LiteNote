package dev.z.blog.infra.impl;

import dev.z.blog.infra.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class JdkCache implements Cache {

    private final Map<Object, Object> cache;

    public JdkCache() {
        cache = new ConcurrentHashMap<>(64);
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
        // TODO jdk cache expire
    }

    @Override
    public void remove(Object key) {
        cache.remove(key);
    }
}
