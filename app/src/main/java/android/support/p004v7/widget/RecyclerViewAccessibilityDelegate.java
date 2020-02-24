package android.support.p004v7.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p001v4.view.AccessibilityDelegateCompat;
import android.support.p001v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/* renamed from: android.support.v7.widget.RecyclerViewAccessibilityDelegate */
public class RecyclerViewAccessibilityDelegate extends AccessibilityDelegateCompat {
    final AccessibilityDelegateCompat mItemDelegate = new ItemDelegate(this);
    final RecyclerView mRecyclerView;

    public RecyclerViewAccessibilityDelegate(@NonNull RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldIgnore() {
        return this.mRecyclerView.hasPendingAdapterUpdates();
    }

    public boolean performAccessibilityAction(View host, int action, Bundle args) {
        if (super.performAccessibilityAction(host, action, args)) {
            return true;
        }
        if (shouldIgnore() || this.mRecyclerView.getLayoutManager() == null) {
            return false;
        }
        return this.mRecyclerView.getLayoutManager().performAccessibilityAction(action, args);
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        if (!shouldIgnore() && this.mRecyclerView.getLayoutManager() != null) {
            this.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfo(info);
        }
    }

    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(host, event);
        if ((host instanceof RecyclerView) && !shouldIgnore()) {
            RecyclerView rv = (RecyclerView) host;
            if (rv.getLayoutManager() != null) {
                rv.getLayoutManager().onInitializeAccessibilityEvent(event);
            }
        }
    }

    @NonNull
    public AccessibilityDelegateCompat getItemDelegate() {
        return this.mItemDelegate;
    }

    /* renamed from: android.support.v7.widget.RecyclerViewAccessibilityDelegate$ItemDelegate */
    public static class ItemDelegate extends AccessibilityDelegateCompat {
        final RecyclerViewAccessibilityDelegate mRecyclerViewDelegate;

        public ItemDelegate(@NonNull RecyclerViewAccessibilityDelegate recyclerViewDelegate) {
            this.mRecyclerViewDelegate = recyclerViewDelegate;
        }

        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            if (!this.mRecyclerViewDelegate.shouldIgnore() && this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager() != null) {
                this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().onInitializeAccessibilityNodeInfoForItem(host, info);
            }
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            if (this.mRecyclerViewDelegate.shouldIgnore() || this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager() == null) {
                return false;
            }
            return this.mRecyclerViewDelegate.mRecyclerView.getLayoutManager().performAccessibilityActionForItem(host, action, args);
        }
    }
}
