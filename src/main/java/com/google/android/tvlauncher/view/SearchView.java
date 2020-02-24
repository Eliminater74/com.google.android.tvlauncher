package com.google.android.tvlauncher.view;

import android.animation.Animator;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvrecommendations.shared.util.Constants;
import java.util.Random;

public class SearchView extends FrameLayout implements LaunchItemsManager.SearchPackageChangeListener {
    private static final String EXTRA_SEARCH_TYPE = "search_type";
    private static final int FOCUSED_KEYBOARD_TEXT = -3;
    private static final int FOCUSED_MIC_TEXT = -2;
    private static final int INIT_TEXT = -1;
    private static final int SEARCH_TYPE_KEYBOARD = 2;
    private static final int SEARCH_TYPE_VOICE = 1;
    private static final String TAG = "SearchView";
    private static final int TEXT_ANIM_FADE = 2;
    private static final int TEXT_ANIM_HORIZONTAL = 1;
    private static final int TEXT_ANIM_VERTICAL = 0;
    private ActionCallbacks mActionCallbacks;
    private Drawable mAssistantIcon;
    private int mClickDeviceId = -1;
    private int mColorBright;
    private Drawable mColorMicFocusedIcon;
    private Context mContext;
    /* access modifiers changed from: private */
    public int mCurrentIndex = 0;
    private String[] mDefaultTextToShow;
    private boolean mEatDpadCenterKeyDown;
    private final int mFocusedColor;
    private final String mFocusedKeyboardText;
    private final String mFocusedMicText;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private boolean mHotwordEnabled;
    private int mHotwordIconVisibility;
    /* access modifiers changed from: private */
    public final int mIdleTextFlipDelay;
    private boolean mIsHintFlippingAllowed;
    private boolean mKatnissExists;
    private FrameLayout mKeyboardContainer;
    private Drawable mKeyboardFocusedIcon;
    /* access modifiers changed from: private */
    public SearchOrb mKeyboardOrbView;
    private int mKeyboardOrbVisibility;
    private final int mKeyboardOrbWidth;
    private Drawable mKeyboardUnfocusedIcon;
    private final int mLaunchFadeDuration;
    private ImageView mMicDisabledIcon;
    private Drawable mMicFocusedIcon;
    /* access modifiers changed from: private */
    public SearchOrb mMicOrbView;
    private int mMicStatus;
    private Drawable mMicUnfocusedIcon;
    private int mOemFocusedOrbColor;
    /* access modifiers changed from: private */
    public Drawable mOemSearchIcon;
    private final String mSearchHintText;
    private final Intent mSearchIntent = getSearchIntent();
    private final int mSearchOrbsSpacing;
    private Runnable mSwitchRunnable;
    /* access modifiers changed from: private */
    public TextSwitcher mSwitcher;
    private LinearLayout mSwitcherContainer;
    private final int mTextSwitcherMarginStart;
    private final int mTextSwitcherWithHotwordIconMarginStart;
    /* access modifiers changed from: private */
    public String[] mTextToShow;
    private final int mUnfocusedColor;

    public interface ActionCallbacks {
        void onStartedKeyboardSearch();

