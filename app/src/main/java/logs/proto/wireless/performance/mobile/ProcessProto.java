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

public final class ProcessProto {

    private ProcessProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface AndroidProcessStatsOrBuilder extends MessageLiteOrBuilder {
        boolean getIsInForeground();

        long getProcessElapsedTimeMs();

        @Deprecated
        String getProcessName();

        @Deprecated
        ByteString getProcessNameBytes();

        int getThreadCount();

        boolean hasIsInForeground();

        boolean hasProcessElapsedTimeMs();

        @Deprecated
        boolean hasProcessName();

        boolean hasThreadCount();
    }

    public interface IosProcessStatsOrBuilder extends MessageLiteOrBuilder {
    }

    public interface ProcessStatsOrBuilder extends MessageLiteOrBuilder {
        AndroidProcessStats getAndroidProcessStats();

        @Deprecated
        IosProcessStats getIosProcessStats();

        boolean hasAndroidProcessStats();

        @Deprecated
        boolean hasIosProcessStats();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ProcessStats extends GeneratedMessageLite<ProcessStats, Builder> implements ProcessStatsOrBuilder {
        public static final int ANDROID_PROCESS_STATS_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final ProcessStats DEFAULT_INSTANCE = new ProcessStats();
        public static final int IOS_PROCESS_STATS_FIELD_NUMBER = 2;
        private static volatile Parser<ProcessStats> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(ProcessStats.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private AndroidProcessStats androidProcessStats_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private IosProcessStats iosProcessStats_;

        private ProcessStats() {
        }

        public static ProcessStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ProcessStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ProcessStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ProcessStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ProcessStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ProcessStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ProcessStats parseFrom(InputStream input) throws IOException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ProcessStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ProcessStats parseDelimitedFrom(InputStream input) throws IOException {
            return (ProcessStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ProcessStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProcessStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ProcessStats parseFrom(CodedInputStream input) throws IOException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ProcessStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ProcessStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static ProcessStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ProcessStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasAndroidProcessStats() {
            return (this.bitField0_ & 1) != 0;
        }

        public AndroidProcessStats getAndroidProcessStats() {
            AndroidProcessStats androidProcessStats = this.androidProcessStats_;
            return androidProcessStats == null ? AndroidProcessStats.getDefaultInstance() : androidProcessStats;
        }

        /* access modifiers changed from: private */
        public void setAndroidProcessStats(AndroidProcessStats value) {
            if (value != null) {
                this.androidProcessStats_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAndroidProcessStats(AndroidProcessStats.Builder builderForValue) {
            this.androidProcessStats_ = (AndroidProcessStats) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeAndroidProcessStats(AndroidProcessStats value) {
            if (value != null) {
                AndroidProcessStats androidProcessStats = this.androidProcessStats_;
                if (androidProcessStats == null || androidProcessStats == AndroidProcessStats.getDefaultInstance()) {
                    this.androidProcessStats_ = value;
                } else {
                    this.androidProcessStats_ = (AndroidProcessStats) ((AndroidProcessStats.Builder) AndroidProcessStats.newBuilder(this.androidProcessStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAndroidProcessStats() {
            this.androidProcessStats_ = null;
            this.bitField0_ &= -2;
        }

        @Deprecated
        public boolean hasIosProcessStats() {
            return (this.bitField0_ & 2) != 0;
        }

        @Deprecated
        public IosProcessStats getIosProcessStats() {
            IosProcessStats iosProcessStats = this.iosProcessStats_;
            return iosProcessStats == null ? IosProcessStats.getDefaultInstance() : iosProcessStats;
        }

        /* access modifiers changed from: private */
        public void setIosProcessStats(IosProcessStats value) {
            if (value != null) {
                this.iosProcessStats_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setIosProcessStats(IosProcessStats.Builder builderForValue) {
            this.iosProcessStats_ = (IosProcessStats) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeIosProcessStats(IosProcessStats value) {
            if (value != null) {
                IosProcessStats iosProcessStats = this.iosProcessStats_;
                if (iosProcessStats == null || iosProcessStats == IosProcessStats.getDefaultInstance()) {
                    this.iosProcessStats_ = value;
                } else {
                    this.iosProcessStats_ = (IosProcessStats) ((IosProcessStats.Builder) IosProcessStats.newBuilder(this.iosProcessStats_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearIosProcessStats() {
            this.iosProcessStats_ = null;
            this.bitField0_ &= -3;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ProcessStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0000\u0002\t\u0001", new Object[]{"bitField0_", "androidProcessStats_", "iosProcessStats_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ProcessStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (ProcessStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<ProcessStats, Builder> implements ProcessStatsOrBuilder {
            private Builder() {
                super(ProcessStats.DEFAULT_INSTANCE);
            }

            public boolean hasAndroidProcessStats() {
                return ((ProcessStats) this.instance).hasAndroidProcessStats();
            }

            public AndroidProcessStats getAndroidProcessStats() {
                return ((ProcessStats) this.instance).getAndroidProcessStats();
            }

            public Builder setAndroidProcessStats(AndroidProcessStats value) {
                copyOnWrite();
                ((ProcessStats) this.instance).setAndroidProcessStats(value);
                return this;
            }

            public Builder setAndroidProcessStats(AndroidProcessStats.Builder builderForValue) {
                copyOnWrite();
                ((ProcessStats) this.instance).setAndroidProcessStats(builderForValue);
                return this;
            }

            public Builder mergeAndroidProcessStats(AndroidProcessStats value) {
                copyOnWrite();
                ((ProcessStats) this.instance).mergeAndroidProcessStats(value);
                return this;
            }

            public Builder clearAndroidProcessStats() {
                copyOnWrite();
                ((ProcessStats) this.instance).clearAndroidProcessStats();
                return this;
            }

            @Deprecated
            public boolean hasIosProcessStats() {
                return ((ProcessStats) this.instance).hasIosProcessStats();
            }

            @Deprecated
            public IosProcessStats getIosProcessStats() {
                return ((ProcessStats) this.instance).getIosProcessStats();
            }

            @Deprecated
            public Builder setIosProcessStats(IosProcessStats value) {
                copyOnWrite();
                ((ProcessStats) this.instance).setIosProcessStats(value);
                return this;
            }

            @Deprecated
            public Builder setIosProcessStats(IosProcessStats.Builder builderForValue) {
                copyOnWrite();
                ((ProcessStats) this.instance).setIosProcessStats(builderForValue);
                return this;
            }

            @Deprecated
            public Builder mergeIosProcessStats(IosProcessStats value) {
                copyOnWrite();
                ((ProcessStats) this.instance).mergeIosProcessStats(value);
                return this;
            }

            @Deprecated
            public Builder clearIosProcessStats() {
                copyOnWrite();
                ((ProcessStats) this.instance).clearIosProcessStats();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AndroidProcessStats extends GeneratedMessageLite<AndroidProcessStats, Builder> implements AndroidProcessStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final AndroidProcessStats DEFAULT_INSTANCE = new AndroidProcessStats();
        public static final int IS_IN_FOREGROUND_FIELD_NUMBER = 2;
        public static final int PROCESS_ELAPSED_TIME_MS_FIELD_NUMBER = 1;
        public static final int PROCESS_NAME_FIELD_NUMBER = 4;
        public static final int THREAD_COUNT_FIELD_NUMBER = 3;
        private static volatile Parser<AndroidProcessStats> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(AndroidProcessStats.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean isInForeground_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private long processElapsedTimeMs_;
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private String processName_ = "";
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int threadCount_;

        private AndroidProcessStats() {
        }

        public static AndroidProcessStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AndroidProcessStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AndroidProcessStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AndroidProcessStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AndroidProcessStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AndroidProcessStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AndroidProcessStats parseFrom(InputStream input) throws IOException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AndroidProcessStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AndroidProcessStats parseDelimitedFrom(InputStream input) throws IOException {
            return (AndroidProcessStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AndroidProcessStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AndroidProcessStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AndroidProcessStats parseFrom(CodedInputStream input) throws IOException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AndroidProcessStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AndroidProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AndroidProcessStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static AndroidProcessStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AndroidProcessStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasProcessElapsedTimeMs() {
            return (this.bitField0_ & 1) != 0;
        }

        public long getProcessElapsedTimeMs() {
            return this.processElapsedTimeMs_;
        }

        /* access modifiers changed from: private */
        public void setProcessElapsedTimeMs(long value) {
            this.bitField0_ |= 1;
            this.processElapsedTimeMs_ = value;
        }

        /* access modifiers changed from: private */
        public void clearProcessElapsedTimeMs() {
            this.bitField0_ &= -2;
            this.processElapsedTimeMs_ = 0;
        }

        public boolean hasIsInForeground() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getIsInForeground() {
            return this.isInForeground_;
        }

        /* access modifiers changed from: private */
        public void setIsInForeground(boolean value) {
            this.bitField0_ |= 2;
            this.isInForeground_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsInForeground() {
            this.bitField0_ &= -3;
            this.isInForeground_ = false;
        }

        public boolean hasThreadCount() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getThreadCount() {
            return this.threadCount_;
        }

        /* access modifiers changed from: private */
        public void setThreadCount(int value) {
            this.bitField0_ |= 4;
            this.threadCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearThreadCount() {
            this.bitField0_ &= -5;
            this.threadCount_ = 0;
        }

        @Deprecated
        public boolean hasProcessName() {
            return (this.bitField0_ & 8) != 0;
        }

        @Deprecated
        public String getProcessName() {
            return this.processName_;
        }

        /* access modifiers changed from: private */
        public void setProcessName(String value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.processName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        @Deprecated
        public ByteString getProcessNameBytes() {
            return ByteString.copyFromUtf8(this.processName_);
        }

        /* access modifiers changed from: private */
        public void setProcessNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.processName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProcessName() {
            this.bitField0_ &= -9;
            this.processName_ = getDefaultInstance().getProcessName();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AndroidProcessStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\u0002\u0000\u0002\u0007\u0001\u0003\u0004\u0002\u0004\b\u0003", new Object[]{"bitField0_", "processElapsedTimeMs_", "isInForeground_", "threadCount_", "processName_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AndroidProcessStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (AndroidProcessStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<AndroidProcessStats, Builder> implements AndroidProcessStatsOrBuilder {
            private Builder() {
                super(AndroidProcessStats.DEFAULT_INSTANCE);
            }

            public boolean hasProcessElapsedTimeMs() {
                return ((AndroidProcessStats) this.instance).hasProcessElapsedTimeMs();
            }

            public long getProcessElapsedTimeMs() {
                return ((AndroidProcessStats) this.instance).getProcessElapsedTimeMs();
            }

            public Builder setProcessElapsedTimeMs(long value) {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).setProcessElapsedTimeMs(value);
                return this;
            }

            public Builder clearProcessElapsedTimeMs() {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).clearProcessElapsedTimeMs();
                return this;
            }

            public boolean hasIsInForeground() {
                return ((AndroidProcessStats) this.instance).hasIsInForeground();
            }

            public boolean getIsInForeground() {
                return ((AndroidProcessStats) this.instance).getIsInForeground();
            }

            public Builder setIsInForeground(boolean value) {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).setIsInForeground(value);
                return this;
            }

            public Builder clearIsInForeground() {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).clearIsInForeground();
                return this;
            }

            public boolean hasThreadCount() {
                return ((AndroidProcessStats) this.instance).hasThreadCount();
            }

            public int getThreadCount() {
                return ((AndroidProcessStats) this.instance).getThreadCount();
            }

            public Builder setThreadCount(int value) {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).setThreadCount(value);
                return this;
            }

            public Builder clearThreadCount() {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).clearThreadCount();
                return this;
            }

            @Deprecated
            public boolean hasProcessName() {
                return ((AndroidProcessStats) this.instance).hasProcessName();
            }

            @Deprecated
            public String getProcessName() {
                return ((AndroidProcessStats) this.instance).getProcessName();
            }

            @Deprecated
            public Builder setProcessName(String value) {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).setProcessName(value);
                return this;
            }

            @Deprecated
            public ByteString getProcessNameBytes() {
                return ((AndroidProcessStats) this.instance).getProcessNameBytes();
            }

            @Deprecated
            public Builder setProcessNameBytes(ByteString value) {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).setProcessNameBytes(value);
                return this;
            }

            @Deprecated
            public Builder clearProcessName() {
                copyOnWrite();
                ((AndroidProcessStats) this.instance).clearProcessName();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class IosProcessStats extends GeneratedMessageLite<IosProcessStats, Builder> implements IosProcessStatsOrBuilder {
        /* access modifiers changed from: private */
        public static final IosProcessStats DEFAULT_INSTANCE = new IosProcessStats();
        private static volatile Parser<IosProcessStats> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(IosProcessStats.class, DEFAULT_INSTANCE);
        }

        private IosProcessStats() {
        }

        public static IosProcessStats parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosProcessStats parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosProcessStats parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosProcessStats parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosProcessStats parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static IosProcessStats parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static IosProcessStats parseFrom(InputStream input) throws IOException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static IosProcessStats parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static IosProcessStats parseDelimitedFrom(InputStream input) throws IOException {
            return (IosProcessStats) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static IosProcessStats parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosProcessStats) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static IosProcessStats parseFrom(CodedInputStream input) throws IOException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static IosProcessStats parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (IosProcessStats) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(IosProcessStats prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static IosProcessStats getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<IosProcessStats> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new IosProcessStats();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0000", null);
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<IosProcessStats> parser = PARSER;
                    if (parser == null) {
                        synchronized (IosProcessStats.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<IosProcessStats, Builder> implements IosProcessStatsOrBuilder {
            private Builder() {
                super(IosProcessStats.DEFAULT_INSTANCE);
            }
        }
    }
}
