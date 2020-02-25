package android.support.p001v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v4.widget.TintableImageSourceView */
public interface TintableImageSourceView {
    @Nullable
    ColorStateList getSupportImageTintList();

    void setSupportImageTintList(@Nullable ColorStateList colorStateList);

    @Nullable
    PorterDuff.Mode getSupportImageTintMode();

    void setSupportImageTintMode(@Nullable PorterDuff.Mode mode);
}
