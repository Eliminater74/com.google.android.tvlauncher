package com.google.android.gms.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.base.C0946R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbb;
import com.google.android.gms.common.internal.zzbc;
import com.google.android.gms.dynamic.zzq;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton extends FrameLayout implements View.OnClickListener {
    public static final int COLOR_AUTO = 2;
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int zza;
    private int zzb;
    private View zzc;
    private View.OnClickListener zzd;

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX INFO: finally extract failed */
    public SignInButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzd = null;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C0946R.styleable.SignInButton, 0, 0);
        try {
            this.zza = obtainStyledAttributes.getInt(C0946R.styleable.SignInButton_buttonSize, 0);
            this.zzb = obtainStyledAttributes.getInt(C0946R.styleable.SignInButton_colorScheme, 2);
            obtainStyledAttributes.recycle();
            setStyle(this.zza, this.zzb);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public final void setSize(int i) {
        setStyle(i, this.zzb);
    }

    public final void setColorScheme(int i) {
        setStyle(this.zza, i);
    }

    @Deprecated
    public final void setScopes(Scope[] scopeArr) {
        setStyle(this.zza, this.zzb);
    }

    public final void setStyle(int i, int i2) {
        this.zza = i;
        this.zzb = i2;
        Context context = getContext();
        View view = this.zzc;
        if (view != null) {
            removeView(view);
        }
        try {
            this.zzc = zzbb.zza(context, this.zza, this.zzb);
        } catch (zzq e) {
            Log.w("SignInButton", "Sign in button not found, using placeholder instead");
            int i3 = this.zza;
            int i4 = this.zzb;
            zzbc zzbc = new zzbc(context);
            zzbc.zza(context.getResources(), i3, i4);
            this.zzc = zzbc;
        }
        addView(this.zzc);
        this.zzc.setEnabled(isEnabled());
        this.zzc.setOnClickListener(this);
    }

    @Deprecated
    public final void setStyle(int i, int i2, Scope[] scopeArr) {
        setStyle(i, i2);
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.zzd = onClickListener;
        View view = this.zzc;
        if (view != null) {
            view.setOnClickListener(this);
        }
    }

    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.zzc.setEnabled(z);
    }

    public final void onClick(View view) {
        View.OnClickListener onClickListener = this.zzd;
        if (onClickListener != null && view == this.zzc) {
            onClickListener.onClick(this);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ButtonSize {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorScheme {
    }
}
