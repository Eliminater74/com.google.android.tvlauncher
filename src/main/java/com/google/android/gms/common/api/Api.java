package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.zzau;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions> {
    private final zza<?, O> zza;
    private final zzf<?, O> zzb = null;
    private final ClientKey<?> zzc;
    private final zzg<?> zzd;
    private final String zze;

    public interface ApiOptions {

        public interface HasAccountOptions extends HasOptions, NotRequiredOptions {
            Account getAccount();
        }

        public interface HasGoogleSignInAccountOptions extends HasOptions {
            GoogleSignInAccount getGoogleSignInAccount();
        }

        public interface HasOptions extends ApiOptions {
        }

        public static final class NoOptions implements NotRequiredOptions {
            private NoOptions() {
            }
        }

        public interface NotRequiredOptions extends ApiOptions {
        }

        public interface Optional extends HasOptions, NotRequiredOptions {
        }
    }

    @Hide
    public interface Client extends zzb {
        void connect(BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks);

        void disconnect();

        void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

        void getRemoteService(IAccountAccessor iAccountAccessor, Set<Scope> set);

        @Nullable
        IBinder getServiceBrokerBinder();

        Intent getSignInIntent();

        boolean isConnected();

        boolean isConnecting();

        void onUserSignOut(BaseGmsClient.SignOutCallbacks signOutCallbacks);

        boolean providesSignIn();

        boolean requiresAccount();

        boolean requiresGooglePlayServices();

        boolean requiresSignIn();

        @Hide
        int zza();

        @Hide
        String zzab();
    }

    @Hide
    public static final class ClientKey<C extends Client> extends zzc<C> {
    }

    @Hide
    public static abstract class zza<T extends Client, O> extends zzd<T, O> {
        @Hide
        public abstract T zza(Context context, Looper looper, ClientSettings clientSettings, O o, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener);
    }

    @Hide
    public interface zzb {
    }

    @Hide
    public static class zzc<C extends zzb> {
    }

    @Hide
    public static abstract class zzd<T extends zzb, O> {
        public int zza() {
            return Integer.MAX_VALUE;
        }

        public List<Scope> zza(O o) {
            return Collections.emptyList();
        }
    }

    @Hide
    public interface zze<T extends IInterface> extends zzb {
    }

    @Hide
    public static abstract class zzf<T extends zze, O> extends zzd<T, O> {
    }

    @Hide
    public static final class zzg<C extends zze> extends zzc<C> {
    }

    /* JADX WARN: Type inference failed for: r3v0, types: [com.google.android.gms.common.api.Api$zza<?, O>, com.google.android.gms.common.api.Api$zza<C, O>, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v0, types: [com.google.android.gms.common.api.Api$ClientKey<C>, java.lang.Object, com.google.android.gms.common.api.Api$ClientKey<?>] */
    /* JADX WARNING: Unknown variable types count: 2 */
    @com.google.android.gms.common.internal.Hide
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <C extends com.google.android.gms.common.api.Api.Client> Api(java.lang.String r2, com.google.android.gms.common.api.Api.zza<C, O> r3, com.google.android.gms.common.api.Api.ClientKey<C> r4) {
        /*
            r1 = this;
            r1.<init>()
            java.lang.String r0 = "Cannot construct an Api with a null ClientBuilder"
            com.google.android.gms.common.internal.zzau.zza(r3, r0)
            java.lang.String r0 = "Cannot construct an Api with a null ClientKey"
            com.google.android.gms.common.internal.zzau.zza(r4, r0)
            r1.zze = r2
            r1.zza = r3
            r2 = 0
            r1.zzb = r2
            r1.zzc = r4
            r1.zzd = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.Api.<init>(java.lang.String, com.google.android.gms.common.api.Api$zza, com.google.android.gms.common.api.Api$ClientKey):void");
    }

    @Hide
    public final zzd<?, O> zza() {
        return this.zza;
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
    public final zza<?, O> zzb() {
        zzau.zza(this.zza != null, (Object) "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
        return this.zza;
    }

    @Hide
    public final zzc<?> zzc() {
        ClientKey<?> clientKey = this.zzc;
        if (clientKey != null) {
            return clientKey;
        }
        throw new IllegalStateException("This API was constructed with null client keys. This should not be possible.");
    }

    @Hide
    public final String zzd() {
        return this.zze;
    }
}
