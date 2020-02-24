package logs.proto.wireless.performance.mobile;

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

public final class ExtensionPhotos {

    public interface AssistantStatsOrBuilder extends MessageLiteOrBuilder {
        int getNumPhotosLoaded();

        int getNumPhotosRequested();

        boolean hasNumPhotosLoaded();

        boolean hasNumPhotosRequested();
    }

    public interface BackgroundTaskStatsOrBuilder extends MessageLiteOrBuilder {
        boolean getIsBackgroundServiceRunningAlready();

        boolean getIsPhotosInForeground();

        boolean hasIsBackgroundServiceRunningAlready();

        boolean hasIsPhotosInForeground();
    }

    public interface CameraAssistantStatsOrBuilder extends MessageLiteOrBuilder {
        boolean getIsActionUnfinished();

        boolean hasIsActionUnfinished();
    }

    public interface FingerprintStatsOrBuilder extends MessageLiteOrBuilder {
        long getFileSizeKb();

        boolean hasFileSizeKb();
    }

    public interface PhotosExtensionOrBuilder extends MessageLiteOrBuilder {
        AssistantStats getAssistantStats();

        BackgroundTaskStats getBackgroundTaskStats();

        CameraAssistantStats getCameraAssistantStats();

        FingerprintStats getFingerprintStats();

        SharingStats getSharingStats();

        VideoPlayerStats getVideoPlayerStats();

        boolean hasAssistantStats();

        boolean hasBackgroundTaskStats();

        boolean hasCameraAssistantStats();

        boolean hasFingerprintStats();

        boolean hasSharingStats();

        boolean hasVideoPlayerStats();
    }

    public interface SharedImageMetadataOrBuilder extends MessageLiteOrBuilder {
        int getHeight();

        long getSizeInBytes();

        int getWidth();

        boolean hasHeight();

        boolean hasSizeInBytes();

        boolean hasWidth();
    }

    public interface SharedImageTransformOrBuilder extends MessageLiteOrBuilder {
        SharedImageTransform.ResizedByType getResizedByType();

        SharedImageMetadata getSharedImageMetadatas(int i);

        int getSharedImageMetadatasCount();

        List<SharedImageMetadata> getSharedImageMetadatasList();

        boolean hasResizedByType();
    }

    public interface SharingStatsOrBuilder extends MessageLiteOrBuilder {
        int getNumCollections();

        int getNumItemsCollection();

        int getNumSharingApps();

        SharedImageTransform getSharedImageTransforms(int i);

        int getSharedImageTransformsCount();

        List<SharedImageTransform> getSharedImageTransformsList();

        boolean hasNumCollections();

        boolean hasNumItemsCollection();

        boolean hasNumSharingApps();
    }

    public interface VideoPlayerStatsOrBuilder extends MessageLiteOrBuilder {
        VideoPlayerStats.MediaType getMediaType();

        VideoPlayerStats.Player getPlayer();

        int getRetryCount();

        long getVideoDurationSeconds();

        boolean hasMediaType();

        boolean hasPlayer();

        boolean hasRetryCount();

        boolean hasVideoDurationSeconds();
    }

