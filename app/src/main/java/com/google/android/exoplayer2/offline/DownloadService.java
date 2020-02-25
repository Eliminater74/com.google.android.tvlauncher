package com.google.android.exoplayer2.offline;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.google.android.exoplayer2.scheduler.Requirements;
import com.google.android.exoplayer2.scheduler.Scheduler;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;

import java.util.HashMap;
import java.util.List;

public abstract class DownloadService extends Service {
    public static final String ACTION_ADD_DOWNLOAD = "com.google.android.exoplayer.downloadService.action.ADD_DOWNLOAD";
    public static final String ACTION_INIT = "com.google.android.exoplayer.downloadService.action.INIT";
    public static final String ACTION_PAUSE_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.PAUSE_DOWNLOADS";
    public static final String ACTION_REMOVE_DOWNLOAD = "com.google.android.exoplayer.downloadService.action.REMOVE_DOWNLOAD";
    public static final String ACTION_RESUME_DOWNLOADS = "com.google.android.exoplayer.downloadService.action.RESUME_DOWNLOADS";
    public static final String ACTION_SET_STOP_REASON = "com.google.android.exoplayer.downloadService.action.SET_STOP_REASON";
    public static final long DEFAULT_FOREGROUND_NOTIFICATION_UPDATE_INTERVAL = 1000;
    public static final int FOREGROUND_NOTIFICATION_ID_NONE = 0;
    public static final String KEY_CONTENT_ID = "content_id";
    public static final String KEY_DOWNLOAD_REQUEST = "download_request";
    public static final String KEY_FOREGROUND = "foreground";
    public static final String KEY_STOP_REASON = "manual_stop_reason";
    private static final String ACTION_RESTART = "com.google.android.exoplayer.downloadService.action.RESTART";
    private static final boolean DEBUG = false;
    private static final String TAG = "DownloadService";
    private static final HashMap<Class<? extends DownloadService>, DownloadManagerHelper> downloadManagerListeners = new HashMap<>();
    @Nullable
    private final String channelId;
    @StringRes
    private final int channelNameResourceId;
    @Nullable
    private final ForegroundNotificationUpdater foregroundNotificationUpdater;
    /* access modifiers changed from: private */
    public DownloadManager downloadManager;
    private boolean isDestroyed;
    private int lastStartId;
    private boolean startedInForeground;
    private boolean taskRemoved;

    protected DownloadService(int foregroundNotificationId) {
        this(foregroundNotificationId, 1000);
    }

    protected DownloadService(int foregroundNotificationId, long foregroundNotificationUpdateInterval) {
        this(foregroundNotificationId, foregroundNotificationUpdateInterval, null, 0);
    }

    protected DownloadService(int foregroundNotificationId, long foregroundNotificationUpdateInterval, @Nullable String channelId2, @StringRes int channelNameResourceId2) {
        if (foregroundNotificationId == 0) {
            this.foregroundNotificationUpdater = null;
            this.channelId = null;
            this.channelNameResourceId = 0;
            return;
        }
        this.foregroundNotificationUpdater = new ForegroundNotificationUpdater(foregroundNotificationId, foregroundNotificationUpdateInterval);
        this.channelId = channelId2;
        this.channelNameResourceId = channelNameResourceId2;
    }

    public static Intent buildAddDownloadIntent(Context context, Class<? extends DownloadService> clazz, DownloadRequest downloadRequest, boolean foreground) {
        return buildAddDownloadIntent(context, clazz, downloadRequest, 0, foreground);
    }

    public static Intent buildAddDownloadIntent(Context context, Class<? extends DownloadService> clazz, DownloadRequest downloadRequest, int stopReason, boolean foreground) {
        return getIntent(context, clazz, ACTION_ADD_DOWNLOAD, foreground).putExtra(KEY_DOWNLOAD_REQUEST, downloadRequest).putExtra(KEY_STOP_REASON, stopReason);
    }

    public static Intent buildRemoveDownloadIntent(Context context, Class<? extends DownloadService> clazz, String id, boolean foreground) {
        return getIntent(context, clazz, ACTION_REMOVE_DOWNLOAD, foreground).putExtra("content_id", id);
    }

    public static Intent buildResumeDownloadsIntent(Context context, Class<? extends DownloadService> clazz, boolean foreground) {
        return getIntent(context, clazz, ACTION_RESUME_DOWNLOADS, foreground);
    }

    public static Intent buildPauseDownloadsIntent(Context context, Class<? extends DownloadService> clazz, boolean foreground) {
        return getIntent(context, clazz, ACTION_PAUSE_DOWNLOADS, foreground);
    }

