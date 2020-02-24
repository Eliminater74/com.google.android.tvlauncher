package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.NotProvisionedException;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.EventDispatcher;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.checkerframework.checker.nullness.qual.EnsuresNonNullIf;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

@TargetApi(18)
class DefaultDrmSession<T extends ExoMediaCrypto> implements DrmSession<T> {
    private static final int MAX_LICENSE_DURATION_TO_RENEW = 60;
    private static final int MSG_KEYS = 1;
    private static final int MSG_PROVISION = 0;
    private static final String TAG = "DefaultDrmSession";
    final MediaDrmCallback callback;
    @Nullable
    private ExoMediaDrm.KeyRequest currentKeyRequest;
    @Nullable
    private ExoMediaDrm.ProvisionRequest currentProvisionRequest;
    private final EventDispatcher<DefaultDrmSessionEventListener> eventDispatcher;
    /* access modifiers changed from: private */
    public final int initialDrmRequestRetryCount;
    @Nullable
    private DrmSession.DrmSessionException lastException;
    @Nullable
    private T mediaCrypto;
    private final ExoMediaDrm<T> mediaDrm;
    private final int mode;
    private byte[] offlineLicenseKeySetId;
    private int openCount;
    @Nullable
    private final HashMap<String, String> optionalKeyRequestParameters;
    private DefaultDrmSession<T>.PostRequestHandler postRequestHandler;
    final DefaultDrmSession<T>.PostResponseHandler postResponseHandler;
    private final ProvisioningManager<T> provisioningManager;
    private HandlerThread requestHandlerThread;
    @Nullable
    public final List<DrmInitData.SchemeData> schemeDatas;
    private byte[] sessionId;
    private int state;
    final UUID uuid;

    public interface ProvisioningManager<T extends ExoMediaCrypto> {
        void onProvisionCompleted();

        void onProvisionError(Exception exc);

        void provisionRequired(DefaultDrmSession<T> defaultDrmSession);
    }

    public DefaultDrmSession(UUID uuid2, ExoMediaDrm<T> mediaDrm2, ProvisioningManager<T> provisioningManager2, @Nullable List<DrmInitData.SchemeData> schemeDatas2, int mode2, @Nullable byte[] offlineLicenseKeySetId2, @Nullable HashMap<String, String> optionalKeyRequestParameters2, MediaDrmCallback callback2, Looper playbackLooper, EventDispatcher<DefaultDrmSessionEventListener> eventDispatcher2, int initialDrmRequestRetryCount2) {
        if (mode2 == 1 || mode2 == 3) {
            Assertions.checkNotNull(offlineLicenseKeySetId2);
        }
        this.uuid = uuid2;
        this.provisioningManager = provisioningManager2;
        this.mediaDrm = mediaDrm2;
        this.mode = mode2;
        if (offlineLicenseKeySetId2 != null) {
            this.offlineLicenseKeySetId = offlineLicenseKeySetId2;
            this.schemeDatas = null;
        } else {
            this.schemeDatas = Collections.unmodifiableList((List) Assertions.checkNotNull(schemeDatas2));
        }
        this.optionalKeyRequestParameters = optionalKeyRequestParameters2;
        this.callback = callback2;
        this.initialDrmRequestRetryCount = initialDrmRequestRetryCount2;
        this.eventDispatcher = eventDispatcher2;
        this.state = 2;
        this.postResponseHandler = new PostResponseHandler(playbackLooper);
        this.requestHandlerThread = new HandlerThread("DrmRequestHandler");
        this.requestHandlerThread.start();
        this.postRequestHandler = new PostRequestHandler(this.requestHandlerThread.getLooper());
    }

    public void acquire() {
        int i = this.openCount + 1;
        this.openCount = i;
        if (i == 1 && this.state != 1 && openInternal(true)) {
            doLicense(true);
        }
    }

    public boolean release() {
        int i = this.openCount - 1;
        this.openCount = i;
        if (i != 0) {
            return false;
        }
        this.state = 0;
        this.postResponseHandler.removeCallbacksAndMessages(null);
        this.postRequestHandler.removeCallbacksAndMessages(null);
        this.postRequestHandler = null;
        this.requestHandlerThread.quit();
        this.requestHandlerThread = null;
        this.mediaCrypto = null;
        this.lastException = null;
        this.currentKeyRequest = null;
        this.currentProvisionRequest = null;
        byte[] bArr = this.sessionId;
        if (bArr != null) {
            this.mediaDrm.closeSession(bArr);
            this.sessionId = null;
            this.eventDispatcher.dispatch(DefaultDrmSession$$Lambda$0.$instance);
        }
        return true;
    }

    public boolean hasSessionId(byte[] sessionId2) {
        return Arrays.equals(this.sessionId, sessionId2);
    }

