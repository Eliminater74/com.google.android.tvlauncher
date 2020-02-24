package com.google.android.exoplayer2.drm;

import android.os.ConditionVariable;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.Nullable;
import android.util.Pair;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.DrmSession;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Assertions;
import java.util.HashMap;
import java.util.UUID;

public final class OfflineLicenseHelper<T extends ExoMediaCrypto> {
    private static final DrmInitData DUMMY_DRM_INIT_DATA = new DrmInitData(new DrmInitData.SchemeData[0]);
    /* access modifiers changed from: private */
    public final ConditionVariable conditionVariable;
    private final DefaultDrmSessionManager<T> drmSessionManager;
    private final HandlerThread handlerThread = new HandlerThread("OfflineLicenseHelper");

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String defaultLicenseUrl, HttpDataSource.Factory httpDataSourceFactory) throws UnsupportedDrmException {
        return newWidevineInstance(defaultLicenseUrl, false, httpDataSourceFactory, null);
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String defaultLicenseUrl, boolean forceDefaultLicenseUrl, HttpDataSource.Factory httpDataSourceFactory) throws UnsupportedDrmException {
        return newWidevineInstance(defaultLicenseUrl, forceDefaultLicenseUrl, httpDataSourceFactory, null);
    }

    public static OfflineLicenseHelper<FrameworkMediaCrypto> newWidevineInstance(String defaultLicenseUrl, boolean forceDefaultLicenseUrl, HttpDataSource.Factory httpDataSourceFactory, @Nullable HashMap<String, String> optionalKeyRequestParameters) throws UnsupportedDrmException {
        return new OfflineLicenseHelper<>(C0841C.WIDEVINE_UUID, FrameworkMediaDrm.newInstance(C0841C.WIDEVINE_UUID), new HttpMediaDrmCallback(defaultLicenseUrl, forceDefaultLicenseUrl, httpDataSourceFactory), optionalKeyRequestParameters);
    }

    public OfflineLicenseHelper(UUID uuid, ExoMediaDrm<T> mediaDrm, MediaDrmCallback callback, @Nullable HashMap<String, String> optionalKeyRequestParameters) {
        this.handlerThread.start();
        this.conditionVariable = new ConditionVariable();
        DefaultDrmSessionEventListener eventListener = new DefaultDrmSessionEventListener() {
            public void onDrmSessionAcquired() {
                DefaultDrmSessionEventListener$$CC.onDrmSessionAcquired$$dflt$$(this);
            }

            public void onDrmSessionReleased() {
                DefaultDrmSessionEventListener$$CC.onDrmSessionReleased$$dflt$$(this);
            }

            public void onDrmKeysLoaded() {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            public void onDrmSessionManagerError(Exception e) {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            public void onDrmKeysRestored() {
                OfflineLicenseHelper.this.conditionVariable.open();
            }

            public void onDrmKeysRemoved() {
                OfflineLicenseHelper.this.conditionVariable.open();
            }
        };
        this.drmSessionManager = new DefaultDrmSessionManager<>(uuid, mediaDrm, callback, optionalKeyRequestParameters);
        this.drmSessionManager.addListener(new Handler(this.handlerThread.getLooper()), eventListener);
    }

    public synchronized byte[] getPropertyByteArray(String key) {
        return this.drmSessionManager.getPropertyByteArray(key);
    }

    public synchronized void setPropertyByteArray(String key, byte[] value) {
        this.drmSessionManager.setPropertyByteArray(key, value);
    }

    public synchronized String getPropertyString(String key) {
        return this.drmSessionManager.getPropertyString(key);
    }

    public synchronized void setPropertyString(String key, String value) {
        this.drmSessionManager.setPropertyString(key, value);
    }

    public synchronized byte[] downloadLicense(DrmInitData drmInitData) throws DrmSession.DrmSessionException {
        Assertions.checkArgument(drmInitData != null);
        return blockingKeyRequest(2, null, drmInitData);
    }

    public synchronized byte[] renewLicense(byte[] offlineLicenseKeySetId) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(offlineLicenseKeySetId);
        return blockingKeyRequest(2, offlineLicenseKeySetId, DUMMY_DRM_INIT_DATA);
    }

    public synchronized void releaseLicense(byte[] offlineLicenseKeySetId) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(offlineLicenseKeySetId);
        blockingKeyRequest(3, offlineLicenseKeySetId, DUMMY_DRM_INIT_DATA);
    }

    public synchronized Pair<Long, Long> getLicenseDurationRemainingSec(byte[] offlineLicenseKeySetId) throws DrmSession.DrmSessionException {
        Assertions.checkNotNull(offlineLicenseKeySetId);
        DrmSession<T> drmSession = openBlockingKeyRequest(1, offlineLicenseKeySetId, DUMMY_DRM_INIT_DATA);
        DrmSession.DrmSessionException error = drmSession.getError();
        Pair<Long, Long> licenseDurationRemainingSec = WidevineUtil.getLicenseDurationRemainingSec(drmSession);
        this.drmSessionManager.releaseSession(drmSession);
        if (error == null) {
            return (Pair) Assertions.checkNotNull(licenseDurationRemainingSec);
        } else if (error.getCause() instanceof KeysExpiredException) {
            return Pair.create(0L, 0L);
        } else {
            throw error;
        }
    }

    public void release() {
        this.handlerThread.quit();
    }

    private byte[] blockingKeyRequest(int licenseMode, @Nullable byte[] offlineLicenseKeySetId, DrmInitData drmInitData) throws DrmSession.DrmSessionException {
        DrmSession<T> drmSession = openBlockingKeyRequest(licenseMode, offlineLicenseKeySetId, drmInitData);
        DrmSession.DrmSessionException error = drmSession.getError();
        byte[] keySetId = drmSession.getOfflineLicenseKeySetId();
        this.drmSessionManager.releaseSession(drmSession);
        if (error == null) {
            return (byte[]) Assertions.checkNotNull(keySetId);
        }
        throw error;
    }

    private DrmSession<T> openBlockingKeyRequest(int licenseMode, @Nullable byte[] offlineLicenseKeySetId, DrmInitData drmInitData) {
        this.drmSessionManager.setMode(licenseMode, offlineLicenseKeySetId);
        this.conditionVariable.close();
        DrmSession<T> drmSession = this.drmSessionManager.acquireSession(this.handlerThread.getLooper(), drmInitData);
        this.conditionVariable.block();
        return drmSession;
    }
}
