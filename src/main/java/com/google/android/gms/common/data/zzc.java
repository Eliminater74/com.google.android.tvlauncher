package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.common.internal.zzau;
import java.util.Arrays;

@Hide
/* compiled from: DataBufferRef */
public class zzc {
    protected final DataHolder mDataHolder;
    protected int mDataRow;
    private int zza;

    public zzc(DataHolder dataHolder, int i) {
        this.mDataHolder = (DataHolder) zzau.zza(dataHolder);
        zza(i);
    }

    /* access modifiers changed from: protected */
    public void zza(int i) {
        zzau.zza(i >= 0 && i < this.mDataHolder.getCount());
        this.mDataRow = i;
        this.zza = this.mDataHolder.zza(this.mDataRow);
    }

    public boolean isDataValid() {
        return !this.mDataHolder.isClosed();
    }

    public final boolean zza(String str) {
        return this.mDataHolder.hasColumn(str);
    }

    /* access modifiers changed from: protected */
    public final long zzb(String str) {
        return this.mDataHolder.getLong(str, this.mDataRow, this.zza);
    }

    /* access modifiers changed from: protected */
    public final int zzc(String str) {
        return this.mDataHolder.getInteger(str, this.mDataRow, this.zza);
    }

    /* access modifiers changed from: protected */
    public final boolean zzd(String str) {
        return this.mDataHolder.getBoolean(str, this.mDataRow, this.zza);
    }

    /* access modifiers changed from: protected */
    public String getString(String str) {
        return this.mDataHolder.getString(str, this.mDataRow, this.zza);
    }

    /* access modifiers changed from: protected */
    public final float zze(String str) {
        return this.mDataHolder.getFloat(str, this.mDataRow, this.zza);
    }

    /* access modifiers changed from: protected */
    public final double zzf(String str) {
        return this.mDataHolder.getDouble(str, this.mDataRow, this.zza);
    }

    /* access modifiers changed from: protected */
    public final byte[] zzg(String str) {
        return this.mDataHolder.getByteArray(str, this.mDataRow, this.zza);
    }

    /* access modifiers changed from: protected */
    public final Uri zzh(String str) {
        return this.mDataHolder.parseUri(str, this.mDataRow, this.zza);
    }

    /* access modifiers changed from: protected */
    public final void zza(String str, CharArrayBuffer charArrayBuffer) {
        this.mDataHolder.copyToBuffer(str, this.mDataRow, this.zza, charArrayBuffer);
    }

    /* access modifiers changed from: protected */
    public final boolean zzi(String str) {
        return this.mDataHolder.hasNull(str, this.mDataRow, this.zza);
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mDataRow), Integer.valueOf(this.zza), this.mDataHolder});
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc zzc = (zzc) obj;
        if (!zzak.zza(Integer.valueOf(zzc.mDataRow), Integer.valueOf(this.mDataRow)) || !zzak.zza(Integer.valueOf(zzc.zza), Integer.valueOf(this.zza)) || zzc.mDataHolder != this.mDataHolder) {
            return false;
        }
        return true;
    }
}
