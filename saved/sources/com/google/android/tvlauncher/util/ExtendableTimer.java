package com.google.android.tvlauncher.util;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.VisibleForTesting;

public class ExtendableTimer {
    private static final boolean DEBUG = false;
    private static final int MAXIMUM_TIMEOUT_MSG = 2;
    private static final int MAX_POOL_SIZE = 10;
    private static final Object POOL_SYNC = new Object();
    private static final String TAG = "ExtendableTimer";
    private static final int TIMEOUT_MSG = 1;
    private static ExtendableTimer sPool;
    private static int sPoolSize = 0;
    private final InternalHandler mHandler = new InternalHandler();
    private long mId;
    private Listener mListener;
    private long mMaximumTimeoutMillis;
    private ExtendableTimer mNext;
    private boolean mStarted;
    private long mTimeoutMillis;

    public interface Listener {
        void onTimerFired(ExtendableTimer extendableTimer);
    }

    @VisibleForTesting
    static ExtendableTimer getPool() {
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

    public static ExtendableTimer obtain() {
        synchronized (POOL_SYNC) {
            if (sPool == null) {
                return new ExtendableTimer();
            }
            ExtendableTimer t = sPool;
            sPool = t.mNext;
            t.mNext = null;
            sPoolSize--;
            return t;
        }
    }

    public void recycle() {
        synchronized (POOL_SYNC) {
            if (sPoolSize < 10 && sPool != this && this.mNext == null) {
                stopTimers();
                resetFields();
                this.mNext = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }

    private void resetFields() {
        this.mId = 0;
        this.mTimeoutMillis = 0;
        this.mMaximumTimeoutMillis = 0;
        this.mListener = null;
        this.mStarted = false;
    }

    public void start() {
        checkRequiredFields();
        if (this.mStarted) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageDelayed(1, this.mTimeoutMillis);
            return;
        }
        this.mStarted = true;
        this.mHandler.sendEmptyMessageDelayed(1, this.mTimeoutMillis);
        this.mHandler.sendEmptyMessageDelayed(2, this.mMaximumTimeoutMillis);
    }

    public void cancel() {
        stopTimers();
    }

    private void checkRequiredFields() {
        long j = this.mTimeoutMillis;
        if (j > 0) {
            long j2 = this.mMaximumTimeoutMillis;
            if (j2 > 0) {
                if (j2 <= j) {
                    throw new IllegalArgumentException("Maximum timeout must be larger than timeout");
                } else if (this.mListener == null) {
                    throw new IllegalArgumentException("Listener must not be null");
                } else {
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Both timeout and maximum timeout must be provided");
    }

    private void stopTimers() {
        if (this.mStarted) {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mStarted = false;
        }
    }

    @VisibleForTesting
    public void fireTimer() {
        stopTimers();
        this.mListener.onTimerFired(this);
    }

    public long getId() {
        return this.mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public long getTimeout() {
        return this.mTimeoutMillis;
    }

    public void setTimeout(long timeoutMillis) {
        this.mTimeoutMillis = timeoutMillis;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public long getMaximumTimeout() {
        return this.mMaximumTimeoutMillis;
    }

    public void setMaximumTimeout(long timeoutMillis) {
        this.mMaximumTimeoutMillis = timeoutMillis;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public Listener getListener() {
        return this.mListener;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
        if (this.mListener == null) {
            cancel();
        }
    }

    public boolean isStarted() {
        return this.mStarted;
    }

    @SuppressLint({"HandlerLeak"})
    private class InternalHandler extends Handler {
        InternalHandler() {
            super(Looper.getMainLooper());
        }

        public void handleMessage(Message msg) {
            ExtendableTimer.this.fireTimer();
        }
    }
}
