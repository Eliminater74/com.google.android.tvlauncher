package androidx.leanback.app;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.widget.DetailsParallax;
import androidx.leanback.widget.Parallax;
import androidx.leanback.widget.ParallaxEffect;
import androidx.leanback.widget.ParallaxTarget;

final class DetailsBackgroundVideoHelper {
    private static final long BACKGROUND_CROSS_FADE_DURATION = 500;
    private static final long CROSSFADE_DELAY = 1000;
    static final int INITIAL = 0;
    static final int NO_VIDEO = 2;
    static final int PLAY_VIDEO = 1;
    ValueAnimator mBackgroundAnimator;
    Drawable mBackgroundDrawable;
    private boolean mBackgroundDrawableVisible;
    PlaybackControlStateCallback mControlStateCallback = new PlaybackControlStateCallback();
    private int mCurrentState = 0;
    private final DetailsParallax mDetailsParallax;
    private ParallaxEffect mParallaxEffect;
    private PlaybackGlue mPlaybackGlue;

    DetailsBackgroundVideoHelper(PlaybackGlue playbackGlue, DetailsParallax detailsParallax, Drawable backgroundDrawable) {
        this.mPlaybackGlue = playbackGlue;
        this.mDetailsParallax = detailsParallax;
        this.mBackgroundDrawable = backgroundDrawable;
        this.mBackgroundDrawableVisible = true;
        this.mBackgroundDrawable.setAlpha(255);
        startParallax();
    }

    /* access modifiers changed from: package-private */
    public void startParallax() {
        if (this.mParallaxEffect == null) {
            Parallax.IntProperty frameTop = this.mDetailsParallax.getOverviewRowTop();
            this.mParallaxEffect = this.mDetailsParallax.addEffect(frameTop.atFraction(1.0f), frameTop.atFraction(0.0f)).target(new ParallaxTarget() {
                public void update(float fraction) {
                    if (fraction == 1.0f) {
                        DetailsBackgroundVideoHelper.this.updateState(2);
                    } else {
                        DetailsBackgroundVideoHelper.this.updateState(1);
                    }
                }
            });
            this.mDetailsParallax.updateValues();
        }
    }

    /* access modifiers changed from: package-private */
    public void stopParallax() {
        this.mDetailsParallax.removeEffect(this.mParallaxEffect);
    }

    /* access modifiers changed from: package-private */
    public boolean isVideoVisible() {
        return this.mCurrentState == 1;
    }

    /* access modifiers changed from: package-private */
    public void updateState(int state) {
        if (state != this.mCurrentState) {
            this.mCurrentState = state;
            applyState();
        }
    }

    private void applyState() {
        int i = this.mCurrentState;
        if (i == 1) {
            PlaybackGlue playbackGlue = this.mPlaybackGlue;
            if (playbackGlue == null) {
                crossFadeBackgroundToVideo(false);
            } else if (playbackGlue.isPrepared()) {
                internalStartPlayback();
            } else {
                this.mPlaybackGlue.addPlayerCallback(this.mControlStateCallback);
            }
        } else if (i == 2) {
            crossFadeBackgroundToVideo(false);
            PlaybackGlue playbackGlue2 = this.mPlaybackGlue;
            if (playbackGlue2 != null) {
                playbackGlue2.removePlayerCallback(this.mControlStateCallback);
                this.mPlaybackGlue.pause();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setPlaybackGlue(PlaybackGlue playbackGlue) {
        PlaybackGlue playbackGlue2 = this.mPlaybackGlue;
        if (playbackGlue2 != null) {
            playbackGlue2.removePlayerCallback(this.mControlStateCallback);
        }
        this.mPlaybackGlue = playbackGlue;
        applyState();
    }

    /* access modifiers changed from: package-private */
    public void internalStartPlayback() {
        PlaybackGlue playbackGlue = this.mPlaybackGlue;
        if (playbackGlue != null) {
            playbackGlue.play();
        }
        this.mDetailsParallax.getRecyclerView().postDelayed(new Runnable() {
            public void run() {
                DetailsBackgroundVideoHelper.this.crossFadeBackgroundToVideo(true);
            }
        }, 1000);
    }

    /* access modifiers changed from: package-private */
    public void crossFadeBackgroundToVideo(boolean crossFadeToVideo) {
        crossFadeBackgroundToVideo(crossFadeToVideo, false);
    }

    /* access modifiers changed from: package-private */
    public void crossFadeBackgroundToVideo(boolean crossFadeToVideo, boolean immediate) {
        boolean newVisible = !crossFadeToVideo;
        int i = 255;
        if (this.mBackgroundDrawableVisible != newVisible) {
            this.mBackgroundDrawableVisible = newVisible;
            ValueAnimator valueAnimator = this.mBackgroundAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.mBackgroundAnimator = null;
            }
            float endAlpha = 1.0f;
            float startAlpha = crossFadeToVideo ? 1.0f : 0.0f;
            if (crossFadeToVideo) {
                endAlpha = 0.0f;
            }
            Drawable drawable = this.mBackgroundDrawable;
            if (drawable != null) {
                if (immediate) {
                    if (crossFadeToVideo) {
                        i = 0;
                    }
                    drawable.setAlpha(i);
                    return;
                }
                this.mBackgroundAnimator = ValueAnimator.ofFloat(startAlpha, endAlpha);
                this.mBackgroundAnimator.setDuration((long) BACKGROUND_CROSS_FADE_DURATION);
                this.mBackgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        DetailsBackgroundVideoHelper.this.mBackgroundDrawable.setAlpha((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f));
                    }
                });
                this.mBackgroundAnimator.addListener(new Animator.AnimatorListener() {
                    public void onAnimationStart(Animator animator) {
                    }

                    public void onAnimationEnd(Animator animator) {
                        DetailsBackgroundVideoHelper.this.mBackgroundAnimator = null;
                    }

                    public void onAnimationCancel(Animator animator) {
                    }

                    public void onAnimationRepeat(Animator animator) {
                    }
                });
                this.mBackgroundAnimator.start();
            }
        } else if (immediate) {
            ValueAnimator valueAnimator2 = this.mBackgroundAnimator;
            if (valueAnimator2 != null) {
                valueAnimator2.cancel();
                this.mBackgroundAnimator = null;
            }
            Drawable drawable2 = this.mBackgroundDrawable;
            if (drawable2 != null) {
                if (crossFadeToVideo) {
                    i = 0;
                }
                drawable2.setAlpha(i);
            }
        }
    }

    private class PlaybackControlStateCallback extends PlaybackGlue.PlayerCallback {
        PlaybackControlStateCallback() {
        }

        public void onPreparedStateChanged(PlaybackGlue glue) {
            if (glue.isPrepared()) {
                DetailsBackgroundVideoHelper.this.internalStartPlayback();
            }
        }
    }
}
