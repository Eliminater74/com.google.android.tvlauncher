package androidx.preference;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.p001v4.view.AccessibilityDelegateCompat;
import android.support.p001v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.p004v7.widget.RecyclerView;
import android.support.p004v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.View;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
public class PreferenceRecyclerViewAccessibilityDelegate extends RecyclerViewAccessibilityDelegate {
    final AccessibilityDelegateCompat mDefaultItemDelegate = super.getItemDelegate();
    final RecyclerView mRecyclerView;
    final AccessibilityDelegateCompat mItemDelegate = new AccessibilityDelegateCompat() {
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            Preference preference;
            PreferenceRecyclerViewAccessibilityDelegate.this.mDefaultItemDelegate.onInitializeAccessibilityNodeInfo(host, info);
            int position = PreferenceRecyclerViewAccessibilityDelegate.this.mRecyclerView.getChildAdapterPosition(host);
            RecyclerView.Adapter adapter = PreferenceRecyclerViewAccessibilityDelegate.this.mRecyclerView.getAdapter();
            if ((adapter instanceof PreferenceGroupAdapter) && (preference = ((PreferenceGroupAdapter) adapter).getItem(position)) != null) {
                preference.onInitializeAccessibilityNodeInfo(info);
            }
        }

        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            return PreferenceRecyclerViewAccessibilityDelegate.this.mDefaultItemDelegate.performAccessibilityAction(host, action, args);
        }
    };

    public PreferenceRecyclerViewAccessibilityDelegate(RecyclerView recyclerView) {
        super(recyclerView);
        this.mRecyclerView = recyclerView;
    }

    @NonNull
    public AccessibilityDelegateCompat getItemDelegate() {
        return this.mItemDelegate;
    }
}
