package androidx.leanback.app;

import android.animation.PropertyValuesHolder;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.app.Fragment;

import androidx.leanback.C0364R;
import androidx.leanback.graphics.FitWidthBitmapDrawable;
import androidx.leanback.media.PlaybackGlue;
import androidx.leanback.media.PlaybackGlueHost;
import androidx.leanback.widget.DetailsParallaxDrawable;
import androidx.leanback.widget.ParallaxTarget;

public class DetailsSupportFragmentBackgroundController {
    final DetailsSupportFragment mFragment;
    boolean mCanUseHost = false;
    Bitmap mCoverBitmap;
    boolean mInitialControlVisible = false;
    DetailsParallaxDrawable mParallaxDrawable;
    int mParallaxDrawableMaxOffset;
    PlaybackGlue mPlaybackGlue;
    int mSolidColor;
    DetailsBackgroundVideoHelper mVideoHelper;
    private Fragment mLastVideoSupportFragmentForGlueHost;

    public DetailsSupportFragmentBackgroundController(DetailsSupportFragment fragment) {
        if (fragment.mDetailsBackgroundController == null) {
            fragment.mDetailsBackgroundController = this;
            this.mFragment = fragment;
            return;
        }
        throw new IllegalStateException("Each DetailsSupportFragment is allowed to initialize DetailsSupportFragmentBackgroundController once");
    }

    public void enableParallax() {
        int offset = this.mParallaxDrawableMaxOffset;
        if (offset == 0) {
            offset = this.mFragment.getContext().getResources().getDimensionPixelSize(C0364R.dimen.lb_details_cover_drawable_parallax_movement);
        }
        Drawable coverDrawable = new FitWidthBitmapDrawable();
        enableParallax(coverDrawable, new ColorDrawable(), new ParallaxTarget.PropertyValuesHolderTarget(coverDrawable, PropertyValuesHolder.ofInt(FitWidthBitmapDrawable.PROPERTY_VERTICAL_OFFSET, 0, -offset)));
    }

    public void enableParallax(@NonNull Drawable coverDrawable, @NonNull Drawable bottomDrawable, @Nullable ParallaxTarget.PropertyValuesHolderTarget coverDrawableParallaxTarget) {
        if (this.mParallaxDrawable == null) {
            Bitmap bitmap = this.mCoverBitmap;
            if (bitmap != null && (coverDrawable instanceof FitWidthBitmapDrawable)) {
                ((FitWidthBitmapDrawable) coverDrawable).setBitmap(bitmap);
            }
            int i = this.mSolidColor;
            if (i != 0 && (bottomDrawable instanceof ColorDrawable)) {
                ((ColorDrawable) bottomDrawable).setColor(i);
            }
            if (this.mPlaybackGlue == null) {
                this.mParallaxDrawable = new DetailsParallaxDrawable(this.mFragment.getContext(), this.mFragment.getParallax(), coverDrawable, bottomDrawable, coverDrawableParallaxTarget);
                this.mFragment.setBackgroundDrawable(this.mParallaxDrawable);
                this.mVideoHelper = new DetailsBackgroundVideoHelper(null, this.mFragment.getParallax(), this.mParallaxDrawable.getCoverDrawable());
                return;
            }
            throw new IllegalStateException("enableParallaxDrawable must be called before enableVideoPlayback");
        }
    }

    public void setupVideoPlayback(@NonNull PlaybackGlue playbackGlue) {
        PlaybackGlue playbackGlue2 = this.mPlaybackGlue;
        if (playbackGlue2 != playbackGlue) {
            PlaybackGlueHost playbackGlueHost = null;
            if (playbackGlue2 != null) {
                playbackGlueHost = playbackGlue2.getHost();
                this.mPlaybackGlue.setHost(null);
            }
            this.mPlaybackGlue = playbackGlue;
            this.mVideoHelper.setPlaybackGlue(this.mPlaybackGlue);
            if (this.mCanUseHost && this.mPlaybackGlue != null) {
                if (playbackGlueHost == null || this.mLastVideoSupportFragmentForGlueHost != findOrCreateVideoSupportFragment()) {
                    this.mPlaybackGlue.setHost(createGlueHost());
                    this.mLastVideoSupportFragmentForGlueHost = findOrCreateVideoSupportFragment();
                    return;
                }
                this.mPlaybackGlue.setHost(playbackGlueHost);
            }
        }
    }

