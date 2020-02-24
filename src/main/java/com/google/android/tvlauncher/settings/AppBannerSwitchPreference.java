package com.google.android.tvlauncher.settings;

import android.content.Context;
import android.widget.ImageView;
import androidx.preference.PreferenceViewHolder;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.Util;

public class AppBannerSwitchPreference extends CustomSwitchPreference {
    private float mDisabledAlpha;
    private boolean mShowIcon;

    AppBannerSwitchPreference(Context context) {
        super(context);
        setLayoutResource(C1188R.layout.appchannel_app_banner);
        this.mDisabledAlpha = Util.getFloat(context.getResources(), C1188R.dimen.preference_app_banner_disabled_alpha);
    }

    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        ImageView icon = (ImageView) holder.findViewById(16908294);
        icon.setClipToOutline(true);
        icon.setAlpha(isEnabled() ? 1.0f : this.mDisabledAlpha);
        holder.findViewById(C1188R.C1191id.icon_container).setVisibility(this.mShowIcon ? 0 : 8);
    }

    /* access modifiers changed from: package-private */
    public void setShowIcon(boolean showIcon) {
        this.mShowIcon = showIcon;
    }
}