    public void onMediaDrmEvent(int what) {
        if (what == 2) {
            onKeysRequired();
        }
    }

    public void provision() {
        this.currentProvisionRequest = this.mediaDrm.getProvisionRequest();
        this.postRequestHandler.post(0, this.currentProvisionRequest, true);
    }

    public void onProvisionCompleted() {
        if (openInternal(false)) {
            doLicense(true);
        }
    }

    public void onProvisionError(Exception error) {
        onError(error);
    }

    public final int getState() {
        return this.state;
    }

    @Nullable
    public final DrmSession.DrmSessionException getError() {
        if (this.state == 1) {
            return this.lastException;
        }
        return null;
    }

    @Nullable
    public final T getMediaCrypto() {
        return this.mediaCrypto;
    }

    @Nullable
    public Map<String, String> queryKeyStatus() {
        byte[] bArr = this.sessionId;
        if (bArr == null) {
            return null;
        }
        return this.mediaDrm.queryKeyStatus(bArr);
    }

    @Nullable
    public byte[] getOfflineLicenseKeySetId() {
        return this.offlineLicenseKeySetId;
    }

    @EnsuresNonNullIf(expression = {"sessionId"}, result = true)
    private boolean openInternal(boolean allowProvisioning) {
        if (isOpen()) {
            return true;
        }
        try {
            this.sessionId = this.mediaDrm.openSession();
            this.eventDispatcher.dispatch(DefaultDrmSession$$Lambda$1.$instance);
            this.mediaCrypto = this.mediaDrm.createMediaCrypto(this.sessionId);
            this.state = 3;
            return true;
        } catch (NotProvisionedException e) {
            if (allowProvisioning) {
                this.provisioningManager.provisionRequired(this);
                return false;
            }
            onError(e);
            return false;
        } catch (Exception e2) {
            onError(e2);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void onProvisionResponse(Object request, Object response) {
        if (request != this.currentProvisionRequest) {
            return;
        }
        if (this.state == 2 || isOpen()) {
            this.currentProvisionRequest = null;
            if (response instanceof Exception) {
                this.provisioningManager.onProvisionError((Exception) response);
                return;
            }
            try {
                this.mediaDrm.provideProvisionResponse((byte[]) response);
                this.provisioningManager.onProvisionCompleted();
            } catch (Exception e) {
                this.provisioningManager.onProvisionError(e);
            }
        }
    }

    @RequiresNonNull({"sessionId"})
    private void doLicense(boolean allowRetry) {
        int i = this.mode;
        if (i == 0 || i == 1) {
            if (this.offlineLicenseKeySetId == null) {
                postKeyRequest(this.sessionId, 1, allowRetry);
            } else if (this.state == 4 || restoreKeys()) {
                long licenseDurationRemainingSec = getLicenseDurationRemainingSec();
                if (this.mode == 0 && licenseDurationRemainingSec <= 60) {
                    StringBuilder sb = new StringBuilder(88);
                    sb.append("Offline license has expired or will expire soon. Remaining seconds: ");
                    sb.append(licenseDurationRemainingSec);
                    Log.m24d(TAG, sb.toString());
                    postKeyRequest(this.sessionId, 2, allowRetry);
                } else if (licenseDurationRemainingSec <= 0) {
                    onError(new KeysExpiredException());
                } else {
                    this.state = 4;
                    this.eventDispatcher.dispatch(DefaultDrmSession$$Lambda$2.$instance);
                }
            }
        } else if (i != 2) {
            if (i == 3) {
                Assertions.checkNotNull(this.offlineLicenseKeySetId);
                if (restoreKeys()) {
                    postKeyRequest(this.offlineLicenseKeySetId, 3, allowRetry);
                }
            }
        } else if (this.offlineLicenseKeySetId == null) {
            postKeyRequest(this.sessionId, 2, allowRetry);
        } else if (restoreKeys()) {
            postKeyRequest(this.sessionId, 2, allowRetry);
        }
    }

    @RequiresNonNull({"sessionId", "offlineLicenseKeySetId"})
    private boolean restoreKeys() {
        try {
            this.mediaDrm.restoreKeys(this.sessionId, this.offlineLicenseKeySetId);
            return true;
        } catch (Exception e) {
            Log.m27e(TAG, "Error trying to restore Widevine keys.", e);
            onError(e);
            return false;
        }
    }

    private long getLicenseDurationRemainingSec() {
        if (!C0841C.WIDEVINE_UUID.equals(this.uuid)) {
            return Long.MAX_VALUE;
        }
        Pair<Long, Long> pair = (Pair) Assertions.checkNotNull(WidevineUtil.getLicenseDurationRemainingSec(this));
        return Math.min(((Long) pair.first).longValue(), ((Long) pair.second).longValue());
    }

    private void postKeyRequest(byte[] scope, int type, boolean allowRetry) {
        try {
            this.currentKeyRequest = this.mediaDrm.getKeyRequest(scope, this.schemeDatas, type, this.optionalKeyRequestParameters);
            this.postRequestHandler.post(1, this.currentKeyRequest, allowRetry);
        } catch (Exception e) {
            onKeysError(e);
        }
    }

    /* access modifiers changed from: private */
    public void onKeyResponse(Object request, Object response) {
        if (request == this.currentKeyRequest && isOpen()) {
            this.currentKeyRequest = null;
            if (response instanceof Exception) {
                onKeysError((Exception) response);
                return;
            }
            try {
                byte[] responseData = (byte[]) response;
                if (this.mode == 3) {
                    this.mediaDrm.provideKeyResponse((byte[]) Util.castNonNull(this.offlineLicenseKeySetId), responseData);
                    this.eventDispatcher.dispatch(DefaultDrmSession$$Lambda$3.$instance);
                    return;
                }
                byte[] keySetId = this.mediaDrm.provideKeyResponse(this.sessionId, responseData);
                if (!((this.mode != 2 && (this.mode != 0 || this.offlineLicenseKeySetId == null)) || keySetId == null || keySetId.length == 0)) {
                    this.offlineLicenseKeySetId = keySetId;
                }
                this.state = 4;
                this.eventDispatcher.dispatch(DefaultDrmSession$$Lambda$4.$instance);
            } catch (Exception e) {
                onKeysError(e);
            }
        }
    }

    private void onKeysRequired() {
        if (this.mode == 0 && this.state == 4) {
            Util.castNonNull(this.sessionId);
            doLicense(false);
        }
    }

    private void onKeysError(Exception e) {
        if (e instanceof NotProvisionedException) {
            this.provisioningManager.provisionRequired(this);
        } else {
            onError(e);
        }
    }

    private void onError(Exception e) {
        this.lastException = new DrmSession.DrmSessionException(e);
        this.eventDispatcher.dispatch(new DefaultDrmSession$$Lambda$5(e));
        if (this.state != 4) {
            this.state = 1;
        }
    }

    @EnsuresNonNullIf(expression = {"sessionId"}, result = true)
    private boolean isOpen() {
        int i = this.state;
        return i == 3 || i == 4;
    }

    @SuppressLint({"HandlerLeak"})
    private class PostResponseHandler extends Handler {
        public PostResponseHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            Pair<Object, Object> requestAndResponse = (Pair) msg.obj;
            Object request = requestAndResponse.first;
            Object response = requestAndResponse.second;
            int i = msg.what;
            if (i == 0) {
                DefaultDrmSession.this.onProvisionResponse(request, response);
            } else if (i == 1) {
                DefaultDrmSession.this.onKeyResponse(request, response);
            }
        }
    }

    @SuppressLint({"HandlerLeak"})
    private class PostRequestHandler extends Handler {
        public PostRequestHandler(Looper backgroundLooper) {
            super(backgroundLooper);
        }

        /* access modifiers changed from: package-private */
        public void post(int what, Object request, boolean allowRetry) {
            obtainMessage(what, (int) allowRetry, 0, request).sendToTarget();
        }

        public void handleMessage(Message msg) {
            Object request = msg.obj;
            try {
                int i = msg.what;
                if (i == 0) {
                    response = DefaultDrmSession.this.callback.executeProvisionRequest(DefaultDrmSession.this.uuid, (ExoMediaDrm.ProvisionRequest) request);
                } else if (i == 1) {
                    response = DefaultDrmSession.this.callback.executeKeyRequest(DefaultDrmSession.this.uuid, (ExoMediaDrm.KeyRequest) request);
                } else {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                response = e;
                if (maybeRetryRequest(msg)) {
                    return;
                }
            }
            DefaultDrmSession.this.postResponseHandler.obtainMessage(msg.what, Pair.create(request, response)).sendToTarget();
        }

        private boolean maybeRetryRequest(Message originalMsg) {
            int errorCount;
            if (!(originalMsg.arg1 == 1) || (errorCount = originalMsg.arg2 + 1) > DefaultDrmSession.this.initialDrmRequestRetryCount) {
                return false;
            }
            Message retryMsg = Message.obtain(originalMsg);
            retryMsg.arg2 = errorCount;
            sendMessageDelayed(retryMsg, getRetryDelayMillis(errorCount));
            return true;
        }

        private long getRetryDelayMillis(int errorCount) {
            return (long) Math.min((errorCount - 1) * 1000, 5000);
        }
    }
}
