package com.google.android.libraries.performance.primes.leak;

import android.support.annotation.VisibleForTesting;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.leak.LeakWatcherThread;
import com.google.android.libraries.stitch.util.Preconditions;
import java.io.File;

public class LeakWatcher {
    private static final String TAG = "LeakWatcher";
    private LeakListener leakListener;
    private LeakWatcherThread leakWatcherThread;
    private final LeakWatcherThread.LeakWatcherThreadFactory leakWatcherThreadFactory;
    private boolean started;

    public LeakWatcher(boolean quantifyLeakSizeEnabled) {
        this(new LeakWatcherThread.LeakWatcherThreadFactory(quantifyLeakSizeEnabled));
    }

    @VisibleForTesting
    LeakWatcher(LeakWatcherThread.LeakWatcherThreadFactory leakWatcherThreadFactory2) {
        this.started = false;
        this.leakWatcherThreadFactory = (LeakWatcherThread.LeakWatcherThreadFactory) Preconditions.checkNotNull(leakWatcherThreadFactory2);
    }

    public void setLeakListener(LeakListener leakListener2) {
        this.leakListener = leakListener2;
    }

    public synchronized void watch(Object object, String name) {
        if (this.started) {
            if (this.leakWatcherThread == null) {
                this.leakWatcherThread = this.leakWatcherThreadFactory.newInstance(this.leakListener);
                this.leakWatcherThread.start();
                PrimesLog.m46d(TAG, "Starting leak watcher thread.", new Object[0]);
            }
            this.leakWatcherThread.watch(object, name);
        }
    }

    public synchronized void start() {
        this.started = true;
    }

    public boolean isStarted() {
        return this.started;
    }

    public synchronized void stop() {
        if (this.started) {
            this.started = false;
            if (this.leakWatcherThread != null) {
                this.leakWatcherThread.interrupt();
                this.leakWatcherThread = null;
            }
            PrimesLog.m46d(TAG, "Stopping leak watcher thread.", new Object[0]);
        }
    }

    public boolean scheduleHeapDumpAndAnalysis(File hprofFile) {
        LeakWatcherThread leakWatcherThread2 = this.leakWatcherThread;
        if (leakWatcherThread2 != null) {
            return leakWatcherThread2.scheduleHeapDumpAndAnalysis(hprofFile);
        }
        return false;
    }
}
