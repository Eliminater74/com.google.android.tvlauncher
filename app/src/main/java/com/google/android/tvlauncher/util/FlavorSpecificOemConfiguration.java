package com.google.android.tvlauncher.util;

import android.content.ContentValues;
import android.content.Context;
import android.media.tv.TvContract;

import java.util.List;

class FlavorSpecificOemConfiguration extends OemConfiguration {
    FlavorSpecificOemConfiguration(Context context, String packageName) {
        super(context, packageName);
    }

    public boolean shouldShowAddToWatchNextFromProgramMenu() {
        return true;
    }

    public boolean shouldShowRemoveProgramFromProgramMenu() {
        return true;
    }

    public boolean isWatchNextChannelEnabledByDefault() {
        return true;
    }

    public List<OemOutOfBoxApp> getVirtualOutOfBoxApps() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public void onOemConfigurationFetched() {
        for (ChannelConfigurationInfo channelConfigurationInfo : this.mConfiguration.getSponsoredChannelsReadFromContentProvider()) {
            if (!channelConfigurationInfo.canHide() && !channelConfigurationInfo.canMove()) {
                makeChannelBrowsable(channelConfigurationInfo.getPackageName(), channelConfigurationInfo.getSystemChannelKey());
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Boolean):void}
     arg types: [java.lang.String, int]
     candidates:
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Byte):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Float):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.String):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Integer):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Long):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, byte[]):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Double):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Short):void}
      ClspMth{android.content.ContentValues.put(java.lang.String, java.lang.Boolean):void} */
    private void makeChannelBrowsable(String packageName, String systemChannelKey) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("browsable", (Boolean) true);
        this.mContext.getContentResolver().update(TvContract.Channels.CONTENT_URI, contentValues, "package_name=? AND system_channel_key=? AND browsable=0", new String[]{packageName, systemChannelKey});
    }
}