    public static Intent buildSetStopReasonIntent(Context context, Class<? extends DownloadService> clazz, @Nullable String id, int stopReason, boolean foreground) {
        return getIntent(context, clazz, ACTION_SET_STOP_REASON, foreground).putExtra("content_id", id).putExtra(KEY_STOP_REASON, stopReason);
    }

    public static void sendAddDownload(Context context, Class<? extends DownloadService> clazz, DownloadRequest downloadRequest, boolean foreground) {
        startService(context, buildAddDownloadIntent(context, clazz, downloadRequest, foreground), foreground);
    }

    public static void sendAddDownload(Context context, Class<? extends DownloadService> clazz, DownloadRequest downloadRequest, int stopReason, boolean foreground) {
        startService(context, buildAddDownloadIntent(context, clazz, downloadRequest, stopReason, foreground), foreground);
    }

    public static void sendRemoveDownload(Context context, Class<? extends DownloadService> clazz, String id, boolean foreground) {
        startService(context, buildRemoveDownloadIntent(context, clazz, id, foreground), foreground);
    }

    public static void sendResumeDownloads(Context context, Class<? extends DownloadService> clazz, boolean foreground) {
        startService(context, buildResumeDownloadsIntent(context, clazz, foreground), foreground);
    }

    public static void sendPauseDownloads(Context context, Class<? extends DownloadService> clazz, boolean foreground) {
        startService(context, buildPauseDownloadsIntent(context, clazz, foreground), foreground);
    }

    public static void sendSetStopReason(Context context, Class<? extends DownloadService> clazz, @Nullable String id, int stopReason, boolean foreground) {
        startService(context, buildSetStopReasonIntent(context, clazz, id, stopReason, foreground), foreground);
    }

    public static void start(Context context, Class<? extends DownloadService> clazz) {
        context.startService(getIntent(context, clazz, ACTION_INIT));
    }

    public static void startForeground(Context context, Class<? extends DownloadService> clazz) {
        Util.startForegroundService(context, getIntent(context, clazz, ACTION_INIT, true));
    }

    private static Intent getIntent(Context context, Class<? extends DownloadService> clazz, String action, boolean foreground) {
        return getIntent(context, clazz, action).putExtra(KEY_FOREGROUND, foreground);
    }

    /* access modifiers changed from: private */
    public static Intent getIntent(Context context, Class<? extends DownloadService> clazz, String action) {
        return new Intent(context, clazz).setAction(action);
    }

