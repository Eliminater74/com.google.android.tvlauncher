package com.google.android.gms.internal;

import com.google.devtools.build.android.desugar.runtime.ThrowableExtension;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;

/* compiled from: ThrowableExtension */
final class zzfrk extends zzfrh {
    private final zzfri zza = new zzfri();

    zzfrk() {
    }

    public final void zza(Throwable th, Throwable th2) {
        if (th2 == th) {
            throw new IllegalArgumentException("Self suppression is not allowed.", th2);
        } else if (th2 != null) {
            this.zza.zza(th, true).add(th2);
        } else {
            throw new NullPointerException("The suppressed exception cannot be null.");
        }
    }

    public final void zza(Throwable th) {
        ThrowableExtension.printStackTrace(th);
        List<Throwable> zza2 = this.zza.zza(th, false);
        if (zza2 != null) {
            synchronized (zza2) {
                for (Throwable printStackTrace : zza2) {
                    System.err.print("Suppressed: ");
                    ThrowableExtension.printStackTrace(printStackTrace);
                }
            }
        }
    }

    public final void zza(Throwable th, PrintStream printStream) {
        ThrowableExtension.printStackTrace(th, printStream);
        List<Throwable> zza2 = this.zza.zza(th, false);
        if (zza2 != null) {
            synchronized (zza2) {
                for (Throwable printStackTrace : zza2) {
                    printStream.print("Suppressed: ");
                    ThrowableExtension.printStackTrace(printStackTrace, printStream);
                }
            }
        }
    }

    public final void zza(Throwable th, PrintWriter printWriter) {
        ThrowableExtension.printStackTrace(th, printWriter);
        List<Throwable> zza2 = this.zza.zza(th, false);
        if (zza2 != null) {
            synchronized (zza2) {
                for (Throwable printStackTrace : zza2) {
                    printWriter.print("Suppressed: ");
                    ThrowableExtension.printStackTrace(printStackTrace, printWriter);
                }
            }
        }
    }
}
