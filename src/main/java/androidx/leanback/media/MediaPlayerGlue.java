package androidx.leanback.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.RestrictTo;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.View;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.widget.Action;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.OnItemViewSelectedListener;
import androidx.leanback.widget.PlaybackControlsRow;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.IOException;
import java.util.List;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
@Deprecated
public class MediaPlayerGlue extends PlaybackControlGlue implements OnItemViewSelectedListener {
    public static final int FAST_FORWARD_REWIND_REPEAT_DELAY = 200;
    public static final int FAST_FORWARD_REWIND_STEP = 10000;
    public static final int NO_REPEAT = 0;
    public static final int REPEAT_ALL = 2;
    public static final int REPEAT_ONE = 1;
    private static final String TAG = "MediaPlayerGlue";
    private String mArtist;
    private Drawable mCover;
    Handler mHandler;
    boolean mInitialized;
    private long mLastKeyDownEvent;
    private String mMediaSourcePath;
    private Uri mMediaSourceUri;
    private MediaPlayer.OnCompletionListener mOnCompletionListener;
    MediaPlayer mPlayer;
    private final PlaybackControlsRow.RepeatAction mRepeatAction;
    private Runnable mRunnable;
    private Action mSelectedAction;
    protected final PlaybackControlsRow.ThumbsDownAction mThumbsDownAction;
    protected final PlaybackControlsRow.ThumbsUpAction mThumbsUpAction;
    private String mTitle;

    public void setCover(Drawable cover) {
        this.mCover = cover;
    }