        void onStartedVoiceSearch();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        boolean z = false;
        this.mContext = context;
        Resources res = context.getResources();
        this.mDefaultTextToShow = res.getStringArray(C1188R.array.search_orb_text_to_show);
        this.mIdleTextFlipDelay = res.getInteger(C1188R.integer.search_orb_idle_hint_flip_delay);
        this.mLaunchFadeDuration = res.getInteger(C1188R.integer.search_orb_text_fade_duration);
        this.mSearchHintText = fixItalics(context.getString(C1188R.string.search_hint_text));
        this.mFocusedMicText = fixItalics(context.getString(C1188R.string.focused_search_mic_hint_text));
        this.mFocusedKeyboardText = context.getString(C1188R.string.focused_search_keyboard_hint_text);
        this.mFocusedColor = ContextCompat.getColor(this.mContext, C1188R.color.search_orb_focused_hint_color);
        this.mUnfocusedColor = ContextCompat.getColor(this.mContext, C1188R.color.search_orb_unfocused_hint_color);
        this.mKatnissExists = isKatnissPackagePresent();
        if (res.getBoolean(C1188R.bool.is_hint_flipping_allowed) && this.mKatnissExists) {
            z = true;
        }
        this.mIsHintFlippingAllowed = z;
        this.mSearchOrbsSpacing = res.getDimensionPixelSize(C1188R.dimen.search_orbs_spacing);
        this.mKeyboardOrbWidth = res.getDimensionPixelSize(C1188R.dimen.top_row_item_size) + res.getDimensionPixelSize(C1188R.dimen.search_orb_icon_padding_end) + res.getDimensionPixelSize(C1188R.dimen.search_orb_keyboard_icon_padding_start);
        this.mTextSwitcherMarginStart = res.getDimensionPixelSize(C1188R.dimen.search_text_margin_start);
        this.mTextSwitcherWithHotwordIconMarginStart = ((res.getDimensionPixelSize(C1188R.dimen.mic_disabled_icon_size) + (res.getDimensionPixelSize(C1188R.dimen.mic_disabled_icon_margin) * 2)) - this.mTextSwitcherMarginStart) * -1;
        this.mColorMicFocusedIcon = res.getDrawable(C1188R.C1189drawable.ic_mic_color, null);
        this.mMicUnfocusedIcon = res.getDrawable(C1188R.C1189drawable.ic_mic_grey, null);
        this.mMicFocusedIcon = res.getDrawable(C1188R.C1189drawable.ic_mic_black, null);
        this.mTextToShow = this.mDefaultTextToShow;
    }

