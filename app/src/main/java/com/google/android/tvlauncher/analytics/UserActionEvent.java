package com.google.android.tvlauncher.analytics;

import com.google.android.tvlauncher.TvlauncherLogEnum;

public class UserActionEvent extends LogEvent {
    public UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode eventCode) {
        super(eventCode);
    }
}
