package com.google.android.exoplayer2.p008ui;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.p001v4.app.NotificationCompat;
import android.support.p001v4.app.NotificationManagerCompat;
import android.support.p001v4.media.session.MediaSessionCompat;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ControlDispatcher;
import com.google.android.exoplayer2.DefaultControlDispatcher;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Player$EventListener$$CC;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.NotificationUtil;
import com.google.android.exoplayer2.util.Util;

import org.checkerframework.checker.nullness.qual.RequiresNonNull;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager */
public class PlayerNotificationManager {
    public static final String ACTION_FAST_FORWARD = "com.google.android.exoplayer.ffwd";
    public static final String ACTION_NEXT = "com.google.android.exoplayer.next";
    public static final String ACTION_PAUSE = "com.google.android.exoplayer.pause";
    public static final String ACTION_PLAY = "com.google.android.exoplayer.play";
    public static final String ACTION_PREVIOUS = "com.google.android.exoplayer.prev";
    public static final String ACTION_REWIND = "com.google.android.exoplayer.rewind";
    public static final String ACTION_STOP = "com.google.android.exoplayer.stop";
    public static final int DEFAULT_FAST_FORWARD_MS = 15000;
    public static final int DEFAULT_REWIND_MS = 5000;
    public static final String EXTRA_INSTANCE_ID = "INSTANCE_ID";
    private static final String ACTION_DISMISS = "com.google.android.exoplayer.dismiss";
    private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;
    private static int instanceIdCounter;
    /* access modifiers changed from: private */
    @Nullable
    public final CustomActionReceiver customActionReceiver;
    /* access modifiers changed from: private */
    public final Map<String, NotificationCompat.Action> customActions;
    /* access modifiers changed from: private */
    public final int instanceId;
    /* access modifiers changed from: private */
    public final Handler mainHandler;
    private final String channelId;
    private final Context context;
    private final PendingIntent dismissPendingIntent;
    private final IntentFilter intentFilter;
    private final MediaDescriptionAdapter mediaDescriptionAdapter;
    private final NotificationBroadcastReceiver notificationBroadcastReceiver;
    private final int notificationId;
    private final NotificationManagerCompat notificationManager;
    private final Map<String, NotificationCompat.Action> playbackActions;
    private final Player.EventListener playerListener;
    private final Timeline.Window window;
    /* access modifiers changed from: private */
    public ControlDispatcher controlDispatcher;
    /* access modifiers changed from: private */
    public int currentNotificationTag;
    /* access modifiers changed from: private */
    public boolean isNotificationStarted;
    /* access modifiers changed from: private */
    public int lastPlaybackState;
    /* access modifiers changed from: private */
    @Nullable
    public PlaybackPreparer playbackPreparer;
    /* access modifiers changed from: private */
    @Nullable
    public Player player;
    /* access modifiers changed from: private */
    public boolean wasPlayWhenReady;
    private int badgeIconType;
    @Nullable
    private NotificationCompat.Builder builder;
    @Nullable
    private ArrayList<NotificationCompat.Action> builderActions;
    private int color;
    private boolean colorized;
    private int defaults;
    private long fastForwardMs;
    @Nullable
    private MediaSessionCompat.Token mediaSessionToken;
    @Nullable
    private NotificationListener notificationListener;
    private int priority;
    private long rewindMs;
    @DrawableRes
    private int smallIconResourceId;
    private boolean useChronometer;
    private boolean useNavigationActions;
    private boolean useNavigationActionsInCompactView;
    private boolean usePlayPauseActions;
    private boolean useStopAction;
    private int visibility;

    public PlayerNotificationManager(Context context2, String channelId2, int notificationId2, MediaDescriptionAdapter mediaDescriptionAdapter2) {
        this(context2, channelId2, notificationId2, mediaDescriptionAdapter2, null, null);
    }

    public PlayerNotificationManager(Context context2, String channelId2, int notificationId2, MediaDescriptionAdapter mediaDescriptionAdapter2, @Nullable NotificationListener notificationListener2) {
        this(context2, channelId2, notificationId2, mediaDescriptionAdapter2, notificationListener2, null);
    }