    private static void startService(Context context, Intent intent, boolean foreground) {
        if (foreground) {
            Util.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    /* access modifiers changed from: protected */
    public abstract DownloadManager getDownloadManager();

    /* access modifiers changed from: protected */
    public abstract Notification getForegroundNotification(List<Download> list);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract Scheduler getScheduler();

    public void onCreate() {
        logd("onCreate");
        String str = this.channelId;
        if (str != null) {
            NotificationUtil.createNotificationChannel(this, str, this.channelNameResourceId, 2);
        }
        Class<?> cls = getClass();
        DownloadManagerHelper downloadManagerHelper = downloadManagerListeners.get(cls);
        if (downloadManagerHelper == null) {
            DownloadManager downloadManager2 = getDownloadManager();
            downloadManager2.resumeDownloads();
            downloadManagerHelper = new DownloadManagerHelper(getApplicationContext(), downloadManager2, getScheduler(), cls);
            downloadManagerListeners.put(cls, downloadManagerHelper);
        }
        this.downloadManager = downloadManagerHelper.downloadManager;
        downloadManagerHelper.attachService(this);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public int onStartCommand(Intent intent, int flags, int startId) {
        char c;
        String str;
        this.lastStartId = startId;
        this.taskRemoved = false;
        String intentAction = null;
        if (intent != null) {
            intentAction = intent.getAction();
            this.startedInForeground |= intent.getBooleanExtra(KEY_FOREGROUND, false) || ACTION_RESTART.equals(intentAction);
        }
        if (intentAction == null) {
            intentAction = ACTION_INIT;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(intentAction).length() + 44);
        sb.append("onStartCommand action: ");
        sb.append(intentAction);
        sb.append(" startId: ");
        sb.append(startId);
        logd(sb.toString());
        switch (intentAction.hashCode()) {
            case -1931239035:
                if (intentAction.equals(ACTION_ADD_DOWNLOAD)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -932047176:
                if (intentAction.equals(ACTION_RESUME_DOWNLOADS)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -871181424:
                if (intentAction.equals(ACTION_RESTART)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 191112771:
                if (intentAction.equals(ACTION_PAUSE_DOWNLOADS)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 671523141:
                if (intentAction.equals(ACTION_SET_STOP_REASON)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1015676687:
                if (intentAction.equals(ACTION_INIT)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1547520644:
                if (intentAction.equals(ACTION_REMOVE_DOWNLOAD)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                break;
            case 2:
                DownloadRequest downloadRequest = (DownloadRequest) intent.getParcelableExtra(KEY_DOWNLOAD_REQUEST);
                if (downloadRequest != null) {
                    this.downloadManager.addDownload(downloadRequest, intent.getIntExtra(KEY_STOP_REASON, 0));
                    break;
                } else {
                    Log.m26e(TAG, "Ignored ADD: Missing download_request extra");
                    break;
                }
            case 3:
                this.downloadManager.resumeDownloads();
                break;
            case 4:
                this.downloadManager.pauseDownloads();
                break;
            case 5:
                if (intent.hasExtra(KEY_STOP_REASON)) {
                    this.downloadManager.setStopReason(intent.getStringExtra("content_id"), intent.getIntExtra(KEY_STOP_REASON, 0));
                    break;
                } else {
                    Log.m26e(TAG, "Ignored SET_MANUAL_STOP_REASON: Missing manual_stop_reason extra");
                    break;
                }
            case 6:
                String contentId = intent.getStringExtra("content_id");
                if (contentId != null) {
                    this.downloadManager.removeDownload(contentId);
                    break;
                } else {
                    Log.m26e(TAG, "Ignored REMOVE: Missing content_id extra");
                    break;
                }
            default:
                String valueOf = String.valueOf(intentAction);
                if (valueOf.length() != 0) {
                    str = "Ignored unrecognized action: ".concat(valueOf);
                } else {
                    str = new String("Ignored unrecognized action: ");
                }
                Log.m26e(TAG, str);
                break;
        }
        if (this.downloadManager.isIdle()) {
            stop();
        }
        return 1;
    }

    public void onTaskRemoved(Intent rootIntent) {
        String valueOf = String.valueOf(rootIntent);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 26);
        sb.append("onTaskRemoved rootIntent: ");
        sb.append(valueOf);
        logd(sb.toString());
        this.taskRemoved = true;
    }

    public void onDestroy() {
        logd("onDestroy");
        this.isDestroyed = true;
        downloadManagerListeners.get(getClass()).detachService(this, true ^ this.downloadManager.isWaitingForRequirements());
        ForegroundNotificationUpdater foregroundNotificationUpdater2 = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater2 != null) {
            foregroundNotificationUpdater2.stopPeriodicUpdates();
        }
    }

    @Nullable
    public final IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public final void invalidateForegroundNotification() {
        ForegroundNotificationUpdater foregroundNotificationUpdater2 = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater2 != null && !this.isDestroyed) {
            foregroundNotificationUpdater2.invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDownloadChanged(Download download) {
    }

    /* access modifiers changed from: protected */
    public void onDownloadRemoved(Download download) {
    }

    /* access modifiers changed from: private */
    public void notifyDownloadChange(Download download) {
        onDownloadChanged(download);
        if (this.foregroundNotificationUpdater == null) {
            return;
        }
        if (download.state == 2 || download.state == 5 || download.state == 7) {
            this.foregroundNotificationUpdater.startPeriodicUpdates();
        } else {
            this.foregroundNotificationUpdater.invalidate();
        }
    }

    /* access modifiers changed from: private */
    public void notifyDownloadRemoved(Download download) {
        onDownloadRemoved(download);
        ForegroundNotificationUpdater foregroundNotificationUpdater2 = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater2 != null) {
            foregroundNotificationUpdater2.invalidate();
        }
    }

    /* access modifiers changed from: private */
    public void stop() {
        ForegroundNotificationUpdater foregroundNotificationUpdater2 = this.foregroundNotificationUpdater;
        if (foregroundNotificationUpdater2 != null) {
            foregroundNotificationUpdater2.stopPeriodicUpdates();
            if (this.startedInForeground && Util.SDK_INT >= 26) {
                this.foregroundNotificationUpdater.showNotificationIfNotAlready();
            }
        }
        if (Util.SDK_INT >= 28 || !this.taskRemoved) {
            boolean stopSelfResult = stopSelfResult(this.lastStartId);
            int i = this.lastStartId;
            StringBuilder sb = new StringBuilder(35);
            sb.append("stopSelf(");
            sb.append(i);
            sb.append(") result: ");
            sb.append(stopSelfResult);
            logd(sb.toString());
            return;
        }
        stopSelf();
        logd("stopSelf()");
    }

    private void logd(String message) {
    }

    private static final class DownloadManagerHelper implements DownloadManager.Listener {
        /* access modifiers changed from: private */
        public final DownloadManager downloadManager;
        private final Context context;
        @Nullable
        private final Scheduler scheduler;
        private final Class<? extends DownloadService> serviceClass;
        @Nullable
        private DownloadService downloadService;

        private DownloadManagerHelper(Context context2, DownloadManager downloadManager2, @Nullable Scheduler scheduler2, Class<? extends DownloadService> serviceClass2) {
            this.context = context2;
            this.downloadManager = downloadManager2;
            this.scheduler = scheduler2;
            this.serviceClass = serviceClass2;
            downloadManager2.addListener(this);
            if (scheduler2 != null) {
                Requirements requirements = downloadManager2.getRequirements();
                setSchedulerEnabled(!requirements.checkRequirements(context2), requirements);
            }
        }

        public void onInitialized(DownloadManager downloadManager2) {
            DownloadManager$Listener$$CC.onInitialized$$dflt$$(this, downloadManager2);
        }

        public void attachService(DownloadService downloadService2) {
            Assertions.checkState(this.downloadService == null);
            this.downloadService = downloadService2;
        }

        public void detachService(DownloadService downloadService2, boolean unschedule) {
            Assertions.checkState(this.downloadService == downloadService2);
            this.downloadService = null;
            Scheduler scheduler2 = this.scheduler;
            if (scheduler2 != null && unschedule) {
                scheduler2.cancel();
            }
        }

        public void onDownloadChanged(DownloadManager downloadManager2, Download download) {
            DownloadService downloadService2 = this.downloadService;
            if (downloadService2 != null) {
                downloadService2.notifyDownloadChange(download);
            }
        }

        public void onDownloadRemoved(DownloadManager downloadManager2, Download download) {
            DownloadService downloadService2 = this.downloadService;
            if (downloadService2 != null) {
                downloadService2.notifyDownloadRemoved(download);
            }
        }

        public final void onIdle(DownloadManager downloadManager2) {
            DownloadService downloadService2 = this.downloadService;
            if (downloadService2 != null) {
                downloadService2.stop();
            }
        }

        public void onRequirementsStateChanged(DownloadManager downloadManager2, Requirements requirements, int notMetRequirements) {
            boolean z = true;
            boolean requirementsMet = notMetRequirements == 0;
            if (this.downloadService == null && requirementsMet) {
                try {
                    this.context.startService(DownloadService.getIntent(this.context, this.serviceClass, DownloadService.ACTION_INIT));
                } catch (IllegalStateException e) {
                    return;
                }
            }
            if (this.scheduler != null) {
                if (requirementsMet) {
                    z = false;
                }
                setSchedulerEnabled(z, requirements);
            }
        }

        private void setSchedulerEnabled(boolean enabled, Requirements requirements) {
            if (!enabled) {
                this.scheduler.cancel();
                return;
            }
            if (!this.scheduler.schedule(requirements, this.context.getPackageName(), DownloadService.ACTION_RESTART)) {
                Log.m26e(DownloadService.TAG, "Scheduling downloads failed.");
            }
        }
    }

    private final class ForegroundNotificationUpdater {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private final int notificationId;
        private final long updateInterval;
        private final Runnable updateRunnable = new DownloadService$ForegroundNotificationUpdater$$Lambda$0(this);
        private boolean notificationDisplayed;
        private boolean periodicUpdatesStarted;

        public ForegroundNotificationUpdater(int notificationId2, long updateInterval2) {
            this.notificationId = notificationId2;
            this.updateInterval = updateInterval2;
        }

        public void startPeriodicUpdates() {
            this.periodicUpdatesStarted = true;
            bridge$lambda$0$DownloadService$ForegroundNotificationUpdater();
        }

        public void stopPeriodicUpdates() {
            this.periodicUpdatesStarted = false;
            this.handler.removeCallbacks(this.updateRunnable);
        }

        public void showNotificationIfNotAlready() {
            if (!this.notificationDisplayed) {
                bridge$lambda$0$DownloadService$ForegroundNotificationUpdater();
            }
        }

        public void invalidate() {
            if (this.notificationDisplayed) {
                bridge$lambda$0$DownloadService$ForegroundNotificationUpdater();
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: update */
        public void bridge$lambda$0$DownloadService$ForegroundNotificationUpdater() {
            List<Download> downloads = DownloadService.this.downloadManager.getCurrentDownloads();
            DownloadService downloadService = DownloadService.this;
            downloadService.startForeground(this.notificationId, downloadService.getForegroundNotification(downloads));
            this.notificationDisplayed = true;
            if (this.periodicUpdatesStarted) {
                this.handler.removeCallbacks(this.updateRunnable);
                this.handler.postDelayed(this.updateRunnable, this.updateInterval);
            }
        }
    }
}
