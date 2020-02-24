package androidx.leanback.preference.internal;

import android.content.Context;
import android.graphics.Outline;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class OutlineOnlyWithChildrenFrameLayout extends FrameLayout {
    ViewOutlineProvider mInnerOutlineProvider;
    private ViewOutlineProvider mMagicalOutlineProvider;

    public OutlineOnlyWithChildrenFrameLayout(Context context) {
        super(context);
    }

    public OutlineOnlyWithChildrenFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OutlineOnlyWithChildrenFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OutlineOnlyWithChildrenFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        invalidateOutline();
    }

    public void setOutlineProvider(ViewOutlineProvider provider) {
        this.mInnerOutlineProvider = provider;
        if (this.mMagicalOutlineProvider == null) {
            this.mMagicalOutlineProvider = new ViewOutlineProvider() {
                public void getOutline(View view, Outline outline) {
                    if (OutlineOnlyWithChildrenFrameLayout.this.getChildCount() > 0) {
                        OutlineOnlyWithChildrenFrameLayout.this.mInnerOutlineProvider.getOutline(view, outline);
                    } else {
                        ViewOutlineProvider.BACKGROUND.getOutline(view, outline);
                    }
                }
            };
        }
        super.setOutlineProvider(this.mMagicalOutlineProvider);
    }
}
