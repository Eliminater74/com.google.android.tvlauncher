package com.google.android.gms.tasks;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.internal.LifecycleCallback;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.internal.zzau;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;

/* compiled from: TaskImpl */
final class zzu<TResult> extends Task<TResult> {
    private final Object zza = new Object();
    private final zzr<TResult> zzb = new zzr<>();
    private boolean zzc;
    private volatile boolean zzd;
    private TResult zze;
    private Exception zzf;

    zzu() {
    }

    public final boolean isComplete() {
        boolean z;
        synchronized (this.zza) {
            z = this.zzc;
        }
        return z;
    }

    /* compiled from: TaskImpl */
    static class zza extends LifecycleCallback {
        private final List<WeakReference<zzq<?>>> zza = new ArrayList();

        public static zza zza(Activity activity) {
            zzcf zzb = zzb(activity);
            zza zza2 = (zza) zzb.zza("TaskOnStopCallback", zza.class);
            if (zza2 == null) {
                return new zza(zzb);
            }
            return zza2;
        }

        private zza(zzcf zzcf) {
            super(zzcf);
            this.zzd.zza("TaskOnStopCallback", this);
        }

        public final <T> void zza(zzq<T> zzq) {
            synchronized (this.zza) {
                this.zza.add(new WeakReference(zzq));
            }
        }

        @MainThread
        public final void zza() {
            synchronized (this.zza) {
                for (WeakReference<zzq<?>> weakReference : this.zza) {
                    zzq zzq = (zzq) weakReference.get();
                    if (zzq != null) {
                        zzq.zza();
                    }
                }
                this.zza.clear();
            }
        }
    }

    public final boolean isCanceled() {
        return this.zzd;
    }

    public final boolean isSuccessful() {
        boolean z;
        synchronized (this.zza) {
            z = this.zzc && !this.zzd && this.zzf == null;
        }
        return z;
    }

    public final TResult getResult() {
        TResult tresult;
        synchronized (this.zza) {
            zzb();
            zzd();
            if (this.zzf == null) {
                tresult = this.zze;
            } else {
                throw new RuntimeExecutionException(this.zzf);
            }
        }
        return tresult;
    }

    public final <X extends Throwable> TResult getResult(@NonNull Class<X> cls) throws Throwable {
        TResult tresult;
        synchronized (this.zza) {
            zzb();
            zzd();
            if (cls.isInstance(this.zzf)) {
                throw ((Throwable) cls.cast(this.zzf));
            } else if (this.zzf == null) {
                tresult = this.zze;
            } else {
                throw new RuntimeExecutionException(this.zzf);
            }
        }
        return tresult;
    }

