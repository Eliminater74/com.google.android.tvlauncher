package com.google.android.tvlauncher.analytics;

import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.common.logging.proto2api.UserActionEnum;

public class ClickEvent extends LogEvent {
    public ClickEvent(TvlauncherLogEnum.TvLauncherEventCode eventCode) {
        super(eventCode);
        setUserAction(UserActionEnum.UserAction.TAP);
    }
}
