package com.google.android.tvlauncher;

import android.content.Context;
import com.google.android.tvlauncher.BackHomeControllerListeners;

public class MainBackHomeController implements BackHomeControllerListeners.OnHomePressedListener, BackHomeControllerListeners.OnBackPressedListener, BackHomeControllerListeners.OnBackNotHandledListener {
    private static MainBackHomeController sInstance = null;
    private BackHomeControllerListeners.OnBackPressedListener mOnBackPressedListener;
    private BackHomeControllerListeners.OnHomePressedListener mOnHomePressedListener;

    public static MainBackHomeController getInstance() {
        if (sInstance == null) {
            sInstance = new MainBackHomeController();
        }
        return sInstance;
    }

    public void setOnHomePressedListener(BackHomeControllerListeners.OnHomePressedListener listener) {
        this.mOnHomePressedListener = listener;
    }

    public void setOnBackPressedListener(BackHomeControllerListeners.OnBackPressedListener listener) {
        this.mOnBackPressedListener = listener;
    }

    public void onBackNotHandled(Context c) {
    }

    public void onHomePressed(Context c) {
        BackHomeControllerListeners.OnHomePressedListener onHomePressedListener = this.mOnHomePressedListener;
        if (onHomePressedListener != null) {
            onHomePressedListener.onHomePressed(c);
        }
    }

    public void onBackPressed(Context c) {
        BackHomeControllerListeners.OnBackPressedListener onBackPressedListener = this.mOnBackPressedListener;
        if (onBackPressedListener != null) {
            onBackPressedListener.onBackPressed(c);
        } else {
            onBackNotHandled(c);
        }
    }
}
