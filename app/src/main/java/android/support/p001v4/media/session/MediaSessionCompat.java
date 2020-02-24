package android.support.p001v4.media.session;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.RemoteControlClient;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.GuardedBy;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.p001v4.app.BundleCompat;
import android.support.p001v4.media.MediaDescriptionCompat;
import android.support.p001v4.media.MediaMetadataCompat;
import android.support.p001v4.media.MediaSessionManager;
import android.support.p001v4.media.RatingCompat;
import android.support.p001v4.media.VolumeProviderCompat;
import android.support.p001v4.media.session.IMediaSession;
import android.support.p001v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import androidx.versionedparcelable.ParcelUtils;
import androidx.versionedparcelable.VersionedParcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: android.support.v4.media.session.MediaSessionCompat */
public class MediaSessionCompat {
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_CAPTIONING_ENABLED = "android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_EXTRAS = "android.support.v4.media.session.action.ARGUMENT_EXTRAS";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_MEDIA_ID = "android.support.v4.media.session.action.ARGUMENT_MEDIA_ID";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_PLAYBACK_SPEED = "android.support.v4.media.session.action.ARGUMENT_PLAYBACK_SPEED";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_QUERY = "android.support.v4.media.session.action.ARGUMENT_QUERY";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_RATING = "android.support.v4.media.session.action.ARGUMENT_RATING";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_REPEAT_MODE = "android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_SHUFFLE_MODE = "android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_ARGUMENT_URI = "android.support.v4.media.session.action.ARGUMENT_URI";
    public static final String ACTION_FLAG_AS_INAPPROPRIATE = "android.support.v4.media.session.action.FLAG_AS_INAPPROPRIATE";
    public static final String ACTION_FOLLOW = "android.support.v4.media.session.action.FOLLOW";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PLAY_FROM_URI = "android.support.v4.media.session.action.PLAY_FROM_URI";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PREPARE = "android.support.v4.media.session.action.PREPARE";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PREPARE_FROM_MEDIA_ID = "android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PREPARE_FROM_SEARCH = "android.support.v4.media.session.action.PREPARE_FROM_SEARCH";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_PREPARE_FROM_URI = "android.support.v4.media.session.action.PREPARE_FROM_URI";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_CAPTIONING_ENABLED = "android.support.v4.media.session.action.SET_CAPTIONING_ENABLED";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_PLAYBACK_SPEED = "android.support.v4.media.session.action.SET_PLAYBACK_SPEED";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_RATING = "android.support.v4.media.session.action.SET_RATING";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_REPEAT_MODE = "android.support.v4.media.session.action.SET_REPEAT_MODE";
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String ACTION_SET_SHUFFLE_MODE = "android.support.v4.media.session.action.SET_SHUFFLE_MODE";
    public static final String ACTION_SKIP_AD = "android.support.v4.media.session.action.SKIP_AD";
    public static final String ACTION_UNFOLLOW = "android.support.v4.media.session.action.UNFOLLOW";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE_VALUE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE_VALUE";
    private static final String DATA_CALLING_PACKAGE = "data_calling_pkg";
    private static final String DATA_CALLING_PID = "data_calling_pid";
    private static final String DATA_CALLING_UID = "data_calling_uid";
    private static final String DATA_EXTRAS = "data_extras";
    @Deprecated
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_QUEUE_COMMANDS = 4;
    @Deprecated
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_EXTRA_BINDER = "android.support.v4.media.session.EXTRA_BINDER";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String KEY_SESSION2_TOKEN = "android.support.v4.media.session.SESSION_TOKEN2";
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static final String KEY_TOKEN = "android.support.v4.media.session.TOKEN";
    private static final int MAX_BITMAP_SIZE_IN_DP = 320;
    public static final int MEDIA_ATTRIBUTE_ALBUM = 1;
    public static final int MEDIA_ATTRIBUTE_ARTIST = 0;
    public static final int MEDIA_ATTRIBUTE_PLAYLIST = 2;
    static final String TAG = "MediaSessionCompat";
    static int sMaxBitmapSize;
    private final ArrayList<OnActiveChangeListener> mActiveListeners;
    private final MediaControllerCompat mController;
    private final MediaSessionImpl mImpl;

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImpl */
    interface MediaSessionImpl {
        String getCallingPackage();

        MediaSessionManager.RemoteUserInfo getCurrentControllerInfo();

        Object getMediaSession();

        PlaybackStateCompat getPlaybackState();

        Object getRemoteControlClient();

        Token getSessionToken();

        boolean isActive();

        void release();

        void sendSessionEvent(String str, Bundle bundle);

        void setActive(boolean z);

        void setCallback(Callback callback, Handler handler);

        void setCaptioningEnabled(boolean z);

        void setCurrentControllerInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo);

        void setExtras(Bundle bundle);

        void setFlags(int i);

        void setMediaButtonReceiver(PendingIntent pendingIntent);

        void setMetadata(MediaMetadataCompat mediaMetadataCompat);

        void setPlaybackState(PlaybackStateCompat playbackStateCompat);

        void setPlaybackToLocal(int i);

