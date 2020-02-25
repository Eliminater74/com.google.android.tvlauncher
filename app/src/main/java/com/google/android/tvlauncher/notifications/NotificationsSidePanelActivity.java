package com.google.android.tvlauncher.notifications;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.p001v4.view.GravityCompat;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.analytics.LoggingActivity;
import com.google.android.tvlauncher.util.Util;
import com.google.logs.tvlauncher.config.TvLauncherConstants;

public class NotificationsSidePanelActivity extends LoggingActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "NotifsSidePanel";
    /* access modifiers changed from: private */
    public View mNoNotifsMessage;
    /* access modifiers changed from: private */
    public NotificationsPanelView mNotifsList;
    /* access modifiers changed from: private */
    public NotificationsPanelAdapter mPanelAdapter;

    public NotificationsSidePanelActivity() {
        super("NotificationSidePanel", TvLauncherConstants.NOTIFICATIONS_PAGE);
    }

    public /* bridge */ /* synthetic */ void onLoadFinished(Loader loader, Object obj) {
        onLoadFinished((Loader<Cursor>) loader, (Cursor) obj);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ViewGroup root = (ViewGroup) findViewById(16908290);
        this.mPanelAdapter = new NotificationsPanelAdapter(this, null, getEventLogger());
        root.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                root.getViewTreeObserver().removeOnPreDrawListener(this);
                Scene scene = new Scene(root);
                scene.setEnterAction(new Runnable() {
                    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
                     method: ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View}
                     arg types: [int, android.view.ViewGroup, int]
                     candidates:
                      ClspMth{android.view.LayoutInflater.inflate(org.xmlpull.v1.XmlPullParser, android.view.ViewGroup, boolean):android.view.View}
                      ClspMth{android.view.LayoutInflater.inflate(int, android.view.ViewGroup, boolean):android.view.View} */
                    public void run() {
                        root.addView(LayoutInflater.from(new ContextThemeWrapper(NotificationsSidePanelActivity.this, C1188R.style.PreferenceThemeOverlayLeanback)).inflate(C1188R.layout.notifications_panel_view, root, false));
                        View unused = NotificationsSidePanelActivity.this.mNoNotifsMessage = NotificationsSidePanelActivity.this.findViewById(C1188R.C1191id.no_notifications_message);
                        NotificationsPanelView unused2 = NotificationsSidePanelActivity.this.mNotifsList = (NotificationsPanelView) NotificationsSidePanelActivity.this.findViewById(C1188R.C1191id.notifications_list);
                        NotificationsSidePanelActivity.this.mPanelAdapter.setList(NotificationsSidePanelActivity.this.mNotifsList);
                        NotificationsSidePanelActivity.this.mNotifsList.setAdapter(NotificationsSidePanelActivity.this.mPanelAdapter);
                        NotificationsSidePanelActivity.this.getLoaderManager().initLoader(0, null, NotificationsSidePanelActivity.this);
                        NotificationsSidePanelActivity.this.mNotifsList.setOnFocusChangedListener(NotificationsSidePanelActivity.this.mPanelAdapter);
                    }
                });
                TransitionManager.go(scene, new Slide(GravityCompat.END));
                return false;
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        Util.forceLandscapeOrientation(this);
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (this.mPanelAdapter.getItemCount() > 1) {
            this.mNotifsList.scrollToPosition(0);
        }
    }

    private void showNoNotificationsMessage() {
        this.mNotifsList.setVisibility(8);
        this.mNoNotifsMessage.setVisibility(0);
    }

    private void showNotifications() {
        this.mNoNotifsMessage.setVisibility(8);
        this.mNotifsList.setVisibility(0);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, NotificationsContract.CONTENT_URI, TvNotification.PROJECTION, null, null, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        this.mPanelAdapter.changeCursor(data);
        if (data == null || data.getCount() <= 0) {
            showNoNotificationsMessage();
        } else {
            showNotifications();
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        this.mPanelAdapter.changeCursor(null);
    }

    public void finish() {
        Scene scene = new Scene((ViewGroup) findViewById(16908290));
        scene.setEnterAction(new Runnable() {
            public void run() {
                NotificationsSidePanelActivity.this.findViewById(C1188R.C1191id.notifications_panel_view).setVisibility(8);
            }
        });
        Slide slide = new Slide(GravityCompat.END);
        slide.addListener(new Transition.TransitionListener() {
            public void onTransitionStart(Transition transition) {
                NotificationsSidePanelActivity.this.getWindow().setDimAmount(0.0f);
            }

            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                NotificationsSidePanelActivity.super.finish();
            }

            public void onTransitionCancel(Transition transition) {
            }

            public void onTransitionPause(Transition transition) {
            }

            public void onTransitionResume(Transition transition) {
            }
        });
        TransitionManager.go(scene, slide);
    }
}
