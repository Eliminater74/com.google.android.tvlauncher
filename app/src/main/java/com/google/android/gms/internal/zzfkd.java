package com.google.android.gms.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: Throwables */
public final class zzfkd {
    private static final Object zza;
    private static final Method zzb;
    private static final Method zzc;

    public static void zza(Throwable th) {
        zzfkb.zza(th);
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        }
    }

    @Deprecated
    public static RuntimeException zzb(Throwable th) {
        zza(th);
        throw new RuntimeException(th);
    }

    private static Object zza() {
        try {
            return Class.forName("sun.misc.SharedSecrets", false, null).getMethod("getJavaLangAccess", new Class[0]).invoke(null, new Object[0]);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable th) {
            return null;
        }
    }

    private static Method zzb() {
        try {
            Method zza2 = zza("getStackTraceDepth", Throwable.class);
            if (zza2 == null) {
                return null;
            }
            zza2.invoke(zza(), new Throwable());
            return zza2;
        } catch (IllegalAccessException | UnsupportedOperationException | InvocationTargetException e) {
            return null;
        }
    }

    private static Method zza(String str, Class<?>... clsArr) throws ThreadDeath {
        try {
            return Class.forName("sun.misc.JavaLangAccess", false, null).getMethod(str, clsArr);
        } catch (ThreadDeath e) {
            throw e;
        } catch (Throwable th) {
            return null;
        }
    }

    static {
        Method method;
        Object zza2 = zza();
        zza = zza2;
        Method method2 = null;
        if (zza2 == null) {
            method = null;
        } else {
            method = zza("getStackTraceElement", Throwable.class, Integer.TYPE);
        }
        zzb = method;
        if (zza != null) {
            method2 = zzb();
        }
        zzc = method2;
    }
}
