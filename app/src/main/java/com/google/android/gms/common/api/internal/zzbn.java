package com.google.android.gms.common.api.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.support.p001v4.util.ArraySet;
import android.util.Log;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.signin.zzd;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Hide
/* compiled from: GoogleApiManager */
public final class zzbn implements Handler.Callback {
    public static final Status zza = new Status(4, "Sign-out occurred while this API call was in progress.");
    /* access modifiers changed from: private */
    public static final Status zzb = new Status(4, "The user must be signed in to make this API call.");
    /* access modifiers changed from: private */
    public static final Object zzf = new Object();
    private static zzbn zzg;
    /* access modifiers changed from: private */
    public final Context zzh;
    /* access modifiers changed from: private */
    public final GoogleApiAvailability zzi;
    /* access modifiers changed from: private */
    public final zzv zzj;
    /* access modifiers changed from: private */
    public final Map<zzi<?>, zzbp<?>> zzm = new ConcurrentHashMap(5, 0.75f, 1);
    /* access modifiers changed from: private */
    public final Set<zzi<?>> zzo = new ArraySet();
    /* access modifiers changed from: private */
    public final Handler zzq;
    private final AtomicInteger zzk = new AtomicInteger(1);
    private final AtomicInteger zzl = new AtomicInteger(0);
    private final Set<zzi<?>> zzp = new ArraySet();
    /* access modifiers changed from: private */
    public long zzc = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
    /* access modifiers changed from: private */
    public long zzd = 120000;
    /* access modifiers changed from: private */
    public long zze = 10000;
    /* access modifiers changed from: private */
    public zzai zzn = null;

