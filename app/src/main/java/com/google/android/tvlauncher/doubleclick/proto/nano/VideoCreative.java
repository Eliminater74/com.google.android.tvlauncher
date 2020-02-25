package com.google.android.tvlauncher.doubleclick.proto.nano;

import com.google.protobuf.nano.CodedInputByteBufferNano;
import com.google.protobuf.nano.CodedOutputByteBufferNano;
import com.google.protobuf.nano.InternalNano;
import com.google.protobuf.nano.InvalidProtocolBufferNanoException;
import com.google.protobuf.nano.MessageNano;
import com.google.protobuf.nano.WireFormatNano;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;

public interface VideoCreative {

    public static final class VastXml extends MessageNano {
        private static volatile VastXml[] _emptyArray;
        public String adId;
        public VastTracking[] clickTracking;
        public VastCompanion[] companion;
        public String customParameters;
        public String customParametersInstream;
        public boolean customSkipEventExists;
        public VastTracking[] customTracking;
        public String destinationUrl;
        public int duration;
        public VastTracking[] eventTracking;
        public int fallbackIndex;

        /* renamed from: id */
        public String f138id;
        public VastImpression[] impression;
        public VastMedia[] media;
        public VastNonLinear[] nonLinearAsset;
        public VastTracking[] nonLinearEventTracking;
        public String redirectUrl;
        public int sequence;
        public boolean streamingMedia;
        public String survey;
        public String vastSchemaValidationErrors;
        public int vastVersion;

        public VastXml() {
            clear();
        }

        public static VastXml[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new VastXml[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static VastXml parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (VastXml) MessageNano.mergeFrom(new VastXml(), data);
        }

        public static VastXml parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new VastXml().mergeFrom(input);
        }

