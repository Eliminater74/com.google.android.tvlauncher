package android.support.p001v4.app;

import android.app.Dialog;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

/* renamed from: android.support.v4.app.DialogCompat */
public class DialogCompat {
    private DialogCompat() {
    }

    @NonNull
    public static View requireViewById(@NonNull Dialog dialog, int id) {
        if (Build.VERSION.SDK_INT >= 28) {
            return dialog.requireViewById(id);
        }
        View view = dialog.findViewById(id);
        if (view != null) {
            return view;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Dialog");
    }
}
