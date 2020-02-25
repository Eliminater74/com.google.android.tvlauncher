package com.google.android.tvlauncher.settings;

import android.content.Context;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.google.android.tvlauncher.C1188R;

public class FavoriteLaunchItemPreference extends Preference {
    public FavoriteLaunchItemPreference(Context context) {
        super(context);
        setLayoutResource(C1188R.layout.favorite_app_preference);
    }

    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        ((ImageView) holder.findViewById(16908294)).setClipToOutline(true);
    }
}