    @Nullable
    public final Exception getException() {
        Exception exc;
        synchronized (this.zza) {
            exc = this.zzf;
        }
        return exc;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.tasks.Task.addOnSuccessListener(java.util.concurrent.Executor, com.google.android.gms.tasks.OnSuccessListener<? super ?>):com.google.android.gms.tasks.Task<TResult>
     arg types: [java.util.concurrent.Executor, com.google.android.gms.tasks.OnSuccessListener<? super TResult>]
     candidates:
      com.google.android.gms.tasks.zzu.addOnSuccessListener(android.app.Activity, com.google.android.gms.tasks.OnSuccessListener):com.google.android.gms.tasks.Task<TResult>
      com.google.android.gms.tasks.Task.addOnSuccessListener(android.app.Activity, com.google.android.gms.tasks.OnSuccessListener<? super ?>):com.google.android.gms.tasks.Task<TResult>
      com.google.android.gms.tasks.Task.addOnSuccessListener(java.util.concurrent.Executor, com.google.android.gms.tasks.OnSuccessListener<? super ?>):com.google.android.gms.tasks.Task<TResult> */
    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        return addOnSuccessListener(TaskExecutors.MAIN_THREAD, (OnSuccessListener<? super Object>) onSuccessListener);
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzb.zza(new zzm(executor, onSuccessListener));
        zze();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        zzm zzm = new zzm(TaskExecutors.MAIN_THREAD, onSuccessListener);
        this.zzb.zza(zzm);
        zza.zza(activity).zza(zzm);
        zze();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return addOnFailureListener(TaskExecutors.MAIN_THREAD, onFailureListener);
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzb.zza(new zzk(executor, onFailureListener));
        zze();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        zzk zzk = new zzk(TaskExecutors.MAIN_THREAD, onFailureListener);
        this.zzb.zza(zzk);
        zza.zza(activity).zza(zzk);
        zze();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull OnCompleteListener<TResult> onCompleteListener) {
        return addOnCompleteListener(TaskExecutors.MAIN_THREAD, onCompleteListener);
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzb.zza(new zzi(executor, onCompleteListener));
        zze();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCompleteListener(@NonNull Activity activity, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        zzi zzi = new zzi(TaskExecutors.MAIN_THREAD, onCompleteListener);
        this.zzb.zza(zzi);
        zza.zza(activity).zza(zzi);
        zze();
        return this;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Continuation<TResult, TContinuationResult> continuation) {
        return continueWith(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWith(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation) {
        zzu zzu = new zzu();
        this.zzb.zza(new zzc(executor, continuation, zzu));
        zze();
        return zzu;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        return continueWithTask(TaskExecutors.MAIN_THREAD, continuation);
    }

    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull OnCanceledListener onCanceledListener) {
        return addOnCanceledListener(TaskExecutors.MAIN_THREAD, onCanceledListener);
    }

    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull Executor executor, @NonNull OnCanceledListener onCanceledListener) {
        this.zzb.zza(new zzg(executor, onCanceledListener));
        zze();
        return this;
    }

    @NonNull
    public final Task<TResult> addOnCanceledListener(@NonNull Activity activity, @NonNull OnCanceledListener onCanceledListener) {
        zzg zzg = new zzg(TaskExecutors.MAIN_THREAD, onCanceledListener);
        this.zzb.zza(zzg);
        zza.zza(activity).zza(zzg);
        zze();
        return this;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> continueWithTask(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation) {
        zzu zzu = new zzu();
        this.zzb.zza(new zze(executor, continuation, zzu));
        zze();
        return zzu;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(Executor executor, SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        zzu zzu = new zzu();
        this.zzb.zza(new zzo(executor, successContinuation, zzu));
        zze();
        return zzu;
    }

    @NonNull
    public final <TContinuationResult> Task<TContinuationResult> onSuccessTask(@NonNull SuccessContinuation<TResult, TContinuationResult> successContinuation) {
        return onSuccessTask(TaskExecutors.MAIN_THREAD, successContinuation);
    }

    public final void zza(Object obj) {
        synchronized (this.zza) {
            zzc();
            this.zzc = true;
            this.zze = obj;
        }
        this.zzb.zza(this);
    }

    public final boolean zzb(Object obj) {
        synchronized (this.zza) {
            if (this.zzc) {
                return false;
            }
            this.zzc = true;
            this.zze = obj;
            this.zzb.zza(this);
            return true;
        }
    }

    public final void zza(@NonNull Exception exc) {
        zzau.zza(exc, "Exception must not be null");
        synchronized (this.zza) {
            zzc();
            this.zzc = true;
            this.zzf = exc;
        }
        this.zzb.zza(this);
    }

    public final boolean zzb(@NonNull Exception exc) {
        zzau.zza(exc, "Exception must not be null");
        synchronized (this.zza) {
            if (this.zzc) {
                return false;
            }
            this.zzc = true;
            this.zzf = exc;
            this.zzb.zza(this);
            return true;
        }
    }

    public final boolean zza() {
        synchronized (this.zza) {
            if (this.zzc) {
                return false;
            }
            this.zzc = true;
            this.zzd = true;
            this.zzb.zza(this);
            return true;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    private final void zzb() {
        zzau.zza(this.zzc, (Object) "Task is not yet complete");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    private final void zzc() {
        zzau.zza(!this.zzc, (Object) "Task is already complete");
    }

    private final void zzd() {
        if (this.zzd) {
            throw new CancellationException("Task is already canceled.");
        }
    }

    private final void zze() {
        synchronized (this.zza) {
            if (this.zzc) {
                this.zzb.zza(this);
            }
        }
    }
}
