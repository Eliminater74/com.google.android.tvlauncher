package com.google.android.libraries.performance.primes;

import android.animation.TimeAnimator;
import android.annotation.TargetApi;

import com.google.android.libraries.stitch.util.ThreadUtil;

import java.util.concurrent.CountDownLatch;

@TargetApi(16)
final class JankEvent {
    /* access modifiers changed from: private */
    public final TimeAnimator timeAnimator = new TimeAnimator();
    /* access modifiers changed from: private */
    public long maxRenderTimeMs;
    private long elapsedTimeMs;
    private int jankyFrameCount;
    private int renderedFrameCount;

    JankEvent(final int maxAcceptedFrameRenderTimeMs) {
        final int minAcceptedFrameRenderTimeMs = maxAcceptedFrameRenderTimeMs - 1;
        this.timeAnimator.setTimeListener(new TimeAnimator.TimeListener() {
            public void onTimeUpdate(TimeAnimator animation, long totalTime, long deltaTime) {
                if (deltaTime >= ((long) minAcceptedFrameRenderTimeMs)) {
                    if (deltaTime > ((long) maxAcceptedFrameRenderTimeMs)) {
                        JankEvent.access$008(JankEvent.this);
                    }
                    JankEvent.access$108(JankEvent.this);
                    JankEvent.access$214(JankEvent.this, deltaTime);
                    JankEvent jankEvent = JankEvent.this;
                    long unused = jankEvent.maxRenderTimeMs = Math.max(deltaTime, jankEvent.maxRenderTimeMs);
                }
            }
        });
        start();
    }

    static /* synthetic */ int access$008(JankEvent x0) {
        int i = x0.jankyFrameCount;
        x0.jankyFrameCount = i + 1;
        return i;
    }

    static /* synthetic */ int access$108(JankEvent x0) {
        int i = x0.renderedFrameCount;
        x0.renderedFrameCount = i + 1;
        return i;
    }

    static /* synthetic */ long access$214(JankEvent x0, long x1) {
        long j = x0.elapsedTimeMs + x1;
        x0.elapsedTimeMs = j;
        return j;
    }

    private void start() {
        if (ThreadUtil.isMainThread()) {
            this.timeAnimator.start();
        } else {
            ThreadUtil.postOnUiThread(new Runnable() {
                public void run() {
                    JankEvent.this.timeAnimator.start();
                }
            });
        }
    }

    /* access modifiers changed from: package-private */
    public void stop() {
        if (this.timeAnimator != null) {
            if (ThreadUtil.isMainThread()) {
                this.timeAnimator.end();
                return;
            }
            final CountDownLatch signal = new CountDownLatch(1);
            ThreadUtil.postOnUiThread(new Runnable() {
                public void run() {
                    try {
                        JankEvent.this.timeAnimator.end();
                    } finally {
                        signal.countDown();
                    }
                }
            });
            try {
                signal.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int getJankyFrameCount() {
        return this.jankyFrameCount;
    }

    /* access modifiers changed from: package-private */
    public int getRenderedFrameCount() {
        return this.renderedFrameCount;
    }

    /* access modifiers changed from: package-private */
    public int getMaxRenderTimeMs() {
        return (int) this.maxRenderTimeMs;
    }

    /* access modifiers changed from: package-private */
    public int getElapsedTimeMs() {
        return (int) this.elapsedTimeMs;
    }
}
