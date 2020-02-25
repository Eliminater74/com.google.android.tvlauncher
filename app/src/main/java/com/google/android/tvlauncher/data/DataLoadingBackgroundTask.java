package com.google.android.tvlauncher.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.annotation.WorkerThread;

import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@VisibleForTesting(otherwise = 3)
public class DataLoadingBackgroundTask implements Runnable {
    @VisibleForTesting
    static final int MAX_POOL_SIZE = 15;
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_THREAD_POOL_SIZE = Math.min(CPU_COUNT - 1, 2);
    private static final boolean DEBUG = false;
    private static final int MAX_THREAD_POOL_SIZE = Math.max(CPU_COUNT - 1, 1);
    private static final Object POOL_SYNC = new Object();
    private static final String TAG = "DLBackgroundTask";
    private static final int THREAD_KEEP_ALIVE_SECONDS = 5;
    private static final int WORK_QUEUE_CAPACITY = 256;
    private static Executor sExecutor = sDefaultExecutor;
    private static InternalHandler sHandler = new InternalHandler();
    private static DataLoadingBackgroundTask sPool;
    private static int sPoolSize = 0;
    private static BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue(256);
    private static final Executor sDefaultExecutor = new ThreadPoolExecutor(CORE_THREAD_POOL_SIZE, MAX_THREAD_POOL_SIZE, 5, TimeUnit.SECONDS, sWorkQueue);
    private volatile Callbacks mCallbacks;
    private volatile boolean mCanceled;
    private volatile ContentResolver mContentResolver;
    private volatile Object mExtraParam;
    private volatile Object mExtraResult;
    private DataLoadingBackgroundTask mNext;
    private volatile String[] mProjection;
    private boolean mRecycled;
    private volatile Cursor mResult;
    private volatile String mSelection;
    private volatile String[] mSelectionArgs;
    private volatile String[] mSingleSelectionArg;
    private volatile String mSortOrder;
    private volatile long mTag;
    private volatile Uri mUri;

    private DataLoadingBackgroundTask(Context context) {
        this.mContentResolver = context.getContentResolver();
    }

    @VisibleForTesting
    static DataLoadingBackgroundTask getPool() {
        return sPool;
    }

    @VisibleForTesting
    static int getPoolSize() {
        return sPoolSize;
    }

    @VisibleForTesting
    static void clearPool() {
        sPool = null;
        sPoolSize = 0;
    }

    @VisibleForTesting
    static void setExecutor(Executor executor) {
        sExecutor = executor;
    }

    @VisibleForTesting
    static void resetExecutor() {
        sExecutor = sDefaultExecutor;
    }

