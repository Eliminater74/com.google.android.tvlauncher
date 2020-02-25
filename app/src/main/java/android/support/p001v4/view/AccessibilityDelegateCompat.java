package android.support.p001v4.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.compat.C0014R;
import android.support.p001v4.view.accessibility.AccessibilityClickableSpanCompat;
import android.support.p001v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p001v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.text.style.ClickableSpan;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/* renamed from: android.support.v4.view.AccessibilityDelegateCompat */
public class AccessibilityDelegateCompat {
    private static final View.AccessibilityDelegate DEFAULT_DELEGATE = new View.AccessibilityDelegate();
    private final View.AccessibilityDelegate mBridge;
    private final View.AccessibilityDelegate mOriginalDelegate;

    public AccessibilityDelegateCompat() {
        this(DEFAULT_DELEGATE);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public AccessibilityDelegateCompat(View.AccessibilityDelegate originalDelegate) {
        this.mOriginalDelegate = originalDelegate;
        this.mBridge = new AccessibilityDelegateAdapter(this);
    }

    static List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> getActionList(View view) {
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions = (List) view.getTag(C0014R.C0016id.tag_accessibility_actions);
        return actions == null ? Collections.emptyList() : actions;
    }

    /* access modifiers changed from: package-private */
    public View.AccessibilityDelegate getBridge() {
        return this.mBridge;
    }

    public void sendAccessibilityEvent(View host, int eventType) {
        this.mOriginalDelegate.sendAccessibilityEvent(host, eventType);
    }

    public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
        this.mOriginalDelegate.sendAccessibilityEventUnchecked(host, event);
    }

    public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
        return this.mOriginalDelegate.dispatchPopulateAccessibilityEvent(host, event);
    }

    public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
        this.mOriginalDelegate.onPopulateAccessibilityEvent(host, event);
    }

    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        this.mOriginalDelegate.onInitializeAccessibilityEvent(host, event);
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(host, info.unwrap());
    }

    public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
        return this.mOriginalDelegate.onRequestSendAccessibilityEvent(host, child, event);
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) {
        Object provider;
        if (Build.VERSION.SDK_INT < 16 || (provider = this.mOriginalDelegate.getAccessibilityNodeProvider(host)) == null) {
            return null;
        }
        return new AccessibilityNodeProviderCompat(provider);
    }

    public boolean performAccessibilityAction(View host, int action, Bundle args) {
        boolean success = false;
        List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions = getActionList(host);
        int i = 0;
        while (true) {
            if (i >= actions.size()) {
                break;
            }
            AccessibilityNodeInfoCompat.AccessibilityActionCompat actionCompat = actions.get(i);
            if (actionCompat.getId() == action) {
                success = actionCompat.perform(host, args);
                break;
            }
            i++;
        }
        if (!success && Build.VERSION.SDK_INT >= 16) {
            success = this.mOriginalDelegate.performAccessibilityAction(host, action, args);
        }
        if (success || action != C0014R.C0016id.accessibility_action_clickable_span) {
            return success;
        }
        return performClickableSpanAction(args.getInt(AccessibilityClickableSpanCompat.SPAN_ID, -1), host);
    }

    private boolean performClickableSpanAction(int clickableSpanId, View host) {
        WeakReference<ClickableSpan> reference;
        SparseArray<WeakReference<ClickableSpan>> spans = (SparseArray) host.getTag(C0014R.C0016id.tag_accessibility_clickable_spans);
        if (spans == null || (reference = spans.get(clickableSpanId)) == null) {
            return false;
        }
        ClickableSpan span = (ClickableSpan) reference.get();
        if (!isSpanStillValid(span, host)) {
            return false;
        }
        span.onClick(host);
        return true;
    }

    private boolean isSpanStillValid(ClickableSpan span, View view) {
        if (span == null) {
            return false;
        }
        ClickableSpan[] spans = AccessibilityNodeInfoCompat.getClickableSpans(view.createAccessibilityNodeInfo().getText());
        int i = 0;
        while (spans != null && i < spans.length) {
            if (span.equals(spans[i])) {
                return true;
            }
            i++;
        }
        return false;
    }

    /* renamed from: android.support.v4.view.AccessibilityDelegateCompat$AccessibilityDelegateAdapter */
    static final class AccessibilityDelegateAdapter extends View.AccessibilityDelegate {
        final AccessibilityDelegateCompat mCompat;

        AccessibilityDelegateAdapter(AccessibilityDelegateCompat compat) {
            this.mCompat = compat;
        }

        public boolean dispatchPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            return this.mCompat.dispatchPopulateAccessibilityEvent(host, event);
        }

        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            this.mCompat.onInitializeAccessibilityEvent(host, event);
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
            AccessibilityNodeInfoCompat nodeInfoCompat = AccessibilityNodeInfoCompat.wrap(info);
            nodeInfoCompat.setScreenReaderFocusable(ViewCompat.isScreenReaderFocusable(host));
            nodeInfoCompat.setHeading(ViewCompat.isAccessibilityHeading(host));
            nodeInfoCompat.setPaneTitle(ViewCompat.getAccessibilityPaneTitle(host));
            this.mCompat.onInitializeAccessibilityNodeInfo(host, nodeInfoCompat);
            nodeInfoCompat.addSpansToExtras(info.getText(), host);
            List<AccessibilityNodeInfoCompat.AccessibilityActionCompat> actions = AccessibilityDelegateCompat.getActionList(host);
            for (int i = 0; i < actions.size(); i++) {
                nodeInfoCompat.addAction(actions.get(i));
            }
        }

        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            this.mCompat.onPopulateAccessibilityEvent(host, event);
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup host, View child, AccessibilityEvent event) {
            return this.mCompat.onRequestSendAccessibilityEvent(host, child, event);
        }

        public void sendAccessibilityEvent(View host, int eventType) {
            this.mCompat.sendAccessibilityEvent(host, eventType);
        }

        public void sendAccessibilityEventUnchecked(View host, AccessibilityEvent event) {
            this.mCompat.sendAccessibilityEventUnchecked(host, event);
        }

        @RequiresApi(16)
        public AccessibilityNodeProvider getAccessibilityNodeProvider(View host) {
            AccessibilityNodeProviderCompat provider = this.mCompat.getAccessibilityNodeProvider(host);
            if (provider != null) {
                return (AccessibilityNodeProvider) provider.getProvider();
            }
            return null;
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            return this.mCompat.performAccessibilityAction(host, action, args);
        }
    }
}
