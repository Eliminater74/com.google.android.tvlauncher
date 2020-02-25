package com.google.android.exoplayer2.mediacodec;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.MediaCodecInfo;
import android.media.MediaCodecList;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseIntArray;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint({"InlinedApi"})
public final class MediaCodecUtil {
    private static final SparseIntArray AVC_LEVEL_NUMBER_TO_CONST = new SparseIntArray();
    private static final SparseIntArray AVC_PROFILE_NUMBER_TO_CONST = new SparseIntArray();
    private static final String CODEC_ID_AVC1 = "avc1";
    private static final String CODEC_ID_AVC2 = "avc2";
    private static final String CODEC_ID_DVH1 = "dvh1";
    private static final String CODEC_ID_DVHE = "dvhe";
    private static final String CODEC_ID_HEV1 = "hev1";
    private static final String CODEC_ID_HVC1 = "hvc1";
    private static final String CODEC_ID_MP4A = "mp4a";
    private static final Map<String, Integer> DOLBY_VISION_STRING_TO_LEVEL = new HashMap();
    private static final Map<String, Integer> DOLBY_VISION_STRING_TO_PROFILE = new HashMap();
    private static final Map<String, Integer> HEVC_CODEC_STRING_TO_PROFILE_LEVEL = new HashMap();
    private static final SparseIntArray MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE = new SparseIntArray();
    private static final Pattern PROFILE_PATTERN = Pattern.compile("^\\D?(\\d+)$");
    private static final String TAG = "MediaCodecUtil";
    private static final HashMap<CodecKey, List<MediaCodecInfo>> decoderInfosCache = new HashMap<>();
    private static int maxH264DecodableFrameSize = -1;

