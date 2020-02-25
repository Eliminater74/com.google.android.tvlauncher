package com.google.android.gms.common;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.gms.common.internal.zzau;

public class ErrorDialogFragment extends DialogFragment {
    private Dialog zza = null;
    private DialogInterface.OnCancelListener zzb = null;

    public static ErrorDialogFragment newInstance(Dialog dialog) {
        return newInstance(dialog, null);
    }

    public static ErrorDialogFragment newInstance(Dialog dialog, DialogInterface.OnCancelListener onCancelListener) {
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        Dialog dialog2 = (Dialog) zzau.zza(dialog, "Cannot display null dialog");
        dialog2.setOnCancelListener(null);
        dialog2.setOnDismissListener(null);
        errorDialogFragment.zza = dialog2;
        if (onCancelListener != null) {
            errorDialogFragment.zzb = onCancelListener;
        }
        return errorDialogFragment;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        if (this.zza == null) {
            setShowsDialog(false);
        }
        return this.zza;
    }

    public void onCancel(DialogInterface dialogInterface) {
        DialogInterface.OnCancelListener onCancelListener = this.zzb;
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialogInterface);
        }
    }

    public void show(FragmentManager fragmentManager, String str) {
        super.show(fragmentManager, str);
    }
}
