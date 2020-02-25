package com.bumptech.glide.load.resource.gif;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.ObjectKey;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

class GifFrameLoader {
    final RequestManager requestManager;
    private final BitmapPool bitmapPool;
    private final List<FrameCallback> callbacks;
    private final GifDecoder gifDecoder;
    private final Handler handler;
    private DelayTarget current;
    private Bitmap firstFrame;
    private boolean isCleared;
    private boolean isLoadPending;
    private boolean isRunning;
    private DelayTarget next;
    @Nullable
    private OnEveryFrameListener onEveryFrameListener;
    private DelayTarget pendingTarget;
    private RequestBuilder<Bitmap> requestBuilder;
    private boolean startFromFirstFrame;
    private Transformation<Bitmap> transformation;

    GifFrameLoader(Glide glide, GifDecoder gifDecoder2, int width, int height, Transformation<Bitmap> transformation2, Bitmap firstFrame2) {
        this(glide.getBitmapPool(), Glide.with(glide.getContext()), gifDecoder2, null, getRequestBuilder(Glide.with(glide.getContext()), width, height), transformation2, firstFrame2);
    }

    GifFrameLoader(BitmapPool bitmapPool2, RequestManager requestManager2, GifDecoder gifDecoder2, Handler handler2, RequestBuilder<Bitmap> requestBuilder2, Transformation<Bitmap> transformation2, Bitmap firstFrame2) {
        this.callbacks = new ArrayList();
        this.requestManager = requestManager2;
        handler2 = handler2 == null ? new Handler(Looper.getMainLooper(), new FrameLoaderCallback()) : handler2;
        this.bitmapPool = bitmapPool2;
        this.handler = handler2;
        this.requestBuilder = requestBuilder2;
        this.gifDecoder = gifDecoder2;
        setFrameTransformation(transformation2, firstFrame2);
    }