    public PlayerNotificationManager(Context context2, String channelId2, int notificationId2, MediaDescriptionAdapter mediaDescriptionAdapter2, @Nullable CustomActionReceiver customActionReceiver2) {
        this(context2, channelId2, notificationId2, mediaDescriptionAdapter2, null, customActionReceiver2);
    }

    public PlayerNotificationManager(Context context2, String channelId2, int notificationId2, MediaDescriptionAdapter mediaDescriptionAdapter2, @Nullable NotificationListener notificationListener2, @Nullable CustomActionReceiver customActionReceiver2) {
        Map<String, NotificationCompat.Action> map;
        Context context3 = context2.getApplicationContext();
        this.context = context3;
        this.channelId = channelId2;
        this.notificationId = notificationId2;
        this.mediaDescriptionAdapter = mediaDescriptionAdapter2;
        this.notificationListener = notificationListener2;
        this.customActionReceiver = customActionReceiver2;
        this.controlDispatcher = new DefaultControlDispatcher();
        this.window = new Timeline.Window();
        int i = instanceIdCounter;
        instanceIdCounter = i + 1;
        this.instanceId = i;
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.notificationManager = NotificationManagerCompat.from(context3);
        this.playerListener = new PlayerListener();
        this.notificationBroadcastReceiver = new NotificationBroadcastReceiver();
        this.intentFilter = new IntentFilter();
        this.useNavigationActions = true;
        this.usePlayPauseActions = true;
        this.colorized = true;
        this.useChronometer = true;
        this.color = 0;
        this.smallIconResourceId = C0931R.C0932drawable.exo_notification_small_icon;
        this.defaults = 0;
        this.priority = -1;
        this.fastForwardMs = 15000;
        this.rewindMs = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
        this.badgeIconType = 1;
        this.visibility = 1;
        this.playbackActions = createPlaybackActions(context3, this.instanceId);
        for (String action : this.playbackActions.keySet()) {
            this.intentFilter.addAction(action);
        }
        if (customActionReceiver2 != null) {
            map = customActionReceiver2.createCustomActions(context3, this.instanceId);
        } else {
            map = Collections.emptyMap();
        }
        this.customActions = map;
        for (String action2 : this.customActions.keySet()) {
            this.intentFilter.addAction(action2);
        }
        this.dismissPendingIntent = createBroadcastIntent(ACTION_DISMISS, context3, this.instanceId);
        this.intentFilter.addAction(ACTION_DISMISS);
    }

    public static PlayerNotificationManager createWithNotificationChannel(Context context2, String channelId2, @StringRes int channelName, int notificationId2, MediaDescriptionAdapter mediaDescriptionAdapter2) {
        NotificationUtil.createNotificationChannel(context2, channelId2, channelName, 2);
        return new PlayerNotificationManager(context2, channelId2, notificationId2, mediaDescriptionAdapter2);
    }

    public static PlayerNotificationManager createWithNotificationChannel(Context context2, String channelId2, @StringRes int channelName, int notificationId2, MediaDescriptionAdapter mediaDescriptionAdapter2, @Nullable NotificationListener notificationListener2) {
        NotificationUtil.createNotificationChannel(context2, channelId2, channelName, 2);
        return new PlayerNotificationManager(context2, channelId2, notificationId2, mediaDescriptionAdapter2, notificationListener2);
    }

