package androidx.leanback.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.leanback.C0364R;

import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class MediaNowPlayingView extends LinearLayout {
    protected final LinearInterpolator mLinearInterpolator = new LinearInterpolator();
    private final ImageView mImage1;
    private final ImageView mImage2;
    private final ImageView mImage3;
    private final ObjectAnimator mObjectAnimator1;
    private final ObjectAnimator mObjectAnimator2;
    private final ObjectAnimator mObjectAnimator3;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
     arg types: [int, androidx.leanback.widget.MediaNowPlayingView, int]
     candidates:
      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
    public MediaNowPlayingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(C0364R.layout.lb_playback_now_playing_bars, (ViewGroup) this, true);
        this.mImage1 = (ImageView) findViewById(C0364R.C0366id.bar1);
        this.mImage2 = (ImageView) findViewById(C0364R.C0366id.bar2);
        this.mImage3 = (ImageView) findViewById(C0364R.C0366id.bar3);
        ImageView imageView = this.mImage1;
        imageView.setPivotY((float) imageView.getDrawable().getIntrinsicHeight());
        ImageView imageView2 = this.mImage2;
        imageView2.setPivotY((float) imageView2.getDrawable().getIntrinsicHeight());
        ImageView imageView3 = this.mImage3;
        imageView3.setPivotY((float) imageView3.getDrawable().getIntrinsicHeight());
        setDropScale(this.mImage1);
        setDropScale(this.mImage2);
        setDropScale(this.mImage3);
        this.mObjectAnimator1 = ObjectAnimator.ofFloat(this.mImage1, "scaleY", 0.41666666f, 0.25f, 0.41666666f, 0.5833333f, 0.75f, 0.8333333f, 0.9166667f, 1.0f, 0.9166667f, 1.0f, 0.8333333f, 0.6666667f, 0.5f, 0.33333334f, 0.16666667f, 0.33333334f, 0.5f, 0.5833333f, 0.75f, 0.9166667f, 0.75f, 0.5833333f, 0.41666666f, 0.25f, 0.41666666f, 0.6666667f, 0.41666666f, 0.25f, 0.33333334f, 0.41666666f);
        this.mObjectAnimator1.setRepeatCount(-1);
        this.mObjectAnimator1.setDuration(2320L);
        this.mObjectAnimator1.setInterpolator(this.mLinearInterpolator);
        this.mObjectAnimator2 = ObjectAnimator.ofFloat(this.mImage2, "scaleY", 1.0f, 0.9166667f, 0.8333333f, 0.9166667f, 1.0f, 0.9166667f, 0.75f, 0.5833333f, 0.75f, 0.9166667f, 1.0f, 0.8333333f, 0.6666667f, 0.8333333f, 1.0f, 0.9166667f, 0.75f, 0.41666666f, 0.25f, 0.41666666f, 0.6666667f, 0.8333333f, 1.0f, 0.8333333f, 0.75f, 0.6666667f, 1.0f);
        this.mObjectAnimator2.setRepeatCount(-1);
        this.mObjectAnimator2.setDuration(2080L);
        this.mObjectAnimator2.setInterpolator(this.mLinearInterpolator);
        this.mObjectAnimator3 = ObjectAnimator.ofFloat(this.mImage3, "scaleY", 0.6666667f, 0.75f, 0.8333333f, 1.0f, 0.9166667f, 0.75f, 0.5833333f, 0.41666666f, 0.5833333f, 0.6666667f, 0.75f, 1.0f, 0.9166667f, 1.0f, 0.75f, 0.5833333f, 0.75f, 0.9166667f, 1.0f, 0.8333333f, 0.6666667f, 0.75f, 0.5833333f, 0.41666666f, 0.25f, 0.6666667f);
        this.mObjectAnimator3.setRepeatCount(-1);
        this.mObjectAnimator3.setDuration((long) AdaptiveTrackSelection.DEFAULT_MIN_TIME_BETWEEN_BUFFER_REEVALUTATION_MS);
        this.mObjectAnimator3.setInterpolator(this.mLinearInterpolator);
    }

    static void setDropScale(View view) {
        view.setScaleY(0.083333336f);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 8) {
            stopAnimation();
        } else {
            startAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == 0) {
            startAnimation();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimation();
    }

    private void startAnimation() {
        startAnimation(this.mObjectAnimator1);
        startAnimation(this.mObjectAnimator2);
        startAnimation(this.mObjectAnimator3);
        this.mImage1.setVisibility(0);
        this.mImage2.setVisibility(0);
        this.mImage3.setVisibility(0);
    }

    private void stopAnimation() {
        stopAnimation(this.mObjectAnimator1, this.mImage1);
        stopAnimation(this.mObjectAnimator2, this.mImage2);
        stopAnimation(this.mObjectAnimator3, this.mImage3);
        this.mImage1.setVisibility(8);
        this.mImage2.setVisibility(8);
        this.mImage3.setVisibility(8);
    }

    private void startAnimation(Animator animator) {
        if (!animator.isStarted()) {
            animator.start();
        }
    }

    private void stopAnimation(Animator animator, View view) {
        if (animator.isStarted()) {
            animator.cancel();
            setDropScale(view);
        }
    }
}
