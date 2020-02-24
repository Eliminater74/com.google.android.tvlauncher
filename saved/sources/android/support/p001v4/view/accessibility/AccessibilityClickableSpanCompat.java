package android.support.p001v4.view.accessibility;

import android.os.Bundle;
import android.support.annotation.RestrictTo;
import android.text.style.ClickableSpan;
import android.view.View;

/* renamed from: android.support.v4.view.accessibility.AccessibilityClickableSpanCompat */
public final class AccessibilityClickableSpanCompat extends ClickableSpan {
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String SPAN_ID = "ACCESSIBILITY_CLICKABLE_SPAN_ID";
    private final int mClickableSpanActionId;
    private final AccessibilityNodeInfoCompat mNodeInfoCompat;
    private final int mOriginalClickableSpanId;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public AccessibilityClickableSpanCompat(int originalClickableSpanId, AccessibilityNodeInfoCompat nodeInfoCompat, int clickableSpanActionId) {
        this.mOriginalClickableSpanId = originalClickableSpanId;
        this.mNodeInfoCompat = nodeInfoCompat;
        this.mClickableSpanActionId = clickableSpanActionId;
    }

    public void onClick(View unused) {
        Bundle arguments = new Bundle();
        arguments.putInt(SPAN_ID, this.mOriginalClickableSpanId);
        this.mNodeInfoCompat.performAction(this.mClickableSpanActionId, arguments);
    }
}
