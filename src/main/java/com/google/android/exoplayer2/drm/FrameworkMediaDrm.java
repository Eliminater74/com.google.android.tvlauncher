package com.google.android.exoplayer2.drm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.DeniedByServerException;
import android.media.MediaCryptoException;
import android.media.MediaDrm;
import android.media.MediaDrmException;
import android.media.NotProvisionedException;
import android.media.UnsupportedSchemeException;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@TargetApi(23)
public final class FrameworkMediaDrm implements ExoMediaDrm<FrameworkMediaCrypto> {
    private static final String CENC_SCHEME_MIME_TYPE = "cenc";
    private static final String MOCK_LA_URL = "<LA_URL>https://x</LA_URL>";
    private static final String MOCK_LA_URL_VALUE = "https://x";
    private static final String TAG = "FrameworkMediaDrm";
    private static final int UTF_16_BYTES_PER_CHARACTER = 2;
    private final MediaDrm mediaDrm;
    private final UUID uuid;

    public static FrameworkMediaDrm newInstance(UUID uuid2) throws UnsupportedDrmException {
        try {
            return new FrameworkMediaDrm(uuid2);
        } catch (UnsupportedSchemeException e) {
            throw new UnsupportedDrmException(1, e);
        } catch (Exception e2) {
            throw new UnsupportedDrmException(2, e2);
        }
    }

    private FrameworkMediaDrm(UUID uuid2) throws UnsupportedSchemeException {
        Assertions.checkNotNull(uuid2);
        Assertions.checkArgument(!C0841C.COMMON_PSSH_UUID.equals(uuid2), "Use C.CLEARKEY_UUID instead");
        this.uuid = uuid2;
        this.mediaDrm = new MediaDrm(adjustUuid(uuid2));
        if (C0841C.WIDEVINE_UUID.equals(uuid2) && needsForceWidevineL3Workaround()) {
            forceWidevineL3(this.mediaDrm);
        }
    }

