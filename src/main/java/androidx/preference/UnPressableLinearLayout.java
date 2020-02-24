package androidx.preference;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.util.AttributeSet;
import android.widget.LinearLayout;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class UnPressableLinearLayout extends LinearLayout {
    public UnPressableLinearLayout(Context context) {
        this(context, null);
    }

    public UnPressableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void dispatchSetPressed(boolean pressed) {
    }
}
