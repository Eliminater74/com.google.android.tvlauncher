package com.google.android.tvlauncher.appsview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.p001v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.TvlauncherLogEnum;
import com.google.android.tvlauncher.analytics.EventLogger;
import com.google.android.tvlauncher.analytics.LogEvent;
import com.google.android.tvlauncher.analytics.LoggingActivity;
import com.google.android.tvlauncher.analytics.UserActionEvent;
import com.google.android.tvlauncher.appsview.data.AppLinksDataManager;
import com.google.android.tvlauncher.util.AddBackgroundColorTransformation;
import com.google.android.tvlauncher.util.IntentUtil;
import com.google.android.tvlauncher.util.OemPromotionApp;
import com.google.android.tvlauncher.util.Util;
import com.google.android.tvrecommendations.shared.util.Constants;

import java.util.Arrays;
import java.util.List;

public class AddAppLinkActivity extends LoggingActivity {
    private static final String TAG = "AddAppLinkActivity";
    /* access modifiers changed from: private */
    public int mCornerRadius;
    /* access modifiers changed from: private */
    public EventLogger mEventLogger;
    /* access modifiers changed from: private */
    public ImageView mScreenshotView;
    private Button mAllowButton;
    private TextView mCategory;
    private Button mDenyButton;
    private TextView mDescription;
    private TextView mDeveloper;
    private LinearLayout mDialogView;
    private ImageView mIconView;
    private Button mOpenButton;
    private RequestOptions mRequestOptions;
    private TextView mTitle;

    public AddAppLinkActivity() {
        super(TAG);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mEventLogger = getEventLogger();
        setContentView(C1188R.layout.add_applink_dialog);
        this.mDialogView = (LinearLayout) findViewById(C1188R.C1191id.dialog_view);
        this.mIconView = (ImageView) findViewById(C1188R.C1191id.app_icon);
        this.mScreenshotView = (ImageView) findViewById(C1188R.C1191id.app_screenshot);
        this.mTitle = (TextView) findViewById(C1188R.C1191id.app_title);
        this.mDeveloper = (TextView) findViewById(C1188R.C1191id.app_developer);
        this.mDescription = (TextView) findViewById(C1188R.C1191id.app_description);
        this.mCategory = (TextView) findViewById(C1188R.C1191id.app_category);
        this.mOpenButton = (Button) findViewById(C1188R.C1191id.open_button);
        this.mAllowButton = (Button) findViewById(C1188R.C1191id.allow_button);
        this.mDenyButton = (Button) findViewById(C1188R.C1191id.deny_button);
        this.mCornerRadius = getResources().getDimensionPixelSize(C1188R.dimen.applink_dialog_image_rounded_corner_radius);
        Drawable placeholderBanner = new ColorDrawable(ContextCompat.getColor(this, C1188R.color.app_banner_background_color));
        this.mRequestOptions = (RequestOptions) ((RequestOptions) ((RequestOptions) ((RequestOptions) new RequestOptions().placeholder(placeholderBanner)).error(placeholderBanner)).diskCacheStrategy(DiskCacheStrategy.NONE)).transform(new AddBackgroundColorTransformation(getColor(C1188R.color.app_banner_background_color), true));
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        updateWindowAttributes(layoutParams);
        window.setAttributes(layoutParams);
        setUp(getIntent());
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Util.forceLandscapeOrientation(this);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        setUp(intent);
    }

    public void onBackPressed() {
        cancelAddAppLinkRequest();
    }

    /* access modifiers changed from: private */
    public void cancelAddAppLinkRequest(String packageName, String dataUri) {
        LogEvent event = new LogEvent(TvlauncherLogEnum.TvLauncherEventCode.DENY_ADD_APP_LINK);
        event.getAppLink().setPackageName(packageName).setIsInstalled(false);
        if (dataUri != null) {
            event.getAppLink().setUri(dataUri);
        }
        this.mEventLogger.log(event);
        cancelAddAppLinkRequest();
    }

    private void cancelAddAppLinkRequest() {
        setResult(0);
        finish();
    }

