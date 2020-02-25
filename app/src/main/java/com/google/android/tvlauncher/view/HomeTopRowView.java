package com.google.android.tvlauncher.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.ClickEvent;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.application.TvLauncherApplicationBase;
import com.google.android.tvlauncher.appsview.PackageChangedReceiver;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.home.HomeRow;
import com.google.android.tvlauncher.home.HomeTopRowButton;
import com.google.android.tvlauncher.home.OnHomeRowRemovedListener;
import com.google.android.tvlauncher.home.OnHomeRowSelectedListener;
import com.google.android.tvlauncher.home.OnHomeStateChangeListener;
import com.google.android.tvlauncher.notifications.NotificationsPanelButtonView;
import com.google.android.tvlauncher.notifications.NotificationsPanelController;
import com.google.android.tvlauncher.notifications.NotificationsTrayAdapter;
import com.google.android.tvlauncher.notifications.NotificationsTrayView;
import com.google.android.tvlauncher.settings.ProfilesManager;
import com.google.android.tvlauncher.util.IntentLaunchDispatcher;
import com.google.android.tvlauncher.util.OemConfiguration;
import com.google.android.tvlauncher.widget.PartnerWidgetInfo;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;

public class HomeTopRowView extends LinearLayout implements View.OnFocusChangeListener, HomeRow, PackageChangedReceiver.Listener {
    public static final int STATE_DEFAULT = 0;
    public static final int STATE_TOP_BAR_SELECTED = 1;
    public static final int STATE_TRAY_SELECTED = 2;
    private static final String SETTINGS_PACKAGE_NAME = "com.android.tv.settings";
    private static final String TAG = "HomeTopRowView";
    /* access modifiers changed from: private */
    public EventLogger mEventLogger;
    private final SearchView.ActionCallbacks mSearchViewActionCallbacks = new SearchView.ActionCallbacks() {
        public void onStartedVoiceSearch() {
            HomeTopRowView.this.mEventLogger.log(new ClickEvent(TvlauncherLogEnum.TvLauncherEventCode.START_VOICE_SEARCH).setVisualElementTag(TvLauncherConstants.VOICE_SEARCH_BUTTON));
        }

        public void onStartedKeyboardSearch() {
            HomeTopRowView.this.mEventLogger.log(new ClickEvent(TvlauncherLogEnum.TvLauncherEventCode.START_KEYBOARD_SEARCH).setVisualElementTag(TvLauncherConstants.KEYBOARD_SEARCH_BUTTON));
        }
    };
    /* access modifiers changed from: private */
    public OnHomeTopRowFocusChangedListener mFocusChangeListener;
    /* access modifiers changed from: private */
    public ViewGroup mItemsContainer;
    /* access modifiers changed from: private */
    public NotificationsTrayView mNotificationsTray;
    /* access modifiers changed from: private */
    public OnHomeRowSelectedListener mOnHomeRowSelectedListener;
    /* access modifiers changed from: private */
    public int mState = 0;
    private final ViewTreeObserver.OnGlobalFocusChangeListener mGlobalFocusChangeListener = new ViewTreeObserver.OnGlobalFocusChangeListener() {
        public void onGlobalFocusChanged(View oldFocus, View newFocus) {
            if (HomeTopRowView.this.findFocus() == newFocus) {
                HomeTopRowView.this.mOnHomeRowSelectedListener.onHomeRowSelected(HomeTopRowView.this);
            }
            if ((!HomeTopRowView.this.mNotificationsTray.hasFocus() || HomeTopRowView.this.mState != 1) && (!HomeTopRowView.this.mItemsContainer.hasFocus() || HomeTopRowView.this.mState != 2)) {
                if (((oldFocus instanceof SearchOrb) || (newFocus instanceof SearchOrb)) && HomeTopRowView.this.mFocusChangeListener != null) {
                    HomeTopRowView.this.mFocusChangeListener.onHomeTopRowFocusChanged();
                }
            } else if (HomeTopRowView.this.mFocusChangeListener != null) {
                HomeTopRowView.this.mFocusChangeListener.onHomeTopRowFocusChanged();
            }
        }
    };
    private Context mContext;
    private int mDefaultItemsContainerBottomMargin;
    private int mDefaultItemsContainerTopMargin;
    private int mDuration;
    private float mFocusedElevation;
    private float mFocusedZoom;
    private HomeTopRowButton mInputs;
    private IntentLaunchDispatcher mIntentLauncher;
    private OnActionListener mOnActionListener;
    private PackageChangedReceiver mPackageChangedReceiver;
    private NotificationsPanelController mPanelController = null;
    private HomeTopRowButton mPartnerWidget;
    private PartnerWidgetInfo mPartnerWidgetInfo = null;
    private HomeTopRowButton mProfiles;
    private SearchView mSearch;
    private int mSelectedItemsContainerBottomMargin;
    private int mSelectedItemsContainerTopMargin;
    private float mUnfocusedElevation;

