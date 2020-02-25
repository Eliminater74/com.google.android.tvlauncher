package androidx.leanback.media;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.view.KeyEvent;
import android.view.View;

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.PlaybackControlsRowPresenter;
import androidx.leanback.widget.PlaybackRowPresenter;
import androidx.leanback.widget.RowPresenter;

import com.google.android.tvlauncher.inputs.InputsManagerUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PlaybackBannerControlGlue<T extends PlayerAdapter> extends PlaybackBaseControlGlue<T> {
    public static final int ACTION_CUSTOM_LEFT_FIRST = 1;
    public static final int ACTION_CUSTOM_RIGHT_FIRST = 4096;
    public static final int ACTION_FAST_FORWARD = 128;
    public static final int ACTION_PLAY_PAUSE = 64;
    public static final int ACTION_REWIND = 32;
    public static final int ACTION_SKIP_TO_NEXT = 256;
    public static final int ACTION_SKIP_TO_PREVIOUS = 16;
    public static final int PLAYBACK_SPEED_FAST_L0 = 10;
    public static final int PLAYBACK_SPEED_FAST_L1 = 11;
    public static final int PLAYBACK_SPEED_FAST_L2 = 12;
    public static final int PLAYBACK_SPEED_FAST_L3 = 13;
    public static final int PLAYBACK_SPEED_FAST_L4 = 14;
    public static final int PLAYBACK_SPEED_INVALID = -1;
    public static final int PLAYBACK_SPEED_NORMAL = 1;
    public static final int PLAYBACK_SPEED_PAUSED = 0;
    private static final int NUMBER_OF_SEEK_SPEEDS = 5;
    private static final String TAG = PlaybackBannerControlGlue.class.getSimpleName();
    private final int[] mFastForwardSpeeds;
    private final int[] mRewindSpeeds;
    private PlaybackControlsRow.FastForwardAction mFastForwardAction;
    private boolean mIsCustomizedFastForwardSupported;
    private boolean mIsCustomizedRewindSupported;
    private int mPlaybackSpeed;
    private PlaybackControlsRow.RewindAction mRewindAction;
    private PlaybackControlsRow.SkipNextAction mSkipNextAction;
    private PlaybackControlsRow.SkipPreviousAction mSkipPreviousAction;
    private long mStartPosition;
    private long mStartTime;

    public PlaybackBannerControlGlue(Context context, int[] seekSpeeds, T impl) {
        this(context, seekSpeeds, seekSpeeds, impl);
    }

    public PlaybackBannerControlGlue(Context context, int[] fastForwardSpeeds, int[] rewindSpeeds, T impl) {
        super(context, impl);
        this.mPlaybackSpeed = 0;
        this.mStartPosition = 0;
        if (fastForwardSpeeds.length == 0 || fastForwardSpeeds.length > 5) {
            throw new IllegalArgumentException("invalid fastForwardSpeeds array size");
        }
        this.mFastForwardSpeeds = fastForwardSpeeds;
        if (rewindSpeeds.length == 0 || rewindSpeeds.length > 5) {
            throw new IllegalArgumentException("invalid rewindSpeeds array size");
        }
        this.mRewindSpeeds = rewindSpeeds;
        if ((this.mPlayerAdapter.getSupportedActions() & 128) != 0) {
            this.mIsCustomizedFastForwardSupported = true;
        }
        if ((this.mPlayerAdapter.getSupportedActions() & 32) != 0) {
            this.mIsCustomizedRewindSupported = true;
        }
    }

    public void setControlsRow(PlaybackControlsRow controlsRow) {
        super.setControlsRow(controlsRow);
        onUpdatePlaybackState();
    }

    /* access modifiers changed from: protected */
    public void onCreatePrimaryActions(ArrayObjectAdapter primaryActionsAdapter) {
        PlaybackControlsRow.SkipNextAction skipNextAction;
        PlaybackControlsRow.FastForwardAction fastForwardAction;
        PlaybackControlsRow.RewindAction rewindAction;
        PlaybackControlsRow.SkipPreviousAction skipPreviousAction;
        long supportedActions = getSupportedActions();
        if ((supportedActions & 16) != 0 && this.mSkipPreviousAction == null) {
            PlaybackControlsRow.SkipPreviousAction skipPreviousAction2 = new PlaybackControlsRow.SkipPreviousAction(getContext());
            this.mSkipPreviousAction = skipPreviousAction2;
            primaryActionsAdapter.add(skipPreviousAction2);
        } else if ((16 & supportedActions) == 0 && (skipPreviousAction = this.mSkipPreviousAction) != null) {
            primaryActionsAdapter.remove(skipPreviousAction);
            this.mSkipPreviousAction = null;
        }
        if ((supportedActions & 32) != 0 && this.mRewindAction == null) {
            PlaybackControlsRow.RewindAction rewindAction2 = new PlaybackControlsRow.RewindAction(getContext(), this.mRewindSpeeds.length);
            this.mRewindAction = rewindAction2;
            primaryActionsAdapter.add(rewindAction2);
        } else if ((32 & supportedActions) == 0 && (rewindAction = this.mRewindAction) != null) {
            primaryActionsAdapter.remove(rewindAction);
            this.mRewindAction = null;
        }
        if ((supportedActions & 64) != 0 && this.mPlayPauseAction == null) {
            this.mPlayPauseAction = new PlaybackControlsRow.PlayPauseAction(getContext());
            PlaybackControlsRow.PlayPauseAction playPauseAction = new PlaybackControlsRow.PlayPauseAction(getContext());
            this.mPlayPauseAction = playPauseAction;
            primaryActionsAdapter.add(playPauseAction);
        } else if ((64 & supportedActions) == 0 && this.mPlayPauseAction != null) {
            primaryActionsAdapter.remove(this.mPlayPauseAction);
            this.mPlayPauseAction = null;
        }
        if ((supportedActions & 128) != 0 && this.mFastForwardAction == null) {
            this.mFastForwardAction = new PlaybackControlsRow.FastForwardAction(getContext(), this.mFastForwardSpeeds.length);
            PlaybackControlsRow.FastForwardAction fastForwardAction2 = new PlaybackControlsRow.FastForwardAction(getContext(), this.mFastForwardSpeeds.length);
            this.mFastForwardAction = fastForwardAction2;
            primaryActionsAdapter.add(fastForwardAction2);
        } else if ((128 & supportedActions) == 0 && (fastForwardAction = this.mFastForwardAction) != null) {
            primaryActionsAdapter.remove(fastForwardAction);
            this.mFastForwardAction = null;
        }
        if ((supportedActions & 256) != 0 && this.mSkipNextAction == null) {
            PlaybackControlsRow.SkipNextAction skipNextAction2 = new PlaybackControlsRow.SkipNextAction(getContext());
            this.mSkipNextAction = skipNextAction2;
            primaryActionsAdapter.add(skipNextAction2);
        } else if ((256 & supportedActions) == 0 && (skipNextAction = this.mSkipNextAction) != null) {
            primaryActionsAdapter.remove(skipNextAction);
            this.mSkipNextAction = null;
        }
    }

    /* access modifiers changed from: protected */
    public PlaybackRowPresenter onCreateRowPresenter() {
        return new PlaybackControlsRowPresenter(new AbstractDetailsDescriptionPresenter() {
            /* access modifiers changed from: protected */
            public void onBindDescription(AbstractDetailsDescriptionPresenter.ViewHolder viewHolder, Object object) {
                PlaybackBannerControlGlue glue = (PlaybackBannerControlGlue) object;
                viewHolder.getTitle().setText(glue.getTitle());
                viewHolder.getSubtitle().setText(glue.getSubtitle());
            }
        }) {
            /* access modifiers changed from: protected */
            public void onBindRowViewHolder(RowPresenter.ViewHolder vh, Object item) {
                super.onBindRowViewHolder(vh, item);
                vh.setOnKeyListener(PlaybackBannerControlGlue.this);
            }

            /* access modifiers changed from: protected */
            public void onUnbindRowViewHolder(RowPresenter.ViewHolder vh) {
                super.onUnbindRowViewHolder(vh);
                vh.setOnKeyListener(null);
            }
        };
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
                    if (event.getAction() == 0) {
                        dispatchAction(action, event);
                    }
                    return true;
            }
        }
        int i = this.mPlaybackSpeed;
        if (!(i >= 10 || i <= -10)) {
            return false;
        }
        play();
        onUpdatePlaybackStatusAfterUserAction();
        if (keyCode == 4 || keyCode == 111) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void onUpdatePlaybackStatusAfterUserAction() {
        updatePlaybackState(this.mIsPlaying);
    }

    private void incrementFastForwardPlaybackSpeed() {
        int i = this.mPlaybackSpeed;
        switch (i) {
            case 10:
            case 11:
            case 12:
            case 13:
                this.mPlaybackSpeed = i + 1;
                return;
            default:
                this.mPlaybackSpeed = 10;
                return;
        }
    }

    private void decrementRewindPlaybackSpeed() {
        int i = this.mPlaybackSpeed;
        switch (i) {
            case -13:
            case -12:
            case -11:
            case InputsManagerUtil.TYPE_CEC_TUNER /*-10*/:
                this.mPlaybackSpeed = i - 1;
                return;
            default:
                this.mPlaybackSpeed = -10;
                return;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchAction(Action action, KeyEvent keyEvent) {
        if (action == this.mPlayPauseAction) {
            boolean canPause = false;
            boolean canPlay = keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 126;
            if (keyEvent == null || keyEvent.getKeyCode() == 85 || keyEvent.getKeyCode() == 127) {
                canPause = true;
            }
            if (canPause) {
                int i = this.mPlaybackSpeed;
                if (!canPlay ? i != 0 : i == 1) {
                    pause();
                    onUpdatePlaybackStatusAfterUserAction();
                    return true;
                }
            }
            if (canPlay && this.mPlaybackSpeed != 1) {
                play();
            }
            onUpdatePlaybackStatusAfterUserAction();
            return true;
        } else if (action == this.mSkipNextAction) {
            next();
            return true;
        } else if (action == this.mSkipPreviousAction) {
            previous();
            return true;
        } else if (action == this.mFastForwardAction) {
            if (this.mPlayerAdapter.isPrepared() && this.mPlaybackSpeed < getMaxForwardSpeedId()) {
                if (this.mIsCustomizedFastForwardSupported) {
                    this.mIsPlaying = true;
                    this.mPlayerAdapter.fastForward();
                } else {
                    fakePause();
                }
                incrementFastForwardPlaybackSpeed();
                onUpdatePlaybackStatusAfterUserAction();
            }
            return true;
        } else if (action != this.mRewindAction) {
            return false;
        } else {
            if (this.mPlayerAdapter.isPrepared() && this.mPlaybackSpeed > (-getMaxRewindSpeedId())) {
                if (this.mIsCustomizedFastForwardSupported) {
                    this.mIsPlaying = true;
                    this.mPlayerAdapter.rewind();
                } else {
                    fakePause();
                }
                decrementRewindPlaybackSpeed();
                onUpdatePlaybackStatusAfterUserAction();
            }
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onPlayStateChanged() {
        onUpdatePlaybackState();
        super.onPlayStateChanged();
    }

    /* access modifiers changed from: protected */
    public void onPlayCompleted() {
        super.onPlayCompleted();
        this.mIsPlaying = false;
        this.mPlaybackSpeed = 0;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        onUpdatePlaybackState();
    }

    /* access modifiers changed from: package-private */
    public void onUpdatePlaybackState() {
        updatePlaybackState(this.mIsPlaying);
    }

    private void updatePlaybackState(boolean isPlaying) {
        int index;
        if (this.mControlsRow != null) {
            if (!isPlaying) {
                onUpdateProgress();
                this.mPlayerAdapter.setProgressUpdatingEnabled(false);
            } else {
                this.mPlayerAdapter.setProgressUpdatingEnabled(true);
            }
            if (this.mFadeWhenPlaying && getHost() != null) {
                getHost().setControlsOverlayAutoHideEnabled(isPlaying);
            }
            ArrayObjectAdapter primaryActionsAdapter = (ArrayObjectAdapter) getControlsRow().getPrimaryActionsAdapter();
            if (!(this.mPlayPauseAction == null || this.mPlayPauseAction.getIndex() == (index = isPlaying))) {
                this.mPlayPauseAction.setIndex((int) index);
                notifyItemChanged(primaryActionsAdapter, this.mPlayPauseAction);
            }
            if (this.mFastForwardAction != null) {
                int index2 = 0;
                int i = this.mPlaybackSpeed;
                if (i >= 10) {
                    index2 = (i - 10) + 1;
                }
                if (this.mFastForwardAction.getIndex() != index2) {
                    this.mFastForwardAction.setIndex(index2);
                    notifyItemChanged(primaryActionsAdapter, this.mFastForwardAction);
                }
            }
            if (this.mRewindAction != null) {
                int index3 = 0;
                int i2 = this.mPlaybackSpeed;
                if (i2 <= -10) {
                    index3 = ((-i2) - 10) + 1;
                }
                if (this.mRewindAction.getIndex() != index3) {
                    this.mRewindAction.setIndex(index3);
                    notifyItemChanged(primaryActionsAdapter, this.mRewindAction);
                }
            }
        }
    }

    @NonNull
    public int[] getFastForwardSpeeds() {
        return this.mFastForwardSpeeds;
    }

    @NonNull
    public int[] getRewindSpeeds() {
        return this.mRewindSpeeds;
    }

    private int getMaxForwardSpeedId() {
        return (this.mFastForwardSpeeds.length - 1) + 10;
    }

    private int getMaxRewindSpeedId() {
        return (this.mRewindSpeeds.length - 1) + 10;
    }

    public long getCurrentPosition() {
        int index;
        int i = this.mPlaybackSpeed;
        if (i == 0 || i == 1) {
            return this.mPlayerAdapter.getCurrentPosition();
        }
        if (i >= 10) {
            if (this.mIsCustomizedFastForwardSupported) {
                return this.mPlayerAdapter.getCurrentPosition();
            }
            index = getFastForwardSpeeds()[i - 10];
        } else if (i > -10) {
            return -1;
        } else {
            if (this.mIsCustomizedRewindSupported) {
                return this.mPlayerAdapter.getCurrentPosition();
            }
            index = -getRewindSpeeds()[(-i) - 10];
        }
        long position = this.mStartPosition + ((System.currentTimeMillis() - this.mStartTime) * ((long) index));
        if (position > getDuration()) {
            this.mPlaybackSpeed = 0;
            long position2 = getDuration();
            this.mPlayerAdapter.seekTo(position2);
            this.mStartPosition = 0;
            pause();
            return position2;
        } else if (position >= 0) {
            return position;
        } else {
            this.mPlaybackSpeed = 0;
            this.mPlayerAdapter.seekTo(0);
            this.mStartPosition = 0;
            pause();
            return 0;
        }
    }

    public void play() {
        if (this.mPlayerAdapter.isPrepared()) {
            if (this.mPlaybackSpeed != 0 || this.mPlayerAdapter.getCurrentPosition() < this.mPlayerAdapter.getDuration()) {
                this.mStartPosition = getCurrentPosition();
            } else {
                this.mStartPosition = 0;
            }
            this.mStartTime = System.currentTimeMillis();
            this.mIsPlaying = true;
            this.mPlaybackSpeed = 1;
            this.mPlayerAdapter.seekTo(this.mStartPosition);
            super.play();
            onUpdatePlaybackState();
        }
    }

    public void pause() {
        this.mIsPlaying = false;
        this.mPlaybackSpeed = 0;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        super.pause();
        onUpdatePlaybackState();
    }

    private void fakePause() {
        this.mIsPlaying = true;
        this.mStartPosition = getCurrentPosition();
        this.mStartTime = System.currentTimeMillis();
        super.pause();
        onUpdatePlaybackState();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ACTION_ {
    }
}
