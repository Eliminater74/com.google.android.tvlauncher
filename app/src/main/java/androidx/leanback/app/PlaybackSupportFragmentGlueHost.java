package androidx.leanback.app;

import android.view.View;

import androidx.leanback.media.PlaybackGlueHost;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.OnActionClickedListener;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.PlaybackSeekUi;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

public class PlaybackSupportFragmentGlueHost extends PlaybackGlueHost implements PlaybackSeekUi {
    final PlaybackSupportFragment mFragment;
    final PlaybackGlueHost.PlayerCallback mPlayerCallback = new PlaybackGlueHost.PlayerCallback() {
        public void onBufferingStateChanged(boolean start) {
            PlaybackSupportFragmentGlueHost.this.mFragment.onBufferingStateChanged(start);
        }

        public void onError(int errorCode, CharSequence errorMessage) {
            PlaybackSupportFragmentGlueHost.this.mFragment.onError(errorCode, errorMessage);
        }

        public void onVideoSizeChanged(int videoWidth, int videoHeight) {
            PlaybackSupportFragmentGlueHost.this.mFragment.onVideoSizeChanged(videoWidth, videoHeight);
        }
    };

    public PlaybackSupportFragmentGlueHost(PlaybackSupportFragment fragment) {
        this.mFragment = fragment;
    }

    public boolean isControlsOverlayAutoHideEnabled() {
        return this.mFragment.isControlsOverlayAutoHideEnabled();
    }

    public void setControlsOverlayAutoHideEnabled(boolean enabled) {
        this.mFragment.setControlsOverlayAutoHideEnabled(enabled);
    }

    public void setOnKeyInterceptListener(View.OnKeyListener onKeyListener) {
        this.mFragment.setOnKeyInterceptListener(onKeyListener);
    }

    public void setOnActionClickedListener(final OnActionClickedListener listener) {
        if (listener == null) {
            this.mFragment.setOnPlaybackItemViewClickedListener(null);
        } else {
            this.mFragment.setOnPlaybackItemViewClickedListener(new OnItemViewClickedListener() {
                public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                    if (item instanceof Action) {
                        listener.onActionClicked((Action) item);
                    }
                }
            });
        }
    }

    public void setHostCallback(PlaybackGlueHost.HostCallback callback) {
        this.mFragment.setHostCallback(callback);
    }

    public void notifyPlaybackRowChanged() {
        this.mFragment.notifyPlaybackRowChanged();
    }

    public void setPlaybackRowPresenter(PlaybackRowPresenter presenter) {
        this.mFragment.setPlaybackRowPresenter(presenter);
    }

    public void setPlaybackRow(Row row) {
        this.mFragment.setPlaybackRow(row);
    }

    public void fadeOut() {
        this.mFragment.fadeOut();
    }

    public boolean isControlsOverlayVisible() {
        return this.mFragment.isControlsOverlayVisible();
    }

    public void hideControlsOverlay(boolean runAnimation) {
        this.mFragment.hideControlsOverlay(runAnimation);
    }

    public void showControlsOverlay(boolean runAnimation) {
        this.mFragment.showControlsOverlay(runAnimation);
    }

    public void setPlaybackSeekUiClient(PlaybackSeekUi.Client client) {
        this.mFragment.setPlaybackSeekUiClient(client);
    }

    public PlaybackGlueHost.PlayerCallback getPlayerCallback() {
        return this.mPlayerCallback;
    }
}
