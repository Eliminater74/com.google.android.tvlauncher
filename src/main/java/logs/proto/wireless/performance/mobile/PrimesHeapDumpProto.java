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

public final class PrimesHeapDumpProto {

    public interface ArrayInstanceOrBuilder extends MessageLiteOrBuilder {
        int getClazz();

        int getValues(int i);

        int getValuesCount();

        List<Integer> getValuesList();

        boolean hasClazz();
    }

    public interface ClassInfoOrBuilder extends MessageLiteOrBuilder {
        String getClassName();

        ByteString getClassNameBytes();

        int getInstanceSize();

        int getSuperClass();

        int getValues(int i);

        int getValuesCount();

        List<Integer> getValuesList();

        boolean hasClassName();

        boolean hasInstanceSize();

        boolean hasSuperClass();
    }

    public interface ClassInstanceOrBuilder extends MessageLiteOrBuilder {
        int getClazz();

        int getValues(int i);

        int getValuesCount();

        List<Integer> getValuesList();

        boolean hasClazz();
    }

    public interface CollapsedArrayInstanceOrBuilder extends MessageLiteOrBuilder {
        int getClazz();

        int getElementClass();

        int getLength();

        int getNumElements();

        PrimitiveType getPrimitiveArrayType();

        int getRetainedBytes();

        boolean hasClazz();

        boolean hasElementClass();

        boolean hasLength();

        boolean hasNumElements();

        boolean hasPrimitiveArrayType();

        boolean hasRetainedBytes();
    }

    public interface HeapDumpContextOrBuilder extends MessageLiteOrBuilder {
        long getAllocatedBytes();

        long getGarbageCollectedBytes();

        int getTotalPssKb();

        HeapDumpContext.TriggerType getTriggerType();

        boolean hasAllocatedBytes();

        boolean hasGarbageCollectedBytes();

        boolean hasTotalPssKb();

        boolean hasTriggerType();
    }

    public interface PrimesHeapDumpOrBuilder extends MessageLiteOrBuilder {
        ArrayInstance getArrayInstance(int i);

        int getArrayInstanceCount();

        List<ArrayInstance> getArrayInstanceList();

        ClassInfo getClassInfo(int i);

        int getClassInfoCount();

        List<ClassInfo> getClassInfoList();

        ClassInstance getClassInstance(int i);

        int getClassInstanceCount();

        List<ClassInstance> getClassInstanceList();

        CollapsedArrayInstance getCollapsedArrayInstance(int i);

        int getCollapsedArrayInstanceCount();

        List<CollapsedArrayInstance> getCollapsedArrayInstanceList();

        HeapDumpContext getContext();

        PrimitiveArrayInstance getPrimitiveArrayInstance(int i);

        int getPrimitiveArrayInstanceCount();

        List<PrimitiveArrayInstance> getPrimitiveArrayInstanceList();

        Root getRoots(int i);

        int getRootsCount();

        List<Root> getRootsList();

        @Deprecated
        int getTotalPss();

        boolean hasContext();

        @Deprecated
        boolean hasTotalPss();
    }

    public interface PrimitiveArrayInstanceOrBuilder extends MessageLiteOrBuilder {
        int getNumElements();

        PrimitiveType getType();

        boolean hasNumElements();

        boolean hasType();
    }

    public interface RootOrBuilder extends MessageLiteOrBuilder {
        int getNodes(int i);

        int getNodesCount();

        List<Integer> getNodesList();

        RootTag getTag();

        boolean hasTag();
    }

    private PrimesHeapDumpProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public enum PrimitiveType implements Internal.EnumLite {
        BOOLEAN(4),
        CHAR(5),
        FLOAT(6),
        DOUBLE(7),
        BYTE(8),
        SHORT(9),
        INT(10),
        LONG(11);
        
        public static final int BOOLEAN_VALUE = 4;
        public static final int BYTE_VALUE = 8;
        public static final int CHAR_VALUE = 5;
        public static final int DOUBLE_VALUE = 7;
        public static final int FLOAT_VALUE = 6;
        public static final int INT_VALUE = 10;
        public static final int LONG_VALUE = 11;
        public static final int SHORT_VALUE = 9;
        private static final Internal.EnumLiteMap<PrimitiveType> internalValueMap = new Internal.EnumLiteMap<PrimitiveType>() {
            public PrimitiveType findValueByNumber(int number) {
                return PrimitiveType.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static PrimitiveType forNumber(int value2) {
            switch (value2) {
                case 4:
                    return BOOLEAN;
                case 5:
                    return CHAR;
                case 6:
                    return FLOAT;
                case 7:
                    return DOUBLE;
                case 8:
                    return BYTE;
                case 9:
                    return SHORT;
                case 10:
                    return INT;
                case 11:
                    return LONG;
                default:
                    return null;
            }
        }

        public static Internal.EnumLiteMap<PrimitiveType> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return PrimitiveTypeVerifier.INSTANCE;
        }

        private static final class PrimitiveTypeVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new PrimitiveTypeVerifier();

            private PrimitiveTypeVerifier() {
            }

            public boolean isInRange(int number) {
                return PrimitiveType.forNumber(number) != null;
            }
        }

        private PrimitiveType(int value2) {
            this.value = value2;
        }
    }

    public enum RootTag implements Internal.EnumLite {
        ROOT_TAG_DEFAULT(0),
        UNKNOWN(255),
        JNI_GLOBAL(1),
        JNI_LOCAL(2),
        JAVA_LOCAL(3),
        NATIVE_STACK(4),
        STICKY_CLASS(5),
        THREAD_BLOCK(6),
        MONITOR_USED(7),
        THREAD_OBJECT(8),
        INTERNED_STRING(137),
        FINALIZING(138),
        DEBUGGER(139),
        REFERENCE_CLEANUP(140),
        VM_INTERNAL(141),
        JNI_MONITOR(142),
        UNREACHABLE(144);
        
        public static final int DEBUGGER_VALUE = 139;
        public static final int FINALIZING_VALUE = 138;
        public static final int INTERNED_STRING_VALUE = 137;
        public static final int JAVA_LOCAL_VALUE = 3;
        public static final int JNI_GLOBAL_VALUE = 1;
        public static final int JNI_LOCAL_VALUE = 2;
        public static final int JNI_MONITOR_VALUE = 142;
        public static final int MONITOR_USED_VALUE = 7;
        public static final int NATIVE_STACK_VALUE = 4;
        public static final int REFERENCE_CLEANUP_VALUE = 140;
        public static final int ROOT_TAG_DEFAULT_VALUE = 0;
        public static final int STICKY_CLASS_VALUE = 5;
        public static final int THREAD_BLOCK_VALUE = 6;
        public static final int THREAD_OBJECT_VALUE = 8;
        public static final int UNKNOWN_VALUE = 255;
        public static final int UNREACHABLE_VALUE = 144;
        public static final int VM_INTERNAL_VALUE = 141;
        private static final Internal.EnumLiteMap<RootTag> internalValueMap = new Internal.EnumLiteMap<RootTag>() {
            public RootTag findValueByNumber(int number) {
                return RootTag.forNumber(number);
            }
        };
        private final int value;

        public final int getNumber() {
            return this.value;
        }

        public static RootTag forNumber(int value2) {
            if (value2 == 144) {
                return UNREACHABLE;
            }
            if (value2 == 255) {
                return UNKNOWN;
            }
            switch (value2) {
                case 0:
                    return ROOT_TAG_DEFAULT;
                case 1:
                    return JNI_GLOBAL;
                case 2:
                    return JNI_LOCAL;
                case 3:
                    return JAVA_LOCAL;
                case 4:
                    return NATIVE_STACK;
                case 5:
                    return STICKY_CLASS;
                case 6:
                    return THREAD_BLOCK;
                case 7:
                    return MONITOR_USED;
                case 8:
                    return THREAD_OBJECT;
                default:
                    switch (value2) {
                        case 137:
                            return INTERNED_STRING;
                        case 138:
                            return FINALIZING;
                        case 139:
                            return DEBUGGER;
                        case 140:
                            return REFERENCE_CLEANUP;
                        case 141:
                            return VM_INTERNAL;
                        case 142:
                            return JNI_MONITOR;
                        default:
                            return null;
                    }
            }
        }

        public static Internal.EnumLiteMap<RootTag> internalGetValueMap() {
            return internalValueMap;
        }

        public static Internal.EnumVerifier internalGetVerifier() {
            return RootTagVerifier.INSTANCE;
        }

        private static final class RootTagVerifier implements Internal.EnumVerifier {
            static final Internal.EnumVerifier INSTANCE = new RootTagVerifier();

            private RootTagVerifier() {
            }

            public boolean isInRange(int number) {
                return RootTag.forNumber(number) != null;
            }
        }