    public final PlaybackGlue getPlaybackGlue() {
        return this.mPlaybackGlue;
    }

    public boolean canNavigateToVideoSupportFragment() {
        return this.mPlaybackGlue != null;
    }

    /* access modifiers changed from: package-private */
    public void switchToVideoBeforeCreate() {
        this.mVideoHelper.crossFadeBackgroundToVideo(true, true);
        this.mInitialControlVisible = true;
    }

    public final void switchToVideo() {
        this.mFragment.switchToVideo();
    }

    public final void switchToRows() {
        this.mFragment.switchToRows();
    }

    /* access modifiers changed from: package-private */
    public void onStart() {
        if (!this.mCanUseHost) {
            this.mCanUseHost = true;
            PlaybackGlue playbackGlue = this.mPlaybackGlue;
            if (playbackGlue != null) {
                playbackGlue.setHost(createGlueHost());
                this.mLastVideoSupportFragmentForGlueHost = findOrCreateVideoSupportFragment();
            }
        }
        PlaybackGlue playbackGlue2 = this.mPlaybackGlue;
        if (playbackGlue2 != null && playbackGlue2.isPrepared()) {
            this.mPlaybackGlue.play();
        }
    }

    /* access modifiers changed from: package-private */
    public void onStop() {
        PlaybackGlue playbackGlue = this.mPlaybackGlue;
        if (playbackGlue != null) {
            playbackGlue.pause();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean disableVideoParallax() {
        DetailsBackgroundVideoHelper detailsBackgroundVideoHelper = this.mVideoHelper;
        if (detailsBackgroundVideoHelper == null) {
            return false;
        }
        detailsBackgroundVideoHelper.stopParallax();
        return this.mVideoHelper.isVideoVisible();
    }

    public final Drawable getCoverDrawable() {
        DetailsParallaxDrawable detailsParallaxDrawable = this.mParallaxDrawable;
        if (detailsParallaxDrawable == null) {
            return null;
        }
        return detailsParallaxDrawable.getCoverDrawable();
    }

    public final Drawable getBottomDrawable() {
        DetailsParallaxDrawable detailsParallaxDrawable = this.mParallaxDrawable;
        if (detailsParallaxDrawable == null) {
            return null;
        }
        return detailsParallaxDrawable.getBottomDrawable();
    }

    public Fragment onCreateVideoSupportFragment() {
        return new VideoSupportFragment();
    }

    public PlaybackGlueHost onCreateGlueHost() {
        return new VideoSupportFragmentGlueHost((VideoSupportFragment) findOrCreateVideoSupportFragment());
    }

    /* access modifiers changed from: package-private */
    public PlaybackGlueHost createGlueHost() {
        PlaybackGlueHost host = onCreateGlueHost();
        if (this.mInitialControlVisible) {
            host.showControlsOverlay(false);
        } else {
            host.hideControlsOverlay(false);
        }
        return host;
    }

    public final Fragment findOrCreateVideoSupportFragment() {
        return this.mFragment.findOrCreateVideoSupportFragment();
    }

    public final Bitmap getCoverBitmap() {
        return this.mCoverBitmap;
    }

    public final void setCoverBitmap(Bitmap bitmap) {
        this.mCoverBitmap = bitmap;
        Drawable drawable = getCoverDrawable();
        if (drawable instanceof FitWidthBitmapDrawable) {
            ((FitWidthBitmapDrawable) drawable).setBitmap(this.mCoverBitmap);
        }
    }

    @ColorInt
    public final int getSolidColor() {
        return this.mSolidColor;
    }

    public final void setSolidColor(@ColorInt int color) {
        this.mSolidColor = color;
        Drawable bottomDrawable = getBottomDrawable();
        if (bottomDrawable instanceof ColorDrawable) {
            ((ColorDrawable) bottomDrawable).setColor(color);
        }
    }

    public final int getParallaxDrawableMaxOffset() {
        return this.mParallaxDrawableMaxOffset;
    }

    public final void setParallaxDrawableMaxOffset(int offset) {
        if (this.mParallaxDrawable == null) {
            this.mParallaxDrawableMaxOffset = offset;
            return;
        }
        throw new IllegalStateException("enableParallax already called");
    }
}