    private zzbn(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zzh = context;
        this.zzq = new Handler(looper, this);
        this.zzi = googleApiAvailability;
        this.zzj = new zzv(googleApiAvailability);
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(6));
    }

    public static zzbn zza(Context context) {
        zzbn zzbn;
        synchronized (zzf) {
            if (zzg == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zzg = new zzbn(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            zzbn = zzg;
        }
        return zzbn;
    }

    public static zzbn zza() {
        zzbn zzbn;
        synchronized (zzf) {
            zzau.zza(zzg, "Must guarantee manager is non-null before using getInstance");
            zzbn = zzg;
        }
        return zzbn;
    }

    public static void zzb() {
        synchronized (zzf) {
            if (zzg != null) {
                zzbn zzbn = zzg;
                zzbn.zzl.incrementAndGet();
                Handler handler = zzbn.zzq;
                handler.sendMessageAtFrontOfQueue(handler.obtainMessage(10));
            }
        }
    }

    public final int zzc() {
        return this.zzk.getAndIncrement();
    }

    public final void zza(GoogleApi<?> googleApi) {
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(7, googleApi));
    }

    @WorkerThread
    private final void zzb(GoogleApi<?> googleApi) {
        zzi<?> zzd2 = googleApi.zzd();
        zzbp zzbp = this.zzm.get(zzd2);
        if (zzbp == null) {
            zzbp = new zzbp(this, googleApi);
            this.zzm.put(zzd2, zzbp);
        }
        if (zzbp.zzk()) {
            this.zzp.add(zzd2);
        }
        zzbp.zzi();
    }

    public final void zza(@NonNull zzai zzai) {
        synchronized (zzf) {
            if (this.zzn != zzai) {
                this.zzn = zzai;
                this.zzo.clear();
            }
            this.zzo.addAll(zzai.zzf());
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzb(@NonNull zzai zzai) {
        synchronized (zzf) {
            if (this.zzn == zzai) {
                this.zzn = null;
                this.zzo.clear();
            }
        }
    }

    public final Task<Map<zzi<?>, String>> zza(Iterable<? extends GoogleApi<?>> iterable) {
        zzk zzk2 = new zzk(iterable);
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(2, zzk2));
        return zzk2.zzb();
    }

    public final void zzd() {
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(3));
    }

    /* access modifiers changed from: package-private */
    public final void zze() {
        this.zzl.incrementAndGet();
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(10));
    }

    public final <O extends Api.ApiOptions> void zza(GoogleApi googleApi, int i, zzn<? extends Result, Api.zzb> zzn2) {
        zzd zzd2 = new zzd(i, zzn2);
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(4, new zzcu(zzd2, this.zzl.get(), googleApi)));
    }

    public final <O extends Api.ApiOptions, TResult> void zza(GoogleApi<O> googleApi, int i, zzdl<Api.zzb, TResult> zzdl, TaskCompletionSource<TResult> taskCompletionSource, zzdg zzdg) {
        zzf zzf2 = new zzf(i, zzdl, taskCompletionSource, zzdg);
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(4, new zzcu(zzf2, this.zzl.get(), googleApi)));
    }

    public final <O extends Api.ApiOptions> Task<Void> zza(@NonNull GoogleApi googleApi, @NonNull zzcv<Api.zzb, ?> zzcv, @NonNull zzdv<Api.zzb, ?> zzdv) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zze zze2 = new zze(new zzcw(zzcv, zzdv), taskCompletionSource);
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(8, new zzcu(zze2, this.zzl.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends Api.ApiOptions> Task<Boolean> zza(@NonNull GoogleApi googleApi, @NonNull zzcl<?> zzcl) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        zzg zzg2 = new zzg(zzcl, taskCompletionSource);
        Handler handler = this.zzq;
        handler.sendMessage(handler.obtainMessage(13, new zzcu(zzg2, this.zzl.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    @WorkerThread
    public final boolean handleMessage(Message message) {
        zzbp zzbp;
        long j = 300000;
        switch (message.what) {
            case 1:
                if (((Boolean) message.obj).booleanValue()) {
                    j = 10000;
                }
                this.zze = j;
                this.zzq.removeMessages(12);
                for (zzi<?> obtainMessage : this.zzm.keySet()) {
                    Handler handler = this.zzq;
                    handler.sendMessageDelayed(handler.obtainMessage(12, obtainMessage), this.zze);
                }
                break;
            case 2:
                zzk zzk2 = (zzk) message.obj;
                Iterator<zzi<?>> it = zzk2.zza().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    } else {
                        zzi next = it.next();
                        zzbp zzbp2 = this.zzm.get(next);
                        if (zzbp2 == null) {
                            zzk2.zza(next, new ConnectionResult(13), null);
                            break;
                        } else if (zzbp2.zzj()) {
                            zzk2.zza(next, ConnectionResult.zza, zzbp2.zzb().zzab());
                        } else if (zzbp2.zze() != null) {
                            zzk2.zza(next, zzbp2.zze(), null);
                        } else {
                            zzbp2.zza(zzk2);
                        }
                    }
                }
            case 3:
                for (zzbp next2 : this.zzm.values()) {
                    next2.zzd();
                    next2.zzi();
                }
                break;
            case 4:
            case 8:
            case 13:
                zzcu zzcu = (zzcu) message.obj;
                zzbp zzbp3 = this.zzm.get(zzcu.zzc.zzd());
                if (zzbp3 == null) {
                    zzb(zzcu.zzc);
                    zzbp3 = this.zzm.get(zzcu.zzc.zzd());
                }
                if (zzbp3.zzk() && this.zzl.get() != zzcu.zzb) {
                    zzcu.zza.zza(zza);
                    zzbp3.zza();
                    break;
                } else {
                    zzbp3.zza(zzcu.zza);
                    break;
                }
            case 5:
                int i = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                Iterator<zzbp<?>> it2 = this.zzm.values().iterator();
                while (true) {
                    if (it2.hasNext()) {
                        zzbp = it2.next();
                        if (zzbp.zzl() == i) {
                        }
                    } else {
                        zzbp = null;
                    }
                }
                if (zzbp == null) {
                    StringBuilder sb = new StringBuilder(76);
                    sb.append("Could not find API instance ");
                    sb.append(i);
                    sb.append(" while trying to fail enqueued calls.");
                    Log.wtf("GoogleApiManager", sb.toString(), new Exception());
                    break;
                } else {
                    String errorString = this.zzi.getErrorString(connectionResult.getErrorCode());
                    String errorMessage = connectionResult.getErrorMessage();
                    StringBuilder sb2 = new StringBuilder(String.valueOf(errorString).length() + 69 + String.valueOf(errorMessage).length());
                    sb2.append("Error resolution was canceled by the user, original error message: ");
                    sb2.append(errorString);
                    sb2.append(": ");
                    sb2.append(errorMessage);
                    zzbp.zza(new Status(17, sb2.toString()));
                    break;
                }
            case 6:
                if (this.zzh.getApplicationContext() instanceof Application) {
                    zzl.zza((Application) this.zzh.getApplicationContext());
                    zzl.zza().zza(new zzbo(this));
                    if (!zzl.zza().zza(true)) {
                        this.zze = 300000;
                        break;
                    }
                }
                break;
            case 7:
                zzb((GoogleApi) message.obj);
                break;
            case 9:
                if (this.zzm.containsKey(message.obj)) {
                    this.zzm.get(message.obj).zzf();
                    break;
                }
                break;
            case 10:
                for (zzi<?> remove : this.zzp) {
                    this.zzm.remove(remove).zza();
                }
                this.zzp.clear();
                break;
            case 11:
                if (this.zzm.containsKey(message.obj)) {
                    this.zzm.get(message.obj).zzg();
                    break;
                }
                break;
            case 12:
                if (this.zzm.containsKey(message.obj)) {
                    this.zzm.get(message.obj).zzh();
                    break;
                }
                break;
            default:
                int i2 = message.what;
                StringBuilder sb3 = new StringBuilder(31);
                sb3.append("Unknown message id: ");
                sb3.append(i2);
                Log.w("GoogleApiManager", sb3.toString());
                return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public final PendingIntent zza(zzi<?> zzi2, int i) {
        zzd zzm2;
        zzbp zzbp = this.zzm.get(zzi2);
        if (zzbp == null || (zzm2 = zzbp.zzm()) == null) {
            return null;
        }
        return PendingIntent.getActivity(this.zzh, i, zzm2.getSignInIntent(), 134217728);
    }

    /* access modifiers changed from: package-private */
    public final boolean zza(ConnectionResult connectionResult, int i) {
        return this.zzi.zza(this.zzh, connectionResult, i);
    }

    public final void zzb(ConnectionResult connectionResult, int i) {
        if (!zza(connectionResult, i)) {
            Handler handler = this.zzq;
            handler.sendMessage(handler.obtainMessage(5, i, 0, connectionResult));
        }
    }
}
