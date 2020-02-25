package com.google.android.exoplayer2.source.hls;

import android.text.TextUtils;

import com.google.android.exoplayer2.C0841C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.PositionHolder;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.text.webvtt.WebvttParserUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class WebvttExtractor implements Extractor {
    private static final int HEADER_MAX_LENGTH = 9;
    private static final int HEADER_MIN_LENGTH = 6;
    private static final Pattern LOCAL_TIMESTAMP = Pattern.compile("LOCAL:([^,]+)");
    private static final Pattern MEDIA_TIMESTAMP = Pattern.compile("MPEGTS:(\\d+)");
    private final String language;
    private final ParsableByteArray sampleDataWrapper = new ParsableByteArray();
    private final TimestampAdjuster timestampAdjuster;
    private ExtractorOutput output;
    private byte[] sampleData = new byte[1024];
    private int sampleSize;

    public WebvttExtractor(String language2, TimestampAdjuster timestampAdjuster2) {
        this.language = language2;
        this.timestampAdjuster = timestampAdjuster2;
    }

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        input.peekFully(this.sampleData, 0, 6, false);
        this.sampleDataWrapper.reset(this.sampleData, 6);
        if (WebvttParserUtil.isWebvttHeaderLine(this.sampleDataWrapper)) {
            return true;
        }
        input.peekFully(this.sampleData, 6, 3, false);
        this.sampleDataWrapper.reset(this.sampleData, 9);
        return WebvttParserUtil.isWebvttHeaderLine(this.sampleDataWrapper);
    }

    public void init(ExtractorOutput output2) {
        this.output = output2;
        output2.seekMap(new SeekMap.Unseekable(C0841C.TIME_UNSET));
    }

    public void seek(long position, long timeUs) {
        throw new IllegalStateException();
    }

    public void release() {
    }

    public int read(ExtractorInput input, PositionHolder seekPosition) throws IOException, InterruptedException {
        int currentFileSize = (int) input.getLength();
        int i = this.sampleSize;
        byte[] bArr = this.sampleData;
        if (i == bArr.length) {
            this.sampleData = Arrays.copyOf(bArr, ((currentFileSize != -1 ? currentFileSize : bArr.length) * 3) / 2);
        }
        byte[] bArr2 = this.sampleData;
        int i2 = this.sampleSize;
        int bytesRead = input.read(bArr2, i2, bArr2.length - i2);
        if (bytesRead != -1) {
            this.sampleSize += bytesRead;
            if (currentFileSize == -1 || this.sampleSize != currentFileSize) {
                return 0;
            }
        }
        processSample();
        return -1;
    }

    private void processSample() throws ParserException {
        ParsableByteArray webvttData = new ParsableByteArray(this.sampleData);
        WebvttParserUtil.validateWebvttHeaderLine(webvttData);
        long vttTimestampUs = 0;
        long tsTimestampUs = 0;
        while (true) {
            String readLine = webvttData.readLine();
            String line = readLine;
            if (TextUtils.isEmpty(readLine)) {
                Matcher cueHeaderMatcher = WebvttParserUtil.findNextCueHeader(webvttData);
                if (cueHeaderMatcher == null) {
                    buildTrackOutput(0);
                    return;
                }
                long firstCueTimeUs = WebvttParserUtil.parseTimestampUs(cueHeaderMatcher.group(1));
                long sampleTimeUs = this.timestampAdjuster.adjustTsTimestamp(TimestampAdjuster.usToPts((firstCueTimeUs + tsTimestampUs) - vttTimestampUs));
                TrackOutput trackOutput = buildTrackOutput(sampleTimeUs - firstCueTimeUs);
                this.sampleDataWrapper.reset(this.sampleData, this.sampleSize);
                trackOutput.sampleData(this.sampleDataWrapper, this.sampleSize);
                trackOutput.sampleMetadata(sampleTimeUs, 1, this.sampleSize, 0, null);
                return;
            } else if (line.startsWith("X-TIMESTAMP-MAP")) {
                Matcher localTimestampMatcher = LOCAL_TIMESTAMP.matcher(line);
                if (!localTimestampMatcher.find()) {
                    String valueOf = String.valueOf(line);
                    throw new ParserException(valueOf.length() != 0 ? "X-TIMESTAMP-MAP doesn't contain local timestamp: ".concat(valueOf) : new String("X-TIMESTAMP-MAP doesn't contain local timestamp: "));
                }
                Matcher mediaTimestampMatcher = MEDIA_TIMESTAMP.matcher(line);
                if (!mediaTimestampMatcher.find()) {
                    String valueOf2 = String.valueOf(line);
                    throw new ParserException(valueOf2.length() != 0 ? "X-TIMESTAMP-MAP doesn't contain media timestamp: ".concat(valueOf2) : new String("X-TIMESTAMP-MAP doesn't contain media timestamp: "));
                } else {
                    vttTimestampUs = WebvttParserUtil.parseTimestampUs(localTimestampMatcher.group(1));
                    tsTimestampUs = TimestampAdjuster.ptsToUs(Long.parseLong(mediaTimestampMatcher.group(1)));
                }
            }
        }
    }

    private TrackOutput buildTrackOutput(long subsampleOffsetUs) {
        TrackOutput trackOutput = this.output.track(0, 3);
        trackOutput.format(Format.createTextSampleFormat((String) null, MimeTypes.TEXT_VTT, (String) null, -1, 0, this.language, (DrmInitData) null, subsampleOffsetUs));
        this.output.endTracks();
        return trackOutput;
    }
}
