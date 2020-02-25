package android.support.p001v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;

/* renamed from: android.support.v4.widget.TintableCompoundButton */
public interface TintableCompoundButton {
    @Nullable
    ColorStateList getSupportButtonTintList();

    void setSupportButtonTintList(@Nullable ColorStateList colorStateList);

    @Nullable
    PorterDuff.Mode getSupportButtonTintMode();

    void setSupportButtonTintMode(@Nullable PorterDuff.Mode mode);
}
