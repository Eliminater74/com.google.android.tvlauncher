package com.google.protos.logs.proto.wireless.android.tvlauncher;

import com.google.common.logging.AncestryVisualElement;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.ProtoField;
import com.google.protobuf.ProtoMessage;
import com.google.protobuf.ProtoPresenceBits;
import com.google.protobuf.ProtoPresenceCheckedField;
import com.google.protobuf.ProtoSyntax;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public final class TvlauncherClientLog {

    public interface AppLinkOrBuilder extends MessageLiteOrBuilder {
        boolean getIsInstalled();

        String getPackageName();

        ByteString getPackageNameBytes();

        String getUri();

        ByteString getUriBytes();

        boolean hasIsInstalled();

        boolean hasPackageName();

        boolean hasUri();
    }

    public interface ApplicationOrBuilder extends MessageLiteOrBuilder {
        int getBrowsableChannelCount();

        int getChannelCount();

        Channel getChannels(int i);

        int getChannelsCount();

        List<Channel> getChannelsList();

        boolean getIsGame();

        String getPackageName();

        ByteString getPackageNameBytes();

        int getTargetSdk();

        int getVersionCode();

        boolean hasBrowsableChannelCount();

        boolean hasChannelCount();

        boolean hasIsGame();

        boolean hasPackageName();

        boolean hasTargetSdk();

        boolean hasVersionCode();
    }

    public interface ChannelCollectionOrBuilder extends MessageLiteOrBuilder {
        int getBrowsableCount();

        int getBrowsableLegacyCount();

        int getCount();

        int getLegacyCount();

        boolean hasBrowsableCount();

        boolean hasBrowsableLegacyCount();

        boolean hasCount();

        boolean hasLegacyCount();
    }

    public interface ChannelOrBuilder extends MessageLiteOrBuilder {
        Channel.DenialReason getDenialReason();

        boolean getIsLegacy();

        String getPackageName();

        ByteString getPackageNameBytes();

        int getPosition();

        int getProgramCount();

        Program getPrograms(int i);

        int getProgramsCount();

        List<Program> getProgramsList();

        String getTitle();

        ByteString getTitleBytes();

        boolean hasDenialReason();

        boolean hasIsLegacy();

        boolean hasPackageName();

        boolean hasPosition();

        boolean hasProgramCount();

        boolean hasTitle();
    }

    public interface DeviceStatusOrBuilder extends MessageLiteOrBuilder {
        Input getActiveInput();

        String getForegroundPackageName();

        ByteString getForegroundPackageNameBytes();

        boolean getIsPlayingAudio();

        boolean getIsPlayingVideo();

        boolean getIsScreenOn();

        String getPipPackageName();

        ByteString getPipPackageNameBytes();

        boolean hasActiveInput();

        boolean hasForegroundPackageName();

        boolean hasIsPlayingAudio();

        boolean hasIsPlayingVideo();

        boolean hasIsScreenOn();

        boolean hasPipPackageName();
    }

    public interface InputCollectionOrBuilder extends MessageLiteOrBuilder {
        int getCount();

        Input getInputs(int i);

        int getInputsCount();

        List<Input> getInputsList();

        boolean hasCount();
    }

    public interface InputOrBuilder extends MessageLiteOrBuilder {
        String getDisplayName();

        ByteString getDisplayNameBytes();

        Input.Type getType();

        boolean hasDisplayName();

        boolean hasType();
    }

    public interface LaunchItemCollectionOrBuilder extends MessageLiteOrBuilder {
        int getCount();

        int getGameCount();

        LaunchItem getItems(int i);

        int getItemsCount();

        List<LaunchItem> getItemsList();

        boolean hasCount();

        boolean hasGameCount();
    }

    public interface LaunchItemOrBuilder extends MessageLiteOrBuilder {
        Application getApp();

        AppLink getAppLink();

        boolean hasApp();

        boolean hasAppLink();
    }

    public interface MediaSessionOrBuilder extends MessageLiteOrBuilder {
        int getDurationSeconds();

        boolean getHasPreview();

        boolean getInitiatedFromLauncher();

        boolean getIsVideo();

        String getPackageName();

        ByteString getPackageNameBytes();

        int getPlayingDurationSeconds();

        long getStartLatencyMillis();

        boolean getWasPreviewPlaying();

        boolean hasDurationSeconds();

        boolean hasHasPreview();

        boolean hasInitiatedFromLauncher();

        boolean hasIsVideo();

        boolean hasPackageName();

        boolean hasPlayingDurationSeconds();

        boolean hasStartLatencyMillis();

        boolean hasWasPreviewPlaying();
    }

    public interface NotificationCollectionOrBuilder extends MessageLiteOrBuilder {
        int getCount();

        boolean getHasNewNotifications();

        int getMaxPriorityCount();

        Notification getNotifications(int i);

        int getNotificationsCount();

        List<Notification> getNotificationsList();

        boolean hasCount();

        boolean hasHasNewNotifications();

        boolean hasMaxPriorityCount();
    }

    public interface NotificationOrBuilder extends MessageLiteOrBuilder {
        Notification.Importance getImportance();

        String getPackageName();

        ByteString getPackageNameBytes();

        String getSummary();

        ByteString getSummaryBytes();

        boolean hasImportance();

        boolean hasPackageName();

        boolean hasSummary();
    }

    public interface ProgramOrBuilder extends MessageLiteOrBuilder {
        String getGenre();

        ByteString getGenreBytes();

        boolean getHasBadge();

        boolean getHasContentRating();

        boolean getHasDescription();

        boolean getHasProgress();

        Program.InteractionCount getInteractionCount();

        boolean getIsLive();

        long getMetadataHashCode();

        String getPackageName();

        ByteString getPackageNameBytes();

        Program.Preview getPreview();

        Program.Rating getRating();

        Program.Type getType();

        boolean hasGenre();

        boolean hasHasBadge();

        boolean hasHasContentRating();

        boolean hasHasDescription();

        boolean hasHasProgress();

        boolean hasInteractionCount();

        boolean hasIsLive();

        boolean hasMetadataHashCode();

        boolean hasPackageName();

        boolean hasPreview();

        boolean hasRating();

        boolean hasType();
    }

    public interface SystemPropertiesOrBuilder extends MessageLiteOrBuilder {
        Application getApps(int i);

        int getAppsCount();

        List<Application> getAppsList();

        boolean getIsDefaultLauncher();

        String getMediaPackageName();

        ByteString getMediaPackageNameBytes();

        WatchNextChannel getWatchNextChannel();

        boolean hasIsDefaultLauncher();

        boolean hasMediaPackageName();

        boolean hasWatchNextChannel();
    }

    public interface TvLauncherClientExtensionOrBuilder extends MessageLiteOrBuilder {
        DeviceStatus getDeviceStatus();

        MediaSession getMediaSession();

        SystemProperties getProperties();

        VisualElementEntry getVisualElementEntry();

        boolean hasDeviceStatus();

        boolean hasMediaSession();

        boolean hasProperties();

        boolean hasVisualElementEntry();
    }

    public interface VisualElementEntryOrBuilder extends MessageLiteOrBuilder {
        AncestryVisualElement.AncestryVisualElementProto getAncestryVisualElement();

        VisualElementMetadata getVeMetadata();

        boolean hasAncestryVisualElement();

        boolean hasVeMetadata();
    }

    public interface VisualElementMetadataOrBuilder extends MessageLiteOrBuilder {
        Channel getChannel();

        ChannelCollection getChannelCollection();

        Input getInput();

        InputCollection getInputCollection();

        LaunchItem getLaunchItem();

        LaunchItemCollection getLaunchItemCollection();

        Notification getNotification();

        NotificationCollection getNotificationCollection();

        Program getProgram();

        WatchNextChannel getWatchNextChannel();

        boolean hasChannel();

        boolean hasChannelCollection();

        boolean hasInput();

        boolean hasInputCollection();

        boolean hasLaunchItem();

        boolean hasLaunchItemCollection();

        boolean hasNotification();

        boolean hasNotificationCollection();

        boolean hasProgram();

        boolean hasWatchNextChannel();
    }

    public interface WatchNextChannelOrBuilder extends MessageLiteOrBuilder {
        int getProgramCount();

        Program getPrograms(int i);

        int getProgramsCount();

        List<Program> getProgramsList();

        boolean hasProgramCount();
    }

    private TvlauncherClientLog() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TvLauncherClientExtension extends GeneratedMessageLite<TvLauncherClientExtension, Builder> implements TvLauncherClientExtensionOrBuilder {
        /* access modifiers changed from: private */
        public static final TvLauncherClientExtension DEFAULT_INSTANCE = new TvLauncherClientExtension();
        public static final int DEVICE_STATUS_FIELD_NUMBER = 3;
        public static final int MEDIA_SESSION_FIELD_NUMBER = 4;
        private static volatile Parser<TvLauncherClientExtension> PARSER = null;
        public static final int PROPERTIES_FIELD_NUMBER = 2;
        public static final int VISUAL_ELEMENT_ENTRY_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private DeviceStatus deviceStatus_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private MediaSession mediaSession_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private SystemProperties properties_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private VisualElementEntry visualElementEntry_;

        private TvLauncherClientExtension() {
        }

        public boolean hasVisualElementEntry() {
            return (this.bitField0_ & 1) != 0;
        }

        public VisualElementEntry getVisualElementEntry() {
            VisualElementEntry visualElementEntry = this.visualElementEntry_;
            return visualElementEntry == null ? VisualElementEntry.getDefaultInstance() : visualElementEntry;
        }

        /* access modifiers changed from: private */
        public void setVisualElementEntry(VisualElementEntry value) {
            if (value != null) {
                this.visualElementEntry_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setVisualElementEntry(VisualElementEntry.Builder builderForValue) {
            this.visualElementEntry_ = (VisualElementEntry) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeVisualElementEntry(VisualElementEntry value) {
            if (value != null) {
                VisualElementEntry visualElementEntry = this.visualElementEntry_;
                if (visualElementEntry == null || visualElementEntry == VisualElementEntry.getDefaultInstance()) {
                    this.visualElementEntry_ = value;
                } else {
                    this.visualElementEntry_ = (VisualElementEntry) ((VisualElementEntry.Builder) VisualElementEntry.newBuilder(this.visualElementEntry_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearVisualElementEntry() {
            this.visualElementEntry_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasProperties() {
            return (this.bitField0_ & 2) != 0;
        }

        public SystemProperties getProperties() {
            SystemProperties systemProperties = this.properties_;
            return systemProperties == null ? SystemProperties.getDefaultInstance() : systemProperties;
        }

        /* access modifiers changed from: private */
        public void setProperties(SystemProperties value) {
            if (value != null) {
                this.properties_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProperties(SystemProperties.Builder builderForValue) {
            this.properties_ = (SystemProperties) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeProperties(SystemProperties value) {
            if (value != null) {
                SystemProperties systemProperties = this.properties_;
                if (systemProperties == null || systemProperties == SystemProperties.getDefaultInstance()) {
                    this.properties_ = value;
                } else {
                    this.properties_ = (SystemProperties) ((SystemProperties.Builder) SystemProperties.newBuilder(this.properties_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProperties() {
            this.properties_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasDeviceStatus() {
            return (this.bitField0_ & 4) != 0;
        }

        public DeviceStatus getDeviceStatus() {
            DeviceStatus deviceStatus = this.deviceStatus_;
            return deviceStatus == null ? DeviceStatus.getDefaultInstance() : deviceStatus;
        }

        /* access modifiers changed from: private */
        public void setDeviceStatus(DeviceStatus value) {
            if (value != null) {
                this.deviceStatus_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setDeviceStatus(DeviceStatus.Builder builderForValue) {
            this.deviceStatus_ = (DeviceStatus) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeDeviceStatus(DeviceStatus value) {
            if (value != null) {
                DeviceStatus deviceStatus = this.deviceStatus_;
                if (deviceStatus == null || deviceStatus == DeviceStatus.getDefaultInstance()) {
                    this.deviceStatus_ = value;
                } else {
                    this.deviceStatus_ = (DeviceStatus) ((DeviceStatus.Builder) DeviceStatus.newBuilder(this.deviceStatus_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDeviceStatus() {
            this.deviceStatus_ = null;
            this.bitField0_ &= -5;
        }

        public boolean hasMediaSession() {
            return (this.bitField0_ & 8) != 0;
        }

        public MediaSession getMediaSession() {
            MediaSession mediaSession = this.mediaSession_;
            return mediaSession == null ? MediaSession.getDefaultInstance() : mediaSession;
        }

        /* access modifiers changed from: private */
        public void setMediaSession(MediaSession value) {
            if (value != null) {
                this.mediaSession_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setMediaSession(MediaSession.Builder builderForValue) {
            this.mediaSession_ = (MediaSession) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeMediaSession(MediaSession value) {
            if (value != null) {
                MediaSession mediaSession = this.mediaSession_;
                if (mediaSession == null || mediaSession == MediaSession.getDefaultInstance()) {
                    this.mediaSession_ = value;
                } else {
                    this.mediaSession_ = (MediaSession) ((MediaSession.Builder) MediaSession.newBuilder(this.mediaSession_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMediaSession() {
            this.mediaSession_ = null;
            this.bitField0_ &= -9;
        }

        public static TvLauncherClientExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TvLauncherClientExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TvLauncherClientExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TvLauncherClientExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TvLauncherClientExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TvLauncherClientExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TvLauncherClientExtension parseFrom(InputStream input) throws IOException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TvLauncherClientExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TvLauncherClientExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (TvLauncherClientExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TvLauncherClientExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TvLauncherClientExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TvLauncherClientExtension parseFrom(CodedInputStream input) throws IOException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TvLauncherClientExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TvLauncherClientExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TvLauncherClientExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<TvLauncherClientExtension, Builder> implements TvLauncherClientExtensionOrBuilder {
            private Builder() {
                super(TvLauncherClientExtension.DEFAULT_INSTANCE);
            }

            public boolean hasVisualElementEntry() {
                return ((TvLauncherClientExtension) this.instance).hasVisualElementEntry();
            }

            public VisualElementEntry getVisualElementEntry() {
                return ((TvLauncherClientExtension) this.instance).getVisualElementEntry();
            }

            public Builder setVisualElementEntry(VisualElementEntry value) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).setVisualElementEntry(value);
                return this;
            }

            public Builder setVisualElementEntry(VisualElementEntry.Builder builderForValue) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).setVisualElementEntry(builderForValue);
                return this;
            }

            public Builder mergeVisualElementEntry(VisualElementEntry value) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).mergeVisualElementEntry(value);
                return this;
            }

            public Builder clearVisualElementEntry() {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).clearVisualElementEntry();
                return this;
            }

            public boolean hasProperties() {
                return ((TvLauncherClientExtension) this.instance).hasProperties();
            }

            public SystemProperties getProperties() {
                return ((TvLauncherClientExtension) this.instance).getProperties();
            }

            public Builder setProperties(SystemProperties value) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).setProperties(value);
                return this;
            }

            public Builder setProperties(SystemProperties.Builder builderForValue) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).setProperties(builderForValue);
                return this;
            }

            public Builder mergeProperties(SystemProperties value) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).mergeProperties(value);
                return this;
            }

            public Builder clearProperties() {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).clearProperties();
                return this;
            }

            public boolean hasDeviceStatus() {
                return ((TvLauncherClientExtension) this.instance).hasDeviceStatus();
            }

            public DeviceStatus getDeviceStatus() {
                return ((TvLauncherClientExtension) this.instance).getDeviceStatus();
            }

            public Builder setDeviceStatus(DeviceStatus value) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).setDeviceStatus(value);
                return this;
            }

            public Builder setDeviceStatus(DeviceStatus.Builder builderForValue) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).setDeviceStatus(builderForValue);
                return this;
            }

            public Builder mergeDeviceStatus(DeviceStatus value) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).mergeDeviceStatus(value);
                return this;
            }

            public Builder clearDeviceStatus() {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).clearDeviceStatus();
                return this;
            }

            public boolean hasMediaSession() {
                return ((TvLauncherClientExtension) this.instance).hasMediaSession();
            }

            public MediaSession getMediaSession() {
                return ((TvLauncherClientExtension) this.instance).getMediaSession();
            }

            public Builder setMediaSession(MediaSession value) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).setMediaSession(value);
                return this;
            }

            public Builder setMediaSession(MediaSession.Builder builderForValue) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).setMediaSession(builderForValue);
                return this;
            }

            public Builder mergeMediaSession(MediaSession value) {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).mergeMediaSession(value);
                return this;
            }

            public Builder clearMediaSession() {
                copyOnWrite();
                ((TvLauncherClientExtension) this.instance).clearMediaSession();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TvLauncherClientExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003", new Object[]{"bitField0_", "visualElementEntry_", "properties_", "deviceStatus_", "mediaSession_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TvLauncherClientExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (TvLauncherClientExtension.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(TvLauncherClientExtension.class, DEFAULT_INSTANCE);
        }

        public static TvLauncherClientExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TvLauncherClientExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class VisualElementEntry extends GeneratedMessageLite<VisualElementEntry, Builder> implements VisualElementEntryOrBuilder {
        public static final int ANCESTRY_VISUAL_ELEMENT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final VisualElementEntry DEFAULT_INSTANCE = new VisualElementEntry();
        private static volatile Parser<VisualElementEntry> PARSER = null;
        public static final int VE_METADATA_FIELD_NUMBER = 2;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private AncestryVisualElement.AncestryVisualElementProto ancestryVisualElement_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private VisualElementMetadata veMetadata_;

        private VisualElementEntry() {
        }

        public boolean hasAncestryVisualElement() {
            return (this.bitField0_ & 1) != 0;
        }

        public AncestryVisualElement.AncestryVisualElementProto getAncestryVisualElement() {
            AncestryVisualElement.AncestryVisualElementProto ancestryVisualElementProto = this.ancestryVisualElement_;
            return ancestryVisualElementProto == null ? AncestryVisualElement.AncestryVisualElementProto.getDefaultInstance() : ancestryVisualElementProto;
        }

        /* access modifiers changed from: private */
        public void setAncestryVisualElement(AncestryVisualElement.AncestryVisualElementProto value) {
            if (value != null) {
                this.ancestryVisualElement_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAncestryVisualElement(AncestryVisualElement.AncestryVisualElementProto.Builder builderForValue) {
            this.ancestryVisualElement_ = (AncestryVisualElement.AncestryVisualElementProto) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeAncestryVisualElement(AncestryVisualElement.AncestryVisualElementProto value) {
            if (value != null) {
                AncestryVisualElement.AncestryVisualElementProto ancestryVisualElementProto = this.ancestryVisualElement_;
                if (ancestryVisualElementProto == null || ancestryVisualElementProto == AncestryVisualElement.AncestryVisualElementProto.getDefaultInstance()) {
                    this.ancestryVisualElement_ = value;
                } else {
                    this.ancestryVisualElement_ = (AncestryVisualElement.AncestryVisualElementProto) ((AncestryVisualElement.AncestryVisualElementProto.Builder) AncestryVisualElement.AncestryVisualElementProto.newBuilder(this.ancestryVisualElement_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAncestryVisualElement() {
            this.ancestryVisualElement_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasVeMetadata() {
            return (this.bitField0_ & 2) != 0;
        }

        public VisualElementMetadata getVeMetadata() {
            VisualElementMetadata visualElementMetadata = this.veMetadata_;
            return visualElementMetadata == null ? VisualElementMetadata.getDefaultInstance() : visualElementMetadata;
        }

        /* access modifiers changed from: private */
        public void setVeMetadata(VisualElementMetadata value) {
            if (value != null) {
                this.veMetadata_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setVeMetadata(VisualElementMetadata.Builder builderForValue) {
            this.veMetadata_ = (VisualElementMetadata) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeVeMetadata(VisualElementMetadata value) {
            if (value != null) {
                VisualElementMetadata visualElementMetadata = this.veMetadata_;
                if (visualElementMetadata == null || visualElementMetadata == VisualElementMetadata.getDefaultInstance()) {
                    this.veMetadata_ = value;
                } else {
                    this.veMetadata_ = (VisualElementMetadata) ((VisualElementMetadata.Builder) VisualElementMetadata.newBuilder(this.veMetadata_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearVeMetadata() {
            this.veMetadata_ = null;
            this.bitField0_ &= -3;
        }

        public static VisualElementEntry parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VisualElementEntry parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VisualElementEntry parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VisualElementEntry parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VisualElementEntry parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VisualElementEntry parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VisualElementEntry parseFrom(InputStream input) throws IOException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static VisualElementEntry parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static VisualElementEntry parseDelimitedFrom(InputStream input) throws IOException {
            return (VisualElementEntry) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static VisualElementEntry parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VisualElementEntry) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static VisualElementEntry parseFrom(CodedInputStream input) throws IOException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static VisualElementEntry parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VisualElementEntry) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(VisualElementEntry prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<VisualElementEntry, Builder> implements VisualElementEntryOrBuilder {
            private Builder() {
                super(VisualElementEntry.DEFAULT_INSTANCE);
            }

            public boolean hasAncestryVisualElement() {
                return ((VisualElementEntry) this.instance).hasAncestryVisualElement();
            }

            public AncestryVisualElement.AncestryVisualElementProto getAncestryVisualElement() {
                return ((VisualElementEntry) this.instance).getAncestryVisualElement();
            }

            public Builder setAncestryVisualElement(AncestryVisualElement.AncestryVisualElementProto value) {
                copyOnWrite();
                ((VisualElementEntry) this.instance).setAncestryVisualElement(value);
                return this;
            }

            public Builder setAncestryVisualElement(AncestryVisualElement.AncestryVisualElementProto.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementEntry) this.instance).setAncestryVisualElement(builderForValue);
                return this;
            }

            public Builder mergeAncestryVisualElement(AncestryVisualElement.AncestryVisualElementProto value) {
                copyOnWrite();
                ((VisualElementEntry) this.instance).mergeAncestryVisualElement(value);
                return this;
            }

            public Builder clearAncestryVisualElement() {
                copyOnWrite();
                ((VisualElementEntry) this.instance).clearAncestryVisualElement();
                return this;
            }

            public boolean hasVeMetadata() {
                return ((VisualElementEntry) this.instance).hasVeMetadata();
            }

            public VisualElementMetadata getVeMetadata() {
                return ((VisualElementEntry) this.instance).getVeMetadata();
            }

            public Builder setVeMetadata(VisualElementMetadata value) {
                copyOnWrite();
                ((VisualElementEntry) this.instance).setVeMetadata(value);
                return this;
            }

            public Builder setVeMetadata(VisualElementMetadata.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementEntry) this.instance).setVeMetadata(builderForValue);
                return this;
            }

            public Builder mergeVeMetadata(VisualElementMetadata value) {
                copyOnWrite();
                ((VisualElementEntry) this.instance).mergeVeMetadata(value);
                return this;
            }

            public Builder clearVeMetadata() {
                copyOnWrite();
                ((VisualElementEntry) this.instance).clearVeMetadata();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new VisualElementEntry();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001", new Object[]{"bitField0_", "ancestryVisualElement_", "veMetadata_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<VisualElementEntry> parser = PARSER;
                    if (parser == null) {
                        synchronized (VisualElementEntry.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(VisualElementEntry.class, DEFAULT_INSTANCE);
        }

        public static VisualElementEntry getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<VisualElementEntry> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class VisualElementMetadata extends GeneratedMessageLite<VisualElementMetadata, Builder> implements VisualElementMetadataOrBuilder {
        public static final int CHANNEL_COLLECTION_FIELD_NUMBER = 3;
        public static final int CHANNEL_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final VisualElementMetadata DEFAULT_INSTANCE = new VisualElementMetadata();
        public static final int INPUT_COLLECTION_FIELD_NUMBER = 10;
        public static final int INPUT_FIELD_NUMBER = 9;
        public static final int LAUNCH_ITEM_COLLECTION_FIELD_NUMBER = 8;
        public static final int LAUNCH_ITEM_FIELD_NUMBER = 7;
        public static final int NOTIFICATION_COLLECTION_FIELD_NUMBER = 6;
        public static final int NOTIFICATION_FIELD_NUMBER = 5;
        private static volatile Parser<VisualElementMetadata> PARSER = null;
        public static final int PROGRAM_FIELD_NUMBER = 1;
        public static final int WATCH_NEXT_CHANNEL_FIELD_NUMBER = 4;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private ChannelCollection channelCollection_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private Channel channel_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private InputCollection inputCollection_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private Input input_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private LaunchItemCollection launchItemCollection_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private LaunchItem launchItem_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private NotificationCollection notificationCollection_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private Notification notification_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private Program program_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private WatchNextChannel watchNextChannel_;

        private VisualElementMetadata() {
        }

        public boolean hasProgram() {
            return (this.bitField0_ & 1) != 0;
        }

        public Program getProgram() {
            Program program = this.program_;
            return program == null ? Program.getDefaultInstance() : program;
        }

        /* access modifiers changed from: private */
        public void setProgram(Program value) {
            if (value != null) {
                this.program_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setProgram(Program.Builder builderForValue) {
            this.program_ = (Program) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeProgram(Program value) {
            if (value != null) {
                Program program = this.program_;
                if (program == null || program == Program.getDefaultInstance()) {
                    this.program_ = value;
                } else {
                    this.program_ = (Program) ((Program.Builder) Program.newBuilder(this.program_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProgram() {
            this.program_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasChannel() {
            return (this.bitField0_ & 2) != 0;
        }

        public Channel getChannel() {
            Channel channel = this.channel_;
            return channel == null ? Channel.getDefaultInstance() : channel;
        }

        /* access modifiers changed from: private */
        public void setChannel(Channel value) {
            if (value != null) {
                this.channel_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setChannel(Channel.Builder builderForValue) {
            this.channel_ = (Channel) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeChannel(Channel value) {
            if (value != null) {
                Channel channel = this.channel_;
                if (channel == null || channel == Channel.getDefaultInstance()) {
                    this.channel_ = value;
                } else {
                    this.channel_ = (Channel) ((Channel.Builder) Channel.newBuilder(this.channel_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearChannel() {
            this.channel_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasChannelCollection() {
            return (this.bitField0_ & 4) != 0;
        }

        public ChannelCollection getChannelCollection() {
            ChannelCollection channelCollection = this.channelCollection_;
            return channelCollection == null ? ChannelCollection.getDefaultInstance() : channelCollection;
        }

        /* access modifiers changed from: private */
        public void setChannelCollection(ChannelCollection value) {
            if (value != null) {
                this.channelCollection_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setChannelCollection(ChannelCollection.Builder builderForValue) {
            this.channelCollection_ = (ChannelCollection) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeChannelCollection(ChannelCollection value) {
            if (value != null) {
                ChannelCollection channelCollection = this.channelCollection_;
                if (channelCollection == null || channelCollection == ChannelCollection.getDefaultInstance()) {
                    this.channelCollection_ = value;
                } else {
                    this.channelCollection_ = (ChannelCollection) ((ChannelCollection.Builder) ChannelCollection.newBuilder(this.channelCollection_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearChannelCollection() {
            this.channelCollection_ = null;
            this.bitField0_ &= -5;
        }

        public boolean hasWatchNextChannel() {
            return (this.bitField0_ & 8) != 0;
        }

        public WatchNextChannel getWatchNextChannel() {
            WatchNextChannel watchNextChannel = this.watchNextChannel_;
            return watchNextChannel == null ? WatchNextChannel.getDefaultInstance() : watchNextChannel;
        }

        /* access modifiers changed from: private */
        public void setWatchNextChannel(WatchNextChannel value) {
            if (value != null) {
                this.watchNextChannel_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWatchNextChannel(WatchNextChannel.Builder builderForValue) {
            this.watchNextChannel_ = (WatchNextChannel) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeWatchNextChannel(WatchNextChannel value) {
            if (value != null) {
                WatchNextChannel watchNextChannel = this.watchNextChannel_;
                if (watchNextChannel == null || watchNextChannel == WatchNextChannel.getDefaultInstance()) {
                    this.watchNextChannel_ = value;
                } else {
                    this.watchNextChannel_ = (WatchNextChannel) ((WatchNextChannel.Builder) WatchNextChannel.newBuilder(this.watchNextChannel_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearWatchNextChannel() {
            this.watchNextChannel_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasNotification() {
            return (this.bitField0_ & 16) != 0;
        }

        public Notification getNotification() {
            Notification notification = this.notification_;
            return notification == null ? Notification.getDefaultInstance() : notification;
        }

        /* access modifiers changed from: private */
        public void setNotification(Notification value) {
            if (value != null) {
                this.notification_ = value;
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setNotification(Notification.Builder builderForValue) {
            this.notification_ = (Notification) builderForValue.build();
            this.bitField0_ |= 16;
        }

        /* access modifiers changed from: private */
        public void mergeNotification(Notification value) {
            if (value != null) {
                Notification notification = this.notification_;
                if (notification == null || notification == Notification.getDefaultInstance()) {
                    this.notification_ = value;
                } else {
                    this.notification_ = (Notification) ((Notification.Builder) Notification.newBuilder(this.notification_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearNotification() {
            this.notification_ = null;
            this.bitField0_ &= -17;
        }

        public boolean hasNotificationCollection() {
            return (this.bitField0_ & 32) != 0;
        }

        public NotificationCollection getNotificationCollection() {
            NotificationCollection notificationCollection = this.notificationCollection_;
            return notificationCollection == null ? NotificationCollection.getDefaultInstance() : notificationCollection;
        }

        /* access modifiers changed from: private */
        public void setNotificationCollection(NotificationCollection value) {
            if (value != null) {
                this.notificationCollection_ = value;
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setNotificationCollection(NotificationCollection.Builder builderForValue) {
            this.notificationCollection_ = (NotificationCollection) builderForValue.build();
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void mergeNotificationCollection(NotificationCollection value) {
            if (value != null) {
                NotificationCollection notificationCollection = this.notificationCollection_;
                if (notificationCollection == null || notificationCollection == NotificationCollection.getDefaultInstance()) {
                    this.notificationCollection_ = value;
                } else {
                    this.notificationCollection_ = (NotificationCollection) ((NotificationCollection.Builder) NotificationCollection.newBuilder(this.notificationCollection_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearNotificationCollection() {
            this.notificationCollection_ = null;
            this.bitField0_ &= -33;
        }

        public boolean hasLaunchItem() {
            return (this.bitField0_ & 64) != 0;
        }

        public LaunchItem getLaunchItem() {
            LaunchItem launchItem = this.launchItem_;
            return launchItem == null ? LaunchItem.getDefaultInstance() : launchItem;
        }

        /* access modifiers changed from: private */
        public void setLaunchItem(LaunchItem value) {
            if (value != null) {
                this.launchItem_ = value;
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setLaunchItem(LaunchItem.Builder builderForValue) {
            this.launchItem_ = (LaunchItem) builderForValue.build();
            this.bitField0_ |= 64;
        }

        /* access modifiers changed from: private */
        public void mergeLaunchItem(LaunchItem value) {
            if (value != null) {
                LaunchItem launchItem = this.launchItem_;
                if (launchItem == null || launchItem == LaunchItem.getDefaultInstance()) {
                    this.launchItem_ = value;
                } else {
                    this.launchItem_ = (LaunchItem) ((LaunchItem.Builder) LaunchItem.newBuilder(this.launchItem_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLaunchItem() {
            this.launchItem_ = null;
            this.bitField0_ &= -65;
        }

        public boolean hasLaunchItemCollection() {
            return (this.bitField0_ & 128) != 0;
        }

        public LaunchItemCollection getLaunchItemCollection() {
            LaunchItemCollection launchItemCollection = this.launchItemCollection_;
            return launchItemCollection == null ? LaunchItemCollection.getDefaultInstance() : launchItemCollection;
        }

        /* access modifiers changed from: private */
        public void setLaunchItemCollection(LaunchItemCollection value) {
            if (value != null) {
                this.launchItemCollection_ = value;
                this.bitField0_ |= 128;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setLaunchItemCollection(LaunchItemCollection.Builder builderForValue) {
            this.launchItemCollection_ = (LaunchItemCollection) builderForValue.build();
            this.bitField0_ |= 128;
        }

        /* access modifiers changed from: private */
        public void mergeLaunchItemCollection(LaunchItemCollection value) {
            if (value != null) {
                LaunchItemCollection launchItemCollection = this.launchItemCollection_;
                if (launchItemCollection == null || launchItemCollection == LaunchItemCollection.getDefaultInstance()) {
                    this.launchItemCollection_ = value;
                } else {
                    this.launchItemCollection_ = (LaunchItemCollection) ((LaunchItemCollection.Builder) LaunchItemCollection.newBuilder(this.launchItemCollection_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 128;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLaunchItemCollection() {
            this.launchItemCollection_ = null;
            this.bitField0_ &= -129;
        }

        public boolean hasInput() {
            return (this.bitField0_ & 256) != 0;
        }

        public Input getInput() {
            Input input = this.input_;
            return input == null ? Input.getDefaultInstance() : input;
        }

        /* access modifiers changed from: private */
        public void setInput(Input value) {
            if (value != null) {
                this.input_ = value;
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setInput(Input.Builder builderForValue) {
            this.input_ = (Input) builderForValue.build();
            this.bitField0_ |= 256;
        }

        /* access modifiers changed from: private */
        public void mergeInput(Input value) {
            if (value != null) {
                Input input = this.input_;
                if (input == null || input == Input.getDefaultInstance()) {
                    this.input_ = value;
                } else {
                    this.input_ = (Input) ((Input.Builder) Input.newBuilder(this.input_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearInput() {
            this.input_ = null;
            this.bitField0_ &= -257;
        }

        public boolean hasInputCollection() {
            return (this.bitField0_ & 512) != 0;
        }

        public InputCollection getInputCollection() {
            InputCollection inputCollection = this.inputCollection_;
            return inputCollection == null ? InputCollection.getDefaultInstance() : inputCollection;
        }

        /* access modifiers changed from: private */
        public void setInputCollection(InputCollection value) {
            if (value != null) {
                this.inputCollection_ = value;
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setInputCollection(InputCollection.Builder builderForValue) {
            this.inputCollection_ = (InputCollection) builderForValue.build();
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void mergeInputCollection(InputCollection value) {
            if (value != null) {
                InputCollection inputCollection = this.inputCollection_;
                if (inputCollection == null || inputCollection == InputCollection.getDefaultInstance()) {
                    this.inputCollection_ = value;
                } else {
                    this.inputCollection_ = (InputCollection) ((InputCollection.Builder) InputCollection.newBuilder(this.inputCollection_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearInputCollection() {
            this.inputCollection_ = null;
            this.bitField0_ &= -513;
        }

        public static VisualElementMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VisualElementMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VisualElementMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VisualElementMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VisualElementMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VisualElementMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VisualElementMetadata parseFrom(InputStream input) throws IOException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static VisualElementMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static VisualElementMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return (VisualElementMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static VisualElementMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VisualElementMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static VisualElementMetadata parseFrom(CodedInputStream input) throws IOException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static VisualElementMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VisualElementMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(VisualElementMetadata prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<VisualElementMetadata, Builder> implements VisualElementMetadataOrBuilder {
            private Builder() {
                super(VisualElementMetadata.DEFAULT_INSTANCE);
            }

            public boolean hasProgram() {
                return ((VisualElementMetadata) this.instance).hasProgram();
            }

            public Program getProgram() {
                return ((VisualElementMetadata) this.instance).getProgram();
            }

            public Builder setProgram(Program value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setProgram(value);
                return this;
            }

            public Builder setProgram(Program.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setProgram(builderForValue);
                return this;
            }

            public Builder mergeProgram(Program value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeProgram(value);
                return this;
            }

            public Builder clearProgram() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearProgram();
                return this;
            }

            public boolean hasChannel() {
                return ((VisualElementMetadata) this.instance).hasChannel();
            }

            public Channel getChannel() {
                return ((VisualElementMetadata) this.instance).getChannel();
            }

            public Builder setChannel(Channel value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setChannel(value);
                return this;
            }

            public Builder setChannel(Channel.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setChannel(builderForValue);
                return this;
            }

            public Builder mergeChannel(Channel value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeChannel(value);
                return this;
            }

            public Builder clearChannel() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearChannel();
                return this;
            }

            public boolean hasChannelCollection() {
                return ((VisualElementMetadata) this.instance).hasChannelCollection();
            }

            public ChannelCollection getChannelCollection() {
                return ((VisualElementMetadata) this.instance).getChannelCollection();
            }

            public Builder setChannelCollection(ChannelCollection value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setChannelCollection(value);
                return this;
            }

            public Builder setChannelCollection(ChannelCollection.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setChannelCollection(builderForValue);
                return this;
            }

            public Builder mergeChannelCollection(ChannelCollection value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeChannelCollection(value);
                return this;
            }

            public Builder clearChannelCollection() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearChannelCollection();
                return this;
            }

            public boolean hasWatchNextChannel() {
                return ((VisualElementMetadata) this.instance).hasWatchNextChannel();
            }

            public WatchNextChannel getWatchNextChannel() {
                return ((VisualElementMetadata) this.instance).getWatchNextChannel();
            }

            public Builder setWatchNextChannel(WatchNextChannel value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setWatchNextChannel(value);
                return this;
            }

            public Builder setWatchNextChannel(WatchNextChannel.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setWatchNextChannel(builderForValue);
                return this;
            }

            public Builder mergeWatchNextChannel(WatchNextChannel value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeWatchNextChannel(value);
                return this;
            }

            public Builder clearWatchNextChannel() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearWatchNextChannel();
                return this;
            }

            public boolean hasNotification() {
                return ((VisualElementMetadata) this.instance).hasNotification();
            }

            public Notification getNotification() {
                return ((VisualElementMetadata) this.instance).getNotification();
            }

            public Builder setNotification(Notification value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setNotification(value);
                return this;
            }

            public Builder setNotification(Notification.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setNotification(builderForValue);
                return this;
            }

            public Builder mergeNotification(Notification value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeNotification(value);
                return this;
            }

            public Builder clearNotification() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearNotification();
                return this;
            }

            public boolean hasNotificationCollection() {
                return ((VisualElementMetadata) this.instance).hasNotificationCollection();
            }

            public NotificationCollection getNotificationCollection() {
                return ((VisualElementMetadata) this.instance).getNotificationCollection();
            }

            public Builder setNotificationCollection(NotificationCollection value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setNotificationCollection(value);
                return this;
            }

            public Builder setNotificationCollection(NotificationCollection.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setNotificationCollection(builderForValue);
                return this;
            }

            public Builder mergeNotificationCollection(NotificationCollection value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeNotificationCollection(value);
                return this;
            }

            public Builder clearNotificationCollection() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearNotificationCollection();
                return this;
            }

            public boolean hasLaunchItem() {
                return ((VisualElementMetadata) this.instance).hasLaunchItem();
            }

            public LaunchItem getLaunchItem() {
                return ((VisualElementMetadata) this.instance).getLaunchItem();
            }

            public Builder setLaunchItem(LaunchItem value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setLaunchItem(value);
                return this;
            }

            public Builder setLaunchItem(LaunchItem.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setLaunchItem(builderForValue);
                return this;
            }

            public Builder mergeLaunchItem(LaunchItem value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeLaunchItem(value);
                return this;
            }

            public Builder clearLaunchItem() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearLaunchItem();
                return this;
            }

            public boolean hasLaunchItemCollection() {
                return ((VisualElementMetadata) this.instance).hasLaunchItemCollection();
            }

            public LaunchItemCollection getLaunchItemCollection() {
                return ((VisualElementMetadata) this.instance).getLaunchItemCollection();
            }

            public Builder setLaunchItemCollection(LaunchItemCollection value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setLaunchItemCollection(value);
                return this;
            }

            public Builder setLaunchItemCollection(LaunchItemCollection.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setLaunchItemCollection(builderForValue);
                return this;
            }

            public Builder mergeLaunchItemCollection(LaunchItemCollection value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeLaunchItemCollection(value);
                return this;
            }

            public Builder clearLaunchItemCollection() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearLaunchItemCollection();
                return this;
            }

            public boolean hasInput() {
                return ((VisualElementMetadata) this.instance).hasInput();
            }

            public Input getInput() {
                return ((VisualElementMetadata) this.instance).getInput();
            }

            public Builder setInput(Input value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setInput(value);
                return this;
            }

            public Builder setInput(Input.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setInput(builderForValue);
                return this;
            }

            public Builder mergeInput(Input value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeInput(value);
                return this;
            }

            public Builder clearInput() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearInput();
                return this;
            }

            public boolean hasInputCollection() {
                return ((VisualElementMetadata) this.instance).hasInputCollection();
            }

            public InputCollection getInputCollection() {
                return ((VisualElementMetadata) this.instance).getInputCollection();
            }

            public Builder setInputCollection(InputCollection value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setInputCollection(value);
                return this;
            }

            public Builder setInputCollection(InputCollection.Builder builderForValue) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).setInputCollection(builderForValue);
                return this;
            }

            public Builder mergeInputCollection(InputCollection value) {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).mergeInputCollection(value);
                return this;
            }

            public Builder clearInputCollection() {
                copyOnWrite();
                ((VisualElementMetadata) this.instance).clearInputCollection();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new VisualElementMetadata();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\n\u0000\u0001\u0001\n\n\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003\u0005\t\u0004\u0006\t\u0005\u0007\t\u0006\b\t\u0007\t\t\b\n\t\t", new Object[]{"bitField0_", "program_", "channel_", "channelCollection_", "watchNextChannel_", "notification_", "notificationCollection_", "launchItem_", "launchItemCollection_", "input_", "inputCollection_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<VisualElementMetadata> parser = PARSER;
                    if (parser == null) {
                        synchronized (VisualElementMetadata.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(VisualElementMetadata.class, DEFAULT_INSTANCE);
        }

        public static VisualElementMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<VisualElementMetadata> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Program extends GeneratedMessageLite<Program, Builder> implements ProgramOrBuilder {
        /* access modifiers changed from: private */
        public static final Program DEFAULT_INSTANCE = new Program();
        public static final int GENRE_FIELD_NUMBER = 7;
        public static final int HAS_BADGE_FIELD_NUMBER = 12;
        public static final int HAS_CONTENT_RATING_FIELD_NUMBER = 10;
        public static final int HAS_DESCRIPTION_FIELD_NUMBER = 9;
        public static final int HAS_PROGRESS_FIELD_NUMBER = 11;
        public static final int INTERACTION_COUNT_FIELD_NUMBER = 6;
        public static final int IS_LIVE_FIELD_NUMBER = 8;
        public static final int METADATA_HASH_CODE_FIELD_NUMBER = 2;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
        private static volatile Parser<Program> PARSER = null;
        public static final int PREVIEW_FIELD_NUMBER = 5;
        public static final int RATING_FIELD_NUMBER = 4;
        public static final int TYPE_FIELD_NUMBER = 3;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 7, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private String genre_ = "";
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private boolean hasBadge_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private boolean hasContentRating_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private boolean hasDescription_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private boolean hasProgress_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private InteractionCount interactionCount_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private boolean isLive_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private long metadataHashCode_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String packageName_ = "";
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private Preview preview_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private Rating rating_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int type_ = 1;

        public interface InteractionCountOrBuilder extends MessageLiteOrBuilder {
            long getCount();

            InteractionCount.Type getType();

            boolean hasCount();

            boolean hasType();
        }

        public interface PreviewOrBuilder extends MessageLiteOrBuilder {
            int getDurationSeconds();

            int getPlayedDurationSeconds();

            long getPlayedTimestamp();

            Preview.PreviewType getType();

            boolean hasDurationSeconds();

            boolean hasPlayedDurationSeconds();

            boolean hasPlayedTimestamp();

            boolean hasType();
        }

        public interface RatingOrBuilder extends MessageLiteOrBuilder {
            float getPercentage();

            float getStarCount();

            long getThumbsDownCount();

            long getThumbsUpCount();

            boolean hasPercentage();

            boolean hasStarCount();

            boolean hasThumbsDownCount();

            boolean hasThumbsUpCount();
        }

        private Program() {
        }

        public enum Type implements Internal.EnumLite {
            MOVIE(1),
            TV_SERIES(2),
            TV_SEASON(3),
            TV_EPISODE(4),
            CLIP(5),
            EVENT(6),
            CHANNEL(7),
            TRACK(8),
            ALBUM(9),
            ARTIST(10),
            PLAYLIST(11),
            STATION(12),
            GAME(13);
            
            public static final int ALBUM_VALUE = 9;
            public static final int ARTIST_VALUE = 10;
            public static final int CHANNEL_VALUE = 7;
            public static final int CLIP_VALUE = 5;
            public static final int EVENT_VALUE = 6;
            public static final int GAME_VALUE = 13;
            public static final int MOVIE_VALUE = 1;
            public static final int PLAYLIST_VALUE = 11;
            public static final int STATION_VALUE = 12;
            public static final int TRACK_VALUE = 8;
            public static final int TV_EPISODE_VALUE = 4;
            public static final int TV_SEASON_VALUE = 3;
            public static final int TV_SERIES_VALUE = 2;
            private static final Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap<Type>() {
                public Type findValueByNumber(int number) {
                    return Type.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static Type forNumber(int value2) {
                switch (value2) {
                    case 1:
                        return MOVIE;
                    case 2:
                        return TV_SERIES;
                    case 3:
                        return TV_SEASON;
                    case 4:
                        return TV_EPISODE;
                    case 5:
                        return CLIP;
                    case 6:
                        return EVENT;
                    case 7:
                        return CHANNEL;
                    case 8:
                        return TRACK;
                    case 9:
                        return ALBUM;
                    case 10:
                        return ARTIST;
                    case 11:
                        return PLAYLIST;
                    case 12:
                        return STATION;
                    case 13:
                        return GAME;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TypeVerifier.INSTANCE;
            }

            private static final class TypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new TypeVerifier();

                private TypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return Type.forNumber(number) != null;
                }
            }

            private Type(int value2) {
                this.value = value2;
            }
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class Rating extends GeneratedMessageLite<Rating, Builder> implements RatingOrBuilder {
            /* access modifiers changed from: private */
            public static final Rating DEFAULT_INSTANCE = new Rating();
            private static volatile Parser<Rating> PARSER = null;
            public static final int PERCENTAGE_FIELD_NUMBER = 2;
            public static final int STAR_COUNT_FIELD_NUMBER = 1;
            public static final int THUMBS_DOWN_COUNT_FIELD_NUMBER = 4;
            public static final int THUMBS_UP_COUNT_FIELD_NUMBER = 3;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.FLOAT)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private float percentage_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.FLOAT)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private float starCount_;
            @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.UINT64)
            @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
            private long thumbsDownCount_;
            @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.UINT64)
            @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
            private long thumbsUpCount_;

            private Rating() {
            }

            public boolean hasStarCount() {
                return (this.bitField0_ & 1) != 0;
            }

            public float getStarCount() {
                return this.starCount_;
            }

            /* access modifiers changed from: private */
            public void setStarCount(float value) {
                this.bitField0_ |= 1;
                this.starCount_ = value;
            }

            /* access modifiers changed from: private */
            public void clearStarCount() {
                this.bitField0_ &= -2;
                this.starCount_ = 0.0f;
            }

            public boolean hasPercentage() {
                return (this.bitField0_ & 2) != 0;
            }

            public float getPercentage() {
                return this.percentage_;
            }

            /* access modifiers changed from: private */
            public void setPercentage(float value) {
                this.bitField0_ |= 2;
                this.percentage_ = value;
            }

            /* access modifiers changed from: private */
            public void clearPercentage() {
                this.bitField0_ &= -3;
                this.percentage_ = 0.0f;
            }

            public boolean hasThumbsUpCount() {
                return (this.bitField0_ & 4) != 0;
            }

            public long getThumbsUpCount() {
                return this.thumbsUpCount_;
            }

            /* access modifiers changed from: private */
            public void setThumbsUpCount(long value) {
                this.bitField0_ |= 4;
                this.thumbsUpCount_ = value;
            }

            /* access modifiers changed from: private */
            public void clearThumbsUpCount() {
                this.bitField0_ &= -5;
                this.thumbsUpCount_ = 0;
            }

            public boolean hasThumbsDownCount() {
                return (this.bitField0_ & 8) != 0;
            }

            public long getThumbsDownCount() {
                return this.thumbsDownCount_;
            }

            /* access modifiers changed from: private */
            public void setThumbsDownCount(long value) {
                this.bitField0_ |= 8;
                this.thumbsDownCount_ = value;
            }

            /* access modifiers changed from: private */
            public void clearThumbsDownCount() {
                this.bitField0_ &= -9;
                this.thumbsDownCount_ = 0;
            }

            public static Rating parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Rating parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Rating parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Rating parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Rating parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Rating parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Rating parseFrom(InputStream input) throws IOException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Rating parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Rating parseDelimitedFrom(InputStream input) throws IOException {
                return (Rating) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Rating parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Rating) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Rating parseFrom(CodedInputStream input) throws IOException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Rating parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Rating) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Rating prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Rating, Builder> implements RatingOrBuilder {
                private Builder() {
                    super(Rating.DEFAULT_INSTANCE);
                }

                public boolean hasStarCount() {
                    return ((Rating) this.instance).hasStarCount();
                }

                public float getStarCount() {
                    return ((Rating) this.instance).getStarCount();
                }

                public Builder setStarCount(float value) {
                    copyOnWrite();
                    ((Rating) this.instance).setStarCount(value);
                    return this;
                }

                public Builder clearStarCount() {
                    copyOnWrite();
                    ((Rating) this.instance).clearStarCount();
                    return this;
                }

                public boolean hasPercentage() {
                    return ((Rating) this.instance).hasPercentage();
                }

                public float getPercentage() {
                    return ((Rating) this.instance).getPercentage();
                }

                public Builder setPercentage(float value) {
                    copyOnWrite();
                    ((Rating) this.instance).setPercentage(value);
                    return this;
                }

                public Builder clearPercentage() {
                    copyOnWrite();
                    ((Rating) this.instance).clearPercentage();
                    return this;
                }

                public boolean hasThumbsUpCount() {
                    return ((Rating) this.instance).hasThumbsUpCount();
                }

                public long getThumbsUpCount() {
                    return ((Rating) this.instance).getThumbsUpCount();
                }

                public Builder setThumbsUpCount(long value) {
                    copyOnWrite();
                    ((Rating) this.instance).setThumbsUpCount(value);
                    return this;
                }

                public Builder clearThumbsUpCount() {
                    copyOnWrite();
                    ((Rating) this.instance).clearThumbsUpCount();
                    return this;
                }

                public boolean hasThumbsDownCount() {
                    return ((Rating) this.instance).hasThumbsDownCount();
                }

                public long getThumbsDownCount() {
                    return ((Rating) this.instance).getThumbsDownCount();
                }

                public Builder setThumbsDownCount(long value) {
                    copyOnWrite();
                    ((Rating) this.instance).setThumbsDownCount(value);
                    return this;
                }

                public Builder clearThumbsDownCount() {
                    copyOnWrite();
                    ((Rating) this.instance).clearThumbsDownCount();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Rating();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0001\u0000\u0002\u0001\u0001\u0003\u0003\u0002\u0004\u0003\u0003", new Object[]{"bitField0_", "starCount_", "percentage_", "thumbsUpCount_", "thumbsDownCount_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Rating> parser = PARSER;
                        if (parser == null) {
                            synchronized (Rating.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return (byte) 1;
                    case SET_MEMOIZED_IS_INITIALIZED:
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(Rating.class, DEFAULT_INSTANCE);
            }

            public static Rating getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Rating> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class Preview extends GeneratedMessageLite<Preview, Builder> implements PreviewOrBuilder {
            /* access modifiers changed from: private */
            public static final Preview DEFAULT_INSTANCE = new Preview();
            public static final int DURATION_SECONDS_FIELD_NUMBER = 2;
            private static volatile Parser<Preview> PARSER = null;
            public static final int PLAYED_DURATION_SECONDS_FIELD_NUMBER = 4;
            public static final int PLAYED_TIMESTAMP_FIELD_NUMBER = 3;
            public static final int TYPE_FIELD_NUMBER = 1;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private int durationSeconds_;
            @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
            private int playedDurationSeconds_;
            @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
            @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
            private long playedTimestamp_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private int type_ = 1;

            private Preview() {
            }

            public enum PreviewType implements Internal.EnumLite {
                VIDEO(1),
                AUDIO(2);
                
                public static final int AUDIO_VALUE = 2;
                public static final int VIDEO_VALUE = 1;
                private static final Internal.EnumLiteMap<PreviewType> internalValueMap = new Internal.EnumLiteMap<PreviewType>() {
                    public PreviewType findValueByNumber(int number) {
                        return PreviewType.forNumber(number);
                    }
                };
                private final int value;

                public final int getNumber() {
                    return this.value;
                }

                public static PreviewType forNumber(int value2) {
                    if (value2 == 1) {
                        return VIDEO;
                    }
                    if (value2 != 2) {
                        return null;
                    }
                    return AUDIO;
                }

                public static Internal.EnumLiteMap<PreviewType> internalGetValueMap() {
                    return internalValueMap;
                }

                public static Internal.EnumVerifier internalGetVerifier() {
                    return PreviewTypeVerifier.INSTANCE;
                }

                private static final class PreviewTypeVerifier implements Internal.EnumVerifier {
                    static final Internal.EnumVerifier INSTANCE = new PreviewTypeVerifier();

                    private PreviewTypeVerifier() {
                    }

                    public boolean isInRange(int number) {
                        return PreviewType.forNumber(number) != null;
                    }
                }

                private PreviewType(int value2) {
                    this.value = value2;
                }
            }

            public boolean hasType() {
                return (this.bitField0_ & 1) != 0;
            }

            public PreviewType getType() {
                PreviewType result = PreviewType.forNumber(this.type_);
                return result == null ? PreviewType.VIDEO : result;
            }

            /* access modifiers changed from: private */
            public void setType(PreviewType value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.type_ = value.getNumber();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearType() {
                this.bitField0_ &= -2;
                this.type_ = 1;
            }

            public boolean hasDurationSeconds() {
                return (this.bitField0_ & 2) != 0;
            }

            public int getDurationSeconds() {
                return this.durationSeconds_;
            }

            /* access modifiers changed from: private */
            public void setDurationSeconds(int value) {
                this.bitField0_ |= 2;
                this.durationSeconds_ = value;
            }

            /* access modifiers changed from: private */
            public void clearDurationSeconds() {
                this.bitField0_ &= -3;
                this.durationSeconds_ = 0;
            }

            public boolean hasPlayedTimestamp() {
                return (this.bitField0_ & 4) != 0;
            }

            public long getPlayedTimestamp() {
                return this.playedTimestamp_;
            }

            /* access modifiers changed from: private */
            public void setPlayedTimestamp(long value) {
                this.bitField0_ |= 4;
                this.playedTimestamp_ = value;
            }

            /* access modifiers changed from: private */
            public void clearPlayedTimestamp() {
                this.bitField0_ &= -5;
                this.playedTimestamp_ = 0;
            }

            public boolean hasPlayedDurationSeconds() {
                return (this.bitField0_ & 8) != 0;
            }

            public int getPlayedDurationSeconds() {
                return this.playedDurationSeconds_;
            }

            /* access modifiers changed from: private */
            public void setPlayedDurationSeconds(int value) {
                this.bitField0_ |= 8;
                this.playedDurationSeconds_ = value;
            }

            /* access modifiers changed from: private */
            public void clearPlayedDurationSeconds() {
                this.bitField0_ &= -9;
                this.playedDurationSeconds_ = 0;
            }

            public static Preview parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Preview parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Preview parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Preview parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Preview parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static Preview parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static Preview parseFrom(InputStream input) throws IOException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Preview parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Preview parseDelimitedFrom(InputStream input) throws IOException {
                return (Preview) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static Preview parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Preview) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Preview parseFrom(CodedInputStream input) throws IOException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static Preview parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Preview) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(Preview prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<Preview, Builder> implements PreviewOrBuilder {
                private Builder() {
                    super(Preview.DEFAULT_INSTANCE);
                }

                public boolean hasType() {
                    return ((Preview) this.instance).hasType();
                }

                public PreviewType getType() {
                    return ((Preview) this.instance).getType();
                }

                public Builder setType(PreviewType value) {
                    copyOnWrite();
                    ((Preview) this.instance).setType(value);
                    return this;
                }

                public Builder clearType() {
                    copyOnWrite();
                    ((Preview) this.instance).clearType();
                    return this;
                }

                public boolean hasDurationSeconds() {
                    return ((Preview) this.instance).hasDurationSeconds();
                }

                public int getDurationSeconds() {
                    return ((Preview) this.instance).getDurationSeconds();
                }

                public Builder setDurationSeconds(int value) {
                    copyOnWrite();
                    ((Preview) this.instance).setDurationSeconds(value);
                    return this;
                }

                public Builder clearDurationSeconds() {
                    copyOnWrite();
                    ((Preview) this.instance).clearDurationSeconds();
                    return this;
                }

                public boolean hasPlayedTimestamp() {
                    return ((Preview) this.instance).hasPlayedTimestamp();
                }

                public long getPlayedTimestamp() {
                    return ((Preview) this.instance).getPlayedTimestamp();
                }

                public Builder setPlayedTimestamp(long value) {
                    copyOnWrite();
                    ((Preview) this.instance).setPlayedTimestamp(value);
                    return this;
                }

                public Builder clearPlayedTimestamp() {
                    copyOnWrite();
                    ((Preview) this.instance).clearPlayedTimestamp();
                    return this;
                }

                public boolean hasPlayedDurationSeconds() {
                    return ((Preview) this.instance).hasPlayedDurationSeconds();
                }

                public int getPlayedDurationSeconds() {
                    return ((Preview) this.instance).getPlayedDurationSeconds();
                }

                public Builder setPlayedDurationSeconds(int value) {
                    copyOnWrite();
                    ((Preview) this.instance).setPlayedDurationSeconds(value);
                    return this;
                }

                public Builder clearPlayedDurationSeconds() {
                    copyOnWrite();
                    ((Preview) this.instance).clearPlayedDurationSeconds();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new Preview();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\u0002\u0002\u0004\u0004\u0003", new Object[]{"bitField0_", "type_", PreviewType.internalGetVerifier(), "durationSeconds_", "playedTimestamp_", "playedDurationSeconds_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<Preview> parser = PARSER;
                        if (parser == null) {
                            synchronized (Preview.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return (byte) 1;
                    case SET_MEMOIZED_IS_INITIALIZED:
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(Preview.class, DEFAULT_INSTANCE);
            }

            public static Preview getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<Preview> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class InteractionCount extends GeneratedMessageLite<InteractionCount, Builder> implements InteractionCountOrBuilder {
            public static final int COUNT_FIELD_NUMBER = 2;
            /* access modifiers changed from: private */
            public static final InteractionCount DEFAULT_INSTANCE = new InteractionCount();
            private static volatile Parser<InteractionCount> PARSER = null;
            public static final int TYPE_FIELD_NUMBER = 1;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT64)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private long count_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private int type_ = 1;

            private InteractionCount() {
            }

            public enum Type implements Internal.EnumLite {
                VIEWS(1),
                LISTENS(2),
                FOLLOWERS(3),
                FANS(4),
                LIKES(5),
                THUMBS(6),
                VIEWERS(7);
                
                public static final int FANS_VALUE = 4;
                public static final int FOLLOWERS_VALUE = 3;
                public static final int LIKES_VALUE = 5;
                public static final int LISTENS_VALUE = 2;
                public static final int THUMBS_VALUE = 6;
                public static final int VIEWERS_VALUE = 7;
                public static final int VIEWS_VALUE = 1;
                private static final Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap<Type>() {
                    public Type findValueByNumber(int number) {
                        return Type.forNumber(number);
                    }
                };
                private final int value;

                public final int getNumber() {
                    return this.value;
                }

                public static Type forNumber(int value2) {
                    switch (value2) {
                        case 1:
                            return VIEWS;
                        case 2:
                            return LISTENS;
                        case 3:
                            return FOLLOWERS;
                        case 4:
                            return FANS;
                        case 5:
                            return LIKES;
                        case 6:
                            return THUMBS;
                        case 7:
                            return VIEWERS;
                        default:
                            return null;
                    }
                }

                public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                    return internalValueMap;
                }

                public static Internal.EnumVerifier internalGetVerifier() {
                    return TypeVerifier.INSTANCE;
                }

                private static final class TypeVerifier implements Internal.EnumVerifier {
                    static final Internal.EnumVerifier INSTANCE = new TypeVerifier();

                    private TypeVerifier() {
                    }

                    public boolean isInRange(int number) {
                        return Type.forNumber(number) != null;
                    }
                }

                private Type(int value2) {
                    this.value = value2;
                }
            }

            public boolean hasType() {
                return (this.bitField0_ & 1) != 0;
            }

            public Type getType() {
                Type result = Type.forNumber(this.type_);
                return result == null ? Type.VIEWS : result;
            }

            /* access modifiers changed from: private */
            public void setType(Type value) {
                if (value != null) {
                    this.bitField0_ |= 1;
                    this.type_ = value.getNumber();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearType() {
                this.bitField0_ &= -2;
                this.type_ = 1;
            }

            public boolean hasCount() {
                return (this.bitField0_ & 2) != 0;
            }

            public long getCount() {
                return this.count_;
            }

            /* access modifiers changed from: private */
            public void setCount(long value) {
                this.bitField0_ |= 2;
                this.count_ = value;
            }

            /* access modifiers changed from: private */
            public void clearCount() {
                this.bitField0_ &= -3;
                this.count_ = 0;
            }

            public static InteractionCount parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static InteractionCount parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static InteractionCount parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static InteractionCount parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static InteractionCount parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static InteractionCount parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static InteractionCount parseFrom(InputStream input) throws IOException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static InteractionCount parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static InteractionCount parseDelimitedFrom(InputStream input) throws IOException {
                return (InteractionCount) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static InteractionCount parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (InteractionCount) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static InteractionCount parseFrom(CodedInputStream input) throws IOException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static InteractionCount parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (InteractionCount) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(InteractionCount prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<InteractionCount, Builder> implements InteractionCountOrBuilder {
                private Builder() {
                    super(InteractionCount.DEFAULT_INSTANCE);
                }

                public boolean hasType() {
                    return ((InteractionCount) this.instance).hasType();
                }

                public Type getType() {
                    return ((InteractionCount) this.instance).getType();
                }

                public Builder setType(Type value) {
                    copyOnWrite();
                    ((InteractionCount) this.instance).setType(value);
                    return this;
                }

                public Builder clearType() {
                    copyOnWrite();
                    ((InteractionCount) this.instance).clearType();
                    return this;
                }

                public boolean hasCount() {
                    return ((InteractionCount) this.instance).hasCount();
                }

                public long getCount() {
                    return ((InteractionCount) this.instance).getCount();
                }

                public Builder setCount(long value) {
                    copyOnWrite();
                    ((InteractionCount) this.instance).setCount(value);
                    return this;
                }

                public Builder clearCount() {
                    copyOnWrite();
                    ((InteractionCount) this.instance).clearCount();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new InteractionCount();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0000\u0002\u0002\u0001", new Object[]{"bitField0_", "type_", Type.internalGetVerifier(), "count_"});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<InteractionCount> parser = PARSER;
                        if (parser == null) {
                            synchronized (InteractionCount.class) {
                                parser = PARSER;
                                if (parser == null) {
                                    parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                    PARSER = parser;
                                }
                            }
                        }
                        return parser;
                    case GET_MEMOIZED_IS_INITIALIZED:
                        return (byte) 1;
                    case SET_MEMOIZED_IS_INITIALIZED:
                        return null;
                    default:
                        throw new UnsupportedOperationException();
                }
            }

            static {
                GeneratedMessageLite.registerDefaultInstance(InteractionCount.class, DEFAULT_INSTANCE);
            }

            public static InteractionCount getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<InteractionCount> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasPackageName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getPackageName() {
            return this.packageName_;
        }

        public ByteString getPackageNameBytes() {
            return ByteString.copyFromUtf8(this.packageName_);
        }

        /* access modifiers changed from: private */
        public void setPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageName() {
            this.bitField0_ &= -2;
            this.packageName_ = getDefaultInstance().getPackageName();
        }

        /* access modifiers changed from: private */
        public void setPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasMetadataHashCode() {
            return (this.bitField0_ & 2) != 0;
        }

        public long getMetadataHashCode() {
            return this.metadataHashCode_;
        }

        /* access modifiers changed from: private */
        public void setMetadataHashCode(long value) {
            this.bitField0_ |= 2;
            this.metadataHashCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMetadataHashCode() {
            this.bitField0_ &= -3;
            this.metadataHashCode_ = 0;
        }

        public boolean hasType() {
            return (this.bitField0_ & 4) != 0;
        }

        public Type getType() {
            Type result = Type.forNumber(this.type_);
            return result == null ? Type.MOVIE : result;
        }

        /* access modifiers changed from: private */
        public void setType(Type value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.type_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearType() {
            this.bitField0_ &= -5;
            this.type_ = 1;
        }

        public boolean hasRating() {
            return (this.bitField0_ & 8) != 0;
        }

        public Rating getRating() {
            Rating rating = this.rating_;
            return rating == null ? Rating.getDefaultInstance() : rating;
        }

        /* access modifiers changed from: private */
        public void setRating(Rating value) {
            if (value != null) {
                this.rating_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setRating(Rating.Builder builderForValue) {
            this.rating_ = (Rating) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeRating(Rating value) {
            if (value != null) {
                Rating rating = this.rating_;
                if (rating == null || rating == Rating.getDefaultInstance()) {
                    this.rating_ = value;
                } else {
                    this.rating_ = (Rating) ((Rating.Builder) Rating.newBuilder(this.rating_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearRating() {
            this.rating_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasPreview() {
            return (this.bitField0_ & 16) != 0;
        }

        public Preview getPreview() {
            Preview preview = this.preview_;
            return preview == null ? Preview.getDefaultInstance() : preview;
        }

        /* access modifiers changed from: private */
        public void setPreview(Preview value) {
            if (value != null) {
                this.preview_ = value;
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPreview(Preview.Builder builderForValue) {
            this.preview_ = (Preview) builderForValue.build();
            this.bitField0_ |= 16;
        }

        /* access modifiers changed from: private */
        public void mergePreview(Preview value) {
            if (value != null) {
                Preview preview = this.preview_;
                if (preview == null || preview == Preview.getDefaultInstance()) {
                    this.preview_ = value;
                } else {
                    this.preview_ = (Preview) ((Preview.Builder) Preview.newBuilder(this.preview_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPreview() {
            this.preview_ = null;
            this.bitField0_ &= -17;
        }

        public boolean hasInteractionCount() {
            return (this.bitField0_ & 32) != 0;
        }

        public InteractionCount getInteractionCount() {
            InteractionCount interactionCount = this.interactionCount_;
            return interactionCount == null ? InteractionCount.getDefaultInstance() : interactionCount;
        }

        /* access modifiers changed from: private */
        public void setInteractionCount(InteractionCount value) {
            if (value != null) {
                this.interactionCount_ = value;
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setInteractionCount(InteractionCount.Builder builderForValue) {
            this.interactionCount_ = (InteractionCount) builderForValue.build();
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void mergeInteractionCount(InteractionCount value) {
            if (value != null) {
                InteractionCount interactionCount = this.interactionCount_;
                if (interactionCount == null || interactionCount == InteractionCount.getDefaultInstance()) {
                    this.interactionCount_ = value;
                } else {
                    this.interactionCount_ = (InteractionCount) ((InteractionCount.Builder) InteractionCount.newBuilder(this.interactionCount_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearInteractionCount() {
            this.interactionCount_ = null;
            this.bitField0_ &= -33;
        }

        public boolean hasGenre() {
            return (this.bitField0_ & 64) != 0;
        }

        public String getGenre() {
            return this.genre_;
        }

        public ByteString getGenreBytes() {
            return ByteString.copyFromUtf8(this.genre_);
        }

        /* access modifiers changed from: private */
        public void setGenre(String value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.genre_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearGenre() {
            this.bitField0_ &= -65;
            this.genre_ = getDefaultInstance().getGenre();
        }

        /* access modifiers changed from: private */
        public void setGenreBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.genre_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasIsLive() {
            return (this.bitField0_ & 128) != 0;
        }

        public boolean getIsLive() {
            return this.isLive_;
        }

        /* access modifiers changed from: private */
        public void setIsLive(boolean value) {
            this.bitField0_ |= 128;
            this.isLive_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsLive() {
            this.bitField0_ &= -129;
            this.isLive_ = false;
        }

        public boolean hasHasDescription() {
            return (this.bitField0_ & 256) != 0;
        }

        public boolean getHasDescription() {
            return this.hasDescription_;
        }

        /* access modifiers changed from: private */
        public void setHasDescription(boolean value) {
            this.bitField0_ |= 256;
            this.hasDescription_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasDescription() {
            this.bitField0_ &= -257;
            this.hasDescription_ = false;
        }

        public boolean hasHasContentRating() {
            return (this.bitField0_ & 512) != 0;
        }

        public boolean getHasContentRating() {
            return this.hasContentRating_;
        }

        /* access modifiers changed from: private */
        public void setHasContentRating(boolean value) {
            this.bitField0_ |= 512;
            this.hasContentRating_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasContentRating() {
            this.bitField0_ &= -513;
            this.hasContentRating_ = false;
        }

        public boolean hasHasProgress() {
            return (this.bitField0_ & 1024) != 0;
        }

        public boolean getHasProgress() {
            return this.hasProgress_;
        }

        /* access modifiers changed from: private */
        public void setHasProgress(boolean value) {
            this.bitField0_ |= 1024;
            this.hasProgress_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasProgress() {
            this.bitField0_ &= -1025;
            this.hasProgress_ = false;
        }

        public boolean hasHasBadge() {
            return (this.bitField0_ & 2048) != 0;
        }

        public boolean getHasBadge() {
            return this.hasBadge_;
        }

        /* access modifiers changed from: private */
        public void setHasBadge(boolean value) {
            this.bitField0_ |= 2048;
            this.hasBadge_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasBadge() {
            this.bitField0_ &= -2049;
            this.hasBadge_ = false;
        }

        public static Program parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Program parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Program parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Program parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Program parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Program parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Program parseFrom(InputStream input) throws IOException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Program parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Program parseDelimitedFrom(InputStream input) throws IOException {
            return (Program) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Program parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Program) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Program parseFrom(CodedInputStream input) throws IOException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Program parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Program) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Program prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Program, Builder> implements ProgramOrBuilder {
            private Builder() {
                super(Program.DEFAULT_INSTANCE);
            }

            public boolean hasPackageName() {
                return ((Program) this.instance).hasPackageName();
            }

            public String getPackageName() {
                return ((Program) this.instance).getPackageName();
            }

            public ByteString getPackageNameBytes() {
                return ((Program) this.instance).getPackageNameBytes();
            }

            public Builder setPackageName(String value) {
                copyOnWrite();
                ((Program) this.instance).setPackageName(value);
                return this;
            }

            public Builder clearPackageName() {
                copyOnWrite();
                ((Program) this.instance).clearPackageName();
                return this;
            }

            public Builder setPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((Program) this.instance).setPackageNameBytes(value);
                return this;
            }

            public boolean hasMetadataHashCode() {
                return ((Program) this.instance).hasMetadataHashCode();
            }

            public long getMetadataHashCode() {
                return ((Program) this.instance).getMetadataHashCode();
            }

            public Builder setMetadataHashCode(long value) {
                copyOnWrite();
                ((Program) this.instance).setMetadataHashCode(value);
                return this;
            }

            public Builder clearMetadataHashCode() {
                copyOnWrite();
                ((Program) this.instance).clearMetadataHashCode();
                return this;
            }

            public boolean hasType() {
                return ((Program) this.instance).hasType();
            }

            public Type getType() {
                return ((Program) this.instance).getType();
            }

            public Builder setType(Type value) {
                copyOnWrite();
                ((Program) this.instance).setType(value);
                return this;
            }

            public Builder clearType() {
                copyOnWrite();
                ((Program) this.instance).clearType();
                return this;
            }

            public boolean hasRating() {
                return ((Program) this.instance).hasRating();
            }

            public Rating getRating() {
                return ((Program) this.instance).getRating();
            }

            public Builder setRating(Rating value) {
                copyOnWrite();
                ((Program) this.instance).setRating(value);
                return this;
            }

            public Builder setRating(Rating.Builder builderForValue) {
                copyOnWrite();
                ((Program) this.instance).setRating(builderForValue);
                return this;
            }

            public Builder mergeRating(Rating value) {
                copyOnWrite();
                ((Program) this.instance).mergeRating(value);
                return this;
            }

            public Builder clearRating() {
                copyOnWrite();
                ((Program) this.instance).clearRating();
                return this;
            }

            public boolean hasPreview() {
                return ((Program) this.instance).hasPreview();
            }

            public Preview getPreview() {
                return ((Program) this.instance).getPreview();
            }

            public Builder setPreview(Preview value) {
                copyOnWrite();
                ((Program) this.instance).setPreview(value);
                return this;
            }

            public Builder setPreview(Preview.Builder builderForValue) {
                copyOnWrite();
                ((Program) this.instance).setPreview(builderForValue);
                return this;
            }

            public Builder mergePreview(Preview value) {
                copyOnWrite();
                ((Program) this.instance).mergePreview(value);
                return this;
            }

            public Builder clearPreview() {
                copyOnWrite();
                ((Program) this.instance).clearPreview();
                return this;
            }

            public boolean hasInteractionCount() {
                return ((Program) this.instance).hasInteractionCount();
            }

            public InteractionCount getInteractionCount() {
                return ((Program) this.instance).getInteractionCount();
            }

            public Builder setInteractionCount(InteractionCount value) {
                copyOnWrite();
                ((Program) this.instance).setInteractionCount(value);
                return this;
            }

            public Builder setInteractionCount(InteractionCount.Builder builderForValue) {
                copyOnWrite();
                ((Program) this.instance).setInteractionCount(builderForValue);
                return this;
            }

            public Builder mergeInteractionCount(InteractionCount value) {
                copyOnWrite();
                ((Program) this.instance).mergeInteractionCount(value);
                return this;
            }

            public Builder clearInteractionCount() {
                copyOnWrite();
                ((Program) this.instance).clearInteractionCount();
                return this;
            }

            public boolean hasGenre() {
                return ((Program) this.instance).hasGenre();
            }

            public String getGenre() {
                return ((Program) this.instance).getGenre();
            }

            public ByteString getGenreBytes() {
                return ((Program) this.instance).getGenreBytes();
            }

            public Builder setGenre(String value) {
                copyOnWrite();
                ((Program) this.instance).setGenre(value);
                return this;
            }

            public Builder clearGenre() {
                copyOnWrite();
                ((Program) this.instance).clearGenre();
                return this;
            }

            public Builder setGenreBytes(ByteString value) {
                copyOnWrite();
                ((Program) this.instance).setGenreBytes(value);
                return this;
            }

            public boolean hasIsLive() {
                return ((Program) this.instance).hasIsLive();
            }

            public boolean getIsLive() {
                return ((Program) this.instance).getIsLive();
            }

            public Builder setIsLive(boolean value) {
                copyOnWrite();
                ((Program) this.instance).setIsLive(value);
                return this;
            }

            public Builder clearIsLive() {
                copyOnWrite();
                ((Program) this.instance).clearIsLive();
                return this;
            }

            public boolean hasHasDescription() {
                return ((Program) this.instance).hasHasDescription();
            }

            public boolean getHasDescription() {
                return ((Program) this.instance).getHasDescription();
            }

            public Builder setHasDescription(boolean value) {
                copyOnWrite();
                ((Program) this.instance).setHasDescription(value);
                return this;
            }

            public Builder clearHasDescription() {
                copyOnWrite();
                ((Program) this.instance).clearHasDescription();
                return this;
            }

            public boolean hasHasContentRating() {
                return ((Program) this.instance).hasHasContentRating();
            }

            public boolean getHasContentRating() {
                return ((Program) this.instance).getHasContentRating();
            }

            public Builder setHasContentRating(boolean value) {
                copyOnWrite();
                ((Program) this.instance).setHasContentRating(value);
                return this;
            }

            public Builder clearHasContentRating() {
                copyOnWrite();
                ((Program) this.instance).clearHasContentRating();
                return this;
            }

            public boolean hasHasProgress() {
                return ((Program) this.instance).hasHasProgress();
            }

            public boolean getHasProgress() {
                return ((Program) this.instance).getHasProgress();
            }

            public Builder setHasProgress(boolean value) {
                copyOnWrite();
                ((Program) this.instance).setHasProgress(value);
                return this;
            }

            public Builder clearHasProgress() {
                copyOnWrite();
                ((Program) this.instance).clearHasProgress();
                return this;
            }

            public boolean hasHasBadge() {
                return ((Program) this.instance).hasHasBadge();
            }

            public boolean getHasBadge() {
                return ((Program) this.instance).getHasBadge();
            }

            public Builder setHasBadge(boolean value) {
                copyOnWrite();
                ((Program) this.instance).setHasBadge(value);
                return this;
            }

            public Builder clearHasBadge() {
                copyOnWrite();
                ((Program) this.instance).clearHasBadge();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Program();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\f\u0000\u0001\u0001\f\f\u0000\u0000\u0000\u0001\b\u0000\u0002\u0002\u0001\u0003\f\u0002\u0004\t\u0003\u0005\t\u0004\u0006\t\u0005\u0007\b\u0006\b\u0007\u0007\t\u0007\b\n\u0007\t\u000b\u0007\n\f\u0007\u000b", new Object[]{"bitField0_", "packageName_", "metadataHashCode_", "type_", Type.internalGetVerifier(), "rating_", "preview_", "interactionCount_", "genre_", "isLive_", "hasDescription_", "hasContentRating_", "hasProgress_", "hasBadge_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Program> parser = PARSER;
                    if (parser == null) {
                        synchronized (Program.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(Program.class, DEFAULT_INSTANCE);
        }

        public static Program getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Program> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Channel extends GeneratedMessageLite<Channel, Builder> implements ChannelOrBuilder {
        /* access modifiers changed from: private */
        public static final Channel DEFAULT_INSTANCE = new Channel();
        public static final int DENIAL_REASON_FIELD_NUMBER = 5;
        public static final int IS_LEGACY_FIELD_NUMBER = 6;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
        private static volatile Parser<Channel> PARSER = null;
        public static final int POSITION_FIELD_NUMBER = 7;
        public static final int PROGRAMS_FIELD_NUMBER = 4;
        public static final int PROGRAM_COUNT_FIELD_NUMBER = 3;
        public static final int TITLE_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int denialReason_ = 1;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean isLegacy_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String packageName_ = "";
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int position_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int programCount_;
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Program> programs_ = emptyProtobufList();
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String title_ = "";

        private Channel() {
        }

        public enum DenialReason implements Internal.EnumLite {
            DENIED_BY_USER(1),
            DIALOG_CLOSED(2),
            APP_ERROR(3),
            DENIED_BY_SYSTEM(4);
            
            public static final int APP_ERROR_VALUE = 3;
            public static final int DENIED_BY_SYSTEM_VALUE = 4;
            public static final int DENIED_BY_USER_VALUE = 1;
            public static final int DIALOG_CLOSED_VALUE = 2;
            private static final Internal.EnumLiteMap<DenialReason> internalValueMap = new Internal.EnumLiteMap<DenialReason>() {
                public DenialReason findValueByNumber(int number) {
                    return DenialReason.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static DenialReason forNumber(int value2) {
                if (value2 == 1) {
                    return DENIED_BY_USER;
                }
                if (value2 == 2) {
                    return DIALOG_CLOSED;
                }
                if (value2 == 3) {
                    return APP_ERROR;
                }
                if (value2 != 4) {
                    return null;
                }
                return DENIED_BY_SYSTEM;
            }

            public static Internal.EnumLiteMap<DenialReason> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return DenialReasonVerifier.INSTANCE;
            }

            private static final class DenialReasonVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new DenialReasonVerifier();

                private DenialReasonVerifier() {
                }

                public boolean isInRange(int number) {
                    return DenialReason.forNumber(number) != null;
                }
            }

            private DenialReason(int value2) {
                this.value = value2;
            }
        }

        public boolean hasPackageName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getPackageName() {
            return this.packageName_;
        }

        public ByteString getPackageNameBytes() {
            return ByteString.copyFromUtf8(this.packageName_);
        }

        /* access modifiers changed from: private */
        public void setPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageName() {
            this.bitField0_ &= -2;
            this.packageName_ = getDefaultInstance().getPackageName();
        }

        /* access modifiers changed from: private */
        public void setPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasTitle() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getTitle() {
            return this.title_;
        }

        public ByteString getTitleBytes() {
            return ByteString.copyFromUtf8(this.title_);
        }

        /* access modifiers changed from: private */
        public void setTitle(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.title_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTitle() {
            this.bitField0_ &= -3;
            this.title_ = getDefaultInstance().getTitle();
        }

        /* access modifiers changed from: private */
        public void setTitleBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.title_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasProgramCount() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getProgramCount() {
            return this.programCount_;
        }

        /* access modifiers changed from: private */
        public void setProgramCount(int value) {
            this.bitField0_ |= 4;
            this.programCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearProgramCount() {
            this.bitField0_ &= -5;
            this.programCount_ = 0;
        }

        public List<Program> getProgramsList() {
            return this.programs_;
        }

        public List<? extends ProgramOrBuilder> getProgramsOrBuilderList() {
            return this.programs_;
        }

        public int getProgramsCount() {
            return this.programs_.size();
        }

        public Program getPrograms(int index) {
            return this.programs_.get(index);
        }

        public ProgramOrBuilder getProgramsOrBuilder(int index) {
            return this.programs_.get(index);
        }

        private void ensureProgramsIsMutable() {
            if (!this.programs_.isModifiable()) {
                this.programs_ = GeneratedMessageLite.mutableCopy(this.programs_);
            }
        }

        /* access modifiers changed from: private */
        public void setPrograms(int index, Program value) {
            if (value != null) {
                ensureProgramsIsMutable();
                this.programs_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrograms(int index, Program.Builder builderForValue) {
            ensureProgramsIsMutable();
            this.programs_.set(index, (Program) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addPrograms(Program value) {
            if (value != null) {
                ensureProgramsIsMutable();
                this.programs_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addPrograms(int index, Program value) {
            if (value != null) {
                ensureProgramsIsMutable();
                this.programs_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addPrograms(Program.Builder builderForValue) {
            ensureProgramsIsMutable();
            this.programs_.add((Program) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addPrograms(int index, Program.Builder builderForValue) {
            ensureProgramsIsMutable();
            this.programs_.add(index, (Program) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Program>, com.google.protobuf.Internal$ProtobufList<com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Program>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllPrograms(Iterable<? extends Program> values) {
            ensureProgramsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.programs_);
        }

        /* access modifiers changed from: private */
        public void clearPrograms() {
            this.programs_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removePrograms(int index) {
            ensureProgramsIsMutable();
            this.programs_.remove(index);
        }

        public boolean hasDenialReason() {
            return (this.bitField0_ & 8) != 0;
        }

        public DenialReason getDenialReason() {
            DenialReason result = DenialReason.forNumber(this.denialReason_);
            return result == null ? DenialReason.DENIED_BY_USER : result;
        }

        /* access modifiers changed from: private */
        public void setDenialReason(DenialReason value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.denialReason_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDenialReason() {
            this.bitField0_ &= -9;
            this.denialReason_ = 1;
        }

        public boolean hasIsLegacy() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getIsLegacy() {
            return this.isLegacy_;
        }

        /* access modifiers changed from: private */
        public void setIsLegacy(boolean value) {
            this.bitField0_ |= 16;
            this.isLegacy_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsLegacy() {
            this.bitField0_ &= -17;
            this.isLegacy_ = false;
        }

        public boolean hasPosition() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getPosition() {
            return this.position_;
        }

        /* access modifiers changed from: private */
        public void setPosition(int value) {
            this.bitField0_ |= 32;
            this.position_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPosition() {
            this.bitField0_ &= -33;
            this.position_ = 0;
        }

        public static Channel parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Channel parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Channel parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Channel parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Channel parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Channel parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Channel parseFrom(InputStream input) throws IOException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Channel parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Channel parseDelimitedFrom(InputStream input) throws IOException {
            return (Channel) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Channel parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Channel) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Channel parseFrom(CodedInputStream input) throws IOException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Channel parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Channel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Channel prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Channel, Builder> implements ChannelOrBuilder {
            private Builder() {
                super(Channel.DEFAULT_INSTANCE);
            }

            public boolean hasPackageName() {
                return ((Channel) this.instance).hasPackageName();
            }

            public String getPackageName() {
                return ((Channel) this.instance).getPackageName();
            }

            public ByteString getPackageNameBytes() {
                return ((Channel) this.instance).getPackageNameBytes();
            }

            public Builder setPackageName(String value) {
                copyOnWrite();
                ((Channel) this.instance).setPackageName(value);
                return this;
            }

            public Builder clearPackageName() {
                copyOnWrite();
                ((Channel) this.instance).clearPackageName();
                return this;
            }

            public Builder setPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((Channel) this.instance).setPackageNameBytes(value);
                return this;
            }

            public boolean hasTitle() {
                return ((Channel) this.instance).hasTitle();
            }

            public String getTitle() {
                return ((Channel) this.instance).getTitle();
            }

            public ByteString getTitleBytes() {
                return ((Channel) this.instance).getTitleBytes();
            }

            public Builder setTitle(String value) {
                copyOnWrite();
                ((Channel) this.instance).setTitle(value);
                return this;
            }

            public Builder clearTitle() {
                copyOnWrite();
                ((Channel) this.instance).clearTitle();
                return this;
            }

            public Builder setTitleBytes(ByteString value) {
                copyOnWrite();
                ((Channel) this.instance).setTitleBytes(value);
                return this;
            }

            public boolean hasProgramCount() {
                return ((Channel) this.instance).hasProgramCount();
            }

            public int getProgramCount() {
                return ((Channel) this.instance).getProgramCount();
            }

            public Builder setProgramCount(int value) {
                copyOnWrite();
                ((Channel) this.instance).setProgramCount(value);
                return this;
            }

            public Builder clearProgramCount() {
                copyOnWrite();
                ((Channel) this.instance).clearProgramCount();
                return this;
            }

            public List<Program> getProgramsList() {
                return Collections.unmodifiableList(((Channel) this.instance).getProgramsList());
            }

            public int getProgramsCount() {
                return ((Channel) this.instance).getProgramsCount();
            }

            public Program getPrograms(int index) {
                return ((Channel) this.instance).getPrograms(index);
            }

            public Builder setPrograms(int index, Program value) {
                copyOnWrite();
                ((Channel) this.instance).setPrograms(index, value);
                return this;
            }

            public Builder setPrograms(int index, Program.Builder builderForValue) {
                copyOnWrite();
                ((Channel) this.instance).setPrograms(index, builderForValue);
                return this;
            }

            public Builder addPrograms(Program value) {
                copyOnWrite();
                ((Channel) this.instance).addPrograms(value);
                return this;
            }

            public Builder addPrograms(int index, Program value) {
                copyOnWrite();
                ((Channel) this.instance).addPrograms(index, value);
                return this;
            }

            public Builder addPrograms(Program.Builder builderForValue) {
                copyOnWrite();
                ((Channel) this.instance).addPrograms(builderForValue);
                return this;
            }

            public Builder addPrograms(int index, Program.Builder builderForValue) {
                copyOnWrite();
                ((Channel) this.instance).addPrograms(index, builderForValue);
                return this;
            }

            public Builder addAllPrograms(Iterable<? extends Program> values) {
                copyOnWrite();
                ((Channel) this.instance).addAllPrograms(values);
                return this;
            }

            public Builder clearPrograms() {
                copyOnWrite();
                ((Channel) this.instance).clearPrograms();
                return this;
            }

            public Builder removePrograms(int index) {
                copyOnWrite();
                ((Channel) this.instance).removePrograms(index);
                return this;
            }

            public boolean hasDenialReason() {
                return ((Channel) this.instance).hasDenialReason();
            }

            public DenialReason getDenialReason() {
                return ((Channel) this.instance).getDenialReason();
            }

            public Builder setDenialReason(DenialReason value) {
                copyOnWrite();
                ((Channel) this.instance).setDenialReason(value);
                return this;
            }

            public Builder clearDenialReason() {
                copyOnWrite();
                ((Channel) this.instance).clearDenialReason();
                return this;
            }

            public boolean hasIsLegacy() {
                return ((Channel) this.instance).hasIsLegacy();
            }

            public boolean getIsLegacy() {
                return ((Channel) this.instance).getIsLegacy();
            }

            public Builder setIsLegacy(boolean value) {
                copyOnWrite();
                ((Channel) this.instance).setIsLegacy(value);
                return this;
            }

            public Builder clearIsLegacy() {
                copyOnWrite();
                ((Channel) this.instance).clearIsLegacy();
                return this;
            }

            public boolean hasPosition() {
                return ((Channel) this.instance).hasPosition();
            }

            public int getPosition() {
                return ((Channel) this.instance).getPosition();
            }

            public Builder setPosition(int value) {
                copyOnWrite();
                ((Channel) this.instance).setPosition(value);
                return this;
            }

            public Builder clearPosition() {
                copyOnWrite();
                ((Channel) this.instance).clearPosition();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Channel();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0001\u0000\u0001\b\u0000\u0002\b\u0001\u0003\u0004\u0002\u0004\u001b\u0005\f\u0003\u0006\u0007\u0004\u0007\u0004\u0005", new Object[]{"bitField0_", "packageName_", "title_", "programCount_", "programs_", Program.class, "denialReason_", DenialReason.internalGetVerifier(), "isLegacy_", "position_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Channel> parser = PARSER;
                    if (parser == null) {
                        synchronized (Channel.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(Channel.class, DEFAULT_INSTANCE);
        }

        public static Channel getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Channel> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ChannelCollection extends GeneratedMessageLite<ChannelCollection, Builder> implements ChannelCollectionOrBuilder {
        public static final int BROWSABLE_COUNT_FIELD_NUMBER = 2;
        public static final int BROWSABLE_LEGACY_COUNT_FIELD_NUMBER = 4;
        public static final int COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final ChannelCollection DEFAULT_INSTANCE = new ChannelCollection();
        public static final int LEGACY_COUNT_FIELD_NUMBER = 3;
        private static volatile Parser<ChannelCollection> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int browsableCount_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int browsableLegacyCount_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int legacyCount_;

        private ChannelCollection() {
        }

        public boolean hasCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getCount() {
            return this.count_;
        }

        /* access modifiers changed from: private */
        public void setCount(int value) {
            this.bitField0_ |= 1;
            this.count_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCount() {
            this.bitField0_ &= -2;
            this.count_ = 0;
        }

        public boolean hasBrowsableCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getBrowsableCount() {
            return this.browsableCount_;
        }

        /* access modifiers changed from: private */
        public void setBrowsableCount(int value) {
            this.bitField0_ |= 2;
            this.browsableCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBrowsableCount() {
            this.bitField0_ &= -3;
            this.browsableCount_ = 0;
        }

        public boolean hasLegacyCount() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getLegacyCount() {
            return this.legacyCount_;
        }

        /* access modifiers changed from: private */
        public void setLegacyCount(int value) {
            this.bitField0_ |= 4;
            this.legacyCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLegacyCount() {
            this.bitField0_ &= -5;
            this.legacyCount_ = 0;
        }

        public boolean hasBrowsableLegacyCount() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getBrowsableLegacyCount() {
            return this.browsableLegacyCount_;
        }

        /* access modifiers changed from: private */
        public void setBrowsableLegacyCount(int value) {
            this.bitField0_ |= 8;
            this.browsableLegacyCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBrowsableLegacyCount() {
            this.bitField0_ &= -9;
            this.browsableLegacyCount_ = 0;
        }

        public static ChannelCollection parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ChannelCollection parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ChannelCollection parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ChannelCollection parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ChannelCollection parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ChannelCollection parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ChannelCollection parseFrom(InputStream input) throws IOException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ChannelCollection parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ChannelCollection parseDelimitedFrom(InputStream input) throws IOException {
            return (ChannelCollection) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ChannelCollection parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ChannelCollection) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ChannelCollection parseFrom(CodedInputStream input) throws IOException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ChannelCollection parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ChannelCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ChannelCollection prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ChannelCollection, Builder> implements ChannelCollectionOrBuilder {
            private Builder() {
                super(ChannelCollection.DEFAULT_INSTANCE);
            }

            public boolean hasCount() {
                return ((ChannelCollection) this.instance).hasCount();
            }

            public int getCount() {
                return ((ChannelCollection) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((ChannelCollection) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((ChannelCollection) this.instance).clearCount();
                return this;
            }

            public boolean hasBrowsableCount() {
                return ((ChannelCollection) this.instance).hasBrowsableCount();
            }

            public int getBrowsableCount() {
                return ((ChannelCollection) this.instance).getBrowsableCount();
            }

            public Builder setBrowsableCount(int value) {
                copyOnWrite();
                ((ChannelCollection) this.instance).setBrowsableCount(value);
                return this;
            }

            public Builder clearBrowsableCount() {
                copyOnWrite();
                ((ChannelCollection) this.instance).clearBrowsableCount();
                return this;
            }

            public boolean hasLegacyCount() {
                return ((ChannelCollection) this.instance).hasLegacyCount();
            }

            public int getLegacyCount() {
                return ((ChannelCollection) this.instance).getLegacyCount();
            }

            public Builder setLegacyCount(int value) {
                copyOnWrite();
                ((ChannelCollection) this.instance).setLegacyCount(value);
                return this;
            }

            public Builder clearLegacyCount() {
                copyOnWrite();
                ((ChannelCollection) this.instance).clearLegacyCount();
                return this;
            }

            public boolean hasBrowsableLegacyCount() {
                return ((ChannelCollection) this.instance).hasBrowsableLegacyCount();
            }

            public int getBrowsableLegacyCount() {
                return ((ChannelCollection) this.instance).getBrowsableLegacyCount();
            }

            public Builder setBrowsableLegacyCount(int value) {
                copyOnWrite();
                ((ChannelCollection) this.instance).setBrowsableLegacyCount(value);
                return this;
            }

            public Builder clearBrowsableLegacyCount() {
                copyOnWrite();
                ((ChannelCollection) this.instance).clearBrowsableLegacyCount();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ChannelCollection();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0004\u0002\u0004\u0004\u0003", new Object[]{"bitField0_", "count_", "browsableCount_", "legacyCount_", "browsableLegacyCount_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ChannelCollection> parser = PARSER;
                    if (parser == null) {
                        synchronized (ChannelCollection.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(ChannelCollection.class, DEFAULT_INSTANCE);
        }

        public static ChannelCollection getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ChannelCollection> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class WatchNextChannel extends GeneratedMessageLite<WatchNextChannel, Builder> implements WatchNextChannelOrBuilder {
        /* access modifiers changed from: private */
        public static final WatchNextChannel DEFAULT_INSTANCE = new WatchNextChannel();
        private static volatile Parser<WatchNextChannel> PARSER = null;
        public static final int PROGRAMS_FIELD_NUMBER = 2;
        public static final int PROGRAM_COUNT_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int programCount_;
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Program> programs_ = emptyProtobufList();

        private WatchNextChannel() {
        }

        public boolean hasProgramCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getProgramCount() {
            return this.programCount_;
        }

        /* access modifiers changed from: private */
        public void setProgramCount(int value) {
            this.bitField0_ |= 1;
            this.programCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearProgramCount() {
            this.bitField0_ &= -2;
            this.programCount_ = 0;
        }

        public List<Program> getProgramsList() {
            return this.programs_;
        }

        public List<? extends ProgramOrBuilder> getProgramsOrBuilderList() {
            return this.programs_;
        }

        public int getProgramsCount() {
            return this.programs_.size();
        }

        public Program getPrograms(int index) {
            return this.programs_.get(index);
        }

        public ProgramOrBuilder getProgramsOrBuilder(int index) {
            return this.programs_.get(index);
        }

        private void ensureProgramsIsMutable() {
            if (!this.programs_.isModifiable()) {
                this.programs_ = GeneratedMessageLite.mutableCopy(this.programs_);
            }
        }

        /* access modifiers changed from: private */
        public void setPrograms(int index, Program value) {
            if (value != null) {
                ensureProgramsIsMutable();
                this.programs_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrograms(int index, Program.Builder builderForValue) {
            ensureProgramsIsMutable();
            this.programs_.set(index, (Program) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addPrograms(Program value) {
            if (value != null) {
                ensureProgramsIsMutable();
                this.programs_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addPrograms(int index, Program value) {
            if (value != null) {
                ensureProgramsIsMutable();
                this.programs_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addPrograms(Program.Builder builderForValue) {
            ensureProgramsIsMutable();
            this.programs_.add((Program) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addPrograms(int index, Program.Builder builderForValue) {
            ensureProgramsIsMutable();
            this.programs_.add(index, (Program) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Program>, com.google.protobuf.Internal$ProtobufList<com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Program>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllPrograms(Iterable<? extends Program> values) {
            ensureProgramsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.programs_);
        }

        /* access modifiers changed from: private */
        public void clearPrograms() {
            this.programs_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removePrograms(int index) {
            ensureProgramsIsMutable();
            this.programs_.remove(index);
        }

        public static WatchNextChannel parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static WatchNextChannel parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static WatchNextChannel parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static WatchNextChannel parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static WatchNextChannel parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static WatchNextChannel parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static WatchNextChannel parseFrom(InputStream input) throws IOException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static WatchNextChannel parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static WatchNextChannel parseDelimitedFrom(InputStream input) throws IOException {
            return (WatchNextChannel) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static WatchNextChannel parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WatchNextChannel) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static WatchNextChannel parseFrom(CodedInputStream input) throws IOException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static WatchNextChannel parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (WatchNextChannel) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(WatchNextChannel prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<WatchNextChannel, Builder> implements WatchNextChannelOrBuilder {
            private Builder() {
                super(WatchNextChannel.DEFAULT_INSTANCE);
            }

            public boolean hasProgramCount() {
                return ((WatchNextChannel) this.instance).hasProgramCount();
            }

            public int getProgramCount() {
                return ((WatchNextChannel) this.instance).getProgramCount();
            }

            public Builder setProgramCount(int value) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).setProgramCount(value);
                return this;
            }

            public Builder clearProgramCount() {
                copyOnWrite();
                ((WatchNextChannel) this.instance).clearProgramCount();
                return this;
            }

            public List<Program> getProgramsList() {
                return Collections.unmodifiableList(((WatchNextChannel) this.instance).getProgramsList());
            }

            public int getProgramsCount() {
                return ((WatchNextChannel) this.instance).getProgramsCount();
            }

            public Program getPrograms(int index) {
                return ((WatchNextChannel) this.instance).getPrograms(index);
            }

            public Builder setPrograms(int index, Program value) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).setPrograms(index, value);
                return this;
            }

            public Builder setPrograms(int index, Program.Builder builderForValue) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).setPrograms(index, builderForValue);
                return this;
            }

            public Builder addPrograms(Program value) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).addPrograms(value);
                return this;
            }

            public Builder addPrograms(int index, Program value) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).addPrograms(index, value);
                return this;
            }

            public Builder addPrograms(Program.Builder builderForValue) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).addPrograms(builderForValue);
                return this;
            }

            public Builder addPrograms(int index, Program.Builder builderForValue) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).addPrograms(index, builderForValue);
                return this;
            }

            public Builder addAllPrograms(Iterable<? extends Program> values) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).addAllPrograms(values);
                return this;
            }

            public Builder clearPrograms() {
                copyOnWrite();
                ((WatchNextChannel) this.instance).clearPrograms();
                return this;
            }

            public Builder removePrograms(int index) {
                copyOnWrite();
                ((WatchNextChannel) this.instance).removePrograms(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new WatchNextChannel();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u0004\u0000\u0002\u001b", new Object[]{"bitField0_", "programCount_", "programs_", Program.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<WatchNextChannel> parser = PARSER;
                    if (parser == null) {
                        synchronized (WatchNextChannel.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(WatchNextChannel.class, DEFAULT_INSTANCE);
        }

        public static WatchNextChannel getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<WatchNextChannel> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Notification extends GeneratedMessageLite<Notification, Builder> implements NotificationOrBuilder {
        /* access modifiers changed from: private */
        public static final Notification DEFAULT_INSTANCE = new Notification();
        public static final int IMPORTANCE_FIELD_NUMBER = 3;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
        private static volatile Parser<Notification> PARSER = null;
        public static final int SUMMARY_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int importance_ = 1;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String packageName_ = "";
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String summary_ = "";

        private Notification() {
        }

        public enum Importance implements Internal.EnumLite {
            MIN(1),
            LOW(2),
            DEFAULT(3),
            HIGH(4),
            MAX(5);
            
            public static final int DEFAULT_VALUE = 3;
            public static final int HIGH_VALUE = 4;
            public static final int LOW_VALUE = 2;
            public static final int MAX_VALUE = 5;
            public static final int MIN_VALUE = 1;
            private static final Internal.EnumLiteMap<Importance> internalValueMap = new Internal.EnumLiteMap<Importance>() {
                public Importance findValueByNumber(int number) {
                    return Importance.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static Importance forNumber(int value2) {
                if (value2 == 1) {
                    return MIN;
                }
                if (value2 == 2) {
                    return LOW;
                }
                if (value2 == 3) {
                    return DEFAULT;
                }
                if (value2 == 4) {
                    return HIGH;
                }
                if (value2 != 5) {
                    return null;
                }
                return MAX;
            }

            public static Internal.EnumLiteMap<Importance> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return ImportanceVerifier.INSTANCE;
            }

            private static final class ImportanceVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new ImportanceVerifier();

                private ImportanceVerifier() {
                }

                public boolean isInRange(int number) {
                    return Importance.forNumber(number) != null;
                }
            }

            private Importance(int value2) {
                this.value = value2;
            }
        }

        public boolean hasPackageName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getPackageName() {
            return this.packageName_;
        }

        public ByteString getPackageNameBytes() {
            return ByteString.copyFromUtf8(this.packageName_);
        }

        /* access modifiers changed from: private */
        public void setPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageName() {
            this.bitField0_ &= -2;
            this.packageName_ = getDefaultInstance().getPackageName();
        }

        /* access modifiers changed from: private */
        public void setPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasSummary() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getSummary() {
            return this.summary_;
        }

        public ByteString getSummaryBytes() {
            return ByteString.copyFromUtf8(this.summary_);
        }

        /* access modifiers changed from: private */
        public void setSummary(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.summary_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSummary() {
            this.bitField0_ &= -3;
            this.summary_ = getDefaultInstance().getSummary();
        }

        /* access modifiers changed from: private */
        public void setSummaryBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.summary_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasImportance() {
            return (this.bitField0_ & 4) != 0;
        }

        public Importance getImportance() {
            Importance result = Importance.forNumber(this.importance_);
            return result == null ? Importance.MIN : result;
        }

        /* access modifiers changed from: private */
        public void setImportance(Importance value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.importance_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearImportance() {
            this.bitField0_ &= -5;
            this.importance_ = 1;
        }

        public static Notification parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Notification parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Notification parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Notification parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Notification parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Notification parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Notification parseFrom(InputStream input) throws IOException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Notification parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Notification parseDelimitedFrom(InputStream input) throws IOException {
            return (Notification) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Notification parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Notification) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Notification parseFrom(CodedInputStream input) throws IOException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Notification parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Notification) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Notification prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Notification, Builder> implements NotificationOrBuilder {
            private Builder() {
                super(Notification.DEFAULT_INSTANCE);
            }

            public boolean hasPackageName() {
                return ((Notification) this.instance).hasPackageName();
            }

            public String getPackageName() {
                return ((Notification) this.instance).getPackageName();
            }

            public ByteString getPackageNameBytes() {
                return ((Notification) this.instance).getPackageNameBytes();
            }

            public Builder setPackageName(String value) {
                copyOnWrite();
                ((Notification) this.instance).setPackageName(value);
                return this;
            }

            public Builder clearPackageName() {
                copyOnWrite();
                ((Notification) this.instance).clearPackageName();
                return this;
            }

            public Builder setPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((Notification) this.instance).setPackageNameBytes(value);
                return this;
            }

            public boolean hasSummary() {
                return ((Notification) this.instance).hasSummary();
            }

            public String getSummary() {
                return ((Notification) this.instance).getSummary();
            }

            public ByteString getSummaryBytes() {
                return ((Notification) this.instance).getSummaryBytes();
            }

            public Builder setSummary(String value) {
                copyOnWrite();
                ((Notification) this.instance).setSummary(value);
                return this;
            }

            public Builder clearSummary() {
                copyOnWrite();
                ((Notification) this.instance).clearSummary();
                return this;
            }

            public Builder setSummaryBytes(ByteString value) {
                copyOnWrite();
                ((Notification) this.instance).setSummaryBytes(value);
                return this;
            }

            public boolean hasImportance() {
                return ((Notification) this.instance).hasImportance();
            }

            public Importance getImportance() {
                return ((Notification) this.instance).getImportance();
            }

            public Builder setImportance(Importance value) {
                copyOnWrite();
                ((Notification) this.instance).setImportance(value);
                return this;
            }

            public Builder clearImportance() {
                copyOnWrite();
                ((Notification) this.instance).clearImportance();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Notification();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\f\u0002", new Object[]{"bitField0_", "packageName_", "summary_", "importance_", Importance.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Notification> parser = PARSER;
                    if (parser == null) {
                        synchronized (Notification.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(Notification.class, DEFAULT_INSTANCE);
        }

        public static Notification getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Notification> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class NotificationCollection extends GeneratedMessageLite<NotificationCollection, Builder> implements NotificationCollectionOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final NotificationCollection DEFAULT_INSTANCE = new NotificationCollection();
        public static final int HAS_NEW_NOTIFICATIONS_FIELD_NUMBER = 3;
        public static final int MAX_PRIORITY_COUNT_FIELD_NUMBER = 2;
        public static final int NOTIFICATIONS_FIELD_NUMBER = 4;
        private static volatile Parser<NotificationCollection> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.UINT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean hasNewNotifications_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.UINT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int maxPriorityCount_;
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Notification> notifications_ = emptyProtobufList();

        private NotificationCollection() {
        }

        public boolean hasCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getCount() {
            return this.count_;
        }

        /* access modifiers changed from: private */
        public void setCount(int value) {
            this.bitField0_ |= 1;
            this.count_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCount() {
            this.bitField0_ &= -2;
            this.count_ = 0;
        }

        public boolean hasMaxPriorityCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getMaxPriorityCount() {
            return this.maxPriorityCount_;
        }

        /* access modifiers changed from: private */
        public void setMaxPriorityCount(int value) {
            this.bitField0_ |= 2;
            this.maxPriorityCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMaxPriorityCount() {
            this.bitField0_ &= -3;
            this.maxPriorityCount_ = 0;
        }

        public boolean hasHasNewNotifications() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getHasNewNotifications() {
            return this.hasNewNotifications_;
        }

        /* access modifiers changed from: private */
        public void setHasNewNotifications(boolean value) {
            this.bitField0_ |= 4;
            this.hasNewNotifications_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasNewNotifications() {
            this.bitField0_ &= -5;
            this.hasNewNotifications_ = false;
        }

        public List<Notification> getNotificationsList() {
            return this.notifications_;
        }

        public List<? extends NotificationOrBuilder> getNotificationsOrBuilderList() {
            return this.notifications_;
        }

        public int getNotificationsCount() {
            return this.notifications_.size();
        }

        public Notification getNotifications(int index) {
            return this.notifications_.get(index);
        }

        public NotificationOrBuilder getNotificationsOrBuilder(int index) {
            return this.notifications_.get(index);
        }

        private void ensureNotificationsIsMutable() {
            if (!this.notifications_.isModifiable()) {
                this.notifications_ = GeneratedMessageLite.mutableCopy(this.notifications_);
            }
        }

        /* access modifiers changed from: private */
        public void setNotifications(int index, Notification value) {
            if (value != null) {
                ensureNotificationsIsMutable();
                this.notifications_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setNotifications(int index, Notification.Builder builderForValue) {
            ensureNotificationsIsMutable();
            this.notifications_.set(index, (Notification) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addNotifications(Notification value) {
            if (value != null) {
                ensureNotificationsIsMutable();
                this.notifications_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addNotifications(int index, Notification value) {
            if (value != null) {
                ensureNotificationsIsMutable();
                this.notifications_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addNotifications(Notification.Builder builderForValue) {
            ensureNotificationsIsMutable();
            this.notifications_.add((Notification) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addNotifications(int index, Notification.Builder builderForValue) {
            ensureNotificationsIsMutable();
            this.notifications_.add(index, (Notification) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Notification>, com.google.protobuf.Internal$ProtobufList<com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Notification>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllNotifications(Iterable<? extends Notification> values) {
            ensureNotificationsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.notifications_);
        }

        /* access modifiers changed from: private */
        public void clearNotifications() {
            this.notifications_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeNotifications(int index) {
            ensureNotificationsIsMutable();
            this.notifications_.remove(index);
        }

        public static NotificationCollection parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NotificationCollection parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NotificationCollection parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NotificationCollection parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NotificationCollection parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static NotificationCollection parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static NotificationCollection parseFrom(InputStream input) throws IOException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static NotificationCollection parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static NotificationCollection parseDelimitedFrom(InputStream input) throws IOException {
            return (NotificationCollection) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static NotificationCollection parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NotificationCollection) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static NotificationCollection parseFrom(CodedInputStream input) throws IOException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static NotificationCollection parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (NotificationCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(NotificationCollection prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<NotificationCollection, Builder> implements NotificationCollectionOrBuilder {
            private Builder() {
                super(NotificationCollection.DEFAULT_INSTANCE);
            }

            public boolean hasCount() {
                return ((NotificationCollection) this.instance).hasCount();
            }

            public int getCount() {
                return ((NotificationCollection) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((NotificationCollection) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((NotificationCollection) this.instance).clearCount();
                return this;
            }

            public boolean hasMaxPriorityCount() {
                return ((NotificationCollection) this.instance).hasMaxPriorityCount();
            }

            public int getMaxPriorityCount() {
                return ((NotificationCollection) this.instance).getMaxPriorityCount();
            }

            public Builder setMaxPriorityCount(int value) {
                copyOnWrite();
                ((NotificationCollection) this.instance).setMaxPriorityCount(value);
                return this;
            }

            public Builder clearMaxPriorityCount() {
                copyOnWrite();
                ((NotificationCollection) this.instance).clearMaxPriorityCount();
                return this;
            }

            public boolean hasHasNewNotifications() {
                return ((NotificationCollection) this.instance).hasHasNewNotifications();
            }

            public boolean getHasNewNotifications() {
                return ((NotificationCollection) this.instance).getHasNewNotifications();
            }

            public Builder setHasNewNotifications(boolean value) {
                copyOnWrite();
                ((NotificationCollection) this.instance).setHasNewNotifications(value);
                return this;
            }

            public Builder clearHasNewNotifications() {
                copyOnWrite();
                ((NotificationCollection) this.instance).clearHasNewNotifications();
                return this;
            }

            public List<Notification> getNotificationsList() {
                return Collections.unmodifiableList(((NotificationCollection) this.instance).getNotificationsList());
            }

            public int getNotificationsCount() {
                return ((NotificationCollection) this.instance).getNotificationsCount();
            }

            public Notification getNotifications(int index) {
                return ((NotificationCollection) this.instance).getNotifications(index);
            }

            public Builder setNotifications(int index, Notification value) {
                copyOnWrite();
                ((NotificationCollection) this.instance).setNotifications(index, value);
                return this;
            }

            public Builder setNotifications(int index, Notification.Builder builderForValue) {
                copyOnWrite();
                ((NotificationCollection) this.instance).setNotifications(index, builderForValue);
                return this;
            }

            public Builder addNotifications(Notification value) {
                copyOnWrite();
                ((NotificationCollection) this.instance).addNotifications(value);
                return this;
            }

            public Builder addNotifications(int index, Notification value) {
                copyOnWrite();
                ((NotificationCollection) this.instance).addNotifications(index, value);
                return this;
            }

            public Builder addNotifications(Notification.Builder builderForValue) {
                copyOnWrite();
                ((NotificationCollection) this.instance).addNotifications(builderForValue);
                return this;
            }

            public Builder addNotifications(int index, Notification.Builder builderForValue) {
                copyOnWrite();
                ((NotificationCollection) this.instance).addNotifications(index, builderForValue);
                return this;
            }

            public Builder addAllNotifications(Iterable<? extends Notification> values) {
                copyOnWrite();
                ((NotificationCollection) this.instance).addAllNotifications(values);
                return this;
            }

            public Builder clearNotifications() {
                copyOnWrite();
                ((NotificationCollection) this.instance).clearNotifications();
                return this;
            }

            public Builder removeNotifications(int index) {
                copyOnWrite();
                ((NotificationCollection) this.instance).removeNotifications(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new NotificationCollection();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001\u000b\u0000\u0002\u000b\u0001\u0003\u0007\u0002\u0004\u001b", new Object[]{"bitField0_", "count_", "maxPriorityCount_", "hasNewNotifications_", "notifications_", Notification.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<NotificationCollection> parser = PARSER;
                    if (parser == null) {
                        synchronized (NotificationCollection.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(NotificationCollection.class, DEFAULT_INSTANCE);
        }

        public static NotificationCollection getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<NotificationCollection> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class LaunchItem extends GeneratedMessageLite<LaunchItem, Builder> implements LaunchItemOrBuilder {
        public static final int APP_FIELD_NUMBER = 1;
        public static final int APP_LINK_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final LaunchItem DEFAULT_INSTANCE = new LaunchItem();
        private static volatile Parser<LaunchItem> PARSER;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private AppLink appLink_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private Application app_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;

        private LaunchItem() {
        }

        public boolean hasApp() {
            return (this.bitField0_ & 1) != 0;
        }

        public Application getApp() {
            Application application = this.app_;
            return application == null ? Application.getDefaultInstance() : application;
        }

        /* access modifiers changed from: private */
        public void setApp(Application value) {
            if (value != null) {
                this.app_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setApp(Application.Builder builderForValue) {
            this.app_ = (Application) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeApp(Application value) {
            if (value != null) {
                Application application = this.app_;
                if (application == null || application == Application.getDefaultInstance()) {
                    this.app_ = value;
                } else {
                    this.app_ = (Application) ((Application.Builder) Application.newBuilder(this.app_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearApp() {
            this.app_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasAppLink() {
            return (this.bitField0_ & 2) != 0;
        }

        public AppLink getAppLink() {
            AppLink appLink = this.appLink_;
            return appLink == null ? AppLink.getDefaultInstance() : appLink;
        }

        /* access modifiers changed from: private */
        public void setAppLink(AppLink value) {
            if (value != null) {
                this.appLink_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAppLink(AppLink.Builder builderForValue) {
            this.appLink_ = (AppLink) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeAppLink(AppLink value) {
            if (value != null) {
                AppLink appLink = this.appLink_;
                if (appLink == null || appLink == AppLink.getDefaultInstance()) {
                    this.appLink_ = value;
                } else {
                    this.appLink_ = (AppLink) ((AppLink.Builder) AppLink.newBuilder(this.appLink_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAppLink() {
            this.appLink_ = null;
            this.bitField0_ &= -3;
        }

        public static LaunchItem parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LaunchItem parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LaunchItem parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LaunchItem parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LaunchItem parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LaunchItem parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LaunchItem parseFrom(InputStream input) throws IOException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static LaunchItem parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static LaunchItem parseDelimitedFrom(InputStream input) throws IOException {
            return (LaunchItem) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static LaunchItem parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LaunchItem) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static LaunchItem parseFrom(CodedInputStream input) throws IOException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static LaunchItem parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LaunchItem) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(LaunchItem prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<LaunchItem, Builder> implements LaunchItemOrBuilder {
            private Builder() {
                super(LaunchItem.DEFAULT_INSTANCE);
            }

            public boolean hasApp() {
                return ((LaunchItem) this.instance).hasApp();
            }

            public Application getApp() {
                return ((LaunchItem) this.instance).getApp();
            }

            public Builder setApp(Application value) {
                copyOnWrite();
                ((LaunchItem) this.instance).setApp(value);
                return this;
            }

            public Builder setApp(Application.Builder builderForValue) {
                copyOnWrite();
                ((LaunchItem) this.instance).setApp(builderForValue);
                return this;
            }

            public Builder mergeApp(Application value) {
                copyOnWrite();
                ((LaunchItem) this.instance).mergeApp(value);
                return this;
            }

            public Builder clearApp() {
                copyOnWrite();
                ((LaunchItem) this.instance).clearApp();
                return this;
            }

            public boolean hasAppLink() {
                return ((LaunchItem) this.instance).hasAppLink();
            }

            public AppLink getAppLink() {
                return ((LaunchItem) this.instance).getAppLink();
            }

            public Builder setAppLink(AppLink value) {
                copyOnWrite();
                ((LaunchItem) this.instance).setAppLink(value);
                return this;
            }

            public Builder setAppLink(AppLink.Builder builderForValue) {
                copyOnWrite();
                ((LaunchItem) this.instance).setAppLink(builderForValue);
                return this;
            }

            public Builder mergeAppLink(AppLink value) {
                copyOnWrite();
                ((LaunchItem) this.instance).mergeAppLink(value);
                return this;
            }

            public Builder clearAppLink() {
                copyOnWrite();
                ((LaunchItem) this.instance).clearAppLink();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new LaunchItem();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001", new Object[]{"bitField0_", "app_", "appLink_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<LaunchItem> parser = PARSER;
                    if (parser == null) {
                        synchronized (LaunchItem.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(LaunchItem.class, DEFAULT_INSTANCE);
        }

        public static LaunchItem getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LaunchItem> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Application extends GeneratedMessageLite<Application, Builder> implements ApplicationOrBuilder {
        public static final int BROWSABLE_CHANNEL_COUNT_FIELD_NUMBER = 6;
        public static final int CHANNELS_FIELD_NUMBER = 7;
        public static final int CHANNEL_COUNT_FIELD_NUMBER = 5;
        /* access modifiers changed from: private */
        public static final Application DEFAULT_INSTANCE = new Application();
        public static final int IS_GAME_FIELD_NUMBER = 3;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
        private static volatile Parser<Application> PARSER = null;
        public static final int TARGET_SDK_FIELD_NUMBER = 4;
        public static final int VERSION_CODE_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int browsableChannelCount_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int channelCount_;
        @ProtoField(fieldNumber = 7, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Channel> channels_ = emptyProtobufList();
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean isGame_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String packageName_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int targetSdk_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.UINT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int versionCode_;

        private Application() {
        }

        public boolean hasPackageName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getPackageName() {
            return this.packageName_;
        }

        public ByteString getPackageNameBytes() {
            return ByteString.copyFromUtf8(this.packageName_);
        }

        /* access modifiers changed from: private */
        public void setPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageName() {
            this.bitField0_ &= -2;
            this.packageName_ = getDefaultInstance().getPackageName();
        }

        /* access modifiers changed from: private */
        public void setPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasVersionCode() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getVersionCode() {
            return this.versionCode_;
        }

        /* access modifiers changed from: private */
        public void setVersionCode(int value) {
            this.bitField0_ |= 2;
            this.versionCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearVersionCode() {
            this.bitField0_ &= -3;
            this.versionCode_ = 0;
        }

        public boolean hasIsGame() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getIsGame() {
            return this.isGame_;
        }

        /* access modifiers changed from: private */
        public void setIsGame(boolean value) {
            this.bitField0_ |= 4;
            this.isGame_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsGame() {
            this.bitField0_ &= -5;
            this.isGame_ = false;
        }

        public boolean hasTargetSdk() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getTargetSdk() {
            return this.targetSdk_;
        }

        /* access modifiers changed from: private */
        public void setTargetSdk(int value) {
            this.bitField0_ |= 8;
            this.targetSdk_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTargetSdk() {
            this.bitField0_ &= -9;
            this.targetSdk_ = 0;
        }

        public boolean hasChannelCount() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getChannelCount() {
            return this.channelCount_;
        }

        /* access modifiers changed from: private */
        public void setChannelCount(int value) {
            this.bitField0_ |= 16;
            this.channelCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearChannelCount() {
            this.bitField0_ &= -17;
            this.channelCount_ = 0;
        }

        public boolean hasBrowsableChannelCount() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getBrowsableChannelCount() {
            return this.browsableChannelCount_;
        }

        /* access modifiers changed from: private */
        public void setBrowsableChannelCount(int value) {
            this.bitField0_ |= 32;
            this.browsableChannelCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBrowsableChannelCount() {
            this.bitField0_ &= -33;
            this.browsableChannelCount_ = 0;
        }

        public List<Channel> getChannelsList() {
            return this.channels_;
        }

        public List<? extends ChannelOrBuilder> getChannelsOrBuilderList() {
            return this.channels_;
        }

        public int getChannelsCount() {
            return this.channels_.size();
        }

        public Channel getChannels(int index) {
            return this.channels_.get(index);
        }

        public ChannelOrBuilder getChannelsOrBuilder(int index) {
            return this.channels_.get(index);
        }

        private void ensureChannelsIsMutable() {
            if (!this.channels_.isModifiable()) {
                this.channels_ = GeneratedMessageLite.mutableCopy(this.channels_);
            }
        }

        /* access modifiers changed from: private */
        public void setChannels(int index, Channel value) {
            if (value != null) {
                ensureChannelsIsMutable();
                this.channels_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setChannels(int index, Channel.Builder builderForValue) {
            ensureChannelsIsMutable();
            this.channels_.set(index, (Channel) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addChannels(Channel value) {
            if (value != null) {
                ensureChannelsIsMutable();
                this.channels_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addChannels(int index, Channel value) {
            if (value != null) {
                ensureChannelsIsMutable();
                this.channels_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addChannels(Channel.Builder builderForValue) {
            ensureChannelsIsMutable();
            this.channels_.add((Channel) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addChannels(int index, Channel.Builder builderForValue) {
            ensureChannelsIsMutable();
            this.channels_.add(index, (Channel) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Channel>, com.google.protobuf.Internal$ProtobufList<com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Channel>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllChannels(Iterable<? extends Channel> values) {
            ensureChannelsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.channels_);
        }

        /* access modifiers changed from: private */
        public void clearChannels() {
            this.channels_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeChannels(int index) {
            ensureChannelsIsMutable();
            this.channels_.remove(index);
        }

        public static Application parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Application parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Application parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Application parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Application parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Application parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Application parseFrom(InputStream input) throws IOException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Application parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Application parseDelimitedFrom(InputStream input) throws IOException {
            return (Application) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Application parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Application) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Application parseFrom(CodedInputStream input) throws IOException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Application parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Application) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Application prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Application, Builder> implements ApplicationOrBuilder {
            private Builder() {
                super(Application.DEFAULT_INSTANCE);
            }

            public boolean hasPackageName() {
                return ((Application) this.instance).hasPackageName();
            }

            public String getPackageName() {
                return ((Application) this.instance).getPackageName();
            }

            public ByteString getPackageNameBytes() {
                return ((Application) this.instance).getPackageNameBytes();
            }

            public Builder setPackageName(String value) {
                copyOnWrite();
                ((Application) this.instance).setPackageName(value);
                return this;
            }

            public Builder clearPackageName() {
                copyOnWrite();
                ((Application) this.instance).clearPackageName();
                return this;
            }

            public Builder setPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((Application) this.instance).setPackageNameBytes(value);
                return this;
            }

            public boolean hasVersionCode() {
                return ((Application) this.instance).hasVersionCode();
            }

            public int getVersionCode() {
                return ((Application) this.instance).getVersionCode();
            }

            public Builder setVersionCode(int value) {
                copyOnWrite();
                ((Application) this.instance).setVersionCode(value);
                return this;
            }

            public Builder clearVersionCode() {
                copyOnWrite();
                ((Application) this.instance).clearVersionCode();
                return this;
            }

            public boolean hasIsGame() {
                return ((Application) this.instance).hasIsGame();
            }

            public boolean getIsGame() {
                return ((Application) this.instance).getIsGame();
            }

            public Builder setIsGame(boolean value) {
                copyOnWrite();
                ((Application) this.instance).setIsGame(value);
                return this;
            }

            public Builder clearIsGame() {
                copyOnWrite();
                ((Application) this.instance).clearIsGame();
                return this;
            }

            public boolean hasTargetSdk() {
                return ((Application) this.instance).hasTargetSdk();
            }

            public int getTargetSdk() {
                return ((Application) this.instance).getTargetSdk();
            }

            public Builder setTargetSdk(int value) {
                copyOnWrite();
                ((Application) this.instance).setTargetSdk(value);
                return this;
            }

            public Builder clearTargetSdk() {
                copyOnWrite();
                ((Application) this.instance).clearTargetSdk();
                return this;
            }

            public boolean hasChannelCount() {
                return ((Application) this.instance).hasChannelCount();
            }

            public int getChannelCount() {
                return ((Application) this.instance).getChannelCount();
            }

            public Builder setChannelCount(int value) {
                copyOnWrite();
                ((Application) this.instance).setChannelCount(value);
                return this;
            }

            public Builder clearChannelCount() {
                copyOnWrite();
                ((Application) this.instance).clearChannelCount();
                return this;
            }

            public boolean hasBrowsableChannelCount() {
                return ((Application) this.instance).hasBrowsableChannelCount();
            }

            public int getBrowsableChannelCount() {
                return ((Application) this.instance).getBrowsableChannelCount();
            }

            public Builder setBrowsableChannelCount(int value) {
                copyOnWrite();
                ((Application) this.instance).setBrowsableChannelCount(value);
                return this;
            }

            public Builder clearBrowsableChannelCount() {
                copyOnWrite();
                ((Application) this.instance).clearBrowsableChannelCount();
                return this;
            }

            public List<Channel> getChannelsList() {
                return Collections.unmodifiableList(((Application) this.instance).getChannelsList());
            }

            public int getChannelsCount() {
                return ((Application) this.instance).getChannelsCount();
            }

            public Channel getChannels(int index) {
                return ((Application) this.instance).getChannels(index);
            }

            public Builder setChannels(int index, Channel value) {
                copyOnWrite();
                ((Application) this.instance).setChannels(index, value);
                return this;
            }

            public Builder setChannels(int index, Channel.Builder builderForValue) {
                copyOnWrite();
                ((Application) this.instance).setChannels(index, builderForValue);
                return this;
            }

            public Builder addChannels(Channel value) {
                copyOnWrite();
                ((Application) this.instance).addChannels(value);
                return this;
            }

            public Builder addChannels(int index, Channel value) {
                copyOnWrite();
                ((Application) this.instance).addChannels(index, value);
                return this;
            }

            public Builder addChannels(Channel.Builder builderForValue) {
                copyOnWrite();
                ((Application) this.instance).addChannels(builderForValue);
                return this;
            }

            public Builder addChannels(int index, Channel.Builder builderForValue) {
                copyOnWrite();
                ((Application) this.instance).addChannels(index, builderForValue);
                return this;
            }

            public Builder addAllChannels(Iterable<? extends Channel> values) {
                copyOnWrite();
                ((Application) this.instance).addAllChannels(values);
                return this;
            }

            public Builder clearChannels() {
                copyOnWrite();
                ((Application) this.instance).clearChannels();
                return this;
            }

            public Builder removeChannels(int index) {
                copyOnWrite();
                ((Application) this.instance).removeChannels(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Application();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0001\u0000\u0001\b\u0000\u0002\u000b\u0001\u0003\u0007\u0002\u0004\u0004\u0003\u0005\u0004\u0004\u0006\u0004\u0005\u0007\u001b", new Object[]{"bitField0_", "packageName_", "versionCode_", "isGame_", "targetSdk_", "channelCount_", "browsableChannelCount_", "channels_", Channel.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Application> parser = PARSER;
                    if (parser == null) {
                        synchronized (Application.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(Application.class, DEFAULT_INSTANCE);
        }

        public static Application getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Application> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AppLink extends GeneratedMessageLite<AppLink, Builder> implements AppLinkOrBuilder {
        /* access modifiers changed from: private */
        public static final AppLink DEFAULT_INSTANCE = new AppLink();
        public static final int IS_INSTALLED_FIELD_NUMBER = 3;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
        private static volatile Parser<AppLink> PARSER = null;
        public static final int URI_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean isInstalled_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String packageName_ = "";
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String uri_ = "";

        private AppLink() {
        }

        public boolean hasPackageName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getPackageName() {
            return this.packageName_;
        }

        public ByteString getPackageNameBytes() {
            return ByteString.copyFromUtf8(this.packageName_);
        }

        /* access modifiers changed from: private */
        public void setPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageName() {
            this.bitField0_ &= -2;
            this.packageName_ = getDefaultInstance().getPackageName();
        }

        /* access modifiers changed from: private */
        public void setPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasUri() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getUri() {
            return this.uri_;
        }

        public ByteString getUriBytes() {
            return ByteString.copyFromUtf8(this.uri_);
        }

        /* access modifiers changed from: private */
        public void setUri(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.uri_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearUri() {
            this.bitField0_ &= -3;
            this.uri_ = getDefaultInstance().getUri();
        }

        /* access modifiers changed from: private */
        public void setUriBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.uri_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasIsInstalled() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getIsInstalled() {
            return this.isInstalled_;
        }

        /* access modifiers changed from: private */
        public void setIsInstalled(boolean value) {
            this.bitField0_ |= 4;
            this.isInstalled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsInstalled() {
            this.bitField0_ &= -5;
            this.isInstalled_ = false;
        }

        public static AppLink parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppLink parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppLink parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppLink parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppLink parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppLink parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppLink parseFrom(InputStream input) throws IOException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AppLink parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AppLink parseDelimitedFrom(InputStream input) throws IOException {
            return (AppLink) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AppLink parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppLink) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AppLink parseFrom(CodedInputStream input) throws IOException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AppLink parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppLink) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AppLink prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<AppLink, Builder> implements AppLinkOrBuilder {
            private Builder() {
                super(AppLink.DEFAULT_INSTANCE);
            }

            public boolean hasPackageName() {
                return ((AppLink) this.instance).hasPackageName();
            }

            public String getPackageName() {
                return ((AppLink) this.instance).getPackageName();
            }

            public ByteString getPackageNameBytes() {
                return ((AppLink) this.instance).getPackageNameBytes();
            }

            public Builder setPackageName(String value) {
                copyOnWrite();
                ((AppLink) this.instance).setPackageName(value);
                return this;
            }

            public Builder clearPackageName() {
                copyOnWrite();
                ((AppLink) this.instance).clearPackageName();
                return this;
            }

            public Builder setPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((AppLink) this.instance).setPackageNameBytes(value);
                return this;
            }

            public boolean hasUri() {
                return ((AppLink) this.instance).hasUri();
            }

            public String getUri() {
                return ((AppLink) this.instance).getUri();
            }

            public ByteString getUriBytes() {
                return ((AppLink) this.instance).getUriBytes();
            }

            public Builder setUri(String value) {
                copyOnWrite();
                ((AppLink) this.instance).setUri(value);
                return this;
            }

            public Builder clearUri() {
                copyOnWrite();
                ((AppLink) this.instance).clearUri();
                return this;
            }

            public Builder setUriBytes(ByteString value) {
                copyOnWrite();
                ((AppLink) this.instance).setUriBytes(value);
                return this;
            }

            public boolean hasIsInstalled() {
                return ((AppLink) this.instance).hasIsInstalled();
            }

            public boolean getIsInstalled() {
                return ((AppLink) this.instance).getIsInstalled();
            }

            public Builder setIsInstalled(boolean value) {
                copyOnWrite();
                ((AppLink) this.instance).setIsInstalled(value);
                return this;
            }

            public Builder clearIsInstalled() {
                copyOnWrite();
                ((AppLink) this.instance).clearIsInstalled();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AppLink();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\u0007\u0002", new Object[]{"bitField0_", "packageName_", "uri_", "isInstalled_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AppLink> parser = PARSER;
                    if (parser == null) {
                        synchronized (AppLink.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(AppLink.class, DEFAULT_INSTANCE);
        }

        public static AppLink getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AppLink> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class LaunchItemCollection extends GeneratedMessageLite<LaunchItemCollection, Builder> implements LaunchItemCollectionOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final LaunchItemCollection DEFAULT_INSTANCE = new LaunchItemCollection();
        public static final int GAME_COUNT_FIELD_NUMBER = 2;
        public static final int ITEMS_FIELD_NUMBER = 3;
        private static volatile Parser<LaunchItemCollection> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int gameCount_;
        @ProtoField(fieldNumber = 3, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<LaunchItem> items_ = emptyProtobufList();

        private LaunchItemCollection() {
        }

        public boolean hasCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getCount() {
            return this.count_;
        }

        /* access modifiers changed from: private */
        public void setCount(int value) {
            this.bitField0_ |= 1;
            this.count_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCount() {
            this.bitField0_ &= -2;
            this.count_ = 0;
        }

        public boolean hasGameCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getGameCount() {
            return this.gameCount_;
        }

        /* access modifiers changed from: private */
        public void setGameCount(int value) {
            this.bitField0_ |= 2;
            this.gameCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearGameCount() {
            this.bitField0_ &= -3;
            this.gameCount_ = 0;
        }

        public List<LaunchItem> getItemsList() {
            return this.items_;
        }

        public List<? extends LaunchItemOrBuilder> getItemsOrBuilderList() {
            return this.items_;
        }

        public int getItemsCount() {
            return this.items_.size();
        }

        public LaunchItem getItems(int index) {
            return this.items_.get(index);
        }

        public LaunchItemOrBuilder getItemsOrBuilder(int index) {
            return this.items_.get(index);
        }

        private void ensureItemsIsMutable() {
            if (!this.items_.isModifiable()) {
                this.items_ = GeneratedMessageLite.mutableCopy(this.items_);
            }
        }

        /* access modifiers changed from: private */
        public void setItems(int index, LaunchItem value) {
            if (value != null) {
                ensureItemsIsMutable();
                this.items_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setItems(int index, LaunchItem.Builder builderForValue) {
            ensureItemsIsMutable();
            this.items_.set(index, (LaunchItem) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addItems(LaunchItem value) {
            if (value != null) {
                ensureItemsIsMutable();
                this.items_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addItems(int index, LaunchItem value) {
            if (value != null) {
                ensureItemsIsMutable();
                this.items_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addItems(LaunchItem.Builder builderForValue) {
            ensureItemsIsMutable();
            this.items_.add((LaunchItem) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addItems(int index, LaunchItem.Builder builderForValue) {
            ensureItemsIsMutable();
            this.items_.add(index, (LaunchItem) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$LaunchItem>, com.google.protobuf.Internal$ProtobufList<com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$LaunchItem>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllItems(Iterable<? extends LaunchItem> values) {
            ensureItemsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.items_);
        }

        /* access modifiers changed from: private */
        public void clearItems() {
            this.items_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeItems(int index) {
            ensureItemsIsMutable();
            this.items_.remove(index);
        }

        public static LaunchItemCollection parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LaunchItemCollection parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LaunchItemCollection parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LaunchItemCollection parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LaunchItemCollection parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static LaunchItemCollection parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static LaunchItemCollection parseFrom(InputStream input) throws IOException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static LaunchItemCollection parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static LaunchItemCollection parseDelimitedFrom(InputStream input) throws IOException {
            return (LaunchItemCollection) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static LaunchItemCollection parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LaunchItemCollection) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static LaunchItemCollection parseFrom(CodedInputStream input) throws IOException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static LaunchItemCollection parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (LaunchItemCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(LaunchItemCollection prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<LaunchItemCollection, Builder> implements LaunchItemCollectionOrBuilder {
            private Builder() {
                super(LaunchItemCollection.DEFAULT_INSTANCE);
            }

            public boolean hasCount() {
                return ((LaunchItemCollection) this.instance).hasCount();
            }

            public int getCount() {
                return ((LaunchItemCollection) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).clearCount();
                return this;
            }

            public boolean hasGameCount() {
                return ((LaunchItemCollection) this.instance).hasGameCount();
            }

            public int getGameCount() {
                return ((LaunchItemCollection) this.instance).getGameCount();
            }

            public Builder setGameCount(int value) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).setGameCount(value);
                return this;
            }

            public Builder clearGameCount() {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).clearGameCount();
                return this;
            }

            public List<LaunchItem> getItemsList() {
                return Collections.unmodifiableList(((LaunchItemCollection) this.instance).getItemsList());
            }

            public int getItemsCount() {
                return ((LaunchItemCollection) this.instance).getItemsCount();
            }

            public LaunchItem getItems(int index) {
                return ((LaunchItemCollection) this.instance).getItems(index);
            }

            public Builder setItems(int index, LaunchItem value) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).setItems(index, value);
                return this;
            }

            public Builder setItems(int index, LaunchItem.Builder builderForValue) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).setItems(index, builderForValue);
                return this;
            }

            public Builder addItems(LaunchItem value) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).addItems(value);
                return this;
            }

            public Builder addItems(int index, LaunchItem value) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).addItems(index, value);
                return this;
            }

            public Builder addItems(LaunchItem.Builder builderForValue) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).addItems(builderForValue);
                return this;
            }

            public Builder addItems(int index, LaunchItem.Builder builderForValue) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).addItems(index, builderForValue);
                return this;
            }

            public Builder addAllItems(Iterable<? extends LaunchItem> values) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).addAllItems(values);
                return this;
            }

            public Builder clearItems() {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).clearItems();
                return this;
            }

            public Builder removeItems(int index) {
                copyOnWrite();
                ((LaunchItemCollection) this.instance).removeItems(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new LaunchItemCollection();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0001\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u001b", new Object[]{"bitField0_", "count_", "gameCount_", "items_", LaunchItem.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<LaunchItemCollection> parser = PARSER;
                    if (parser == null) {
                        synchronized (LaunchItemCollection.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(LaunchItemCollection.class, DEFAULT_INSTANCE);
        }

        public static LaunchItemCollection getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<LaunchItemCollection> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Input extends GeneratedMessageLite<Input, Builder> implements InputOrBuilder {
        /* access modifiers changed from: private */
        public static final Input DEFAULT_INSTANCE = new Input();
        public static final int DISPLAY_NAME_FIELD_NUMBER = 2;
        private static volatile Parser<Input> PARSER = null;
        public static final int TYPE_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String displayName_ = "";
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int type_ = 1;

        private Input() {
        }

        public enum Type implements Internal.EnumLite {
            TUNER(1),
            COMPOSITE(2),
            SVIDEO(3),
            SCART(4),
            COMPONENT(5),
            VGA(6),
            DVI(7),
            HDMI(8),
            DISPLAY_PORT(9),
            CEC_DEVICE(-2),
            BUNDLED_TUNER(-3),
            CEC_DEVICE_RECORDER(-4),
            CEC_DEVICE_PLAYBACK(-5),
            MHL_MOBILE(-6);
            
            public static final int BUNDLED_TUNER_VALUE = -3;
            public static final int CEC_DEVICE_PLAYBACK_VALUE = -5;
            public static final int CEC_DEVICE_RECORDER_VALUE = -4;
            public static final int CEC_DEVICE_VALUE = -2;
            public static final int COMPONENT_VALUE = 5;
            public static final int COMPOSITE_VALUE = 2;
            public static final int DISPLAY_PORT_VALUE = 9;
            public static final int DVI_VALUE = 7;
            public static final int HDMI_VALUE = 8;
            public static final int MHL_MOBILE_VALUE = -6;
            public static final int SCART_VALUE = 4;
            public static final int SVIDEO_VALUE = 3;
            public static final int TUNER_VALUE = 1;
            public static final int VGA_VALUE = 6;
            private static final Internal.EnumLiteMap<Type> internalValueMap = new Internal.EnumLiteMap<Type>() {
                public Type findValueByNumber(int number) {
                    return Type.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static Type forNumber(int value2) {
                switch (value2) {
                    case -6:
                        return MHL_MOBILE;
                    case -5:
                        return CEC_DEVICE_PLAYBACK;
                    case -4:
                        return CEC_DEVICE_RECORDER;
                    case -3:
                        return BUNDLED_TUNER;
                    case -2:
                        return CEC_DEVICE;
                    case -1:
                    case 0:
                    default:
                        return null;
                    case 1:
                        return TUNER;
                    case 2:
                        return COMPOSITE;
                    case 3:
                        return SVIDEO;
                    case 4:
                        return SCART;
                    case 5:
                        return COMPONENT;
                    case 6:
                        return VGA;
                    case 7:
                        return DVI;
                    case 8:
                        return HDMI;
                    case 9:
                        return DISPLAY_PORT;
                }
            }

            public static Internal.EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TypeVerifier.INSTANCE;
            }

            private static final class TypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new TypeVerifier();

                private TypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return Type.forNumber(number) != null;
                }
            }

            private Type(int value2) {
                this.value = value2;
            }
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) != 0;
        }

        public Type getType() {
            Type result = Type.forNumber(this.type_);
            return result == null ? Type.TUNER : result;
        }

        /* access modifiers changed from: private */
        public void setType(Type value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.type_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearType() {
            this.bitField0_ &= -2;
            this.type_ = 1;
        }

        public boolean hasDisplayName() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getDisplayName() {
            return this.displayName_;
        }

        public ByteString getDisplayNameBytes() {
            return ByteString.copyFromUtf8(this.displayName_);
        }

        /* access modifiers changed from: private */
        public void setDisplayName(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.displayName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDisplayName() {
            this.bitField0_ &= -3;
            this.displayName_ = getDefaultInstance().getDisplayName();
        }

        /* access modifiers changed from: private */
        public void setDisplayNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.displayName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static Input parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Input parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Input parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Input parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Input parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Input parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Input parseFrom(InputStream input) throws IOException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Input parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Input parseDelimitedFrom(InputStream input) throws IOException {
            return (Input) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Input parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Input) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Input parseFrom(CodedInputStream input) throws IOException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Input parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Input) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Input prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Input, Builder> implements InputOrBuilder {
            private Builder() {
                super(Input.DEFAULT_INSTANCE);
            }

            public boolean hasType() {
                return ((Input) this.instance).hasType();
            }

            public Type getType() {
                return ((Input) this.instance).getType();
            }

            public Builder setType(Type value) {
                copyOnWrite();
                ((Input) this.instance).setType(value);
                return this;
            }

            public Builder clearType() {
                copyOnWrite();
                ((Input) this.instance).clearType();
                return this;
            }

            public boolean hasDisplayName() {
                return ((Input) this.instance).hasDisplayName();
            }

            public String getDisplayName() {
                return ((Input) this.instance).getDisplayName();
            }

            public ByteString getDisplayNameBytes() {
                return ((Input) this.instance).getDisplayNameBytes();
            }

            public Builder setDisplayName(String value) {
                copyOnWrite();
                ((Input) this.instance).setDisplayName(value);
                return this;
            }

            public Builder clearDisplayName() {
                copyOnWrite();
                ((Input) this.instance).clearDisplayName();
                return this;
            }

            public Builder setDisplayNameBytes(ByteString value) {
                copyOnWrite();
                ((Input) this.instance).setDisplayNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Input();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0000\u0002\b\u0001", new Object[]{"bitField0_", "type_", Type.internalGetVerifier(), "displayName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Input> parser = PARSER;
                    if (parser == null) {
                        synchronized (Input.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(Input.class, DEFAULT_INSTANCE);
        }

        public static Input getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Input> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class InputCollection extends GeneratedMessageLite<InputCollection, Builder> implements InputCollectionOrBuilder {
        public static final int COUNT_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final InputCollection DEFAULT_INSTANCE = new InputCollection();
        public static final int INPUTS_FIELD_NUMBER = 2;
        private static volatile Parser<InputCollection> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int count_;
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Input> inputs_ = emptyProtobufList();

        private InputCollection() {
        }

        public boolean hasCount() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getCount() {
            return this.count_;
        }

        /* access modifiers changed from: private */
        public void setCount(int value) {
            this.bitField0_ |= 1;
            this.count_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCount() {
            this.bitField0_ &= -2;
            this.count_ = 0;
        }

        public List<Input> getInputsList() {
            return this.inputs_;
        }

        public List<? extends InputOrBuilder> getInputsOrBuilderList() {
            return this.inputs_;
        }

        public int getInputsCount() {
            return this.inputs_.size();
        }

        public Input getInputs(int index) {
            return this.inputs_.get(index);
        }

        public InputOrBuilder getInputsOrBuilder(int index) {
            return this.inputs_.get(index);
        }

        private void ensureInputsIsMutable() {
            if (!this.inputs_.isModifiable()) {
                this.inputs_ = GeneratedMessageLite.mutableCopy(this.inputs_);
            }
        }

        /* access modifiers changed from: private */
        public void setInputs(int index, Input value) {
            if (value != null) {
                ensureInputsIsMutable();
                this.inputs_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setInputs(int index, Input.Builder builderForValue) {
            ensureInputsIsMutable();
            this.inputs_.set(index, (Input) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addInputs(Input value) {
            if (value != null) {
                ensureInputsIsMutable();
                this.inputs_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addInputs(int index, Input value) {
            if (value != null) {
                ensureInputsIsMutable();
                this.inputs_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addInputs(Input.Builder builderForValue) {
            ensureInputsIsMutable();
            this.inputs_.add((Input) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addInputs(int index, Input.Builder builderForValue) {
            ensureInputsIsMutable();
            this.inputs_.add(index, (Input) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Input>, com.google.protobuf.Internal$ProtobufList<com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Input>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllInputs(Iterable<? extends Input> values) {
            ensureInputsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.inputs_);
        }

        /* access modifiers changed from: private */
        public void clearInputs() {
            this.inputs_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeInputs(int index) {
            ensureInputsIsMutable();
            this.inputs_.remove(index);
        }

        public static InputCollection parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InputCollection parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InputCollection parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InputCollection parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InputCollection parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InputCollection parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InputCollection parseFrom(InputStream input) throws IOException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static InputCollection parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static InputCollection parseDelimitedFrom(InputStream input) throws IOException {
            return (InputCollection) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static InputCollection parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InputCollection) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static InputCollection parseFrom(CodedInputStream input) throws IOException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static InputCollection parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InputCollection) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(InputCollection prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<InputCollection, Builder> implements InputCollectionOrBuilder {
            private Builder() {
                super(InputCollection.DEFAULT_INSTANCE);
            }

            public boolean hasCount() {
                return ((InputCollection) this.instance).hasCount();
            }

            public int getCount() {
                return ((InputCollection) this.instance).getCount();
            }

            public Builder setCount(int value) {
                copyOnWrite();
                ((InputCollection) this.instance).setCount(value);
                return this;
            }

            public Builder clearCount() {
                copyOnWrite();
                ((InputCollection) this.instance).clearCount();
                return this;
            }

            public List<Input> getInputsList() {
                return Collections.unmodifiableList(((InputCollection) this.instance).getInputsList());
            }

            public int getInputsCount() {
                return ((InputCollection) this.instance).getInputsCount();
            }

            public Input getInputs(int index) {
                return ((InputCollection) this.instance).getInputs(index);
            }

            public Builder setInputs(int index, Input value) {
                copyOnWrite();
                ((InputCollection) this.instance).setInputs(index, value);
                return this;
            }

            public Builder setInputs(int index, Input.Builder builderForValue) {
                copyOnWrite();
                ((InputCollection) this.instance).setInputs(index, builderForValue);
                return this;
            }

            public Builder addInputs(Input value) {
                copyOnWrite();
                ((InputCollection) this.instance).addInputs(value);
                return this;
            }

            public Builder addInputs(int index, Input value) {
                copyOnWrite();
                ((InputCollection) this.instance).addInputs(index, value);
                return this;
            }

            public Builder addInputs(Input.Builder builderForValue) {
                copyOnWrite();
                ((InputCollection) this.instance).addInputs(builderForValue);
                return this;
            }

            public Builder addInputs(int index, Input.Builder builderForValue) {
                copyOnWrite();
                ((InputCollection) this.instance).addInputs(index, builderForValue);
                return this;
            }

            public Builder addAllInputs(Iterable<? extends Input> values) {
                copyOnWrite();
                ((InputCollection) this.instance).addAllInputs(values);
                return this;
            }

            public Builder clearInputs() {
                copyOnWrite();
                ((InputCollection) this.instance).clearInputs();
                return this;
            }

            public Builder removeInputs(int index) {
                copyOnWrite();
                ((InputCollection) this.instance).removeInputs(index);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new InputCollection();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u0004\u0000\u0002\u001b", new Object[]{"bitField0_", "count_", "inputs_", Input.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<InputCollection> parser = PARSER;
                    if (parser == null) {
                        synchronized (InputCollection.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(InputCollection.class, DEFAULT_INSTANCE);
        }

        public static InputCollection getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<InputCollection> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SystemProperties extends GeneratedMessageLite<SystemProperties, Builder> implements SystemPropertiesOrBuilder {
        public static final int APPS_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final SystemProperties DEFAULT_INSTANCE = new SystemProperties();
        public static final int IS_DEFAULT_LAUNCHER_FIELD_NUMBER = 1;
        public static final int MEDIA_PACKAGE_NAME_FIELD_NUMBER = 2;
        private static volatile Parser<SystemProperties> PARSER = null;
        public static final int WATCH_NEXT_CHANNEL_FIELD_NUMBER = 4;
        @ProtoField(fieldNumber = 3, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Application> apps_ = emptyProtobufList();
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean isDefaultLauncher_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String mediaPackageName_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private WatchNextChannel watchNextChannel_;

        private SystemProperties() {
        }

        public boolean hasIsDefaultLauncher() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getIsDefaultLauncher() {
            return this.isDefaultLauncher_;
        }

        /* access modifiers changed from: private */
        public void setIsDefaultLauncher(boolean value) {
            this.bitField0_ |= 1;
            this.isDefaultLauncher_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsDefaultLauncher() {
            this.bitField0_ &= -2;
            this.isDefaultLauncher_ = false;
        }

        public boolean hasMediaPackageName() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getMediaPackageName() {
            return this.mediaPackageName_;
        }

        public ByteString getMediaPackageNameBytes() {
            return ByteString.copyFromUtf8(this.mediaPackageName_);
        }

        /* access modifiers changed from: private */
        public void setMediaPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.mediaPackageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMediaPackageName() {
            this.bitField0_ &= -3;
            this.mediaPackageName_ = getDefaultInstance().getMediaPackageName();
        }

        /* access modifiers changed from: private */
        public void setMediaPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.mediaPackageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<Application> getAppsList() {
            return this.apps_;
        }

        public List<? extends ApplicationOrBuilder> getAppsOrBuilderList() {
            return this.apps_;
        }

        public int getAppsCount() {
            return this.apps_.size();
        }

        public Application getApps(int index) {
            return this.apps_.get(index);
        }

        public ApplicationOrBuilder getAppsOrBuilder(int index) {
            return this.apps_.get(index);
        }

        private void ensureAppsIsMutable() {
            if (!this.apps_.isModifiable()) {
                this.apps_ = GeneratedMessageLite.mutableCopy(this.apps_);
            }
        }

        /* access modifiers changed from: private */
        public void setApps(int index, Application value) {
            if (value != null) {
                ensureAppsIsMutable();
                this.apps_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setApps(int index, Application.Builder builderForValue) {
            ensureAppsIsMutable();
            this.apps_.set(index, (Application) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addApps(Application value) {
            if (value != null) {
                ensureAppsIsMutable();
                this.apps_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addApps(int index, Application value) {
            if (value != null) {
                ensureAppsIsMutable();
                this.apps_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addApps(Application.Builder builderForValue) {
            ensureAppsIsMutable();
            this.apps_.add((Application) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addApps(int index, Application.Builder builderForValue) {
            ensureAppsIsMutable();
            this.apps_.add(index, (Application) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Application>, com.google.protobuf.Internal$ProtobufList<com.google.protos.logs.proto.wireless.android.tvlauncher.TvlauncherClientLog$Application>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllApps(Iterable<? extends Application> values) {
            ensureAppsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.apps_);
        }

        /* access modifiers changed from: private */
        public void clearApps() {
            this.apps_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeApps(int index) {
            ensureAppsIsMutable();
            this.apps_.remove(index);
        }

        public boolean hasWatchNextChannel() {
            return (this.bitField0_ & 4) != 0;
        }

        public WatchNextChannel getWatchNextChannel() {
            WatchNextChannel watchNextChannel = this.watchNextChannel_;
            return watchNextChannel == null ? WatchNextChannel.getDefaultInstance() : watchNextChannel;
        }

        /* access modifiers changed from: private */
        public void setWatchNextChannel(WatchNextChannel value) {
            if (value != null) {
                this.watchNextChannel_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setWatchNextChannel(WatchNextChannel.Builder builderForValue) {
            this.watchNextChannel_ = (WatchNextChannel) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeWatchNextChannel(WatchNextChannel value) {
            if (value != null) {
                WatchNextChannel watchNextChannel = this.watchNextChannel_;
                if (watchNextChannel == null || watchNextChannel == WatchNextChannel.getDefaultInstance()) {
                    this.watchNextChannel_ = value;
                } else {
                    this.watchNextChannel_ = (WatchNextChannel) ((WatchNextChannel.Builder) WatchNextChannel.newBuilder(this.watchNextChannel_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearWatchNextChannel() {
            this.watchNextChannel_ = null;
            this.bitField0_ &= -5;
        }

        public static SystemProperties parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemProperties parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemProperties parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemProperties parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemProperties parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemProperties parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemProperties parseFrom(InputStream input) throws IOException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemProperties parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SystemProperties parseDelimitedFrom(InputStream input) throws IOException {
            return (SystemProperties) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemProperties parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemProperties) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SystemProperties parseFrom(CodedInputStream input) throws IOException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemProperties parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemProperties) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SystemProperties prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SystemProperties, Builder> implements SystemPropertiesOrBuilder {
            private Builder() {
                super(SystemProperties.DEFAULT_INSTANCE);
            }

            public boolean hasIsDefaultLauncher() {
                return ((SystemProperties) this.instance).hasIsDefaultLauncher();
            }

            public boolean getIsDefaultLauncher() {
                return ((SystemProperties) this.instance).getIsDefaultLauncher();
            }

            public Builder setIsDefaultLauncher(boolean value) {
                copyOnWrite();
                ((SystemProperties) this.instance).setIsDefaultLauncher(value);
                return this;
            }

            public Builder clearIsDefaultLauncher() {
                copyOnWrite();
                ((SystemProperties) this.instance).clearIsDefaultLauncher();
                return this;
            }

            public boolean hasMediaPackageName() {
                return ((SystemProperties) this.instance).hasMediaPackageName();
            }

            public String getMediaPackageName() {
                return ((SystemProperties) this.instance).getMediaPackageName();
            }

            public ByteString getMediaPackageNameBytes() {
                return ((SystemProperties) this.instance).getMediaPackageNameBytes();
            }

            public Builder setMediaPackageName(String value) {
                copyOnWrite();
                ((SystemProperties) this.instance).setMediaPackageName(value);
                return this;
            }

            public Builder clearMediaPackageName() {
                copyOnWrite();
                ((SystemProperties) this.instance).clearMediaPackageName();
                return this;
            }

            public Builder setMediaPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((SystemProperties) this.instance).setMediaPackageNameBytes(value);
                return this;
            }

            public List<Application> getAppsList() {
                return Collections.unmodifiableList(((SystemProperties) this.instance).getAppsList());
            }

            public int getAppsCount() {
                return ((SystemProperties) this.instance).getAppsCount();
            }

            public Application getApps(int index) {
                return ((SystemProperties) this.instance).getApps(index);
            }

            public Builder setApps(int index, Application value) {
                copyOnWrite();
                ((SystemProperties) this.instance).setApps(index, value);
                return this;
            }

            public Builder setApps(int index, Application.Builder builderForValue) {
                copyOnWrite();
                ((SystemProperties) this.instance).setApps(index, builderForValue);
                return this;
            }

            public Builder addApps(Application value) {
                copyOnWrite();
                ((SystemProperties) this.instance).addApps(value);
                return this;
            }

            public Builder addApps(int index, Application value) {
                copyOnWrite();
                ((SystemProperties) this.instance).addApps(index, value);
                return this;
            }

            public Builder addApps(Application.Builder builderForValue) {
                copyOnWrite();
                ((SystemProperties) this.instance).addApps(builderForValue);
                return this;
            }

            public Builder addApps(int index, Application.Builder builderForValue) {
                copyOnWrite();
                ((SystemProperties) this.instance).addApps(index, builderForValue);
                return this;
            }

            public Builder addAllApps(Iterable<? extends Application> values) {
                copyOnWrite();
                ((SystemProperties) this.instance).addAllApps(values);
                return this;
            }

            public Builder clearApps() {
                copyOnWrite();
                ((SystemProperties) this.instance).clearApps();
                return this;
            }

            public Builder removeApps(int index) {
                copyOnWrite();
                ((SystemProperties) this.instance).removeApps(index);
                return this;
            }

            public boolean hasWatchNextChannel() {
                return ((SystemProperties) this.instance).hasWatchNextChannel();
            }

            public WatchNextChannel getWatchNextChannel() {
                return ((SystemProperties) this.instance).getWatchNextChannel();
            }

            public Builder setWatchNextChannel(WatchNextChannel value) {
                copyOnWrite();
                ((SystemProperties) this.instance).setWatchNextChannel(value);
                return this;
            }

            public Builder setWatchNextChannel(WatchNextChannel.Builder builderForValue) {
                copyOnWrite();
                ((SystemProperties) this.instance).setWatchNextChannel(builderForValue);
                return this;
            }

            public Builder mergeWatchNextChannel(WatchNextChannel value) {
                copyOnWrite();
                ((SystemProperties) this.instance).mergeWatchNextChannel(value);
                return this;
            }

            public Builder clearWatchNextChannel() {
                copyOnWrite();
                ((SystemProperties) this.instance).clearWatchNextChannel();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SystemProperties();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001\u0007\u0000\u0002\b\u0001\u0003\u001b\u0004\t\u0002", new Object[]{"bitField0_", "isDefaultLauncher_", "mediaPackageName_", "apps_", Application.class, "watchNextChannel_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SystemProperties> parser = PARSER;
                    if (parser == null) {
                        synchronized (SystemProperties.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(SystemProperties.class, DEFAULT_INSTANCE);
        }

        public static SystemProperties getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SystemProperties> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class DeviceStatus extends GeneratedMessageLite<DeviceStatus, Builder> implements DeviceStatusOrBuilder {
        public static final int ACTIVE_INPUT_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final DeviceStatus DEFAULT_INSTANCE = new DeviceStatus();
        public static final int FOREGROUND_PACKAGE_NAME_FIELD_NUMBER = 1;
        public static final int IS_PLAYING_AUDIO_FIELD_NUMBER = 4;
        public static final int IS_PLAYING_VIDEO_FIELD_NUMBER = 3;
        public static final int IS_SCREEN_ON_FIELD_NUMBER = 5;
        private static volatile Parser<DeviceStatus> PARSER = null;
        public static final int PIP_PACKAGE_NAME_FIELD_NUMBER = 6;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private Input activeInput_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String foregroundPackageName_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean isPlayingAudio_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean isPlayingVideo_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean isScreenOn_;
        @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private String pipPackageName_ = "";

        private DeviceStatus() {
        }

        public boolean hasForegroundPackageName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getForegroundPackageName() {
            return this.foregroundPackageName_;
        }

        public ByteString getForegroundPackageNameBytes() {
            return ByteString.copyFromUtf8(this.foregroundPackageName_);
        }

        /* access modifiers changed from: private */
        public void setForegroundPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.foregroundPackageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearForegroundPackageName() {
            this.bitField0_ &= -2;
            this.foregroundPackageName_ = getDefaultInstance().getForegroundPackageName();
        }

        /* access modifiers changed from: private */
        public void setForegroundPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.foregroundPackageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasActiveInput() {
            return (this.bitField0_ & 2) != 0;
        }

        public Input getActiveInput() {
            Input input = this.activeInput_;
            return input == null ? Input.getDefaultInstance() : input;
        }

        /* access modifiers changed from: private */
        public void setActiveInput(Input value) {
            if (value != null) {
                this.activeInput_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setActiveInput(Input.Builder builderForValue) {
            this.activeInput_ = (Input) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeActiveInput(Input value) {
            if (value != null) {
                Input input = this.activeInput_;
                if (input == null || input == Input.getDefaultInstance()) {
                    this.activeInput_ = value;
                } else {
                    this.activeInput_ = (Input) ((Input.Builder) Input.newBuilder(this.activeInput_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearActiveInput() {
            this.activeInput_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasIsPlayingVideo() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getIsPlayingVideo() {
            return this.isPlayingVideo_;
        }

        /* access modifiers changed from: private */
        public void setIsPlayingVideo(boolean value) {
            this.bitField0_ |= 4;
            this.isPlayingVideo_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsPlayingVideo() {
            this.bitField0_ &= -5;
            this.isPlayingVideo_ = false;
        }

        public boolean hasIsPlayingAudio() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getIsPlayingAudio() {
            return this.isPlayingAudio_;
        }

        /* access modifiers changed from: private */
        public void setIsPlayingAudio(boolean value) {
            this.bitField0_ |= 8;
            this.isPlayingAudio_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsPlayingAudio() {
            this.bitField0_ &= -9;
            this.isPlayingAudio_ = false;
        }

        public boolean hasIsScreenOn() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getIsScreenOn() {
            return this.isScreenOn_;
        }

        /* access modifiers changed from: private */
        public void setIsScreenOn(boolean value) {
            this.bitField0_ |= 16;
            this.isScreenOn_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsScreenOn() {
            this.bitField0_ &= -17;
            this.isScreenOn_ = false;
        }

        public boolean hasPipPackageName() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getPipPackageName() {
            return this.pipPackageName_;
        }

        public ByteString getPipPackageNameBytes() {
            return ByteString.copyFromUtf8(this.pipPackageName_);
        }

        /* access modifiers changed from: private */
        public void setPipPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.pipPackageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPipPackageName() {
            this.bitField0_ &= -33;
            this.pipPackageName_ = getDefaultInstance().getPipPackageName();
        }

        /* access modifiers changed from: private */
        public void setPipPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.pipPackageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public static DeviceStatus parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceStatus parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceStatus parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceStatus parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceStatus parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static DeviceStatus parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static DeviceStatus parseFrom(InputStream input) throws IOException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceStatus parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceStatus parseDelimitedFrom(InputStream input) throws IOException {
            return (DeviceStatus) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceStatus parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceStatus) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static DeviceStatus parseFrom(CodedInputStream input) throws IOException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static DeviceStatus parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DeviceStatus) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(DeviceStatus prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<DeviceStatus, Builder> implements DeviceStatusOrBuilder {
            private Builder() {
                super(DeviceStatus.DEFAULT_INSTANCE);
            }

            public boolean hasForegroundPackageName() {
                return ((DeviceStatus) this.instance).hasForegroundPackageName();
            }

            public String getForegroundPackageName() {
                return ((DeviceStatus) this.instance).getForegroundPackageName();
            }

            public ByteString getForegroundPackageNameBytes() {
                return ((DeviceStatus) this.instance).getForegroundPackageNameBytes();
            }

            public Builder setForegroundPackageName(String value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setForegroundPackageName(value);
                return this;
            }

            public Builder clearForegroundPackageName() {
                copyOnWrite();
                ((DeviceStatus) this.instance).clearForegroundPackageName();
                return this;
            }

            public Builder setForegroundPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setForegroundPackageNameBytes(value);
                return this;
            }

            public boolean hasActiveInput() {
                return ((DeviceStatus) this.instance).hasActiveInput();
            }

            public Input getActiveInput() {
                return ((DeviceStatus) this.instance).getActiveInput();
            }

            public Builder setActiveInput(Input value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setActiveInput(value);
                return this;
            }

            public Builder setActiveInput(Input.Builder builderForValue) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setActiveInput(builderForValue);
                return this;
            }

            public Builder mergeActiveInput(Input value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).mergeActiveInput(value);
                return this;
            }

            public Builder clearActiveInput() {
                copyOnWrite();
                ((DeviceStatus) this.instance).clearActiveInput();
                return this;
            }

            public boolean hasIsPlayingVideo() {
                return ((DeviceStatus) this.instance).hasIsPlayingVideo();
            }

            public boolean getIsPlayingVideo() {
                return ((DeviceStatus) this.instance).getIsPlayingVideo();
            }

            public Builder setIsPlayingVideo(boolean value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setIsPlayingVideo(value);
                return this;
            }

            public Builder clearIsPlayingVideo() {
                copyOnWrite();
                ((DeviceStatus) this.instance).clearIsPlayingVideo();
                return this;
            }

            public boolean hasIsPlayingAudio() {
                return ((DeviceStatus) this.instance).hasIsPlayingAudio();
            }

            public boolean getIsPlayingAudio() {
                return ((DeviceStatus) this.instance).getIsPlayingAudio();
            }

            public Builder setIsPlayingAudio(boolean value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setIsPlayingAudio(value);
                return this;
            }

            public Builder clearIsPlayingAudio() {
                copyOnWrite();
                ((DeviceStatus) this.instance).clearIsPlayingAudio();
                return this;
            }

            public boolean hasIsScreenOn() {
                return ((DeviceStatus) this.instance).hasIsScreenOn();
            }

            public boolean getIsScreenOn() {
                return ((DeviceStatus) this.instance).getIsScreenOn();
            }

            public Builder setIsScreenOn(boolean value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setIsScreenOn(value);
                return this;
            }

            public Builder clearIsScreenOn() {
                copyOnWrite();
                ((DeviceStatus) this.instance).clearIsScreenOn();
                return this;
            }

            public boolean hasPipPackageName() {
                return ((DeviceStatus) this.instance).hasPipPackageName();
            }

            public String getPipPackageName() {
                return ((DeviceStatus) this.instance).getPipPackageName();
            }

            public ByteString getPipPackageNameBytes() {
                return ((DeviceStatus) this.instance).getPipPackageNameBytes();
            }

            public Builder setPipPackageName(String value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setPipPackageName(value);
                return this;
            }

            public Builder clearPipPackageName() {
                copyOnWrite();
                ((DeviceStatus) this.instance).clearPipPackageName();
                return this;
            }

            public Builder setPipPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((DeviceStatus) this.instance).setPipPackageNameBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new DeviceStatus();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\b\u0000\u0002\t\u0001\u0003\u0007\u0002\u0004\u0007\u0003\u0005\u0007\u0004\u0006\b\u0005", new Object[]{"bitField0_", "foregroundPackageName_", "activeInput_", "isPlayingVideo_", "isPlayingAudio_", "isScreenOn_", "pipPackageName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<DeviceStatus> parser = PARSER;
                    if (parser == null) {
                        synchronized (DeviceStatus.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(DeviceStatus.class, DEFAULT_INSTANCE);
        }

        public static DeviceStatus getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<DeviceStatus> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MediaSession extends GeneratedMessageLite<MediaSession, Builder> implements MediaSessionOrBuilder {
        /* access modifiers changed from: private */
        public static final MediaSession DEFAULT_INSTANCE = new MediaSession();
        public static final int DURATION_SECONDS_FIELD_NUMBER = 7;
        public static final int HAS_PREVIEW_FIELD_NUMBER = 5;
        public static final int INITIATED_FROM_LAUNCHER_FIELD_NUMBER = 3;
        public static final int IS_VIDEO_FIELD_NUMBER = 2;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
        private static volatile Parser<MediaSession> PARSER = null;
        public static final int PLAYING_DURATION_SECONDS_FIELD_NUMBER = 8;
        public static final int START_LATENCY_MILLIS_FIELD_NUMBER = 4;
        public static final int WAS_PREVIEW_PLAYING_FIELD_NUMBER = 6;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private int durationSeconds_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean hasPreview_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean initiatedFromLauncher_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean isVideo_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String packageName_ = "";
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int playingDurationSeconds_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long startLatencyMillis_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private boolean wasPreviewPlaying_;

        private MediaSession() {
        }

        public boolean hasPackageName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getPackageName() {
            return this.packageName_;
        }

        public ByteString getPackageNameBytes() {
            return ByteString.copyFromUtf8(this.packageName_);
        }

        /* access modifiers changed from: private */
        public void setPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageName() {
            this.bitField0_ &= -2;
            this.packageName_ = getDefaultInstance().getPackageName();
        }

        /* access modifiers changed from: private */
        public void setPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasIsVideo() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getIsVideo() {
            return this.isVideo_;
        }

        /* access modifiers changed from: private */
        public void setIsVideo(boolean value) {
            this.bitField0_ |= 2;
            this.isVideo_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsVideo() {
            this.bitField0_ &= -3;
            this.isVideo_ = false;
        }

        public boolean hasInitiatedFromLauncher() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getInitiatedFromLauncher() {
            return this.initiatedFromLauncher_;
        }

        /* access modifiers changed from: private */
        public void setInitiatedFromLauncher(boolean value) {
            this.bitField0_ |= 4;
            this.initiatedFromLauncher_ = value;
        }

        /* access modifiers changed from: private */
        public void clearInitiatedFromLauncher() {
            this.bitField0_ &= -5;
            this.initiatedFromLauncher_ = false;
        }

        public boolean hasStartLatencyMillis() {
            return (this.bitField0_ & 8) != 0;
        }

        public long getStartLatencyMillis() {
            return this.startLatencyMillis_;
        }

        /* access modifiers changed from: private */
        public void setStartLatencyMillis(long value) {
            this.bitField0_ |= 8;
            this.startLatencyMillis_ = value;
        }

        /* access modifiers changed from: private */
        public void clearStartLatencyMillis() {
            this.bitField0_ &= -9;
            this.startLatencyMillis_ = 0;
        }

        public boolean hasHasPreview() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getHasPreview() {
            return this.hasPreview_;
        }

        /* access modifiers changed from: private */
        public void setHasPreview(boolean value) {
            this.bitField0_ |= 16;
            this.hasPreview_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHasPreview() {
            this.bitField0_ &= -17;
            this.hasPreview_ = false;
        }

        public boolean hasWasPreviewPlaying() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getWasPreviewPlaying() {
            return this.wasPreviewPlaying_;
        }

        /* access modifiers changed from: private */
        public void setWasPreviewPlaying(boolean value) {
            this.bitField0_ |= 32;
            this.wasPreviewPlaying_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWasPreviewPlaying() {
            this.bitField0_ &= -33;
            this.wasPreviewPlaying_ = false;
        }

        public boolean hasDurationSeconds() {
            return (this.bitField0_ & 64) != 0;
        }

        public int getDurationSeconds() {
            return this.durationSeconds_;
        }

        /* access modifiers changed from: private */
        public void setDurationSeconds(int value) {
            this.bitField0_ |= 64;
            this.durationSeconds_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDurationSeconds() {
            this.bitField0_ &= -65;
            this.durationSeconds_ = 0;
        }

        public boolean hasPlayingDurationSeconds() {
            return (this.bitField0_ & 128) != 0;
        }

        public int getPlayingDurationSeconds() {
            return this.playingDurationSeconds_;
        }

        /* access modifiers changed from: private */
        public void setPlayingDurationSeconds(int value) {
            this.bitField0_ |= 128;
            this.playingDurationSeconds_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPlayingDurationSeconds() {
            this.bitField0_ &= -129;
            this.playingDurationSeconds_ = 0;
        }

        public static MediaSession parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MediaSession parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MediaSession parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MediaSession parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MediaSession parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MediaSession parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MediaSession parseFrom(InputStream input) throws IOException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MediaSession parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MediaSession parseDelimitedFrom(InputStream input) throws IOException {
            return (MediaSession) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MediaSession parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MediaSession) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MediaSession parseFrom(CodedInputStream input) throws IOException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MediaSession parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MediaSession) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MediaSession prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MediaSession, Builder> implements MediaSessionOrBuilder {
            private Builder() {
                super(MediaSession.DEFAULT_INSTANCE);
            }

            public boolean hasPackageName() {
                return ((MediaSession) this.instance).hasPackageName();
            }

            public String getPackageName() {
                return ((MediaSession) this.instance).getPackageName();
            }

            public ByteString getPackageNameBytes() {
                return ((MediaSession) this.instance).getPackageNameBytes();
            }

            public Builder setPackageName(String value) {
                copyOnWrite();
                ((MediaSession) this.instance).setPackageName(value);
                return this;
            }

            public Builder clearPackageName() {
                copyOnWrite();
                ((MediaSession) this.instance).clearPackageName();
                return this;
            }

            public Builder setPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((MediaSession) this.instance).setPackageNameBytes(value);
                return this;
            }

            public boolean hasIsVideo() {
                return ((MediaSession) this.instance).hasIsVideo();
            }

            public boolean getIsVideo() {
                return ((MediaSession) this.instance).getIsVideo();
            }

            public Builder setIsVideo(boolean value) {
                copyOnWrite();
                ((MediaSession) this.instance).setIsVideo(value);
                return this;
            }

            public Builder clearIsVideo() {
                copyOnWrite();
                ((MediaSession) this.instance).clearIsVideo();
                return this;
            }

            public boolean hasInitiatedFromLauncher() {
                return ((MediaSession) this.instance).hasInitiatedFromLauncher();
            }

            public boolean getInitiatedFromLauncher() {
                return ((MediaSession) this.instance).getInitiatedFromLauncher();
            }

            public Builder setInitiatedFromLauncher(boolean value) {
                copyOnWrite();
                ((MediaSession) this.instance).setInitiatedFromLauncher(value);
                return this;
            }

            public Builder clearInitiatedFromLauncher() {
                copyOnWrite();
                ((MediaSession) this.instance).clearInitiatedFromLauncher();
                return this;
            }

            public boolean hasStartLatencyMillis() {
                return ((MediaSession) this.instance).hasStartLatencyMillis();
            }

            public long getStartLatencyMillis() {
                return ((MediaSession) this.instance).getStartLatencyMillis();
            }

            public Builder setStartLatencyMillis(long value) {
                copyOnWrite();
                ((MediaSession) this.instance).setStartLatencyMillis(value);
                return this;
            }

            public Builder clearStartLatencyMillis() {
                copyOnWrite();
                ((MediaSession) this.instance).clearStartLatencyMillis();
                return this;
            }

            public boolean hasHasPreview() {
                return ((MediaSession) this.instance).hasHasPreview();
            }

            public boolean getHasPreview() {
                return ((MediaSession) this.instance).getHasPreview();
            }

            public Builder setHasPreview(boolean value) {
                copyOnWrite();
                ((MediaSession) this.instance).setHasPreview(value);
                return this;
            }

            public Builder clearHasPreview() {
                copyOnWrite();
                ((MediaSession) this.instance).clearHasPreview();
                return this;
            }

            public boolean hasWasPreviewPlaying() {
                return ((MediaSession) this.instance).hasWasPreviewPlaying();
            }

            public boolean getWasPreviewPlaying() {
                return ((MediaSession) this.instance).getWasPreviewPlaying();
            }

            public Builder setWasPreviewPlaying(boolean value) {
                copyOnWrite();
                ((MediaSession) this.instance).setWasPreviewPlaying(value);
                return this;
            }

            public Builder clearWasPreviewPlaying() {
                copyOnWrite();
                ((MediaSession) this.instance).clearWasPreviewPlaying();
                return this;
            }

            public boolean hasDurationSeconds() {
                return ((MediaSession) this.instance).hasDurationSeconds();
            }

            public int getDurationSeconds() {
                return ((MediaSession) this.instance).getDurationSeconds();
            }

            public Builder setDurationSeconds(int value) {
                copyOnWrite();
                ((MediaSession) this.instance).setDurationSeconds(value);
                return this;
            }

            public Builder clearDurationSeconds() {
                copyOnWrite();
                ((MediaSession) this.instance).clearDurationSeconds();
                return this;
            }

            public boolean hasPlayingDurationSeconds() {
                return ((MediaSession) this.instance).hasPlayingDurationSeconds();
            }

            public int getPlayingDurationSeconds() {
                return ((MediaSession) this.instance).getPlayingDurationSeconds();
            }

            public Builder setPlayingDurationSeconds(int value) {
                copyOnWrite();
                ((MediaSession) this.instance).setPlayingDurationSeconds(value);
                return this;
            }

            public Builder clearPlayingDurationSeconds() {
                copyOnWrite();
                ((MediaSession) this.instance).clearPlayingDurationSeconds();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MediaSession();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0000\u0000\u0001\b\u0000\u0002\u0007\u0001\u0003\u0007\u0002\u0004\u0002\u0003\u0005\u0007\u0004\u0006\u0007\u0005\u0007\u0004\u0006\b\u0004\u0007", new Object[]{"bitField0_", "packageName_", "isVideo_", "initiatedFromLauncher_", "startLatencyMillis_", "hasPreview_", "wasPreviewPlaying_", "durationSeconds_", "playingDurationSeconds_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MediaSession> parser = PARSER;
                    if (parser == null) {
                        synchronized (MediaSession.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case GET_MEMOIZED_IS_INITIALIZED:
                    return (byte) 1;
                case SET_MEMOIZED_IS_INITIALIZED:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            GeneratedMessageLite.registerDefaultInstance(MediaSession.class, DEFAULT_INSTANCE);
        }

        public static MediaSession getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MediaSession> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
