package dev.z.blog.infra;

import java.util.concurrent.TimeUnit;

public interface Lock {

    void lock();

    boolean tryLock();

    boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException;

    void unlock();
}