    private void setUp(Intent intent) {
        String callingPackageName = getCallingPackage();
        if (callingPackageName == null || !Util.isLauncherOrSystemApp(this, callingPackageName) || !hasStringExtraAndNotEmpty(intent, Constants.EXTRA_APP_NAME) || !hasStringExtraAndNotEmpty(intent, Constants.EXTRA_PACKAGE_NAME) || !hasStringExtraAndNotEmpty(intent, Constants.EXTRA_BANNER_URI) || !hasStringExtraAndNotEmpty(intent, Constants.EXTRA_DATA_URI) || !hasStringExtraAndNotEmpty(intent, Constants.EXTRA_DEVELOPER) || !hasStringExtraAndNotEmpty(intent, Constants.EXTRA_CATEGORY) || !hasStringExtraAndNotEmpty(intent, Constants.EXTRA_DESCRIPTION) || !intent.hasExtra(Constants.EXTRA_IS_GAME)) {
            Log.e(TAG, String.format("The metadata for installing the app link is invalid. App name: %s, Package name: %s, Banner uri: %s, Data uri: %s, Developer: %s , Category: %s, Description: %s, has IsGame extra: %s", intent.getStringExtra(Constants.EXTRA_APP_NAME), intent.getStringExtra(Constants.EXTRA_PACKAGE_NAME), intent.getStringExtra(Constants.EXTRA_BANNER_URI), intent.getStringExtra(Constants.EXTRA_DATA_URI), intent.getStringExtra(Constants.EXTRA_DEVELOPER), intent.getStringExtra(Constants.EXTRA_CATEGORY), intent.getStringExtra(Constants.EXTRA_DESCRIPTION), Boolean.valueOf(intent.hasExtra(Constants.EXTRA_IS_GAME))));
            cancelAddAppLinkRequest();
            return;
        }
        OemPromotionApp appPromotion = ((OemPromotionApp.Builder) ((OemPromotionApp.Builder) ((OemPromotionApp.Builder) ((OemPromotionApp.Builder) ((OemPromotionApp.Builder) ((OemPromotionApp.Builder) ((OemPromotionApp.Builder) ((OemPromotionApp.Builder) ((OemPromotionApp.Builder) new OemPromotionApp.Builder().setAppName(intent.getStringExtra(Constants.EXTRA_APP_NAME))).setPackageName(intent.getStringExtra(Constants.EXTRA_PACKAGE_NAME))).setBannerUri(intent.getStringExtra(Constants.EXTRA_BANNER_URI))).setDataUri(intent.getStringExtra(Constants.EXTRA_DATA_URI))).setDeveloper(intent.getStringExtra(Constants.EXTRA_DEVELOPER))).setCategory(intent.getStringExtra(Constants.EXTRA_CATEGORY))).setDescription(intent.getStringExtra(Constants.EXTRA_DESCRIPTION))).setGame(intent.getBooleanExtra(Constants.EXTRA_IS_GAME, false))).setVirtualApp(true)).build();
        if (AppLinksDataManager.getInstance(this).getAppLink(appPromotion.getId()) != null) {
            Log.e(TAG, "The app link is already installed");
            cancelAddAppLinkRequest();
            return;
        }
        String[] screenshots = intent.getStringArrayExtra(Constants.EXTRA_SCREENSHOTS);
        if (screenshots != null && screenshots.length > 0) {
            appPromotion.addScreenshotUris(Arrays.asList(screenshots));
        }
        displayUi(appPromotion);
    }

    private void displayUi(final OemPromotionApp appPromotion) {
        if (isFinishing() || isDestroyed()) {
            Log.e(TAG, "Activity is no longer running");
            return;
        }
        this.mOpenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                LogEvent event = new UserActionEvent(TvlauncherLogEnum.TvLauncherEventCode.START_APP);
                event.getAppLink().setPackageName(appPromotion.getPackageName()).setUri(appPromotion.getDataUri());
                AddAppLinkActivity.this.mEventLogger.log(event);
                AddAppLinkActivity.this.startActivity(IntentUtil.createVirtualAppIntent(appPromotion.getPackageName(), appPromotion.getDataUri()));
                AddAppLinkActivity.this.setResult(-1);
                AddAppLinkActivity.this.finish();
            }
        });
        this.mAllowButton.setText(C1188R.string.add);
        this.mAllowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AppLinksDataManager.getInstance(AddAppLinkActivity.this).createAppLink(appPromotion);
                LogEvent event = new LogEvent(TvlauncherLogEnum.TvLauncherEventCode.APPROVE_ADD_APP_LINK);
                event.getAppLink().setPackageName(appPromotion.getPackageName()).setIsInstalled(true);
                if (appPromotion.getDataUri() != null) {
                    event.getAppLink().setUri(appPromotion.getDataUri());
                }
                AddAppLinkActivity.this.mEventLogger.log(event);
                AddAppLinkActivity.this.setResult(-1);
                AddAppLinkActivity.this.finish();
            }
        });
        this.mDenyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AddAppLinkActivity.this.cancelAddAppLinkRequest(appPromotion.getPackageName(), appPromotion.getDataUri());
            }
        });
        CharSequence category = Html.fromHtml(getResources().getString(C1188R.string.app_category, TextUtils.htmlEncode(appPromotion.getCategory())), 0);
        this.mTitle.setText(appPromotion.getAppName());
        this.mDeveloper.setText(appPromotion.getDeveloper());
        this.mDescription.setText(appPromotion.getDescription());
        this.mCategory.setText(category);
        this.mDialogView.setVisibility(0);
        this.mOpenButton.requestFocus();
        ViewOutlineProvider outlineProvider = new ViewOutlineProvider() {
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), (float) AddAppLinkActivity.this.mCornerRadius);
            }
        };
        this.mIconView.setOutlineProvider(outlineProvider);
        this.mIconView.setClipToOutline(true);
        Glide.with((Activity) this).load(appPromotion.getBannerUri()).apply((BaseRequestOptions<?>) this.mRequestOptions).into(this.mIconView);
        List<String> screenshots = appPromotion.getScreenshotUris();
        if (screenshots.size() > 0) {
            this.mScreenshotView.setOutlineProvider(outlineProvider);
            this.mScreenshotView.setClipToOutline(true);
            Glide.with((Activity) this).load(screenshots.get(0)).into(new SimpleTarget<Drawable>() {
                public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                    onResourceReady((Drawable) obj, (Transition<? super Drawable>) transition);
                }

                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    AddAppLinkActivity.this.mScreenshotView.setImageDrawable(resource);
                    AddAppLinkActivity.this.mScreenshotView.setVisibility(0);
                }
            });
        }
    }

    private void updateWindowAttributes(WindowManager.LayoutParams outLayoutParams) {
        outLayoutParams.width = -1;
        outLayoutParams.height = -2;
        outLayoutParams.format = -1;
        outLayoutParams.gravity = 80;
    }

    private boolean hasStringExtraAndNotEmpty(Intent intent, String name) {
        return intent.hasExtra(name) && !TextUtils.isEmpty(intent.getStringExtra(name));
    }
}
