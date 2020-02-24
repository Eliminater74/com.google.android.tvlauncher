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

public final class ExtensionCalendar {

    public interface CalendarExtensionOrBuilder extends MessageLiteOrBuilder {
        int getActiveExperiments(int i);

        int getActiveExperimentsCount();

        List<Integer> getActiveExperimentsList();

        String getRemoteFeatureConfig(int i);

        ByteString getRemoteFeatureConfigBytes(int i);

        int getRemoteFeatureConfigCount();

        List<String> getRemoteFeatureConfigList();

        int getResultCode();

        int getResultSource();

        CalendarExtension.ResultStatus getResultStatus();

        CalendarExtension.ViewScreenType getViewScreenType();

        boolean hasResultCode();

        boolean hasResultSource();

        boolean hasResultStatus();

        boolean hasViewScreenType();
    }

    private ExtensionCalendar() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CalendarExtension extends GeneratedMessageLite<CalendarExtension, Builder> implements CalendarExtensionOrBuilder {
        public static final int ACTIVE_EXPERIMENTS_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final CalendarExtension DEFAULT_INSTANCE = new CalendarExtension();
        private static volatile Parser<CalendarExtension> PARSER = null;
        public static final int REMOTE_FEATURE_CONFIG_FIELD_NUMBER = 5;
        public static final int RESULT_CODE_FIELD_NUMBER = 4;
        public static final int RESULT_SOURCE_FIELD_NUMBER = 3;
        public static final int RESULT_STATUS_FIELD_NUMBER = 2;
        public static final int VIEW_SCREEN_TYPE_FIELD_NUMBER = 6;
        @ProtoField(fieldNumber = 1, type = FieldType.INT32_LIST)
        private Internal.IntList activeExperiments_ = emptyIntList();
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 5, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> remoteFeatureConfig_ = GeneratedMessageLite.emptyProtobufList();
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int resultCode_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int resultSource_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int resultStatus_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int viewScreenType_;

        private CalendarExtension() {
        }

        public enum ResultStatus implements Internal.EnumLite {
            UNKNOWN(0),
            SUCCESS(1),
            FAILURE(2),
            CANCEL(3);
            
            public static final int CANCEL_VALUE = 3;
            public static final int FAILURE_VALUE = 2;
            public static final int SUCCESS_VALUE = 1;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<ResultStatus> internalValueMap = new Internal.EnumLiteMap<ResultStatus>() {
                public ResultStatus findValueByNumber(int number) {
                    return ResultStatus.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static ResultStatus forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return SUCCESS;
                }
                if (value2 == 2) {
                    return FAILURE;
                }
                if (value2 != 3) {
                    return null;
                }
                return CANCEL;
            }

            public static Internal.EnumLiteMap<ResultStatus> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return ResultStatusVerifier.INSTANCE;
            }

            private static final class ResultStatusVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new ResultStatusVerifier();

                private ResultStatusVerifier() {
                }

                public boolean isInRange(int number) {
                    return ResultStatus.forNumber(number) != null;
                }
            }

