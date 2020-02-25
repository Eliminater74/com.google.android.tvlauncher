package com.google.android.libraries.performance.primes.flags;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.google.android.gsf.Gservices;
import com.google.android.libraries.performance.primes.PrimesExecutors;
import com.google.android.libraries.performance.primes.PrimesLog;
import com.google.android.libraries.performance.primes.Shutdown;
import com.google.android.libraries.performance.primes.Supplier;
import com.google.android.libraries.phenotype.client.PhenotypeFlag;

import java.util.concurrent.ScheduledExecutorService;

public final class PrimesShutdown {
    private static final String TAG = "PrimesShutdown";

    private PrimesShutdown() {
    }

    public static Shutdown createInstance() {
        return new GservicesDefaultShutdown();
    }

    private static final class GservicesDefaultShutdown extends Shutdown {
        private GservicesDefaultShutdown() {
        }

        public void init(Context context, Supplier<SharedPreferences> supplier, Supplier<ScheduledExecutorService> executorServiceSupplier) {
            Supplier<Boolean> shutdownFlag = new GservicesShutdownFlag(context);
            updateShutdownFlag(shutdownFlag);
            if (!isShutdown()) {
                context.registerReceiver(new GServicesBroadcastReceiver(this, shutdownFlag, executorServiceSupplier), new IntentFilter(Gservices.CHANGED_ACTION));
            }
        }
    }

    static final class GservicesShutdownFlag implements Supplier<Boolean> {
        private static final PhenotypeFlag<Boolean> PRIMES_SHUTDOWN_FLAG = new PhenotypeFlag.Factory(ServiceFlags.SHARED_PREFS_FILE).withPhenotypePrefix("ShutdownFeature__").withGservicePrefix("primes::").disableBypassPhenotypeForDebug().preferGservices().createFlag("shutdown_primes", false);
        private final Context context;

        GservicesShutdownFlag(Context context2) {
            this.context = context2;
        }

        public Boolean get() {
            PhenotypeFlag.maybeInit(this.context);
            return PRIMES_SHUTDOWN_FLAG.get();
        }
    }

    private static final class GServicesBroadcastReceiver extends BroadcastReceiver {
        /* access modifiers changed from: private */
        public final Shutdown shutdown;
        /* access modifiers changed from: private */
        public final Supplier<Boolean> shutdownFlag;
        private final Supplier<ScheduledExecutorService> executorServiceSupplier;

        GServicesBroadcastReceiver(Shutdown shutdown2, Supplier<Boolean> shutdownFlag2, Supplier<ScheduledExecutorService> executorServiceSupplier2) {
            this.shutdown = shutdown2;
            this.shutdownFlag = shutdownFlag2;
            this.executorServiceSupplier = executorServiceSupplier2;
        }

        public void onReceive(Context context, Intent intent) {
            Supplier<ScheduledExecutorService> supplier;
            ScheduledExecutorService executorService;
            PrimesLog.m46d(PrimesShutdown.TAG, "BroadcastReceiver: action = %s", intent.getAction());
            if (this.shutdown.isShutdown()) {
                context.unregisterReceiver(this);
            } else if (Gservices.CHANGED_ACTION.equals(intent.getAction()) && (supplier = this.executorServiceSupplier) != null && (executorService = supplier.get()) != null) {
                PrimesExecutors.handleFuture(executorService.submit(new Runnable() {
                    public void run() {
                        GServicesBroadcastReceiver.this.shutdown.updateShutdownFlag(GServicesBroadcastReceiver.this.shutdownFlag);
                    }
                }));
            }
        }
    }
}
