package com.google.protobuf;

import com.google.protobuf.FieldSet.FieldDescriptorLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

final class FieldSet<FieldDescriptorType extends FieldDescriptorLite<FieldDescriptorType>> {
    private static final FieldSet DEFAULT_INSTANCE = new FieldSet(true);
    final SmallSortedMap<FieldDescriptorType, Object> fields = SmallSortedMap.newFieldMap(16);
    private boolean hasLazyField = false;
    private boolean isImmutable;

    private FieldSet() {
    }

    private FieldSet(boolean dummy) {
        makeImmutable();
    }

    public static <T extends FieldDescriptorLite<T>> FieldSet<T> newFieldSet() {
        return new FieldSet<>();
    }

    public static <T extends FieldDescriptorLite<T>> FieldSet<T> emptySet() {
        return DEFAULT_INSTANCE;
    }

    private static void verifyType(WireFormat.FieldType type, Object value) {
        Internal.checkNotNull(value);
        boolean isValid = false;
        boolean z = false;
        switch (type.getJavaType()) {
            case INT:
                isValid = value instanceof Integer;
                break;
            case LONG:
                isValid = value instanceof Long;
                break;
            case FLOAT:
                isValid = value instanceof Float;
                break;
            case DOUBLE:
                isValid = value instanceof Double;
                break;
            case BOOLEAN:
                isValid = value instanceof Boolean;
                break;
            case STRING:
                isValid = value instanceof String;
                break;
            case BYTE_STRING:
                if ((value instanceof ByteString) || (value instanceof byte[])) {
                    z = true;
                }
                isValid = z;
                break;
            case ENUM:
                if ((value instanceof Integer) || (value instanceof Internal.EnumLite)) {
                    z = true;
                }
                isValid = z;
                break;
            case MESSAGE:
                if ((value instanceof MessageLite) || (value instanceof LazyField)) {
                    z = true;
                }
                isValid = z;
                break;
        }
        if (!isValid) {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
    }

    static int getWireFormatForFieldType(WireFormat.FieldType type, boolean isPacked) {
        if (isPacked) {
            return 2;
        }
        return type.getWireType();
    }

    public static Object readPrimitiveField(CodedInputStream input, WireFormat.FieldType type, boolean checkUtf8) throws IOException {
        if (checkUtf8) {
            return WireFormat.readPrimitiveField(input, type, WireFormat.Utf8Validation.STRICT);
        }
        return WireFormat.readPrimitiveField(input, type, WireFormat.Utf8Validation.LOOSE);
    }

    public static Object readPrimitiveFieldForMutable(CodedInputStream input, WireFormat.FieldType type, boolean checkUtf8) throws IOException {
        if (type == WireFormat.FieldType.BYTES) {
            return input.readByteArray();
        }
        return readPrimitiveField(input, type, checkUtf8);
    }

    static void writeElement(CodedOutputStream output, WireFormat.FieldType type, int number, Object value) throws IOException {
        if (type != WireFormat.FieldType.GROUP) {
            output.writeTag(number, getWireFormatForFieldType(type, false));
            writeElementNoTag(output, type, value);
        } else if (Internal.isProto1Group((MessageLite) value)) {
            output.writeTag(number, 3);
            output.writeGroupNoTag((MessageLite) value);
        } else {
            output.writeGroup(number, (MessageLite) value);
        }
    }

    static void writeElementNoTag(CodedOutputStream output, WireFormat.FieldType type, Object value) throws IOException {
        switch (type) {
            case DOUBLE:
                output.writeDoubleNoTag(((Double) value).doubleValue());
                return;
            case FLOAT:
                output.writeFloatNoTag(((Float) value).floatValue());
                return;
            case INT64:
                output.writeInt64NoTag(((Long) value).longValue());
                return;
            case UINT64:
                output.writeUInt64NoTag(((Long) value).longValue());
                return;
            case INT32:
                output.writeInt32NoTag(((Integer) value).intValue());
                return;
            case FIXED64:
                output.writeFixed64NoTag(((Long) value).longValue());
                return;
            case FIXED32:
                output.writeFixed32NoTag(((Integer) value).intValue());
                return;
            case BOOL:
                output.writeBoolNoTag(((Boolean) value).booleanValue());
                return;
            case GROUP:
                output.writeGroupNoTag((MessageLite) value);
                return;
            case MESSAGE:
                output.writeMessageNoTag((MessageLite) value);
                return;
            case STRING:
                if (value instanceof ByteString) {
                    output.writeBytesNoTag((ByteString) value);
                    return;
                } else {
                    output.writeStringNoTag((String) value);
                    return;
                }
            case BYTES:
                if (value instanceof ByteString) {
                    output.writeBytesNoTag((ByteString) value);
                    return;
                } else {
                    output.writeByteArrayNoTag((byte[]) value);
                    return;
                }
            case UINT32:
                output.writeUInt32NoTag(((Integer) value).intValue());
                return;
            case SFIXED32:
                output.writeSFixed32NoTag(((Integer) value).intValue());
                return;
            case SFIXED64:
                output.writeSFixed64NoTag(((Long) value).longValue());
                return;
            case SINT32:
                output.writeSInt32NoTag(((Integer) value).intValue());
                return;
            case SINT64:
                output.writeSInt64NoTag(((Long) value).longValue());
                return;
            case ENUM:
                if (value instanceof Internal.EnumLite) {
                    output.writeEnumNoTag(((Internal.EnumLite) value).getNumber());
                    return;
                } else {
                    output.writeEnumNoTag(((Integer) value).intValue());
                    return;
                }
            default:
                return;
        }
    }

    public static void writeField(FieldDescriptorLite<?> descriptor, Object value, CodedOutputStream output) throws IOException {
        WireFormat.FieldType type = descriptor.getLiteType();
        int number = descriptor.getNumber();
        if (descriptor.isRepeated()) {
            List<?> valueList = (List) value;
            if (descriptor.isPacked()) {
                output.writeTag(number, 2);
                int dataSize = 0;
                for (Object element : valueList) {
                    dataSize += computeElementSizeNoTag(type, element);
                }
                output.writeRawVarint32(dataSize);
                for (Object element2 : valueList) {
                    writeElementNoTag(output, type, element2);
                }
                return;
            }
            for (Object element3 : valueList) {
                writeElement(output, type, number, element3);
            }
        } else if (value instanceof LazyField) {
            writeElement(output, type, number, ((LazyField) value).getValue());
        } else {
            writeElement(output, type, number, value);
        }
    }

    static int computeElementSize(WireFormat.FieldType type, int number, Object value) {
        int tagSize = CodedOutputStream.computeTagSize(number);
        if (type == WireFormat.FieldType.GROUP && !Internal.isProto1Group((MessageLite) value)) {
            tagSize *= 2;
        }
        return computeElementSizeNoTag(type, value) + tagSize;
    }

    static int computeElementSizeNoTag(WireFormat.FieldType type, Object value) {
        switch (type) {
            case DOUBLE:
                return CodedOutputStream.computeDoubleSizeNoTag(((Double) value).doubleValue());
            case FLOAT:
                return CodedOutputStream.computeFloatSizeNoTag(((Float) value).floatValue());
            case INT64:
                return CodedOutputStream.computeInt64SizeNoTag(((Long) value).longValue());
            case UINT64:
                return CodedOutputStream.computeUInt64SizeNoTag(((Long) value).longValue());
            case INT32:
                return CodedOutputStream.computeInt32SizeNoTag(((Integer) value).intValue());
            case FIXED64:
                return CodedOutputStream.computeFixed64SizeNoTag(((Long) value).longValue());
            case FIXED32:
                return CodedOutputStream.computeFixed32SizeNoTag(((Integer) value).intValue());
            case BOOL:
                return CodedOutputStream.computeBoolSizeNoTag(((Boolean) value).booleanValue());
            case GROUP:
                return CodedOutputStream.computeGroupSizeNoTag((MessageLite) value);
            case MESSAGE:
                if (value instanceof LazyField) {
                    return CodedOutputStream.computeLazyFieldSizeNoTag((LazyField) value);
                }
                return CodedOutputStream.computeMessageSizeNoTag((MessageLite) value);
            case STRING:
                if (value instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString) value);
                }
                return CodedOutputStream.computeStringSizeNoTag((String) value);
            case BYTES:
                if (value instanceof ByteString) {
                    return CodedOutputStream.computeBytesSizeNoTag((ByteString) value);
                }
                return CodedOutputStream.computeByteArraySizeNoTag((byte[]) value);
            case UINT32:
                return CodedOutputStream.computeUInt32SizeNoTag(((Integer) value).intValue());
            case SFIXED32:
                return CodedOutputStream.computeSFixed32SizeNoTag(((Integer) value).intValue());
            case SFIXED64:
                return CodedOutputStream.computeSFixed64SizeNoTag(((Long) value).longValue());
            case SINT32:
                return CodedOutputStream.computeSInt32SizeNoTag(((Integer) value).intValue());
            case SINT64:
                return CodedOutputStream.computeSInt64SizeNoTag(((Long) value).longValue());
            case ENUM:
                if (value instanceof Internal.EnumLite) {
                    return CodedOutputStream.computeEnumSizeNoTag(((Internal.EnumLite) value).getNumber());
                }
                return CodedOutputStream.computeEnumSizeNoTag(((Integer) value).intValue());
            default:
                throw new RuntimeException("There is no way to get here, but the compiler thinks otherwise.");
        }
    }

    public static int computeFieldSize(FieldDescriptorLite<?> descriptor, Object value) {
        WireFormat.FieldType type = descriptor.getLiteType();
        int number = descriptor.getNumber();
        if (!descriptor.isRepeated()) {
            return computeElementSize(type, number, value);
        }
        if (descriptor.isPacked()) {
            int dataSize = 0;
            for (Object element : (List) value) {
                dataSize += computeElementSizeNoTag(type, element);
            }
            return CodedOutputStream.computeTagSize(number) + dataSize + CodedOutputStream.computeRawVarint32Size(dataSize);
        }
        int size = 0;
        for (Object element2 : (List) value) {
            size += computeElementSize(type, number, element2);
        }
        return size;
    }

    /* access modifiers changed from: package-private */
    public boolean isEmpty() {
        return this.fields.isEmpty();
    }

    public void makeImmutable() {
        if (!this.isImmutable) {
            this.fields.makeImmutable();
            this.isImmutable = true;
        }
    }

    public boolean isImmutable() {
        return this.isImmutable;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldSet)) {
            return false;
        }
        return this.fields.equals(((FieldSet) o).fields);
    }

    public int hashCode() {
        return this.fields.hashCode();
    }

    public FieldSet<FieldDescriptorType> clone() {
        FieldSet<FieldDescriptorType> clone = newFieldSet();
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            Map.Entry<FieldDescriptorType, Object> entry = this.fields.getArrayEntryAt(i);
            clone.setField((FieldDescriptorLite) entry.getKey(), entry.getValue());
        }
        for (Map.Entry<FieldDescriptorType, Object> entry2 : this.fields.getOverflowEntries()) {
            clone.setField((FieldDescriptorLite) entry2.getKey(), entry2.getValue());
        }
        clone.hasLazyField = this.hasLazyField;
        return clone;
    }

    private Object convertToImmutable(FieldDescriptorType descriptor, Object value) {
        if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            if (descriptor.isRepeated()) {
                List<Object> immutableMessages = new ArrayList<>();
                Iterator it = ((List) value).iterator();
                while (it.hasNext()) {
                    immutableMessages.add(((MutableMessageLite) it.next()).immutableCopy());
                }
                return immutableMessages;
            } else if (value instanceof LazyField) {
                return ((MutableMessageLite) ((LazyField) value).getValue()).immutableCopy();
            } else {
                return ((MutableMessageLite) value).immutableCopy();
            }
        } else if (descriptor.getLiteJavaType() != WireFormat.JavaType.BYTE_STRING) {
            return value;
        } else {
            if (!descriptor.isRepeated()) {
                return ByteString.copyFrom((byte[]) value);
            }
            List<Object> immutableFields = new ArrayList<>();
            Iterator it2 = ((List) value).iterator();
            while (it2.hasNext()) {
                immutableFields.add(ByteString.copyFrom((byte[]) it2.next()));
            }
            return immutableFields;
        }
    }

    private Object convertToMutable(FieldDescriptorType descriptor, Object value) {
        if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            if (descriptor.isRepeated()) {
                List<Object> mutableMessages = new ArrayList<>();
                Iterator it = ((List) value).iterator();
                while (it.hasNext()) {
                    mutableMessages.add(((MessageLite) it.next()).mutableCopy());
                }
                return mutableMessages;
            } else if (value instanceof LazyField) {
                return ((LazyField) value).getValue().mutableCopy();
            } else {
                return ((MessageLite) value).mutableCopy();
            }
        } else if (descriptor.getLiteJavaType() != WireFormat.JavaType.BYTE_STRING) {
            return value;
        } else {
            if (!descriptor.isRepeated()) {
                return ((ByteString) value).toByteArray();
            }
            List<Object> mutableFields = new ArrayList<>();
            Iterator it2 = ((List) value).iterator();
            while (it2.hasNext()) {
                mutableFields.add(((ByteString) it2.next()).toByteArray());
            }
            return mutableFields;
        }
    }

    public FieldSet<FieldDescriptorType> cloneWithAllFieldsToImmutable() {
        FieldSet<FieldDescriptorType> clone = newFieldSet();
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            Map.Entry<FieldDescriptorType, Object> entry = this.fields.getArrayEntryAt(i);
            FieldDescriptorType descriptor = (FieldDescriptorLite) entry.getKey();
            clone.setField(descriptor, convertToImmutable(descriptor, entry.getValue()));
        }
        for (Map.Entry<FieldDescriptorType, Object> entry2 : this.fields.getOverflowEntries()) {
            FieldDescriptorType descriptor2 = (FieldDescriptorLite) entry2.getKey();
            clone.setField(descriptor2, convertToImmutable(descriptor2, entry2.getValue()));
        }
        clone.hasLazyField = false;
        return clone;
    }

    public FieldSet<FieldDescriptorType> cloneWithAllFieldsToMutable() {
        FieldSet<FieldDescriptorType> clone = newFieldSet();
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            Map.Entry<FieldDescriptorType, Object> entry = this.fields.getArrayEntryAt(i);
            FieldDescriptorType descriptor = (FieldDescriptorLite) entry.getKey();
            clone.setField(descriptor, convertToMutable(descriptor, entry.getValue()));
        }
        for (Map.Entry<FieldDescriptorType, Object> entry2 : this.fields.getOverflowEntries()) {
            FieldDescriptorType descriptor2 = (FieldDescriptorLite) entry2.getKey();
            clone.setField(descriptor2, convertToMutable(descriptor2, entry2.getValue()));
        }
        clone.hasLazyField = false;
        return clone;
    }

    public void clear() {
        this.fields.clear();
        this.hasLazyField = false;
    }

    public Map<FieldDescriptorType, Object> getAllFields() {
        if (!this.hasLazyField) {
            return this.fields.isImmutable() ? this.fields : Collections.unmodifiableMap(this.fields);
        }
        SmallSortedMap<FieldDescriptorType, Object> result = SmallSortedMap.newFieldMap(16);
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            cloneFieldEntry(result, this.fields.getArrayEntryAt(i));
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.fields.getOverflowEntries()) {
            cloneFieldEntry(result, entry);
        }
        if (this.fields.isImmutable()) {
            result.makeImmutable();
        }
        return result;
    }

    private void cloneFieldEntry(Map<FieldDescriptorType, Object> map, Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType key = (FieldDescriptorLite) entry.getKey();
        Object value = entry.getValue();
        if (value instanceof LazyField) {
            map.put(key, ((LazyField) value).getValue());
        } else {
            map.put(key, value);
        }
    }

    public Iterator<Map.Entry<FieldDescriptorType, Object>> iterator() {
        if (this.hasLazyField) {
            return new LazyField.LazyIterator(this.fields.entrySet().iterator());
        }
        return this.fields.entrySet().iterator();
    }

    /* access modifiers changed from: package-private */
    public Iterator<Map.Entry<FieldDescriptorType, Object>> descendingIterator() {
        if (this.hasLazyField) {
            return new LazyField.LazyIterator(this.fields.descendingEntrySet().iterator());
        }
        return this.fields.descendingEntrySet().iterator();
    }

    public boolean hasField(FieldDescriptorType descriptor) {
        if (!descriptor.isRepeated()) {
            return this.fields.get(descriptor) != null;
        }
        throw new IllegalArgumentException("hasField() can only be called on non-repeated fields.");
    }

    public Object getField(FieldDescriptorType descriptor) {
        Object o = this.fields.get(descriptor);
        if (o instanceof LazyField) {
            return ((LazyField) o).getValue();
        }
        return o;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.SmallSortedMap.put(java.lang.Comparable, java.lang.Object):V
     arg types: [FieldDescriptorType, java.lang.Object]
     candidates:
      com.google.protobuf.SmallSortedMap.put(java.lang.Object, java.lang.Object):java.lang.Object
      ClspMth{java.util.AbstractMap.put(java.lang.Object, java.lang.Object):V}
      ClspMth{java.util.Map.put(java.lang.Object, java.lang.Object):V}
      com.google.protobuf.SmallSortedMap.put(java.lang.Comparable, java.lang.Object):V */
    public void setField(FieldDescriptorType descriptor, Object value) {
        if (!descriptor.isRepeated()) {
            verifyType(descriptor.getLiteType(), value);
        } else if (value instanceof List) {
            List<Object> newList = new ArrayList<>();
            newList.addAll((List) value);
            for (Object element : newList) {
                verifyType(descriptor.getLiteType(), element);
            }
            value = newList;
        } else {
            throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
        }
        if (value instanceof LazyField) {
            this.hasLazyField = true;
        }
        this.fields.put((Comparable) descriptor, value);
    }

    public void clearField(FieldDescriptorType descriptor) {
        this.fields.remove(descriptor);
        if (this.fields.isEmpty()) {
            this.hasLazyField = false;
        }
    }

    public int getRepeatedFieldCount(FieldDescriptorType descriptor) {
        if (descriptor.isRepeated()) {
            Object value = getField(descriptor);
            if (value == null) {
                return 0;
            }
            return ((List) value).size();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public Object getRepeatedField(FieldDescriptorType descriptor, int index) {
        if (descriptor.isRepeated()) {
            Object value = getField(descriptor);
            if (value != null) {
                return ((List) value).get(index);
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    public void setRepeatedField(FieldDescriptorType descriptor, int index, Object value) {
        if (descriptor.isRepeated()) {
            Object list = getField(descriptor);
            if (list != null) {
                verifyType(descriptor.getLiteType(), value);
                ((List) list).set(index, value);
                return;
            }
            throw new IndexOutOfBoundsException();
        }
        throw new IllegalArgumentException("getRepeatedField() can only be called on repeated fields.");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.SmallSortedMap.put(java.lang.Comparable, java.lang.Object):V
     arg types: [FieldDescriptorType, java.util.List<java.lang.Object>]
     candidates:
      com.google.protobuf.SmallSortedMap.put(java.lang.Object, java.lang.Object):java.lang.Object
      ClspMth{java.util.AbstractMap.put(java.lang.Object, java.lang.Object):V}
      ClspMth{java.util.Map.put(java.lang.Object, java.lang.Object):V}
      com.google.protobuf.SmallSortedMap.put(java.lang.Comparable, java.lang.Object):V */
    public void addRepeatedField(FieldDescriptorType descriptor, Object value) {
        List<Object> list;
        if (descriptor.isRepeated()) {
            verifyType(descriptor.getLiteType(), value);
            Object existingValue = getField(descriptor);
            if (existingValue == null) {
                list = new ArrayList<>();
                this.fields.put((Comparable) descriptor, (Object) list);
            } else {
                list = (List) existingValue;
            }
            list.add(value);
            return;
        }
        throw new IllegalArgumentException("addRepeatedField() can only be called on repeated fields.");
    }

    public boolean isInitialized() {
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            if (!isInitialized(this.fields.getArrayEntryAt(i))) {
                return false;
            }
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.fields.getOverflowEntries()) {
            if (!isInitialized(entry)) {
                return false;
            }
        }
        return true;
    }

    private boolean isInitialized(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType descriptor = (FieldDescriptorLite) entry.getKey();
        if (descriptor.getLiteJavaType() == WireFormat.JavaType.MESSAGE) {
            if (descriptor.isRepeated()) {
                for (MessageLite element : (List) entry.getValue()) {
                    if (!element.isInitialized()) {
                        return false;
                    }
                }
            } else {
                Object value = entry.getValue();
                if (value instanceof MessageLite) {
                    if (!((MessageLite) value).isInitialized()) {
                        return false;
                    }
                } else if (value instanceof LazyField) {
                    return true;
                } else {
                    throw new IllegalArgumentException("Wrong object type used with protocol message reflection.");
                }
            }
        }
        return true;
    }

    public void mergeFrom(FieldSet<FieldDescriptorType> other) {
        for (int i = 0; i < other.fields.getNumArrayEntries(); i++) {
            mergeFromField(other.fields.getArrayEntryAt(i));
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : other.fields.getOverflowEntries()) {
            mergeFromField(entry);
        }
    }

    private Object cloneIfMutable(Object value) {
        if (value instanceof MutableMessageLite) {
            return ((MutableMessageLite) value).clone();
        }
        if (!(value instanceof byte[])) {
            return value;
        }
        byte[] bytes = (byte[]) value;
        byte[] copy = new byte[bytes.length];
        System.arraycopy(bytes, 0, copy, 0, bytes.length);
        return copy;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.protobuf.SmallSortedMap.put(java.lang.Comparable, java.lang.Object):V
     arg types: [FieldDescriptorType, java.lang.Object]
     candidates:
      com.google.protobuf.SmallSortedMap.put(java.lang.Object, java.lang.Object):java.lang.Object
      ClspMth{java.util.AbstractMap.put(java.lang.Object, java.lang.Object):V}
      ClspMth{java.util.Map.put(java.lang.Object, java.lang.Object):V}
      com.google.protobuf.SmallSortedMap.put(java.lang.Comparable, java.lang.Object):V */
    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: FieldDescriptorType
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
    private void mergeFromField(java.util.Map.Entry<FieldDescriptorType, java.lang.Object> r8) {
        /*
            r7 = this;
            java.lang.Object r0 = r8.getKey()
            com.google.protobuf.FieldSet$FieldDescriptorLite r0 = (com.google.protobuf.FieldSet.FieldDescriptorLite) r0
            java.lang.Object r1 = r8.getValue()
            boolean r2 = r1 instanceof com.google.protobuf.LazyField
            if (r2 == 0) goto L_0x0015
            r2 = r1
            com.google.protobuf.LazyField r2 = (com.google.protobuf.LazyField) r2
            com.google.protobuf.MessageLite r1 = r2.getValue()
        L_0x0015:
            boolean r2 = r0.isRepeated()
            if (r2 == 0) goto L_0x0049
            java.lang.Object r2 = r7.getField(r0)
            if (r2 != 0) goto L_0x0027
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r2 = r3
        L_0x0027:
            r3 = r1
            java.util.List r3 = (java.util.List) r3
            java.util.Iterator r3 = r3.iterator()
        L_0x002e:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0043
            java.lang.Object r4 = r3.next()
            r5 = r2
            java.util.List r5 = (java.util.List) r5
            java.lang.Object r6 = r7.cloneIfMutable(r4)
            r5.add(r6)
            goto L_0x002e
        L_0x0043:
            com.google.protobuf.SmallSortedMap<FieldDescriptorType, java.lang.Object> r3 = r7.fields
            r3.put(r0, r2)
            goto L_0x0091
        L_0x0049:
            com.google.protobuf.WireFormat$JavaType r2 = r0.getLiteJavaType()
            com.google.protobuf.WireFormat$JavaType r3 = com.google.protobuf.WireFormat.JavaType.MESSAGE
            if (r2 != r3) goto L_0x0088
            java.lang.Object r2 = r7.getField(r0)
            if (r2 != 0) goto L_0x0061
            com.google.protobuf.SmallSortedMap<FieldDescriptorType, java.lang.Object> r3 = r7.fields
            java.lang.Object r4 = r7.cloneIfMutable(r1)
            r3.put(r0, r4)
            goto L_0x0087
        L_0x0061:
            boolean r3 = r2 instanceof com.google.protobuf.MutableMessageLite
            if (r3 == 0) goto L_0x0070
            r3 = r2
            com.google.protobuf.MutableMessageLite r3 = (com.google.protobuf.MutableMessageLite) r3
            r4 = r1
            com.google.protobuf.MutableMessageLite r4 = (com.google.protobuf.MutableMessageLite) r4
            com.google.protobuf.MutableMessageLite r2 = r0.internalMergeFrom(r3, r4)
            goto L_0x0082
        L_0x0070:
            r3 = r2
            com.google.protobuf.MessageLite r3 = (com.google.protobuf.MessageLite) r3
            com.google.protobuf.MessageLite$Builder r3 = r3.toBuilder()
            r4 = r1
            com.google.protobuf.MessageLite r4 = (com.google.protobuf.MessageLite) r4
            com.google.protobuf.MessageLite$Builder r3 = r0.internalMergeFrom(r3, r4)
            com.google.protobuf.MessageLite r2 = r3.build()
        L_0x0082:
            com.google.protobuf.SmallSortedMap<FieldDescriptorType, java.lang.Object> r3 = r7.fields
            r3.put(r0, r2)
        L_0x0087:
            goto L_0x0091
        L_0x0088:
            com.google.protobuf.SmallSortedMap<FieldDescriptorType, java.lang.Object> r2 = r7.fields
            java.lang.Object r3 = r7.cloneIfMutable(r1)
            r2.put(r0, r3)
        L_0x0091:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.FieldSet.mergeFromField(java.util.Map$Entry):void");
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            Map.Entry<FieldDescriptorType, Object> entry = this.fields.getArrayEntryAt(i);
            writeField((FieldDescriptorLite) entry.getKey(), entry.getValue(), output);
        }
        for (Map.Entry<FieldDescriptorType, Object> entry2 : this.fields.getOverflowEntries()) {
            writeField((FieldDescriptorLite) entry2.getKey(), entry2.getValue(), output);
        }
    }

    public void writeMessageSetTo(CodedOutputStream output) throws IOException {
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            writeMessageSetTo(this.fields.getArrayEntryAt(i), output);
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.fields.getOverflowEntries()) {
            writeMessageSetTo(entry, output);
        }
    }

    private void writeMessageSetTo(Map.Entry<FieldDescriptorType, Object> entry, CodedOutputStream output) throws IOException {
        FieldDescriptorType descriptor = (FieldDescriptorLite) entry.getKey();
        if (descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE || descriptor.isRepeated() || descriptor.isPacked()) {
            writeField(descriptor, entry.getValue(), output);
            return;
        }
        Object value = entry.getValue();
        if (value instanceof LazyField) {
            value = ((LazyField) value).getValue();
        }
        output.writeMessageSetExtension(((FieldDescriptorLite) entry.getKey()).getNumber(), (MessageLite) value);
    }

    public int getSerializedSize() {
        int size = 0;
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            Map.Entry<FieldDescriptorType, Object> entry = this.fields.getArrayEntryAt(i);
            size += computeFieldSize((FieldDescriptorLite) entry.getKey(), entry.getValue());
        }
        for (Map.Entry<FieldDescriptorType, Object> entry2 : this.fields.getOverflowEntries()) {
            size += computeFieldSize((FieldDescriptorLite) entry2.getKey(), entry2.getValue());
        }
        return size;
    }

    public int getMessageSetSerializedSize() {
        int size = 0;
        for (int i = 0; i < this.fields.getNumArrayEntries(); i++) {
            size += getMessageSetSerializedSize(this.fields.getArrayEntryAt(i));
        }
        for (Map.Entry<FieldDescriptorType, Object> entry : this.fields.getOverflowEntries()) {
            size += getMessageSetSerializedSize(entry);
        }
        return size;
    }

    private int getMessageSetSerializedSize(Map.Entry<FieldDescriptorType, Object> entry) {
        FieldDescriptorType descriptor = (FieldDescriptorLite) entry.getKey();
        Object value = entry.getValue();
        if (descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE || descriptor.isRepeated() || descriptor.isPacked()) {
            return computeFieldSize(descriptor, value);
        }
        if (value instanceof LazyField) {
            return CodedOutputStream.computeLazyFieldMessageSetExtensionSize(((FieldDescriptorLite) entry.getKey()).getNumber(), (LazyField) value);
        }
        return CodedOutputStream.computeMessageSetExtensionSize(((FieldDescriptorLite) entry.getKey()).getNumber(), (MessageLite) value);
    }

    public interface FieldDescriptorLite<T extends FieldDescriptorLite<T>> extends Comparable<T> {
        Internal.EnumLiteMap<?> getEnumType();

        WireFormat.JavaType getLiteJavaType();

        WireFormat.FieldType getLiteType();

        int getNumber();

        MessageLite.Builder internalMergeFrom(MessageLite.Builder builder, MessageLite messageLite);

        MutableMessageLite internalMergeFrom(MutableMessageLite mutableMessageLite, MutableMessageLite mutableMessageLite2);

        boolean isPacked();

        boolean isRepeated();
    }
}
