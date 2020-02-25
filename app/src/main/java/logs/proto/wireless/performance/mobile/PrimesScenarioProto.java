package logs.proto.wireless.performance.mobile;

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

public final class PrimesScenarioProto {

    private PrimesScenarioProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface PrimesScenarioOrBuilder extends MessageLiteOrBuilder {
        String getEnd();

        ByteString getEndBytes();

        String getName();

        ByteString getNameBytes();

        String getStart();

        ByteString getStartBytes();

        boolean hasEnd();

        boolean hasName();

        boolean hasStart();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimesScenario extends GeneratedMessageLite<PrimesScenario, Builder> implements PrimesScenarioOrBuilder {
        /* access modifiers changed from: private */
        public static final PrimesScenario DEFAULT_INSTANCE = new PrimesScenario();
        public static final int END_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int START_FIELD_NUMBER = 2;
        private static volatile Parser<PrimesScenario> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(PrimesScenario.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String end_ = "";
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String name_ = "";
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String start_ = "";

        private PrimesScenario() {
        }

        public static PrimesScenario parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesScenario parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesScenario parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesScenario parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesScenario parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesScenario parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesScenario parseFrom(InputStream input) throws IOException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesScenario parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesScenario parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimesScenario) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesScenario parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesScenario) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesScenario parseFrom(CodedInputStream input) throws IOException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesScenario parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesScenario) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimesScenario prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static PrimesScenario getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimesScenario> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getName() {
            return this.name_;
        }

        /* access modifiers changed from: private */
        public void setName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getNameBytes() {
            return ByteString.copyFromUtf8(this.name_);
        }

        /* access modifiers changed from: private */
        public void setNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.name_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearName() {
            this.bitField0_ &= -2;
            this.name_ = getDefaultInstance().getName();
        }

        public boolean hasStart() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getStart() {
            return this.start_;
        }

        /* access modifiers changed from: private */
        public void setStart(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.start_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getStartBytes() {
            return ByteString.copyFromUtf8(this.start_);
        }

        /* access modifiers changed from: private */
        public void setStartBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.start_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearStart() {
            this.bitField0_ &= -3;
            this.start_ = getDefaultInstance().getStart();
        }

        public boolean hasEnd() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getEnd() {
            return this.end_;
        }

        /* access modifiers changed from: private */
        public void setEnd(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.end_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getEndBytes() {
            return ByteString.copyFromUtf8(this.end_);
        }

        /* access modifiers changed from: private */
        public void setEndBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.end_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearEnd() {
            this.bitField0_ &= -5;
            this.end_ = getDefaultInstance().getEnd();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimesScenario();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\b\u0000\u0002\b\u0001\u0003\b\u0002", new Object[]{"bitField0_", "name_", "start_", "end_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimesScenario> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimesScenario.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<PrimesScenario, Builder> implements PrimesScenarioOrBuilder {
            private Builder() {
                super(PrimesScenario.DEFAULT_INSTANCE);
            }

            public boolean hasName() {
                return ((PrimesScenario) this.instance).hasName();
            }

            public String getName() {
                return ((PrimesScenario) this.instance).getName();
            }

            public Builder setName(String value) {
                copyOnWrite();
                ((PrimesScenario) this.instance).setName(value);
                return this;
            }

            public ByteString getNameBytes() {
                return ((PrimesScenario) this.instance).getNameBytes();
            }

            public Builder setNameBytes(ByteString value) {
                copyOnWrite();
                ((PrimesScenario) this.instance).setNameBytes(value);
                return this;
            }

            public Builder clearName() {
                copyOnWrite();
                ((PrimesScenario) this.instance).clearName();
                return this;
            }

            public boolean hasStart() {
                return ((PrimesScenario) this.instance).hasStart();
            }

            public String getStart() {
                return ((PrimesScenario) this.instance).getStart();
            }

            public Builder setStart(String value) {
                copyOnWrite();
                ((PrimesScenario) this.instance).setStart(value);
                return this;
            }

            public ByteString getStartBytes() {
                return ((PrimesScenario) this.instance).getStartBytes();
            }

            public Builder setStartBytes(ByteString value) {
                copyOnWrite();
                ((PrimesScenario) this.instance).setStartBytes(value);
                return this;
            }

            public Builder clearStart() {
                copyOnWrite();
                ((PrimesScenario) this.instance).clearStart();
                return this;
            }

            public boolean hasEnd() {
                return ((PrimesScenario) this.instance).hasEnd();
            }

            public String getEnd() {
                return ((PrimesScenario) this.instance).getEnd();
            }

            public Builder setEnd(String value) {
                copyOnWrite();
                ((PrimesScenario) this.instance).setEnd(value);
                return this;
            }

            public ByteString getEndBytes() {
                return ((PrimesScenario) this.instance).getEndBytes();
            }

            public Builder setEndBytes(ByteString value) {
                copyOnWrite();
                ((PrimesScenario) this.instance).setEndBytes(value);
                return this;
            }

            public Builder clearEnd() {
                copyOnWrite();
                ((PrimesScenario) this.instance).clearEnd();
                return this;
            }
        }
    }
}
