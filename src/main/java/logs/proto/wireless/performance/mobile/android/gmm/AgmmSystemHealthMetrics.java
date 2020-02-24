package logs.proto.wireless.performance.mobile.android.gmm;

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

public final class AgmmSystemHealthMetrics {

    public interface SystemHealthMetricExtensionOrBuilder extends MessageLiteOrBuilder {
        int getCustomEntryPoint();

        int getCustomExitPoint();

        int getCustomFeatureName();

        int getOverlappingEvents(int i);

        int getOverlappingEventsCount();

        List<Integer> getOverlappingEventsList();

        SystemHealthMetricExtension.VEInfo getVesAppearedInTheFlow(int i);

        int getVesAppearedInTheFlowCount();

        List<SystemHealthMetricExtension.VEInfo> getVesAppearedInTheFlowList();

        boolean hasCustomEntryPoint();

        boolean hasCustomExitPoint();

        boolean hasCustomFeatureName();
    }

    private AgmmSystemHealthMetrics() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class SystemHealthMetricExtension extends GeneratedMessageLite<SystemHealthMetricExtension, Builder> implements SystemHealthMetricExtensionOrBuilder {
        public static final int CUSTOM_ENTRY_POINT_FIELD_NUMBER = 1;
        public static final int CUSTOM_EXIT_POINT_FIELD_NUMBER = 2;
        public static final int CUSTOM_FEATURE_NAME_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final SystemHealthMetricExtension DEFAULT_INSTANCE = new SystemHealthMetricExtension();
        public static final int OVERLAPPING_EVENTS_FIELD_NUMBER = 5;
        private static volatile Parser<SystemHealthMetricExtension> PARSER = null;
        public static final int VES_APPEARED_IN_THE_FLOW_FIELD_NUMBER = 4;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int customEntryPoint_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int customExitPoint_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int customFeatureName_;
        @ProtoField(fieldNumber = 5, type = FieldType.INT32_LIST)
        private Internal.IntList overlappingEvents_ = emptyIntList();
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<VEInfo> vesAppearedInTheFlow_ = emptyProtobufList();

        public interface VEInfoOrBuilder extends MessageLiteOrBuilder {
            int getLeafVeTypeId();

            VEInfo.VEAction getVeAction();

            boolean hasLeafVeTypeId();

            boolean hasVeAction();
        }

        private SystemHealthMetricExtension() {
        }

        @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
        public static final class VEInfo extends GeneratedMessageLite<VEInfo, Builder> implements VEInfoOrBuilder {
            /* access modifiers changed from: private */
            public static final VEInfo DEFAULT_INSTANCE = new VEInfo();
            public static final int LEAF_VE_TYPE_ID_FIELD_NUMBER = 1;
            private static volatile Parser<VEInfo> PARSER = null;
            public static final int VE_ACTION_FIELD_NUMBER = 2;
            @ProtoPresenceBits(mo28548id = 0)
            private int bitField0_;
            @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
            @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
            private int leafVeTypeId_;
            @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.ENUM)
            @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
            private int veAction_;

            private VEInfo() {
            }

            public enum VEAction implements Internal.EnumLite {
                UNKNOWN_ACTION(0),
                PAGE_ACTIVATION(1),
                INTERACTION(2);
                
                public static final int INTERACTION_VALUE = 2;
                public static final int PAGE_ACTIVATION_VALUE = 1;
                public static final int UNKNOWN_ACTION_VALUE = 0;
                private static final Internal.EnumLiteMap<VEAction> internalValueMap = new Internal.EnumLiteMap<VEAction>() {
                    public VEAction findValueByNumber(int number) {
                        return VEAction.forNumber(number);
                    }
                };
                private final int value;

                public final int getNumber() {
                    return this.value;
                }

                public static VEAction forNumber(int value2) {
                    if (value2 == 0) {
                        return UNKNOWN_ACTION;
                    }
                    if (value2 == 1) {
                        return PAGE_ACTIVATION;
                    }
                    if (value2 != 2) {
                        return null;
                    }
                    return INTERACTION;
                }

                public static Internal.EnumLiteMap<VEAction> internalGetValueMap() {
                    return internalValueMap;
                }

                public static Internal.EnumVerifier internalGetVerifier() {
                    return VEActionVerifier.INSTANCE;
                }

                private static final class VEActionVerifier implements Internal.EnumVerifier {
                    static final Internal.EnumVerifier INSTANCE = new VEActionVerifier();

                    private VEActionVerifier() {
                    }

                    public boolean isInRange(int number) {
                        return VEAction.forNumber(number) != null;
                    }
                }

                private VEAction(int value2) {
                    this.value = value2;
                }
            }

            public boolean hasLeafVeTypeId() {
                return (this.bitField0_ & 1) != 0;
            }

            public int getLeafVeTypeId() {
                return this.leafVeTypeId_;
            }

            /* access modifiers changed from: private */
            public void setLeafVeTypeId(int value) {
                this.bitField0_ |= 1;
                this.leafVeTypeId_ = value;
            }

            /* access modifiers changed from: private */
            public void clearLeafVeTypeId() {
                this.bitField0_ &= -2;
                this.leafVeTypeId_ = 0;
            }

            public boolean hasVeAction() {
                return (this.bitField0_ & 2) != 0;
            }

            public VEAction getVeAction() {
                VEAction result = VEAction.forNumber(this.veAction_);
                return result == null ? VEAction.UNKNOWN_ACTION : result;
            }

            /* access modifiers changed from: private */
            public void setVeAction(VEAction value) {
                if (value != null) {
                    this.bitField0_ |= 2;
                    this.veAction_ = value.getNumber();
                    return;
                }
                throw new NullPointerException();
            }

            /* access modifiers changed from: private */
            public void clearVeAction() {
                this.bitField0_ &= -3;
                this.veAction_ = 0;
            }

            public static VEInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static VEInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static VEInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static VEInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static VEInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
            }

