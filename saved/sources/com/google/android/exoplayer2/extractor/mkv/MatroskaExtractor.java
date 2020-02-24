package com.google.android.exoplayer2.extractor.mkv;

import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.util.SparseArray;
import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ChunkIndex;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.NalUnitUtil;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.primitives.UnsignedBytes;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MatroskaExtractor implements Extractor {
    private static final int BLOCK_STATE_DATA = 2;
    private static final int BLOCK_STATE_HEADER = 1;
    private static final int BLOCK_STATE_START = 0;
    private static final String CODEC_ID_AAC = "A_AAC";
    private static final String CODEC_ID_AC3 = "A_AC3";
    private static final String CODEC_ID_ACM = "A_MS/ACM";
    private static final String CODEC_ID_ASS = "S_TEXT/ASS";
    private static final String CODEC_ID_AV1 = "V_AV1";
    private static final String CODEC_ID_DTS = "A_DTS";
    private static final String CODEC_ID_DTS_EXPRESS = "A_DTS/EXPRESS";
    private static final String CODEC_ID_DTS_LOSSLESS = "A_DTS/LOSSLESS";
    private static final String CODEC_ID_DVBSUB = "S_DVBSUB";
    private static final String CODEC_ID_E_AC3 = "A_EAC3";
    private static final String CODEC_ID_FLAC = "A_FLAC";
    private static final String CODEC_ID_FOURCC = "V_MS/VFW/FOURCC";
    private static final String CODEC_ID_H264 = "V_MPEG4/ISO/AVC";
    private static final String CODEC_ID_H265 = "V_MPEGH/ISO/HEVC";
    private static final String CODEC_ID_MP2 = "A_MPEG/L2";
    private static final String CODEC_ID_MP3 = "A_MPEG/L3";
    private static final String CODEC_ID_MPEG2 = "V_MPEG2";
    private static final String CODEC_ID_MPEG4_AP = "V_MPEG4/ISO/AP";
    private static final String CODEC_ID_MPEG4_ASP = "V_MPEG4/ISO/ASP";
    private static final String CODEC_ID_MPEG4_SP = "V_MPEG4/ISO/SP";
    private static final String CODEC_ID_OPUS = "A_OPUS";
    private static final String CODEC_ID_PCM_INT_LIT = "A_PCM/INT/LIT";
    private static final String CODEC_ID_PGS = "S_HDMV/PGS";
    private static final String CODEC_ID_SUBRIP = "S_TEXT/UTF8";
    private static final String CODEC_ID_THEORA = "V_THEORA";
    private static final String CODEC_ID_TRUEHD = "A_TRUEHD";
    private static final String CODEC_ID_VOBSUB = "S_VOBSUB";
    private static final String CODEC_ID_VORBIS = "A_VORBIS";
    private static final String CODEC_ID_VP8 = "V_VP8";
    private static final String CODEC_ID_VP9 = "V_VP9";
    private static final String DOC_TYPE_MATROSKA = "matroska";
    private static final String DOC_TYPE_WEBM = "webm";
    private static final int ENCRYPTION_IV_SIZE = 8;
    public static final ExtractorsFactory FACTORY = MatroskaExtractor$$Lambda$0.$instance;
    public static final int FLAG_DISABLE_SEEK_FOR_CUES = 1;
    private static final int FOURCC_COMPRESSION_DIVX = 1482049860;
    private static final int FOURCC_COMPRESSION_H263 = 859189832;
    private static final int FOURCC_COMPRESSION_VC1 = 826496599;
    private static final int ID_AUDIO = 225;
    private static final int ID_AUDIO_BIT_DEPTH = 25188;
    private static final int ID_BLOCK = 161;
    private static final int ID_BLOCK_DURATION = 155;
    private static final int ID_BLOCK_GROUP = 160;
    private static final int ID_CHANNELS = 159;
    private static final int ID_CLUSTER = 524531317;
    private static final int ID_CODEC_DELAY = 22186;
    private static final int ID_CODEC_ID = 134;
    private static final int ID_CODEC_PRIVATE = 25506;
    private static final int ID_COLOUR = 21936;
    private static final int ID_COLOUR_PRIMARIES = 21947;
    private static final int ID_COLOUR_RANGE = 21945;
    private static final int ID_COLOUR_TRANSFER = 21946;
    private static final int ID_CONTENT_COMPRESSION = 20532;
    private static final int ID_CONTENT_COMPRESSION_ALGORITHM = 16980;
    private static final int ID_CONTENT_COMPRESSION_SETTINGS = 16981;
    private static final int ID_CONTENT_ENCODING = 25152;
    private static final int ID_CONTENT_ENCODINGS = 28032;
    private static final int ID_CONTENT_ENCODING_ORDER = 20529;
    private static final int ID_CONTENT_ENCODING_SCOPE = 20530;
    private static final int ID_CONTENT_ENCRYPTION = 20533;
    private static final int ID_CONTENT_ENCRYPTION_AES_SETTINGS = 18407;
    private static final int ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE = 18408;
    private static final int ID_CONTENT_ENCRYPTION_ALGORITHM = 18401;
    private static final int ID_CONTENT_ENCRYPTION_KEY_ID = 18402;
    private static final int ID_CUES = 475249515;
    private static final int ID_CUE_CLUSTER_POSITION = 241;
    private static final int ID_CUE_POINT = 187;
    private static final int ID_CUE_TIME = 179;
    private static final int ID_CUE_TRACK_POSITIONS = 183;
    private static final int ID_DEFAULT_DURATION = 2352003;
    private static final int ID_DISPLAY_HEIGHT = 21690;
    private static final int ID_DISPLAY_UNIT = 21682;
    private static final int ID_DISPLAY_WIDTH = 21680;
    private static final int ID_DOC_TYPE = 17026;
    private static final int ID_DOC_TYPE_READ_VERSION = 17029;
    private static final int ID_DURATION = 17545;
    private static final int ID_EBML = 440786851;
    private static final int ID_EBML_READ_VERSION = 17143;
    private static final int ID_FLAG_DEFAULT = 136;
    private static final int ID_FLAG_FORCED = 21930;
    private static final int ID_INFO = 357149030;
    private static final int ID_LANGUAGE = 2274716;
    private static final int ID_LUMNINANCE_MAX = 21977;
    private static final int ID_LUMNINANCE_MIN = 21978;
    private static final int ID_MASTERING_METADATA = 21968;
    private static final int ID_MAX_CLL = 21948;
    private static final int ID_MAX_FALL = 21949;
    private static final int ID_NAME = 21358;
    private static final int ID_PIXEL_HEIGHT = 186;
    private static final int ID_PIXEL_WIDTH = 176;
    private static final int ID_PRIMARY_B_CHROMATICITY_X = 21973;
    private static final int ID_PRIMARY_B_CHROMATICITY_Y = 21974;
    private static final int ID_PRIMARY_G_CHROMATICITY_X = 21971;
    private static final int ID_PRIMARY_G_CHROMATICITY_Y = 21972;
    private static final int ID_PRIMARY_R_CHROMATICITY_X = 21969;
    private static final int ID_PRIMARY_R_CHROMATICITY_Y = 21970;
    private static final int ID_PROJECTION = 30320;
    private static final int ID_PROJECTION_POSE_PITCH = 30324;
    private static final int ID_PROJECTION_POSE_ROLL = 30325;
    private static final int ID_PROJECTION_POSE_YAW = 30323;
    private static final int ID_PROJECTION_PRIVATE = 30322;
    private static final int ID_PROJECTION_TYPE = 30321;
    private static final int ID_REFERENCE_BLOCK = 251;
    private static final int ID_SAMPLING_FREQUENCY = 181;
    private static final int ID_SEEK = 19899;
    private static final int ID_SEEK_HEAD = 290298740;
    private static final int ID_SEEK_ID = 21419;
    private static final int ID_SEEK_POSITION = 21420;
    private static final int ID_SEEK_PRE_ROLL = 22203;
    private static final int ID_SEGMENT = 408125543;
    private static final int ID_SEGMENT_INFO = 357149030;
    private static final int ID_SIMPLE_BLOCK = 163;
    private static final int ID_STEREO_MODE = 21432;
    private static final int ID_TIMECODE_SCALE = 2807729;
    private static final int ID_TIME_CODE = 231;
    private static final int ID_TRACKS = 374648427;
    private static final int ID_TRACK_ENTRY = 174;
    private static final int ID_TRACK_NUMBER = 215;
    private static final int ID_TRACK_TYPE = 131;
    private static final int ID_VIDEO = 224;
    private static final int ID_WHITE_POINT_CHROMATICITY_X = 21975;
    private static final int ID_WHITE_POINT_CHROMATICITY_Y = 21976;
    private static final int LACING_EBML = 3;
    private static final int LACING_FIXED_SIZE = 2;
    private static final int LACING_NONE = 0;
    private static final int LACING_XIPH = 1;
    private static final int OPUS_MAX_INPUT_SIZE = 5760;
    /* access modifiers changed from: private */
    public static final byte[] SSA_DIALOGUE_FORMAT = Util.getUtf8Bytes("Format: Start, End, ReadOrder, Layer, Style, Name, MarginL, MarginR, MarginV, Effect, Text");
    private static final byte[] SSA_PREFIX = {68, 105, 97, 108, 111, 103, 117, 101, 58, 32, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 58, 48, 48, 58, 48, 48, 58, 48, 48, 44};
    private static final int SSA_PREFIX_END_TIMECODE_OFFSET = 21;
    private static final byte[] SSA_TIMECODE_EMPTY = {32, 32, 32, 32, 32, 32, 32, 32, 32, 32};
    private static final String SSA_TIMECODE_FORMAT = "%01d:%02d:%02d:%02d";
    private static final long SSA_TIMECODE_LAST_VALUE_SCALING_FACTOR = 10000;
    private static final byte[] SUBRIP_PREFIX = {49, 10, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 48, 48, 32, 45, 45, 62, 32, 48, 48, 58, 48, 48, 58, 48, 48, 44, 48, 48, 48, 10};
    private static final int SUBRIP_PREFIX_END_TIMECODE_OFFSET = 19;
    private static final byte[] SUBRIP_TIMECODE_EMPTY = {32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32};
    private static final String SUBRIP_TIMECODE_FORMAT = "%02d:%02d:%02d,%03d";
    private static final long SUBRIP_TIMECODE_LAST_VALUE_SCALING_FACTOR = 1000;
    private static final String TAG = "MatroskaExtractor";
    private static final int TRACK_TYPE_AUDIO = 2;
    private static final int UNSET_ENTRY_ID = -1;
    private static final int VORBIS_MAX_INPUT_SIZE = 8192;
    private static final int WAVE_FORMAT_EXTENSIBLE = 65534;
    private static final int WAVE_FORMAT_PCM = 1;
    private static final int WAVE_FORMAT_SIZE = 18;
    /* access modifiers changed from: private */
    public static final UUID WAVE_SUBFORMAT_PCM = new UUID(72057594037932032L, -9223371306706625679L);
    private long blockDurationUs;
    private int blockFlags;
    private int blockLacingSampleCount;
    private int blockLacingSampleIndex;
    private int[] blockLacingSampleSizes;
    private int blockState;
    private long blockTimeUs;
    private int blockTrackNumber;
    private int blockTrackNumberLength;
    private long clusterTimecodeUs;
    private LongArray cueClusterPositions;
    private LongArray cueTimesUs;
    private long cuesContentPosition;
    private Track currentTrack;
    private long durationTimecode;
    private long durationUs;
    private final ParsableByteArray encryptionInitializationVector;
    private final ParsableByteArray encryptionSubsampleData;
    private ByteBuffer encryptionSubsampleDataBuffer;
    private ExtractorOutput extractorOutput;
    private final ParsableByteArray nalLength;
    private final ParsableByteArray nalStartCode;
    private final EbmlReader reader;
    private int sampleBytesRead;
    private int sampleBytesWritten;
    private int sampleCurrentNalBytesRemaining;
    private boolean sampleEncodingHandled;
    private boolean sampleInitializationVectorRead;
    private int samplePartitionCount;
    private boolean samplePartitionCountRead;
    private boolean sampleRead;
    private boolean sampleSeenReferenceBlock;
    private byte sampleSignalByte;
    private boolean sampleSignalByteRead;
    private final ParsableByteArray sampleStrippedBytes;
    private final ParsableByteArray scratch;
    private int seekEntryId;
    private final ParsableByteArray seekEntryIdBytes;
    private long seekEntryPosition;
    private boolean seekForCues;
    private final boolean seekForCuesEnabled;
    private long seekPositionAfterBuildingCues;
    private boolean seenClusterPositionForCurrentCuePoint;
    private long segmentContentPosition;
    private long segmentContentSize;
    private boolean sentSeekMap;
    private final ParsableByteArray subtitleSample;
    private long timecodeScale;
    private final SparseArray<Track> tracks;
    private final VarintReader varintReader;
    private final ParsableByteArray vorbisNumPageSamples;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    static final /* synthetic */ Extractor[] lambda$static$0$MatroskaExtractor() {
        return new Extractor[]{new MatroskaExtractor()};
    }

    public MatroskaExtractor() {
        this(0);
    }

    public MatroskaExtractor(int flags) {
        this(new DefaultEbmlReader(), flags);
    }

    MatroskaExtractor(EbmlReader reader2, int flags) {
        this.segmentContentPosition = -1;
        this.timecodeScale = C0841C.TIME_UNSET;
        this.durationTimecode = C0841C.TIME_UNSET;
        this.durationUs = C0841C.TIME_UNSET;
        this.cuesContentPosition = -1;
        this.seekPositionAfterBuildingCues = -1;
        this.clusterTimecodeUs = C0841C.TIME_UNSET;
        this.reader = reader2;
        this.reader.init(new InnerEbmlProcessor());
        this.seekForCuesEnabled = (flags & 1) == 0;
        this.varintReader = new VarintReader();
        this.tracks = new SparseArray<>();
        this.scratch = new ParsableByteArray(4);
        this.vorbisNumPageSamples = new ParsableByteArray(ByteBuffer.allocate(4).putInt(-1).array());
        this.seekEntryIdBytes = new ParsableByteArray(4);
        this.nalStartCode = new ParsableByteArray(NalUnitUtil.NAL_START_CODE);
        this.nalLength = new ParsableByteArray(4);
        this.sampleStrippedBytes = new ParsableByteArray();
        this.subtitleSample = new ParsableByteArray();
        this.encryptionInitializationVector = new ParsableByteArray(8);
        this.encryptionSubsampleData = new ParsableByteArray();
    }

    public final boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        return new Sniffer().sniff(input);
    }

    public final void init(ExtractorOutput output) {
        this.extractorOutput = output;
    }

    @CallSuper
    public void seek(long position, long timeUs) {
        this.clusterTimecodeUs = C0841C.TIME_UNSET;
        this.blockState = 0;
        this.reader.reset();
        this.varintReader.reset();
        resetSample();
        for (int i = 0; i < this.tracks.size(); i++) {
            this.tracks.valueAt(i).reset();
        }
    }

    public final void release() {
    }

    public final int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        this.sampleRead = false;
        boolean continueReading = true;
        while (continueReading && !this.sampleRead) {
            continueReading = this.reader.read(input);
            if (continueReading && maybeSeekForCues(seekPosition, input.getPosition())) {
                return 1;
            }
        }
        if (continueReading) {
            return 0;
        }
        for (int i = 0; i < this.tracks.size(); i++) {
            this.tracks.valueAt(i).outputPendingSampleMetadata();
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public int getElementType(int id) {
        switch (id) {
            case 131:
            case 136:
            case 155:
            case 159:
            case 176:
            case 179:
            case 186:
            case 215:
            case 231:
            case 241:
            case 251:
            case ID_CONTENT_COMPRESSION_ALGORITHM /*16980*/:
            case ID_DOC_TYPE_READ_VERSION /*17029*/:
            case ID_EBML_READ_VERSION /*17143*/:
            case ID_CONTENT_ENCRYPTION_ALGORITHM /*18401*/:
            case ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE /*18408*/:
            case ID_CONTENT_ENCODING_ORDER /*20529*/:
            case ID_CONTENT_ENCODING_SCOPE /*20530*/:
            case ID_SEEK_POSITION /*21420*/:
            case ID_STEREO_MODE /*21432*/:
            case ID_DISPLAY_WIDTH /*21680*/:
            case ID_DISPLAY_UNIT /*21682*/:
            case ID_DISPLAY_HEIGHT /*21690*/:
            case ID_FLAG_FORCED /*21930*/:
            case ID_COLOUR_RANGE /*21945*/:
            case ID_COLOUR_TRANSFER /*21946*/:
            case ID_COLOUR_PRIMARIES /*21947*/:
            case ID_MAX_CLL /*21948*/:
            case ID_MAX_FALL /*21949*/:
            case ID_CODEC_DELAY /*22186*/:
            case ID_SEEK_PRE_ROLL /*22203*/:
            case ID_AUDIO_BIT_DEPTH /*25188*/:
            case ID_PROJECTION_TYPE /*30321*/:
            case ID_DEFAULT_DURATION /*2352003*/:
            case ID_TIMECODE_SCALE /*2807729*/:
                return 2;
            case 134:
            case ID_DOC_TYPE /*17026*/:
            case ID_NAME /*21358*/:
            case ID_LANGUAGE /*2274716*/:
                return 3;
            case 160:
            case 174:
            case 183:
            case 187:
            case 224:
            case 225:
            case ID_CONTENT_ENCRYPTION_AES_SETTINGS /*18407*/:
            case ID_SEEK /*19899*/:
            case ID_CONTENT_COMPRESSION /*20532*/:
            case ID_CONTENT_ENCRYPTION /*20533*/:
            case ID_COLOUR /*21936*/:
            case ID_MASTERING_METADATA /*21968*/:
            case ID_CONTENT_ENCODING /*25152*/:
            case ID_CONTENT_ENCODINGS /*28032*/:
            case ID_PROJECTION /*30320*/:
            case ID_SEEK_HEAD /*290298740*/:
            case 357149030:
            case ID_TRACKS /*374648427*/:
            case ID_SEGMENT /*408125543*/:
            case ID_EBML /*440786851*/:
            case ID_CUES /*475249515*/:
            case ID_CLUSTER /*524531317*/:
                return 1;
            case 161:
            case 163:
            case ID_CONTENT_COMPRESSION_SETTINGS /*16981*/:
            case ID_CONTENT_ENCRYPTION_KEY_ID /*18402*/:
            case ID_SEEK_ID /*21419*/:
            case ID_CODEC_PRIVATE /*25506*/:
            case ID_PROJECTION_PRIVATE /*30322*/:
                return 4;
            case 181:
            case ID_DURATION /*17545*/:
            case ID_PRIMARY_R_CHROMATICITY_X /*21969*/:
            case ID_PRIMARY_R_CHROMATICITY_Y /*21970*/:
            case ID_PRIMARY_G_CHROMATICITY_X /*21971*/:
            case ID_PRIMARY_G_CHROMATICITY_Y /*21972*/:
            case ID_PRIMARY_B_CHROMATICITY_X /*21973*/:
            case ID_PRIMARY_B_CHROMATICITY_Y /*21974*/:
            case ID_WHITE_POINT_CHROMATICITY_X /*21975*/:
            case ID_WHITE_POINT_CHROMATICITY_Y /*21976*/:
            case ID_LUMNINANCE_MAX /*21977*/:
            case ID_LUMNINANCE_MIN /*21978*/:
            case ID_PROJECTION_POSE_YAW /*30323*/:
            case ID_PROJECTION_POSE_PITCH /*30324*/:
            case ID_PROJECTION_POSE_ROLL /*30325*/:
                return 5;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public boolean isLevel1Element(int id) {
        return id == 357149030 || id == ID_CLUSTER || id == ID_CUES || id == ID_TRACKS;
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void startMasterElement(int id, long contentPosition, long contentSize) throws ParserException {
        if (id == 160) {
            this.sampleSeenReferenceBlock = false;
        } else if (id == 174) {
            this.currentTrack = new Track();
        } else if (id == 187) {
            this.seenClusterPositionForCurrentCuePoint = false;
        } else if (id == ID_SEEK) {
            this.seekEntryId = -1;
            this.seekEntryPosition = -1;
        } else if (id == ID_CONTENT_ENCRYPTION) {
            this.currentTrack.hasContentEncryption = true;
        } else if (id == ID_MASTERING_METADATA) {
            this.currentTrack.hasColorInfo = true;
        } else if (id == ID_CONTENT_ENCODING) {
        } else {
            if (id == ID_SEGMENT) {
                long j = this.segmentContentPosition;
                if (j == -1 || j == contentPosition) {
                    this.segmentContentPosition = contentPosition;
                    this.segmentContentSize = contentSize;
                    return;
                }
                throw new ParserException("Multiple Segment elements not supported");
            } else if (id == ID_CUES) {
                this.cueTimesUs = new LongArray();
                this.cueClusterPositions = new LongArray();
            } else if (id != ID_CLUSTER || this.sentSeekMap) {
            } else {
                if (!this.seekForCuesEnabled || this.cuesContentPosition == -1) {
                    this.extractorOutput.seekMap(new SeekMap.Unseekable(this.durationUs));
                    this.sentSeekMap = true;
                    return;
                }
                this.seekForCues = true;
            }
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void endMasterElement(int id) throws ParserException {
        if (id != 160) {
            if (id == 174) {
                if (isCodecSupported(this.currentTrack.codecId)) {
                    Track track = this.currentTrack;
                    track.initializeOutput(this.extractorOutput, track.number);
                    this.tracks.put(this.currentTrack.number, this.currentTrack);
                }
                this.currentTrack = null;
            } else if (id == ID_SEEK) {
                int i = this.seekEntryId;
                if (i != -1) {
                    long j = this.seekEntryPosition;
                    if (j != -1) {
                        if (i == ID_CUES) {
                            this.cuesContentPosition = j;
                            return;
                        }
                        return;
                    }
                }
                throw new ParserException("Mandatory element SeekID or SeekPosition not found");
            } else if (id != ID_CONTENT_ENCODING) {
                if (id != ID_CONTENT_ENCODINGS) {
                    if (id == 357149030) {
                        if (this.timecodeScale == C0841C.TIME_UNSET) {
                            this.timecodeScale = 1000000;
                        }
                        long j2 = this.durationTimecode;
                        if (j2 != C0841C.TIME_UNSET) {
                            this.durationUs = scaleTimecodeToUs(j2);
                        }
                    } else if (id != ID_TRACKS) {
                        if (id == ID_CUES && !this.sentSeekMap) {
                            this.extractorOutput.seekMap(buildSeekMap());
                            this.sentSeekMap = true;
                        }
                    } else if (this.tracks.size() != 0) {
                        this.extractorOutput.endTracks();
                    } else {
                        throw new ParserException("No valid tracks were found");
                    }
                } else if (this.currentTrack.hasContentEncryption && this.currentTrack.sampleStrippedBytes != null) {
                    throw new ParserException("Combining encryption and compression is not supported");
                }
            } else if (!this.currentTrack.hasContentEncryption) {
            } else {
                if (this.currentTrack.cryptoData != null) {
                    this.currentTrack.drmInitData = new DrmInitData(new DrmInitData.SchemeData(C0841C.UUID_NIL, MimeTypes.VIDEO_WEBM, this.currentTrack.cryptoData.encryptionKey));
                    return;
                }
                throw new ParserException("Encrypted Track found but ContentEncKeyID was not found");
            }
        } else if (this.blockState == 2) {
            if (!this.sampleSeenReferenceBlock) {
                this.blockFlags |= 1;
            }
            commitSampleToOutput(this.tracks.get(this.blockTrackNumber), this.blockTimeUs);
            this.blockState = 0;
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void integerElement(int id, long value) throws ParserException {
        if (id != ID_CONTENT_ENCODING_ORDER) {
            if (id != ID_CONTENT_ENCODING_SCOPE) {
                boolean z = false;
                switch (id) {
                    case 131:
                        this.currentTrack.type = (int) value;
                        return;
                    case 136:
                        Track track = this.currentTrack;
                        if (value == 1) {
                            z = true;
                        }
                        track.flagDefault = z;
                        return;
                    case 155:
                        this.blockDurationUs = scaleTimecodeToUs(value);
                        return;
                    case 159:
                        this.currentTrack.channelCount = (int) value;
                        return;
                    case 176:
                        this.currentTrack.width = (int) value;
                        return;
                    case 179:
                        this.cueTimesUs.add(scaleTimecodeToUs(value));
                        return;
                    case 186:
                        this.currentTrack.height = (int) value;
                        return;
                    case 215:
                        this.currentTrack.number = (int) value;
                        return;
                    case 231:
                        this.clusterTimecodeUs = scaleTimecodeToUs(value);
                        return;
                    case 241:
                        if (!this.seenClusterPositionForCurrentCuePoint) {
                            this.cueClusterPositions.add(value);
                            this.seenClusterPositionForCurrentCuePoint = true;
                            return;
                        }
                        return;
                    case 251:
                        this.sampleSeenReferenceBlock = true;
                        return;
                    case ID_CONTENT_COMPRESSION_ALGORITHM /*16980*/:
                        if (value != 3) {
                            StringBuilder sb = new StringBuilder(50);
                            sb.append("ContentCompAlgo ");
                            sb.append(value);
                            sb.append(" not supported");
                            throw new ParserException(sb.toString());
                        }
                        return;
                    case ID_DOC_TYPE_READ_VERSION /*17029*/:
                        if (value < 1 || value > 2) {
                            StringBuilder sb2 = new StringBuilder(53);
                            sb2.append("DocTypeReadVersion ");
                            sb2.append(value);
                            sb2.append(" not supported");
                            throw new ParserException(sb2.toString());
                        }
                        return;
                    case ID_EBML_READ_VERSION /*17143*/:
                        if (value != 1) {
                            StringBuilder sb3 = new StringBuilder(50);
                            sb3.append("EBMLReadVersion ");
                            sb3.append(value);
                            sb3.append(" not supported");
                            throw new ParserException(sb3.toString());
                        }
                        return;
                    case ID_CONTENT_ENCRYPTION_ALGORITHM /*18401*/:
                        if (value != 5) {
                            StringBuilder sb4 = new StringBuilder(49);
                            sb4.append("ContentEncAlgo ");
                            sb4.append(value);
                            sb4.append(" not supported");
                            throw new ParserException(sb4.toString());
                        }
                        return;
                    case ID_CONTENT_ENCRYPTION_AES_SETTINGS_CIPHER_MODE /*18408*/:
                        if (value != 1) {
                            StringBuilder sb5 = new StringBuilder(56);
                            sb5.append("AESSettingsCipherMode ");
                            sb5.append(value);
                            sb5.append(" not supported");
                            throw new ParserException(sb5.toString());
                        }
                        return;
                    case ID_SEEK_POSITION /*21420*/:
                        this.seekEntryPosition = this.segmentContentPosition + value;
                        return;
                    case ID_STEREO_MODE /*21432*/:
                        int layout = (int) value;
                        if (layout == 0) {
                            this.currentTrack.stereoMode = 0;
                            return;
                        } else if (layout == 1) {
                            this.currentTrack.stereoMode = 2;
                            return;
                        } else if (layout == 3) {
                            this.currentTrack.stereoMode = 1;
                            return;
                        } else if (layout == 15) {
                            this.currentTrack.stereoMode = 3;
                            return;
                        } else {
                            return;
                        }
                    case ID_DISPLAY_WIDTH /*21680*/:
                        this.currentTrack.displayWidth = (int) value;
                        return;
                    case ID_DISPLAY_UNIT /*21682*/:
                        this.currentTrack.displayUnit = (int) value;
                        return;
                    case ID_DISPLAY_HEIGHT /*21690*/:
                        this.currentTrack.displayHeight = (int) value;
                        return;
                    case ID_FLAG_FORCED /*21930*/:
                        Track track2 = this.currentTrack;
                        if (value == 1) {
                            z = true;
                        }
                        track2.flagForced = z;
                        return;
                    case ID_CODEC_DELAY /*22186*/:
                        this.currentTrack.codecDelayNs = value;
                        return;
                    case ID_SEEK_PRE_ROLL /*22203*/:
                        this.currentTrack.seekPreRollNs = value;
                        return;
                    case ID_AUDIO_BIT_DEPTH /*25188*/:
                        this.currentTrack.audioBitDepth = (int) value;
                        return;
                    case ID_PROJECTION_TYPE /*30321*/:
                        int i = (int) value;
                        if (i == 0) {
                            this.currentTrack.projectionType = 0;
                            return;
                        } else if (i == 1) {
                            this.currentTrack.projectionType = 1;
                            return;
                        } else if (i == 2) {
                            this.currentTrack.projectionType = 2;
                            return;
                        } else if (i == 3) {
                            this.currentTrack.projectionType = 3;
                            return;
                        } else {
                            return;
                        }
                    case ID_DEFAULT_DURATION /*2352003*/:
                        this.currentTrack.defaultSampleDurationNs = (int) value;
                        return;
                    case ID_TIMECODE_SCALE /*2807729*/:
                        this.timecodeScale = value;
                        return;
                    default:
                        switch (id) {
                            case ID_COLOUR_RANGE /*21945*/:
                                int i2 = (int) value;
                                if (i2 == 1) {
                                    this.currentTrack.colorRange = 2;
                                    return;
                                } else if (i2 == 2) {
                                    this.currentTrack.colorRange = 1;
                                    return;
                                } else {
                                    return;
                                }
                            case ID_COLOUR_TRANSFER /*21946*/:
                                int i3 = (int) value;
                                if (i3 != 1) {
                                    if (i3 == 16) {
                                        this.currentTrack.colorTransfer = 6;
                                        return;
                                    } else if (i3 == 18) {
                                        this.currentTrack.colorTransfer = 7;
                                        return;
                                    } else if (!(i3 == 6 || i3 == 7)) {
                                        return;
                                    }
                                }
                                this.currentTrack.colorTransfer = 3;
                                return;
                            case ID_COLOUR_PRIMARIES /*21947*/:
                                Track track3 = this.currentTrack;
                                track3.hasColorInfo = true;
                                int i4 = (int) value;
                                if (i4 == 1) {
                                    track3.colorSpace = 1;
                                    return;
                                } else if (i4 == 9) {
                                    track3.colorSpace = 6;
                                    return;
                                } else if (i4 == 4 || i4 == 5 || i4 == 6 || i4 == 7) {
                                    this.currentTrack.colorSpace = 2;
                                    return;
                                } else {
                                    return;
                                }
                            case ID_MAX_CLL /*21948*/:
                                this.currentTrack.maxContentLuminance = (int) value;
                                return;
                            case ID_MAX_FALL /*21949*/:
                                this.currentTrack.maxFrameAverageLuminance = (int) value;
                                return;
                            default:
                                return;
                        }
                }
            } else if (value != 1) {
                StringBuilder sb6 = new StringBuilder(55);
                sb6.append("ContentEncodingScope ");
                sb6.append(value);
                sb6.append(" not supported");
                throw new ParserException(sb6.toString());
            }
        } else if (value != 0) {
            StringBuilder sb7 = new StringBuilder(55);
            sb7.append("ContentEncodingOrder ");
            sb7.append(value);
            sb7.append(" not supported");
            throw new ParserException(sb7.toString());
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void floatElement(int id, double value) throws ParserException {
        if (id == 181) {
            this.currentTrack.sampleRate = (int) value;
        } else if (id != ID_DURATION) {
            switch (id) {
                case ID_PRIMARY_R_CHROMATICITY_X /*21969*/:
                    this.currentTrack.primaryRChromaticityX = (float) value;
                    return;
                case ID_PRIMARY_R_CHROMATICITY_Y /*21970*/:
                    this.currentTrack.primaryRChromaticityY = (float) value;
                    return;
                case ID_PRIMARY_G_CHROMATICITY_X /*21971*/:
                    this.currentTrack.primaryGChromaticityX = (float) value;
                    return;
                case ID_PRIMARY_G_CHROMATICITY_Y /*21972*/:
                    this.currentTrack.primaryGChromaticityY = (float) value;
                    return;
                case ID_PRIMARY_B_CHROMATICITY_X /*21973*/:
                    this.currentTrack.primaryBChromaticityX = (float) value;
                    return;
                case ID_PRIMARY_B_CHROMATICITY_Y /*21974*/:
                    this.currentTrack.primaryBChromaticityY = (float) value;
                    return;
                case ID_WHITE_POINT_CHROMATICITY_X /*21975*/:
                    this.currentTrack.whitePointChromaticityX = (float) value;
                    return;
                case ID_WHITE_POINT_CHROMATICITY_Y /*21976*/:
                    this.currentTrack.whitePointChromaticityY = (float) value;
                    return;
                case ID_LUMNINANCE_MAX /*21977*/:
                    this.currentTrack.maxMasteringLuminance = (float) value;
                    return;
                case ID_LUMNINANCE_MIN /*21978*/:
                    this.currentTrack.minMasteringLuminance = (float) value;
                    return;
                default:
                    switch (id) {
                        case ID_PROJECTION_POSE_YAW /*30323*/:
                            this.currentTrack.projectionPoseYaw = (float) value;
                            return;
                        case ID_PROJECTION_POSE_PITCH /*30324*/:
                            this.currentTrack.projectionPosePitch = (float) value;
                            return;
                        case ID_PROJECTION_POSE_ROLL /*30325*/:
                            this.currentTrack.projectionPoseRoll = (float) value;
                            return;
                        default:
                            return;
                    }
            }
        } else {
            this.durationTimecode = (long) value;
        }
    }

    /* access modifiers changed from: protected */
    @CallSuper
    public void stringElement(int id, String value) throws ParserException {
        if (id == 134) {
            this.currentTrack.codecId = value;
        } else if (id != ID_DOC_TYPE) {
            if (id == ID_NAME) {
                this.currentTrack.name = value;
            } else if (id == ID_LANGUAGE) {
                String unused = this.currentTrack.language = value;
            }
        } else if (!DOC_TYPE_WEBM.equals(value) && !DOC_TYPE_MATROSKA.equals(value)) {
            StringBuilder sb = new StringBuilder(String.valueOf(value).length() + 22);
            sb.append("DocType ");
            sb.append(value);
            sb.append(" not supported");
            throw new ParserException(sb.toString());
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0201, code lost:
        throw new com.google.android.exoplayer2.ParserException("EBML lacing sample size out of range.");
     */
    @android.support.annotation.CallSuper
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void binaryElement(int r23, int r24, com.google.android.exoplayer2.extractor.ExtractorInput r25) throws java.io.IOException, java.lang.InterruptedException {
        /*
            r22 = this;
            r0 = r22
            r1 = r23
            r2 = r24
            r3 = r25
            r4 = 161(0xa1, float:2.26E-43)
            r5 = 4
            r6 = 163(0xa3, float:2.28E-43)
            r7 = 0
            r8 = 1
            if (r1 == r4) goto L_0x0096
            if (r1 == r6) goto L_0x0096
            r4 = 16981(0x4255, float:2.3795E-41)
            if (r1 == r4) goto L_0x0089
            r4 = 18402(0x47e2, float:2.5787E-41)
            if (r1 == r4) goto L_0x0079
            r4 = 21419(0x53ab, float:3.0014E-41)
            if (r1 == r4) goto L_0x005a
            r4 = 25506(0x63a2, float:3.5742E-41)
            if (r1 == r4) goto L_0x004d
            r4 = 30322(0x7672, float:4.249E-41)
            if (r1 != r4) goto L_0x0034
            com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$Track r4 = r0.currentTrack
            byte[] r5 = new byte[r2]
            r4.projectionData = r5
            byte[] r4 = r4.projectionData
            r3.readFully(r4, r7, r2)
            goto L_0x02c0
        L_0x0034:
            com.google.android.exoplayer2.ParserException r4 = new com.google.android.exoplayer2.ParserException
            r5 = 26
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>(r5)
            java.lang.String r5 = "Unexpected id: "
            r6.append(r5)
            r6.append(r1)
            java.lang.String r5 = r6.toString()
            r4.<init>(r5)
            throw r4
        L_0x004d:
            com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$Track r4 = r0.currentTrack
            byte[] r5 = new byte[r2]
            r4.codecPrivate = r5
            byte[] r4 = r4.codecPrivate
            r3.readFully(r4, r7, r2)
            goto L_0x02c0
        L_0x005a:
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r0.seekEntryIdBytes
            byte[] r4 = r4.data
            java.util.Arrays.fill(r4, r7)
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r0.seekEntryIdBytes
            byte[] r4 = r4.data
            int r5 = r5 - r2
            r3.readFully(r4, r5, r2)
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r0.seekEntryIdBytes
            r4.setPosition(r7)
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r0.seekEntryIdBytes
            long r4 = r4.readUnsignedInt()
            int r5 = (int) r4
            r0.seekEntryId = r5
            goto L_0x02c0
        L_0x0079:
            byte[] r4 = new byte[r2]
            r3.readFully(r4, r7, r2)
            com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$Track r5 = r0.currentTrack
            com.google.android.exoplayer2.extractor.TrackOutput$CryptoData r6 = new com.google.android.exoplayer2.extractor.TrackOutput$CryptoData
            r6.<init>(r8, r4, r7, r7)
            r5.cryptoData = r6
            goto L_0x02c0
        L_0x0089:
            com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$Track r4 = r0.currentTrack
            byte[] r5 = new byte[r2]
            r4.sampleStrippedBytes = r5
            byte[] r4 = r4.sampleStrippedBytes
            r3.readFully(r4, r7, r2)
            goto L_0x02c0
        L_0x0096:
            int r4 = r0.blockState
            r9 = 8
            if (r4 != 0) goto L_0x00bb
            com.google.android.exoplayer2.extractor.mkv.VarintReader r4 = r0.varintReader
            long r10 = r4.readUnsignedVarint(r3, r7, r8, r9)
            int r4 = (int) r10
            r0.blockTrackNumber = r4
            com.google.android.exoplayer2.extractor.mkv.VarintReader r4 = r0.varintReader
            int r4 = r4.getLastLength()
            r0.blockTrackNumberLength = r4
            r10 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            r0.blockDurationUs = r10
            r0.blockState = r8
            com.google.android.exoplayer2.util.ParsableByteArray r4 = r0.scratch
            r4.reset()
        L_0x00bb:
            android.util.SparseArray<com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$Track> r4 = r0.tracks
            int r10 = r0.blockTrackNumber
            java.lang.Object r4 = r4.get(r10)
            com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$Track r4 = (com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.Track) r4
            if (r4 != 0) goto L_0x00d1
            int r5 = r0.blockTrackNumberLength
            int r5 = r2 - r5
            r3.skipFully(r5)
            r0.blockState = r7
            return
        L_0x00d1:
            int r10 = r0.blockState
            if (r10 != r8) goto L_0x028c
            r10 = 3
            r0.readScratch(r3, r10)
            com.google.android.exoplayer2.util.ParsableByteArray r11 = r0.scratch
            byte[] r11 = r11.data
            r12 = 2
            byte r11 = r11[r12]
            r11 = r11 & 6
            int r11 = r11 >> r8
            r13 = 255(0xff, float:3.57E-43)
            if (r11 != 0) goto L_0x00fc
            r0.blockLacingSampleCount = r8
            int[] r5 = r0.blockLacingSampleSizes
            int[] r5 = ensureArrayCapacity(r5, r8)
            r0.blockLacingSampleSizes = r5
            int[] r5 = r0.blockLacingSampleSizes
            int r14 = r0.blockTrackNumberLength
            int r14 = r2 - r14
            int r14 = r14 - r10
            r5[r7] = r14
            goto L_0x0217
        L_0x00fc:
            if (r1 != r6) goto L_0x0284
            r0.readScratch(r3, r5)
            com.google.android.exoplayer2.util.ParsableByteArray r14 = r0.scratch
            byte[] r14 = r14.data
            byte r14 = r14[r10]
            r14 = r14 & r13
            int r14 = r14 + r8
            r0.blockLacingSampleCount = r14
            int[] r14 = r0.blockLacingSampleSizes
            int r15 = r0.blockLacingSampleCount
            int[] r14 = ensureArrayCapacity(r14, r15)
            r0.blockLacingSampleSizes = r14
            if (r11 != r12) goto L_0x0126
            int r10 = r0.blockTrackNumberLength
            int r10 = r2 - r10
            int r10 = r10 - r5
            int r5 = r0.blockLacingSampleCount
            int r10 = r10 / r5
            int[] r14 = r0.blockLacingSampleSizes
            java.util.Arrays.fill(r14, r7, r5, r10)
            goto L_0x0217
        L_0x0126:
            if (r11 != r8) goto L_0x0161
            r5 = 0
            r10 = 4
            r14 = 0
        L_0x012b:
            int r15 = r0.blockLacingSampleCount
            int r6 = r15 + -1
            if (r14 >= r6) goto L_0x0154
            int[] r6 = r0.blockLacingSampleSizes
            r6[r14] = r7
        L_0x0135:
            int r10 = r10 + r8
            r0.readScratch(r3, r10)
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r0.scratch
            byte[] r6 = r6.data
            int r15 = r10 + -1
            byte r6 = r6[r15]
            r6 = r6 & r13
            int[] r15 = r0.blockLacingSampleSizes
            r16 = r15[r14]
            int r16 = r16 + r6
            r15[r14] = r16
            if (r6 == r13) goto L_0x0135
            r15 = r15[r14]
            int r5 = r5 + r15
            int r14 = r14 + 1
            r6 = 163(0xa3, float:2.28E-43)
            goto L_0x012b
        L_0x0154:
            int[] r6 = r0.blockLacingSampleSizes
            int r15 = r15 - r8
            int r14 = r0.blockTrackNumberLength
            int r14 = r2 - r14
            int r14 = r14 - r10
            int r14 = r14 - r5
            r6[r15] = r14
            goto L_0x0217
        L_0x0161:
            if (r11 != r10) goto L_0x026b
            r5 = 0
            r6 = 4
            r10 = 0
        L_0x0166:
            int r14 = r0.blockLacingSampleCount
            int r15 = r14 + -1
            if (r10 >= r15) goto L_0x020a
            int[] r14 = r0.blockLacingSampleSizes
            r14[r10] = r7
            int r6 = r6 + 1
            r0.readScratch(r3, r6)
            com.google.android.exoplayer2.util.ParsableByteArray r14 = r0.scratch
            byte[] r14 = r14.data
            int r15 = r6 + -1
            byte r14 = r14[r15]
            if (r14 == 0) goto L_0x0202
            r14 = 0
            r16 = 0
            r12 = r16
        L_0x0185:
            if (r12 >= r9) goto L_0x01d2
            int r16 = 7 - r12
            int r16 = r8 << r16
            com.google.android.exoplayer2.util.ParsableByteArray r7 = r0.scratch
            byte[] r7 = r7.data
            int r17 = r6 + -1
            byte r7 = r7[r17]
            r7 = r7 & r16
            if (r7 == 0) goto L_0x01cd
            int r7 = r6 + -1
            int r6 = r6 + r12
            r0.readScratch(r3, r6)
            com.google.android.exoplayer2.util.ParsableByteArray r8 = r0.scratch
            byte[] r8 = r8.data
            int r18 = r7 + 1
            byte r7 = r8[r7]
            r7 = r7 & r13
            r8 = r16 ^ -1
            r7 = r7 & r8
            long r7 = (long) r7
            r14 = r7
            r7 = r18
        L_0x01ad:
            if (r7 >= r6) goto L_0x01be
            long r14 = r14 << r9
            com.google.android.exoplayer2.util.ParsableByteArray r8 = r0.scratch
            byte[] r8 = r8.data
            int r18 = r7 + 1
            byte r7 = r8[r7]
            r7 = r7 & r13
            long r7 = (long) r7
            long r14 = r14 | r7
            r7 = r18
            goto L_0x01ad
        L_0x01be:
            if (r10 <= 0) goto L_0x01d2
            int r8 = r12 * 7
            int r8 = r8 + 6
            r18 = 1
            long r20 = r18 << r8
            long r20 = r20 - r18
            long r14 = r14 - r20
            goto L_0x01d2
        L_0x01cd:
            int r12 = r12 + 1
            r7 = 0
            r8 = 1
            goto L_0x0185
        L_0x01d2:
            r7 = -2147483648(0xffffffff80000000, double:NaN)
            int r12 = (r14 > r7 ? 1 : (r14 == r7 ? 0 : -1))
            if (r12 < 0) goto L_0x01fa
            r7 = 2147483647(0x7fffffff, double:1.060997895E-314)
            int r12 = (r14 > r7 ? 1 : (r14 == r7 ? 0 : -1))
            if (r12 > 0) goto L_0x01fa
            int r7 = (int) r14
            int[] r8 = r0.blockLacingSampleSizes
            if (r10 != 0) goto L_0x01e7
            r12 = r7
            goto L_0x01ec
        L_0x01e7:
            int r12 = r10 + -1
            r12 = r8[r12]
            int r12 = r12 + r7
        L_0x01ec:
            r8[r10] = r12
            int[] r8 = r0.blockLacingSampleSizes
            r8 = r8[r10]
            int r5 = r5 + r8
            int r10 = r10 + 1
            r7 = 0
            r8 = 1
            r12 = 2
            goto L_0x0166
        L_0x01fa:
            com.google.android.exoplayer2.ParserException r7 = new com.google.android.exoplayer2.ParserException
            java.lang.String r8 = "EBML lacing sample size out of range."
            r7.<init>(r8)
            throw r7
        L_0x0202:
            com.google.android.exoplayer2.ParserException r7 = new com.google.android.exoplayer2.ParserException
            java.lang.String r8 = "No valid varint length mask found"
            r7.<init>(r8)
            throw r7
        L_0x020a:
            int[] r7 = r0.blockLacingSampleSizes
            r8 = 1
            int r14 = r14 - r8
            int r8 = r0.blockTrackNumberLength
            int r8 = r2 - r8
            int r8 = r8 - r6
            int r8 = r8 - r5
            r7[r14] = r8
        L_0x0217:
            com.google.android.exoplayer2.util.ParsableByteArray r5 = r0.scratch
            byte[] r5 = r5.data
            r6 = 0
            byte r5 = r5[r6]
            int r5 = r5 << r9
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r0.scratch
            byte[] r6 = r6.data
            r7 = 1
            byte r6 = r6[r7]
            r6 = r6 & r13
            r5 = r5 | r6
            long r6 = r0.clusterTimecodeUs
            long r12 = (long) r5
            long r12 = r0.scaleTimecodeToUs(r12)
            long r6 = r6 + r12
            r0.blockTimeUs = r6
            com.google.android.exoplayer2.util.ParsableByteArray r6 = r0.scratch
            byte[] r6 = r6.data
            r7 = 2
            byte r6 = r6[r7]
            r6 = r6 & r9
            if (r6 != r9) goto L_0x023e
            r6 = 1
            goto L_0x023f
        L_0x023e:
            r6 = 0
        L_0x023f:
            int r8 = r4.type
            if (r8 == r7) goto L_0x0255
            r8 = 163(0xa3, float:2.28E-43)
            if (r1 != r8) goto L_0x0253
            com.google.android.exoplayer2.util.ParsableByteArray r8 = r0.scratch
            byte[] r8 = r8.data
            byte r8 = r8[r7]
            r7 = 128(0x80, float:1.794E-43)
            r8 = r8 & r7
            if (r8 != r7) goto L_0x0253
            goto L_0x0255
        L_0x0253:
            r7 = 0
            goto L_0x0256
        L_0x0255:
            r7 = 1
        L_0x0256:
            if (r7 == 0) goto L_0x025a
            r8 = 1
            goto L_0x025b
        L_0x025a:
            r8 = 0
        L_0x025b:
            if (r6 == 0) goto L_0x0260
            r9 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x0261
        L_0x0260:
            r9 = 0
        L_0x0261:
            r8 = r8 | r9
            r0.blockFlags = r8
            r8 = 2
            r0.blockState = r8
            r8 = 0
            r0.blockLacingSampleIndex = r8
            goto L_0x028c
        L_0x026b:
            com.google.android.exoplayer2.ParserException r5 = new com.google.android.exoplayer2.ParserException
            r6 = 36
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r6)
            java.lang.String r6 = "Unexpected lacing value: "
            r7.append(r6)
            r7.append(r11)
            java.lang.String r6 = r7.toString()
            r5.<init>(r6)
            throw r5
        L_0x0284:
            com.google.android.exoplayer2.ParserException r5 = new com.google.android.exoplayer2.ParserException
            java.lang.String r6 = "Lacing only supported in SimpleBlocks."
            r5.<init>(r6)
            throw r5
        L_0x028c:
            r5 = 163(0xa3, float:2.28E-43)
            if (r1 != r5) goto L_0x02b7
        L_0x0290:
            int r5 = r0.blockLacingSampleIndex
            int r6 = r0.blockLacingSampleCount
            if (r5 >= r6) goto L_0x02b3
            int[] r6 = r0.blockLacingSampleSizes
            r5 = r6[r5]
            r0.writeSampleData(r3, r4, r5)
            long r5 = r0.blockTimeUs
            int r7 = r0.blockLacingSampleIndex
            int r8 = r4.defaultSampleDurationNs
            int r7 = r7 * r8
            int r7 = r7 / 1000
            long r7 = (long) r7
            long r5 = r5 + r7
            r0.commitSampleToOutput(r4, r5)
            int r7 = r0.blockLacingSampleIndex
            r8 = 1
            int r7 = r7 + r8
            r0.blockLacingSampleIndex = r7
            goto L_0x0290
        L_0x02b3:
            r5 = 0
            r0.blockState = r5
            goto L_0x02c0
        L_0x02b7:
            r5 = 0
            int[] r6 = r0.blockLacingSampleSizes
            r5 = r6[r5]
            r0.writeSampleData(r3, r4, r5)
        L_0x02c0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.binaryElement(int, int, com.google.android.exoplayer2.extractor.ExtractorInput):void");
    }

    private void commitSampleToOutput(Track track, long timeUs) {
        Track track2 = track;
        if (track2.trueHdSampleRechunker != null) {
            track2.trueHdSampleRechunker.sampleMetadata(track2, timeUs);
        } else {
            if (CODEC_ID_SUBRIP.equals(track2.codecId)) {
                commitSubtitleSample(track, SUBRIP_TIMECODE_FORMAT, 19, 1000, SUBRIP_TIMECODE_EMPTY);
            } else if (CODEC_ID_ASS.equals(track2.codecId)) {
                commitSubtitleSample(track, SSA_TIMECODE_FORMAT, 21, SSA_TIMECODE_LAST_VALUE_SCALING_FACTOR, SSA_TIMECODE_EMPTY);
            }
            track2.output.sampleMetadata(timeUs, this.blockFlags, this.sampleBytesWritten, 0, track2.cryptoData);
        }
        this.sampleRead = true;
        resetSample();
    }

    private void resetSample() {
        this.sampleBytesRead = 0;
        this.sampleBytesWritten = 0;
        this.sampleCurrentNalBytesRemaining = 0;
        this.sampleEncodingHandled = false;
        this.sampleSignalByteRead = false;
        this.samplePartitionCountRead = false;
        this.samplePartitionCount = 0;
        this.sampleSignalByte = 0;
        this.sampleInitializationVectorRead = false;
        this.sampleStrippedBytes.reset();
    }

    private void readScratch(ExtractorInput input, int requiredLength) throws IOException, InterruptedException {
        if (this.scratch.limit() < requiredLength) {
            if (this.scratch.capacity() < requiredLength) {
                ParsableByteArray parsableByteArray = this.scratch;
                parsableByteArray.reset(Arrays.copyOf(parsableByteArray.data, Math.max(this.scratch.data.length * 2, requiredLength)), this.scratch.limit());
            }
            input.readFully(this.scratch.data, this.scratch.limit(), requiredLength - this.scratch.limit());
            this.scratch.setLimit(requiredLength);
        }
    }

    private void writeSampleData(ExtractorInput input, Track track, int size) throws IOException, InterruptedException {
        int i;
        ExtractorInput extractorInput = input;
        Track track2 = track;
        int i2 = size;
        if (CODEC_ID_SUBRIP.equals(track2.codecId)) {
            writeSubtitleSampleData(extractorInput, SUBRIP_PREFIX, i2);
        } else if (CODEC_ID_ASS.equals(track2.codecId)) {
            writeSubtitleSampleData(extractorInput, SSA_PREFIX, i2);
        } else {
            TrackOutput output = track2.output;
            if (!this.sampleEncodingHandled) {
                if (track2.hasContentEncryption) {
                    this.blockFlags &= -1073741825;
                    int i3 = 128;
                    if (!this.sampleSignalByteRead) {
                        extractorInput.readFully(this.scratch.data, 0, 1);
                        this.sampleBytesRead++;
                        if ((this.scratch.data[0] & UnsignedBytes.MAX_POWER_OF_TWO) != 128) {
                            this.sampleSignalByte = this.scratch.data[0];
                            this.sampleSignalByteRead = true;
                        } else {
                            throw new ParserException("Extension bit is set in signal byte");
                        }
                    }
                    if ((this.sampleSignalByte & 1) == 1) {
                        boolean hasSubsampleEncryption = (this.sampleSignalByte & 2) == 2;
                        this.blockFlags |= 1073741824;
                        if (!this.sampleInitializationVectorRead) {
                            extractorInput.readFully(this.encryptionInitializationVector.data, 0, 8);
                            this.sampleBytesRead += 8;
                            this.sampleInitializationVectorRead = true;
                            byte[] bArr = this.scratch.data;
                            if (!hasSubsampleEncryption) {
                                i3 = 0;
                            }
                            bArr[0] = (byte) (i3 | 8);
                            this.scratch.setPosition(0);
                            output.sampleData(this.scratch, 1);
                            this.sampleBytesWritten++;
                            this.encryptionInitializationVector.setPosition(0);
                            output.sampleData(this.encryptionInitializationVector, 8);
                            this.sampleBytesWritten += 8;
                        }
                        if (hasSubsampleEncryption) {
                            if (!this.samplePartitionCountRead) {
                                extractorInput.readFully(this.scratch.data, 0, 1);
                                this.sampleBytesRead++;
                                this.scratch.setPosition(0);
                                this.samplePartitionCount = this.scratch.readUnsignedByte();
                                this.samplePartitionCountRead = true;
                            }
                            int samplePartitionDataSize = this.samplePartitionCount * 4;
                            this.scratch.reset(samplePartitionDataSize);
                            extractorInput.readFully(this.scratch.data, 0, samplePartitionDataSize);
                            this.sampleBytesRead += samplePartitionDataSize;
                            short subsampleCount = (short) ((this.samplePartitionCount / 2) + 1);
                            int subsampleDataSize = (subsampleCount * 6) + 2;
                            ByteBuffer byteBuffer = this.encryptionSubsampleDataBuffer;
                            if (byteBuffer == null || byteBuffer.capacity() < subsampleDataSize) {
                                this.encryptionSubsampleDataBuffer = ByteBuffer.allocate(subsampleDataSize);
                            }
                            this.encryptionSubsampleDataBuffer.position(0);
                            this.encryptionSubsampleDataBuffer.putShort(subsampleCount);
                            int partitionOffset = 0;
                            int i4 = 0;
                            while (true) {
                                i = this.samplePartitionCount;
                                if (i4 >= i) {
                                    break;
                                }
                                int previousPartitionOffset = partitionOffset;
                                partitionOffset = this.scratch.readUnsignedIntToInt();
                                if (i4 % 2 == 0) {
                                    this.encryptionSubsampleDataBuffer.putShort((short) (partitionOffset - previousPartitionOffset));
                                } else {
                                    this.encryptionSubsampleDataBuffer.putInt(partitionOffset - previousPartitionOffset);
                                }
                                i4++;
                            }
                            int finalPartitionSize = (i2 - this.sampleBytesRead) - partitionOffset;
                            if (i % 2 == 1) {
                                this.encryptionSubsampleDataBuffer.putInt(finalPartitionSize);
                            } else {
                                this.encryptionSubsampleDataBuffer.putShort((short) finalPartitionSize);
                                this.encryptionSubsampleDataBuffer.putInt(0);
                            }
                            this.encryptionSubsampleData.reset(this.encryptionSubsampleDataBuffer.array(), subsampleDataSize);
                            output.sampleData(this.encryptionSubsampleData, subsampleDataSize);
                            this.sampleBytesWritten += subsampleDataSize;
                        }
                    }
                } else if (track2.sampleStrippedBytes != null) {
                    this.sampleStrippedBytes.reset(track2.sampleStrippedBytes, track2.sampleStrippedBytes.length);
                }
                this.sampleEncodingHandled = true;
            }
            int size2 = i2 + this.sampleStrippedBytes.limit();
            if (!CODEC_ID_H264.equals(track2.codecId) && !CODEC_ID_H265.equals(track2.codecId)) {
                if (track2.trueHdSampleRechunker != null) {
                    Assertions.checkState(this.sampleStrippedBytes.limit() == 0);
                    track2.trueHdSampleRechunker.startSample(extractorInput, this.blockFlags, size2);
                }
                while (true) {
                    int i5 = this.sampleBytesRead;
                    if (i5 >= size2) {
                        break;
                    }
                    readToOutput(extractorInput, output, size2 - i5);
                }
            } else {
                byte[] nalLengthData = this.nalLength.data;
                nalLengthData[0] = 0;
                nalLengthData[1] = 0;
                nalLengthData[2] = 0;
                int nalUnitLengthFieldLength = track2.nalUnitLengthFieldLength;
                int nalUnitLengthFieldLengthDiff = 4 - track2.nalUnitLengthFieldLength;
                while (this.sampleBytesRead < size2) {
                    int i6 = this.sampleCurrentNalBytesRemaining;
                    if (i6 == 0) {
                        readToTarget(extractorInput, nalLengthData, nalUnitLengthFieldLengthDiff, nalUnitLengthFieldLength);
                        this.nalLength.setPosition(0);
                        this.sampleCurrentNalBytesRemaining = this.nalLength.readUnsignedIntToInt();
                        this.nalStartCode.setPosition(0);
                        output.sampleData(this.nalStartCode, 4);
                        this.sampleBytesWritten += 4;
                    } else {
                        this.sampleCurrentNalBytesRemaining = i6 - readToOutput(extractorInput, output, i6);
                    }
                }
            }
            if (CODEC_ID_VORBIS.equals(track2.codecId)) {
                this.vorbisNumPageSamples.setPosition(0);
                output.sampleData(this.vorbisNumPageSamples, 4);
                this.sampleBytesWritten += 4;
            }
        }
    }

    private void writeSubtitleSampleData(ExtractorInput input, byte[] samplePrefix, int size) throws IOException, InterruptedException {
        int sizeWithPrefix = samplePrefix.length + size;
        if (this.subtitleSample.capacity() < sizeWithPrefix) {
            this.subtitleSample.data = Arrays.copyOf(samplePrefix, sizeWithPrefix + size);
        } else {
            System.arraycopy(samplePrefix, 0, this.subtitleSample.data, 0, samplePrefix.length);
        }
        input.readFully(this.subtitleSample.data, samplePrefix.length, size);
        this.subtitleSample.reset(sizeWithPrefix);
    }

    private void commitSubtitleSample(Track track, String timecodeFormat, int endTimecodeOffset, long lastTimecodeValueScalingFactor, byte[] emptyTimecode) {
        setSampleDuration(this.subtitleSample.data, this.blockDurationUs, timecodeFormat, endTimecodeOffset, lastTimecodeValueScalingFactor, emptyTimecode);
        TrackOutput trackOutput = track.output;
        ParsableByteArray parsableByteArray = this.subtitleSample;
        trackOutput.sampleData(parsableByteArray, parsableByteArray.limit());
        this.sampleBytesWritten += this.subtitleSample.limit();
    }

    private static void setSampleDuration(byte[] subripSampleData, long durationUs2, String timecodeFormat, int endTimecodeOffset, long lastTimecodeValueScalingFactor, byte[] emptyTimecode) {
        byte[] timeCodeData;
        if (durationUs2 == C0841C.TIME_UNSET) {
            timeCodeData = emptyTimecode;
        } else {
            int hours = (int) (durationUs2 / 3600000000L);
            long durationUs3 = durationUs2 - (((long) (hours * 3600)) * 1000000);
            int minutes = (int) (durationUs3 / 60000000);
            long durationUs4 = durationUs3 - (((long) (minutes * 60)) * 1000000);
            int seconds = (int) (durationUs4 / 1000000);
            timeCodeData = Util.getUtf8Bytes(String.format(Locale.US, timecodeFormat, Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds), Integer.valueOf((int) ((durationUs4 - (((long) seconds) * 1000000)) / lastTimecodeValueScalingFactor))));
        }
        System.arraycopy(timeCodeData, 0, subripSampleData, endTimecodeOffset, emptyTimecode.length);
    }

    private void readToTarget(ExtractorInput input, byte[] target, int offset, int length) throws IOException, InterruptedException {
        int pendingStrippedBytes = Math.min(length, this.sampleStrippedBytes.bytesLeft());
        input.readFully(target, offset + pendingStrippedBytes, length - pendingStrippedBytes);
        if (pendingStrippedBytes > 0) {
            this.sampleStrippedBytes.readBytes(target, offset, pendingStrippedBytes);
        }
        this.sampleBytesRead += length;
    }

    private int readToOutput(ExtractorInput input, TrackOutput output, int length) throws IOException, InterruptedException {
        int bytesRead;
        int strippedBytesLeft = this.sampleStrippedBytes.bytesLeft();
        if (strippedBytesLeft > 0) {
            bytesRead = Math.min(length, strippedBytesLeft);
            output.sampleData(this.sampleStrippedBytes, bytesRead);
        } else {
            bytesRead = output.sampleData(input, length, false);
        }
        this.sampleBytesRead += bytesRead;
        this.sampleBytesWritten += bytesRead;
        return bytesRead;
    }

    private SeekMap buildSeekMap() {
        LongArray longArray;
        LongArray longArray2;
        if (this.segmentContentPosition == -1 || this.durationUs == C0841C.TIME_UNSET || (longArray = this.cueTimesUs) == null || longArray.size() == 0 || (longArray2 = this.cueClusterPositions) == null || longArray2.size() != this.cueTimesUs.size()) {
            this.cueTimesUs = null;
            this.cueClusterPositions = null;
            return new SeekMap.Unseekable(this.durationUs);
        }
        int cuePointsSize = this.cueTimesUs.size();
        int[] sizes = new int[cuePointsSize];
        long[] offsets = new long[cuePointsSize];
        long[] durationsUs = new long[cuePointsSize];
        long[] timesUs = new long[cuePointsSize];
        for (int i = 0; i < cuePointsSize; i++) {
            timesUs[i] = this.cueTimesUs.get(i);
            offsets[i] = this.segmentContentPosition + this.cueClusterPositions.get(i);
        }
        for (int i2 = 0; i2 < cuePointsSize - 1; i2++) {
            sizes[i2] = (int) (offsets[i2 + 1] - offsets[i2]);
            durationsUs[i2] = timesUs[i2 + 1] - timesUs[i2];
        }
        sizes[cuePointsSize - 1] = (int) ((this.segmentContentPosition + this.segmentContentSize) - offsets[cuePointsSize - 1]);
        durationsUs[cuePointsSize - 1] = this.durationUs - timesUs[cuePointsSize - 1];
        this.cueTimesUs = null;
        this.cueClusterPositions = null;
        return new ChunkIndex(sizes, offsets, durationsUs, timesUs);
    }

    private boolean maybeSeekForCues(PositionHolder seekPosition, long currentPosition) {
        if (this.seekForCues) {
            this.seekPositionAfterBuildingCues = currentPosition;
            seekPosition.position = this.cuesContentPosition;
            this.seekForCues = false;
            return true;
        }
        if (this.sentSeekMap) {
            long j = this.seekPositionAfterBuildingCues;
            if (j != -1) {
                seekPosition.position = j;
                this.seekPositionAfterBuildingCues = -1;
                return true;
            }
        }
        return false;
    }

    private long scaleTimecodeToUs(long unscaledTimecode) throws ParserException {
        long j = this.timecodeScale;
        if (j != C0841C.TIME_UNSET) {
            return Util.scaleLargeTimestamp(unscaledTimecode, j, 1000);
        }
        throw new ParserException("Can't scale timecode prior to timecodeScale being set.");
    }

    private static boolean isCodecSupported(String codecId) {
        return CODEC_ID_VP8.equals(codecId) || CODEC_ID_VP9.equals(codecId) || CODEC_ID_AV1.equals(codecId) || CODEC_ID_MPEG2.equals(codecId) || CODEC_ID_MPEG4_SP.equals(codecId) || CODEC_ID_MPEG4_ASP.equals(codecId) || CODEC_ID_MPEG4_AP.equals(codecId) || CODEC_ID_H264.equals(codecId) || CODEC_ID_H265.equals(codecId) || CODEC_ID_FOURCC.equals(codecId) || CODEC_ID_THEORA.equals(codecId) || CODEC_ID_OPUS.equals(codecId) || CODEC_ID_VORBIS.equals(codecId) || CODEC_ID_AAC.equals(codecId) || CODEC_ID_MP2.equals(codecId) || CODEC_ID_MP3.equals(codecId) || CODEC_ID_AC3.equals(codecId) || CODEC_ID_E_AC3.equals(codecId) || CODEC_ID_TRUEHD.equals(codecId) || CODEC_ID_DTS.equals(codecId) || CODEC_ID_DTS_EXPRESS.equals(codecId) || CODEC_ID_DTS_LOSSLESS.equals(codecId) || CODEC_ID_FLAC.equals(codecId) || CODEC_ID_ACM.equals(codecId) || CODEC_ID_PCM_INT_LIT.equals(codecId) || CODEC_ID_SUBRIP.equals(codecId) || CODEC_ID_ASS.equals(codecId) || CODEC_ID_VOBSUB.equals(codecId) || CODEC_ID_PGS.equals(codecId) || CODEC_ID_DVBSUB.equals(codecId);
    }

    private static int[] ensureArrayCapacity(int[] array, int length) {
        if (array == null) {
            return new int[length];
        }
        if (array.length >= length) {
            return array;
        }
        return new int[Math.max(array.length * 2, length)];
    }

    private final class InnerEbmlProcessor implements EbmlProcessor {
        private InnerEbmlProcessor() {
        }

        public int getElementType(int id) {
            return MatroskaExtractor.this.getElementType(id);
        }

        public boolean isLevel1Element(int id) {
            return MatroskaExtractor.this.isLevel1Element(id);
        }

        public void startMasterElement(int id, long contentPosition, long contentSize) throws ParserException {
            MatroskaExtractor.this.startMasterElement(id, contentPosition, contentSize);
        }

        public void endMasterElement(int id) throws ParserException {
            MatroskaExtractor.this.endMasterElement(id);
        }

        public void integerElement(int id, long value) throws ParserException {
            MatroskaExtractor.this.integerElement(id, value);
        }

        public void floatElement(int id, double value) throws ParserException {
            MatroskaExtractor.this.floatElement(id, value);
        }

        public void stringElement(int id, String value) throws ParserException {
            MatroskaExtractor.this.stringElement(id, value);
        }

        public void binaryElement(int id, int contentsSize, ExtractorInput input) throws IOException, InterruptedException {
            MatroskaExtractor.this.binaryElement(id, contentsSize, input);
        }
    }

    private static final class TrueHdSampleRechunker {
        private int blockFlags;
        private int chunkSize;
        private boolean foundSyncframe;
        private int sampleCount;
        private final byte[] syncframePrefix = new byte[10];
        private long timeUs;

        public void reset() {
            this.foundSyncframe = false;
        }

        public void startSample(ExtractorInput input, int blockFlags2, int size) throws IOException, InterruptedException {
            if (!this.foundSyncframe) {
                input.peekFully(this.syncframePrefix, 0, 10);
                input.resetPeekPosition();
                if (Ac3Util.parseTrueHdSyncframeAudioSampleCount(this.syncframePrefix) != 0) {
                    this.foundSyncframe = true;
                    this.sampleCount = 0;
                } else {
                    return;
                }
            }
            if (this.sampleCount == 0) {
                this.blockFlags = blockFlags2;
                this.chunkSize = 0;
            }
            this.chunkSize += size;
        }

        public void sampleMetadata(Track track, long timeUs2) {
            if (this.foundSyncframe) {
                int i = this.sampleCount;
                this.sampleCount = i + 1;
                if (i == 0) {
                    this.timeUs = timeUs2;
                }
                if (this.sampleCount >= 16) {
                    track.output.sampleMetadata(this.timeUs, this.blockFlags, this.chunkSize, 0, track.cryptoData);
                    this.sampleCount = 0;
                }
            }
        }

        public void outputPendingSampleMetadata(Track track) {
            if (this.foundSyncframe && this.sampleCount > 0) {
                track.output.sampleMetadata(this.timeUs, this.blockFlags, this.chunkSize, 0, track.cryptoData);
                this.sampleCount = 0;
            }
        }
    }

    private static final class Track {
        private static final int DEFAULT_MAX_CLL = 1000;
        private static final int DEFAULT_MAX_FALL = 200;
        private static final int DISPLAY_UNIT_PIXELS = 0;
        private static final int MAX_CHROMATICITY = 50000;
        public int audioBitDepth;
        public int channelCount;
        public long codecDelayNs;
        public String codecId;
        public byte[] codecPrivate;
        public int colorRange;
        public int colorSpace;
        public int colorTransfer;
        public TrackOutput.CryptoData cryptoData;
        public int defaultSampleDurationNs;
        public int displayHeight;
        public int displayUnit;
        public int displayWidth;
        public DrmInitData drmInitData;
        public boolean flagDefault;
        public boolean flagForced;
        public boolean hasColorInfo;
        public boolean hasContentEncryption;
        public int height;
        /* access modifiers changed from: private */
        public String language;
        public int maxContentLuminance;
        public int maxFrameAverageLuminance;
        public float maxMasteringLuminance;
        public float minMasteringLuminance;
        public int nalUnitLengthFieldLength;
        public String name;
        public int number;
        public TrackOutput output;
        public float primaryBChromaticityX;
        public float primaryBChromaticityY;
        public float primaryGChromaticityX;
        public float primaryGChromaticityY;
        public float primaryRChromaticityX;
        public float primaryRChromaticityY;
        public byte[] projectionData;
        public float projectionPosePitch;
        public float projectionPoseRoll;
        public float projectionPoseYaw;
        public int projectionType;
        public int sampleRate;
        public byte[] sampleStrippedBytes;
        public long seekPreRollNs;
        public int stereoMode;
        @Nullable
        public TrueHdSampleRechunker trueHdSampleRechunker;
        public int type;
        public float whitePointChromaticityX;
        public float whitePointChromaticityY;
        public int width;

        private Track() {
            this.width = -1;
            this.height = -1;
            this.displayWidth = -1;
            this.displayHeight = -1;
            this.displayUnit = 0;
            this.projectionType = -1;
            this.projectionPoseYaw = 0.0f;
            this.projectionPosePitch = 0.0f;
            this.projectionPoseRoll = 0.0f;
            this.projectionData = null;
            this.stereoMode = -1;
            this.hasColorInfo = false;
            this.colorSpace = -1;
            this.colorTransfer = -1;
            this.colorRange = -1;
            this.maxContentLuminance = 1000;
            this.maxFrameAverageLuminance = 200;
            this.primaryRChromaticityX = -1.0f;
            this.primaryRChromaticityY = -1.0f;
            this.primaryGChromaticityX = -1.0f;
            this.primaryGChromaticityY = -1.0f;
            this.primaryBChromaticityX = -1.0f;
            this.primaryBChromaticityY = -1.0f;
            this.whitePointChromaticityX = -1.0f;
            this.whitePointChromaticityY = -1.0f;
            this.maxMasteringLuminance = -1.0f;
            this.minMasteringLuminance = -1.0f;
            this.channelCount = 1;
            this.audioBitDepth = -1;
            this.sampleRate = 8000;
            this.codecDelayNs = 0;
            this.seekPreRollNs = 0;
            this.flagDefault = true;
            this.language = "eng";
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v26, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.util.List<byte[]>} */
        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void initializeOutput(com.google.android.exoplayer2.extractor.ExtractorOutput r29, int r30) throws com.google.android.exoplayer2.ParserException {
            /*
                r28 = this;
                r0 = r28
                r1 = -1
                r2 = -1
                r3 = 0
                java.lang.String r4 = r0.codecId
                int r5 = r4.hashCode()
                r6 = 4
                r7 = 1
                r8 = 8
                r9 = 0
                r10 = 3
                r11 = 2
                r12 = -1
                switch(r5) {
                    case -2095576542: goto L_0x0165;
                    case -2095575984: goto L_0x015b;
                    case -1985379776: goto L_0x0150;
                    case -1784763192: goto L_0x0145;
                    case -1730367663: goto L_0x013a;
                    case -1482641358: goto L_0x012f;
                    case -1482641357: goto L_0x0124;
                    case -1373388978: goto L_0x0119;
                    case -933872740: goto L_0x010e;
                    case -538363189: goto L_0x0103;
                    case -538363109: goto L_0x00f8;
                    case -425012669: goto L_0x00ec;
                    case -356037306: goto L_0x00e0;
                    case 62923557: goto L_0x00d4;
                    case 62923603: goto L_0x00c8;
                    case 62927045: goto L_0x00bc;
                    case 82318131: goto L_0x00b1;
                    case 82338133: goto L_0x00a6;
                    case 82338134: goto L_0x009b;
                    case 99146302: goto L_0x008f;
                    case 444813526: goto L_0x0083;
                    case 542569478: goto L_0x0077;
                    case 725957860: goto L_0x006b;
                    case 738597099: goto L_0x005f;
                    case 855502857: goto L_0x0053;
                    case 1422270023: goto L_0x0047;
                    case 1809237540: goto L_0x003c;
                    case 1950749482: goto L_0x0030;
                    case 1950789798: goto L_0x0024;
                    case 1951062397: goto L_0x0018;
                    default: goto L_0x0016;
                }
            L_0x0016:
                goto L_0x016f
            L_0x0018:
                java.lang.String r5 = "A_OPUS"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 12
                goto L_0x0170
            L_0x0024:
                java.lang.String r5 = "A_FLAC"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 22
                goto L_0x0170
            L_0x0030:
                java.lang.String r5 = "A_EAC3"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 17
                goto L_0x0170
            L_0x003c:
                java.lang.String r5 = "V_MPEG2"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 3
                goto L_0x0170
            L_0x0047:
                java.lang.String r5 = "S_TEXT/UTF8"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 25
                goto L_0x0170
            L_0x0053:
                java.lang.String r5 = "V_MPEGH/ISO/HEVC"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 8
                goto L_0x0170
            L_0x005f:
                java.lang.String r5 = "S_TEXT/ASS"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 26
                goto L_0x0170
            L_0x006b:
                java.lang.String r5 = "A_PCM/INT/LIT"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 24
                goto L_0x0170
            L_0x0077:
                java.lang.String r5 = "A_DTS/EXPRESS"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 20
                goto L_0x0170
            L_0x0083:
                java.lang.String r5 = "V_THEORA"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 10
                goto L_0x0170
            L_0x008f:
                java.lang.String r5 = "S_HDMV/PGS"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 28
                goto L_0x0170
            L_0x009b:
                java.lang.String r5 = "V_VP9"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 1
                goto L_0x0170
            L_0x00a6:
                java.lang.String r5 = "V_VP8"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 0
                goto L_0x0170
            L_0x00b1:
                java.lang.String r5 = "V_AV1"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 2
                goto L_0x0170
            L_0x00bc:
                java.lang.String r5 = "A_DTS"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 19
                goto L_0x0170
            L_0x00c8:
                java.lang.String r5 = "A_AC3"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 16
                goto L_0x0170
            L_0x00d4:
                java.lang.String r5 = "A_AAC"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 13
                goto L_0x0170
            L_0x00e0:
                java.lang.String r5 = "A_DTS/LOSSLESS"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 21
                goto L_0x0170
            L_0x00ec:
                java.lang.String r5 = "S_VOBSUB"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 27
                goto L_0x0170
            L_0x00f8:
                java.lang.String r5 = "V_MPEG4/ISO/AVC"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 7
                goto L_0x0170
            L_0x0103:
                java.lang.String r5 = "V_MPEG4/ISO/ASP"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 5
                goto L_0x0170
            L_0x010e:
                java.lang.String r5 = "S_DVBSUB"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 29
                goto L_0x0170
            L_0x0119:
                java.lang.String r5 = "V_MS/VFW/FOURCC"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 9
                goto L_0x0170
            L_0x0124:
                java.lang.String r5 = "A_MPEG/L3"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 15
                goto L_0x0170
            L_0x012f:
                java.lang.String r5 = "A_MPEG/L2"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 14
                goto L_0x0170
            L_0x013a:
                java.lang.String r5 = "A_VORBIS"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 11
                goto L_0x0170
            L_0x0145:
                java.lang.String r5 = "A_TRUEHD"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 18
                goto L_0x0170
            L_0x0150:
                java.lang.String r5 = "A_MS/ACM"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 23
                goto L_0x0170
            L_0x015b:
                java.lang.String r5 = "V_MPEG4/ISO/SP"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 4
                goto L_0x0170
            L_0x0165:
                java.lang.String r5 = "V_MPEG4/ISO/AP"
                boolean r4 = r4.equals(r5)
                if (r4 == 0) goto L_0x0016
                r4 = 6
                goto L_0x0170
            L_0x016f:
                r4 = -1
            L_0x0170:
                java.lang.String r5 = ". Setting mimeType to "
                java.lang.String r13 = "Unsupported PCM bit depth: "
                java.lang.String r14 = "MatroskaExtractor"
                switch(r4) {
                    case 0: goto L_0x0331;
                    case 1: goto L_0x032d;
                    case 2: goto L_0x0329;
                    case 3: goto L_0x0325;
                    case 4: goto L_0x0316;
                    case 5: goto L_0x0316;
                    case 6: goto L_0x0316;
                    case 7: goto L_0x0301;
                    case 8: goto L_0x02ec;
                    case 9: goto L_0x02d6;
                    case 10: goto L_0x02d2;
                    case 11: goto L_0x02c7;
                    case 12: goto L_0x0282;
                    case 13: goto L_0x0278;
                    case 14: goto L_0x0272;
                    case 15: goto L_0x026c;
                    case 16: goto L_0x0268;
                    case 17: goto L_0x0264;
                    case 18: goto L_0x0259;
                    case 19: goto L_0x0255;
                    case 20: goto L_0x0255;
                    case 21: goto L_0x0251;
                    case 22: goto L_0x0247;
                    case 23: goto L_0x01e9;
                    case 24: goto L_0x01b6;
                    case 25: goto L_0x01b2;
                    case 26: goto L_0x01ad;
                    case 27: goto L_0x01a3;
                    case 28: goto L_0x019f;
                    case 29: goto L_0x0183;
                    default: goto L_0x0179;
                }
            L_0x0179:
                r9 = r29
                com.google.android.exoplayer2.ParserException r4 = new com.google.android.exoplayer2.ParserException
                java.lang.String r5 = "Unrecognized codec identifier."
                r4.<init>(r5)
                throw r4
            L_0x0183:
                java.lang.String r4 = "application/dvbsubs"
                byte[] r5 = new byte[r6]
                byte[] r6 = r0.codecPrivate
                byte r8 = r6[r9]
                r5[r9] = r8
                byte r8 = r6[r7]
                r5[r7] = r8
                byte r7 = r6[r11]
                r5[r11] = r7
                byte r6 = r6[r10]
                r5[r10] = r6
                java.util.List r3 = java.util.Collections.singletonList(r5)
                goto L_0x0335
            L_0x019f:
                java.lang.String r4 = "application/pgs"
                goto L_0x0335
            L_0x01a3:
                java.lang.String r4 = "application/vobsub"
                byte[] r5 = r0.codecPrivate
                java.util.List r3 = java.util.Collections.singletonList(r5)
                goto L_0x0335
            L_0x01ad:
                java.lang.String r4 = "text/x-ssa"
                goto L_0x0335
            L_0x01b2:
                java.lang.String r4 = "application/x-subrip"
                goto L_0x0335
            L_0x01b6:
                java.lang.String r4 = "audio/raw"
                int r6 = r0.audioBitDepth
                int r2 = com.google.android.exoplayer2.util.Util.getPcmEncoding(r6)
                if (r2 != 0) goto L_0x0335
                r2 = -1
                java.lang.String r4 = "audio/x-unknown"
                int r6 = r0.audioBitDepth
                java.lang.String r7 = java.lang.String.valueOf(r4)
                int r7 = r7.length()
                int r7 = r7 + 60
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>(r7)
                r8.append(r13)
                r8.append(r6)
                r8.append(r5)
                r8.append(r4)
                java.lang.String r5 = r8.toString()
                com.google.android.exoplayer2.util.Log.m30w(r14, r5)
                goto L_0x0335
            L_0x01e9:
                java.lang.String r4 = "audio/raw"
                com.google.android.exoplayer2.util.ParsableByteArray r6 = new com.google.android.exoplayer2.util.ParsableByteArray
                byte[] r7 = r0.codecPrivate
                r6.<init>(r7)
                boolean r6 = parseMsAcmCodecPrivate(r6)
                if (r6 == 0) goto L_0x0229
                int r6 = r0.audioBitDepth
                int r2 = com.google.android.exoplayer2.util.Util.getPcmEncoding(r6)
                if (r2 != 0) goto L_0x0335
                r2 = -1
                java.lang.String r4 = "audio/x-unknown"
                int r6 = r0.audioBitDepth
                java.lang.String r7 = java.lang.String.valueOf(r4)
                int r7 = r7.length()
                int r7 = r7 + 60
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>(r7)
                r8.append(r13)
                r8.append(r6)
                r8.append(r5)
                r8.append(r4)
                java.lang.String r5 = r8.toString()
                com.google.android.exoplayer2.util.Log.m30w(r14, r5)
                goto L_0x0335
            L_0x0229:
                java.lang.String r4 = "audio/x-unknown"
                java.lang.String r5 = "Non-PCM MS/ACM is unsupported. Setting mimeType to "
                java.lang.String r6 = java.lang.String.valueOf(r4)
                int r7 = r6.length()
                if (r7 == 0) goto L_0x023c
                java.lang.String r5 = r5.concat(r6)
                goto L_0x0242
            L_0x023c:
                java.lang.String r6 = new java.lang.String
                r6.<init>(r5)
                r5 = r6
            L_0x0242:
                com.google.android.exoplayer2.util.Log.m30w(r14, r5)
                goto L_0x0335
            L_0x0247:
                java.lang.String r4 = "audio/flac"
                byte[] r5 = r0.codecPrivate
                java.util.List r3 = java.util.Collections.singletonList(r5)
                goto L_0x0335
            L_0x0251:
                java.lang.String r4 = "audio/vnd.dts.hd"
                goto L_0x0335
            L_0x0255:
                java.lang.String r4 = "audio/vnd.dts"
                goto L_0x0335
            L_0x0259:
                java.lang.String r4 = "audio/true-hd"
                com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$TrueHdSampleRechunker r5 = new com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor$TrueHdSampleRechunker
                r5.<init>()
                r0.trueHdSampleRechunker = r5
                goto L_0x0335
            L_0x0264:
                java.lang.String r4 = "audio/eac3"
                goto L_0x0335
            L_0x0268:
                java.lang.String r4 = "audio/ac3"
                goto L_0x0335
            L_0x026c:
                java.lang.String r4 = "audio/mpeg"
                r1 = 4096(0x1000, float:5.74E-42)
                goto L_0x0335
            L_0x0272:
                java.lang.String r4 = "audio/mpeg-L2"
                r1 = 4096(0x1000, float:5.74E-42)
                goto L_0x0335
            L_0x0278:
                java.lang.String r4 = "audio/mp4a-latm"
                byte[] r5 = r0.codecPrivate
                java.util.List r3 = java.util.Collections.singletonList(r5)
                goto L_0x0335
            L_0x0282:
                java.lang.String r4 = "audio/opus"
                r1 = 5760(0x1680, float:8.071E-42)
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>(r10)
                r3 = r5
                byte[] r5 = r0.codecPrivate
                r3.add(r5)
                java.nio.ByteBuffer r5 = java.nio.ByteBuffer.allocate(r8)
                java.nio.ByteOrder r6 = java.nio.ByteOrder.nativeOrder()
                java.nio.ByteBuffer r5 = r5.order(r6)
                long r6 = r0.codecDelayNs
                java.nio.ByteBuffer r5 = r5.putLong(r6)
                byte[] r5 = r5.array()
                r3.add(r5)
                java.nio.ByteBuffer r5 = java.nio.ByteBuffer.allocate(r8)
                java.nio.ByteOrder r6 = java.nio.ByteOrder.nativeOrder()
                java.nio.ByteBuffer r5 = r5.order(r6)
                long r6 = r0.seekPreRollNs
                java.nio.ByteBuffer r5 = r5.putLong(r6)
                byte[] r5 = r5.array()
                r3.add(r5)
                goto L_0x0335
            L_0x02c7:
                java.lang.String r4 = "audio/vorbis"
                r1 = 8192(0x2000, float:1.14794E-41)
                byte[] r5 = r0.codecPrivate
                java.util.List r3 = parseVorbisCodecPrivate(r5)
                goto L_0x0335
            L_0x02d2:
                java.lang.String r4 = "video/x-unknown"
                goto L_0x0335
            L_0x02d6:
                com.google.android.exoplayer2.util.ParsableByteArray r4 = new com.google.android.exoplayer2.util.ParsableByteArray
                byte[] r5 = r0.codecPrivate
                r4.<init>(r5)
                android.util.Pair r4 = parseFourCcPrivate(r4)
                java.lang.Object r5 = r4.first
                java.lang.String r5 = (java.lang.String) r5
                java.lang.Object r6 = r4.second
                r3 = r6
                java.util.List r3 = (java.util.List) r3
                r4 = r5
                goto L_0x0335
            L_0x02ec:
                java.lang.String r4 = "video/hevc"
                com.google.android.exoplayer2.util.ParsableByteArray r5 = new com.google.android.exoplayer2.util.ParsableByteArray
                byte[] r6 = r0.codecPrivate
                r5.<init>(r6)
                com.google.android.exoplayer2.video.HevcConfig r5 = com.google.android.exoplayer2.video.HevcConfig.parse(r5)
                java.util.List<byte[]> r3 = r5.initializationData
                int r6 = r5.nalUnitLengthFieldLength
                r0.nalUnitLengthFieldLength = r6
                goto L_0x0335
            L_0x0301:
                java.lang.String r4 = "video/avc"
                com.google.android.exoplayer2.util.ParsableByteArray r5 = new com.google.android.exoplayer2.util.ParsableByteArray
                byte[] r6 = r0.codecPrivate
                r5.<init>(r6)
                com.google.android.exoplayer2.video.AvcConfig r5 = com.google.android.exoplayer2.video.AvcConfig.parse(r5)
                java.util.List<byte[]> r3 = r5.initializationData
                int r6 = r5.nalUnitLengthFieldLength
                r0.nalUnitLengthFieldLength = r6
                goto L_0x0335
            L_0x0316:
                java.lang.String r4 = "video/mp4v-es"
                byte[] r5 = r0.codecPrivate
                if (r5 != 0) goto L_0x031f
                r5 = 0
                goto L_0x0323
            L_0x031f:
                java.util.List r5 = java.util.Collections.singletonList(r5)
            L_0x0323:
                r3 = r5
                goto L_0x0335
            L_0x0325:
                java.lang.String r4 = "video/mpeg2"
                goto L_0x0335
            L_0x0329:
                java.lang.String r4 = "video/av01"
                goto L_0x0335
            L_0x032d:
                java.lang.String r4 = "video/x-vnd.on2.vp9"
                goto L_0x0335
            L_0x0331:
                java.lang.String r4 = "video/x-vnd.on2.vp8"
            L_0x0335:
                r5 = 0
                boolean r6 = r0.flagDefault
                r5 = r5 | r6
                boolean r6 = r0.flagForced
                if (r6 == 0) goto L_0x033e
                r9 = 2
            L_0x033e:
                r5 = r5 | r9
                boolean r6 = com.google.android.exoplayer2.util.MimeTypes.isAudio(r4)
                if (r6 == 0) goto L_0x036c
                r6 = 1
                java.lang.String r13 = java.lang.Integer.toString(r30)
                r15 = 0
                r16 = -1
                int r7 = r0.channelCount
                int r8 = r0.sampleRate
                com.google.android.exoplayer2.drm.DrmInitData r9 = r0.drmInitData
                java.lang.String r10 = r0.language
                r14 = r4
                r17 = r1
                r18 = r7
                r19 = r8
                r20 = r2
                r21 = r3
                r22 = r9
                r23 = r5
                r24 = r10
                com.google.android.exoplayer2.Format r7 = com.google.android.exoplayer2.Format.createAudioSampleFormat(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
                goto L_0x04f2
            L_0x036c:
                boolean r6 = com.google.android.exoplayer2.util.MimeTypes.isVideo(r4)
                if (r6 == 0) goto L_0x0464
                r6 = 2
                int r7 = r0.displayUnit
                if (r7 != 0) goto L_0x0387
                int r7 = r0.displayWidth
                if (r7 != r12) goto L_0x037d
                int r7 = r0.width
            L_0x037d:
                r0.displayWidth = r7
                int r7 = r0.displayHeight
                if (r7 != r12) goto L_0x0385
                int r7 = r0.height
            L_0x0385:
                r0.displayHeight = r7
            L_0x0387:
                r7 = -1082130432(0xffffffffbf800000, float:-1.0)
                int r8 = r0.displayWidth
                if (r8 == r12) goto L_0x039d
                int r9 = r0.displayHeight
                if (r9 == r12) goto L_0x039d
                int r10 = r0.height
                int r10 = r10 * r8
                float r8 = (float) r10
                int r10 = r0.width
                int r10 = r10 * r9
                float r9 = (float) r10
                float r8 = r8 / r9
                r7 = r8
            L_0x039d:
                r8 = 0
                boolean r9 = r0.hasColorInfo
                if (r9 == 0) goto L_0x03b2
                byte[] r9 = r28.getHdrStaticInfo()
                com.google.android.exoplayer2.video.ColorInfo r10 = new com.google.android.exoplayer2.video.ColorInfo
                int r11 = r0.colorSpace
                int r12 = r0.colorRange
                int r13 = r0.colorTransfer
                r10.<init>(r11, r12, r13, r9)
                r8 = r10
            L_0x03b2:
                r9 = -1
                java.lang.String r10 = r0.name
                java.lang.String r11 = "htc_video_rotA-000"
                boolean r10 = r11.equals(r10)
                if (r10 == 0) goto L_0x03bf
                r9 = 0
                goto L_0x03e5
            L_0x03bf:
                java.lang.String r10 = r0.name
                java.lang.String r11 = "htc_video_rotA-090"
                boolean r10 = r11.equals(r10)
                if (r10 == 0) goto L_0x03cc
                r9 = 90
                goto L_0x03e5
            L_0x03cc:
                java.lang.String r10 = r0.name
                java.lang.String r11 = "htc_video_rotA-180"
                boolean r10 = r11.equals(r10)
                if (r10 == 0) goto L_0x03d9
                r9 = 180(0xb4, float:2.52E-43)
                goto L_0x03e5
            L_0x03d9:
                java.lang.String r10 = r0.name
                java.lang.String r11 = "htc_video_rotA-270"
                boolean r10 = r11.equals(r10)
                if (r10 == 0) goto L_0x03e5
                r9 = 270(0x10e, float:3.78E-43)
            L_0x03e5:
                int r10 = r0.projectionType
                if (r10 != 0) goto L_0x0435
                float r10 = r0.projectionPoseYaw
                r11 = 0
                int r10 = java.lang.Float.compare(r10, r11)
                if (r10 != 0) goto L_0x0435
                float r10 = r0.projectionPosePitch
                int r10 = java.lang.Float.compare(r10, r11)
                if (r10 != 0) goto L_0x0435
                float r10 = r0.projectionPoseRoll
                int r10 = java.lang.Float.compare(r10, r11)
                if (r10 != 0) goto L_0x0404
                r9 = 0
                goto L_0x0435
            L_0x0404:
                float r10 = r0.projectionPosePitch
                r11 = 1119092736(0x42b40000, float:90.0)
                int r10 = java.lang.Float.compare(r10, r11)
                if (r10 != 0) goto L_0x0411
                r9 = 90
                goto L_0x0435
            L_0x0411:
                float r10 = r0.projectionPosePitch
                r11 = -1020002304(0xffffffffc3340000, float:-180.0)
                int r10 = java.lang.Float.compare(r10, r11)
                if (r10 == 0) goto L_0x0433
                float r10 = r0.projectionPosePitch
                r11 = 1127481344(0x43340000, float:180.0)
                int r10 = java.lang.Float.compare(r10, r11)
                if (r10 != 0) goto L_0x0426
                goto L_0x0433
            L_0x0426:
                float r10 = r0.projectionPosePitch
                r11 = -1028390912(0xffffffffc2b40000, float:-90.0)
                int r10 = java.lang.Float.compare(r10, r11)
                if (r10 != 0) goto L_0x0435
                r9 = 270(0x10e, float:3.78E-43)
                goto L_0x0435
            L_0x0433:
                r9 = 180(0xb4, float:2.52E-43)
            L_0x0435:
                java.lang.String r13 = java.lang.Integer.toString(r30)
                r16 = -1
                int r10 = r0.width
                int r11 = r0.height
                r20 = -1082130432(0xffffffffbf800000, float:-1.0)
                byte[] r12 = r0.projectionData
                int r14 = r0.stereoMode
                com.google.android.exoplayer2.drm.DrmInitData r15 = r0.drmInitData
                r25 = r14
                r14 = r4
                r27 = r15
                r15 = 0
                r17 = r1
                r18 = r10
                r19 = r11
                r21 = r3
                r22 = r9
                r23 = r7
                r24 = r12
                r26 = r8
                com.google.android.exoplayer2.Format r7 = com.google.android.exoplayer2.Format.createVideoSampleFormat(r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)
                goto L_0x04f2
            L_0x0464:
                java.lang.String r6 = "application/x-subrip"
                boolean r6 = r6.equals(r4)
                if (r6 == 0) goto L_0x047b
                r6 = 3
                java.lang.String r7 = java.lang.Integer.toString(r30)
                java.lang.String r8 = r0.language
                com.google.android.exoplayer2.drm.DrmInitData r9 = r0.drmInitData
                com.google.android.exoplayer2.Format r7 = com.google.android.exoplayer2.Format.createTextSampleFormat(r7, r4, r5, r8, r9)
                goto L_0x04f2
            L_0x047b:
                java.lang.String r6 = "text/x-ssa"
                boolean r6 = r6.equals(r4)
                if (r6 == 0) goto L_0x04b7
                r6 = 3
                java.util.ArrayList r7 = new java.util.ArrayList
                r7.<init>(r11)
                r3 = r7
                byte[] r7 = com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.SSA_DIALOGUE_FORMAT
                r3.add(r7)
                byte[] r7 = r0.codecPrivate
                r3.add(r7)
                java.lang.String r13 = java.lang.Integer.toString(r30)
                r15 = 0
                r16 = -1
                java.lang.String r7 = r0.language
                r19 = -1
                com.google.android.exoplayer2.drm.DrmInitData r8 = r0.drmInitData
                r21 = 9223372036854775807(0x7fffffffffffffff, double:NaN)
                r14 = r4
                r17 = r5
                r18 = r7
                r20 = r8
                r23 = r3
                com.google.android.exoplayer2.Format r7 = com.google.android.exoplayer2.Format.createTextSampleFormat(r13, r14, r15, r16, r17, r18, r19, r20, r21, r23)
                goto L_0x04f2
            L_0x04b7:
                java.lang.String r6 = "application/vobsub"
                boolean r6 = r6.equals(r4)
                if (r6 != 0) goto L_0x04d8
                java.lang.String r6 = "application/pgs"
                boolean r6 = r6.equals(r4)
                if (r6 != 0) goto L_0x04d8
                java.lang.String r6 = "application/dvbsubs"
                boolean r6 = r6.equals(r4)
                if (r6 == 0) goto L_0x04d0
                goto L_0x04d8
            L_0x04d0:
                com.google.android.exoplayer2.ParserException r6 = new com.google.android.exoplayer2.ParserException
                java.lang.String r7 = "Unexpected MIME type."
                r6.<init>(r7)
                throw r6
            L_0x04d8:
                r6 = 3
                java.lang.String r13 = java.lang.Integer.toString(r30)
                r15 = 0
                r16 = -1
                java.lang.String r7 = r0.language
                com.google.android.exoplayer2.drm.DrmInitData r8 = r0.drmInitData
                r14 = r4
                r17 = r5
                r18 = r3
                r19 = r7
                r20 = r8
                com.google.android.exoplayer2.Format r7 = com.google.android.exoplayer2.Format.createImageSampleFormat(r13, r14, r15, r16, r17, r18, r19, r20)
            L_0x04f2:
                int r8 = r0.number
                r9 = r29
                com.google.android.exoplayer2.extractor.TrackOutput r8 = r9.track(r8, r6)
                r0.output = r8
                com.google.android.exoplayer2.extractor.TrackOutput r8 = r0.output
                r8.format(r7)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor.Track.initializeOutput(com.google.android.exoplayer2.extractor.ExtractorOutput, int):void");
        }

        public void outputPendingSampleMetadata() {
            TrueHdSampleRechunker trueHdSampleRechunker2 = this.trueHdSampleRechunker;
            if (trueHdSampleRechunker2 != null) {
                trueHdSampleRechunker2.outputPendingSampleMetadata(this);
            }
        }

        public void reset() {
            TrueHdSampleRechunker trueHdSampleRechunker2 = this.trueHdSampleRechunker;
            if (trueHdSampleRechunker2 != null) {
                trueHdSampleRechunker2.reset();
            }
        }

        private byte[] getHdrStaticInfo() {
            if (this.primaryRChromaticityX == -1.0f || this.primaryRChromaticityY == -1.0f || this.primaryGChromaticityX == -1.0f || this.primaryGChromaticityY == -1.0f || this.primaryBChromaticityX == -1.0f || this.primaryBChromaticityY == -1.0f || this.whitePointChromaticityX == -1.0f || this.whitePointChromaticityY == -1.0f || this.maxMasteringLuminance == -1.0f || this.minMasteringLuminance == -1.0f) {
                return null;
            }
            byte[] hdrStaticInfoData = new byte[25];
            ByteBuffer hdrStaticInfo = ByteBuffer.wrap(hdrStaticInfoData);
            hdrStaticInfo.put((byte) 0);
            hdrStaticInfo.putShort((short) ((int) ((this.primaryRChromaticityX * 50000.0f) + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) ((this.primaryRChromaticityY * 50000.0f) + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) ((this.primaryGChromaticityX * 50000.0f) + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) ((this.primaryGChromaticityY * 50000.0f) + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) ((this.primaryBChromaticityX * 50000.0f) + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) ((this.primaryBChromaticityY * 50000.0f) + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) ((this.whitePointChromaticityX * 50000.0f) + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) ((this.whitePointChromaticityY * 50000.0f) + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) (this.maxMasteringLuminance + 0.5f)));
            hdrStaticInfo.putShort((short) ((int) (this.minMasteringLuminance + 0.5f)));
            hdrStaticInfo.putShort((short) this.maxContentLuminance);
            hdrStaticInfo.putShort((short) this.maxFrameAverageLuminance);
            return hdrStaticInfoData;
        }

        private static Pair<String, List<byte[]>> parseFourCcPrivate(ParsableByteArray buffer) throws ParserException {
            try {
                buffer.skipBytes(16);
                long compression = buffer.readLittleEndianUnsignedInt();
                if (compression == 1482049860) {
                    return new Pair<>(MimeTypes.VIDEO_DIVX, null);
                }
                if (compression == 859189832) {
                    return new Pair<>(MimeTypes.VIDEO_H263, null);
                }
                if (compression == 826496599) {
                    byte[] bufferData = buffer.data;
                    for (int offset = buffer.getPosition() + 20; offset < bufferData.length - 4; offset++) {
                        if (bufferData[offset] == 0 && bufferData[offset + 1] == 0 && bufferData[offset + 2] == 1 && bufferData[offset + 3] == 15) {
                            return new Pair<>(MimeTypes.VIDEO_VC1, Collections.singletonList(Arrays.copyOfRange(bufferData, offset, bufferData.length)));
                        }
                    }
                    throw new ParserException("Failed to find FourCC VC1 initialization data");
                }
                Log.m30w(MatroskaExtractor.TAG, "Unknown FourCC. Setting mimeType to video/x-unknown");
                return new Pair<>(MimeTypes.VIDEO_UNKNOWN, null);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParserException("Error parsing FourCC private data");
            }
        }

        private static List<byte[]> parseVorbisCodecPrivate(byte[] codecPrivate2) throws ParserException {
            try {
                if (codecPrivate2[0] == 2) {
                    int offset = 1;
                    int vorbisInfoLength = 0;
                    while (codecPrivate2[offset] == -1) {
                        vorbisInfoLength += 255;
                        offset++;
                    }
                    int offset2 = offset + 1;
                    int vorbisInfoLength2 = vorbisInfoLength + codecPrivate2[offset];
                    int vorbisSkipLength = 0;
                    while (codecPrivate2[offset2] == -1) {
                        vorbisSkipLength += 255;
                        offset2++;
                    }
                    int offset3 = offset2 + 1;
                    int vorbisSkipLength2 = vorbisSkipLength + codecPrivate2[offset2];
                    if (codecPrivate2[offset3] == 1) {
                        byte[] vorbisInfo = new byte[vorbisInfoLength2];
                        System.arraycopy(codecPrivate2, offset3, vorbisInfo, 0, vorbisInfoLength2);
                        int offset4 = offset3 + vorbisInfoLength2;
                        if (codecPrivate2[offset4] == 3) {
                            int offset5 = offset4 + vorbisSkipLength2;
                            if (codecPrivate2[offset5] == 5) {
                                byte[] vorbisBooks = new byte[(codecPrivate2.length - offset5)];
                                System.arraycopy(codecPrivate2, offset5, vorbisBooks, 0, codecPrivate2.length - offset5);
                                List<byte[]> initializationData = new ArrayList<>(2);
                                initializationData.add(vorbisInfo);
                                initializationData.add(vorbisBooks);
                                return initializationData;
                            }
                            throw new ParserException("Error parsing vorbis codec private");
                        }
                        throw new ParserException("Error parsing vorbis codec private");
                    }
                    throw new ParserException("Error parsing vorbis codec private");
                }
                throw new ParserException("Error parsing vorbis codec private");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParserException("Error parsing vorbis codec private");
            }
        }

        private static boolean parseMsAcmCodecPrivate(ParsableByteArray buffer) throws ParserException {
            try {
                int formatTag = buffer.readLittleEndianUnsignedShort();
                if (formatTag == 1) {
                    return true;
                }
                if (formatTag != MatroskaExtractor.WAVE_FORMAT_EXTENSIBLE) {
                    return false;
                }
                buffer.setPosition(24);
                if (buffer.readLong() == MatroskaExtractor.WAVE_SUBFORMAT_PCM.getMostSignificantBits() && buffer.readLong() == MatroskaExtractor.WAVE_SUBFORMAT_PCM.getLeastSignificantBits()) {
                    return true;
                }
                return false;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ParserException("Error parsing MS/ACM codec private");
            }
        }
    }
}
