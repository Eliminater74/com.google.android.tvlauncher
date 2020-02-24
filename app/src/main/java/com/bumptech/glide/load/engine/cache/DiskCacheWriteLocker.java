package com.bumptech.glide.load.engine.cache;

import com.bumptech.glide.util.Preconditions;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

final class DiskCacheWriteLocker {
    private final Map<String, WriteLock> locks = new HashMap();
    private final WriteLockPool writeLockPool = new WriteLockPool();

    DiskCacheWriteLocker() {
    }

    /* access modifiers changed from: package-private */
    public void acquire(String safeKey) {
        WriteLock writeLock;
        synchronized (this) {
            writeLock = this.locks.get(safeKey);
            if (writeLock == null) {
                writeLock = this.writeLockPool.obtain();
                this.locks.put(safeKey, writeLock);
            }
            writeLock.interestedThreads++;
        }
        writeLock.lock.lock();
    }

    /* access modifiers changed from: package-private */
    public void release(String safeKey) {
        WriteLock writeLock;
        synchronized (this) {
            writeLock = (WriteLock) Preconditions.checkNotNull(this.locks.get(safeKey));
            if (writeLock.interestedThreads >= 1) {
                writeLock.interestedThreads--;
                if (writeLock.interestedThreads == 0) {
                    WriteLock removed = this.locks.remove(safeKey);
                    if (removed.equals(writeLock)) {
                        this.writeLockPool.offer(removed);
                    } else {
                        String valueOf = String.valueOf(writeLock);
                        String valueOf2 = String.valueOf(removed);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 79 + String.valueOf(valueOf2).length() + String.valueOf(safeKey).length());
                        sb.append("Removed the wrong lock, expected to remove: ");
                        sb.append(valueOf);
                        sb.append(", but actually removed: ");
                        sb.append(valueOf2);
                        sb.append(", safeKey: ");
                        sb.append(safeKey);
                        throw new IllegalStateException(sb.toString());
                    }
                }
            } else {
                int i = writeLock.interestedThreads;
                StringBuilder sb2 = new StringBuilder(String.valueOf(safeKey).length() + 81);
                sb2.append("Cannot release a lock that is not held, safeKey: ");
                sb2.append(safeKey);
                sb2.append(", interestedThreads: ");
                sb2.append(i);
                throw new IllegalStateException(sb2.toString());
            }
        }
        writeLock.lock.unlock();
    }

    private static class WriteLock {
        int interestedThreads;
        final Lock lock = new ReentrantLock();

        WriteLock() {
        }
    }

    private static class WriteLockPool {
        private static final int MAX_POOL_SIZE = 10;
        private final Queue<WriteLock> pool = new ArrayDeque();

        WriteLockPool() {
        }

        /* access modifiers changed from: package-private */
        public WriteLock obtain() {
            WriteLock result;
            synchronized (this.pool) {
                result = this.pool.poll();
            }
            if (result == null) {
                return new WriteLock();
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        public void offer(WriteLock writeLock) {
            synchronized (this.pool) {
                if (this.pool.size() < 10) {
                    this.pool.offer(writeLock);
                }
            }
        }
    }
}
