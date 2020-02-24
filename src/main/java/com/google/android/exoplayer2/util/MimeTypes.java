package com.google.android.exoplayer2.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.ArrayList;

public final class MimeTypes {
    public static final String APPLICATION_CAMERA_MOTION = "application/x-camera-motion";
    public static final String APPLICATION_CEA608 = "application/cea-608";
    public static final String APPLICATION_CEA708 = "application/cea-708";
    public static final String APPLICATION_DVBSUBS = "application/dvbsubs";
    public static final String APPLICATION_EMSG = "application/x-emsg";
    public static final String APPLICATION_EXIF = "application/x-exif";
    public static final String APPLICATION_ICY = "application/x-icy";
    public static final String APPLICATION_ID3 = "application/id3";
    public static final String APPLICATION_M3U8 = "application/x-mpegURL";
    public static final String APPLICATION_MP4 = "application/mp4";
    public static final String APPLICATION_MP4CEA608 = "application/x-mp4-cea-608";
    public static final String APPLICATION_MP4VTT = "application/x-mp4-vtt";
    public static final String APPLICATION_MPD = "application/dash+xml";
    public static final String APPLICATION_PGS = "application/pgs";
    public static final String APPLICATION_RAWCC = "application/x-rawcc";
    public static final String APPLICATION_SCTE35 = "application/x-scte35";
    public static final String APPLICATION_SS = "application/vnd.ms-sstr+xml";
    public static final String APPLICATION_SUBRIP = "application/x-subrip";
    public static final String APPLICATION_TTML = "application/ttml+xml";
    public static final String APPLICATION_TX3G = "application/x-quicktime-tx3g";
    public static final String APPLICATION_VOBSUB = "application/vobsub";
    public static final String APPLICATION_WEBM = "application/webm";
    public static final String AUDIO_AAC = "audio/mp4a-latm";
    public static final String AUDIO_AC3 = "audio/ac3";
    public static final String AUDIO_AC4 = "audio/ac4";
    public static final String AUDIO_ALAC = "audio/alac";
    public static final String AUDIO_ALAW = "audio/g711-alaw";
    public static final String AUDIO_AMR_NB = "audio/3gpp";
    public static final String AUDIO_AMR_WB = "audio/amr-wb";
    public static final String AUDIO_DTS = "audio/vnd.dts";
    public static final String AUDIO_DTS_EXPRESS = "audio/vnd.dts.hd;profile=lbr";
    public static final String AUDIO_DTS_HD = "audio/vnd.dts.hd";
    public static final String AUDIO_E_AC3 = "audio/eac3";
    public static final String AUDIO_E_AC3_JOC = "audio/eac3-joc";
    public static final String AUDIO_FLAC = "audio/flac";
    public static final String AUDIO_MLAW = "audio/g711-mlaw";
    public static final String AUDIO_MP4 = "audio/mp4";
    public static final String AUDIO_MPEG = "audio/mpeg";
    public static final String AUDIO_MPEG_L1 = "audio/mpeg-L1";
    public static final String AUDIO_MPEG_L2 = "audio/mpeg-L2";
    public static final String AUDIO_MSGSM = "audio/gsm";
    public static final String AUDIO_OPUS = "audio/opus";
    public static final String AUDIO_RAW = "audio/raw";
    public static final String AUDIO_TRUEHD = "audio/true-hd";
    public static final String AUDIO_UNKNOWN = "audio/x-unknown";
    public static final String AUDIO_VORBIS = "audio/vorbis";
    public static final String AUDIO_WEBM = "audio/webm";
    public static final String BASE_TYPE_APPLICATION = "application";
    public static final String BASE_TYPE_AUDIO = "audio";
    public static final String BASE_TYPE_TEXT = "text";
    public static final String BASE_TYPE_VIDEO = "video";
    public static final String TEXT_SSA = "text/x-ssa";
    public static final String TEXT_VTT = "text/vtt";
    public static final String VIDEO_AV1 = "video/av01";
    public static final String VIDEO_DIVX = "video/divx";
    public static final String VIDEO_DOLBY_VISION = "video/dolby-vision";
    public static final String VIDEO_H263 = "video/3gpp";
    public static final String VIDEO_H264 = "video/avc";
    public static final String VIDEO_H265 = "video/hevc";
    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_MP4V = "video/mp4v-es";
    public static final String VIDEO_MPEG = "video/mpeg";
    public static final String VIDEO_MPEG2 = "video/mpeg2";
    public static final String VIDEO_UNKNOWN = "video/x-unknown";
    public static final String VIDEO_VC1 = "video/wvc1";
    public static final String VIDEO_VP8 = "video/x-vnd.on2.vp8";
    public static final String VIDEO_VP9 = "video/x-vnd.on2.vp9";
    public static final String VIDEO_WEBM = "video/webm";
    private static final ArrayList<CustomMimeType> customMimeTypes = new ArrayList<>();