        private RootTag(int value2) {
            this.value = value2;
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimesHeapDump extends GeneratedMessageLite<PrimesHeapDump, Builder> implements PrimesHeapDumpOrBuilder {
        public static final int ARRAY_INSTANCE_FIELD_NUMBER = 3;
        public static final int CLASS_INFO_FIELD_NUMBER = 1;
        public static final int CLASS_INSTANCE_FIELD_NUMBER = 2;
        public static final int COLLAPSED_ARRAY_INSTANCE_FIELD_NUMBER = 8;
        public static final int CONTEXT_FIELD_NUMBER = 7;
        /* access modifiers changed from: private */
        public static final PrimesHeapDump DEFAULT_INSTANCE = new PrimesHeapDump();
        private static volatile Parser<PrimesHeapDump> PARSER = null;
        public static final int PRIMITIVE_ARRAY_INSTANCE_FIELD_NUMBER = 4;
        public static final int ROOTS_FIELD_NUMBER = 6;
        public static final int TOTAL_PSS_FIELD_NUMBER = 5;
        @ProtoField(fieldNumber = 3, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ArrayInstance> arrayInstance_ = emptyProtobufList();
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ClassInfo> classInfo_ = emptyProtobufList();
        @ProtoField(fieldNumber = 2, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<ClassInstance> classInstance_ = emptyProtobufList();
        @ProtoField(fieldNumber = 8, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<CollapsedArrayInstance> collapsedArrayInstance_ = emptyProtobufList();
        @ProtoField(fieldNumber = 7, isRequired = false, type = FieldType.MESSAGE)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private HeapDumpContext context_;
        @ProtoField(fieldNumber = 4, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<PrimitiveArrayInstance> primitiveArrayInstance_ = emptyProtobufList();
        @ProtoField(fieldNumber = 6, type = FieldType.MESSAGE_LIST)
        private Internal.ProtobufList<Root> roots_ = emptyProtobufList();
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int totalPss_;

        private PrimesHeapDump() {
        }

        public List<ClassInfo> getClassInfoList() {
            return this.classInfo_;
        }

        public List<? extends ClassInfoOrBuilder> getClassInfoOrBuilderList() {
            return this.classInfo_;
        }

        public int getClassInfoCount() {
            return this.classInfo_.size();
        }

        public ClassInfo getClassInfo(int index) {
            return this.classInfo_.get(index);
        }

        public ClassInfoOrBuilder getClassInfoOrBuilder(int index) {
            return this.classInfo_.get(index);
        }

        private void ensureClassInfoIsMutable() {
            if (!this.classInfo_.isModifiable()) {
                this.classInfo_ = GeneratedMessageLite.mutableCopy(this.classInfo_);
            }
        }

        /* access modifiers changed from: private */
        public void setClassInfo(int index, ClassInfo value) {
            if (value != null) {
                ensureClassInfoIsMutable();
                this.classInfo_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setClassInfo(int index, ClassInfo.Builder builderForValue) {
            ensureClassInfoIsMutable();
            this.classInfo_.set(index, (ClassInfo) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addClassInfo(ClassInfo value) {
            if (value != null) {
                ensureClassInfoIsMutable();
                this.classInfo_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addClassInfo(int index, ClassInfo value) {
            if (value != null) {
                ensureClassInfoIsMutable();
                this.classInfo_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addClassInfo(ClassInfo.Builder builderForValue) {
            ensureClassInfoIsMutable();
            this.classInfo_.add((ClassInfo) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addClassInfo(int index, ClassInfo.Builder builderForValue) {
            ensureClassInfoIsMutable();
            this.classInfo_.add(index, (ClassInfo) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$ClassInfo>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$ClassInfo>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllClassInfo(Iterable<? extends ClassInfo> values) {
            ensureClassInfoIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.classInfo_);
        }

        /* access modifiers changed from: private */
        public void clearClassInfo() {
            this.classInfo_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeClassInfo(int index) {
            ensureClassInfoIsMutable();
            this.classInfo_.remove(index);
        }

        public List<ClassInstance> getClassInstanceList() {
            return this.classInstance_;
        }

        public List<? extends ClassInstanceOrBuilder> getClassInstanceOrBuilderList() {
            return this.classInstance_;
        }

        public int getClassInstanceCount() {
            return this.classInstance_.size();
        }

        public ClassInstance getClassInstance(int index) {
            return this.classInstance_.get(index);
        }

        public ClassInstanceOrBuilder getClassInstanceOrBuilder(int index) {
            return this.classInstance_.get(index);
        }

        private void ensureClassInstanceIsMutable() {
            if (!this.classInstance_.isModifiable()) {
                this.classInstance_ = GeneratedMessageLite.mutableCopy(this.classInstance_);
            }
        }

        /* access modifiers changed from: private */
        public void setClassInstance(int index, ClassInstance value) {
            if (value != null) {
                ensureClassInstanceIsMutable();
                this.classInstance_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setClassInstance(int index, ClassInstance.Builder builderForValue) {
            ensureClassInstanceIsMutable();
            this.classInstance_.set(index, (ClassInstance) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addClassInstance(ClassInstance value) {
            if (value != null) {
                ensureClassInstanceIsMutable();
                this.classInstance_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addClassInstance(int index, ClassInstance value) {
            if (value != null) {
                ensureClassInstanceIsMutable();
                this.classInstance_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addClassInstance(ClassInstance.Builder builderForValue) {
            ensureClassInstanceIsMutable();
            this.classInstance_.add((ClassInstance) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addClassInstance(int index, ClassInstance.Builder builderForValue) {
            ensureClassInstanceIsMutable();
            this.classInstance_.add(index, (ClassInstance) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$ClassInstance>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$ClassInstance>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllClassInstance(Iterable<? extends ClassInstance> values) {
            ensureClassInstanceIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.classInstance_);
        }

        /* access modifiers changed from: private */
        public void clearClassInstance() {
            this.classInstance_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeClassInstance(int index) {
            ensureClassInstanceIsMutable();
            this.classInstance_.remove(index);
        }

        public List<ArrayInstance> getArrayInstanceList() {
            return this.arrayInstance_;
        }

        public List<? extends ArrayInstanceOrBuilder> getArrayInstanceOrBuilderList() {
            return this.arrayInstance_;
        }

        public int getArrayInstanceCount() {
            return this.arrayInstance_.size();
        }

        public ArrayInstance getArrayInstance(int index) {
            return this.arrayInstance_.get(index);
        }

        public ArrayInstanceOrBuilder getArrayInstanceOrBuilder(int index) {
            return this.arrayInstance_.get(index);
        }

        private void ensureArrayInstanceIsMutable() {
            if (!this.arrayInstance_.isModifiable()) {
                this.arrayInstance_ = GeneratedMessageLite.mutableCopy(this.arrayInstance_);
            }
        }

        /* access modifiers changed from: private */
        public void setArrayInstance(int index, ArrayInstance value) {
            if (value != null) {
                ensureArrayInstanceIsMutable();
                this.arrayInstance_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setArrayInstance(int index, ArrayInstance.Builder builderForValue) {
            ensureArrayInstanceIsMutable();
            this.arrayInstance_.set(index, (ArrayInstance) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addArrayInstance(ArrayInstance value) {
            if (value != null) {
                ensureArrayInstanceIsMutable();
                this.arrayInstance_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addArrayInstance(int index, ArrayInstance value) {
            if (value != null) {
                ensureArrayInstanceIsMutable();
                this.arrayInstance_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addArrayInstance(ArrayInstance.Builder builderForValue) {
            ensureArrayInstanceIsMutable();
            this.arrayInstance_.add((ArrayInstance) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addArrayInstance(int index, ArrayInstance.Builder builderForValue) {
            ensureArrayInstanceIsMutable();
            this.arrayInstance_.add(index, (ArrayInstance) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$ArrayInstance>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$ArrayInstance>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllArrayInstance(Iterable<? extends ArrayInstance> values) {
            ensureArrayInstanceIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.arrayInstance_);
        }

        /* access modifiers changed from: private */
        public void clearArrayInstance() {
            this.arrayInstance_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeArrayInstance(int index) {
            ensureArrayInstanceIsMutable();
            this.arrayInstance_.remove(index);
        }

        public List<PrimitiveArrayInstance> getPrimitiveArrayInstanceList() {
            return this.primitiveArrayInstance_;
        }

        public List<? extends PrimitiveArrayInstanceOrBuilder> getPrimitiveArrayInstanceOrBuilderList() {
            return this.primitiveArrayInstance_;
        }

        public int getPrimitiveArrayInstanceCount() {
            return this.primitiveArrayInstance_.size();
        }

        public PrimitiveArrayInstance getPrimitiveArrayInstance(int index) {
            return this.primitiveArrayInstance_.get(index);
        }

        public PrimitiveArrayInstanceOrBuilder getPrimitiveArrayInstanceOrBuilder(int index) {
            return this.primitiveArrayInstance_.get(index);
        }

        private void ensurePrimitiveArrayInstanceIsMutable() {
            if (!this.primitiveArrayInstance_.isModifiable()) {
                this.primitiveArrayInstance_ = GeneratedMessageLite.mutableCopy(this.primitiveArrayInstance_);
            }
        }

        /* access modifiers changed from: private */
        public void setPrimitiveArrayInstance(int index, PrimitiveArrayInstance value) {
            if (value != null) {
                ensurePrimitiveArrayInstanceIsMutable();
                this.primitiveArrayInstance_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setPrimitiveArrayInstance(int index, PrimitiveArrayInstance.Builder builderForValue) {
            ensurePrimitiveArrayInstanceIsMutable();
            this.primitiveArrayInstance_.set(index, (PrimitiveArrayInstance) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addPrimitiveArrayInstance(PrimitiveArrayInstance value) {
            if (value != null) {
                ensurePrimitiveArrayInstanceIsMutable();
                this.primitiveArrayInstance_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addPrimitiveArrayInstance(int index, PrimitiveArrayInstance value) {
            if (value != null) {
                ensurePrimitiveArrayInstanceIsMutable();
                this.primitiveArrayInstance_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addPrimitiveArrayInstance(PrimitiveArrayInstance.Builder builderForValue) {
            ensurePrimitiveArrayInstanceIsMutable();
            this.primitiveArrayInstance_.add((PrimitiveArrayInstance) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addPrimitiveArrayInstance(int index, PrimitiveArrayInstance.Builder builderForValue) {
            ensurePrimitiveArrayInstanceIsMutable();
            this.primitiveArrayInstance_.add(index, (PrimitiveArrayInstance) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$PrimitiveArrayInstance>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$PrimitiveArrayInstance>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllPrimitiveArrayInstance(Iterable<? extends PrimitiveArrayInstance> values) {
            ensurePrimitiveArrayInstanceIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.primitiveArrayInstance_);
        }

        /* access modifiers changed from: private */
        public void clearPrimitiveArrayInstance() {
            this.primitiveArrayInstance_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removePrimitiveArrayInstance(int index) {
            ensurePrimitiveArrayInstanceIsMutable();
            this.primitiveArrayInstance_.remove(index);
        }

        public List<CollapsedArrayInstance> getCollapsedArrayInstanceList() {
            return this.collapsedArrayInstance_;
        }

        public List<? extends CollapsedArrayInstanceOrBuilder> getCollapsedArrayInstanceOrBuilderList() {
            return this.collapsedArrayInstance_;
        }

        public int getCollapsedArrayInstanceCount() {
            return this.collapsedArrayInstance_.size();
        }

        public CollapsedArrayInstance getCollapsedArrayInstance(int index) {
            return this.collapsedArrayInstance_.get(index);
        }

        public CollapsedArrayInstanceOrBuilder getCollapsedArrayInstanceOrBuilder(int index) {
            return this.collapsedArrayInstance_.get(index);
        }

        private void ensureCollapsedArrayInstanceIsMutable() {
            if (!this.collapsedArrayInstance_.isModifiable()) {
                this.collapsedArrayInstance_ = GeneratedMessageLite.mutableCopy(this.collapsedArrayInstance_);
            }
        }

        /* access modifiers changed from: private */
        public void setCollapsedArrayInstance(int index, CollapsedArrayInstance value) {
            if (value != null) {
                ensureCollapsedArrayInstanceIsMutable();
                this.collapsedArrayInstance_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setCollapsedArrayInstance(int index, CollapsedArrayInstance.Builder builderForValue) {
            ensureCollapsedArrayInstanceIsMutable();
            this.collapsedArrayInstance_.set(index, (CollapsedArrayInstance) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addCollapsedArrayInstance(CollapsedArrayInstance value) {
            if (value != null) {
                ensureCollapsedArrayInstanceIsMutable();
                this.collapsedArrayInstance_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addCollapsedArrayInstance(int index, CollapsedArrayInstance value) {
            if (value != null) {
                ensureCollapsedArrayInstanceIsMutable();
                this.collapsedArrayInstance_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addCollapsedArrayInstance(CollapsedArrayInstance.Builder builderForValue) {
            ensureCollapsedArrayInstanceIsMutable();
            this.collapsedArrayInstance_.add((CollapsedArrayInstance) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addCollapsedArrayInstance(int index, CollapsedArrayInstance.Builder builderForValue) {
            ensureCollapsedArrayInstanceIsMutable();
            this.collapsedArrayInstance_.add(index, (CollapsedArrayInstance) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$CollapsedArrayInstance>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$CollapsedArrayInstance>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllCollapsedArrayInstance(Iterable<? extends CollapsedArrayInstance> values) {
            ensureCollapsedArrayInstanceIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.collapsedArrayInstance_);
        }

        /* access modifiers changed from: private */
        public void clearCollapsedArrayInstance() {
            this.collapsedArrayInstance_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeCollapsedArrayInstance(int index) {
            ensureCollapsedArrayInstanceIsMutable();
            this.collapsedArrayInstance_.remove(index);
        }

        public List<Root> getRootsList() {
            return this.roots_;
        }

        public List<? extends RootOrBuilder> getRootsOrBuilderList() {
            return this.roots_;
        }

        public int getRootsCount() {
            return this.roots_.size();
        }

        public Root getRoots(int index) {
            return this.roots_.get(index);
        }

        public RootOrBuilder getRootsOrBuilder(int index) {
            return this.roots_.get(index);
        }

        private void ensureRootsIsMutable() {
            if (!this.roots_.isModifiable()) {
                this.roots_ = GeneratedMessageLite.mutableCopy(this.roots_);
            }
        }

        /* access modifiers changed from: private */
        public void setRoots(int index, Root value) {
            if (value != null) {
                ensureRootsIsMutable();
                this.roots_.set(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setRoots(int index, Root.Builder builderForValue) {
            ensureRootsIsMutable();
            this.roots_.set(index, (Root) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addRoots(Root value) {
            if (value != null) {
                ensureRootsIsMutable();
                this.roots_.add(value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addRoots(int index, Root value) {
            if (value != null) {
                ensureRootsIsMutable();
                this.roots_.add(index, value);
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void addRoots(Root.Builder builderForValue) {
            ensureRootsIsMutable();
            this.roots_.add((Root) builderForValue.build());
        }

        /* access modifiers changed from: private */
        public void addRoots(int index, Root.Builder builderForValue) {
            ensureRootsIsMutable();
            this.roots_.add(index, (Root) builderForValue.build());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$Root>, com.google.protobuf.Internal$ProtobufList<logs.proto.wireless.performance.mobile.PrimesHeapDumpProto$Root>]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllRoots(Iterable<? extends Root> values) {
            ensureRootsIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.roots_);
        }

        /* access modifiers changed from: private */
        public void clearRoots() {
            this.roots_ = emptyProtobufList();
        }

        /* access modifiers changed from: private */
        public void removeRoots(int index) {
            ensureRootsIsMutable();
            this.roots_.remove(index);
        }

        @Deprecated
        public boolean hasTotalPss() {
            return (this.bitField0_ & 1) != 0;
        }

        @Deprecated
        public int getTotalPss() {
            return this.totalPss_;
        }

        /* access modifiers changed from: private */
        public void setTotalPss(int value) {
            this.bitField0_ |= 1;
            this.totalPss_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalPss() {
            this.bitField0_ &= -2;
            this.totalPss_ = 0;
        }

        public boolean hasContext() {
            return (this.bitField0_ & 2) != 0;
        }

        public HeapDumpContext getContext() {
            HeapDumpContext heapDumpContext = this.context_;
            return heapDumpContext == null ? HeapDumpContext.getDefaultInstance() : heapDumpContext;
        }

        /* access modifiers changed from: private */
        public void setContext(HeapDumpContext value) {
            if (value != null) {
                this.context_ = value;
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void setContext(HeapDumpContext.Builder builderForValue) {
            this.context_ = (HeapDumpContext) builderForValue.build();
            this.bitField0_ |= 2;
        }

        /* access modifiers changed from: private */
        public void mergeContext(HeapDumpContext value) {
            if (value != null) {
                HeapDumpContext heapDumpContext = this.context_;
                if (heapDumpContext == null || heapDumpContext == HeapDumpContext.getDefaultInstance()) {
                    this.context_ = value;
                } else {
                    this.context_ = (HeapDumpContext) ((HeapDumpContext.Builder) HeapDumpContext.newBuilder(this.context_).mergeFrom((GeneratedMessageLite) value)).buildPartial();
                }
                this.bitField0_ |= 2;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearContext() {
            this.context_ = null;
            this.bitField0_ &= -3;
        }

        public static PrimesHeapDump parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDump parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDump parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDump parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDump parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimesHeapDump parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimesHeapDump parseFrom(InputStream input) throws IOException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDump parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesHeapDump parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimesHeapDump) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDump parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDump) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimesHeapDump parseFrom(CodedInputStream input) throws IOException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimesHeapDump parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimesHeapDump) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimesHeapDump prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PrimesHeapDump, Builder> implements PrimesHeapDumpOrBuilder {
            private Builder() {
                super(PrimesHeapDump.DEFAULT_INSTANCE);
            }

            public List<ClassInfo> getClassInfoList() {
                return Collections.unmodifiableList(((PrimesHeapDump) this.instance).getClassInfoList());
            }

            public int getClassInfoCount() {
                return ((PrimesHeapDump) this.instance).getClassInfoCount();
            }

            public ClassInfo getClassInfo(int index) {
                return ((PrimesHeapDump) this.instance).getClassInfo(index);
            }

            public Builder setClassInfo(int index, ClassInfo value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setClassInfo(index, value);
                return this;
            }

            public Builder setClassInfo(int index, ClassInfo.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setClassInfo(index, builderForValue);
                return this;
            }

            public Builder addClassInfo(ClassInfo value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addClassInfo(value);
                return this;
            }

            public Builder addClassInfo(int index, ClassInfo value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addClassInfo(index, value);
                return this;
            }

            public Builder addClassInfo(ClassInfo.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addClassInfo(builderForValue);
                return this;
            }

            public Builder addClassInfo(int index, ClassInfo.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addClassInfo(index, builderForValue);
                return this;
            }

            public Builder addAllClassInfo(Iterable<? extends ClassInfo> values) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addAllClassInfo(values);
                return this;
            }

            public Builder clearClassInfo() {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).clearClassInfo();
                return this;
            }

            public Builder removeClassInfo(int index) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).removeClassInfo(index);
                return this;
            }

            public List<ClassInstance> getClassInstanceList() {
                return Collections.unmodifiableList(((PrimesHeapDump) this.instance).getClassInstanceList());
            }

            public int getClassInstanceCount() {
                return ((PrimesHeapDump) this.instance).getClassInstanceCount();
            }

            public ClassInstance getClassInstance(int index) {
                return ((PrimesHeapDump) this.instance).getClassInstance(index);
            }

            public Builder setClassInstance(int index, ClassInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setClassInstance(index, value);
                return this;
            }

            public Builder setClassInstance(int index, ClassInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setClassInstance(index, builderForValue);
                return this;
            }

            public Builder addClassInstance(ClassInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addClassInstance(value);
                return this;
            }

            public Builder addClassInstance(int index, ClassInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addClassInstance(index, value);
                return this;
            }

            public Builder addClassInstance(ClassInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addClassInstance(builderForValue);
                return this;
            }

            public Builder addClassInstance(int index, ClassInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addClassInstance(index, builderForValue);
                return this;
            }

            public Builder addAllClassInstance(Iterable<? extends ClassInstance> values) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addAllClassInstance(values);
                return this;
            }

            public Builder clearClassInstance() {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).clearClassInstance();
                return this;
            }

            public Builder removeClassInstance(int index) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).removeClassInstance(index);
                return this;
            }

            public List<ArrayInstance> getArrayInstanceList() {
                return Collections.unmodifiableList(((PrimesHeapDump) this.instance).getArrayInstanceList());
            }

            public int getArrayInstanceCount() {
                return ((PrimesHeapDump) this.instance).getArrayInstanceCount();
            }

            public ArrayInstance getArrayInstance(int index) {
                return ((PrimesHeapDump) this.instance).getArrayInstance(index);
            }

            public Builder setArrayInstance(int index, ArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setArrayInstance(index, value);
                return this;
            }

            public Builder setArrayInstance(int index, ArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setArrayInstance(index, builderForValue);
                return this;
            }

            public Builder addArrayInstance(ArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addArrayInstance(value);
                return this;
            }

            public Builder addArrayInstance(int index, ArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addArrayInstance(index, value);
                return this;
            }

            public Builder addArrayInstance(ArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addArrayInstance(builderForValue);
                return this;
            }

            public Builder addArrayInstance(int index, ArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addArrayInstance(index, builderForValue);
                return this;
            }

            public Builder addAllArrayInstance(Iterable<? extends ArrayInstance> values) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addAllArrayInstance(values);
                return this;
            }

            public Builder clearArrayInstance() {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).clearArrayInstance();
                return this;
            }

            public Builder removeArrayInstance(int index) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).removeArrayInstance(index);
                return this;
            }

            public List<PrimitiveArrayInstance> getPrimitiveArrayInstanceList() {
                return Collections.unmodifiableList(((PrimesHeapDump) this.instance).getPrimitiveArrayInstanceList());
            }

            public int getPrimitiveArrayInstanceCount() {
                return ((PrimesHeapDump) this.instance).getPrimitiveArrayInstanceCount();
            }

            public PrimitiveArrayInstance getPrimitiveArrayInstance(int index) {
                return ((PrimesHeapDump) this.instance).getPrimitiveArrayInstance(index);
            }

            public Builder setPrimitiveArrayInstance(int index, PrimitiveArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setPrimitiveArrayInstance(index, value);
                return this;
            }

            public Builder setPrimitiveArrayInstance(int index, PrimitiveArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setPrimitiveArrayInstance(index, builderForValue);
                return this;
            }

            public Builder addPrimitiveArrayInstance(PrimitiveArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addPrimitiveArrayInstance(value);
                return this;
            }

            public Builder addPrimitiveArrayInstance(int index, PrimitiveArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addPrimitiveArrayInstance(index, value);
                return this;
            }

            public Builder addPrimitiveArrayInstance(PrimitiveArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addPrimitiveArrayInstance(builderForValue);
                return this;
            }

            public Builder addPrimitiveArrayInstance(int index, PrimitiveArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addPrimitiveArrayInstance(index, builderForValue);
                return this;
            }

            public Builder addAllPrimitiveArrayInstance(Iterable<? extends PrimitiveArrayInstance> values) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addAllPrimitiveArrayInstance(values);
                return this;
            }

            public Builder clearPrimitiveArrayInstance() {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).clearPrimitiveArrayInstance();
                return this;
            }

            public Builder removePrimitiveArrayInstance(int index) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).removePrimitiveArrayInstance(index);
                return this;
            }

            public List<CollapsedArrayInstance> getCollapsedArrayInstanceList() {
                return Collections.unmodifiableList(((PrimesHeapDump) this.instance).getCollapsedArrayInstanceList());
            }

            public int getCollapsedArrayInstanceCount() {
                return ((PrimesHeapDump) this.instance).getCollapsedArrayInstanceCount();
            }

            public CollapsedArrayInstance getCollapsedArrayInstance(int index) {
                return ((PrimesHeapDump) this.instance).getCollapsedArrayInstance(index);
            }

            public Builder setCollapsedArrayInstance(int index, CollapsedArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setCollapsedArrayInstance(index, value);
                return this;
            }

            public Builder setCollapsedArrayInstance(int index, CollapsedArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setCollapsedArrayInstance(index, builderForValue);
                return this;
            }

            public Builder addCollapsedArrayInstance(CollapsedArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addCollapsedArrayInstance(value);
                return this;
            }

            public Builder addCollapsedArrayInstance(int index, CollapsedArrayInstance value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addCollapsedArrayInstance(index, value);
                return this;
            }

            public Builder addCollapsedArrayInstance(CollapsedArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addCollapsedArrayInstance(builderForValue);
                return this;
            }

            public Builder addCollapsedArrayInstance(int index, CollapsedArrayInstance.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addCollapsedArrayInstance(index, builderForValue);
                return this;
            }

            public Builder addAllCollapsedArrayInstance(Iterable<? extends CollapsedArrayInstance> values) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addAllCollapsedArrayInstance(values);
                return this;
            }

            public Builder clearCollapsedArrayInstance() {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).clearCollapsedArrayInstance();
                return this;
            }

            public Builder removeCollapsedArrayInstance(int index) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).removeCollapsedArrayInstance(index);
                return this;
            }

            public List<Root> getRootsList() {
                return Collections.unmodifiableList(((PrimesHeapDump) this.instance).getRootsList());
            }

            public int getRootsCount() {
                return ((PrimesHeapDump) this.instance).getRootsCount();
            }

            public Root getRoots(int index) {
                return ((PrimesHeapDump) this.instance).getRoots(index);
            }

            public Builder setRoots(int index, Root value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setRoots(index, value);
                return this;
            }

            public Builder setRoots(int index, Root.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setRoots(index, builderForValue);
                return this;
            }

            public Builder addRoots(Root value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addRoots(value);
                return this;
            }

            public Builder addRoots(int index, Root value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addRoots(index, value);
                return this;
            }

            public Builder addRoots(Root.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addRoots(builderForValue);
                return this;
            }

            public Builder addRoots(int index, Root.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addRoots(index, builderForValue);
                return this;
            }

            public Builder addAllRoots(Iterable<? extends Root> values) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).addAllRoots(values);
                return this;
            }

            public Builder clearRoots() {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).clearRoots();
                return this;
            }

            public Builder removeRoots(int index) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).removeRoots(index);
                return this;
            }

            @Deprecated
            public boolean hasTotalPss() {
                return ((PrimesHeapDump) this.instance).hasTotalPss();
            }

            @Deprecated
            public int getTotalPss() {
                return ((PrimesHeapDump) this.instance).getTotalPss();
            }

            @Deprecated
            public Builder setTotalPss(int value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setTotalPss(value);
                return this;
            }

            @Deprecated
            public Builder clearTotalPss() {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).clearTotalPss();
                return this;
            }

            public boolean hasContext() {
                return ((PrimesHeapDump) this.instance).hasContext();
            }

            public HeapDumpContext getContext() {
                return ((PrimesHeapDump) this.instance).getContext();
            }

            public Builder setContext(HeapDumpContext value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setContext(value);
                return this;
            }

            public Builder setContext(HeapDumpContext.Builder builderForValue) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).setContext(builderForValue);
                return this;
            }

            public Builder mergeContext(HeapDumpContext value) {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).mergeContext(value);
                return this;
            }

            public Builder clearContext() {
                copyOnWrite();
                ((PrimesHeapDump) this.instance).clearContext();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimesHeapDump();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\b\u0000\u0001\u0001\b\b\u0000\u0006\u0000\u0001\u001b\u0002\u001b\u0003\u001b\u0004\u001b\u0005\u0004\u0000\u0006\u001b\u0007\t\u0001\b\u001b", new Object[]{"bitField0_", "classInfo_", ClassInfo.class, "classInstance_", ClassInstance.class, "arrayInstance_", ArrayInstance.class, "primitiveArrayInstance_", PrimitiveArrayInstance.class, "totalPss_", "roots_", Root.class, "context_", "collapsedArrayInstance_", CollapsedArrayInstance.class});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimesHeapDump> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimesHeapDump.class) {
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
            GeneratedMessageLite.registerDefaultInstance(PrimesHeapDump.class, DEFAULT_INSTANCE);
        }

        public static PrimesHeapDump getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimesHeapDump> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ClassInfo extends GeneratedMessageLite<ClassInfo, Builder> implements ClassInfoOrBuilder {
        public static final int CLASS_NAME_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final ClassInfo DEFAULT_INSTANCE = new ClassInfo();
        public static final int INSTANCE_SIZE_FIELD_NUMBER = 6;
        private static volatile Parser<ClassInfo> PARSER = null;
        public static final int SUPER_CLASS_FIELD_NUMBER = 1;
        public static final int VALUES_FIELD_NUMBER = 5;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isEnforceUtf8 = false, isRequired = false, type = FieldType.STRING)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private String className_ = "";
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int instanceSize_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int superClass_;
        private int valuesMemoizedSerializedSize = -1;
        @ProtoField(fieldNumber = 5, type = FieldType.INT32_LIST_PACKED)
        private Internal.IntList values_ = emptyIntList();

        private ClassInfo() {
        }

        public boolean hasSuperClass() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getSuperClass() {
            return this.superClass_;
        }

        /* access modifiers changed from: private */
        public void setSuperClass(int value) {
            this.bitField0_ |= 1;
            this.superClass_ = value;
        }

        /* access modifiers changed from: private */
        public void clearSuperClass() {
            this.bitField0_ &= -2;
            this.superClass_ = 0;
        }

        public boolean hasClassName() {
            return (this.bitField0_ & 2) != 0;
        }

        public String getClassName() {
            return this.className_;
        }

        public ByteString getClassNameBytes() {
            return ByteString.copyFromUtf8(this.className_);
        }

        /* access modifiers changed from: private */
        public void setClassName(String value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.className_ = value;
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearClassName() {
            this.bitField0_ &= -3;
            this.className_ = getDefaultInstance().getClassName();
        }

        /* access modifiers changed from: private */
        public void setClassNameBytes(ByteString value) {
            if (value != null) {
                this.bitField0_ |= 2;
                this.className_ = value.toStringUtf8();
                return;
            }
            throw new NullPointerException();
        }

        public List<Integer> getValuesList() {
            return this.values_;
        }

        public int getValuesCount() {
            return this.values_.size();
        }

        public int getValues(int index) {
            return this.values_.getInt(index);
        }

        private void ensureValuesIsMutable() {
            if (!this.values_.isModifiable()) {
                this.values_ = GeneratedMessageLite.mutableCopy(this.values_);
            }
        }

        /* access modifiers changed from: private */
        public void setValues(int index, int value) {
            ensureValuesIsMutable();
            this.values_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addValues(int value) {
            ensureValuesIsMutable();
            this.values_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllValues(Iterable<? extends Integer> values) {
            ensureValuesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.values_);
        }

        /* access modifiers changed from: private */
        public void clearValues() {
            this.values_ = emptyIntList();
        }

        public boolean hasInstanceSize() {
            return (this.bitField0_ & 4) != 0;
        }

        public int getInstanceSize() {
            return this.instanceSize_;
        }

        /* access modifiers changed from: private */
        public void setInstanceSize(int value) {
            this.bitField0_ |= 4;
            this.instanceSize_ = value;
        }

        /* access modifiers changed from: private */
        public void clearInstanceSize() {
            this.bitField0_ &= -5;
            this.instanceSize_ = 0;
        }

        public static ClassInfo parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClassInfo parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClassInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClassInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClassInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClassInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClassInfo parseFrom(InputStream input) throws IOException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ClassInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ClassInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (ClassInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ClassInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClassInfo) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ClassInfo parseFrom(CodedInputStream input) throws IOException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ClassInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClassInfo) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ClassInfo prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ClassInfo, Builder> implements ClassInfoOrBuilder {
            private Builder() {
                super(ClassInfo.DEFAULT_INSTANCE);
            }

            public boolean hasSuperClass() {
                return ((ClassInfo) this.instance).hasSuperClass();
            }

            public int getSuperClass() {
                return ((ClassInfo) this.instance).getSuperClass();
            }

            public Builder setSuperClass(int value) {
                copyOnWrite();
                ((ClassInfo) this.instance).setSuperClass(value);
                return this;
            }

            public Builder clearSuperClass() {
                copyOnWrite();
                ((ClassInfo) this.instance).clearSuperClass();
                return this;
            }

            public boolean hasClassName() {
                return ((ClassInfo) this.instance).hasClassName();
            }

            public String getClassName() {
                return ((ClassInfo) this.instance).getClassName();
            }

            public ByteString getClassNameBytes() {
                return ((ClassInfo) this.instance).getClassNameBytes();
            }

            public Builder setClassName(String value) {
                copyOnWrite();
                ((ClassInfo) this.instance).setClassName(value);
                return this;
            }

            public Builder clearClassName() {
                copyOnWrite();
                ((ClassInfo) this.instance).clearClassName();
                return this;
            }

            public Builder setClassNameBytes(ByteString value) {
                copyOnWrite();
                ((ClassInfo) this.instance).setClassNameBytes(value);
                return this;
            }

            public List<Integer> getValuesList() {
                return Collections.unmodifiableList(((ClassInfo) this.instance).getValuesList());
            }

            public int getValuesCount() {
                return ((ClassInfo) this.instance).getValuesCount();
            }

            public int getValues(int index) {
                return ((ClassInfo) this.instance).getValues(index);
            }

            public Builder setValues(int index, int value) {
                copyOnWrite();
                ((ClassInfo) this.instance).setValues(index, value);
                return this;
            }

            public Builder addValues(int value) {
                copyOnWrite();
                ((ClassInfo) this.instance).addValues(value);
                return this;
            }

            public Builder addAllValues(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((ClassInfo) this.instance).addAllValues(values);
                return this;
            }

            public Builder clearValues() {
                copyOnWrite();
                ((ClassInfo) this.instance).clearValues();
                return this;
            }

            public boolean hasInstanceSize() {
                return ((ClassInfo) this.instance).hasInstanceSize();
            }

            public int getInstanceSize() {
                return ((ClassInfo) this.instance).getInstanceSize();
            }

            public Builder setInstanceSize(int value) {
                copyOnWrite();
                ((ClassInfo) this.instance).setInstanceSize(value);
                return this;
            }

            public Builder clearInstanceSize() {
                copyOnWrite();
                ((ClassInfo) this.instance).clearInstanceSize();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ClassInfo();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0006\u0004\u0000\u0001\u0000\u0001\u0004\u0000\u0002\b\u0001\u0005'\u0006\u0004\u0002", new Object[]{"bitField0_", "superClass_", "className_", "values_", "instanceSize_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ClassInfo> parser = PARSER;
                    if (parser == null) {
                        synchronized (ClassInfo.class) {
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
            GeneratedMessageLite.registerDefaultInstance(ClassInfo.class, DEFAULT_INSTANCE);
        }

        public static ClassInfo getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ClassInfo> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ClassInstance extends GeneratedMessageLite<ClassInstance, Builder> implements ClassInstanceOrBuilder {
        public static final int CLAZZ_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final ClassInstance DEFAULT_INSTANCE = new ClassInstance();
        private static volatile Parser<ClassInstance> PARSER = null;
        public static final int VALUES_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int clazz_;
        private int valuesMemoizedSerializedSize = -1;
        @ProtoField(fieldNumber = 2, type = FieldType.INT32_LIST_PACKED)
        private Internal.IntList values_ = emptyIntList();

        private ClassInstance() {
        }

        public boolean hasClazz() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getClazz() {
            return this.clazz_;
        }

        /* access modifiers changed from: private */
        public void setClazz(int value) {
            this.bitField0_ |= 1;
            this.clazz_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClazz() {
            this.bitField0_ &= -2;
            this.clazz_ = 0;
        }

        public List<Integer> getValuesList() {
            return this.values_;
        }

        public int getValuesCount() {
            return this.values_.size();
        }

        public int getValues(int index) {
            return this.values_.getInt(index);
        }

        private void ensureValuesIsMutable() {
            if (!this.values_.isModifiable()) {
                this.values_ = GeneratedMessageLite.mutableCopy(this.values_);
            }
        }

        /* access modifiers changed from: private */
        public void setValues(int index, int value) {
            ensureValuesIsMutable();
            this.values_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addValues(int value) {
            ensureValuesIsMutable();
            this.values_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllValues(Iterable<? extends Integer> values) {
            ensureValuesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.values_);
        }

        /* access modifiers changed from: private */
        public void clearValues() {
            this.values_ = emptyIntList();
        }

        public static ClassInstance parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClassInstance parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClassInstance parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClassInstance parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClassInstance parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ClassInstance parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ClassInstance parseFrom(InputStream input) throws IOException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ClassInstance parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ClassInstance parseDelimitedFrom(InputStream input) throws IOException {
            return (ClassInstance) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ClassInstance parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClassInstance) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ClassInstance parseFrom(CodedInputStream input) throws IOException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ClassInstance parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ClassInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ClassInstance prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ClassInstance, Builder> implements ClassInstanceOrBuilder {
            private Builder() {
                super(ClassInstance.DEFAULT_INSTANCE);
            }

            public boolean hasClazz() {
                return ((ClassInstance) this.instance).hasClazz();
            }

            public int getClazz() {
                return ((ClassInstance) this.instance).getClazz();
            }

            public Builder setClazz(int value) {
                copyOnWrite();
                ((ClassInstance) this.instance).setClazz(value);
                return this;
            }

            public Builder clearClazz() {
                copyOnWrite();
                ((ClassInstance) this.instance).clearClazz();
                return this;
            }

            public List<Integer> getValuesList() {
                return Collections.unmodifiableList(((ClassInstance) this.instance).getValuesList());
            }

            public int getValuesCount() {
                return ((ClassInstance) this.instance).getValuesCount();
            }

            public int getValues(int index) {
                return ((ClassInstance) this.instance).getValues(index);
            }

            public Builder setValues(int index, int value) {
                copyOnWrite();
                ((ClassInstance) this.instance).setValues(index, value);
                return this;
            }

            public Builder addValues(int value) {
                copyOnWrite();
                ((ClassInstance) this.instance).addValues(value);
                return this;
            }

            public Builder addAllValues(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((ClassInstance) this.instance).addAllValues(values);
                return this;
            }

            public Builder clearValues() {
                copyOnWrite();
                ((ClassInstance) this.instance).clearValues();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ClassInstance();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u0004\u0000\u0002'", new Object[]{"bitField0_", "clazz_", "values_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ClassInstance> parser = PARSER;
                    if (parser == null) {
                        synchronized (ClassInstance.class) {
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
            GeneratedMessageLite.registerDefaultInstance(ClassInstance.class, DEFAULT_INSTANCE);
        }

        public static ClassInstance getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ClassInstance> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class ArrayInstance extends GeneratedMessageLite<ArrayInstance, Builder> implements ArrayInstanceOrBuilder {
        public static final int CLAZZ_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final ArrayInstance DEFAULT_INSTANCE = new ArrayInstance();
        private static volatile Parser<ArrayInstance> PARSER = null;
        public static final int VALUES_FIELD_NUMBER = 2;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int clazz_;
        private int valuesMemoizedSerializedSize = -1;
        @ProtoField(fieldNumber = 2, type = FieldType.INT32_LIST_PACKED)
        private Internal.IntList values_ = emptyIntList();

        private ArrayInstance() {
        }

        public boolean hasClazz() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getClazz() {
            return this.clazz_;
        }

        /* access modifiers changed from: private */
        public void setClazz(int value) {
            this.bitField0_ |= 1;
            this.clazz_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClazz() {
            this.bitField0_ &= -2;
            this.clazz_ = 0;
        }

        public List<Integer> getValuesList() {
            return this.values_;
        }

        public int getValuesCount() {
            return this.values_.size();
        }

        public int getValues(int index) {
            return this.values_.getInt(index);
        }

        private void ensureValuesIsMutable() {
            if (!this.values_.isModifiable()) {
                this.values_ = GeneratedMessageLite.mutableCopy(this.values_);
            }
        }

        /* access modifiers changed from: private */
        public void setValues(int index, int value) {
            ensureValuesIsMutable();
            this.values_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addValues(int value) {
            ensureValuesIsMutable();
            this.values_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllValues(Iterable<? extends Integer> values) {
            ensureValuesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.values_);
        }

        /* access modifiers changed from: private */
        public void clearValues() {
            this.values_ = emptyIntList();
        }

        public static ArrayInstance parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ArrayInstance parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ArrayInstance parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ArrayInstance parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ArrayInstance parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ArrayInstance parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ArrayInstance parseFrom(InputStream input) throws IOException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ArrayInstance parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ArrayInstance parseDelimitedFrom(InputStream input) throws IOException {
            return (ArrayInstance) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ArrayInstance parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ArrayInstance) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ArrayInstance parseFrom(CodedInputStream input) throws IOException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ArrayInstance parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ArrayInstance prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ArrayInstance, Builder> implements ArrayInstanceOrBuilder {
            private Builder() {
                super(ArrayInstance.DEFAULT_INSTANCE);
            }

            public boolean hasClazz() {
                return ((ArrayInstance) this.instance).hasClazz();
            }

            public int getClazz() {
                return ((ArrayInstance) this.instance).getClazz();
            }

            public Builder setClazz(int value) {
                copyOnWrite();
                ((ArrayInstance) this.instance).setClazz(value);
                return this;
            }

            public Builder clearClazz() {
                copyOnWrite();
                ((ArrayInstance) this.instance).clearClazz();
                return this;
            }

            public List<Integer> getValuesList() {
                return Collections.unmodifiableList(((ArrayInstance) this.instance).getValuesList());
            }

            public int getValuesCount() {
                return ((ArrayInstance) this.instance).getValuesCount();
            }

            public int getValues(int index) {
                return ((ArrayInstance) this.instance).getValues(index);
            }

            public Builder setValues(int index, int value) {
                copyOnWrite();
                ((ArrayInstance) this.instance).setValues(index, value);
                return this;
            }

            public Builder addValues(int value) {
                copyOnWrite();
                ((ArrayInstance) this.instance).addValues(value);
                return this;
            }

            public Builder addAllValues(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((ArrayInstance) this.instance).addAllValues(values);
                return this;
            }

            public Builder clearValues() {
                copyOnWrite();
                ((ArrayInstance) this.instance).clearValues();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new ArrayInstance();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\u0004\u0000\u0002'", new Object[]{"bitField0_", "clazz_", "values_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<ArrayInstance> parser = PARSER;
                    if (parser == null) {
                        synchronized (ArrayInstance.class) {
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
            GeneratedMessageLite.registerDefaultInstance(ArrayInstance.class, DEFAULT_INSTANCE);
        }

        public static ArrayInstance getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ArrayInstance> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class PrimitiveArrayInstance extends GeneratedMessageLite<PrimitiveArrayInstance, Builder> implements PrimitiveArrayInstanceOrBuilder {
        /* access modifiers changed from: private */
        public static final PrimitiveArrayInstance DEFAULT_INSTANCE = new PrimitiveArrayInstance();
        public static final int NUM_ELEMENTS_FIELD_NUMBER = 2;
        private static volatile Parser<PrimitiveArrayInstance> PARSER = null;
        public static final int TYPE_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int numElements_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int type_ = 4;

        private PrimitiveArrayInstance() {
        }

        public boolean hasType() {
            return (this.bitField0_ & 1) != 0;
        }

        public PrimitiveType getType() {
            PrimitiveType result = PrimitiveType.forNumber(this.type_);
            return result == null ? PrimitiveType.BOOLEAN : result;
        }

        /* access modifiers changed from: private */
        public void setType(PrimitiveType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.type_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearType() {
            this.bitField0_ &= -2;
            this.type_ = 4;
        }

        public boolean hasNumElements() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getNumElements() {
            return this.numElements_;
        }

        /* access modifiers changed from: private */
        public void setNumElements(int value) {
            this.bitField0_ |= 2;
            this.numElements_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumElements() {
            this.bitField0_ &= -3;
            this.numElements_ = 0;
        }

        public static PrimitiveArrayInstance parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimitiveArrayInstance parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimitiveArrayInstance parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimitiveArrayInstance parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimitiveArrayInstance parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static PrimitiveArrayInstance parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static PrimitiveArrayInstance parseFrom(InputStream input) throws IOException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimitiveArrayInstance parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimitiveArrayInstance parseDelimitedFrom(InputStream input) throws IOException {
            return (PrimitiveArrayInstance) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimitiveArrayInstance parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimitiveArrayInstance) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static PrimitiveArrayInstance parseFrom(CodedInputStream input) throws IOException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static PrimitiveArrayInstance parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (PrimitiveArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(PrimitiveArrayInstance prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<PrimitiveArrayInstance, Builder> implements PrimitiveArrayInstanceOrBuilder {
            private Builder() {
                super(PrimitiveArrayInstance.DEFAULT_INSTANCE);
            }

            public boolean hasType() {
                return ((PrimitiveArrayInstance) this.instance).hasType();
            }

            public PrimitiveType getType() {
                return ((PrimitiveArrayInstance) this.instance).getType();
            }

            public Builder setType(PrimitiveType value) {
                copyOnWrite();
                ((PrimitiveArrayInstance) this.instance).setType(value);
                return this;
            }

            public Builder clearType() {
                copyOnWrite();
                ((PrimitiveArrayInstance) this.instance).clearType();
                return this;
            }

            public boolean hasNumElements() {
                return ((PrimitiveArrayInstance) this.instance).hasNumElements();
            }

            public int getNumElements() {
                return ((PrimitiveArrayInstance) this.instance).getNumElements();
            }

            public Builder setNumElements(int value) {
                copyOnWrite();
                ((PrimitiveArrayInstance) this.instance).setNumElements(value);
                return this;
            }

            public Builder clearNumElements() {
                copyOnWrite();
                ((PrimitiveArrayInstance) this.instance).clearNumElements();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new PrimitiveArrayInstance();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0000\u0002\u0004\u0001", new Object[]{"bitField0_", "type_", PrimitiveType.internalGetVerifier(), "numElements_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<PrimitiveArrayInstance> parser = PARSER;
                    if (parser == null) {
                        synchronized (PrimitiveArrayInstance.class) {
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
            GeneratedMessageLite.registerDefaultInstance(PrimitiveArrayInstance.class, DEFAULT_INSTANCE);
        }

        public static PrimitiveArrayInstance getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<PrimitiveArrayInstance> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class CollapsedArrayInstance extends GeneratedMessageLite<CollapsedArrayInstance, Builder> implements CollapsedArrayInstanceOrBuilder {
        public static final int CLAZZ_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final CollapsedArrayInstance DEFAULT_INSTANCE = new CollapsedArrayInstance();
        public static final int ELEMENT_CLASS_FIELD_NUMBER = 2;
        public static final int LENGTH_FIELD_NUMBER = 5;
        public static final int NUM_ELEMENTS_FIELD_NUMBER = 4;
        private static volatile Parser<CollapsedArrayInstance> PARSER = null;
        public static final int PRIMITIVE_ARRAY_TYPE_FIELD_NUMBER = 3;
        public static final int RETAINED_BYTES_FIELD_NUMBER = 6;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int clazz_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int elementClass_;
        @ProtoField(fieldNumber = 5, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 16, presenceBitsId = 0)
        private int length_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private int numElements_;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private int primitiveArrayType_ = 4;
        @ProtoField(fieldNumber = 6, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 32, presenceBitsId = 0)
        private int retainedBytes_;

        private CollapsedArrayInstance() {
        }

        public boolean hasClazz() {
            return (this.bitField0_ & 1) != 0;
        }

        public int getClazz() {
            return this.clazz_;
        }

        /* access modifiers changed from: private */
        public void setClazz(int value) {
            this.bitField0_ |= 1;
            this.clazz_ = value;
        }

        /* access modifiers changed from: private */
        public void clearClazz() {
            this.bitField0_ &= -2;
            this.clazz_ = 0;
        }

        public boolean hasElementClass() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getElementClass() {
            return this.elementClass_;
        }

        /* access modifiers changed from: private */
        public void setElementClass(int value) {
            this.bitField0_ |= 2;
            this.elementClass_ = value;
        }

        /* access modifiers changed from: private */
        public void clearElementClass() {
            this.bitField0_ &= -3;
            this.elementClass_ = 0;
        }

        public boolean hasPrimitiveArrayType() {
            return (this.bitField0_ & 4) != 0;
        }

        public PrimitiveType getPrimitiveArrayType() {
            PrimitiveType result = PrimitiveType.forNumber(this.primitiveArrayType_);
            return result == null ? PrimitiveType.BOOLEAN : result;
        }

        /* access modifiers changed from: private */
        public void setPrimitiveArrayType(PrimitiveType value) {
            if (value != null) {
                this.bitField0_ |= 4;
                this.primitiveArrayType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearPrimitiveArrayType() {
            this.bitField0_ &= -5;
            this.primitiveArrayType_ = 4;
        }

        public boolean hasNumElements() {
            return (this.bitField0_ & 8) != 0;
        }

        public int getNumElements() {
            return this.numElements_;
        }

        /* access modifiers changed from: private */
        public void setNumElements(int value) {
            this.bitField0_ |= 8;
            this.numElements_ = value;
        }

        /* access modifiers changed from: private */
        public void clearNumElements() {
            this.bitField0_ &= -9;
            this.numElements_ = 0;
        }

        public boolean hasLength() {
            return (this.bitField0_ & 16) != 0;
        }

        public int getLength() {
            return this.length_;
        }

        /* access modifiers changed from: private */
        public void setLength(int value) {
            this.bitField0_ |= 16;
            this.length_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLength() {
            this.bitField0_ &= -17;
            this.length_ = 0;
        }

        public boolean hasRetainedBytes() {
            return (this.bitField0_ & 32) != 0;
        }

        public int getRetainedBytes() {
            return this.retainedBytes_;
        }

        /* access modifiers changed from: private */
        public void setRetainedBytes(int value) {
            this.bitField0_ |= 32;
            this.retainedBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearRetainedBytes() {
            this.bitField0_ &= -33;
            this.retainedBytes_ = 0;
        }

        public static CollapsedArrayInstance parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CollapsedArrayInstance parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CollapsedArrayInstance parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CollapsedArrayInstance parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CollapsedArrayInstance parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CollapsedArrayInstance parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CollapsedArrayInstance parseFrom(InputStream input) throws IOException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CollapsedArrayInstance parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CollapsedArrayInstance parseDelimitedFrom(InputStream input) throws IOException {
            return (CollapsedArrayInstance) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CollapsedArrayInstance parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CollapsedArrayInstance) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CollapsedArrayInstance parseFrom(CodedInputStream input) throws IOException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CollapsedArrayInstance parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CollapsedArrayInstance) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CollapsedArrayInstance prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<CollapsedArrayInstance, Builder> implements CollapsedArrayInstanceOrBuilder {
            private Builder() {
                super(CollapsedArrayInstance.DEFAULT_INSTANCE);
            }

            public boolean hasClazz() {
                return ((CollapsedArrayInstance) this.instance).hasClazz();
            }

            public int getClazz() {
                return ((CollapsedArrayInstance) this.instance).getClazz();
            }

            public Builder setClazz(int value) {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).setClazz(value);
                return this;
            }

            public Builder clearClazz() {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).clearClazz();
                return this;
            }

            public boolean hasElementClass() {
                return ((CollapsedArrayInstance) this.instance).hasElementClass();
            }

            public int getElementClass() {
                return ((CollapsedArrayInstance) this.instance).getElementClass();
            }

            public Builder setElementClass(int value) {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).setElementClass(value);
                return this;
            }

            public Builder clearElementClass() {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).clearElementClass();
                return this;
            }

            public boolean hasPrimitiveArrayType() {
                return ((CollapsedArrayInstance) this.instance).hasPrimitiveArrayType();
            }

            public PrimitiveType getPrimitiveArrayType() {
                return ((CollapsedArrayInstance) this.instance).getPrimitiveArrayType();
            }

            public Builder setPrimitiveArrayType(PrimitiveType value) {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).setPrimitiveArrayType(value);
                return this;
            }

            public Builder clearPrimitiveArrayType() {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).clearPrimitiveArrayType();
                return this;
            }

            public boolean hasNumElements() {
                return ((CollapsedArrayInstance) this.instance).hasNumElements();
            }

            public int getNumElements() {
                return ((CollapsedArrayInstance) this.instance).getNumElements();
            }

            public Builder setNumElements(int value) {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).setNumElements(value);
                return this;
            }

            public Builder clearNumElements() {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).clearNumElements();
                return this;
            }

            public boolean hasLength() {
                return ((CollapsedArrayInstance) this.instance).hasLength();
            }

            public int getLength() {
                return ((CollapsedArrayInstance) this.instance).getLength();
            }

            public Builder setLength(int value) {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).setLength(value);
                return this;
            }

            public Builder clearLength() {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).clearLength();
                return this;
            }

            public boolean hasRetainedBytes() {
                return ((CollapsedArrayInstance) this.instance).hasRetainedBytes();
            }

            public int getRetainedBytes() {
                return ((CollapsedArrayInstance) this.instance).getRetainedBytes();
            }

            public Builder setRetainedBytes(int value) {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).setRetainedBytes(value);
                return this;
            }

            public Builder clearRetainedBytes() {
                copyOnWrite();
                ((CollapsedArrayInstance) this.instance).clearRetainedBytes();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new CollapsedArrayInstance();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0006\u0000\u0001\u0001\u0006\u0006\u0000\u0000\u0000\u0001\u0004\u0000\u0002\u0004\u0001\u0003\f\u0002\u0004\u0004\u0003\u0005\u0004\u0004\u0006\u0004\u0005", new Object[]{"bitField0_", "clazz_", "elementClass_", "primitiveArrayType_", PrimitiveType.internalGetVerifier(), "numElements_", "length_", "retainedBytes_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<CollapsedArrayInstance> parser = PARSER;
                    if (parser == null) {
                        synchronized (CollapsedArrayInstance.class) {
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
            GeneratedMessageLite.registerDefaultInstance(CollapsedArrayInstance.class, DEFAULT_INSTANCE);
        }

        public static CollapsedArrayInstance getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CollapsedArrayInstance> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class Root extends GeneratedMessageLite<Root, Builder> implements RootOrBuilder {
        /* access modifiers changed from: private */
        public static final Root DEFAULT_INSTANCE = new Root();
        public static final int NODES_FIELD_NUMBER = 2;
        private static volatile Parser<Root> PARSER = null;
        public static final int TAG_FIELD_NUMBER = 1;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        private int nodesMemoizedSerializedSize = -1;
        @ProtoField(fieldNumber = 2, type = FieldType.INT32_LIST_PACKED)
        private Internal.IntList nodes_ = emptyIntList();
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int tag_;

        private Root() {
        }

        public boolean hasTag() {
            return (this.bitField0_ & 1) != 0;
        }

        public RootTag getTag() {
            RootTag result = RootTag.forNumber(this.tag_);
            return result == null ? RootTag.ROOT_TAG_DEFAULT : result;
        }

        /* access modifiers changed from: private */
        public void setTag(RootTag value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.tag_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTag() {
            this.bitField0_ &= -2;
            this.tag_ = 0;
        }

        public List<Integer> getNodesList() {
            return this.nodes_;
        }

        public int getNodesCount() {
            return this.nodes_.size();
        }

        public int getNodes(int index) {
            return this.nodes_.getInt(index);
        }

        private void ensureNodesIsMutable() {
            if (!this.nodes_.isModifiable()) {
                this.nodes_ = GeneratedMessageLite.mutableCopy(this.nodes_);
            }
        }

        /* access modifiers changed from: private */
        public void setNodes(int index, int value) {
            ensureNodesIsMutable();
            this.nodes_.setInt(index, value);
        }

        /* access modifiers changed from: private */
        public void addNodes(int value) {
            ensureNodesIsMutable();
            this.nodes_.addInt(value);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void
         arg types: [java.lang.Iterable<? extends java.lang.Integer>, com.google.protobuf.Internal$IntList]
         candidates:
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.Collection):void
          com.google.protobuf.AbstractMessageLite.addAll(java.lang.Iterable, java.util.List):void */
        /* access modifiers changed from: private */
        public void addAllNodes(Iterable<? extends Integer> values) {
            ensureNodesIsMutable();
            AbstractMessageLite.addAll((Iterable) values, (List) this.nodes_);
        }

        /* access modifiers changed from: private */
        public void clearNodes() {
            this.nodes_ = emptyIntList();
        }

        public static Root parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Root parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Root parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Root parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Root parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Root parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Root parseFrom(InputStream input) throws IOException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Root parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Root parseDelimitedFrom(InputStream input) throws IOException {
            return (Root) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Root parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Root) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Root parseFrom(CodedInputStream input) throws IOException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Root parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Root) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Root prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Root, Builder> implements RootOrBuilder {
            private Builder() {
                super(Root.DEFAULT_INSTANCE);
            }

            public boolean hasTag() {
                return ((Root) this.instance).hasTag();
            }

            public RootTag getTag() {
                return ((Root) this.instance).getTag();
            }

            public Builder setTag(RootTag value) {
                copyOnWrite();
                ((Root) this.instance).setTag(value);
                return this;
            }

            public Builder clearTag() {
                copyOnWrite();
                ((Root) this.instance).clearTag();
                return this;
            }

            public List<Integer> getNodesList() {
                return Collections.unmodifiableList(((Root) this.instance).getNodesList());
            }

            public int getNodesCount() {
                return ((Root) this.instance).getNodesCount();
            }

            public int getNodes(int index) {
                return ((Root) this.instance).getNodes(index);
            }

            public Builder setNodes(int index, int value) {
                copyOnWrite();
                ((Root) this.instance).setNodes(index, value);
                return this;
            }

            public Builder addNodes(int value) {
                copyOnWrite();
                ((Root) this.instance).addNodes(value);
                return this;
            }

            public Builder addAllNodes(Iterable<? extends Integer> values) {
                copyOnWrite();
                ((Root) this.instance).addAllNodes(values);
                return this;
            }

            public Builder clearNodes() {
                copyOnWrite();
                ((Root) this.instance).clearNodes();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new Root();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0002\u0000\u0001\u0001\u0002\u0002\u0000\u0001\u0000\u0001\f\u0000\u0002'", new Object[]{"bitField0_", "tag_", RootTag.internalGetVerifier(), "nodes_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<Root> parser = PARSER;
                    if (parser == null) {
                        synchronized (Root.class) {
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
            GeneratedMessageLite.registerDefaultInstance(Root.class, DEFAULT_INSTANCE);
        }

        public static Root getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Root> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    @ProtoMessage(checkInitialized = {}, messageSetWireFormat = false, protoSyntax = ProtoSyntax.PROTO2)
    public static final class HeapDumpContext extends GeneratedMessageLite<HeapDumpContext, Builder> implements HeapDumpContextOrBuilder {
        public static final int ALLOCATED_BYTES_FIELD_NUMBER = 3;
        /* access modifiers changed from: private */
        public static final HeapDumpContext DEFAULT_INSTANCE = new HeapDumpContext();
        public static final int GARBAGE_COLLECTED_BYTES_FIELD_NUMBER = 4;
        private static volatile Parser<HeapDumpContext> PARSER = null;
        public static final int TOTAL_PSS_KB_FIELD_NUMBER = 2;
        public static final int TRIGGER_TYPE_FIELD_NUMBER = 1;
        @ProtoField(fieldNumber = 3, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 4, presenceBitsId = 0)
        private long allocatedBytes_;
        @ProtoPresenceBits(mo28548id = 0)
        private int bitField0_;
        @ProtoField(fieldNumber = 4, isRequired = false, type = FieldType.INT64)
        @ProtoPresenceCheckedField(mask = 8, presenceBitsId = 0)
        private long garbageCollectedBytes_;
        @ProtoField(fieldNumber = 2, isRequired = false, type = FieldType.INT32)
        @ProtoPresenceCheckedField(mask = 2, presenceBitsId = 0)
        private int totalPssKb_;
        @ProtoField(fieldNumber = 1, isRequired = false, type = FieldType.ENUM)
        @ProtoPresenceCheckedField(mask = 1, presenceBitsId = 0)
        private int triggerType_;

        private HeapDumpContext() {
        }

        public enum TriggerType implements Internal.EnumLite {
            UNKNOWN(0),
            BACKGROUND_MEMORY_SAMPLE_THRESHOLD(1),
            OUT_OF_MEMORY_ERROR(2);
            
            public static final int BACKGROUND_MEMORY_SAMPLE_THRESHOLD_VALUE = 1;
            public static final int OUT_OF_MEMORY_ERROR_VALUE = 2;
            public static final int UNKNOWN_VALUE = 0;
            private static final Internal.EnumLiteMap<TriggerType> internalValueMap = new Internal.EnumLiteMap<TriggerType>() {
                public TriggerType findValueByNumber(int number) {
                    return TriggerType.forNumber(number);
                }
            };
            private final int value;

            public final int getNumber() {
                return this.value;
            }

            public static TriggerType forNumber(int value2) {
                if (value2 == 0) {
                    return UNKNOWN;
                }
                if (value2 == 1) {
                    return BACKGROUND_MEMORY_SAMPLE_THRESHOLD;
                }
                if (value2 != 2) {
                    return null;
                }
                return OUT_OF_MEMORY_ERROR;
            }

            public static Internal.EnumLiteMap<TriggerType> internalGetValueMap() {
                return internalValueMap;
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return TriggerTypeVerifier.INSTANCE;
            }

            private static final class TriggerTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new TriggerTypeVerifier();

                private TriggerTypeVerifier() {
                }

                public boolean isInRange(int number) {
                    return TriggerType.forNumber(number) != null;
                }
            }

            private TriggerType(int value2) {
                this.value = value2;
            }
        }

        public boolean hasTriggerType() {
            return (this.bitField0_ & 1) != 0;
        }

        public TriggerType getTriggerType() {
            TriggerType result = TriggerType.forNumber(this.triggerType_);
            return result == null ? TriggerType.UNKNOWN : result;
        }

        /* access modifiers changed from: private */
        public void setTriggerType(TriggerType value) {
            if (value != null) {
                this.bitField0_ |= 1;
                this.triggerType_ = value.getNumber();
                return;
            }
            throw new NullPointerException();
        }

        /* access modifiers changed from: private */
        public void clearTriggerType() {
            this.bitField0_ &= -2;
            this.triggerType_ = 0;
        }

        public boolean hasTotalPssKb() {
            return (this.bitField0_ & 2) != 0;
        }

        public int getTotalPssKb() {
            return this.totalPssKb_;
        }

        /* access modifiers changed from: private */
        public void setTotalPssKb(int value) {
            this.bitField0_ |= 2;
            this.totalPssKb_ = value;
        }

        /* access modifiers changed from: private */
        public void clearTotalPssKb() {
            this.bitField0_ &= -3;
            this.totalPssKb_ = 0;
        }

        public boolean hasAllocatedBytes() {
            return (this.bitField0_ & 4) != 0;
        }

        public long getAllocatedBytes() {
            return this.allocatedBytes_;
        }

        /* access modifiers changed from: private */
        public void setAllocatedBytes(long value) {
            this.bitField0_ |= 4;
            this.allocatedBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearAllocatedBytes() {
            this.bitField0_ &= -5;
            this.allocatedBytes_ = 0;
        }

        public boolean hasGarbageCollectedBytes() {
            return (this.bitField0_ & 8) != 0;
        }

        public long getGarbageCollectedBytes() {
            return this.garbageCollectedBytes_;
        }

        /* access modifiers changed from: private */
        public void setGarbageCollectedBytes(long value) {
            this.bitField0_ |= 8;
            this.garbageCollectedBytes_ = value;
        }

        /* access modifiers changed from: private */
        public void clearGarbageCollectedBytes() {
            this.bitField0_ &= -9;
            this.garbageCollectedBytes_ = 0;
        }

        public static HeapDumpContext parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HeapDumpContext parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HeapDumpContext parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HeapDumpContext parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HeapDumpContext parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static HeapDumpContext parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static HeapDumpContext parseFrom(InputStream input) throws IOException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static HeapDumpContext parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static HeapDumpContext parseDelimitedFrom(InputStream input) throws IOException {
            return (HeapDumpContext) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static HeapDumpContext parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HeapDumpContext) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static HeapDumpContext parseFrom(CodedInputStream input) throws IOException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static HeapDumpContext parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (HeapDumpContext) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(HeapDumpContext prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<HeapDumpContext, Builder> implements HeapDumpContextOrBuilder {
            private Builder() {
                super(HeapDumpContext.DEFAULT_INSTANCE);
            }

            public boolean hasTriggerType() {
                return ((HeapDumpContext) this.instance).hasTriggerType();
            }

            public TriggerType getTriggerType() {
                return ((HeapDumpContext) this.instance).getTriggerType();
            }

            public Builder setTriggerType(TriggerType value) {
                copyOnWrite();
                ((HeapDumpContext) this.instance).setTriggerType(value);
                return this;
            }

            public Builder clearTriggerType() {
                copyOnWrite();
                ((HeapDumpContext) this.instance).clearTriggerType();
                return this;
            }

            public boolean hasTotalPssKb() {
                return ((HeapDumpContext) this.instance).hasTotalPssKb();
            }

            public int getTotalPssKb() {
                return ((HeapDumpContext) this.instance).getTotalPssKb();
            }

            public Builder setTotalPssKb(int value) {
                copyOnWrite();
                ((HeapDumpContext) this.instance).setTotalPssKb(value);
                return this;
            }

            public Builder clearTotalPssKb() {
                copyOnWrite();
                ((HeapDumpContext) this.instance).clearTotalPssKb();
                return this;
            }

            public boolean hasAllocatedBytes() {
                return ((HeapDumpContext) this.instance).hasAllocatedBytes();
            }

            public long getAllocatedBytes() {
                return ((HeapDumpContext) this.instance).getAllocatedBytes();
            }

            public Builder setAllocatedBytes(long value) {
                copyOnWrite();
                ((HeapDumpContext) this.instance).setAllocatedBytes(value);
                return this;
            }

            public Builder clearAllocatedBytes() {
                copyOnWrite();
                ((HeapDumpContext) this.instance).clearAllocatedBytes();
                return this;
            }

            public boolean hasGarbageCollectedBytes() {
                return ((HeapDumpContext) this.instance).hasGarbageCollectedBytes();
            }

            public long getGarbageCollectedBytes() {
                return ((HeapDumpContext) this.instance).getGarbageCollectedBytes();
            }

            public Builder setGarbageCollectedBytes(long value) {
                copyOnWrite();
                ((HeapDumpContext) this.instance).setGarbageCollectedBytes(value);
                return this;
            }

            public Builder clearGarbageCollectedBytes() {
                copyOnWrite();
                ((HeapDumpContext) this.instance).clearGarbageCollectedBytes();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (method) {
                case NEW_MUTABLE_INSTANCE:
                    return new HeapDumpContext();
                case NEW_BUILDER:
                    return new Builder();
                case BUILD_MESSAGE_INFO:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0001\u0004\u0000\u0001\u0001\u0004\u0004\u0000\u0000\u0000\u0001\f\u0000\u0002\u0004\u0001\u0003\u0002\u0002\u0004\u0002\u0003", new Object[]{"bitField0_", "triggerType_", TriggerType.internalGetVerifier(), "totalPssKb_", "allocatedBytes_", "garbageCollectedBytes_"});
                case GET_DEFAULT_INSTANCE:
                    return DEFAULT_INSTANCE;
                case GET_PARSER:
                    Parser<HeapDumpContext> parser = PARSER;
                    if (parser == null) {
                        synchronized (HeapDumpContext.class) {
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
            GeneratedMessageLite.registerDefaultInstance(HeapDumpContext.class, DEFAULT_INSTANCE);
        }

        public static HeapDumpContext getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<HeapDumpContext> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
