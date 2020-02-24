package com.google.android.tvlauncher.home;

import android.content.Intent;
import android.view.View;
import com.google.android.tvlauncher.settings.AppChannelPermissionActivity;

final /* synthetic */ class ConfigureChannelsRowController$$Lambda$0 implements View.OnClickListener {
    static final View.OnClickListener $instance = new ConfigureChannelsRowController$$Lambda$0();

    private ConfigureChannelsRowController$$Lambda$0() {
    }

    public void onClick(View view) {
        view.getContext().startActivity(new Intent(view.getContext(), AppChannelPermissionActivity.class));
    }
}
