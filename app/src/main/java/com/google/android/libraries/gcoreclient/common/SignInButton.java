package com.google.android.libraries.gcoreclient.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class SignInButton extends FrameLayout {
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;

    public abstract void setColorScheme(int i);

    public abstract void setSize(int i);

    public abstract void setStyle(int i, int i2);

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SignInButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