    private static Map<String, NotificationCompat.Action> createPlaybackActions(Context context2, int instanceId2) {
        Map<String, NotificationCompat.Action> actions = new HashMap<>();
        actions.put(ACTION_PLAY, new NotificationCompat.Action(C0931R.C0932drawable.exo_notification_play, context2.getString(C0931R.string.exo_controls_play_description), createBroadcastIntent(ACTION_PLAY, context2, instanceId2)));
        actions.put(ACTION_PAUSE, new NotificationCompat.Action(C0931R.C0932drawable.exo_notification_pause, context2.getString(C0931R.string.exo_controls_pause_description), createBroadcastIntent(ACTION_PAUSE, context2, instanceId2)));
        actions.put(ACTION_STOP, new NotificationCompat.Action(C0931R.C0932drawable.exo_notification_stop, context2.getString(C0931R.string.exo_controls_stop_description), createBroadcastIntent(ACTION_STOP, context2, instanceId2)));
        actions.put(ACTION_REWIND, new NotificationCompat.Action(C0931R.C0932drawable.exo_notification_rewind, context2.getString(C0931R.string.exo_controls_rewind_description), createBroadcastIntent(ACTION_REWIND, context2, instanceId2)));
        actions.put(ACTION_FAST_FORWARD, new NotificationCompat.Action(C0931R.C0932drawable.exo_notification_fastforward, context2.getString(C0931R.string.exo_controls_fastforward_description), createBroadcastIntent(ACTION_FAST_FORWARD, context2, instanceId2)));
        actions.put(ACTION_PREVIOUS, new NotificationCompat.Action(C0931R.C0932drawable.exo_notification_previous, context2.getString(C0931R.string.exo_controls_previous_description), createBroadcastIntent(ACTION_PREVIOUS, context2, instanceId2)));
        actions.put(ACTION_NEXT, new NotificationCompat.Action(C0931R.C0932drawable.exo_notification_next, context2.getString(C0931R.string.exo_controls_next_description), createBroadcastIntent(ACTION_NEXT, context2, instanceId2)));
        return actions;
    }

    private static PendingIntent createBroadcastIntent(String action, Context context2, int instanceId2) {
        Intent intent = new Intent(action).setPackage(context2.getPackageName());
        intent.putExtra(EXTRA_INSTANCE_ID, instanceId2);
        return PendingIntent.getBroadcast(context2, instanceId2, intent, 134217728);
    }

    private static void setLargeIcon(NotificationCompat.Builder builder2, @Nullable Bitmap largeIcon) {
        builder2.setLargeIcon(largeIcon);
    }

    public final void setPlayer(@Nullable Player player2) {
        boolean z = true;
        Assertions.checkState(Looper.myLooper() == Looper.getMainLooper());
        if (!(player2 == null || player2.getApplicationLooper() == Looper.getMainLooper())) {
            z = false;
        }
        Assertions.checkArgument(z);
        Player player3 = this.player;
        if (player3 != player2) {
            if (player3 != null) {
                player3.removeListener(this.playerListener);
                if (player2 == null) {
                    stopNotification(false);
                }
            }
            this.player = player2;
            if (player2 != null) {
                this.wasPlayWhenReady = player2.getPlayWhenReady();
                this.lastPlaybackState = player2.getPlaybackState();
                player2.addListener(this.playerListener);
                startOrUpdateNotification();
            }
        }
    }

    public void setPlaybackPreparer(@Nullable PlaybackPreparer playbackPreparer2) {
        this.playbackPreparer = playbackPreparer2;
    }

    public final void setControlDispatcher(ControlDispatcher controlDispatcher2) {
        this.controlDispatcher = controlDispatcher2 != null ? controlDispatcher2 : new DefaultControlDispatcher();
    }

    @Deprecated
    public final void setNotificationListener(NotificationListener notificationListener2) {
        this.notificationListener = notificationListener2;
    }

    public final void setFastForwardIncrementMs(long fastForwardMs2) {
        if (this.fastForwardMs != fastForwardMs2) {
            this.fastForwardMs = fastForwardMs2;
            invalidate();
        }
    }

    public final void setRewindIncrementMs(long rewindMs2) {
        if (this.rewindMs != rewindMs2) {
            this.rewindMs = rewindMs2;
            invalidate();
        }
    }

    public final void setUseNavigationActions(boolean useNavigationActions2) {
        if (this.useNavigationActions != useNavigationActions2) {
            this.useNavigationActions = useNavigationActions2;
            invalidate();
        }
    }

    public final void setUseNavigationActionsInCompactView(boolean useNavigationActionsInCompactView2) {
        if (this.useNavigationActionsInCompactView != useNavigationActionsInCompactView2) {
            this.useNavigationActionsInCompactView = useNavigationActionsInCompactView2;
            invalidate();
        }
    }

