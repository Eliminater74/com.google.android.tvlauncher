package com.google.android.tvlauncher.inputs;

import android.graphics.drawable.Drawable;
import android.net.Uri;

public interface InputsManager {
    Uri getActiveIconUri(int i);

    String getGroupId(int i);

    Drawable getIcon(int i);

    Uri getIconUri(int i);

    String getInputId(int i);

    int getInputState(int i);

    int getInputType(int i);

    int getItemCount();

    String getLabel(int i);

    String getParentLabel(int i);

    Uri getSelectedActiveIconUri(int i);

    Uri getSelectedIconUri(int i);

    boolean hasInputs();

    void launchInputActivity(int i);

    void loadInputs();

    void loadInputsIfNeeded();

    void registerOnChangedListener(OnInputsChangedListener onInputsChangedListener);

    void unregisterOnChangedListener(OnInputsChangedListener onInputsChangedListener);
}
