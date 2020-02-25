package com.google.android.tvlauncher.instantvideo.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.tvlauncher.instantvideo.media.MediaPlayer;
import com.google.android.tvlauncher.instantvideo.preload.InstantVideoPreloadManager;

public class InstantVideoView extends FrameLayout {
    private static final boolean DEBUG = false;
    private static final long FADE_OUT_DURATION_MS = 2000;
    private static final String TAG = "InstantVideoView";
    private static final int VIDEO_IDLE = 0;
    private static final int VIDEO_PREPARING = 1;
    private static final int VIDEO_SHOWN = 2;
    /* access modifiers changed from: private */
    public ImageView mImageView;
    /* access modifiers changed from: private */
    public ViewPropertyAnimator mImageViewFadeOut;
    /* access modifiers changed from: private */
    public boolean mVideoStarted;
    private MediaPlayer mPlayer;
    private Runnable mStopVideoRunnable;
    private Uri mVideoUri;
    private View mVideoView;
    private float mVolume;

    public InstantVideoView(Context context) {
        this(context, null, 0);
    }

    public InstantVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InstantVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mVolume = 1.0f;
        this.mStopVideoRunnable = new Runnable() {
            public void run() {
                InstantVideoView.this.stopVideoInternal();
            }
        };
        setImageViewEnabled(true);
        setDisplayedChild(0);
    }

    public Uri getVideoUri() {
        return this.mVideoUri;
    }

    public void setVideoUri(Uri uri) {
        this.mVideoUri = uri;
    }

    public void setImageDrawable(Drawable drawable) {
        this.mImageView.setImageDrawable(drawable);
    }

    public ImageView getImageView() {
        return this.mImageView;
    }

    public void setVolume(float volume) {
        this.mVolume = volume;
        MediaPlayer mediaPlayer = this.mPlayer;
        if (mediaPlayer != null && this.mVideoStarted) {
            mediaPlayer.setVolume(volume);
        }
    }

    public void setImageViewEnabled(boolean enable) {
        if (enable && this.mImageView == null) {
            this.mImageView = new ImageView(getContext());
            addView(this.mImageView, new FrameLayout.LayoutParams(-1, -1, 17));
        } else if (!enable && this.mImageView != null) {
            ViewPropertyAnimator viewPropertyAnimator = this.mImageViewFadeOut;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
                this.mImageViewFadeOut = null;
            }
            removeView(this.mImageView);
            this.mImageView = null;
        }
    }

    public void start(final VideoCallback callback) {
        if (!this.mVideoStarted) {
            if (this.mVideoView != null) {
                stopVideoInternal();
            }
            this.mVideoStarted = true;
            this.mPlayer = InstantVideoPreloadManager.getInstance(getContext()).getOrCreatePlayer(this.mVideoUri);
            MediaPlayer mediaPlayer = this.mPlayer;
            if (mediaPlayer == null) {
                this.mVideoStarted = false;
                if (callback != null) {
                    callback.onVideoError(this);
                    return;
                }
                return;
            }
            this.mVideoView = mediaPlayer.getPlayerView();
            addView(this.mVideoView, new FrameLayout.LayoutParams(-1, -1, 17));
            ImageView imageView = this.mImageView;
            if (imageView != null) {
                bringChildToFront(imageView);
            }
            if (!(getWidth() == 0 || getHeight() == 0)) {
                this.mPlayer.setDisplaySize(getWidth(), getHeight());
            }
            setDisplayedChild(1);
            this.mPlayer.prepare();
            this.mPlayer.setPlayWhenReady(true);
            this.mPlayer.setVolume(this.mVolume);
            this.mPlayer.setVideoCallback(new MediaPlayer.VideoCallback() {
                public void onVideoAvailable() {
                    if (InstantVideoView.this.mVideoStarted) {
                        InstantVideoView.this.setDisplayedChild(2);
                        VideoCallback videoCallback = callback;
                        if (videoCallback != null) {
                            videoCallback.onVideoStarted(InstantVideoView.this);
                        }
                    }
                }

                public void onVideoError() {
                    VideoCallback videoCallback;
                    if (InstantVideoView.this.mVideoStarted && (videoCallback = callback) != null) {
                        videoCallback.onVideoError(InstantVideoView.this);
                    }
                }

                public void onVideoEnded() {
                    VideoCallback videoCallback;
                    if (InstantVideoView.this.mVideoStarted && (videoCallback = callback) != null) {
                        videoCallback.onVideoEnded(InstantVideoView.this);
                    }
                }
            });
        }
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        MediaPlayer mediaPlayer;
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        if (width != 0 && height != 0 && (mediaPlayer = this.mPlayer) != null) {
            mediaPlayer.setDisplaySize(width, height);
        }
    }

    /* access modifiers changed from: private */
    public void setDisplayedChild(int videoState) {
        if (videoState == 0) {
            ViewPropertyAnimator viewPropertyAnimator = this.mImageViewFadeOut;
            if (viewPropertyAnimator != null) {
                viewPropertyAnimator.cancel();
                this.mImageViewFadeOut = null;
            }
            ImageView imageView = this.mImageView;
            if (imageView != null) {
                imageView.setVisibility(0);
                this.mImageView.setAlpha(1.0f);
            }
            View view = this.mVideoView;
            if (view != null) {
                view.setVisibility(8);
            }
        } else if (videoState == 1) {
            View view2 = this.mVideoView;
            if (view2 != null) {
                view2.setVisibility(0);
                this.mVideoView.setAlpha(0.0f);
            }
        } else {
            ImageView imageView2 = this.mImageView;
            if (imageView2 != null) {
                this.mImageViewFadeOut = imageView2.animate();
                this.mImageViewFadeOut.alpha(0.0f).setDuration(2000).setInterpolator(new DecelerateInterpolator()).setListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        ViewPropertyAnimator unused = InstantVideoView.this.mImageViewFadeOut = null;
                        InstantVideoView.this.mImageView.setVisibility(8);
                    }
                }).start();
            }
            View view3 = this.mVideoView;
            if (view3 != null) {
                view3.setVisibility(0);
                this.mVideoView.setAlpha(1.0f);
            }
        }
    }

    public void stop() {
        if (this.mVideoStarted) {
            this.mVideoStarted = false;
            this.mPlayer.setVideoCallback(null);
            setDisplayedChild(0);
            post(this.mStopVideoRunnable);
        }
    }

    public void seekTo(int positionMs) {
        this.mPlayer.seekTo(positionMs);
    }

    public int getCurrentPosition() {
        return this.mPlayer.getCurrentPosition();
    }

    public boolean isPlaying() {
        return this.mVideoStarted;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    /* access modifiers changed from: private */
    public void stopVideoInternal() {
        removeCallbacks(this.mStopVideoRunnable);
        if (this.mVideoView != null) {
            this.mPlayer.setVideoCallback(null);
            this.mPlayer.stop();
            InstantVideoPreloadManager.getInstance(getContext()).recyclePlayer(this.mPlayer, this.mVideoUri);
            this.mPlayer = null;
            removeView(this.mVideoView);
            this.mVideoView = null;
        }
    }

    public static abstract class VideoCallback {
        public void onVideoError(InstantVideoView view) {
        }

        public void onVideoEnded(InstantVideoView view) {
        }

        public void onVideoStarted(InstantVideoView view) {
        }
    }
}
