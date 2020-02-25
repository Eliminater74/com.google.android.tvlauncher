package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: AbstractMessageLite */
public abstract class zzgmp<MessageType extends zzgmp<MessageType, BuilderType>, BuilderType extends zzgmq<MessageType, BuilderType>> implements zzgpt {
    private static boolean zzb = false;
    protected int zza = 0;

    public final zzgnb zzj() {
        try {
            zzgng zzb2 = zzgnb.zzb(zza());
            zza(zzb2.zzb());
            return zzb2.zza();
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + String.valueOf("ByteString").length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("ByteString");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }

    public final byte[] zzk() {
        try {
            byte[] bArr = new byte[zza()];
            zzgnp zza2 = zzgnp.zza(bArr);
            zza(zza2);
            zza2.zzc();
            return bArr;
        } catch (IOException e) {
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder(String.valueOf(name).length() + 62 + String.valueOf("byte array").length());
            sb.append("Serializing ");
            sb.append(name);
            sb.append(" to a ");
            sb.append("byte array");
            sb.append(" threw an IOException (should never happen).");
            throw new RuntimeException(sb.toString(), e);
        }
    }
}
