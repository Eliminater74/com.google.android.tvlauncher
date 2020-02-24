package android.support.p004v7.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.app.ActivityCompat;
import android.support.p001v4.app.NavUtils;
import android.support.p001v4.util.ArrayMap;
import android.support.p001v4.view.KeyEventDispatcher;
import android.support.p001v4.view.LayoutInflaterCompat;
import android.support.p001v4.view.ViewCompat;
import android.support.p001v4.view.ViewPropertyAnimatorCompat;
import android.support.p001v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.p001v4.widget.PopupWindowCompat;
import android.support.p004v7.app.ActionBarDrawerToggle;
import android.support.p004v7.appcompat.C0233R;
import android.support.p004v7.content.res.AppCompatResources;
import android.support.p004v7.view.ActionMode;
import android.support.p004v7.view.ContextThemeWrapper;
import android.support.p004v7.view.StandaloneActionMode;
import android.support.p004v7.view.SupportActionModeWrapper;
import android.support.p004v7.view.SupportMenuInflater;
import android.support.p004v7.view.WindowCallbackWrapper;
import android.support.p004v7.view.menu.ListMenuPresenter;
import android.support.p004v7.view.menu.MenuBuilder;
import android.support.p004v7.view.menu.MenuPresenter;
import android.support.p004v7.view.menu.MenuView;
import android.support.p004v7.widget.ActionBarContextView;
import android.support.p004v7.widget.AppCompatDrawableManager;
import android.support.p004v7.widget.ContentFrameLayout;
import android.support.p004v7.widget.DecorContentParent;
import android.support.p004v7.widget.TintTypedArray;
import android.support.p004v7.widget.Toolbar;
import android.support.p004v7.widget.VectorEnabledTintResources;
import android.support.p004v7.widget.ViewStubCompat;
import android.support.p004v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.lang.Thread;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* renamed from: android.support.v7.app.AppCompatDelegateImpl */
class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    private static final boolean DEBUG = false;
    static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
    private static final boolean IS_PRE_LOLLIPOP = (Build.VERSION.SDK_INT < 21);
    private static boolean sInstalledExceptionHandler = true;
    private static final Map<Class<?>, Integer> sLocalNightModes = new ArrayMap();
    private static final int[] sWindowBackgroundStyleable = {16842836};
    ActionBar mActionBar;
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private boolean mActivityHandlesUiMode;
    private boolean mActivityHandlesUiModeChecked;
    final AppCompatCallback mAppCompatCallback;
    private AppCompatViewInflater mAppCompatViewInflater;
    private AppCompatWindowCallback mAppCompatWindowCallback;
    private AutoNightModeManager mAutoBatteryNightModeManager;
    private AutoNightModeManager mAutoTimeNightModeManager;
    private boolean mBaseContextAttached;
    private boolean mClosingActionMenu;
    final Context mContext;
    private boolean mCreated;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private boolean mHandleNativeActionModes;
    boolean mHasActionBar;
    final Object mHost;
    int mInvalidatePanelMenuFeatures;
    boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable;
    boolean mIsDestroyed;
    boolean mIsFloating;
    private int mLocalNightMode;
    private boolean mLongPressBackDown;
    MenuInflater mMenuInflater;
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private View mStatusGuard;
    private ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private int mThemeResId;
    private CharSequence mTitle;
    private TextView mTitleView;
    Window mWindow;
    boolean mWindowNoTitle;

    static {
        if (IS_PRE_LOLLIPOP && !sInstalledExceptionHandler) {
            final Thread.UncaughtExceptionHandler defHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread thread, Throwable thowable) {
                    if (shouldWrapException(thowable)) {
                        Throwable wrapped = new Resources.NotFoundException(thowable.getMessage() + AppCompatDelegateImpl.EXCEPTION_HANDLER_MESSAGE_SUFFIX);
                        wrapped.initCause(thowable.getCause());
                        wrapped.setStackTrace(thowable.getStackTrace());
                        defHandler.uncaughtException(thread, wrapped);
                        return;
                    }
                    defHandler.uncaughtException(thread, thowable);
                }

                private boolean shouldWrapException(Throwable throwable) {
                    String message;
                    if (!(throwable instanceof Resources.NotFoundException) || (message = throwable.getMessage()) == null) {
                        return false;
                    }
                    if (message.contains("drawable") || message.contains("Drawable")) {
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    AppCompatDelegateImpl(Activity activity, AppCompatCallback callback) {
        this(activity, null, callback, activity);
    }

    AppCompatDelegateImpl(Dialog dialog, AppCompatCallback callback) {
        this(dialog.getContext(), dialog.getWindow(), callback, dialog);
    }

    AppCompatDelegateImpl(Context context, Window window, AppCompatCallback callback) {
        this(context, window, callback, context);
    }

    AppCompatDelegateImpl(Context context, Activity activity, AppCompatCallback callback) {
        this(context, null, callback, activity);
    }

    private AppCompatDelegateImpl(Context context, Window window, AppCompatCallback callback, Object host) {
        Integer value;
        AppCompatActivity activity;
        this.mFadeAnim = null;
        this.mHandleNativeActionModes = true;
        this.mLocalNightMode = -100;
        this.mInvalidatePanelMenuRunnable = new Runnable() {
            public void run() {
                if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 1) != 0) {
                    AppCompatDelegateImpl.this.doInvalidatePanelMenu(0);
                }
                if ((AppCompatDelegateImpl.this.mInvalidatePanelMenuFeatures & 4096) != 0) {
                    AppCompatDelegateImpl.this.doInvalidatePanelMenu(108);
                }
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                appCompatDelegateImpl.mInvalidatePanelMenuPosted = false;
                appCompatDelegateImpl.mInvalidatePanelMenuFeatures = 0;
            }
        };
        this.mContext = context;
        this.mAppCompatCallback = callback;
        this.mHost = host;
        if (this.mLocalNightMode == -100 && (this.mHost instanceof Dialog) && (activity = tryUnwrapContext()) != null) {
            this.mLocalNightMode = activity.getDelegate().getLocalNightMode();
        }
        if (this.mLocalNightMode == -100 && (value = sLocalNightModes.get(this.mHost.getClass())) != null) {
            this.mLocalNightMode = value.intValue();
            sLocalNightModes.remove(this.mHost.getClass());
        }
        if (window != null) {
            attachToWindow(window);
        }
        AppCompatDrawableManager.preload();
    }

    public void attachBaseContext(Context context) {
        applyDayNight();
        this.mBaseContextAttached = true;
    }

    public void onCreate(Bundle savedInstanceState) {
        this.mBaseContextAttached = true;
        applyDayNight();
        ensureWindow();
        Object obj = this.mHost;
        if (obj instanceof Activity) {
            String parentActivityName = null;
            try {
                parentActivityName = NavUtils.getParentActivityName((Activity) obj);
            } catch (IllegalArgumentException e) {
            }
            if (parentActivityName != null) {
                ActionBar ab = peekSupportActionBar();
                if (ab == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    ab.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
        }
        this.mCreated = true;
    }

    public void onPostCreate(Bundle savedInstanceState) {
        ensureSubDecor();
    }

    public ActionBar getSupportActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    /* access modifiers changed from: package-private */
    public final ActionBar peekSupportActionBar() {
        return this.mActionBar;
    }

    /* access modifiers changed from: package-private */
    public final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    private void initWindowDecorActionBar() {
        ensureSubDecor();
        if (this.mHasActionBar && this.mActionBar == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                this.mActionBar = new WindowDecorActionBar((Activity) obj, this.mOverlayActionBar);
            } else if (obj instanceof Dialog) {
                this.mActionBar = new WindowDecorActionBar((Dialog) obj);
            }
            ActionBar actionBar = this.mActionBar;
            if (actionBar != null) {
                actionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
        }
    }

    public void setSupportActionBar(Toolbar toolbar) {
        if (this.mHost instanceof Activity) {
            ActionBar ab = getSupportActionBar();
            if (!(ab instanceof WindowDecorActionBar)) {
                this.mMenuInflater = null;
                if (ab != null) {
                    ab.onDestroy();
                }
                if (toolbar != null) {
                    ToolbarActionBar tbab = new ToolbarActionBar(toolbar, getTitle(), this.mAppCompatWindowCallback);
                    this.mActionBar = tbab;
                    this.mWindow.setCallback(tbab.getWrappedWindowCallback());
                } else {
                    this.mActionBar = null;
                    this.mWindow.setCallback(this.mAppCompatWindowCallback);
                }
                invalidateOptionsMenu();
                return;
            }
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
    }

    /* access modifiers changed from: package-private */
    public final Context getActionBarThemedContext() {
        Context context = null;
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            context = ab.getThemedContext();
        }
        if (context == null) {
            return this.mContext;
        }
        return context;
    }

    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            this.mMenuInflater = new SupportMenuInflater(actionBar != null ? actionBar.getThemedContext() : this.mContext);
        }
        return this.mMenuInflater;
    }

    @Nullable
    public <T extends View> T findViewById(@IdRes int id) {
        ensureSubDecor();
        return this.mWindow.findViewById(id);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        ActionBar ab;
        if (this.mHasActionBar && this.mSubDecorInstalled && (ab = getSupportActionBar()) != null) {
            ab.onConfigurationChanged(newConfig);
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
        applyDayNight(false);
    }

    public void onStart() {
        applyDayNight();
        markStarted(this);
    }

    public void onStop() {
        markStopped(this);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setShowHideAnimationEnabled(false);
        }
        AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
        if (autoNightModeManager != null) {
            autoNightModeManager.cleanup();
        }
        AutoNightModeManager autoNightModeManager2 = this.mAutoBatteryNightModeManager;
        if (autoNightModeManager2 != null) {
            autoNightModeManager2.cleanup();
        }
    }

    public void onPostResume() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setShowHideAnimationEnabled(true);
        }
    }

    public void setContentView(View v) {
        ensureSubDecor();
        ViewGroup contentParent = (ViewGroup) this.mSubDecor.findViewById(16908290);
        contentParent.removeAllViews();
        contentParent.addView(v);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    public void setContentView(int resId) {
        ensureSubDecor();
        ViewGroup contentParent = (ViewGroup) this.mSubDecor.findViewById(16908290);
        contentParent.removeAllViews();
        LayoutInflater.from(this.mContext).inflate(resId, contentParent);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    public void setContentView(View v, ViewGroup.LayoutParams lp) {
        ensureSubDecor();
        ViewGroup contentParent = (ViewGroup) this.mSubDecor.findViewById(16908290);
        contentParent.removeAllViews();
        contentParent.addView(v, lp);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    public void addContentView(View v, ViewGroup.LayoutParams lp) {
        ensureSubDecor();
        ((ViewGroup) this.mSubDecor.findViewById(16908290)).addView(v, lp);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.mLocalNightMode != -100) {
            sLocalNightModes.put(this.mHost.getClass(), Integer.valueOf(this.mLocalNightMode));
        }
    }

    public void onDestroy() {
        markStopped(this);
        if (this.mInvalidatePanelMenuPosted) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
        }
        this.mIsDestroyed = true;
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
        if (autoNightModeManager != null) {
            autoNightModeManager.cleanup();
        }
        AutoNightModeManager autoNightModeManager2 = this.mAutoBatteryNightModeManager;
        if (autoNightModeManager2 != null) {
            autoNightModeManager2.cleanup();
        }
    }

    public void setTheme(@StyleRes int themeResId) {
        this.mThemeResId = themeResId;
    }

    private void ensureWindow() {
        if (this.mWindow == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                attachToWindow(((Activity) obj).getWindow());
            }
        }
        if (this.mWindow == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    private void attachToWindow(@NonNull Window window) {
        if (this.mWindow == null) {
            Window.Callback callback = window.getCallback();
            if (!(callback instanceof AppCompatWindowCallback)) {
                this.mAppCompatWindowCallback = new AppCompatWindowCallback(callback);
                window.setCallback(this.mAppCompatWindowCallback);
                TintTypedArray a = TintTypedArray.obtainStyledAttributes(this.mContext, (AttributeSet) null, sWindowBackgroundStyleable);
                Drawable winBg = a.getDrawableIfKnown(0);
                if (winBg != null) {
                    window.setBackgroundDrawable(winBg);
                }
                a.recycle();
                this.mWindow = window;
                return;
            }
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }

    private void ensureSubDecor() {
        if (!this.mSubDecorInstalled) {
            this.mSubDecor = createSubDecor();
            CharSequence title = getTitle();
            if (!TextUtils.isEmpty(title)) {
                DecorContentParent decorContentParent = this.mDecorContentParent;
                if (decorContentParent != null) {
                    decorContentParent.setWindowTitle(title);
                } else if (peekSupportActionBar() != null) {
                    peekSupportActionBar().setWindowTitle(title);
                } else {
                    TextView textView = this.mTitleView;
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
            }
            applyFixedSizeWindow();
            onSubDecorInstalled(this.mSubDecor);
            this.mSubDecorInstalled = true;
            PanelFeatureState st = getPanelState(0, false);
            if (this.mIsDestroyed) {
                return;
            }
            if (st == null || st.menu == null) {
                invalidatePanelMenu(108);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r8v5, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r3v26, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.ViewGroup createSubDecor() {
        /*
            r10 = this;
            android.content.Context r0 = r10.mContext
            int[] r1 = android.support.p004v7.appcompat.C0233R.styleable.AppCompatTheme
            android.content.res.TypedArray r0 = r0.obtainStyledAttributes(r1)
            int r1 = android.support.p004v7.appcompat.C0233R.styleable.AppCompatTheme_windowActionBar
            boolean r1 = r0.hasValue(r1)
            if (r1 == 0) goto L_0x01a8
            int r1 = android.support.p004v7.appcompat.C0233R.styleable.AppCompatTheme_windowNoTitle
            r2 = 0
            boolean r1 = r0.getBoolean(r1, r2)
            r3 = 1
            if (r1 == 0) goto L_0x001e
            r10.requestWindowFeature(r3)
            goto L_0x002b
        L_0x001e:
            int r1 = android.support.p004v7.appcompat.C0233R.styleable.AppCompatTheme_windowActionBar
            boolean r1 = r0.getBoolean(r1, r2)
            if (r1 == 0) goto L_0x002b
            r1 = 108(0x6c, float:1.51E-43)
            r10.requestWindowFeature(r1)
        L_0x002b:
            int r1 = android.support.p004v7.appcompat.C0233R.styleable.AppCompatTheme_windowActionBarOverlay
            boolean r1 = r0.getBoolean(r1, r2)
            r4 = 109(0x6d, float:1.53E-43)
            if (r1 == 0) goto L_0x0038
            r10.requestWindowFeature(r4)
        L_0x0038:
            int r1 = android.support.p004v7.appcompat.C0233R.styleable.AppCompatTheme_windowActionModeOverlay
            boolean r1 = r0.getBoolean(r1, r2)
            if (r1 == 0) goto L_0x0045
            r1 = 10
            r10.requestWindowFeature(r1)
        L_0x0045:
            int r1 = android.support.p004v7.appcompat.C0233R.styleable.AppCompatTheme_android_windowIsFloating
            boolean r1 = r0.getBoolean(r1, r2)
            r10.mIsFloating = r1
            r0.recycle()
            r10.ensureWindow()
            android.view.Window r1 = r10.mWindow
            r1.getDecorView()
            android.content.Context r1 = r10.mContext
            android.view.LayoutInflater r1 = android.view.LayoutInflater.from(r1)
            r5 = 0
            boolean r6 = r10.mWindowNoTitle
            r7 = 0
            if (r6 != 0) goto L_0x00d9
            boolean r6 = r10.mIsFloating
            if (r6 == 0) goto L_0x0077
            int r3 = android.support.p004v7.appcompat.C0233R.layout.abc_dialog_title_material
            android.view.View r3 = r1.inflate(r3, r7)
            r5 = r3
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            r10.mOverlayActionBar = r2
            r10.mHasActionBar = r2
            goto L_0x010a
        L_0x0077:
            boolean r6 = r10.mHasActionBar
            if (r6 == 0) goto L_0x010a
            android.util.TypedValue r6 = new android.util.TypedValue
            r6.<init>()
            android.content.Context r8 = r10.mContext
            android.content.res.Resources$Theme r8 = r8.getTheme()
            int r9 = android.support.p004v7.appcompat.C0233R.attr.actionBarTheme
            r8.resolveAttribute(r9, r6, r3)
            int r3 = r6.resourceId
            if (r3 == 0) goto L_0x0099
            android.support.v7.view.ContextThemeWrapper r3 = new android.support.v7.view.ContextThemeWrapper
            android.content.Context r8 = r10.mContext
            int r9 = r6.resourceId
            r3.<init>(r8, r9)
            goto L_0x009b
        L_0x0099:
            android.content.Context r3 = r10.mContext
        L_0x009b:
            android.view.LayoutInflater r8 = android.view.LayoutInflater.from(r3)
            int r9 = android.support.p004v7.appcompat.C0233R.layout.abc_screen_toolbar
            android.view.View r8 = r8.inflate(r9, r7)
            r5 = r8
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            int r8 = android.support.p004v7.appcompat.C0233R.C0235id.decor_content_parent
            android.view.View r8 = r5.findViewById(r8)
            android.support.v7.widget.DecorContentParent r8 = (android.support.p004v7.widget.DecorContentParent) r8
            r10.mDecorContentParent = r8
            android.support.v7.widget.DecorContentParent r8 = r10.mDecorContentParent
            android.view.Window$Callback r9 = r10.getWindowCallback()
            r8.setWindowCallback(r9)
            boolean r8 = r10.mOverlayActionBar
            if (r8 == 0) goto L_0x00c4
            android.support.v7.widget.DecorContentParent r8 = r10.mDecorContentParent
            r8.initFeature(r4)
        L_0x00c4:
            boolean r4 = r10.mFeatureProgress
            if (r4 == 0) goto L_0x00ce
            android.support.v7.widget.DecorContentParent r4 = r10.mDecorContentParent
            r8 = 2
            r4.initFeature(r8)
        L_0x00ce:
            boolean r4 = r10.mFeatureIndeterminateProgress
            if (r4 == 0) goto L_0x00d8
            android.support.v7.widget.DecorContentParent r4 = r10.mDecorContentParent
            r8 = 5
            r4.initFeature(r8)
        L_0x00d8:
            goto L_0x010a
        L_0x00d9:
            boolean r3 = r10.mOverlayActionMode
            if (r3 == 0) goto L_0x00e7
            int r3 = android.support.p004v7.appcompat.C0233R.layout.abc_screen_simple_overlay_action_mode
            android.view.View r3 = r1.inflate(r3, r7)
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            r5 = r3
            goto L_0x00f0
        L_0x00e7:
            int r3 = android.support.p004v7.appcompat.C0233R.layout.abc_screen_simple
            android.view.View r3 = r1.inflate(r3, r7)
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            r5 = r3
        L_0x00f0:
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 21
            if (r3 < r4) goto L_0x00ff
            android.support.v7.app.AppCompatDelegateImpl$3 r3 = new android.support.v7.app.AppCompatDelegateImpl$3
            r3.<init>()
            android.support.p001v4.view.ViewCompat.setOnApplyWindowInsetsListener(r5, r3)
            goto L_0x010a
        L_0x00ff:
            r3 = r5
            android.support.v7.widget.FitWindowsViewGroup r3 = (android.support.p004v7.widget.FitWindowsViewGroup) r3
            android.support.v7.app.AppCompatDelegateImpl$4 r4 = new android.support.v7.app.AppCompatDelegateImpl$4
            r4.<init>()
            r3.setOnFitSystemWindowsListener(r4)
        L_0x010a:
            if (r5 == 0) goto L_0x0162
            android.support.v7.widget.DecorContentParent r3 = r10.mDecorContentParent
            if (r3 != 0) goto L_0x011a
            int r3 = android.support.p004v7.appcompat.C0233R.C0235id.title
            android.view.View r3 = r5.findViewById(r3)
            android.widget.TextView r3 = (android.widget.TextView) r3
            r10.mTitleView = r3
        L_0x011a:
            android.support.p004v7.widget.ViewUtils.makeOptionalFitsSystemWindows(r5)
            int r3 = android.support.p004v7.appcompat.C0233R.C0235id.action_bar_activity_content
            android.view.View r3 = r5.findViewById(r3)
            android.support.v7.widget.ContentFrameLayout r3 = (android.support.p004v7.widget.ContentFrameLayout) r3
            android.view.Window r4 = r10.mWindow
            r6 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r4 = r4.findViewById(r6)
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            if (r4 == 0) goto L_0x0154
        L_0x0132:
            int r8 = r4.getChildCount()
            if (r8 <= 0) goto L_0x0143
            android.view.View r8 = r4.getChildAt(r2)
            r4.removeViewAt(r2)
            r3.addView(r8)
            goto L_0x0132
        L_0x0143:
            r2 = -1
            r4.setId(r2)
            r3.setId(r6)
            boolean r2 = r4 instanceof android.widget.FrameLayout
            if (r2 == 0) goto L_0x0154
            r2 = r4
            android.widget.FrameLayout r2 = (android.widget.FrameLayout) r2
            r2.setForeground(r7)
        L_0x0154:
            android.view.Window r2 = r10.mWindow
            r2.setContentView(r5)
            android.support.v7.app.AppCompatDelegateImpl$5 r2 = new android.support.v7.app.AppCompatDelegateImpl$5
            r2.<init>()
            r3.setAttachListener(r2)
            return r5
        L_0x0162:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "AppCompat does not support the current theme features: { windowActionBar: "
            r3.append(r4)
            boolean r4 = r10.mHasActionBar
            r3.append(r4)
            java.lang.String r4 = ", windowActionBarOverlay: "
            r3.append(r4)
            boolean r4 = r10.mOverlayActionBar
            r3.append(r4)
            java.lang.String r4 = ", android:windowIsFloating: "
            r3.append(r4)
            boolean r4 = r10.mIsFloating
            r3.append(r4)
            java.lang.String r4 = ", windowActionModeOverlay: "
            r3.append(r4)
            boolean r4 = r10.mOverlayActionMode
            r3.append(r4)
            java.lang.String r4 = ", windowNoTitle: "
            r3.append(r4)
            boolean r4 = r10.mWindowNoTitle
            r3.append(r4)
            java.lang.String r4 = " }"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x01a8:
            r0.recycle()
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "You need to use a Theme.AppCompat theme (or descendant) with this activity."
            r1.<init>(r2)
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p004v7.app.AppCompatDelegateImpl.createSubDecor():android.view.ViewGroup");
    }

    /* access modifiers changed from: package-private */
    public void onSubDecorInstalled(ViewGroup subDecor) {
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout cfl = (ContentFrameLayout) this.mSubDecor.findViewById(16908290);
        View windowDecor = this.mWindow.getDecorView();
        cfl.setDecorPadding(windowDecor.getPaddingLeft(), windowDecor.getPaddingTop(), windowDecor.getPaddingRight(), windowDecor.getPaddingBottom());
        TypedArray a = this.mContext.obtainStyledAttributes(C0233R.styleable.AppCompatTheme);
        a.getValue(C0233R.styleable.AppCompatTheme_windowMinWidthMajor, cfl.getMinWidthMajor());
        a.getValue(C0233R.styleable.AppCompatTheme_windowMinWidthMinor, cfl.getMinWidthMinor());
        if (a.hasValue(C0233R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            a.getValue(C0233R.styleable.AppCompatTheme_windowFixedWidthMajor, cfl.getFixedWidthMajor());
        }
        if (a.hasValue(C0233R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            a.getValue(C0233R.styleable.AppCompatTheme_windowFixedWidthMinor, cfl.getFixedWidthMinor());
        }
        if (a.hasValue(C0233R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            a.getValue(C0233R.styleable.AppCompatTheme_windowFixedHeightMajor, cfl.getFixedHeightMajor());
        }
        if (a.hasValue(C0233R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            a.getValue(C0233R.styleable.AppCompatTheme_windowFixedHeightMinor, cfl.getFixedHeightMinor());
        }
        a.recycle();
        cfl.requestLayout();
    }

    public boolean requestWindowFeature(int featureId) {
        int featureId2 = sanitizeWindowFeatureId(featureId);
        if (this.mWindowNoTitle && featureId2 == 108) {
            return false;
        }
        if (this.mHasActionBar && featureId2 == 1) {
            this.mHasActionBar = false;
        }
        if (featureId2 == 1) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mWindowNoTitle = true;
            return true;
        } else if (featureId2 == 2) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureProgress = true;
            return true;
        } else if (featureId2 == 5) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureIndeterminateProgress = true;
            return true;
        } else if (featureId2 == 10) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mOverlayActionMode = true;
            return true;
        } else if (featureId2 == 108) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mHasActionBar = true;
            return true;
        } else if (featureId2 != 109) {
            return this.mWindow.requestFeature(featureId2);
        } else {
            throwFeatureRequestIfSubDecorInstalled();
            this.mOverlayActionBar = true;
            return true;
        }
    }

    public boolean hasWindowFeature(int featureId) {
        boolean result = false;
        int sanitizeWindowFeatureId = sanitizeWindowFeatureId(featureId);
        if (sanitizeWindowFeatureId == 1) {
            result = this.mWindowNoTitle;
        } else if (sanitizeWindowFeatureId == 2) {
            result = this.mFeatureProgress;
        } else if (sanitizeWindowFeatureId == 5) {
            result = this.mFeatureIndeterminateProgress;
        } else if (sanitizeWindowFeatureId == 10) {
            result = this.mOverlayActionMode;
        } else if (sanitizeWindowFeatureId == 108) {
            result = this.mHasActionBar;
        } else if (sanitizeWindowFeatureId == 109) {
            result = this.mOverlayActionBar;
        }
        if (result || this.mWindow.hasFeature(featureId)) {
            return true;
        }
        return false;
    }

    public final void setTitle(CharSequence title) {
        this.mTitle = title;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(title);
        } else if (peekSupportActionBar() != null) {
            peekSupportActionBar().setWindowTitle(title);
        } else {
            TextView textView = this.mTitleView;
            if (textView != null) {
                textView.setText(title);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final CharSequence getTitle() {
        Object obj = this.mHost;
        if (obj instanceof Activity) {
            return ((Activity) obj).getTitle();
        }
        return this.mTitle;
    }

    /* access modifiers changed from: package-private */
    public void onPanelClosed(int featureId) {
        if (featureId == 108) {
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.dispatchMenuVisibilityChanged(false);
            }
        } else if (featureId == 0) {
            PanelFeatureState st = getPanelState(featureId, true);
            if (st.isOpen) {
                closePanel(st, false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onMenuOpened(int featureId) {
        ActionBar ab;
        if (featureId == 108 && (ab = getSupportActionBar()) != null) {
            ab.dispatchMenuVisibilityChanged(true);
        }
    }

    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
        PanelFeatureState panel;
        Window.Callback cb = getWindowCallback();
        if (cb == null || this.mIsDestroyed || (panel = findMenuPanel(menu.getRootMenu())) == null) {
            return false;
        }
        return cb.onMenuItemSelected(panel.featureId, item);
    }

    public void onMenuModeChange(MenuBuilder menu) {
        reopenMenu(menu, true);
    }

    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        AppCompatCallback appCompatCallback;
        if (callback != null) {
            ActionMode actionMode = this.mActionMode;
            if (actionMode != null) {
                actionMode.finish();
            }
            ActionMode.Callback wrappedCallback = new ActionModeCallbackWrapperV9(callback);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                this.mActionMode = ab.startActionMode(wrappedCallback);
                ActionMode actionMode2 = this.mActionMode;
                if (!(actionMode2 == null || (appCompatCallback = this.mAppCompatCallback) == null)) {
                    appCompatCallback.onSupportActionModeStarted(actionMode2);
                }
            }
            if (this.mActionMode == null) {
                this.mActionMode = startSupportActionModeFromWindow(wrappedCallback);
            }
            return this.mActionMode;
        }
        throw new IllegalArgumentException("ActionMode callback can not be null.");
    }

    public void invalidateOptionsMenu() {
        ActionBar ab = getSupportActionBar();
        if (ab == null || !ab.invalidateOptionsMenu()) {
            invalidatePanelMenu(0);
        }
    }

    /* access modifiers changed from: package-private */
    public ActionMode startSupportActionModeFromWindow(@NonNull ActionMode.Callback callback) {
        AppCompatCallback appCompatCallback;
        Context actionBarContext;
        endOnGoingFadeAnimation();
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        if (!(callback instanceof ActionModeCallbackWrapperV9)) {
            callback = new ActionModeCallbackWrapperV9(callback);
        }
        ActionMode mode = null;
        AppCompatCallback appCompatCallback2 = this.mAppCompatCallback;
        if (appCompatCallback2 != null && !this.mIsDestroyed) {
            try {
                mode = appCompatCallback2.onWindowStartingSupportActionMode(callback);
            } catch (AbstractMethodError e) {
            }
        }
        if (mode != null) {
            this.mActionMode = mode;
        } else {
            boolean z = true;
            if (this.mActionModeView == null) {
                if (this.mIsFloating) {
                    TypedValue outValue = new TypedValue();
                    Resources.Theme baseTheme = this.mContext.getTheme();
                    baseTheme.resolveAttribute(C0233R.attr.actionBarTheme, outValue, true);
                    if (outValue.resourceId != 0) {
                        Resources.Theme actionBarTheme = this.mContext.getResources().newTheme();
                        actionBarTheme.setTo(baseTheme);
                        actionBarTheme.applyStyle(outValue.resourceId, true);
                        actionBarContext = new ContextThemeWrapper(this.mContext, 0);
                        actionBarContext.getTheme().setTo(actionBarTheme);
                    } else {
                        actionBarContext = this.mContext;
                    }
                    this.mActionModeView = new ActionBarContextView(actionBarContext);
                    this.mActionModePopup = new PopupWindow(actionBarContext, (AttributeSet) null, C0233R.attr.actionModePopupWindowStyle);
                    PopupWindowCompat.setWindowLayoutType(this.mActionModePopup, 2);
                    this.mActionModePopup.setContentView(this.mActionModeView);
                    this.mActionModePopup.setWidth(-1);
                    actionBarContext.getTheme().resolveAttribute(C0233R.attr.actionBarSize, outValue, true);
                    this.mActionModeView.setContentHeight(TypedValue.complexToDimensionPixelSize(outValue.data, actionBarContext.getResources().getDisplayMetrics()));
                    this.mActionModePopup.setHeight(-2);
                    this.mShowActionModePopup = new Runnable() {
                        public void run() {
                            AppCompatDelegateImpl.this.mActionModePopup.showAtLocation(AppCompatDelegateImpl.this.mActionModeView, 55, 0, 0);
                            AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
                            if (AppCompatDelegateImpl.this.shouldAnimateActionModeView()) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0f);
                                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl.mFadeAnim = ViewCompat.animate(appCompatDelegateImpl.mActionModeView).alpha(1.0f);
                                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() {
                                    public void onAnimationStart(View view) {
                                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                    }

                                    public void onAnimationEnd(View view) {
                                        AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                        AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                                        AppCompatDelegateImpl.this.mFadeAnim = null;
                                    }
                                });
                                return;
                            }
                            AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                            AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                        }
                    };
                } else {
                    ViewStubCompat stub = (ViewStubCompat) this.mSubDecor.findViewById(C0233R.C0235id.action_mode_bar_stub);
                    if (stub != null) {
                        stub.setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()));
                        this.mActionModeView = (ActionBarContextView) stub.inflate();
                    }
                }
            }
            if (this.mActionModeView != null) {
                endOnGoingFadeAnimation();
                this.mActionModeView.killMode();
                Context context = this.mActionModeView.getContext();
                ActionBarContextView actionBarContextView = this.mActionModeView;
                if (this.mActionModePopup != null) {
                    z = false;
                }
                ActionMode mode2 = new StandaloneActionMode(context, actionBarContextView, callback, z);
                if (callback.onCreateActionMode(mode2, mode2.getMenu())) {
                    mode2.invalidate();
                    this.mActionModeView.initForMode(mode2);
                    this.mActionMode = mode2;
                    if (shouldAnimateActionModeView()) {
                        this.mActionModeView.setAlpha(0.0f);
                        this.mFadeAnim = ViewCompat.animate(this.mActionModeView).alpha(1.0f);
                        this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() {
                            public void onAnimationStart(View view) {
                                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                AppCompatDelegateImpl.this.mActionModeView.sendAccessibilityEvent(32);
                                if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
                                    ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent());
                                }
                            }

                            public void onAnimationEnd(View view) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                                AppCompatDelegateImpl.this.mFadeAnim = null;
                            }
                        });
                    } else {
                        this.mActionModeView.setAlpha(1.0f);
                        this.mActionModeView.setVisibility(0);
                        this.mActionModeView.sendAccessibilityEvent(32);
                        if (this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) this.mActionModeView.getParent());
                        }
                    }
                    if (this.mActionModePopup != null) {
                        this.mWindow.getDecorView().post(this.mShowActionModePopup);
                    }
                } else {
                    this.mActionMode = null;
                }
            }
        }
        ActionMode actionMode2 = this.mActionMode;
        if (!(actionMode2 == null || (appCompatCallback = this.mAppCompatCallback) == null)) {
            appCompatCallback.onSupportActionModeStarted(actionMode2);
        }
        return this.mActionMode;
    }

    /* access modifiers changed from: package-private */
    public final boolean shouldAnimateActionModeView() {
        ViewGroup viewGroup;
        return this.mSubDecorInstalled && (viewGroup = this.mSubDecor) != null && ViewCompat.isLaidOut(viewGroup);
    }

    public void setHandleNativeActionModesEnabled(boolean enabled) {
        this.mHandleNativeActionModes = enabled;
    }

    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    /* access modifiers changed from: package-private */
    public void endOnGoingFadeAnimation() {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean onBackPressed() {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
            return true;
        }
        ActionBar ab = getSupportActionBar();
        if (ab == null || !ab.collapseActionView()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onKeyShortcut(int keyCode, KeyEvent ev) {
        ActionBar ab = getSupportActionBar();
        if (ab != null && ab.onKeyShortcut(keyCode, ev)) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.mPreparedPanel;
        if (panelFeatureState == null || !performPanelShortcut(panelFeatureState, ev.getKeyCode(), ev, 1)) {
            if (this.mPreparedPanel == null) {
                PanelFeatureState st = getPanelState(0, true);
                preparePanel(st, ev);
                boolean handled = performPanelShortcut(st, ev.getKeyCode(), ev, 1);
                st.isPrepared = false;
                if (handled) {
                    return true;
                }
            }
            return false;
        }
        PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
        if (panelFeatureState2 != null) {
            panelFeatureState2.isHandled = true;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchKeyEvent(KeyEvent event) {
        View root;
        Object obj = this.mHost;
        boolean isDown = true;
        if (((obj instanceof KeyEventDispatcher.Component) || (obj instanceof AppCompatDialog)) && (root = this.mWindow.getDecorView()) != null && KeyEventDispatcher.dispatchBeforeHierarchy(root, event)) {
            return true;
        }
        if (event.getKeyCode() == 82 && this.mAppCompatWindowCallback.getWrapped().dispatchKeyEvent(event)) {
            return true;
        }
        int keyCode = event.getKeyCode();
        if (event.getAction() != 0) {
            isDown = false;
        }
        return isDown ? onKeyDown(keyCode, event) : onKeyUp(keyCode, event);
    }

    /* access modifiers changed from: package-private */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            boolean wasLongPressBackDown = this.mLongPressBackDown;
            this.mLongPressBackDown = false;
            PanelFeatureState st = getPanelState(0, false);
            if (st != null && st.isOpen) {
                if (!wasLongPressBackDown) {
                    closePanel(st, true);
                }
                return true;
            } else if (onBackPressed()) {
                return true;
            }
        } else if (keyCode == 82) {
            onKeyUpPanel(0, event);
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        if (keyCode == 4) {
            if ((event.getFlags() & 128) == 0) {
                z = false;
            }
            this.mLongPressBackDown = z;
        } else if (keyCode == 82) {
            onKeyDownPanel(0, event);
            return true;
        }
        return false;
    }

    public View createView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        boolean z = false;
        if (this.mAppCompatViewInflater == null) {
            String viewInflaterClassName = this.mContext.obtainStyledAttributes(C0233R.styleable.AppCompatTheme).getString(C0233R.styleable.AppCompatTheme_viewInflaterClass);
            if (viewInflaterClassName == null || AppCompatViewInflater.class.getName().equals(viewInflaterClassName)) {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater) Class.forName(viewInflaterClassName).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable t) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + viewInflaterClassName + ". Falling back to default.", t);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            }
        }
        boolean inheritContext = false;
        if (IS_PRE_LOLLIPOP) {
            if (!(attrs instanceof XmlPullParser)) {
                z = shouldInheritContext((ViewParent) parent);
            } else if (((XmlPullParser) attrs).getDepth() > 1) {
                z = true;
            }
            inheritContext = z;
        }
        return this.mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            return false;
        }
        View windowDecor = this.mWindow.getDecorView();
        while (parent != null) {
            if (parent == windowDecor || !(parent instanceof View) || ViewCompat.isAttachedToWindow((View) parent)) {
                return false;
            }
            parent = parent.getParent();
        }
        return true;
    }

    public void installViewFactory() {
        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        if (layoutInflater.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflater, this);
        } else if (!(layoutInflater.getFactory2() instanceof AppCompatDelegateImpl)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    public final View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return createView(parent, name, context, attrs);
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }

    @Nullable
    private AppCompatActivity tryUnwrapContext() {
        for (Context context = this.mContext; context != null; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity) context;
            }
            if (!(context instanceof ContextWrapper)) {
                return null;
            }
        }
        return null;
    }

    /* JADX INFO: Multiple debug info for r2v3 android.view.Window$Callback: [D('cb' android.view.Window$Callback), D('config' android.content.res.Configuration)] */
    private void openPanel(PanelFeatureState st, KeyEvent event) {
        ViewGroup.LayoutParams lp;
        PanelFeatureState panelFeatureState = st;
        if (!panelFeatureState.isOpen && !this.mIsDestroyed) {
            if (panelFeatureState.featureId == 0) {
                if ((this.mContext.getResources().getConfiguration().screenLayout & 15) == 4) {
                    return;
                }
            }
            Window.Callback cb = getWindowCallback();
            if (cb == null || cb.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
                WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
                if (wm != null && preparePanel(st, event)) {
                    int width = -2;
                    if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) {
                        if (panelFeatureState.decorView == null) {
                            if (!initializePanelDecor(st) || panelFeatureState.decorView == null) {
                                return;
                            }
                        } else if (panelFeatureState.refreshDecorView && panelFeatureState.decorView.getChildCount() > 0) {
                            panelFeatureState.decorView.removeAllViews();
                        }
                        if (initializePanelContent(st) && st.hasPanelItems()) {
                            ViewGroup.LayoutParams lp2 = panelFeatureState.shownPanelView.getLayoutParams();
                            if (lp2 == null) {
                                lp2 = new ViewGroup.LayoutParams(-2, -2);
                            }
                            panelFeatureState.decorView.setBackgroundResource(panelFeatureState.background);
                            ViewParent shownPanelParent = panelFeatureState.shownPanelView.getParent();
                            if (shownPanelParent instanceof ViewGroup) {
                                ((ViewGroup) shownPanelParent).removeView(panelFeatureState.shownPanelView);
                            }
                            panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, lp2);
                            if (!panelFeatureState.shownPanelView.hasFocus()) {
                                panelFeatureState.shownPanelView.requestFocus();
                            }
                        } else {
                            return;
                        }
                    } else if (!(panelFeatureState.createdPanelView == null || (lp = panelFeatureState.createdPanelView.getLayoutParams()) == null || lp.width != -1)) {
                        width = -1;
                    }
                    panelFeatureState.isHandled = false;
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(width, -2, panelFeatureState.f15x, panelFeatureState.f16y, 1002, 8519680, -3);
                    layoutParams.gravity = panelFeatureState.gravity;
                    layoutParams.windowAnimations = panelFeatureState.windowAnimations;
                    wm.addView(panelFeatureState.decorView, layoutParams);
                    panelFeatureState.isOpen = true;
                    return;
                }
                return;
            }
            closePanel(panelFeatureState, true);
        }
    }

    private boolean initializePanelDecor(PanelFeatureState st) {
        st.setStyle(getActionBarThemedContext());
        st.decorView = new ListMenuDecorView(st.listPresenterContext);
        st.gravity = 81;
        return true;
    }

    /* JADX INFO: Multiple debug info for r0v1 android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState: [D('cb' android.view.Window$Callback), D('st' android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState)] */
    private void reopenMenu(MenuBuilder menu, boolean toggleMenuMode) {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent == null || !decorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.mContext).hasPermanentMenuKey() && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState st = getPanelState(0, true);
            st.refreshDecorView = true;
            closePanel(st, false);
            openPanel(st, null);
            return;
        }
        Window.Callback cb = getWindowCallback();
        if (this.mDecorContentParent.isOverflowMenuShowing() && toggleMenuMode) {
            this.mDecorContentParent.hideOverflowMenu();
            if (!this.mIsDestroyed) {
                cb.onPanelClosed(108, getPanelState(0, true).menu);
            }
        } else if (cb != null && !this.mIsDestroyed) {
            if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                this.mInvalidatePanelMenuRunnable.run();
            }
            PanelFeatureState st2 = getPanelState(0, true);
            if (st2.menu != null && !st2.refreshMenuContent && cb.onPreparePanel(0, st2.createdPanelView, st2.menu)) {
                cb.onMenuOpened(108, st2.menu);
                this.mDecorContentParent.showOverflowMenu();
            }
        }
    }

    private boolean initializePanelMenu(PanelFeatureState st) {
        Context context = this.mContext;
        if ((st.featureId == 0 || st.featureId == 108) && this.mDecorContentParent != null) {
            TypedValue outValue = new TypedValue();
            Resources.Theme baseTheme = context.getTheme();
            baseTheme.resolveAttribute(C0233R.attr.actionBarTheme, outValue, true);
            Resources.Theme widgetTheme = null;
            if (outValue.resourceId != 0) {
                widgetTheme = context.getResources().newTheme();
                widgetTheme.setTo(baseTheme);
                widgetTheme.applyStyle(outValue.resourceId, true);
                widgetTheme.resolveAttribute(C0233R.attr.actionBarWidgetTheme, outValue, true);
            } else {
                baseTheme.resolveAttribute(C0233R.attr.actionBarWidgetTheme, outValue, true);
            }
            if (outValue.resourceId != 0) {
                if (widgetTheme == null) {
                    widgetTheme = context.getResources().newTheme();
                    widgetTheme.setTo(baseTheme);
                }
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            if (widgetTheme != null) {
                context = new ContextThemeWrapper(context, 0);
                context.getTheme().setTo(widgetTheme);
            }
        }
        MenuBuilder menu = new MenuBuilder(context);
        menu.setCallback(this);
        st.setMenu(menu);
        return true;
    }

    private boolean initializePanelContent(PanelFeatureState st) {
        if (st.createdPanelView != null) {
            st.shownPanelView = st.createdPanelView;
            return true;
        } else if (st.menu == null) {
            return false;
        } else {
            if (this.mPanelMenuPresenterCallback == null) {
                this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
            }
            st.shownPanelView = (View) st.getListMenuView(this.mPanelMenuPresenterCallback);
            if (st.shownPanelView != null) {
                return true;
            }
            return false;
        }
    }

    private boolean preparePanel(PanelFeatureState st, KeyEvent event) {
        DecorContentParent decorContentParent;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        if (this.mIsDestroyed) {
            return false;
        }
        if (st.isPrepared) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.mPreparedPanel;
        if (!(panelFeatureState == null || panelFeatureState == st)) {
            closePanel(panelFeatureState, false);
        }
        Window.Callback cb = getWindowCallback();
        if (cb != null) {
            st.createdPanelView = cb.onCreatePanelView(st.featureId);
        }
        boolean isActionBarMenu = st.featureId == 0 || st.featureId == 108;
        if (isActionBarMenu && (decorContentParent3 = this.mDecorContentParent) != null) {
            decorContentParent3.setMenuPrepared();
        }
        if (st.createdPanelView == null && (!isActionBarMenu || !(peekSupportActionBar() instanceof ToolbarActionBar))) {
            if (st.menu == null || st.refreshMenuContent) {
                if (st.menu == null && (!initializePanelMenu(st) || st.menu == null)) {
                    return false;
                }
                if (isActionBarMenu && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(st.menu, this.mActionMenuPresenterCallback);
                }
                st.menu.stopDispatchingItemsChanged();
                if (!cb.onCreatePanelMenu(st.featureId, st.menu)) {
                    st.setMenu(null);
                    if (isActionBarMenu && (decorContentParent2 = this.mDecorContentParent) != null) {
                        decorContentParent2.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                st.refreshMenuContent = false;
            }
            st.menu.stopDispatchingItemsChanged();
            if (st.frozenActionViewState != null) {
                st.menu.restoreActionViewStates(st.frozenActionViewState);
                st.frozenActionViewState = null;
            }
            if (!cb.onPreparePanel(0, st.createdPanelView, st.menu)) {
                if (isActionBarMenu && (decorContentParent = this.mDecorContentParent) != null) {
                    decorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                }
                st.menu.startDispatchingItemsChanged();
                return false;
            }
            st.qwertyMode = KeyCharacterMap.load(event != null ? event.getDeviceId() : -1).getKeyboardType() != 1;
            st.menu.setQwertyMode(st.qwertyMode);
            st.menu.startDispatchingItemsChanged();
        }
        st.isPrepared = true;
        st.isHandled = false;
        this.mPreparedPanel = st;
        return true;
    }

    /* access modifiers changed from: package-private */
    public void checkCloseActionMenu(MenuBuilder menu) {
        if (!this.mClosingActionMenu) {
            this.mClosingActionMenu = true;
            this.mDecorContentParent.dismissPopups();
            Window.Callback cb = getWindowCallback();
            if (cb != null && !this.mIsDestroyed) {
                cb.onPanelClosed(108, menu);
            }
            this.mClosingActionMenu = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void closePanel(int featureId) {
        closePanel(getPanelState(featureId, true), true);
    }

    /* access modifiers changed from: package-private */
    public void closePanel(PanelFeatureState st, boolean doCallback) {
        DecorContentParent decorContentParent;
        if (!doCallback || st.featureId != 0 || (decorContentParent = this.mDecorContentParent) == null || !decorContentParent.isOverflowMenuShowing()) {
            WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
            if (!(wm == null || !st.isOpen || st.decorView == null)) {
                wm.removeView(st.decorView);
                if (doCallback) {
                    callOnPanelClosed(st.featureId, st, null);
                }
            }
            st.isPrepared = false;
            st.isHandled = false;
            st.isOpen = false;
            st.shownPanelView = null;
            st.refreshDecorView = true;
            if (this.mPreparedPanel == st) {
                this.mPreparedPanel = null;
                return;
            }
            return;
        }
        checkCloseActionMenu(st.menu);
    }

    private boolean onKeyDownPanel(int featureId, KeyEvent event) {
        if (event.getRepeatCount() != 0) {
            return false;
        }
        PanelFeatureState st = getPanelState(featureId, true);
        if (!st.isOpen) {
            return preparePanel(st, event);
        }
        return false;
    }

    private boolean onKeyUpPanel(int featureId, KeyEvent event) {
        DecorContentParent decorContentParent;
        if (this.mActionMode != null) {
            return false;
        }
        boolean handled = false;
        PanelFeatureState st = getPanelState(featureId, true);
        if (featureId != 0 || (decorContentParent = this.mDecorContentParent) == null || !decorContentParent.canShowOverflowMenu() || ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
            if (st.isOpen || st.isHandled) {
                handled = st.isOpen;
                closePanel(st, true);
            } else if (st.isPrepared) {
                boolean show = true;
                if (st.refreshMenuContent) {
                    st.isPrepared = false;
                    show = preparePanel(st, event);
                }
                if (show) {
                    openPanel(st, event);
                    handled = true;
                }
            }
        } else if (this.mDecorContentParent.isOverflowMenuShowing()) {
            handled = this.mDecorContentParent.hideOverflowMenu();
        } else if (!this.mIsDestroyed && preparePanel(st, event)) {
            handled = this.mDecorContentParent.showOverflowMenu();
        }
        if (handled) {
            AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
            if (audioManager != null) {
                audioManager.playSoundEffect(0);
            } else {
                Log.w("AppCompatDelegate", "Couldn't get audio manager");
            }
        }
        return handled;
    }

    /* access modifiers changed from: package-private */
    public void callOnPanelClosed(int featureId, PanelFeatureState panel, Menu menu) {
        if (menu == null) {
            if (panel == null && featureId >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.mPanels;
                if (featureId < panelFeatureStateArr.length) {
                    panel = panelFeatureStateArr[featureId];
                }
            }
            if (panel != null) {
                menu = panel.menu;
            }
        }
        if ((panel == null || panel.isOpen) && !this.mIsDestroyed) {
            this.mAppCompatWindowCallback.getWrapped().onPanelClosed(featureId, menu);
        }
    }

    /* access modifiers changed from: package-private */
    public PanelFeatureState findMenuPanel(Menu menu) {
        PanelFeatureState[] panels = this.mPanels;
        int N = panels != null ? panels.length : 0;
        for (int i = 0; i < N; i++) {
            PanelFeatureState panel = panels[i];
            if (panel != null && panel.menu == menu) {
                return panel;
            }
        }
        return null;
    }

    /* JADX INFO: Multiple debug info for r0v1 android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState: [D('st' android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState), D('nar' android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState[])] */
    /* access modifiers changed from: protected */
    public PanelFeatureState getPanelState(int featureId, boolean required) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        PanelFeatureState[] ar = panelFeatureStateArr;
        if (panelFeatureStateArr == null || ar.length <= featureId) {
            PanelFeatureState[] nar = new PanelFeatureState[(featureId + 1)];
            if (ar != null) {
                System.arraycopy(ar, 0, nar, 0, ar.length);
            }
            ar = nar;
            this.mPanels = nar;
        }
        PanelFeatureState st = ar[featureId];
        if (st != null) {
            return st;
        }
        PanelFeatureState panelFeatureState = new PanelFeatureState(featureId);
        PanelFeatureState st2 = panelFeatureState;
        ar[featureId] = panelFeatureState;
        return st2;
    }

    private boolean performPanelShortcut(PanelFeatureState st, int keyCode, KeyEvent event, int flags) {
        if (event.isSystem()) {
            return false;
        }
        boolean handled = false;
        if ((st.isPrepared || preparePanel(st, event)) && st.menu != null) {
            handled = st.menu.performShortcut(keyCode, event, flags);
        }
        if (handled && (flags & 1) == 0 && this.mDecorContentParent == null) {
            closePanel(st, true);
        }
        return handled;
    }

    private void invalidatePanelMenu(int featureId) {
        this.mInvalidatePanelMenuFeatures |= 1 << featureId;
        if (!this.mInvalidatePanelMenuPosted) {
            ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void doInvalidatePanelMenu(int featureId) {
        PanelFeatureState st;
        PanelFeatureState st2 = getPanelState(featureId, true);
        if (st2.menu != null) {
            Bundle savedActionViewStates = new Bundle();
            st2.menu.saveActionViewStates(savedActionViewStates);
            if (savedActionViewStates.size() > 0) {
                st2.frozenActionViewState = savedActionViewStates;
            }
            st2.menu.stopDispatchingItemsChanged();
            st2.menu.clear();
        }
        st2.refreshMenuContent = true;
        st2.refreshDecorView = true;
        if ((featureId == 108 || featureId == 0) && this.mDecorContentParent != null && (st = getPanelState(0, false)) != null) {
            st.isPrepared = false;
            preparePanel(st, null);
        }
    }

    /* access modifiers changed from: package-private */
    public int updateStatusGuard(int insetTop) {
        boolean showStatusGuard = false;
        ActionBarContextView actionBarContextView = this.mActionModeView;
        int i = 0;
        if (actionBarContextView != null && (actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) this.mActionModeView.getLayoutParams();
            boolean mlpChanged = false;
            if (this.mActionModeView.isShown()) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect insets = this.mTempRect1;
                Rect localInsets = this.mTempRect2;
                insets.set(0, insetTop, 0, 0);
                ViewUtils.computeFitSystemWindows(this.mSubDecor, insets, localInsets);
                if (mlp.topMargin != (localInsets.top == 0 ? insetTop : 0)) {
                    mlpChanged = true;
                    mlp.topMargin = insetTop;
                    View view = this.mStatusGuard;
                    if (view == null) {
                        this.mStatusGuard = new View(this.mContext);
                        this.mStatusGuard.setBackgroundColor(this.mContext.getResources().getColor(C0233R.color.abc_input_method_navigation_guard));
                        this.mSubDecor.addView(this.mStatusGuard, -1, new ViewGroup.LayoutParams(-1, insetTop));
                    } else {
                        ViewGroup.LayoutParams lp = view.getLayoutParams();
                        if (lp.height != insetTop) {
                            lp.height = insetTop;
                            this.mStatusGuard.setLayoutParams(lp);
                        }
                    }
                }
                showStatusGuard = this.mStatusGuard != null;
                if (!this.mOverlayActionMode && showStatusGuard) {
                    insetTop = 0;
                }
            } else if (mlp.topMargin != 0) {
                mlpChanged = true;
                mlp.topMargin = 0;
            }
            if (mlpChanged) {
                this.mActionModeView.setLayoutParams(mlp);
            }
        }
        View view2 = this.mStatusGuard;
        if (view2 != null) {
            if (!showStatusGuard) {
                i = 8;
            }
            view2.setVisibility(i);
        }
        return insetTop;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private int sanitizeWindowFeatureId(int featureId) {
        if (featureId == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (featureId != 9) {
            return featureId;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    /* access modifiers changed from: package-private */
    public ViewGroup getSubDecor() {
        return this.mSubDecor;
    }

    /* access modifiers changed from: package-private */
    public void dismissPopups() {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                } catch (IllegalArgumentException e) {
                }
            }
            this.mActionModePopup = null;
        }
        endOnGoingFadeAnimation();
        PanelFeatureState st = getPanelState(0, false);
        if (st != null && st.menu != null) {
            st.menu.close();
        }
    }

    public boolean applyDayNight() {
        return applyDayNight(true);
    }

    private boolean applyDayNight(boolean recreateIfNeeded) {
        if (this.mIsDestroyed) {
            return false;
        }
        int nightMode = calculateNightMode();
        boolean applied = updateForNightMode(mapNightMode(nightMode), recreateIfNeeded);
        if (nightMode == 0) {
            getAutoTimeNightModeManager().setup();
        } else {
            AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
            if (autoNightModeManager != null) {
                autoNightModeManager.cleanup();
            }
        }
        if (nightMode == 3) {
            getAutoBatteryNightModeManager().setup();
        } else {
            AutoNightModeManager autoNightModeManager2 = this.mAutoBatteryNightModeManager;
            if (autoNightModeManager2 != null) {
                autoNightModeManager2.cleanup();
            }
        }
        return applied;
    }

    public void setLocalNightMode(int mode) {
        if (this.mLocalNightMode != mode) {
            this.mLocalNightMode = mode;
            applyDayNight();
        }
    }

    public int getLocalNightMode() {
        return this.mLocalNightMode;
    }

    /* access modifiers changed from: package-private */
    public int mapNightMode(int mode) {
        if (mode == -100) {
            return -1;
        }
        if (mode != -1) {
            if (mode != 0) {
                if (!(mode == 1 || mode == 2)) {
                    if (mode == 3) {
                        return getAutoBatteryNightModeManager().getApplyableNightMode();
                    }
                    throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
                }
            } else if (Build.VERSION.SDK_INT < 23 || ((UiModeManager) this.mContext.getSystemService(UiModeManager.class)).getNightMode() != 0) {
                return getAutoTimeNightModeManager().getApplyableNightMode();
            } else {
                return -1;
            }
        }
        return mode;
    }

    private int calculateNightMode() {
        int i = this.mLocalNightMode;
        return i != -100 ? i : getDefaultNightMode();
    }

    private boolean updateForNightMode(int mode, boolean allowRecreation) {
        int newNightMode;
        boolean handled = false;
        int applicationNightMode = this.mContext.getApplicationContext().getResources().getConfiguration().uiMode & 48;
        if (mode == 1) {
            newNightMode = 16;
        } else if (mode != 2) {
            newNightMode = applicationNightMode;
        } else {
            newNightMode = 32;
        }
        boolean activityHandlingUiMode = isActivityManifestHandlingUiMode();
        if (newNightMode != applicationNightMode && !activityHandlingUiMode && Build.VERSION.SDK_INT >= 17 && !this.mBaseContextAttached && (this.mHost instanceof android.view.ContextThemeWrapper)) {
            Configuration conf = new Configuration();
            conf.uiMode = (conf.uiMode & -49) | newNightMode;
            try {
                ((android.view.ContextThemeWrapper) this.mHost).applyOverrideConfiguration(conf);
                handled = true;
            } catch (IllegalStateException e) {
                handled = false;
            }
        }
        if (!handled && !activityHandlingUiMode && (this.mContext.getResources().getConfiguration().uiMode & 48) != newNightMode) {
            if (allowRecreation && (Build.VERSION.SDK_INT >= 17 || this.mCreated)) {
                Object obj = this.mHost;
                if (obj instanceof Activity) {
                    ActivityCompat.recreate((Activity) obj);
                    handled = true;
                }
            }
            if (!handled) {
                updateResourcesConfigurationForNightMode(newNightMode);
                handled = true;
            }
        }
        if (handled || activityHandlingUiMode) {
            Object obj2 = this.mHost;
            if (obj2 instanceof AppCompatActivity) {
                ((AppCompatActivity) obj2).onNightModeChanged(mode);
            }
        }
        return handled;
    }

    private void updateResourcesConfigurationForNightMode(int uiModeNightModeValue) {
        Resources res = this.mContext.getResources();
        Configuration conf = new Configuration();
        conf.uiMode = (res.getConfiguration().uiMode & -49) | uiModeNightModeValue;
        res.updateConfiguration(conf, null);
        if (Build.VERSION.SDK_INT < 26) {
            ResourcesFlusher.flush(res);
        }
        int i = this.mThemeResId;
        if (i != 0) {
            this.mContext.setTheme(i);
            if (Build.VERSION.SDK_INT >= 23) {
                this.mContext.getTheme().applyStyle(this.mThemeResId, true);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @NonNull
    public final AutoNightModeManager getAutoTimeNightModeManager() {
        if (this.mAutoTimeNightModeManager == null) {
            this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(this.mContext));
        }
        return this.mAutoTimeNightModeManager;
    }

    private AutoNightModeManager getAutoBatteryNightModeManager() {
        if (this.mAutoBatteryNightModeManager == null) {
            this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(this.mContext);
        }
        return this.mAutoBatteryNightModeManager;
    }

    private boolean isActivityManifestHandlingUiMode() {
        if (!this.mActivityHandlesUiModeChecked && (this.mHost instanceof Activity)) {
            PackageManager pm = this.mContext.getPackageManager();
            if (pm == null) {
                return false;
            }
            try {
                ActivityInfo info = pm.getActivityInfo(new ComponentName(this.mContext, this.mHost.getClass()), 0);
                this.mActivityHandlesUiMode = (info == null || (info.configChanges & 512) == 0) ? false : true;
            } catch (PackageManager.NameNotFoundException e) {
                Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
                this.mActivityHandlesUiMode = false;
            }
        }
        this.mActivityHandlesUiModeChecked = true;
        return this.mActivityHandlesUiMode;
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$ActionModeCallbackWrapperV9 */
    class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(ActionMode.Callback wrapped) {
            this.mWrapped = wrapped;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return this.mWrapped.onCreateActionMode(mode, menu);
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(mode, menu);
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        public void onDestroyActionMode(ActionMode mode) {
            this.mWrapped.onDestroyActionMode(mode);
            if (AppCompatDelegateImpl.this.mActionModePopup != null) {
                AppCompatDelegateImpl.this.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup);
            }
            if (AppCompatDelegateImpl.this.mActionModeView != null) {
                AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                appCompatDelegateImpl.mFadeAnim = ViewCompat.animate(appCompatDelegateImpl.mActionModeView).alpha(0.0f);
                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() {
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                        if (AppCompatDelegateImpl.this.mActionModePopup != null) {
                            AppCompatDelegateImpl.this.mActionModePopup.dismiss();
                        } else if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent());
                        }
                        AppCompatDelegateImpl.this.mActionModeView.removeAllViews();
                        AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                        AppCompatDelegateImpl.this.mFadeAnim = null;
                    }
                });
            }
            if (AppCompatDelegateImpl.this.mAppCompatCallback != null) {
                AppCompatDelegateImpl.this.mAppCompatCallback.onSupportActionModeFinished(AppCompatDelegateImpl.this.mActionMode);
            }
            AppCompatDelegateImpl.this.mActionMode = null;
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$PanelMenuPresenterCallback */
    private final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            Menu parentMenu = menu.getRootMenu();
            boolean isSubMenu = parentMenu != menu;
            PanelFeatureState panel = AppCompatDelegateImpl.this.findMenuPanel(isSubMenu ? parentMenu : menu);
            if (panel == null) {
                return;
            }
            if (isSubMenu) {
                AppCompatDelegateImpl.this.callOnPanelClosed(panel.featureId, panel, parentMenu);
                AppCompatDelegateImpl.this.closePanel(panel, true);
                return;
            }
            AppCompatDelegateImpl.this.closePanel(panel, allMenusAreClosing);
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            Window.Callback cb;
            if (subMenu != null || !AppCompatDelegateImpl.this.mHasActionBar || (cb = AppCompatDelegateImpl.this.getWindowCallback()) == null || AppCompatDelegateImpl.this.mIsDestroyed) {
                return true;
            }
            cb.onMenuOpened(108, subMenu);
            return true;
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$ActionMenuPresenterCallback */
    private final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            Window.Callback cb = AppCompatDelegateImpl.this.getWindowCallback();
            if (cb == null) {
                return true;
            }
            cb.onMenuOpened(108, subMenu);
            return true;
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
            AppCompatDelegateImpl.this.checkCloseActionMenu(menu);
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState */
    protected static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;

        /* renamed from: x */
        int f15x;

        /* renamed from: y */
        int f16y;

        PanelFeatureState(int featureId2) {
            this.featureId = featureId2;
        }

        public boolean hasPanelItems() {
            if (this.shownPanelView == null) {
                return false;
            }
            if (this.createdPanelView == null && this.listMenuPresenter.getAdapter().getCount() <= 0) {
                return false;
            }
            return true;
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.listMenuPresenter);
            }
            this.listMenuPresenter = null;
        }

        /* access modifiers changed from: package-private */
        public void setStyle(Context context) {
            TypedValue outValue = new TypedValue();
            Resources.Theme widgetTheme = context.getResources().newTheme();
            widgetTheme.setTo(context.getTheme());
            widgetTheme.resolveAttribute(C0233R.attr.actionBarPopupTheme, outValue, true);
            if (outValue.resourceId != 0) {
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            widgetTheme.resolveAttribute(C0233R.attr.panelMenuListTheme, outValue, true);
            if (outValue.resourceId != 0) {
                widgetTheme.applyStyle(outValue.resourceId, true);
            } else {
                widgetTheme.applyStyle(C0233R.style.Theme_AppCompat_CompactMenu, true);
            }
            Context context2 = new ContextThemeWrapper(context, 0);
            context2.getTheme().setTo(widgetTheme);
            this.listPresenterContext = context2;
            TypedArray a = context2.obtainStyledAttributes(C0233R.styleable.AppCompatTheme);
            this.background = a.getResourceId(C0233R.styleable.AppCompatTheme_panelBackground, 0);
            this.windowAnimations = a.getResourceId(C0233R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            a.recycle();
        }

        /* access modifiers changed from: package-private */
        public void setMenu(MenuBuilder menu2) {
            ListMenuPresenter listMenuPresenter2;
            MenuBuilder menuBuilder = this.menu;
            if (menu2 != menuBuilder) {
                if (menuBuilder != null) {
                    menuBuilder.removeMenuPresenter(this.listMenuPresenter);
                }
                this.menu = menu2;
                if (menu2 != null && (listMenuPresenter2 = this.listMenuPresenter) != null) {
                    menu2.addMenuPresenter(listMenuPresenter2);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public MenuView getListMenuView(MenuPresenter.Callback cb) {
            if (this.menu == null) {
                return null;
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, C0233R.layout.abc_list_menu_item_layout);
                this.listMenuPresenter.setCallback(cb);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        /* access modifiers changed from: package-private */
        public Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            if (this.menu != null) {
                savedState.menuState = new Bundle();
                this.menu.savePresenterStates(savedState.menuState);
            }
            return savedState;
        }

        /* access modifiers changed from: package-private */
        public void onRestoreInstanceState(Parcelable state) {
            SavedState savedState = (SavedState) state;
            this.featureId = savedState.featureId;
            this.wasLastOpen = savedState.isOpen;
            this.frozenMenuState = savedState.menuState;
            this.shownPanelView = null;
            this.decorView = null;
        }

        /* access modifiers changed from: package-private */
        public void applyFrozenState() {
            Bundle bundle;
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null && (bundle = this.frozenMenuState) != null) {
                menuBuilder.restorePresenterStates(bundle);
                this.frozenMenuState = null;
            }
        }

        @SuppressLint({"BanParcelableUsage"})
        /* renamed from: android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState$SavedState */
        private static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
                public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                    return SavedState.readFromParcel(in, loader);
                }

                public SavedState createFromParcel(Parcel in) {
                    return SavedState.readFromParcel(in, null);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
            int featureId;
            boolean isOpen;
            Bundle menuState;

            SavedState() {
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.featureId);
                dest.writeInt(this.isOpen ? 1 : 0);
                if (this.isOpen) {
                    dest.writeBundle(this.menuState);
                }
            }

            static SavedState readFromParcel(Parcel source, ClassLoader loader) {
                SavedState savedState = new SavedState();
                savedState.featureId = source.readInt();
                boolean z = true;
                if (source.readInt() != 1) {
                    z = false;
                }
                savedState.isOpen = z;
                if (savedState.isOpen) {
                    savedState.menuState = source.readBundle(loader);
                }
                return savedState;
            }
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$ListMenuDecorView */
    private class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        public boolean dispatchKeyEvent(KeyEvent event) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
        }

        public boolean onInterceptTouchEvent(MotionEvent event) {
            if (event.getAction() != 0 || !isOutOfBounds((int) event.getX(), (int) event.getY())) {
                return super.onInterceptTouchEvent(event);
            }
            AppCompatDelegateImpl.this.closePanel(0);
            return true;
        }

        public void setBackgroundResource(int resid) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), resid));
        }

        private boolean isOutOfBounds(int x, int y) {
            return x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5;
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$AppCompatWindowCallback */
    class AppCompatWindowCallback extends WindowCallbackWrapper {
        AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        public boolean dispatchKeyEvent(KeyEvent event) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
        }

        public boolean dispatchKeyShortcutEvent(KeyEvent event) {
            return super.dispatchKeyShortcutEvent(event) || AppCompatDelegateImpl.this.onKeyShortcut(event.getKeyCode(), event);
        }

        public boolean onCreatePanelMenu(int featureId, Menu menu) {
            if (featureId != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(featureId, menu);
            }
            return false;
        }

        public void onContentChanged() {
        }

        public boolean onPreparePanel(int featureId, View view, Menu menu) {
            MenuBuilder mb = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (featureId == 0 && mb == null) {
                return false;
            }
            if (mb != null) {
                mb.setOverrideVisibleItems(true);
            }
            boolean handled = super.onPreparePanel(featureId, view, menu);
            if (mb != null) {
                mb.setOverrideVisibleItems(false);
            }
            return handled;
        }

        public boolean onMenuOpened(int featureId, Menu menu) {
            super.onMenuOpened(featureId, menu);
            AppCompatDelegateImpl.this.onMenuOpened(featureId);
            return true;
        }

        public void onPanelClosed(int featureId, Menu menu) {
            super.onPanelClosed(featureId, menu);
            AppCompatDelegateImpl.this.onPanelClosed(featureId);
        }

        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null;
            }
            if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
                return startAsSupportActionMode(callback);
            }
            return super.onWindowStartingActionMode(callback);
        }

        /* access modifiers changed from: package-private */
        public final android.view.ActionMode startAsSupportActionMode(ActionMode.Callback callback) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, callback);
            android.support.p004v7.view.ActionMode supportActionMode = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper);
            if (supportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(supportActionMode);
            }
            return null;
        }

        @RequiresApi(23)
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
            if (!AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() || type != 0) {
                return super.onWindowStartingActionMode(callback, type);
            }
            return startAsSupportActionMode(callback);
        }

        @RequiresApi(24)
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
            PanelFeatureState panel = AppCompatDelegateImpl.this.getPanelState(0, true);
            if (panel == null || panel.menu == null) {
                super.onProvideKeyboardShortcuts(data, menu, deviceId);
            } else {
                super.onProvideKeyboardShortcuts(data, panel.menu, deviceId);
            }
        }
    }

    @VisibleForTesting
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$AutoNightModeManager */
    abstract class AutoNightModeManager {
        private BroadcastReceiver mReceiver;

        /* access modifiers changed from: package-private */
        @Nullable
        public abstract IntentFilter createIntentFilterForBroadcastReceiver();

        /* access modifiers changed from: package-private */
        public abstract int getApplyableNightMode();

        /* access modifiers changed from: package-private */
        public abstract void onChange();

        AutoNightModeManager() {
        }

        /* access modifiers changed from: package-private */
        public void setup() {
            cleanup();
            IntentFilter filter = createIntentFilterForBroadcastReceiver();
            if (filter != null && filter.countActions() != 0) {
                if (this.mReceiver == null) {
                    this.mReceiver = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent intent) {
                            AutoNightModeManager.this.onChange();
                        }
                    };
                }
                AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, filter);
            }
        }

        /* access modifiers changed from: package-private */
        public void cleanup() {
            if (this.mReceiver != null) {
                try {
                    AppCompatDelegateImpl.this.mContext.unregisterReceiver(this.mReceiver);
                } catch (IllegalArgumentException e) {
                }
                this.mReceiver = null;
            }
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$AutoTimeNightModeManager */
    private class AutoTimeNightModeManager extends AutoNightModeManager {
        private final TwilightManager mTwilightManager;

        AutoTimeNightModeManager(@NonNull TwilightManager twilightManager) {
            super();
            this.mTwilightManager = twilightManager;
        }

        public int getApplyableNightMode() {
            return this.mTwilightManager.isNight() ? 2 : 1;
        }

        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }

        /* access modifiers changed from: package-private */
        public IntentFilter createIntentFilterForBroadcastReceiver() {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.TIME_SET");
            filter.addAction("android.intent.action.TIMEZONE_CHANGED");
            filter.addAction("android.intent.action.TIME_TICK");
            return filter;
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$AutoBatteryNightModeManager */
    private class AutoBatteryNightModeManager extends AutoNightModeManager {
        private final PowerManager mPowerManager;

        AutoBatteryNightModeManager(@NonNull Context context) {
            super();
            this.mPowerManager = (PowerManager) context.getSystemService("power");
        }

        public int getApplyableNightMode() {
            if (Build.VERSION.SDK_INT < 21 || !this.mPowerManager.isPowerSaveMode()) {
                return 1;
            }
            return 2;
        }

        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }

        /* access modifiers changed from: package-private */
        public IntentFilter createIntentFilterForBroadcastReceiver() {
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return filter;
        }
    }

    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl();
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$ActionBarDrawableToggleImpl */
    private class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
        ActionBarDrawableToggleImpl() {
        }

        public Drawable getThemeUpIndicator() {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), (AttributeSet) null, new int[]{C0233R.attr.homeAsUpIndicator});
            Drawable result = a.getDrawable(0);
            a.recycle();
            return result;
        }

        public Context getActionBarThemedContext() {
            return AppCompatDelegateImpl.this.getActionBarThemedContext();
        }

        public boolean isNavigationVisible() {
            ActionBar ab = AppCompatDelegateImpl.this.getSupportActionBar();
            return (ab == null || (ab.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarUpIndicator(Drawable upDrawable, int contentDescRes) {
            ActionBar ab = AppCompatDelegateImpl.this.getSupportActionBar();
            if (ab != null) {
                ab.setHomeAsUpIndicator(upDrawable);
                ab.setHomeActionContentDescription(contentDescRes);
            }
        }

        public void setActionBarDescription(int contentDescRes) {
            ActionBar ab = AppCompatDelegateImpl.this.getSupportActionBar();
            if (ab != null) {
                ab.setHomeActionContentDescription(contentDescRes);
            }
        }
    }
}
