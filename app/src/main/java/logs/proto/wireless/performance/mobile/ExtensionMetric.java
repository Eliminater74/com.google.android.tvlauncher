package logs.proto.wireless.performance.mobile;

import com.google.android.gms.framework.logging.proto.GcoreDimensions;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.FieldType;
import com.google.protobuf.GeneratedMessageLite;
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
import logs.proto.wireless.performance.mobile.ExtensionBugle;
import logs.proto.wireless.performance.mobile.ExtensionCalendar;
import logs.proto.wireless.performance.mobile.ExtensionCloudDpc;
import logs.proto.wireless.performance.mobile.ExtensionCsapk;
import logs.proto.wireless.performance.mobile.ExtensionGcs;
import logs.proto.wireless.performance.mobile.ExtensionGmail;
import logs.proto.wireless.performance.mobile.ExtensionGmm;
import logs.proto.wireless.performance.mobile.ExtensionPhotos;
import logs.proto.wireless.performance.mobile.ExtensionPhotosScanner;
import logs.proto.wireless.performance.mobile.ExtensionTranslate;
import logs.proto.wireless.performance.mobile.ExtensionYoutube;

public final class ExtensionMetric {

    public interface MetricExtensionOrBuilder extends MessageLiteOrBuilder {
        ExtensionBugle.BugleExtension getBugleExtension();

        ExtensionCalendar.CalendarExtension getCalendarExtension();

        ExtensionCloudDpc.CloudDpcExtension getCloudDpcExtension();

        ExtensionCsapk.CsapkExtension getCsapkExtension();

        GcoreDimensions.GCoreDimensions getGcoreDimension();

        ExtensionGcs.GcsExtension getGcsExtension();

        ExtensionGmail.GmailExtension getGmailExtension();

        ExtensionGmm.GmmExtension getGmmExtension();

        ExtensionPhotos.PhotosExtension getPhotosExtension();

        ExtensionPhotosScanner.PhotosScannerExtension getPhotosScannerExtension();

        ExtensionTranslate.TranslateExtension getTranslateExtension();

        ExtensionYoutube.YouTubeExtension getYoutubeExtension();

        boolean hasBugleExtension();

        boolean hasCalendarExtension();

        boolean hasCloudDpcExtension();

        boolean hasCsapkExtension();

        boolean hasGcoreDimension();

        boolean hasGcsExtension();

        boolean hasGmailExtension();

        boolean hasGmmExtension();

        boolean hasPhotosExtension();

        boolean hasPhotosScannerExtension();

        boolean hasTranslateExtension();

        boolean hasYoutubeExtension();
    }

    private ExtensionMetric() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class MetricExtension extends GeneratedMessageLite<MetricExtension, Builder> implements MetricExtensionOrBuilder {
        public static final int BUGLE_EXTENSION_FIELD_NUMBER = 11;
        public static final int CALENDAR_EXTENSION_FIELD_NUMBER = 4;
        public static final int CLOUD_DPC_EXTENSION_FIELD_NUMBER = 7;
        public static final int CSAPK_EXTENSION_FIELD_NUMBER = 12;
        /* access modifiers changed from: private */
        public static final MetricExtension DEFAULT_INSTANCE = new MetricExtension();
        public static final int GCORE_DIMENSION_FIELD_NUMBER = 8;
        public static final int GCS_EXTENSION_FIELD_NUMBER = 9;
        public static final int GMAIL_EXTENSION_FIELD_NUMBER = 1;
        public static final int GMM_EXTENSION_FIELD_NUMBER = 2;
        private static volatile Parser<MetricExtension> PARSER = null;
        public static final int PHOTOS_EXTENSION_FIELD_NUMBER = 5;
        public static final int PHOTOS_SCANNER_EXTENSION_FIELD_NUMBER = 3;
        public static final int TRANSLATE_EXTENSION_FIELD_NUMBER = 10;
        public static final int YOUTUBE_EXTENSION_FIELD_NUMBER = 6;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private ExtensionBugle.BugleExtension bugleExtension_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private ExtensionCalendar.CalendarExtension calendarExtension_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private ExtensionCloudDpc.CloudDpcExtension cloudDpcExtension_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2048, presenceBitsId = 0)
        private ExtensionCsapk.CsapkExtension csapkExtension_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private GcoreDimensions.GCoreDimensions gcoreDimension_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private ExtensionGcs.GcsExtension gcsExtension_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private ExtensionGmail.GmailExtension gmailExtension_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private ExtensionGmm.GmmExtension gmmExtension_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private ExtensionPhotos.PhotosExtension photosExtension_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private ExtensionPhotosScanner.PhotosScannerExtension photosScannerExtension_;
        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private ExtensionTranslate.TranslateExtension translateExtension_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private ExtensionYoutube.YouTubeExtension youtubeExtension_;

