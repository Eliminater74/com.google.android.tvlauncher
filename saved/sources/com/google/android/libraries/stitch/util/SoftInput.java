package com.google.android.libraries.stitch.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class SoftInput {
    public static final int KEYBOARD_POPUP_DELAY = 150;
    public static final int KEYBOARD_POPUP_DELAY_EXTENDED = 400;
    public static final int KEYBOARD_POPUP_DELAY_SHORT = 50;

    private SoftInput() {
    }

    public static InputMethodManager getInputMethodManager(Context context) {
        return (InputMethodManager) context.getSystemService("input_method");
    }

    public static void show(View v) {
        InputMethodManager imm;
        if (v != null && (imm = getInputMethodManager(v.getContext())) != null) {
            imm.showSoftInput(v, 0);
        }
    }

    public static void hide(View v) {
        InputMethodManager imm;
        if (v != null && (imm = getInputMethodManager(v.getContext())) != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static void restart(View v) {
        InputMethodManager imm;
        if (v != null && (imm = getInputMethodManager(v.getContext())) != null) {
            imm.restartInput(v);
        }
    }
}
