package com.google.android.tvlauncher.home.view;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.support.annotation.NonNull;
import android.support.p001v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvrecommendations.shared.util.ColorUtils;

public class ProgramView extends FrameLayout {
    private TextView mDurationBadge;
    private int mDurationBadgeVisibility;
    private TextView mLiveBadge;
    private int mLiveBadgeVisibility;
    private ImageView mLiveIcon;
    private int mLiveIconVisibility;
    private ImageView mLogo;
    private View mLogoAndBadgesContainer;
    private View mLogoDimmer;
    private int mLogoDimmerVisibility;
    private int mLogoVisibility;
    private OnWindowVisibilityChangedListener mOnWindowVisibilityChangedListener;
    private ProgressBar mPlaybackProgress;
    private View mPlaybackProgressDimmer;
    private View mPreviewDelayProgress;
    private ImageView mPreviewImage;
    private ImageView mPreviewImageBackground;
    private View mPreviewImageContainer;
    private float mPreviewImageCurrentDimmingFactor;
    private float mPreviewImageDimmedFactorValue;
    private View mPreviewVideo;

    public interface OnWindowVisibilityChangedListener {
        void onWindowVisibilityChanged(int i);
    }

    public ProgramView(@NonNull Context context) {
        this(context, null);
    }

    public ProgramView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /* access modifiers changed from: protected */
    public void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        OnWindowVisibilityChangedListener onWindowVisibilityChangedListener = this.mOnWindowVisibilityChangedListener;
        if (onWindowVisibilityChangedListener != null) {
            onWindowVisibilityChangedListener.onWindowVisibilityChanged(visibility);
        }
    }

    public void setOnWindowVisibilityChangedListener(OnWindowVisibilityChangedListener listener) {
        this.mOnWindowVisibilityChangedListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mPreviewImageContainer = findViewById(C1188R.C1191id.preview_image_container);
        this.mPreviewImageBackground = (ImageView) findViewById(C1188R.C1191id.preview_image_background);
        this.mPreviewImage = (ImageView) findViewById(C1188R.C1191id.preview_image);
        this.mPreviewVideo = findViewById(C1188R.C1191id.preview_video_view);
        this.mPreviewDelayProgress = findViewById(C1188R.C1191id.preview_delay_progress);
        this.mPlaybackProgress = (ProgressBar) findViewById(C1188R.C1191id.playback_progress);
        this.mPlaybackProgressDimmer = findViewById(C1188R.C1191id.program_playback_progress_dimmer);
        this.mLogoAndBadgesContainer = findViewById(C1188R.C1191id.program_logo_and_badges_container);
        this.mLogo = (ImageView) findViewById(C1188R.C1191id.program_card_logo);
        this.mLogoVisibility = this.mLogo.getVisibility();
        if (Util.isRtl(getContext())) {
            this.mLogo.setScaleType(ImageView.ScaleType.FIT_END);
        }
        this.mLogoDimmer = findViewById(C1188R.C1191id.program_logo_dimmer);
        this.mLogoDimmerVisibility = this.mLogoDimmer.getVisibility();
        this.mLiveBadge = (TextView) findViewById(C1188R.C1191id.program_live_badge);
        this.mLiveBadgeVisibility = this.mLiveBadge.getVisibility();
        this.mLiveIcon = (ImageView) findViewById(C1188R.C1191id.program_live_icon);
        this.mLiveIconVisibility = this.mLiveIcon.getVisibility();
        this.mDurationBadge = (TextView) findViewById(C1188R.C1191id.program_duration_badge);
        this.mDurationBadgeVisibility = this.mDurationBadge.getVisibility();
        View badgesContainer = (View) this.mLiveBadge.getParent();
        badgesContainer.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) view.getResources().getDimensionPixelSize(C1188R.dimen.program_badge_background_corner_radius));
            }
        });
        badgesContainer.setClipToOutline(true);
        this.mPreviewImageDimmedFactorValue = Util.getFloat(getResources(), C1188R.dimen.unfocused_channel_dimming_factor);
    }

    public View getPreviewImageContainer() {
        return this.mPreviewImageContainer;
    }

    public ImageView getPreviewImageBackground() {
        return this.mPreviewImageBackground;
    }

    public ImageView getPreviewImage() {
        return this.mPreviewImage;
    }

    public void setPreviewImageDimmed(boolean dimmed) {
        if (dimmed) {
            this.mPreviewImageCurrentDimmingFactor = this.mPreviewImageDimmedFactorValue;
            this.mPreviewImage.setColorFilter(ColorUtils.getColorFilter(ViewCompat.MEASURED_STATE_MASK, this.mPreviewImageCurrentDimmingFactor));
            return;
        }
        this.mPreviewImageCurrentDimmingFactor = 0.0f;
        this.mPreviewImage.setColorFilter((ColorFilter) null);
    }

    public float getPreviewImageDimmingFactor() {
        return this.mPreviewImageCurrentDimmingFactor;
    }

    /* access modifiers changed from: package-private */
    public View getPreviewDelayProgress() {
        return this.mPreviewDelayProgress;
    }

    public View getPreviewVideo() {
        return this.mPreviewVideo;
    }

    public ProgressBar getPlaybackProgress() {
        return this.mPlaybackProgress;
    }

    public View getPlaybackProgressDimmer() {
        return this.mPlaybackProgressDimmer;
    }

    public View getLogoAndBadgesContainer() {
        return this.mLogoAndBadgesContainer;
    }

    public ImageView getLogo() {
        return this.mLogo;
    }

    /* access modifiers changed from: package-private */
    public int getLogoVisibility() {
        return this.mLogoVisibility;
    }

    public void setLogoVisibility(int logoVisibility) {
        this.mLogoVisibility = logoVisibility;
        this.mLogo.setVisibility(logoVisibility);
    }

    /* access modifiers changed from: package-private */
    public View getLogoDimmer() {
        return this.mLogoDimmer;
    }

    /* access modifiers changed from: package-private */
    public int getLogoDimmerVisibility() {
        return this.mLogoDimmerVisibility;
    }

    public void setLogoDimmerVisibility(int visibility) {
        this.mLogoDimmerVisibility = visibility;
        this.mLogoDimmer.setVisibility(visibility);
    }

    public TextView getLiveBadge() {
        return this.mLiveBadge;
    }

    /* access modifiers changed from: package-private */
    public int getLiveBadgeVisibility() {
        return this.mLiveBadgeVisibility;
    }

    public void setLiveBadgeVisibility(int visibility) {
        this.mLiveBadgeVisibility = visibility;
        this.mLiveBadge.setVisibility(visibility);
    }

    public ImageView getLiveIcon() {
        return this.mLiveIcon;
    }

    /* access modifiers changed from: package-private */
    public int getLiveIconVisibility() {
        return this.mLiveIconVisibility;
    }

    public void setLiveIconVisibility(int visibility) {
        this.mLiveIconVisibility = visibility;
        this.mLiveIcon.setVisibility(visibility);
    }

    public TextView getDurationBadge() {
        return this.mDurationBadge;
    }

    /* access modifiers changed from: package-private */
    public int getDurationBadgeVisibility() {
        return this.mDurationBadgeVisibility;
    }

    public void setDurationBadgeVisibility(int visibility) {
        this.mDurationBadgeVisibility = visibility;
        this.mDurationBadge.setVisibility(visibility);
    }
}
