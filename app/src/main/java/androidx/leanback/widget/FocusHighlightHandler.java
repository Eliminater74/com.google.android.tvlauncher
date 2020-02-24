package androidx.leanback.widget;

import android.support.annotation.RestrictTo;
import android.view.View;

interface FocusHighlightHandler {
    void onInitializeView(View view);

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    void onItemFocused(View view, boolean z);
}
