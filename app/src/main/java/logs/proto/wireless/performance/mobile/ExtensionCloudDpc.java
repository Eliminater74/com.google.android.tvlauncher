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

import wireless.android.work.clouddpc.performance.schema.CommonEnums;

public final class ExtensionCloudDpc {

    private ExtensionCloudDpc() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public interface ActivityMetricOrBuilder extends MessageLiteOrBuilder {
        String getActivityStarted();

        ByteString getActivityStartedBytes();

        boolean getGooglerAccount();

        boolean getIsCancelled();

        boolean getIsDestroyed();

        boolean getIsWiped();

        boolean getLoginScopeTokenRevoked();

        boolean getPersonalAccountAdded();

        int getRequestCode();

        boolean getRestoreLaunched();

        boolean hasActivityStarted();

        boolean hasGooglerAccount();

        boolean hasIsCancelled();

        boolean hasIsDestroyed();

        boolean hasIsWiped();

        boolean hasLoginScopeTokenRevoked();

        boolean hasPersonalAccountAdded();

        boolean hasRequestCode();

        boolean hasRestoreLaunched();
    }

    public interface AppInstallMetricOrBuilder extends MessageLiteOrBuilder {
        InstallEvent getInstallEvent(int i);

        int getInstallEventCount();

        List<InstallEvent> getInstallEventList();

        int getMillisecondsSinceInstallWindowStart();

        int getNumOfPackagesInstalled();

        int getNumOfPackagesToInstall();

        boolean hasMillisecondsSinceInstallWindowStart();

        boolean hasNumOfPackagesInstalled();

        boolean hasNumOfPackagesToInstall();
    }

    public interface CloudDpcExtensionOrBuilder extends MessageLiteOrBuilder {
        ActivityMetric getActivityMetric();

        AppInstallMetric getAppInstallMetric();

        boolean getDevBuild();

        String getEmmId();

        ByteString getEmmIdBytes();

        String getEventName();

        ByteString getEventNameBytes();

        CommonEnums.EventState getEventState();

        CommonEnums.MetricType getMetricType(int i);

        int getMetricTypeCount();

        List<CommonEnums.MetricType> getMetricTypeList();

        CommonEnums.Mitigation getMitigation();

        CommonEnums.ProvisionEntryPoint getProvisionEntryPoint();

        CommonEnums.ProvisionMode getProvisionMode();

        SystemAppMetric getSystemAppMetric(int i);

        int getSystemAppMetricCount();

        List<SystemAppMetric> getSystemAppMetricList();

        TaskFailMetric getTaskFailMetric();

        boolean getUserDebugBuild();

        boolean hasActivityMetric();

        boolean hasAppInstallMetric();

        boolean hasDevBuild();

        boolean hasEmmId();

        boolean hasEventName();

        boolean hasEventState();

        boolean hasMitigation();

        boolean hasProvisionEntryPoint();

        boolean hasProvisionMode();

        boolean hasTaskFailMetric();

        boolean hasUserDebugBuild();
    }

    public interface InstallEventOrBuilder extends MessageLiteOrBuilder {
        float getApkSizeMb();

        int getInstallDurationMilliseconds();

        CommonEnums.InstallErrorReason getInstallErrorReason();

        boolean hasApkSizeMb();

        boolean hasInstallDurationMilliseconds();

        boolean hasInstallErrorReason();
    }

    public interface SystemAppMetricOrBuilder extends MessageLiteOrBuilder {
        CommonEnums.SmartSystemAppAction getAction();

        String getIntent();

        ByteString getIntentBytes();

        String getPackageName();

        ByteString getPackageNameBytes();

        boolean hasAction();

        boolean hasIntent();

        boolean hasPackageName();
    }

    public interface TaskFailMetricOrBuilder extends MessageLiteOrBuilder {
        String getExceptionCause();

        ByteString getExceptionCauseBytes();

        int getRetryCount();

        String getShrinkedStacktrace(int i);

        ByteString getShrinkedStacktraceBytes(int i);

        int getShrinkedStacktraceCount();

        List<String> getShrinkedStacktraceList();

        int getTaskFailureErrorCode();

        CommonEnums.TaskFailureReason getTaskFailureReason();

        CommonEnums.SetupTaskFailureType getTaskFailureType();

        boolean hasExceptionCause();

        boolean hasRetryCount();

        boolean hasTaskFailureErrorCode();

        boolean hasTaskFailureReason();

