package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzx;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@KeepName
@Hide
public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
    static final ThreadLocal<Boolean> zzc = new zzt();
    @KeepName
    private zzb mResultGuardian;
    private final Object zza;
    @Hide
    private final zza<R> zzb;
    private final WeakReference<GoogleApiClient> zzd;
    private final CountDownLatch zze;
    private final ArrayList<PendingResult.zza> zzf;
    private ResultCallback<? super R> zzg;
    private final AtomicReference<zzdu> zzh;
    /* access modifiers changed from: private */
    public R zzi;
    private Status zzj;
    private volatile boolean zzk;
    private boolean zzl;
    private boolean zzm;
    private zzx zzn;
    private Integer zzo;
    private volatile zzdo<R> zzp;
    private boolean zzq;

    final class zzb {
        private zzb() {
        }

        /* access modifiers changed from: protected */
        public final void finalize() throws Throwable {
            BasePendingResult.zzb(BasePendingResult.this.zzi);
            super.finalize();
        }

        /* synthetic */ zzb(BasePendingResult basePendingResult, zzt zzt) {
            this();
        }
    }

    @Deprecated
    BasePendingResult() {
        this.zza = new Object();
        this.zze = new CountDownLatch(1);
        this.zzf = new ArrayList<>();
        this.zzh = new AtomicReference<>();
        this.zzq = false;
        this.zzb = new zza<>(Looper.getMainLooper());
        this.zzd = new WeakReference<>(null);
    }

    /* access modifiers changed from: protected */
    @Hide
    @NonNull
    public abstract R zza(Status status);

    @Hide
    public static class zza<R extends Result> extends Handler {
        public zza() {
            this(Looper.getMainLooper());
        }

        public zza(Looper looper) {
            super(looper);
        }

        public final void zza(ResultCallback<? super R> resultCallback, R r) {
            sendMessage(obtainMessage(1, new Pair(resultCallback, r)));
        }

        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Pair pair = (Pair) message.obj;
                ResultCallback resultCallback = (ResultCallback) pair.first;
                Result result = (Result) pair.second;
                try {
                    resultCallback.onResult(result);
                } catch (RuntimeException e) {
                    BasePendingResult.zzb(result);
                    throw e;
                }
            } else if (i != 2) {
                int i2 = message.what;
                StringBuilder sb = new StringBuilder(45);
                sb.append("Don't know how to handle message: ");
                sb.append(i2);
                Log.wtf("BasePendingResult", sb.toString(), new Exception());
            } else {
                ((BasePendingResult) message.obj).zzd(Status.zzd);
            }
        }
    }

    protected BasePendingResult(GoogleApiClient googleApiClient) {
        this.zza = new Object();
        this.zze = new CountDownLatch(1);
        this.zzf = new ArrayList<>();
        this.zzh = new AtomicReference<>();
        this.zzq = false;
        this.zzb = new zza<>(googleApiClient != null ? googleApiClient.zzc() : Looper.getMainLooper());
        this.zzd = new WeakReference<>(googleApiClient);
    }

    @Deprecated
    protected BasePendingResult(Looper looper) {
        this.zza = new Object();
        this.zze = new CountDownLatch(1);
        this.zzf = new ArrayList<>();
        this.zzh = new AtomicReference<>();
        this.zzq = false;
        this.zzb = new zza<>(looper);
        this.zzd = new WeakReference<>(null);
    }

    @Hide
    public final boolean zze() {
        return this.zze.getCount() == 0;
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
    public final R await() {
        zzau.zzc("await must not be called on the UI thread");
        boolean z = true;
        zzau.zza(!this.zzk, (Object) "Result has already been consumed");
        if (this.zzp != null) {
            z = false;
        }
        zzau.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            this.zze.await();
        } catch (InterruptedException e) {
            zzd(Status.zzb);
        }
        zzau.zza(zze(), (Object) "Result is not ready.");
        return zza();
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
    public final R await(long j, TimeUnit timeUnit) {
        if (j > 0) {
            zzau.zzc("await must not be called on the UI thread when time is greater than zero.");
        }
        boolean z = true;
        zzau.zza(!this.zzk, (Object) "Result has already been consumed.");
        if (this.zzp != null) {
            z = false;
        }
        zzau.zza(z, (Object) "Cannot await if then() has been called.");
        try {
            if (!this.zze.await(j, timeUnit)) {
                zzd(Status.zzd);
            }
        } catch (InterruptedException e) {
            zzd(Status.zzb);
        }
        zzau.zza(zze(), (Object) "Result is not ready.");
        return zza();
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
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x003e, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r6) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.zza
            monitor-enter(r0)
            if (r6 != 0) goto L_0x000a
            r6 = 0
            r5.zzg = r6     // Catch:{ all -> 0x003f }
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return
        L_0x000a:
            boolean r1 = r5.zzk     // Catch:{ all -> 0x003f }
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0012
            r1 = 1
            goto L_0x0013
        L_0x0012:
            r1 = 0
        L_0x0013:
            java.lang.String r4 = "Result has already been consumed."
            com.google.android.gms.common.internal.zzau.zza(r1, r4)     // Catch:{ all -> 0x003f }
            com.google.android.gms.common.api.internal.zzdo<R> r1 = r5.zzp     // Catch:{ all -> 0x003f }
            if (r1 != 0) goto L_0x001d
            goto L_0x001e
        L_0x001d:
            r2 = 0
        L_0x001e:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.zzau.zza(r2, r1)     // Catch:{ all -> 0x003f }
            boolean r1 = r5.isCanceled()     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x002b
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return
        L_0x002b:
            boolean r1 = r5.zze()     // Catch:{ all -> 0x003f }
            if (r1 == 0) goto L_0x003b
            com.google.android.gms.common.api.internal.BasePendingResult$zza<R> r1 = r5.zzb     // Catch:{ all -> 0x003f }
            com.google.android.gms.common.api.Result r2 = r5.zza()     // Catch:{ all -> 0x003f }
            r1.zza(r6, r2)     // Catch:{ all -> 0x003f }
            goto L_0x003d
        L_0x003b:
            r5.zzg = r6     // Catch:{ all -> 0x003f }
        L_0x003d:
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            return
        L_0x003f:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003f }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
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
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x004c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(com.google.android.gms.common.api.ResultCallback<? super R> r6, long r7, java.util.concurrent.TimeUnit r9) {
        /*
            r5 = this;
            java.lang.Object r0 = r5.zza
            monitor-enter(r0)
            if (r6 != 0) goto L_0x000a
            r6 = 0
            r5.zzg = r6     // Catch:{ all -> 0x004d }
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return
        L_0x000a:
            boolean r1 = r5.zzk     // Catch:{ all -> 0x004d }
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0012
            r1 = 1
            goto L_0x0013
        L_0x0012:
            r1 = 0
        L_0x0013:
            java.lang.String r4 = "Result has already been consumed."
            com.google.android.gms.common.internal.zzau.zza(r1, r4)     // Catch:{ all -> 0x004d }
            com.google.android.gms.common.api.internal.zzdo<R> r1 = r5.zzp     // Catch:{ all -> 0x004d }
            if (r1 != 0) goto L_0x001d
            goto L_0x001e
        L_0x001d:
            r2 = 0
        L_0x001e:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.zzau.zza(r2, r1)     // Catch:{ all -> 0x004d }
            boolean r1 = r5.isCanceled()     // Catch:{ all -> 0x004d }
            if (r1 == 0) goto L_0x002b
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return
        L_0x002b:
            boolean r1 = r5.zze()     // Catch:{ all -> 0x004d }
            if (r1 == 0) goto L_0x003b
            com.google.android.gms.common.api.internal.BasePendingResult$zza<R> r7 = r5.zzb     // Catch:{ all -> 0x004d }
            com.google.android.gms.common.api.Result r8 = r5.zza()     // Catch:{ all -> 0x004d }
            r7.zza(r6, r8)     // Catch:{ all -> 0x004d }
            goto L_0x004b
        L_0x003b:
            r5.zzg = r6     // Catch:{ all -> 0x004d }
            com.google.android.gms.common.api.internal.BasePendingResult$zza<R> r6 = r5.zzb     // Catch:{ all -> 0x004d }
            long r7 = r9.toMillis(r7)     // Catch:{ all -> 0x004d }
            r9 = 2
            android.os.Message r9 = r6.obtainMessage(r9, r5)     // Catch:{ all -> 0x004d }
            r6.sendMessageDelayed(r9, r7)     // Catch:{ all -> 0x004d }
        L_0x004b:
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            return
        L_0x004d:
            r6 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x004d }
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    @Hide
    public final void zza(PendingResult.zza zza2) {
        zzau.zzb(zza2 != null, "Callback cannot be null.");
        synchronized (this.zza) {
            if (zze()) {
                zza2.zza(this.zzj);
            } else {
                this.zzf.add(zza2);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.zza
            monitor-enter(r0)
            boolean r1 = r2.zzl     // Catch:{ all -> 0x002c }
            if (r1 != 0) goto L_0x002a
            boolean r1 = r2.zzk     // Catch:{ all -> 0x002c }
            if (r1 == 0) goto L_0x000c
            goto L_0x002a
        L_0x000c:
            com.google.android.gms.common.internal.zzx r1 = r2.zzn     // Catch:{ all -> 0x002c }
            if (r1 == 0) goto L_0x0017
            com.google.android.gms.common.internal.zzx r1 = r2.zzn     // Catch:{ RemoteException -> 0x0016 }
            r1.zza()     // Catch:{ RemoteException -> 0x0016 }
            goto L_0x0017
        L_0x0016:
            r1 = move-exception
        L_0x0017:
            R r1 = r2.zzi     // Catch:{ all -> 0x002c }
            zzb(r1)     // Catch:{ all -> 0x002c }
            r1 = 1
            r2.zzl = r1     // Catch:{ all -> 0x002c }
            com.google.android.gms.common.api.Status r1 = com.google.android.gms.common.api.Status.zze     // Catch:{ all -> 0x002c }
            com.google.android.gms.common.api.Result r1 = r2.zza(r1)     // Catch:{ all -> 0x002c }
            r2.zzc(r1)     // Catch:{ all -> 0x002c }
            monitor-exit(r0)     // Catch:{ all -> 0x002c }
            return
        L_0x002a:
            monitor-exit(r0)     // Catch:{ all -> 0x002c }
            return
        L_0x002c:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002c }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.cancel():void");
    }

    @Hide
    public final boolean zzf() {
        boolean isCanceled;
        synchronized (this.zza) {
            if (this.zzd.get() == null || !this.zzq) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.zza) {
            z = this.zzl;
        }
        return z;
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
    @Hide
    public <S extends Result> TransformedResult<S> then(ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult<S> then;
        zzau.zza(!this.zzk, (Object) "Result has already been consumed.");
        synchronized (this.zza) {
            boolean z = false;
            zzau.zza(this.zzp == null, (Object) "Cannot call then() twice.");
            zzau.zza(this.zzg == null, (Object) "Cannot call then() if callbacks are set.");
            if (!this.zzl) {
                z = true;
            }
            zzau.zza(z, (Object) "Cannot call then() if result was canceled.");
            this.zzq = true;
            this.zzp = new zzdo<>(this.zzd);
            then = this.zzp.then(resultTransform);
            if (zze()) {
                this.zzb.zza(this.zzp, zza());
            } else {
                this.zzg = this.zzp;
            }
        }
        return then;
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
    @Hide
    public final void zza(Result result) {
        synchronized (this.zza) {
            if (this.zzm || this.zzl) {
                zzb(result);
                return;
            }
            boolean zze2 = zze();
            boolean z = true;
            zzau.zza(!zze(), (Object) "Results have already been set");
            if (this.zzk) {
                z = false;
            }
            zzau.zza(z, (Object) "Result has already been consumed");
            zzc(result);
        }
    }

    @Hide
    public final void zzd(Status status) {
        synchronized (this.zza) {
            if (!zze()) {
                zza(zza(status));
                this.zzm = true;
            }
        }
    }

    @Hide
    public final void zza(zzdu zzdu) {
        this.zzh.set(zzdu);
    }

    @Hide
    public final Integer zzb() {
        return this.zzo;
    }

    @Hide
    public final void zzb(int i) {
        zzau.zzb(this.zzo == null, "PendingResult should only be stored once.");
        this.zzo = Integer.valueOf(i);
    }

    /* access modifiers changed from: protected */
    @Hide
    public final void zza(zzx zzx) {
        synchronized (this.zza) {
            this.zzn = zzx;
        }
    }

    @Hide
    public final void zzg() {
        this.zzq = this.zzq || zzc.get().booleanValue();
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
    private final R zza() {
        R r;
        synchronized (this.zza) {
            zzau.zza(!this.zzk, (Object) "Result has already been consumed.");
            zzau.zza(zze(), (Object) "Result is not ready.");
            r = this.zzi;
            this.zzi = null;
            this.zzg = null;
            this.zzk = true;
        }
        zzdu andSet = this.zzh.getAndSet(null);
        if (andSet != null) {
            andSet.zza(this);
        }
        return r;
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
    @Hide
    public void store(ResultStore resultStore, int i) {
        zzau.zza(resultStore, "ResultStore must not be null.");
        synchronized (this.zza) {
            zzau.zza(!this.zzk, (Object) "Result has already been consumed.");
            resultStore.zza(i, this);
        }
    }

    private final void zzc(Result result) {
        this.zzi = result;
        this.zzn = null;
        this.zze.countDown();
        this.zzj = this.zzi.getStatus();
        if (this.zzl) {
            this.zzg = null;
        } else if (this.zzg != null) {
            this.zzb.removeMessages(2);
            this.zzb.zza(this.zzg, zza());
        } else if (this.zzi instanceof Releasable) {
            this.mResultGuardian = new zzb(this, null);
        }
        ArrayList arrayList = this.zzf;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PendingResult.zza) obj).zza(this.zzj);
        }
        this.zzf.clear();
    }

    @Hide
    public static void zzb(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(valueOf);
                Log.w("BasePendingResult", sb.toString(), e);
            }
        }
    }
}
