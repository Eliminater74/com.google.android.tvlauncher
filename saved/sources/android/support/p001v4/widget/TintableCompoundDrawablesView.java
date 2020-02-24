package android.support.p001v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;

/* renamed from: android.support.v4.widget.TintableCompoundDrawablesView */
public interface TintableCompoundDrawablesView {
    @Nullable
    ColorStateList getSupportCompoundDrawablesTintList();

    @Nullable
    PorterDuff.Mode getSupportCompoundDrawablesTintMode();

    void setSupportCompoundDrawablesTintList(@Nullable ColorStateList colorStateList);

    void setSupportCompoundDrawablesTintMode(@Nullable PorterDuff.Mode mode);
}
