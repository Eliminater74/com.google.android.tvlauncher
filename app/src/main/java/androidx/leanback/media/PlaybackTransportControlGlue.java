package androidx.leanback.media;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import androidx.leanback.media.PlayerAdapter;
import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.PlaybackSeekDataProvider;
import androidx.leanback.widget.PlaybackSeekUi;
import androidx.leanback.widget.PlaybackTransportRowPresenter;
import androidx.leanback.widget.RowPresenter;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import java.lang.ref.WeakReference;

public class PlaybackTransportControlGlue<T extends PlayerAdapter> extends PlaybackBaseControlGlue<T> {
    static final boolean DEBUG = false;
    static final int MSG_UPDATE_PLAYBACK_STATE = 100;
    static final String TAG = "PlaybackTransportGlue";
    static final int UPDATE_PLAYBACK_STATE_DELAY_MS = 2000;
    static final Handler sHandler = new UpdatePlaybackStateHandler();
    final WeakReference<PlaybackBaseControlGlue> mGlueWeakReference = new WeakReference<>(this);
    final PlaybackTransportControlGlue<T>.SeekUiClient mPlaybackSeekUiClient = new SeekUiClient();
    boolean mSeekEnabled;
    PlaybackSeekDataProvider mSeekProvider;

    static class UpdatePlaybackStateHandler extends Handler {
        UpdatePlaybackStateHandler() {
        }

        public void handleMessage(Message msg) {
            PlaybackTransportControlGlue glue;
            if (msg.what == 100 && (glue = (PlaybackTransportControlGlue) ((WeakReference) msg.obj).get()) != null) {
                glue.onUpdatePlaybackState();
            }
        }
    }

    public PlaybackTransportControlGlue(Context context, T impl) {
        super(context, impl);
    }

    public void setControlsRow(PlaybackControlsRow controlsRow) {
        super.setControlsRow(controlsRow);
        sHandler.removeMessages(100, this.mGlueWeakReference);
        onUpdatePlaybackState();
    }

