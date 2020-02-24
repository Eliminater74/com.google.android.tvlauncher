package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: BinderWrapper */
final class zzd implements Parcelable.Creator<BinderWrapper> {
    zzd() {
    }

    public final /* synthetic */ Object[] newArray(int i) {
        return new BinderWrapper[i];
    }

    public final /* synthetic */ Object createFromParcel(Parcel parcel) {
        return new BinderWrapper(parcel, null);
    }
}
