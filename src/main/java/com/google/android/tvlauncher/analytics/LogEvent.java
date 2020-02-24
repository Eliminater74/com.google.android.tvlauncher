package com.google.android.tvlauncher.analytics;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.libraries.social.analytics.visualelement.VisualElementTag;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.common.logging.AncestryVisualElement;
import com.google.common.logging.proto2api.UserActionEnum;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog;

public class LogEvent {
    private static final int ROW_INDEX_MULTIPLIER = 10000;
    private AncestryVisualElement.AncestryVisualElementProto.Builder mAncestryVisualElement;
    private TvlauncherClientLog.AppLink.Builder mAppLink;
    private TvlauncherClientLog.Application.Builder mApplication;
    private boolean mBypassUsageReportingOptOut;
    private TvlauncherClientLog.Channel.Builder mChannel;
    private TvlauncherClientLog.ChannelCollection.Builder mChannelCollection;
    private TvlauncherLogEnum.TvLauncherEventCode mEventCode;
    private String[] mExpectedParameters;
    private TvlauncherClientLog.Input.Builder mInput;
    private TvlauncherClientLog.InputCollection.Builder mInputCollection;
    private TvlauncherClientLog.LaunchItem.Builder mLaunchItem;
    private TvlauncherClientLog.LaunchItemCollection.Builder mLaunchItemCollection;
    private TvlauncherClientLog.TvLauncherClientExtension.Builder mLogEntry;
    private TvlauncherClientLog.VisualElementMetadata.Builder mMetadata;
    private TvlauncherClientLog.Notification.Builder mNotification;
    private TvlauncherClientLog.NotificationCollection.Builder mNotificationCollection;
    private TvlauncherClientLog.Program.Builder mProgram;
    private long mTimeoutMillis;
    private TvlauncherClientLog.VisualElementEntry.Builder mVisualElementsEntry;
    private TvlauncherClientLog.WatchNextChannel.Builder mWatchNextChannel;

    public LogEvent() {
    }

    public LogEvent(TvlauncherLogEnum.TvLauncherEventCode eventCode) {
        this.mEventCode = eventCode;
    }

    public LogEvent bypassUsageReportingOptOut() {
        this.mBypassUsageReportingOptOut = true;
        return this;
    }

    public boolean shouldBypassUsageReportingOptOut() {
        return this.mBypassUsageReportingOptOut;
    }

    public LogEvent expectParameters(String... expectedParameters) {
        this.mExpectedParameters = expectedParameters;
        return this;
    }

    public LogEvent setParameterTimeout(long timeoutMillis) {
        this.mTimeoutMillis = timeoutMillis;
        return this;
    }

    /* access modifiers changed from: package-private */
    public long getParameterTimeout() {
        return this.mTimeoutMillis;
    }

    /* access modifiers changed from: package-private */
    public String[] getExpectedParameters() {
        return this.mExpectedParameters;
    }

    public TvlauncherClientLog.TvLauncherClientExtension getClientLogEntry() {
        build();
        return (TvlauncherClientLog.TvLauncherClientExtension) this.mLogEntry.build();
    }

    public TvlauncherLogEnum.TvLauncherEventCode getEventCode() {
        return this.mEventCode;
    }

    public LogEvent setEventCode(TvlauncherLogEnum.TvLauncherEventCode eventCode) {
        this.mEventCode = eventCode;
        return this;
    }

    public LogEvent setVisualElementTag(VisualElementTag visualElementTag) {
        ensureVisualElements();
        this.mAncestryVisualElement.setElementId(visualElementTag.f123id);
        return this;
    }

    public LogEvent setVisualElementIndex(int index) {
        ensureVisualElements();
        setVisualElementRowColumn(this.mAncestryVisualElement.getElementIndex() / 10000, index);
        return this;
    }

    public LogEvent setVisualElementRowIndex(int index) {
        ensureVisualElements();
        setVisualElementRowColumn(index, this.mAncestryVisualElement.getElementIndex());
        return this;
    }

    private void setVisualElementRowColumn(int row, int column) {
        this.mAncestryVisualElement.setElementIndex((row * 10000) + (column % 10000));
    }

    public LogEvent pushParentVisualElementTag(VisualElementTag visualElementTag) {
        ensureVisualElements();
        if (!this.mAncestryVisualElement.hasElementId()) {
            this.mAncestryVisualElement.setElementId(visualElementTag.f123id);
        } else {
            this.mAncestryVisualElement.addPathToRootElementId(visualElementTag.f123id);
        }
        return this;
    }

    public LogEvent setUserAction(UserActionEnum.UserAction userAction) {
        ensureVisualElements();
        this.mAncestryVisualElement.setUserAction(userAction);
        return this;
    }

    public TvlauncherClientLog.Channel.Builder getChannel() {
        if (this.mChannel == null) {
            this.mChannel = TvlauncherClientLog.Channel.newBuilder();
        }
        return this.mChannel;
    }

    public boolean hasChannel() {
        return this.mChannel != null;
    }

    public TvlauncherClientLog.ChannelCollection.Builder getChannelCollection() {
        if (this.mChannelCollection == null) {
            this.mChannelCollection = TvlauncherClientLog.ChannelCollection.newBuilder();
        }
        return this.mChannelCollection;
    }

