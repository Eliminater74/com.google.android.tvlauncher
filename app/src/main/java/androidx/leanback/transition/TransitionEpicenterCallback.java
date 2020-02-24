package androidx.leanback.transition;

import android.graphics.Rect;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public abstract class TransitionEpicenterCallback {
    public abstract Rect onGetEpicenter(Object obj);
}
