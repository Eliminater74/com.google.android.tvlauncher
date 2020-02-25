package com.google.android.exoplayer2.util;

import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

public final class PriorityTaskManager {
    private final Object lock = new Object();
    private final PriorityQueue<Integer> queue = new PriorityQueue<>(10, Collections.reverseOrder());
    private int highestPriority = Integer.MIN_VALUE;

    public void add(int priority) {
        synchronized (this.lock) {
            this.queue.add(Integer.valueOf(priority));
            this.highestPriority = Math.max(this.highestPriority, priority);
        }
    }

    public void proceed(int priority) throws InterruptedException {
        synchronized (this.lock) {
            while (this.highestPriority != priority) {
                this.lock.wait();
            }
        }
    }

    public boolean proceedNonBlocking(int priority) {
        boolean z;
        synchronized (this.lock) {
            z = this.highestPriority == priority;
        }
        return z;
    }

    public void proceedOrThrow(int priority) throws PriorityTooLowException {
        synchronized (this.lock) {
            if (this.highestPriority != priority) {
                throw new PriorityTooLowException(priority, this.highestPriority);
            }
        }
    }

    public void remove(int priority) {
        synchronized (this.lock) {
            this.queue.remove(Integer.valueOf(priority));
            this.highestPriority = this.queue.isEmpty() ? Integer.MIN_VALUE : ((Integer) Util.castNonNull(this.queue.peek())).intValue();
            this.lock.notifyAll();
        }
    }

    public static class PriorityTooLowException extends IOException {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public PriorityTooLowException(int r3, int r4) {
            /*
                r2 = this;
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r1 = 60
                r0.<init>(r1)
                java.lang.String r1 = "Priority too low [priority="
                r0.append(r1)
                r0.append(r3)
                java.lang.String r1 = ", highest="
                r0.append(r1)
                r0.append(r4)
                java.lang.String r1 = "]"
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                r2.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.util.PriorityTaskManager.PriorityTooLowException.<init>(int, int):void");
        }
    }
}
