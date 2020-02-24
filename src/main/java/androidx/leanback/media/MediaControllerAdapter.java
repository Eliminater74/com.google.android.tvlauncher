package androidx.leanback.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.p001v4.media.MediaMetadataCompat;
import android.support.p001v4.media.session.MediaControllerCompat;
import android.support.p001v4.media.session.PlaybackStateCompat;

public class MediaControllerAdapter extends PlayerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "MediaControllerAdapter";
    private MediaControllerCompat mController;
    Handler mHandler = new Handler();
    boolean mIsBuffering = false;
    MediaControllerCompat.Callback mMediaControllerCallback = new MediaControllerCompat.Callback() {
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            if (MediaControllerAdapter.this.mIsBuffering && state.getState() != 6) {
                MediaControllerAdapter.this.getCallback().onBufferingStateChanged(MediaControllerAdapter.this, false);
                MediaControllerAdapter.this.getCallback().onBufferedPositionChanged(MediaControllerAdapter.this);
                MediaControllerAdapter.this.mIsBuffering = false;
            }
            if (state.getState() != 0) {
                if (state.getState() == 1) {
                    MediaControllerAdapter.this.getCallback().onPlayCompleted(MediaControllerAdapter.this);
                } else if (state.getState() == 2) {
                    MediaControllerAdapter.this.getCallback().onPlayStateChanged(MediaControllerAdapter.this);
                    MediaControllerAdapter.this.getCallback().onCurrentPositionChanged(MediaControllerAdapter.this);
                } else if (state.getState() == 3) {
                    MediaControllerAdapter.this.getCallback().onPlayStateChanged(MediaControllerAdapter.this);
                    MediaControllerAdapter.this.getCallback().onCurrentPositionChanged(MediaControllerAdapter.this);
                } else if (state.getState() == 6) {
                    MediaControllerAdapter mediaControllerAdapter = MediaControllerAdapter.this;
                    mediaControllerAdapter.mIsBuffering = true;
                    mediaControllerAdapter.getCallback().onBufferingStateChanged(MediaControllerAdapter.this, true);
                    MediaControllerAdapter.this.getCallback().onBufferedPositionChanged(MediaControllerAdapter.this);
                } else if (state.getState() == 7) {
                    if (state.getErrorMessage() == null) {
                        MediaControllerAdapter.this.getCallback().onError(MediaControllerAdapter.this, state.getErrorCode(), "");
                    } else {
                        MediaControllerAdapter.this.getCallback().onError(MediaControllerAdapter.this, state.getErrorCode(), state.getErrorMessage().toString());
                    }
                } else if (state.getState() == 4) {
                    MediaControllerAdapter.this.getCallback().onPlayStateChanged(MediaControllerAdapter.this);
                    MediaControllerAdapter.this.getCallback().onCurrentPositionChanged(MediaControllerAdapter.this);
                } else if (state.getState() == 5) {
                    MediaControllerAdapter.this.getCallback().onPlayStateChanged(MediaControllerAdapter.this);
                    MediaControllerAdapter.this.getCallback().onCurrentPositionChanged(MediaControllerAdapter.this);
                }
            }
        }

        public void onMetadataChanged(MediaMetadataCompat metadata) {
            MediaControllerAdapter.this.getCallback().onMetadataChanged(MediaControllerAdapter.this);
        }
    };
    private final Runnable mPositionUpdaterRunnable = new Runnable() {
        public void run() {
            MediaControllerAdapter.this.getCallback().onCurrentPositionChanged(MediaControllerAdapter.this);
            MediaControllerAdapter.this.mHandler.postDelayed(this, (long) MediaControllerAdapter.this.getUpdatePeriod());
        }
    };

    /* access modifiers changed from: package-private */
    public int getUpdatePeriod() {
        return 16;
    }

    public MediaControllerAdapter(MediaControllerCompat controller) {
        if (controller != null) {
            this.mController = controller;
            return;
        }
        throw new NullPointerException("Object of MediaControllerCompat is null");
    }

    public MediaControllerCompat getMediaController() {
        return this.mController;
    }

    public void play() {
        this.mController.getTransportControls().play();
    }

    public void pause() {
        this.mController.getTransportControls().pause();
    }

    public void seekTo(long positionInMs) {
        this.mController.getTransportControls().seekTo(positionInMs);
    }

    public void next() {
        this.mController.getTransportControls().skipToNext();
    }

    public void previous() {
        this.mController.getTransportControls().skipToPrevious();
    }

    public void fastForward() {
        this.mController.getTransportControls().fastForward();
    }

    public void rewind() {
        this.mController.getTransportControls().rewind();
    }

    public void setRepeatAction(int repeatActionIndex) {
        this.mController.getTransportControls().setRepeatMode(mapRepeatActionToRepeatMode(repeatActionIndex));
    }

    public void setShuffleAction(int shuffleActionIndex) {
        this.mController.getTransportControls().setShuffleMode(mapShuffleActionToShuffleMode(shuffleActionIndex));
    }

    public boolean isPlaying() {
        if (this.mController.getPlaybackState() == null) {
            return false;
        }
        if (this.mController.getPlaybackState().getState() == 3 || this.mController.getPlaybackState().getState() == 4 || this.mController.getPlaybackState().getState() == 5) {
            return true;
        }
        return false;
    }

    public long getCurrentPosition() {
        if (this.mController.getPlaybackState() == null) {
            return 0;
        }
        return this.mController.getPlaybackState().getPosition();
    }

    public long getBufferedPosition() {
        if (this.mController.getPlaybackState() == null) {
            return 0;
        }
        return this.mController.getPlaybackState().getBufferedPosition();
    }

    public CharSequence getMediaTitle() {
        if (this.mController.getMetadata() == null) {
            return "";
        }
        return this.mController.getMetadata().getDescription().getTitle();
    }

    public CharSequence getMediaSubtitle() {
        if (this.mController.getMetadata() == null) {
            return "";
        }
        return this.mController.getMetadata().getDescription().getSubtitle();
    }

    public Drawable getMediaArt(Context context) {
        Bitmap bitmap;
        if (this.mController.getMetadata() == null || (bitmap = this.mController.getMetadata().getDescription().getIconBitmap()) == null) {
            return null;
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public long getDuration() {
        if (this.mController.getMetadata() == null) {
            return 0;
        }
        return (long) ((int) this.mController.getMetadata().getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
    }

    public void onAttachedToHost(PlaybackGlueHost host) {
        this.mController.registerCallback(this.mMediaControllerCallback);
    }

    public void onDetachedFromHost() {
        this.mController.unregisterCallback(this.mMediaControllerCallback);
    }

    public void setProgressUpdatingEnabled(boolean enabled) {
        this.mHandler.removeCallbacks(this.mPositionUpdaterRunnable);
        if (enabled) {
            this.mHandler.postDelayed(this.mPositionUpdaterRunnable, (long) getUpdatePeriod());
        }
    }

    public long getSupportedActions() {
        long supportedActions = 0;
        if (this.mController.getPlaybackState() == null) {
            return 0;
        }
        long actionsFromController = this.mController.getPlaybackState().getActions();
        if ((actionsFromController & 512) != 0) {
            supportedActions = 0 | 64;
        }
        if ((actionsFromController & 32) != 0) {
            supportedActions |= 256;
        }
        if ((actionsFromController & 16) != 0) {
            supportedActions |= 16;
        }
        if ((64 & actionsFromController) != 0) {
            supportedActions |= 128;
        }
        if ((8 & actionsFromController) != 0) {
            supportedActions |= 32;
        }
        if ((PlaybackStateCompat.ACTION_SET_REPEAT_MODE & actionsFromController) != 0) {
            supportedActions |= 512;
        }
        if ((PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE & actionsFromController) != 0) {
            return supportedActions | PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        }
        return supportedActions;
    }

    private int mapRepeatActionToRepeatMode(int repeatActionIndex) {
        if (repeatActionIndex == 0) {
            return 0;
        }
        if (repeatActionIndex == 1) {
            return 2;
        }
        if (repeatActionIndex != 2) {
            return -1;
        }
        return 1;
    }

    private int mapShuffleActionToShuffleMode(int shuffleActionIndex) {
        if (shuffleActionIndex == 0) {
            return 0;
        }
        if (shuffleActionIndex != 1) {
            return -1;
        }
        return 1;
    }
}
