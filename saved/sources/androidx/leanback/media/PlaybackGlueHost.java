package androidx.leanback.media;

import android.view.View;
import androidx.leanback.widget.OnActionClickedListener;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.Row;

public abstract class PlaybackGlueHost {
    PlaybackGlue mGlue;

    public static abstract class HostCallback {
        public void onHostStart() {
        }

        public void onHostStop() {
        }

        public void onHostPause() {
        }

        public void onHostResume() {
        }

        public void onHostDestroy() {
        }
    }

    public static class PlayerCallback {
        public void onVideoSizeChanged(int videoWidth, int videoHeight) {
        }

        public void onBufferingStateChanged(boolean start) {
        }

        public void onError(int errorCode, CharSequence errorMessage) {
        }
    }

    @Deprecated
    public void setFadingEnabled(boolean enable) {
    }

    public void setControlsOverlayAutoHideEnabled(boolean enabled) {
        setFadingEnabled(enabled);
    }

    public boolean isControlsOverlayAutoHideEnabled() {
        return false;
    }

    @Deprecated
    public void fadeOut() {
    }

    public boolean isControlsOverlayVisible() {
        return true;
    }

    public void hideControlsOverlay(boolean runAnimation) {
    }

    public void showControlsOverlay(boolean runAnimation) {
    }

    public void setOnKeyInterceptListener(View.OnKeyListener onKeyListener) {
    }

    public void setOnActionClickedListener(OnActionClickedListener listener) {
    }

    public void setHostCallback(HostCallback callback) {
    }

    public void notifyPlaybackRowChanged() {
    }

    public void setPlaybackRowPresenter(PlaybackRowPresenter presenter) {
    }

    public void setPlaybackRow(Row row) {
    }

    /* access modifiers changed from: package-private */
    public final void attachToGlue(PlaybackGlue glue) {
        PlaybackGlue playbackGlue = this.mGlue;
        if (playbackGlue != null) {
            playbackGlue.onDetachedFromHost();
        }
        this.mGlue = glue;
        PlaybackGlue playbackGlue2 = this.mGlue;
        if (playbackGlue2 != null) {
            playbackGlue2.onAttachedToHost(this);
        }
    }

    public PlayerCallback getPlayerCallback() {
        return null;
    }
}
