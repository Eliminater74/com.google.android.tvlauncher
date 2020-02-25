package com.google.common.util.concurrent;

import android.support.p001v4.app.NotificationCompat;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import com.google.errorprone.annotations.concurrent.GuardedBy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;

@GwtIncompatible
final class ListenerCallQueue<L> {
    /* access modifiers changed from: private */
    public static final Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
    private final List<PerListenerQueue<L>> listeners = Collections.synchronizedList(new ArrayList());

    ListenerCallQueue() {
    }

    public void addListener(L listener, Executor executor) {
        Preconditions.checkNotNull(listener, "listener");
        Preconditions.checkNotNull(executor, "executor");
        this.listeners.add(new PerListenerQueue(listener, executor));
    }

    public void enqueue(Event<L> event) {
        enqueueHelper(event, event);
    }

    public void enqueue(Event<L> event, String label) {
        enqueueHelper(event, label);
    }

    private void enqueueHelper(Event<L> event, Object label) {
        Preconditions.checkNotNull(event, NotificationCompat.CATEGORY_EVENT);
        Preconditions.checkNotNull(label, "label");
        synchronized (this.listeners) {
            for (PerListenerQueue<L> queue : this.listeners) {
                queue.add(event, label);
            }
        }
    }

    public void dispatch() {
        for (int i = 0; i < this.listeners.size(); i++) {
            this.listeners.get(i).dispatch();
        }
    }

    interface Event<L> {
        void call(Object obj);
    }

    private static final class PerListenerQueue<L> implements Runnable {
        final Executor executor;
        @GuardedBy("this")
        final Queue<Object> labelQueue = Queues.newArrayDeque();
        final L listener;
        @GuardedBy("this")
        final Queue<Event<L>> waitQueue = Queues.newArrayDeque();
        @GuardedBy("this")
        boolean isThreadScheduled;

        PerListenerQueue(L listener2, Executor executor2) {
            this.listener = Preconditions.checkNotNull(listener2);
            this.executor = (Executor) Preconditions.checkNotNull(executor2);
        }