    @NonNull
    static DataLoadingBackgroundTask obtain(@NonNull Context context) {
        synchronized (POOL_SYNC) {
            if (sPool == null) {
                return new DataLoadingBackgroundTask(context.getApplicationContext());
            }
            DataLoadingBackgroundTask t = sPool;
            sPool = t.mNext;
            t.mNext = null;
            t.mRecycled = false;
            sPoolSize--;
            return t;
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void recycle() {
        synchronized (POOL_SYNC) {
            if (sPoolSize < 15 && sPool != this && this.mNext == null) {
                resetFields();
                this.mNext = sPool;
                sPool = this;
                sPoolSize++;
            }
            this.mRecycled = true;
        }
    }

    private void resetFields() {
        this.mCanceled = false;
        this.mTag = 0;
        this.mCallbacks = null;
        this.mUri = null;
        this.mProjection = null;
        this.mSelection = null;
        this.mSelectionArgs = null;
        this.mSortOrder = null;
        this.mExtraParam = null;
        this.mResult = null;
        this.mExtraResult = null;
    }

    /* access modifiers changed from: package-private */
    public void execute() {
        checkRequiredFields();
        try {
            sExecutor.execute(this);
        } catch (RejectedExecutionException ex) {
            this.mCallbacks.onTaskFailed(this, ex);
            recycle();
        }
    }

    private void checkRequiredFields() {
        if (this.mRecycled) {
            throw new IllegalStateException("Can't execute after been recycled. Use DataLoadingBackgroundTask.obtain(Context) to get a new task");
        } else if (this.mCallbacks == null) {
            throw new IllegalArgumentException("Callbacks must not be null");
        } else if (this.mUri == null) {
            throw new IllegalArgumentException("Uri must not be null");
        }
    }

    /* access modifiers changed from: package-private */
    public void cancel() {
        this.mCanceled = true;
    }

    public void run() {
        Process.setThreadPriority(10);
        if (isCanceled()) {
            postFinish();
            return;
        }
        try {
            performQuery();
            Binder.flushPendingCommands();
            if (isCanceled()) {
                postFinish();
                return;
            }
            this.mCallbacks.onTaskPostProcess(this);
            postFinish();
        } catch (Throwable t) {
            throw new RuntimeException("An error occurred while executing ContentResolver query", t);
        }
    }

    private void postFinish() {
        sHandler.obtainMessage(0, this).sendToTarget();
    }

    /* access modifiers changed from: private */
    @MainThread
    public void finish() {
        if (isCanceled()) {
            this.mCallbacks.onTaskCanceled(this);
        } else {
            this.mCallbacks.onTaskCompleted(this);
        }
        recycle();
    }

    @WorkerThread
    private void performQuery() {
        this.mResult = this.mContentResolver.query(this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder);
        if (this.mResult != null) {
            this.mResult.getCount();
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isCanceled() {
        return this.mCanceled;
    }

    /* access modifiers changed from: package-private */
    public long getTag() {
        return this.mTag;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setTag(long tag) {
        this.mTag = tag;
        return this;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Callbacks getCallbacks() {
        return this.mCallbacks;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setCallbacks(@NonNull Callbacks callbacks) {
        if (callbacks != null) {
            this.mCallbacks = callbacks;
            return this;
        }
        throw new IllegalArgumentException("Callbacks must not be null");
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public Uri getUri() {
        return this.mUri;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setUri(@NonNull Uri uri) {
        if (uri != null) {
            this.mUri = uri;
            return this;
        }
        throw new IllegalArgumentException("Uri must not be null");
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String[] getProjection() {
        return this.mProjection;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setProjection(@Nullable String[] projection) {
        this.mProjection = projection;
        return this;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getSelection() {
        return this.mSelection;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setSelection(@Nullable String selection) {
        this.mSelection = selection;
        return this;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String[] getSelectionArgs() {
        return this.mSelectionArgs;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setSelectionArgs(@Nullable String[] args) {
        this.mSelectionArgs = args;
        return this;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setSelectionArg(@Nullable String arg) {
        if (this.mSingleSelectionArg == null) {
            this.mSingleSelectionArg = new String[1];
        }
        this.mSingleSelectionArg[0] = arg;
        this.mSelectionArgs = this.mSingleSelectionArg;
        return this;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public String getSortOrder() {
        return this.mSortOrder;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setSortOrder(@Nullable String sortOrder) {
        this.mSortOrder = sortOrder;
        return this;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Object getExtraParam() {
        return this.mExtraParam;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setExtraParam(@Nullable Object extraParam) {
        this.mExtraParam = extraParam;
        return this;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Cursor getResult() {
        return this.mResult;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Object getExtraResult() {
        return this.mExtraResult;
    }

    /* access modifiers changed from: package-private */
    public DataLoadingBackgroundTask setExtraResult(@Nullable Object extra) {
        this.mExtraResult = extra;
        return this;
    }

    public String toString() {
        long j = this.mTag;
        String valueOf = String.valueOf(this.mUri);
        String str = this.mSelection;
        String arrays = Arrays.toString(this.mSelectionArgs);
        String str2 = this.mSortOrder;
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 106 + String.valueOf(str).length() + String.valueOf(arrays).length() + String.valueOf(str2).length());
        sb.append("DataLoadingBackgroundTask{mTag=");
        sb.append(j);
        sb.append(", mUri=");
        sb.append(valueOf);
        sb.append(", mSelection='");
        sb.append(str);
        sb.append('\'');
        sb.append(", mSelectionArgs=");
        sb.append(arrays);
        sb.append(", mSortOrder='");
        sb.append(str2);
        sb.append('\'');
        sb.append('}');
        return sb.toString();
    }

    interface Callbacks {
        @MainThread
        void onTaskCanceled(DataLoadingBackgroundTask dataLoadingBackgroundTask);

        @MainThread
        void onTaskCompleted(DataLoadingBackgroundTask dataLoadingBackgroundTask);

        @MainThread
        void onTaskFailed(DataLoadingBackgroundTask dataLoadingBackgroundTask, Throwable th);

        @WorkerThread
        void onTaskPostProcess(DataLoadingBackgroundTask dataLoadingBackgroundTask);
    }

    private static class InternalHandler extends Handler {
        InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message msg) {
            ((DataLoadingBackgroundTask) msg.obj).finish();
        }
    }
}
