package android.support.p004v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.p001v4.graphics.ColorUtils;
import android.support.p004v7.appcompat.C0233R;
import android.support.p004v7.content.res.AppCompatResources;
import android.support.p004v7.widget.ResourceManagerInternal;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* renamed from: android.support.v7.widget.AppCompatDrawableManager */
public final class AppCompatDrawableManager {
    private static final boolean DEBUG = false;
    /* access modifiers changed from: private */
    public static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    private static AppCompatDrawableManager INSTANCE = null;
    private static final String TAG = "AppCompatDrawableManag";
    private ResourceManagerInternal mResourceManager;

    public static synchronized void preload() {
        synchronized (AppCompatDrawableManager.class) {
            if (INSTANCE == null) {
                INSTANCE = new AppCompatDrawableManager();
                INSTANCE.mResourceManager = ResourceManagerInternal.get();
                INSTANCE.mResourceManager.setHooks(new ResourceManagerInternal.ResourceManagerHooks() {
                    private final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY = {C0233R.C0234drawable.abc_popup_background_mtrl_mult, C0233R.C0234drawable.abc_cab_background_internal_bg, C0233R.C0234drawable.abc_menu_hardkey_panel_mtrl_mult};
                    private final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED = {C0233R.C0234drawable.abc_textfield_activated_mtrl_alpha, C0233R.C0234drawable.abc_textfield_search_activated_mtrl_alpha, C0233R.C0234drawable.abc_cab_background_top_mtrl_alpha, C0233R.C0234drawable.abc_text_cursor_material, C0233R.C0234drawable.abc_text_select_handle_left_mtrl_dark, C0233R.C0234drawable.abc_text_select_handle_middle_mtrl_dark, C0233R.C0234drawable.abc_text_select_handle_right_mtrl_dark, C0233R.C0234drawable.abc_text_select_handle_left_mtrl_light, C0233R.C0234drawable.abc_text_select_handle_middle_mtrl_light, C0233R.C0234drawable.abc_text_select_handle_right_mtrl_light};
                    private final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL = {C0233R.C0234drawable.abc_textfield_search_default_mtrl_alpha, C0233R.C0234drawable.abc_textfield_default_mtrl_alpha, C0233R.C0234drawable.abc_ab_share_pack_mtrl_alpha};
                    private final int[] TINT_CHECKABLE_BUTTON_LIST = {C0233R.C0234drawable.abc_btn_check_material, C0233R.C0234drawable.abc_btn_radio_material, C0233R.C0234drawable.abc_btn_check_material_anim, C0233R.C0234drawable.abc_btn_radio_material_anim};
                    private final int[] TINT_COLOR_CONTROL_NORMAL = {C0233R.C0234drawable.abc_ic_commit_search_api_mtrl_alpha, C0233R.C0234drawable.abc_seekbar_tick_mark_material, C0233R.C0234drawable.abc_ic_menu_share_mtrl_alpha, C0233R.C0234drawable.abc_ic_menu_copy_mtrl_am_alpha, C0233R.C0234drawable.abc_ic_menu_cut_mtrl_alpha, C0233R.C0234drawable.abc_ic_menu_selectall_mtrl_alpha, C0233R.C0234drawable.abc_ic_menu_paste_mtrl_am_alpha};
                    private final int[] TINT_COLOR_CONTROL_STATE_LIST = {C0233R.C0234drawable.abc_tab_indicator_material, C0233R.C0234drawable.abc_textfield_search_material};

                    private ColorStateList createDefaultButtonColorStateList(@NonNull Context context) {
                        return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorButtonNormal));
                    }

                    private ColorStateList createBorderlessButtonColorStateList(@NonNull Context context) {
                        return createButtonColorStateList(context, 0);
                    }

                    private ColorStateList createColoredButtonColorStateList(@NonNull Context context) {
                        return createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorAccent));
                    }

                    private ColorStateList createButtonColorStateList(@NonNull Context context, @ColorInt int baseColor) {
                        int[][] states = new int[4][];
                        int[] colors = new int[4];
                        int colorControlHighlight = ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorControlHighlight);
                        int disabledColor = ThemeUtils.getDisabledThemeAttrColor(context, C0233R.attr.colorButtonNormal);
                        states[0] = ThemeUtils.DISABLED_STATE_SET;
                        colors[0] = disabledColor;
                        int i = 0 + 1;
                        states[i] = ThemeUtils.PRESSED_STATE_SET;
                        colors[i] = ColorUtils.compositeColors(colorControlHighlight, baseColor);
                        int i2 = i + 1;
                        states[i2] = ThemeUtils.FOCUSED_STATE_SET;
                        colors[i2] = ColorUtils.compositeColors(colorControlHighlight, baseColor);
                        int i3 = i2 + 1;
                        states[i3] = ThemeUtils.EMPTY_STATE_SET;
                        colors[i3] = baseColor;
                        int i4 = i3 + 1;
                        return new ColorStateList(states, colors);
                    }

