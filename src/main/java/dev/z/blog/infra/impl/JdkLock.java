package dev.z.blog.infra.impl;

import dev.z.blog.infra.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class JdkLock implements Lock {

    private final ReentrantLock lock;

    public JdkLock() {
        lock = new ReentrantLock();
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public boolean tryLock() {
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return lock.tryLock(timeout, unit);
    }

    @Override
    public void unlock() {
        lock.unlock();
    }
}
