package com.google.android.gms.feedback;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.util.Log;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.internal.zzbkv;
import com.google.android.gms.internal.zzbky;

public class ThemeSettings extends zzbkv {
    public static final Parcelable.Creator<ThemeSettings> CREATOR = new zzk();
    public static final int DEFAULT_PRIMARY_COLOR = 0;
    public static final int LIGHT_THEME = 0;
    public static final int LIGHT_WITH_DARK_ACTION_BAR_THEME = 1;
    private int zza;
    private int zzb;

    ThemeSettings(int i, int i2) {
        this.zza = i;
        this.zzb = i2;
    }

    public ThemeSettings() {
        this(0, 0);
    }

    public static int getThemeColor(Context context, String str) {
        return zza(context, str, context.obtainStyledAttributes((AttributeSet) null, new int[]{context.getResources().getIdentifier(str, "attr", context.getPackageName())}));
    }

    @VisibleForTesting
    private static int zza(Context context, String str, TypedArray typedArray) {
        try {
            return typedArray.getColor(0, 0);
        } catch (Exception e) {
            String valueOf = String.valueOf(context);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 48 + String.valueOf(str).length());
            sb.append("Could not get theme color for [context: ");
            sb.append(valueOf);
            sb.append(", attr: ");
            sb.append(str);
            Log.w("ThemeSettings", sb.toString());
            return 0;
        } finally {
            typedArray.recycle();
        }
    }

    public static int getThemePrimaryColor(Context context) {
        if (zzp.zzg()) {
            return getThemeColor(context, "android:colorPrimary");
        }
        return getThemeColor(context, "colorPrimary");
    }

    public ThemeSettings setTheme(int i) {
        this.zza = i;
        return this;
    }

    public ThemeSettings setPrimaryColor(int i) {
        this.zzb = i;
        return this;
    }

    @Hide
    public void writeToParcel(Parcel parcel, int i) {
        int zza2 = zzbky.zza(parcel);
        zzbky.zza(parcel, 2, this.zza);
        zzbky.zza(parcel, 3, this.zzb);
        zzbky.zza(parcel, zza2);
    }
}