    public void registerActionsCallbacks(ActionCallbacks actionsCallbacks) {
        this.mActionCallbacks = actionsCallbacks;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isKatnissPackagePresent() {
        PackageInfo info;
        try {
            info = this.mContext.getPackageManager().getPackageInfo(Constants.SEARCH_APP_PACKAGE_NAME, 0);
        } catch (PackageManager.NameNotFoundException e) {
            info = null;
        }
        if (info != null) {
            return true;
        }
        return false;
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mMicDisabledIcon = (ImageView) findViewById(C1188R.C1191id.mic_disabled_icon);
        this.mSwitcherContainer = (LinearLayout) findViewById(C1188R.C1191id.search_container);
        this.mMicOrbView = (SearchOrb) findViewById(C1188R.C1191id.mic_orb);
        this.mKeyboardOrbView = (SearchOrb) findViewById(C1188R.C1191id.keyboard_orb);
        this.mKeyboardOrbVisibility = this.mKeyboardOrbView.getVisibility();
        this.mKeyboardContainer = (FrameLayout) findViewById(C1188R.C1191id.keyboard_orb_container);
        this.mSwitcherContainer.bringToFront();
        initializeSearchOrbs();
        initTextSwitcher(getContext());
        bind(false);
    }

    public void onSearchPackageChanged() {
        boolean katnissExists = isKatnissPackagePresent();
        if (katnissExists != this.mKatnissExists) {
            this.mKatnissExists = katnissExists;
            initializeSearchOrbs();
            setSearchState();
            return;
        }
        loadPartnerSearchIcon();
    }

    @VisibleForTesting
    public void loadPartnerSearchIcon() {
        int maxIconSize = this.mContext.getResources().getDimensionPixelSize(C1188R.dimen.search_orb_icon_max_size);
        Uri oemSearchIconUri = OemConfiguration.get(this.mContext).getCustomSearchIconUri();
        if (oemSearchIconUri != null) {
            Glide.with(this.mContext).asDrawable().load(oemSearchIconUri).into(new SimpleTarget<Drawable>(maxIconSize, maxIconSize) {
                public void onResourceReady(Drawable drawable, Transition transition) {
                    if (drawable != null) {
                        Drawable unused = SearchView.this.mOemSearchIcon = drawable;
                        SearchView.this.updateSearchOrbAppearance();
                    }
                }
            });
        }
    }

    private void initializeSearchOrbs() {
        this.mKeyboardFocusedIcon = ContextCompat.getDrawable(this.mContext, C1188R.C1189drawable.ic_keyboard_black);
        this.mKeyboardUnfocusedIcon = ContextCompat.getDrawable(this.mContext, C1188R.C1189drawable.ic_keyboard_grey);
        this.mColorBright = ContextCompat.getColor(this.mContext, C1188R.color.search_orb_bg_bright_color);
        this.mOemFocusedOrbColor = OemConfiguration.get(this.mContext).getSearchOrbFocusedColor(this.mColorBright);
        this.mKeyboardOrbView.setOrbIcon(this.mKeyboardUnfocusedIcon);
        this.mMicOrbView.setFocusedOrbColor(this.mColorBright);
        this.mKeyboardOrbView.setFocusedOrbColor(this.mColorBright);
        loadPartnerSearchIcon();
        updateSearchOrbAppearance();
    }

    @VisibleForTesting
    public void updateSearchOrbAppearance() {
        SearchOrb searchOrb = this.mKeyboardOrbView;
        boolean keyboardHasFocus = searchOrb != null && searchOrb.hasFocus();
        boolean micHasFocus = this.mMicOrbView.hasFocus();
        if (this.mKatnissExists) {
            this.mMicOrbView.setFocusedOrbColor(this.mColorBright);
            this.mKeyboardOrbView.setFocusedOrbColor(this.mColorBright);
            Drawable drawable = this.mAssistantIcon;
            if (drawable == null) {
                this.mMicOrbView.setOrbIcon(micHasFocus ? this.mColorMicFocusedIcon : this.mMicUnfocusedIcon);
            } else if (micHasFocus) {
                this.mMicOrbView.setOrbIcon(this.mColorMicFocusedIcon);
            } else if (keyboardHasFocus) {
                this.mMicOrbView.setOrbIcon(this.mMicUnfocusedIcon);
            } else {
                this.mMicOrbView.setOrbIcon(drawable);
            }
        } else {
            this.mMicOrbView.setFocusedOrbColor(this.mOemFocusedOrbColor);
            this.mKeyboardOrbView.setFocusedOrbColor(this.mOemFocusedOrbColor);
            Drawable drawable2 = this.mOemSearchIcon;
            if (drawable2 != null) {
                this.mMicOrbView.setOrbIcon(drawable2);
            } else {
                this.mMicOrbView.setOrbIcon(micHasFocus ? this.mMicFocusedIcon : this.mMicUnfocusedIcon);
            }
        }
    }

    public static int getColor(Resources res, int id, @Nullable Resources.Theme theme) {
        return res.getColor(id, theme);
    }

    private boolean focusIsOnSearchView() {
        return this.mMicOrbView.hasFocus() || this.mKeyboardOrbView.hasFocus();
    }

    private void setSearchState() {
        int i;
        this.mHandler.removeCallbacks(this.mSwitchRunnable);
        boolean focused = focusIsOnSearchView();
        int old = this.mCurrentIndex;
        boolean useFade = false;
        int i2 = 1;
        boolean isKeyboard = this.mKatnissExists && focused && !this.mMicOrbView.hasFocus();
        if (focused) {
            i = !isKeyboard ? -2 : -3;
        } else {
            i = -1;
        }
        this.mCurrentIndex = i;
        int i3 = this.mCurrentIndex;
        if (old != i3) {
            if (!(old == -1 || i3 == -1)) {
                useFade = true;
            }
            if (useFade) {
                i2 = 2;
            }
            configSwitcher(focused, i2);
            this.mSwitcher.setText(fixItalics(getHintText(focused, isKeyboard)));
        }
    }

    private String getHintText(boolean focused, boolean isKeyboard) {
        if (focused) {
            return isKeyboard ? this.mFocusedKeyboardText : this.mFocusedMicText;
        }
        return this.mSearchHintText;
    }

    private void initTextSwitcher(final Context context) {
        this.mSwitcher = (TextSwitcher) findViewById(C1188R.C1191id.text_switcher);
        this.mSwitcher.setAnimateFirstView(false);
        this.mSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            LayoutInflater inflater = ((LayoutInflater) context.getSystemService("layout_inflater"));

            /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
             method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
             arg types: [int, com.google.android.tvlauncher.view.SearchView, int]
             candidates:
              ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
              ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
            public View makeView() {
                return this.inflater.inflate(C1188R.layout.search_orb_text_hint, (ViewGroup) SearchView.this, false);
            }
        });
        this.mSwitchRunnable = new Runnable() {
            public void run() {
                int old = SearchView.this.mCurrentIndex;
                int unused = SearchView.this.mCurrentIndex = new Random().nextInt(SearchView.this.mTextToShow.length);
                if (old == SearchView.this.mCurrentIndex) {
                    SearchView searchView = SearchView.this;
                    int unused2 = searchView.mCurrentIndex = (searchView.mCurrentIndex + 1) % SearchView.this.mTextToShow.length;
                }
                SearchView.this.configSwitcher(false, 0);
                TextSwitcher access$400 = SearchView.this.mSwitcher;
                SearchView searchView2 = SearchView.this;
                access$400.setText(searchView2.fixItalics(searchView2.mTextToShow[SearchView.this.mCurrentIndex]));
                SearchView.this.mHandler.postDelayed(this, (long) SearchView.this.mIdleTextFlipDelay);
            }
        };
        reset();
    }

    public String fixItalics(String text) {
        if (text == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(text);
        if (getLayoutDirection() == 1) {
            builder.insert(0, " ");
        } else {
            builder.append(" ");
        }
        return builder.toString();
    }

    /* access modifiers changed from: private */
    public void configSwitcher(boolean focused, int animType) {
        int outAnim;
        int inAnim;
        View v = this.mSwitcher.getNextView();
        if (v instanceof TextView) {
            ((TextView) v).setTextColor(focused ? this.mFocusedColor : this.mUnfocusedColor);
        }
        if (animType == 1) {
            inAnim = C1188R.anim.slide_in_left;
            outAnim = C1188R.anim.slide_out_right;
        } else if (animType == 0) {
            inAnim = C1188R.anim.slide_in_bottom;
            outAnim = C1188R.anim.slide_out_top;
        } else {
            inAnim = C1188R.anim.fade_in;
            outAnim = C1188R.anim.fade_out;
        }
        this.mSwitcher.setInAnimation(this.mContext, inAnim);
        this.mSwitcher.setOutAnimation(this.mContext, outAnim);
    }

    private void updateMicDisabledIconVisibility() {
        if (this.mHotwordEnabled && this.mMicStatus == 2) {
            setHotwordIconVisibility(0);
            ViewGroup.MarginLayoutParams textParams = (ViewGroup.MarginLayoutParams) this.mSwitcher.getLayoutParams();
            textParams.setMarginStart(this.mTextSwitcherMarginStart);
            this.mSwitcher.setLayoutParams(textParams);
            return;
        }
        setHotwordIconVisibility(4);
        ViewGroup.MarginLayoutParams textParams2 = (ViewGroup.MarginLayoutParams) this.mSwitcher.getLayoutParams();
        textParams2.setMarginStart(this.mTextSwitcherWithHotwordIconMarginStart);
        this.mSwitcher.setLayoutParams(textParams2);
    }

    public void reset() {
        this.mHandler.removeCallbacks(this.mSwitchRunnable);
        this.mSwitcher.reset();
        this.mCurrentIndex = 0;
        setSearchState();
    }

    public void setIdleState(boolean isIdle) {
        if (this.mIsHintFlippingAllowed) {
            this.mHandler.removeCallbacks(this.mSwitchRunnable);
            if (isIdle && isAttachedToWindow() && isFullyOnScreen() && !this.mMicOrbView.hasFocus()) {
                this.mHandler.post(this.mSwitchRunnable);
            }
        }
    }

    @VisibleForTesting
    public boolean isFullyOnScreen() {
        Rect rect = new Rect();
        return getGlobalVisibleRect(rect) && getHeight() == rect.height() && getWidth() == rect.width();
    }

    private void setVisible(boolean visible) {
        animateVisibility(this.mSwitcher, visible);
    }

    private void animateVisibility(View view, boolean visible) {
        view.clearAnimation();
        float targetAlpha = visible ? 1.0f : 0.0f;
        if (view.getAlpha() != targetAlpha) {
            ViewPropertyAnimator anim = view.animate().alpha(targetAlpha).setDuration((long) this.mLaunchFadeDuration);
            if (!visible) {
                anim.setListener(new Animator.AnimatorListener() {
                    public void onAnimationStart(Animator animation) {
                    }

                    public void onAnimationEnd(Animator animation) {
                        if (SearchView.this.mKeyboardOrbView != null && SearchView.this.mKeyboardOrbView.hasFocus()) {
                            SearchView.this.mMicOrbView.requestFocus();
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                    }

                    public void onAnimationRepeat(Animator animation) {
                    }
                });
            }
            anim.start();
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        boolean isTouchExplorationEnabled = true;
        setVisible(true);
        AccessibilityManager am = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (!am.isEnabled() || !am.isTouchExplorationEnabled()) {
            isTouchExplorationEnabled = false;
        }
        View.OnClickListener listener = new SearchView$$Lambda$0(this, isTouchExplorationEnabled);
        this.mMicOrbView.setOnClickListener(listener);
        SearchOrb searchOrb = this.mKeyboardOrbView;
        if (searchOrb != null) {
            searchOrb.setOnClickListener(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onAttachedToWindow$0$SearchView(boolean isTouchExplorationEnabled, View view) {
        boolean success;
        SearchOrb searchOrb = this.mKeyboardOrbView;
        boolean isKeyboardSearch = searchOrb != null && searchOrb.hasFocus();
        if (isKeyboardSearch) {
            this.mActionCallbacks.onStartedKeyboardSearch();
        } else {
            this.mActionCallbacks.onStartedVoiceSearch();
        }
        if (isTouchExplorationEnabled) {
            success = startSearchActivitySafely(this.mContext, this.mSearchIntent, isKeyboardSearch);
        } else {
            success = startSearchActivitySafely(this.mContext, this.mSearchIntent, this.mClickDeviceId, isKeyboardSearch);
        }
        if (success) {
            reset();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        if (isConfirmKey(event.getKeyCode())) {
            if (event.isLongPress()) {
                this.mEatDpadCenterKeyDown = true;
                playErrorSound(getContext());
                return true;
            } else if (action == 1) {
                if (this.mEatDpadCenterKeyDown) {
                    this.mEatDpadCenterKeyDown = false;
                    return true;
                }
                this.mClickDeviceId = event.getDeviceId();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /* access modifiers changed from: protected */
    public void onVisibilityChanged(View changedView, int visibility) {
        if (changedView == this) {
            if (!(visibility == 0)) {
                reset();
            } else if (this.mKeyboardOrbView.hasFocus()) {
                this.mMicOrbView.requestFocus();
            }
        }
    }

    public static Intent getSearchIntent() {
        return new Intent("android.intent.action.ASSIST").addFlags(270532608);
    }

    private static boolean startActivitySafely(Context context, Intent intent) {
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            String valueOf = String.valueOf(intent);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 27);
            sb.append("Exception launching intent ");
            sb.append(valueOf);
            Log.e(TAG, sb.toString(), e);
            Toast.makeText(context, context.getString(C1188R.string.app_unavailable), 0).show();
            return false;
        }
    }

    private static boolean startSearchActivitySafely(Context context, Intent intent, int deviceId, boolean isKeyboardSearch) {
        intent.putExtra("android.intent.extra.ASSIST_INPUT_DEVICE_ID", deviceId);
        intent.putExtra(EXTRA_SEARCH_TYPE, isKeyboardSearch ? 2 : 1);
        return startActivitySafely(context, intent);
    }

    private static boolean startSearchActivitySafely(Context context, Intent intent, boolean isKeyboardSearch) {
        intent.putExtra(EXTRA_SEARCH_TYPE, isKeyboardSearch ? 2 : 1);
        return startActivitySafely(context, intent);
    }

    private static void playErrorSound(Context context) {
        ((AudioManager) context.getSystemService("audio")).playSoundEffect(9);
    }

    private static boolean isConfirmKey(int keyCode) {
        if (keyCode == 23 || keyCode == 62 || keyCode == 66 || keyCode == 96 || keyCode == 160) {
            return true;
        }
        return false;
    }

    public void updateSearchSuggestions(String[] suggestions) {
        this.mCurrentIndex = 0;
        if (suggestions == null || suggestions.length == 0) {
            this.mTextToShow = this.mDefaultTextToShow;
        } else {
            this.mTextToShow = suggestions;
        }
    }

    public void updateAssistantIcon(Drawable icon) {
        this.mAssistantIcon = icon;
        updateSearchOrbAppearance();
    }

    public void updateMicStatus(int status) {
        this.mMicStatus = status;
        updateMicDisabledIconVisibility();
    }

    public void updateHotwordEnabled(boolean enabled) {
        this.mHotwordEnabled = enabled;
        updateMicDisabledIconVisibility();
    }

    public void bind(boolean selected) {
        if (selected) {
            setKeyboardOrbVisibility(0);
            ViewGroup.MarginLayoutParams textParams = (ViewGroup.MarginLayoutParams) this.mSwitcherContainer.getLayoutParams();
            textParams.setMarginStart(0);
            this.mSwitcherContainer.setLayoutParams(textParams);
            this.mKeyboardOrbView.setScaleX(1.0f);
            this.mKeyboardOrbView.setScaleY(1.0f);
        } else {
            setKeyboardOrbVisibility(4);
            ViewGroup.MarginLayoutParams textParams2 = (ViewGroup.MarginLayoutParams) this.mSwitcherContainer.getLayoutParams();
            textParams2.setMarginStart((this.mSearchOrbsSpacing + this.mKeyboardOrbWidth) * -1);
            this.mSwitcherContainer.setLayoutParams(textParams2);
            this.mKeyboardOrbView.setScaleX(0.0f);
            this.mKeyboardOrbView.setScaleY(0.0f);
        }
        updateOrbFocusState(selected);
    }

    public void updateOrbFocusState(boolean topRowSelected) {
        setSearchState();
        if (topRowSelected) {
            if (this.mAssistantIcon != null || this.mKatnissExists) {
                this.mMicOrbView.setOrbIcon(this.mColorMicFocusedIcon);
            } else {
                Drawable drawable = this.mOemSearchIcon;
                if (drawable != null) {
                    this.mMicOrbView.setOrbIcon(drawable);
                } else {
                    SearchOrb searchOrb = this.mMicOrbView;
                    searchOrb.setOrbIcon(searchOrb.hasFocus() ? this.mMicFocusedIcon : this.mMicUnfocusedIcon);
                }
            }
            SearchOrb searchOrb2 = this.mKeyboardOrbView;
            searchOrb2.setOrbIcon(searchOrb2.hasFocus() ? this.mKeyboardFocusedIcon : this.mKeyboardUnfocusedIcon);
        } else {
            Drawable drawable2 = this.mAssistantIcon;
            if (drawable2 != null) {
                this.mMicOrbView.setOrbIcon(drawable2);
            } else if (this.mKatnissExists) {
                this.mMicOrbView.setOrbIcon(this.mColorMicFocusedIcon);
            } else {
                Drawable drawable3 = this.mOemSearchIcon;
                if (drawable3 != null) {
                    this.mMicOrbView.setOrbIcon(drawable3);
                } else {
                    this.mMicOrbView.setOrbIcon(this.mMicUnfocusedIcon);
                }
            }
        }
        this.mMicOrbView.bind();
        this.mKeyboardOrbView.bind();
    }

    public int getKeyboardOrbVisibility() {
        return this.mKeyboardOrbVisibility;
    }

    public void setKeyboardOrbVisibility(int visibility) {
        this.mKeyboardOrbVisibility = visibility;
        this.mKeyboardOrbView.setVisibility(visibility);
    }

    public int getHotwordIconVisibility() {
        return this.mHotwordIconVisibility;
    }

    public void setHotwordIconVisibility(int visibility) {
        this.mHotwordIconVisibility = visibility;
        this.mMicDisabledIcon.setVisibility(visibility);
    }

    @VisibleForTesting
    public String[] getTextToShow() {
        return this.mTextToShow;
    }

    @VisibleForTesting
    public String[] getDefaultTextToShow() {
        return this.mDefaultTextToShow;
    }

    @VisibleForTesting
    public Drawable getAssistantIcon() {
        return this.mAssistantIcon;
    }

    @VisibleForTesting
    public void setOemSearchIcon(Drawable oemSearchIcon) {
        this.mOemSearchIcon = oemSearchIcon;
    }

    public TextSwitcher getTextSwitcher() {
        return this.mSwitcher;
    }

    public View getTextSwitcherContainer() {
        return this.mSwitcherContainer;
    }

    public View getKeyboardOrbContainer() {
        return this.mKeyboardContainer;
    }

    public SearchOrb getMicOrb() {
        return this.mMicOrbView;
    }

    public SearchOrb getKeyboardOrb() {
        return this.mKeyboardOrbView;
    }

    public View getHotwordDisabledIcon() {
        return this.mMicDisabledIcon;
    }

    @VisibleForTesting
    public void setIsHintTextFlippingAllowed(boolean isAllowed) {
        this.mIsHintFlippingAllowed = isAllowed;
    }

    /* access modifiers changed from: protected */
    @VisibleForTesting
    public ImageView getMicDisabledIcon() {
        return this.mMicDisabledIcon;
    }
}