    private static RequestBuilder<Bitmap> getRequestBuilder(RequestManager requestManager2, int width, int height) {
        return requestManager2.asBitmap().apply((BaseRequestOptions<?>) ((RequestOptions) ((RequestOptions) RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE).useAnimationPool(true)).skipMemoryCache(true)).override(width, height));
    }

    private static Key getFrameSignature() {
        return new ObjectKey(Double.valueOf(Math.random()));
    }

    /* access modifiers changed from: package-private */
    public void setFrameTransformation(Transformation<Bitmap> transformation2, Bitmap firstFrame2) {
        this.transformation = (Transformation) Preconditions.checkNotNull(transformation2);
        this.firstFrame = (Bitmap) Preconditions.checkNotNull(firstFrame2);
        this.requestBuilder = this.requestBuilder.apply((BaseRequestOptions<?>) new RequestOptions().transform(transformation2));
    }

    /* access modifiers changed from: package-private */
    public Transformation<Bitmap> getFrameTransformation() {
        return this.transformation;
    }

    /* access modifiers changed from: package-private */
    public Bitmap getFirstFrame() {
        return this.firstFrame;
    }

    /* access modifiers changed from: package-private */
    public void subscribe(FrameCallback frameCallback) {
        if (this.isCleared) {
            throw new IllegalStateException("Cannot subscribe to a cleared frame loader");
        } else if (!this.callbacks.contains(frameCallback)) {
            boolean start = this.callbacks.isEmpty();
            this.callbacks.add(frameCallback);
            if (start) {
                start();
            }
        } else {
            throw new IllegalStateException("Cannot subscribe twice in a row");
        }
    }

    /* access modifiers changed from: package-private */
    public void unsubscribe(FrameCallback frameCallback) {
        this.callbacks.remove(frameCallback);
        if (this.callbacks.isEmpty()) {
            stop();
        }
    }

    /* access modifiers changed from: package-private */
    public int getWidth() {
        return getCurrentFrame().getWidth();
    }

    /* access modifiers changed from: package-private */
    public int getHeight() {
        return getCurrentFrame().getHeight();
    }

    /* access modifiers changed from: package-private */
    public int getSize() {
        return this.gifDecoder.getByteSize() + getFrameSize();
    }

    /* access modifiers changed from: package-private */
    public int getCurrentIndex() {
        DelayTarget delayTarget = this.current;
        if (delayTarget != null) {
            return delayTarget.index;
        }
        return -1;
    }

    private int getFrameSize() {
        return Util.getBitmapByteSize(getCurrentFrame().getWidth(), getCurrentFrame().getHeight(), getCurrentFrame().getConfig());
    }

    /* access modifiers changed from: package-private */
    public ByteBuffer getBuffer() {
        return this.gifDecoder.getData().asReadOnlyBuffer();
    }

    /* access modifiers changed from: package-private */
    public int getFrameCount() {
        return this.gifDecoder.getFrameCount();
    }

    /* access modifiers changed from: package-private */
    public int getLoopCount() {
        return this.gifDecoder.getTotalIterationCount();
    }

    private void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.isCleared = false;
            loadNextFrame();
        }
    }

    private void stop() {
        this.isRunning = false;
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.callbacks.clear();
        recycleFirstFrame();
        stop();
        DelayTarget delayTarget = this.current;
        if (delayTarget != null) {
            this.requestManager.clear(delayTarget);
            this.current = null;
        }
        DelayTarget delayTarget2 = this.next;
        if (delayTarget2 != null) {
            this.requestManager.clear(delayTarget2);
            this.next = null;
        }
        DelayTarget delayTarget3 = this.pendingTarget;
        if (delayTarget3 != null) {
            this.requestManager.clear(delayTarget3);
            this.pendingTarget = null;
        }
        this.gifDecoder.clear();
        this.isCleared = true;
    }

    /* access modifiers changed from: package-private */
    public Bitmap getCurrentFrame() {
        DelayTarget delayTarget = this.current;
        return delayTarget != null ? delayTarget.getResource() : this.firstFrame;
    }

    private void loadNextFrame() {
        if (this.isRunning && !this.isLoadPending) {
            if (this.startFromFirstFrame) {
                Preconditions.checkArgument(this.pendingTarget == null, "Pending target must be null when starting from the first frame");
                this.gifDecoder.resetFrameIndex();
                this.startFromFirstFrame = false;
            }
            if (this.pendingTarget != null) {
                DelayTarget temp = this.pendingTarget;
                this.pendingTarget = null;
                onFrameReady(temp);
                return;
            }
            this.isLoadPending = true;
            long targetTime = SystemClock.uptimeMillis() + ((long) this.gifDecoder.getNextDelay());
            this.gifDecoder.advance();
            this.next = new DelayTarget(this.handler, this.gifDecoder.getCurrentFrameIndex(), targetTime);
            this.requestBuilder.apply((BaseRequestOptions<?>) RequestOptions.signatureOf(getFrameSignature())).load((Object) this.gifDecoder).into(this.next);
        }
    }

    private void recycleFirstFrame() {
        Bitmap bitmap = this.firstFrame;
        if (bitmap != null) {
            this.bitmapPool.put(bitmap);
            this.firstFrame = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void setNextStartFromFirstFrame() {
        Preconditions.checkArgument(!this.isRunning, "Can't restart a running animation");
        this.startFromFirstFrame = true;
        DelayTarget delayTarget = this.pendingTarget;
        if (delayTarget != null) {
            this.requestManager.clear(delayTarget);
            this.pendingTarget = null;
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setOnEveryFrameReadyListener(@Nullable OnEveryFrameListener onEveryFrameListener2) {
        this.onEveryFrameListener = onEveryFrameListener2;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void onFrameReady(DelayTarget delayTarget) {
        OnEveryFrameListener onEveryFrameListener2 = this.onEveryFrameListener;
        if (onEveryFrameListener2 != null) {
            onEveryFrameListener2.onFrameReady();
        }
        this.isLoadPending = false;
        if (this.isCleared) {
            this.handler.obtainMessage(2, delayTarget).sendToTarget();
        } else if (!this.isRunning) {
            this.pendingTarget = delayTarget;
        } else {
            if (delayTarget.getResource() != null) {
                recycleFirstFrame();
                DelayTarget previous = this.current;
                this.current = delayTarget;
                for (int i = this.callbacks.size() - 1; i >= 0; i--) {
                    this.callbacks.get(i).onFrameReady();
                }
                if (previous != null) {
                    this.handler.obtainMessage(2, previous).sendToTarget();
                }
            }
            loadNextFrame();
        }
    }

    public interface FrameCallback {
        void onFrameReady();
    }

    @VisibleForTesting
    interface OnEveryFrameListener {
        void onFrameReady();
    }

    @VisibleForTesting
    static class DelayTarget extends SimpleTarget<Bitmap> {
        final int index;
        private final Handler handler;
        private final long targetTime;
        private Bitmap resource;

        DelayTarget(Handler handler2, int index2, long targetTime2) {
            this.handler = handler2;
            this.index = index2;
            this.targetTime = targetTime2;
        }

        public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
            onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
        }

        /* access modifiers changed from: package-private */
        public Bitmap getResource() {
            return this.resource;
        }

        public void onResourceReady(@NonNull Bitmap resource2, @Nullable Transition<? super Bitmap> transition) {
            this.resource = resource2;
            this.handler.sendMessageAtTime(this.handler.obtainMessage(1, this), this.targetTime);
        }
    }

    private class FrameLoaderCallback implements Handler.Callback {
        static final int MSG_CLEAR = 2;
        static final int MSG_DELAY = 1;

        FrameLoaderCallback() {
        }

        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                GifFrameLoader.this.onFrameReady((DelayTarget) msg.obj);
                return true;
            } else if (msg.what != 2) {
                return false;
            } else {
                GifFrameLoader.this.requestManager.clear((DelayTarget) msg.obj);
                return false;
            }
        }
    }
}
