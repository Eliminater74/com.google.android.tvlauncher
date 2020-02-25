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

public final class CpuMetric {

    private CpuMetric() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface CpuUsageMetricOrBuilder extends MessageLiteOrBuilder {
        StackTrace getStackTraces(int i);

        int getStackTracesCount();

        List<StackTrace> getStackTracesList();
    }

    public interface StackElementOrBuilder extends MessageLiteOrBuilder {
        String getFunctionName();

        ByteString getFunctionNameBytes();

        boolean hasFunctionName();
    }

    public interface StackTraceOrBuilder extends MessageLiteOrBuilder {
        StackElement getStackElements(int i);

        int getStackElementsCount();

        List<StackElement> getStackElementsList();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CpuUsageMetric extends GeneratedMessageLite<CpuUsageMetric, Builder> implements CpuUsageMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final CpuUsageMetric DEFAULT_INSTANCE = new CpuUsageMetric();
        public static final int STACK_TRACES_FIELD_NUMBER = 1;
        private static volatile Parser<CpuUsageMetric> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(CpuUsageMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<StackTrace> stackTraces_ = emptyProtobufList();

        private CpuUsageMetric() {
        }

        public static CpuUsageMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CpuUsageMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CpuUsageMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CpuUsageMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CpuUsageMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CpuUsageMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CpuUsageMetric parseFrom(InputStream input) throws IOException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CpuUsageMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CpuUsageMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (CpuUsageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CpuUsageMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CpuUsageMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CpuUsageMetric parseFrom(CodedInputStream input) throws IOException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CpuUsageMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CpuUsageMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CpuUsageMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static CpuUsageMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CpuUsageMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public List<StackTrace> getStackTracesList() {
            return this.stackTraces_;
        }

        public List<? extends StackTraceOrBuilder> getStackTracesOrBuilderList() {
            return this.stackTraces_;
        }

        public int getStackTracesCount() {
            return this.stackTraces_.size();
        }

        public StackTrace getStackTraces(int index) {
            return this.stackTraces_.get(index);
        }

        public StackTraceOrBuilder getStackTracesOrBuilder(int index) {
            return this.stackTraces_.get(index);
        }

        private void ensureStackTracesIsMutable() {
            if (!this.stackTraces_.isModifiable()) {
                this.stackTraces_ = GeneratedMessageLite.mutableCopy(this.stackTraces_);
            }
        }

        /* access modifiers changed from: private */
        public void setStackTraces(int index, StackTrace value) {
            if (value != null) {
                ensureStackTracesIsMutable();
                this.stackTraces_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStackTraces(int index, StackTrace.Builder builderForValue) {
            ensureStackTracesIsMutable();
            this.stackTraces_.set(index, (StackTrace) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStackTraces(StackTrace value) {
            if (value != null) {
                ensureStackTracesIsMutable();
                this.stackTraces_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStackTraces(int index, StackTrace value) {
            if (value != null) {
                ensureStackTracesIsMutable();
                this.stackTraces_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStackTraces(StackTrace.Builder builderForValue) {
            ensureStackTracesIsMutable();
            this.stackTraces_.add((StackTrace) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStackTraces(int index, StackTrace.Builder builderForValue) {
            ensureStackTracesIsMutable();
            this.stackTraces_.add(index, (StackTrace) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.CpuMetric$StackTrace>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.CpuMetric$StackTrace>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllStackTraces(Iterable<? extends StackTrace> values) {
            ensureStackTracesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.stackTraces_);
        }

        /* access modifiers changed from: private */
        public void clearStackTraces() {
            this.stackTraces_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeStackTraces(int index) {
            ensureStackTracesIsMutable();
            this.stackTraces_.remove(index);
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CpuUsageMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"stackTraces_", StackTrace.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CpuUsageMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (CpuUsageMetric.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<CpuUsageMetric, Builder> implements CpuUsageMetricOrBuilder {
            private Builder() {
                super(CpuUsageMetric.DEFAULT_INSTANCE);
            }

            public List<StackTrace> getStackTracesList() {
                return Collections.unmodifiableList(((CpuUsageMetric) this.instance).getStackTracesList());
            }

            public int getStackTracesCount() {
                return ((CpuUsageMetric) this.instance).getStackTracesCount();
            }

            public StackTrace getStackTraces(int index) {
                return ((CpuUsageMetric) this.instance).getStackTraces(index);
            }

            public Builder setStackTraces(int index, StackTrace value) {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).setStackTraces(index, value);
                return this;
            }

            public Builder setStackTraces(int index, StackTrace.Builder builderForValue) {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).setStackTraces(index, builderForValue);
                return this;
            }

            public Builder addStackTraces(StackTrace value) {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).addStackTraces(value);
                return this;
            }

            public Builder addStackTraces(int index, StackTrace value) {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).addStackTraces(index, value);
                return this;
            }

            public Builder addStackTraces(StackTrace.Builder builderForValue) {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).addStackTraces(builderForValue);
                return this;
            }

            public Builder addStackTraces(int index, StackTrace.Builder builderForValue) {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).addStackTraces(index, builderForValue);
                return this;
            }

            public Builder addAllStackTraces(Iterable<? extends StackTrace> values) {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).addAllStackTraces(values);
                return this;
            }

            public Builder clearStackTraces() {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).clearStackTraces();
                return this;
            }

            public Builder removeStackTraces(int index) {
                copyOnWrite();
                ((CpuUsageMetric) this.instance).removeStackTraces(index);
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class StackTrace extends GeneratedMessageLite<StackTrace, Builder> implements StackTraceOrBuilder {
        /* access modifiers changed from: private */
        public static final StackTrace DEFAULT_INSTANCE = new StackTrace();
        public static final int STACK_ELEMENTS_FIELD_NUMBER = 1;
        private static volatile Parser<StackTrace> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(StackTrace.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<StackElement> stackElements_ = emptyProtobufList();

        private StackTrace() {
        }

        public static StackTrace parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StackTrace parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StackTrace parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StackTrace parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StackTrace parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StackTrace parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StackTrace parseFrom(InputStream input) throws IOException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StackTrace parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StackTrace parseDelimitedFrom(InputStream input) throws IOException {
            return (StackTrace) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static StackTrace parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StackTrace) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StackTrace parseFrom(CodedInputStream input) throws IOException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StackTrace parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StackTrace) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(StackTrace prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static StackTrace getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<StackTrace> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public List<StackElement> getStackElementsList() {
            return this.stackElements_;
        }

        public List<? extends StackElementOrBuilder> getStackElementsOrBuilderList() {
            return this.stackElements_;
        }

        public int getStackElementsCount() {
            return this.stackElements_.size();
        }

        public StackElement getStackElements(int index) {
            return this.stackElements_.get(index);
        }

        public StackElementOrBuilder getStackElementsOrBuilder(int index) {
            return this.stackElements_.get(index);
        }

        private void ensureStackElementsIsMutable() {
            if (!this.stackElements_.isModifiable()) {
                this.stackElements_ = GeneratedMessageLite.mutableCopy(this.stackElements_);
            }
        }

        /* access modifiers changed from: private */
        public void setStackElements(int index, StackElement value) {
            if (value != null) {
                ensureStackElementsIsMutable();
                this.stackElements_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setStackElements(int index, StackElement.Builder builderForValue) {
            ensureStackElementsIsMutable();
            this.stackElements_.set(index, (StackElement) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStackElements(StackElement value) {
            if (value != null) {
                ensureStackElementsIsMutable();
                this.stackElements_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStackElements(int index, StackElement value) {
            if (value != null) {
                ensureStackElementsIsMutable();
                this.stackElements_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addStackElements(StackElement.Builder builderForValue) {
            ensureStackElementsIsMutable();
            this.stackElements_.add((StackElement) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addStackElements(int index, StackElement.Builder builderForValue) {
            ensureStackElementsIsMutable();
            this.stackElements_.add(index, (StackElement) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.CpuMetric$StackElement>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.CpuMetric$StackElement>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllStackElements(Iterable<? extends StackElement> values) {
            ensureStackElementsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.stackElements_);
        }

        /* access modifiers changed from: private */
        public void clearStackElements() {
            this.stackElements_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeStackElements(int index) {
            ensureStackElementsIsMutable();
            this.stackElements_.remove(index);
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new StackTrace();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001b", new Object[]{"stackElements_", StackElement.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<StackTrace> parser = PARSER;
                    if (parser == null) {
                        synchronized (StackTrace.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<StackTrace, Builder> implements StackTraceOrBuilder {
            private Builder() {
                super(StackTrace.DEFAULT_INSTANCE);
            }

            public List<StackElement> getStackElementsList() {
                return Collections.unmodifiableList(((StackTrace) this.instance).getStackElementsList());
            }

            public int getStackElementsCount() {
                return ((StackTrace) this.instance).getStackElementsCount();
            }

            public StackElement getStackElements(int index) {
                return ((StackTrace) this.instance).getStackElements(index);
            }

            public Builder setStackElements(int index, StackElement value) {
                copyOnWrite();
                ((StackTrace) this.instance).setStackElements(index, value);
                return this;
            }

            public Builder setStackElements(int index, StackElement.Builder builderForValue) {
                copyOnWrite();
                ((StackTrace) this.instance).setStackElements(index, builderForValue);
                return this;
            }

            public Builder addStackElements(StackElement value) {
                copyOnWrite();
                ((StackTrace) this.instance).addStackElements(value);
                return this;
            }

            public Builder addStackElements(int index, StackElement value) {
                copyOnWrite();
                ((StackTrace) this.instance).addStackElements(index, value);
                return this;
            }

            public Builder addStackElements(StackElement.Builder builderForValue) {
                copyOnWrite();
                ((StackTrace) this.instance).addStackElements(builderForValue);
                return this;
            }

            public Builder addStackElements(int index, StackElement.Builder builderForValue) {
                copyOnWrite();
                ((StackTrace) this.instance).addStackElements(index, builderForValue);
                return this;
            }

            public Builder addAllStackElements(Iterable<? extends StackElement> values) {
                copyOnWrite();
                ((StackTrace) this.instance).addAllStackElements(values);
                return this;
            }

            public Builder clearStackElements() {
                copyOnWrite();
                ((StackTrace) this.instance).clearStackElements();
                return this;
            }

            public Builder removeStackElements(int index) {
                copyOnWrite();
                ((StackTrace) this.instance).removeStackElements(index);
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class StackElement extends GeneratedMessageLite<StackElement, Builder> implements StackElementOrBuilder {
        /* access modifiers changed from: private */
        public static final StackElement DEFAULT_INSTANCE = new StackElement();
        public static final int FUNCTION_NAME_FIELD_NUMBER = 1;
        private static volatile Parser<StackElement> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(StackElement.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String functionName_ = "";

        private StackElement() {
        }

        public static StackElement parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StackElement parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StackElement parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StackElement parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StackElement parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static StackElement parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static StackElement parseFrom(InputStream input) throws IOException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StackElement parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StackElement parseDelimitedFrom(InputStream input) throws IOException {
            return (StackElement) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static StackElement parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StackElement) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static StackElement parseFrom(CodedInputStream input) throws IOException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static StackElement parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (StackElement) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(StackElement prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static StackElement getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<StackElement> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasFunctionName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getFunctionName() {
            return this.functionName_;
        }

        /* access modifiers changed from: private */
        public void setFunctionName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.functionName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getFunctionNameBytes() {
            return ByteString.copyFromUtf8(this.functionName_);
        }

        /* access modifiers changed from: private */
        public void setFunctionNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.functionName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearFunctionName() {
            this.bitField0_ &= -2;
            this.functionName_ = getDefaultInstance().getFunctionName();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new StackElement();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0001\u0000\u0001\u0001\u0001\u0001\u0000\u0000\u0000\u0001\b\u0000", new Object[]{"bitField0_", "functionName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<StackElement> parser = PARSER;
                    if (parser == null) {
                        synchronized (StackElement.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<StackElement, Builder> implements StackElementOrBuilder {
            private Builder() {
                super(StackElement.DEFAULT_INSTANCE);
            }

            public boolean hasFunctionName() {
                return ((StackElement) this.instance).hasFunctionName();
            }

            public String getFunctionName() {
                return ((StackElement) this.instance).getFunctionName();
            }

            public Builder setFunctionName(String value) {
                copyOnWrite();
                ((StackElement) this.instance).setFunctionName(value);
                return this;
            }

            public ByteString getFunctionNameBytes() {
                return ((StackElement) this.instance).getFunctionNameBytes();
            }

            public Builder setFunctionNameBytes(ByteString value) {
                copyOnWrite();
                ((StackElement) this.instance).setFunctionNameBytes(value);
                return this;
            }

            public Builder clearFunctionName() {
                copyOnWrite();
                ((StackElement) this.instance).clearFunctionName();
                return this;
            }
        }
    }
}
