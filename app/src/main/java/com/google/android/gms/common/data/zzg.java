package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.Hide;

import java.util.ArrayList;

@Hide
/* compiled from: EntityBuffer */
public abstract class zzg<T> extends AbstractDataBuffer<T> {
    private boolean zzb = false;
    private ArrayList<Integer> zzc;

    protected zzg(DataHolder dataHolder) {
        super(dataHolder);
    }

    /* access modifiers changed from: protected */
    @Hide
    public abstract T zza(int i, int i2);

    /* access modifiers changed from: protected */
    @Hide
    public abstract String zzb();

    public final T get(int i) {
        int i2;
        zzc();
        int zza = zza(i);
        if (i < 0 || i == this.zzc.size()) {
            i2 = 0;
        } else {
            if (i == this.zzc.size() - 1) {
                i2 = this.zza.getCount() - this.zzc.get(i).intValue();
            } else {
                i2 = this.zzc.get(i + 1).intValue() - this.zzc.get(i).intValue();
            }
            if (i2 == 1) {
                this.zza.zza(zza(i));
            }
        }
        return zza(zza, i2);
    }

    public int getCount() {
        zzc();
        return this.zzc.size();
    }

    private final void zzc() {
        synchronized (this) {
            if (!this.zzb) {
                int count = this.zza.getCount();
                this.zzc = new ArrayList<>();
                if (count > 0) {
                    this.zzc.add(0);
                    String zzb2 = zzb();
                    String string = this.zza.getString(zzb2, 0, this.zza.zza(0));
                    int i = 1;
                    while (i < count) {
                        int zza = this.zza.zza(i);
                        String string2 = this.zza.getString(zzb2, i, zza);
                        if (string2 != null) {
                            if (!string2.equals(string)) {
                                this.zzc.add(Integer.valueOf(i));
                                string = string2;
                            }
                            i++;
                        } else {
                            StringBuilder sb = new StringBuilder(String.valueOf(zzb2).length() + 78);
                            sb.append("Missing value for markerColumn: ");
                            sb.append(zzb2);
                            sb.append(", at row: ");
                            sb.append(i);
                            sb.append(", for window: ");
                            sb.append(zza);
                            throw new NullPointerException(sb.toString());
                        }
                    }
                }
                this.zzb = true;
            }
        }
    }

    private final int zza(int i) {
        if (i >= 0 && i < this.zzc.size()) {
            return this.zzc.get(i).intValue();
        }
        StringBuilder sb = new StringBuilder(53);
        sb.append("Position ");
        sb.append(i);
        sb.append(" is out of bounds for this buffer");
        throw new IllegalArgumentException(sb.toString());
    }
}
