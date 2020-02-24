package com.google.android.gms.phenotype;

import java.util.Comparator;

/* compiled from: Flag */
final class zzj implements Comparator<Flag> {
    zzj() {
    }

    public final /* synthetic */ int compare(Object obj, Object obj2) {
        Flag flag = (Flag) obj;
        Flag flag2 = (Flag) obj2;
        if (flag.flagStorageType == flag2.flagStorageType) {
            return flag.name.compareTo(flag2.name);
        }
        return flag.flagStorageType - flag2.flagStorageType;
    }
}
