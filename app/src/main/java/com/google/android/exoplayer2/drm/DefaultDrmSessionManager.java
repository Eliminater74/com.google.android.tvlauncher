package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EventDispatcher;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@TargetApi(18)
public class DefaultDrmSessionManager<T extends ExoMediaCrypto> implements DrmSessionManager<T>, DefaultDrmSession.ProvisioningManager<T> {
    public static final int INITIAL_DRM_REQUEST_RETRY_COUNT = 3;
    public static final int MODE_DOWNLOAD = 2;
    public static final int MODE_PLAYBACK = 0;
    public static final int MODE_QUERY = 1;
    public static final int MODE_RELEASE = 3;
    public static final String PLAYREADY_CUSTOM_DATA_KEY = "PRCustomData";
    private static final String TAG = "DefaultDrmSessionMgr";
    /* access modifiers changed from: private */
    public final List<DefaultDrmSession<T>> sessions;
    private final MediaDrmCallback callback;
    private final EventDispatcher<DefaultDrmSessionEventListener> eventDispatcher;
    private final int initialDrmRequestRetryCount;
    private final ExoMediaDrm<T> mediaDrm;
    private final boolean multiSession;
    @Nullable
    private final HashMap<String, String> optionalKeyRequestParameters;
    private final List<DefaultDrmSession<T>> provisioningSessions;
    private final UUID uuid;
    @Nullable
    volatile DefaultDrmSessionManager<T>.MediaDrmHandler mediaDrmHandler;
    private int mode;
    @Nullable
    private byte[] offlineLicenseKeySetId;
    @Nullable
    private Looper playbackLooper;

    public DefaultDrmSessionManager(UUID uuid2, ExoMediaDrm<T> mediaDrm2, MediaDrmCallback callback2, @Nullable HashMap<String, String> optionalKeyRequestParameters2) {
        this(uuid2, mediaDrm2, callback2, optionalKeyRequestParameters2, false, 3);
    }

    public DefaultDrmSessionManager(UUID uuid2, ExoMediaDrm<T> mediaDrm2, MediaDrmCallback callback2, @Nullable HashMap<String, String> optionalKeyRequestParameters2, boolean multiSession2) {
        this(uuid2, mediaDrm2, callback2, optionalKeyRequestParameters2, multiSession2, 3);
    }

