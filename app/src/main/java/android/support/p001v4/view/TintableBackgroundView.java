package android.support.p001v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;

/* renamed from: android.support.v4.view.TintableBackgroundView */
public interface TintableBackgroundView {
    @Nullable
    ColorStateList getSupportBackgroundTintList();

    void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList);

    @Nullable
    PorterDuff.Mode getSupportBackgroundTintMode();

    void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode);
}
