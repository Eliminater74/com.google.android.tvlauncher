package com.google.protobuf.nano;

import com.google.protobuf.MessageLite;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class FieldData implements Cloneable {
    private Extension<?, ?> cachedExtension;
    private List<UnknownFieldData> unknownFieldData;
    private Object value;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.protobuf.nano.Extension<?, ?>, com.google.protobuf.nano.Extension<?, T>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    <T> FieldData(com.google.protobuf.nano.Extension<?, T> r1, T r2) {
        /*
            r0 = this;
            r0.<init>()
            r0.cachedExtension = r1
            r0.value = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.nano.FieldData.<init>(com.google.protobuf.nano.Extension, java.lang.Object):void");
    }

    FieldData() {
        this.unknownFieldData = new ArrayList();
    }

    /* JADX INFO: Multiple debug info for r0v1 java.lang.Object: [D('bytes' byte[]), D('newValue' java.lang.Object)] */
    /* access modifiers changed from: package-private */
    public void addUnknownField(UnknownFieldData unknownField) throws IOException {
        Object newValue;
        List<UnknownFieldData> list = this.unknownFieldData;
        if (list != null) {
            list.add(unknownField);
            return;
        }
        Object newValue2 = this.value;
        if (newValue2 instanceof MessageNano) {
            byte[] bytes = unknownField.bytes;
            CodedInputByteBufferNano input = CodedInputByteBufferNano.newInstance(bytes, 0, bytes.length);
            int length = input.readInt32();
            if (length == bytes.length - CodedOutputByteBufferNano.computeInt32SizeNoTag(length)) {
                newValue = ((MessageNano) this.value).mergeFrom(input);
            } else {
                throw InvalidProtocolBufferNanoException.truncatedMessage();
            }
        } else if (newValue2 instanceof MessageNano[]) {
            MessageNano[] newElements = (MessageNano[]) this.cachedExtension.getValueFrom(Collections.singletonList(unknownField));
            MessageNano[] array = (MessageNano[]) this.value;
            MessageNano[] newArray = (MessageNano[]) Arrays.copyOf(array, array.length + newElements.length);
            System.arraycopy(newElements, 0, newArray, array.length, newElements.length);
            newValue = newArray;
        } else if (newValue2 instanceof MessageLite) {
            newValue = ((MessageLite) this.value).toBuilder().mergeFrom((MessageLite) this.cachedExtension.getValueFrom(Collections.singletonList(unknownField))).build();
        } else if (newValue2 instanceof MessageLite[]) {
            MessageLite[] newElements2 = (MessageLite[]) this.cachedExtension.getValueFrom(Collections.singletonList(unknownField));
            MessageLite[] array2 = (MessageLite[]) this.value;
            MessageLite[] newArray2 = (MessageLite[]) Arrays.copyOf(array2, array2.length + newElements2.length);
            System.arraycopy(newElements2, 0, newArray2, array2.length, newElements2.length);
            newValue = newArray2;
        } else {
            newValue = this.cachedExtension.getValueFrom(Collections.singletonList(unknownField));
        }
        setValue(this.cachedExtension, newValue);
    }

    /* access modifiers changed from: package-private */
    public UnknownFieldData getUnknownField(int index) {
        List<UnknownFieldData> list = this.unknownFieldData;
        if (list != null && index < list.size()) {
            return this.unknownFieldData.get(index);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int getUnknownFieldSize() {
        List<UnknownFieldData> list = this.unknownFieldData;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.google.protobuf.nano.Extension<?, ?>, com.google.protobuf.nano.Extension, com.google.protobuf.nano.Extension<?, T>, java.lang.Object] */
    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: ?
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
    <T> T getValue(com.google.protobuf.nano.Extension<?, T> r3) {
        /*
            r2 = this;
            java.lang.Object r0 = r2.value
            if (r0 == 0) goto L_0x0015
            com.google.protobuf.nano.Extension<?, ?> r0 = r2.cachedExtension
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x000d
            goto L_0x0022
        L_0x000d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Tried to getExtension with a different Extension."
            r0.<init>(r1)
            throw r0
        L_0x0015:
            r2.cachedExtension = r3
            java.util.List<com.google.protobuf.nano.UnknownFieldData> r0 = r2.unknownFieldData
            java.lang.Object r0 = r3.getValueFrom(r0)
            r2.value = r0
            r0 = 0
            r2.unknownFieldData = r0
        L_0x0022:
            java.lang.Object r0 = r2.value
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.nano.FieldData.getValue(com.google.protobuf.nano.Extension):java.lang.Object");
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.protobuf.nano.Extension<?, ?>, com.google.protobuf.nano.Extension<?, T>] */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> void setValue(com.google.protobuf.nano.Extension<?, T> r2, T r3) {
        /*
            r1 = this;
            r1.cachedExtension = r2
            r1.value = r3
            r0 = 0
            r1.unknownFieldData = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.nano.FieldData.setValue(com.google.protobuf.nano.Extension, java.lang.Object):void");
    }

    /* access modifiers changed from: package-private */
    public int computeSerializedSize() {
        int size = 0;
        Object obj = this.value;
        if (obj != null) {
            return this.cachedExtension.computeSerializedSize(obj);
        }
        for (UnknownFieldData unknownField : this.unknownFieldData) {
            size += unknownField.computeSerializedSize();
        }
        return size;
    }

    /* access modifiers changed from: package-private */
    public int computeSerializedSizeAsMessageSet() {
        int size = 0;
        Object obj = this.value;
        if (obj != null) {
            return this.cachedExtension.computeSerializedSizeAsMessageSet(obj);
        }
        for (UnknownFieldData unknownField : this.unknownFieldData) {
            size += unknownField.computeSerializedSizeAsMessageSet();
        }
        return size;
    }

    /* access modifiers changed from: package-private */
    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
        Object obj = this.value;
        if (obj != null) {
            this.cachedExtension.writeTo(obj, output);
            return;
        }
        for (UnknownFieldData unknownField : this.unknownFieldData) {
            unknownField.writeTo(output);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeAsMessageSetTo(CodedOutputByteBufferNano output) throws IOException {
        Object obj = this.value;
        if (obj != null) {
            this.cachedExtension.writeAsMessageSetTo(obj, output);
            return;
        }
        for (UnknownFieldData unknownField : this.unknownFieldData) {
            unknownField.writeAsMessageSetTo(output);
        }
    }

    public boolean equals(Object o) {
        List<UnknownFieldData> list;
        if (o == this) {
            return true;
        }
        if (!(o instanceof FieldData)) {
            return false;
        }
        FieldData other = (FieldData) o;
        if (this.value == null || other.value == null) {
            List<UnknownFieldData> list2 = this.unknownFieldData;
            if (list2 != null && (list = other.unknownFieldData) != null) {
                return list2.equals(list);
            }
            try {
                return Arrays.equals(toByteArray(), other.toByteArray());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            Extension<?, ?> extension = this.cachedExtension;
            if (extension != other.cachedExtension) {
                return false;
            }
            if (!extension.clazz.isArray()) {
                return this.value.equals(other.value);
            }
            Object obj = this.value;
            if (obj instanceof byte[]) {
                return Arrays.equals((byte[]) obj, (byte[]) other.value);
            }
            if (obj instanceof int[]) {
                return Arrays.equals((int[]) obj, (int[]) other.value);
            }
            if (obj instanceof long[]) {
                return Arrays.equals((long[]) obj, (long[]) other.value);
            }
            if (obj instanceof float[]) {
                return Arrays.equals((float[]) obj, (float[]) other.value);
            }
            if (obj instanceof double[]) {
                return Arrays.equals((double[]) obj, (double[]) other.value);
            }
            if (obj instanceof boolean[]) {
                return Arrays.equals((boolean[]) obj, (boolean[]) other.value);
            }
            return Arrays.deepEquals((Object[]) obj, (Object[]) other.value);
        }
    }

    public int hashCode() {
        try {
            return (17 * 31) + Arrays.hashCode(toByteArray());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private byte[] toByteArray() throws IOException {
        byte[] result = new byte[computeSerializedSize()];
        writeTo(CodedOutputByteBufferNano.newInstance(result));
        return result;
    }

    public final FieldData clone() {
        FieldData clone = new FieldData();
        try {
            clone.cachedExtension = this.cachedExtension;
            if (this.unknownFieldData == null) {
                clone.unknownFieldData = null;
            } else {
                clone.unknownFieldData.addAll(this.unknownFieldData);
            }
            if (this.value != null) {
                if (this.value instanceof MessageNano) {
                    clone.value = ((MessageNano) this.value).clone();
                } else if (this.value instanceof byte[]) {
                    clone.value = ((byte[]) this.value).clone();
                } else {
                    int i = 0;
                    if (this.value instanceof byte[][]) {
                        byte[][] valueArray = (byte[][]) this.value;
                        byte[][] cloneArray = new byte[valueArray.length][];
                        clone.value = cloneArray;
                        while (i < valueArray.length) {
                            cloneArray[i] = (byte[]) valueArray[i].clone();
                            i++;
                        }
                    } else if (this.value instanceof boolean[]) {
                        clone.value = ((boolean[]) this.value).clone();
                    } else if (this.value instanceof int[]) {
                        clone.value = ((int[]) this.value).clone();
                    } else if (this.value instanceof long[]) {
                        clone.value = ((long[]) this.value).clone();
                    } else if (this.value instanceof float[]) {
                        clone.value = ((float[]) this.value).clone();
                    } else if (this.value instanceof double[]) {
                        clone.value = ((double[]) this.value).clone();
                    } else if (this.value instanceof MessageNano[]) {
                        MessageNano[] valueArray2 = (MessageNano[]) this.value;
                        MessageNano[] cloneArray2 = new MessageNano[valueArray2.length];
                        clone.value = cloneArray2;
                        while (i < valueArray2.length) {
                            cloneArray2[i] = valueArray2[i].clone();
                            i++;
                        }
                    }
                }
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}