    /* access modifiers changed from: protected */
    public void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
        PlaybackControlsRow.PlayPauseAction playPauseAction = new PlaybackControlsRow.PlayPauseAction(getContext());
        this.mPlayPauseAction = playPauseAction;
        primaryActionsAdapter.add(playPauseAction);
    }

    /* access modifiers changed from: protected */
    public PlaybackRowPresenter onCreateRowPresenter() {
        AbstractDetailsDescriptionPresenter detailsPresenter = new AbstractDetailsDescriptionPresenter() {
            /* access modifiers changed from: protected */
            public void onBindDescription(AbstractDetailsDescriptionPresenter.ViewHolder viewHolder, Object obj) {
                PlaybackBaseControlGlue glue = (PlaybackBaseControlGlue) obj;
                viewHolder.getTitle().setText(glue.getTitle());
                viewHolder.getSubtitle().setText(glue.getSubtitle());
            }
        };
        PlaybackTransportRowPresenter rowPresenter = new PlaybackTransportRowPresenter() {
            /* access modifiers changed from: protected */
            public void onBindRowViewHolder(RowPresenter.ViewHolder vh, Object item) {
                super.onBindRowViewHolder(vh, item);
                vh.setOnKeyListener(PlaybackTransportControlGlue.this);
            }

            /* access modifiers changed from: protected */
            public void onUnbindRowViewHolder(RowPresenter.ViewHolder vh) {
                super.onUnbindRowViewHolder(vh);
                vh.setOnKeyListener(null);
            }
        };
        rowPresenter.setDescriptionPresenter(detailsPresenter);
        return rowPresenter;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToHost(PlaybackGlueHost host) {
        super.onAttachedToHost(host);
        if (host instanceof PlaybackSeekUi) {
            ((PlaybackSeekUi) host).setPlaybackSeekUiClient(this.mPlaybackSeekUiClient);
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromHost() {
        super.onDetachedFromHost();
        if (getHost() instanceof PlaybackSeekUi) {
            ((PlaybackSeekUi) getHost()).setPlaybackSeekUiClient(null);
        }
    }

    /* access modifiers changed from: protected */
    public void onUpdateProgress() {
        if (!this.mPlaybackSeekUiClient.mIsSeek) {
            super.onUpdateProgress();
        }
    }

    public void onActionClicked(Action action) {
        dispatchAction(action, null);
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (!(keyCode == 4 || keyCode == 111)) {
            switch (keyCode) {
                case 19:
                case 20:
                case 21:
                case 22:
                    break;
                default:
                    Action action = this.mControlsRow.getActionForKeyCode(this.mControlsRow.getPrimaryActionsAdapter(), keyCode);
                    if (action == null) {
                        action = this.mControlsRow.getActionForKeyCode(this.mControlsRow.getSecondaryActionsAdapter(), keyCode);
                    }
                    if (action == null) {
                        return false;
                    }
                    if (event.getAction() != 0) {
                        return true;
                    }
                    dispatchAction(action, event);
                    return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void onUpdatePlaybackStatusAfterUserAction() {
        updatePlaybackState(this.mIsPlaying);
        sHandler.removeMessages(100, this.mGlueWeakReference);
        Handler handler = sHandler;
        handler.sendMessageDelayed(handler.obtainMessage(100, this.mGlueWeakReference), AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchAction(Action action, KeyEvent keyEvent) {
        if (action instanceof PlaybackControlsRow.PlayPauseAction) {
            boolean canPlay = keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 126;
            if ((keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 127) && this.mIsPlaying) {
                this.mIsPlaying = false;
                pause();
            } else if (canPlay && !this.mIsPlaying) {
                this.mIsPlaying = true;
                play();
            }
            onUpdatePlaybackStatusAfterUserAction();
            return true;
        } else if (action instanceof PlaybackControlsRow.SkipNextAction) {
            next();
            return true;
        } else if (!(action instanceof PlaybackControlsRow.SkipPreviousAction)) {
            return false;
        } else {
            previous();
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onPlayStateChanged() {
        if (sHandler.hasMessages(100, this.mGlueWeakReference)) {
            sHandler.removeMessages(100, this.mGlueWeakReference);
            if (this.mPlayerAdapter.isPlaying() != this.mIsPlaying) {
                Handler handler = sHandler;
                handler.sendMessageDelayed(handler.obtainMessage(100, this.mGlueWeakReference), AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
            } else {
                onUpdatePlaybackState();
            }
        } else {
            onUpdatePlaybackState();
        }
        super.onPlayStateChanged();
    }

    /* access modifiers changed from: package-private */
    public void onUpdatePlaybackState() {
        this.mIsPlaying = this.mPlayerAdapter.isPlaying();
        updatePlaybackState(this.mIsPlaying);
    }

    private void updatePlaybackState(boolean isPlaying) {
        int index;
        if (this.mControlsRow != null) {
            if (!isPlaying) {
                onUpdateProgress();
                this.mPlayerAdapter.setProgressUpdatingEnabled(this.mPlaybackSeekUiClient.mIsSeek);
            } else {
                this.mPlayerAdapter.setProgressUpdatingEnabled(true);
            }
            if (this.mFadeWhenPlaying && getHost() != null) {
                getHost().setControlsOverlayAutoHideEnabled(isPlaying);
            }
            if (this.mPlayPauseAction != null && this.mPlayPauseAction.getIndex() != (index = isPlaying)) {
                this.mPlayPauseAction.setIndex((int) index);
                notifyItemChanged((ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter(), this.mPlayPauseAction);
            }
        }
    }

    class SeekUiClient extends PlaybackSeekUi.Client {
        boolean mIsSeek;
        long mLastUserPosition;
        boolean mPausedBeforeSeek;
        long mPositionBeforeSeek;

        SeekUiClient() {
        }

        public PlaybackSeekDataProvider getPlaybackSeekDataProvider() {
            return PlaybackTransportControlGlue.this.mSeekProvider;
        }

        public boolean isSeekEnabled() {
            return PlaybackTransportControlGlue.this.mSeekProvider != null || PlaybackTransportControlGlue.this.mSeekEnabled;
        }

        public void onSeekStarted() {
            this.mIsSeek = true;
            this.mPausedBeforeSeek = !PlaybackTransportControlGlue.this.isPlaying();
            PlaybackTransportControlGlue.this.mPlayerAdapter.setProgressUpdatingEnabled(true);
            this.mPositionBeforeSeek = PlaybackTransportControlGlue.this.mSeekProvider == null ? PlaybackTransportControlGlue.this.mPlayerAdapter.getCurrentPosition() : -1;
            this.mLastUserPosition = -1;
            PlaybackTransportControlGlue.this.pause();
        }

        public void onSeekPositionChanged(long pos) {
            if (PlaybackTransportControlGlue.this.mSeekProvider == null) {
                PlaybackTransportControlGlue.this.mPlayerAdapter.seekTo(pos);
            } else {
                this.mLastUserPosition = pos;
            }
            if (PlaybackTransportControlGlue.this.mControlsRow != null) {
                PlaybackTransportControlGlue.this.mControlsRow.setCurrentPosition(pos);
            }
        }

        public void onSeekFinished(boolean cancelled) {
            if (!cancelled) {
                long j = this.mLastUserPosition;
                if (j >= 0) {
                    PlaybackTransportControlGlue.this.seekTo(j);
                }
            } else {
                long j2 = this.mPositionBeforeSeek;
                if (j2 >= 0) {
                    PlaybackTransportControlGlue.this.seekTo(j2);
                }
            }
            this.mIsSeek = false;
            if (!this.mPausedBeforeSeek) {
                PlaybackTransportControlGlue.this.play();
                return;
            }
            PlaybackTransportControlGlue.this.mPlayerAdapter.setProgressUpdatingEnabled(false);
            PlaybackTransportControlGlue.this.onUpdateProgress();
        }
    }

    public final void setSeekProvider(PlaybackSeekDataProvider seekProvider) {
        this.mSeekProvider = seekProvider;
    }

    public final PlaybackSeekDataProvider getSeekProvider() {
        return this.mSeekProvider;
    }

    public final void setSeekEnabled(boolean seekEnabled) {
        this.mSeekEnabled = seekEnabled;
    }

    public final boolean isSeekEnabled() {
        return this.mSeekEnabled;
    }
}
