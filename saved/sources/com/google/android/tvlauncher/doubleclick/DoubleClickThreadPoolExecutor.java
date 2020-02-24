package com.google.android.tvlauncher.doubleclick;

import android.support.annotation.MainThread;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DoubleClickThreadPoolExecutor extends ThreadPoolExecutor {
    private static final String TAG = "DoubleClickThreadPoolExecutor";
    private final ConcurrentMap<Runnable, Boolean> mQueuedOrRunningTasks = new ConcurrentHashMap();

    DoubleClickThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public boolean addTaskAndExecuteIfNeeded(Runnable task) {
        if (this.mQueuedOrRunningTasks.containsKey(task)) {
            return false;
        }
        if (getQueue().remainingCapacity() <= 0) {
            Runnable taskToBeRemoved = getQueue().peek();
            if (!remove(taskToBeRemoved)) {
                Log.e(TAG, "Could not remove the first queued task from working queue");
                return false;
            }
            this.mQueuedOrRunningTasks.remove(taskToBeRemoved);
        }
        execute(task);
        this.mQueuedOrRunningTasks.put(task, true);
        return true;
    }

    /* access modifiers changed from: protected */
    public void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        this.mQueuedOrRunningTasks.remove(r);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Set<Runnable> getQueuedOrRunningTasks() {
        return this.mQueuedOrRunningTasks.keySet();
    }
}
