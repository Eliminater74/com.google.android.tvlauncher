package android.support.p004v7.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p001v4.content.res.ResourcesCompat;
import android.support.p001v4.widget.AutoSizeableTextView;
import android.support.p001v4.widget.TextViewCompat;
import android.support.p004v7.appcompat.C0233R;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Locale;

/* renamed from: android.support.v7.widget.AppCompatTextHelper */
class AppCompatTextHelper {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    private static final int TEXT_FONT_WEIGHT_UNSPECIFIED = -1;
    @NonNull
    private final AppCompatTextViewAutoSizeHelper mAutoSizeTextHelper;
    private final TextView mView;
    private boolean mAsyncFontPending;
    private TintInfo mDrawableBottomTint;
    private TintInfo mDrawableEndTint;
    private TintInfo mDrawableLeftTint;
    private TintInfo mDrawableRightTint;
    private TintInfo mDrawableStartTint;
    private TintInfo mDrawableTint;
    private TintInfo mDrawableTopTint;
    private Typeface mFontTypeface;
    private int mFontWeight = -1;
    private int mStyle = 0;

    AppCompatTextHelper(TextView view) {
        this.mView = view;
        this.mAutoSizeTextHelper = new AppCompatTextViewAutoSizeHelper(this.mView);
    }

    private static TintInfo createTintInfo(Context context, AppCompatDrawableManager drawableManager, int drawableId) {
        ColorStateList tintList = drawableManager.getTintList(context, drawableId);
        if (tintList == null) {
            return null;
        }
        TintInfo tintInfo = new TintInfo();
        tintInfo.mHasTintList = true;
        tintInfo.mTintList = tintList;
        return tintInfo;
    }

