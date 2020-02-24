package com.google.android.libraries.performance.primes;

import android.app.Activity;
import com.google.android.libraries.performance.primes.PrimesThreadsConfigurations;
import java.util.concurrent.atomic.AtomicReference;

public final class PrimesInitTrigger implements PrimesThreadsConfigurations.ActivityResumedCallback {
    private boolean initTriggered;
    private final AtomicReference<Runnable> primesInitTaskRef = new AtomicReference<>();

    public void onActivityResumed(Activity activity, Runnable initTask) {
        synchronized (this.primesInitTaskRef) {
            if (this.initTriggered) {
                initTask.run();
            } else {
                this.primesInitTaskRef.set(initTask);
            }
        }
    }

    public void trigger() {
        synchronized (this.primesInitTaskRef) {
            this.initTriggered = true;
            Runnable initTask = this.primesInitTaskRef.getAndSet(null);
            if (initTask != null) {
                initTask.run();
            }
        }
    }
}