    public DefaultDrmSessionManager(UUID uuid2, ExoMediaDrm<T> mediaDrm2, MediaDrmCallback callback2, @Nullable HashMap<String, String> optionalKeyRequestParameters2, boolean multiSession2, int initialDrmRequestRetryCount2) {
        Assertions.checkNotNull(uuid2);
        Assertions.checkNotNull(mediaDrm2);
        Assertions.checkArgument(!C0841C.COMMON_PSSH_UUID.equals(uuid2), "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid2;
        this.mediaDrm = mediaDrm2;
        this.callback = callback2;
        this.optionalKeyRequestParameters = optionalKeyRequestParameters2;
        this.eventDispatcher = new EventDispatcher<>();
        this.multiSession = multiSession2;
        this.initialDrmRequestRetryCount = initialDrmRequestRetryCount2;
        this.mode = 0;
        this.sessions = new ArrayList();
        this.provisioningSessions = new ArrayList();
        if (multiSession2 && C0841C.WIDEVINE_UUID.equals(uuid2) && Util.SDK_INT >= 19) {
            mediaDrm2.setPropertyString("sessionSharing", "enable");
        }
        mediaDrm2.setOnEventListener(new MediaDrmEventListener());
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newWidevineInstance(MediaDrmCallback callback2, @Nullable HashMap<String, String> optionalKeyRequestParameters2) throws UnsupportedDrmException {
        return newFrameworkInstance(C0841C.WIDEVINE_UUID, callback2, optionalKeyRequestParameters2);
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newPlayReadyInstance(MediaDrmCallback callback2, @Nullable String customData) throws UnsupportedDrmException {
        HashMap<String, String> optionalKeyRequestParameters2;
        if (!TextUtils.isEmpty(customData)) {
            optionalKeyRequestParameters2 = new HashMap<>();
            optionalKeyRequestParameters2.put(PLAYREADY_CUSTOM_DATA_KEY, customData);
        } else {
            optionalKeyRequestParameters2 = null;
        }
        return newFrameworkInstance(C0841C.PLAYREADY_UUID, callback2, optionalKeyRequestParameters2);
    }

    public static DefaultDrmSessionManager<FrameworkMediaCrypto> newFrameworkInstance(UUID uuid2, MediaDrmCallback callback2, @Nullable HashMap<String, String> optionalKeyRequestParameters2) throws UnsupportedDrmException {
        return new DefaultDrmSessionManager(uuid2, FrameworkMediaDrm.newInstance(uuid2), callback2, optionalKeyRequestParameters2, false, 3);
    }

    private static List<DrmInitData.SchemeData> getSchemeDatas(DrmInitData drmInitData, UUID uuid2, boolean allowMissingData) {
        List<DrmInitData.SchemeData> matchingSchemeDatas = new ArrayList<>(drmInitData.schemeDataCount);
        for (int i = 0; i < drmInitData.schemeDataCount; i++) {
            DrmInitData.SchemeData schemeData = drmInitData.get(i);
            if ((schemeData.matches(uuid2) || (C0841C.CLEARKEY_UUID.equals(uuid2) && schemeData.matches(C0841C.COMMON_PSSH_UUID))) && (schemeData.data != null || allowMissingData)) {
                matchingSchemeDatas.add(schemeData);
            }
        }
        return matchingSchemeDatas;
    }

    public final void addListener(Handler handler, DefaultDrmSessionEventListener eventListener) {
        this.eventDispatcher.addListener(handler, eventListener);
    }

    public final void removeListener(DefaultDrmSessionEventListener eventListener) {
        this.eventDispatcher.removeListener(eventListener);
    }

    public final String getPropertyString(String key) {
        return this.mediaDrm.getPropertyString(key);
    }

    public final void setPropertyString(String key, String value) {
        this.mediaDrm.setPropertyString(key, value);
    }

    public final byte[] getPropertyByteArray(String key) {
        return this.mediaDrm.getPropertyByteArray(key);
    }

    public final void setPropertyByteArray(String key, byte[] value) {
        this.mediaDrm.setPropertyByteArray(key, value);
    }

    public void setMode(int mode2, @Nullable byte[] offlineLicenseKeySetId2) {
        Assertions.checkState(this.sessions.isEmpty());
        if (mode2 == 1 || mode2 == 3) {
            Assertions.checkNotNull(offlineLicenseKeySetId2);
        }
        this.mode = mode2;
        this.offlineLicenseKeySetId = offlineLicenseKeySetId2;
    }

    public boolean canAcquireSession(DrmInitData drmInitData) {
        if (this.offlineLicenseKeySetId != null) {
            return true;
        }
        if (getSchemeDatas(drmInitData, this.uuid, true).isEmpty()) {
            if (drmInitData.schemeDataCount != 1 || !drmInitData.get(0).matches(C0841C.COMMON_PSSH_UUID)) {
                return false;
            }
            String valueOf = String.valueOf(this.uuid);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 72);
            sb.append("DrmInitData only contains common PSSH SchemeData. Assuming support for: ");
            sb.append(valueOf);
            Log.m30w(TAG, sb.toString());
        }
        String schemeType = drmInitData.schemeType;
        if (schemeType == null || C0841C.CENC_TYPE_cenc.equals(schemeType)) {
            return true;
        }
        if ((C0841C.CENC_TYPE_cbc1.equals(schemeType) || C0841C.CENC_TYPE_cbcs.equals(schemeType) || C0841C.CENC_TYPE_cens.equals(schemeType)) && Util.SDK_INT < 25) {
            return false;
        }
        return true;
    }

    public DrmSession<T> acquireSession(Looper playbackLooper2, DrmInitData drmInitData) {
        List<DrmInitData.SchemeData> schemeDatas;
        DefaultDrmSession<T> session;
        Looper looper = playbackLooper2;
        Looper looper2 = this.playbackLooper;
        Assertions.checkState(looper2 == null || looper2 == looper);
        if (this.sessions.isEmpty()) {
            this.playbackLooper = looper;
            if (this.mediaDrmHandler == null) {
                this.mediaDrmHandler = new MediaDrmHandler(looper);
            }
        }
        DefaultDrmSession<T> defaultDrmSession = null;
        if (this.offlineLicenseKeySetId == null) {
            List<DrmInitData.SchemeData> schemeDatas2 = getSchemeDatas(drmInitData, this.uuid, false);
            if (schemeDatas2.isEmpty()) {
                MissingSchemeDataException error = new MissingSchemeDataException(this.uuid);
                this.eventDispatcher.dispatch(new DefaultDrmSessionManager$$Lambda$0(error));
                return new ErrorStateDrmSession(new DrmSession.DrmSessionException(error));
            }
            schemeDatas = schemeDatas2;
        } else {
            schemeDatas = null;
        }
        if (this.multiSession) {
            Iterator<DefaultDrmSession<T>> it = this.sessions.iterator();
            while (true) {
                if (!it.hasNext()) {
                    session = null;
                    break;
                }
                DefaultDrmSession<T> existingSession = it.next();
                if (Util.areEqual(existingSession.schemeDatas, schemeDatas)) {
                    session = existingSession;
                    break;
                }
            }
        } else {
            if (!this.sessions.isEmpty()) {
                defaultDrmSession = this.sessions.get(0);
            }
            session = defaultDrmSession;
        }
        if (session == null) {
            DefaultDrmSession<T> session2 = new DefaultDrmSession<>(this.uuid, this.mediaDrm, this, schemeDatas, this.mode, this.offlineLicenseKeySetId, this.optionalKeyRequestParameters, this.callback, playbackLooper2, this.eventDispatcher, this.initialDrmRequestRetryCount);
            this.sessions.add(session2);
            session = session2;
        }
        session.acquire();
        return session;
    }

    public void releaseSession(DrmSession<T> session) {
        if (!(session instanceof ErrorStateDrmSession)) {
            DefaultDrmSession<T> drmSession = (DefaultDrmSession) session;
            if (drmSession.release()) {
                this.sessions.remove(drmSession);
                if (this.provisioningSessions.size() > 1 && this.provisioningSessions.get(0) == drmSession) {
                    this.provisioningSessions.get(1).provision();
                }
                this.provisioningSessions.remove(drmSession);
            }
        }
    }

    public void provisionRequired(DefaultDrmSession<T> session) {
        if (!this.provisioningSessions.contains(session)) {
            this.provisioningSessions.add(session);
            if (this.provisioningSessions.size() == 1) {
                session.provision();
            }
        }
    }

    public void onProvisionCompleted() {
        for (DefaultDrmSession<T> session : this.provisioningSessions) {
            session.onProvisionCompleted();
        }
        this.provisioningSessions.clear();
    }

    public void onProvisionError(Exception error) {
        for (DefaultDrmSession<T> session : this.provisioningSessions) {
            session.onProvisionError(error);
        }
        this.provisioningSessions.clear();
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    public static final class MissingSchemeDataException extends Exception {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private MissingSchemeDataException(java.util.UUID r4) {
            /*
                r3 = this;
                java.lang.String r0 = java.lang.String.valueOf(r4)
                java.lang.String r1 = java.lang.String.valueOf(r0)
                int r1 = r1.length()
                int r1 = r1 + 29
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>(r1)
                java.lang.String r1 = "Media does not support uuid: "
                r2.append(r1)
                r2.append(r0)
                java.lang.String r0 = r2.toString()
                r3.<init>(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.drm.DefaultDrmSessionManager.MissingSchemeDataException.<init>(java.util.UUID):void");
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class MediaDrmHandler extends Handler {
        public MediaDrmHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            byte[] sessionId = (byte[]) msg.obj;
            if (sessionId != null) {
                for (DefaultDrmSession<T> session : DefaultDrmSessionManager.this.sessions) {
                    if (session.hasSessionId(sessionId)) {
                        session.onMediaDrmEvent(msg.what);
                        return;
                    }
                }
            }
        }
    }

    private class MediaDrmEventListener implements ExoMediaDrm.OnEventListener<T> {
        private MediaDrmEventListener() {
        }

        public void onEvent(ExoMediaDrm<? extends T> exoMediaDrm, byte[] sessionId, int event, int extra, @Nullable byte[] data) {
            ((MediaDrmHandler) Assertions.checkNotNull(DefaultDrmSessionManager.this.mediaDrmHandler)).obtainMessage(event, sessionId).sendToTarget();
        }
    }
}