        void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat);

        void setQueue(List<QueueItem> list);

        void setQueueTitle(CharSequence charSequence);

        void setRatingType(int i);

        void setRepeatMode(int i);

        void setSessionActivity(PendingIntent pendingIntent);

        void setShuffleMode(int i);
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$OnActiveChangeListener */
    public interface OnActiveChangeListener {
        void onActiveChanged();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$SessionFlags */
    public @interface SessionFlags {
    }

    public MediaSessionCompat(@NonNull Context context, @NonNull String tag) {
        this(context, tag, null, null);
    }

    public MediaSessionCompat(@NonNull Context context, @NonNull String tag, @Nullable ComponentName mbrComponent, @Nullable PendingIntent mbrIntent) {
        this(context, tag, mbrComponent, mbrIntent, null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public MediaSessionCompat(@NonNull Context context, @NonNull String tag, @Nullable ComponentName mbrComponent, @Nullable PendingIntent mbrIntent, @Nullable Bundle sessionInfo) {
        this(context, tag, mbrComponent, mbrIntent, sessionInfo, null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public MediaSessionCompat(@NonNull Context context, @NonNull String tag, @Nullable ComponentName mbrComponent, @Nullable PendingIntent mbrIntent, @Nullable Bundle sessionInfo, @Nullable VersionedParcelable session2Token) {
        this.mActiveListeners = new ArrayList<>();
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        } else if (!TextUtils.isEmpty(tag)) {
            if (mbrComponent == null && (mbrComponent = MediaButtonReceiver.getMediaButtonReceiverComponent(context)) == null) {
                Log.w(TAG, "Couldn't find a unique registered media button receiver in the given context.");
            }
            if (mbrComponent != null && mbrIntent == null) {
                Intent mediaButtonIntent = new Intent("android.intent.action.MEDIA_BUTTON");
                mediaButtonIntent.setComponent(mbrComponent);
                mbrIntent = PendingIntent.getBroadcast(context, 0, mediaButtonIntent, 0);
            }
            if (Build.VERSION.SDK_INT >= 28) {
                this.mImpl = new MediaSessionImplApi28(context, tag, session2Token, sessionInfo);
                setCallback(new Callback() {
                });
                this.mImpl.setMediaButtonReceiver(mbrIntent);
            } else if (Build.VERSION.SDK_INT >= 21) {
                this.mImpl = new MediaSessionImplApi21(context, tag, session2Token, sessionInfo);
                setCallback(new Callback() {
                });
                this.mImpl.setMediaButtonReceiver(mbrIntent);
            } else if (Build.VERSION.SDK_INT >= 19) {
                this.mImpl = new MediaSessionImplApi19(context, tag, mbrComponent, mbrIntent, sessionInfo);
            } else if (Build.VERSION.SDK_INT >= 18) {
                this.mImpl = new MediaSessionImplApi18(context, tag, mbrComponent, mbrIntent, sessionInfo);
            } else {
                this.mImpl = new MediaSessionImplBase(context, tag, mbrComponent, mbrIntent, sessionInfo);
            }
            this.mController = new MediaControllerCompat(context, this);
            if (sMaxBitmapSize == 0) {
                sMaxBitmapSize = (int) (TypedValue.applyDimension(1, 320.0f, context.getResources().getDisplayMetrics()) + 0.5f);
            }
        } else {
            throw new IllegalArgumentException("tag must not be null or empty");
        }
    }

    private MediaSessionCompat(Context context, MediaSessionImpl impl) {
        this.mActiveListeners = new ArrayList<>();
        this.mImpl = impl;
        this.mController = new MediaControllerCompat(context, this);
    }

    public void setCallback(Callback callback) {
        setCallback(callback, null);
    }

    public void setCallback(Callback callback, Handler handler) {
        if (callback == null) {
            this.mImpl.setCallback(null, null);
        } else {
            this.mImpl.setCallback(callback, handler != null ? handler : new Handler());
        }
    }

    public void setSessionActivity(PendingIntent pi) {
        this.mImpl.setSessionActivity(pi);
    }

    public void setMediaButtonReceiver(PendingIntent mbr) {
        this.mImpl.setMediaButtonReceiver(mbr);
    }

    public void setFlags(int flags) {
        this.mImpl.setFlags(flags);
    }

    public void setPlaybackToLocal(int stream) {
        this.mImpl.setPlaybackToLocal(stream);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProvider) {
        if (volumeProvider != null) {
            this.mImpl.setPlaybackToRemote(volumeProvider);
            return;
        }
        throw new IllegalArgumentException("volumeProvider may not be null!");
    }

    public void setActive(boolean active) {
        this.mImpl.setActive(active);
        Iterator<OnActiveChangeListener> it = this.mActiveListeners.iterator();
        while (it.hasNext()) {
            it.next().onActiveChanged();
        }
    }

    public boolean isActive() {
        return this.mImpl.isActive();
    }

    public void sendSessionEvent(String event, Bundle extras) {
        if (!TextUtils.isEmpty(event)) {
            this.mImpl.sendSessionEvent(event, extras);
            return;
        }
        throw new IllegalArgumentException("event cannot be null or empty");
    }

    public void release() {
        this.mImpl.release();
    }

    public Token getSessionToken() {
        return this.mImpl.getSessionToken();
    }

    public MediaControllerCompat getController() {
        return this.mController;
    }

    public void setPlaybackState(PlaybackStateCompat state) {
        this.mImpl.setPlaybackState(state);
    }

    public void setMetadata(MediaMetadataCompat metadata) {
        this.mImpl.setMetadata(metadata);
    }

    public void setQueue(List<QueueItem> queue) {
        this.mImpl.setQueue(queue);
    }

    public void setQueueTitle(CharSequence title) {
        this.mImpl.setQueueTitle(title);
    }

    public void setRatingType(int type) {
        this.mImpl.setRatingType(type);
    }

    public void setCaptioningEnabled(boolean enabled) {
        this.mImpl.setCaptioningEnabled(enabled);
    }

    public void setRepeatMode(int repeatMode) {
        this.mImpl.setRepeatMode(repeatMode);
    }

    public void setShuffleMode(int shuffleMode) {
        this.mImpl.setShuffleMode(shuffleMode);
    }

    public void setExtras(Bundle extras) {
        this.mImpl.setExtras(extras);
    }

    public Object getMediaSession() {
        return this.mImpl.getMediaSession();
    }

    public Object getRemoteControlClient() {
        return this.mImpl.getRemoteControlClient();
    }

    @NonNull
    public final MediaSessionManager.RemoteUserInfo getCurrentControllerInfo() {
        return this.mImpl.getCurrentControllerInfo();
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public String getCallingPackage() {
        return this.mImpl.getCallingPackage();
    }

    public void addOnActiveChangeListener(OnActiveChangeListener listener) {
        if (listener != null) {
            this.mActiveListeners.add(listener);
            return;
        }
        throw new IllegalArgumentException("Listener may not be null");
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener listener) {
        if (listener != null) {
            this.mActiveListeners.remove(listener);
            return;
        }
        throw new IllegalArgumentException("Listener may not be null");
    }

    public static MediaSessionCompat fromMediaSession(Context context, Object mediaSession) {
        MediaSessionImpl impl;
        if (Build.VERSION.SDK_INT < 21 || context == null || mediaSession == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 28) {
            impl = new MediaSessionImplApi28(mediaSession);
        } else {
            impl = new MediaSessionImplApi21(mediaSession);
        }
        return new MediaSessionCompat(context, impl);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public static void ensureClassLoader(@Nullable Bundle bundle) {
        if (bundle != null) {
            bundle.setClassLoader(MediaSessionCompat.class.getClassLoader());
        }
    }

    static PlaybackStateCompat getStateWithUpdatedPosition(PlaybackStateCompat state, MediaMetadataCompat metadata) {
        long duration;
        long position;
        PlaybackStateCompat playbackStateCompat = state;
        MediaMetadataCompat mediaMetadataCompat = metadata;
        if (playbackStateCompat == null || state.getPosition() == -1) {
            return playbackStateCompat;
        }
        if (state.getState() == 3 || state.getState() == 4 || state.getState() == 5) {
            long updateTime = state.getLastPositionUpdateTime();
            if (updateTime > 0) {
                long currentTime = SystemClock.elapsedRealtime();
                long position2 = ((long) (state.getPlaybackSpeed() * ((float) (currentTime - updateTime)))) + state.getPosition();
                if (mediaMetadataCompat == null || !mediaMetadataCompat.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                    duration = -1;
                } else {
                    duration = mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
                }
                if (duration >= 0 && position2 > duration) {
                    position = duration;
                } else if (position2 < 0) {
                    position = 0;
                } else {
                    position = position2;
                }
                return new PlaybackStateCompat.Builder(playbackStateCompat).setState(state.getState(), position, state.getPlaybackSpeed(), currentTime).build();
            }
        }
        return playbackStateCompat;
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$Callback */
    public static abstract class Callback {
        final MediaSession.Callback mCallbackFwk;
        private CallbackHandler mCallbackHandler = null;
        private boolean mMediaPlayPauseKeyPending;
        WeakReference<MediaSessionImpl> mSessionImpl;

        public Callback() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.mCallbackFwk = new MediaSessionCallbackApi21();
            } else {
                this.mCallbackFwk = null;
            }
        }

        /* access modifiers changed from: package-private */
        public void setSessionImpl(MediaSessionImpl impl, Handler handler) {
            this.mSessionImpl = new WeakReference<>(impl);
            CallbackHandler callbackHandler = this.mCallbackHandler;
            if (callbackHandler != null) {
                callbackHandler.removeCallbacksAndMessages(null);
            }
            this.mCallbackHandler = new CallbackHandler(handler.getLooper());
        }

        public void onCommand(String command, Bundle extras, ResultReceiver cb) {
        }

        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            MediaSessionImpl impl;
            KeyEvent keyEvent;
            if (Build.VERSION.SDK_INT >= 27 || (impl = this.mSessionImpl.get()) == null || this.mCallbackHandler == null || (keyEvent = (KeyEvent) mediaButtonEvent.getParcelableExtra("android.intent.extra.KEY_EVENT")) == null || keyEvent.getAction() != 0) {
                return false;
            }
            MediaSessionManager.RemoteUserInfo remoteUserInfo = impl.getCurrentControllerInfo();
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 79 || keyCode == 85) {
                if (keyEvent.getRepeatCount() > 0) {
                    handleMediaPlayPauseKeySingleTapIfPending();
                } else if (this.mMediaPlayPauseKeyPending) {
                    this.mCallbackHandler.removeMessages(1);
                    this.mMediaPlayPauseKeyPending = false;
                    PlaybackStateCompat state = impl.getPlaybackState();
                    if ((32 & (state == null ? 0 : state.getActions())) != 0) {
                        onSkipToNext();
                    }
                } else {
                    this.mMediaPlayPauseKeyPending = true;
                    CallbackHandler callbackHandler = this.mCallbackHandler;
                    callbackHandler.sendMessageDelayed(callbackHandler.obtainMessage(1, remoteUserInfo), (long) ViewConfiguration.getDoubleTapTimeout());
                }
                return true;
            }
            handleMediaPlayPauseKeySingleTapIfPending();
            return false;
        }

        /* access modifiers changed from: package-private */
        public void handleMediaPlayPauseKeySingleTapIfPending() {
            if (this.mMediaPlayPauseKeyPending) {
                boolean canPause = false;
                this.mMediaPlayPauseKeyPending = false;
                this.mCallbackHandler.removeMessages(1);
                MediaSessionImpl impl = this.mSessionImpl.get();
                if (impl != null) {
                    PlaybackStateCompat state = impl.getPlaybackState();
                    long validActions = state == null ? 0 : state.getActions();
                    boolean isPlaying = state != null && state.getState() == 3;
                    boolean canPlay = (516 & validActions) != 0;
                    if ((514 & validActions) != 0) {
                        canPause = true;
                    }
                    if (isPlaying && canPause) {
                        onPause();
                    } else if (!isPlaying && canPlay) {
                        onPlay();
                    }
                }
            }
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String mediaId, Bundle extras) {
        }

        public void onPrepareFromSearch(String query, Bundle extras) {
        }

        public void onPrepareFromUri(Uri uri, Bundle extras) {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String mediaId, Bundle extras) {
        }

        public void onPlayFromSearch(String query, Bundle extras) {
        }

        public void onPlayFromUri(Uri uri, Bundle extras) {
        }

        public void onSkipToQueueItem(long id) {
        }

        public void onPause() {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onFastForward() {
        }

        public void onRewind() {
        }

        public void onStop() {
        }

        public void onSeekTo(long pos) {
        }

        public void onSetRating(RatingCompat rating) {
        }

        public void onSetRating(RatingCompat rating, Bundle extras) {
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void onSetPlaybackSpeed(float speed) {
        }

        public void onSetCaptioningEnabled(boolean enabled) {
        }

        public void onSetRepeatMode(int repeatMode) {
        }

        public void onSetShuffleMode(int shuffleMode) {
        }

        public void onCustomAction(String action, Bundle extras) {
        }

        public void onAddQueueItem(MediaDescriptionCompat description) {
        }

        public void onAddQueueItem(MediaDescriptionCompat description, int index) {
        }

        public void onRemoveQueueItem(MediaDescriptionCompat description) {
        }

        @Deprecated
        public void onRemoveQueueItemAt(int index) {
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$Callback$CallbackHandler */
        private class CallbackHandler extends Handler {
            private static final int MSG_MEDIA_PLAY_PAUSE_KEY_DOUBLE_TAP_TIMEOUT = 1;

            CallbackHandler(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message msg) {
                MediaSessionImpl impl;
                if (msg.what == 1 && (impl = Callback.this.mSessionImpl.get()) != null) {
                    impl.setCurrentControllerInfo((MediaSessionManager.RemoteUserInfo) msg.obj);
                    Callback.this.handleMediaPlayPauseKeySingleTapIfPending();
                    impl.setCurrentControllerInfo(null);
                }
            }
        }

        @RequiresApi(21)
        /* renamed from: android.support.v4.media.session.MediaSessionCompat$Callback$MediaSessionCallbackApi21 */
        private class MediaSessionCallbackApi21 extends MediaSession.Callback {
            MediaSessionCallbackApi21() {
            }

            /* JADX INFO: additional move instructions added (1) to help type inference */
            /* JADX WARN: Type inference failed for: r1v1 */
            /* JADX WARN: Type inference failed for: r1v2, types: [android.support.v4.media.session.MediaSessionCompat$QueueItem] */
            /* JADX WARN: Type inference failed for: r1v12, types: [android.os.IBinder] */
            /* JADX WARN: Type inference failed for: r1v16 */
            /* JADX WARN: Type inference failed for: r1v17 */
            /* JADX WARNING: Multi-variable type inference failed */
            /* JADX WARNING: Unknown variable types count: 1 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onCommand(java.lang.String r7, android.os.Bundle r8, android.os.ResultReceiver r9) {
                /*
                    r6 = this;
                    android.support.p001v4.media.session.MediaSessionCompat.ensureClassLoader(r8)
                    r6.setCurrentControllerInfo()
                    java.lang.String r0 = "android.support.v4.media.session.command.GET_EXTRA_BINDER"
                    boolean r0 = r7.equals(r0)     // Catch:{ BadParcelableException -> 0x00ca }
                    r1 = 0
                    if (r0 == 0) goto L_0x0043
                    android.support.v4.media.session.MediaSessionCompat$Callback r0 = android.support.p001v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.ref.WeakReference<android.support.v4.media.session.MediaSessionCompat$MediaSessionImpl> r0 = r0.mSessionImpl     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.Object r0 = r0.get()     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi21 r0 = (android.support.p001v4.media.session.MediaSessionCompat.MediaSessionImplApi21) r0     // Catch:{ BadParcelableException -> 0x00ca }
                    if (r0 == 0) goto L_0x0041
                    android.os.Bundle r2 = new android.os.Bundle     // Catch:{ BadParcelableException -> 0x00ca }
                    r2.<init>()     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.session.MediaSessionCompat$Token r3 = r0.getSessionToken()     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.session.IMediaSession r4 = r3.getExtraBinder()     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.String r5 = "android.support.v4.media.session.EXTRA_BINDER"
                    if (r4 != 0) goto L_0x002d
                    goto L_0x0031
                L_0x002d:
                    android.os.IBinder r1 = r4.asBinder()     // Catch:{ BadParcelableException -> 0x00ca }
                L_0x0031:
                    android.support.p001v4.app.BundleCompat.putBinder(r2, r5, r1)     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.String r1 = "android.support.v4.media.session.SESSION_TOKEN2"
                    androidx.versionedparcelable.VersionedParcelable r5 = r3.getSession2Token()     // Catch:{ BadParcelableException -> 0x00ca }
                    androidx.versionedparcelable.ParcelUtils.putVersionedParcelable(r2, r1, r5)     // Catch:{ BadParcelableException -> 0x00ca }
                    r1 = 0
                    r9.send(r1, r2)     // Catch:{ BadParcelableException -> 0x00ca }
                L_0x0041:
                    goto L_0x00c9
                L_0x0043:
                    java.lang.String r0 = "android.support.v4.media.session.command.ADD_QUEUE_ITEM"
                    boolean r0 = r7.equals(r0)     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.String r2 = "android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"
                    if (r0 == 0) goto L_0x005a
                    android.support.v4.media.session.MediaSessionCompat$Callback r0 = android.support.p001v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00ca }
                    android.os.Parcelable r1 = r8.getParcelable(r2)     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.MediaDescriptionCompat r1 = (android.support.p001v4.media.MediaDescriptionCompat) r1     // Catch:{ BadParcelableException -> 0x00ca }
                    r0.onAddQueueItem(r1)     // Catch:{ BadParcelableException -> 0x00ca }
                    goto L_0x00c9
                L_0x005a:
                    java.lang.String r0 = "android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT"
                    boolean r0 = r7.equals(r0)     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.String r3 = "android.support.v4.media.session.command.ARGUMENT_INDEX"
                    if (r0 == 0) goto L_0x0074
                    android.support.v4.media.session.MediaSessionCompat$Callback r0 = android.support.p001v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00ca }
                    android.os.Parcelable r1 = r8.getParcelable(r2)     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.MediaDescriptionCompat r1 = (android.support.p001v4.media.MediaDescriptionCompat) r1     // Catch:{ BadParcelableException -> 0x00ca }
                    int r2 = r8.getInt(r3)     // Catch:{ BadParcelableException -> 0x00ca }
                    r0.onAddQueueItem(r1, r2)     // Catch:{ BadParcelableException -> 0x00ca }
                    goto L_0x00c9
                L_0x0074:
                    java.lang.String r0 = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM"
                    boolean r0 = r7.equals(r0)     // Catch:{ BadParcelableException -> 0x00ca }
                    if (r0 == 0) goto L_0x0088
                    android.support.v4.media.session.MediaSessionCompat$Callback r0 = android.support.p001v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00ca }
                    android.os.Parcelable r1 = r8.getParcelable(r2)     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.MediaDescriptionCompat r1 = (android.support.p001v4.media.MediaDescriptionCompat) r1     // Catch:{ BadParcelableException -> 0x00ca }
                    r0.onRemoveQueueItem(r1)     // Catch:{ BadParcelableException -> 0x00ca }
                    goto L_0x00c9
                L_0x0088:
                    java.lang.String r0 = "android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT"
                    boolean r0 = r7.equals(r0)     // Catch:{ BadParcelableException -> 0x00ca }
                    if (r0 == 0) goto L_0x00c4
                    android.support.v4.media.session.MediaSessionCompat$Callback r0 = android.support.p001v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.ref.WeakReference<android.support.v4.media.session.MediaSessionCompat$MediaSessionImpl> r0 = r0.mSessionImpl     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.Object r0 = r0.get()     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi21 r0 = (android.support.p001v4.media.session.MediaSessionCompat.MediaSessionImplApi21) r0     // Catch:{ BadParcelableException -> 0x00ca }
                    if (r0 == 0) goto L_0x00c3
                    java.util.List<android.support.v4.media.session.MediaSessionCompat$QueueItem> r2 = r0.mQueue     // Catch:{ BadParcelableException -> 0x00ca }
                    if (r2 == 0) goto L_0x00c3
                    r2 = -1
                    int r2 = r8.getInt(r3, r2)     // Catch:{ BadParcelableException -> 0x00ca }
                    if (r2 < 0) goto L_0x00b7
                    java.util.List<android.support.v4.media.session.MediaSessionCompat$QueueItem> r3 = r0.mQueue     // Catch:{ BadParcelableException -> 0x00ca }
                    int r3 = r3.size()     // Catch:{ BadParcelableException -> 0x00ca }
                    if (r2 >= r3) goto L_0x00b7
                    java.util.List<android.support.v4.media.session.MediaSessionCompat$QueueItem> r1 = r0.mQueue     // Catch:{ BadParcelableException -> 0x00ca }
                    java.lang.Object r1 = r1.get(r2)     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.session.MediaSessionCompat$QueueItem r1 = (android.support.p001v4.media.session.MediaSessionCompat.QueueItem) r1     // Catch:{ BadParcelableException -> 0x00ca }
                L_0x00b7:
                    if (r1 == 0) goto L_0x00c3
                    android.support.v4.media.session.MediaSessionCompat$Callback r3 = android.support.p001v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00ca }
                    android.support.v4.media.MediaDescriptionCompat r4 = r1.getDescription()     // Catch:{ BadParcelableException -> 0x00ca }
                    r3.onRemoveQueueItem(r4)     // Catch:{ BadParcelableException -> 0x00ca }
                L_0x00c3:
                    goto L_0x00c9
                L_0x00c4:
                    android.support.v4.media.session.MediaSessionCompat$Callback r0 = android.support.p001v4.media.session.MediaSessionCompat.Callback.this     // Catch:{ BadParcelableException -> 0x00ca }
                    r0.onCommand(r7, r8, r9)     // Catch:{ BadParcelableException -> 0x00ca }
                L_0x00c9:
                    goto L_0x00d2
                L_0x00ca:
                    r0 = move-exception
                    java.lang.String r1 = "MediaSessionCompat"
                    java.lang.String r2 = "Could not unparcel the extra data."
                    android.util.Log.e(r1, r2)
                L_0x00d2:
                    r6.clearCurrentControllerInfo()
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.p001v4.media.session.MediaSessionCompat.Callback.MediaSessionCallbackApi21.onCommand(java.lang.String, android.os.Bundle, android.os.ResultReceiver):void");
            }

            public boolean onMediaButtonEvent(Intent mediaButtonIntent) {
                setCurrentControllerInfo();
                boolean result = Callback.this.onMediaButtonEvent(mediaButtonIntent);
                clearCurrentControllerInfo();
                return result || super.onMediaButtonEvent(mediaButtonIntent);
            }

            public void onPlay() {
                setCurrentControllerInfo();
                Callback.this.onPlay();
                clearCurrentControllerInfo();
            }

            public void onPlayFromMediaId(String mediaId, Bundle extras) {
                MediaSessionCompat.ensureClassLoader(extras);
                setCurrentControllerInfo();
                Callback.this.onPlayFromMediaId(mediaId, extras);
                clearCurrentControllerInfo();
            }

            public void onPlayFromSearch(String search, Bundle extras) {
                MediaSessionCompat.ensureClassLoader(extras);
                setCurrentControllerInfo();
                Callback.this.onPlayFromSearch(search, extras);
                clearCurrentControllerInfo();
            }

            @RequiresApi(23)
            public void onPlayFromUri(Uri uri, Bundle extras) {
                MediaSessionCompat.ensureClassLoader(extras);
                setCurrentControllerInfo();
                Callback.this.onPlayFromUri(uri, extras);
                clearCurrentControllerInfo();
            }

            public void onSkipToQueueItem(long id) {
                setCurrentControllerInfo();
                Callback.this.onSkipToQueueItem(id);
                clearCurrentControllerInfo();
            }

            public void onPause() {
                setCurrentControllerInfo();
                Callback.this.onPause();
                clearCurrentControllerInfo();
            }

            public void onSkipToNext() {
                setCurrentControllerInfo();
                Callback.this.onSkipToNext();
                clearCurrentControllerInfo();
            }

            public void onSkipToPrevious() {
                setCurrentControllerInfo();
                Callback.this.onSkipToPrevious();
                clearCurrentControllerInfo();
            }

            public void onFastForward() {
                setCurrentControllerInfo();
                Callback.this.onFastForward();
                clearCurrentControllerInfo();
            }

            public void onRewind() {
                setCurrentControllerInfo();
                Callback.this.onRewind();
                clearCurrentControllerInfo();
            }

            public void onStop() {
                setCurrentControllerInfo();
                Callback.this.onStop();
                clearCurrentControllerInfo();
            }

            public void onSeekTo(long pos) {
                setCurrentControllerInfo();
                Callback.this.onSeekTo(pos);
                clearCurrentControllerInfo();
            }

            public void onSetRating(Rating ratingFwk) {
                setCurrentControllerInfo();
                Callback.this.onSetRating(RatingCompat.fromRating(ratingFwk));
                clearCurrentControllerInfo();
            }

            public void onSetRating(Rating ratingFwk, Bundle extras) {
            }

            public void onCustomAction(String action, Bundle extras) {
                MediaSessionCompat.ensureClassLoader(extras);
                setCurrentControllerInfo();
                Bundle bundle = extras.getBundle(MediaSessionCompat.ACTION_ARGUMENT_EXTRAS);
                MediaSessionCompat.ensureClassLoader(bundle);
                if (action.equals(MediaSessionCompat.ACTION_PLAY_FROM_URI)) {
                    Callback.this.onPlayFromUri((Uri) extras.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), bundle);
                } else if (action.equals(MediaSessionCompat.ACTION_PREPARE)) {
                    Callback.this.onPrepare();
                } else if (action.equals(MediaSessionCompat.ACTION_PREPARE_FROM_MEDIA_ID)) {
                    Callback.this.onPrepareFromMediaId(extras.getString(MediaSessionCompat.ACTION_ARGUMENT_MEDIA_ID), bundle);
                } else if (action.equals(MediaSessionCompat.ACTION_PREPARE_FROM_SEARCH)) {
                    Callback.this.onPrepareFromSearch(extras.getString(MediaSessionCompat.ACTION_ARGUMENT_QUERY), bundle);
                } else if (action.equals(MediaSessionCompat.ACTION_PREPARE_FROM_URI)) {
                    Callback.this.onPrepareFromUri((Uri) extras.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_URI), bundle);
                } else if (action.equals(MediaSessionCompat.ACTION_SET_CAPTIONING_ENABLED)) {
                    Callback.this.onSetCaptioningEnabled(extras.getBoolean(MediaSessionCompat.ACTION_ARGUMENT_CAPTIONING_ENABLED));
                } else if (action.equals(MediaSessionCompat.ACTION_SET_REPEAT_MODE)) {
                    Callback.this.onSetRepeatMode(extras.getInt(MediaSessionCompat.ACTION_ARGUMENT_REPEAT_MODE));
                } else if (action.equals(MediaSessionCompat.ACTION_SET_SHUFFLE_MODE)) {
                    Callback.this.onSetShuffleMode(extras.getInt(MediaSessionCompat.ACTION_ARGUMENT_SHUFFLE_MODE));
                } else if (action.equals(MediaSessionCompat.ACTION_SET_RATING)) {
                    Callback.this.onSetRating((RatingCompat) extras.getParcelable(MediaSessionCompat.ACTION_ARGUMENT_RATING), bundle);
                } else if (action.equals(MediaSessionCompat.ACTION_SET_PLAYBACK_SPEED)) {
                    Callback.this.onSetPlaybackSpeed(extras.getFloat(MediaSessionCompat.ACTION_ARGUMENT_PLAYBACK_SPEED, 1.0f));
                } else {
                    Callback.this.onCustomAction(action, extras);
                }
                clearCurrentControllerInfo();
            }

            /* access modifiers changed from: package-private */
            public void setCurrentControllerInfo() {
                if (Build.VERSION.SDK_INT < 28) {
                    MediaSessionImpl sessionImpl = Callback.this.mSessionImpl != null ? Callback.this.mSessionImpl.get() : null;
                    if (sessionImpl != null) {
                        String packageName = sessionImpl.getCallingPackage();
                        if (TextUtils.isEmpty(packageName)) {
                            packageName = MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER;
                        }
                        sessionImpl.setCurrentControllerInfo(new MediaSessionManager.RemoteUserInfo(packageName, -1, -1));
                    }
                }
            }

            /* access modifiers changed from: package-private */
            public void clearCurrentControllerInfo() {
                MediaSessionImpl sessionImpl = Callback.this.mSessionImpl != null ? Callback.this.mSessionImpl.get() : null;
                if (sessionImpl != null) {
                    sessionImpl.setCurrentControllerInfo(null);
                }
            }

            @RequiresApi(24)
            public void onPrepare() {
                setCurrentControllerInfo();
                Callback.this.onPrepare();
                clearCurrentControllerInfo();
            }

            @RequiresApi(24)
            public void onPrepareFromMediaId(String mediaId, Bundle extras) {
                MediaSessionCompat.ensureClassLoader(extras);
                setCurrentControllerInfo();
                Callback.this.onPrepareFromMediaId(mediaId, extras);
                clearCurrentControllerInfo();
            }

            @RequiresApi(24)
            public void onPrepareFromSearch(String query, Bundle extras) {
                MediaSessionCompat.ensureClassLoader(extras);
                setCurrentControllerInfo();
                Callback.this.onPrepareFromSearch(query, extras);
                clearCurrentControllerInfo();
            }

            @RequiresApi(24)
            public void onPrepareFromUri(Uri uri, Bundle extras) {
                MediaSessionCompat.ensureClassLoader(extras);
                setCurrentControllerInfo();
                Callback.this.onPrepareFromUri(uri, extras);
                clearCurrentControllerInfo();
            }
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$Token */
    public static final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() {
            public Token createFromParcel(Parcel in) {
                Object inner;
                if (Build.VERSION.SDK_INT >= 21) {
                    inner = in.readParcelable(null);
                } else {
                    inner = in.readStrongBinder();
                }
                return new Token(inner);
            }

            public Token[] newArray(int size) {
                return new Token[size];
            }
        };
        private IMediaSession mExtraBinder;
        private final Object mInner;
        private VersionedParcelable mSession2Token;

        Token(Object inner) {
            this(inner, null, null);
        }

        Token(Object inner, IMediaSession extraBinder) {
            this(inner, extraBinder, null);
        }

        Token(Object inner, IMediaSession extraBinder, VersionedParcelable session2Token) {
            this.mInner = inner;
            this.mExtraBinder = extraBinder;
            this.mSession2Token = session2Token;
        }

        public static Token fromToken(Object token) {
            return fromToken(token, null);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static Token fromToken(Object token, IMediaSession extraBinder) {
            if (token == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            if (token instanceof MediaSession.Token) {
                return new Token(token, extraBinder);
            }
            throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            if (Build.VERSION.SDK_INT >= 21) {
                dest.writeParcelable((Parcelable) this.mInner, flags);
            } else {
                dest.writeStrongBinder((IBinder) this.mInner);
            }
        }

        public int hashCode() {
            Object obj = this.mInner;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token other = (Token) obj;
            Object obj2 = this.mInner;
            if (obj2 != null) {
                Object obj3 = other.mInner;
                if (obj3 == null) {
                    return false;
                }
                return obj2.equals(obj3);
            } else if (other.mInner == null) {
                return true;
            } else {
                return false;
            }
        }

        public Object getToken() {
            return this.mInner;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public IMediaSession getExtraBinder() {
            return this.mExtraBinder;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void setExtraBinder(IMediaSession extraBinder) {
            this.mExtraBinder = extraBinder;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public VersionedParcelable getSession2Token() {
            return this.mSession2Token;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public void setSession2Token(VersionedParcelable session2Token) {
            this.mSession2Token = session2Token;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public Bundle toBundle() {
            Bundle bundle = new Bundle();
            bundle.putParcelable(MediaSessionCompat.KEY_TOKEN, this);
            IMediaSession iMediaSession = this.mExtraBinder;
            if (iMediaSession != null) {
                BundleCompat.putBinder(bundle, MediaSessionCompat.KEY_EXTRA_BINDER, iMediaSession.asBinder());
            }
            VersionedParcelable versionedParcelable = this.mSession2Token;
            if (versionedParcelable != null) {
                ParcelUtils.putVersionedParcelable(bundle, MediaSessionCompat.KEY_SESSION2_TOKEN, versionedParcelable);
            }
            return bundle;
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
        public static Token fromBundle(Bundle tokenBundle) {
            if (tokenBundle == null) {
                return null;
            }
            IMediaSession extraSession = IMediaSession.Stub.asInterface(BundleCompat.getBinder(tokenBundle, MediaSessionCompat.KEY_EXTRA_BINDER));
            VersionedParcelable session2Token = ParcelUtils.getVersionedParcelable(tokenBundle, MediaSessionCompat.KEY_SESSION2_TOKEN);
            Token token = (Token) tokenBundle.getParcelable(MediaSessionCompat.KEY_TOKEN);
            if (token == null) {
                return null;
            }
            return new Token(token.mInner, extraSession, session2Token);
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$QueueItem */
    public static final class QueueItem implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>() {
            public QueueItem createFromParcel(Parcel p) {
                return new QueueItem(p);
            }

            public QueueItem[] newArray(int size) {
                return new QueueItem[size];
            }
        };
        public static final int UNKNOWN_ID = -1;
        private final MediaDescriptionCompat mDescription;
        private final long mId;
        private MediaSession.QueueItem mItemFwk;

        public QueueItem(MediaDescriptionCompat description, long id) {
            this(null, description, id);
        }

        private QueueItem(MediaSession.QueueItem queueItem, MediaDescriptionCompat description, long id) {
            if (description == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            } else if (id != -1) {
                this.mDescription = description;
                this.mId = id;
                this.mItemFwk = queueItem;
            } else {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
        }

        QueueItem(Parcel in) {
            this.mDescription = MediaDescriptionCompat.CREATOR.createFromParcel(in);
            this.mId = in.readLong();
        }

        public MediaDescriptionCompat getDescription() {
            return this.mDescription;
        }

        public long getQueueId() {
            return this.mId;
        }

        public void writeToParcel(Parcel dest, int flags) {
            this.mDescription.writeToParcel(dest, flags);
            dest.writeLong(this.mId);
        }

        public int describeContents() {
            return 0;
        }

        public Object getQueueItem() {
            if (this.mItemFwk != null || Build.VERSION.SDK_INT < 21) {
                return this.mItemFwk;
            }
            this.mItemFwk = new MediaSession.QueueItem((MediaDescription) this.mDescription.getMediaDescription(), this.mId);
            return this.mItemFwk;
        }

        public static QueueItem fromQueueItem(Object queueItem) {
            if (queueItem == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            MediaSession.QueueItem queueItemObj = (MediaSession.QueueItem) queueItem;
            return new QueueItem(queueItemObj, MediaDescriptionCompat.fromMediaDescription(queueItemObj.getDescription()), queueItemObj.getQueueId());
        }

        public static List<QueueItem> fromQueueItemList(List<?> itemList) {
            if (itemList == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            List<QueueItem> items = new ArrayList<>();
            for (Object itemObj : itemList) {
                items.add(fromQueueItem(itemObj));
            }
            return items;
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }
    }

    @SuppressLint({"BanParcelableUsage"})
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$ResultReceiverWrapper */
    public static final class ResultReceiverWrapper implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator<ResultReceiverWrapper>() {
            public ResultReceiverWrapper createFromParcel(Parcel p) {
                return new ResultReceiverWrapper(p);
            }

            public ResultReceiverWrapper[] newArray(int size) {
                return new ResultReceiverWrapper[size];
            }
        };
        ResultReceiver mResultReceiver;

        public ResultReceiverWrapper(@NonNull ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }

        ResultReceiverWrapper(Parcel in) {
            this.mResultReceiver = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(in);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            this.mResultReceiver.writeToParcel(dest, flags);
        }
    }

    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplBase */
    static class MediaSessionImplBase implements MediaSessionImpl {
        static final int RCC_PLAYSTATE_NONE = 0;
        final AudioManager mAudioManager;
        volatile Callback mCallback;
        boolean mCaptioningEnabled;
        private final Context mContext;
        final RemoteCallbackList<IMediaControllerCallback> mControllerCallbacks = new RemoteCallbackList<>();
        boolean mDestroyed = false;
        Bundle mExtras;
        int mFlags = 3;
        private MessageHandler mHandler;
        boolean mIsActive = false;
        int mLocalStream;
        final Object mLock = new Object();
        private final ComponentName mMediaButtonReceiverComponentName;
        private final PendingIntent mMediaButtonReceiverIntent;
        MediaMetadataCompat mMetadata;
        final String mPackageName;
        List<QueueItem> mQueue;
        CharSequence mQueueTitle;
        int mRatingType;
        final RemoteControlClient mRcc;
        private MediaSessionManager.RemoteUserInfo mRemoteUserInfo;
        int mRepeatMode;
        PendingIntent mSessionActivity;
        final Bundle mSessionInfo;
        int mShuffleMode;
        PlaybackStateCompat mState;
        private final MediaSessionStub mStub;
        final String mTag;
        private final Token mToken;
        private VolumeProviderCompat.Callback mVolumeCallback = new VolumeProviderCompat.Callback() {
            public void onVolumeChanged(VolumeProviderCompat volumeProvider) {
                if (MediaSessionImplBase.this.mVolumeProvider == volumeProvider) {
                    MediaSessionImplBase.this.sendVolumeInfoChanged(new ParcelableVolumeInfo(MediaSessionImplBase.this.mVolumeType, MediaSessionImplBase.this.mLocalStream, volumeProvider.getVolumeControl(), volumeProvider.getMaxVolume(), volumeProvider.getCurrentVolume()));
                }
            }
        };
        VolumeProviderCompat mVolumeProvider;
        int mVolumeType;

        public MediaSessionImplBase(Context context, String tag, ComponentName mbrComponent, PendingIntent mbrIntent, Bundle sessionInfo) {
            if (mbrComponent != null) {
                this.mContext = context;
                this.mPackageName = context.getPackageName();
                this.mSessionInfo = sessionInfo;
                this.mAudioManager = (AudioManager) context.getSystemService("audio");
                this.mTag = tag;
                this.mMediaButtonReceiverComponentName = mbrComponent;
                this.mMediaButtonReceiverIntent = mbrIntent;
                this.mStub = new MediaSessionStub();
                this.mToken = new Token(this.mStub);
                this.mRatingType = 0;
                this.mVolumeType = 1;
                this.mLocalStream = 3;
                this.mRcc = new RemoteControlClient(mbrIntent);
                return;
            }
            throw new IllegalArgumentException("MediaButtonReceiver component may not be null.");
        }

        public void setCallback(Callback callback, Handler handler) {
            Handler handler2;
            this.mCallback = callback;
            if (callback != null) {
                if (handler == null) {
                    handler2 = new Handler();
                } else {
                    handler2 = handler;
                }
                synchronized (this.mLock) {
                    if (this.mHandler != null) {
                        this.mHandler.removeCallbacksAndMessages(null);
                    }
                    this.mHandler = new MessageHandler(handler2.getLooper());
                    this.mCallback.setSessionImpl(this, handler2);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void postToHandler(int what, int arg1, int arg2, Object obj, Bundle extras) {
            synchronized (this.mLock) {
                if (this.mHandler != null) {
                    Message msg = this.mHandler.obtainMessage(what, arg1, arg2, obj);
                    Bundle data = new Bundle();
                    int uid = Binder.getCallingUid();
                    data.putInt("data_calling_uid", uid);
                    data.putString(MediaSessionCompat.DATA_CALLING_PACKAGE, getPackageNameForUid(uid));
                    int pid = Binder.getCallingPid();
                    if (pid > 0) {
                        data.putInt("data_calling_pid", pid);
                    } else {
                        data.putInt("data_calling_pid", -1);
                    }
                    if (extras != null) {
                        data.putBundle(MediaSessionCompat.DATA_EXTRAS, extras);
                    }
                    msg.setData(data);
                    msg.sendToTarget();
                }
            }
        }

        /* access modifiers changed from: package-private */
        public String getPackageNameForUid(int uid) {
            String result = this.mContext.getPackageManager().getNameForUid(uid);
            if (TextUtils.isEmpty(result)) {
                return MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER;
            }
            return result;
        }

        public void setFlags(int flags) {
            synchronized (this.mLock) {
                this.mFlags = flags | 1 | 2;
            }
        }

        public void setPlaybackToLocal(int stream) {
            VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
            if (volumeProviderCompat != null) {
                volumeProviderCompat.setCallback(null);
            }
            this.mLocalStream = stream;
            this.mVolumeType = 1;
            int i = this.mVolumeType;
            int i2 = this.mLocalStream;
            sendVolumeInfoChanged(new ParcelableVolumeInfo(i, i2, 2, this.mAudioManager.getStreamMaxVolume(i2), this.mAudioManager.getStreamVolume(this.mLocalStream)));
        }

        public void setPlaybackToRemote(VolumeProviderCompat volumeProvider) {
            if (volumeProvider != null) {
                VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
                if (volumeProviderCompat != null) {
                    volumeProviderCompat.setCallback(null);
                }
                this.mVolumeType = 2;
                this.mVolumeProvider = volumeProvider;
                sendVolumeInfoChanged(new ParcelableVolumeInfo(this.mVolumeType, this.mLocalStream, this.mVolumeProvider.getVolumeControl(), this.mVolumeProvider.getMaxVolume(), this.mVolumeProvider.getCurrentVolume()));
                volumeProvider.setCallback(this.mVolumeCallback);
                return;
            }
            throw new IllegalArgumentException("volumeProvider may not be null");
        }

        public void setActive(boolean active) {
            if (active != this.mIsActive) {
                this.mIsActive = active;
                updateMbrAndRcc();
            }
        }

        public boolean isActive() {
            return this.mIsActive;
        }

        public void sendSessionEvent(String event, Bundle extras) {
            sendEvent(event, extras);
        }

        public void release() {
            this.mIsActive = false;
            this.mDestroyed = true;
            updateMbrAndRcc();
            sendSessionDestroyed();
        }

        public Token getSessionToken() {
            return this.mToken;
        }

        public void setPlaybackState(PlaybackStateCompat state) {
            synchronized (this.mLock) {
                this.mState = state;
            }
            sendState(state);
            if (this.mIsActive) {
                if (state == null) {
                    this.mRcc.setPlaybackState(0);
                    this.mRcc.setTransportControlFlags(0);
                    return;
                }
                setRccState(state);
                this.mRcc.setTransportControlFlags(getRccTransportControlFlagsFromActions(state.getActions()));
            }
        }

        public PlaybackStateCompat getPlaybackState() {
            PlaybackStateCompat playbackStateCompat;
            synchronized (this.mLock) {
                playbackStateCompat = this.mState;
            }
            return playbackStateCompat;
        }

        /* access modifiers changed from: package-private */
        public void setRccState(PlaybackStateCompat state) {
            this.mRcc.setPlaybackState(getRccStateFromState(state.getState()));
        }

        /* access modifiers changed from: package-private */
        public int getRccStateFromState(int state) {
            switch (state) {
                case 0:
                    return 0;
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
                case 4:
                    return 4;
                case 5:
                    return 5;
                case 6:
                case 8:
                    return 8;
                case 7:
                    return 9;
                case 9:
                    return 7;
                case 10:
                case 11:
                    return 6;
                default:
                    return -1;
            }
        }

        /* access modifiers changed from: package-private */
        public int getRccTransportControlFlagsFromActions(long actions) {
            int transportControlFlags = 0;
            if ((1 & actions) != 0) {
                transportControlFlags = 0 | 32;
            }
            if ((2 & actions) != 0) {
                transportControlFlags |= 16;
            }
            if ((4 & actions) != 0) {
                transportControlFlags |= 4;
            }
            if ((8 & actions) != 0) {
                transportControlFlags |= 2;
            }
            if ((16 & actions) != 0) {
                transportControlFlags |= 1;
            }
            if ((32 & actions) != 0) {
                transportControlFlags |= 128;
            }
            if ((64 & actions) != 0) {
                transportControlFlags |= 64;
            }
            if ((512 & actions) != 0) {
                return transportControlFlags | 8;
            }
            return transportControlFlags;
        }

        public void setMetadata(MediaMetadataCompat metadata) {
            Bundle bundle;
            if (metadata != null) {
                metadata = new MediaMetadataCompat.Builder(metadata, MediaSessionCompat.sMaxBitmapSize).build();
            }
            synchronized (this.mLock) {
                this.mMetadata = metadata;
            }
            sendMetadata(metadata);
            if (this.mIsActive) {
                if (metadata == null) {
                    bundle = null;
                } else {
                    bundle = metadata.getBundle();
                }
                buildRccMetadata(bundle).apply();
            }
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.media.RemoteControlClient.MetadataEditor.putBitmap(int, android.graphics.Bitmap):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException}
         arg types: [int, android.graphics.Bitmap]
         candidates:
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putBitmap(int, android.graphics.Bitmap):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.MediaMetadataEditor.putBitmap(int, android.graphics.Bitmap):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putBitmap(int, android.graphics.Bitmap):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException} */
        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.media.RemoteControlClient.MetadataEditor.putString(int, java.lang.String):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException}
         arg types: [int, java.lang.String]
         candidates:
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putString(int, java.lang.String):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.MediaMetadataEditor.putString(int, java.lang.String):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putString(int, java.lang.String):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException} */
        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.media.RemoteControlClient.MetadataEditor.putLong(int, long):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException}
         arg types: [int, long]
         candidates:
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putLong(int, long):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.MediaMetadataEditor.putLong(int, long):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putLong(int, long):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException} */
        /* access modifiers changed from: package-private */
        public RemoteControlClient.MetadataEditor buildRccMetadata(Bundle metadata) {
            RemoteControlClient.MetadataEditor editor = this.mRcc.editMetadata(true);
            if (metadata == null) {
                return editor;
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_ART)) {
                Bitmap art = (Bitmap) metadata.getParcelable(MediaMetadataCompat.METADATA_KEY_ART);
                if (art != null) {
                    art = art.copy(art.getConfig(), false);
                }
                editor.putBitmap(100, art);
            } else if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ART)) {
                Bitmap art2 = (Bitmap) metadata.getParcelable(MediaMetadataCompat.METADATA_KEY_ALBUM_ART);
                if (art2 != null) {
                    art2 = art2.copy(art2.getConfig(), false);
                }
                editor.putBitmap(100, art2);
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM)) {
                editor.putString(1, metadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST)) {
                editor.putString(13, metadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ARTIST));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_ARTIST)) {
                editor.putString(2, metadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_AUTHOR)) {
                editor.putString(3, metadata.getString(MediaMetadataCompat.METADATA_KEY_AUTHOR));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_COMPILATION)) {
                editor.putString(15, metadata.getString(MediaMetadataCompat.METADATA_KEY_COMPILATION));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_COMPOSER)) {
                editor.putString(4, metadata.getString(MediaMetadataCompat.METADATA_KEY_COMPOSER));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_DATE)) {
                editor.putString(5, metadata.getString(MediaMetadataCompat.METADATA_KEY_DATE));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER)) {
                editor.putLong(14, metadata.getLong(MediaMetadataCompat.METADATA_KEY_DISC_NUMBER));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
                editor.putLong(9, metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_GENRE)) {
                editor.putString(6, metadata.getString(MediaMetadataCompat.METADATA_KEY_GENRE));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_TITLE)) {
                editor.putString(7, metadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER)) {
                editor.putLong(0, metadata.getLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_WRITER)) {
                editor.putString(11, metadata.getString(MediaMetadataCompat.METADATA_KEY_WRITER));
            }
            return editor;
        }

        public void setSessionActivity(PendingIntent pi) {
            synchronized (this.mLock) {
                this.mSessionActivity = pi;
            }
        }

        public void setMediaButtonReceiver(PendingIntent mbr) {
        }

        public void setQueue(List<QueueItem> queue) {
            this.mQueue = queue;
            sendQueue(queue);
        }

        public void setQueueTitle(CharSequence title) {
            this.mQueueTitle = title;
            sendQueueTitle(title);
        }

        public Object getMediaSession() {
            return null;
        }

        public Object getRemoteControlClient() {
            return null;
        }

        public String getCallingPackage() {
            return null;
        }

        public void setRatingType(int type) {
            this.mRatingType = type;
        }

        public void setCaptioningEnabled(boolean enabled) {
            if (this.mCaptioningEnabled != enabled) {
                this.mCaptioningEnabled = enabled;
                sendCaptioningEnabled(enabled);
            }
        }

        public void setRepeatMode(int repeatMode) {
            if (this.mRepeatMode != repeatMode) {
                this.mRepeatMode = repeatMode;
                sendRepeatMode(repeatMode);
            }
        }

        public void setShuffleMode(int shuffleMode) {
            if (this.mShuffleMode != shuffleMode) {
                this.mShuffleMode = shuffleMode;
                sendShuffleMode(shuffleMode);
            }
        }

        public void setExtras(Bundle extras) {
            this.mExtras = extras;
            sendExtras(extras);
        }

        public MediaSessionManager.RemoteUserInfo getCurrentControllerInfo() {
            MediaSessionManager.RemoteUserInfo remoteUserInfo;
            synchronized (this.mLock) {
                remoteUserInfo = this.mRemoteUserInfo;
            }
            return remoteUserInfo;
        }

        public void setCurrentControllerInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            synchronized (this.mLock) {
                this.mRemoteUserInfo = remoteUserInfo;
            }
        }

        /* access modifiers changed from: package-private */
        public void updateMbrAndRcc() {
            if (this.mIsActive) {
                registerMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
                this.mAudioManager.registerRemoteControlClient(this.mRcc);
                setMetadata(this.mMetadata);
                setPlaybackState(this.mState);
                return;
            }
            unregisterMediaButtonEventReceiver(this.mMediaButtonReceiverIntent, this.mMediaButtonReceiverComponentName);
            this.mRcc.setPlaybackState(0);
            this.mAudioManager.unregisterRemoteControlClient(this.mRcc);
        }

        /* access modifiers changed from: package-private */
        public void registerMediaButtonEventReceiver(PendingIntent mbrIntent, ComponentName mbrComponent) {
            this.mAudioManager.registerMediaButtonEventReceiver(mbrComponent);
        }

        /* access modifiers changed from: package-private */
        public void unregisterMediaButtonEventReceiver(PendingIntent mbrIntent, ComponentName mbrComponent) {
            this.mAudioManager.unregisterMediaButtonEventReceiver(mbrComponent);
        }

        /* access modifiers changed from: package-private */
        public void adjustVolume(int direction, int flags) {
            if (this.mVolumeType == 2) {
                VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
                if (volumeProviderCompat != null) {
                    volumeProviderCompat.onAdjustVolume(direction);
                    return;
                }
                return;
            }
            this.mAudioManager.adjustStreamVolume(this.mLocalStream, direction, flags);
        }

        /* access modifiers changed from: package-private */
        public void setVolumeTo(int value, int flags) {
            if (this.mVolumeType == 2) {
                VolumeProviderCompat volumeProviderCompat = this.mVolumeProvider;
                if (volumeProviderCompat != null) {
                    volumeProviderCompat.onSetVolumeTo(value);
                    return;
                }
                return;
            }
            this.mAudioManager.setStreamVolume(this.mLocalStream, value, flags);
        }

        /* access modifiers changed from: package-private */
        public void sendVolumeInfoChanged(ParcelableVolumeInfo info) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onVolumeInfoChanged(info);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendSessionDestroyed() {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onSessionDestroyed();
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
            this.mControllerCallbacks.kill();
        }

        private void sendEvent(String event, Bundle extras) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onEvent(event, extras);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendState(PlaybackStateCompat state) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onPlaybackStateChanged(state);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendMetadata(MediaMetadataCompat metadata) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onMetadataChanged(metadata);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendQueue(List<QueueItem> queue) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onQueueChanged(queue);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendQueueTitle(CharSequence queueTitle) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onQueueTitleChanged(queueTitle);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendCaptioningEnabled(boolean enabled) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onCaptioningEnabledChanged(enabled);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendRepeatMode(int repeatMode) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onRepeatModeChanged(repeatMode);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendShuffleMode(int shuffleMode) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onShuffleModeChanged(shuffleMode);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        private void sendExtras(Bundle extras) {
            for (int i = this.mControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mControllerCallbacks.getBroadcastItem(i).onExtrasChanged(extras);
                } catch (RemoteException e) {
                }
            }
            this.mControllerCallbacks.finishBroadcast();
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplBase$MediaSessionStub */
        class MediaSessionStub extends IMediaSession.Stub {
            MediaSessionStub() {
            }

            public void sendCommand(String command, Bundle args, ResultReceiverWrapper cb) {
                postToHandler(1, new Command(command, args, cb == null ? null : cb.mResultReceiver));
            }

            public boolean sendMediaButton(KeyEvent mediaButton) {
                postToHandler(21, mediaButton);
                return true;
            }

            public void registerCallbackListener(IMediaControllerCallback cb) {
                if (MediaSessionImplBase.this.mDestroyed) {
                    try {
                        cb.onSessionDestroyed();
                    } catch (Exception e) {
                    }
                } else {
                    MediaSessionImplBase.this.mControllerCallbacks.register(cb, new MediaSessionManager.RemoteUserInfo(MediaSessionImplBase.this.getPackageNameForUid(getCallingUid()), getCallingPid(), getCallingUid()));
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback cb) {
                MediaSessionImplBase.this.mControllerCallbacks.unregister(cb);
            }

            public String getPackageName() {
                return MediaSessionImplBase.this.mPackageName;
            }

            public Bundle getSessionInfo() {
                if (MediaSessionImplBase.this.mSessionInfo == null) {
                    return null;
                }
                return new Bundle(MediaSessionImplBase.this.mSessionInfo);
            }

            public String getTag() {
                return MediaSessionImplBase.this.mTag;
            }

            public PendingIntent getLaunchPendingIntent() {
                PendingIntent pendingIntent;
                synchronized (MediaSessionImplBase.this.mLock) {
                    pendingIntent = MediaSessionImplBase.this.mSessionActivity;
                }
                return pendingIntent;
            }

            public long getFlags() {
                long j;
                synchronized (MediaSessionImplBase.this.mLock) {
                    j = (long) MediaSessionImplBase.this.mFlags;
                }
                return j;
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                int volumeType;
                int stream;
                int current;
                int max;
                int controlType;
                synchronized (MediaSessionImplBase.this.mLock) {
                    volumeType = MediaSessionImplBase.this.mVolumeType;
                    stream = MediaSessionImplBase.this.mLocalStream;
                    VolumeProviderCompat vp = MediaSessionImplBase.this.mVolumeProvider;
                    if (volumeType == 2) {
                        controlType = vp.getVolumeControl();
                        max = vp.getMaxVolume();
                        current = vp.getCurrentVolume();
                    } else {
                        int max2 = MediaSessionImplBase.this.mAudioManager.getStreamMaxVolume(stream);
                        current = MediaSessionImplBase.this.mAudioManager.getStreamVolume(stream);
                        controlType = 2;
                        max = max2;
                    }
                }
                return new ParcelableVolumeInfo(volumeType, stream, controlType, max, current);
            }

            public void adjustVolume(int direction, int flags, String packageName) {
                MediaSessionImplBase.this.adjustVolume(direction, flags);
            }

            public void setVolumeTo(int value, int flags, String packageName) {
                MediaSessionImplBase.this.setVolumeTo(value, flags);
            }

            public void prepare() throws RemoteException {
                postToHandler(3);
            }

            public void prepareFromMediaId(String mediaId, Bundle extras) throws RemoteException {
                postToHandler(4, mediaId, extras);
            }

            public void prepareFromSearch(String query, Bundle extras) throws RemoteException {
                postToHandler(5, query, extras);
            }

            public void prepareFromUri(Uri uri, Bundle extras) throws RemoteException {
                postToHandler(6, uri, extras);
            }

            public void play() throws RemoteException {
                postToHandler(7);
            }

            public void playFromMediaId(String mediaId, Bundle extras) throws RemoteException {
                postToHandler(8, mediaId, extras);
            }

            public void playFromSearch(String query, Bundle extras) throws RemoteException {
                postToHandler(9, query, extras);
            }

            public void playFromUri(Uri uri, Bundle extras) throws RemoteException {
                postToHandler(10, uri, extras);
            }

            public void skipToQueueItem(long id) {
                postToHandler(11, Long.valueOf(id));
            }

            public void pause() throws RemoteException {
                postToHandler(12);
            }

            public void stop() throws RemoteException {
                postToHandler(13);
            }

            public void next() throws RemoteException {
                postToHandler(14);
            }

            public void previous() throws RemoteException {
                postToHandler(15);
            }

            public void fastForward() throws RemoteException {
                postToHandler(16);
            }

            public void rewind() throws RemoteException {
                postToHandler(17);
            }

            public void seekTo(long pos) throws RemoteException {
                postToHandler(18, Long.valueOf(pos));
            }

            public void rate(RatingCompat rating) throws RemoteException {
                postToHandler(19, rating);
            }

            public void rateWithExtras(RatingCompat rating, Bundle extras) throws RemoteException {
                postToHandler(31, rating, extras);
            }

            public void setPlaybackSpeed(float speed) throws RemoteException {
                postToHandler(32, Float.valueOf(speed));
            }

            public void setCaptioningEnabled(boolean enabled) throws RemoteException {
                postToHandler(29, Boolean.valueOf(enabled));
            }

            public void setRepeatMode(int repeatMode) throws RemoteException {
                postToHandler(23, repeatMode);
            }

            public void setShuffleModeEnabledRemoved(boolean enabled) throws RemoteException {
            }

            public void setShuffleMode(int shuffleMode) throws RemoteException {
                postToHandler(30, shuffleMode);
            }

            public void sendCustomAction(String action, Bundle args) throws RemoteException {
                postToHandler(20, action, args);
            }

            public MediaMetadataCompat getMetadata() {
                return MediaSessionImplBase.this.mMetadata;
            }

            public PlaybackStateCompat getPlaybackState() {
                PlaybackStateCompat state;
                MediaMetadataCompat metadata;
                synchronized (MediaSessionImplBase.this.mLock) {
                    state = MediaSessionImplBase.this.mState;
                    metadata = MediaSessionImplBase.this.mMetadata;
                }
                return MediaSessionCompat.getStateWithUpdatedPosition(state, metadata);
            }

            public List<QueueItem> getQueue() {
                List<QueueItem> list;
                synchronized (MediaSessionImplBase.this.mLock) {
                    list = MediaSessionImplBase.this.mQueue;
                }
                return list;
            }

            public void addQueueItem(MediaDescriptionCompat description) {
                postToHandler(25, description);
            }

            public void addQueueItemAt(MediaDescriptionCompat description, int index) {
                postToHandler(26, description, index);
            }

            public void removeQueueItem(MediaDescriptionCompat description) {
                postToHandler(27, description);
            }

            public void removeQueueItemAt(int index) {
                postToHandler(28, index);
            }

            public CharSequence getQueueTitle() {
                return MediaSessionImplBase.this.mQueueTitle;
            }

            public Bundle getExtras() {
                Bundle bundle;
                synchronized (MediaSessionImplBase.this.mLock) {
                    bundle = MediaSessionImplBase.this.mExtras;
                }
                return bundle;
            }

            public int getRatingType() {
                return MediaSessionImplBase.this.mRatingType;
            }

            public boolean isCaptioningEnabled() {
                return MediaSessionImplBase.this.mCaptioningEnabled;
            }

            public int getRepeatMode() {
                return MediaSessionImplBase.this.mRepeatMode;
            }

            public boolean isShuffleModeEnabledRemoved() {
                return false;
            }

            public int getShuffleMode() {
                return MediaSessionImplBase.this.mShuffleMode;
            }

            public boolean isTransportControlEnabled() {
                return true;
            }

            /* access modifiers changed from: package-private */
            public void postToHandler(int what) {
                MediaSessionImplBase.this.postToHandler(what, 0, 0, null, null);
            }

            /* access modifiers changed from: package-private */
            public void postToHandler(int what, int arg1) {
                MediaSessionImplBase.this.postToHandler(what, arg1, 0, null, null);
            }

            /* access modifiers changed from: package-private */
            public void postToHandler(int what, Object obj) {
                MediaSessionImplBase.this.postToHandler(what, 0, 0, obj, null);
            }

            /* access modifiers changed from: package-private */
            public void postToHandler(int what, Object obj, int arg1) {
                MediaSessionImplBase.this.postToHandler(what, arg1, 0, obj, null);
            }

            /* access modifiers changed from: package-private */
            public void postToHandler(int what, Object obj, Bundle extras) {
                MediaSessionImplBase.this.postToHandler(what, 0, 0, obj, extras);
            }
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplBase$Command */
        private static final class Command {
            public final String command;
            public final Bundle extras;
            public final ResultReceiver stub;

            public Command(String command2, Bundle extras2, ResultReceiver stub2) {
                this.command = command2;
                this.extras = extras2;
                this.stub = stub2;
            }
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplBase$MessageHandler */
        class MessageHandler extends Handler {
            private static final int KEYCODE_MEDIA_PAUSE = 127;
            private static final int KEYCODE_MEDIA_PLAY = 126;
            private static final int MSG_ADD_QUEUE_ITEM = 25;
            private static final int MSG_ADD_QUEUE_ITEM_AT = 26;
            private static final int MSG_ADJUST_VOLUME = 2;
            private static final int MSG_COMMAND = 1;
            private static final int MSG_CUSTOM_ACTION = 20;
            private static final int MSG_FAST_FORWARD = 16;
            private static final int MSG_MEDIA_BUTTON = 21;
            private static final int MSG_NEXT = 14;
            private static final int MSG_PAUSE = 12;
            private static final int MSG_PLAY = 7;
            private static final int MSG_PLAY_MEDIA_ID = 8;
            private static final int MSG_PLAY_SEARCH = 9;
            private static final int MSG_PLAY_URI = 10;
            private static final int MSG_PREPARE = 3;
            private static final int MSG_PREPARE_MEDIA_ID = 4;
            private static final int MSG_PREPARE_SEARCH = 5;
            private static final int MSG_PREPARE_URI = 6;
            private static final int MSG_PREVIOUS = 15;
            private static final int MSG_RATE = 19;
            private static final int MSG_RATE_EXTRA = 31;
            private static final int MSG_REMOVE_QUEUE_ITEM = 27;
            private static final int MSG_REMOVE_QUEUE_ITEM_AT = 28;
            private static final int MSG_REWIND = 17;
            private static final int MSG_SEEK_TO = 18;
            private static final int MSG_SET_CAPTIONING_ENABLED = 29;
            private static final int MSG_SET_PLAYBACK_SPEED = 32;
            private static final int MSG_SET_REPEAT_MODE = 23;
            private static final int MSG_SET_SHUFFLE_MODE = 30;
            private static final int MSG_SET_VOLUME = 22;
            private static final int MSG_SKIP_TO_ITEM = 11;
            private static final int MSG_STOP = 13;

            public MessageHandler(Looper looper) {
                super(looper);
            }

            public void handleMessage(Message msg) {
                Callback cb = MediaSessionImplBase.this.mCallback;
                if (cb != null) {
                    Bundle data = msg.getData();
                    MediaSessionCompat.ensureClassLoader(data);
                    MediaSessionImplBase.this.setCurrentControllerInfo(new MediaSessionManager.RemoteUserInfo(data.getString(MediaSessionCompat.DATA_CALLING_PACKAGE), data.getInt("data_calling_pid"), data.getInt("data_calling_uid")));
                    Bundle extras = data.getBundle(MediaSessionCompat.DATA_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(extras);
                    try {
                        switch (msg.what) {
                            case 1:
                                Command cmd = (Command) msg.obj;
                                cb.onCommand(cmd.command, cmd.extras, cmd.stub);
                                break;
                            case 2:
                                MediaSessionImplBase.this.adjustVolume(msg.arg1, 0);
                                break;
                            case 3:
                                cb.onPrepare();
                                break;
                            case 4:
                                cb.onPrepareFromMediaId((String) msg.obj, extras);
                                break;
                            case 5:
                                cb.onPrepareFromSearch((String) msg.obj, extras);
                                break;
                            case 6:
                                cb.onPrepareFromUri((Uri) msg.obj, extras);
                                break;
                            case 7:
                                cb.onPlay();
                                break;
                            case 8:
                                cb.onPlayFromMediaId((String) msg.obj, extras);
                                break;
                            case 9:
                                cb.onPlayFromSearch((String) msg.obj, extras);
                                break;
                            case 10:
                                cb.onPlayFromUri((Uri) msg.obj, extras);
                                break;
                            case 11:
                                cb.onSkipToQueueItem(((Long) msg.obj).longValue());
                                break;
                            case 12:
                                cb.onPause();
                                break;
                            case 13:
                                cb.onStop();
                                break;
                            case 14:
                                cb.onSkipToNext();
                                break;
                            case 15:
                                cb.onSkipToPrevious();
                                break;
                            case 16:
                                cb.onFastForward();
                                break;
                            case 17:
                                cb.onRewind();
                                break;
                            case 18:
                                cb.onSeekTo(((Long) msg.obj).longValue());
                                break;
                            case 19:
                                cb.onSetRating((RatingCompat) msg.obj);
                                break;
                            case 20:
                                cb.onCustomAction((String) msg.obj, extras);
                                break;
                            case 21:
                                KeyEvent keyEvent = (KeyEvent) msg.obj;
                                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                                if (!cb.onMediaButtonEvent(intent)) {
                                    onMediaButtonEvent(keyEvent, cb);
                                    break;
                                }
                                break;
                            case 22:
                                MediaSessionImplBase.this.setVolumeTo(msg.arg1, 0);
                                break;
                            case 23:
                                cb.onSetRepeatMode(msg.arg1);
                                break;
                            case 25:
                                cb.onAddQueueItem((MediaDescriptionCompat) msg.obj);
                                break;
                            case 26:
                                cb.onAddQueueItem((MediaDescriptionCompat) msg.obj, msg.arg1);
                                break;
                            case 27:
                                cb.onRemoveQueueItem((MediaDescriptionCompat) msg.obj);
                                break;
                            case 28:
                                if (MediaSessionImplBase.this.mQueue != null) {
                                    QueueItem item = (msg.arg1 < 0 || msg.arg1 >= MediaSessionImplBase.this.mQueue.size()) ? null : MediaSessionImplBase.this.mQueue.get(msg.arg1);
                                    if (item != null) {
                                        cb.onRemoveQueueItem(item.getDescription());
                                    }
                                    break;
                                }
                                break;
                            case 29:
                                cb.onSetCaptioningEnabled(((Boolean) msg.obj).booleanValue());
                                break;
                            case 30:
                                cb.onSetShuffleMode(msg.arg1);
                                break;
                            case 31:
                                cb.onSetRating((RatingCompat) msg.obj, extras);
                                break;
                            case 32:
                                cb.onSetPlaybackSpeed(((Float) msg.obj).floatValue());
                                break;
                        }
                    } finally {
                        MediaSessionImplBase.this.setCurrentControllerInfo(null);
                    }
                }
            }

            private void onMediaButtonEvent(KeyEvent ke, Callback cb) {
                if (ke != null && ke.getAction() == 0) {
                    long validActions = MediaSessionImplBase.this.mState == null ? 0 : MediaSessionImplBase.this.mState.getActions();
                    int keyCode = ke.getKeyCode();
                    if (keyCode != 79) {
                        if (keyCode != 126) {
                            if (keyCode != 127) {
                                switch (keyCode) {
                                    case 85:
                                        break;
                                    case 86:
                                        if ((1 & validActions) != 0) {
                                            cb.onStop();
                                            return;
                                        }
                                        return;
                                    case 87:
                                        if ((32 & validActions) != 0) {
                                            cb.onSkipToNext();
                                            return;
                                        }
                                        return;
                                    case 88:
                                        if ((16 & validActions) != 0) {
                                            cb.onSkipToPrevious();
                                            return;
                                        }
                                        return;
                                    case 89:
                                        if ((8 & validActions) != 0) {
                                            cb.onRewind();
                                            return;
                                        }
                                        return;
                                    case 90:
                                        if ((64 & validActions) != 0) {
                                            cb.onFastForward();
                                            return;
                                        }
                                        return;
                                    default:
                                        return;
                                }
                            } else if ((2 & validActions) != 0) {
                                cb.onPause();
                                return;
                            } else {
                                return;
                            }
                        } else if ((4 & validActions) != 0) {
                            cb.onPlay();
                            return;
                        } else {
                            return;
                        }
                    }
                    Log.w(MediaSessionCompat.TAG, "KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_HEADSETHOOK are handled already");
                }
            }
        }
    }

    @RequiresApi(18)
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi18 */
    static class MediaSessionImplApi18 extends MediaSessionImplBase {
        private static boolean sIsMbrPendingIntentSupported = true;

        MediaSessionImplApi18(Context context, String tag, ComponentName mbrComponent, PendingIntent mbrIntent, Bundle sessionInfo) {
            super(context, tag, mbrComponent, mbrIntent, sessionInfo);
        }

        public void setCallback(Callback callback, Handler handler) {
            super.setCallback(callback, handler);
            if (callback == null) {
                this.mRcc.setPlaybackPositionUpdateListener(null);
                return;
            }
            this.mRcc.setPlaybackPositionUpdateListener(new RemoteControlClient.OnPlaybackPositionUpdateListener() {
                public void onPlaybackPositionUpdate(long newPositionMs) {
                    MediaSessionImplApi18.this.postToHandler(18, -1, -1, Long.valueOf(newPositionMs), null);
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void setRccState(PlaybackStateCompat state) {
            long position = state.getPosition();
            float speed = state.getPlaybackSpeed();
            long updateTime = state.getLastPositionUpdateTime();
            long currTime = SystemClock.elapsedRealtime();
            if (state.getState() == 3 && position > 0) {
                long diff = 0;
                if (updateTime > 0) {
                    diff = currTime - updateTime;
                    if (speed > 0.0f && speed != 1.0f) {
                        diff = (long) (((float) diff) * speed);
                    }
                }
                position += diff;
            }
            this.mRcc.setPlaybackState(getRccStateFromState(state.getState()), position, speed);
        }

        /* access modifiers changed from: package-private */
        public int getRccTransportControlFlagsFromActions(long actions) {
            int transportControlFlags = super.getRccTransportControlFlagsFromActions(actions);
            if ((256 & actions) != 0) {
                return transportControlFlags | 256;
            }
            return transportControlFlags;
        }

        /* access modifiers changed from: package-private */
        public void registerMediaButtonEventReceiver(PendingIntent mbrIntent, ComponentName mbrComponent) {
            if (sIsMbrPendingIntentSupported) {
                try {
                    this.mAudioManager.registerMediaButtonEventReceiver(mbrIntent);
                } catch (NullPointerException e) {
                    Log.w(MediaSessionCompat.TAG, "Unable to register media button event receiver with PendingIntent, falling back to ComponentName.");
                    sIsMbrPendingIntentSupported = false;
                }
            }
            if (!sIsMbrPendingIntentSupported) {
                super.registerMediaButtonEventReceiver(mbrIntent, mbrComponent);
            }
        }

        /* access modifiers changed from: package-private */
        public void unregisterMediaButtonEventReceiver(PendingIntent mbrIntent, ComponentName mbrComponent) {
            if (sIsMbrPendingIntentSupported) {
                this.mAudioManager.unregisterMediaButtonEventReceiver(mbrIntent);
            } else {
                super.unregisterMediaButtonEventReceiver(mbrIntent, mbrComponent);
            }
        }
    }

    @RequiresApi(19)
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi19 */
    static class MediaSessionImplApi19 extends MediaSessionImplApi18 {
        MediaSessionImplApi19(Context context, String tag, ComponentName mbrComponent, PendingIntent mbrIntent, Bundle sessionInfo) {
            super(context, tag, mbrComponent, mbrIntent, sessionInfo);
        }

        public void setCallback(Callback callback, Handler handler) {
            super.setCallback(callback, handler);
            if (callback == null) {
                this.mRcc.setMetadataUpdateListener(null);
                return;
            }
            this.mRcc.setMetadataUpdateListener(new RemoteControlClient.OnMetadataUpdateListener() {
                public void onMetadataUpdate(int key, Object newValue) {
                    if (key == 268435457 && (newValue instanceof Rating)) {
                        MediaSessionImplApi19.this.postToHandler(19, -1, -1, RatingCompat.fromRating(newValue), null);
                    }
                }
            });
        }

        /* access modifiers changed from: package-private */
        public int getRccTransportControlFlagsFromActions(long actions) {
            int transportControlFlags = super.getRccTransportControlFlagsFromActions(actions);
            if ((128 & actions) != 0) {
                return transportControlFlags | 512;
            }
            return transportControlFlags;
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.media.RemoteControlClient.MetadataEditor.putLong(int, long):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException}
         arg types: [int, long]
         candidates:
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putLong(int, long):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.MediaMetadataEditor.putLong(int, long):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putLong(int, long):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException} */
        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: ClspMth{android.media.MediaMetadataEditor.putObject(int, java.lang.Object):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException}
         arg types: [int, android.os.Parcelable]
         candidates:
          ClspMth{android.media.RemoteControlClient.MetadataEditor.putObject(int, java.lang.Object):android.media.RemoteControlClient$MetadataEditor throws java.lang.IllegalArgumentException}
          ClspMth{android.media.MediaMetadataEditor.putObject(int, java.lang.Object):android.media.MediaMetadataEditor throws java.lang.IllegalArgumentException} */
        /* access modifiers changed from: package-private */
        public RemoteControlClient.MetadataEditor buildRccMetadata(Bundle metadata) {
            RemoteControlClient.MetadataEditor editor = super.buildRccMetadata(metadata);
            if ((128 & (this.mState == null ? 0 : this.mState.getActions())) != 0) {
                editor.addEditableKey(268435457);
            }
            if (metadata == null) {
                return editor;
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_YEAR)) {
                editor.putLong(8, metadata.getLong(MediaMetadataCompat.METADATA_KEY_YEAR));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_RATING)) {
                editor.putObject(101, (Object) metadata.getParcelable(MediaMetadataCompat.METADATA_KEY_RATING));
            }
            if (metadata.containsKey(MediaMetadataCompat.METADATA_KEY_USER_RATING)) {
                editor.putObject(268435457, (Object) metadata.getParcelable(MediaMetadataCompat.METADATA_KEY_USER_RATING));
            }
            return editor;
        }
    }

    @RequiresApi(21)
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi21 */
    static class MediaSessionImplApi21 implements MediaSessionImpl {
        boolean mCaptioningEnabled;
        boolean mDestroyed = false;
        final RemoteCallbackList<IMediaControllerCallback> mExtraControllerCallbacks = new RemoteCallbackList<>();
        final Object mLock = new Object();
        MediaMetadataCompat mMetadata;
        PlaybackStateCompat mPlaybackState;
        List<QueueItem> mQueue;
        int mRatingType;
        @GuardedBy("mLock")
        MediaSessionManager.RemoteUserInfo mRemoteUserInfo;
        int mRepeatMode;
        final MediaSession mSessionFwk;
        final Bundle mSessionInfo;
        int mShuffleMode;
        final Token mToken;

        MediaSessionImplApi21(Context context, String tag, VersionedParcelable session2Token, Bundle sessionInfo) {
            this.mSessionFwk = new MediaSession(context, tag);
            this.mToken = new Token(this.mSessionFwk.getSessionToken(), new ExtraSession(), session2Token);
            this.mSessionInfo = sessionInfo;
            setFlags(3);
        }

        MediaSessionImplApi21(Object mediaSession) {
            if (mediaSession instanceof MediaSession) {
                this.mSessionFwk = (MediaSession) mediaSession;
                this.mToken = new Token(this.mSessionFwk.getSessionToken(), new ExtraSession());
                this.mSessionInfo = null;
                setFlags(3);
                return;
            }
            throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
        }

        public void setCallback(Callback callback, Handler handler) {
            this.mSessionFwk.setCallback(callback == null ? null : callback.mCallbackFwk, handler);
            if (callback != null) {
                callback.setSessionImpl(this, handler);
            }
        }

        @SuppressLint({"WrongConstant"})
        public void setFlags(int flags) {
            this.mSessionFwk.setFlags(flags | 1 | 2);
        }

        public void setPlaybackToLocal(int stream) {
            AudioAttributes.Builder bob = new AudioAttributes.Builder();
            bob.setLegacyStreamType(stream);
            this.mSessionFwk.setPlaybackToLocal(bob.build());
        }

        public void setPlaybackToRemote(VolumeProviderCompat volumeProvider) {
            this.mSessionFwk.setPlaybackToRemote((VolumeProvider) volumeProvider.getVolumeProvider());
        }

        public void setActive(boolean active) {
            this.mSessionFwk.setActive(active);
        }

        public boolean isActive() {
            return this.mSessionFwk.isActive();
        }

        public void sendSessionEvent(String event, Bundle extras) {
            if (Build.VERSION.SDK_INT < 23) {
                for (int i = this.mExtraControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                    try {
                        this.mExtraControllerCallbacks.getBroadcastItem(i).onEvent(event, extras);
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast();
            }
            this.mSessionFwk.sendSessionEvent(event, extras);
        }

        public void release() {
            this.mDestroyed = true;
            this.mSessionFwk.release();
        }

        public Token getSessionToken() {
            return this.mToken;
        }

        public void setPlaybackState(PlaybackStateCompat state) {
            PlaybackState playbackState;
            this.mPlaybackState = state;
            for (int i = this.mExtraControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                try {
                    this.mExtraControllerCallbacks.getBroadcastItem(i).onPlaybackStateChanged(state);
                } catch (RemoteException e) {
                }
            }
            this.mExtraControllerCallbacks.finishBroadcast();
            MediaSession mediaSession = this.mSessionFwk;
            if (state == null) {
                playbackState = null;
            } else {
                playbackState = (PlaybackState) state.getPlaybackState();
            }
            mediaSession.setPlaybackState(playbackState);
        }

        public PlaybackStateCompat getPlaybackState() {
            return this.mPlaybackState;
        }

        public void setMetadata(MediaMetadataCompat metadata) {
            MediaMetadata mediaMetadata;
            this.mMetadata = metadata;
            MediaSession mediaSession = this.mSessionFwk;
            if (metadata == null) {
                mediaMetadata = null;
            } else {
                mediaMetadata = (MediaMetadata) metadata.getMediaMetadata();
            }
            mediaSession.setMetadata(mediaMetadata);
        }

        public void setSessionActivity(PendingIntent pi) {
            this.mSessionFwk.setSessionActivity(pi);
        }

        public void setMediaButtonReceiver(PendingIntent mbr) {
            this.mSessionFwk.setMediaButtonReceiver(mbr);
        }

        public void setQueue(List<QueueItem> queue) {
            this.mQueue = queue;
            if (queue == null) {
                this.mSessionFwk.setQueue(null);
                return;
            }
            ArrayList<MediaSession.QueueItem> queueItemFwks = new ArrayList<>();
            for (QueueItem item : queue) {
                queueItemFwks.add((MediaSession.QueueItem) item.getQueueItem());
            }
            this.mSessionFwk.setQueue(queueItemFwks);
        }

        public void setQueueTitle(CharSequence title) {
            this.mSessionFwk.setQueueTitle(title);
        }

        public void setRatingType(int type) {
            if (Build.VERSION.SDK_INT < 22) {
                this.mRatingType = type;
            } else {
                this.mSessionFwk.setRatingType(type);
            }
        }

        public void setCaptioningEnabled(boolean enabled) {
            if (this.mCaptioningEnabled != enabled) {
                this.mCaptioningEnabled = enabled;
                for (int i = this.mExtraControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                    try {
                        this.mExtraControllerCallbacks.getBroadcastItem(i).onCaptioningEnabledChanged(enabled);
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast();
            }
        }

        public void setRepeatMode(int repeatMode) {
            if (this.mRepeatMode != repeatMode) {
                this.mRepeatMode = repeatMode;
                for (int i = this.mExtraControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                    try {
                        this.mExtraControllerCallbacks.getBroadcastItem(i).onRepeatModeChanged(repeatMode);
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast();
            }
        }

        public void setShuffleMode(int shuffleMode) {
            if (this.mShuffleMode != shuffleMode) {
                this.mShuffleMode = shuffleMode;
                for (int i = this.mExtraControllerCallbacks.beginBroadcast() - 1; i >= 0; i--) {
                    try {
                        this.mExtraControllerCallbacks.getBroadcastItem(i).onShuffleModeChanged(shuffleMode);
                    } catch (RemoteException e) {
                    }
                }
                this.mExtraControllerCallbacks.finishBroadcast();
            }
        }

        public void setExtras(Bundle extras) {
            this.mSessionFwk.setExtras(extras);
        }

        public Object getMediaSession() {
            return this.mSessionFwk;
        }

        public Object getRemoteControlClient() {
            return null;
        }

        public void setCurrentControllerInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
            synchronized (this.mLock) {
                this.mRemoteUserInfo = remoteUserInfo;
            }
        }

        public String getCallingPackage() {
            if (Build.VERSION.SDK_INT < 24) {
                return null;
            }
            try {
                return (String) this.mSessionFwk.getClass().getMethod("getCallingPackage", new Class[0]).invoke(this.mSessionFwk, new Object[0]);
            } catch (Exception e) {
                Log.e(MediaSessionCompat.TAG, "Cannot execute MediaSession.getCallingPackage()", e);
                return null;
            }
        }

        public MediaSessionManager.RemoteUserInfo getCurrentControllerInfo() {
            MediaSessionManager.RemoteUserInfo remoteUserInfo;
            synchronized (this.mLock) {
                remoteUserInfo = this.mRemoteUserInfo;
            }
            return remoteUserInfo;
        }

        /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi21$ExtraSession */
        class ExtraSession extends IMediaSession.Stub {
            ExtraSession() {
            }

            public void sendCommand(String command, Bundle args, ResultReceiverWrapper cb) {
                throw new AssertionError();
            }

            public boolean sendMediaButton(KeyEvent mediaButton) {
                throw new AssertionError();
            }

            public void registerCallbackListener(IMediaControllerCallback cb) {
                if (!MediaSessionImplApi21.this.mDestroyed) {
                    MediaSessionImplApi21.this.mExtraControllerCallbacks.register(cb, new MediaSessionManager.RemoteUserInfo(MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER, getCallingPid(), getCallingUid()));
                }
            }

            public void unregisterCallbackListener(IMediaControllerCallback cb) {
                MediaSessionImplApi21.this.mExtraControllerCallbacks.unregister(cb);
            }

            public String getPackageName() {
                throw new AssertionError();
            }

            public Bundle getSessionInfo() {
                if (MediaSessionImplApi21.this.mSessionInfo == null) {
                    return null;
                }
                return new Bundle(MediaSessionImplApi21.this.mSessionInfo);
            }

            public String getTag() {
                throw new AssertionError();
            }

            public PendingIntent getLaunchPendingIntent() {
                throw new AssertionError();
            }

            public long getFlags() {
                throw new AssertionError();
            }

            public ParcelableVolumeInfo getVolumeAttributes() {
                throw new AssertionError();
            }

            public void adjustVolume(int direction, int flags, String packageName) {
                throw new AssertionError();
            }

            public void setVolumeTo(int value, int flags, String packageName) {
                throw new AssertionError();
            }

            public void prepare() throws RemoteException {
                throw new AssertionError();
            }

            public void prepareFromMediaId(String mediaId, Bundle extras) throws RemoteException {
                throw new AssertionError();
            }

            public void prepareFromSearch(String query, Bundle extras) throws RemoteException {
                throw new AssertionError();
            }

            public void prepareFromUri(Uri uri, Bundle extras) throws RemoteException {
                throw new AssertionError();
            }

            public void play() throws RemoteException {
                throw new AssertionError();
            }

            public void playFromMediaId(String mediaId, Bundle extras) throws RemoteException {
                throw new AssertionError();
            }

            public void playFromSearch(String query, Bundle extras) throws RemoteException {
                throw new AssertionError();
            }

            public void playFromUri(Uri uri, Bundle extras) throws RemoteException {
                throw new AssertionError();
            }

            public void skipToQueueItem(long id) {
                throw new AssertionError();
            }

            public void pause() throws RemoteException {
                throw new AssertionError();
            }

            public void stop() throws RemoteException {
                throw new AssertionError();
            }

            public void next() throws RemoteException {
                throw new AssertionError();
            }

            public void previous() throws RemoteException {
                throw new AssertionError();
            }

            public void fastForward() throws RemoteException {
                throw new AssertionError();
            }

            public void rewind() throws RemoteException {
                throw new AssertionError();
            }

            public void seekTo(long pos) throws RemoteException {
                throw new AssertionError();
            }

            public void rate(RatingCompat rating) throws RemoteException {
                throw new AssertionError();
            }

            public void rateWithExtras(RatingCompat rating, Bundle extras) throws RemoteException {
                throw new AssertionError();
            }

            public void setPlaybackSpeed(float speed) throws RemoteException {
                throw new AssertionError();
            }

            public void setCaptioningEnabled(boolean enabled) throws RemoteException {
                throw new AssertionError();
            }

            public void setRepeatMode(int repeatMode) throws RemoteException {
                throw new AssertionError();
            }

            public void setShuffleModeEnabledRemoved(boolean enabled) throws RemoteException {
            }

            public void setShuffleMode(int shuffleMode) throws RemoteException {
                throw new AssertionError();
            }

            public void sendCustomAction(String action, Bundle args) throws RemoteException {
                throw new AssertionError();
            }

            public MediaMetadataCompat getMetadata() {
                throw new AssertionError();
            }

            public PlaybackStateCompat getPlaybackState() {
                return MediaSessionCompat.getStateWithUpdatedPosition(MediaSessionImplApi21.this.mPlaybackState, MediaSessionImplApi21.this.mMetadata);
            }

            public List<QueueItem> getQueue() {
                return null;
            }

            public void addQueueItem(MediaDescriptionCompat descriptionCompat) {
                throw new AssertionError();
            }

            public void addQueueItemAt(MediaDescriptionCompat descriptionCompat, int index) {
                throw new AssertionError();
            }

            public void removeQueueItem(MediaDescriptionCompat description) {
                throw new AssertionError();
            }

            public void removeQueueItemAt(int index) {
                throw new AssertionError();
            }

            public CharSequence getQueueTitle() {
                throw new AssertionError();
            }

            public Bundle getExtras() {
                throw new AssertionError();
            }

            public int getRatingType() {
                return MediaSessionImplApi21.this.mRatingType;
            }

            public boolean isCaptioningEnabled() {
                return MediaSessionImplApi21.this.mCaptioningEnabled;
            }

            public int getRepeatMode() {
                return MediaSessionImplApi21.this.mRepeatMode;
            }

            public boolean isShuffleModeEnabledRemoved() {
                return false;
            }

            public int getShuffleMode() {
                return MediaSessionImplApi21.this.mShuffleMode;
            }

            public boolean isTransportControlEnabled() {
                throw new AssertionError();
            }
        }
    }

    @RequiresApi(28)
    /* renamed from: android.support.v4.media.session.MediaSessionCompat$MediaSessionImplApi28 */
    static class MediaSessionImplApi28 extends MediaSessionImplApi21 {
        MediaSessionImplApi28(Context context, String tag, VersionedParcelable session2Token, Bundle sessionInfo) {
            super(context, tag, session2Token, sessionInfo);
        }

        MediaSessionImplApi28(Object mediaSession) {
            super(mediaSession);
        }

        public void setCurrentControllerInfo(MediaSessionManager.RemoteUserInfo remoteUserInfo) {
        }

        @NonNull
        public final MediaSessionManager.RemoteUserInfo getCurrentControllerInfo() {
            return new MediaSessionManager.RemoteUserInfo(this.mSessionFwk.getCurrentControllerInfo());
        }
    }
}