        private MetricExtension() {
        }

        public boolean hasGmailExtension() {
            return (this.bitField0_ & 1) != 0;
        }

        public ExtensionGmail.GmailExtension getGmailExtension() {
            ExtensionGmail.GmailExtension gmailExtension = this.gmailExtension_;
            return gmailExtension == null ? ExtensionGmail.GmailExtension.getDefaultInstance() : gmailExtension;
        }

        /* access modifiers changed from: private */
        public void setGmailExtension(ExtensionGmail.GmailExtension value) {
            if (value != null) {
                this.gmailExtension_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setGmailExtension(ExtensionGmail.GmailExtension.Builder builderForValue) {
            this.gmailExtension_ = (ExtensionGmail.GmailExtension) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeGmailExtension(ExtensionGmail.GmailExtension value) {
            if (value != null) {
                ExtensionGmail.GmailExtension gmailExtension = this.gmailExtension_;
                if (gmailExtension == null || gmailExtension == ExtensionGmail.GmailExtension.getDefaultInstance()) {
                    this.gmailExtension_ = value;
                } else {
                    this.gmailExtension_ = (ExtensionGmail.GmailExtension) ((ExtensionGmail.GmailExtension.Builder) ExtensionGmail.GmailExtension.newBuilder(this.gmailExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearGmailExtension() {
            this.gmailExtension_ = null;
            this.bitField0_ &= -2;
        }

        public boolean hasGmmExtension() {
            return (this.bitField0_ & 2) != 0;
        }

        public ExtensionGmm.GmmExtension getGmmExtension() {
            ExtensionGmm.GmmExtension gmmExtension = this.gmmExtension_;
            return gmmExtension == null ? ExtensionGmm.GmmExtension.getDefaultInstance() : gmmExtension;
        }

        /* access modifiers changed from: private */
        public void setGmmExtension(ExtensionGmm.GmmExtension value) {
            if (value != null) {
                this.gmmExtension_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setGmmExtension(ExtensionGmm.GmmExtension.Builder builderForValue) {
            this.gmmExtension_ = (ExtensionGmm.GmmExtension) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeGmmExtension(ExtensionGmm.GmmExtension value) {
            if (value != null) {
                ExtensionGmm.GmmExtension gmmExtension = this.gmmExtension_;
                if (gmmExtension == null || gmmExtension == ExtensionGmm.GmmExtension.getDefaultInstance()) {
                    this.gmmExtension_ = value;
                } else {
                    this.gmmExtension_ = (ExtensionGmm.GmmExtension) ((ExtensionGmm.GmmExtension.Builder) ExtensionGmm.GmmExtension.newBuilder(this.gmmExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearGmmExtension() {
            this.gmmExtension_ = null;
            this.bitField0_ &= -3;
        }

        public boolean hasPhotosScannerExtension() {
            return (this.bitField0_ & 4) != 0;
        }

        public ExtensionPhotosScanner.PhotosScannerExtension getPhotosScannerExtension() {
            ExtensionPhotosScanner.PhotosScannerExtension photosScannerExtension = this.photosScannerExtension_;
            return photosScannerExtension == null ? ExtensionPhotosScanner.PhotosScannerExtension.getDefaultInstance() : photosScannerExtension;
        }

        /* access modifiers changed from: private */
        public void setPhotosScannerExtension(ExtensionPhotosScanner.PhotosScannerExtension value) {
            if (value != null) {
                this.photosScannerExtension_ = value;
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPhotosScannerExtension(ExtensionPhotosScanner.PhotosScannerExtension.Builder builderForValue) {
            this.photosScannerExtension_ = (ExtensionPhotosScanner.PhotosScannerExtension) builderForValue.build();
            this.bitField0_ |= 4;
        }

        /* access modifiers changed from: private */
        public void mergePhotosScannerExtension(ExtensionPhotosScanner.PhotosScannerExtension value) {
            if (value != null) {
                ExtensionPhotosScanner.PhotosScannerExtension photosScannerExtension = this.photosScannerExtension_;
                if (photosScannerExtension == null || photosScannerExtension == ExtensionPhotosScanner.PhotosScannerExtension.getDefaultInstance()) {
                    this.photosScannerExtension_ = value;
                } else {
                    this.photosScannerExtension_ = (ExtensionPhotosScanner.PhotosScannerExtension) ((ExtensionPhotosScanner.PhotosScannerExtension.Builder) ExtensionPhotosScanner.PhotosScannerExtension.newBuilder(this.photosScannerExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 4;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPhotosScannerExtension() {
            this.photosScannerExtension_ = null;
            this.bitField0_ &= -5;
        }

        public boolean hasCalendarExtension() {
            return (this.bitField0_ & 8) != 0;
        }

        public ExtensionCalendar.CalendarExtension getCalendarExtension() {
            ExtensionCalendar.CalendarExtension calendarExtension = this.calendarExtension_;
            return calendarExtension == null ? ExtensionCalendar.CalendarExtension.getDefaultInstance() : calendarExtension;
        }

        /* access modifiers changed from: private */
        public void setCalendarExtension(ExtensionCalendar.CalendarExtension value) {
            if (value != null) {
                this.calendarExtension_ = value;
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCalendarExtension(ExtensionCalendar.CalendarExtension.Builder builderForValue) {
            this.calendarExtension_ = (ExtensionCalendar.CalendarExtension) builderForValue.build();
            this.bitField0_ |= 8;
        }

        /* access modifiers changed from: private */
        public void mergeCalendarExtension(ExtensionCalendar.CalendarExtension value) {
            if (value != null) {
                ExtensionCalendar.CalendarExtension calendarExtension = this.calendarExtension_;
                if (calendarExtension == null || calendarExtension == ExtensionCalendar.CalendarExtension.getDefaultInstance()) {
                    this.calendarExtension_ = value;
                } else {
                    this.calendarExtension_ = (ExtensionCalendar.CalendarExtension) ((ExtensionCalendar.CalendarExtension.Builder) ExtensionCalendar.CalendarExtension.newBuilder(this.calendarExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 8;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCalendarExtension() {
            this.calendarExtension_ = null;
            this.bitField0_ &= -9;
        }

        public boolean hasPhotosExtension() {
            return (this.bitField0_ & 16) != 0;
        }

        public ExtensionPhotos.PhotosExtension getPhotosExtension() {
            ExtensionPhotos.PhotosExtension photosExtension = this.photosExtension_;
            return photosExtension == null ? ExtensionPhotos.PhotosExtension.getDefaultInstance() : photosExtension;
        }

        /* access modifiers changed from: private */
        public void setPhotosExtension(ExtensionPhotos.PhotosExtension value) {
            if (value != null) {
                this.photosExtension_ = value;
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPhotosExtension(ExtensionPhotos.PhotosExtension.Builder builderForValue) {
            this.photosExtension_ = (ExtensionPhotos.PhotosExtension) builderForValue.build();
            this.bitField0_ |= 16;
        }

        /* access modifiers changed from: private */
        public void mergePhotosExtension(ExtensionPhotos.PhotosExtension value) {
            if (value != null) {
                ExtensionPhotos.PhotosExtension photosExtension = this.photosExtension_;
                if (photosExtension == null || photosExtension == ExtensionPhotos.PhotosExtension.getDefaultInstance()) {
                    this.photosExtension_ = value;
                } else {
                    this.photosExtension_ = (ExtensionPhotos.PhotosExtension) ((ExtensionPhotos.PhotosExtension.Builder) ExtensionPhotos.PhotosExtension.newBuilder(this.photosExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 16;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPhotosExtension() {
            this.photosExtension_ = null;
            this.bitField0_ &= -17;
        }

        public boolean hasYoutubeExtension() {
            return (this.bitField0_ & 32) != 0;
        }

        public ExtensionYoutube.YouTubeExtension getYoutubeExtension() {
            ExtensionYoutube.YouTubeExtension youTubeExtension = this.youtubeExtension_;
            return youTubeExtension == null ? ExtensionYoutube.YouTubeExtension.getDefaultInstance() : youTubeExtension;
        }

        /* access modifiers changed from: private */
        public void setYoutubeExtension(ExtensionYoutube.YouTubeExtension value) {
            if (value != null) {
                this.youtubeExtension_ = value;
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setYoutubeExtension(ExtensionYoutube.YouTubeExtension.Builder builderForValue) {
            this.youtubeExtension_ = (ExtensionYoutube.YouTubeExtension) builderForValue.build();
            this.bitField0_ |= 32;
        }

        /* access modifiers changed from: private */
        public void mergeYoutubeExtension(ExtensionYoutube.YouTubeExtension value) {
            if (value != null) {
                ExtensionYoutube.YouTubeExtension youTubeExtension = this.youtubeExtension_;
                if (youTubeExtension == null || youTubeExtension == ExtensionYoutube.YouTubeExtension.getDefaultInstance()) {
                    this.youtubeExtension_ = value;
                } else {
                    this.youtubeExtension_ = (ExtensionYoutube.YouTubeExtension) ((ExtensionYoutube.YouTubeExtension.Builder) ExtensionYoutube.YouTubeExtension.newBuilder(this.youtubeExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 32;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearYoutubeExtension() {
            this.youtubeExtension_ = null;
            this.bitField0_ &= -33;
        }

        public boolean hasCloudDpcExtension() {
            return (this.bitField0_ & 64) != 0;
        }

        public ExtensionCloudDpc.CloudDpcExtension getCloudDpcExtension() {
            ExtensionCloudDpc.CloudDpcExtension cloudDpcExtension = this.cloudDpcExtension_;
            return cloudDpcExtension == null ? ExtensionCloudDpc.CloudDpcExtension.getDefaultInstance() : cloudDpcExtension;
        }

        /* access modifiers changed from: private */
        public void setCloudDpcExtension(ExtensionCloudDpc.CloudDpcExtension value) {
            if (value != null) {
                this.cloudDpcExtension_ = value;
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCloudDpcExtension(ExtensionCloudDpc.CloudDpcExtension.Builder builderForValue) {
            this.cloudDpcExtension_ = (ExtensionCloudDpc.CloudDpcExtension) builderForValue.build();
            this.bitField0_ |= 64;
        }

        /* access modifiers changed from: private */
        public void mergeCloudDpcExtension(ExtensionCloudDpc.CloudDpcExtension value) {
            if (value != null) {
                ExtensionCloudDpc.CloudDpcExtension cloudDpcExtension = this.cloudDpcExtension_;
                if (cloudDpcExtension == null || cloudDpcExtension == ExtensionCloudDpc.CloudDpcExtension.getDefaultInstance()) {
                    this.cloudDpcExtension_ = value;
                } else {
                    this.cloudDpcExtension_ = (ExtensionCloudDpc.CloudDpcExtension) ((ExtensionCloudDpc.CloudDpcExtension.Builder) ExtensionCloudDpc.CloudDpcExtension.newBuilder(this.cloudDpcExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCloudDpcExtension() {
            this.cloudDpcExtension_ = null;
            this.bitField0_ &= -65;
        }

        public boolean hasGcoreDimension() {
            return (this.bitField0_ & 128) != 0;
        }

        public GcoreDimensions.GCoreDimensions getGcoreDimension() {
            GcoreDimensions.GCoreDimensions gCoreDimensions = this.gcoreDimension_;
            return gCoreDimensions == null ? GcoreDimensions.GCoreDimensions.getDefaultInstance() : gCoreDimensions;
        }

        /* access modifiers changed from: private */
        public void setGcoreDimension(GcoreDimensions.GCoreDimensions value) {
            if (value != null) {
                this.gcoreDimension_ = value;
                this.bitField0_ |= 128;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setGcoreDimension(GcoreDimensions.GCoreDimensions.Builder builderForValue) {
            this.gcoreDimension_ = (GcoreDimensions.GCoreDimensions) builderForValue.build();
            this.bitField0_ |= 128;
        }

        /* access modifiers changed from: private */
        public void mergeGcoreDimension(GcoreDimensions.GCoreDimensions value) {
            if (value != null) {
                GcoreDimensions.GCoreDimensions gCoreDimensions = this.gcoreDimension_;
                if (gCoreDimensions == null || gCoreDimensions == GcoreDimensions.GCoreDimensions.getDefaultInstance()) {
                    this.gcoreDimension_ = value;
                } else {
                    this.gcoreDimension_ = (GcoreDimensions.GCoreDimensions) ((GcoreDimensions.GCoreDimensions.Builder) GcoreDimensions.GCoreDimensions.newBuilder(this.gcoreDimension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 128;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearGcoreDimension() {
            this.gcoreDimension_ = null;
            this.bitField0_ &= -129;
        }

        public boolean hasGcsExtension() {
            return (this.bitField0_ & 256) != 0;
        }

        public ExtensionGcs.GcsExtension getGcsExtension() {
            ExtensionGcs.GcsExtension gcsExtension = this.gcsExtension_;
            return gcsExtension == null ? ExtensionGcs.GcsExtension.getDefaultInstance() : gcsExtension;
        }

        /* access modifiers changed from: private */
        public void setGcsExtension(ExtensionGcs.GcsExtension value) {
            if (value != null) {
                this.gcsExtension_ = value;
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setGcsExtension(ExtensionGcs.GcsExtension.Builder builderForValue) {
            this.gcsExtension_ = (ExtensionGcs.GcsExtension) builderForValue.build();
            this.bitField0_ |= 256;
        }

        /* access modifiers changed from: private */
        public void mergeGcsExtension(ExtensionGcs.GcsExtension value) {
            if (value != null) {
                ExtensionGcs.GcsExtension gcsExtension = this.gcsExtension_;
                if (gcsExtension == null || gcsExtension == ExtensionGcs.GcsExtension.getDefaultInstance()) {
                    this.gcsExtension_ = value;
                } else {
                    this.gcsExtension_ = (ExtensionGcs.GcsExtension) ((ExtensionGcs.GcsExtension.Builder) ExtensionGcs.GcsExtension.newBuilder(this.gcsExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearGcsExtension() {
            this.gcsExtension_ = null;
            this.bitField0_ &= -257;
        }

        public boolean hasTranslateExtension() {
            return (this.bitField0_ & 512) != 0;
        }

        public ExtensionTranslate.TranslateExtension getTranslateExtension() {
            ExtensionTranslate.TranslateExtension translateExtension = this.translateExtension_;
            return translateExtension == null ? ExtensionTranslate.TranslateExtension.getDefaultInstance() : translateExtension;
        }

        /* access modifiers changed from: private */
        public void setTranslateExtension(ExtensionTranslate.TranslateExtension value) {
            if (value != null) {
                this.translateExtension_ = value;
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setTranslateExtension(ExtensionTranslate.TranslateExtension.Builder builderForValue) {
            this.translateExtension_ = (ExtensionTranslate.TranslateExtension) builderForValue.build();
            this.bitField0_ |= 512;
        }

        /* access modifiers changed from: private */
        public void mergeTranslateExtension(ExtensionTranslate.TranslateExtension value) {
            if (value != null) {
                ExtensionTranslate.TranslateExtension translateExtension = this.translateExtension_;
                if (translateExtension == null || translateExtension == ExtensionTranslate.TranslateExtension.getDefaultInstance()) {
                    this.translateExtension_ = value;
                } else {
                    this.translateExtension_ = (ExtensionTranslate.TranslateExtension) ((ExtensionTranslate.TranslateExtension.Builder) ExtensionTranslate.TranslateExtension.newBuilder(this.translateExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 512;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTranslateExtension() {
            this.translateExtension_ = null;
            this.bitField0_ &= -513;
        }

        public boolean hasBugleExtension() {
            return (this.bitField0_ & 1024) != 0;
        }

        public ExtensionBugle.BugleExtension getBugleExtension() {
            ExtensionBugle.BugleExtension bugleExtension = this.bugleExtension_;
            return bugleExtension == null ? ExtensionBugle.BugleExtension.getDefaultInstance() : bugleExtension;
        }

        /* access modifiers changed from: private */
        public void setBugleExtension(ExtensionBugle.BugleExtension value) {
            if (value != null) {
                this.bugleExtension_ = value;
                this.bitField0_ |= 1024;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setBugleExtension(ExtensionBugle.BugleExtension.Builder builderForValue) {
            this.bugleExtension_ = (ExtensionBugle.BugleExtension) builderForValue.build();
            this.bitField0_ |= 1024;
        }

        /* access modifiers changed from: private */
        public void mergeBugleExtension(ExtensionBugle.BugleExtension value) {
            if (value != null) {
                ExtensionBugle.BugleExtension bugleExtension = this.bugleExtension_;
                if (bugleExtension == null || bugleExtension == ExtensionBugle.BugleExtension.getDefaultInstance()) {
                    this.bugleExtension_ = value;
                } else {
                    this.bugleExtension_ = (ExtensionBugle.BugleExtension) ((ExtensionBugle.BugleExtension.Builder) ExtensionBugle.BugleExtension.newBuilder(this.bugleExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1024;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearBugleExtension() {
            this.bugleExtension_ = null;
            this.bitField0_ &= -1025;
        }

        public boolean hasCsapkExtension() {
            return (this.bitField0_ & 2048) != 0;
        }

        public ExtensionCsapk.CsapkExtension getCsapkExtension() {
            ExtensionCsapk.CsapkExtension csapkExtension = this.csapkExtension_;
            return csapkExtension == null ? ExtensionCsapk.CsapkExtension.getDefaultInstance() : csapkExtension;
        }

        /* access modifiers changed from: private */
        public void setCsapkExtension(ExtensionCsapk.CsapkExtension value) {
            if (value != null) {
                this.csapkExtension_ = value;
                this.bitField0_ |= 2048;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCsapkExtension(ExtensionCsapk.CsapkExtension.Builder builderForValue) {
            this.csapkExtension_ = (ExtensionCsapk.CsapkExtension) builderForValue.build();
            this.bitField0_ |= 2048;
        }

        /* access modifiers changed from: private */
        public void mergeCsapkExtension(ExtensionCsapk.CsapkExtension value) {
            if (value != null) {
                ExtensionCsapk.CsapkExtension csapkExtension = this.csapkExtension_;
                if (csapkExtension == null || csapkExtension == ExtensionCsapk.CsapkExtension.getDefaultInstance()) {
                    this.csapkExtension_ = value;
                } else {
                    this.csapkExtension_ = (ExtensionCsapk.CsapkExtension) ((ExtensionCsapk.CsapkExtension.Builder) ExtensionCsapk.CsapkExtension.newBuilder(this.csapkExtension_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2048;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearCsapkExtension() {
            this.csapkExtension_ = null;
            this.bitField0_ &= -2049;
        }

        public static MetricExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MetricExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MetricExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MetricExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MetricExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static MetricExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static MetricExtension parseFrom(InputStream input) throws IOException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MetricExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MetricExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (MetricExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static MetricExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MetricExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static MetricExtension parseFrom(CodedInputStream input) throws IOException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static MetricExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(MetricExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<MetricExtension, Builder> implements MetricExtensionOrBuilder {
            private Builder() {
                super(MetricExtension.DEFAULT_INSTANCE);
            }

            public boolean hasGmailExtension() {
                return ((MetricExtension) this.instance).hasGmailExtension();
            }

            public ExtensionGmail.GmailExtension getGmailExtension() {
                return ((MetricExtension) this.instance).getGmailExtension();
            }

            public Builder setGmailExtension(ExtensionGmail.GmailExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setGmailExtension(value);
                return this;
            }

            public Builder setGmailExtension(ExtensionGmail.GmailExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setGmailExtension(builderForValue);
                return this;
            }

            public Builder mergeGmailExtension(ExtensionGmail.GmailExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeGmailExtension(value);
                return this;
            }

            public Builder clearGmailExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearGmailExtension();
                return this;
            }

            public boolean hasGmmExtension() {
                return ((MetricExtension) this.instance).hasGmmExtension();
            }

            public ExtensionGmm.GmmExtension getGmmExtension() {
                return ((MetricExtension) this.instance).getGmmExtension();
            }

            public Builder setGmmExtension(ExtensionGmm.GmmExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setGmmExtension(value);
                return this;
            }

            public Builder setGmmExtension(ExtensionGmm.GmmExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setGmmExtension(builderForValue);
                return this;
            }

            public Builder mergeGmmExtension(ExtensionGmm.GmmExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeGmmExtension(value);
                return this;
            }

            public Builder clearGmmExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearGmmExtension();
                return this;
            }

            public boolean hasPhotosScannerExtension() {
                return ((MetricExtension) this.instance).hasPhotosScannerExtension();
            }

            public ExtensionPhotosScanner.PhotosScannerExtension getPhotosScannerExtension() {
                return ((MetricExtension) this.instance).getPhotosScannerExtension();
            }

            public Builder setPhotosScannerExtension(ExtensionPhotosScanner.PhotosScannerExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setPhotosScannerExtension(value);
                return this;
            }

            public Builder setPhotosScannerExtension(ExtensionPhotosScanner.PhotosScannerExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setPhotosScannerExtension(builderForValue);
                return this;
            }

            public Builder mergePhotosScannerExtension(ExtensionPhotosScanner.PhotosScannerExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergePhotosScannerExtension(value);
                return this;
            }

            public Builder clearPhotosScannerExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearPhotosScannerExtension();
                return this;
            }

            public boolean hasCalendarExtension() {
                return ((MetricExtension) this.instance).hasCalendarExtension();
            }

            public ExtensionCalendar.CalendarExtension getCalendarExtension() {
                return ((MetricExtension) this.instance).getCalendarExtension();
            }

            public Builder setCalendarExtension(ExtensionCalendar.CalendarExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setCalendarExtension(value);
                return this;
            }

            public Builder setCalendarExtension(ExtensionCalendar.CalendarExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setCalendarExtension(builderForValue);
                return this;
            }

            public Builder mergeCalendarExtension(ExtensionCalendar.CalendarExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeCalendarExtension(value);
                return this;
            }

            public Builder clearCalendarExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearCalendarExtension();
                return this;
            }

            public boolean hasPhotosExtension() {
                return ((MetricExtension) this.instance).hasPhotosExtension();
            }

            public ExtensionPhotos.PhotosExtension getPhotosExtension() {
                return ((MetricExtension) this.instance).getPhotosExtension();
            }

            public Builder setPhotosExtension(ExtensionPhotos.PhotosExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setPhotosExtension(value);
                return this;
            }

            public Builder setPhotosExtension(ExtensionPhotos.PhotosExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setPhotosExtension(builderForValue);
                return this;
            }

            public Builder mergePhotosExtension(ExtensionPhotos.PhotosExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergePhotosExtension(value);
                return this;
            }

            public Builder clearPhotosExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearPhotosExtension();
                return this;
            }

            public boolean hasYoutubeExtension() {
                return ((MetricExtension) this.instance).hasYoutubeExtension();
            }

            public ExtensionYoutube.YouTubeExtension getYoutubeExtension() {
                return ((MetricExtension) this.instance).getYoutubeExtension();
            }

            public Builder setYoutubeExtension(ExtensionYoutube.YouTubeExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setYoutubeExtension(value);
                return this;
            }

            public Builder setYoutubeExtension(ExtensionYoutube.YouTubeExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setYoutubeExtension(builderForValue);
                return this;
            }

            public Builder mergeYoutubeExtension(ExtensionYoutube.YouTubeExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeYoutubeExtension(value);
                return this;
            }

            public Builder clearYoutubeExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearYoutubeExtension();
                return this;
            }

            public boolean hasCloudDpcExtension() {
                return ((MetricExtension) this.instance).hasCloudDpcExtension();
            }

            public ExtensionCloudDpc.CloudDpcExtension getCloudDpcExtension() {
                return ((MetricExtension) this.instance).getCloudDpcExtension();
            }

            public Builder setCloudDpcExtension(ExtensionCloudDpc.CloudDpcExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setCloudDpcExtension(value);
                return this;
            }

            public Builder setCloudDpcExtension(ExtensionCloudDpc.CloudDpcExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setCloudDpcExtension(builderForValue);
                return this;
            }

            public Builder mergeCloudDpcExtension(ExtensionCloudDpc.CloudDpcExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeCloudDpcExtension(value);
                return this;
            }

            public Builder clearCloudDpcExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearCloudDpcExtension();
                return this;
            }

            public boolean hasGcoreDimension() {
                return ((MetricExtension) this.instance).hasGcoreDimension();
            }

            public GcoreDimensions.GCoreDimensions getGcoreDimension() {
                return ((MetricExtension) this.instance).getGcoreDimension();
            }

            public Builder setGcoreDimension(GcoreDimensions.GCoreDimensions value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setGcoreDimension(value);
                return this;
            }

            public Builder setGcoreDimension(GcoreDimensions.GCoreDimensions.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setGcoreDimension(builderForValue);
                return this;
            }

            public Builder mergeGcoreDimension(GcoreDimensions.GCoreDimensions value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeGcoreDimension(value);
                return this;
            }

            public Builder clearGcoreDimension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearGcoreDimension();
                return this;
            }

            public boolean hasGcsExtension() {
                return ((MetricExtension) this.instance).hasGcsExtension();
            }

            public ExtensionGcs.GcsExtension getGcsExtension() {
                return ((MetricExtension) this.instance).getGcsExtension();
            }

            public Builder setGcsExtension(ExtensionGcs.GcsExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setGcsExtension(value);
                return this;
            }

            public Builder setGcsExtension(ExtensionGcs.GcsExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setGcsExtension(builderForValue);
                return this;
            }

            public Builder mergeGcsExtension(ExtensionGcs.GcsExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeGcsExtension(value);
                return this;
            }

            public Builder clearGcsExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearGcsExtension();
                return this;
            }

            public boolean hasTranslateExtension() {
                return ((MetricExtension) this.instance).hasTranslateExtension();
            }

            public ExtensionTranslate.TranslateExtension getTranslateExtension() {
                return ((MetricExtension) this.instance).getTranslateExtension();
            }

            public Builder setTranslateExtension(ExtensionTranslate.TranslateExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setTranslateExtension(value);
                return this;
            }

            public Builder setTranslateExtension(ExtensionTranslate.TranslateExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setTranslateExtension(builderForValue);
                return this;
            }

            public Builder mergeTranslateExtension(ExtensionTranslate.TranslateExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeTranslateExtension(value);
                return this;
            }

            public Builder clearTranslateExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearTranslateExtension();
                return this;
            }

            public boolean hasBugleExtension() {
                return ((MetricExtension) this.instance).hasBugleExtension();
            }

            public ExtensionBugle.BugleExtension getBugleExtension() {
                return ((MetricExtension) this.instance).getBugleExtension();
            }

            public Builder setBugleExtension(ExtensionBugle.BugleExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setBugleExtension(value);
                return this;
            }

            public Builder setBugleExtension(ExtensionBugle.BugleExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setBugleExtension(builderForValue);
                return this;
            }

            public Builder mergeBugleExtension(ExtensionBugle.BugleExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeBugleExtension(value);
                return this;
            }

            public Builder clearBugleExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearBugleExtension();
                return this;
            }

            public boolean hasCsapkExtension() {
                return ((MetricExtension) this.instance).hasCsapkExtension();
            }

            public ExtensionCsapk.CsapkExtension getCsapkExtension() {
                return ((MetricExtension) this.instance).getCsapkExtension();
            }

            public Builder setCsapkExtension(ExtensionCsapk.CsapkExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).setCsapkExtension(value);
                return this;
            }

            public Builder setCsapkExtension(ExtensionCsapk.CsapkExtension.Builder builderForValue) {
                copyOnWrite();
                ((MetricExtension) this.instance).setCsapkExtension(builderForValue);
                return this;
            }

            public Builder mergeCsapkExtension(ExtensionCsapk.CsapkExtension value) {
                copyOnWrite();
                ((MetricExtension) this.instance).mergeCsapkExtension(value);
                return this;
            }

            public Builder clearCsapkExtension() {
                copyOnWrite();
                ((MetricExtension) this.instance).clearCsapkExtension();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new MetricExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\f\u0000\u0001\u0001\f\f\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001\u0003\t\u0002\u0004\t\u0003\u0005\t\u0004\u0006\t\u0005\u0007\t\u0006\b\t\u0007\t\t\b\n\t\t\u000b\t\n\f\t\u000b", new Object[]{"bitField0_", "gmailExtension_", "gmmExtension_", "photosScannerExtension_", "calendarExtension_", "photosExtension_", "youtubeExtension_", "cloudDpcExtension_", "gcoreDimension_", "gcsExtension_", "translateExtension_", "bugleExtension_", "csapkExtension_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<MetricExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (MetricExtension.class) {
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
            GeneratedMessageLite.registerDefaultInstance(MetricExtension.class, DEFAULT_INSTANCE);
        }

        public static MetricExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<MetricExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
