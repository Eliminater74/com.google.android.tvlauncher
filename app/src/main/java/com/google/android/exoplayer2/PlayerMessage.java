package com.google.android.exoplayer2;

import android.os.Handler;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;

public final class PlayerMessage {
    private final Sender sender;
    private final Target target;
    private final Timeline timeline;
    private boolean deleteAfterDelivery = true;
    private Handler handler;
    private boolean isCanceled;
    private boolean isDelivered;
    private boolean isProcessed;
    private boolean isSent;
    @Nullable
    private Object payload;
    private long positionMs = C0841C.TIME_UNSET;
    private int type;
    private int windowIndex;

    public PlayerMessage(Sender sender2, Target target2, Timeline timeline2, int defaultWindowIndex, Handler defaultHandler) {
        this.sender = sender2;
        this.target = target2;
        this.timeline = timeline2;
        this.handler = defaultHandler;
        this.windowIndex = defaultWindowIndex;
    }

    public Timeline getTimeline() {
        return this.timeline;
    }

    public Target getTarget() {
        return this.target;
    }

    public int getType() {
        return this.type;
    }

    public PlayerMessage setType(int messageType) {
        Assertions.checkState(!this.isSent);
        this.type = messageType;
        return this;
    }

    @Nullable
    public Object getPayload() {
        return this.payload;
    }

    public PlayerMessage setPayload(@Nullable Object payload2) {
        Assertions.checkState(!this.isSent);
        this.payload = payload2;
        return this;
    }

    public Handler getHandler() {
        return this.handler;
    }

    public PlayerMessage setHandler(Handler handler2) {
        Assertions.checkState(!this.isSent);
        this.handler = handler2;
        return this;
    }

    public long getPositionMs() {
        return this.positionMs;
    }

    public PlayerMessage setPosition(long positionMs2) {
        Assertions.checkState(!this.isSent);
        this.positionMs = positionMs2;
        return this;
    }

    public PlayerMessage setPosition(int windowIndex2, long positionMs2) {
        boolean z = true;
        Assertions.checkState(!this.isSent);
        if (positionMs2 == C0841C.TIME_UNSET) {
            z = false;
        }
        Assertions.checkArgument(z);
        if (windowIndex2 < 0 || (!this.timeline.isEmpty() && windowIndex2 >= this.timeline.getWindowCount())) {
            throw new IllegalSeekPositionException(this.timeline, windowIndex2, positionMs2);
        }
        this.windowIndex = windowIndex2;
        this.positionMs = positionMs2;
        return this;
    }

    public int getWindowIndex() {
        return this.windowIndex;
    }

    public boolean getDeleteAfterDelivery() {
        return this.deleteAfterDelivery;
    }

    public PlayerMessage setDeleteAfterDelivery(boolean deleteAfterDelivery2) {
        Assertions.checkState(!this.isSent);
        this.deleteAfterDelivery = deleteAfterDelivery2;
        return this;
    }

    public PlayerMessage send() {
        Assertions.checkState(!this.isSent);
        if (this.positionMs == C0841C.TIME_UNSET) {
            Assertions.checkArgument(this.deleteAfterDelivery);
        }
        this.isSent = true;
        this.sender.sendMessage(this);
        return this;
    }

    public synchronized PlayerMessage cancel() {
        Assertions.checkState(this.isSent);
        this.isCanceled = true;
        markAsProcessed(false);
        return this;
    }

    public synchronized boolean isCanceled() {
        return this.isCanceled;
    }

    public synchronized boolean blockUntilDelivered() throws InterruptedException {
        Assertions.checkState(this.isSent);
        Assertions.checkState(this.handler.getLooper().getThread() != Thread.currentThread());
        while (!this.isProcessed) {
            wait();
        }
        return this.isDelivered;
    }

    public synchronized void markAsProcessed(boolean isDelivered2) {
        this.isDelivered |= isDelivered2;
        this.isProcessed = true;
        notifyAll();
    }

    public interface Sender {
        void sendMessage(PlayerMessage playerMessage);
    }

    public interface Target {
        void handleMessage(int i, @Nullable Object obj) throws ExoPlaybackException;
    }
}
