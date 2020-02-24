package com.google.android.gms.signin;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.internal.zzemc;

/* compiled from: SignIn */
final class zzb extends Api.zza<zzemc, SignInOptions> {
    zzb() {
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzemc.<init>(android.content.Context, android.os.Looper, boolean, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.signin.SignInOptions, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener):void
     arg types: [android.content.Context, android.os.Looper, int, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.signin.SignInOptions, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener]
     candidates:
      com.google.android.gms.internal.zzemc.<init>(android.content.Context, android.os.Looper, boolean, com.google.android.gms.common.internal.ClientSettings, android.os.Bundle, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener):void
      com.google.android.gms.internal.zzemc.<init>(android.content.Context, android.os.Looper, boolean, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.signin.SignInOptions, com.google.android.gms.common.api.GoogleApiClient$ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener):void */
    public final /* synthetic */ Api.Client zza(Context context, Looper looper, ClientSettings clientSettings, Object obj, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        SignInOptions signInOptions;
        SignInOptions signInOptions2 = (SignInOptions) obj;
        if (signInOptions2 == null) {
            signInOptions = SignInOptions.DEFAULT;
        } else {
            signInOptions = signInOptions2;
        }
        return new zzemc(context, looper, true, clientSettings, signInOptions, connectionCallbacks, onConnectionFailedListener);
    }
}