    public void setOnEventListener(ExoMediaDrm.OnEventListener<? super FrameworkMediaCrypto> listener) {
        FrameworkMediaDrm$$Lambda$0 frameworkMediaDrm$$Lambda$0;
        MediaDrm mediaDrm2 = this.mediaDrm;
        if (listener == null) {
            frameworkMediaDrm$$Lambda$0 = null;
        } else {
            frameworkMediaDrm$$Lambda$0 = new FrameworkMediaDrm$$Lambda$0(this, listener);
        }
        mediaDrm2.setOnEventListener(frameworkMediaDrm$$Lambda$0);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$setOnEventListener$0$FrameworkMediaDrm(ExoMediaDrm.OnEventListener listener, MediaDrm mediaDrm2, byte[] sessionId, int event, int extra, byte[] data) {
        listener.onEvent(this, sessionId, event, extra, data);
    }

    public void setOnKeyStatusChangeListener(ExoMediaDrm.OnKeyStatusChangeListener<? super FrameworkMediaCrypto> listener) {
        FrameworkMediaDrm$$Lambda$1 frameworkMediaDrm$$Lambda$1;
        if (Util.SDK_INT >= 23) {
            MediaDrm mediaDrm2 = this.mediaDrm;
            if (listener == null) {
                frameworkMediaDrm$$Lambda$1 = null;
            } else {
                frameworkMediaDrm$$Lambda$1 = new FrameworkMediaDrm$$Lambda$1(this, listener);
            }
            mediaDrm2.setOnKeyStatusChangeListener(frameworkMediaDrm$$Lambda$1, (Handler) null);
            return;
        }
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$setOnKeyStatusChangeListener$1$FrameworkMediaDrm(ExoMediaDrm.OnKeyStatusChangeListener listener, MediaDrm mediaDrm2, byte[] sessionId, List keyInfo, boolean hasNewUsableKey) {
        List<ExoMediaDrm.KeyStatus> exoKeyInfo = new ArrayList<>();
        Iterator it = keyInfo.iterator();
        while (it.hasNext()) {
            MediaDrm.KeyStatus keyStatus = (MediaDrm.KeyStatus) it.next();
            exoKeyInfo.add(new ExoMediaDrm.KeyStatus(keyStatus.getStatusCode(), keyStatus.getKeyId()));
        }
        listener.onKeyStatusChange(this, sessionId, exoKeyInfo, hasNewUsableKey);
    }

    public byte[] openSession() throws MediaDrmException {
        return this.mediaDrm.openSession();
    }

    public void closeSession(byte[] sessionId) {
        this.mediaDrm.closeSession(sessionId);
    }

    public ExoMediaDrm.KeyRequest getKeyRequest(byte[] scope, @Nullable List<DrmInitData.SchemeData> schemeDatas, int keyType, @Nullable HashMap<String, String> optionalParameters) throws NotProvisionedException {
        DrmInitData.SchemeData schemeData = null;
        byte[] initData = null;
        String mimeType = null;
        if (schemeDatas != null) {
            schemeData = getSchemeData(this.uuid, schemeDatas);
            initData = adjustRequestInitData(this.uuid, (byte[]) Assertions.checkNotNull(schemeData.data));
            mimeType = adjustRequestMimeType(this.uuid, schemeData.mimeType);
        }
        MediaDrm.KeyRequest request = this.mediaDrm.getKeyRequest(scope, initData, mimeType, keyType, optionalParameters);
        byte[] requestData = adjustRequestData(this.uuid, request.getData());
        String licenseServerUrl = request.getDefaultUrl();
        if (MOCK_LA_URL_VALUE.equals(licenseServerUrl)) {
            licenseServerUrl = "";
        }
        if (TextUtils.isEmpty(licenseServerUrl) && schemeData != null && !TextUtils.isEmpty(schemeData.licenseServerUrl)) {
            licenseServerUrl = schemeData.licenseServerUrl;
        }
        return new ExoMediaDrm.KeyRequest(requestData, licenseServerUrl);
    }

    public byte[] provideKeyResponse(byte[] scope, byte[] response) throws NotProvisionedException, DeniedByServerException {
        if (C0841C.CLEARKEY_UUID.equals(this.uuid)) {
            response = ClearKeyUtil.adjustResponseData(response);
        }
        return this.mediaDrm.provideKeyResponse(scope, response);
    }

    public ExoMediaDrm.ProvisionRequest getProvisionRequest() {
        MediaDrm.ProvisionRequest request = this.mediaDrm.getProvisionRequest();
        return new ExoMediaDrm.ProvisionRequest(request.getData(), request.getDefaultUrl());
    }

    public void provideProvisionResponse(byte[] response) throws DeniedByServerException {
        this.mediaDrm.provideProvisionResponse(response);
    }

    public Map<String, String> queryKeyStatus(byte[] sessionId) {
        return this.mediaDrm.queryKeyStatus(sessionId);
    }

    public void release() {
        this.mediaDrm.release();
    }

    public void restoreKeys(byte[] sessionId, byte[] keySetId) {
        this.mediaDrm.restoreKeys(sessionId, keySetId);
    }

    public String getPropertyString(String propertyName) {
        return this.mediaDrm.getPropertyString(propertyName);
    }

    public byte[] getPropertyByteArray(String propertyName) {
        return this.mediaDrm.getPropertyByteArray(propertyName);
    }

    public void setPropertyString(String propertyName, String value) {
        this.mediaDrm.setPropertyString(propertyName, value);
    }

    public void setPropertyByteArray(String propertyName, byte[] value) {
        this.mediaDrm.setPropertyByteArray(propertyName, value);
    }

    public FrameworkMediaCrypto createMediaCrypto(byte[] initData) throws MediaCryptoException {
        return new FrameworkMediaCrypto(adjustUuid(this.uuid), initData, Util.SDK_INT < 21 && C0841C.WIDEVINE_UUID.equals(this.uuid) && "L3".equals(getPropertyString("securityLevel")));
    }

    private static DrmInitData.SchemeData getSchemeData(UUID uuid2, List<DrmInitData.SchemeData> schemeDatas) {
        if (!C0841C.WIDEVINE_UUID.equals(uuid2)) {
            return schemeDatas.get(0);
        }
        if (Util.SDK_INT >= 28 && schemeDatas.size() > 1) {
            DrmInitData.SchemeData firstSchemeData = schemeDatas.get(0);
            int concatenatedDataLength = 0;
            boolean canConcatenateData = true;
            int i = 0;
            while (true) {
                if (i >= schemeDatas.size()) {
                    break;
                }
                DrmInitData.SchemeData schemeData = schemeDatas.get(i);
                byte[] schemeDataData = (byte[]) Util.castNonNull(schemeData.data);
                if (schemeData.requiresSecureDecryption != firstSchemeData.requiresSecureDecryption || !Util.areEqual(schemeData.mimeType, firstSchemeData.mimeType) || !Util.areEqual(schemeData.licenseServerUrl, firstSchemeData.licenseServerUrl) || !PsshAtomUtil.isPsshAtom(schemeDataData)) {
                    canConcatenateData = false;
                } else {
                    concatenatedDataLength += schemeDataData.length;
                    i++;
                }
            }
            canConcatenateData = false;
            if (canConcatenateData) {
                byte[] concatenatedData = new byte[concatenatedDataLength];
                int concatenatedDataPosition = 0;
                for (int i2 = 0; i2 < schemeDatas.size(); i2++) {
                    byte[] schemeDataData2 = (byte[]) Util.castNonNull(schemeDatas.get(i2).data);
                    int schemeDataLength = schemeDataData2.length;
                    System.arraycopy(schemeDataData2, 0, concatenatedData, concatenatedDataPosition, schemeDataLength);
                    concatenatedDataPosition += schemeDataLength;
                }
                return firstSchemeData.copyWithData(concatenatedData);
            }
        }
        for (int i3 = 0; i3 < schemeDatas.size(); i3++) {
            DrmInitData.SchemeData schemeData2 = schemeDatas.get(i3);
            int version = PsshAtomUtil.parseVersion((byte[]) Util.castNonNull(schemeData2.data));
            if (Util.SDK_INT < 23 && version == 0) {
                return schemeData2;
            }
            if (Util.SDK_INT >= 23 && version == 1) {
                return schemeData2;
            }
        }
        return schemeDatas.get(0);
    }

    private static UUID adjustUuid(UUID uuid2) {
        return (Util.SDK_INT >= 27 || !C0841C.CLEARKEY_UUID.equals(uuid2)) ? uuid2 : C0841C.COMMON_PSSH_UUID;
    }

    private static byte[] adjustRequestInitData(UUID uuid2, byte[] initData) {
        byte[] psshData;
        if (C0841C.PLAYREADY_UUID.equals(uuid2)) {
            byte[] schemeSpecificData = PsshAtomUtil.parseSchemeSpecificData(initData, uuid2);
            if (schemeSpecificData == null) {
                schemeSpecificData = initData;
            }
            initData = PsshAtomUtil.buildPsshAtom(C0841C.PLAYREADY_UUID, addLaUrlAttributeIfMissing(schemeSpecificData));
        }
        if (((Util.SDK_INT >= 21 || !C0841C.WIDEVINE_UUID.equals(uuid2)) && (!C0841C.PLAYREADY_UUID.equals(uuid2) || !"Amazon".equals(Util.MANUFACTURER) || (!"AFTB".equals(Util.MODEL) && !"AFTS".equals(Util.MODEL) && !"AFTM".equals(Util.MODEL)))) || (psshData = PsshAtomUtil.parseSchemeSpecificData(initData, uuid2)) == null) {
            return initData;
        }
        return psshData;
    }

    private static String adjustRequestMimeType(UUID uuid2, String mimeType) {
        if (Util.SDK_INT >= 26 || !C0841C.CLEARKEY_UUID.equals(uuid2) || (!MimeTypes.VIDEO_MP4.equals(mimeType) && !MimeTypes.AUDIO_MP4.equals(mimeType))) {
            return mimeType;
        }
        return "cenc";
    }

    private static byte[] adjustRequestData(UUID uuid2, byte[] requestData) {
        if (C0841C.CLEARKEY_UUID.equals(uuid2)) {
            return ClearKeyUtil.adjustRequestData(requestData);
        }
        return requestData;
    }

    @SuppressLint({"WrongConstant"})
    private static void forceWidevineL3(MediaDrm mediaDrm2) {
        mediaDrm2.setPropertyString("securityLevel", "L3");
    }

    private static boolean needsForceWidevineL3Workaround() {
        return "ASUS_Z00AD".equals(Util.MODEL);
    }

    private static byte[] addLaUrlAttributeIfMissing(byte[] data) {
        ParsableByteArray byteArray = new ParsableByteArray(data);
        int length = byteArray.readLittleEndianInt();
        int objectRecordCount = byteArray.readLittleEndianShort();
        int recordType = byteArray.readLittleEndianShort();
        if (objectRecordCount == 1 && recordType == 1) {
            String xml = byteArray.readString(byteArray.readLittleEndianShort(), Charset.forName(C0841C.UTF16LE_NAME));
            if (xml.contains("<LA_URL>")) {
                return data;
            }
            int endOfDataTagIndex = xml.indexOf("</DATA>");
            if (endOfDataTagIndex == -1) {
                Log.m30w(TAG, "Could not find the </DATA> tag. Skipping LA_URL workaround.");
            }
            String substring = xml.substring(0, endOfDataTagIndex);
            String substring2 = xml.substring(endOfDataTagIndex);
            StringBuilder sb = new StringBuilder(String.valueOf(substring).length() + 26 + String.valueOf(substring2).length());
            sb.append(substring);
            sb.append(MOCK_LA_URL);
            sb.append(substring2);
            String xmlWithMockLaUrl = sb.toString();
            int extraBytes = MOCK_LA_URL.length() * 2;
            ByteBuffer newData = ByteBuffer.allocate(length + extraBytes);
            newData.order(ByteOrder.LITTLE_ENDIAN);
            newData.putInt(length + extraBytes);
            newData.putShort((short) objectRecordCount);
            newData.putShort((short) recordType);
            newData.putShort((short) (xmlWithMockLaUrl.length() * 2));
            newData.put(xmlWithMockLaUrl.getBytes(Charset.forName(C0841C.UTF16LE_NAME)));
            return newData.array();
        }
        Log.m28i(TAG, "Unexpected record count or type. Skipping LA_URL workaround.");
        return data;
    }
}
