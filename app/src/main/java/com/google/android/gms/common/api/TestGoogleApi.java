package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.p001v4.app.FragmentActivity;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.api.internal.zzh;
import com.google.android.gms.common.internal.Hide;

@Hide
public class TestGoogleApi<O extends Api.ApiOptions> extends GoogleApi<O> {
    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, android.os.Looper, com.google.android.gms.common.api.internal.zzdg):void
     arg types: [android.support.v4.app.FragmentActivity, com.google.android.gms.common.api.Api<O>, O, android.os.Looper, com.google.android.gms.common.api.internal.zzh]
     candidates:
      com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, android.os.Looper, com.google.android.gms.common.api.internal.zzdg):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, android.os.Looper, com.google.android.gms.common.api.internal.zzdg):void */
    public TestGoogleApi(@NonNull FragmentActivity fragmentActivity, Api<O> api, O o, Looper looper) {
        super((Activity) fragmentActivity, (Api) api, (Api.ApiOptions) o, looper, (zzdg) new zzh());
    }

    public TestGoogleApi(@NonNull Context context, Api<O> api, O o, Looper looper) {
        super(context, api, o, looper, new zzh());
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.zzdg):void
     arg types: [android.support.v4.app.FragmentActivity, com.google.android.gms.common.api.Api<O>, O, com.google.android.gms.common.api.internal.zzh]
     candidates:
      com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.GoogleApi$zza):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.GoogleApi$zza):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.content.Context, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.zzdg):void
      com.google.android.gms.common.api.GoogleApi.<init>(android.app.Activity, com.google.android.gms.common.api.Api, com.google.android.gms.common.api.Api$ApiOptions, com.google.android.gms.common.api.internal.zzdg):void */
    public TestGoogleApi(@NonNull FragmentActivity fragmentActivity, Api<O> api, O o) {
        super((Activity) fragmentActivity, (Api) api, (Api.ApiOptions) o, (zzdg) new zzh());
    }

    public GoogleApiClient asGoogleApiClient() {
        return super.asGoogleApiClient();
    }
}
