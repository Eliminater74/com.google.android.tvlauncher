package com.google.android.tvlauncher.doubleclick.proto.nano;

import com.google.android.tvlauncher.doubleclick.proto.nano.VideoCreative;
import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import java.io.IOException;

public interface AdConfig {

    public static final class AdAsset extends MessageNano {
        public static final int DIRECT_AD_CONFIG_FIELD_NUMBER = 1;
        public static final int DOUBLECLICK_AD_CONFIG_FIELD_NUMBER = 2;
        private static volatile AdAsset[] _emptyArray;
        public long expiration;
        public String trackingId;
        private int typeCase_ = 0;
        private Object type_;

        public int getTypeCase() {
            return this.typeCase_;
        }

        public AdAsset clearType() {
            this.typeCase_ = 0;
            this.type_ = null;
            return this;
        }

        public static AdAsset[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new AdAsset[0];
                    }
                }
            }
            return _emptyArray;
        }

        public boolean hasDirectAdConfig() {
            return this.typeCase_ == 1;
        }

        public DirectAdConfig getDirectAdConfig() {
            if (this.typeCase_ == 1) {
                return (DirectAdConfig) this.type_;
            }
            return null;
        }

        public AdAsset setDirectAdConfig(DirectAdConfig value) {
            if (value != null) {
                this.typeCase_ = 1;
                this.type_ = value;
                return this;
            }
            throw new NullPointerException();
        }

        public boolean hasDoubleclickAdConfig() {
            return this.typeCase_ == 2;
        }

        public DoubleClickAdConfig getDoubleclickAdConfig() {
            if (this.typeCase_ == 2) {
                return (DoubleClickAdConfig) this.type_;
            }
            return null;
        }

        public AdAsset setDoubleclickAdConfig(DoubleClickAdConfig value) {
            if (value != null) {
                this.typeCase_ = 2;
                this.type_ = value;
                return this;
            }
            throw new NullPointerException();
        }

        public AdAsset() {
            clear();
        }

        public AdAsset clear() {
            this.expiration = 0;
            this.trackingId = "";
            clearType();
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (this.typeCase_ == 1) {
                output.writeMessage(1, (MessageNano) this.type_);
            }
            if (this.typeCase_ == 2) {
                output.writeMessage(2, (MessageNano) this.type_);
            }
            long j = this.expiration;
            if (j != 0) {
                output.writeInt64(3, j);
            }
            if (!this.trackingId.equals("")) {
                output.writeString(4, this.trackingId);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (this.typeCase_ == 1) {
                size += CodedOutputByteBufferNano.computeMessageSize(1, (MessageNano) this.type_);
            }
            if (this.typeCase_ == 2) {
                size += CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.type_);
            }
            long j = this.expiration;
            if (j != 0) {
                size += CodedOutputByteBufferNano.computeInt64Size(3, j);
            }
            if (!this.trackingId.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(4, this.trackingId);
            }
            return size;
        }

        public AdAsset mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
                if (tag == 10) {
                    if (this.typeCase_ != 1) {
                        this.type_ = new DirectAdConfig();
                    }
                    input.readMessage((MessageNano) this.type_);
                    this.typeCase_ = 1;
                } else if (tag == 18) {
                    if (this.typeCase_ != 2) {
                        this.type_ = new DoubleClickAdConfig();
                    }
                    input.readMessage((MessageNano) this.type_);
                    this.typeCase_ = 2;
                } else if (tag == 24) {
                    this.expiration = input.readInt64();
                } else if (tag == 34) {
                    this.trackingId = input.readString();
                } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                    return this;
                }
            }
        }

        public static AdAsset parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (AdAsset) MessageNano.mergeFrom(new AdAsset(), data);
        }

        public static AdAsset parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new AdAsset().mergeFrom(input);
        }
    }

    public static final class DirectAdConfig extends MessageNano {
        private static volatile DirectAdConfig[] _emptyArray;
        public String dataUrl;
        public String packageName;

        public static DirectAdConfig[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new DirectAdConfig[0];
                    }
                }
            }
            return _emptyArray;
        }

        public DirectAdConfig() {
            clear();
        }

        public DirectAdConfig clear() {
            this.packageName = "";
            this.dataUrl = "";
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.packageName.equals("")) {
                output.writeString(1, this.packageName);
            }
            if (!this.dataUrl.equals("")) {
                output.writeString(2, this.dataUrl);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.packageName.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.packageName);
            }
            if (!this.dataUrl.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(2, this.dataUrl);
            }
            return size;
        }

        public DirectAdConfig mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
                if (tag == 10) {
                    this.packageName = input.readString();
                } else if (tag == 18) {
                    this.dataUrl = input.readString();
                } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                    return this;
                }
            }
        }

        public static DirectAdConfig parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (DirectAdConfig) MessageNano.mergeFrom(new DirectAdConfig(), data);
        }

        public static DirectAdConfig parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new DirectAdConfig().mergeFrom(input);
        }
    }

    public static final class DoubleClickAdConfig extends MessageNano {
        public static final int CUSTOM_CREATIVE_FIELD_NUMBER = 2;
        public static final int VAST_FIELD_NUMBER = 3;
        private static volatile DoubleClickAdConfig[] _emptyArray;
        public String adUnitId;
        private int formatCase_ = 0;
        private Object format_;

        public int getFormatCase() {
            return this.formatCase_;
        }

        public DoubleClickAdConfig clearFormat() {
            this.formatCase_ = 0;
            this.format_ = null;
            return this;
        }

        public static DoubleClickAdConfig[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new DoubleClickAdConfig[0];
                    }
                }
            }
            return _emptyArray;
        }

        public boolean hasCustomCreative() {
            return this.formatCase_ == 2;
        }

        public CustomCreative getCustomCreative() {
            if (this.formatCase_ == 2) {
                return (CustomCreative) this.format_;
            }
            return null;
        }

        public DoubleClickAdConfig setCustomCreative(CustomCreative value) {
            if (value != null) {
                this.formatCase_ = 2;
                this.format_ = value;
                return this;
            }
            throw new NullPointerException();
        }

        public boolean hasVast() {
            return this.formatCase_ == 3;
        }

        public VideoCreative.VastXml getVast() {
            if (this.formatCase_ == 3) {
                return (VideoCreative.VastXml) this.format_;
            }
            return null;
        }

        public DoubleClickAdConfig setVast(VideoCreative.VastXml value) {
            if (value != null) {
                this.formatCase_ = 3;
                this.format_ = value;
                return this;
            }
            throw new NullPointerException();
        }

        public DoubleClickAdConfig() {
            clear();
        }

        public DoubleClickAdConfig clear() {
            this.adUnitId = "";
            clearFormat();
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.adUnitId.equals("")) {
                output.writeString(1, this.adUnitId);
            }
            if (this.formatCase_ == 2) {
                output.writeMessage(2, (MessageNano) this.format_);
            }
            if (this.formatCase_ == 3) {
                output.writeMessage(3, (MessageNano) this.format_);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.adUnitId.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.adUnitId);
            }
            if (this.formatCase_ == 2) {
                size += CodedOutputByteBufferNano.computeMessageSize(2, (MessageNano) this.format_);
            }
            if (this.formatCase_ == 3) {
                return size + CodedOutputByteBufferNano.computeMessageSize(3, (MessageNano) this.format_);
            }
            return size;
        }

        public DoubleClickAdConfig mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
                if (tag == 10) {
                    this.adUnitId = input.readString();
                } else if (tag == 18) {
                    if (this.formatCase_ != 2) {
                        this.format_ = new CustomCreative();
                    }
                    input.readMessage((MessageNano) this.format_);
                    this.formatCase_ = 2;
                } else if (tag == 26) {
                    if (this.formatCase_ != 3) {
                        this.format_ = new VideoCreative.VastXml();
                    }
                    input.readMessage((MessageNano) this.format_);
                    this.formatCase_ = 3;
                } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                    return this;
                }
            }
        }

        public static DoubleClickAdConfig parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (DoubleClickAdConfig) MessageNano.mergeFrom(new DoubleClickAdConfig(), data);
        }

        public static DoubleClickAdConfig parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new DoubleClickAdConfig().mergeFrom(input);
        }
    }

    public static final class CustomCreative extends MessageNano {
        public static final int APP_INSTALL_CREATIVE_INFO_FIELD_NUMBER = 5;
        public static final int CONTENT_CREATIVE_INFO_FIELD_NUMBER = 6;
        private static volatile CustomCreative[] _emptyArray;
        public String clickTrackingUrl;
        public String displayBannerImpressionTrackingUrl;
        private int formatInfoCase_ = 0;
        private Object formatInfo_;
        public String imageUri;
        public String videoUri;

        public int getFormatInfoCase() {
            return this.formatInfoCase_;
        }

        public CustomCreative clearFormatInfo() {
            this.formatInfoCase_ = 0;
            this.formatInfo_ = null;
            return this;
        }

        public static CustomCreative[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new CustomCreative[0];
                    }
                }
            }
            return _emptyArray;
        }

        public boolean hasAppInstallCreativeInfo() {
            return this.formatInfoCase_ == 5;
        }

        public AppInstallCreativeInfo getAppInstallCreativeInfo() {
            if (this.formatInfoCase_ == 5) {
                return (AppInstallCreativeInfo) this.formatInfo_;
            }
            return null;
        }

        public CustomCreative setAppInstallCreativeInfo(AppInstallCreativeInfo value) {
            if (value != null) {
                this.formatInfoCase_ = 5;
                this.formatInfo_ = value;
                return this;
            }
            throw new NullPointerException();
        }

        public boolean hasContentCreativeInfo() {
            return this.formatInfoCase_ == 6;
        }

        public ContentCreativeInfo getContentCreativeInfo() {
            if (this.formatInfoCase_ == 6) {
                return (ContentCreativeInfo) this.formatInfo_;
            }
            return null;
        }

        public CustomCreative setContentCreativeInfo(ContentCreativeInfo value) {
            if (value != null) {
                this.formatInfoCase_ = 6;
                this.formatInfo_ = value;
                return this;
            }
            throw new NullPointerException();
        }

        public CustomCreative() {
            clear();
        }

        public CustomCreative clear() {
            this.imageUri = "";
            this.videoUri = "";
            this.displayBannerImpressionTrackingUrl = "";
            this.clickTrackingUrl = "";
            clearFormatInfo();
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.imageUri.equals("")) {
                output.writeString(1, this.imageUri);
            }
            if (!this.videoUri.equals("")) {
                output.writeString(2, this.videoUri);
            }
            if (!this.displayBannerImpressionTrackingUrl.equals("")) {
                output.writeString(3, this.displayBannerImpressionTrackingUrl);
            }
            if (!this.clickTrackingUrl.equals("")) {
                output.writeString(4, this.clickTrackingUrl);
            }
            if (this.formatInfoCase_ == 5) {
                output.writeMessage(5, (MessageNano) this.formatInfo_);
            }
            if (this.formatInfoCase_ == 6) {
                output.writeMessage(6, (MessageNano) this.formatInfo_);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.imageUri.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.imageUri);
            }
            if (!this.videoUri.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.videoUri);
            }
            if (!this.displayBannerImpressionTrackingUrl.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(3, this.displayBannerImpressionTrackingUrl);
            }
            if (!this.clickTrackingUrl.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(4, this.clickTrackingUrl);
            }
            if (this.formatInfoCase_ == 5) {
                size += CodedOutputByteBufferNano.computeMessageSize(5, (MessageNano) this.formatInfo_);
            }
            if (this.formatInfoCase_ == 6) {
                return size + CodedOutputByteBufferNano.computeMessageSize(6, (MessageNano) this.formatInfo_);
            }
            return size;
        }

        public CustomCreative mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
                if (tag == 10) {
                    this.imageUri = input.readString();
                } else if (tag == 18) {
                    this.videoUri = input.readString();
                } else if (tag == 26) {
                    this.displayBannerImpressionTrackingUrl = input.readString();
                } else if (tag == 34) {
                    this.clickTrackingUrl = input.readString();
                } else if (tag == 42) {
                    if (this.formatInfoCase_ != 5) {
                        this.formatInfo_ = new AppInstallCreativeInfo();
                    }
                    input.readMessage((MessageNano) this.formatInfo_);
                    this.formatInfoCase_ = 5;
                } else if (tag == 50) {
                    if (this.formatInfoCase_ != 6) {
                        this.formatInfo_ = new ContentCreativeInfo();
                    }
                    input.readMessage((MessageNano) this.formatInfo_);
                    this.formatInfoCase_ = 6;
                } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                    return this;
                }
            }
        }

        public static CustomCreative parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (CustomCreative) MessageNano.mergeFrom(new CustomCreative(), data);
        }

        public static CustomCreative parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new CustomCreative().mergeFrom(input);
        }
    }

    public static final class AppInstallCreativeInfo extends MessageNano {
        private static volatile AppInstallCreativeInfo[] _emptyArray;
        public String deeplinkUrl;
        public String marketUrl;
        public String packageName;

        public static AppInstallCreativeInfo[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new AppInstallCreativeInfo[0];
                    }
                }
            }
            return _emptyArray;
        }

        public AppInstallCreativeInfo() {
            clear();
        }

        public AppInstallCreativeInfo clear() {
            this.packageName = "";
            this.marketUrl = "";
            this.deeplinkUrl = "";
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.packageName.equals("")) {
                output.writeString(1, this.packageName);
            }
            if (!this.marketUrl.equals("")) {
                output.writeString(2, this.marketUrl);
            }
            if (!this.deeplinkUrl.equals("")) {
                output.writeString(3, this.deeplinkUrl);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.packageName.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.packageName);
            }
            if (!this.marketUrl.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.marketUrl);
            }
            if (!this.deeplinkUrl.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(3, this.deeplinkUrl);
            }
            return size;
        }

        public AppInstallCreativeInfo mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
                if (tag == 10) {
                    this.packageName = input.readString();
                } else if (tag == 18) {
                    this.marketUrl = input.readString();
                } else if (tag == 26) {
                    this.deeplinkUrl = input.readString();
                } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                    return this;
                }
            }
        }

        public static AppInstallCreativeInfo parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (AppInstallCreativeInfo) MessageNano.mergeFrom(new AppInstallCreativeInfo(), data);
        }

        public static AppInstallCreativeInfo parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new AppInstallCreativeInfo().mergeFrom(input);
        }
    }

    public static final class ContentCreativeInfo extends MessageNano {
        private static volatile ContentCreativeInfo[] _emptyArray;

        public static ContentCreativeInfo[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new ContentCreativeInfo[0];
                    }
                }
            }
            return _emptyArray;
        }

        public ContentCreativeInfo() {
            clear();
        }

        public ContentCreativeInfo clear() {
            this.cachedSize = -1;
            return this;
        }

        public ContentCreativeInfo mergeFrom(CodedInputByteBufferNano input) throws IOException {
            int tag;
            do {
                tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
            } while (WireFormatNano.parseUnknownField(input, tag));
            return this;
        }

        public static ContentCreativeInfo parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (ContentCreativeInfo) MessageNano.mergeFrom(new ContentCreativeInfo(), data);
        }

        public static ContentCreativeInfo parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new ContentCreativeInfo().mergeFrom(input);
        }
    }
}