                    private ColorStateList createSwitchThumbColorStateList(Context context) {
                        int[][] states = new int[3][];
                        int[] colors = new int[3];
                        ColorStateList thumbColor = ThemeUtils.getThemeAttrColorStateList(context, C0233R.attr.colorSwitchThumbNormal);
                        if (thumbColor == null || !thumbColor.isStateful()) {
                            states[0] = ThemeUtils.DISABLED_STATE_SET;
                            colors[0] = ThemeUtils.getDisabledThemeAttrColor(context, C0233R.attr.colorSwitchThumbNormal);
                            int i = 0 + 1;
                            states[i] = ThemeUtils.CHECKED_STATE_SET;
                            colors[i] = ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorControlActivated);
                            int i2 = i + 1;
                            states[i2] = ThemeUtils.EMPTY_STATE_SET;
                            colors[i2] = ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorSwitchThumbNormal);
                            int i3 = i2 + 1;
                        } else {
                            states[0] = ThemeUtils.DISABLED_STATE_SET;
                            colors[0] = thumbColor.getColorForState(states[0], 0);
                            int i4 = 0 + 1;
                            states[i4] = ThemeUtils.CHECKED_STATE_SET;
                            colors[i4] = ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorControlActivated);
                            int i5 = i4 + 1;
                            states[i5] = ThemeUtils.EMPTY_STATE_SET;
                            colors[i5] = thumbColor.getDefaultColor();
                            int i6 = i5 + 1;
                        }
                        return new ColorStateList(states, colors);
                    }

                    public Drawable createDrawableFor(@NonNull ResourceManagerInternal resourceManager, @NonNull Context context, int resId) {
                        if (resId != C0233R.C0234drawable.abc_cab_background_top_material) {
                            return null;
                        }
                        return new LayerDrawable(new Drawable[]{resourceManager.getDrawable(context, C0233R.C0234drawable.abc_cab_background_internal_bg), resourceManager.getDrawable(context, C0233R.C0234drawable.abc_cab_background_top_mtrl_alpha)});
                    }

                    private void setPorterDuffColorFilter(Drawable d, int color, PorterDuff.Mode mode) {
                        if (DrawableUtils.canSafelyMutateDrawable(d)) {
                            d = d.mutate();
                        }
                        d.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(color, mode == null ? AppCompatDrawableManager.DEFAULT_MODE : mode));
                    }

                    public boolean tintDrawable(@NonNull Context context, int resId, @NonNull Drawable drawable) {
                        if (resId == C0233R.C0234drawable.abc_seekbar_track_material) {
                            LayerDrawable ld = (LayerDrawable) drawable;
                            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorControlNormal), AppCompatDrawableManager.DEFAULT_MODE);
                            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorControlNormal), AppCompatDrawableManager.DEFAULT_MODE);
                            setPorterDuffColorFilter(ld.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorControlActivated), AppCompatDrawableManager.DEFAULT_MODE);
                            return true;
                        } else if (resId != C0233R.C0234drawable.abc_ratingbar_material && resId != C0233R.C0234drawable.abc_ratingbar_indicator_material && resId != C0233R.C0234drawable.abc_ratingbar_small_material) {
                            return false;
                        } else {
                            LayerDrawable ld2 = (LayerDrawable) drawable;
                            setPorterDuffColorFilter(ld2.findDrawableByLayerId(16908288), ThemeUtils.getDisabledThemeAttrColor(context, C0233R.attr.colorControlNormal), AppCompatDrawableManager.DEFAULT_MODE);
                            setPorterDuffColorFilter(ld2.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorControlActivated), AppCompatDrawableManager.DEFAULT_MODE);
                            setPorterDuffColorFilter(ld2.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, C0233R.attr.colorControlActivated), AppCompatDrawableManager.DEFAULT_MODE);
                            return true;
                        }
                    }

                    private boolean arrayContains(int[] array, int value) {
                        for (int id : array) {
                            if (id == value) {
                                return true;
                            }
                        }
                        return false;
                    }

                    public ColorStateList getTintListForDrawableRes(@NonNull Context context, int resId) {
                        if (resId == C0233R.C0234drawable.abc_edit_text_material) {
                            return AppCompatResources.getColorStateList(context, C0233R.color.abc_tint_edittext);
                        }
                        if (resId == C0233R.C0234drawable.abc_switch_track_mtrl_alpha) {
                            return AppCompatResources.getColorStateList(context, C0233R.color.abc_tint_switch_track);
                        }
                        if (resId == C0233R.C0234drawable.abc_switch_thumb_material) {
                            return createSwitchThumbColorStateList(context);
                        }
                        if (resId == C0233R.C0234drawable.abc_btn_default_mtrl_shape) {
                            return createDefaultButtonColorStateList(context);
                        }
                        if (resId == C0233R.C0234drawable.abc_btn_borderless_material) {
                            return createBorderlessButtonColorStateList(context);
                        }
                        if (resId == C0233R.C0234drawable.abc_btn_colored_material) {
                            return createColoredButtonColorStateList(context);
                        }
                        if (resId == C0233R.C0234drawable.abc_spinner_mtrl_am_alpha || resId == C0233R.C0234drawable.abc_spinner_textfield_background_material) {
                            return AppCompatResources.getColorStateList(context, C0233R.color.abc_tint_spinner);
                        }
                        if (arrayContains(this.TINT_COLOR_CONTROL_NORMAL, resId)) {
                            return ThemeUtils.getThemeAttrColorStateList(context, C0233R.attr.colorControlNormal);
                        }
                        if (arrayContains(this.TINT_COLOR_CONTROL_STATE_LIST, resId)) {
                            return AppCompatResources.getColorStateList(context, C0233R.color.abc_tint_default);
                        }
                        if (arrayContains(this.TINT_CHECKABLE_BUTTON_LIST, resId)) {
                            return AppCompatResources.getColorStateList(context, C0233R.color.abc_tint_btn_checkable);
                        }
                        if (resId == C0233R.C0234drawable.abc_seekbar_thumb_material) {
                            return AppCompatResources.getColorStateList(context, C0233R.color.abc_tint_seek_thumb);
                        }
                        return null;
                    }

                    public boolean tintDrawableUsingColorFilter(@NonNull Context context, int resId, @NonNull Drawable drawable) {
                        PorterDuff.Mode tintMode = AppCompatDrawableManager.DEFAULT_MODE;
                        boolean colorAttrSet = false;
                        int colorAttr = 0;
                        int alpha = -1;
                        if (arrayContains(this.COLORFILTER_TINT_COLOR_CONTROL_NORMAL, resId)) {
                            colorAttr = C0233R.attr.colorControlNormal;
                            colorAttrSet = true;
                        } else if (arrayContains(this.COLORFILTER_COLOR_CONTROL_ACTIVATED, resId)) {
                            colorAttr = C0233R.attr.colorControlActivated;
                            colorAttrSet = true;
                        } else if (arrayContains(this.COLORFILTER_COLOR_BACKGROUND_MULTIPLY, resId)) {
                            colorAttr = 16842801;
                            colorAttrSet = true;
                            tintMode = PorterDuff.Mode.MULTIPLY;
                        } else if (resId == C0233R.C0234drawable.abc_list_divider_mtrl_alpha) {
                            colorAttr = 16842800;
                            colorAttrSet = true;
                            alpha = Math.round(40.8f);
                        } else if (resId == C0233R.C0234drawable.abc_dialog_material_background) {
                            colorAttr = 16842801;
                            colorAttrSet = true;
                        }
                        if (!colorAttrSet) {
                            return false;
                        }
                        if (DrawableUtils.canSafelyMutateDrawable(drawable)) {
                            drawable = drawable.mutate();
                        }
                        drawable.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(context, colorAttr), tintMode));
                        if (alpha == -1) {
                            return true;
                        }
                        drawable.setAlpha(alpha);
                        return true;
                    }

                    public PorterDuff.Mode getTintModeForDrawableRes(int resId) {
                        if (resId == C0233R.C0234drawable.abc_switch_thumb_material) {
                            return PorterDuff.Mode.MULTIPLY;
                        }
                        return null;
                    }
                });
            }
        }
    }

    public static synchronized AppCompatDrawableManager get() {
        AppCompatDrawableManager appCompatDrawableManager;
        synchronized (AppCompatDrawableManager.class) {
            if (INSTANCE == null) {
                preload();
            }
            appCompatDrawableManager = INSTANCE;
        }
        return appCompatDrawableManager;
    }

    public synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes int resId) {
        return this.mResourceManager.getDrawable(context, resId);
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable getDrawable(@NonNull Context context, @DrawableRes int resId, boolean failIfNotKnown) {
        return this.mResourceManager.getDrawable(context, resId, failIfNotKnown);
    }

    public synchronized void onConfigurationChanged(@NonNull Context context) {
        this.mResourceManager.onConfigurationChanged(context);
    }

    /* access modifiers changed from: package-private */
    public synchronized Drawable onDrawableLoadedFromResources(@NonNull Context context, @NonNull VectorEnabledTintResources resources, @DrawableRes int resId) {
        return this.mResourceManager.onDrawableLoadedFromResources(context, resources, resId);
    }

    /* access modifiers changed from: package-private */
    public boolean tintDrawableUsingColorFilter(@NonNull Context context, @DrawableRes int resId, @NonNull Drawable drawable) {
        return this.mResourceManager.tintDrawableUsingColorFilter(context, resId, drawable);
    }

    /* access modifiers changed from: package-private */
    public synchronized ColorStateList getTintList(@NonNull Context context, @DrawableRes int resId) {
        return this.mResourceManager.getTintList(context, resId);
    }

    static void tintDrawable(Drawable drawable, TintInfo tint, int[] state) {
        ResourceManagerInternal.tintDrawable(drawable, tint, state);
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(int color, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        synchronized (AppCompatDrawableManager.class) {
            porterDuffColorFilter = ResourceManagerInternal.getPorterDuffColorFilter(color, mode);
        }
        return porterDuffColorFilter;
    }
}