    public TvlauncherClientLog.Notification.Builder getNotification() {
        if (this.mNotification == null) {
            this.mNotification = TvlauncherClientLog.Notification.newBuilder();
        }
        return this.mNotification;
    }

    public TvlauncherClientLog.NotificationCollection.Builder getNotificationCollection() {
        if (this.mNotificationCollection == null) {
            this.mNotificationCollection = TvlauncherClientLog.NotificationCollection.newBuilder();
        }
        return this.mNotificationCollection;
    }

    public TvlauncherClientLog.Application.Builder getApplication() {
        if (this.mApplication == null) {
            this.mApplication = TvlauncherClientLog.Application.newBuilder();
        }
        return this.mApplication;
    }

    public TvlauncherClientLog.AppLink.Builder getAppLink() {
        if (this.mAppLink == null) {
            this.mAppLink = TvlauncherClientLog.AppLink.newBuilder();
        }
        return this.mAppLink;
    }

    public TvlauncherClientLog.Program.Builder getProgram() {
        if (this.mProgram == null) {
            this.mProgram = TvlauncherClientLog.Program.newBuilder();
        }
        return this.mProgram;
    }

    public TvlauncherClientLog.Input.Builder getInput() {
        if (this.mInput == null) {
            this.mInput = TvlauncherClientLog.Input.newBuilder();
        }
        return this.mInput;
    }

    public TvlauncherClientLog.InputCollection.Builder getInputCollection() {
        if (this.mInputCollection == null) {
            this.mInputCollection = TvlauncherClientLog.InputCollection.newBuilder();
        }
        return this.mInputCollection;
    }

    public TvlauncherClientLog.WatchNextChannel.Builder getWatchNextChannel() {
        if (this.mWatchNextChannel == null) {
            this.mWatchNextChannel = TvlauncherClientLog.WatchNextChannel.newBuilder();
        }
        return this.mWatchNextChannel;
    }

    public TvlauncherClientLog.LaunchItemCollection.Builder getLaunchItemCollection() {
        if (this.mLaunchItemCollection == null) {
            this.mLaunchItemCollection = TvlauncherClientLog.LaunchItemCollection.newBuilder();
        }
        return this.mLaunchItemCollection;
    }

    private void ensureVisualElements() {
        if (this.mAncestryVisualElement == null) {
            this.mAncestryVisualElement = AncestryVisualElement.AncestryVisualElementProto.newBuilder();
        }
    }

    private void ensureVisualElementsEntry() {
        if (this.mVisualElementsEntry == null) {
            this.mVisualElementsEntry = TvlauncherClientLog.VisualElementEntry.newBuilder();
        }
    }

    private void ensureMetadata() {
        if (this.mMetadata == null) {
            this.mMetadata = TvlauncherClientLog.VisualElementMetadata.newBuilder();
        }
    }

    private void ensureLaunchItem() {
        if (this.mLaunchItem == null) {
            this.mLaunchItem = TvlauncherClientLog.LaunchItem.newBuilder();
        }
    }

