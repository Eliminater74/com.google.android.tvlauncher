package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.zzbn;
import com.google.android.gms.common.internal.Hide;

public class GoogleApiActivity extends Activity implements DialogInterface.OnCancelListener {
    @Hide
    private int zza = 0;

    @Hide
    public static PendingIntent zza(Context context, PendingIntent pendingIntent, int i) {
        return PendingIntent.getActivity(context, 0, zza(context, pendingIntent, i, true), 134217728);
    }

    @Hide
    public static Intent zza(Context context, PendingIntent pendingIntent, int i, boolean z) {
        Intent intent = new Intent(context, GoogleApiActivity.class);
        intent.putExtra("pending_intent", pendingIntent);
        intent.putExtra("failing_client_id", i);
        intent.putExtra("notify_manager", z);
        return intent;
    }

    /* access modifiers changed from: protected */
    @Hide
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zza = bundle.getInt("resolution");
        }
        if (this.zza != 1) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                Log.e("GoogleApiActivity", "Activity started without extras");
                finish();
                return;
            }
            PendingIntent pendingIntent = (PendingIntent) extras.get("pending_intent");
            Integer num = (Integer) extras.get("error_code");
            if (pendingIntent == null && num == null) {
                Log.e("GoogleApiActivity", "Activity started without resolution");
                finish();
            } else if (pendingIntent != null) {
                try {
                    startIntentSenderForResult(pendingIntent.getIntentSender(), 1, null, 0, 0, 0);
                    this.zza = 1;
                } catch (IntentSender.SendIntentException e) {
                    Log.e("GoogleApiActivity", "Failed to launch pendingIntent", e);
                    finish();
                }
            } else {
                GoogleApiAvailability.getInstance().showErrorDialogFragment(this, num.intValue(), 2, this);
                this.zza = 1;
            }
        }
    }

    /* access modifiers changed from: protected */
    @Hide
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            boolean booleanExtra = getIntent().getBooleanExtra("notify_manager", true);
            this.zza = 0;
            setResult(i2, intent);
            if (booleanExtra) {
                zzbn zza2 = zzbn.zza(this);
                if (i2 == -1) {
                    zza2.zzd();
                } else if (i2 == 0) {
                    zza2.zzb(new ConnectionResult(13, null), getIntent().getIntExtra("failing_client_id", -1));
                }
            }
        } else if (i == 2) {
            this.zza = 0;
            setResult(i2, intent);
        }
        finish();
    }

    /* access modifiers changed from: protected */
    @Hide
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("resolution", this.zza);
        super.onSaveInstanceState(bundle);
    }

    @Hide
    public void onCancel(DialogInterface dialogInterface) {
        this.zza = 0;
        setResult(0);
        finish();
    }
}
