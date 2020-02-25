package com.google.protos.datapol;

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

public final class RetentionAnnotations {

    private RetentionAnnotations() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum RetentionTag implements Internal.EnumLite {
        RT_DEFAULT(0),
        RT_OBSOLETE(1),
        RT_WEEK(10),
        RT_MONTH(20),
        RT_5_WEEKS(21),
        RT_QUARTER(30),
        RT_HALF_A_YEAR(40),
        RT_YEAR(50),
        RT_16_MONTHS(58),
        RT_YEAR_AND_A_HALF(60),
        RT_2_YEARS(70),
        RT_3_YEARS(80),
        RT_ARCHIVAL(1000);

        public static final int RT_16_MONTHS_VALUE = 58;
        public static final int RT_2_YEARS_VALUE = 70;
        public static final int RT_3_YEARS_VALUE = 80;
        public static final int RT_5_WEEKS_VALUE = 21;
        public static final int RT_ARCHIVAL_VALUE = 1000;
        public static final int RT_DEFAULT_VALUE = 0;
        public static final int RT_HALF_A_YEAR_VALUE = 40;
        public static final int RT_MONTH_VALUE = 20;
        public static final int RT_OBSOLETE_VALUE = 1;
        public static final int RT_QUARTER_VALUE = 30;
        public static final int RT_WEEK_VALUE = 10;
        public static final int RT_YEAR_AND_A_HALF_VALUE = 60;
        public static final int RT_YEAR_VALUE = 50;
        private static final Internal.EnumLiteMap<RetentionTag> internalValueMap = new Internal.EnumLiteMap<RetentionTag>() {
            public RetentionTag findValueByNumber(int number) {
                return RetentionTag.forNumber(number);
            }
        };
        private final int value;

        private RetentionTag(int value2) {
            this.value = value2;
        }

        public static RetentionTag forNumber(int value2) {
            if (value2 == 0) {
                return RT_DEFAULT;
            }
            if (value2 == 1) {
                return RT_OBSOLETE;
            }
            if (value2 == 10) {
                return RT_WEEK;
            }
            if (value2 == 30) {
                return RT_QUARTER;
            }
            if (value2 == 40) {
                return RT_HALF_A_YEAR;
            }
            if (value2 == 50) {
                return RT_YEAR;
            }
            if (value2 == 58) {
                return RT_16_MONTHS;
            }
            if (value2 == 60) {
                return RT_YEAR_AND_A_HALF;
            }
            if (value2 == 70) {
                return RT_2_YEARS;
            }
            if (value2 == 80) {
                return RT_3_YEARS;
            }
            if (value2 == 1000) {
                return RT_ARCHIVAL;
            }
            if (value2 == 20) {
                return RT_MONTH;
            }
            if (value2 != 21) {
                return null;
            }
            return RT_5_WEEKS;
        }

        public static Internal.EnumLiteMap<RetentionTag> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return RetentionTagVerifier.INSTANCE;
        }

        public final int getNumber() {
            return this.value;
        }

        private static final class RetentionTagVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new RetentionTagVerifier();

            private RetentionTagVerifier() {
            }

            public boolean isInRange(int number) {
                return RetentionTag.forNumber(number) != null;
            }
        }
    }

    public interface RetentionSpecOrBuilder extends MessageLiteOrBuilder {
        String getContext();

        ByteString getContextBytes();

        RetentionTag getDesiredRetention();

        boolean hasContext();

        boolean hasDesiredRetention();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class RetentionSpec extends GeneratedMessageLite<RetentionSpec, Builder> implements RetentionSpecOrBuilder {
        public static final int CONTEXT_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final RetentionSpec DEFAULT_INSTANCE = new RetentionSpec();
        public static final int DESIRED_RETENTION_FIELD_NUMBER = 1;
        private static volatile Parser<RetentionSpec> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(RetentionSpec.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String context_ = "*";
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int desiredRetention_;

        private RetentionSpec() {
        }

        public static RetentionSpec parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RetentionSpec parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RetentionSpec parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RetentionSpec parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RetentionSpec parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static RetentionSpec parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static RetentionSpec parseFrom(InputStream input) throws IOException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static RetentionSpec parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static RetentionSpec parseDelimitedFrom(InputStream input) throws IOException {
            return (RetentionSpec) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static RetentionSpec parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RetentionSpec) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static RetentionSpec parseFrom(CodedInputStream input) throws IOException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static RetentionSpec parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (RetentionSpec) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(RetentionSpec prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static RetentionSpec getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<RetentionSpec> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasDesiredRetention() {
            return (this.bitField0_ & 1) != 0;
        }

        public RetentionTag getDesiredRetention() {
            RetentionTag result = RetentionTag.forNumber(this.desiredRetention_);
            return result == null ? RetentionTag.RT_DEFAULT : result;
        }

        /* access modifiers changed from: private */
        public void setDesiredRetention(RetentionTag value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.desiredRetention_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearDesiredRetention() {
            this.bitField0_ &= -2;
            this.desiredRetention_ = 0;
        }

        public boolean hasContext() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getContext() {
            return this.context_;
        }

        /* access modifiers changed from: private */
        public void setContext(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.context_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getContextBytes() {
            return ByteString.copyFromUtf8(this.context_);
        }

        /* access modifiers changed from: private */
        public void setContextBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.context_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearContext() {
            this.bitField0_ &= -3;
            this.context_ = getDefaultInstance().getContext();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new RetentionSpec();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0000\u0002\b\u0001", new Object[]{"bitField0_", "desiredRetention_", RetentionTag.internalGetVerifier(), "context_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<RetentionSpec> parser = PARSER;
                    if (parser == null) {
                        synchronized (RetentionSpec.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<RetentionSpec, Builder> implements RetentionSpecOrBuilder {
            private Builder() {
                super(RetentionSpec.DEFAULT_INSTANCE);
            }

            public boolean hasDesiredRetention() {
                return ((RetentionSpec) this.instance).hasDesiredRetention();
            }

            public RetentionTag getDesiredRetention() {
                return ((RetentionSpec) this.instance).getDesiredRetention();
            }

            public Builder setDesiredRetention(RetentionTag value) {
                copyOnWrite();
                ((RetentionSpec) this.instance).setDesiredRetention(value);
                return this;
            }

            public Builder clearDesiredRetention() {
                copyOnWrite();
                ((RetentionSpec) this.instance).clearDesiredRetention();
                return this;
            }

            public boolean hasContext() {
                return ((RetentionSpec) this.instance).hasContext();
            }

            public String getContext() {
                return ((RetentionSpec) this.instance).getContext();
            }

            public Builder setContext(String value) {
                copyOnWrite();
                ((RetentionSpec) this.instance).setContext(value);
                return this;
            }

            public ByteString getContextBytes() {
                return ((RetentionSpec) this.instance).getContextBytes();
            }

            public Builder setContextBytes(ByteString value) {
                copyOnWrite();
                ((RetentionSpec) this.instance).setContextBytes(value);
                return this;
            }

            public Builder clearContext() {
                copyOnWrite();
                ((RetentionSpec) this.instance).clearContext();
                return this;
            }
        }
    }
}
