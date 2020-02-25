package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.GuardedBy;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

@GwtIncompatible
final class SequentialExecutor implements Executor {
    /* access modifiers changed from: private */
    public static final Logger log = Logger.getLogger(SequentialExecutor.class.getName());
    /* access modifiers changed from: private */
    @GuardedBy("queue")
    public final Deque<Runnable> queue = new ArrayDeque();
    private final Executor executor;
    private final QueueWorker worker = new QueueWorker();
    /* access modifiers changed from: private */
    @GuardedBy("queue")
    public WorkerRunningState workerRunningState = WorkerRunningState.IDLE;
    @GuardedBy("queue")
    private long workerRunCount = 0;

    SequentialExecutor(Executor executor2) {
        this.executor = (Executor) Preconditions.checkNotNull(executor2);
    }

    static /* synthetic */ long access$308(SequentialExecutor x0) {
        long j = x0.workerRunCount;
        x0.workerRunCount = 1 + j;
        return j;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0024, code lost:
        r0 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        r9.executor.execute(r9.worker);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0032, code lost:
        if (r9.workerRunningState == com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING) goto L_0x0035;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0035, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0037, code lost:
        if (r0 == false) goto L_0x003a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0039, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x003a, code lost:
        r6 = r9.queue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003c, code lost:
        monitor-enter(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0041, code lost:
        if (r9.workerRunCount != r1) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0047, code lost:
        if (r9.workerRunningState != com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING) goto L_0x004d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0049, code lost:
        r9.workerRunningState = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x004d, code lost:
        monitor-exit(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x004e, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0052, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0057, code lost:
        monitor-enter(r9.queue);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x005c, code lost:
        if (r9.workerRunningState == com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE) goto L_0x0064;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x006d, code lost:
        r0 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0070, code lost:
        if ((r5 instanceof java.util.concurrent.RejectedExecutionException) == false) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x0075, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0077, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute(final java.lang.Runnable r10) {
        /*
            r9 = this;
            com.google.common.base.Preconditions.checkNotNull(r10)
            java.util.Deque<java.lang.Runnable> r0 = r9.queue
            monitor-enter(r0)
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r1 = r9.workerRunningState     // Catch:{ all -> 0x0082 }
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r2 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch:{ all -> 0x0082 }
            if (r1 == r2) goto L_0x007b
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r1 = r9.workerRunningState     // Catch:{ all -> 0x0082 }
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r2 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUED     // Catch:{ all -> 0x0082 }
            if (r1 != r2) goto L_0x0013
            goto L_0x007b
        L_0x0013:
            long r1 = r9.workerRunCount     // Catch:{ all -> 0x0082 }
            com.google.common.util.concurrent.SequentialExecutor$1 r3 = new com.google.common.util.concurrent.SequentialExecutor$1     // Catch:{ all -> 0x0082 }
            r3.<init>(r9, r10)     // Catch:{ all -> 0x0082 }
            java.util.Deque<java.lang.Runnable> r4 = r9.queue     // Catch:{ all -> 0x0082 }
            r4.add(r3)     // Catch:{ all -> 0x0082 }
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r4 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING     // Catch:{ all -> 0x0082 }
            r9.workerRunningState = r4     // Catch:{ all -> 0x0082 }
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            r0 = 1
            r4 = 0
            java.util.concurrent.Executor r5 = r9.executor     // Catch:{ RuntimeException -> 0x0054, Error -> 0x0052 }
            com.google.common.util.concurrent.SequentialExecutor$QueueWorker r6 = r9.worker     // Catch:{ RuntimeException -> 0x0054, Error -> 0x0052 }
            r5.execute(r6)     // Catch:{ RuntimeException -> 0x0054, Error -> 0x0052 }
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r5 = r9.workerRunningState
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r6 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING
            if (r5 == r6) goto L_0x0035
            goto L_0x0036
        L_0x0035:
            r0 = 0
        L_0x0036:
            r5 = r0
            if (r5 == 0) goto L_0x003a
            return
        L_0x003a:
            java.util.Deque<java.lang.Runnable> r6 = r9.queue
            monitor-enter(r6)
            long r7 = r9.workerRunCount     // Catch:{ all -> 0x004f }
            int r0 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r0 != 0) goto L_0x004d
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r0 = r9.workerRunningState     // Catch:{ all -> 0x004f }
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r4 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING     // Catch:{ all -> 0x004f }
            if (r0 != r4) goto L_0x004d
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r0 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUED     // Catch:{ all -> 0x004f }
            r9.workerRunningState = r0     // Catch:{ all -> 0x004f }
        L_0x004d:
            monitor-exit(r6)     // Catch:{ all -> 0x004f }
            return
        L_0x004f:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x004f }
            throw r0
        L_0x0052:
            r5 = move-exception
            goto L_0x0055
        L_0x0054:
            r5 = move-exception
        L_0x0055:
            java.util.Deque<java.lang.Runnable> r6 = r9.queue
            monitor-enter(r6)
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r7 = r9.workerRunningState     // Catch:{ all -> 0x0078 }
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r8 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE     // Catch:{ all -> 0x0078 }
            if (r7 == r8) goto L_0x0064
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r7 = r9.workerRunningState     // Catch:{ all -> 0x0078 }
            com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r8 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.QUEUING     // Catch:{ all -> 0x0078 }
            if (r7 != r8) goto L_0x006d
        L_0x0064:
            java.util.Deque<java.lang.Runnable> r7 = r9.queue     // Catch:{ all -> 0x0078 }
            boolean r7 = r7.removeLastOccurrence(r3)     // Catch:{ all -> 0x0078 }
            if (r7 == 0) goto L_0x006d
            goto L_0x006e
        L_0x006d:
            r0 = 0
        L_0x006e:
            boolean r4 = r5 instanceof java.util.concurrent.RejectedExecutionException     // Catch:{ all -> 0x0078 }
            if (r4 == 0) goto L_0x0076
            if (r0 != 0) goto L_0x0076
            monitor-exit(r6)     // Catch:{ all -> 0x0078 }
            return
        L_0x0076:
            throw r5     // Catch:{ all -> 0x0078 }
        L_0x0078:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0078 }
            throw r0
        L_0x007b:
            java.util.Deque<java.lang.Runnable> r1 = r9.queue     // Catch:{ all -> 0x0082 }
            r1.add(r10)     // Catch:{ all -> 0x0082 }
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            return
        L_0x0082:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0082 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SequentialExecutor.execute(java.lang.Runnable):void");
    }

    enum WorkerRunningState {
        IDLE,
        QUEUING,
        QUEUED,
        RUNNING
    }

    private final class QueueWorker implements Runnable {
        private QueueWorker() {
        }

        public void run() {
            try {
                workOnQueue();
            } catch (Error e) {
                synchronized (SequentialExecutor.this.queue) {
                    WorkerRunningState unused = SequentialExecutor.this.workerRunningState = WorkerRunningState.IDLE;
                    throw e;
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
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0018, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0043, code lost:
            if (r0 == false) goto L_?;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0045, code lost:
            java.lang.Thread.currentThread().interrupt();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0052, code lost:
            r0 = r0 | java.lang.Thread.interrupted();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r3.run();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:49:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0016, code lost:
            if (r0 == false) goto L_?;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void workOnQueue() {
            /*
                r11 = this;
                r0 = 0
                r1 = 0
            L_0x0002:
                com.google.common.util.concurrent.SequentialExecutor r2 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0089 }
                java.util.Deque r2 = r2.queue     // Catch:{ all -> 0x0089 }
                monitor-enter(r2)     // Catch:{ all -> 0x0089 }
                if (r1 != 0) goto L_0x002d
                com.google.common.util.concurrent.SequentialExecutor r3 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0086 }
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r3 = r3.workerRunningState     // Catch:{ all -> 0x0086 }
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r4 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch:{ all -> 0x0086 }
                if (r3 != r4) goto L_0x0020
                monitor-exit(r2)     // Catch:{ all -> 0x0086 }
                if (r0 == 0) goto L_0x001f
                java.lang.Thread r2 = java.lang.Thread.currentThread()
                r2.interrupt()
            L_0x001f:
                return
            L_0x0020:
                com.google.common.util.concurrent.SequentialExecutor r3 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0086 }
                com.google.common.util.concurrent.SequentialExecutor.access$308(r3)     // Catch:{ all -> 0x0086 }
                com.google.common.util.concurrent.SequentialExecutor r3 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0086 }
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r4 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.RUNNING     // Catch:{ all -> 0x0086 }
                com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState unused = r3.workerRunningState = r4     // Catch:{ all -> 0x0086 }
                r1 = 1
            L_0x002d:
                com.google.common.util.concurrent.SequentialExecutor r3 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0086 }
                java.util.Deque r3 = r3.queue     // Catch:{ all -> 0x0086 }
                java.lang.Object r3 = r3.poll()     // Catch:{ all -> 0x0086 }
                java.lang.Runnable r3 = (java.lang.Runnable) r3     // Catch:{ all -> 0x0086 }
                if (r3 != 0) goto L_0x004d
                com.google.common.util.concurrent.SequentialExecutor r4 = com.google.common.util.concurrent.SequentialExecutor.this     // Catch:{ all -> 0x0086 }
                com.google.common.util.concurrent.SequentialExecutor$WorkerRunningState r5 = com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState.IDLE     // Catch:{ all -> 0x0086 }
                com.google.common.util.concurrent.SequentialExecutor.WorkerRunningState unused = r4.workerRunningState = r5     // Catch:{ all -> 0x0086 }
                monitor-exit(r2)     // Catch:{ all -> 0x0086 }
                if (r0 == 0) goto L_0x004c
                java.lang.Thread r2 = java.lang.Thread.currentThread()
                r2.interrupt()
            L_0x004c:
                return
            L_0x004d:
                monitor-exit(r2)     // Catch:{ all -> 0x0086 }
                boolean r2 = java.lang.Thread.interrupted()     // Catch:{ all -> 0x0089 }
                r0 = r0 | r2
                r3.run()     // Catch:{ RuntimeException -> 0x0057 }
                goto L_0x0084
            L_0x0057:
                r9 = move-exception
                java.util.logging.Logger r4 = com.google.common.util.concurrent.SequentialExecutor.log     // Catch:{ all -> 0x0089 }
                java.util.logging.Level r5 = java.util.logging.Level.SEVERE     // Catch:{ all -> 0x0089 }
                java.lang.String r6 = "com.google.common.util.concurrent.SequentialExecutor$QueueWorker"
                java.lang.String r7 = "workOnQueue"
                java.lang.String r2 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x0089 }
                java.lang.String r8 = java.lang.String.valueOf(r2)     // Catch:{ all -> 0x0089 }
                int r8 = r8.length()     // Catch:{ all -> 0x0089 }
                int r8 = r8 + 35
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x0089 }
                r10.<init>(r8)     // Catch:{ all -> 0x0089 }
                java.lang.String r8 = "Exception while executing runnable "
                r10.append(r8)     // Catch:{ all -> 0x0089 }
                r10.append(r2)     // Catch:{ all -> 0x0089 }
                java.lang.String r8 = r10.toString()     // Catch:{ all -> 0x0089 }
                r4.logp(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0089 }
            L_0x0084:
                goto L_0x0002
            L_0x0086:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0086 }
                throw r3     // Catch:{ all -> 0x0089 }
            L_0x0089:
                r2 = move-exception
                if (r0 == 0) goto L_0x0093
                java.lang.Thread r3 = java.lang.Thread.currentThread()
                r3.interrupt()
            L_0x0093:
                throw r2
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SequentialExecutor.QueueWorker.workOnQueue():void");
        }
    }
}