    private ExtensionPhotos() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PhotosExtension extends GeneratedMessageLite<PhotosExtension, Builder> implements PhotosExtensionOrBuilder {
        public static final int ASSISTANT_STATS_FIELD_NUMBER = 1;
        public static final int BACKGROUND_TASK_STATS_FIELD_NUMBER = 3;
        public static final int CAMERA_ASSISTANT_STATS_FIELD_NUMBER = 4;
        /* access modifiers changed from: private */
        public static final PhotosExtension DEFAULT_INSTANCE = new PhotosExtension();
        public static final int FINGERPRINT_STATS_FIELD_NUMBER = 6;
        private static volatile Parser<PhotosExtension> PARSER = null;
        public static final int SHARING_STATS_FIELD_NUMBER = 2;
        public static final int VIDEO_PLAYER_STATS_FIELD_NUMBER = 5;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private AssistantStats assistantStats_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private BackgroundTaskStats backgroundTaskStats_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private CameraAssistantStats cameraAssistantStats_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private FingerprintStats fingerprintStats_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private SharingStats sharingStats_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private VideoPlayerStats videoPlayerStats_;

        private PhotosExtension() {
        }

        public boolean hasAssistantStats() {
            return (this.bitField0_ & 1) != 0;
        }

        public AssistantStats getAssistantStats() {
            AssistantStats assistantStats = this.assistantStats_;
            return assistantStats == null ? AssistantStats.getDefaultInstance() : assistantStats;
        }

        /* access modifiers changed from: private */
        public void setAssistantStats(AssistantStats value) {
            if (value != null) {
                this.assistantStats_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAssistantStats(AssistantStats.Builder builderForValue) {
            this.assistantStats_ = (AssistantStats) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeAssistantStats(AssistantStats value) {
            if (value != null) {
                AssistantStats assistantStats = this.assistantStats_;
                if (assistantStats == null || assistantStats == AssistantStats.getDefaultInstance()) {
                    this.assistantStats_ = value;
                } else {
                    this.assistantStats_ = (AssistantStats) ((AssistantStats.Builder) AssistantStats.newBuilder(this.assistantStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAssistantStats() {
            this.assistantStats_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasSharingStats() {
            return (this.bitField0_ & 2) != 0;
        }

        public SharingStats getSharingStats() {
            SharingStats sharingStats = this.sharingStats_;
            return sharingStats == null ? SharingStats.getDefaultInstance() : sharingStats;
        }

        /* access modifiers changed from: private */
        public void setSharingStats(SharingStats value) {
            if (value != null) {
                this.sharingStats_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSharingStats(SharingStats.Builder builderForValue) {
            this.sharingStats_ = (SharingStats) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeSharingStats(SharingStats value) {
            if (value != null) {
                SharingStats sharingStats = this.sharingStats_;
                if (sharingStats == null || sharingStats == SharingStats.getDefaultInstance()) {
                    this.sharingStats_ = value;
                } else {
                    this.sharingStats_ = (SharingStats) ((SharingStats.Builder) SharingStats.newBuilder(this.sharingStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSharingStats() {
            this.sharingStats_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasBackgroundTaskStats() {
            return (this.bitField0_ & 4) != 0;
        }

        public BackgroundTaskStats getBackgroundTaskStats() {
            BackgroundTaskStats backgroundTaskStats = this.backgroundTaskStats_;
            return backgroundTaskStats == null ? BackgroundTaskStats.getDefaultInstance() : backgroundTaskStats;
        }

        /* access modifiers changed from: private */
        public void setBackgroundTaskStats(BackgroundTaskStats value) {
            if (value != null) {
                this.backgroundTaskStats_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setBackgroundTaskStats(BackgroundTaskStats.Builder builderForValue) {
            this.backgroundTaskStats_ = (BackgroundTaskStats) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergeBackgroundTaskStats(BackgroundTaskStats value) {
            if (value != null) {
                BackgroundTaskStats backgroundTaskStats = this.backgroundTaskStats_;
                if (backgroundTaskStats == null || backgroundTaskStats == BackgroundTaskStats.getDefaultInstance()) {
                    this.backgroundTaskStats_ = value;
                } else {
                    this.backgroundTaskStats_ = (BackgroundTaskStats) ((BackgroundTaskStats.Builder) BackgroundTaskStats.newBuilder(this.backgroundTaskStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearBackgroundTaskStats() {
            this.backgroundTaskStats_ = null;
            this.bitField0_ &= -5;
        }

        public boolean hasCameraAssistantStats() {
            return (this.bitField0_ & 8) != 0;
        }

        public CameraAssistantStats getCameraAssistantStats() {
            CameraAssistantStats cameraAssistantStats = this.cameraAssistantStats_;
            return cameraAssistantStats == null ? CameraAssistantStats.getDefaultInstance() : cameraAssistantStats;
        }

        /* access modifiers changed from: private */
        public void setCameraAssistantStats(CameraAssistantStats value) {
            if (value != null) {
                this.cameraAssistantStats_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCameraAssistantStats(CameraAssistantStats.Builder builderForValue) {
            this.cameraAssistantStats_ = (CameraAssistantStats) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeCameraAssistantStats(CameraAssistantStats value) {
            if (value != null) {
                CameraAssistantStats cameraAssistantStats = this.cameraAssistantStats_;
                if (cameraAssistantStats == null || cameraAssistantStats == CameraAssistantStats.getDefaultInstance()) {
                    this.cameraAssistantStats_ = value;
                } else {
                    this.cameraAssistantStats_ = (CameraAssistantStats) ((CameraAssistantStats.Builder) CameraAssistantStats.newBuilder(this.cameraAssistantStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCameraAssistantStats() {
            this.cameraAssistantStats_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasVideoPlayerStats() {
            return (this.bitField0_ & 16) != 0;
        }

        public VideoPlayerStats getVideoPlayerStats() {
            VideoPlayerStats videoPlayerStats = this.videoPlayerStats_;
            return videoPlayerStats == null ? VideoPlayerStats.getDefaultInstance() : videoPlayerStats;
        }

        /* access modifiers changed from: private */
        public void setVideoPlayerStats(VideoPlayerStats value) {
            if (value != null) {
                this.videoPlayerStats_ = value;
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setVideoPlayerStats(VideoPlayerStats.Builder builderForValue) {
            this.videoPlayerStats_ = (VideoPlayerStats) builderForValue.build();
            this.bitField0_ |= 16;
        }

        /* access modifiers changed from: private */
        public void mergeVideoPlayerStats(VideoPlayerStats value) {
            if (value != null) {
                VideoPlayerStats videoPlayerStats = this.videoPlayerStats_;
                if (videoPlayerStats == null || videoPlayerStats == VideoPlayerStats.getDefaultInstance()) {
                    this.videoPlayerStats_ = value;
                } else {
                    this.videoPlayerStats_ = (VideoPlayerStats) ((VideoPlayerStats.Builder) VideoPlayerStats.newBuilder(this.videoPlayerStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearVideoPlayerStats() {
            this.videoPlayerStats_ = null;
            this.bitField0_ &= -17;
        }

        public boolean hasFingerprintStats() {
            return (this.bitField0_ & 32) != 0;
        }

        public FingerprintStats getFingerprintStats() {
            FingerprintStats fingerprintStats = this.fingerprintStats_;
            return fingerprintStats == null ? FingerprintStats.getDefaultInstance() : fingerprintStats;
        }

        /* access modifiers changed from: private */
        public void setFingerprintStats(FingerprintStats value) {
            if (value != null) {
                this.fingerprintStats_ = value;
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setFingerprintStats(FingerprintStats.Builder builderForValue) {
            this.fingerprintStats_ = (FingerprintStats) builderForValue.build();
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void mergeFingerprintStats(FingerprintStats value) {
            if (value != null) {
                FingerprintStats fingerprintStats = this.fingerprintStats_;
                if (fingerprintStats == null || fingerprintStats == FingerprintStats.getDefaultInstance()) {
                    this.fingerprintStats_ = value;
                } else {
                    this.fingerprintStats_ = (FingerprintStats) ((FingerprintStats.Builder) FingerprintStats.newBuilder(this.fingerprintStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearFingerprintStats() {
            this.fingerprintStats_ = null;
            this.bitField0_ &= -33;
        }

        public static PhotosExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PhotosExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PhotosExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PhotosExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PhotosExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PhotosExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PhotosExtension parseFrom(InputStream input) throws IOException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PhotosExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PhotosExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (PhotosExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PhotosExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PhotosExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PhotosExtension parseFrom(CodedInputStream input) throws IOException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PhotosExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PhotosExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PhotosExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PhotosExtension, Builder> implements PhotosExtensionOrBuilder {
            private Builder() {
                super(PhotosExtension.DEFAULT_INSTANCE);
            }

            public boolean hasAssistantStats() {
                return ((PhotosExtension) this.instance).hasAssistantStats();
            }

            public AssistantStats getAssistantStats() {
                return ((PhotosExtension) this.instance).getAssistantStats();
            }

            public Builder setAssistantStats(AssistantStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setAssistantStats(value);
                return this;
            }

            public Builder setAssistantStats(AssistantStats.Builder builderForValue) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setAssistantStats(builderForValue);
                return this;
            }

            public Builder mergeAssistantStats(AssistantStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).mergeAssistantStats(value);
                return this;
            }

            public Builder clearAssistantStats() {
                copyOnWrite();
                ((PhotosExtension) this.instance).clearAssistantStats();
                return this;
            }

            public boolean hasSharingStats() {
                return ((PhotosExtension) this.instance).hasSharingStats();
            }

            public SharingStats getSharingStats() {
                return ((PhotosExtension) this.instance).getSharingStats();
            }

            public Builder setSharingStats(SharingStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setSharingStats(value);
                return this;
            }

            public Builder setSharingStats(SharingStats.Builder builderForValue) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setSharingStats(builderForValue);
                return this;
            }

            public Builder mergeSharingStats(SharingStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).mergeSharingStats(value);
                return this;
            }

            public Builder clearSharingStats() {
                copyOnWrite();
                ((PhotosExtension) this.instance).clearSharingStats();
                return this;
            }

            public boolean hasBackgroundTaskStats() {
                return ((PhotosExtension) this.instance).hasBackgroundTaskStats();
            }

            public BackgroundTaskStats getBackgroundTaskStats() {
                return ((PhotosExtension) this.instance).getBackgroundTaskStats();
            }

            public Builder setBackgroundTaskStats(BackgroundTaskStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setBackgroundTaskStats(value);
                return this;
            }

            public Builder setBackgroundTaskStats(BackgroundTaskStats.Builder builderForValue) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setBackgroundTaskStats(builderForValue);
                return this;
            }

            public Builder mergeBackgroundTaskStats(BackgroundTaskStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).mergeBackgroundTaskStats(value);
                return this;
            }

            public Builder clearBackgroundTaskStats() {
                copyOnWrite();
                ((PhotosExtension) this.instance).clearBackgroundTaskStats();
                return this;
            }

            public boolean hasCameraAssistantStats() {
                return ((PhotosExtension) this.instance).hasCameraAssistantStats();
            }

            public CameraAssistantStats getCameraAssistantStats() {
                return ((PhotosExtension) this.instance).getCameraAssistantStats();
            }

            public Builder setCameraAssistantStats(CameraAssistantStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setCameraAssistantStats(value);
                return this;
            }

            public Builder setCameraAssistantStats(CameraAssistantStats.Builder builderForValue) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setCameraAssistantStats(builderForValue);
                return this;
            }

            public Builder mergeCameraAssistantStats(CameraAssistantStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).mergeCameraAssistantStats(value);
                return this;
            }

            public Builder clearCameraAssistantStats() {
                copyOnWrite();
                ((PhotosExtension) this.instance).clearCameraAssistantStats();
                return this;
            }

            public boolean hasVideoPlayerStats() {
                return ((PhotosExtension) this.instance).hasVideoPlayerStats();
            }

            public VideoPlayerStats getVideoPlayerStats() {
                return ((PhotosExtension) this.instance).getVideoPlayerStats();
            }

            public Builder setVideoPlayerStats(VideoPlayerStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setVideoPlayerStats(value);
                return this;
            }

            public Builder setVideoPlayerStats(VideoPlayerStats.Builder builderForValue) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setVideoPlayerStats(builderForValue);
                return this;
            }

            public Builder mergeVideoPlayerStats(VideoPlayerStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).mergeVideoPlayerStats(value);
                return this;
            }

            public Builder clearVideoPlayerStats() {
                copyOnWrite();
                ((PhotosExtension) this.instance).clearVideoPlayerStats();
                return this;
            }

            public boolean hasFingerprintStats() {
                return ((PhotosExtension) this.instance).hasFingerprintStats();
            }

            public FingerprintStats getFingerprintStats() {
                return ((PhotosExtension) this.instance).getFingerprintStats();
            }

            public Builder setFingerprintStats(FingerprintStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setFingerprintStats(value);
                return this;
            }

            public Builder setFingerprintStats(FingerprintStats.Builder builderForValue) {
                copyOnWrite();
                ((PhotosExtension) this.instance).setFingerprintStats(builderForValue);
                return this;
            }

            public Builder mergeFingerprintStats(FingerprintStats value) {
                copyOnWrite();
                ((PhotosExtension) this.instance).mergeFingerprintStats(value);
                return this;
            }

            public Builder clearFingerprintStats() {
                copyOnWrite();
                ((PhotosExtension) this.instance).clearFingerprintStats();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PhotosExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003\u0005\t\u0004\u0006\t\u0005", new Object[]{"bitField0_", "assistantStats_", "sharingStats_", "backgroundTaskStats_", "cameraAssistantStats_", "videoPlayerStats_", "fingerprintStats_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PhotosExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (PhotosExtension.class) {
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
            GeneratedMessageLite.registerDefaultInstance(PhotosExtension.class, DEFAULT_INSTANCE);
        }

        public static PhotosExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PhotosExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AssistantStats extends GeneratedMessageLite<AssistantStats, Builder> implements AssistantStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final AssistantStats DEFAULT_INSTANCE = new AssistantStats();
        public static final int NUM_PHOTOS_LOADED_FIELD_NUMBER = 2;
        public static final int NUM_PHOTOS_REQUESTED_FIELD_NUMBER = 3;
        private static volatile Parser<AssistantStats> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int numPhotosLoaded_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int numPhotosRequested_;

        private AssistantStats() {
        }

        public boolean hasNumPhotosLoaded() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getNumPhotosLoaded() {
            return this.numPhotosLoaded_;
        }

        /* access modifiers changed from: private */
        public void setNumPhotosLoaded(int value) {
            this.bitField0_ |= 1;
            this.numPhotosLoaded_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumPhotosLoaded() {
            this.bitField0_ &= -2;
            this.numPhotosLoaded_ = 0;
        }

        public boolean hasNumPhotosRequested() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumPhotosRequested() {
            return this.numPhotosRequested_;
        }

        /* access modifiers changed from: private */
        public void setNumPhotosRequested(int value) {
            this.bitField0_ |= 2;
            this.numPhotosRequested_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumPhotosRequested() {
            this.bitField0_ &= -3;
            this.numPhotosRequested_ = 0;
        }

        public static AssistantStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AssistantStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AssistantStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AssistantStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AssistantStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AssistantStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AssistantStats parseFrom(InputStream input) throws IOException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AssistantStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AssistantStats parseDelimitedFrom(InputStream input) throws IOException {
            return (AssistantStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AssistantStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AssistantStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AssistantStats parseFrom(CodedInputStream input) throws IOException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AssistantStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AssistantStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<AssistantStats, Builder> implements AssistantStatsOrBuilder {
            private Builder() {
                super(AssistantStats.DEFAULT_INSTANCE);
            }

            public boolean hasNumPhotosLoaded() {
                return ((AssistantStats) this.instance).hasNumPhotosLoaded();
            }

            public int getNumPhotosLoaded() {
                return ((AssistantStats) this.instance).getNumPhotosLoaded();
            }

            public Builder setNumPhotosLoaded(int value) {
                copyOnWrite();
                ((AssistantStats) this.instance).setNumPhotosLoaded(value);
                return this;
            }

            public Builder clearNumPhotosLoaded() {
                copyOnWrite();
                ((AssistantStats) this.instance).clearNumPhotosLoaded();
                return this;
            }

            public boolean hasNumPhotosRequested() {
                return ((AssistantStats) this.instance).hasNumPhotosRequested();
            }

            public int getNumPhotosRequested() {
                return ((AssistantStats) this.instance).getNumPhotosRequested();
            }

            public Builder setNumPhotosRequested(int value) {
                copyOnWrite();
                ((AssistantStats) this.instance).setNumPhotosRequested(value);
                return this;
            }

            public Builder clearNumPhotosRequested() {
                copyOnWrite();
                ((AssistantStats) this.instance).clearNumPhotosRequested();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AssistantStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0002\u0003\u0002\u0000\u0000\u0000\u0002\u0004\u0000\u0003\u0004\u0001", new Object[]{"bitField0_", "numPhotosLoaded_", "numPhotosRequested_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AssistantStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (AssistantStats.class) {
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
            GeneratedMessageLite.registerDefaultInstance(AssistantStats.class, DEFAULT_INSTANCE);
        }

        public static AssistantStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AssistantStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SharingStats extends GeneratedMessageLite<SharingStats, Builder> implements SharingStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final SharingStats DEFAULT_INSTANCE = new SharingStats();
        public static final int NUM_COLLECTIONS_FIELD_NUMBER = 4;
        public static final int NUM_ITEMS_COLLECTION_FIELD_NUMBER = 1;
        public static final int NUM_SHARING_APPS_FIELD_NUMBER = 3;
        private static volatile Parser<SharingStats> PARSER = null;
        public static final int SHARED_IMAGE_TRANSFORMS_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int numCollections_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int numItemsCollection_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int numSharingApps_;
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<SharedImageTransform> sharedImageTransforms_ = emptyProtobufList();

        private SharingStats() {
        }

        public boolean hasNumItemsCollection() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getNumItemsCollection() {
            return this.numItemsCollection_;
        }

        /* access modifiers changed from: private */
        public void setNumItemsCollection(int value) {
            this.bitField0_ |= 1;
            this.numItemsCollection_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumItemsCollection() {
            this.bitField0_ &= -2;
            this.numItemsCollection_ = 0;
        }

        public List<SharedImageTransform> getSharedImageTransformsList() {
            return this.sharedImageTransforms_;
        }

        public List<? extends SharedImageTransformOrBuilder> getSharedImageTransformsOrBuilderList() {
            return this.sharedImageTransforms_;
        }

        public int getSharedImageTransformsCount() {
            return this.sharedImageTransforms_.size();
        }

        public SharedImageTransform getSharedImageTransforms(int index) {
            return this.sharedImageTransforms_.get(index);
        }

        public SharedImageTransformOrBuilder getSharedImageTransformsOrBuilder(int index) {
            return this.sharedImageTransforms_.get(index);
        }

        private void ensureSharedImageTransformsIsMutable() {
            if (!this.sharedImageTransforms_.isModifiable()) {
                this.sharedImageTransforms_ = GeneratedMessageLite.mutableCopy(this.sharedImageTransforms_);
            }
        }

        /* access modifiers changed from: private */
        public void setSharedImageTransforms(int index, SharedImageTransform value) {
            if (value != null) {
                ensureSharedImageTransformsIsMutable();
                this.sharedImageTransforms_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSharedImageTransforms(int index, SharedImageTransform.Builder builderForValue) {
            ensureSharedImageTransformsIsMutable();
            this.sharedImageTransforms_.set(index, (SharedImageTransform) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSharedImageTransforms(SharedImageTransform value) {
            if (value != null) {
                ensureSharedImageTransformsIsMutable();
                this.sharedImageTransforms_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSharedImageTransforms(int index, SharedImageTransform value) {
            if (value != null) {
                ensureSharedImageTransformsIsMutable();
                this.sharedImageTransforms_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSharedImageTransforms(SharedImageTransform.Builder builderForValue) {
            ensureSharedImageTransformsIsMutable();
            this.sharedImageTransforms_.add((SharedImageTransform) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSharedImageTransforms(int index, SharedImageTransform.Builder builderForValue) {
            ensureSharedImageTransformsIsMutable();
            this.sharedImageTransforms_.add(index, (SharedImageTransform) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.ExtensionPhotos$SharedImageTransform>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.ExtensionPhotos$SharedImageTransform>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSharedImageTransforms(Iterable<? extends SharedImageTransform> values) {
            ensureSharedImageTransformsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.sharedImageTransforms_);
        }

        /* access modifiers changed from: private */
        public void clearSharedImageTransforms() {
            this.sharedImageTransforms_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSharedImageTransforms(int index) {
            ensureSharedImageTransformsIsMutable();
            this.sharedImageTransforms_.remove(index);
        }

        public boolean hasNumSharingApps() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumSharingApps() {
            return this.numSharingApps_;
        }

        /* access modifiers changed from: private */
        public void setNumSharingApps(int value) {
            this.bitField0_ |= 2;
            this.numSharingApps_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumSharingApps() {
            this.bitField0_ &= -3;
            this.numSharingApps_ = 0;
        }

        public boolean hasNumCollections() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getNumCollections() {
            return this.numCollections_;
        }

        /* access modifiers changed from: private */
        public void setNumCollections(int value) {
            this.bitField0_ |= 4;
            this.numCollections_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumCollections() {
            this.bitField0_ &= -5;
            this.numCollections_ = 0;
        }

        public static SharingStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharingStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharingStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharingStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharingStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharingStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharingStats parseFrom(InputStream input) throws IOException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SharingStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SharingStats parseDelimitedFrom(InputStream input) throws IOException {
            return (SharingStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SharingStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharingStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SharingStats parseFrom(CodedInputStream input) throws IOException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SharingStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharingStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SharingStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SharingStats, Builder> implements SharingStatsOrBuilder {
            private Builder() {
                super(SharingStats.DEFAULT_INSTANCE);
            }

            public boolean hasNumItemsCollection() {
                return ((SharingStats) this.instance).hasNumItemsCollection();
            }

            public int getNumItemsCollection() {
                return ((SharingStats) this.instance).getNumItemsCollection();
            }

            public Builder setNumItemsCollection(int value) {
                copyOnWrite();
                ((SharingStats) this.instance).setNumItemsCollection(value);
                return this;
            }

            public Builder clearNumItemsCollection() {
                copyOnWrite();
                ((SharingStats) this.instance).clearNumItemsCollection();
                return this;
            }

            public List<SharedImageTransform> getSharedImageTransformsList() {
                return Collections.unmodifiableList(((SharingStats) this.instance).getSharedImageTransformsList());
            }

            public int getSharedImageTransformsCount() {
                return ((SharingStats) this.instance).getSharedImageTransformsCount();
            }

            public SharedImageTransform getSharedImageTransforms(int index) {
                return ((SharingStats) this.instance).getSharedImageTransforms(index);
            }

            public Builder setSharedImageTransforms(int index, SharedImageTransform value) {
                copyOnWrite();
                ((SharingStats) this.instance).setSharedImageTransforms(index, value);
                return this;
            }

            public Builder setSharedImageTransforms(int index, SharedImageTransform.Builder builderForValue) {
                copyOnWrite();
                ((SharingStats) this.instance).setSharedImageTransforms(index, builderForValue);
                return this;
            }

            public Builder addSharedImageTransforms(SharedImageTransform value) {
                copyOnWrite();
                ((SharingStats) this.instance).addSharedImageTransforms(value);
                return this;
            }

            public Builder addSharedImageTransforms(int index, SharedImageTransform value) {
                copyOnWrite();
                ((SharingStats) this.instance).addSharedImageTransforms(index, value);
                return this;
            }

            public Builder addSharedImageTransforms(SharedImageTransform.Builder builderForValue) {
                copyOnWrite();
                ((SharingStats) this.instance).addSharedImageTransforms(builderForValue);
                return this;
            }

            public Builder addSharedImageTransforms(int index, SharedImageTransform.Builder builderForValue) {
                copyOnWrite();
                ((SharingStats) this.instance).addSharedImageTransforms(index, builderForValue);
                return this;
            }

            public Builder addAllSharedImageTransforms(Iterable<? extends SharedImageTransform> values) {
                copyOnWrite();
                ((SharingStats) this.instance).addAllSharedImageTransforms(values);
                return this;
            }

            public Builder clearSharedImageTransforms() {
                copyOnWrite();
                ((SharingStats) this.instance).clearSharedImageTransforms();
                return this;
            }

            public Builder removeSharedImageTransforms(int index) {
                copyOnWrite();
                ((SharingStats) this.instance).removeSharedImageTransforms(index);
                return this;
            }

            public boolean hasNumSharingApps() {
                return ((SharingStats) this.instance).hasNumSharingApps();
            }

            public int getNumSharingApps() {
                return ((SharingStats) this.instance).getNumSharingApps();
            }

            public Builder setNumSharingApps(int value) {
                copyOnWrite();
                ((SharingStats) this.instance).setNumSharingApps(value);
                return this;
            }

            public Builder clearNumSharingApps() {
                copyOnWrite();
                ((SharingStats) this.instance).clearNumSharingApps();
                return this;
            }

            public boolean hasNumCollections() {
                return ((SharingStats) this.instance).hasNumCollections();
            }

            public int getNumCollections() {
                return ((SharingStats) this.instance).getNumCollections();
            }

            public Builder setNumCollections(int value) {
                copyOnWrite();
                ((SharingStats) this.instance).setNumCollections(value);
                return this;
            }

            public Builder clearNumCollections() {
                copyOnWrite();
                ((SharingStats) this.instance).clearNumCollections();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SharingStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001\u0004\u0000\u0002\u001b\u0003\u0004\u0001\u0004\u0004\u0002", new Object[]{"bitField0_", "numItemsCollection_", "sharedImageTransforms_", SharedImageTransform.class, "numSharingApps_", "numCollections_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SharingStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (SharingStats.class) {
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
            GeneratedMessageLite.registerDefaultInstance(SharingStats.class, DEFAULT_INSTANCE);
        }

        public static SharingStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SharingStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class BackgroundTaskStats extends GeneratedMessageLite<BackgroundTaskStats, Builder> implements BackgroundTaskStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final BackgroundTaskStats DEFAULT_INSTANCE = new BackgroundTaskStats();
        public static final int IS_BACKGROUND_SERVICE_RUNNING_ALREADY_FIELD_NUMBER = 2;
        public static final int IS_PHOTOS_IN_FOREGROUND_FIELD_NUMBER = 1;
        private static volatile Parser<BackgroundTaskStats> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean isBackgroundServiceRunningAlready_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean isPhotosInForeground_;

        private BackgroundTaskStats() {
        }

        public boolean hasIsPhotosInForeground() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getIsPhotosInForeground() {
            return this.isPhotosInForeground_;
        }

        /* access modifiers changed from: private */
        public void setIsPhotosInForeground(boolean value) {
            this.bitField0_ |= 1;
            this.isPhotosInForeground_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsPhotosInForeground() {
            this.bitField0_ &= -2;
            this.isPhotosInForeground_ = false;
        }

        public boolean hasIsBackgroundServiceRunningAlready() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getIsBackgroundServiceRunningAlready() {
            return this.isBackgroundServiceRunningAlready_;
        }

        /* access modifiers changed from: private */
        public void setIsBackgroundServiceRunningAlready(boolean value) {
            this.bitField0_ |= 2;
            this.isBackgroundServiceRunningAlready_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsBackgroundServiceRunningAlready() {
            this.bitField0_ &= -3;
            this.isBackgroundServiceRunningAlready_ = false;
        }

        public static BackgroundTaskStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BackgroundTaskStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BackgroundTaskStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BackgroundTaskStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BackgroundTaskStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BackgroundTaskStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BackgroundTaskStats parseFrom(InputStream input) throws IOException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BackgroundTaskStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BackgroundTaskStats parseDelimitedFrom(InputStream input) throws IOException {
            return (BackgroundTaskStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static BackgroundTaskStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BackgroundTaskStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BackgroundTaskStats parseFrom(CodedInputStream input) throws IOException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BackgroundTaskStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BackgroundTaskStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(BackgroundTaskStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<BackgroundTaskStats, Builder> implements BackgroundTaskStatsOrBuilder {
            private Builder() {
                super(BackgroundTaskStats.DEFAULT_INSTANCE);
            }

            public boolean hasIsPhotosInForeground() {
                return ((BackgroundTaskStats) this.instance).hasIsPhotosInForeground();
            }

            public boolean getIsPhotosInForeground() {
                return ((BackgroundTaskStats) this.instance).getIsPhotosInForeground();
            }

            public Builder setIsPhotosInForeground(boolean value) {
                copyOnWrite();
                ((BackgroundTaskStats) this.instance).setIsPhotosInForeground(value);
                return this;
            }

            public Builder clearIsPhotosInForeground() {
                copyOnWrite();
                ((BackgroundTaskStats) this.instance).clearIsPhotosInForeground();
                return this;
            }

            public boolean hasIsBackgroundServiceRunningAlready() {
                return ((BackgroundTaskStats) this.instance).hasIsBackgroundServiceRunningAlready();
            }

            public boolean getIsBackgroundServiceRunningAlready() {
                return ((BackgroundTaskStats) this.instance).getIsBackgroundServiceRunningAlready();
            }

            public Builder setIsBackgroundServiceRunningAlready(boolean value) {
                copyOnWrite();
                ((BackgroundTaskStats) this.instance).setIsBackgroundServiceRunningAlready(value);
                return this;
            }

            public Builder clearIsBackgroundServiceRunningAlready() {
                copyOnWrite();
                ((BackgroundTaskStats) this.instance).clearIsBackgroundServiceRunningAlready();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new BackgroundTaskStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0007\u0000\u0002\u0007\u0001", new Object[]{"bitField0_", "isPhotosInForeground_", "isBackgroundServiceRunningAlready_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<BackgroundTaskStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (BackgroundTaskStats.class) {
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
            GeneratedMessageLite.registerDefaultInstance(BackgroundTaskStats.class, DEFAULT_INSTANCE);
        }

        public static BackgroundTaskStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BackgroundTaskStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SharedImageTransform extends GeneratedMessageLite<SharedImageTransform, Builder> implements SharedImageTransformOrBuilder {
        /* access modifiers changed from: private */
        public static final SharedImageTransform DEFAULT_INSTANCE = new SharedImageTransform();
        private static volatile Parser<SharedImageTransform> PARSER = null;
        public static final int RESIZED_BY_TYPE_FIELD_NUMBER = 2;
        public static final int SHARED_IMAGE_METADATAS_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int resizedByType_;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<SharedImageMetadata> sharedImageMetadatas_ = emptyProtobufList();

        private SharedImageTransform() {
        }

        public enum ResizedByType implements Internal.EnumLite {
            FIFE(0),
            LOCAL(1);
            
            public static final int FIFE_VALUE = 0;
            public static final int LOCAL_VALUE = 1;
            private static final Internal.EnumLiteMap<ResizedByType> internalValueMap = new Internal.EnumLiteMap<ResizedByType>() {
                public ResizedByType findValueByNumber(int number) {
                    return ResizedByType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static ResizedByType forNumber(int value2) {
                if (value2 == 0) {
                    return FIFE;
                }
                if (value2 != 1) {
                    return null;
                }
                return LOCAL;
            }

            public static Internal.EnumLiteMap<ResizedByType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return ResizedByTypeVerifier.INSTANCE;
            }

            private static final class ResizedByTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new ResizedByTypeVerifier();

                private ResizedByTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return ResizedByType.forNumber(number) != null;
                }
            }

            private ResizedByType(int value2) {
                this.value = value2;
            }
        }

        public List<SharedImageMetadata> getSharedImageMetadatasList() {
            return this.sharedImageMetadatas_;
        }

        public List<? extends SharedImageMetadataOrBuilder> getSharedImageMetadatasOrBuilderList() {
            return this.sharedImageMetadatas_;
        }

        public int getSharedImageMetadatasCount() {
            return this.sharedImageMetadatas_.size();
        }

        public SharedImageMetadata getSharedImageMetadatas(int index) {
            return this.sharedImageMetadatas_.get(index);
        }

        public SharedImageMetadataOrBuilder getSharedImageMetadatasOrBuilder(int index) {
            return this.sharedImageMetadatas_.get(index);
        }

        private void ensureSharedImageMetadatasIsMutable() {
            if (!this.sharedImageMetadatas_.isModifiable()) {
                this.sharedImageMetadatas_ = GeneratedMessageLite.mutableCopy(this.sharedImageMetadatas_);
            }
        }

        /* access modifiers changed from: private */
        public void setSharedImageMetadatas(int index, SharedImageMetadata value) {
            if (value != null) {
                ensureSharedImageMetadatasIsMutable();
                this.sharedImageMetadatas_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSharedImageMetadatas(int index, SharedImageMetadata.Builder builderForValue) {
            ensureSharedImageMetadatasIsMutable();
            this.sharedImageMetadatas_.set(index, (SharedImageMetadata) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSharedImageMetadatas(SharedImageMetadata value) {
            if (value != null) {
                ensureSharedImageMetadatasIsMutable();
                this.sharedImageMetadatas_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSharedImageMetadatas(int index, SharedImageMetadata value) {
            if (value != null) {
                ensureSharedImageMetadatasIsMutable();
                this.sharedImageMetadatas_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSharedImageMetadatas(SharedImageMetadata.Builder builderForValue) {
            ensureSharedImageMetadatasIsMutable();
            this.sharedImageMetadatas_.add((SharedImageMetadata) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSharedImageMetadatas(int index, SharedImageMetadata.Builder builderForValue) {
            ensureSharedImageMetadatasIsMutable();
            this.sharedImageMetadatas_.add(index, (SharedImageMetadata) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.ExtensionPhotos$SharedImageMetadata>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.ExtensionPhotos$SharedImageMetadata>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSharedImageMetadatas(Iterable<? extends SharedImageMetadata> values) {
            ensureSharedImageMetadatasIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.sharedImageMetadatas_);
        }

        /* access modifiers changed from: private */
        public void clearSharedImageMetadatas() {
            this.sharedImageMetadatas_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSharedImageMetadatas(int index) {
            ensureSharedImageMetadatasIsMutable();
            this.sharedImageMetadatas_.remove(index);
        }

        public boolean hasResizedByType() {
            return (this.bitField0_ & 1) != 0;
        }

        public ResizedByType getResizedByType() {
            ResizedByType result = ResizedByType.forNumber(this.resizedByType_);
            return result == null ? ResizedByType.FIFE : result;
        }

        /* access modifiers changed from: private */
        public void setResizedByType(ResizedByType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.resizedByType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearResizedByType() {
            this.bitField0_ &= -2;
            this.resizedByType_ = 0;
        }

        public static SharedImageTransform parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharedImageTransform parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharedImageTransform parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharedImageTransform parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharedImageTransform parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharedImageTransform parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharedImageTransform parseFrom(InputStream input) throws IOException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SharedImageTransform parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SharedImageTransform parseDelimitedFrom(InputStream input) throws IOException {
            return (SharedImageTransform) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SharedImageTransform parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharedImageTransform) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SharedImageTransform parseFrom(CodedInputStream input) throws IOException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SharedImageTransform parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharedImageTransform) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SharedImageTransform prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SharedImageTransform, Builder> implements SharedImageTransformOrBuilder {
            private Builder() {
                super(SharedImageTransform.DEFAULT_INSTANCE);
            }

            public List<SharedImageMetadata> getSharedImageMetadatasList() {
                return Collections.unmodifiableList(((SharedImageTransform) this.instance).getSharedImageMetadatasList());
            }

            public int getSharedImageMetadatasCount() {
                return ((SharedImageTransform) this.instance).getSharedImageMetadatasCount();
            }

            public SharedImageMetadata getSharedImageMetadatas(int index) {
                return ((SharedImageTransform) this.instance).getSharedImageMetadatas(index);
            }

            public Builder setSharedImageMetadatas(int index, SharedImageMetadata value) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).setSharedImageMetadatas(index, value);
                return this;
            }

            public Builder setSharedImageMetadatas(int index, SharedImageMetadata.Builder builderForValue) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).setSharedImageMetadatas(index, builderForValue);
                return this;
            }

            public Builder addSharedImageMetadatas(SharedImageMetadata value) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).addSharedImageMetadatas(value);
                return this;
            }

            public Builder addSharedImageMetadatas(int index, SharedImageMetadata value) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).addSharedImageMetadatas(index, value);
                return this;
            }

            public Builder addSharedImageMetadatas(SharedImageMetadata.Builder builderForValue) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).addSharedImageMetadatas(builderForValue);
                return this;
            }

            public Builder addSharedImageMetadatas(int index, SharedImageMetadata.Builder builderForValue) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).addSharedImageMetadatas(index, builderForValue);
                return this;
            }

            public Builder addAllSharedImageMetadatas(Iterable<? extends SharedImageMetadata> values) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).addAllSharedImageMetadatas(values);
                return this;
            }

            public Builder clearSharedImageMetadatas() {
                copyOnWrite();
                ((SharedImageTransform) this.instance).clearSharedImageMetadatas();
                return this;
            }

            public Builder removeSharedImageMetadatas(int index) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).removeSharedImageMetadatas(index);
                return this;
            }

            public boolean hasResizedByType() {
                return ((SharedImageTransform) this.instance).hasResizedByType();
            }

            public ResizedByType getResizedByType() {
                return ((SharedImageTransform) this.instance).getResizedByType();
            }

            public Builder setResizedByType(ResizedByType value) {
                copyOnWrite();
                ((SharedImageTransform) this.instance).setResizedByType(value);
                return this;
            }

            public Builder clearResizedByType() {
                copyOnWrite();
                ((SharedImageTransform) this.instance).clearResizedByType();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SharedImageTransform();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u001b\u0002\f\u0000", new Object[]{"bitField0_", "sharedImageMetadatas_", SharedImageMetadata.class, "resizedByType_", ResizedByType.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SharedImageTransform> parser = PARSER;
                    if (parser == null) {
                        synchronized (SharedImageTransform.class) {
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
            GeneratedMessageLite.registerDefaultInstance(SharedImageTransform.class, DEFAULT_INSTANCE);
        }

        public static SharedImageTransform getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SharedImageTransform> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SharedImageMetadata extends GeneratedMessageLite<SharedImageMetadata, Builder> implements SharedImageMetadataOrBuilder {
        /* access modifiers changed from: private */
        public static final SharedImageMetadata DEFAULT_INSTANCE = new SharedImageMetadata();
        public static final int HEIGHT_FIELD_NUMBER = 1;
        private static volatile Parser<SharedImageMetadata> PARSER = null;
        public static final int SIZE_IN_BYTES_FIELD_NUMBER = 3;
        public static final int WIDTH_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int height_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long sizeInBytes_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int width_;

        private SharedImageMetadata() {
        }

        public boolean hasHeight() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getHeight() {
            return this.height_;
        }

        /* access modifiers changed from: private */
        public void setHeight(int value) {
            this.bitField0_ |= 1;
            this.height_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHeight() {
            this.bitField0_ &= -2;
            this.height_ = 0;
        }

        public boolean hasWidth() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getWidth() {
            return this.width_;
        }

        /* access modifiers changed from: private */
        public void setWidth(int value) {
            this.bitField0_ |= 2;
            this.width_ = value;
        }

        /* access modifiers changed from: private */
        public void clearWidth() {
            this.bitField0_ &= -3;
            this.width_ = 0;
        }

        public boolean hasSizeInBytes() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getSizeInBytes() {
            return this.sizeInBytes_;
        }

        /* access modifiers changed from: private */
        public void setSizeInBytes(long value) {
            this.bitField0_ |= 4;
            this.sizeInBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSizeInBytes() {
            this.bitField0_ &= -5;
            this.sizeInBytes_ = 0;
        }

        public static SharedImageMetadata parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharedImageMetadata parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharedImageMetadata parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharedImageMetadata parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharedImageMetadata parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SharedImageMetadata parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SharedImageMetadata parseFrom(InputStream input) throws IOException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SharedImageMetadata parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SharedImageMetadata parseDelimitedFrom(InputStream input) throws IOException {
            return (SharedImageMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SharedImageMetadata parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharedImageMetadata) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SharedImageMetadata parseFrom(CodedInputStream input) throws IOException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SharedImageMetadata parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SharedImageMetadata) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SharedImageMetadata prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SharedImageMetadata, Builder> implements SharedImageMetadataOrBuilder {
            private Builder() {
                super(SharedImageMetadata.DEFAULT_INSTANCE);
            }

            public boolean hasHeight() {
                return ((SharedImageMetadata) this.instance).hasHeight();
            }

            public int getHeight() {
                return ((SharedImageMetadata) this.instance).getHeight();
            }

            public Builder setHeight(int value) {
                copyOnWrite();
                ((SharedImageMetadata) this.instance).setHeight(value);
                return this;
            }

            public Builder clearHeight() {
                copyOnWrite();
                ((SharedImageMetadata) this.instance).clearHeight();
                return this;
            }

            public boolean hasWidth() {
                return ((SharedImageMetadata) this.instance).hasWidth();
            }

            public int getWidth() {
                return ((SharedImageMetadata) this.instance).getWidth();
            }

            public Builder setWidth(int value) {
                copyOnWrite();
                ((SharedImageMetadata) this.instance).setWidth(value);
                return this;
            }

            public Builder clearWidth() {
                copyOnWrite();
                ((SharedImageMetadata) this.instance).clearWidth();
                return this;
            }

            public boolean hasSizeInBytes() {
                return ((SharedImageMetadata) this.instance).hasSizeInBytes();
            }

            public long getSizeInBytes() {
                return ((SharedImageMetadata) this.instance).getSizeInBytes();
            }

            public Builder setSizeInBytes(long value) {
                copyOnWrite();
                ((SharedImageMetadata) this.instance).setSizeInBytes(value);
                return this;
            }

            public Builder clearSizeInBytes() {
                copyOnWrite();
                ((SharedImageMetadata) this.instance).clearSizeInBytes();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SharedImageMetadata();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0002\u0002", new Object[]{"bitField0_", "height_", "width_", "sizeInBytes_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SharedImageMetadata> parser = PARSER;
                    if (parser == null) {
                        synchronized (SharedImageMetadata.class) {
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
            GeneratedMessageLite.registerDefaultInstance(SharedImageMetadata.class, DEFAULT_INSTANCE);
        }

        public static SharedImageMetadata getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SharedImageMetadata> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CameraAssistantStats extends GeneratedMessageLite<CameraAssistantStats, Builder> implements CameraAssistantStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final CameraAssistantStats DEFAULT_INSTANCE = new CameraAssistantStats();
        public static final int IS_ACTION_UNFINISHED_FIELD_NUMBER = 1;
        private static volatile Parser<CameraAssistantStats> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean isActionUnfinished_;

        private CameraAssistantStats() {
        }

        public boolean hasIsActionUnfinished() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getIsActionUnfinished() {
            return this.isActionUnfinished_;
        }

        /* access modifiers changed from: private */
        public void setIsActionUnfinished(boolean value) {
            this.bitField0_ |= 1;
            this.isActionUnfinished_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsActionUnfinished() {
            this.bitField0_ &= -2;
            this.isActionUnfinished_ = false;
        }

        public static CameraAssistantStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CameraAssistantStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CameraAssistantStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CameraAssistantStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CameraAssistantStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CameraAssistantStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CameraAssistantStats parseFrom(InputStream input) throws IOException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CameraAssistantStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CameraAssistantStats parseDelimitedFrom(InputStream input) throws IOException {
            return (CameraAssistantStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CameraAssistantStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CameraAssistantStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CameraAssistantStats parseFrom(CodedInputStream input) throws IOException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CameraAssistantStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CameraAssistantStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CameraAssistantStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<CameraAssistantStats, Builder> implements CameraAssistantStatsOrBuilder {
            private Builder() {
                super(CameraAssistantStats.DEFAULT_INSTANCE);
            }

            public boolean hasIsActionUnfinished() {
                return ((CameraAssistantStats) this.instance).hasIsActionUnfinished();
            }

            public boolean getIsActionUnfinished() {
                return ((CameraAssistantStats) this.instance).getIsActionUnfinished();
            }

            public Builder setIsActionUnfinished(boolean value) {
                copyOnWrite();
                ((CameraAssistantStats) this.instance).setIsActionUnfinished(value);
                return this;
            }

            public Builder clearIsActionUnfinished() {
                copyOnWrite();
                ((CameraAssistantStats) this.instance).clearIsActionUnfinished();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CameraAssistantStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0007\u0000", new Object[]{"bitField0_", "isActionUnfinished_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CameraAssistantStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (CameraAssistantStats.class) {
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
            GeneratedMessageLite.registerDefaultInstance(CameraAssistantStats.class, DEFAULT_INSTANCE);
        }

        public static CameraAssistantStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CameraAssistantStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class VideoPlayerStats extends GeneratedMessageLite<VideoPlayerStats, Builder> implements VideoPlayerStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final VideoPlayerStats DEFAULT_INSTANCE = new VideoPlayerStats();
        public static final int MEDIA_TYPE_FIELD_NUMBER = 4;
        private static volatile Parser<VideoPlayerStats> PARSER = null;
        public static final int PLAYER_FIELD_NUMBER = 1;
        public static final int RETRY_COUNT_FIELD_NUMBER = 2;
        public static final int VIDEO_DURATION_SECONDS_FIELD_NUMBER = 3;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int mediaType_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int player_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int retryCount_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long videoDurationSeconds_;

        private VideoPlayerStats() {
        }

        public enum Player implements Internal.EnumLite {
            UNKNOWN_PLAYER(0),
            FRAMEWORK_MEDIA_PLAYER(1),
            EXO_PLAYER_V2(2);
            
            public static final int EXO_PLAYER_V2_VALUE = 2;
            public static final int FRAMEWORK_MEDIA_PLAYER_VALUE = 1;
            public static final int UNKNOWN_PLAYER_VALUE = 0;
            private static final Internal.EnumLiteMap<Player> internalValueMap = new Internal.EnumLiteMap<Player>() {
                public Player findValueByNumber(int number) {
                    return Player.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static Player forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN_PLAYER;
                }
                if (value2 == 1) {
                    return FRAMEWORK_MEDIA_PLAYER;
                }
                if (value2 != 2) {
                    return null;
                }
                return EXO_PLAYER_V2;
            }

            public static Internal.EnumLiteMap<Player> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return PlayerVerifier.INSTANCE;
            }

            private static final class PlayerVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new PlayerVerifier();

                private PlayerVerifier() {
                }

                public boolean isInRange(int number) {
                    return Player.forNumber(number) != null;
                }
            }

            private Player(int value2) {
                this.value = value2;
            }
        }

        public enum MediaType implements Internal.EnumLite {
            UNKNOWN_MEDIA_TYPE(0),
            VIDEO(1),
            MICRO_VIDEO(2);
            
            public static final int MICRO_VIDEO_VALUE = 2;
            public static final int UNKNOWN_MEDIA_TYPE_VALUE = 0;
            public static final int VIDEO_VALUE = 1;
            private static final Internal.EnumLiteMap<MediaType> internalValueMap = new Internal.EnumLiteMap<MediaType>() {
                public MediaType findValueByNumber(int number) {
                    return MediaType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static MediaType forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN_MEDIA_TYPE;
                }
                if (value2 == 1) {
                    return VIDEO;
                }
                if (value2 != 2) {
                    return null;
                }
                return MICRO_VIDEO;
            }

            public static Internal.EnumLiteMap<MediaType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return MediaTypeVerifier.INSTANCE;
            }

            private static final class MediaTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new MediaTypeVerifier();

                private MediaTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return MediaType.forNumber(number) != null;
                }
            }

            private MediaType(int value2) {
                this.value = value2;
            }
        }

        public boolean hasPlayer() {
            return (this.bitField0_ & 1) != 0;
        }

        public Player getPlayer() {
            Player result = Player.forNumber(this.player_);
            return result == null ? Player.UNKNOWN_PLAYER : result;
        }

        /* access modifiers changed from: private */
        public void setPlayer(Player value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.player_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPlayer() {
            this.bitField0_ &= -2;
            this.player_ = 0;
        }

        public boolean hasRetryCount() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getRetryCount() {
            return this.retryCount_;
        }

        /* access modifiers changed from: private */
        public void setRetryCount(int value) {
            this.bitField0_ |= 2;
            this.retryCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRetryCount() {
            this.bitField0_ &= -3;
            this.retryCount_ = 0;
        }

        public boolean hasVideoDurationSeconds() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getVideoDurationSeconds() {
            return this.videoDurationSeconds_;
        }

        /* access modifiers changed from: private */
        public void setVideoDurationSeconds(long value) {
            this.bitField0_ |= 4;
            this.videoDurationSeconds_ = value;
        }

        /* access modifiers changed from: private */
        public void clearVideoDurationSeconds() {
            this.bitField0_ &= -5;
            this.videoDurationSeconds_ = 0;
        }

        public boolean hasMediaType() {
            return (this.bitField0_ & 8) != 0;
        }

        public MediaType getMediaType() {
            MediaType result = MediaType.forNumber(this.mediaType_);
            return result == null ? MediaType.UNKNOWN_MEDIA_TYPE : result;
        }

        /* access modifiers changed from: private */
        public void setMediaType(MediaType value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.mediaType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMediaType() {
            this.bitField0_ &= -9;
            this.mediaType_ = 0;
        }

        public static VideoPlayerStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VideoPlayerStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VideoPlayerStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VideoPlayerStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VideoPlayerStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static VideoPlayerStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static VideoPlayerStats parseFrom(InputStream input) throws IOException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static VideoPlayerStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static VideoPlayerStats parseDelimitedFrom(InputStream input) throws IOException {
            return (VideoPlayerStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static VideoPlayerStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VideoPlayerStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static VideoPlayerStats parseFrom(CodedInputStream input) throws IOException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static VideoPlayerStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (VideoPlayerStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(VideoPlayerStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<VideoPlayerStats, Builder> implements VideoPlayerStatsOrBuilder {
            private Builder() {
                super(VideoPlayerStats.DEFAULT_INSTANCE);
            }

            public boolean hasPlayer() {
                return ((VideoPlayerStats) this.instance).hasPlayer();
            }

            public Player getPlayer() {
                return ((VideoPlayerStats) this.instance).getPlayer();
            }

            public Builder setPlayer(Player value) {
                copyOnWrite();
                ((VideoPlayerStats) this.instance).setPlayer(value);
                return this;
            }

            public Builder clearPlayer() {
                copyOnWrite();
                ((VideoPlayerStats) this.instance).clearPlayer();
                return this;
            }

            public boolean hasRetryCount() {
                return ((VideoPlayerStats) this.instance).hasRetryCount();
            }

            public int getRetryCount() {
                return ((VideoPlayerStats) this.instance).getRetryCount();
            }

            public Builder setRetryCount(int value) {
                copyOnWrite();
                ((VideoPlayerStats) this.instance).setRetryCount(value);
                return this;
            }

            public Builder clearRetryCount() {
                copyOnWrite();
                ((VideoPlayerStats) this.instance).clearRetryCount();
                return this;
            }

            public boolean hasVideoDurationSeconds() {
                return ((VideoPlayerStats) this.instance).hasVideoDurationSeconds();
            }

            public long getVideoDurationSeconds() {
                return ((VideoPlayerStats) this.instance).getVideoDurationSeconds();
            }

            public Builder setVideoDurationSeconds(long value) {
                copyOnWrite();
                ((VideoPlayerStats) this.instance).setVideoDurationSeconds(value);
                return this;
            }

            public Builder clearVideoDurationSeconds() {
                copyOnWrite();
                ((VideoPlayerStats) this.instance).clearVideoDurationSeconds();
                return this;
            }

            public boolean hasMediaType() {
                return ((VideoPlayerStats) this.instance).hasMediaType();
            }

            public MediaType getMediaType() {
                return ((VideoPlayerStats) this.instance).getMediaType();
            }

            public Builder setMediaType(MediaType value) {
                copyOnWrite();
                ((VideoPlayerStats) this.instance).setMediaType(value);
                return this;
            }

            public Builder clearMediaType() {
                copyOnWrite();
                ((VideoPlayerStats) this.instance).clearMediaType();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new VideoPlayerStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\u0002\u0002\u0004\f\u0003", new Object[]{"bitField0_", "player_", Player.internalGetVerifier(), "retryCount_", "videoDurationSeconds_", "mediaType_", MediaType.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<VideoPlayerStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (VideoPlayerStats.class) {
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
            GeneratedMessageLite.registerDefaultInstance(VideoPlayerStats.class, DEFAULT_INSTANCE);
        }

        public static VideoPlayerStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<VideoPlayerStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class FingerprintStats extends GeneratedMessageLite<FingerprintStats, Builder> implements FingerprintStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final FingerprintStats DEFAULT_INSTANCE = new FingerprintStats();
        public static final int FILE_SIZE_KB_FIELD_NUMBER = 1;
        private static volatile Parser<FingerprintStats> PARSER;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long fileSizeKb_;

        private FingerprintStats() {
        }

        public boolean hasFileSizeKb() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getFileSizeKb() {
            return this.fileSizeKb_;
        }

        /* access modifiers changed from: private */
        public void setFileSizeKb(long value) {
            this.bitField0_ |= 1;
            this.fileSizeKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearFileSizeKb() {
            this.bitField0_ &= -2;
            this.fileSizeKb_ = 0;
        }

        public static FingerprintStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FingerprintStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FingerprintStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FingerprintStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FingerprintStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static FingerprintStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static FingerprintStats parseFrom(InputStream input) throws IOException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FingerprintStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FingerprintStats parseDelimitedFrom(InputStream input) throws IOException {
            return (FingerprintStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static FingerprintStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FingerprintStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static FingerprintStats parseFrom(CodedInputStream input) throws IOException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static FingerprintStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FingerprintStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(FingerprintStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<FingerprintStats, Builder> implements FingerprintStatsOrBuilder {
            private Builder() {
                super(FingerprintStats.DEFAULT_INSTANCE);
            }

            public boolean hasFileSizeKb() {
                return ((FingerprintStats) this.instance).hasFileSizeKb();
            }

            public long getFileSizeKb() {
                return ((FingerprintStats) this.instance).getFileSizeKb();
            }

            public Builder setFileSizeKb(long value) {
                copyOnWrite();
                ((FingerprintStats) this.instance).setFileSizeKb(value);
                return this;
            }

            public Builder clearFileSizeKb() {
                copyOnWrite();
                ((FingerprintStats) this.instance).clearFileSizeKb();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new FingerprintStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\u0002\u0000", new Object[]{"bitField0_", "fileSizeKb_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<FingerprintStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (FingerprintStats.class) {
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
            GeneratedMessageLite.registerDefaultInstance(FingerprintStats.class, DEFAULT_INSTANCE);
        }

        public static FingerprintStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<FingerprintStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
