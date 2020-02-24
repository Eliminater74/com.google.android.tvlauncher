package androidx.leanback.media;

public abstract class PlayerAdapter {
    Callback mCallback;

    public abstract void pause();

    public abstract void play();

    public static class Callback {
        public void onPlayStateChanged(PlayerAdapter adapter) {
        }

        public void onPreparedStateChanged(PlayerAdapter adapter) {
        }

        public void onPlayCompleted(PlayerAdapter adapter) {
        }

        public void onCurrentPositionChanged(PlayerAdapter adapter) {
        }

        public void onBufferedPositionChanged(PlayerAdapter adapter) {
        }

        public void onDurationChanged(PlayerAdapter adapter) {
        }

        public void onVideoSizeChanged(PlayerAdapter adapter, int width, int height) {
        }

        public void onError(PlayerAdapter adapter, int errorCode, String errorMessage) {
        }

        public void onBufferingStateChanged(PlayerAdapter adapter, boolean start) {
        }

        public void onMetadataChanged(PlayerAdapter adapter) {
        }
    }

    public final void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public final Callback getCallback() {
        return this.mCallback;
    }

    public boolean isPrepared() {
        return true;
    }

    public void next() {
    }

    public void previous() {
    }

    public void fastForward() {
    }

    public void rewind() {
    }

    public void seekTo(long positionInMs) {
    }

    public void setProgressUpdatingEnabled(boolean enable) {
    }

    public void setShuffleAction(int shuffleActionIndex) {
    }

    public void setRepeatAction(int repeatActionIndex) {
    }

    public boolean isPlaying() {
        return false;
    }

    public long getDuration() {
        return 0;
    }

    public long getSupportedActions() {
        return 64;
    }

    public long getCurrentPosition() {
        return 0;
    }

    public long getBufferedPosition() {
        return 0;
    }

    public void onAttachedToHost(PlaybackGlueHost host) {
    }

    public void onDetachedFromHost() {
    }
}
