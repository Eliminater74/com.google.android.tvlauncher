package com.google.android.gms.internal;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;
import java.io.PrintStream;
import java.io.PrintWriter;

/* compiled from: ThrowableExtension */
final class zzfrl extends zzfrh {
    zzfrl() {
    }

    public final void zza(Throwable th, Throwable th2) {
        ThrowableExtension.addSuppressed(th, th2);
    }

    public final void zza(Throwable th) {
        ThrowableExtension.printStackTrace(th);
    }

    public final void zza(Throwable th, PrintStream printStream) {
        ThrowableExtension.printStackTrace(th, printStream);
    }

    public final void zza(Throwable th, PrintWriter printWriter) {
        ThrowableExtension.printStackTrace(th, printWriter);
    }
}
