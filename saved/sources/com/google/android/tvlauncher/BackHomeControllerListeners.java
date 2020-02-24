package com.google.android.tvlauncher;

import android.content.Context;

public class BackHomeControllerListeners {

    public interface OnBackNotHandledListener {
        void onBackNotHandled(Context context);
    }

    public interface OnBackPressedListener {
        void onBackPressed(Context context);
    }

    public interface OnHomeNotHandledListener {
        void onHomeNotHandled(Context context);
    }

    public interface OnHomePressedListener {
        void onHomePressed(Context context);
    }
}