    public static void registerCustomMimeType(String mimeType, String codecPrefix, int trackType) {
        CustomMimeType customMimeType = new CustomMimeType(mimeType, codecPrefix, trackType);
        int customMimeTypeCount = customMimeTypes.size();
        int i = 0;
        while (true) {
            if (i >= customMimeTypeCount) {
                break;
            } else if (mimeType.equals(customMimeTypes.get(i).mimeType)) {
                customMimeTypes.remove(i);
                break;
            } else {
                i++;
            }
        }
        customMimeTypes.add(customMimeType);
    }

    public static boolean isAudio(@Nullable String mimeType) {
        return "audio".equals(getTopLevelType(mimeType));
    }

    public static boolean isVideo(@Nullable String mimeType) {
        return "video".equals(getTopLevelType(mimeType));
    }

    public static boolean isText(@Nullable String mimeType) {
        return "text".equals(getTopLevelType(mimeType));
    }

    public static boolean isApplication(@Nullable String mimeType) {
        return BASE_TYPE_APPLICATION.equals(getTopLevelType(mimeType));
    }

    @Nullable
    public static String getVideoMediaMimeType(@Nullable String codecs) {
        if (codecs == null) {
            return null;
        }
        for (String codec : Util.splitCodecs(codecs)) {
            String mimeType = getMediaMimeType(codec);
            if (mimeType != null && isVideo(mimeType)) {
                return mimeType;
            }
        }
        return null;
    }

    @Nullable
    public static String getAudioMediaMimeType(@Nullable String codecs) {
        if (codecs == null) {
            return null;
        }
        for (String codec : Util.splitCodecs(codecs)) {
            String mimeType = getMediaMimeType(codec);
            if (mimeType != null && isAudio(mimeType)) {
                return mimeType;
            }
        }
        return null;
    }

    @Nullable
    public static String getMediaMimeType(@Nullable String codec) {
        if (codec == null) {
            return null;
        }
        String codec2 = Util.toLowerInvariant(codec.trim());
        if (codec2.startsWith("avc1") || codec2.startsWith("avc3")) {
            return VIDEO_H264;
        }
        if (codec2.startsWith("hev1") || codec2.startsWith("hvc1")) {
            return VIDEO_H265;
        }
        if (codec2.startsWith("dvav") || codec2.startsWith("dva1") || codec2.startsWith("dvhe") || codec2.startsWith("dvh1")) {
            return VIDEO_DOLBY_VISION;
        }
        if (codec2.startsWith("av01")) {
            return VIDEO_AV1;
        }
        if (codec2.startsWith("vp9") || codec2.startsWith("vp09")) {
            return VIDEO_VP9;
        }
        if (codec2.startsWith("vp8") || codec2.startsWith("vp08")) {
            return VIDEO_VP8;
        }
        if (codec2.startsWith("mp4a")) {
            String mimeType = null;
            if (codec2.startsWith("mp4a.")) {
                String objectTypeString = codec2.substring(5);
                if (objectTypeString.length() >= 2) {
                    try {
                        mimeType = getMimeTypeFromMp4ObjectType(Integer.parseInt(Util.toUpperInvariant(objectTypeString.substring(0, 2)), 16));
                    } catch (NumberFormatException e) {
                    }
                }
            }
            return mimeType == null ? AUDIO_AAC : mimeType;
        } else if (codec2.startsWith("ac-3") || codec2.startsWith("dac3")) {
            return AUDIO_AC3;
        } else {
            if (codec2.startsWith("ec-3") || codec2.startsWith("dec3")) {
                return AUDIO_E_AC3;
            }
            if (codec2.startsWith("ec+3")) {
                return AUDIO_E_AC3_JOC;
            }
            if (codec2.startsWith("ac-4") || codec2.startsWith("dac4")) {
                return AUDIO_AC4;
            }
            if (codec2.startsWith("dtsc") || codec2.startsWith("dtse")) {
                return AUDIO_DTS;
            }
            if (codec2.startsWith("dtsh") || codec2.startsWith("dtsl")) {
                return AUDIO_DTS_HD;
            }
            if (codec2.startsWith("opus")) {
                return AUDIO_OPUS;
            }
            if (codec2.startsWith("vorbis")) {
                return AUDIO_VORBIS;
            }
            if (codec2.startsWith("flac")) {
                return AUDIO_FLAC;
            }
            return getCustomMimeTypeForCodec(codec2);
        }
    }

