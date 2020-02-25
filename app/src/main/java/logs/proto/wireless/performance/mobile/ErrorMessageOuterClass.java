package logs.proto.wireless.performance.mobile;

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

public final class ErrorMessageOuterClass {

    private ErrorMessageOuterClass() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface ErrorMessageOrBuilder extends MessageLiteOrBuilder {
        long getHashedStackTrace();

        ErrorMessage.Level getLevel();

        String getMessageFormat();

        ByteString getMessageFormatBytes();

        String getSourceClassName();

        ByteString getSourceClassNameBytes();

        String getSourceMethodName();

        ByteString getSourceMethodNameBytes();

        boolean hasHashedStackTrace();

        boolean hasLevel();

        boolean hasMessageFormat();

        boolean hasSourceClassName();

        boolean hasSourceMethodName();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ErrorMessage extends GeneratedMessageLite<ErrorMessage, Builder> implements ErrorMessageOrBuilder {
        /* access modifiers changed from: private */
        public static final ErrorMessage DEFAULT_INSTANCE = new ErrorMessage();
        public static final int HASHED_STACK_TRACE_FIELD_NUMBER = 5;
        public static final int LEVEL_FIELD_NUMBER = 2;
        public static final int MESSAGE_FORMAT_FIELD_NUMBER = 1;
        public static final int SOURCE_CLASS_NAME_FIELD_NUMBER = 3;
        public static final int SOURCE_METHOD_NAME_FIELD_NUMBER = 4;
        private static volatile Parser<ErrorMessage> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(ErrorMessage.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.FIXED64)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private long hashedStackTrace_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int level_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String messageFormat_ = "";
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String sourceClassName_ = "";
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String sourceMethodName_ = "";

        private ErrorMessage() {
        }

        public static ErrorMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ErrorMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ErrorMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ErrorMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ErrorMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ErrorMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ErrorMessage parseFrom(InputStream input) throws IOException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ErrorMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ErrorMessage parseDelimitedFrom(InputStream input) throws IOException {
            return (ErrorMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ErrorMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ErrorMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ErrorMessage parseFrom(CodedInputStream input) throws IOException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ErrorMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ErrorMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ErrorMessage prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static ErrorMessage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ErrorMessage> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasMessageFormat() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getMessageFormat() {
            return this.messageFormat_;
        }

        /* access modifiers changed from: private */
        public void setMessageFormat(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.messageFormat_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getMessageFormatBytes() {
            return ByteString.copyFromUtf8(this.messageFormat_);
        }

        /* access modifiers changed from: private */
        public void setMessageFormatBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.messageFormat_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMessageFormat() {
            this.bitField0_ &= -2;
            this.messageFormat_ = getDefaultInstance().getMessageFormat();
        }

        public boolean hasLevel() {
            return (this.bitField0_ & 2) != 0;
        }

        public Level getLevel() {
            Level result = Level.forNumber(this.level_);
            return result == null ? Level.LEVEL_UNSPECIFIED : result;
        }

        /* access modifiers changed from: private */
        public void setLevel(Level value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.level_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearLevel() {
            this.bitField0_ &= -3;
            this.level_ = 0;
        }

        public boolean hasSourceClassName() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getSourceClassName() {
            return this.sourceClassName_;
        }

        /* access modifiers changed from: private */
        public void setSourceClassName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.sourceClassName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getSourceClassNameBytes() {
            return ByteString.copyFromUtf8(this.sourceClassName_);
        }

        /* access modifiers changed from: private */
        public void setSourceClassNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.sourceClassName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSourceClassName() {
            this.bitField0_ &= -5;
            this.sourceClassName_ = getDefaultInstance().getSourceClassName();
        }

        public boolean hasSourceMethodName() {
            return (this.bitField0_ & 8) != 0;
        }

        public String getSourceMethodName() {
            return this.sourceMethodName_;
        }

        /* access modifiers changed from: private */
        public void setSourceMethodName(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.sourceMethodName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getSourceMethodNameBytes() {
            return ByteString.copyFromUtf8(this.sourceMethodName_);
        }

        /* access modifiers changed from: private */
        public void setSourceMethodNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.sourceMethodName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearSourceMethodName() {
            this.bitField0_ &= -9;
            this.sourceMethodName_ = getDefaultInstance().getSourceMethodName();
        }

        public boolean hasHashedStackTrace() {
            return (this.bitField0_ & 16) != 0;
        }

        public long getHashedStackTrace() {
            return this.hashedStackTrace_;
        }

        /* access modifiers changed from: private */
        public void setHashedStackTrace(long value) {
            this.bitField0_ |= 16;
            this.hashedStackTrace_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHashedStackTrace() {
            this.bitField0_ &= -17;
            this.hashedStackTrace_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ErrorMessage();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0000\u0000\u0001\b\u0000\u0002\f\u0001\u0003\b\u0002\u0004\b\u0003\u0005\u0005\u0004", new Object[]{"bitField0_", "messageFormat_", "level_", Level.internalGetVerifier(), "sourceClassName_", "sourceMethodName_", "hashedStackTrace_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ErrorMessage> parser = PARSER;
                    if (parser == null) {
                        synchronized (ErrorMessage.class) {
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

        public enum Level implements Internal.EnumLite {
            LEVEL_UNSPECIFIED(0),
            FINEST(1),
            FINER(2),
            FINE(3),
            CONFIG(4),
            INFO(5),
            NOTICE(6),
            WARNING(7),
            ERROR(8),
            SEVERE(9),
            FATAL(10);

            public static final int CONFIG_VALUE = 4;
            public static final int ERROR_VALUE = 8;
            public static final int FATAL_VALUE = 10;
            public static final int FINER_VALUE = 2;
            public static final int FINEST_VALUE = 1;
            public static final int FINE_VALUE = 3;
            public static final int INFO_VALUE = 5;
            public static final int LEVEL_UNSPECIFIED_VALUE = 0;
            public static final int NOTICE_VALUE = 6;
            public static final int SEVERE_VALUE = 9;
            public static final int WARNING_VALUE = 7;
            private static final Internal.EnumLiteMap<Level> internalValueMap = new Internal.EnumLiteMap<Level>() {
                public Level findValueByNumber(int number) {
                    return Level.forNumber(number);
                }
            };
            private final int value;

            private Level(int value2) {
                this.value = value2;
            }

            public static Level forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return LEVEL_UNSPECIFIED;
                    case 1:
                        return FINEST;
                    case 2:
                        return FINER;
                    case 3:
                        return FINE;
                    case 4:
                        return CONFIG;
                    case 5:
                        return INFO;
                    case 6:
                        return NOTICE;
                    case 7:
                        return WARNING;
                    case 8:
                        return ERROR;
                    case 9:
                        return SEVERE;
                    case 10:
                        return FATAL;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<Level> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return LevelVerifier.INSTANCE;
            }

            public final int getNumber() {
                return this.value;
            }

            private static final class LevelVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new LevelVerifier();

                private LevelVerifier() {
                }

                public boolean isInRange(int number) {
                    return Level.forNumber(number) != null;
                }
            }
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ErrorMessage, Builder> implements ErrorMessageOrBuilder {
            private Builder() {
                super(ErrorMessage.DEFAULT_INSTANCE);
            }

            public boolean hasMessageFormat() {
                return ((ErrorMessage) this.instance).hasMessageFormat();
            }

            public String getMessageFormat() {
                return ((ErrorMessage) this.instance).getMessageFormat();
            }

            public Builder setMessageFormat(String value) {
                copyOnWrite();
                ((ErrorMessage) this.instance).setMessageFormat(value);
                return this;
            }

            public ByteString getMessageFormatBytes() {
                return ((ErrorMessage) this.instance).getMessageFormatBytes();
            }

            public Builder setMessageFormatBytes(ByteString value) {
                copyOnWrite();
                ((ErrorMessage) this.instance).setMessageFormatBytes(value);
                return this;
            }

            public Builder clearMessageFormat() {
                copyOnWrite();
                ((ErrorMessage) this.instance).clearMessageFormat();
                return this;
            }

            public boolean hasLevel() {
                return ((ErrorMessage) this.instance).hasLevel();
            }

            public Level getLevel() {
                return ((ErrorMessage) this.instance).getLevel();
            }

            public Builder setLevel(Level value) {
                copyOnWrite();
                ((ErrorMessage) this.instance).setLevel(value);
                return this;
            }

            public Builder clearLevel() {
                copyOnWrite();
                ((ErrorMessage) this.instance).clearLevel();
                return this;
            }

            public boolean hasSourceClassName() {
                return ((ErrorMessage) this.instance).hasSourceClassName();
            }

            public String getSourceClassName() {
                return ((ErrorMessage) this.instance).getSourceClassName();
            }

            public Builder setSourceClassName(String value) {
                copyOnWrite();
                ((ErrorMessage) this.instance).setSourceClassName(value);
                return this;
            }

            public ByteString getSourceClassNameBytes() {
                return ((ErrorMessage) this.instance).getSourceClassNameBytes();
            }

            public Builder setSourceClassNameBytes(ByteString value) {
                copyOnWrite();
                ((ErrorMessage) this.instance).setSourceClassNameBytes(value);
                return this;
            }

            public Builder clearSourceClassName() {
                copyOnWrite();
                ((ErrorMessage) this.instance).clearSourceClassName();
                return this;
            }

            public boolean hasSourceMethodName() {
                return ((ErrorMessage) this.instance).hasSourceMethodName();
            }

            public String getSourceMethodName() {
                return ((ErrorMessage) this.instance).getSourceMethodName();
            }

            public Builder setSourceMethodName(String value) {
                copyOnWrite();
                ((ErrorMessage) this.instance).setSourceMethodName(value);
                return this;
            }

            public ByteString getSourceMethodNameBytes() {
                return ((ErrorMessage) this.instance).getSourceMethodNameBytes();
            }

            public Builder setSourceMethodNameBytes(ByteString value) {
                copyOnWrite();
                ((ErrorMessage) this.instance).setSourceMethodNameBytes(value);
                return this;
            }

            public Builder clearSourceMethodName() {
                copyOnWrite();
                ((ErrorMessage) this.instance).clearSourceMethodName();
                return this;
            }

            public boolean hasHashedStackTrace() {
                return ((ErrorMessage) this.instance).hasHashedStackTrace();
            }

            public long getHashedStackTrace() {
                return ((ErrorMessage) this.instance).getHashedStackTrace();
            }

            public Builder setHashedStackTrace(long value) {
                copyOnWrite();
                ((ErrorMessage) this.instance).setHashedStackTrace(value);
                return this;
            }

            public Builder clearHashedStackTrace() {
                copyOnWrite();
                ((ErrorMessage) this.instance).clearHashedStackTrace();
                return this;
            }
        }
    }
}