    public void setArtist(String artist) {
        this.mArtist = artist;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setVideoUrl(String videoUrl) {
        setMediaSource(videoUrl);
        onMetadataChanged();
    }

    public MediaPlayerGlue(Context context) {
        this(context, new int[]{1}, new int[]{1});
    }

    public MediaPlayerGlue(Context context, int[] fastForwardSpeeds, int[] rewindSpeeds) {
        super(context, fastForwardSpeeds, rewindSpeeds);
        this.mPlayer = new MediaPlayer();
        this.mHandler = new Handler();
        this.mInitialized = false;
        this.mLastKeyDownEvent = 0;
        this.mMediaSourceUri = null;
        this.mMediaSourcePath = null;
        this.mRepeatAction = new PlaybackControlsRow.RepeatAction(getContext());
        this.mThumbsDownAction = new PlaybackControlsRow.ThumbsDownAction(getContext());
        this.mThumbsUpAction = new PlaybackControlsRow.ThumbsUpAction(getContext());
        this.mThumbsDownAction.setIndex(1);
        this.mThumbsUpAction.setIndex(1);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToHost(PlaybackGlueHost host) {
        super.onAttachedToHost(host);
        if (host instanceof SurfaceHolderGlueHost) {
            ((SurfaceHolderGlueHost) host).setSurfaceHolderCallback(new VideoPlayerSurfaceHolderCallback());
        }
    }

    public void reset() {
        changeToUnitialized();
        this.mPlayer.reset();
    }

    /* access modifiers changed from: package-private */
    public void changeToUnitialized() {
        if (this.mInitialized) {
            this.mInitialized = false;
            List<PlaybackGlue.PlayerCallback> callbacks = getPlayerCallbacks();
            if (callbacks != null) {
                for (PlaybackGlue.PlayerCallback callback : callbacks) {
                    callback.onPreparedStateChanged(this);
                }
            }
        }
    }

    public void release() {
        changeToUnitialized();
        this.mPlayer.release();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromHost() {
        if (getHost() instanceof SurfaceHolderGlueHost) {
            ((SurfaceHolderGlueHost) getHost()).setSurfaceHolderCallback(null);
        }
        reset();
        release();
        super.onDetachedFromHost();
    }

    /* access modifiers changed from: protected */
    public void onCreateSecondaryActions(ArrayObjectAdapter secondaryActionsAdapter) {
        secondaryActionsAdapter.add(this.mRepeatAction);
        secondaryActionsAdapter.add(this.mThumbsDownAction);
        secondaryActionsAdapter.add(this.mThumbsUpAction);
    }

    public void setDisplay(SurfaceHolder surfaceHolder) {
        this.mPlayer.setDisplay(surfaceHolder);
    }

    public void enableProgressUpdating(boolean enabled) {
        Runnable runnable = this.mRunnable;
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
        }
        if (enabled) {
            if (this.mRunnable == null) {
                this.mRunnable = new Runnable() {
                    public void run() {
                        MediaPlayerGlue.this.updateProgress();
                        MediaPlayerGlue.this.mHandler.postDelayed(this, (long) MediaPlayerGlue.this.getUpdatePeriod());
                    }
                };
            }
            this.mHandler.postDelayed(this.mRunnable, (long) getUpdatePeriod());
        }
    }

    public void onActionClicked(Action action) {
        super.onActionClicked(action);
        if (action instanceof PlaybackControlsRow.RepeatAction) {
            ((PlaybackControlsRow.RepeatAction) action).nextIndex();
        } else {
            PlaybackControlsRow.ThumbsUpAction thumbsUpAction = this.mThumbsUpAction;
            if (action != thumbsUpAction) {
                PlaybackControlsRow.ThumbsDownAction thumbsDownAction = this.mThumbsDownAction;
                if (action == thumbsDownAction) {
                    if (thumbsDownAction.getIndex() == 0) {
                        this.mThumbsDownAction.setIndex(1);
                    } else {
                        this.mThumbsDownAction.setIndex(0);
                        this.mThumbsUpAction.setIndex(1);
                    }
                }
            } else if (thumbsUpAction.getIndex() == 0) {
                this.mThumbsUpAction.setIndex(1);
            } else {
                this.mThumbsUpAction.setIndex(0);
                this.mThumbsDownAction.setIndex(1);
            }
        }
        onMetadataChanged();
    }

    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Action action = this.mSelectedAction;
        boolean consume = false;
        if ((((((action instanceof PlaybackControlsRow.RewindAction) || (action instanceof PlaybackControlsRow.FastForwardAction)) && this.mInitialized) && event.getKeyCode() == 23) && event.getAction() == 0) && System.currentTimeMillis() - this.mLastKeyDownEvent > 200) {
            consume = true;
        }
        if (!consume) {
            return super.onKey(v, keyCode, event);
        }
        this.mLastKeyDownEvent = System.currentTimeMillis();
        int newPosition = getCurrentPosition() + 10000;
        if (this.mSelectedAction instanceof PlaybackControlsRow.RewindAction) {
            newPosition = getCurrentPosition() - 10000;
        }
        if (newPosition < 0) {
            newPosition = 0;
        }
        if (newPosition > getMediaDuration()) {
            newPosition = getMediaDuration();
        }
        seekTo(newPosition);
        return true;
    }

    public boolean hasValidMedia() {
        return (this.mTitle == null || (this.mMediaSourcePath == null && this.mMediaSourceUri == null)) ? false : true;
    }

    public boolean isMediaPlaying() {
        return this.mInitialized && this.mPlayer.isPlaying();
    }

    public boolean isPlaying() {
        return isMediaPlaying();
    }

    public CharSequence getMediaTitle() {
        String str = this.mTitle;
        return str != null ? str : "N/a";
    }

    public CharSequence getMediaSubtitle() {
        String str = this.mArtist;
        return str != null ? str : "N/a";
    }

    public int getMediaDuration() {
        if (this.mInitialized) {
            return this.mPlayer.getDuration();
        }
        return 0;
    }

    public Drawable getMediaArt() {
        return this.mCover;
    }

    public long getSupportedActions() {
        return 224;
    }

    public int getCurrentSpeedId() {
        return isMediaPlaying() ? 1 : 0;
    }

    public int getCurrentPosition() {
        if (this.mInitialized) {
            return this.mPlayer.getCurrentPosition();
        }
        return 0;
    }

    public void play(int speed) {
        if (this.mInitialized && !this.mPlayer.isPlaying()) {
            this.mPlayer.start();
            onMetadataChanged();
            onStateChanged();
            updateProgress();
        }
    }

    public void pause() {
        if (isMediaPlaying()) {
            this.mPlayer.pause();
            onStateChanged();
        }
    }

    public void setMode(int mode) {
        if (mode == 0) {
            this.mOnCompletionListener = null;
        } else if (mode == 1) {
            this.mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
                public boolean mFirstRepeat;

                public void onCompletion(MediaPlayer mediaPlayer) {
                    if (!this.mFirstRepeat) {
                        this.mFirstRepeat = true;
                        mediaPlayer.setOnCompletionListener(null);
                    }
                    MediaPlayerGlue.this.play();
                }
            };
        } else if (mode == 2) {
            this.mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mediaPlayer) {
                    MediaPlayerGlue.this.play();
                }
            };
        }
    }

    /* access modifiers changed from: protected */
    public void seekTo(int newPosition) {
        if (this.mInitialized) {
            this.mPlayer.seekTo(newPosition);
        }
    }

    public boolean setMediaSource(Uri uri) {
        Uri uri2 = this.mMediaSourceUri;
        if (uri2 != null) {
            if (uri2.equals(uri)) {
                return false;
            }
        } else if (uri == null) {
            return false;
        }
        this.mMediaSourceUri = uri;
        this.mMediaSourcePath = null;
        prepareMediaForPlaying();
        return true;
    }

    public boolean setMediaSource(String path) {
        String str = this.mMediaSourcePath;
        if (str != null) {
            if (str.equals(path)) {
                return false;
            }
        } else if (path == null) {
            return false;
        }
        this.mMediaSourceUri = null;
        this.mMediaSourcePath = path;
        prepareMediaForPlaying();
        return true;
    }

    private void prepareMediaForPlaying() {
        reset();
        try {
            if (this.mMediaSourceUri != null) {
                this.mPlayer.setDataSource(getContext(), this.mMediaSourceUri);
            } else if (this.mMediaSourcePath != null) {
                this.mPlayer.setDataSource(this.mMediaSourcePath);
            } else {
                return;
            }
            this.mPlayer.setAudioStreamType(3);
            this.mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    MediaPlayerGlue mediaPlayerGlue = MediaPlayerGlue.this;
                    mediaPlayerGlue.mInitialized = true;
                    List<PlaybackGlue.PlayerCallback> callbacks = mediaPlayerGlue.getPlayerCallbacks();
                    if (callbacks != null) {
                        for (PlaybackGlue.PlayerCallback callback : callbacks) {
                            callback.onPreparedStateChanged(MediaPlayerGlue.this);
                        }
                    }
                }
            });
            MediaPlayer.OnCompletionListener onCompletionListener = this.mOnCompletionListener;
            if (onCompletionListener != null) {
                this.mPlayer.setOnCompletionListener(onCompletionListener);
            }
            this.mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    if (MediaPlayerGlue.this.getControlsRow() != null) {
                        MediaPlayerGlue.this.getControlsRow().setBufferedProgress((int) (((float) mp.getDuration()) * (((float) percent) / 100.0f)));
                    }
                }
            });
            this.mPlayer.prepareAsync();
            onStateChanged();
        } catch (IOException e) {
            ThrowableExtension.printStackTrace(e);
            throw new RuntimeException(e);
        }
    }

    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        if (item instanceof Action) {
            this.mSelectedAction = (Action) item;
        } else {
            this.mSelectedAction = null;
        }
    }

    public boolean isPrepared() {
        return this.mInitialized;
    }

    class VideoPlayerSurfaceHolderCallback implements SurfaceHolder.Callback {
        VideoPlayerSurfaceHolderCallback() {
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            MediaPlayerGlue.this.setDisplay(surfaceHolder);
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            MediaPlayerGlue.this.setDisplay(null);
        }
    }
}