            public static VEInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
            }

            public static VEInfo parseFrom(InputStream input) throws IOException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static VEInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static VEInfo parseDelimitedFrom(InputStream input) throws IOException {
                return (VEInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
            }

            public static VEInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (VEInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static VEInfo parseFrom(CodedInputStream input) throws IOException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
            }

            public static VEInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (VEInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return (Builder) DEFAULT_INSTANCE.createBuilder();
            }

            public static Builder newBuilder(VEInfo prototype) {
                return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
            }

            public static final class Builder extends GeneratedMessageLite.Builder<VEInfo, Builder> implements VEInfoOrBuilder {
                private Builder() {
                    super(VEInfo.DEFAULT_INSTANCE);
                }

                public boolean hasLeafVeTypeId() {
                    return ((VEInfo) this.instance).hasLeafVeTypeId();
                }

                public int getLeafVeTypeId() {
                    return ((VEInfo) this.instance).getLeafVeTypeId();
                }

                public Builder setLeafVeTypeId(int value) {
                    copyOnWrite();
                    ((VEInfo) this.instance).setLeafVeTypeId(value);
                    return this;
                }

                public Builder clearLeafVeTypeId() {
                    copyOnWrite();
                    ((VEInfo) this.instance).clearLeafVeTypeId();
                    return this;
                }

                public boolean hasVeAction() {
                    return ((VEInfo) this.instance).hasVeAction();
                }

                public VEAction getVeAction() {
                    return ((VEInfo) this.instance).getVeAction();
                }

                public Builder setVeAction(VEAction value) {
                    copyOnWrite();
                    ((VEInfo) this.instance).setVeAction(value);
                    return this;
                }

                public Builder clearVeAction() {
                    copyOnWrite();
                    ((VEInfo) this.instance).clearVeAction();
                    return this;
                }
            }

            /* access modifiers changed from: protected */
            public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
                switch (method) {
                    case NEW_MUTABLE_INSTANCE:
                        return new VEInfo();
                    case NEW_BUILDER:
                        return new Builder();
                    case BUILD_MESSAGE_INFO:
                        return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\u0004\u0000\u0002\f\u0001", new Object[]{"bitField0_", "leafVeTypeId_", "veAction_", VEAction.internalGetVerifier()});
                    case GET_DEFAULT_INSTANCE:
                        return DEFAULT_INSTANCE;
                    case GET_PARSER:
                        Parser<VEInfo> parser = PARSER;
                        if (parser == null) {
                            synchronized (VEInfo.class) {
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
                GeneratedMessageLite.registerDefaultInstance(VEInfo.class, DEFAULT_INSTANCE);
            }

            public static VEInfo getDefaultInstance() {
                return DEFAULT_INSTANCE;
            }

            public static Parser<VEInfo> parser() {
                return DEFAULT_INSTANCE.getParserForType();
            }
        }

        public boolean hasCustomEntryPoint() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getCustomEntryPoint() {
            return this.customEntryPoint_;
        }

        /* access modifiers changed from: private */
        public void setCustomEntryPoint(int value) {
            this.bitField0_ |= 1;
            this.customEntryPoint_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCustomEntryPoint() {
            this.bitField0_ &= -2;
            this.customEntryPoint_ = 0;
        }

        public boolean hasCustomExitPoint() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getCustomExitPoint() {
            return this.customExitPoint_;
        }

        /* access modifiers changed from: private */
        public void setCustomExitPoint(int value) {
            this.bitField0_ |= 2;
            this.customExitPoint_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCustomExitPoint() {
            this.bitField0_ &= -3;
            this.customExitPoint_ = 0;
        }

        public boolean hasCustomFeatureName() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getCustomFeatureName() {
            return this.customFeatureName_;
        }

        /* access modifiers changed from: private */
        public void setCustomFeatureName(int value) {
            this.bitField0_ |= 4;
            this.customFeatureName_ = value;
        }

        /* access modifiers changed from: private */
        public void clearCustomFeatureName() {
            this.bitField0_ &= -5;
            this.customFeatureName_ = 0;
        }

        public List<VEInfo> getVesAppearedInTheFlowList() {
            return this.vesAppearedInTheFlow_;
        }

        public List<? extends VEInfoOrBuilder> getVesAppearedInTheFlowOrBuilderList() {
            return this.vesAppearedInTheFlow_;
        }

        public int getVesAppearedInTheFlowCount() {
            return this.vesAppearedInTheFlow_.size();
        }

        public VEInfo getVesAppearedInTheFlow(int index) {
            return this.vesAppearedInTheFlow_.get(index);
        }

        public VEInfoOrBuilder getVesAppearedInTheFlowOrBuilder(int index) {
            return this.vesAppearedInTheFlow_.get(index);
        }

        private void ensureVesAppearedInTheFlowIsMutable() {
            if (!this.vesAppearedInTheFlow_.isModifiable()) {
                this.vesAppearedInTheFlow_ = GeneratedMessageLite.mutableCopy(this.vesAppearedInTheFlow_);
            }
        }

        /* access modifiers changed from: private */
        public void setVesAppearedInTheFlow(int index, VEInfo value) {
            if (value != null) {
                ensureVesAppearedInTheFlowIsMutable();
                this.vesAppearedInTheFlow_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setVesAppearedInTheFlow(int index, VEInfo.Builder builderForValue) {
            ensureVesAppearedInTheFlowIsMutable();
            this.vesAppearedInTheFlow_.set(index, (VEInfo) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addVesAppearedInTheFlow(VEInfo value) {
            if (value != null) {
                ensureVesAppearedInTheFlowIsMutable();
                this.vesAppearedInTheFlow_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addVesAppearedInTheFlow(int index, VEInfo value) {
            if (value != null) {
                ensureVesAppearedInTheFlowIsMutable();
                this.vesAppearedInTheFlow_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addVesAppearedInTheFlow(VEInfo.Builder builderForValue) {
            ensureVesAppearedInTheFlowIsMutable();
            this.vesAppearedInTheFlow_.add((VEInfo) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addVesAppearedInTheFlow(int index, VEInfo.Builder builderForValue) {
            ensureVesAppearedInTheFlowIsMutable();
            this.vesAppearedInTheFlow_.add(index, (VEInfo) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.android.gmm.AgmmSystemHealthMetrics$SystemHealthMetricExtension$VEInfo>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.android.gmm.AgmmSystemHealthMetrics$SystemHealthMetricExtension$VEInfo>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllVesAppearedInTheFlow(Iterable<? extends VEInfo> values) {
            ensureVesAppearedInTheFlowIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.vesAppearedInTheFlow_);
        }

        /* access modifiers changed from: private */
        public void clearVesAppearedInTheFlow() {
            this.vesAppearedInTheFlow_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeVesAppearedInTheFlow(int index) {
            ensureVesAppearedInTheFlowIsMutable();
            this.vesAppearedInTheFlow_.remove(index);
        }

        public List<Integer> getOverlappingEventsList() {
            return this.overlappingEvents_;
        }

        public int getOverlappingEventsCount() {
            return this.overlappingEvents_.size();
        }

        public int getOverlappingEvents(int index) {
            return this.overlappingEvents_.getInt(index);
        }

        private void ensureOverlappingEventsIsMutable() {
            if (!this.overlappingEvents_.isModifiable()) {
                this.overlappingEvents_ = GeneratedMessageLite.mutableCopy(this.overlappingEvents_);
            }
        }

        /* access modifiers changed from: private */
        public void setOverlappingEvents(int index, int value) {
            ensureOverlappingEventsIsMutable();
            this.overlappingEvents_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addOverlappingEvents(int value) {
            ensureOverlappingEventsIsMutable();
            this.overlappingEvents_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllOverlappingEvents(Iterable<? extends Integer> values) {
            ensureOverlappingEventsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.overlappingEvents_);
        }

        /* access modifiers changed from: private */
        public void clearOverlappingEvents() {
            this.overlappingEvents_ = emptyIntList();
        }

        public static SystemHealthMetricExtension parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemHealthMetricExtension parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemHealthMetricExtension parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemHealthMetricExtension parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemHealthMetricExtension parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static SystemHealthMetricExtension parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static SystemHealthMetricExtension parseFrom(InputStream input) throws IOException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemHealthMetricExtension parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SystemHealthMetricExtension parseDelimitedFrom(InputStream input) throws IOException {
            return (SystemHealthMetricExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemHealthMetricExtension parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemHealthMetricExtension) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static SystemHealthMetricExtension parseFrom(CodedInputStream input) throws IOException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static SystemHealthMetricExtension parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SystemHealthMetricExtension) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(SystemHealthMetricExtension prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<SystemHealthMetricExtension, Builder> implements SystemHealthMetricExtensionOrBuilder {
            private Builder() {
                super(SystemHealthMetricExtension.DEFAULT_INSTANCE);
            }

            public boolean hasCustomEntryPoint() {
                return ((SystemHealthMetricExtension) this.instance).hasCustomEntryPoint();
            }

            public int getCustomEntryPoint() {
                return ((SystemHealthMetricExtension) this.instance).getCustomEntryPoint();
            }

            public Builder setCustomEntryPoint(int value) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).setCustomEntryPoint(value);
                return this;
            }

            public Builder clearCustomEntryPoint() {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).clearCustomEntryPoint();
                return this;
            }

            public boolean hasCustomExitPoint() {
                return ((SystemHealthMetricExtension) this.instance).hasCustomExitPoint();
            }

            public int getCustomExitPoint() {
                return ((SystemHealthMetricExtension) this.instance).getCustomExitPoint();
            }

            public Builder setCustomExitPoint(int value) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).setCustomExitPoint(value);
                return this;
            }

            public Builder clearCustomExitPoint() {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).clearCustomExitPoint();
                return this;
            }

            public boolean hasCustomFeatureName() {
                return ((SystemHealthMetricExtension) this.instance).hasCustomFeatureName();
            }

            public int getCustomFeatureName() {
                return ((SystemHealthMetricExtension) this.instance).getCustomFeatureName();
            }

            public Builder setCustomFeatureName(int value) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).setCustomFeatureName(value);
                return this;
            }

            public Builder clearCustomFeatureName() {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).clearCustomFeatureName();
                return this;
            }

            public List<VEInfo> getVesAppearedInTheFlowList() {
                return Collections.unmodifiableList(((SystemHealthMetricExtension) this.instance).getVesAppearedInTheFlowList());
            }

            public int getVesAppearedInTheFlowCount() {
                return ((SystemHealthMetricExtension) this.instance).getVesAppearedInTheFlowCount();
            }

            public VEInfo getVesAppearedInTheFlow(int index) {
                return ((SystemHealthMetricExtension) this.instance).getVesAppearedInTheFlow(index);
            }

            public Builder setVesAppearedInTheFlow(int index, VEInfo value) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).setVesAppearedInTheFlow(index, value);
                return this;
            }

            public Builder setVesAppearedInTheFlow(int index, VEInfo.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).setVesAppearedInTheFlow(index, builderForValue);
                return this;
            }

            public Builder addVesAppearedInTheFlow(VEInfo value) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).addVesAppearedInTheFlow(value);
                return this;
            }

            public Builder addVesAppearedInTheFlow(int index, VEInfo value) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).addVesAppearedInTheFlow(index, value);
                return this;
            }

            public Builder addVesAppearedInTheFlow(VEInfo.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).addVesAppearedInTheFlow(builderForValue);
                return this;
            }

            public Builder addVesAppearedInTheFlow(int index, VEInfo.Builder builderForValue) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).addVesAppearedInTheFlow(index, builderForValue);
                return this;
            }

            public Builder addAllVesAppearedInTheFlow(Iterable<? extends VEInfo> values) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).addAllVesAppearedInTheFlow(values);
                return this;
            }

            public Builder clearVesAppearedInTheFlow() {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).clearVesAppearedInTheFlow();
                return this;
            }

            public Builder removeVesAppearedInTheFlow(int index) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).removeVesAppearedInTheFlow(index);
                return this;
            }

            public List<Integer> getOverlappingEventsList() {
                return Collections.unmodifiableList(((SystemHealthMetricExtension) this.instance).getOverlappingEventsList());
            }

            public int getOverlappingEventsCount() {
                return ((SystemHealthMetricExtension) this.instance).getOverlappingEventsCount();
            }

            public int getOverlappingEvents(int index) {
                return ((SystemHealthMetricExtension) this.instance).getOverlappingEvents(index);
            }

            public Builder setOverlappingEvents(int index, int value) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).setOverlappingEvents(index, value);
                return this;
            }

            public Builder addOverlappingEvents(int value) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).addOverlappingEvents(value);
                return this;
            }

            public Builder addAllOverlappingEvents(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).addAllOverlappingEvents(values);
                return this;
            }

            public Builder clearOverlappingEvents() {
                copyOnWrite();
                ((SystemHealthMetricExtension) this.instance).clearOverlappingEvents();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new SystemHealthMetricExtension();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0005\u0000\u0001\u0001\u0005\u0005\u0000\u0002\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\u0004\u0002\u0004\u001b\u0005\u0016", new Object[]{"bitField0_", "customEntryPoint_", "customExitPoint_", "customFeatureName_", "vesAppearedInTheFlow_", VEInfo.class, "overlappingEvents_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<SystemHealthMetricExtension> parser = PARSER;
                    if (parser == null) {
                        synchronized (SystemHealthMetricExtension.class) {
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
            GeneratedMessageLite.registerDefaultInstance(SystemHealthMetricExtension.class, DEFAULT_INSTANCE);
        }

        public static SystemHealthMetricExtension getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<SystemHealthMetricExtension> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