    static {
        AVC_PROFILE_NUMBER_TO_CONST.put(66, 1);
        AVC_PROFILE_NUMBER_TO_CONST.put(77, 2);
        AVC_PROFILE_NUMBER_TO_CONST.put(88, 4);
        AVC_PROFILE_NUMBER_TO_CONST.put(100, 8);
        AVC_PROFILE_NUMBER_TO_CONST.put(110, 16);
        AVC_PROFILE_NUMBER_TO_CONST.put(122, 32);
        AVC_PROFILE_NUMBER_TO_CONST.put(ClientAnalytics.LogRequest.LogSource.FAMILYLINK_ANDROID_PRIMES_VALUE, 64);
        AVC_LEVEL_NUMBER_TO_CONST.put(10, 1);
        AVC_LEVEL_NUMBER_TO_CONST.put(11, 4);
        AVC_LEVEL_NUMBER_TO_CONST.put(12, 8);
        AVC_LEVEL_NUMBER_TO_CONST.put(13, 16);
        AVC_LEVEL_NUMBER_TO_CONST.put(20, 32);
        AVC_LEVEL_NUMBER_TO_CONST.put(21, 64);
        AVC_LEVEL_NUMBER_TO_CONST.put(22, 128);
        AVC_LEVEL_NUMBER_TO_CONST.put(30, 256);
        AVC_LEVEL_NUMBER_TO_CONST.put(31, 512);
        AVC_LEVEL_NUMBER_TO_CONST.put(32, 1024);
        AVC_LEVEL_NUMBER_TO_CONST.put(40, 2048);
        AVC_LEVEL_NUMBER_TO_CONST.put(41, 4096);
        AVC_LEVEL_NUMBER_TO_CONST.put(42, 8192);
        AVC_LEVEL_NUMBER_TO_CONST.put(50, 16384);
        AVC_LEVEL_NUMBER_TO_CONST.put(51, 32768);
        AVC_LEVEL_NUMBER_TO_CONST.put(52, 65536);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L30", 1);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L60", 4);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L63", 16);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L90", 64);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L93", 256);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L120", 1024);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L123", 4096);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L150", 16384);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L153", 65536);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L156", 262144);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L180", 1048576);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L183", 4194304);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("L186", 16777216);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H30", 2);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H60", 8);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H63", 32);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H90", 128);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H93", 512);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H120", 2048);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H123", 8192);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H150", 32768);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H153", 131072);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H156", 524288);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H180", 2097152);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H183", 8388608);
        HEVC_CODEC_STRING_TO_PROFILE_LEVEL.put("H186", 33554432);
        DOLBY_VISION_STRING_TO_PROFILE.put("00", 1);
        DOLBY_VISION_STRING_TO_PROFILE.put("01", 2);
        DOLBY_VISION_STRING_TO_PROFILE.put("02", 4);
        DOLBY_VISION_STRING_TO_PROFILE.put("03", 8);
        DOLBY_VISION_STRING_TO_PROFILE.put("04", 16);
        DOLBY_VISION_STRING_TO_PROFILE.put("05", 32);
        DOLBY_VISION_STRING_TO_PROFILE.put("06", 64);
        DOLBY_VISION_STRING_TO_PROFILE.put("07", 128);
        DOLBY_VISION_STRING_TO_PROFILE.put("08", 256);
        DOLBY_VISION_STRING_TO_PROFILE.put("09", 512);
        DOLBY_VISION_STRING_TO_LEVEL.put("01", 1);
        DOLBY_VISION_STRING_TO_LEVEL.put("02", 2);
        DOLBY_VISION_STRING_TO_LEVEL.put("03", 4);
        DOLBY_VISION_STRING_TO_LEVEL.put("04", 8);
        DOLBY_VISION_STRING_TO_LEVEL.put("05", 16);
        DOLBY_VISION_STRING_TO_LEVEL.put("06", 32);
        DOLBY_VISION_STRING_TO_LEVEL.put("07", 64);
        DOLBY_VISION_STRING_TO_LEVEL.put("08", 128);
        DOLBY_VISION_STRING_TO_LEVEL.put("09", 256);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(1, 1);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(2, 2);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(3, 3);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(4, 4);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(5, 5);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(6, 6);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(17, 17);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(20, 20);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(23, 23);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(29, 29);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(39, 39);
        MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.put(42, 42);
    }

    private MediaCodecUtil() {
    }

    public static void warmDecoderInfoCache(String mimeType, boolean secure, boolean tunneling) {
        try {
            getDecoderInfos(mimeType, secure, tunneling);
        } catch (DecoderQueryException e) {
            Log.m27e(TAG, "Codec warming failed", e);
        }
    }

    @Nullable
    public static MediaCodecInfo getPassthroughDecoderInfo() throws DecoderQueryException {
        MediaCodecInfo decoderInfo = getDecoderInfo(MimeTypes.AUDIO_RAW, false, false);
        if (decoderInfo == null) {
            return null;
        }
        return MediaCodecInfo.newPassthroughInstance(decoderInfo.name);
    }

    @Nullable
    public static MediaCodecInfo getDecoderInfo(String mimeType, boolean secure, boolean tunneling) throws DecoderQueryException {
        List<MediaCodecInfo> decoderInfos = getDecoderInfos(mimeType, secure, tunneling);
        if (decoderInfos.isEmpty()) {
            return null;
        }
        return decoderInfos.get(0);
    }

    public static synchronized List<MediaCodecInfo> getDecoderInfos(String mimeType, boolean secure, boolean tunneling) throws DecoderQueryException {
        MediaCodecListCompat mediaCodecList;
        synchronized (MediaCodecUtil.class) {
            CodecKey key = new CodecKey(mimeType, secure, tunneling);
            List<MediaCodecInfo> cachedDecoderInfos = decoderInfosCache.get(key);
            if (cachedDecoderInfos != null) {
                return cachedDecoderInfos;
            }
            if (Util.SDK_INT >= 21) {
                mediaCodecList = new MediaCodecListCompatV21(secure, tunneling);
            } else {
                mediaCodecList = new MediaCodecListCompatV16();
            }
            ArrayList<MediaCodecInfo> decoderInfos = getDecoderInfosInternal(key, mediaCodecList, mimeType);
            if (secure && decoderInfos.isEmpty() && 21 <= Util.SDK_INT && Util.SDK_INT <= 23) {
                decoderInfos = getDecoderInfosInternal(key, new MediaCodecListCompatV16(), mimeType);
                if (!decoderInfos.isEmpty()) {
                    String str = decoderInfos.get(0).name;
                    StringBuilder sb = new StringBuilder(String.valueOf(mimeType).length() + 63 + String.valueOf(str).length());
                    sb.append("MediaCodecList API didn't list secure decoder for: ");
                    sb.append(mimeType);
                    sb.append(". Assuming: ");
                    sb.append(str);
                    Log.m30w(TAG, sb.toString());
                }
            }
            applyWorkarounds(mimeType, decoderInfos);
            List<MediaCodecInfo> unmodifiableDecoderInfos = Collections.unmodifiableList(decoderInfos);
            decoderInfosCache.put(key, unmodifiableDecoderInfos);
            return unmodifiableDecoderInfos;
        }
    }

    @CheckResult
    public static List<MediaCodecInfo> getDecoderInfosSortedByFormatSupport(List<MediaCodecInfo> decoderInfos, Format format) {
        List<MediaCodecInfo> decoderInfos2 = new ArrayList<>(decoderInfos);
        sortByScore(decoderInfos2, new MediaCodecUtil$$Lambda$0(format));
        return decoderInfos2;
    }

    static final /* synthetic */ int lambda$getDecoderInfosSortedByFormatSupport$0$MediaCodecUtil(Format format, MediaCodecInfo decoderInfo) {
        try {
            return decoderInfo.isFormatSupported(format) ? 1 : 0;
        } catch (DecoderQueryException e) {
            return -1;
        }
    }

    public static int maxH264DecodableFrameSize() throws DecoderQueryException {
        if (maxH264DecodableFrameSize == -1) {
            int result = 0;
            MediaCodecInfo decoderInfo = getDecoderInfo(MimeTypes.VIDEO_H264, false, false);
            if (decoderInfo != null) {
                for (MediaCodecInfo.CodecProfileLevel profileLevel : decoderInfo.getProfileLevels()) {
                    result = Math.max(avcLevelToMaxFrameSize(profileLevel.level), result);
                }
                result = Math.max(result, Util.SDK_INT >= 21 ? 345600 : 172800);
            }
            maxH264DecodableFrameSize = result;
        }
        return maxH264DecodableFrameSize;
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0058, code lost:
        if (r3.equals(com.google.android.exoplayer2.mediacodec.MediaCodecUtil.CODEC_ID_AVC1) != false) goto L_0x005c;
     */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Pair<java.lang.Integer, java.lang.Integer> getCodecProfileAndLevel(@android.support.annotation.Nullable java.lang.String r6) {
        /*
            r0 = 0
            if (r6 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = "\\."
            java.lang.String[] r1 = r6.split(r1)
            r2 = 0
            r3 = r1[r2]
            r4 = -1
            int r5 = r3.hashCode()
            switch(r5) {
                case 3006243: goto L_0x0052;
                case 3006244: goto L_0x0048;
                case 3095771: goto L_0x003e;
                case 3095823: goto L_0x0034;
                case 3199032: goto L_0x002a;
                case 3214780: goto L_0x0020;
                case 3356560: goto L_0x0016;
                default: goto L_0x0015;
            }
        L_0x0015:
            goto L_0x005b
        L_0x0016:
            java.lang.String r2 = "mp4a"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0015
            r2 = 6
            goto L_0x005c
        L_0x0020:
            java.lang.String r2 = "hvc1"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0015
            r2 = 3
            goto L_0x005c
        L_0x002a:
            java.lang.String r2 = "hev1"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0015
            r2 = 2
            goto L_0x005c
        L_0x0034:
            java.lang.String r2 = "dvhe"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0015
            r2 = 4
            goto L_0x005c
        L_0x003e:
            java.lang.String r2 = "dvh1"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0015
            r2 = 5
            goto L_0x005c
        L_0x0048:
            java.lang.String r2 = "avc2"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x0015
            r2 = 1
            goto L_0x005c
        L_0x0052:
            java.lang.String r5 = "avc1"
            boolean r3 = r3.equals(r5)
            if (r3 == 0) goto L_0x0015
            goto L_0x005c
        L_0x005b:
            r2 = -1
        L_0x005c:
            switch(r2) {
                case 0: goto L_0x006f;
                case 1: goto L_0x006f;
                case 2: goto L_0x006a;
                case 3: goto L_0x006a;
                case 4: goto L_0x0065;
                case 5: goto L_0x0065;
                case 6: goto L_0x0060;
                default: goto L_0x005f;
            }
        L_0x005f:
            return r0
        L_0x0060:
            android.util.Pair r0 = getAacCodecProfileAndLevel(r6, r1)
            return r0
        L_0x0065:
            android.util.Pair r0 = getDolbyVisionProfileAndLevel(r6, r1)
            return r0
        L_0x006a:
            android.util.Pair r0 = getHevcProfileAndLevel(r6, r1)
            return r0
        L_0x006f:
            android.util.Pair r0 = getAvcProfileAndLevel(r6, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getCodecProfileAndLevel(java.lang.String):android.util.Pair");
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x00be A[SYNTHETIC, Splitter:B:55:0x00be] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00f3 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.util.ArrayList<com.google.android.exoplayer2.mediacodec.MediaCodecInfo> getDecoderInfosInternal(com.google.android.exoplayer2.mediacodec.MediaCodecUtil.CodecKey r19, com.google.android.exoplayer2.mediacodec.MediaCodecUtil.MediaCodecListCompat r20, java.lang.String r21) throws com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException {
        /*
            r1 = r19
            r2 = r20
            java.lang.String r3 = "secure-playback"
            java.lang.String r4 = "tunneled-playback"
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x012e }
            r0.<init>()     // Catch:{ Exception -> 0x012e }
            r5 = r0
            java.lang.String r0 = r1.mimeType     // Catch:{ Exception -> 0x012e }
            r6 = r0
            int r0 = r20.getCodecCount()     // Catch:{ Exception -> 0x012e }
            r7 = r0
            boolean r0 = r20.secureDecodersExplicit()     // Catch:{ Exception -> 0x012e }
            r8 = r0
            r0 = 0
            r9 = r0
        L_0x001f:
            if (r9 >= r7) goto L_0x012b
            android.media.MediaCodecInfo r0 = r2.getCodecInfoAt(r9)     // Catch:{ Exception -> 0x012e }
            r10 = r0
            java.lang.String r0 = r10.getName()     // Catch:{ Exception -> 0x012e }
            r11 = r0
            r12 = r21
            java.lang.String r0 = getCodecSupportedType(r10, r11, r8, r12)     // Catch:{ Exception -> 0x0129 }
            r13 = r0
            if (r13 != 0) goto L_0x003b
            r18 = r3
            r16 = r4
            goto L_0x00e7
        L_0x003b:
            android.media.MediaCodecInfo$CodecCapabilities r0 = r10.getCapabilitiesForType(r13)     // Catch:{ Exception -> 0x00b1 }
            boolean r14 = r2.isFeatureSupported(r4, r13, r0)     // Catch:{ Exception -> 0x00b1 }
            boolean r15 = r2.isFeatureRequired(r4, r13, r0)     // Catch:{ Exception -> 0x00b1 }
            r16 = r4
            boolean r4 = r1.tunneling     // Catch:{ Exception -> 0x00ad }
            if (r4 != 0) goto L_0x0051
            if (r15 != 0) goto L_0x0057
        L_0x0051:
            boolean r4 = r1.tunneling     // Catch:{ Exception -> 0x00ad }
            if (r4 == 0) goto L_0x005b
            if (r14 != 0) goto L_0x005b
        L_0x0057:
            r18 = r3
            goto L_0x00e7
        L_0x005b:
            boolean r4 = r2.isFeatureSupported(r3, r13, r0)     // Catch:{ Exception -> 0x00ad }
            boolean r17 = r2.isFeatureRequired(r3, r13, r0)     // Catch:{ Exception -> 0x00ad }
            boolean r2 = r1.secure     // Catch:{ Exception -> 0x00ad }
            if (r2 != 0) goto L_0x006b
            if (r17 != 0) goto L_0x0071
        L_0x006b:
            boolean r2 = r1.secure     // Catch:{ Exception -> 0x00ad }
            if (r2 == 0) goto L_0x0075
            if (r4 != 0) goto L_0x0075
        L_0x0071:
            r18 = r3
            goto L_0x00e7
        L_0x0075:
            boolean r2 = codecNeedsDisableAdaptationWorkaround(r11)     // Catch:{ Exception -> 0x00ad }
            if (r8 == 0) goto L_0x0084
            r18 = r3
            boolean r3 = r1.secure     // Catch:{ Exception -> 0x0082 }
            if (r3 == r4) goto L_0x008c
            goto L_0x0086
        L_0x0082:
            r0 = move-exception
            goto L_0x00b6
        L_0x0084:
            r18 = r3
        L_0x0086:
            if (r8 != 0) goto L_0x0095
            boolean r3 = r1.secure     // Catch:{ Exception -> 0x0082 }
            if (r3 != 0) goto L_0x0095
        L_0x008c:
            r3 = 0
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r3 = com.google.android.exoplayer2.mediacodec.MediaCodecInfo.newInstance(r11, r6, r0, r2, r3)     // Catch:{ Exception -> 0x0082 }
            r5.add(r3)     // Catch:{ Exception -> 0x0082 }
            goto L_0x00ac
        L_0x0095:
            if (r8 != 0) goto L_0x00ac
            if (r4 == 0) goto L_0x00ac
            java.lang.String r3 = java.lang.String.valueOf(r11)     // Catch:{ Exception -> 0x0082 }
            java.lang.String r1 = ".secure"
            java.lang.String r1 = r3.concat(r1)     // Catch:{ Exception -> 0x0082 }
            r3 = 1
            com.google.android.exoplayer2.mediacodec.MediaCodecInfo r1 = com.google.android.exoplayer2.mediacodec.MediaCodecInfo.newInstance(r1, r6, r0, r2, r3)     // Catch:{ Exception -> 0x0082 }
            r5.add(r1)     // Catch:{ Exception -> 0x0082 }
            return r5
        L_0x00ac:
            goto L_0x00e7
        L_0x00ad:
            r0 = move-exception
            r18 = r3
            goto L_0x00b6
        L_0x00b1:
            r0 = move-exception
            r18 = r3
            r16 = r4
        L_0x00b6:
            int r1 = com.google.android.exoplayer2.util.Util.SDK_INT     // Catch:{ Exception -> 0x0129 }
            r2 = 23
            java.lang.String r3 = "MediaCodecUtil"
            if (r1 > r2) goto L_0x00f3
            boolean r1 = r5.isEmpty()     // Catch:{ Exception -> 0x0129 }
            if (r1 != 0) goto L_0x00f3
            java.lang.String r1 = java.lang.String.valueOf(r11)     // Catch:{ Exception -> 0x0129 }
            int r1 = r1.length()     // Catch:{ Exception -> 0x0129 }
            int r1 = r1 + 46
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0129 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r1 = "Skipping codec "
            r2.append(r1)     // Catch:{ Exception -> 0x0129 }
            r2.append(r11)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r1 = " (failed to query capabilities)"
            r2.append(r1)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x0129 }
            com.google.android.exoplayer2.util.Log.m26e(r3, r1)     // Catch:{ Exception -> 0x0129 }
        L_0x00e7:
            int r9 = r9 + 1
            r1 = r19
            r2 = r20
            r4 = r16
            r3 = r18
            goto L_0x001f
        L_0x00f3:
            java.lang.String r1 = java.lang.String.valueOf(r11)     // Catch:{ Exception -> 0x0129 }
            int r1 = r1.length()     // Catch:{ Exception -> 0x0129 }
            int r1 = r1 + 25
            java.lang.String r2 = java.lang.String.valueOf(r13)     // Catch:{ Exception -> 0x0129 }
            int r2 = r2.length()     // Catch:{ Exception -> 0x0129 }
            int r1 = r1 + r2
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0129 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r1 = "Failed to query codec "
            r2.append(r1)     // Catch:{ Exception -> 0x0129 }
            r2.append(r11)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r1 = " ("
            r2.append(r1)     // Catch:{ Exception -> 0x0129 }
            r2.append(r13)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r1 = ")"
            r2.append(r1)     // Catch:{ Exception -> 0x0129 }
            java.lang.String r1 = r2.toString()     // Catch:{ Exception -> 0x0129 }
            com.google.android.exoplayer2.util.Log.m26e(r3, r1)     // Catch:{ Exception -> 0x0129 }
            throw r0     // Catch:{ Exception -> 0x0129 }
        L_0x0129:
            r0 = move-exception
            goto L_0x0131
        L_0x012b:
            r12 = r21
            return r5
        L_0x012e:
            r0 = move-exception
            r12 = r21
        L_0x0131:
            com.google.android.exoplayer2.mediacodec.MediaCodecUtil$DecoderQueryException r1 = new com.google.android.exoplayer2.mediacodec.MediaCodecUtil$DecoderQueryException
            r2 = 0
            r1.<init>(r0)
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.mediacodec.MediaCodecUtil.getDecoderInfosInternal(com.google.android.exoplayer2.mediacodec.MediaCodecUtil$CodecKey, com.google.android.exoplayer2.mediacodec.MediaCodecUtil$MediaCodecListCompat, java.lang.String):java.util.ArrayList");
    }

    @Nullable
    private static String getCodecSupportedType(MediaCodecInfo info, String name, boolean secureDecodersExplicit, String requestedMimeType) {
        if (!isCodecUsableDecoder(info, name, secureDecodersExplicit, requestedMimeType)) {
            return null;
        }
        if (requestedMimeType.equals(MimeTypes.VIDEO_DOLBY_VISION)) {
            if ("OMX.MS.HEVCDV.Decoder".equals(name)) {
                return "video/hevcdv";
            }
            if ("OMX.RTK.video.decoder".equals(name) || "OMX.realtek.video.decoder.tunneled".equals(name)) {
                return "video/dv_hevc";
            }
        }
        for (String supportedType : info.getSupportedTypes()) {
            if (supportedType.equalsIgnoreCase(requestedMimeType)) {
                return supportedType;
            }
        }
        return null;
    }

    private static boolean isCodecUsableDecoder(MediaCodecInfo info, String name, boolean secureDecodersExplicit, String requestedMimeType) {
        if (info.isEncoder() || (!secureDecodersExplicit && name.endsWith(".secure"))) {
            return false;
        }
        if (Util.SDK_INT < 21 && ("CIPAACDecoder".equals(name) || "CIPMP3Decoder".equals(name) || "CIPVorbisDecoder".equals(name) || "CIPAMRNBDecoder".equals(name) || "AACDecoder".equals(name) || "MP3Decoder".equals(name))) {
            return false;
        }
        if (Util.SDK_INT < 18 && "OMX.MTK.AUDIO.DECODER.AAC".equals(name) && ("a70".equals(Util.DEVICE) || ("Xiaomi".equals(Util.MANUFACTURER) && Util.DEVICE.startsWith("HM")))) {
            return false;
        }
        if (Util.SDK_INT == 16 && "OMX.qcom.audio.decoder.mp3".equals(name) && ("dlxu".equals(Util.DEVICE) || "protou".equals(Util.DEVICE) || "ville".equals(Util.DEVICE) || "villeplus".equals(Util.DEVICE) || "villec2".equals(Util.DEVICE) || Util.DEVICE.startsWith("gee") || "C6602".equals(Util.DEVICE) || "C6603".equals(Util.DEVICE) || "C6606".equals(Util.DEVICE) || "C6616".equals(Util.DEVICE) || "L36h".equals(Util.DEVICE) || "SO-02E".equals(Util.DEVICE))) {
            return false;
        }
        if (Util.SDK_INT == 16 && "OMX.qcom.audio.decoder.aac".equals(name) && ("C1504".equals(Util.DEVICE) || "C1505".equals(Util.DEVICE) || "C1604".equals(Util.DEVICE) || "C1605".equals(Util.DEVICE))) {
            return false;
        }
        if (Util.SDK_INT < 24 && (("OMX.SEC.aac.dec".equals(name) || "OMX.Exynos.AAC.Decoder".equals(name)) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("zeroflte") || Util.DEVICE.startsWith("zerolte") || Util.DEVICE.startsWith("zenlte") || "SC-05G".equals(Util.DEVICE) || "marinelteatt".equals(Util.DEVICE) || "404SC".equals(Util.DEVICE) || "SC-04G".equals(Util.DEVICE) || "SCV31".equals(Util.DEVICE)))) {
            return false;
        }
        if (Util.SDK_INT <= 19 && "OMX.SEC.vp8.dec".equals(name) && "samsung".equals(Util.MANUFACTURER) && (Util.DEVICE.startsWith("d2") || Util.DEVICE.startsWith("serrano") || Util.DEVICE.startsWith("jflte") || Util.DEVICE.startsWith("santos") || Util.DEVICE.startsWith("t0"))) {
            return false;
        }
        if (Util.SDK_INT <= 19 && Util.DEVICE.startsWith("jflte") && "OMX.qcom.video.decoder.vp8".equals(name)) {
            return false;
        }
        if (!MimeTypes.AUDIO_E_AC3_JOC.equals(requestedMimeType) || !"OMX.MTK.AUDIO.DECODER.DSPAC3".equals(name)) {
            return true;
        }
        return false;
    }

    private static void applyWorkarounds(String mimeType, List<MediaCodecInfo> decoderInfos) {
        if (MimeTypes.AUDIO_RAW.equals(mimeType)) {
            sortByScore(decoderInfos, MediaCodecUtil$$Lambda$1.$instance);
        } else if (Util.SDK_INT < 21 && decoderInfos.size() > 1) {
            String firstCodecName = decoderInfos.get(0).name;
            if ("OMX.SEC.mp3.dec".equals(firstCodecName) || "OMX.SEC.MP3.Decoder".equals(firstCodecName) || "OMX.brcm.audio.mp3.decoder".equals(firstCodecName)) {
                sortByScore(decoderInfos, MediaCodecUtil$$Lambda$2.$instance);
            }
        }
    }

    static final /* synthetic */ int lambda$applyWorkarounds$1$MediaCodecUtil(MediaCodecInfo decoderInfo) {
        String name = decoderInfo.name;
        if (name.startsWith("OMX.google") || name.startsWith("c2.android")) {
            return 1;
        }
        if (Util.SDK_INT >= 26 || !name.equals("OMX.MTK.AUDIO.DECODER.RAW")) {
            return 0;
        }
        return -1;
    }

    static final /* synthetic */ int lambda$applyWorkarounds$2$MediaCodecUtil(MediaCodecInfo decoderInfo) {
        return decoderInfo.name.startsWith("OMX.google") ? 1 : 0;
    }

    private static boolean codecNeedsDisableAdaptationWorkaround(String name) {
        return Util.SDK_INT <= 22 && ("ODROID-XU3".equals(Util.MODEL) || "Nexus 10".equals(Util.MODEL)) && ("OMX.Exynos.AVC.Decoder".equals(name) || "OMX.Exynos.AVC.Decoder.secure".equals(name));
    }

    private static Pair<Integer, Integer> getDolbyVisionProfileAndLevel(String codec, String[] parts) {
        if (parts.length < 3) {
            String valueOf = String.valueOf(codec);
            Log.m30w(TAG, valueOf.length() != 0 ? "Ignoring malformed Dolby Vision codec string: ".concat(valueOf) : new String("Ignoring malformed Dolby Vision codec string: "));
            return null;
        }
        Matcher matcher = PROFILE_PATTERN.matcher(parts[1]);
        if (!matcher.matches()) {
            String valueOf2 = String.valueOf(codec);
            Log.m30w(TAG, valueOf2.length() != 0 ? "Ignoring malformed Dolby Vision codec string: ".concat(valueOf2) : new String("Ignoring malformed Dolby Vision codec string: "));
            return null;
        }
        String profileString = matcher.group(1);
        Integer profile = DOLBY_VISION_STRING_TO_PROFILE.get(profileString);
        if (profile == null) {
            String valueOf3 = String.valueOf(profileString);
            Log.m30w(TAG, valueOf3.length() != 0 ? "Unknown Dolby Vision profile string: ".concat(valueOf3) : new String("Unknown Dolby Vision profile string: "));
            return null;
        }
        String levelString = parts[2];
        Integer level = DOLBY_VISION_STRING_TO_LEVEL.get(levelString);
        if (level != null) {
            return new Pair<>(profile, level);
        }
        String valueOf4 = String.valueOf(levelString);
        Log.m30w(TAG, valueOf4.length() != 0 ? "Unknown Dolby Vision level string: ".concat(valueOf4) : new String("Unknown Dolby Vision level string: "));
        return null;
    }

    private static Pair<Integer, Integer> getHevcProfileAndLevel(String codec, String[] parts) {
        int profile;
        if (parts.length < 4) {
            String valueOf = String.valueOf(codec);
            Log.m30w(TAG, valueOf.length() != 0 ? "Ignoring malformed HEVC codec string: ".concat(valueOf) : new String("Ignoring malformed HEVC codec string: "));
            return null;
        }
        Matcher matcher = PROFILE_PATTERN.matcher(parts[1]);
        if (!matcher.matches()) {
            String valueOf2 = String.valueOf(codec);
            Log.m30w(TAG, valueOf2.length() != 0 ? "Ignoring malformed HEVC codec string: ".concat(valueOf2) : new String("Ignoring malformed HEVC codec string: "));
            return null;
        }
        String profileString = matcher.group(1);
        if (IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE.equals(profileString)) {
            profile = 1;
        } else if ("2".equals(profileString)) {
            profile = 2;
        } else {
            String valueOf3 = String.valueOf(profileString);
            Log.m30w(TAG, valueOf3.length() != 0 ? "Unknown HEVC profile string: ".concat(valueOf3) : new String("Unknown HEVC profile string: "));
            return null;
        }
        String levelString = parts[3];
        Integer level = HEVC_CODEC_STRING_TO_PROFILE_LEVEL.get(levelString);
        if (level != null) {
            return new Pair<>(Integer.valueOf(profile), level);
        }
        String valueOf4 = String.valueOf(levelString);
        Log.m30w(TAG, valueOf4.length() != 0 ? "Unknown HEVC level string: ".concat(valueOf4) : new String("Unknown HEVC level string: "));
        return null;
    }

    private static Pair<Integer, Integer> getAvcProfileAndLevel(String codec, String[] parts) {
        int levelInteger;
        int profileInteger;
        if (parts.length < 2) {
            String valueOf = String.valueOf(codec);
            Log.m30w(TAG, valueOf.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(valueOf) : new String("Ignoring malformed AVC codec string: "));
            return null;
        }
        try {
            if (parts[1].length() == 6) {
                int profileInteger2 = Integer.parseInt(parts[1].substring(0, 2), 16);
                levelInteger = Integer.parseInt(parts[1].substring(4), 16);
                profileInteger = profileInteger2;
            } else if (parts.length >= 3) {
                profileInteger = Integer.parseInt(parts[1]);
                levelInteger = Integer.parseInt(parts[2]);
            } else {
                String valueOf2 = String.valueOf(codec);
                Log.m30w(TAG, valueOf2.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(valueOf2) : new String("Ignoring malformed AVC codec string: "));
                return null;
            }
            int profile = AVC_PROFILE_NUMBER_TO_CONST.get(profileInteger, -1);
            if (profile == -1) {
                StringBuilder sb = new StringBuilder(32);
                sb.append("Unknown AVC profile: ");
                sb.append(profileInteger);
                Log.m30w(TAG, sb.toString());
                return null;
            }
            int level = AVC_LEVEL_NUMBER_TO_CONST.get(levelInteger, -1);
            if (level != -1) {
                return new Pair<>(Integer.valueOf(profile), Integer.valueOf(level));
            }
            StringBuilder sb2 = new StringBuilder(30);
            sb2.append("Unknown AVC level: ");
            sb2.append(levelInteger);
            Log.m30w(TAG, sb2.toString());
            return null;
        } catch (NumberFormatException e) {
            String valueOf3 = String.valueOf(codec);
            Log.m30w(TAG, valueOf3.length() != 0 ? "Ignoring malformed AVC codec string: ".concat(valueOf3) : new String("Ignoring malformed AVC codec string: "));
            return null;
        }
    }

    private static int avcLevelToMaxFrameSize(int avcLevel) {
        if (avcLevel == 1 || avcLevel == 2) {
            return 25344;
        }
        switch (avcLevel) {
            case 8:
            case 16:
            case 32:
                return 101376;
            case 64:
                return 202752;
            case 128:
            case 256:
                return 414720;
            case 512:
                return 921600;
            case 1024:
                return 1310720;
            case 2048:
            case 4096:
                return 2097152;
            case 8192:
                return 2228224;
            case 16384:
                return 5652480;
            case 32768:
            case 65536:
                return 9437184;
            default:
                return -1;
        }
    }

    @Nullable
    private static Pair<Integer, Integer> getAacCodecProfileAndLevel(String codec, String[] parts) {
        int profile;
        if (parts.length != 3) {
            String valueOf = String.valueOf(codec);
            Log.m30w(TAG, valueOf.length() != 0 ? "Ignoring malformed MP4A codec string: ".concat(valueOf) : new String("Ignoring malformed MP4A codec string: "));
            return null;
        }
        try {
            if (MimeTypes.AUDIO_AAC.equals(MimeTypes.getMimeTypeFromMp4ObjectType(Integer.parseInt(parts[1], 16))) && (profile = MP4A_AUDIO_OBJECT_TYPE_TO_PROFILE.get(Integer.parseInt(parts[2]), -1)) != -1) {
                return new Pair<>(Integer.valueOf(profile), 0);
            }
        } catch (NumberFormatException e) {
            String valueOf2 = String.valueOf(codec);
            Log.m30w(TAG, valueOf2.length() != 0 ? "Ignoring malformed MP4A codec string: ".concat(valueOf2) : new String("Ignoring malformed MP4A codec string: "));
        }
        return null;
    }

    static final /* synthetic */ int lambda$sortByScore$3$MediaCodecUtil(ScoreProvider scoreProvider, Object a, Object b) {
        return scoreProvider.getScore(b) - scoreProvider.getScore(a);
    }

    private static <T> void sortByScore(List<T> list, ScoreProvider<T> scoreProvider) {
        Collections.sort(list, new MediaCodecUtil$$Lambda$3(scoreProvider));
    }

    private interface MediaCodecListCompat {
        int getCodecCount();

        MediaCodecInfo getCodecInfoAt(int i);

        boolean isFeatureRequired(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities);

        boolean isFeatureSupported(String str, String str2, MediaCodecInfo.CodecCapabilities codecCapabilities);

        boolean secureDecodersExplicit();
    }

    private interface ScoreProvider<T> {
        int getScore(T t);
    }

    public static class DecoderQueryException extends Exception {
        private DecoderQueryException(Throwable cause) {
            super("Failed to query underlying media codecs", cause);
        }
    }

    @TargetApi(21)
    private static final class MediaCodecListCompatV21 implements MediaCodecListCompat {
        private final int codecKind;
        private MediaCodecInfo[] mediaCodecInfos;

        public MediaCodecListCompatV21(boolean includeSecure, boolean includeTunneling) {
            int i;
            if (includeSecure || includeTunneling) {
                i = 1;
            } else {
                i = 0;
            }
            this.codecKind = i;
        }

        public int getCodecCount() {
            ensureMediaCodecInfosInitialized();
            return this.mediaCodecInfos.length;
        }

        public MediaCodecInfo getCodecInfoAt(int index) {
            ensureMediaCodecInfosInitialized();
            return this.mediaCodecInfos[index];
        }

        public boolean secureDecodersExplicit() {
            return true;
        }

        public boolean isFeatureSupported(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities) {
            return capabilities.isFeatureSupported(feature);
        }

        public boolean isFeatureRequired(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities) {
            return capabilities.isFeatureRequired(feature);
        }

        private void ensureMediaCodecInfosInitialized() {
            if (this.mediaCodecInfos == null) {
                this.mediaCodecInfos = new MediaCodecList(this.codecKind).getCodecInfos();
            }
        }
    }

    private static final class MediaCodecListCompatV16 implements MediaCodecListCompat {
        private MediaCodecListCompatV16() {
        }

        public int getCodecCount() {
            return MediaCodecList.getCodecCount();
        }

        public MediaCodecInfo getCodecInfoAt(int index) {
            return MediaCodecList.getCodecInfoAt(index);
        }

        public boolean secureDecodersExplicit() {
            return false;
        }

        public boolean isFeatureSupported(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities) {
            return "secure-playback".equals(feature) && MimeTypes.VIDEO_H264.equals(mimeType);
        }

        public boolean isFeatureRequired(String feature, String mimeType, MediaCodecInfo.CodecCapabilities capabilities) {
            return false;
        }
    }

    private static final class CodecKey {
        public final String mimeType;
        public final boolean secure;
        public final boolean tunneling;

        public CodecKey(String mimeType2, boolean secure2, boolean tunneling2) {
            this.mimeType = mimeType2;
            this.secure = secure2;
            this.tunneling = tunneling2;
        }

        public int hashCode() {
            int i = 1 * 31;
            String str = this.mimeType;
            int i2 = 1231;
            int result = (((i + (str == null ? 0 : str.hashCode())) * 31) + (this.secure ? 1231 : 1237)) * 31;
            if (!this.tunneling) {
                i2 = 1237;
            }
            return result + i2;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != CodecKey.class) {
                return false;
            }
            CodecKey other = (CodecKey) obj;
            if (TextUtils.equals(this.mimeType, other.mimeType) && this.secure == other.secure && this.tunneling == other.tunneling) {
                return true;
            }
            return false;
        }
    }
}
