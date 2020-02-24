package com.google.android.libraries.phenotype.client;

import android.os.Binder;
import com.google.android.libraries.phenotype.client.FlagLoader;

public abstract /* synthetic */ class FlagLoader$$CC {
    /* JADX INFO: Multiple debug info for r0v1 V: [D('value' V), D('e' java.lang.SecurityException)] */
    public static <V> V executeBinderAware$$STATIC$$(FlagLoader.BinderAwareFunction<V> function) {
        long bid;
        try {
            return function.execute();
        } catch (SecurityException e) {
            bid = Binder.clearCallingIdentity();
            SecurityException value = function.execute();
            Binder.restoreCallingIdentity(bid);
            return value;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(bid);
            throw th;
        }
    }
}