        /* access modifiers changed from: package-private */
        public synchronized void add(Event<L> event, Object label) {
            this.waitQueue.add(event);
            this.labelQueue.add(label);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
         arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.RuntimeException]
         candidates:
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
        /* access modifiers changed from: package-private */
        public void dispatch() {
            boolean scheduleEventRunner = false;
            synchronized (this) {
                if (!this.isThreadScheduled) {
                    this.isThreadScheduled = true;
                    scheduleEventRunner = true;
                }
            }
            if (scheduleEventRunner) {
                try {
                    this.executor.execute(this);
                } catch (RuntimeException e) {
                    synchronized (this) {
                        this.isThreadScheduled = false;
                        Logger access$000 = ListenerCallQueue.logger;
                        Level level = Level.SEVERE;
                        String valueOf = String.valueOf(this.listener);
                        String valueOf2 = String.valueOf(this.executor);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 42 + String.valueOf(valueOf2).length());
                        sb.append("Exception while running callbacks for ");
                        sb.append(valueOf);
                        sb.append(" on ");
                        sb.append(valueOf2);
                        access$000.logp(level, "com.google.common.util.concurrent.ListenerCallQueue$PerListenerQueue", "dispatch", sb.toString(), (Throwable) e);
                        throw e;
                    }
                }
            }
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void}
         arg types: [java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.RuntimeException]
         candidates:
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.Throwable, java.util.function.Supplier<java.lang.String>):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object[]):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Object):void}
          ClspMth{java.util.logging.Logger.logp(java.util.logging.Level, java.lang.String, java.lang.String, java.lang.String, java.lang.Throwable):void} */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x001e, code lost:
            monitor-enter(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r13.isThreadScheduled = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
            monitor-exit(r13);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
            r2.call(r13.listener);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x001c, code lost:
            if (0 == 0) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r13 = this;
                r0 = 1
            L_0x0001:
                r1 = 0
                monitor-enter(r13)     // Catch:{ all -> 0x0077 }
                boolean r2 = r13.isThreadScheduled     // Catch:{ all -> 0x0074 }
                com.google.common.base.Preconditions.checkState(r2)     // Catch:{ all -> 0x0074 }
                java.util.Queue<com.google.common.util.concurrent.ListenerCallQueue$Event<L>> r2 = r13.waitQueue     // Catch:{ all -> 0x0074 }
                java.lang.Object r2 = r2.poll()     // Catch:{ all -> 0x0074 }
                com.google.common.util.concurrent.ListenerCallQueue$Event r2 = (com.google.common.util.concurrent.ListenerCallQueue.Event) r2     // Catch:{ all -> 0x0074 }
                java.util.Queue<java.lang.Object> r3 = r13.labelQueue     // Catch:{ all -> 0x0074 }
                java.lang.Object r3 = r3.poll()     // Catch:{ all -> 0x0074 }
                if (r2 != 0) goto L_0x0027
                r13.isThreadScheduled = r1     // Catch:{ all -> 0x0074 }
                r0 = 0
                monitor-exit(r13)     // Catch:{ all -> 0x0074 }
                if (r0 == 0) goto L_0x0026
                monitor-enter(r13)
                r13.isThreadScheduled = r1     // Catch:{ all -> 0x0023 }
                monitor-exit(r13)     // Catch:{ all -> 0x0023 }
                goto L_0x0026
            L_0x0023:
                r1 = move-exception
                monitor-exit(r13)     // Catch:{ all -> 0x0023 }
                throw r1
            L_0x0026:
                return
            L_0x0027:
                monitor-exit(r13)     // Catch:{ all -> 0x0074 }
                L r4 = r13.listener     // Catch:{ RuntimeException -> 0x002e }
                r2.call(r4)     // Catch:{ RuntimeException -> 0x002e }
                goto L_0x0073
            L_0x002e:
                r4 = move-exception
                r10 = r4
                java.util.logging.Logger r5 = com.google.common.util.concurrent.ListenerCallQueue.logger     // Catch:{ all -> 0x0077 }
                java.util.logging.Level r6 = java.util.logging.Level.SEVERE     // Catch:{ all -> 0x0077 }
                java.lang.String r7 = "com.google.common.util.concurrent.ListenerCallQueue$PerListenerQueue"
                java.lang.String r8 = "run"
                L r4 = r13.listener     // Catch:{ all -> 0x0077 }
                java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0077 }
                java.lang.String r9 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0077 }
                java.lang.String r11 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x0077 }
                int r11 = r11.length()     // Catch:{ all -> 0x0077 }
                int r11 = r11 + 37
                java.lang.String r12 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x0077 }
                int r12 = r12.length()     // Catch:{ all -> 0x0077 }
                int r11 = r11 + r12
                java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x0077 }
                r12.<init>(r11)     // Catch:{ all -> 0x0077 }
                java.lang.String r11 = "Exception while executing callback: "
                r12.append(r11)     // Catch:{ all -> 0x0077 }
                r12.append(r4)     // Catch:{ all -> 0x0077 }
                java.lang.String r4 = " "
                r12.append(r4)     // Catch:{ all -> 0x0077 }
                r12.append(r9)     // Catch:{ all -> 0x0077 }
                java.lang.String r9 = r12.toString()     // Catch:{ all -> 0x0077 }
                r5.logp(r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0077 }
            L_0x0073:
                goto L_0x0001
            L_0x0074:
                r2 = move-exception
                monitor-exit(r13)     // Catch:{ all -> 0x0074 }
                throw r2     // Catch:{ all -> 0x0077 }
            L_0x0077:
                r2 = move-exception
                if (r0 == 0) goto L_0x0082
                monitor-enter(r13)
                r13.isThreadScheduled = r1     // Catch:{ all -> 0x007f }
                monitor-exit(r13)     // Catch:{ all -> 0x007f }
                goto L_0x0082
            L_0x007f:
                r1 = move-exception
                monitor-exit(r13)     // Catch:{ all -> 0x007f }
                throw r1
            L_0x0082:
                throw r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ListenerCallQueue.PerListenerQueue.run():void");
        }
    }
}