    public HomeTopRowView(Context context) {
        super(context);
        init(context);
    }

    public HomeTopRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeTopRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public SearchView.ActionCallbacks getSearchViewActionCallbacks() {
        return this.mSearchViewActionCallbacks;
    }

    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        if (this.mNotificationsTray.getVisibility() == 0) {
            views.add(this.mNotificationsTray);
        }
        super.addFocusables(views, direction, focusableMode);
    }

    private void init(Context context) {
        this.mContext = context;
        Resources resources = context.getResources();
        this.mIntentLauncher = ((TvLauncherApplicationBase) this.mContext.getApplicationContext()).getIntentLauncher();
        this.mFocusedElevation = resources.getDimension(C1188R.dimen.top_row_item_focused_z);
        this.mUnfocusedElevation = resources.getDimension(C1188R.dimen.top_row_item_unfocused_z);
        this.mFocusedZoom = resources.getFraction(C1188R.fraction.top_row_item_focused_zoom, 1, 1);
        this.mDuration = resources.getInteger(C1188R.integer.top_row_scale_duration_ms);
        this.mDefaultItemsContainerTopMargin = resources.getDimensionPixelSize(C1188R.dimen.top_row_items_container_margin_top);
        this.mDefaultItemsContainerBottomMargin = resources.getDimensionPixelSize(C1188R.dimen.top_row_items_container_margin_bottom);
        this.mSelectedItemsContainerTopMargin = resources.getDimensionPixelSize(C1188R.dimen.top_row_selected_items_container_margin_top);
        this.mSelectedItemsContainerBottomMargin = resources.getDimensionPixelSize(C1188R.dimen.top_row_selected_items_container_margin_bottom);
        this.mPackageChangedReceiver = new PackageChangedReceiver(this);
    }

    public void setOnActionListener(OnActionListener listener) {
        this.mOnActionListener = listener;
    }

    public void setEventLogger(EventLogger eventLogger) {
        this.mEventLogger = eventLogger;
    }

    public void setFocusChangeListener(OnHomeTopRowFocusChangedListener focusChangeListener) {
        this.mFocusChangeListener = focusChangeListener;
    }

    public void updateNotificationsTrayVisibility() {
        this.mNotificationsTray.updateVisibility();
        updateMargins();
    }

    public void hideNotificationsTray() {
        NotificationsTrayView notificationsTrayView = this.mNotificationsTray;
        if (notificationsTrayView != null) {
            notificationsTrayView.setVisibility(8);
        }
    }

    public NotificationsTrayAdapter getNotificationsTrayAdapter() {
        return this.mNotificationsTray.getTrayAdapter();
    }

    public void setNotificationsTrayAdapter(NotificationsTrayAdapter adapter) {
        this.mNotificationsTray.setTrayAdapter(adapter);
    }

    public NotificationsPanelController getNotificationsPanelController() {
        return this.mPanelController;
    }

    public void setNotificationsPanelController(NotificationsPanelController controller) {
        this.mPanelController = controller;
        this.mPanelController.setView((NotificationsPanelButtonView) findViewById(C1188R.C1191id.notification_panel_button));
    }

    public SearchView getSearchWidget() {
        return this.mSearch;
    }

    public ViewGroup getItemsContainer() {
        return this.mItemsContainer;
    }

    public NotificationsTrayView getNotificationsTray() {
        return this.mNotificationsTray;
    }

    public void updateInputIconVisibility(boolean visible) {
        this.mInputs.setVisibility(visible ? 0 : 8);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mItemsContainer = (ViewGroup) findViewById(C1188R.C1191id.items_container);
        this.mSearch = (SearchView) findViewById(C1188R.C1191id.search_view);
        this.mSearch.setOutlineProvider(new ViewOutlineProvider(this) {
            public void getOutline(View view, Outline outline) {
                outline.setOval(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        });
        this.mSearch.registerActionsCallbacks(this.mSearchViewActionCallbacks);
        this.mProfiles = (HomeTopRowButton) findViewById(C1188R.C1191id.profiles);
        this.mProfiles.setIcon(C1188R.C1189drawable.ic_tv_options_parental_black);
        this.mProfiles.setText(C1188R.string.restricted_profile_icon_title);
        this.mProfiles.setContentDescription(getResources().getString(C1188R.string.profiles_accessibility_description));
        this.mProfiles.setOnClickListener(new HomeTopRowView$$Lambda$0(this));
        updateProfiles();
        this.mInputs = (HomeTopRowButton) findViewById(C1188R.C1191id.inputs);
        Uri inputsUri = OemConfiguration.get(this.mContext).getOemInputsIconUri();
        if (inputsUri != null) {
            ((RequestBuilder) Glide.with(this.mContext).asDrawable().load(inputsUri).error(C1188R.C1189drawable.ic_action_inputs_black)).into(new ImageViewTarget<Drawable>(this, this.mInputs.getIconImageView()) {
                /* access modifiers changed from: protected */
                public void setResource(@Nullable Drawable resource) {
                    setDrawable(resource);
                }
            });
        } else {
            this.mInputs.setIcon(C1188R.C1189drawable.ic_action_inputs_black);
        }
        String label = OemConfiguration.get(this.mContext).getInputsPanelLabelText(this.mContext);
        this.mInputs.setText(label);
        this.mInputs.setContentDescription(label);
        this.mInputs.setOnClickListener(new HomeTopRowView$$Lambda$1(this));
        this.mPartnerWidget = (HomeTopRowButton) findViewById(C1188R.C1191id.partner_widget);
        this.mPartnerWidget.setOnClickListener(new HomeTopRowView$$Lambda$2(this));
        updatePartnerWidget();
        HomeTopRowButton settings = (HomeTopRowButton) findViewById(C1188R.C1191id.settings);
        settings.setIcon(C1188R.C1189drawable.ic_action_settings_black);
        settings.setText(C1188R.string.settings_icon_title);
        settings.setContentDescription(getResources().getString(C1188R.string.settings_accessibility_description));
        settings.setOnClickListener(new HomeTopRowView$$Lambda$3(this));
        this.mNotificationsTray = (NotificationsTrayView) findViewById(C1188R.C1191id.notifications_tray);
        updateNotificationsTrayVisibility();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$0$HomeTopRowView(View v) {
        Intent launchIntent = ProfilesManager.getInstance(getContext()).getProfileIntent();
        if (launchIntent != null) {
            this.mContext.startActivity(launchIntent);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$1$HomeTopRowView(View v) {
        OnActionListener onActionListener = this.mOnActionListener;
        if (onActionListener != null) {
            onActionListener.onShowInputs();
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$2$HomeTopRowView(View v) {
        PartnerWidgetInfo partnerWidgetInfo = this.mPartnerWidgetInfo;
        if (partnerWidgetInfo != null && partnerWidgetInfo.isComplete()) {
            this.mIntentLauncher.launchIntentFromUri(this.mPartnerWidgetInfo.getIntent(), false);
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$onFinishInflate$3$HomeTopRowView(View v) {
        OnActionListener onActionListener = this.mOnActionListener;
        if (onActionListener != null) {
            onActionListener.onStartSettings();
        }
    }

    public void onFocusChange(View v, boolean hasFocus) {
        float scale = hasFocus ? this.mFocusedZoom : 1.0f;
        v.animate().z(hasFocus ? this.mFocusedElevation : this.mUnfocusedElevation).scaleX(scale).scaleY(scale).setDuration((long) this.mDuration);
    }

    public void setState(boolean selected) {
        boolean z = false;
        if (!selected) {
            this.mState = 0;
        } else if (this.mNotificationsTray.hasFocus()) {
            this.mState = 2;
        } else {
            this.mState = 1;
        }
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mSearch.getLayoutParams();
        lp.setMarginStart(getResources().getDimensionPixelOffset(C1188R.dimen.search_orb_margin_start));
        this.mSearch.setLayoutParams(lp);
        SearchView searchView = this.mSearch;
        if (this.mState == 1) {
            z = true;
        }
        searchView.bind(z);
        updateMargins();
    }

    private void updateMargins() {
        int i;
        int i2;
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) this.mItemsContainer.getLayoutParams();
        int i3 = this.mState;
        if (i3 != 0) {
            if (i3 == 1) {
                i = this.mSelectedItemsContainerTopMargin;
            } else {
                i = this.mDefaultItemsContainerTopMargin;
            }
            lp.topMargin = i;
            if (this.mNotificationsTray.getVisibility() == 0) {
                i2 = this.mDefaultItemsContainerBottomMargin;
            } else {
                i2 = this.mSelectedItemsContainerBottomMargin;
            }
            lp.bottomMargin = i2;
        } else {
            lp.topMargin = this.mDefaultItemsContainerTopMargin;
            lp.bottomMargin = this.mDefaultItemsContainerBottomMargin;
        }
        this.mItemsContainer.setLayoutParams(lp);
    }

    private void updateProfiles() {
        if (ProfilesManager.getInstance(this.mContext).hasRestrictedProfile()) {
            this.mProfiles.setVisibility(0);
        } else {
            this.mProfiles.setVisibility(8);
        }
    }

    public void onPartnerWidgetUpdate(PartnerWidgetInfo info) {
        this.mPartnerWidgetInfo = info;
        updatePartnerWidget();
    }

    private void updatePartnerWidget() {
        PartnerWidgetInfo partnerWidgetInfo = this.mPartnerWidgetInfo;
        if (partnerWidgetInfo == null || !partnerWidgetInfo.isComplete()) {
            this.mPartnerWidget.setVisibility(8);
            return;
        }
        this.mPartnerWidget.setIcon(this.mPartnerWidgetInfo.getIcon());
        this.mPartnerWidget.setText(this.mPartnerWidgetInfo.getTitle());
        this.mPartnerWidget.setContentDescription(this.mPartnerWidgetInfo.getTitle());
        this.mPartnerWidget.setVisibility(0);
    }

    public void setOnHomeStateChangeListener(OnHomeStateChangeListener listener) {
    }

    public void setOnHomeRowSelectedListener(OnHomeRowSelectedListener listener) {
        this.mOnHomeRowSelectedListener = listener;
    }

    public void setOnHomeRowRemovedListener(OnHomeRowRemovedListener listener) {
    }

    public void setHomeIsFastScrolling(boolean homeIsFastScrolling) {
    }

    public View getView() {
        return this;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalFocusChangeListener(this.mGlobalFocusChangeListener);
        this.mContext.unregisterReceiver(this.mPackageChangedReceiver);
        LaunchItemsManagerProvider.getInstance(getContext()).removeSearchPackageChangeListener(this.mSearch);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalFocusChangeListener(this.mGlobalFocusChangeListener);
        this.mContext.registerReceiver(this.mPackageChangedReceiver, PackageChangedReceiver.getIntentFilter());
        LaunchItemsManagerProvider.getInstance(getContext()).addSearchPackageChangeListener(this.mSearch);
    }

    public void updateProfiles(String packageName) {
        if (TextUtils.equals(SETTINGS_PACKAGE_NAME, packageName) && this.mProfiles != null) {
            updateProfiles();
        }
    }

    public void onPackageAdded(String packageName) {
        updateProfiles(packageName);
    }

    public void onPackageChanged(String packageName) {
        updateProfiles(packageName);
    }

    public void onPackageFullyRemoved(String packageName) {
        updateProfiles(packageName);
    }

    public void onPackageRemoved(String packageName) {
        updateProfiles(packageName);
    }

    public void onPackageReplaced(String packageName) {
        updateProfiles(packageName);
    }

    public interface OnActionListener {
        void onShowInputs();

        void onStartSettings();
    }

    public interface OnHomeTopRowFocusChangedListener {
        void onHomeTopRowFocusChanged();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }
}
