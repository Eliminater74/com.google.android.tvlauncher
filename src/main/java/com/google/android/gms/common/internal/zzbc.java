package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.p001v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.widget.Button;
import com.google.android.gms.base.C0946R;
import com.google.android.gms.common.util.zzi;

/* compiled from: SignInButtonImpl */
public final class zzbc extends Button {
    public zzbc(Context context) {
        this(context, null);
    }

    private zzbc(Context context, AttributeSet attributeSet) {
        super(context, null, 16842824);
    }

    public final void zza(Resources resources, int i, int i2) {
        setTypeface(Typeface.DEFAULT_BOLD);
        setTextSize(14.0f);
        int i3 = (int) ((resources.getDisplayMetrics().density * 48.0f) + 0.5f);
        setMinHeight(i3);
        setMinWidth(i3);
        int zza = zza(i2, C0946R.C0947drawable.common_google_signin_btn_icon_dark, C0946R.C0947drawable.common_google_signin_btn_icon_light, C0946R.C0947drawable.common_google_signin_btn_icon_light);
        int zza2 = zza(i2, C0946R.C0947drawable.common_google_signin_btn_text_dark, C0946R.C0947drawable.common_google_signin_btn_text_light, C0946R.C0947drawable.common_google_signin_btn_text_light);
        if (i == 0 || i == 1) {
            zza = zza2;
        } else if (i != 2) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("Unknown button size: ");
            sb.append(i);
            throw new IllegalStateException(sb.toString());
        }
        Drawable wrap = DrawableCompat.wrap(resources.getDrawable(zza));
        DrawableCompat.setTintList(wrap, resources.getColorStateList(C0946R.color.common_google_signin_btn_tint));
        DrawableCompat.setTintMode(wrap, PorterDuff.Mode.SRC_ATOP);
        setBackgroundDrawable(wrap);
        setTextColor((ColorStateList) zzau.zza(resources.getColorStateList(zza(i2, C0946R.color.common_google_signin_btn_text_dark, C0946R.color.common_google_signin_btn_text_light, C0946R.color.common_google_signin_btn_text_light))));
        if (i == 0) {
            setText(resources.getString(C0946R.string.common_signin_button_text));
        } else if (i == 1) {
            setText(resources.getString(C0946R.string.common_signin_button_text_long));
        } else if (i == 2) {
            setText((CharSequence) null);
        } else {
            StringBuilder sb2 = new StringBuilder(32);
            sb2.append("Unknown button size: ");
            sb2.append(i);
            throw new IllegalStateException(sb2.toString());
        }
        setTransformationMethod(null);
        if (zzi.zza(getContext())) {
            setGravity(19);
        }
    }

    private static int zza(int i, int i2, int i3, int i4) {
        if (i == 0) {
            return i2;
        }
        if (i == 1) {
            return i3;
        }
        if (i == 2) {
            return i4;
        }
        StringBuilder sb = new StringBuilder(33);
        sb.append("Unknown color scheme: ");
        sb.append(i);
        throw new IllegalStateException(sb.toString());
    }
}
