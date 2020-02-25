package com.google.common.logging;

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

public final class AccessibilityStateProto {

    private AccessibilityStateProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface AccessibilityStateOrBuilder extends MessageLiteOrBuilder {
        AccessibilityState.OnOff getBraillebackEnabled();

        AccessibilityState.OnOff getHighContrastTextEnabled();

        AccessibilityState.OnOff getJustspeakEnabled();

        AccessibilityState.OnOff getLargeTextEnabled();

        AccessibilityState.OnOff getMagnificationEnabled();

        AccessibilityState.OnOff getSwitchaccessEnabled();

        AccessibilityState.OnOff getTalkbackEnabled();

        boolean hasBraillebackEnabled();

        boolean hasHighContrastTextEnabled();

        boolean hasJustspeakEnabled();

        boolean hasLargeTextEnabled();

        boolean hasMagnificationEnabled();

        boolean hasSwitchaccessEnabled();

        boolean hasTalkbackEnabled();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AccessibilityState extends GeneratedMessageLite<AccessibilityState, Builder> implements AccessibilityStateOrBuilder {
        public static final int BRAILLEBACK_ENABLED_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final AccessibilityState DEFAULT_INSTANCE = new AccessibilityState();
        public static final int HIGH_CONTRAST_TEXT_ENABLED_FIELD_NUMBER = 7;
        public static final int JUSTSPEAK_ENABLED_FIELD_NUMBER = 4;
        public static final int LARGE_TEXT_ENABLED_FIELD_NUMBER = 5;
        public static final int MAGNIFICATION_ENABLED_FIELD_NUMBER = 6;
        public static final int SWITCHACCESS_ENABLED_FIELD_NUMBER = 3;
        public static final int TALKBACK_ENABLED_FIELD_NUMBER = 1;
        private static volatile Parser<AccessibilityState> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(AccessibilityState.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int braillebackEnabled_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private int highContrastTextEnabled_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int justspeakEnabled_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int largeTextEnabled_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int magnificationEnabled_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int switchaccessEnabled_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int talkbackEnabled_;

        private AccessibilityState() {
        }

        public static AccessibilityState parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AccessibilityState parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AccessibilityState parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AccessibilityState parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AccessibilityState parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AccessibilityState parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AccessibilityState parseFrom(InputStream input) throws IOException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AccessibilityState parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AccessibilityState parseDelimitedFrom(InputStream input) throws IOException {
            return (AccessibilityState) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AccessibilityState parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AccessibilityState) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AccessibilityState parseFrom(CodedInputStream input) throws IOException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AccessibilityState parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AccessibilityState) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AccessibilityState prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static AccessibilityState getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AccessibilityState> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasTalkbackEnabled() {
            return (this.bitField0_ & 1) != 0;
        }

        public OnOff getTalkbackEnabled() {
            OnOff result = OnOff.forNumber(this.talkbackEnabled_);
            return result == null ? OnOff.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setTalkbackEnabled(OnOff value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.talkbackEnabled_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTalkbackEnabled() {
            this.bitField0_ &= -2;
            this.talkbackEnabled_ = 0;
        }

        public boolean hasBraillebackEnabled() {
            return (this.bitField0_ & 2) != 0;
        }

        public OnOff getBraillebackEnabled() {
            OnOff result = OnOff.forNumber(this.braillebackEnabled_);
            return result == null ? OnOff.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setBraillebackEnabled(OnOff value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.braillebackEnabled_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearBraillebackEnabled() {
            this.bitField0_ &= -3;
            this.braillebackEnabled_ = 0;
        }

        public boolean hasSwitchaccessEnabled() {
            return (this.bitField0_ & 4) != 0;
        }

        public OnOff getSwitchaccessEnabled() {
            OnOff result = OnOff.forNumber(this.switchaccessEnabled_);
            return result == null ? OnOff.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setSwitchaccessEnabled(OnOff value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.switchaccessEnabled_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSwitchaccessEnabled() {
            this.bitField0_ &= -5;
            this.switchaccessEnabled_ = 0;
        }

        public boolean hasJustspeakEnabled() {
            return (this.bitField0_ & 8) != 0;
        }

        public OnOff getJustspeakEnabled() {
            OnOff result = OnOff.forNumber(this.justspeakEnabled_);
            return result == null ? OnOff.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setJustspeakEnabled(OnOff value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.justspeakEnabled_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearJustspeakEnabled() {
            this.bitField0_ &= -9;
            this.justspeakEnabled_ = 0;
        }

        public boolean hasLargeTextEnabled() {
            return (this.bitField0_ & 16) != 0;
        }

        public OnOff getLargeTextEnabled() {
            OnOff result = OnOff.forNumber(this.largeTextEnabled_);
            return result == null ? OnOff.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setLargeTextEnabled(OnOff value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.largeTextEnabled_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLargeTextEnabled() {
            this.bitField0_ &= -17;
            this.largeTextEnabled_ = 0;
        }

        public boolean hasMagnificationEnabled() {
            return (this.bitField0_ & 32) != 0;
        }

        public OnOff getMagnificationEnabled() {
            OnOff result = OnOff.forNumber(this.magnificationEnabled_);
            return result == null ? OnOff.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setMagnificationEnabled(OnOff value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.magnificationEnabled_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMagnificationEnabled() {
            this.bitField0_ &= -33;
            this.magnificationEnabled_ = 0;
        }

        public boolean hasHighContrastTextEnabled() {
            return (this.bitField0_ & 64) != 0;
        }

        public OnOff getHighContrastTextEnabled() {
            OnOff result = OnOff.forNumber(this.highContrastTextEnabled_);
            return result == null ? OnOff.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setHighContrastTextEnabled(OnOff value) {
            if (value != null) {
                this.bitField0_ |= 64;
                this.highContrastTextEnabled_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearHighContrastTextEnabled() {
            this.bitField0_ &= -65;
            this.highContrastTextEnabled_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AccessibilityState();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0007\u0000\u0001\u0001\u0007\u0007\u0000\u0000\u0000\u0001\f\u0000\u0002\f\u0001\u0003\f\u0002\u0004\f\u0003\u0005\f\u0004\u0006\f\u0005\u0007\f\u0006", new Object[]{"bitField0_", "talkbackEnabled_", OnOff.internalGetVerifier(), "braillebackEnabled_", OnOff.internalGetVerifier(), "switchaccessEnabled_", OnOff.internalGetVerifier(), "justspeakEnabled_", OnOff.internalGetVerifier(), "largeTextEnabled_", OnOff.internalGetVerifier(), "magnificationEnabled_", OnOff.internalGetVerifier(), "highContrastTextEnabled_", OnOff.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AccessibilityState> parser = PARSER;
                    if (parser == null) {
                        synchronized (AccessibilityState.class) {
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

        public enum OnOff implements Internal.EnumLite {
            UNKNOWN(0),
            ON(1),
            OFF(2);

            public static final int OFF_VALUE = 2;
            public static final int ON_VALUE = 1;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<OnOff> internalValueMap = new Internal.EnumLiteMap<OnOff>() {
                public OnOff findValueByNumber(int number) {
                    return OnOff.forNumber(number);
                }
            };
            private final int value;

            private OnOff(int value2) {
                this.value = value2;
            }

            public static OnOff forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return ON;
                }
                if (value2 != 2) {
                    return null;
                }
                return OFF;
            }

            public static Internal.EnumLiteMap<OnOff> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return OnOffVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class OnOffVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new OnOffVerifier();

                private OnOffVerifier() {
                }

                public boolean isInRange(int number) {
                    return OnOff.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<AccessibilityState, Builder> implements AccessibilityStateOrBuilder {
            private Builder() {
                super(AccessibilityState.DEFAULT_INSTANCE);
            }

            public boolean hasTalkbackEnabled() {
                return ((AccessibilityState) this.instance).hasTalkbackEnabled();
            }

            public OnOff getTalkbackEnabled() {
                return ((AccessibilityState) this.instance).getTalkbackEnabled();
            }

            public Builder setTalkbackEnabled(OnOff value) {
                copyOnWrite();
                ((AccessibilityState) this.instance).setTalkbackEnabled(value);
                return this;
            }

            public Builder clearTalkbackEnabled() {
                copyOnWrite();
                ((AccessibilityState) this.instance).clearTalkbackEnabled();
                return this;
            }

            public boolean hasBraillebackEnabled() {
                return ((AccessibilityState) this.instance).hasBraillebackEnabled();
            }

            public OnOff getBraillebackEnabled() {
                return ((AccessibilityState) this.instance).getBraillebackEnabled();
            }

            public Builder setBraillebackEnabled(OnOff value) {
                copyOnWrite();
                ((AccessibilityState) this.instance).setBraillebackEnabled(value);
                return this;
            }

            public Builder clearBraillebackEnabled() {
                copyOnWrite();
                ((AccessibilityState) this.instance).clearBraillebackEnabled();
                return this;
            }

            public boolean hasSwitchaccessEnabled() {
                return ((AccessibilityState) this.instance).hasSwitchaccessEnabled();
            }

            public OnOff getSwitchaccessEnabled() {
                return ((AccessibilityState) this.instance).getSwitchaccessEnabled();
            }

            public Builder setSwitchaccessEnabled(OnOff value) {
                copyOnWrite();
                ((AccessibilityState) this.instance).setSwitchaccessEnabled(value);
                return this;
            }

            public Builder clearSwitchaccessEnabled() {
                copyOnWrite();
                ((AccessibilityState) this.instance).clearSwitchaccessEnabled();
                return this;
            }

            public boolean hasJustspeakEnabled() {
                return ((AccessibilityState) this.instance).hasJustspeakEnabled();
            }

            public OnOff getJustspeakEnabled() {
                return ((AccessibilityState) this.instance).getJustspeakEnabled();
            }

            public Builder setJustspeakEnabled(OnOff value) {
                copyOnWrite();
                ((AccessibilityState) this.instance).setJustspeakEnabled(value);
                return this;
            }

            public Builder clearJustspeakEnabled() {
                copyOnWrite();
                ((AccessibilityState) this.instance).clearJustspeakEnabled();
                return this;
            }

            public boolean hasLargeTextEnabled() {
                return ((AccessibilityState) this.instance).hasLargeTextEnabled();
            }

            public OnOff getLargeTextEnabled() {
                return ((AccessibilityState) this.instance).getLargeTextEnabled();
            }

            public Builder setLargeTextEnabled(OnOff value) {
                copyOnWrite();
                ((AccessibilityState) this.instance).setLargeTextEnabled(value);
                return this;
            }

            public Builder clearLargeTextEnabled() {
                copyOnWrite();
                ((AccessibilityState) this.instance).clearLargeTextEnabled();
                return this;
            }

            public boolean hasMagnificationEnabled() {
                return ((AccessibilityState) this.instance).hasMagnificationEnabled();
            }

            public OnOff getMagnificationEnabled() {
                return ((AccessibilityState) this.instance).getMagnificationEnabled();
            }

            public Builder setMagnificationEnabled(OnOff value) {
                copyOnWrite();
                ((AccessibilityState) this.instance).setMagnificationEnabled(value);
                return this;
            }

            public Builder clearMagnificationEnabled() {
                copyOnWrite();
                ((AccessibilityState) this.instance).clearMagnificationEnabled();
                return this;
            }

            public boolean hasHighContrastTextEnabled() {
                return ((AccessibilityState) this.instance).hasHighContrastTextEnabled();
            }

            public OnOff getHighContrastTextEnabled() {
                return ((AccessibilityState) this.instance).getHighContrastTextEnabled();
            }

            public Builder setHighContrastTextEnabled(OnOff value) {
                copyOnWrite();
                ((AccessibilityState) this.instance).setHighContrastTextEnabled(value);
                return this;
            }

            public Builder clearHighContrastTextEnabled() {
                copyOnWrite();
                ((AccessibilityState) this.instance).clearHighContrastTextEnabled();
                return this;
            }
        }
    }
}