    public final void setUsePlayPauseActions(boolean usePlayPauseActions2) {
        if (this.usePlayPauseActions != usePlayPauseActions2) {
            this.usePlayPauseActions = usePlayPauseActions2;
            invalidate();
        }
    }

    public final void setUseStopAction(boolean useStopAction2) {
        if (this.useStopAction != useStopAction2) {
            this.useStopAction = useStopAction2;
            invalidate();
        }
    }

    public final void setMediaSessionToken(MediaSessionCompat.Token token) {
        if (!Util.areEqual(this.mediaSessionToken, token)) {
            this.mediaSessionToken = token;
            invalidate();
        }
    }

    public final void setBadgeIconType(int badgeIconType2) {
        if (this.badgeIconType != badgeIconType2) {
            if (badgeIconType2 == 0 || badgeIconType2 == 1 || badgeIconType2 == 2) {
                this.badgeIconType = badgeIconType2;
                invalidate();
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    public final void setColorized(boolean colorized2) {
        if (this.colorized != colorized2) {
            this.colorized = colorized2;
            invalidate();
        }
    }

    public final void setDefaults(int defaults2) {
        if (this.defaults != defaults2) {
            this.defaults = defaults2;
            invalidate();
        }
    }

    public final void setColor(int color2) {
        if (this.color != color2) {
            this.color = color2;
            invalidate();
        }
    }

    public final void setPriority(int priority2) {
        if (this.priority != priority2) {
            if (priority2 == -2 || priority2 == -1 || priority2 == 0 || priority2 == 1 || priority2 == 2) {
                this.priority = priority2;
                invalidate();
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    public final void setSmallIcon(@DrawableRes int smallIconResourceId2) {
        if (this.smallIconResourceId != smallIconResourceId2) {
            this.smallIconResourceId = smallIconResourceId2;
            invalidate();
        }
    }

    public final void setUseChronometer(boolean useChronometer2) {
        if (this.useChronometer != useChronometer2) {
            this.useChronometer = useChronometer2;
            invalidate();
        }
    }

    public final void setVisibility(int visibility2) {
        if (this.visibility != visibility2) {
            if (visibility2 == -1 || visibility2 == 0 || visibility2 == 1) {
                this.visibility = visibility2;
                invalidate();
                return;
            }
            throw new IllegalStateException();
        }
    }

    public void invalidate() {
        if (this.isNotificationStarted && this.player != null) {
            startOrUpdateNotification();
        }
    }

    /* access modifiers changed from: private */
    @Nullable
    public Notification startOrUpdateNotification() {
        Assertions.checkNotNull(this.player);
        return startOrUpdateNotification(null);
    }

    /* access modifiers changed from: private */
    @Nullable
    @RequiresNonNull({"player"})
    public Notification startOrUpdateNotification(@Nullable Bitmap bitmap) {
        Player player2 = this.player;
        boolean ongoing = getOngoing(player2);
        this.builder = createNotification(player2, this.builder, ongoing, bitmap);
        NotificationCompat.Builder builder2 = this.builder;
        if (builder2 == null) {
            stopNotification(false);
            return null;
        }
        Notification notification = builder2.build();
        this.notificationManager.notify(this.notificationId, notification);
        if (!this.isNotificationStarted) {
            this.isNotificationStarted = true;
            this.context.registerReceiver(this.notificationBroadcastReceiver, this.intentFilter);
            NotificationListener notificationListener2 = this.notificationListener;
            if (notificationListener2 != null) {
                notificationListener2.onNotificationStarted(this.notificationId, notification);
            }
        }
        NotificationListener listener = this.notificationListener;
        if (listener != null) {
            listener.onNotificationPosted(this.notificationId, notification, ongoing);
        }
        return notification;
    }

    /* access modifiers changed from: private */
    public void stopNotification(boolean dismissedByUser) {
        if (this.isNotificationStarted) {
            this.isNotificationStarted = false;
            this.notificationManager.cancel(this.notificationId);
            this.context.unregisterReceiver(this.notificationBroadcastReceiver);
            NotificationListener notificationListener2 = this.notificationListener;
            if (notificationListener2 != null) {
                notificationListener2.onNotificationCancelled(this.notificationId, dismissedByUser);
                this.notificationListener.onNotificationCancelled(this.notificationId);
            }
        }
    }

    /* access modifiers changed from: protected */
    @Nullable
    public NotificationCompat.Builder createNotification(Player player2, @Nullable NotificationCompat.Builder builder2, boolean ongoing, @Nullable Bitmap largeIcon) {
        NotificationCompat.Action action;
        if (player2.getPlaybackState() == 1) {
            this.builderActions = null;
            return null;
        }
        List<String> actionNames = getActions(player2);
        ArrayList<NotificationCompat.Action> actions = new ArrayList<>(actionNames.size());
        for (int i = 0; i < actionNames.size(); i++) {
            String actionName = actionNames.get(i);
            if (this.playbackActions.containsKey(actionName)) {
                action = this.playbackActions.get(actionName);
            } else {
                action = this.customActions.get(actionName);
            }
            if (action != null) {
                actions.add(action);
            }
        }
        if (builder2 == null || !actions.equals(this.builderActions)) {
            builder2 = new NotificationCompat.Builder(this.context, this.channelId);
            this.builderActions = actions;
            for (int i2 = 0; i2 < actions.size(); i2++) {
                builder2.addAction(actions.get(i2));
            }
        }
        NotificationCompat.MediaStyle mediaStyle = new NotificationCompat.MediaStyle();
        MediaSessionCompat.Token token = this.mediaSessionToken;
        if (token != null) {
            mediaStyle.setMediaSession(token);
        }
        mediaStyle.setShowActionsInCompactView(getActionIndicesForCompactView(actionNames, player2));
        mediaStyle.setShowCancelButton(!ongoing);
        mediaStyle.setCancelButtonIntent(this.dismissPendingIntent);
        builder2.setStyle(mediaStyle);
        builder2.setDeleteIntent(this.dismissPendingIntent);
        builder2.setBadgeIconType(this.badgeIconType).setOngoing(ongoing).setColor(this.color).setColorized(this.colorized).setSmallIcon(this.smallIconResourceId).setVisibility(this.visibility).setPriority(this.priority).setDefaults(this.defaults);
        if (Util.SDK_INT < 21 || !this.useChronometer || player2.isPlayingAd() || player2.isCurrentWindowDynamic() || !player2.getPlayWhenReady() || player2.getPlaybackState() != 3) {
            builder2.setShowWhen(false).setUsesChronometer(false);
        } else {
            builder2.setWhen(System.currentTimeMillis() - player2.getContentPosition()).setShowWhen(true).setUsesChronometer(true);
        }
        builder2.setContentTitle(this.mediaDescriptionAdapter.getCurrentContentTitle(player2));
        builder2.setContentText(this.mediaDescriptionAdapter.getCurrentContentText(player2));
        builder2.setSubText(this.mediaDescriptionAdapter.getCurrentSubText(player2));
        if (largeIcon == null) {
            MediaDescriptionAdapter mediaDescriptionAdapter2 = this.mediaDescriptionAdapter;
            int i3 = this.currentNotificationTag + 1;
            this.currentNotificationTag = i3;
            largeIcon = mediaDescriptionAdapter2.getCurrentLargeIcon(player2, new BitmapCallback(i3));
        }
        setLargeIcon(builder2, largeIcon);
        builder2.setContentIntent(this.mediaDescriptionAdapter.createCurrentContentIntent(player2));
        return builder2;
    }

    /* access modifiers changed from: protected */
    public List<String> getActions(Player player2) {
        boolean enablePrevious = false;
        boolean enableRewind = false;
        boolean enableFastForward = false;
        boolean enableNext = false;
        Timeline timeline = player2.getCurrentTimeline();
        if (!timeline.isEmpty() && !player2.isPlayingAd()) {
            timeline.getWindow(player2.getCurrentWindowIndex(), this.window);
            boolean z = false;
            enablePrevious = this.window.isSeekable || !this.window.isDynamic || player2.hasPrevious();
            enableRewind = this.rewindMs > 0;
            enableFastForward = this.fastForwardMs > 0;
            if (this.window.isDynamic || player2.hasNext()) {
                z = true;
            }
            enableNext = z;
        }
        List<String> stringActions = new ArrayList<>();
        if (this.useNavigationActions && enablePrevious) {
            stringActions.add(ACTION_PREVIOUS);
        }
        if (enableRewind) {
            stringActions.add(ACTION_REWIND);
        }
        if (this.usePlayPauseActions) {
            if (isPlaying(player2)) {
                stringActions.add(ACTION_PAUSE);
            } else {
                stringActions.add(ACTION_PLAY);
            }
        }
        if (enableFastForward) {
            stringActions.add(ACTION_FAST_FORWARD);
        }
        if (this.useNavigationActions && enableNext) {
            stringActions.add(ACTION_NEXT);
        }
        CustomActionReceiver customActionReceiver2 = this.customActionReceiver;
        if (customActionReceiver2 != null) {
            stringActions.addAll(customActionReceiver2.getCustomActions(player2));
        }
        if (this.useStopAction) {
            stringActions.add(ACTION_STOP);
        }
        return stringActions;
    }

    /* JADX INFO: Multiple debug info for r7v0 boolean: [D('actionCounter' int), D('playWhenReady' boolean)] */
    /* access modifiers changed from: protected */
    public int[] getActionIndicesForCompactView(List<String> actionNames, Player player2) {
        int actionCounter;
        int actionCounter2;
        int pauseActionIndex = actionNames.indexOf(ACTION_PAUSE);
        int playActionIndex = actionNames.indexOf(ACTION_PLAY);
        int skipPreviousActionIndex = this.useNavigationActionsInCompactView ? actionNames.indexOf(ACTION_PREVIOUS) : -1;
        int skipNextActionIndex = this.useNavigationActionsInCompactView ? actionNames.indexOf(ACTION_NEXT) : -1;
        int[] actionIndices = new int[3];
        int actionCounter3 = 0;
        if (skipPreviousActionIndex != -1) {
            actionIndices[0] = skipPreviousActionIndex;
            actionCounter3 = 0 + 1;
        }
        boolean playWhenReady = player2.getPlayWhenReady();
        if (pauseActionIndex != -1 && playWhenReady) {
            actionCounter = actionCounter3 + 1;
            actionIndices[actionCounter3] = pauseActionIndex;
        } else if (playActionIndex == -1 || playWhenReady) {
            actionCounter = actionCounter3;
        } else {
            actionCounter = actionCounter3 + 1;
            actionIndices[actionCounter3] = playActionIndex;
        }
        if (skipNextActionIndex != -1) {
            actionCounter2 = actionCounter + 1;
            actionIndices[actionCounter] = skipNextActionIndex;
        } else {
            actionCounter2 = actionCounter;
        }
        return Arrays.copyOf(actionIndices, actionCounter2);
    }

    /* access modifiers changed from: protected */
    public boolean getOngoing(Player player2) {
        int playbackState = player2.getPlaybackState();
        return (playbackState == 2 || playbackState == 3) && player2.getPlayWhenReady();
    }

    /* access modifiers changed from: private */
    public void previous(Player player2) {
        Timeline timeline = player2.getCurrentTimeline();
        if (!timeline.isEmpty() && !player2.isPlayingAd()) {
            timeline.getWindow(player2.getCurrentWindowIndex(), this.window);
            int previousWindowIndex = player2.getPreviousWindowIndex();
            if (previousWindowIndex == -1 || (player2.getCurrentPosition() > MAX_POSITION_FOR_SEEK_TO_PREVIOUS && (!this.window.isDynamic || this.window.isSeekable))) {
                seekTo(player2, 0);
            } else {
                seekTo(player2, previousWindowIndex, C0841C.TIME_UNSET);
            }
        }
    }

    /* access modifiers changed from: private */
    public void next(Player player2) {
        Timeline timeline = player2.getCurrentTimeline();
        if (!timeline.isEmpty() && !player2.isPlayingAd()) {
            int windowIndex = player2.getCurrentWindowIndex();
            int nextWindowIndex = player2.getNextWindowIndex();
            if (nextWindowIndex != -1) {
                seekTo(player2, nextWindowIndex, C0841C.TIME_UNSET);
            } else if (timeline.getWindow(windowIndex, this.window).isDynamic) {
                seekTo(player2, windowIndex, C0841C.TIME_UNSET);
            }
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [long, int]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    /* access modifiers changed from: private */
    public void rewind(Player player2) {
        if (player2.isCurrentWindowSeekable() && this.rewindMs > 0) {
            seekTo(player2, Math.max(player2.getCurrentPosition() - this.rewindMs, 0L));
        }
    }

    /* access modifiers changed from: private */
    public void fastForward(Player player2) {
        if (player2.isCurrentWindowSeekable() && this.fastForwardMs > 0) {
            seekTo(player2, player2.getCurrentPosition() + this.fastForwardMs);
        }
    }

    private void seekTo(Player player2, long positionMs) {
        seekTo(player2, player2.getCurrentWindowIndex(), positionMs);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.max(long, long):long}
     arg types: [long, int]
     candidates:
      ClspMth{java.lang.Math.max(double, double):double}
      ClspMth{java.lang.Math.max(int, int):int}
      ClspMth{java.lang.Math.max(float, float):float}
      ClspMth{java.lang.Math.max(long, long):long} */
    private void seekTo(Player player2, int windowIndex, long positionMs) {
        long duration = player2.getDuration();
        if (duration != C0841C.TIME_UNSET) {
            positionMs = Math.min(positionMs, duration);
        }
        this.controlDispatcher.dispatchSeekTo(player2, windowIndex, Math.max(positionMs, 0L));
    }

    private boolean isPlaying(Player player2) {
        if (player2.getPlaybackState() == 4 || player2.getPlaybackState() == 1 || !player2.getPlayWhenReady()) {
            return false;
        }
        return true;
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager$CustomActionReceiver */
    public interface CustomActionReceiver {
        Map<String, NotificationCompat.Action> createCustomActions(Context context, int i);

        List<String> getCustomActions(Player player);

        void onCustomAction(Player player, String str, Intent intent);
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager$MediaDescriptionAdapter */
    public interface MediaDescriptionAdapter {
        @Nullable
        PendingIntent createCurrentContentIntent(Player player);

        @Nullable
        String getCurrentContentText(Player player);

        String getCurrentContentTitle(Player player);

        @Nullable
        Bitmap getCurrentLargeIcon(Player player, BitmapCallback bitmapCallback);

        @Nullable
        String getCurrentSubText(Player player);
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager$NotificationListener */
    public interface NotificationListener {
        @Deprecated
        void onNotificationCancelled(int i);

        void onNotificationCancelled(int i, boolean z);

        void onNotificationPosted(int i, Notification notification, boolean z);

        @Deprecated
        void onNotificationStarted(int i, Notification notification);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager$Priority */
    public @interface Priority {
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager$Visibility */
    public @interface Visibility {
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager$BitmapCallback */
    public final class BitmapCallback {
        private final int notificationTag;

        private BitmapCallback(int notificationTag2) {
            this.notificationTag = notificationTag2;
        }

        public void onBitmap(Bitmap bitmap) {
            if (bitmap != null) {
                PlayerNotificationManager.this.mainHandler.post(new PlayerNotificationManager$BitmapCallback$$Lambda$0(this, bitmap));
            }
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ void lambda$onBitmap$0$PlayerNotificationManager$BitmapCallback(Bitmap bitmap) {
            if (PlayerNotificationManager.this.player != null && this.notificationTag == PlayerNotificationManager.this.currentNotificationTag && PlayerNotificationManager.this.isNotificationStarted) {
                Notification unused = PlayerNotificationManager.this.startOrUpdateNotification(bitmap);
            }
        }
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager$PlayerListener */
    private class PlayerListener implements Player.EventListener {
        private PlayerListener() {
        }

        public void onLoadingChanged(boolean z) {
            Player$EventListener$$CC.onLoadingChanged$$dflt$$(this, z);
        }

        public void onPlayerError(ExoPlaybackException exoPlaybackException) {
            Player$EventListener$$CC.onPlayerError$$dflt$$(this, exoPlaybackException);
        }

        public void onSeekProcessed() {
            Player$EventListener$$CC.onSeekProcessed$$dflt$$(this);
        }

        public void onShuffleModeEnabledChanged(boolean z) {
            Player$EventListener$$CC.onShuffleModeEnabledChanged$$dflt$$(this, z);
        }

        public void onTracksChanged(TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
            Player$EventListener$$CC.onTracksChanged$$dflt$$(this, trackGroupArray, trackSelectionArray);
        }

        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (PlayerNotificationManager.this.wasPlayWhenReady != playWhenReady || PlayerNotificationManager.this.lastPlaybackState != playbackState) {
                Notification unused = PlayerNotificationManager.this.startOrUpdateNotification();
                boolean unused2 = PlayerNotificationManager.this.wasPlayWhenReady = playWhenReady;
                int unused3 = PlayerNotificationManager.this.lastPlaybackState = playbackState;
            }
        }

        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
            Notification unused = PlayerNotificationManager.this.startOrUpdateNotification();
        }

        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            Notification unused = PlayerNotificationManager.this.startOrUpdateNotification();
        }

        public void onPositionDiscontinuity(int reason) {
            Notification unused = PlayerNotificationManager.this.startOrUpdateNotification();
        }

        public void onRepeatModeChanged(int repeatMode) {
            Notification unused = PlayerNotificationManager.this.startOrUpdateNotification();
        }
    }

    /* renamed from: com.google.android.exoplayer2.ui.PlayerNotificationManager$NotificationBroadcastReceiver */
    private class NotificationBroadcastReceiver extends BroadcastReceiver {
        private NotificationBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            Player player = PlayerNotificationManager.this.player;
            if (player != null && PlayerNotificationManager.this.isNotificationStarted && intent.getIntExtra(PlayerNotificationManager.EXTRA_INSTANCE_ID, PlayerNotificationManager.this.instanceId) == PlayerNotificationManager.this.instanceId) {
                String action = intent.getAction();
                if (PlayerNotificationManager.ACTION_PLAY.equals(action)) {
                    if (player.getPlaybackState() == 1) {
                        if (PlayerNotificationManager.this.playbackPreparer != null) {
                            PlayerNotificationManager.this.playbackPreparer.preparePlayback();
                        }
                    } else if (player.getPlaybackState() == 4) {
                        PlayerNotificationManager.this.controlDispatcher.dispatchSeekTo(player, player.getCurrentWindowIndex(), C0841C.TIME_UNSET);
                    }
                    PlayerNotificationManager.this.controlDispatcher.dispatchSetPlayWhenReady(player, true);
                } else if (PlayerNotificationManager.ACTION_PAUSE.equals(action)) {
                    PlayerNotificationManager.this.controlDispatcher.dispatchSetPlayWhenReady(player, false);
                } else if (PlayerNotificationManager.ACTION_PREVIOUS.equals(action)) {
                    PlayerNotificationManager.this.previous(player);
                } else if (PlayerNotificationManager.ACTION_REWIND.equals(action)) {
                    PlayerNotificationManager.this.rewind(player);
                } else if (PlayerNotificationManager.ACTION_FAST_FORWARD.equals(action)) {
                    PlayerNotificationManager.this.fastForward(player);
                } else if (PlayerNotificationManager.ACTION_NEXT.equals(action)) {
                    PlayerNotificationManager.this.next(player);
                } else if (PlayerNotificationManager.ACTION_STOP.equals(action)) {
                    PlayerNotificationManager.this.controlDispatcher.dispatchStop(player, true);
                } else if (PlayerNotificationManager.ACTION_DISMISS.equals(action)) {
                    PlayerNotificationManager.this.stopNotification(true);
                } else if (action != null && PlayerNotificationManager.this.customActionReceiver != null && PlayerNotificationManager.this.customActions.containsKey(action)) {
                    PlayerNotificationManager.this.customActionReceiver.onCustomAction(player, action, intent);
                }
            }
        }
    }
}
