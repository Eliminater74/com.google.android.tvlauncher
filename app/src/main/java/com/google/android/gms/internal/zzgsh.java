package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/* compiled from: MessageNano */
public abstract class zzgsh {
    public static final int UNSET_ENUM_VALUE = Integer.MIN_VALUE;
    protected volatile int zzaz = -1;

    public static final byte[] toByteArray(zzgsh zzgsh) {
        byte[] bArr = new byte[zzgsh.getSerializedSize()];
        toByteArray(zzgsh, bArr, 0, bArr.length);
        return bArr;
    }

    public static final void toByteArray(zzgsh zzgsh, byte[] bArr, int i, int i2) {
        try {
            zzgrz zza = zzgrz.zza(bArr, i, i2);
            zzgsh.writeTo(zza);
            zza.zza();
        } catch (IOException e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final <T extends zzgsh> T mergeFrom(T t, byte[] bArr) throws zzgsg {
        return mergeFrom(t, bArr, 0, bArr.length);
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
    public static final <T extends com.google.android.gms.internal.zzgsh> T mergeFrom(T r0, byte[] r1, int r2, int r3) throws com.google.android.gms.internal.zzgsg {
        /*
            com.google.android.gms.internal.zzgry r1 = com.google.android.gms.internal.zzgry.zza(r1, r2, r3)     // Catch:{ zzgsg -> 0x0015, IOException -> 0x000c }
            r0.mergeFrom(r1)     // Catch:{ zzgsg -> 0x0015, IOException -> 0x000c }
            r2 = 0
            r1.zza(r2)     // Catch:{ zzgsg -> 0x0015, IOException -> 0x000c }
            return r0
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
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgsh.mergeFrom(com.google.android.gms.internal.zzgsh, byte[], int, int):com.google.android.gms.internal.zzgsh");
    }

    public static final <T extends zzgsh> T cloneUsingSerialization(T t) {
        try {
            return mergeFrom((zzgsh) t.getClass().getConstructor(new Class[0]).newInstance(new Object[0]), toByteArray(t));
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        } catch (InstantiationException e2) {
            throw new IllegalStateException(e2);
        } catch (InvocationTargetException e3) {
            throw new IllegalStateException(e3);
        } catch (IllegalAccessException e4) {
            throw new IllegalStateException(e4);
        } catch (zzgsg e5) {
            throw new IllegalStateException(e5);
        }
    }

    public static final boolean messageNanoEquals(zzgsh zzgsh, zzgsh zzgsh2) {
        int serializedSize;
        if (zzgsh == zzgsh2) {
            return true;
        }
        if (zzgsh == null || zzgsh2 == null || zzgsh.getClass() != zzgsh2.getClass() || zzgsh2.getSerializedSize() != (serializedSize = zzgsh.getSerializedSize())) {
            return false;
        }
        byte[] bArr = new byte[serializedSize];
        byte[] bArr2 = new byte[serializedSize];
        toByteArray(zzgsh, bArr, 0, serializedSize);
        toByteArray(zzgsh2, bArr2, 0, serializedSize);
        return Arrays.equals(bArr, bArr2);
    }

    public abstract zzgsh mergeFrom(zzgry zzgry) throws IOException;

    public int getCachedSize() {
        if (this.zzaz < 0) {
            getSerializedSize();
        }
        return this.zzaz;
    }

    public int getSerializedSize() {
        int computeSerializedSize = computeSerializedSize();
        this.zzaz = computeSerializedSize;
        return computeSerializedSize;
    }

    /* access modifiers changed from: protected */
    public int computeSerializedSize() {
        return 0;
    }

    public void writeTo(zzgrz zzgrz) throws IOException {
    }

    public String toString() {
        return zzgsi.zza(this);
    }

    public zzgsh clone() throws CloneNotSupportedException {
        return (zzgsh) super.clone();
    }
}
