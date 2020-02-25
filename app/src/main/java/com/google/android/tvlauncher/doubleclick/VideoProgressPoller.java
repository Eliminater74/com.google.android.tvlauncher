package com.google.android.tvlauncher.doubleclick;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.google.android.tvlauncher.instantvideo.widget.InstantVideoView;

import java.util.ArrayList;
import java.util.List;

public class VideoProgressPoller implements Handler.Callback {
    @VisibleForTesting
    static final int VIDEO_UPDATE_MSG = 1;
    private static final int STOP_POLLER = 3;
    private static final String TAG = "VideoProgressPoller";
    private static final int VIDEO_ENDED_MSG = 2;
    @VisibleForTesting
    final Handler mHandler;
    private final InstantVideoView mInstantVideoView;
    private boolean mIsTracking = false;
    private List<OnVideoProgressUpdateListener> mOnVideoProgressUpdateListeners = new ArrayList(2);
    private long mRefreshIntervalMillis;
    private long mVideoDurationMillis;

    public VideoProgressPoller(InstantVideoView videoView) {
        this.mInstantVideoView = videoView;
        this.mHandler = new Handler(this);
    }

    public void addVideoProgressUpdateListener(OnVideoProgressUpdateListener listener) {
        if (!this.mOnVideoProgressUpdateListeners.contains(listener)) {
            this.mOnVideoProgressUpdateListeners.add(listener);
        }
    }

    public void removeVideoProgressUpdateListener(OnVideoProgressUpdateListener listener) {
        this.mOnVideoProgressUpdateListeners.remove(listener);
    }

    public void startTracking(long videoDurationMillis) {
        if (this.mIsTracking) {
            long j = this.mVideoDurationMillis;
            StringBuilder sb = new StringBuilder(130);
            sb.append("startTracking was called while the tracking is in progress, last duration: ");
            sb.append(j);
            sb.append(" new duration: ");
            sb.append(videoDurationMillis);
            Log.e(TAG, sb.toString());
            stopTracking();
        }
        this.mIsTracking = true;
        this.mVideoDurationMillis = videoDurationMillis;
        this.mRefreshIntervalMillis = computeRefreshInterval(videoDurationMillis);
        if (this.mRefreshIntervalMillis >= 0) {
            this.mHandler.sendEmptyMessage(1);
            return;
        }
        StringBuilder sb2 = new StringBuilder(81);
        sb2.append("A negative refresh interval obtained from video duration of: ");
        sb2.append(videoDurationMillis);
        throw new IllegalArgumentException(sb2.toString());
    }

    public void onVideoStopped() {
        stopTracking();
    }

    public void onVideoEnded() {
        Handler handler = this.mHandler;
        handler.sendMessageAtFrontOfQueue(Message.obtain(handler, 2));
    }

    private void stopTracking() {
        if (this.mIsTracking) {
            this.mIsTracking = false;
            Handler handler = this.mHandler;
            handler.sendMessageAtFrontOfQueue(Message.obtain(handler, 3));
        }
    }

    /* access modifiers changed from: package-private */
    public long computeRefreshInterval(long videoDurationMillis) {
        return videoDurationMillis / 4;
    }

    public boolean handleMessage(Message msg) {
        int i = msg.what;
        if (i != 1) {
            if (i == 2) {
                notifyProgressUpdateListeners(this.mVideoDurationMillis);
            } else if (i != 3) {
                return false;
            }
            this.mHandler.removeMessages(1);
            return false;
        }
        int currentPosition = this.mInstantVideoView.getCurrentPosition();
        notifyProgressUpdateListeners((long) currentPosition);
        if (((long) currentPosition) >= this.mVideoDurationMillis) {
            return false;
        }
        this.mHandler.sendEmptyMessageDelayed(1, this.mRefreshIntervalMillis);
        return false;
    }

    private void notifyProgressUpdateListeners(long currentProgressMillis) {
        for (OnVideoProgressUpdateListener listener : this.mOnVideoProgressUpdateListeners) {
            listener.onVideoProgressUpdate(currentProgressMillis);
        }
    }

    public interface OnVideoProgressUpdateListener {
        void onVideoProgressUpdate(long j);
    }
}