            private ResultStatus(int value2) {
                this.value = value2;
            }
        }

        public enum ViewScreenType implements Internal.EnumLite {
            VIEWSCREEN_UNKNOWN(0),
            EVENT(1),
            ICS(2),
            SMARTMAIL(3),
            GROOVE(4),
            REMINDER(5),
            BIRTHDAY(6);
            
            public static final int BIRTHDAY_VALUE = 6;
            public static final int EVENT_VALUE = 1;
            public static final int GROOVE_VALUE = 4;
            public static final int ICS_VALUE = 2;
            public static final int REMINDER_VALUE = 5;
            public static final int SMARTMAIL_VALUE = 3;
            public static final int VIEWSCREEN_UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<ViewScreenType> internalValueMap = new Internal.EnumLiteMap<ViewScreenType>() {
                public ViewScreenType findValueByNumber(int number) {
                    return ViewScreenType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static ViewScreenType forNumber(int value2) {
                switch (value2) {
                    case 0:
                        return VIEWSCREEN_UNKNOWN;
                    case 1:
                        return EVENT;
                    case 2:
                        return ICS;
                    case 3:
                        return SMARTMAIL;
                    case 4:
                        return GROOVE;
                    case 5:
                        return REMINDER;
                    case 6:
                        return BIRTHDAY;
                    default:
                        return null;
                }
            }

            public static Internal.EnumLiteMap<ViewScreenType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return ViewScreenTypeVerifier.INSTANCE;
            }

            private static final class ViewScreenTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new ViewScreenTypeVerifier();

                private ViewScreenTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return ViewScreenType.forNumber(number) != null;
                }
            }

            private ViewScreenType(int value2) {
                this.value = value2;
            }
        }

        public List<Integer> getActiveExperimentsList() {
            return this.activeExperiments_;
        }

        public int getActiveExperimentsCount() {
            return this.activeExperiments_.size();
        }

        public int getActiveExperiments(int index) {
            return this.activeExperiments_.getInt(index);
        }

        private void ensureActiveExperimentsIsMutable() {
            if (!this.activeExperiments_.isModifiable()) {
                this.activeExperiments_ = GeneratedMessageLite.mutableCopy(this.activeExperiments_);
            }
        }

        /* access modifiers changed from: private */
        public void setActiveExperiments(int index, int value) {
            ensureActiveExperimentsIsMutable();
            this.activeExperiments_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addActiveExperiments(int value) {
            ensureActiveExperimentsIsMutable();
            this.activeExperiments_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllActiveExperiments(Iterable<? extends Integer> values) {
            ensureActiveExperimentsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.activeExperiments_);
        }

        /* access modifiers changed from: private */
        public void clearActiveExperiments() {
            this.activeExperiments_ = emptyIntList();
        }

        public boolean hasResultStatus() {
            return (this.bitField0_ & 1) != 0;
        }

        public ResultStatus getResultStatus() {
            ResultStatus result = ResultStatus.forNumber(this.resultStatus_);
            return result == null ? ResultStatus.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setResultStatus(ResultStatus value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.resultStatus_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearResultStatus() {
            this.bitField0_ &= -2;
            this.resultStatus_ = 0;
        }

        public boolean hasResultSource() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getResultSource() {
            return this.resultSource_;
        }

        /* access modifiers changed from: private */
        public void setResultSource(int value) {
            this.bitField0_ |= 2;
            this.resultSource_ = value;
        }

        /* access modifiers changed from: private */
        public void clearResultSource() {
            this.bitField0_ &= -3;
            this.resultSource_ = 0;
        }

        public boolean hasResultCode() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getResultCode() {
            return this.resultCode_;
        }

        /* access modifiers changed from: private */
        public void setResultCode(int value) {
            this.bitField0_ |= 4;
            this.resultCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearResultCode() {
            this.bitField0_ &= -5;
            this.resultCode_ = 0;
        }

        public List<String> getRemoteFeatureConfigList() {
            return this.remoteFeatureConfig_;
        }

        public int getRemoteFeatureConfigCount() {
            return this.remoteFeatureConfig_.size();
        }

        public String getRemoteFeatureConfig(int index) {
            return this.remoteFeatureConfig_.get(index);
        }

        public ByteString getRemoteFeatureConfigBytes(int index) {
            return ByteString.copyFromUtf8(this.remoteFeatureConfig_.get(index));
        }

        private void ensureRemoteFeatureConfigIsMutable() {
            if (!this.remoteFeatureConfig_.isModifiable()) {
                this.remoteFeatureConfig_ = GeneratedMessageLite.mutableCopy(this.remoteFeatureConfig_);
            }
        }

        /* access modifiers changed from: private */
        public void setRemoteFeatureConfig(int index, String value) {
            if (value != null) {
                ensureRemoteFeatureConfigIsMutable();
                this.remoteFeatureConfig_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addRemoteFeatureConfig(String value) {
            if (value != null) {
                ensureRemoteFeatureConfigIsMutable();
                this.remoteFeatureConfig_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<java.lang.String>, com.google.protobuf.Internal$ProtobufList<java.lang.String>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllRemoteFeatureConfig(Iterable<String> values) {
            ensureRemoteFeatureConfigIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.remoteFeatureConfig_);
        }

        /* access modifiers changed from: private */
        public void clearRemoteFeatureConfig() {
            this.remoteFeatureConfig_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addRemoteFeatureConfigBytes(ByteString value) {
            if (value != null) {
                ensureRemoteFeatureConfigIsMutable();
                this.remoteFeatureConfig_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        public boolean hasViewScreenType() {
            return (this.bitField0_ & 8) != 0;
        }

        public ViewScreenType getViewScreenType() {
            ViewScreenType result = ViewScreenType.forNumber(this.viewScreenType_);
            return result == null ? ViewScreenType.VIEWSCREEN_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setViewScreenType(ViewScreenType value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.viewScreenType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearViewScreenType() {
            this.bitField0_ &= -9;
            this.viewScreenType_ = 0;
        }

        public static CalendarExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CalendarExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CalendarExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CalendarExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CalendarExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CalendarExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CalendarExtension parseFrom(InputStream input) throws IOException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CalendarExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CalendarExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (CalendarExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CalendarExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CalendarExtension parseFrom(CodedInputStream input) throws IOException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CalendarExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CalendarExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CalendarExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<CalendarExtension, Builder> implements CalendarExtensionOrBuilder {
            private Builder() {
                super(CalendarExtension.DEFAULT_INSTANCE);
            }

            public List<Integer> getActiveExperimentsList() {
                return Collections.unmodifiableList(((CalendarExtension) this.instance).getActiveExperimentsList());
            }

            public int getActiveExperimentsCount() {
                return ((CalendarExtension) this.instance).getActiveExperimentsCount();
            }

            public int getActiveExperiments(int index) {
                return ((CalendarExtension) this.instance).getActiveExperiments(index);
            }

            public Builder setActiveExperiments(int index, int value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).setActiveExperiments(index, value);
                return this;
            }

            public Builder addActiveExperiments(int value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).addActiveExperiments(value);
                return this;
            }

            public Builder addAllActiveExperiments(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((CalendarExtension) this.instance).addAllActiveExperiments(values);
                return this;
            }

            public Builder clearActiveExperiments() {
                copyOnWrite();
                ((CalendarExtension) this.instance).clearActiveExperiments();
                return this;
            }

            public boolean hasResultStatus() {
                return ((CalendarExtension) this.instance).hasResultStatus();
            }

            public ResultStatus getResultStatus() {
                return ((CalendarExtension) this.instance).getResultStatus();
            }

            public Builder setResultStatus(ResultStatus value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).setResultStatus(value);
                return this;
            }

            public Builder clearResultStatus() {
                copyOnWrite();
                ((CalendarExtension) this.instance).clearResultStatus();
                return this;
            }

            public boolean hasResultSource() {
                return ((CalendarExtension) this.instance).hasResultSource();
            }

            public int getResultSource() {
                return ((CalendarExtension) this.instance).getResultSource();
            }

            public Builder setResultSource(int value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).setResultSource(value);
                return this;
            }

            public Builder clearResultSource() {
                copyOnWrite();
                ((CalendarExtension) this.instance).clearResultSource();
                return this;
            }

            public boolean hasResultCode() {
                return ((CalendarExtension) this.instance).hasResultCode();
            }

            public int getResultCode() {
                return ((CalendarExtension) this.instance).getResultCode();
            }

            public Builder setResultCode(int value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).setResultCode(value);
                return this;
            }

            public Builder clearResultCode() {
                copyOnWrite();
                ((CalendarExtension) this.instance).clearResultCode();
                return this;
            }

            public List<String> getRemoteFeatureConfigList() {
                return Collections.unmodifiableList(((CalendarExtension) this.instance).getRemoteFeatureConfigList());
            }

            public int getRemoteFeatureConfigCount() {
                return ((CalendarExtension) this.instance).getRemoteFeatureConfigCount();
            }

            public String getRemoteFeatureConfig(int index) {
                return ((CalendarExtension) this.instance).getRemoteFeatureConfig(index);
            }

            public ByteString getRemoteFeatureConfigBytes(int index) {
                return ((CalendarExtension) this.instance).getRemoteFeatureConfigBytes(index);
            }

            public Builder setRemoteFeatureConfig(int index, String value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).setRemoteFeatureConfig(index, value);
                return this;
            }

            public Builder addRemoteFeatureConfig(String value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).addRemoteFeatureConfig(value);
                return this;
            }

            public Builder addAllRemoteFeatureConfig(Iterable<String> values) {
                copyOnWrite();
                ((CalendarExtension) this.instance).addAllRemoteFeatureConfig(values);
                return this;
            }

            public Builder clearRemoteFeatureConfig() {
                copyOnWrite();
                ((CalendarExtension) this.instance).clearRemoteFeatureConfig();
                return this;
            }

            public Builder addRemoteFeatureConfigBytes(ByteString value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).addRemoteFeatureConfigBytes(value);
                return this;
            }

            public boolean hasViewScreenType() {
                return ((CalendarExtension) this.instance).hasViewScreenType();
            }

            public ViewScreenType getViewScreenType() {
                return ((CalendarExtension) this.instance).getViewScreenType();
            }

            public Builder setViewScreenType(ViewScreenType value) {
                copyOnWrite();
                ((CalendarExtension) this.instance).setViewScreenType(value);
                return this;
            }

            public Builder clearViewScreenType() {
                copyOnWrite();
                ((CalendarExtension) this.instance).clearViewScreenType();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CalendarExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0002\u0000\u0001\u0016\u0002\f\u0000\u0003\u0004\u0001\u0004\u0004\u0002\u0005\u001a\u0006\f\u0003", new Object[]{"bitField0_", "activeExperiments_", "resultStatus_", ResultStatus.internalGetVerifier(), "resultSource_", "resultCode_", "remoteFeatureConfig_", "viewScreenType_", ViewScreenType.internalGetVerifier()});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CalendarExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (CalendarExtension.class) {
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
            GeneratedMessageLite.registerDefaultInstance(CalendarExtension.class, DEFAULT_INSTANCE);
        }

        public static CalendarExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CalendarExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
