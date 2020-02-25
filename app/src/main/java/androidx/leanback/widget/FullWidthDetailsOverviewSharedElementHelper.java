package androidx.leanback.widget;

import android.app.Activity;
import android.os.Handler;
import android.support.p001v4.app.ActivityCompat;
import android.support.p001v4.view.ViewCompat;
import android.text.TextUtils;

import androidx.leanback.transition.TransitionHelper;
import androidx.leanback.transition.TransitionListener;

import java.lang.ref.WeakReference;

public class FullWidthDetailsOverviewSharedElementHelper extends FullWidthDetailsOverviewRowPresenter.Listener {
    static final boolean DEBUG = false;
    static final String TAG = "DetailsTransitionHelper";
    private static final long DEFAULT_TIMEOUT = 5000;
    Activity mActivityToRunTransition;
    String mSharedElementName;
    FullWidthDetailsOverviewRowPresenter.ViewHolder mViewHolder;
    private boolean mAutoStartSharedElementTransition = true;
    private boolean mStartedPostpone;

    public void setSharedElementEnterTransition(Activity activity, String sharedElementName) {
        setSharedElementEnterTransition(activity, sharedElementName, 5000);
    }

    public void setSharedElementEnterTransition(Activity activity, String sharedElementName, long timeoutMs) {
        if ((activity == null && !TextUtils.isEmpty(sharedElementName)) || (activity != null && TextUtils.isEmpty(sharedElementName))) {
            throw new IllegalArgumentException();
        } else if (activity != this.mActivityToRunTransition || !TextUtils.equals(sharedElementName, this.mSharedElementName)) {
            this.mActivityToRunTransition = activity;
            this.mSharedElementName = sharedElementName;
            setAutoStartSharedElementTransition(TransitionHelper.getSharedElementEnterTransition(activity.getWindow()) != null);
            ActivityCompat.postponeEnterTransition(this.mActivityToRunTransition);
            if (timeoutMs > 0) {
                new Handler().postDelayed(new TransitionTimeOutRunnable(this), timeoutMs);
            }
        }
    }

    public boolean getAutoStartSharedElementTransition() {
        return this.mAutoStartSharedElementTransition;
    }

    public void setAutoStartSharedElementTransition(boolean enabled) {
        this.mAutoStartSharedElementTransition = enabled;
    }

    public void onBindLogo(FullWidthDetailsOverviewRowPresenter.ViewHolder vh) {
        this.mViewHolder = vh;
        if (this.mAutoStartSharedElementTransition) {
            FullWidthDetailsOverviewRowPresenter.ViewHolder viewHolder = this.mViewHolder;
            if (viewHolder != null) {
                ViewCompat.setTransitionName(viewHolder.getLogoViewHolder().view, null);
            }
            this.mViewHolder.getDetailsDescriptionFrame().postOnAnimation(new Runnable() {
                public void run() {
                    ViewCompat.setTransitionName(FullWidthDetailsOverviewSharedElementHelper.this.mViewHolder.getLogoViewHolder().view, FullWidthDetailsOverviewSharedElementHelper.this.mSharedElementName);
                    Object transition = TransitionHelper.getSharedElementEnterTransition(FullWidthDetailsOverviewSharedElementHelper.this.mActivityToRunTransition.getWindow());
                    if (transition != null) {
                        TransitionHelper.addTransitionListener(transition, new TransitionListener() {
                            public void onTransitionEnd(Object transition) {
                                if (FullWidthDetailsOverviewSharedElementHelper.this.mViewHolder.getActionsRow().isFocused()) {
                                    FullWidthDetailsOverviewSharedElementHelper.this.mViewHolder.getActionsRow().requestFocus();
                                }
                                TransitionHelper.removeTransitionListener(transition, this);
                            }
                        });
                    }
                    FullWidthDetailsOverviewSharedElementHelper.this.startPostponedEnterTransitionInternal();
                }
            });
        }
    }

    public void startPostponedEnterTransition() {
        new Handler().post(new Runnable() {
            public void run() {
                FullWidthDetailsOverviewSharedElementHelper.this.startPostponedEnterTransitionInternal();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void startPostponedEnterTransitionInternal() {
        if (!this.mStartedPostpone && this.mViewHolder != null) {
            ActivityCompat.startPostponedEnterTransition(this.mActivityToRunTransition);
            this.mStartedPostpone = true;
        }
    }

    static class TransitionTimeOutRunnable implements Runnable {
        WeakReference<FullWidthDetailsOverviewSharedElementHelper> mHelperRef;

        TransitionTimeOutRunnable(FullWidthDetailsOverviewSharedElementHelper helper) {
            this.mHelperRef = new WeakReference<>(helper);
        }

        public void run() {
            FullWidthDetailsOverviewSharedElementHelper helper = this.mHelperRef.get();
            if (helper != null) {
                helper.startPostponedEnterTransition();
            }
        }
    }
}