    private void build() {
        if (this.mLogEntry == null) {
            this.mLogEntry = TvlauncherClientLog.TvLauncherClientExtension.newBuilder();
            if (this.mChannel != null) {
                ensureMetadata();
                this.mMetadata.setChannel(this.mChannel);
            }
            if (this.mChannelCollection != null) {
                ensureMetadata();
                this.mMetadata.setChannelCollection(this.mChannelCollection);
            }
            if (this.mNotification != null) {
                ensureMetadata();
                this.mMetadata.setNotification(this.mNotification);
            }
            if (this.mProgram != null) {
                ensureMetadata();
                this.mMetadata.setProgram(this.mProgram);
            }
            if (this.mNotificationCollection != null) {
                ensureMetadata();
                this.mMetadata.setNotificationCollection(this.mNotificationCollection);
            }
            if (this.mApplication != null) {
                ensureLaunchItem();
                this.mLaunchItem.setApp(this.mApplication);
            }
            if (this.mAppLink != null) {
                ensureLaunchItem();
                this.mLaunchItem.setAppLink(this.mAppLink);
            }
            if (this.mLaunchItem != null) {
                ensureMetadata();
                this.mMetadata.setLaunchItem(this.mLaunchItem);
            }
            if (this.mLaunchItemCollection != null) {
                ensureMetadata();
                this.mMetadata.setLaunchItemCollection(this.mLaunchItemCollection);
            }
            if (this.mInput != null) {
                ensureMetadata();
                this.mMetadata.setInput(this.mInput);
            }
            if (this.mInputCollection != null) {
                ensureMetadata();
                this.mMetadata.setInputCollection(this.mInputCollection);
            }
            if (this.mWatchNextChannel != null) {
                ensureMetadata();
                this.mMetadata.setWatchNextChannel(this.mWatchNextChannel);
            }
            if (this.mMetadata != null && !this.mBypassUsageReportingOptOut) {
                ensureVisualElementsEntry();
                this.mVisualElementsEntry.setVeMetadata(this.mMetadata);
            }
            if (this.mAncestryVisualElement != null) {
                ensureVisualElementsEntry();
                this.mVisualElementsEntry.setAncestryVisualElement(this.mAncestryVisualElement);
            }
            TvlauncherClientLog.VisualElementEntry.Builder builder = this.mVisualElementsEntry;
            if (builder != null) {
                this.mLogEntry.setVisualElementEntry(builder);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void mergeFrom(LogEvent event) {
        build();
        this.mLogEntry.mergeFrom((GeneratedMessageLite) event.getClientLogEntry());
    }

    @NonNull
    public static TvlauncherClientLog.Notification.Importance notificationImportance(int importance) {
        if (importance == 1) {
            return TvlauncherClientLog.Notification.Importance.MIN;
        }
        if (importance == 2) {
            return TvlauncherClientLog.Notification.Importance.LOW;
        }
        if (importance == 3) {
            return TvlauncherClientLog.Notification.Importance.DEFAULT;
        }
        if (importance == 4) {
            return TvlauncherClientLog.Notification.Importance.HIGH;
        }
        if (importance != 5) {
            return TvlauncherClientLog.Notification.Importance.DEFAULT;
        }
        return TvlauncherClientLog.Notification.Importance.MAX;
    }

    public static TvlauncherClientLog.Program.Type programType(int programType) {
        switch (programType) {
            case 0:
                return TvlauncherClientLog.Program.Type.MOVIE;
            case 1:
                return TvlauncherClientLog.Program.Type.TV_SERIES;
            case 2:
                return TvlauncherClientLog.Program.Type.TV_SEASON;
            case 3:
                return TvlauncherClientLog.Program.Type.TV_EPISODE;
            case 4:
                return TvlauncherClientLog.Program.Type.CLIP;
            case 5:
                return TvlauncherClientLog.Program.Type.EVENT;
            case 6:
                return TvlauncherClientLog.Program.Type.CHANNEL;
            case 7:
                return TvlauncherClientLog.Program.Type.TRACK;
            case 8:
                return TvlauncherClientLog.Program.Type.ALBUM;
            case 9:
                return TvlauncherClientLog.Program.Type.ARTIST;
            case 10:
                return TvlauncherClientLog.Program.Type.PLAYLIST;
            case 11:
                return TvlauncherClientLog.Program.Type.STATION;
            case 12:
                return TvlauncherClientLog.Program.Type.GAME;
            default:
                return null;
        }
    }

    public static TvlauncherClientLog.Program.InteractionCount.Type interactionType(int type) {
        switch (type) {
            case 0:
                return TvlauncherClientLog.Program.InteractionCount.Type.VIEWS;
            case 1:
                return TvlauncherClientLog.Program.InteractionCount.Type.LISTENS;
            case 2:
                return TvlauncherClientLog.Program.InteractionCount.Type.FOLLOWERS;
            case 3:
                return TvlauncherClientLog.Program.InteractionCount.Type.FANS;
            case 4:
                return TvlauncherClientLog.Program.InteractionCount.Type.LIKES;
            case 5:
                return TvlauncherClientLog.Program.InteractionCount.Type.THUMBS;
            case 6:
                return TvlauncherClientLog.Program.InteractionCount.Type.VIEWERS;
            default:
                return null;
        }
    }

    @Nullable
    public static TvlauncherClientLog.Input.Type inputType(int inputType) {
        if (inputType == -10) {
            return TvlauncherClientLog.Input.Type.CEC_DEVICE;
        }
        if (inputType == -9) {
            return TvlauncherClientLog.Input.Type.CEC_DEVICE;
        }
        if (inputType == -8) {
            return TvlauncherClientLog.Input.Type.CEC_DEVICE;
        }
        if (inputType == -6) {
            return TvlauncherClientLog.Input.Type.MHL_MOBILE;
        }
        if (inputType == -5) {
            return TvlauncherClientLog.Input.Type.CEC_DEVICE_PLAYBACK;
        }
        if (inputType == -4) {
            return TvlauncherClientLog.Input.Type.CEC_DEVICE_RECORDER;
        }
        if (inputType == -3) {
            return TvlauncherClientLog.Input.Type.BUNDLED_TUNER;
        }
        if (inputType == -2) {
            return TvlauncherClientLog.Input.Type.CEC_DEVICE;
        }
        if (inputType == 0) {
            return TvlauncherClientLog.Input.Type.TUNER;
        }
        switch (inputType) {
            case 1001:
                return TvlauncherClientLog.Input.Type.COMPOSITE;
            case 1002:
                return TvlauncherClientLog.Input.Type.SVIDEO;
            case 1003:
                return TvlauncherClientLog.Input.Type.SCART;
            case 1004:
                return TvlauncherClientLog.Input.Type.COMPONENT;
            case 1005:
                return TvlauncherClientLog.Input.Type.VGA;
            case 1006:
                return TvlauncherClientLog.Input.Type.DVI;
            case 1007:
                return TvlauncherClientLog.Input.Type.HDMI;
            case 1008:
                return TvlauncherClientLog.Input.Type.DISPLAY_PORT;
            default:
                return null;
        }
    }
}
