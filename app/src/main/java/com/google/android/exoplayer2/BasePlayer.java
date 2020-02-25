package com.google.android.exoplayer2;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Util;

public abstract class BasePlayer implements Player {
    protected final Timeline.Window window = new Timeline.Window();

    public final void seekToDefaultPosition() {
        seekToDefaultPosition(getCurrentWindowIndex());
    }

    public final void seekToDefaultPosition(int windowIndex) {
        seekTo(windowIndex, C0841C.TIME_UNSET);
    }

    public final void seekTo(long positionMs) {
        seekTo(getCurrentWindowIndex(), positionMs);
    }

    public final boolean hasPrevious() {
        return getPreviousWindowIndex() != -1;
    }

    public final void previous() {
        int previousWindowIndex = getPreviousWindowIndex();
        if (previousWindowIndex != -1) {
            seekToDefaultPosition(previousWindowIndex);
        }
    }

    public final boolean hasNext() {
        return getNextWindowIndex() != -1;
    }

    public final void next() {
        int nextWindowIndex = getNextWindowIndex();
        if (nextWindowIndex != -1) {
            seekToDefaultPosition(nextWindowIndex);
        }
    }

    public final void stop() {
        stop(false);
    }

    public final int getNextWindowIndex() {
        Timeline timeline = getCurrentTimeline();
        if (timeline.isEmpty()) {
            return -1;
        }
        return timeline.getNextWindowIndex(getCurrentWindowIndex(), getRepeatModeForNavigation(), getShuffleModeEnabled());
    }

    public final int getPreviousWindowIndex() {
        Timeline timeline = getCurrentTimeline();
        if (timeline.isEmpty()) {
            return -1;
        }
        return timeline.getPreviousWindowIndex(getCurrentWindowIndex(), getRepeatModeForNavigation(), getShuffleModeEnabled());
    }

    @Nullable
    public final Object getCurrentTag() {
        int windowIndex = getCurrentWindowIndex();
        Timeline timeline = getCurrentTimeline();
        if (windowIndex >= timeline.getWindowCount()) {
            return null;
        }
        return timeline.getWindow(windowIndex, this.window, true).tag;
    }

    public final int getBufferedPercentage() {
        long position = getBufferedPosition();
        long duration = getDuration();
        if (position == C0841C.TIME_UNSET || duration == C0841C.TIME_UNSET) {
            return 0;
        }
        if (duration == 0) {
            return 100;
        }
        return Util.constrainValue((int) ((100 * position) / duration), 0, 100);
    }

    public final boolean isCurrentWindowDynamic() {
        Timeline timeline = getCurrentTimeline();
        return !timeline.isEmpty() && timeline.getWindow(getCurrentWindowIndex(), this.window).isDynamic;
    }

    public final boolean isCurrentWindowSeekable() {
        Timeline timeline = getCurrentTimeline();
        return !timeline.isEmpty() && timeline.getWindow(getCurrentWindowIndex(), this.window).isSeekable;
    }

    public final long getContentDuration() {
        Timeline timeline = getCurrentTimeline();
        if (timeline.isEmpty()) {
            return C0841C.TIME_UNSET;
        }
        return timeline.getWindow(getCurrentWindowIndex(), this.window).getDurationMs();
    }

    private int getRepeatModeForNavigation() {
        int repeatMode = getRepeatMode();
        if (repeatMode == 1) {
            return 0;
        }
        return repeatMode;
    }

    protected interface ListenerInvocation {
        void invokeListener(Player.EventListener eventListener);
    }

    protected static final class ListenerHolder {
        public final Player.EventListener listener;
        private boolean released;

        public ListenerHolder(Player.EventListener listener2) {
            this.listener = listener2;
        }

        public void release() {
            this.released = true;
        }

        public void invoke(ListenerInvocation listenerInvocation) {
            if (!this.released) {
                listenerInvocation.invokeListener(this.listener);
            }
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            return this.listener.equals(((ListenerHolder) other).listener);
        }

        public int hashCode() {
            return this.listener.hashCode();
        }
    }
}
