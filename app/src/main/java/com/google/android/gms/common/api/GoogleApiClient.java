package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p001v4.app.FragmentActivity;
import android.support.p001v4.util.ArrayMap;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.zzbb;
import com.google.android.gms.common.api.internal.zzce;
import com.google.android.gms.common.api.internal.zzcj;
import com.google.android.gms.common.api.internal.zzda;
import com.google.android.gms.common.api.internal.zzdo;
import com.google.android.gms.common.api.internal.zzn;
import com.google.android.gms.common.api.internal.zzu;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient {
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    /* access modifiers changed from: private */
    public static final Set<GoogleApiClient> zza = Collections.newSetFromMap(new WeakHashMap());

    public static void dumpAll(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (zza) {
            int i = 0;
            String concat = String.valueOf(str).concat("  ");
            for (GoogleApiClient dump : zza) {
                printWriter.append((CharSequence) str).append((CharSequence) "GoogleApiClient#").println(i);
                dump.dump(concat, fileDescriptor, printWriter, strArr);
                i++;
            }
        }
    }

    @Hide
    public static Set<GoogleApiClient> zza() {
        Set<GoogleApiClient> set;
        synchronized (zza) {
            set = zza;
        }
        return set;
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public abstract void disconnect();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull Api<?> api);

    public abstract boolean hasConnectedApi(@NonNull Api<?> api);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void stopAutoManage(@NonNull FragmentActivity fragmentActivity);

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    @Hide
    public <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public <L> zzcj<L> zza(@NonNull L l) {
        throw new UnsupportedOperationException();
    }

    @Hide
    @NonNull
    public <C extends Api.Client> C zza(@NonNull Api.zzc<C> zzc) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public boolean zza(@NonNull Api<?> api) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public Context zzb() {
        throw new UnsupportedOperationException();
    }

    @Hide
    public Looper zzc() {
        throw new UnsupportedOperationException();
    }

    @Hide
    public boolean zza(zzda zzda) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zzd() {
        throw new UnsupportedOperationException();
    }

    public void connect(int i) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zza(ResultStore resultStore) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zza(zzdo zzdo) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zzb(zzdo zzdo) {
        throw new UnsupportedOperationException();
    }

    public interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public interface OnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    public static final class Builder {
        private final Set<Scope> zzb;
        private final Set<Scope> zzc;
        private final Map<Api<?>, ClientSettings.OptionalApiSettings> zzh;
        private final Context zzi;
        private final Map<Api<?>, Api.ApiOptions> zzj;
        private final ArrayList<ConnectionCallbacks> zzq;
        private final ArrayList<OnConnectionFailedListener> zzr;
        private Account zza;
        private int zzd;
        private View zze;
        private String zzf;
        private String zzg;
        private zzce zzk;
        private int zzl;
        private OnConnectionFailedListener zzm;
        private Looper zzn;
        private GoogleApiAvailability zzo;
        private Api.zza<? extends zzd, SignInOptions> zzp;
        private boolean zzs;

        public Builder(@NonNull Context context) {
            this.zzb = new HashSet();
            this.zzc = new HashSet();
            this.zzh = new ArrayMap();
            this.zzj = new ArrayMap();
            this.zzl = -1;
            this.zzo = GoogleApiAvailability.getInstance();
            this.zzp = zza.zza;
            this.zzq = new ArrayList<>();
            this.zzr = new ArrayList<>();
            this.zzs = false;
            this.zzi = context;
            this.zzn = context.getMainLooper();
            this.zzf = context.getPackageName();
            this.zzg = context.getClass().getName();
        }

        public Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            zzau.zza(connectionCallbacks, "Must provide a connected listener");
            this.zzq.add(connectionCallbacks);
            zzau.zza(onConnectionFailedListener, "Must provide a connection failed listener");
            this.zzr.add(onConnectionFailedListener);
        }

        public final Builder setHandler(@NonNull Handler handler) {
            zzau.zza(handler, "Handler must not be null");
            this.zzn = handler.getLooper();
            return this;
        }

        public final Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
            zzau.zza(connectionCallbacks, "Listener must not be null");
            this.zzq.add(connectionCallbacks);
            return this;
        }

        public final Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
            zzau.zza(onConnectionFailedListener, "Listener must not be null");
            this.zzr.add(onConnectionFailedListener);
            return this;
        }

        public final Builder setViewForPopups(@NonNull View view) {
            zzau.zza(view, "View must not be null");
            this.zze = view;
            return this;
        }

        public final Builder addScope(@NonNull Scope scope) {
            zzau.zza(scope, "Scope must not be null");
            this.zzb.add(scope);
            return this;
        }

        public final Builder addApi(@NonNull Api<? extends Api.ApiOptions.NotRequiredOptions> api) {
            zzau.zza(api, "Api must not be null");
            this.zzj.put(api, null);
            List<Scope> zza2 = api.zza().zza(null);
            this.zzc.addAll(zza2);
            this.zzb.addAll(zza2);
            return this;
        }

        public final Builder addApiIfAvailable(@NonNull Api<? extends Api.ApiOptions.NotRequiredOptions> api, Scope... scopeArr) {
            zzau.zza(api, "Api must not be null");
            this.zzj.put(api, null);
            zza(api, null, scopeArr);
            return this;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
         arg types: [O, java.lang.String]
         candidates:
          com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
          com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
          com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
          com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
          com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
        public final <O extends Api.ApiOptions.HasOptions> Builder addApi(@NonNull Api<O> api, @NonNull O o) {
            zzau.zza(api, "Api must not be null");
            zzau.zza((Object) o, (Object) "Null options are not permitted for this Api");
            this.zzj.put(api, o);
            List<Scope> zza2 = api.zza().zza(o);
            this.zzc.addAll(zza2);
            this.zzb.addAll(zza2);
            return this;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
         arg types: [O, java.lang.String]
         candidates:
          com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
          com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
          com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
          com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
          com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T */
        public final <O extends Api.ApiOptions.HasOptions> Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o, Scope... scopeArr) {
            zzau.zza(api, "Api must not be null");
            zzau.zza((Object) o, (Object) "Null options are not permitted for this Api");
            this.zzj.put(api, o);
            zza(api, o, scopeArr);
            return this;
        }

        public final Builder setAccountName(String str) {
            this.zza = str == null ? null : new Account(str, "com.google");
            return this;
        }

        @Hide
        public final Builder setAccount(Account account) {
            this.zza = account;
            return this;
        }

        public final Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }

        public final Builder setGravityForPopups(int i) {
            this.zzd = i;
            return this;
        }

        public final Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int i, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            zzce zzce = new zzce(fragmentActivity);
            zzau.zzb(i >= 0, "clientId must be non-negative");
            this.zzl = i;
            this.zzm = onConnectionFailedListener;
            this.zzk = zzce;
            return this;
        }

        public final Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        @Hide
        public final ClientSettings zza() {
            SignInOptions signInOptions;
            SignInOptions signInOptions2 = SignInOptions.DEFAULT;
            if (this.zzj.containsKey(zza.zzb)) {
                signInOptions = (SignInOptions) this.zzj.get(zza.zzb);
            } else {
                signInOptions = signInOptions2;
            }
            return new ClientSettings(this.zza, this.zzb, this.zzh, this.zzd, this.zze, this.zzf, this.zzg, signInOptions);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.gms.common.api.internal.zzbb.zza(java.lang.Iterable<com.google.android.gms.common.api.Api$Client>, boolean):int
         arg types: [java.util.Collection, int]
         candidates:
          com.google.android.gms.common.api.internal.zzbb.zza(int, boolean):void
          com.google.android.gms.common.api.internal.zzcd.zza(int, boolean):void
          com.google.android.gms.common.api.internal.zzbb.zza(java.lang.Iterable<com.google.android.gms.common.api.Api$Client>, boolean):int */
        public final GoogleApiClient build() {
            zzau.zzb(!this.zzj.isEmpty(), "must call addApi() to add at least one API");
            ClientSettings zza2 = zza();
            Api api = null;
            Map<Api<?>, ClientSettings.OptionalApiSettings> optionalApiSettings = zza2.getOptionalApiSettings();
            ArrayMap arrayMap = new ArrayMap();
            ArrayMap arrayMap2 = new ArrayMap();
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (Api next : this.zzj.keySet()) {
                Api.ApiOptions apiOptions = this.zzj.get(next);
                boolean z2 = optionalApiSettings.get(next) != null;
                arrayMap.put(next, Boolean.valueOf(z2));
                zzu zzu = new zzu(next, z2);
                arrayList.add(zzu);
                Api.zza zzb2 = next.zzb();
                Api api2 = next;
                Api.Client zza3 = zzb2.zza(this.zzi, this.zzn, zza2, apiOptions, zzu, zzu);
                arrayMap2.put(api2.zzc(), zza3);
                if (zzb2.zza() == 1) {
                    z = apiOptions != null;
                }
                if (zza3.providesSignIn()) {
                    if (api == null) {
                        api = api2;
                    } else {
                        String zzd2 = api2.zzd();
                        String zzd3 = api.zzd();
                        StringBuilder sb = new StringBuilder(String.valueOf(zzd2).length() + 21 + String.valueOf(zzd3).length());
                        sb.append(zzd2);
                        sb.append(" cannot be used with ");
                        sb.append(zzd3);
                        throw new IllegalStateException(sb.toString());
                    }
                }
            }
            if (api != null) {
                if (!z) {
                    zzau.zza(this.zza == null, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", api.zzd());
                    zzau.zza(this.zzb.equals(this.zzc), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", api.zzd());
                } else {
                    String zzd4 = api.zzd();
                    StringBuilder sb2 = new StringBuilder(String.valueOf(zzd4).length() + 82);
                    sb2.append("With using ");
                    sb2.append(zzd4);
                    sb2.append(", GamesOptions can only be specified within GoogleSignInOptions.Builder");
                    throw new IllegalStateException(sb2.toString());
                }
            }
            zzbb zzbb = new zzbb(this.zzi, new ReentrantLock(), this.zzn, zza2, this.zzo, this.zzp, arrayMap, this.zzq, this.zzr, arrayMap2, this.zzl, zzbb.zza((Iterable<Api.Client>) arrayMap2.values(), true), arrayList, false);
            synchronized (GoogleApiClient.zza) {
                GoogleApiClient.zza.add(zzbb);
            }
            if (this.zzl >= 0) {
                zzj.zza(this.zzk).zza(this.zzl, zzbb, this.zzm);
            }
            return zzbb;
        }

        private final <O extends Api.ApiOptions> void zza(Api<O> api, O o, Scope... scopeArr) {
            HashSet hashSet = new HashSet(api.zza().zza(o));
            for (Scope add : scopeArr) {
                hashSet.add(add);
            }
            this.zzh.put(api, new ClientSettings.OptionalApiSettings(hashSet));
        }
    }
}
