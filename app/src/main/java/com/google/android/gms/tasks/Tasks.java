package com.google.android.gms.tasks;

import android.support.annotation.NonNull;

import com.google.android.gms.common.internal.zzau;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class Tasks {

    private Tasks() {
    }

    public static <TResult> Task<TResult> forResult(TResult tresult) {
        zzu zzu = new zzu();
        zzu.zza((Object) tresult);
        return zzu;
    }

    public static <TResult> Task<TResult> forException(@NonNull Exception exc) {
        zzu zzu = new zzu();
        zzu.zza(exc);
        return zzu;
    }

    public static <TResult> Task<TResult> forCanceled() {
        zzu zzu = new zzu();
        zzu.zza();
        return zzu;
    }

    public static <TResult> Task<TResult> call(@NonNull Callable<TResult> callable) {
        return call(TaskExecutors.MAIN_THREAD, callable);
    }

    public static <TResult> Task<TResult> call(@NonNull Executor executor, @NonNull Callable<TResult> callable) {
        zzau.zza(executor, "Executor must not be null");
        zzau.zza(callable, "Callback must not be null");
        zzu zzu = new zzu();
        executor.execute(new zzv(zzu, callable));
        return zzu;
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task) throws ExecutionException, InterruptedException {
        zzau.zzc("Must not be called on the main application thread");
        zzau.zza(task, "Task must not be null");
        if (task.isComplete()) {
            return zza(task);
        }
        zza zza2 = new zza(null);
        zza(task, zza2);
        zza2.zza();
        return zza(task);
    }

    public static <TResult> TResult await(@NonNull Task<TResult> task, long j, @NonNull TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        zzau.zzc("Must not be called on the main application thread");
        zzau.zza(task, "Task must not be null");
        zzau.zza(timeUnit, "TimeUnit must not be null");
        if (task.isComplete()) {
            return zza(task);
        }
        zza zza2 = new zza(null);
        zza(task, zza2);
        if (zza2.zza(j, timeUnit)) {
            return zza(task);
        }
        throw new TimeoutException("Timed out waiting for Task");
    }

    public static Task<Void> whenAll(Collection<? extends Task<?>> collection) {
        if (collection.isEmpty()) {
            return forResult(null);
        }
        for (Task task : collection) {
            if (task == null) {
                throw new NullPointerException("null tasks are not accepted");
            }
        }
        zzu zzu = new zzu();
        zzc zzc2 = new zzc(collection.size(), zzu);
        for (Task zza2 : collection) {
            zza(zza2, zzc2);
        }
        return zzu;
    }

    public static Task<Void> whenAll(Task<?>... taskArr) {
        if (taskArr.length == 0) {
            return forResult(null);
        }
        return whenAll(Arrays.asList(taskArr));
    }

    public static <TResult> Task<List<TResult>> whenAllSuccess(Collection<? extends Task<?>> collection) {
        return whenAll(collection).continueWith(new zzw(collection));
    }

    public static <TResult> Task<List<TResult>> whenAllSuccess(Task<?>... taskArr) {
        return whenAllSuccess(Arrays.asList(taskArr));
    }

    public static Task<List<Task<?>>> whenAllComplete(Collection<? extends Task<?>> collection) {
        return whenAll(collection).continueWithTask(new zzx(collection));
    }

    public static Task<List<Task<?>>> whenAllComplete(Task<?>... taskArr) {
        return whenAllComplete(Arrays.asList(taskArr));
    }

    private static <TResult> TResult zza(Task<TResult> task) throws ExecutionException {
        if (task.isSuccessful()) {
            return task.getResult();
        }
        if (task.isCanceled()) {
            throw new CancellationException("Task is already canceled");
        }
        throw new ExecutionException(task.getException());
    }

    private static void zza(Task<?> task, zzb zzb2) {
        task.addOnSuccessListener(TaskExecutors.zza, zzb2);
        task.addOnFailureListener(TaskExecutors.zza, zzb2);
        task.addOnCanceledListener(TaskExecutors.zza, zzb2);
    }

    interface zzb extends OnCanceledListener, OnFailureListener, OnSuccessListener<Object> {
    }

    static final class zza implements zzb {
        private final CountDownLatch zza;

        private zza() {
            this.zza = new CountDownLatch(1);
        }

        /* synthetic */ zza(zzv zzv) {
            this();
        }

        public final void onSuccess(Object obj) {
            this.zza.countDown();
        }

        public final void onFailure(@NonNull Exception exc) {
            this.zza.countDown();
        }

        public final void onCanceled() {
            this.zza.countDown();
        }

        public final void zza() throws InterruptedException {
            this.zza.await();
        }

        public final boolean zza(long j, TimeUnit timeUnit) throws InterruptedException {
            return this.zza.await(j, timeUnit);
        }
    }

    static final class zzc implements zzb {
        private final Object zza = new Object();
        private final int zzb;
        private final zzu<Void> zzc;
        private int zzd;
        private int zze;
        private int zzf;
        private Exception zzg;
        private boolean zzh;

        public zzc(int i, zzu<Void> zzu) {
            this.zzb = i;
            this.zzc = zzu;
        }

        public final void onFailure(@NonNull Exception exc) {
            synchronized (this.zza) {
                this.zze++;
                this.zzg = exc;
                zza();
            }
        }

        public final void onSuccess(Object obj) {
            synchronized (this.zza) {
                this.zzd++;
                zza();
            }
        }

        public final void onCanceled() {
            synchronized (this.zza) {
                this.zzf++;
                this.zzh = true;
                zza();
            }
        }

        private final void zza() {
            int i = this.zzd;
            int i2 = this.zze;
            int i3 = i + i2 + this.zzf;
            int i4 = this.zzb;
            if (i3 != i4) {
                return;
            }
            if (this.zzg != null) {
                zzu<Void> zzu = this.zzc;
                StringBuilder sb = new StringBuilder(54);
                sb.append(i2);
                sb.append(" out of ");
                sb.append(i4);
                sb.append(" underlying tasks failed");
                zzu.zza((Exception) new ExecutionException(sb.toString(), this.zzg));
            } else if (this.zzh) {
                this.zzc.zza();
            } else {
                this.zzc.zza((Object) null);
            }
        }
    }
}
