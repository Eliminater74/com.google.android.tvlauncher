package com.google.android.tvlauncher.settings;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.widget.TextView;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;

public class CustomSwitchPreference extends SwitchPreference {
    private boolean mDimSummary;
    private boolean mShowToggle = true;

    CustomSwitchPreference(Context context) {
        super(context);
    }

    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        boolean z = false;
        holder.findViewById(16908352).setVisibility(this.mShowToggle ? 0 : 4);
        holder.itemView.setClickable(this.mShowToggle);
        TextView summary = (TextView) holder.findViewById(16908304);
        if (isEnabled() && !this.mDimSummary) {
            z = true;
        }
        summary.setEnabled(z);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isShowToggle() {
        return this.mShowToggle;
    }

    /* access modifiers changed from: package-private */
    public void setShowToggle(boolean showToggle) {
        this.mShowToggle = showToggle;
    }

    /* access modifiers changed from: package-private */
    public void setDimSummary(boolean dimSummary) {
        this.mDimSummary = dimSummary;
    }
}
