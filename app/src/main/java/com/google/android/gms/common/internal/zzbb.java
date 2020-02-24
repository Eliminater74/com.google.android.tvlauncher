package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.view.View;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzp;
import com.google.android.gms.dynamic.zzq;

/* compiled from: SignInButtonCreator */
public final class zzbb extends zzp<zzah> {
    private static final zzbb zza = new zzbb();

    private zzbb() {
        super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
    }

    public static View zza(Context context, int i, int i2) throws zzq {
        return zza.zzb(context, i, i2);
    }

    private final View zzb(Context context, int i, int i2) throws zzq {
        try {
            zzaz zzaz = new zzaz(i, i2, null);
            return (View) zzn.zza(((zzah) zzb(context)).zza(zzn.zza(context), zzaz));
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("Could not get button with size ");
            sb.append(i);
            sb.append(" and color ");
            sb.append(i2);
            throw new zzq(sb.toString(), e);
        }
    }

    public final /* synthetic */ Object zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ISignInButtonCreator");
        if (queryLocalInterface instanceof zzah) {
            return (zzah) queryLocalInterface;
        }
        return new zzai(iBinder);
    }
}