        boolean hasTaskFailureType();
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CloudDpcExtension extends GeneratedMessageLite<CloudDpcExtension, Builder> implements CloudDpcExtensionOrBuilder {
        public static final int ACTIVITY_METRIC_FIELD_NUMBER = 10;
        public static final int APP_INSTALL_METRIC_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final CloudDpcExtension DEFAULT_INSTANCE = new CloudDpcExtension();
        public static final int DEV_BUILD_FIELD_NUMBER = 12;
        public static final int EMM_ID_FIELD_NUMBER = 7;
        public static final int EVENT_NAME_FIELD_NUMBER = 4;
        public static final int EVENT_STATE_FIELD_NUMBER = 5;
        public static final int METRIC_TYPE_FIELD_NUMBER = 1;
        public static final int MITIGATION_FIELD_NUMBER = 6;
        public static final int PROVISION_ENTRY_POINT_FIELD_NUMBER = 9;
        public static final int PROVISION_MODE_FIELD_NUMBER = 3;
        public static final int SYSTEM_APP_METRIC_FIELD_NUMBER = 13;
        public static final int TASK_FAIL_METRIC_FIELD_NUMBER = 8;
        public static final int USER_DEBUG_BUILD_FIELD_NUMBER = 11;
        private static final Internal.ListAdapter.Converter<Integer, CommonEnums.MetricType> metricType_converter_ = new Internal.ListAdapter.Converter<Integer, CommonEnums.MetricType>() {
            public CommonEnums.MetricType convert(Integer from) {
                CommonEnums.MetricType result = CommonEnums.MetricType.forNumber(from.intValue());
                return result == null ? CommonEnums.MetricType.APP_INSTALL_METRIC : result;
            }
        };
        private static volatile Parser<CloudDpcExtension> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(CloudDpcExtension.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 10, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private ActivityMetric activityMetric_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private AppInstallMetric appInstallMetric_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 12, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1024, presenceBitsId = 0)
        private boolean devBuild_;
        @ProtoField(fieldNumber = 7, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private String emmId_ = "unspecified";
        @ProtoField(fieldNumber = 4, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String eventName_ = "";
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int eventState_;
        @ProtoField(fieldNumber = 1, type = FieldType.ENUM_LIST)
        private Internal.IntList metricType_ = emptyIntList();
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int mitigation_;
        @ProtoField(fieldNumber = 9, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int provisionEntryPoint_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int provisionMode_;
        @ProtoField(fieldNumber = 13, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<SystemAppMetric> systemAppMetric_ = emptyProtobufList();
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private TaskFailMetric taskFailMetric_;
        @ProtoField(fieldNumber = 11, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 512, presenceBitsId = 0)
        private boolean userDebugBuild_;

        private CloudDpcExtension() {
        }

        public static CloudDpcExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CloudDpcExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CloudDpcExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CloudDpcExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CloudDpcExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CloudDpcExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CloudDpcExtension parseFrom(InputStream input) throws IOException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CloudDpcExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CloudDpcExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (CloudDpcExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CloudDpcExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CloudDpcExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CloudDpcExtension parseFrom(CodedInputStream input) throws IOException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CloudDpcExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CloudDpcExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CloudDpcExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static CloudDpcExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CloudDpcExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public List<CommonEnums.MetricType> getMetricTypeList() {
            return new Internal.ListAdapter(this.metricType_, metricType_converter_);
        }

        public int getMetricTypeCount() {
            return this.metricType_.size();
        }

        public CommonEnums.MetricType getMetricType(int index) {
            return metricType_converter_.convert(Integer.valueOf(this.metricType_.getInt(index)));
        }

        private void ensureMetricTypeIsMutable() {
            if (!this.metricType_.isModifiable()) {
                this.metricType_ = GeneratedMessageLite.mutableCopy(this.metricType_);
            }
        }

        /* access modifiers changed from: private */
        public void setMetricType(int index, CommonEnums.MetricType value) {
            if (value != null) {
                ensureMetricTypeIsMutable();
                this.metricType_.setInt(index, value.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addMetricType(CommonEnums.MetricType value) {
            if (value != null) {
                ensureMetricTypeIsMutable();
                this.metricType_.addInt(value.getNumber());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addAllMetricType(Iterable<? extends CommonEnums.MetricType> values) {
            ensureMetricTypeIsMutable();
            for (CommonEnums.MetricType value : values) {
                this.metricType_.addInt(value.getNumber());
            }
        }

        /* access modifiers changed from: private */
        public void clearMetricType() {
            this.metricType_ = emptyIntList();
        }

        public boolean hasAppInstallMetric() {
            return (this.bitField0_ & 1) != 0;
        }

        public AppInstallMetric getAppInstallMetric() {
            AppInstallMetric appInstallMetric = this.appInstallMetric_;
            return appInstallMetric == null ? AppInstallMetric.getDefaultInstance() : appInstallMetric;
        }

        /* access modifiers changed from: private */
        public void setAppInstallMetric(AppInstallMetric value) {
            if (value != null) {
                this.appInstallMetric_ = value;
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setAppInstallMetric(AppInstallMetric.Builder builderForValue) {
            this.appInstallMetric_ = (AppInstallMetric) builderForValue.build();
            this.bitField0_ |= 1;
        }

        /* access modifiers changed from: private */
        public void mergeAppInstallMetric(AppInstallMetric value) {
            if (value != null) {
                AppInstallMetric appInstallMetric = this.appInstallMetric_;
                if (appInstallMetric == null || appInstallMetric == AppInstallMetric.getDefaultInstance()) {
                    this.appInstallMetric_ = value;
                } else {
                    this.appInstallMetric_ = (AppInstallMetric) ((AppInstallMetric.Builder) AppInstallMetric.newBuilder(this.appInstallMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 1;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAppInstallMetric() {
            this.appInstallMetric_ = null;
            this.bitField0_ &= -2;
        }

        public List<SystemAppMetric> getSystemAppMetricList() {
            return this.systemAppMetric_;
        }

        public List<? extends SystemAppMetricOrBuilder> getSystemAppMetricOrBuilderList() {
            return this.systemAppMetric_;
        }

        public int getSystemAppMetricCount() {
            return this.systemAppMetric_.size();
        }

        public SystemAppMetric getSystemAppMetric(int index) {
            return this.systemAppMetric_.get(index);
        }

        public SystemAppMetricOrBuilder getSystemAppMetricOrBuilder(int index) {
            return this.systemAppMetric_.get(index);
        }

        private void ensureSystemAppMetricIsMutable() {
            if (!this.systemAppMetric_.isModifiable()) {
                this.systemAppMetric_ = GeneratedMessageLite.mutableCopy(this.systemAppMetric_);
            }
        }

        /* access modifiers changed from: private */
        public void setSystemAppMetric(int index, SystemAppMetric value) {
            if (value != null) {
                ensureSystemAppMetricIsMutable();
                this.systemAppMetric_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setSystemAppMetric(int index, SystemAppMetric.Builder builderForValue) {
            ensureSystemAppMetricIsMutable();
            this.systemAppMetric_.set(index, (SystemAppMetric) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSystemAppMetric(SystemAppMetric value) {
            if (value != null) {
                ensureSystemAppMetricIsMutable();
                this.systemAppMetric_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSystemAppMetric(int index, SystemAppMetric value) {
            if (value != null) {
                ensureSystemAppMetricIsMutable();
                this.systemAppMetric_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addSystemAppMetric(SystemAppMetric.Builder builderForValue) {
            ensureSystemAppMetricIsMutable();
            this.systemAppMetric_.add((SystemAppMetric) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addSystemAppMetric(int index, SystemAppMetric.Builder builderForValue) {
            ensureSystemAppMetricIsMutable();
            this.systemAppMetric_.add(index, (SystemAppMetric) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.ExtensionCloudDpc$SystemAppMetric>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.ExtensionCloudDpc$SystemAppMetric>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllSystemAppMetric(Iterable<? extends SystemAppMetric> values) {
            ensureSystemAppMetricIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.systemAppMetric_);
        }

        /* access modifiers changed from: private */
        public void clearSystemAppMetric() {
            this.systemAppMetric_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeSystemAppMetric(int index) {
            ensureSystemAppMetricIsMutable();
            this.systemAppMetric_.remove(index);
        }

        public boolean hasProvisionMode() {
            return (this.bitField0_ & 2) != 0;
        }

        public CommonEnums.ProvisionMode getProvisionMode() {
            CommonEnums.ProvisionMode result = CommonEnums.ProvisionMode.forNumber(this.provisionMode_);
            return result == null ? CommonEnums.ProvisionMode.UNSPECIFIED_MODE : result;
        }

        /* access modifiers changed from: private */
        public void setProvisionMode(CommonEnums.ProvisionMode value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.provisionMode_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProvisionMode() {
            this.bitField0_ &= -3;
            this.provisionMode_ = 0;
        }

        public boolean hasEventName() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getEventName() {
            return this.eventName_;
        }

        /* access modifiers changed from: private */
        public void setEventName(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.eventName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getEventNameBytes() {
            return ByteString.copyFromUtf8(this.eventName_);
        }

        /* access modifiers changed from: private */
        public void setEventNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.eventName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearEventName() {
            this.bitField0_ &= -5;
            this.eventName_ = getDefaultInstance().getEventName();
        }

        public boolean hasEventState() {
            return (this.bitField0_ & 8) != 0;
        }

        public CommonEnums.EventState getEventState() {
            CommonEnums.EventState result = CommonEnums.EventState.forNumber(this.eventState_);
            return result == null ? CommonEnums.EventState.UNSPECIFIED_STATE : result;
        }

        /* access modifiers changed from: private */
        public void setEventState(CommonEnums.EventState value) {
            if (value != null) {
                this.bitField0_ |= 8;
                this.eventState_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearEventState() {
            this.bitField0_ &= -9;
            this.eventState_ = 0;
        }

        public boolean hasMitigation() {
            return (this.bitField0_ & 16) != 0;
        }

        public CommonEnums.Mitigation getMitigation() {
            CommonEnums.Mitigation result = CommonEnums.Mitigation.forNumber(this.mitigation_);
            return result == null ? CommonEnums.Mitigation.UNSPECIFIED_MITIGATION : result;
        }

        /* access modifiers changed from: private */
        public void setMitigation(CommonEnums.Mitigation value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.mitigation_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearMitigation() {
            this.bitField0_ &= -17;
            this.mitigation_ = 0;
        }

        public boolean hasEmmId() {
            return (this.bitField0_ & 32) != 0;
        }

        public String getEmmId() {
            return this.emmId_;
        }

        /* access modifiers changed from: private */
        public void setEmmId(String value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.emmId_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getEmmIdBytes() {
            return ByteString.copyFromUtf8(this.emmId_);
        }

        /* access modifiers changed from: private */
        public void setEmmIdBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 32;
                this.emmId_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearEmmId() {
            this.bitField0_ &= -33;
            this.emmId_ = getDefaultInstance().getEmmId();
        }

        public boolean hasTaskFailMetric() {
            return (this.bitField0_ & 64) != 0;
        }

        public TaskFailMetric getTaskFailMetric() {
            TaskFailMetric taskFailMetric = this.taskFailMetric_;
            return taskFailMetric == null ? TaskFailMetric.getDefaultInstance() : taskFailMetric;
        }

        /* access modifiers changed from: private */
        public void setTaskFailMetric(TaskFailMetric value) {
            if (value != null) {
                this.taskFailMetric_ = value;
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setTaskFailMetric(TaskFailMetric.Builder builderForValue) {
            this.taskFailMetric_ = (TaskFailMetric) builderForValue.build();
            this.bitField0_ |= 64;
        }

        /* access modifiers changed from: private */
        public void mergeTaskFailMetric(TaskFailMetric value) {
            if (value != null) {
                TaskFailMetric taskFailMetric = this.taskFailMetric_;
                if (taskFailMetric == null || taskFailMetric == TaskFailMetric.getDefaultInstance()) {
                    this.taskFailMetric_ = value;
                } else {
                    this.taskFailMetric_ = (TaskFailMetric) ((TaskFailMetric.Builder) TaskFailMetric.newBuilder(this.taskFailMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 64;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTaskFailMetric() {
            this.taskFailMetric_ = null;
            this.bitField0_ &= -65;
        }

        public boolean hasProvisionEntryPoint() {
            return (this.bitField0_ & 128) != 0;
        }

        public CommonEnums.ProvisionEntryPoint getProvisionEntryPoint() {
            CommonEnums.ProvisionEntryPoint result = CommonEnums.ProvisionEntryPoint.forNumber(this.provisionEntryPoint_);
            return result == null ? CommonEnums.ProvisionEntryPoint.UNSPECIFIED_PROVISION_ENTRY_POINT : result;
        }

        /* access modifiers changed from: private */
        public void setProvisionEntryPoint(CommonEnums.ProvisionEntryPoint value) {
            if (value != null) {
                this.bitField0_ |= 128;
                this.provisionEntryPoint_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearProvisionEntryPoint() {
            this.bitField0_ &= -129;
            this.provisionEntryPoint_ = 0;
        }

        public boolean hasActivityMetric() {
            return (this.bitField0_ & 256) != 0;
        }

        public ActivityMetric getActivityMetric() {
            ActivityMetric activityMetric = this.activityMetric_;
            return activityMetric == null ? ActivityMetric.getDefaultInstance() : activityMetric;
        }

        /* access modifiers changed from: private */
        public void setActivityMetric(ActivityMetric value) {
            if (value != null) {
                this.activityMetric_ = value;
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setActivityMetric(ActivityMetric.Builder builderForValue) {
            this.activityMetric_ = (ActivityMetric) builderForValue.build();
            this.bitField0_ |= 256;
        }

        /* access modifiers changed from: private */
        public void mergeActivityMetric(ActivityMetric value) {
            if (value != null) {
                ActivityMetric activityMetric = this.activityMetric_;
                if (activityMetric == null || activityMetric == ActivityMetric.getDefaultInstance()) {
                    this.activityMetric_ = value;
                } else {
                    this.activityMetric_ = (ActivityMetric) ((ActivityMetric.Builder) ActivityMetric.newBuilder(this.activityMetric_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 256;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearActivityMetric() {
            this.activityMetric_ = null;
            this.bitField0_ &= -257;
        }

        public boolean hasUserDebugBuild() {
            return (this.bitField0_ & 512) != 0;
        }

        public boolean getUserDebugBuild() {
            return this.userDebugBuild_;
        }

        /* access modifiers changed from: private */
        public void setUserDebugBuild(boolean value) {
            this.bitField0_ |= 512;
            this.userDebugBuild_ = value;
        }

        /* access modifiers changed from: private */
        public void clearUserDebugBuild() {
            this.bitField0_ &= -513;
            this.userDebugBuild_ = false;
        }

        public boolean hasDevBuild() {
            return (this.bitField0_ & 1024) != 0;
        }

        public boolean getDevBuild() {
            return this.devBuild_;
        }

        /* access modifiers changed from: private */
        public void setDevBuild(boolean value) {
            this.bitField0_ |= 1024;
            this.devBuild_ = value;
        }

        /* access modifiers changed from: private */
        public void clearDevBuild() {
            this.bitField0_ &= -1025;
            this.devBuild_ = false;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CloudDpcExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\r\u0000\u0001\u0001\r\r\u0000\u0002\u0000\u0001\u001e\u0002\t\u0000\u0003\f\u0001\u0004\b\u0002\u0005\f\u0003\u0006\f\u0004\u0007\b\u0005\b\t\u0006\t\f\u0007\n\t\b\u000b\u0007\t\f\u0007\n\r\u001b", new Object[]{"bitField0_", "metricType_", CommonEnums.MetricType.internalGetVerifier(), "appInstallMetric_", "provisionMode_", CommonEnums.ProvisionMode.internalGetVerifier(), "eventName_", "eventState_", CommonEnums.EventState.internalGetVerifier(), "mitigation_", CommonEnums.Mitigation.internalGetVerifier(), "emmId_", "taskFailMetric_", "provisionEntryPoint_", CommonEnums.ProvisionEntryPoint.internalGetVerifier(), "activityMetric_", "userDebugBuild_", "devBuild_", "systemAppMetric_", SystemAppMetric.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CloudDpcExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (CloudDpcExtension.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<CloudDpcExtension, Builder> implements CloudDpcExtensionOrBuilder {
            private Builder() {
                super(CloudDpcExtension.DEFAULT_INSTANCE);
            }

            public List<CommonEnums.MetricType> getMetricTypeList() {
                return ((CloudDpcExtension) this.instance).getMetricTypeList();
            }

            public int getMetricTypeCount() {
                return ((CloudDpcExtension) this.instance).getMetricTypeCount();
            }

            public CommonEnums.MetricType getMetricType(int index) {
                return ((CloudDpcExtension) this.instance).getMetricType(index);
            }

            public Builder setMetricType(int index, CommonEnums.MetricType value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setMetricType(index, value);
                return this;
            }

            public Builder addMetricType(CommonEnums.MetricType value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).addMetricType(value);
                return this;
            }

            public Builder addAllMetricType(Iterable<? extends CommonEnums.MetricType> values) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).addAllMetricType(values);
                return this;
            }

            public Builder clearMetricType() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearMetricType();
                return this;
            }

            public boolean hasAppInstallMetric() {
                return ((CloudDpcExtension) this.instance).hasAppInstallMetric();
            }

            public AppInstallMetric getAppInstallMetric() {
                return ((CloudDpcExtension) this.instance).getAppInstallMetric();
            }

            public Builder setAppInstallMetric(AppInstallMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setAppInstallMetric(value);
                return this;
            }

            public Builder setAppInstallMetric(AppInstallMetric.Builder builderForValue) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setAppInstallMetric(builderForValue);
                return this;
            }

            public Builder mergeAppInstallMetric(AppInstallMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).mergeAppInstallMetric(value);
                return this;
            }

            public Builder clearAppInstallMetric() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearAppInstallMetric();
                return this;
            }

            public List<SystemAppMetric> getSystemAppMetricList() {
                return Collections.unmodifiableList(((CloudDpcExtension) this.instance).getSystemAppMetricList());
            }

            public int getSystemAppMetricCount() {
                return ((CloudDpcExtension) this.instance).getSystemAppMetricCount();
            }

            public SystemAppMetric getSystemAppMetric(int index) {
                return ((CloudDpcExtension) this.instance).getSystemAppMetric(index);
            }

            public Builder setSystemAppMetric(int index, SystemAppMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setSystemAppMetric(index, value);
                return this;
            }

            public Builder setSystemAppMetric(int index, SystemAppMetric.Builder builderForValue) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setSystemAppMetric(index, builderForValue);
                return this;
            }

            public Builder addSystemAppMetric(SystemAppMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).addSystemAppMetric(value);
                return this;
            }

            public Builder addSystemAppMetric(int index, SystemAppMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).addSystemAppMetric(index, value);
                return this;
            }

            public Builder addSystemAppMetric(SystemAppMetric.Builder builderForValue) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).addSystemAppMetric(builderForValue);
                return this;
            }

            public Builder addSystemAppMetric(int index, SystemAppMetric.Builder builderForValue) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).addSystemAppMetric(index, builderForValue);
                return this;
            }

            public Builder addAllSystemAppMetric(Iterable<? extends SystemAppMetric> values) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).addAllSystemAppMetric(values);
                return this;
            }

            public Builder clearSystemAppMetric() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearSystemAppMetric();
                return this;
            }

            public Builder removeSystemAppMetric(int index) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).removeSystemAppMetric(index);
                return this;
            }

            public boolean hasProvisionMode() {
                return ((CloudDpcExtension) this.instance).hasProvisionMode();
            }

            public CommonEnums.ProvisionMode getProvisionMode() {
                return ((CloudDpcExtension) this.instance).getProvisionMode();
            }

            public Builder setProvisionMode(CommonEnums.ProvisionMode value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setProvisionMode(value);
                return this;
            }

            public Builder clearProvisionMode() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearProvisionMode();
                return this;
            }

            public boolean hasEventName() {
                return ((CloudDpcExtension) this.instance).hasEventName();
            }

            public String getEventName() {
                return ((CloudDpcExtension) this.instance).getEventName();
            }

            public Builder setEventName(String value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setEventName(value);
                return this;
            }

            public ByteString getEventNameBytes() {
                return ((CloudDpcExtension) this.instance).getEventNameBytes();
            }

            public Builder setEventNameBytes(ByteString value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setEventNameBytes(value);
                return this;
            }

            public Builder clearEventName() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearEventName();
                return this;
            }

            public boolean hasEventState() {
                return ((CloudDpcExtension) this.instance).hasEventState();
            }

            public CommonEnums.EventState getEventState() {
                return ((CloudDpcExtension) this.instance).getEventState();
            }

            public Builder setEventState(CommonEnums.EventState value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setEventState(value);
                return this;
            }

            public Builder clearEventState() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearEventState();
                return this;
            }

            public boolean hasMitigation() {
                return ((CloudDpcExtension) this.instance).hasMitigation();
            }

            public CommonEnums.Mitigation getMitigation() {
                return ((CloudDpcExtension) this.instance).getMitigation();
            }

            public Builder setMitigation(CommonEnums.Mitigation value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setMitigation(value);
                return this;
            }

            public Builder clearMitigation() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearMitigation();
                return this;
            }

            public boolean hasEmmId() {
                return ((CloudDpcExtension) this.instance).hasEmmId();
            }

            public String getEmmId() {
                return ((CloudDpcExtension) this.instance).getEmmId();
            }

            public Builder setEmmId(String value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setEmmId(value);
                return this;
            }

            public ByteString getEmmIdBytes() {
                return ((CloudDpcExtension) this.instance).getEmmIdBytes();
            }

            public Builder setEmmIdBytes(ByteString value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setEmmIdBytes(value);
                return this;
            }

            public Builder clearEmmId() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearEmmId();
                return this;
            }

            public boolean hasTaskFailMetric() {
                return ((CloudDpcExtension) this.instance).hasTaskFailMetric();
            }

            public TaskFailMetric getTaskFailMetric() {
                return ((CloudDpcExtension) this.instance).getTaskFailMetric();
            }

            public Builder setTaskFailMetric(TaskFailMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setTaskFailMetric(value);
                return this;
            }

            public Builder setTaskFailMetric(TaskFailMetric.Builder builderForValue) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setTaskFailMetric(builderForValue);
                return this;
            }

            public Builder mergeTaskFailMetric(TaskFailMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).mergeTaskFailMetric(value);
                return this;
            }

            public Builder clearTaskFailMetric() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearTaskFailMetric();
                return this;
            }

            public boolean hasProvisionEntryPoint() {
                return ((CloudDpcExtension) this.instance).hasProvisionEntryPoint();
            }

            public CommonEnums.ProvisionEntryPoint getProvisionEntryPoint() {
                return ((CloudDpcExtension) this.instance).getProvisionEntryPoint();
            }

            public Builder setProvisionEntryPoint(CommonEnums.ProvisionEntryPoint value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setProvisionEntryPoint(value);
                return this;
            }

            public Builder clearProvisionEntryPoint() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearProvisionEntryPoint();
                return this;
            }

            public boolean hasActivityMetric() {
                return ((CloudDpcExtension) this.instance).hasActivityMetric();
            }

            public ActivityMetric getActivityMetric() {
                return ((CloudDpcExtension) this.instance).getActivityMetric();
            }

            public Builder setActivityMetric(ActivityMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setActivityMetric(value);
                return this;
            }

            public Builder setActivityMetric(ActivityMetric.Builder builderForValue) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setActivityMetric(builderForValue);
                return this;
            }

            public Builder mergeActivityMetric(ActivityMetric value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).mergeActivityMetric(value);
                return this;
            }

            public Builder clearActivityMetric() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearActivityMetric();
                return this;
            }

            public boolean hasUserDebugBuild() {
                return ((CloudDpcExtension) this.instance).hasUserDebugBuild();
            }

            public boolean getUserDebugBuild() {
                return ((CloudDpcExtension) this.instance).getUserDebugBuild();
            }

            public Builder setUserDebugBuild(boolean value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setUserDebugBuild(value);
                return this;
            }

            public Builder clearUserDebugBuild() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearUserDebugBuild();
                return this;
            }

            public boolean hasDevBuild() {
                return ((CloudDpcExtension) this.instance).hasDevBuild();
            }

            public boolean getDevBuild() {
                return ((CloudDpcExtension) this.instance).getDevBuild();
            }

            public Builder setDevBuild(boolean value) {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).setDevBuild(value);
                return this;
            }

            public Builder clearDevBuild() {
                copyOnWrite();
                ((CloudDpcExtension) this.instance).clearDevBuild();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class AppInstallMetric extends GeneratedMessageLite<AppInstallMetric, Builder> implements AppInstallMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final AppInstallMetric DEFAULT_INSTANCE = new AppInstallMetric();
        public static final int INSTALL_EVENT_FIELD_NUMBER = 4;
        public static final int MILLISECONDS_SINCE_INSTALL_WINDOW_START_FIELD_NUMBER = 3;
        public static final int NUM_OF_PACKAGES_INSTALLED_FIELD_NUMBER = 2;
        public static final int NUM_OF_PACKAGES_TO_INSTALL_FIELD_NUMBER = 1;
        private static volatile Parser<AppInstallMetric> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(AppInstallMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<InstallEvent> installEvent_ = emptyProtobufList();
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int millisecondsSinceInstallWindowStart_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int numOfPackagesInstalled_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int numOfPackagesToInstall_;

        private AppInstallMetric() {
        }

        public static AppInstallMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppInstallMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppInstallMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppInstallMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppInstallMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static AppInstallMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static AppInstallMetric parseFrom(InputStream input) throws IOException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AppInstallMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AppInstallMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (AppInstallMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static AppInstallMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppInstallMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static AppInstallMetric parseFrom(CodedInputStream input) throws IOException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static AppInstallMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (AppInstallMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(AppInstallMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static AppInstallMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<AppInstallMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasNumOfPackagesToInstall() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getNumOfPackagesToInstall() {
            return this.numOfPackagesToInstall_;
        }

        /* access modifiers changed from: private */
        public void setNumOfPackagesToInstall(int value) {
            this.bitField0_ |= 1;
            this.numOfPackagesToInstall_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumOfPackagesToInstall() {
            this.bitField0_ &= -2;
            this.numOfPackagesToInstall_ = 0;
        }

        public boolean hasNumOfPackagesInstalled() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumOfPackagesInstalled() {
            return this.numOfPackagesInstalled_;
        }

        /* access modifiers changed from: private */
        public void setNumOfPackagesInstalled(int value) {
            this.bitField0_ |= 2;
            this.numOfPackagesInstalled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumOfPackagesInstalled() {
            this.bitField0_ &= -3;
            this.numOfPackagesInstalled_ = 0;
        }

        public boolean hasMillisecondsSinceInstallWindowStart() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getMillisecondsSinceInstallWindowStart() {
            return this.millisecondsSinceInstallWindowStart_;
        }

        /* access modifiers changed from: private */
        public void setMillisecondsSinceInstallWindowStart(int value) {
            this.bitField0_ |= 4;
            this.millisecondsSinceInstallWindowStart_ = value;
        }

        /* access modifiers changed from: private */
        public void clearMillisecondsSinceInstallWindowStart() {
            this.bitField0_ &= -5;
            this.millisecondsSinceInstallWindowStart_ = 0;
        }

        public List<InstallEvent> getInstallEventList() {
            return this.installEvent_;
        }

        public List<? extends InstallEventOrBuilder> getInstallEventOrBuilderList() {
            return this.installEvent_;
        }

        public int getInstallEventCount() {
            return this.installEvent_.size();
        }

        public InstallEvent getInstallEvent(int index) {
            return this.installEvent_.get(index);
        }

        public InstallEventOrBuilder getInstallEventOrBuilder(int index) {
            return this.installEvent_.get(index);
        }

        private void ensureInstallEventIsMutable() {
            if (!this.installEvent_.isModifiable()) {
                this.installEvent_ = GeneratedMessageLite.mutableCopy(this.installEvent_);
            }
        }

        /* access modifiers changed from: private */
        public void setInstallEvent(int index, InstallEvent value) {
            if (value != null) {
                ensureInstallEventIsMutable();
                this.installEvent_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setInstallEvent(int index, InstallEvent.Builder builderForValue) {
            ensureInstallEventIsMutable();
            this.installEvent_.set(index, (InstallEvent) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addInstallEvent(InstallEvent value) {
            if (value != null) {
                ensureInstallEventIsMutable();
                this.installEvent_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addInstallEvent(int index, InstallEvent value) {
            if (value != null) {
                ensureInstallEventIsMutable();
                this.installEvent_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addInstallEvent(InstallEvent.Builder builderForValue) {
            ensureInstallEventIsMutable();
            this.installEvent_.add((InstallEvent) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addInstallEvent(int index, InstallEvent.Builder builderForValue) {
            ensureInstallEventIsMutable();
            this.installEvent_.add(index, (InstallEvent) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.ExtensionCloudDpc$InstallEvent>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.ExtensionCloudDpc$InstallEvent>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllInstallEvent(Iterable<? extends InstallEvent> values) {
            ensureInstallEventIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.installEvent_);
        }

        /* access modifiers changed from: private */
        public void clearInstallEvent() {
            this.installEvent_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeInstallEvent(int index) {
            ensureInstallEventIsMutable();
            this.installEvent_.remove(index);
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new AppInstallMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0001\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0004\u0002\u0004\u001b", new Object[]{"bitField0_", "numOfPackagesToInstall_", "numOfPackagesInstalled_", "millisecondsSinceInstallWindowStart_", "installEvent_", InstallEvent.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<AppInstallMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (AppInstallMetric.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<AppInstallMetric, Builder> implements AppInstallMetricOrBuilder {
            private Builder() {
                super(AppInstallMetric.DEFAULT_INSTANCE);
            }

            public boolean hasNumOfPackagesToInstall() {
                return ((AppInstallMetric) this.instance).hasNumOfPackagesToInstall();
            }

            public int getNumOfPackagesToInstall() {
                return ((AppInstallMetric) this.instance).getNumOfPackagesToInstall();
            }

            public Builder setNumOfPackagesToInstall(int value) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).setNumOfPackagesToInstall(value);
                return this;
            }

            public Builder clearNumOfPackagesToInstall() {
                copyOnWrite();
                ((AppInstallMetric) this.instance).clearNumOfPackagesToInstall();
                return this;
            }

            public boolean hasNumOfPackagesInstalled() {
                return ((AppInstallMetric) this.instance).hasNumOfPackagesInstalled();
            }

            public int getNumOfPackagesInstalled() {
                return ((AppInstallMetric) this.instance).getNumOfPackagesInstalled();
            }

            public Builder setNumOfPackagesInstalled(int value) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).setNumOfPackagesInstalled(value);
                return this;
            }

            public Builder clearNumOfPackagesInstalled() {
                copyOnWrite();
                ((AppInstallMetric) this.instance).clearNumOfPackagesInstalled();
                return this;
            }

            public boolean hasMillisecondsSinceInstallWindowStart() {
                return ((AppInstallMetric) this.instance).hasMillisecondsSinceInstallWindowStart();
            }

            public int getMillisecondsSinceInstallWindowStart() {
                return ((AppInstallMetric) this.instance).getMillisecondsSinceInstallWindowStart();
            }

            public Builder setMillisecondsSinceInstallWindowStart(int value) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).setMillisecondsSinceInstallWindowStart(value);
                return this;
            }

            public Builder clearMillisecondsSinceInstallWindowStart() {
                copyOnWrite();
                ((AppInstallMetric) this.instance).clearMillisecondsSinceInstallWindowStart();
                return this;
            }

            public List<InstallEvent> getInstallEventList() {
                return Collections.unmodifiableList(((AppInstallMetric) this.instance).getInstallEventList());
            }

            public int getInstallEventCount() {
                return ((AppInstallMetric) this.instance).getInstallEventCount();
            }

            public InstallEvent getInstallEvent(int index) {
                return ((AppInstallMetric) this.instance).getInstallEvent(index);
            }

            public Builder setInstallEvent(int index, InstallEvent value) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).setInstallEvent(index, value);
                return this;
            }

            public Builder setInstallEvent(int index, InstallEvent.Builder builderForValue) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).setInstallEvent(index, builderForValue);
                return this;
            }

            public Builder addInstallEvent(InstallEvent value) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).addInstallEvent(value);
                return this;
            }

            public Builder addInstallEvent(int index, InstallEvent value) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).addInstallEvent(index, value);
                return this;
            }

            public Builder addInstallEvent(InstallEvent.Builder builderForValue) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).addInstallEvent(builderForValue);
                return this;
            }

            public Builder addInstallEvent(int index, InstallEvent.Builder builderForValue) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).addInstallEvent(index, builderForValue);
                return this;
            }

            public Builder addAllInstallEvent(Iterable<? extends InstallEvent> values) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).addAllInstallEvent(values);
                return this;
            }

            public Builder clearInstallEvent() {
                copyOnWrite();
                ((AppInstallMetric) this.instance).clearInstallEvent();
                return this;
            }

            public Builder removeInstallEvent(int index) {
                copyOnWrite();
                ((AppInstallMetric) this.instance).removeInstallEvent(index);
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class InstallEvent extends GeneratedMessageLite<InstallEvent, Builder> implements InstallEventOrBuilder {
        public static final int APK_SIZE_MB_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final InstallEvent DEFAULT_INSTANCE = new InstallEvent();
        public static final int INSTALL_DURATION_MILLISECONDS_FIELD_NUMBER = 3;
        public static final int INSTALL_ERROR_REASON_FIELD_NUMBER = 1;
        private static volatile Parser<InstallEvent> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(InstallEvent.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.FLOAT)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private float apkSizeMb_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int installDurationMilliseconds_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int installErrorReason_;

        private InstallEvent() {
        }

        public static InstallEvent parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InstallEvent parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InstallEvent parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InstallEvent parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InstallEvent parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static InstallEvent parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static InstallEvent parseFrom(InputStream input) throws IOException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static InstallEvent parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static InstallEvent parseDelimitedFrom(InputStream input) throws IOException {
            return (InstallEvent) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static InstallEvent parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InstallEvent) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static InstallEvent parseFrom(CodedInputStream input) throws IOException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static InstallEvent parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (InstallEvent) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(InstallEvent prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static InstallEvent getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<InstallEvent> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasInstallErrorReason() {
            return (this.bitField0_ & 1) != 0;
        }

        public CommonEnums.InstallErrorReason getInstallErrorReason() {
            CommonEnums.InstallErrorReason result = CommonEnums.InstallErrorReason.forNumber(this.installErrorReason_);
            return result == null ? CommonEnums.InstallErrorReason.REASON_UNSPECIFIED_ERROR : result;
        }

        /* access modifiers changed from: private */
        public void setInstallErrorReason(CommonEnums.InstallErrorReason value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.installErrorReason_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearInstallErrorReason() {
            this.bitField0_ &= -2;
            this.installErrorReason_ = 0;
        }

        public boolean hasApkSizeMb() {
            return (this.bitField0_ & 2) != 0;
        }

        public float getApkSizeMb() {
            return this.apkSizeMb_;
        }

        /* access modifiers changed from: private */
        public void setApkSizeMb(float value) {
            this.bitField0_ |= 2;
            this.apkSizeMb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearApkSizeMb() {
            this.bitField0_ &= -3;
            this.apkSizeMb_ = 0.0f;
        }

        public boolean hasInstallDurationMilliseconds() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getInstallDurationMilliseconds() {
            return this.installDurationMilliseconds_;
        }

        /* access modifiers changed from: private */
        public void setInstallDurationMilliseconds(int value) {
            this.bitField0_ |= 4;
            this.installDurationMilliseconds_ = value;
        }

        /* access modifiers changed from: private */
        public void clearInstallDurationMilliseconds() {
            this.bitField0_ &= -5;
            this.installDurationMilliseconds_ = 0;
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new InstallEvent();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\f\u0000\u0002\u0001\u0001\u0003\u0004\u0002", new Object[]{"bitField0_", "installErrorReason_", CommonEnums.InstallErrorReason.internalGetVerifier(), "apkSizeMb_", "installDurationMilliseconds_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<InstallEvent> parser = PARSER;
                    if (parser == null) {
                        synchronized (InstallEvent.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<InstallEvent, Builder> implements InstallEventOrBuilder {
            private Builder() {
                super(InstallEvent.DEFAULT_INSTANCE);
            }

            public boolean hasInstallErrorReason() {
                return ((InstallEvent) this.instance).hasInstallErrorReason();
            }

            public CommonEnums.InstallErrorReason getInstallErrorReason() {
                return ((InstallEvent) this.instance).getInstallErrorReason();
            }

            public Builder setInstallErrorReason(CommonEnums.InstallErrorReason value) {
                copyOnWrite();
                ((InstallEvent) this.instance).setInstallErrorReason(value);
                return this;
            }

            public Builder clearInstallErrorReason() {
                copyOnWrite();
                ((InstallEvent) this.instance).clearInstallErrorReason();
                return this;
            }

            public boolean hasApkSizeMb() {
                return ((InstallEvent) this.instance).hasApkSizeMb();
            }

            public float getApkSizeMb() {
                return ((InstallEvent) this.instance).getApkSizeMb();
            }

            public Builder setApkSizeMb(float value) {
                copyOnWrite();
                ((InstallEvent) this.instance).setApkSizeMb(value);
                return this;
            }

            public Builder clearApkSizeMb() {
                copyOnWrite();
                ((InstallEvent) this.instance).clearApkSizeMb();
                return this;
            }

            public boolean hasInstallDurationMilliseconds() {
                return ((InstallEvent) this.instance).hasInstallDurationMilliseconds();
            }

            public int getInstallDurationMilliseconds() {
                return ((InstallEvent) this.instance).getInstallDurationMilliseconds();
            }

            public Builder setInstallDurationMilliseconds(int value) {
                copyOnWrite();
                ((InstallEvent) this.instance).setInstallDurationMilliseconds(value);
                return this;
            }

            public Builder clearInstallDurationMilliseconds() {
                copyOnWrite();
                ((InstallEvent) this.instance).clearInstallDurationMilliseconds();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class TaskFailMetric extends GeneratedMessageLite<TaskFailMetric, Builder> implements TaskFailMetricOrBuilder {
        /* access modifiers changed from: private */
        public static final TaskFailMetric DEFAULT_INSTANCE = new TaskFailMetric();
        public static final int EXCEPTION_CAUSE_FIELD_NUMBER = 5;
        public static final int RETRY_COUNT_FIELD_NUMBER = 4;
        public static final int SHRINKED_STACKTRACE_FIELD_NUMBER = 6;
        public static final int TASK_FAILURE_ERROR_CODE_FIELD_NUMBER = 2;
        public static final int TASK_FAILURE_REASON_FIELD_NUMBER = 1;
        public static final int TASK_FAILURE_TYPE_FIELD_NUMBER = 3;
        private static volatile Parser<TaskFailMetric> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(TaskFailMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 5, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private String exceptionCause_ = "";
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int retryCount_;
        @ProtoField(fieldNumber = 6, isEnforceUtf8 = false, type = FieldType.STRING_LIST)
        private Internal.ProtobufList<String> shrinkedStacktrace_ = GeneratedMessageLite.emptyProtobufList();
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int taskFailureErrorCode_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int taskFailureReason_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int taskFailureType_;

        private TaskFailMetric() {
        }

        public static TaskFailMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TaskFailMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TaskFailMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TaskFailMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TaskFailMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static TaskFailMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static TaskFailMetric parseFrom(InputStream input) throws IOException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TaskFailMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TaskFailMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (TaskFailMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static TaskFailMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TaskFailMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static TaskFailMetric parseFrom(CodedInputStream input) throws IOException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static TaskFailMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (TaskFailMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(TaskFailMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static TaskFailMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<TaskFailMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasTaskFailureReason() {
            return (this.bitField0_ & 1) != 0;
        }

        public CommonEnums.TaskFailureReason getTaskFailureReason() {
            CommonEnums.TaskFailureReason result = CommonEnums.TaskFailureReason.forNumber(this.taskFailureReason_);
            return result == null ? CommonEnums.TaskFailureReason.FAILURE_REASON_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setTaskFailureReason(CommonEnums.TaskFailureReason value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.taskFailureReason_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTaskFailureReason() {
            this.bitField0_ &= -2;
            this.taskFailureReason_ = 0;
        }

        public boolean hasTaskFailureErrorCode() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getTaskFailureErrorCode() {
            return this.taskFailureErrorCode_;
        }

        /* access modifiers changed from: private */
        public void setTaskFailureErrorCode(int value) {
            this.bitField0_ |= 2;
            this.taskFailureErrorCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTaskFailureErrorCode() {
            this.bitField0_ &= -3;
            this.taskFailureErrorCode_ = 0;
        }

        public boolean hasTaskFailureType() {
            return (this.bitField0_ & 4) != 0;
        }

        public CommonEnums.SetupTaskFailureType getTaskFailureType() {
            CommonEnums.SetupTaskFailureType result = CommonEnums.SetupTaskFailureType.forNumber(this.taskFailureType_);
            return result == null ? CommonEnums.SetupTaskFailureType.ERROR_UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setTaskFailureType(CommonEnums.SetupTaskFailureType value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.taskFailureType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTaskFailureType() {
            this.bitField0_ &= -5;
            this.taskFailureType_ = 0;
        }

        public boolean hasRetryCount() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getRetryCount() {
            return this.retryCount_;
        }

        /* access modifiers changed from: private */
        public void setRetryCount(int value) {
            this.bitField0_ |= 8;
            this.retryCount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRetryCount() {
            this.bitField0_ &= -9;
            this.retryCount_ = 0;
        }

        public boolean hasExceptionCause() {
            return (this.bitField0_ & 16) != 0;
        }

        public String getExceptionCause() {
            return this.exceptionCause_;
        }

        /* access modifiers changed from: private */
        public void setExceptionCause(String value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.exceptionCause_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getExceptionCauseBytes() {
            return ByteString.copyFromUtf8(this.exceptionCause_);
        }

        /* access modifiers changed from: private */
        public void setExceptionCauseBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 16;
                this.exceptionCause_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearExceptionCause() {
            this.bitField0_ &= -17;
            this.exceptionCause_ = getDefaultInstance().getExceptionCause();
        }

        public List<String> getShrinkedStacktraceList() {
            return this.shrinkedStacktrace_;
        }

        public int getShrinkedStacktraceCount() {
            return this.shrinkedStacktrace_.size();
        }

        public String getShrinkedStacktrace(int index) {
            return this.shrinkedStacktrace_.get(index);
        }

        public ByteString getShrinkedStacktraceBytes(int index) {
            return ByteString.copyFromUtf8(this.shrinkedStacktrace_.get(index));
        }

        private void ensureShrinkedStacktraceIsMutable() {
            if (!this.shrinkedStacktrace_.isModifiable()) {
                this.shrinkedStacktrace_ = GeneratedMessageLite.mutableCopy(this.shrinkedStacktrace_);
            }
        }

        /* access modifiers changed from: private */
        public void setShrinkedStacktrace(int index, String value) {
            if (value != null) {
                ensureShrinkedStacktraceIsMutable();
                this.shrinkedStacktrace_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addShrinkedStacktrace(String value) {
            if (value != null) {
                ensureShrinkedStacktraceIsMutable();
                this.shrinkedStacktrace_.add(value);
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
        public void addAllShrinkedStacktrace(Iterable<String> values) {
            ensureShrinkedStacktraceIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.shrinkedStacktrace_);
        }

        /* access modifiers changed from: private */
        public void clearShrinkedStacktrace() {
            this.shrinkedStacktrace_ = GeneratedMessageLite.emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void addShrinkedStacktraceBytes(ByteString value) {
            if (value != null) {
                ensureShrinkedStacktraceIsMutable();
                this.shrinkedStacktrace_.add(value.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new TaskFailMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0001\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\f\u0002\u0004\u0004\u0003\u0005\b\u0004\u0006\u001a", new Object[]{"bitField0_", "taskFailureReason_", CommonEnums.TaskFailureReason.internalGetVerifier(), "taskFailureErrorCode_", "taskFailureType_", CommonEnums.SetupTaskFailureType.internalGetVerifier(), "retryCount_", "exceptionCause_", "shrinkedStacktrace_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<TaskFailMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (TaskFailMetric.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<TaskFailMetric, Builder> implements TaskFailMetricOrBuilder {
            private Builder() {
                super(TaskFailMetric.DEFAULT_INSTANCE);
            }

            public boolean hasTaskFailureReason() {
                return ((TaskFailMetric) this.instance).hasTaskFailureReason();
            }

            public CommonEnums.TaskFailureReason getTaskFailureReason() {
                return ((TaskFailMetric) this.instance).getTaskFailureReason();
            }

            public Builder setTaskFailureReason(CommonEnums.TaskFailureReason value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).setTaskFailureReason(value);
                return this;
            }

            public Builder clearTaskFailureReason() {
                copyOnWrite();
                ((TaskFailMetric) this.instance).clearTaskFailureReason();
                return this;
            }

            public boolean hasTaskFailureErrorCode() {
                return ((TaskFailMetric) this.instance).hasTaskFailureErrorCode();
            }

            public int getTaskFailureErrorCode() {
                return ((TaskFailMetric) this.instance).getTaskFailureErrorCode();
            }

            public Builder setTaskFailureErrorCode(int value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).setTaskFailureErrorCode(value);
                return this;
            }

            public Builder clearTaskFailureErrorCode() {
                copyOnWrite();
                ((TaskFailMetric) this.instance).clearTaskFailureErrorCode();
                return this;
            }

            public boolean hasTaskFailureType() {
                return ((TaskFailMetric) this.instance).hasTaskFailureType();
            }

            public CommonEnums.SetupTaskFailureType getTaskFailureType() {
                return ((TaskFailMetric) this.instance).getTaskFailureType();
            }

            public Builder setTaskFailureType(CommonEnums.SetupTaskFailureType value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).setTaskFailureType(value);
                return this;
            }

            public Builder clearTaskFailureType() {
                copyOnWrite();
                ((TaskFailMetric) this.instance).clearTaskFailureType();
                return this;
            }

            public boolean hasRetryCount() {
                return ((TaskFailMetric) this.instance).hasRetryCount();
            }

            public int getRetryCount() {
                return ((TaskFailMetric) this.instance).getRetryCount();
            }

            public Builder setRetryCount(int value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).setRetryCount(value);
                return this;
            }

            public Builder clearRetryCount() {
                copyOnWrite();
                ((TaskFailMetric) this.instance).clearRetryCount();
                return this;
            }

            public boolean hasExceptionCause() {
                return ((TaskFailMetric) this.instance).hasExceptionCause();
            }

            public String getExceptionCause() {
                return ((TaskFailMetric) this.instance).getExceptionCause();
            }

            public Builder setExceptionCause(String value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).setExceptionCause(value);
                return this;
            }

            public ByteString getExceptionCauseBytes() {
                return ((TaskFailMetric) this.instance).getExceptionCauseBytes();
            }

            public Builder setExceptionCauseBytes(ByteString value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).setExceptionCauseBytes(value);
                return this;
            }

            public Builder clearExceptionCause() {
                copyOnWrite();
                ((TaskFailMetric) this.instance).clearExceptionCause();
                return this;
            }

            public List<String> getShrinkedStacktraceList() {
                return Collections.unmodifiableList(((TaskFailMetric) this.instance).getShrinkedStacktraceList());
            }

            public int getShrinkedStacktraceCount() {
                return ((TaskFailMetric) this.instance).getShrinkedStacktraceCount();
            }

            public String getShrinkedStacktrace(int index) {
                return ((TaskFailMetric) this.instance).getShrinkedStacktrace(index);
            }

            public ByteString getShrinkedStacktraceBytes(int index) {
                return ((TaskFailMetric) this.instance).getShrinkedStacktraceBytes(index);
            }

            public Builder setShrinkedStacktrace(int index, String value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).setShrinkedStacktrace(index, value);
                return this;
            }

            public Builder addShrinkedStacktrace(String value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).addShrinkedStacktrace(value);
                return this;
            }

            public Builder addAllShrinkedStacktrace(Iterable<String> values) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).addAllShrinkedStacktrace(values);
                return this;
            }

            public Builder clearShrinkedStacktrace() {
                copyOnWrite();
                ((TaskFailMetric) this.instance).clearShrinkedStacktrace();
                return this;
            }

            public Builder addShrinkedStacktraceBytes(ByteString value) {
                copyOnWrite();
                ((TaskFailMetric) this.instance).addShrinkedStacktraceBytes(value);
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ActivityMetric extends GeneratedMessageLite<ActivityMetric, Builder> implements ActivityMetricOrBuilder {
        public static final int ACTIVITY_STARTED_FIELD_NUMBER = 9;
        /* access modifiers changed from: private */
        public static final ActivityMetric DEFAULT_INSTANCE = new ActivityMetric();
        public static final int GOOGLER_ACCOUNT_FIELD_NUMBER = 3;
        public static final int IS_CANCELLED_FIELD_NUMBER = 5;
        public static final int IS_DESTROYED_FIELD_NUMBER = 7;
        public static final int IS_WIPED_FIELD_NUMBER = 6;
        public static final int LOGIN_SCOPE_TOKEN_REVOKED_FIELD_NUMBER = 2;
        public static final int PERSONAL_ACCOUNT_ADDED_FIELD_NUMBER = 1;
        public static final int REQUEST_CODE_FIELD_NUMBER = 8;
        public static final int RESTORE_LAUNCHED_FIELD_NUMBER = 4;
        private static volatile Parser<ActivityMetric> PARSER = null;

        static {
            GeneratedMessageLite.registerDefaultInstance(ActivityMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 9, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 256, presenceBitsId = 0)
        private String activityStarted_ = "";
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private boolean googlerAccount_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private boolean isCancelled_;
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 64, presenceBitsId = 0)
        private boolean isDestroyed_;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private boolean isWiped_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private boolean loginScopeTokenRevoked_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private boolean personalAccountAdded_;
        @ProtoField(fieldNumber = 8, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 128, presenceBitsId = 0)
        private int requestCode_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.BOOL)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private boolean restoreLaunched_;

        private ActivityMetric() {
        }

        public static ActivityMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ActivityMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ActivityMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ActivityMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ActivityMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ActivityMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ActivityMetric parseFrom(InputStream input) throws IOException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ActivityMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ActivityMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (ActivityMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ActivityMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ActivityMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ActivityMetric parseFrom(CodedInputStream input) throws IOException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ActivityMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ActivityMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ActivityMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static ActivityMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ActivityMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasPersonalAccountAdded() {
            return (this.bitField0_ & 1) != 0;
        }

        public boolean getPersonalAccountAdded() {
            return this.personalAccountAdded_;
        }

        /* access modifiers changed from: private */
        public void setPersonalAccountAdded(boolean value) {
            this.bitField0_ |= 1;
            this.personalAccountAdded_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPersonalAccountAdded() {
            this.bitField0_ &= -2;
            this.personalAccountAdded_ = false;
        }

        public boolean hasLoginScopeTokenRevoked() {
            return (this.bitField0_ & 2) != 0;
        }

        public boolean getLoginScopeTokenRevoked() {
            return this.loginScopeTokenRevoked_;
        }

        /* access modifiers changed from: private */
        public void setLoginScopeTokenRevoked(boolean value) {
            this.bitField0_ |= 2;
            this.loginScopeTokenRevoked_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLoginScopeTokenRevoked() {
            this.bitField0_ &= -3;
            this.loginScopeTokenRevoked_ = false;
        }

        public boolean hasGooglerAccount() {
            return (this.bitField0_ & 4) != 0;
        }

        public boolean getGooglerAccount() {
            return this.googlerAccount_;
        }

        /* access modifiers changed from: private */
        public void setGooglerAccount(boolean value) {
            this.bitField0_ |= 4;
            this.googlerAccount_ = value;
        }

        /* access modifiers changed from: private */
        public void clearGooglerAccount() {
            this.bitField0_ &= -5;
            this.googlerAccount_ = false;
        }

        public boolean hasRestoreLaunched() {
            return (this.bitField0_ & 8) != 0;
        }

        public boolean getRestoreLaunched() {
            return this.restoreLaunched_;
        }

        /* access modifiers changed from: private */
        public void setRestoreLaunched(boolean value) {
            this.bitField0_ |= 8;
            this.restoreLaunched_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRestoreLaunched() {
            this.bitField0_ &= -9;
            this.restoreLaunched_ = false;
        }

        public boolean hasIsCancelled() {
            return (this.bitField0_ & 16) != 0;
        }

        public boolean getIsCancelled() {
            return this.isCancelled_;
        }

        /* access modifiers changed from: private */
        public void setIsCancelled(boolean value) {
            this.bitField0_ |= 16;
            this.isCancelled_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsCancelled() {
            this.bitField0_ &= -17;
            this.isCancelled_ = false;
        }

        public boolean hasIsWiped() {
            return (this.bitField0_ & 32) != 0;
        }

        public boolean getIsWiped() {
            return this.isWiped_;
        }

        /* access modifiers changed from: private */
        public void setIsWiped(boolean value) {
            this.bitField0_ |= 32;
            this.isWiped_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsWiped() {
            this.bitField0_ &= -33;
            this.isWiped_ = false;
        }

        public boolean hasIsDestroyed() {
            return (this.bitField0_ & 64) != 0;
        }

        public boolean getIsDestroyed() {
            return this.isDestroyed_;
        }

        /* access modifiers changed from: private */
        public void setIsDestroyed(boolean value) {
            this.bitField0_ |= 64;
            this.isDestroyed_ = value;
        }

        /* access modifiers changed from: private */
        public void clearIsDestroyed() {
            this.bitField0_ &= -65;
            this.isDestroyed_ = false;
        }

        public boolean hasRequestCode() {
            return (this.bitField0_ & 128) != 0;
        }

        public int getRequestCode() {
            return this.requestCode_;
        }

        /* access modifiers changed from: private */
        public void setRequestCode(int value) {
            this.bitField0_ |= 128;
            this.requestCode_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRequestCode() {
            this.bitField0_ &= -129;
            this.requestCode_ = 0;
        }

        public boolean hasActivityStarted() {
            return (this.bitField0_ & 256) != 0;
        }

        public String getActivityStarted() {
            return this.activityStarted_;
        }

        /* access modifiers changed from: private */
        public void setActivityStarted(String value) {
            if (value != null) {
                this.bitField0_ |= 256;
                this.activityStarted_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getActivityStartedBytes() {
            return ByteString.copyFromUtf8(this.activityStarted_);
        }

        /* access modifiers changed from: private */
        public void setActivityStartedBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 256;
                this.activityStarted_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearActivityStarted() {
            this.bitField0_ &= -257;
            this.activityStarted_ = getDefaultInstance().getActivityStarted();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ActivityMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\t\u0000\u0001\u0001\t\t\u0000\u0000\u0000\u0001\u0007\u0000\u0002\u0007\u0001\u0003\u0007\u0002\u0004\u0007\u0003\u0005\u0007\u0004\u0006\u0007\u0005\u0007\u0007\u0006\b\u0004\u0007\t\b\b", new Object[]{"bitField0_", "personalAccountAdded_", "loginScopeTokenRevoked_", "googlerAccount_", "restoreLaunched_", "isCancelled_", "isWiped_", "isDestroyed_", "requestCode_", "activityStarted_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ActivityMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (ActivityMetric.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<ActivityMetric, Builder> implements ActivityMetricOrBuilder {
            private Builder() {
                super(ActivityMetric.DEFAULT_INSTANCE);
            }

            public boolean hasPersonalAccountAdded() {
                return ((ActivityMetric) this.instance).hasPersonalAccountAdded();
            }

            public boolean getPersonalAccountAdded() {
                return ((ActivityMetric) this.instance).getPersonalAccountAdded();
            }

            public Builder setPersonalAccountAdded(boolean value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setPersonalAccountAdded(value);
                return this;
            }

            public Builder clearPersonalAccountAdded() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearPersonalAccountAdded();
                return this;
            }

            public boolean hasLoginScopeTokenRevoked() {
                return ((ActivityMetric) this.instance).hasLoginScopeTokenRevoked();
            }

            public boolean getLoginScopeTokenRevoked() {
                return ((ActivityMetric) this.instance).getLoginScopeTokenRevoked();
            }

            public Builder setLoginScopeTokenRevoked(boolean value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setLoginScopeTokenRevoked(value);
                return this;
            }

            public Builder clearLoginScopeTokenRevoked() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearLoginScopeTokenRevoked();
                return this;
            }

            public boolean hasGooglerAccount() {
                return ((ActivityMetric) this.instance).hasGooglerAccount();
            }

            public boolean getGooglerAccount() {
                return ((ActivityMetric) this.instance).getGooglerAccount();
            }

            public Builder setGooglerAccount(boolean value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setGooglerAccount(value);
                return this;
            }

            public Builder clearGooglerAccount() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearGooglerAccount();
                return this;
            }

            public boolean hasRestoreLaunched() {
                return ((ActivityMetric) this.instance).hasRestoreLaunched();
            }

            public boolean getRestoreLaunched() {
                return ((ActivityMetric) this.instance).getRestoreLaunched();
            }

            public Builder setRestoreLaunched(boolean value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setRestoreLaunched(value);
                return this;
            }

            public Builder clearRestoreLaunched() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearRestoreLaunched();
                return this;
            }

            public boolean hasIsCancelled() {
                return ((ActivityMetric) this.instance).hasIsCancelled();
            }

            public boolean getIsCancelled() {
                return ((ActivityMetric) this.instance).getIsCancelled();
            }

            public Builder setIsCancelled(boolean value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setIsCancelled(value);
                return this;
            }

            public Builder clearIsCancelled() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearIsCancelled();
                return this;
            }

            public boolean hasIsWiped() {
                return ((ActivityMetric) this.instance).hasIsWiped();
            }

            public boolean getIsWiped() {
                return ((ActivityMetric) this.instance).getIsWiped();
            }

            public Builder setIsWiped(boolean value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setIsWiped(value);
                return this;
            }

            public Builder clearIsWiped() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearIsWiped();
                return this;
            }

            public boolean hasIsDestroyed() {
                return ((ActivityMetric) this.instance).hasIsDestroyed();
            }

            public boolean getIsDestroyed() {
                return ((ActivityMetric) this.instance).getIsDestroyed();
            }

            public Builder setIsDestroyed(boolean value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setIsDestroyed(value);
                return this;
            }

            public Builder clearIsDestroyed() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearIsDestroyed();
                return this;
            }

            public boolean hasRequestCode() {
                return ((ActivityMetric) this.instance).hasRequestCode();
            }

            public int getRequestCode() {
                return ((ActivityMetric) this.instance).getRequestCode();
            }

            public Builder setRequestCode(int value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setRequestCode(value);
                return this;
            }

            public Builder clearRequestCode() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearRequestCode();
                return this;
            }

            public boolean hasActivityStarted() {
                return ((ActivityMetric) this.instance).hasActivityStarted();
            }

            public String getActivityStarted() {
                return ((ActivityMetric) this.instance).getActivityStarted();
            }

            public Builder setActivityStarted(String value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setActivityStarted(value);
                return this;
            }

            public ByteString getActivityStartedBytes() {
                return ((ActivityMetric) this.instance).getActivityStartedBytes();
            }

            public Builder setActivityStartedBytes(ByteString value) {
                copyOnWrite();
                ((ActivityMetric) this.instance).setActivityStartedBytes(value);
                return this;
            }

            public Builder clearActivityStarted() {
                copyOnWrite();
                ((ActivityMetric) this.instance).clearActivityStarted();
                return this;
            }
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SystemAppMetric extends GeneratedMessageLite<SystemAppMetric, Builder> implements SystemAppMetricOrBuilder {
        public static final int ACTION_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final SystemAppMetric DEFAULT_INSTANCE = new SystemAppMetric();
        public static final int INTENT_FIELD_NUMBER = 3;
        public static final int PACKAGE_NAME_FIELD_NUMBER = 1;
        private static volatile Parser<SystemAppMetric> PARSER;

        static {
            GeneratedMessageLite.registerDefaultInstance(SystemAppMetric.class, DEFAULT_INSTANCE);
        }

        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int action_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 3, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private String intent_ = "";
        @ProtoField(fieldNumber = 1, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private String packageName_ = "";

        private SystemAppMetric() {
        }

        public static SystemAppMetric parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemAppMetric parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemAppMetric parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemAppMetric parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemAppMetric parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemAppMetric parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemAppMetric parseFrom(InputStream input) throws IOException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemAppMetric parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SystemAppMetric parseDelimitedFrom(InputStream input) throws IOException {
            return (SystemAppMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemAppMetric parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemAppMetric) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SystemAppMetric parseFrom(CodedInputStream input) throws IOException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemAppMetric parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemAppMetric) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SystemAppMetric prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static SystemAppMetric getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SystemAppMetric> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        public boolean hasPackageName() {
            return (this.bitField0_ & 1) != 0;
        }

        public String getPackageName() {
            return this.packageName_;
        }

        /* access modifiers changed from: private */
        public void setPackageName(String value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getPackageNameBytes() {
            return ByteString.copyFromUtf8(this.packageName_);
        }

        /* access modifiers changed from: private */
        public void setPackageNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.packageName_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPackageName() {
            this.bitField0_ &= -2;
            this.packageName_ = getDefaultInstance().getPackageName();
        }

        public boolean hasAction() {
            return (this.bitField0_ & 2) != 0;
        }

        public CommonEnums.SmartSystemAppAction getAction() {
            CommonEnums.SmartSystemAppAction result = CommonEnums.SmartSystemAppAction.forNumber(this.action_);
            return result == null ? CommonEnums.SmartSystemAppAction.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setAction(CommonEnums.SmartSystemAppAction value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.action_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearAction() {
            this.bitField0_ &= -3;
            this.action_ = 0;
        }

        public boolean hasIntent() {
            return (this.bitField0_ & 4) != 0;
        }

        public String getIntent() {
            return this.intent_;
        }

        /* access modifiers changed from: private */
        public void setIntent(String value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.intent_ = value;
                return;
            }
            throw new NullPointerException();
        }

        public ByteString getIntentBytes() {
            return ByteString.copyFromUtf8(this.intent_);
        }

        /* access modifiers changed from: private */
        public void setIntentBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.intent_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearIntent() {
            this.bitField0_ &= -5;
            this.intent_ = getDefaultInstance().getIntent();
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SystemAppMetric();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0003\u0000\u0001\u0001\u0003\u0003\u0000\u0000\u0000\u0001\b\u0000\u0002\f\u0001\u0003\b\u0002", new Object[]{"bitField0_", "packageName_", "action_", CommonEnums.SmartSystemAppAction.internalGetVerifier(), "intent_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SystemAppMetric> parser = PARSER;
                    if (parser == null) {
                        synchronized (SystemAppMetric.class) {
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

        public static final class Builder extends GeneratedMessageLite.Builder<SystemAppMetric, Builder> implements SystemAppMetricOrBuilder {
            private Builder() {
                super(SystemAppMetric.DEFAULT_INSTANCE);
            }

            public boolean hasPackageName() {
                return ((SystemAppMetric) this.instance).hasPackageName();
            }

            public String getPackageName() {
                return ((SystemAppMetric) this.instance).getPackageName();
            }

            public Builder setPackageName(String value) {
                copyOnWrite();
                ((SystemAppMetric) this.instance).setPackageName(value);
                return this;
            }

            public ByteString getPackageNameBytes() {
                return ((SystemAppMetric) this.instance).getPackageNameBytes();
            }

            public Builder setPackageNameBytes(ByteString value) {
                copyOnWrite();
                ((SystemAppMetric) this.instance).setPackageNameBytes(value);
                return this;
            }

            public Builder clearPackageName() {
                copyOnWrite();
                ((SystemAppMetric) this.instance).clearPackageName();
                return this;
            }

            public boolean hasAction() {
                return ((SystemAppMetric) this.instance).hasAction();
            }

            public CommonEnums.SmartSystemAppAction getAction() {
                return ((SystemAppMetric) this.instance).getAction();
            }

            public Builder setAction(CommonEnums.SmartSystemAppAction value) {
                copyOnWrite();
                ((SystemAppMetric) this.instance).setAction(value);
                return this;
            }

            public Builder clearAction() {
                copyOnWrite();
                ((SystemAppMetric) this.instance).clearAction();
                return this;
            }

            public boolean hasIntent() {
                return ((SystemAppMetric) this.instance).hasIntent();
            }

            public String getIntent() {
                return ((SystemAppMetric) this.instance).getIntent();
            }

            public Builder setIntent(String value) {
                copyOnWrite();
                ((SystemAppMetric) this.instance).setIntent(value);
                return this;
            }

            public ByteString getIntentBytes() {
                return ((SystemAppMetric) this.instance).getIntentBytes();
            }

            public Builder setIntentBytes(ByteString value) {
                copyOnWrite();
                ((SystemAppMetric) this.instance).setIntentBytes(value);
                return this;
            }

            public Builder clearIntent() {
                copyOnWrite();
                ((SystemAppMetric) this.instance).clearIntent();
                return this;
            }
        }
    }
}
