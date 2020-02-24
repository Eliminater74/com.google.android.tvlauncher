package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.BinderThread;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.IGmsCallbacks;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseGmsClient<T extends IInterface> {
    public static final int CONNECT_STATE_CONNECTED = 4;
    public static final int CONNECT_STATE_DISCONNECTED = 1;
    public static final int CONNECT_STATE_DISCONNECTING = 5;
    public static final int CONNECT_STATE_LOCAL_CONNECTING = 3;
    public static final int CONNECT_STATE_REMOTE_CONNECTING = 2;
    public static final String FEATURE_GOOGLE_ME = "service_googleme";
    public static final String KEY_PENDING_INTENT = "pendingIntent";
    @Hide
    public static final String[] zzb = {"service_esmobile", FEATURE_GOOGLE_ME};
    protected ConnectionProgressReportCallbacks mConnectionProgressReportCallbacks;
    protected AtomicInteger mDisconnectCount;
    final Handler zza;
    private int zzc;
    private long zzd;
    private long zze;
    private int zzf;
    private long zzg;
    private zzu zzh;
    private final Context zzi;
    private final Looper zzj;
    private final GmsClientSupervisor zzk;
    private final GoogleApiAvailabilityLight zzl;
    private final Object zzm;
    /* access modifiers changed from: private */
    public final Object zzn;
    /* access modifiers changed from: private */
    public IGmsServiceBroker zzo;
    private T zzp;
    /* access modifiers changed from: private */
    public final ArrayList<zzc<?>> zzq;
    private zze zzr;
    private int zzs;
    /* access modifiers changed from: private */
    public final BaseConnectionCallbacks zzt;
    /* access modifiers changed from: private */
    public final BaseOnConnectionFailedListener zzu;
    private final int zzv;
    private final String zzw;
    /* access modifiers changed from: private */
    public ConnectionResult zzx;
    /* access modifiers changed from: private */
    public boolean zzy;

    public interface BaseConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface BaseOnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    @Hide
    public interface ConnectionProgressReportCallbacks {
        void onReportServiceBinding(@NonNull ConnectionResult connectionResult);
    }

    @Hide
    public interface SignOutCallbacks {
        void onSignOutComplete();
    }

    public class zzf implements ConnectionProgressReportCallbacks {
        public zzf() {
        }

        public final void onReportServiceBinding(@NonNull ConnectionResult connectionResult) {
            if (connectionResult.isSuccess()) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                baseGmsClient.getRemoteService(null, baseGmsClient.getScopes());
            } else if (BaseGmsClient.this.zzu != null) {
                BaseGmsClient.this.zzu.onConnectionFailed(connectionResult);
            }
        }
    }

    protected BaseGmsClient(Context context, Looper looper, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this(context, looper, GmsClientSupervisor.getInstance(context), GoogleApiAvailabilityLight.getInstance(), i, (BaseConnectionCallbacks) zzau.zza(baseConnectionCallbacks), (BaseOnConnectionFailedListener) zzau.zza(baseOnConnectionFailedListener), str);
    }

    /* access modifiers changed from: protected */
    @Hide
    @NonNull
    public abstract String getServiceDescriptor();

    /* access modifiers changed from: protected */
    @Hide
    @NonNull
    public abstract String getStartServiceAction();

    /* access modifiers changed from: protected */
    @Nullable
    @Hide
    public abstract T zza(IBinder iBinder);

    @Hide
    final class zzb extends Handler {
        public zzb(Looper looper) {
            super(looper);
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: android.app.PendingIntent} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r8) {
            /*
                r7 = this;
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                java.util.concurrent.atomic.AtomicInteger r0 = r0.mDisconnectCount
                int r0 = r0.get()
                int r1 = r8.arg1
                if (r0 == r1) goto L_0x0016
                boolean r0 = zzb(r8)
                if (r0 == 0) goto L_0x0015
                zza(r8)
            L_0x0015:
                return
            L_0x0016:
                int r0 = r8.what
                r1 = 4
                r2 = 1
                r3 = 5
                if (r0 == r2) goto L_0x002a
                int r0 = r8.what
                r4 = 7
                if (r0 == r4) goto L_0x002a
                int r0 = r8.what
                if (r0 == r1) goto L_0x002a
                int r0 = r8.what
                if (r0 != r3) goto L_0x0036
            L_0x002a:
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                boolean r0 = r0.isConnecting()
                if (r0 != 0) goto L_0x0036
                zza(r8)
                return
            L_0x0036:
                int r0 = r8.what
                r4 = 8
                r5 = 3
                r6 = 0
                if (r0 != r1) goto L_0x0081
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.ConnectionResult r1 = new com.google.android.gms.common.ConnectionResult
                int r8 = r8.arg2
                r1.<init>(r8)
                com.google.android.gms.common.ConnectionResult unused = r0.zzx = r1
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                boolean r8 = r8.zze()
                if (r8 == 0) goto L_0x0060
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                boolean r8 = r8.zzy
                if (r8 != 0) goto L_0x0060
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                r8.zzb(r5, null)
                return
            L_0x0060:
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.ConnectionResult r8 = r8.zzx
                if (r8 == 0) goto L_0x006f
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.ConnectionResult r8 = r8.zzx
                goto L_0x0074
            L_0x006f:
                com.google.android.gms.common.ConnectionResult r8 = new com.google.android.gms.common.ConnectionResult
                r8.<init>(r4)
            L_0x0074:
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.internal.BaseGmsClient$ConnectionProgressReportCallbacks r0 = r0.mConnectionProgressReportCallbacks
                r0.onReportServiceBinding(r8)
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                r0.onConnectionFailed(r8)
                return
            L_0x0081:
                int r0 = r8.what
                if (r0 != r3) goto L_0x00a6
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.ConnectionResult r8 = r8.zzx
                if (r8 == 0) goto L_0x0094
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.ConnectionResult r8 = r8.zzx
                goto L_0x0099
            L_0x0094:
                com.google.android.gms.common.ConnectionResult r8 = new com.google.android.gms.common.ConnectionResult
                r8.<init>(r4)
            L_0x0099:
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.internal.BaseGmsClient$ConnectionProgressReportCallbacks r0 = r0.mConnectionProgressReportCallbacks
                r0.onReportServiceBinding(r8)
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                r0.onConnectionFailed(r8)
                return
            L_0x00a6:
                int r0 = r8.what
                if (r0 != r5) goto L_0x00c9
                java.lang.Object r0 = r8.obj
                boolean r0 = r0 instanceof android.app.PendingIntent
                if (r0 == 0) goto L_0x00b5
                java.lang.Object r0 = r8.obj
                r6 = r0
                android.app.PendingIntent r6 = (android.app.PendingIntent) r6
            L_0x00b5:
                com.google.android.gms.common.ConnectionResult r0 = new com.google.android.gms.common.ConnectionResult
                int r8 = r8.arg2
                r0.<init>(r8, r6)
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.internal.BaseGmsClient$ConnectionProgressReportCallbacks r8 = r8.mConnectionProgressReportCallbacks
                r8.onReportServiceBinding(r0)
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                r8.onConnectionFailed(r0)
                return
            L_0x00c9:
                int r0 = r8.what
                r1 = 6
                if (r0 != r1) goto L_0x00f3
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                r0.zzb(r3, null)
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.internal.BaseGmsClient$BaseConnectionCallbacks r0 = r0.zzt
                if (r0 == 0) goto L_0x00e6
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                com.google.android.gms.common.internal.BaseGmsClient$BaseConnectionCallbacks r0 = r0.zzt
                int r1 = r8.arg2
                r0.onConnectionSuspended(r1)
            L_0x00e6:
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                int r8 = r8.arg2
                r0.onConnectionSuspended(r8)
                com.google.android.gms.common.internal.BaseGmsClient r8 = com.google.android.gms.common.internal.BaseGmsClient.this
                boolean unused = r8.zza(r3, r2, r6)
                return
            L_0x00f3:
                int r0 = r8.what
                r1 = 2
                if (r0 != r1) goto L_0x0104
                com.google.android.gms.common.internal.BaseGmsClient r0 = com.google.android.gms.common.internal.BaseGmsClient.this
                boolean r0 = r0.isConnected()
                if (r0 != 0) goto L_0x0104
                zza(r8)
                return
            L_0x0104:
                boolean r0 = zzb(r8)
                if (r0 == 0) goto L_0x0112
                java.lang.Object r8 = r8.obj
                com.google.android.gms.common.internal.BaseGmsClient$zzc r8 = (com.google.android.gms.common.internal.BaseGmsClient.zzc) r8
                r8.zzc()
                return
            L_0x0112:
                int r8 = r8.what
                r0 = 45
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>(r0)
                java.lang.String r0 = "Don't know how to handle message: "
                r1.append(r0)
                r1.append(r8)
                java.lang.String r8 = r1.toString()
                java.lang.Exception r0 = new java.lang.Exception
                r0.<init>()
                java.lang.String r1 = "GmsClient"
                android.util.Log.wtf(r1, r8, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.BaseGmsClient.zzb.handleMessage(android.os.Message):void");
        }

        private static void zza(Message message) {
            zzc zzc = (zzc) message.obj;
            zzc.zzb();
            zzc.zzd();
        }

        private static boolean zzb(Message message) {
            return message.what == 2 || message.what == 1 || message.what == 7;
        }
    }

    public final class zze implements ServiceConnection {
        private final int zza;

        public zze(int i) {
            this.zza = i;
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IGmsServiceBroker iGmsServiceBroker;
            if (iBinder == null) {
                BaseGmsClient.this.zzb(16);
                return;
            }
            synchronized (BaseGmsClient.this.zzn) {
                BaseGmsClient baseGmsClient = BaseGmsClient.this;
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
                if (queryLocalInterface == null || !(queryLocalInterface instanceof IGmsServiceBroker)) {
                    iGmsServiceBroker = new zzad(iBinder);
                } else {
                    iGmsServiceBroker = (IGmsServiceBroker) queryLocalInterface;
                }
                IGmsServiceBroker unused = baseGmsClient.zzo = iGmsServiceBroker;
            }
            BaseGmsClient.this.zza(0, (Bundle) null, this.zza);
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            synchronized (BaseGmsClient.this.zzn) {
                IGmsServiceBroker unused = BaseGmsClient.this.zzo = (IGmsServiceBroker) null;
            }
            BaseGmsClient.this.zza.sendMessage(BaseGmsClient.this.zza.obtainMessage(6, this.zza, 1));
        }
    }

    @Hide
    public final class zzh extends zza {
        @BinderThread
        public zzh(int i, @Nullable Bundle bundle) {
            super(i, null);
        }

        /* access modifiers changed from: protected */
        public final void zza(ConnectionResult connectionResult) {
            BaseGmsClient.this.mConnectionProgressReportCallbacks.onReportServiceBinding(connectionResult);
            BaseGmsClient.this.onConnectionFailed(connectionResult);
        }

        /* access modifiers changed from: protected */
        public final boolean zza() {
            BaseGmsClient.this.mConnectionProgressReportCallbacks.onReportServiceBinding(ConnectionResult.zza);
            return true;
        }
    }

    @Hide
    public abstract class zzc<TListener> {
        private TListener zza;
        private boolean zzb = false;

        public zzc(TListener tlistener) {
            this.zza = tlistener;
        }

        /* access modifiers changed from: protected */
        public abstract void zza(TListener tlistener);

        /* access modifiers changed from: protected */
        public abstract void zzb();

        public final void zzc() {
            TListener tlistener;
            synchronized (this) {
                tlistener = this.zza;
                if (this.zzb) {
                    String valueOf = String.valueOf(this);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47);
                    sb.append("Callback proxy ");
                    sb.append(valueOf);
                    sb.append(" being reused. This is not safe.");
                    Log.w("GmsClient", sb.toString());
                }
            }
            if (tlistener != null) {
                try {
                    zza(tlistener);
                } catch (RuntimeException e) {
                    zzb();
                    throw e;
                }
            } else {
                zzb();
            }
            synchronized (this) {
                this.zzb = true;
            }
            zzd();
        }

        public final void zzd() {
            zze();
            synchronized (BaseGmsClient.this.zzq) {
                BaseGmsClient.this.zzq.remove(this);
            }
        }

        public final void zze() {
            synchronized (this) {
                this.zza = null;
            }
        }
    }

    @Hide
    public static final class zzd extends IGmsCallbacks.zza {
        private BaseGmsClient zza;
        private final int zzb;

        public zzd(@NonNull BaseGmsClient baseGmsClient, int i) {
            this.zza = baseGmsClient;
            this.zzb = i;
        }

        @BinderThread
        public final void onAccountValidationComplete(int i, @Nullable Bundle bundle) {
            Log.wtf("GmsClient", "received deprecated onAccountValidationComplete callback, ignoring", new Exception());
        }

        @BinderThread
        public final void onPostInitComplete(int i, @NonNull IBinder iBinder, @Nullable Bundle bundle) {
            zzau.zza(this.zza, "onPostInitComplete can be called only once per call to getRemoteService");
            this.zza.onPostInitHandler(i, iBinder, bundle, this.zzb);
            this.zza = null;
        }
    }

    @Hide
    public final class zzg extends zza {
        private final IBinder zza;

        @BinderThread
        public zzg(int i, IBinder iBinder, Bundle bundle) {
            super(i, bundle);
            this.zza = iBinder;
        }

        /* access modifiers changed from: protected */
        public final void zza(ConnectionResult connectionResult) {
            if (BaseGmsClient.this.zzu != null) {
                BaseGmsClient.this.zzu.onConnectionFailed(connectionResult);
            }
            BaseGmsClient.this.onConnectionFailed(connectionResult);
        }

        /* access modifiers changed from: protected */
        public final boolean zza() {
            try {
                String interfaceDescriptor = this.zza.getInterfaceDescriptor();
                if (!BaseGmsClient.this.getServiceDescriptor().equals(interfaceDescriptor)) {
                    String serviceDescriptor = BaseGmsClient.this.getServiceDescriptor();
                    StringBuilder sb = new StringBuilder(String.valueOf(serviceDescriptor).length() + 34 + String.valueOf(interfaceDescriptor).length());
                    sb.append("service descriptor mismatch: ");
                    sb.append(serviceDescriptor);
                    sb.append(" vs. ");
                    sb.append(interfaceDescriptor);
                    Log.e("GmsClient", sb.toString());
                    return false;
                }
                IInterface zza2 = BaseGmsClient.this.zza(this.zza);
                if (zza2 == null || (!BaseGmsClient.this.zza(2, 4, zza2) && !BaseGmsClient.this.zza(3, 4, zza2))) {
                    return false;
                }
                ConnectionResult unused = BaseGmsClient.this.zzx = (ConnectionResult) null;
                Bundle connectionHint = BaseGmsClient.this.getConnectionHint();
                if (BaseGmsClient.this.zzt == null) {
                    return true;
                }
                BaseGmsClient.this.zzt.onConnected(connectionHint);
                return true;
            } catch (RemoteException e) {
                Log.w("GmsClient", "service probably died");
                return false;
            }
        }
    }

    @Hide
    abstract class zza extends zzc<Boolean> {
        private final int zza;
        private final Bundle zzb;

        @BinderThread
        protected zza(int i, Bundle bundle) {
            super(true);
            this.zza = i;
            this.zzb = bundle;
        }

        /* access modifiers changed from: protected */
        public abstract void zza(ConnectionResult connectionResult);

        /* access modifiers changed from: protected */
        public abstract boolean zza();

        /* access modifiers changed from: protected */
        public final void zzb() {
        }

        /* JADX WARN: Type inference failed for: r4v11, types: [android.os.Parcelable] */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final /* synthetic */ void zza(java.lang.Object r4) {
            /*
                r3 = this;
                java.lang.Boolean r4 = (java.lang.Boolean) r4
                r0 = 1
                r1 = 0
                if (r4 != 0) goto L_0x000c
                com.google.android.gms.common.internal.BaseGmsClient r4 = com.google.android.gms.common.internal.BaseGmsClient.this
                r4.zzb(r0, null)
                return
            L_0x000c:
                int r4 = r3.zza
                if (r4 == 0) goto L_0x0040
                r2 = 10
                if (r4 == r2) goto L_0x0033
                com.google.android.gms.common.internal.BaseGmsClient r4 = com.google.android.gms.common.internal.BaseGmsClient.this
                r4.zzb(r0, null)
                android.os.Bundle r4 = r3.zzb
                if (r4 == 0) goto L_0x0028
                java.lang.String r0 = "pendingIntent"
                android.os.Parcelable r4 = r4.getParcelable(r0)
                r1 = r4
                android.app.PendingIntent r1 = (android.app.PendingIntent) r1
            L_0x0028:
                com.google.android.gms.common.ConnectionResult r4 = new com.google.android.gms.common.ConnectionResult
                int r0 = r3.zza
                r4.<init>(r0, r1)
                r3.zza(r4)
                goto L_0x0056
            L_0x0033:
                com.google.android.gms.common.internal.BaseGmsClient r4 = com.google.android.gms.common.internal.BaseGmsClient.this
                r4.zzb(r0, null)
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r0 = "A fatal developer error has occurred. Check the logs for further information."
                r4.<init>(r0)
                throw r4
            L_0x0040:
                boolean r4 = r3.zza()
                if (r4 != 0) goto L_0x0056
                com.google.android.gms.common.internal.BaseGmsClient r4 = com.google.android.gms.common.internal.BaseGmsClient.this
                r4.zzb(r0, null)
                com.google.android.gms.common.ConnectionResult r4 = new com.google.android.gms.common.ConnectionResult
                r0 = 8
                r4.<init>(r0, r1)
                r3.zza(r4)
                return
            L_0x0056:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.BaseGmsClient.zza.zza(java.lang.Object):void");
        }
    }

    protected BaseGmsClient(Context context, Looper looper, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener, String str) {
        this.zzm = new Object();
        this.zzn = new Object();
        this.zzq = new ArrayList<>();
        this.zzs = 1;
        this.zzx = null;
        this.zzy = false;
        this.mDisconnectCount = new AtomicInteger(0);
        this.zzi = (Context) zzau.zza(context, "Context must not be null");
        this.zzj = (Looper) zzau.zza(looper, "Looper must not be null");
        this.zzk = (GmsClientSupervisor) zzau.zza(gmsClientSupervisor, "Supervisor must not be null");
        this.zzl = (GoogleApiAvailabilityLight) zzau.zza(googleApiAvailabilityLight, "API availability must not be null");
        this.zza = new zzb(looper);
        this.zzv = i;
        this.zzt = baseConnectionCallbacks;
        this.zzu = baseOnConnectionFailedListener;
        this.zzw = str;
    }

    protected BaseGmsClient(Context context, Handler handler, GmsClientSupervisor gmsClientSupervisor, GoogleApiAvailabilityLight googleApiAvailabilityLight, int i, BaseConnectionCallbacks baseConnectionCallbacks, BaseOnConnectionFailedListener baseOnConnectionFailedListener) {
        this.zzm = new Object();
        this.zzn = new Object();
        this.zzq = new ArrayList<>();
        this.zzs = 1;
        this.zzx = null;
        this.zzy = false;
        this.mDisconnectCount = new AtomicInteger(0);
        this.zzi = (Context) zzau.zza(context, "Context must not be null");
        this.zza = (Handler) zzau.zza(handler, "Handler must not be null");
        this.zzj = handler.getLooper();
        this.zzk = (GmsClientSupervisor) zzau.zza(gmsClientSupervisor, "Supervisor must not be null");
        this.zzl = (GoogleApiAvailabilityLight) zzau.zza(googleApiAvailabilityLight, "API availability must not be null");
        this.zzv = i;
        this.zzt = baseConnectionCallbacks;
        this.zzu = baseOnConnectionFailedListener;
        this.zzw = null;
    }

    /* access modifiers changed from: protected */
    @Hide
    public String zzac() {
        return "com.google.android.gms";
    }

    /* access modifiers changed from: protected */
    @Hide
    public int zzaa() {
        return 129;
    }

    @Nullable
    @Hide
    private final String zzc() {
        String str = this.zzw;
        return str == null ? this.zzi.getClass().getName() : str;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public String getLocalStartServiceAction() {
        return null;
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onConnectedLocked(@NonNull T t) {
        this.zze = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onConnectionSuspended(int i) {
        this.zzc = i;
        this.zzd = System.currentTimeMillis();
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzf = connectionResult.getErrorCode();
        this.zzg = System.currentTimeMillis();
    }

    /* access modifiers changed from: private */
    public final void zzb(int i, T t) {
        zzu zzu2;
        zzau.zzb((i == 4) == (t != null));
        synchronized (this.zzm) {
            this.zzs = i;
            this.zzp = t;
            zza(i, t);
            if (i != 1) {
                if (i == 2 || i == 3) {
                    if (!(this.zzr == null || this.zzh == null)) {
                        String zza2 = this.zzh.zza();
                        String zzb2 = this.zzh.zzb();
                        StringBuilder sb = new StringBuilder(String.valueOf(zza2).length() + 70 + String.valueOf(zzb2).length());
                        sb.append("Calling connect() while still connected, missing disconnect() for ");
                        sb.append(zza2);
                        sb.append(" on ");
                        sb.append(zzb2);
                        Log.e("GmsClient", sb.toString());
                        this.zzk.unbindService(this.zzh.zza(), this.zzh.zzb(), this.zzh.zzc(), this.zzr, zzc());
                        this.mDisconnectCount.incrementAndGet();
                    }
                    this.zzr = new zze(this.mDisconnectCount.get());
                    if (this.zzs != 3 || getLocalStartServiceAction() == null) {
                        zzu2 = new zzu(zzac(), getStartServiceAction(), false, zzaa());
                    } else {
                        zzu2 = new zzu(this.zzi.getPackageName(), getLocalStartServiceAction(), true, zzaa());
                    }
                    this.zzh = zzu2;
                    if (!this.zzk.bindService(this.zzh.zza(), this.zzh.zzb(), this.zzh.zzc(), this.zzr, zzc())) {
                        String zza3 = this.zzh.zza();
                        String zzb3 = this.zzh.zzb();
                        StringBuilder sb2 = new StringBuilder(String.valueOf(zza3).length() + 34 + String.valueOf(zzb3).length());
                        sb2.append("unable to connect to service: ");
                        sb2.append(zza3);
                        sb2.append(" on ");
                        sb2.append(zzb3);
                        Log.e("GmsClient", sb2.toString());
                        zza(16, (Bundle) null, this.mDisconnectCount.get());
                    }
                } else if (i == 4) {
                    onConnectedLocked(t);
                }
            } else if (this.zzr != null) {
                this.zzk.unbindService(getStartServiceAction(), zzac(), zzaa(), this.zzr, zzc());
                this.zzr = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void zza(int i, T t) {
    }

    /* access modifiers changed from: private */
    public final boolean zza(int i, int i2, T t) {
        synchronized (this.zzm) {
            if (this.zzs != i) {
                return false;
            }
            zzb(i2, t);
            return true;
        }
    }

    public void checkAvailabilityAndConnect() {
        int isGooglePlayServicesAvailable = this.zzl.isGooglePlayServicesAvailable(this.zzi);
        if (isGooglePlayServicesAvailable != 0) {
            zzb(1, null);
            triggerNotAvailable(new zzf(), isGooglePlayServicesAvailable, null);
            return;
        }
        connect(new zzf());
    }

    public void connect(@NonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks) {
        this.mConnectionProgressReportCallbacks = (ConnectionProgressReportCallbacks) zzau.zza(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        zzb(2, null);
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this.zzm) {
            z = this.zzs == 4;
        }
        return z;
    }

    public boolean isConnecting() {
        boolean z;
        synchronized (this.zzm) {
            if (this.zzs != 2) {
                if (this.zzs != 3) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    @Hide
    private final boolean zzd() {
        boolean z;
        synchronized (this.zzm) {
            z = this.zzs == 3;
        }
        return z;
    }

    public void disconnect() {
        this.mDisconnectCount.incrementAndGet();
        synchronized (this.zzq) {
            int size = this.zzq.size();
            for (int i = 0; i < size; i++) {
                this.zzq.get(i).zze();
            }
            this.zzq.clear();
        }
        synchronized (this.zzn) {
            this.zzo = null;
        }
        zzb(1, null);
    }

    @Hide
    public final void zza(int i) {
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(6, this.mDisconnectCount.get(), i));
    }

    /* access modifiers changed from: private */
    @Hide
    public final void zzb(int i) {
        int i2;
        if (zzd()) {
            i2 = 5;
            this.zzy = true;
        } else {
            i2 = 4;
        }
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(i2, this.mDisconnectCount.get(), 16));
    }

    /* access modifiers changed from: protected */
    public void triggerNotAvailable(@NonNull ConnectionProgressReportCallbacks connectionProgressReportCallbacks, int i, @Nullable PendingIntent pendingIntent) {
        this.mConnectionProgressReportCallbacks = (ConnectionProgressReportCallbacks) zzau.zza(connectionProgressReportCallbacks, "Connection progress callbacks cannot be null.");
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(3, this.mDisconnectCount.get(), i, pendingIntent));
    }

    @Hide
    public final Context zzad() {
        return this.zzi;
    }

    @Hide
    public final Looper zzae() {
        return this.zzj;
    }

    public Account getAccount() {
        return null;
    }

    public Feature[] getRequiredFeatures() {
        return new Feature[0];
    }

    public final Account getAccountOrDefault() {
        return getAccount() != null ? getAccount() : new Account("<<default account>>", "com.google");
    }

    /* access modifiers changed from: protected */
    @Hide
    public Bundle zzb() {
        return new Bundle();
    }

    /* access modifiers changed from: protected */
    public void onPostInitHandler(int i, IBinder iBinder, Bundle bundle, int i2) {
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(1, i2, -1, new zzg(i, iBinder, bundle)));
    }

    /* access modifiers changed from: protected */
    @Hide
    public final void zza(int i, @Nullable Bundle bundle, int i2) {
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(7, i2, -1, new zzh(i, null)));
    }

    /* access modifiers changed from: protected */
    @Hide
    public final void zzaf() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public Bundle getConnectionHint() {
        return null;
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
    public final T zzag() throws DeadObjectException {
        T t;
        synchronized (this.zzm) {
            if (this.zzs != 5) {
                zzaf();
                zzau.zza(this.zzp != null, (Object) "Client is connected but service is null");
                t = this.zzp;
            } else {
                throw new DeadObjectException();
            }
        }
        return t;
    }

    public final void setServiceForTesting(T t) {
        zzb(t != null ? (char) 4 : 1, t);
    }

    public final void setServiceBrokerForTesting(IGmsServiceBroker iGmsServiceBroker) {
        synchronized (this.zzn) {
            this.zzo = iGmsServiceBroker;
        }
    }

    public final IGmsServiceBroker getServiceBrokerForTesting() {
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.zzn) {
            iGmsServiceBroker = this.zzo;
        }
        return iGmsServiceBroker;
    }

    @Hide
    @Deprecated
    public final void zza(zzc<?> zzc2) {
        synchronized (this.zzq) {
            this.zzq.add(zzc2);
        }
        Handler handler = this.zza;
        handler.sendMessage(handler.obtainMessage(2, this.mDisconnectCount.get(), -1, zzc2));
    }

    @WorkerThread
    @Hide
    public void getRemoteService(IAccountAccessor iAccountAccessor, Set<Scope> set) {
        GetServiceRequest extraArgs = new GetServiceRequest(this.zzv).setCallingPackage(this.zzi.getPackageName()).setExtraArgs(zzb());
        if (set != null) {
            extraArgs.setScopes(set);
        }
        if (requiresSignIn()) {
            extraArgs.setClientRequestedAccount(getAccountOrDefault()).setAuthenticatedAccount(iAccountAccessor);
        } else if (requiresAccount()) {
            extraArgs.setClientRequestedAccount(getAccount());
        }
        extraArgs.setClientRequiredFeatures(getRequiredFeatures());
        try {
            synchronized (this.zzn) {
                if (this.zzo != null) {
                    this.zzo.getService(new zzd(this, this.mDisconnectCount.get()), extraArgs);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (DeadObjectException e) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e);
            zza(1);
        } catch (SecurityException e2) {
            throw e2;
        } catch (RemoteException | RuntimeException e3) {
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e3);
            onPostInitHandler(8, null, null, this.mDisconnectCount.get());
        }
    }

    public boolean requiresSignIn() {
        return false;
    }

    public void onUserSignOut(@NonNull SignOutCallbacks signOutCallbacks) {
        signOutCallbacks.onSignOutComplete();
    }

    public boolean requiresAccount() {
        return false;
    }

    public boolean requiresGooglePlayServices() {
        return true;
    }

    public boolean providesSignIn() {
        return false;
    }

    public Intent getSignInIntent() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    /* access modifiers changed from: protected */
    public Set<Scope> getScopes() {
        return Collections.EMPTY_SET;
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        int i;
        T t;
        IGmsServiceBroker iGmsServiceBroker;
        synchronized (this.zzm) {
            i = this.zzs;
            t = this.zzp;
        }
        synchronized (this.zzn) {
            iGmsServiceBroker = this.zzo;
        }
        printWriter.append((CharSequence) str).append((CharSequence) "mConnectState=");
        if (i == 1) {
            printWriter.print("DISCONNECTED");
        } else if (i == 2) {
            printWriter.print("REMOTE_CONNECTING");
        } else if (i == 3) {
            printWriter.print("LOCAL_CONNECTING");
        } else if (i == 4) {
            printWriter.print("CONNECTED");
        } else if (i != 5) {
            printWriter.print("UNKNOWN");
        } else {
            printWriter.print("DISCONNECTING");
        }
        printWriter.append((CharSequence) " mService=");
        if (t == null) {
            printWriter.append((CharSequence) "null");
        } else {
            printWriter.append((CharSequence) getServiceDescriptor()).append((CharSequence) "@").append((CharSequence) Integer.toHexString(System.identityHashCode(t.asBinder())));
        }
        printWriter.append((CharSequence) " mServiceBroker=");
        if (iGmsServiceBroker == null) {
            printWriter.println("null");
        } else {
            printWriter.append((CharSequence) "IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(iGmsServiceBroker.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zze > 0) {
            PrintWriter append = printWriter.append((CharSequence) str).append((CharSequence) "lastConnectedTime=");
            long j = this.zze;
            String format = simpleDateFormat.format(new Date(j));
            StringBuilder sb = new StringBuilder(String.valueOf(format).length() + 21);
            sb.append(j);
            sb.append(" ");
            sb.append(format);
            append.println(sb.toString());
        }
        if (this.zzd > 0) {
            printWriter.append((CharSequence) str).append((CharSequence) "lastSuspendedCause=");
            int i2 = this.zzc;
            if (i2 == 1) {
                printWriter.append((CharSequence) "CAUSE_SERVICE_DISCONNECTED");
            } else if (i2 != 2) {
                printWriter.append((CharSequence) String.valueOf(i2));
            } else {
                printWriter.append((CharSequence) "CAUSE_NETWORK_LOST");
            }
            PrintWriter append2 = printWriter.append((CharSequence) " lastSuspendedTime=");
            long j2 = this.zzd;
            String format2 = simpleDateFormat.format(new Date(j2));
            StringBuilder sb2 = new StringBuilder(String.valueOf(format2).length() + 21);
            sb2.append(j2);
            sb2.append(" ");
            sb2.append(format2);
            append2.println(sb2.toString());
        }
        if (this.zzg > 0) {
            printWriter.append((CharSequence) str).append((CharSequence) "lastFailedStatus=").append((CharSequence) CommonStatusCodes.getStatusCodeString(this.zzf));
            PrintWriter append3 = printWriter.append((CharSequence) " lastFailedTime=");
            long j3 = this.zzg;
            String format3 = simpleDateFormat.format(new Date(j3));
            StringBuilder sb3 = new StringBuilder(String.valueOf(format3).length() + 21);
            sb3.append(j3);
            sb3.append(" ");
            sb3.append(format3);
            append3.println(sb3.toString());
        }
    }

    @Nullable
    public IBinder getServiceBrokerBinder() {
        synchronized (this.zzn) {
            if (this.zzo == null) {
                return null;
            }
            IBinder asBinder = this.zzo.asBinder();
            return asBinder;
        }
    }

    /* access modifiers changed from: private */
    public final boolean zze() {
        if (this.zzy || TextUtils.isEmpty(getServiceDescriptor()) || TextUtils.isEmpty(getLocalStartServiceAction())) {
            return false;
        }
        try {
            Class.forName(getServiceDescriptor());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Hide
    public final String zzab() {
        zzu zzu2;
        if (isConnected() && (zzu2 = this.zzh) != null) {
            return zzu2.zzb();
        }
        throw new RuntimeException("Failed to connect when checking package");
    }
}
