package com.google.protobuf.nano;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public abstract class MessageNano {
    public static final int UNSET_ENUM_VALUE = Integer.MIN_VALUE;
    protected volatile int cachedSize = -1;

    public interface GeneratedMapEntry {
    }

    public abstract MessageNano mergeFrom(CodedInputByteBufferNano codedInputByteBufferNano) throws IOException;

    public int getCachedSize() {
        if (this.cachedSize < 0) {
            getSerializedSize();
        }
        return this.cachedSize;
    }

    public int getSerializedSize() {
        int size = computeSerializedSize();
        this.cachedSize = size;
        return size;
    }

    /* access modifiers changed from: protected */
    public int computeSerializedSize() {
        return 0;
    }

    public void writeTo(CodedOutputByteBufferNano output) throws IOException {
    }

    public static final byte[] toByteArray(MessageNano msg) {
        byte[] result = new byte[msg.getSerializedSize()];
        toByteArray(msg, result, 0, result.length);
        return result;
    }

    public static final void toByteArray(MessageNano msg, byte[] data, int offset, int length) {
        try {
            CodedOutputByteBufferNano output = CodedOutputByteBufferNano.newInstance(data, offset, length);
            msg.writeTo(output);
            output.checkNoSpaceLeft();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends MessageNano> T mergeFrom(T msg, byte[] data) throws InvalidProtocolBufferNanoException {
        return mergeFrom(msg, data, 0, data.length);
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public static final <T extends com.google.protobuf.nano.MessageNano> T mergeFrom(T r3, byte[] r4, int r5, int r6) throws com.google.protobuf.nano.InvalidProtocolBufferNanoException {
        /*
            com.google.protobuf.nano.CodedInputByteBufferNano r0 = com.google.protobuf.nano.CodedInputByteBufferNano.newInstance(r4, r5, r6)     // Catch:{ InvalidProtocolBufferNanoException -> 0x0015, IOException -> 0x000c }
            r3.mergeFrom(r0)     // Catch:{ InvalidProtocolBufferNanoException -> 0x0015, IOException -> 0x000c }
            r1 = 0
            r0.checkLastTagWas(r1)     // Catch:{ InvalidProtocolBufferNanoException -> 0x0015, IOException -> 0x000c }
            return r3
        L_0x000c:
            r0 = move-exception
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "Reading from a byte array threw an IOException (should never happen)."
            r1.<init>(r2, r0)
            throw r1
        L_0x0015:
            r0 = move-exception
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.nano.MessageNano.mergeFrom(com.google.protobuf.nano.MessageNano, byte[], int, int):com.google.protobuf.nano.MessageNano");
    }

    public static final <T extends MessageNano> T cloneUsingSerialization(T msg) {
        try {
            return mergeFrom((MessageNano) msg.getClass().getConstructor(new Class[0]).newInstance(new Object[0]), toByteArray(msg));
        } catch (NoSuchMethodException nsme) {
            throw new IllegalStateException(nsme);
        } catch (InstantiationException ie) {
            throw new IllegalStateException(ie);
        } catch (InvocationTargetException ite) {
            throw new IllegalStateException(ite);
        } catch (IllegalAccessException iae) {
            throw new IllegalStateException(iae);
        } catch (InvalidProtocolBufferNanoException ipbne) {
            throw new IllegalStateException(ipbne);
        }
    }

    public static final boolean messageNanoEquals(MessageNano a, MessageNano b) {
        int serializedSize;
        if (a == b) {
            return true;
        }
        if (a == null || b == null || a.getClass() != b.getClass() || b.getSerializedSize() != (serializedSize = a.getSerializedSize())) {
            return false;
        }
        byte[] aByteArray = new byte[serializedSize];
        byte[] bByteArray = new byte[serializedSize];
        toByteArray(a, aByteArray, 0, serializedSize);
        toByteArray(b, bByteArray, 0, serializedSize);
        return Arrays.equals(aByteArray, bByteArray);
    }

    public String toString() {
        return MessageNanoPrinter.print(this);
    }

    public MessageNano clone() throws CloneNotSupportedException {
        return (MessageNano) super.clone();
    }
}