    /* access modifiers changed from: package-private */
    @SuppressLint({"NewApi"})
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        boolean allCapsSet;
        boolean allCapsSet2;
        ColorStateList textColorLink;
        ColorStateList textColor;
        String localeListString;
        AppCompatDrawableManager drawableManager;
        Drawable drawableLeft;
        AppCompatDrawableManager drawableManager2;
        Drawable drawableBottom;
        AttributeSet attributeSet = attrs;
        int i = defStyleAttr;
        Context context = this.mView.getContext();
        AppCompatDrawableManager drawableManager3 = AppCompatDrawableManager.get();
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0233R.styleable.AppCompatTextHelper, i, 0);
        int ap = a.getResourceId(C0233R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (a.hasValue(C0233R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.mDrawableLeftTint = createTintInfo(context, drawableManager3, a.getResourceId(C0233R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (a.hasValue(C0233R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.mDrawableTopTint = createTintInfo(context, drawableManager3, a.getResourceId(C0233R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (a.hasValue(C0233R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.mDrawableRightTint = createTintInfo(context, drawableManager3, a.getResourceId(C0233R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (a.hasValue(C0233R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.mDrawableBottomTint = createTintInfo(context, drawableManager3, a.getResourceId(C0233R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (a.hasValue(C0233R.styleable.AppCompatTextHelper_android_drawableStart)) {
                this.mDrawableStartTint = createTintInfo(context, drawableManager3, a.getResourceId(C0233R.styleable.AppCompatTextHelper_android_drawableStart, 0));
            }
            if (a.hasValue(C0233R.styleable.AppCompatTextHelper_android_drawableEnd)) {
                this.mDrawableEndTint = createTintInfo(context, drawableManager3, a.getResourceId(C0233R.styleable.AppCompatTextHelper_android_drawableEnd, 0));
            }
        }
        a.recycle();
        boolean hasPwdTm = this.mView.getTransformationMethod() instanceof PasswordTransformationMethod;
        boolean allCaps = false;
        boolean allCapsSet3 = false;
        ColorStateList textColor2 = null;
        ColorStateList textColorHint = null;
        ColorStateList textColorLink2 = null;
        String fontVariation = null;
        String localeListString2 = null;
        if (ap != -1) {
            TintTypedArray a2 = TintTypedArray.obtainStyledAttributes(context, ap, C0233R.styleable.TextAppearance);
            if (!hasPwdTm && a2.hasValue(C0233R.styleable.TextAppearance_textAllCaps)) {
                allCapsSet3 = true;
                allCaps = a2.getBoolean(C0233R.styleable.TextAppearance_textAllCaps, false);
            }
            updateTypefaceAndStyle(context, a2);
            if (Build.VERSION.SDK_INT < 23) {
                if (a2.hasValue(C0233R.styleable.TextAppearance_android_textColor)) {
                    textColor2 = a2.getColorStateList(C0233R.styleable.TextAppearance_android_textColor);
                }
                if (a2.hasValue(C0233R.styleable.TextAppearance_android_textColorHint)) {
                    textColorHint = a2.getColorStateList(C0233R.styleable.TextAppearance_android_textColorHint);
                }
                if (a2.hasValue(C0233R.styleable.TextAppearance_android_textColorLink)) {
                    textColorLink2 = a2.getColorStateList(C0233R.styleable.TextAppearance_android_textColorLink);
                }
            }
            if (a2.hasValue(C0233R.styleable.TextAppearance_textLocale)) {
                localeListString2 = a2.getString(C0233R.styleable.TextAppearance_textLocale);
            }
            if (Build.VERSION.SDK_INT >= 26 && a2.hasValue(C0233R.styleable.TextAppearance_fontVariationSettings)) {
                fontVariation = a2.getString(C0233R.styleable.TextAppearance_fontVariationSettings);
            }
            a2.recycle();
        }
        TintTypedArray a3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0233R.styleable.TextAppearance, i, 0);
        if (hasPwdTm || !a3.hasValue(C0233R.styleable.TextAppearance_textAllCaps)) {
            allCapsSet = allCapsSet3;
            allCapsSet2 = allCaps;
        } else {
            allCapsSet = true;
            allCapsSet2 = a3.getBoolean(C0233R.styleable.TextAppearance_textAllCaps, false);
        }
        if (Build.VERSION.SDK_INT < 23) {
            if (a3.hasValue(C0233R.styleable.TextAppearance_android_textColor)) {
                textColor2 = a3.getColorStateList(C0233R.styleable.TextAppearance_android_textColor);
            }
            if (a3.hasValue(C0233R.styleable.TextAppearance_android_textColorHint)) {
                textColorHint = a3.getColorStateList(C0233R.styleable.TextAppearance_android_textColorHint);
            }
            if (a3.hasValue(C0233R.styleable.TextAppearance_android_textColorLink)) {
                textColorLink = textColor2;
                textColor = a3.getColorStateList(C0233R.styleable.TextAppearance_android_textColorLink);
            } else {
                ColorStateList colorStateList = textColorLink2;
                textColorLink = textColor2;
                textColor = colorStateList;
            }
        } else {
            ColorStateList colorStateList2 = textColorLink2;
            textColorLink = textColor2;
            textColor = colorStateList2;
        }
        if (a3.hasValue(C0233R.styleable.TextAppearance_textLocale)) {
            localeListString = a3.getString(C0233R.styleable.TextAppearance_textLocale);
        } else {
            localeListString = localeListString2;
        }
        if (Build.VERSION.SDK_INT >= 26 && a3.hasValue(C0233R.styleable.TextAppearance_fontVariationSettings)) {
            fontVariation = a3.getString(C0233R.styleable.TextAppearance_fontVariationSettings);
        }
        if (Build.VERSION.SDK_INT < 28) {
            drawableManager = drawableManager3;
        } else if (!a3.hasValue(C0233R.styleable.TextAppearance_android_textSize)) {
            drawableManager = drawableManager3;
        } else if (a3.getDimensionPixelSize(C0233R.styleable.TextAppearance_android_textSize, -1) == 0) {
            drawableManager = drawableManager3;
            this.mView.setTextSize(0, 0.0f);
        } else {
            drawableManager = drawableManager3;
        }
        updateTypefaceAndStyle(context, a3);
        a3.recycle();
        if (textColorLink != null) {
            this.mView.setTextColor(textColorLink);
        }
        if (textColorHint != null) {
            this.mView.setHintTextColor(textColorHint);
        }
        if (textColor != null) {
            this.mView.setLinkTextColor(textColor);
        }
        if (!hasPwdTm && allCapsSet) {
            setAllCaps(allCapsSet2);
        }
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            if (this.mFontWeight == -1) {
                this.mView.setTypeface(typeface, this.mStyle);
            } else {
                this.mView.setTypeface(typeface);
            }
        }
        if (fontVariation != null) {
            this.mView.setFontVariationSettings(fontVariation);
        }
        if (localeListString != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                this.mView.setTextLocales(LocaleList.forLanguageTags(localeListString));
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mView.setTextLocale(Locale.forLanguageTag(localeListString.substring(0, localeListString.indexOf(44))));
            }
        }
        this.mAutoSizeTextHelper.loadFromAttributes(attributeSet, i);
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            if (this.mAutoSizeTextHelper.getAutoSizeTextType() != 0) {
                int[] autoSizeTextSizesInPx = this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
                if (autoSizeTextSizesInPx.length > 0) {
                    if (((float) this.mView.getAutoSizeStepGranularity()) != -1.0f) {
                        this.mView.setAutoSizeTextTypeUniformWithConfiguration(this.mAutoSizeTextHelper.getAutoSizeMinTextSize(), this.mAutoSizeTextHelper.getAutoSizeMaxTextSize(), this.mAutoSizeTextHelper.getAutoSizeStepGranularity(), 0);
                    } else {
                        this.mView.setAutoSizeTextTypeUniformWithPresetSizes(autoSizeTextSizesInPx, 0);
                    }
                }
            }
        }
        TintTypedArray a4 = TintTypedArray.obtainStyledAttributes(context, attributeSet, C0233R.styleable.AppCompatTextView);
        Drawable drawableStart = null;
        Drawable drawableEnd = null;
        Drawable drawableTop = null;
        int drawableLeftId = a4.getResourceId(C0233R.styleable.AppCompatTextView_drawableLeftCompat, -1);
        if (drawableLeftId != -1) {
            drawableManager2 = drawableManager;
            drawableLeft = drawableManager2.getDrawable(context, drawableLeftId);
        } else {
            drawableManager2 = drawableManager;
            drawableLeft = null;
        }
        Drawable drawableRight = null;
        int drawableTopId = a4.getResourceId(C0233R.styleable.AppCompatTextView_drawableTopCompat, -1);
        if (drawableTopId != -1) {
            drawableTop = drawableManager2.getDrawable(context, drawableTopId);
        }
        int drawableRightId = a4.getResourceId(C0233R.styleable.AppCompatTextView_drawableRightCompat, -1);
        if (drawableRightId != -1) {
            drawableRight = drawableManager2.getDrawable(context, drawableRightId);
        }
        int drawableBottomId = a4.getResourceId(C0233R.styleable.AppCompatTextView_drawableBottomCompat, -1);
        if (drawableBottomId != -1) {
            drawableBottom = drawableManager2.getDrawable(context, drawableBottomId);
        } else {
            drawableBottom = null;
        }
        int drawableStartId = a4.getResourceId(C0233R.styleable.AppCompatTextView_drawableStartCompat, -1);
        if (drawableStartId != -1) {
            drawableStart = drawableManager2.getDrawable(context, drawableStartId);
        }
        int drawableEndId = a4.getResourceId(C0233R.styleable.AppCompatTextView_drawableEndCompat, -1);
        if (drawableEndId != -1) {
            drawableEnd = drawableManager2.getDrawable(context, drawableEndId);
        }
        setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom, drawableStart, drawableEnd);
        if (a4.hasValue(C0233R.styleable.AppCompatTextView_drawableTint)) {
            TextViewCompat.setCompoundDrawableTintList(this.mView, a4.getColorStateList(C0233R.styleable.AppCompatTextView_drawableTint));
        }
        if (a4.hasValue(C0233R.styleable.AppCompatTextView_drawableTintMode)) {
            TextViewCompat.setCompoundDrawableTintMode(this.mView, DrawableUtils.parseTintMode(a4.getInt(C0233R.styleable.AppCompatTextView_drawableTintMode, -1), null));
        }
        int firstBaselineToTopHeight = a4.getDimensionPixelSize(C0233R.styleable.AppCompatTextView_firstBaselineToTopHeight, -1);
        int lastBaselineToBottomHeight = a4.getDimensionPixelSize(C0233R.styleable.AppCompatTextView_lastBaselineToBottomHeight, -1);
        int lineHeight = a4.getDimensionPixelSize(C0233R.styleable.AppCompatTextView_lineHeight, -1);
        a4.recycle();
        if (firstBaselineToTopHeight != -1) {
            TextViewCompat.setFirstBaselineToTopHeight(this.mView, firstBaselineToTopHeight);
        }
        if (lastBaselineToBottomHeight != -1) {
            TextViewCompat.setLastBaselineToBottomHeight(this.mView, lastBaselineToBottomHeight);
        }
        if (lineHeight != -1) {
            TextViewCompat.setLineHeight(this.mView, lineHeight);
        }
    }

    private void updateTypefaceAndStyle(Context context, TintTypedArray a) {
        String fontFamilyName;
        TintTypedArray tintTypedArray = a;
        this.mStyle = tintTypedArray.getInt(C0233R.styleable.TextAppearance_android_textStyle, this.mStyle);
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 28) {
            this.mFontWeight = tintTypedArray.getInt(C0233R.styleable.TextAppearance_android_textFontWeight, -1);
            if (this.mFontWeight != -1) {
                this.mStyle = (this.mStyle & 2) | 0;
            }
        }
        if (tintTypedArray.hasValue(C0233R.styleable.TextAppearance_android_fontFamily) || tintTypedArray.hasValue(C0233R.styleable.TextAppearance_fontFamily)) {
            this.mFontTypeface = null;
            int fontFamilyId = tintTypedArray.hasValue(C0233R.styleable.TextAppearance_fontFamily) ? C0233R.styleable.TextAppearance_fontFamily : C0233R.styleable.TextAppearance_android_fontFamily;
            final int fontWeight = this.mFontWeight;
            final int style = this.mStyle;
            if (!context.isRestricted()) {
                final WeakReference<TextView> textViewWeak = new WeakReference<>(this.mView);
                try {
                    Typeface typeface = tintTypedArray.getFont(fontFamilyId, this.mStyle, new ResourcesCompat.FontCallback() {
                        public void onFontRetrieved(@NonNull Typeface typeface) {
                            int i;
                            if (Build.VERSION.SDK_INT >= 28 && (i = fontWeight) != -1) {
                                typeface = Typeface.create(typeface, i, (style & 2) != 0);
                            }
                            AppCompatTextHelper.this.onAsyncTypefaceReceived(textViewWeak, typeface);
                        }

                        public void onFontRetrievalFailed(int reason) {
                        }
                    });
                    if (typeface != null) {
                        if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                            this.mFontTypeface = typeface;
                        } else {
                            this.mFontTypeface = Typeface.create(Typeface.create(typeface, 0), this.mFontWeight, (this.mStyle & 2) != 0);
                        }
                    }
                    this.mAsyncFontPending = this.mFontTypeface == null;
                } catch (Resources.NotFoundException | UnsupportedOperationException e) {
                }
            }
            if (this.mFontTypeface == null && (fontFamilyName = tintTypedArray.getString(fontFamilyId)) != null) {
                if (Build.VERSION.SDK_INT < 28 || this.mFontWeight == -1) {
                    this.mFontTypeface = Typeface.create(fontFamilyName, this.mStyle);
                    return;
                }
                Typeface create = Typeface.create(fontFamilyName, 0);
                int i = this.mFontWeight;
                if ((2 & this.mStyle) != 0) {
                    z = true;
                }
                this.mFontTypeface = Typeface.create(create, i, z);
            }
        } else if (tintTypedArray.hasValue(C0233R.styleable.TextAppearance_android_typeface)) {
            this.mAsyncFontPending = false;
            int typefaceIndex = tintTypedArray.getInt(C0233R.styleable.TextAppearance_android_typeface, 1);
            if (typefaceIndex == 1) {
                this.mFontTypeface = Typeface.SANS_SERIF;
            } else if (typefaceIndex == 2) {
                this.mFontTypeface = Typeface.SERIF;
            } else if (typefaceIndex == 3) {
                this.mFontTypeface = Typeface.MONOSPACE;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onAsyncTypefaceReceived(WeakReference<TextView> textViewWeak, Typeface typeface) {
        if (this.mAsyncFontPending) {
            this.mFontTypeface = typeface;
            TextView textView = textViewWeak.get();
            if (textView != null) {
                textView.setTypeface(typeface, this.mStyle);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onSetTextAppearance(Context context, int resId) {
        String fontVariation;
        ColorStateList textColor;
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, resId, C0233R.styleable.TextAppearance);
        if (a.hasValue(C0233R.styleable.TextAppearance_textAllCaps)) {
            setAllCaps(a.getBoolean(C0233R.styleable.TextAppearance_textAllCaps, false));
        }
        if (Build.VERSION.SDK_INT < 23 && a.hasValue(C0233R.styleable.TextAppearance_android_textColor) && (textColor = a.getColorStateList(C0233R.styleable.TextAppearance_android_textColor)) != null) {
            this.mView.setTextColor(textColor);
        }
        if (a.hasValue(C0233R.styleable.TextAppearance_android_textSize) && a.getDimensionPixelSize(C0233R.styleable.TextAppearance_android_textSize, -1) == 0) {
            this.mView.setTextSize(0, 0.0f);
        }
        updateTypefaceAndStyle(context, a);
        if (Build.VERSION.SDK_INT >= 26 && a.hasValue(C0233R.styleable.TextAppearance_fontVariationSettings) && (fontVariation = a.getString(C0233R.styleable.TextAppearance_fontVariationSettings)) != null) {
            this.mView.setFontVariationSettings(fontVariation);
        }
        a.recycle();
        Typeface typeface = this.mFontTypeface;
        if (typeface != null) {
            this.mView.setTypeface(typeface, this.mStyle);
        }
    }

    /* access modifiers changed from: package-private */
    public void setAllCaps(boolean allCaps) {
        this.mView.setAllCaps(allCaps);
    }

    /* access modifiers changed from: package-private */
    public void onSetCompoundDrawables() {
        applyCompoundDrawablesTints();
    }

    /* access modifiers changed from: package-private */
    public void applyCompoundDrawablesTints() {
        if (!(this.mDrawableLeftTint == null && this.mDrawableTopTint == null && this.mDrawableRightTint == null && this.mDrawableBottomTint == null)) {
            Drawable[] compoundDrawables = this.mView.getCompoundDrawables();
            applyCompoundDrawableTint(compoundDrawables[0], this.mDrawableLeftTint);
            applyCompoundDrawableTint(compoundDrawables[1], this.mDrawableTopTint);
            applyCompoundDrawableTint(compoundDrawables[2], this.mDrawableRightTint);
            applyCompoundDrawableTint(compoundDrawables[3], this.mDrawableBottomTint);
        }
        if (Build.VERSION.SDK_INT < 17) {
            return;
        }
        if (this.mDrawableStartTint != null || this.mDrawableEndTint != null) {
            Drawable[] compoundDrawables2 = this.mView.getCompoundDrawablesRelative();
            applyCompoundDrawableTint(compoundDrawables2[0], this.mDrawableStartTint);
            applyCompoundDrawableTint(compoundDrawables2[2], this.mDrawableEndTint);
        }
    }

    private void applyCompoundDrawableTint(Drawable drawable, TintInfo info) {
        if (drawable != null && info != null) {
            AppCompatDrawableManager.tintDrawable(drawable, info, this.mView.getDrawableState());
        }
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            autoSizeText();
        }
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void setTextSize(int unit, float size) {
        if (!AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE && !isAutoSizeEnabled()) {
            setTextSizeInternal(unit, size);
        }
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void autoSizeText() {
        this.mAutoSizeTextHelper.autoSizeText();
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public boolean isAutoSizeEnabled() {
        return this.mAutoSizeTextHelper.isAutoSizeEnabled();
    }

    private void setTextSizeInternal(int unit, float size) {
        this.mAutoSizeTextHelper.setTextSizeInternal(unit, size);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeUniformWithConfiguration(int autoSizeMinTextSize, int autoSizeMaxTextSize, int autoSizeStepGranularity, int unit) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithConfiguration(autoSizeMinTextSize, autoSizeMaxTextSize, autoSizeStepGranularity, unit);
    }

    /* access modifiers changed from: package-private */
    public void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull int[] presetSizes, int unit) throws IllegalArgumentException {
        this.mAutoSizeTextHelper.setAutoSizeTextTypeUniformWithPresetSizes(presetSizes, unit);
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeTextType() {
        return this.mAutoSizeTextHelper.getAutoSizeTextType();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeStepGranularity() {
        return this.mAutoSizeTextHelper.getAutoSizeStepGranularity();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeMinTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMinTextSize();
    }

    /* access modifiers changed from: package-private */
    public int getAutoSizeMaxTextSize() {
        return this.mAutoSizeTextHelper.getAutoSizeMaxTextSize();
    }

    /* access modifiers changed from: package-private */
    public int[] getAutoSizeTextAvailableSizes() {
        return this.mAutoSizeTextHelper.getAutoSizeTextAvailableSizes();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ColorStateList getCompoundDrawableTintList() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintList;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCompoundDrawableTintList(@Nullable ColorStateList tintList) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintList = tintList;
        tintInfo.mHasTintList = tintList != null;
        setCompoundTints();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public PorterDuff.Mode getCompoundDrawableTintMode() {
        TintInfo tintInfo = this.mDrawableTint;
        if (tintInfo != null) {
            return tintInfo.mTintMode;
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void setCompoundDrawableTintMode(@Nullable PorterDuff.Mode tintMode) {
        if (this.mDrawableTint == null) {
            this.mDrawableTint = new TintInfo();
        }
        TintInfo tintInfo = this.mDrawableTint;
        tintInfo.mTintMode = tintMode;
        tintInfo.mHasTintMode = tintMode != null;
        setCompoundTints();
    }

    private void setCompoundTints() {
        TintInfo tintInfo = this.mDrawableTint;
        this.mDrawableLeftTint = tintInfo;
        this.mDrawableTopTint = tintInfo;
        this.mDrawableRightTint = tintInfo;
        this.mDrawableBottomTint = tintInfo;
        this.mDrawableStartTint = tintInfo;
        this.mDrawableEndTint = tintInfo;
    }

    private void setCompoundDrawables(Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom, Drawable drawableStart, Drawable drawableEnd) {
        if (Build.VERSION.SDK_INT >= 17 && (drawableStart != null || drawableEnd != null)) {
            Drawable[] existingRel = this.mView.getCompoundDrawablesRelative();
            this.mView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawableStart != null ? drawableStart : existingRel[0], drawableTop != null ? drawableTop : existingRel[1], drawableEnd != null ? drawableEnd : existingRel[2], drawableBottom != null ? drawableBottom : existingRel[3]);
        } else if (drawableLeft != null || drawableTop != null || drawableRight != null || drawableBottom != null) {
            if (Build.VERSION.SDK_INT >= 17) {
                Drawable[] existingRel2 = this.mView.getCompoundDrawablesRelative();
                if (!(existingRel2[0] == null && existingRel2[2] == null)) {
                    this.mView.setCompoundDrawablesRelativeWithIntrinsicBounds(existingRel2[0], drawableTop != null ? drawableTop : existingRel2[1], existingRel2[2], drawableBottom != null ? drawableBottom : existingRel2[3]);
                    return;
                }
            }
            Drawable[] existingAbs = this.mView.getCompoundDrawables();
            this.mView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft != null ? drawableLeft : existingAbs[0], drawableTop != null ? drawableTop : existingAbs[1], drawableRight != null ? drawableRight : existingAbs[2], drawableBottom != null ? drawableBottom : existingAbs[3]);
        }
    }
}
