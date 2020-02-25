package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;

import java.util.ArrayList;

@Hide
/* compiled from: SafeParcelableSerializer */
public final class zzbla {
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
    public static <T extends com.google.android.gms.internal.zzbkz> byte[] zza(T r2) {
        /*
            android.os.Parcel r0 = android.os.Parcel.obtain()
            r1 = 0
            r2.writeToParcel(r0, r1)
            byte[] r2 = r0.marshall()
            r0.recycle()
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbla.zza(com.google.android.gms.internal.zzbkz):byte[]");
    }

    public static <T extends zzbkz> T zza(byte[] bArr, Parcelable.Creator<T> creator) {
        zzau.zza(creator);
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        T t = (zzbkz) creator.createFromParcel(obtain);
        obtain.recycle();
        return t;
    }

    public static <T extends zzbkz> void zza(T t, Intent intent, String str) {
        intent.putExtra(str, zza(t));
    }

    public static <T extends zzbkz> T zza(Intent intent, String str, Parcelable.Creator<T> creator) {
        byte[] byteArrayExtra = intent.getByteArrayExtra(str);
        if (byteArrayExtra == null) {
            return null;
        }
        return zza(byteArrayExtra, creator);
    }

    public static <T extends zzbkz> void zza(Iterable<T> iterable, Intent intent, String str) {
        ArrayList arrayList = new ArrayList();
        for (T zza : iterable) {
            arrayList.add(zza(zza));
        }
        intent.putExtra(str, arrayList);
    }

    public static <T extends zzbkz> ArrayList<T> zzb(Intent intent, String str, Parcelable.Creator<T> creator) {
        ArrayList arrayList = (ArrayList) intent.getSerializableExtra(str);
        if (arrayList == null) {
            return null;
        }
        ArrayList<T> arrayList2 = new ArrayList<>(arrayList.size());
        ArrayList arrayList3 = arrayList;
        int size = arrayList3.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList3.get(i);
            i++;
            arrayList2.add(zza((byte[]) obj, creator));
        }
        return arrayList2;
    }
}
