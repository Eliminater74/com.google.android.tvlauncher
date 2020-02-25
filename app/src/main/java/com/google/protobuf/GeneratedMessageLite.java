package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite.Builder;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class GeneratedMessageLite<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite<MessageType, BuilderType> {
    private static Map<Object, GeneratedMessageLite<?, ?>> defaultInstanceMap = new ConcurrentHashMap();
    protected int memoizedSerializedSize = -1;
    protected UnknownFieldSetLite unknownFields = UnknownFieldSetLite.getDefaultInstance();

    static <T extends GeneratedMessageLite<?, ?>> T getDefaultInstance(Class<T> clazz) {
        T result = (GeneratedMessageLite) defaultInstanceMap.get(clazz);
        if (result == null) {
            try {
                Class.forName(clazz.getName(), true, clazz.getClassLoader());
                result = (GeneratedMessageLite) defaultInstanceMap.get(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (result == null) {
            result = ((GeneratedMessageLite) UnsafeUtil.allocateInstance(clazz)).getDefaultInstanceForType();
            if (result != null) {
                defaultInstanceMap.put(clazz, result);
            } else {
                throw new IllegalStateException();
            }
        }
        return result;
    }

    protected static <T extends GeneratedMessageLite<?, ?>> void registerDefaultInstance(Class<T> clazz, T defaultInstance) {
        defaultInstanceMap.put(clazz, defaultInstance);
    }

    protected static Object newMessageInfo(MessageLite defaultInstance, String info, Object[] objects) {
        return new RawMessageInfo(defaultInstance, info, objects);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newSingularGeneratedExtension(ContainingType containingTypeDefaultInstance, Type defaultValue, MessageLite messageDefaultInstance, Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type, Class singularType) {
        return new GeneratedExtension(containingTypeDefaultInstance, defaultValue, messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, false, false), singularType);
    }

    public static <ContainingType extends MessageLite, Type> GeneratedExtension<ContainingType, Type> newRepeatedGeneratedExtension(ContainingType containingTypeDefaultInstance, MessageLite messageDefaultInstance, Internal.EnumLiteMap<?> enumTypeMap, int number, WireFormat.FieldType type, boolean isPacked, Class singularType) {
        return new GeneratedExtension(containingTypeDefaultInstance, Collections.emptyList(), messageDefaultInstance, new ExtensionDescriptor(enumTypeMap, number, type, true, isPacked), singularType);
    }

    static Method getMethodOrDie(Class clazz, String name, Class... params) {
        try {
            return clazz.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            String name2 = clazz.getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name2).length() + 45 + String.valueOf(name).length());
            sb.append("Generated message class \"");
            sb.append(name2);
            sb.append("\" missing method \"");
            sb.append(name);
            sb.append("\".");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    static Object invokeOrDie(Method method, Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    protected static Object[] newFieldInfoArray(int size) {
        return new FieldInfo[size];
    }

    protected static MessageInfo newMessageInfo(ProtoSyntax syntax, int[] checkInitialized, Object[] fieldInfos, Object defaultInstance) {
        return new StructuralMessageInfo(syntax, false, checkInitialized, (FieldInfo[]) fieldInfos, defaultInstance);
    }

    protected static MessageInfo newMessageInfoForMessageSet(ProtoSyntax syntax, int[] checkInitialized, Object[] fieldInfos, Object defaultInstance) {
        return new StructuralMessageInfo(syntax, true, checkInitialized, (FieldInfo[]) fieldInfos, defaultInstance);
    }

    protected static OneofInfo newOneofInfo(int id, Field caseField, Field valueField) {
        if (caseField == null || valueField == null) {
            return null;
        }
        return new OneofInfo(id, caseField, valueField);
    }

    protected static FieldInfo fieldInfo(Field field, int fieldNumber, FieldType fieldType) {
        return fieldInfo(field, fieldNumber, fieldType, false);
    }

    protected static FieldInfo fieldInfo(Field field, int fieldNumber, FieldType fieldType, boolean enforceUtf8) {
        if (field == null) {
            return null;
        }
        return FieldInfo.forField(field, fieldNumber, fieldType, enforceUtf8);
    }

    protected static FieldInfo fieldInfoForRepeatedMessage(Field field, int fieldNumber, FieldType fieldType, Class<?> messageClass) {
        if (field == null) {
            return null;
        }
        return FieldInfo.forRepeatedMessageField(field, fieldNumber, fieldType, messageClass);
    }

    protected static FieldInfo fieldInfoWithEnumVerifier(Field field, int fieldNumber, FieldType fieldType, Internal.EnumVerifier enumVerifier) {
        if (field == null) {
            return null;
        }
        return FieldInfo.forFieldWithEnumVerifier(field, fieldNumber, fieldType, enumVerifier);
    }

    protected static FieldInfo fieldInfoForProto2Optional(Field field, long combinedFieldNumberAndPresenceMask, FieldType fieldType, Field presenceField) {
        return fieldInfoForProto2Optional(field, (int) ((combinedFieldNumberAndPresenceMask >>> 32) & -1), fieldType, presenceField, (int) (combinedFieldNumberAndPresenceMask & -1), false, null);
    }

    public static FieldInfo fieldInfoForProto2Optional(Field field, int fieldNumber, FieldType fieldType, Field presenceField, int presenceMask, boolean enforceUtf8, Internal.EnumVerifier enumVerifier) {
        if (field == null || presenceField == null) {
            return null;
        }
        return FieldInfo.forProto2OptionalField(field, fieldNumber, fieldType, presenceField, presenceMask, enforceUtf8, enumVerifier);
    }

    protected static FieldInfo fieldInfoForProto2Required(Field field, long combinedFieldNumberAndPresenceMask, FieldType fieldType, Field presenceField) {
        return fieldInfoForProto2Required(field, (int) ((combinedFieldNumberAndPresenceMask >>> 32) & -1), fieldType, presenceField, (int) (combinedFieldNumberAndPresenceMask & -1), false, null);
    }

    public static FieldInfo fieldInfoForProto2Required(Field field, int fieldNumber, FieldType fieldType, Field presenceField, int presenceMask, boolean enforceUtf8, Internal.EnumVerifier enumVerifier) {
        if (field == null || presenceField == null) {
            return null;
        }
        return FieldInfo.forProto2RequiredField(field, fieldNumber, fieldType, presenceField, presenceMask, enforceUtf8, enumVerifier);
    }

    protected static FieldInfo fieldInfoForOneofPrimitive(int fieldNumber, FieldType fieldType, Object oneof, Class<?> oneofStoredType) {
        if (oneof == null) {
            return null;
        }
        return FieldInfo.forOneofMemberField(fieldNumber, fieldType, (OneofInfo) oneof, oneofStoredType, false, null);
    }

    protected static FieldInfo fieldInfoForOneofString(int fieldNumber, Object oneof, boolean enforceUtf8) {
        if (oneof == null) {
            return null;
        }
        return FieldInfo.forOneofMemberField(fieldNumber, FieldType.STRING, (OneofInfo) oneof, String.class, enforceUtf8, null);
    }

    protected static FieldInfo fieldInfoForOneofEnum(int fieldNumber, Object oneof, Class<?> oneofStoredType, Internal.EnumVerifier enumVerifier) {
        if (oneof == null) {
            return null;
        }
        return FieldInfo.forOneofMemberField(fieldNumber, FieldType.ENUM, (OneofInfo) oneof, oneofStoredType, false, enumVerifier);
    }

    protected static FieldInfo fieldInfoForOneofMessage(int fieldNumber, FieldType fieldType, Object oneof, Class<?> oneofStoredType) {
        if (oneof == null) {
            return null;
        }
        return FieldInfo.forOneofMemberField(fieldNumber, fieldType, (OneofInfo) oneof, oneofStoredType, false, null);
    }

    protected static FieldInfo fieldInfoForMap(Field field, int fieldNumber, Object mapDefaultEntry, Internal.EnumVerifier enumVerifier) {
        if (field == null) {
            return null;
        }
        return FieldInfo.forMapField(field, fieldNumber, mapDefaultEntry, enumVerifier);
    }

    protected static Field reflectField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    public static <MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>, T> GeneratedExtension<MessageType, T> checkIsLite(ExtensionLite<MessageType, T> extension) {
        if (extension.isLite()) {
            return (GeneratedExtension) extension;
        }
        throw new IllegalArgumentException("Expected a lite extension.");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected static final <T extends com.google.protobuf.GeneratedMessageLite<T, ?>> boolean isInitialized(T r4, boolean r5) {
        /*
            com.google.protobuf.GeneratedMessageLite$MethodToInvoke r0 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED
            java.lang.Object r0 = r4.dynamicMethod(r0)
            java.lang.Byte r0 = (java.lang.Byte) r0
            byte r0 = r0.byteValue()
            r1 = 1
            if (r0 != r1) goto L_0x0010
            return r1
        L_0x0010:
            if (r0 != 0) goto L_0x0014
            r1 = 0
            return r1
        L_0x0014:
            com.google.protobuf.Protobuf r1 = com.google.protobuf.Protobuf.getInstance()
            com.google.protobuf.Schema r1 = r1.schemaFor(r4)
            boolean r1 = r1.isInitialized(r4)
            if (r5 == 0) goto L_0x002c
            com.google.protobuf.GeneratedMessageLite$MethodToInvoke r2 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED
            if (r1 == 0) goto L_0x0028
            r3 = r4
            goto L_0x0029
        L_0x0028:
            r3 = 0
        L_0x0029:
            r4.dynamicMethod(r2, r3)
        L_0x002c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageLite.isInitialized(com.google.protobuf.GeneratedMessageLite, boolean):boolean");
    }

    protected static Internal.IntList emptyIntList() {
        return IntArrayList.emptyList();
    }

    protected static Internal.IntList mutableCopy(Internal.IntList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static Internal.LongList emptyLongList() {
        return LongArrayList.emptyList();
    }

    protected static Internal.LongList mutableCopy(Internal.LongList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static Internal.FloatList emptyFloatList() {
        return FloatArrayList.emptyList();
    }

    protected static Internal.FloatList mutableCopy(Internal.FloatList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static Internal.DoubleList emptyDoubleList() {
        return DoubleArrayList.emptyList();
    }

    protected static Internal.DoubleList mutableCopy(Internal.DoubleList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static Internal.BooleanList emptyBooleanList() {
        return BooleanArrayList.emptyList();
    }

    protected static Internal.BooleanList mutableCopy(Internal.BooleanList list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    protected static <E> Internal.ProtobufList<E> emptyProtobufList() {
        return ProtobufArrayList.emptyList();
    }

    protected static <E> Internal.ProtobufList<E> mutableCopy(Internal.ProtobufList<E> list) {
        int size = list.size();
        return list.mutableCopyWithCapacity(size == 0 ? 10 : size * 2);
    }

    static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(GeneratedMessageLite generatedMessageLite, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        T result = (GeneratedMessageLite) generatedMessageLite.dynamicMethod(MethodToInvoke.NEW_MUTABLE_INSTANCE);
        try {
            Protobuf.getInstance().schemaFor((Object) result).mergeFrom(result, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
            result.makeImmutable();
            return result;
        } catch (IOException e) {
            if (e.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e.getCause());
            }
            throw new InvalidProtocolBufferException(e.getMessage()).setUnfinishedMessage(result);
        } catch (RuntimeException e2) {
            if (e2.getCause() instanceof InvalidProtocolBufferException) {
                throw ((InvalidProtocolBufferException) e2.getCause());
            }
            throw e2;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    static <T extends com.google.protobuf.GeneratedMessageLite<T, ?>> T parsePartialFrom(T r7, byte[] r8, int r9, int r10, com.google.protobuf.ExtensionRegistryLite r11) throws com.google.protobuf.InvalidProtocolBufferException {
        /*
            com.google.protobuf.GeneratedMessageLite$MethodToInvoke r0 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE
            java.lang.Object r0 = r7.dynamicMethod(r0)
            com.google.protobuf.GeneratedMessageLite r0 = (com.google.protobuf.GeneratedMessageLite) r0
            com.google.protobuf.Protobuf r1 = com.google.protobuf.Protobuf.getInstance()     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            com.google.protobuf.Schema r1 = r1.schemaFor(r0)     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            int r5 = r9 + r10
            com.google.protobuf.ArrayDecoders$Registers r6 = new com.google.protobuf.ArrayDecoders$Registers     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r6.<init>(r11)     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r2 = r0
            r3 = r8
            r4 = r9
            r1.mergeFrom(r2, r3, r4, r5, r6)     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r0.makeImmutable()     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            int r1 = r0.memoizedHashCode     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            if (r1 != 0) goto L_0x0026
            return r0
        L_0x0026:
            java.lang.RuntimeException r1 = new java.lang.RuntimeException     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r1.<init>()     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            throw r1     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
        L_0x002c:
            r1 = move-exception
            com.google.protobuf.InvalidProtocolBufferException r2 = com.google.protobuf.InvalidProtocolBufferException.truncatedMessage()
            com.google.protobuf.InvalidProtocolBufferException r2 = r2.setUnfinishedMessage(r0)
            throw r2
        L_0x0036:
            r1 = move-exception
            java.lang.Throwable r2 = r1.getCause()
            boolean r2 = r2 instanceof com.google.protobuf.InvalidProtocolBufferException
            if (r2 == 0) goto L_0x0046
            java.lang.Throwable r2 = r1.getCause()
            com.google.protobuf.InvalidProtocolBufferException r2 = (com.google.protobuf.InvalidProtocolBufferException) r2
            throw r2
        L_0x0046:
            com.google.protobuf.InvalidProtocolBufferException r2 = new com.google.protobuf.InvalidProtocolBufferException
            java.lang.String r3 = r1.getMessage()
            r2.<init>(r3)
            com.google.protobuf.InvalidProtocolBufferException r2 = r2.setUnfinishedMessage(r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageLite.parsePartialFrom(com.google.protobuf.GeneratedMessageLite, byte[], int, int, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.GeneratedMessageLite");
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(T defaultInstance, CodedInputStream input) throws InvalidProtocolBufferException {
        return parsePartialFrom(defaultInstance, input, ExtensionRegistryLite.getEmptyRegistry());
    }

    private static <T extends GeneratedMessageLite<T, ?>> T checkMessageInitialized(T message) throws InvalidProtocolBufferException {
        if (message == null || message.isInitialized()) {
            return message;
        }
        throw message.newUninitializedMessageException().asInvalidProtocolBufferException().setUnfinishedMessage(message);
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parseFrom(generatedMessageLite, CodedInputStream.newInstance(data), extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, ByteBuffer data) throws InvalidProtocolBufferException {
        return parseFrom(generatedMessageLite, data, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, ByteString data) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parseFrom(generatedMessageLite, data, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, data, extensionRegistry));
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(GeneratedMessageLite generatedMessageLite, ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        T message;
        try {
            CodedInputStream input = data.newCodedInput();
            message = parsePartialFrom(generatedMessageLite, input, extensionRegistry);
            input.checkLastTagWas(0);
            return message;
        } catch (InvalidProtocolBufferException e) {
            throw e.setUnfinishedMessage(message);
        } catch (InvalidProtocolBufferException e2) {
            throw e2;
        }
    }

    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialFrom(GeneratedMessageLite generatedMessageLite, byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, data, 0, data.length, extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, byte[] data) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, data, 0, data.length, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, data, 0, data.length, extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, InputStream input) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, CodedInputStream.newInstance(input), ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, CodedInputStream.newInstance(input), extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, CodedInputStream input) throws InvalidProtocolBufferException {
        return parseFrom(generatedMessageLite, input, ExtensionRegistryLite.getEmptyRegistry());
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseFrom(GeneratedMessageLite generatedMessageLite, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialFrom(generatedMessageLite, input, extensionRegistry));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(GeneratedMessageLite generatedMessageLite, InputStream input) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialDelimitedFrom(generatedMessageLite, input, ExtensionRegistryLite.getEmptyRegistry()));
    }

    protected static <T extends GeneratedMessageLite<T, ?>> T parseDelimitedFrom(T defaultInstance, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        return checkMessageInitialized(parsePartialDelimitedFrom(defaultInstance, input, extensionRegistry));
    }

    /* JADX INFO: Multiple debug info for r0v2 int: [D('firstByte' int), D('size' int)] */
    private static <T extends GeneratedMessageLite<T, ?>> T parsePartialDelimitedFrom(T defaultInstance, InputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
        try {
            int firstByte = input.read();
            if (firstByte == -1) {
                return null;
            }
            CodedInputStream codedInput = CodedInputStream.newInstance(new AbstractMessageLite.Builder.LimitedInputStream(input, CodedInputStream.readRawVarint32(firstByte, input)));
            T message = parsePartialFrom(defaultInstance, codedInput, extensionRegistry);
            try {
                codedInput.checkLastTagWas(0);
                return message;
            } catch (InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(message);
            }
        } catch (IOException e2) {
            throw new InvalidProtocolBufferException(e2.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public abstract Object dynamicMethod(MethodToInvoke methodToInvoke, Object obj, Object obj2);

    public final Parser<MessageType> getParserForType() {
        return (Parser) dynamicMethod(MethodToInvoke.GET_PARSER);
    }

    public final MessageType getDefaultInstanceForType() {
        return (GeneratedMessageLite) dynamicMethod(MethodToInvoke.GET_DEFAULT_INSTANCE);
    }

    public final BuilderType newBuilderForType() {
        return (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }

    public String toString() {
        return MessageLiteToString.toString(this, super.toString());
    }

    public int hashCode() {
        if (this.memoizedHashCode != 0) {
            return this.memoizedHashCode;
        }
        this.memoizedHashCode = Protobuf.getInstance().schemaFor(this).hashCode(this);
        return this.memoizedHashCode;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!getDefaultInstanceForType().getClass().isInstance(other)) {
            return false;
        }
        return Protobuf.getInstance().schemaFor(this).equals(this, (GeneratedMessageLite) other);
    }

    private final void ensureUnknownFieldsInitialized() {
        if (this.unknownFields == UnknownFieldSetLite.getDefaultInstance()) {
            this.unknownFields = UnknownFieldSetLite.newInstance();
        }
    }

    /* access modifiers changed from: protected */
    public boolean parseUnknownField(int tag, CodedInputStream input) throws IOException {
        if (WireFormat.getTagWireType(tag) == 4) {
            return false;
        }
        ensureUnknownFieldsInitialized();
        return this.unknownFields.mergeFieldFrom(tag, input);
    }

    /* access modifiers changed from: protected */
    public void mergeVarintField(int tag, int value) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.mergeVarintField(tag, value);
    }

    /* access modifiers changed from: protected */
    public void mergeLengthDelimitedField(int fieldNumber, ByteString value) {
        ensureUnknownFieldsInitialized();
        this.unknownFields.mergeLengthDelimitedField(fieldNumber, value);
    }

    /* access modifiers changed from: protected */
    public void makeImmutable() {
        Protobuf.getInstance().schemaFor(this).makeImmutable(this);
    }

    /* access modifiers changed from: protected */
    public final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder() {
        return (Builder) dynamicMethod(MethodToInvoke.NEW_BUILDER);
    }

    /* access modifiers changed from: protected */
    public final <MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> BuilderType createBuilder(MessageType prototype) {
        return createBuilder().mergeFrom((GeneratedMessageLite) prototype);
    }

    public final boolean isInitialized() {
        return isInitialized(this, Boolean.TRUE.booleanValue());
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: BuilderType
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public final BuilderType toBuilder() {
        /*
            r1 = this;
            com.google.protobuf.GeneratedMessageLite$MethodToInvoke r0 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_BUILDER
            java.lang.Object r0 = r1.dynamicMethod(r0)
            com.google.protobuf.GeneratedMessageLite$Builder r0 = (com.google.protobuf.GeneratedMessageLite.Builder) r0
            r0.mergeFrom(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageLite.toBuilder():com.google.protobuf.GeneratedMessageLite$Builder");
    }

    /* access modifiers changed from: protected */
    public Object dynamicMethod(MethodToInvoke method, Object arg0) {
        return dynamicMethod(method, arg0, null);
    }

    /* access modifiers changed from: protected */
    public Object dynamicMethod(MethodToInvoke method) {
        return dynamicMethod(method, null, null);
    }

    /* access modifiers changed from: package-private */
    public int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize;
    }

    /* access modifiers changed from: package-private */
    public void setMemoizedSerializedSize(int size) {
        this.memoizedSerializedSize = size;
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        writeToInternal(output);
    }

    public int getSerializedSize() {
        if (this.memoizedSerializedSize == -1) {
            this.memoizedSerializedSize = getSerializedSizeInternal();
        }
        return this.memoizedSerializedSize;
    }

    /* access modifiers changed from: package-private */
    public Object buildMessageInfo() throws Exception {
        return dynamicMethod(MethodToInvoke.BUILD_MESSAGE_INFO);
    }

    /* access modifiers changed from: protected */
    public final void mergeUnknownFields(UnknownFieldSetLite unknownFields2) {
        this.unknownFields = UnknownFieldSetLite.mutableCopyOf(this.unknownFields, unknownFields2);
    }

    public MutableMessageLite mutableCopy() {
        throw new UnsupportedOperationException("Lite does not support the mutable API.");
    }

    public enum MethodToInvoke {
        GET_MEMOIZED_IS_INITIALIZED,
        SET_MEMOIZED_IS_INITIALIZED,
        BUILD_MESSAGE_INFO,
        NEW_MUTABLE_INSTANCE,
        NEW_BUILDER,
        GET_DEFAULT_INSTANCE,
        GET_PARSER
    }

    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends MessageLiteOrBuilder {
        <Type> Type getExtension(ExtensionLite<MessageType, Type> extensionLite);

        <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extensionLite, int i);

        <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extensionLite);

        <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extensionLite);
    }

    public static abstract class Builder<MessageType extends GeneratedMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>> extends AbstractMessageLite.Builder<MessageType, BuilderType> {
        private final MessageType defaultInstance;
        protected MessageType instance;
        protected boolean isBuilt = false;

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: MessageType
            	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
            	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
            	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
            	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
            	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
            	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
            */
        protected Builder(MessageType r2) {
            /*
                r1 = this;
                r1.<init>()
                r1.defaultInstance = r2
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r0 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE
                java.lang.Object r0 = r2.dynamicMethod(r0)
                com.google.protobuf.GeneratedMessageLite r0 = (com.google.protobuf.GeneratedMessageLite) r0
                r1.instance = r0
                r0 = 0
                r1.isBuilt = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageLite.Builder.<init>(com.google.protobuf.GeneratedMessageLite):void");
        }

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: MessageType
            	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
            	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
            	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
            	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
            	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
            	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
            */
        protected void copyOnWrite() {
            /*
                r2 = this;
                boolean r0 = r2.isBuilt
                if (r0 == 0) goto L_0x0018
                MessageType r0 = r2.instance
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE
                java.lang.Object r0 = r0.dynamicMethod(r1)
                com.google.protobuf.GeneratedMessageLite r0 = (com.google.protobuf.GeneratedMessageLite) r0
                MessageType r1 = r2.instance
                r2.mergeFromInstance(r0, r1)
                r2.instance = r0
                r1 = 0
                r2.isBuilt = r1
            L_0x0018:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageLite.Builder.copyOnWrite():void");
        }

        public final boolean isInitialized() {
            return GeneratedMessageLite.isInitialized(this.instance, false);
        }

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: MessageType
            	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
            	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
            	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
            	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
            	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
            	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
            */
        public final BuilderType clear() {
            /*
                r2 = this;
                MessageType r0 = r2.instance
                com.google.protobuf.GeneratedMessageLite$MethodToInvoke r1 = com.google.protobuf.GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE
                java.lang.Object r0 = r0.dynamicMethod(r1)
                com.google.protobuf.GeneratedMessageLite r0 = (com.google.protobuf.GeneratedMessageLite) r0
                r2.instance = r0
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageLite.Builder.clear():com.google.protobuf.GeneratedMessageLite$Builder");
        }

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: BuilderType
            	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
            	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
            	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
            	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
            	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
            	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
            	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
            */
        public BuilderType clone() {
            /*
                r2 = this;
                com.google.protobuf.GeneratedMessageLite r0 = r2.getDefaultInstanceForType()
                com.google.protobuf.GeneratedMessageLite$Builder r0 = r0.newBuilderForType()
                com.google.protobuf.GeneratedMessageLite r1 = r2.buildPartial()
                r0.mergeFrom(r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageLite.Builder.clone():com.google.protobuf.GeneratedMessageLite$Builder");
        }

        public MessageType buildPartial() {
            if (this.isBuilt) {
                return this.instance;
            }
            this.instance.makeImmutable();
            this.isBuilt = true;
            return this.instance;
        }

        public final MessageType build() {
            MessageType result = buildPartial();
            if (result.isInitialized()) {
                return result;
            }
            throw newUninitializedMessageException(result);
        }

        /* access modifiers changed from: protected */
        public BuilderType internalMergeFrom(MessageType message) {
            return mergeFrom((GeneratedMessageLite) message);
        }

        public BuilderType mergeFrom(GeneratedMessageLite generatedMessageLite) {
            copyOnWrite();
            mergeFromInstance(this.instance, generatedMessageLite);
            return this;
        }

        private void mergeFromInstance(MessageType dest, MessageType src) {
            Protobuf.getInstance().schemaFor((Object) dest).mergeFrom(dest, src);
        }

        public MessageType getDefaultInstanceForType() {
            return this.defaultInstance;
        }

        public BuilderType mergeFrom(byte[] input, int offset, int length, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            copyOnWrite();
            try {
                Protobuf.getInstance().schemaFor((Object) this.instance).mergeFrom(this.instance, input, offset, offset + length, new ArrayDecoders.Registers(extensionRegistry));
                return this;
            } catch (InvalidProtocolBufferException e) {
                throw e;
            } catch (IndexOutOfBoundsException e2) {
                throw InvalidProtocolBufferException.truncatedMessage();
            } catch (IOException e3) {
                throw new RuntimeException("Reading from byte array should not throw IOException.", e3);
            }
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.protobuf.GeneratedMessageLite.Builder.mergeFrom(byte[], int, int, com.google.protobuf.ExtensionRegistryLite):BuilderType
         arg types: [byte[], int, int, com.google.protobuf.ExtensionRegistryLite]
         candidates:
          com.google.protobuf.GeneratedMessageLite.Builder.mergeFrom(byte[], int, int, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.AbstractMessageLite$Builder
          com.google.protobuf.GeneratedMessageLite.Builder.mergeFrom(byte[], int, int, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
          com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(byte[], int, int, com.google.protobuf.ExtensionRegistryLite):BuilderType
          com.google.protobuf.AbstractMessageLite.Builder.mergeFrom(byte[], int, int, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
          com.google.protobuf.MessageLite.Builder.mergeFrom(byte[], int, int, com.google.protobuf.ExtensionRegistryLite):com.google.protobuf.MessageLite$Builder
          com.google.protobuf.GeneratedMessageLite.Builder.mergeFrom(byte[], int, int, com.google.protobuf.ExtensionRegistryLite):BuilderType */
        public BuilderType mergeFrom(byte[] input, int offset, int length) throws InvalidProtocolBufferException {
            return mergeFrom(input, offset, length, ExtensionRegistryLite.getEmptyRegistry());
        }

        public BuilderType mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            copyOnWrite();
            try {
                Protobuf.getInstance().schemaFor((Object) this.instance).mergeFrom(this.instance, CodedInputStreamReader.forCodedInput(input), extensionRegistry);
                return this;
            } catch (RuntimeException e) {
                if (e.getCause() instanceof IOException) {
                    throw ((IOException) e.getCause());
                }
                throw e;
            }
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends GeneratedMessageLite<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected FieldSet<ExtensionDescriptor> extensions = FieldSet.emptySet();

        /* access modifiers changed from: protected */
        public final void mergeExtensionFields(MessageType other) {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
            this.extensions.mergeFrom(other.extensions);
        }

        /* access modifiers changed from: protected */
        public <MessageType extends MessageLite> boolean parseUnknownField(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            int fieldNumber = WireFormat.getTagFieldNumber(tag);
            return parseExtension(input, extensionRegistry, extensionRegistry.findLiteExtensionByNumber(defaultInstance, fieldNumber), tag, fieldNumber);
        }

        /* JADX INFO: Multiple debug info for r7v10 int: [D('rawValue' int), D('value' java.lang.Object)] */
        private boolean parseExtension(CodedInputStream input, ExtensionRegistryLite extensionRegistry, GeneratedExtension<?, ?> extension, int tag, int fieldNumber) throws IOException {
            Object value;
            MessageLite existingValue;
            CodedInputStream codedInputStream = input;
            ExtensionRegistryLite extensionRegistryLite = extensionRegistry;
            GeneratedExtension<?, ?> generatedExtension = extension;
            int wireType = WireFormat.getTagWireType(tag);
            boolean unknown = false;
            boolean packed = false;
            if (generatedExtension == null) {
                unknown = true;
            } else if (wireType == FieldSet.getWireFormatForFieldType(generatedExtension.descriptor.getLiteType(), false)) {
                packed = false;
            } else if (!generatedExtension.descriptor.isRepeated || !generatedExtension.descriptor.type.isPackable() || wireType != FieldSet.getWireFormatForFieldType(generatedExtension.descriptor.getLiteType(), true)) {
                unknown = true;
            } else {
                packed = true;
            }
            if (unknown) {
                return parseUnknownField(tag, codedInputStream);
            }
            ensureExtensionsAreMutable();
            if (packed) {
                int limit = codedInputStream.pushLimit(input.readRawVarint32());
                if (generatedExtension.descriptor.getLiteType() == WireFormat.FieldType.ENUM) {
                    while (input.getBytesUntilLimit() > 0) {
                        Object value2 = generatedExtension.descriptor.getEnumType().findValueByNumber(input.readEnum());
                        if (value2 == null) {
                            return true;
                        }
                        this.extensions.addRepeatedField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(value2));
                    }
                } else {
                    while (input.getBytesUntilLimit() > 0) {
                        this.extensions.addRepeatedField(generatedExtension.descriptor, FieldSet.readPrimitiveField(codedInputStream, generatedExtension.descriptor.getLiteType(), false));
                    }
                }
                codedInputStream.popLimit(limit);
            } else {
                int i = C18601.$SwitchMap$com$google$protobuf$WireFormat$JavaType[generatedExtension.descriptor.getLiteJavaType().ordinal()];
                if (i == 1) {
                    MessageLite.Builder subBuilder = null;
                    if (!generatedExtension.descriptor.isRepeated() && (existingValue = (MessageLite) this.extensions.getField(generatedExtension.descriptor)) != null) {
                        subBuilder = existingValue.toBuilder();
                    }
                    if (subBuilder == null) {
                        subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
                    }
                    if (generatedExtension.descriptor.getLiteType() == WireFormat.FieldType.GROUP) {
                        codedInputStream.readGroup(extension.getNumber(), subBuilder, extensionRegistryLite);
                    } else {
                        codedInputStream.readMessage(subBuilder, extensionRegistryLite);
                    }
                    value = subBuilder.build();
                } else if (i != 2) {
                    value = FieldSet.readPrimitiveField(codedInputStream, generatedExtension.descriptor.getLiteType(), false);
                } else {
                    int rawValue = input.readEnum();
                    value = generatedExtension.descriptor.getEnumType().findValueByNumber(rawValue);
                    if (value == null) {
                        mergeVarintField(fieldNumber, rawValue);
                        return true;
                    }
                }
                if (generatedExtension.descriptor.isRepeated()) {
                    this.extensions.addRepeatedField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(value));
                } else {
                    this.extensions.setField(generatedExtension.descriptor, generatedExtension.singularToFieldSetType(value));
                }
            }
            return true;
        }

        /* access modifiers changed from: protected */
        public <MessageType extends MessageLite> boolean parseUnknownFieldAsMessageSet(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            if (tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
                mergeMessageSetExtensionFromCodedStream(defaultInstance, input, extensionRegistry);
                return true;
            } else if (WireFormat.getTagWireType(tag) == 2) {
                return parseUnknownField(defaultInstance, input, extensionRegistry, tag);
            } else {
                return input.skipField(tag);
            }
        }

        private <MessageType extends MessageLite> void mergeMessageSetExtensionFromCodedStream(MessageType defaultInstance, CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            int typeId = 0;
            ByteString rawBytes = null;
            GeneratedExtension<?, ?> extension = null;
            while (true) {
                int tag = input.readTag();
                if (tag == 0) {
                    break;
                } else if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                    typeId = input.readUInt32();
                    if (typeId != 0) {
                        extension = extensionRegistry.findLiteExtensionByNumber(defaultInstance, typeId);
                    }
                } else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                    if (typeId == 0 || extension == null) {
                        rawBytes = input.readBytes();
                    } else {
                        eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, typeId);
                        rawBytes = null;
                    }
                } else if (!input.skipField(tag)) {
                    break;
                }
            }
            input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
            if (rawBytes != null && typeId != 0) {
                if (extension != null) {
                    mergeMessageSetExtensionFromBytes(rawBytes, extensionRegistry, extension);
                } else {
                    mergeLengthDelimitedField(typeId, rawBytes);
                }
            }
        }

        private void eagerlyMergeMessageSetExtension(CodedInputStream input, GeneratedExtension<?, ?> extension, ExtensionRegistryLite extensionRegistry, int typeId) throws IOException {
            parseExtension(input, extensionRegistry, extension, WireFormat.makeTag(typeId, 2), typeId);
        }

        private void mergeMessageSetExtensionFromBytes(ByteString rawBytes, ExtensionRegistryLite extensionRegistry, GeneratedExtension<?, ?> extension) throws IOException {
            MessageLite.Builder subBuilder = null;
            MessageLite existingValue = (MessageLite) this.extensions.getField(extension.descriptor);
            if (existingValue != null) {
                subBuilder = existingValue.toBuilder();
            }
            if (subBuilder == null) {
                subBuilder = extension.getMessageDefaultInstance().newBuilderForType();
            }
            subBuilder.mergeFrom(rawBytes, extensionRegistry);
            ensureExtensionsAreMutable().setField(extension.descriptor, extension.singularToFieldSetType(subBuilder.build()));
        }

        /* access modifiers changed from: package-private */
        public FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
            return this.extensions;
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extension) {
            GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            return this.extensions.hasField(extensionLite.descriptor);
        }

        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extension) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            return this.extensions.getRepeatedFieldCount(extensionLite.descriptor);
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extension) {
            GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            Object value = this.extensions.getField(extensionLite.descriptor);
            if (value == null) {
                return extensionLite.defaultValue;
            }
            return extensionLite.fromFieldSetType(value);
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, List<Type>> extension, int index) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            return extensionLite.singularFromFieldSetType(this.extensions.getRepeatedField(extensionLite.descriptor, index));
        }

        /* access modifiers changed from: protected */
        public boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        /* access modifiers changed from: protected */
        public ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(this, false, null);
        }

        /* access modifiers changed from: protected */
        public ExtendableMessage<MessageType, BuilderType>.ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter(this, true, null);
        }

        /* access modifiers changed from: protected */
        public int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        /* access modifiers changed from: protected */
        public int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }

        protected class ExtensionWriter {
            private final Iterator<Map.Entry<ExtensionDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Map.Entry<ExtensionDescriptor, Object> next;

            /* synthetic */ ExtensionWriter(ExtendableMessage x0, boolean x1, C18601 x2) {
                this(x1);
            }

            private ExtensionWriter(boolean messageSetWireFormat2) {
                this.iter = ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = this.iter.next();
                }
                this.messageSetWireFormat = messageSetWireFormat2;
            }

            public void writeUntil(int end, CodedOutputStream output) throws IOException {
                while (true) {
                    Map.Entry<ExtensionDescriptor, Object> entry = this.next;
                    if (entry != null && entry.getKey().getNumber() < end) {
                        ExtensionDescriptor extension = this.next.getKey();
                        if (!this.messageSetWireFormat || extension.getLiteJavaType() != WireFormat.JavaType.MESSAGE || extension.isRepeated()) {
                            FieldSet.writeField(extension, this.next.getValue(), output);
                        } else {
                            output.writeMessageSetExtension(extension.getNumber(), (MessageLite) this.next.getValue());
                        }
                        if (this.iter.hasNext()) {
                            this.next = this.iter.next();
                        } else {
                            this.next = null;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /* renamed from: com.google.protobuf.GeneratedMessageLite$1 */
    static /* synthetic */ class C18601 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$WireFormat$JavaType = new int[WireFormat.JavaType.values().length];

        static {
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat.JavaType.MESSAGE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$protobuf$WireFormat$JavaType[WireFormat.JavaType.ENUM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage<MessageType, BuilderType>, BuilderType extends ExtendableBuilder<MessageType, BuilderType>> extends Builder<MessageType, BuilderType> implements ExtendableMessageOrBuilder<MessageType, BuilderType> {
        protected ExtendableBuilder(MessageType defaultInstance) {
            super(defaultInstance);
        }

        /* access modifiers changed from: package-private */
        public void internalSetExtensionSet(FieldSet<ExtensionDescriptor> extensions) {
            copyOnWrite();
            ((ExtendableMessage) this.instance).extensions = extensions;
        }

        /* access modifiers changed from: protected */
        public void copyOnWrite() {
            if (this.isBuilt) {
                super.copyOnWrite();
                ((ExtendableMessage) this.instance).extensions = ((ExtendableMessage) this.instance).extensions.clone();
            }
        }

        private FieldSet<ExtensionDescriptor> ensureExtensionsAreMutable() {
            FieldSet<ExtensionDescriptor> extensions = ((ExtendableMessage) this.instance).extensions;
            if (!extensions.isImmutable()) {
                return extensions;
            }
            FieldSet<ExtensionDescriptor> extensions2 = extensions.clone();
            ((ExtendableMessage) this.instance).extensions = extensions2;
            return extensions2;
        }

        public final MessageType buildPartial() {
            if (this.isBuilt) {
                return (ExtendableMessage) this.instance;
            }
            ((ExtendableMessage) this.instance).extensions.makeImmutable();
            return (ExtendableMessage) super.buildPartial();
        }

        private void verifyExtensionContainingType(GeneratedExtension<MessageType, ?> extension) {
            if (extension.getContainingTypeDefaultInstance() != getDefaultInstanceForType()) {
                throw new IllegalArgumentException("This extension is for a different message type.  Please make sure that you are not suppressing any generics type warnings.");
            }
        }

        public final <Type> boolean hasExtension(ExtensionLite<MessageType, Type> extension) {
            return ((ExtendableMessage) this.instance).hasExtension(extension);
        }

        public final <Type> int getExtensionCount(ExtensionLite<MessageType, List<Type>> extension) {
            return ((ExtendableMessage) this.instance).getExtensionCount(extension);
        }

        public final <Type> Type getExtension(ExtensionLite<MessageType, Type> extension) {
            return ((ExtendableMessage) this.instance).getExtension(extension);
        }

        /* JADX WARN: Type inference failed for: r2v0, types: [com.google.protobuf.ExtensionLite, com.google.protobuf.ExtensionLite<MessageType, java.util.List<Type>>] */
        /* JADX WARNING: Unknown variable types count: 1 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final <Type> Type getExtension(com.google.protobuf.ExtensionLite<MessageType, java.util.List<Type>> r2, int r3) {
            /*
                r1 = this;
                com.google.protobuf.GeneratedMessageLite r0 = r1.instance
                com.google.protobuf.GeneratedMessageLite$ExtendableMessage r0 = (com.google.protobuf.GeneratedMessageLite.ExtendableMessage) r0
                java.lang.Object r0 = r0.getExtension(r2, r3)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.GeneratedMessageLite.ExtendableBuilder.getExtension(com.google.protobuf.ExtensionLite, int):java.lang.Object");
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, Type> extension, Type value) {
            GeneratedExtension<MessageType, Type> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().setField(extensionLite.descriptor, extensionLite.toFieldSetType(value));
            return this;
        }

        public final <Type> BuilderType setExtension(ExtensionLite<MessageType, List<Type>> extension, int index, Type value) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().setRepeatedField(extensionLite.descriptor, index, extensionLite.singularToFieldSetType(value));
            return this;
        }

        public final <Type> BuilderType addExtension(ExtensionLite<MessageType, List<Type>> extension, Type value) {
            GeneratedExtension<MessageType, List<Type>> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().addRepeatedField(extensionLite.descriptor, extensionLite.singularToFieldSetType(value));
            return this;
        }

        public final <Type> BuilderType clearExtension(ExtensionLite<MessageType, ?> extension) {
            GeneratedExtension<MessageType, ?> extensionLite = GeneratedMessageLite.checkIsLite(extension);
            verifyExtensionContainingType(extensionLite);
            copyOnWrite();
            ensureExtensionsAreMutable().clearField(extensionLite.descriptor);
            return this;
        }
    }

    static final class ExtensionDescriptor implements FieldSet.FieldDescriptorLite<ExtensionDescriptor> {
        final Internal.EnumLiteMap<?> enumTypeMap;
        final boolean isPacked;
        final boolean isRepeated;
        final int number;
        final WireFormat.FieldType type;

        ExtensionDescriptor(Internal.EnumLiteMap<?> enumTypeMap2, int number2, WireFormat.FieldType type2, boolean isRepeated2, boolean isPacked2) {
            this.enumTypeMap = enumTypeMap2;
            this.number = number2;
            this.type = type2;
            this.isRepeated = isRepeated2;
            this.isPacked = isPacked2;
        }

        public int getNumber() {
            return this.number;
        }

        public WireFormat.FieldType getLiteType() {
            return this.type;
        }

        public WireFormat.JavaType getLiteJavaType() {
            return this.type.getJavaType();
        }

        public boolean isRepeated() {
            return this.isRepeated;
        }

        public boolean isPacked() {
            return this.isPacked;
        }

        public Internal.EnumLiteMap<?> getEnumType() {
            return this.enumTypeMap;
        }

        public MessageLite.Builder internalMergeFrom(MessageLite.Builder to, MessageLite from) {
            return ((Builder) to).mergeFrom((GeneratedMessageLite) from);
        }

        public MutableMessageLite internalMergeFrom(MutableMessageLite to, MutableMessageLite from) {
            throw new UnsupportedOperationException();
        }

        public int compareTo(ExtensionDescriptor other) {
            return this.number - other.number;
        }
    }

    public static class GeneratedExtension<ContainingType extends MessageLite, Type> extends ExtensionLite<ContainingType, Type> {
        final ContainingType containingTypeDefaultInstance;
        final Type defaultValue;
        final ExtensionDescriptor descriptor;
        final MessageLite messageDefaultInstance;

        GeneratedExtension(ContainingType containingTypeDefaultInstance2, Type defaultValue2, MessageLite messageDefaultInstance2, ExtensionDescriptor descriptor2, Class singularType) {
            if (containingTypeDefaultInstance2 == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            } else if (descriptor2.getLiteType() == WireFormat.FieldType.MESSAGE && messageDefaultInstance2 == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            } else {
                this.containingTypeDefaultInstance = containingTypeDefaultInstance2;
                this.defaultValue = defaultValue2;
                this.messageDefaultInstance = messageDefaultInstance2;
                this.descriptor = descriptor2;
            }
        }

        public ContainingType getContainingTypeDefaultInstance() {
            return this.containingTypeDefaultInstance;
        }

        public int getNumber() {
            return this.descriptor.getNumber();
        }

        public MessageLite getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        /* access modifiers changed from: package-private */
        public Object fromFieldSetType(Object value) {
            if (!this.descriptor.isRepeated()) {
                return singularFromFieldSetType(value);
            }
            if (this.descriptor.getLiteJavaType() != WireFormat.JavaType.ENUM) {
                return value;
            }
            List result = new ArrayList();
            for (Object element : (List) value) {
                result.add(singularFromFieldSetType(element));
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        public Object singularFromFieldSetType(Object value) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return this.descriptor.enumTypeMap.findValueByNumber(((Integer) value).intValue());
            }
            return value;
        }

        /* access modifiers changed from: package-private */
        public Object toFieldSetType(Object value) {
            if (!this.descriptor.isRepeated()) {
                return singularToFieldSetType(value);
            }
            if (this.descriptor.getLiteJavaType() != WireFormat.JavaType.ENUM) {
                return value;
            }
            List result = new ArrayList();
            for (Object element : (List) value) {
                result.add(singularToFieldSetType(element));
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        public Object singularToFieldSetType(Object value) {
            if (this.descriptor.getLiteJavaType() == WireFormat.JavaType.ENUM) {
                return Integer.valueOf(((Internal.EnumLite) value).getNumber());
            }
            return value;
        }

        public WireFormat.FieldType getLiteType() {
            return this.descriptor.getLiteType();
        }

        public boolean isRepeated() {
            return this.descriptor.isRepeated;
        }

        public Type getDefaultValue() {
            return this.defaultValue;
        }
    }

    protected static final class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        private final byte[] asBytes;
        private final Class<?> messageClass;
        private final String messageClassName = this.messageClass.getName();

        SerializedForm(MessageLite regularForm) {
            this.messageClass = regularForm.getClass();
            this.asBytes = regularForm.toByteArray();
        }

        /* renamed from: of */
        public static SerializedForm m232of(MessageLite message) {
            return new SerializedForm(message);
        }

        /* access modifiers changed from: protected */
        public Object readResolve() throws ObjectStreamException {
            try {
                Field defaultInstanceField = resolveMessageClass().getDeclaredField("DEFAULT_INSTANCE");
                defaultInstanceField.setAccessible(true);
                return ((MessageLite) defaultInstanceField.get(null)).newBuilderForType().mergeFrom(this.asBytes).buildPartial();
            } catch (ClassNotFoundException e) {
                String valueOf = String.valueOf(this.messageClassName);
                throw new RuntimeException(valueOf.length() != 0 ? "Unable to find proto buffer class: ".concat(valueOf) : new String("Unable to find proto buffer class: "), e);
            } catch (NoSuchFieldException e2) {
                String valueOf2 = String.valueOf(this.messageClassName);
                throw new RuntimeException(valueOf2.length() != 0 ? "Unable to find DEFAULT_INSTANCE in ".concat(valueOf2) : new String("Unable to find DEFAULT_INSTANCE in "), e2);
            } catch (SecurityException e3) {
                String valueOf3 = String.valueOf(this.messageClassName);
                throw new RuntimeException(valueOf3.length() != 0 ? "Unable to call DEFAULT_INSTANCE in ".concat(valueOf3) : new String("Unable to call DEFAULT_INSTANCE in "), e3);
            } catch (IllegalAccessException e4) {
                throw new RuntimeException("Unable to call parsePartialFrom", e4);
            } catch (InvalidProtocolBufferException e5) {
                throw new RuntimeException("Unable to understand proto buffer", e5);
            }
        }

        private Class<?> resolveMessageClass() throws ClassNotFoundException {
            Class<?> cls = this.messageClass;
            return cls != null ? cls : Class.forName(this.messageClassName);
        }
    }

    protected static class DefaultInstanceBasedParser<T extends GeneratedMessageLite<T, ?>> extends AbstractParser<T> {
        private final T defaultInstance;

        public DefaultInstanceBasedParser(T defaultInstance2) {
            this.defaultInstance = defaultInstance2;
        }

        public T parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, input, extensionRegistry);
        }

        public T parsePartialFrom(byte[] input, int offset, int length, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parsePartialFrom(this.defaultInstance, input, offset, length, extensionRegistry);
        }
    }
}