    @Nullable
    public static String getMimeTypeFromMp4ObjectType(int objectType) {
        if (objectType == 32) {
            return VIDEO_MP4V;
        }
        if (objectType == 33) {
            return VIDEO_H264;
        }
        if (objectType == 35) {
            return VIDEO_H265;
        }
        if (objectType == 64) {
            return AUDIO_AAC;
        }
        if (objectType == 163) {
            return VIDEO_VC1;
        }
        if (objectType == 177) {
            return VIDEO_VP9;
        }
        if (objectType == 165) {
            return AUDIO_AC3;
        }
        if (objectType == 166) {
            return AUDIO_E_AC3;
        }
        switch (objectType) {
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
                return VIDEO_MPEG2;
            case 102:
            case 103:
            case 104:
                return AUDIO_AAC;
            case 105:
            case 107:
                return AUDIO_MPEG;
            case 106:
                return VIDEO_MPEG;
            default:
                switch (objectType) {
                    case ClientAnalytics.LogRequest.LogSource.FITNESS_ANDROID_VALUE /*169*/:
                    case 172:
                        return AUDIO_DTS;
                    case ClientAnalytics.LogRequest.LogSource.FITNESS_GMS_CORE_VALUE /*170*/:
                    case ClientAnalytics.LogRequest.LogSource.YT_CREATOR_ANDROID_PRIMES_VALUE /*171*/:
                        return AUDIO_DTS_HD;
                    case ClientAnalytics.LogRequest.LogSource.AUDITOR_VALUE /*173*/:
                        return AUDIO_OPUS;
                    case ClientAnalytics.LogRequest.LogSource.NQLOOKUP_VALUE /*174*/:
                        return AUDIO_AC4;
                    default:
                        return null;
                }
        }
    }

    public static int getTrackType(@Nullable String mimeType) {
        if (TextUtils.isEmpty(mimeType)) {
            return -1;
        }
        if (isAudio(mimeType)) {
            return 1;
        }
        if (isVideo(mimeType)) {
            return 2;
        }
        if (isText(mimeType) || APPLICATION_CEA608.equals(mimeType) || APPLICATION_CEA708.equals(mimeType) || APPLICATION_MP4CEA608.equals(mimeType) || APPLICATION_SUBRIP.equals(mimeType) || APPLICATION_TTML.equals(mimeType) || APPLICATION_TX3G.equals(mimeType) || APPLICATION_MP4VTT.equals(mimeType) || APPLICATION_RAWCC.equals(mimeType) || APPLICATION_VOBSUB.equals(mimeType) || APPLICATION_PGS.equals(mimeType) || APPLICATION_DVBSUBS.equals(mimeType)) {
            return 3;
        }
        if (APPLICATION_ID3.equals(mimeType) || APPLICATION_EMSG.equals(mimeType) || APPLICATION_SCTE35.equals(mimeType)) {
            return 4;
        }
        if (APPLICATION_CAMERA_MOTION.equals(mimeType)) {
            return 5;
        }
        return getTrackTypeForCustomMimeType(mimeType);
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getEncoding(String mimeType) {
        char c;
        switch (mimeType.hashCode()) {
            case -2123537834:
                if (mimeType.equals(AUDIO_E_AC3_JOC)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1095064472:
                if (mimeType.equals(AUDIO_DTS)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 187078296:
                if (mimeType.equals(AUDIO_AC3)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 187078297:
                if (mimeType.equals(AUDIO_AC4)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 1504578661:
                if (mimeType.equals(AUDIO_E_AC3)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1505942594:
                if (mimeType.equals(AUDIO_DTS_HD)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1556697186:
                if (mimeType.equals(AUDIO_TRUEHD)) {
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
                return 5;
            case 1:
            case 2:
                return 6;
            case 3:
                return 17;
            case 4:
                return 7;
            case 5:
                return 8;
            case 6:
                return 14;
            default:
                return 0;
        }
    }

    public static int getTrackTypeOfCodec(String codec) {
        return getTrackType(getMediaMimeType(codec));
    }

    @Nullable
    private static String getTopLevelType(@Nullable String mimeType) {
        int indexOfSlash;
        if (mimeType == null || (indexOfSlash = mimeType.indexOf(47)) == -1) {
            return null;
        }
        return mimeType.substring(0, indexOfSlash);
    }

    @Nullable
    private static String getCustomMimeTypeForCodec(String codec) {
        int customMimeTypeCount = customMimeTypes.size();
        for (int i = 0; i < customMimeTypeCount; i++) {
            CustomMimeType customMimeType = customMimeTypes.get(i);
            if (codec.startsWith(customMimeType.codecPrefix)) {
                return customMimeType.mimeType;
            }
        }
        return null;
    }

    private static int getTrackTypeForCustomMimeType(String mimeType) {
        int customMimeTypeCount = customMimeTypes.size();
        for (int i = 0; i < customMimeTypeCount; i++) {
            CustomMimeType customMimeType = customMimeTypes.get(i);
            if (mimeType.equals(customMimeType.mimeType)) {
                return customMimeType.trackType;
            }
        }
        return -1;
    }

    private MimeTypes() {
    }

    private static final class CustomMimeType {
        public final String codecPrefix;
        public final String mimeType;
        public final int trackType;

        public CustomMimeType(String mimeType2, String codecPrefix2, int trackType2) {
            this.mimeType = mimeType2;
            this.codecPrefix = codecPrefix2;
            this.trackType = trackType2;
        }
    }
}