        public VastXml clear() {
            this.destinationUrl = "";
            this.duration = 0;
            this.survey = "";
            this.media = VastMedia.emptyArray();
            this.companion = VastCompanion.emptyArray();
            this.customParameters = "";
            this.eventTracking = VastTracking.emptyArray();
            this.clickTracking = VastTracking.emptyArray();
            this.customTracking = VastTracking.emptyArray();
            this.nonLinearAsset = VastNonLinear.emptyArray();
            this.nonLinearEventTracking = VastTracking.emptyArray();
            this.redirectUrl = "";
            this.fallbackIndex = 0;
            this.streamingMedia = false;
            this.customParametersInstream = "";
            this.vastSchemaValidationErrors = "";
            this.impression = VastImpression.emptyArray();
            this.f138id = "";
            this.adId = "";
            this.vastVersion = 0;
            this.customSkipEventExists = false;
            this.sequence = 0;
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.destinationUrl.equals("")) {
                output.writeString(1, this.destinationUrl);
            }
            int i = this.duration;
            if (i != 0) {
                output.writeInt32(2, i);
            }
            if (!this.survey.equals("")) {
                output.writeString(3, this.survey);
            }
            VastMedia[] vastMediaArr = this.media;
            if (vastMediaArr != null && vastMediaArr.length > 0) {
                int i2 = 0;
                while (true) {
                    VastMedia[] vastMediaArr2 = this.media;
                    if (i2 >= vastMediaArr2.length) {
                        break;
                    }
                    VastMedia element = vastMediaArr2[i2];
                    if (element != null) {
                        output.writeMessage(4, element);
                    }
                    i2++;
                }
            }
            VastCompanion[] vastCompanionArr = this.companion;
            if (vastCompanionArr != null && vastCompanionArr.length > 0) {
                int i3 = 0;
                while (true) {
                    VastCompanion[] vastCompanionArr2 = this.companion;
                    if (i3 >= vastCompanionArr2.length) {
                        break;
                    }
                    VastCompanion element2 = vastCompanionArr2[i3];
                    if (element2 != null) {
                        output.writeMessage(5, element2);
                    }
                    i3++;
                }
            }
            if (!this.customParameters.equals("")) {
                output.writeString(6, this.customParameters);
            }
            VastTracking[] vastTrackingArr = this.eventTracking;
            if (vastTrackingArr != null && vastTrackingArr.length > 0) {
                int i4 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr2 = this.eventTracking;
                    if (i4 >= vastTrackingArr2.length) {
                        break;
                    }
                    VastTracking element3 = vastTrackingArr2[i4];
                    if (element3 != null) {
                        output.writeMessage(7, element3);
                    }
                    i4++;
                }
            }
            VastTracking[] vastTrackingArr3 = this.clickTracking;
            if (vastTrackingArr3 != null && vastTrackingArr3.length > 0) {
                int i5 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr4 = this.clickTracking;
                    if (i5 >= vastTrackingArr4.length) {
                        break;
                    }
                    VastTracking element4 = vastTrackingArr4[i5];
                    if (element4 != null) {
                        output.writeMessage(8, element4);
                    }
                    i5++;
                }
            }
            VastTracking[] vastTrackingArr5 = this.customTracking;
            if (vastTrackingArr5 != null && vastTrackingArr5.length > 0) {
                int i6 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr6 = this.customTracking;
                    if (i6 >= vastTrackingArr6.length) {
                        break;
                    }
                    VastTracking element5 = vastTrackingArr6[i6];
                    if (element5 != null) {
                        output.writeMessage(9, element5);
                    }
                    i6++;
                }
            }
            VastNonLinear[] vastNonLinearArr = this.nonLinearAsset;
            if (vastNonLinearArr != null && vastNonLinearArr.length > 0) {
                int i7 = 0;
                while (true) {
                    VastNonLinear[] vastNonLinearArr2 = this.nonLinearAsset;
                    if (i7 >= vastNonLinearArr2.length) {
                        break;
                    }
                    VastNonLinear element6 = vastNonLinearArr2[i7];
                    if (element6 != null) {
                        output.writeMessage(10, element6);
                    }
                    i7++;
                }
            }
            VastTracking[] vastTrackingArr7 = this.nonLinearEventTracking;
            if (vastTrackingArr7 != null && vastTrackingArr7.length > 0) {
                int i8 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr8 = this.nonLinearEventTracking;
                    if (i8 >= vastTrackingArr8.length) {
                        break;
                    }
                    VastTracking element7 = vastTrackingArr8[i8];
                    if (element7 != null) {
                        output.writeMessage(11, element7);
                    }
                    i8++;
                }
            }
            if (!this.redirectUrl.equals("")) {
                output.writeString(12, this.redirectUrl);
            }
            int i9 = this.fallbackIndex;
            if (i9 != 0) {
                output.writeInt32(13, i9);
            }
            boolean z = this.streamingMedia;
            if (z) {
                output.writeBool(14, z);
            }
            if (!this.customParametersInstream.equals("")) {
                output.writeString(15, this.customParametersInstream);
            }
            if (!this.vastSchemaValidationErrors.equals("")) {
                output.writeString(16, this.vastSchemaValidationErrors);
            }
            VastImpression[] vastImpressionArr = this.impression;
            if (vastImpressionArr != null && vastImpressionArr.length > 0) {
                int i10 = 0;
                while (true) {
                    VastImpression[] vastImpressionArr2 = this.impression;
                    if (i10 >= vastImpressionArr2.length) {
                        break;
                    }
                    VastImpression element8 = vastImpressionArr2[i10];
                    if (element8 != null) {
                        output.writeMessage(17, element8);
                    }
                    i10++;
                }
            }
            if (!this.f138id.equals("")) {
                output.writeString(18, this.f138id);
            }
            if (!this.adId.equals("")) {
                output.writeString(19, this.adId);
            }
            int i11 = this.vastVersion;
            if (i11 != 0) {
                output.writeInt32(20, i11);
            }
            boolean z2 = this.customSkipEventExists;
            if (z2) {
                output.writeBool(21, z2);
            }
            int i12 = this.sequence;
            if (i12 != 0) {
                output.writeInt32(22, i12);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.destinationUrl.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.destinationUrl);
            }
            int i = this.duration;
            if (i != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(2, i);
            }
            if (!this.survey.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(3, this.survey);
            }
            VastMedia[] vastMediaArr = this.media;
            if (vastMediaArr != null && vastMediaArr.length > 0) {
                int i2 = 0;
                while (true) {
                    VastMedia[] vastMediaArr2 = this.media;
                    if (i2 >= vastMediaArr2.length) {
                        break;
                    }
                    VastMedia element = vastMediaArr2[i2];
                    if (element != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(4, element);
                    }
                    i2++;
                }
            }
            VastCompanion[] vastCompanionArr = this.companion;
            if (vastCompanionArr != null && vastCompanionArr.length > 0) {
                int i3 = 0;
                while (true) {
                    VastCompanion[] vastCompanionArr2 = this.companion;
                    if (i3 >= vastCompanionArr2.length) {
                        break;
                    }
                    VastCompanion element2 = vastCompanionArr2[i3];
                    if (element2 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(5, element2);
                    }
                    i3++;
                }
            }
            if (!this.customParameters.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(6, this.customParameters);
            }
            VastTracking[] vastTrackingArr = this.eventTracking;
            if (vastTrackingArr != null && vastTrackingArr.length > 0) {
                int i4 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr2 = this.eventTracking;
                    if (i4 >= vastTrackingArr2.length) {
                        break;
                    }
                    VastTracking element3 = vastTrackingArr2[i4];
                    if (element3 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(7, element3);
                    }
                    i4++;
                }
            }
            VastTracking[] vastTrackingArr3 = this.clickTracking;
            if (vastTrackingArr3 != null && vastTrackingArr3.length > 0) {
                int i5 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr4 = this.clickTracking;
                    if (i5 >= vastTrackingArr4.length) {
                        break;
                    }
                    VastTracking element4 = vastTrackingArr4[i5];
                    if (element4 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(8, element4);
                    }
                    i5++;
                }
            }
            VastTracking[] vastTrackingArr5 = this.customTracking;
            if (vastTrackingArr5 != null && vastTrackingArr5.length > 0) {
                int i6 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr6 = this.customTracking;
                    if (i6 >= vastTrackingArr6.length) {
                        break;
                    }
                    VastTracking element5 = vastTrackingArr6[i6];
                    if (element5 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(9, element5);
                    }
                    i6++;
                }
            }
            VastNonLinear[] vastNonLinearArr = this.nonLinearAsset;
            if (vastNonLinearArr != null && vastNonLinearArr.length > 0) {
                int i7 = 0;
                while (true) {
                    VastNonLinear[] vastNonLinearArr2 = this.nonLinearAsset;
                    if (i7 >= vastNonLinearArr2.length) {
                        break;
                    }
                    VastNonLinear element6 = vastNonLinearArr2[i7];
                    if (element6 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(10, element6);
                    }
                    i7++;
                }
            }
            VastTracking[] vastTrackingArr7 = this.nonLinearEventTracking;
            if (vastTrackingArr7 != null && vastTrackingArr7.length > 0) {
                int i8 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr8 = this.nonLinearEventTracking;
                    if (i8 >= vastTrackingArr8.length) {
                        break;
                    }
                    VastTracking element7 = vastTrackingArr8[i8];
                    if (element7 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(11, element7);
                    }
                    i8++;
                }
            }
            if (!this.redirectUrl.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(12, this.redirectUrl);
            }
            int i9 = this.fallbackIndex;
            if (i9 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(13, i9);
            }
            boolean z = this.streamingMedia;
            if (z) {
                size += CodedOutputByteBufferNano.computeBoolSize(14, z);
            }
            if (!this.customParametersInstream.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(15, this.customParametersInstream);
            }
            if (!this.vastSchemaValidationErrors.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(16, this.vastSchemaValidationErrors);
            }
            VastImpression[] vastImpressionArr = this.impression;
            if (vastImpressionArr != null && vastImpressionArr.length > 0) {
                int i10 = 0;
                while (true) {
                    VastImpression[] vastImpressionArr2 = this.impression;
                    if (i10 >= vastImpressionArr2.length) {
                        break;
                    }
                    VastImpression element8 = vastImpressionArr2[i10];
                    if (element8 != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(17, element8);
                    }
                    i10++;
                }
            }
            if (!this.f138id.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(18, this.f138id);
            }
            if (!this.adId.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(19, this.adId);
            }
            int i11 = this.vastVersion;
            if (i11 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(20, i11);
            }
            boolean z2 = this.customSkipEventExists;
            if (z2) {
                size += CodedOutputByteBufferNano.computeBoolSize(21, z2);
            }
            int i12 = this.sequence;
            if (i12 != 0) {
                return size + CodedOutputByteBufferNano.computeInt32Size(22, i12);
            }
            return size;
        }

        public VastXml mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.destinationUrl = input.readString();
                        break;
                    case 16:
                        this.duration = input.readInt32();
                        break;
                    case 26:
                        this.survey = input.readString();
                        break;
                    case 34:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 34);
                        VastMedia[] vastMediaArr = this.media;
                        int i = vastMediaArr == null ? 0 : vastMediaArr.length;
                        VastMedia[] newArray = new VastMedia[(i + arrayLength)];
                        if (i != 0) {
                            System.arraycopy(this.media, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = new VastMedia();
                            input.readMessage(newArray[i]);
                            input.readTag();
                            i++;
                        }
                        newArray[i] = new VastMedia();
                        input.readMessage(newArray[i]);
                        this.media = newArray;
                        break;
                    case 42:
                        int arrayLength2 = WireFormatNano.getRepeatedFieldArrayLength(input, 42);
                        VastCompanion[] vastCompanionArr = this.companion;
                        int i2 = vastCompanionArr == null ? 0 : vastCompanionArr.length;
                        VastCompanion[] newArray2 = new VastCompanion[(i2 + arrayLength2)];
                        if (i2 != 0) {
                            System.arraycopy(this.companion, 0, newArray2, 0, i2);
                        }
                        while (i2 < newArray2.length - 1) {
                            newArray2[i2] = new VastCompanion();
                            input.readMessage(newArray2[i2]);
                            input.readTag();
                            i2++;
                        }
                        newArray2[i2] = new VastCompanion();
                        input.readMessage(newArray2[i2]);
                        this.companion = newArray2;
                        break;
                    case 50:
                        this.customParameters = input.readString();
                        break;
                    case 58:
                        int arrayLength3 = WireFormatNano.getRepeatedFieldArrayLength(input, 58);
                        VastTracking[] vastTrackingArr = this.eventTracking;
                        int i3 = vastTrackingArr == null ? 0 : vastTrackingArr.length;
                        VastTracking[] newArray3 = new VastTracking[(i3 + arrayLength3)];
                        if (i3 != 0) {
                            System.arraycopy(this.eventTracking, 0, newArray3, 0, i3);
                        }
                        while (i3 < newArray3.length - 1) {
                            newArray3[i3] = new VastTracking();
                            input.readMessage(newArray3[i3]);
                            input.readTag();
                            i3++;
                        }
                        newArray3[i3] = new VastTracking();
                        input.readMessage(newArray3[i3]);
                        this.eventTracking = newArray3;
                        break;
                    case 66:
                        int arrayLength4 = WireFormatNano.getRepeatedFieldArrayLength(input, 66);
                        VastTracking[] vastTrackingArr2 = this.clickTracking;
                        int i4 = vastTrackingArr2 == null ? 0 : vastTrackingArr2.length;
                        VastTracking[] newArray4 = new VastTracking[(i4 + arrayLength4)];
                        if (i4 != 0) {
                            System.arraycopy(this.clickTracking, 0, newArray4, 0, i4);
                        }
                        while (i4 < newArray4.length - 1) {
                            newArray4[i4] = new VastTracking();
                            input.readMessage(newArray4[i4]);
                            input.readTag();
                            i4++;
                        }
                        newArray4[i4] = new VastTracking();
                        input.readMessage(newArray4[i4]);
                        this.clickTracking = newArray4;
                        break;
                    case 74:
                        int arrayLength5 = WireFormatNano.getRepeatedFieldArrayLength(input, 74);
                        VastTracking[] vastTrackingArr3 = this.customTracking;
                        int i5 = vastTrackingArr3 == null ? 0 : vastTrackingArr3.length;
                        VastTracking[] newArray5 = new VastTracking[(i5 + arrayLength5)];
                        if (i5 != 0) {
                            System.arraycopy(this.customTracking, 0, newArray5, 0, i5);
                        }
                        while (i5 < newArray5.length - 1) {
                            newArray5[i5] = new VastTracking();
                            input.readMessage(newArray5[i5]);
                            input.readTag();
                            i5++;
                        }
                        newArray5[i5] = new VastTracking();
                        input.readMessage(newArray5[i5]);
                        this.customTracking = newArray5;
                        break;
                    case 82:
                        int arrayLength6 = WireFormatNano.getRepeatedFieldArrayLength(input, 82);
                        VastNonLinear[] vastNonLinearArr = this.nonLinearAsset;
                        int i6 = vastNonLinearArr == null ? 0 : vastNonLinearArr.length;
                        VastNonLinear[] newArray6 = new VastNonLinear[(i6 + arrayLength6)];
                        if (i6 != 0) {
                            System.arraycopy(this.nonLinearAsset, 0, newArray6, 0, i6);
                        }
                        while (i6 < newArray6.length - 1) {
                            newArray6[i6] = new VastNonLinear();
                            input.readMessage(newArray6[i6]);
                            input.readTag();
                            i6++;
                        }
                        newArray6[i6] = new VastNonLinear();
                        input.readMessage(newArray6[i6]);
                        this.nonLinearAsset = newArray6;
                        break;
                    case 90:
                        int arrayLength7 = WireFormatNano.getRepeatedFieldArrayLength(input, 90);
                        VastTracking[] vastTrackingArr4 = this.nonLinearEventTracking;
                        int i7 = vastTrackingArr4 == null ? 0 : vastTrackingArr4.length;
                        VastTracking[] newArray7 = new VastTracking[(i7 + arrayLength7)];
                        if (i7 != 0) {
                            System.arraycopy(this.nonLinearEventTracking, 0, newArray7, 0, i7);
                        }
                        while (i7 < newArray7.length - 1) {
                            newArray7[i7] = new VastTracking();
                            input.readMessage(newArray7[i7]);
                            input.readTag();
                            i7++;
                        }
                        newArray7[i7] = new VastTracking();
                        input.readMessage(newArray7[i7]);
                        this.nonLinearEventTracking = newArray7;
                        break;
                    case 98:
                        this.redirectUrl = input.readString();
                        break;
                    case 104:
                        this.fallbackIndex = input.readInt32();
                        break;
                    case 112:
                        this.streamingMedia = input.readBool();
                        break;
                    case 122:
                        this.customParametersInstream = input.readString();
                        break;
                    case 130:
                        this.vastSchemaValidationErrors = input.readString();
                        break;
                    case 138:
                        int arrayLength8 = WireFormatNano.getRepeatedFieldArrayLength(input, 138);
                        VastImpression[] vastImpressionArr = this.impression;
                        int i8 = vastImpressionArr == null ? 0 : vastImpressionArr.length;
                        VastImpression[] newArray8 = new VastImpression[(i8 + arrayLength8)];
                        if (i8 != 0) {
                            System.arraycopy(this.impression, 0, newArray8, 0, i8);
                        }
                        while (i8 < newArray8.length - 1) {
                            newArray8[i8] = new VastImpression();
                            input.readMessage(newArray8[i8]);
                            input.readTag();
                            i8++;
                        }
                        newArray8[i8] = new VastImpression();
                        input.readMessage(newArray8[i8]);
                        this.impression = newArray8;
                        break;
                    case ClientAnalytics.LogRequest.LogSource.ON_THE_GO_COUNTERS_VALUE /*146*/:
                        this.f138id = input.readString();
                        break;
                    case ClientAnalytics.LogRequest.LogSource.YT_MAIN_APP_ANDROID_PRIMES_VALUE /*154*/:
                        this.adId = input.readString();
                        break;
                    case ClientAnalytics.LogRequest.LogSource.JAM_KIOSK_ANDROID_PRIMES_VALUE /*160*/:
                        this.vastVersion = input.readInt32();
                        break;
                    case ClientAnalytics.LogRequest.LogSource.SLIDES_ANDROID_PRIMES_VALUE /*168*/:
                        this.customSkipEventExists = input.readBool();
                        break;
                    case ClientAnalytics.LogRequest.LogSource.ANDROID_DIALER_VALUE /*176*/:
                        this.sequence = input.readInt32();
                        break;
                    default:
                        if (WireFormatNano.parseUnknownField(input, tag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }
    }

    public static final class VastCompanion extends MessageNano {
        private static volatile VastCompanion[] _emptyArray;
        public String apiFramework;
        public String destinationUrl;
        public VastTracking[] eventTracking;
        public int expandedHeight;
        public int expandedWidth;
        public int height;
        public String htmlResource;
        public String iframeResource;
        public String staticResource;
        public String type;
        public int width;

        public VastCompanion() {
            clear();
        }

        public static VastCompanion[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new VastCompanion[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static VastCompanion parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (VastCompanion) MessageNano.mergeFrom(new VastCompanion(), data);
        }

        public static VastCompanion parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new VastCompanion().mergeFrom(input);
        }

        public VastCompanion clear() {
            this.htmlResource = "";
            this.iframeResource = "";
            this.staticResource = "";
            this.type = "";
            this.destinationUrl = "";
            this.width = 0;
            this.height = 0;
            this.expandedWidth = 0;
            this.expandedHeight = 0;
            this.apiFramework = "";
            this.eventTracking = VastTracking.emptyArray();
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.htmlResource.equals("")) {
                output.writeString(1, this.htmlResource);
            }
            if (!this.iframeResource.equals("")) {
                output.writeString(2, this.iframeResource);
            }
            if (!this.staticResource.equals("")) {
                output.writeString(3, this.staticResource);
            }
            if (!this.type.equals("")) {
                output.writeString(4, this.type);
            }
            if (!this.destinationUrl.equals("")) {
                output.writeString(5, this.destinationUrl);
            }
            int i = this.width;
            if (i != 0) {
                output.writeInt32(6, i);
            }
            int i2 = this.height;
            if (i2 != 0) {
                output.writeInt32(7, i2);
            }
            int i3 = this.expandedWidth;
            if (i3 != 0) {
                output.writeInt32(8, i3);
            }
            int i4 = this.expandedHeight;
            if (i4 != 0) {
                output.writeInt32(9, i4);
            }
            if (!this.apiFramework.equals("")) {
                output.writeString(10, this.apiFramework);
            }
            VastTracking[] vastTrackingArr = this.eventTracking;
            if (vastTrackingArr != null && vastTrackingArr.length > 0) {
                int i5 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr2 = this.eventTracking;
                    if (i5 >= vastTrackingArr2.length) {
                        break;
                    }
                    VastTracking element = vastTrackingArr2[i5];
                    if (element != null) {
                        output.writeMessage(11, element);
                    }
                    i5++;
                }
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.htmlResource.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.htmlResource);
            }
            if (!this.iframeResource.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.iframeResource);
            }
            if (!this.staticResource.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(3, this.staticResource);
            }
            if (!this.type.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(4, this.type);
            }
            if (!this.destinationUrl.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(5, this.destinationUrl);
            }
            int i = this.width;
            if (i != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(6, i);
            }
            int i2 = this.height;
            if (i2 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(7, i2);
            }
            int i3 = this.expandedWidth;
            if (i3 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(8, i3);
            }
            int i4 = this.expandedHeight;
            if (i4 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(9, i4);
            }
            if (!this.apiFramework.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(10, this.apiFramework);
            }
            VastTracking[] vastTrackingArr = this.eventTracking;
            if (vastTrackingArr != null && vastTrackingArr.length > 0) {
                int i5 = 0;
                while (true) {
                    VastTracking[] vastTrackingArr2 = this.eventTracking;
                    if (i5 >= vastTrackingArr2.length) {
                        break;
                    }
                    VastTracking element = vastTrackingArr2[i5];
                    if (element != null) {
                        size += CodedOutputByteBufferNano.computeMessageSize(11, element);
                    }
                    i5++;
                }
            }
            return size;
        }

        public VastCompanion mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.htmlResource = input.readString();
                        break;
                    case 18:
                        this.iframeResource = input.readString();
                        break;
                    case 26:
                        this.staticResource = input.readString();
                        break;
                    case 34:
                        this.type = input.readString();
                        break;
                    case 42:
                        this.destinationUrl = input.readString();
                        break;
                    case 48:
                        this.width = input.readInt32();
                        break;
                    case 56:
                        this.height = input.readInt32();
                        break;
                    case 64:
                        this.expandedWidth = input.readInt32();
                        break;
                    case 72:
                        this.expandedHeight = input.readInt32();
                        break;
                    case 82:
                        this.apiFramework = input.readString();
                        break;
                    case 90:
                        int arrayLength = WireFormatNano.getRepeatedFieldArrayLength(input, 90);
                        VastTracking[] vastTrackingArr = this.eventTracking;
                        int i = vastTrackingArr == null ? 0 : vastTrackingArr.length;
                        VastTracking[] newArray = new VastTracking[(i + arrayLength)];
                        if (i != 0) {
                            System.arraycopy(this.eventTracking, 0, newArray, 0, i);
                        }
                        while (i < newArray.length - 1) {
                            newArray[i] = new VastTracking();
                            input.readMessage(newArray[i]);
                            input.readTag();
                            i++;
                        }
                        newArray[i] = new VastTracking();
                        input.readMessage(newArray[i]);
                        this.eventTracking = newArray;
                        break;
                    default:
                        if (WireFormatNano.parseUnknownField(input, tag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }
    }

    public static final class VastNonLinear extends MessageNano {
        private static volatile VastNonLinear[] _emptyArray;
        public String apiFramework;
        public String customParameters;
        public String destinationUrl;
        public int expandedHeight;
        public int expandedWidth;
        public int height;
        public String htmlResource;
        public String iframeResource;
        public boolean maintainAspectRatio;
        public int minSuggestedDuration;
        public boolean scalable;
        public String staticResource;
        public String type;
        public int width;

        public VastNonLinear() {
            clear();
        }

        public static VastNonLinear[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new VastNonLinear[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static VastNonLinear parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (VastNonLinear) MessageNano.mergeFrom(new VastNonLinear(), data);
        }

        public static VastNonLinear parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new VastNonLinear().mergeFrom(input);
        }

        public VastNonLinear clear() {
            this.htmlResource = "";
            this.iframeResource = "";
            this.staticResource = "";
            this.type = "";
            this.destinationUrl = "";
            this.width = 0;
            this.height = 0;
            this.expandedWidth = 0;
            this.expandedHeight = 0;
            this.apiFramework = "";
            this.scalable = false;
            this.maintainAspectRatio = false;
            this.minSuggestedDuration = 0;
            this.customParameters = "";
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.htmlResource.equals("")) {
                output.writeString(1, this.htmlResource);
            }
            if (!this.iframeResource.equals("")) {
                output.writeString(2, this.iframeResource);
            }
            if (!this.staticResource.equals("")) {
                output.writeString(3, this.staticResource);
            }
            if (!this.type.equals("")) {
                output.writeString(4, this.type);
            }
            if (!this.destinationUrl.equals("")) {
                output.writeString(5, this.destinationUrl);
            }
            int i = this.width;
            if (i != 0) {
                output.writeInt32(6, i);
            }
            int i2 = this.height;
            if (i2 != 0) {
                output.writeInt32(7, i2);
            }
            int i3 = this.expandedWidth;
            if (i3 != 0) {
                output.writeInt32(8, i3);
            }
            int i4 = this.expandedHeight;
            if (i4 != 0) {
                output.writeInt32(9, i4);
            }
            if (!this.apiFramework.equals("")) {
                output.writeString(10, this.apiFramework);
            }
            boolean z = this.scalable;
            if (z) {
                output.writeBool(11, z);
            }
            boolean z2 = this.maintainAspectRatio;
            if (z2) {
                output.writeBool(12, z2);
            }
            int i5 = this.minSuggestedDuration;
            if (i5 != 0) {
                output.writeInt32(13, i5);
            }
            if (!this.customParameters.equals("")) {
                output.writeString(14, this.customParameters);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.htmlResource.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.htmlResource);
            }
            if (!this.iframeResource.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(2, this.iframeResource);
            }
            if (!this.staticResource.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(3, this.staticResource);
            }
            if (!this.type.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(4, this.type);
            }
            if (!this.destinationUrl.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(5, this.destinationUrl);
            }
            int i = this.width;
            if (i != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(6, i);
            }
            int i2 = this.height;
            if (i2 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(7, i2);
            }
            int i3 = this.expandedWidth;
            if (i3 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(8, i3);
            }
            int i4 = this.expandedHeight;
            if (i4 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(9, i4);
            }
            if (!this.apiFramework.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(10, this.apiFramework);
            }
            boolean z = this.scalable;
            if (z) {
                size += CodedOutputByteBufferNano.computeBoolSize(11, z);
            }
            boolean z2 = this.maintainAspectRatio;
            if (z2) {
                size += CodedOutputByteBufferNano.computeBoolSize(12, z2);
            }
            int i5 = this.minSuggestedDuration;
            if (i5 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(13, i5);
            }
            if (!this.customParameters.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(14, this.customParameters);
            }
            return size;
        }

        public VastNonLinear mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                switch (tag) {
                    case 0:
                        return this;
                    case 10:
                        this.htmlResource = input.readString();
                        break;
                    case 18:
                        this.iframeResource = input.readString();
                        break;
                    case 26:
                        this.staticResource = input.readString();
                        break;
                    case 34:
                        this.type = input.readString();
                        break;
                    case 42:
                        this.destinationUrl = input.readString();
                        break;
                    case 48:
                        this.width = input.readInt32();
                        break;
                    case 56:
                        this.height = input.readInt32();
                        break;
                    case 64:
                        this.expandedWidth = input.readInt32();
                        break;
                    case 72:
                        this.expandedHeight = input.readInt32();
                        break;
                    case 82:
                        this.apiFramework = input.readString();
                        break;
                    case 88:
                        this.scalable = input.readBool();
                        break;
                    case 96:
                        this.maintainAspectRatio = input.readBool();
                        break;
                    case 104:
                        this.minSuggestedDuration = input.readInt32();
                        break;
                    case 114:
                        this.customParameters = input.readString();
                        break;
                    default:
                        if (WireFormatNano.parseUnknownField(input, tag)) {
                            break;
                        } else {
                            return this;
                        }
                }
            }
        }
    }

    public static final class VastMedia extends MessageNano {
        private static volatile VastMedia[] _emptyArray;
        public String apiFramework;
        public int bitrate;
        public String delivery;
        public int height;
        public boolean maintainAspectRatio;
        public boolean scalable;
        public String type;
        public String url;
        public int width;

        public VastMedia() {
            clear();
        }

        public static VastMedia[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new VastMedia[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static VastMedia parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (VastMedia) MessageNano.mergeFrom(new VastMedia(), data);
        }

        public static VastMedia parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new VastMedia().mergeFrom(input);
        }

        public VastMedia clear() {
            this.delivery = "";
            this.type = "";
            this.url = "";
            this.bitrate = 0;
            this.width = 0;
            this.height = 0;
            this.scalable = false;
            this.maintainAspectRatio = false;
            this.apiFramework = "";
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            output.writeString(1, this.delivery);
            output.writeString(2, this.type);
            output.writeString(3, this.url);
            int i = this.bitrate;
            if (i != 0) {
                output.writeInt32(4, i);
            }
            int i2 = this.width;
            if (i2 != 0) {
                output.writeInt32(5, i2);
            }
            int i3 = this.height;
            if (i3 != 0) {
                output.writeInt32(6, i3);
            }
            boolean z = this.scalable;
            if (z) {
                output.writeBool(7, z);
            }
            boolean z2 = this.maintainAspectRatio;
            if (z2) {
                output.writeBool(8, z2);
            }
            if (!this.apiFramework.equals("")) {
                output.writeString(9, this.apiFramework);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize() + CodedOutputByteBufferNano.computeStringSize(1, this.delivery) + CodedOutputByteBufferNano.computeStringSize(2, this.type) + CodedOutputByteBufferNano.computeStringSize(3, this.url);
            int i = this.bitrate;
            if (i != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(4, i);
            }
            int i2 = this.width;
            if (i2 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(5, i2);
            }
            int i3 = this.height;
            if (i3 != 0) {
                size += CodedOutputByteBufferNano.computeInt32Size(6, i3);
            }
            boolean z = this.scalable;
            if (z) {
                size += CodedOutputByteBufferNano.computeBoolSize(7, z);
            }
            boolean z2 = this.maintainAspectRatio;
            if (z2) {
                size += CodedOutputByteBufferNano.computeBoolSize(8, z2);
            }
            if (!this.apiFramework.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(9, this.apiFramework);
            }
            return size;
        }

        public VastMedia mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
                if (tag == 10) {
                    this.delivery = input.readString();
                } else if (tag == 18) {
                    this.type = input.readString();
                } else if (tag == 26) {
                    this.url = input.readString();
                } else if (tag == 32) {
                    this.bitrate = input.readInt32();
                } else if (tag == 40) {
                    this.width = input.readInt32();
                } else if (tag == 48) {
                    this.height = input.readInt32();
                } else if (tag == 56) {
                    this.scalable = input.readBool();
                } else if (tag == 64) {
                    this.maintainAspectRatio = input.readBool();
                } else if (tag == 74) {
                    this.apiFramework = input.readString();
                } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                    return this;
                }
            }
        }
    }

    public static final class VastImpression extends MessageNano {
        private static volatile VastImpression[] _emptyArray;

        /* renamed from: id */
        public String f137id;
        public String url;

        public VastImpression() {
            clear();
        }

        public static VastImpression[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new VastImpression[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static VastImpression parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (VastImpression) MessageNano.mergeFrom(new VastImpression(), data);
        }

        public static VastImpression parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new VastImpression().mergeFrom(input);
        }

        public VastImpression clear() {
            this.f137id = "";
            this.url = "";
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            if (!this.f137id.equals("")) {
                output.writeString(1, this.f137id);
            }
            if (!this.url.equals("")) {
                output.writeString(2, this.url);
            }
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            int size = super.computeSerializedSize();
            if (!this.f137id.equals("")) {
                size += CodedOutputByteBufferNano.computeStringSize(1, this.f137id);
            }
            if (!this.url.equals("")) {
                return size + CodedOutputByteBufferNano.computeStringSize(2, this.url);
            }
            return size;
        }

        public VastImpression mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
                if (tag == 10) {
                    this.f137id = input.readString();
                } else if (tag == 18) {
                    this.url = input.readString();
                } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                    return this;
                }
            }
        }
    }

    public static final class VastTracking extends MessageNano {
        private static volatile VastTracking[] _emptyArray;
        public String eventName;
        public String eventUrl;

        public VastTracking() {
            clear();
        }

        public static VastTracking[] emptyArray() {
            if (_emptyArray == null) {
                synchronized (InternalNano.LAZY_INIT_LOCK) {
                    if (_emptyArray == null) {
                        _emptyArray = new VastTracking[0];
                    }
                }
            }
            return _emptyArray;
        }

        public static VastTracking parseFrom(byte[] data) throws InvalidProtocolBufferNanoException {
            return (VastTracking) MessageNano.mergeFrom(new VastTracking(), data);
        }

        public static VastTracking parseFrom(CodedInputByteBufferNano input) throws IOException {
            return new VastTracking().mergeFrom(input);
        }

        public VastTracking clear() {
            this.eventName = "";
            this.eventUrl = "";
            this.cachedSize = -1;
            return this;
        }

        public void writeTo(CodedOutputByteBufferNano output) throws IOException {
            output.writeString(1, this.eventName);
            output.writeString(2, this.eventUrl);
            super.writeTo(output);
        }

        /* access modifiers changed from: protected */
        public int computeSerializedSize() {
            return super.computeSerializedSize() + CodedOutputByteBufferNano.computeStringSize(1, this.eventName) + CodedOutputByteBufferNano.computeStringSize(2, this.eventUrl);
        }

        public VastTracking mergeFrom(CodedInputByteBufferNano input) throws IOException {
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    return this;
                }
                if (tag == 10) {
                    this.eventName = input.readString();
                } else if (tag == 18) {
                    this.eventUrl = input.readString();
                } else if (!WireFormatNano.parseUnknownField(input, tag)) {
                    return this;
                }
            }
        }
    }
}
