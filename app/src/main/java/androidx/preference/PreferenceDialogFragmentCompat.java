package androidx.preference;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.p001v4.app.DialogFragment;
import android.support.p001v4.app.Fragment;
import android.support.p004v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class PreferenceDialogFragmentCompat extends DialogFragment implements DialogInterface.OnClickListener {
    protected static final String ARG_KEY = "key";
    private static final String SAVE_STATE_ICON = "PreferenceDialogFragment.icon";
    private static final String SAVE_STATE_LAYOUT = "PreferenceDialogFragment.layout";
    private static final String SAVE_STATE_MESSAGE = "PreferenceDialogFragment.message";
    private static final String SAVE_STATE_NEGATIVE_TEXT = "PreferenceDialogFragment.negativeText";
    private static final String SAVE_STATE_POSITIVE_TEXT = "PreferenceDialogFragment.positiveText";
    private static final String SAVE_STATE_TITLE = "PreferenceDialogFragment.title";
    private BitmapDrawable mDialogIcon;
    @LayoutRes
    private int mDialogLayoutRes;
    private CharSequence mDialogMessage;
    private CharSequence mDialogTitle;
    private CharSequence mNegativeButtonText;
    private CharSequence mPositiveButtonText;
    private DialogPreference mPreference;
    private int mWhichButtonClicked;

    /*  JADX ERROR: Method load error
        jadx.core.utils.exceptions.DecodeException: Load method exception: Method info already added: android.view.Window.setSoftInputMode(int):void in method: androidx.preference.PreferenceDialogFragmentCompat.requestInputMethod(android.app.Dialog):void, dex: classes.dex
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:154)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:306)
        	at jadx.core.ProcessClass.process(ProcessClass.java:36)
        	at java.util.ArrayList.forEach(ArrayList.java:1257)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:59)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:297)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:276)
        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Method info already added: android.view.Window.setSoftInputMode(int):void
        	at jadx.core.dex.info.InfoStorage.putMethod(InfoStorage.java:42)
        	at jadx.core.dex.info.MethodInfo.fromDex(MethodInfo.java:50)
        	at jadx.core.dex.instructions.InsnDecoder.invoke(InsnDecoder.java:678)
        	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:540)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:78)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:139)
        	... 6 more
        */
    private void requestInputMethod(android.app.Dialog r1) {
        /*
            r2 = this;
            android.view.Window r0 = r3.getWindow()
            r1 = 5
            r0.setSoftInputMode(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceDialogFragmentCompat.requestInputMethod(android.app.Dialog):void");
    }

    public abstract void onDialogClosed(boolean z);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fragment rawFragment = getTargetFragment();
        if (rawFragment instanceof DialogPreference.TargetFragment) {
            DialogPreference.TargetFragment fragment = (DialogPreference.TargetFragment) rawFragment;
            String key = getArguments().getString("key");
            if (savedInstanceState == null) {
                this.mPreference = (DialogPreference) fragment.findPreference(key);
                this.mDialogTitle = this.mPreference.getDialogTitle();
                this.mPositiveButtonText = this.mPreference.getPositiveButtonText();
                this.mNegativeButtonText = this.mPreference.getNegativeButtonText();
                this.mDialogMessage = this.mPreference.getDialogMessage();
                this.mDialogLayoutRes = this.mPreference.getDialogLayoutResource();
                Drawable icon = this.mPreference.getDialogIcon();
                if (icon == null || (icon instanceof BitmapDrawable)) {
                    this.mDialogIcon = (BitmapDrawable) icon;
                    return;
                }
                Bitmap bitmap = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                icon.draw(canvas);
                this.mDialogIcon = new BitmapDrawable(getResources(), bitmap);
                return;
            }
            this.mDialogTitle = savedInstanceState.getCharSequence(SAVE_STATE_TITLE);
            this.mPositiveButtonText = savedInstanceState.getCharSequence(SAVE_STATE_POSITIVE_TEXT);
            this.mNegativeButtonText = savedInstanceState.getCharSequence(SAVE_STATE_NEGATIVE_TEXT);
            this.mDialogMessage = savedInstanceState.getCharSequence(SAVE_STATE_MESSAGE);
            this.mDialogLayoutRes = savedInstanceState.getInt(SAVE_STATE_LAYOUT, 0);
            Bitmap bitmap2 = (Bitmap) savedInstanceState.getParcelable(SAVE_STATE_ICON);
            if (bitmap2 != null) {
                this.mDialogIcon = new BitmapDrawable(getResources(), bitmap2);
                return;
            }
            return;
        }
        throw new IllegalStateException("Target fragment must implement TargetFragment interface");
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(SAVE_STATE_TITLE, this.mDialogTitle);
        outState.putCharSequence(SAVE_STATE_POSITIVE_TEXT, this.mPositiveButtonText);
        outState.putCharSequence(SAVE_STATE_NEGATIVE_TEXT, this.mNegativeButtonText);
        outState.putCharSequence(SAVE_STATE_MESSAGE, this.mDialogMessage);
        outState.putInt(SAVE_STATE_LAYOUT, this.mDialogLayoutRes);
        BitmapDrawable bitmapDrawable = this.mDialogIcon;
        if (bitmapDrawable != null) {
            outState.putParcelable(SAVE_STATE_ICON, bitmapDrawable.getBitmap());
        }
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        this.mWhichButtonClicked = -2;
        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle(this.mDialogTitle).setIcon(this.mDialogIcon).setPositiveButton(this.mPositiveButtonText, this).setNegativeButton(this.mNegativeButtonText, this);
        View contentView = onCreateDialogView(context);
        if (contentView != null) {
            onBindDialogView(contentView);
            builder.setView(contentView);
        } else {
            builder.setMessage(this.mDialogMessage);
        }
        onPrepareDialogBuilder(builder);
        Dialog dialog = builder.create();
        if (needInputMethod()) {
            requestInputMethod(dialog);
        }
        return dialog;
    }

    public DialogPreference getPreference() {
        if (this.mPreference == null) {
            this.mPreference = (DialogPreference) ((DialogPreference.TargetFragment) getTargetFragment()).findPreference(getArguments().getString("key"));
        }
        return this.mPreference;
    }

    /* access modifiers changed from: protected */
    public void onPrepareDialogBuilder(AlertDialog.Builder builder) {
    }

    /* access modifiers changed from: protected */
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public boolean needInputMethod() {
        return false;
    }

    /* access modifiers changed from: protected */
    public View onCreateDialogView(Context context) {
        int resId = this.mDialogLayoutRes;
        if (resId == 0) {
            return null;
        }
        return LayoutInflater.from(context).inflate(resId, (ViewGroup) null);
    }

    /* access modifiers changed from: protected */
    public void onBindDialogView(View view) {
        View dialogMessageView = view.findViewById(16908299);
        if (dialogMessageView != null) {
            CharSequence message = this.mDialogMessage;
            int newVisibility = 8;
            if (!TextUtils.isEmpty(message)) {
                if (dialogMessageView instanceof TextView) {
                    ((TextView) dialogMessageView).setText(message);
                }
                newVisibility = 0;
            }
            if (dialogMessageView.getVisibility() != newVisibility) {
                dialogMessageView.setVisibility(newVisibility);
            }
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        this.mWhichButtonClicked = which;
    }

    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        onDialogClosed(this.mWhichButtonClicked == -1);
    }
}
