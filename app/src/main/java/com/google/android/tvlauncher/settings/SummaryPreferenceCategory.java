package com.google.android.tvlauncher.settings;

import android.content.Context;

import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceViewHolder;

import com.google.android.tvlauncher.C1188R;

public class SummaryPreferenceCategory extends PreferenceCategory {
    private boolean mEnabled;

    SummaryPreferenceCategory(Context context) {
        super(context);
        setLayoutResource(C1188R.layout.preference_category_with_summary);
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
        super.setEnabled(enabled);
    }

    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setEnabled(isEnabled());
    }
}
