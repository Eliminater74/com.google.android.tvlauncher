package com.google.android.gms.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.internal.zzaa;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzav;
import com.google.android.gms.common.internal.zzl;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;

/* compiled from: SignInClientImpl */
public final class zzemc extends zzl<zzema> implements zzd {
    private final boolean zzc;
    private final ClientSettings zzd;
    private final Bundle zze;
    private Integer zzf;

    private zzemc(Context context, Looper looper, boolean z, ClientSettings clientSettings, Bundle bundle, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 44, clientSettings, connectionCallbacks, onConnectionFailedListener);
        this.zzc = true;
        this.zzd = clientSettings;
        this.zze = bundle;
        this.zzf = clientSettings.getClientSessionId();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzemc.<init>(android.content.Context, android.os.Looper, boolean, com.google.android.gms.common.internal.ClientSettings, android.os.Bundle, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener):void
     arg types: [android.content.Context, android.os.Looper, int, com.google.android.gms.common.internal.ClientSettings, android.os.Bundle, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener]
     candidates:
      com.google.android.gms.internal.zzemc.<init>(android.content.Context, android.os.Looper, boolean, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.signin.SignInOptions, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener):void
      com.google.android.gms.internal.zzemc.<init>(android.content.Context, android.os.Looper, boolean, com.google.android.gms.common.internal.ClientSettings, android.os.Bundle, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener):void */
    public zzemc(Context context, Looper looper, boolean z, ClientSettings clientSettings, SignInOptions signInOptions, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, true, clientSettings, zza(clientSettings), connectionCallbacks, onConnectionFailedListener);
    }

    public static Bundle zza(ClientSettings clientSettings) {
        SignInOptions signInOptions = clientSettings.getSignInOptions();
        Integer clientSessionId = clientSettings.getClientSessionId();
        Bundle bundle = new Bundle();
        bundle.putParcelable("com.google.android.gms.signin.internal.clientRequestedAccount", clientSettings.getAccount());
        if (clientSessionId != null) {
            bundle.putInt(ClientSettings.KEY_CLIENT_SESSION_ID, clientSessionId.intValue());
        }
        if (signInOptions != null) {
            bundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", signInOptions.isOfflineAccessRequested());
            bundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", signInOptions.isIdTokenRequested());
            bundle.putString("com.google.android.gms.signin.internal.serverClientId", signInOptions.getServerClientId());
            bundle.putBoolean("com.google.android.gms.signin.internal.usePromptModeForAuthCode", true);
            bundle.putBoolean("com.google.android.gms.signin.internal.forceCodeForRefreshToken", signInOptions.isForceCodeForRefreshToken());
            bundle.putString("com.google.android.gms.signin.internal.hostedDomain", signInOptions.getHostedDomain());
            bundle.putBoolean("com.google.android.gms.signin.internal.waitForAccessTokenRefresh", signInOptions.waitForAccessTokenRefresh());
            if (signInOptions.getAuthApiSignInModuleVersion() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.authApiSignInModuleVersion", signInOptions.getAuthApiSignInModuleVersion().longValue());
            }
            if (signInOptions.getRealClientLibraryVersion() != null) {
                bundle.putLong("com.google.android.gms.signin.internal.realClientLibraryVersion", signInOptions.getRealClientLibraryVersion().longValue());
            }
        }
        return bundle;
    }

    public final boolean requiresSignIn() {
        return this.zzc;
    }

    public final void zza(IAccountAccessor iAccountAccessor, boolean z) {
        try {
            ((zzema) zzag()).zza(iAccountAccessor, this.zzf.intValue(), z);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
        }
    }

    public final void zzc() {
        try {
            ((zzema) zzag()).zza(this.zzf.intValue());
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
        }
    }

    public final void zza(zzely zzely) {
        zzau.zza(zzely, "Expecting a valid ISignInCallbacks");
        try {
            Account accountOrDefault = this.zzd.getAccountOrDefault();
            GoogleSignInAccount googleSignInAccount = null;
            if ("<<default account>>".equals(accountOrDefault.name)) {
                googleSignInAccount = zzaa.zza(zzad()).zza();
            }
            ((zzema) zzag()).zza(new zzemd(new zzav(accountOrDefault, this.zzf.intValue(), googleSignInAccount)), zzely);
        } catch (RemoteException e) {
            Log.w("SignInClientImpl", "Remote service probably died when signIn is called");
            try {
                zzely.zza(new zzemf(8));
            } catch (RemoteException e2) {
                Log.wtf("SignInClientImpl", "ISignInCallbacks#onSignInComplete should be executed from the same process, unexpected RemoteException.", e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public final String getStartServiceAction() {
        return "com.google.android.gms.signin.service.START";
    }

    /* access modifiers changed from: protected */
    public final String getServiceDescriptor() {
        return "com.google.android.gms.signin.internal.ISignInService";
    }

    /* access modifiers changed from: protected */
    public final Bundle zzb() {
        if (!zzad().getPackageName().equals(this.zzd.getRealClientPackageName())) {
            this.zze.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzd.getRealClientPackageName());
        }
        return this.zze;
    }

    public final void zzd() {
        connect(new BaseGmsClient.zzf());
    }

    public final int zza() {
        return 12525000;
    }

    /* access modifiers changed from: protected */
    public final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
        if (queryLocalInterface instanceof zzema) {
            return (zzema) queryLocalInterface;
        }
        return new zzemb(iBinder);
    }
}
