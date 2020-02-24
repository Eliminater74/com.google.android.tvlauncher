package com.google.android.exoplayer2.extractor;

import com.google.android.exoplayer2.extractor.amr.AmrExtractor;
import com.google.android.exoplayer2.extractor.flv.FlvExtractor;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.extractor.ogg.OggExtractor;
import com.google.android.exoplayer2.extractor.p007ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.p007ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.p007ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.p007ts.PsExtractor;
import com.google.android.exoplayer2.extractor.p007ts.TsExtractor;
import com.google.android.exoplayer2.extractor.wav.WavExtractor;
import java.lang.reflect.Constructor;

public final class DefaultExtractorsFactory implements ExtractorsFactory {
    private static final Constructor<? extends Extractor> FLAC_EXTRACTOR_CONSTRUCTOR;
    private int adtsFlags;
    private int amrFlags;
    private boolean constantBitrateSeekingEnabled;
    private int fragmentedMp4Flags;
    private int matroskaFlags;
    private int mp3Flags;
    private int mp4Flags;
    private int tsFlags;
    private int tsMode = 1;

    static {
        Constructor<? extends Extractor> flacExtractorConstructor = null;
        try {
            flacExtractorConstructor = Class.forName("com.google.android.exoplayer2.ext.flac.FlacExtractor").asSubclass(Extractor.class).getConstructor(new Class[0]);
        } catch (ClassNotFoundException e) {
        } catch (Exception e2) {
            throw new RuntimeException("Error instantiating FLAC extension", e2);
        }
        FLAC_EXTRACTOR_CONSTRUCTOR = flacExtractorConstructor;
    }

    public synchronized DefaultExtractorsFactory setConstantBitrateSeekingEnabled(boolean constantBitrateSeekingEnabled2) {
        this.constantBitrateSeekingEnabled = constantBitrateSeekingEnabled2;
        return this;
    }

    public synchronized DefaultExtractorsFactory setAdtsExtractorFlags(int flags) {
        this.adtsFlags = flags;
        return this;
    }

    public synchronized DefaultExtractorsFactory setAmrExtractorFlags(int flags) {
        this.amrFlags = flags;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMatroskaExtractorFlags(int flags) {
        this.matroskaFlags = flags;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMp4ExtractorFlags(int flags) {
        this.mp4Flags = flags;
        return this;
    }

    public synchronized DefaultExtractorsFactory setFragmentedMp4ExtractorFlags(int flags) {
        this.fragmentedMp4Flags = flags;
        return this;
    }

    public synchronized DefaultExtractorsFactory setMp3ExtractorFlags(int flags) {
        this.mp3Flags = flags;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorMode(int mode) {
        this.tsMode = mode;
        return this;
    }

    public synchronized DefaultExtractorsFactory setTsExtractorFlags(int flags) {
        this.tsFlags = flags;
        return this;
    }

    public synchronized Extractor[] createExtractors() {
        Extractor[] extractors;
        int i;
        int i2;
        extractors = new Extractor[(FLAC_EXTRACTOR_CONSTRUCTOR == null ? 13 : 14)];
        extractors[0] = new MatroskaExtractor(this.matroskaFlags);
        int i3 = 1;
        extractors[1] = new FragmentedMp4Extractor(this.fragmentedMp4Flags);
        extractors[2] = new Mp4Extractor(this.mp4Flags);
        int i4 = this.mp3Flags;
        if (this.constantBitrateSeekingEnabled) {
            i = 1;
        } else {
            i = 0;
        }
        extractors[3] = new Mp3Extractor(i4 | i);
        int i5 = this.adtsFlags;
        if (this.constantBitrateSeekingEnabled) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        extractors[4] = new AdtsExtractor(0, i5 | i2);
        extractors[5] = new Ac3Extractor();
        extractors[6] = new TsExtractor(this.tsMode, this.tsFlags);
        extractors[7] = new FlvExtractor();
        extractors[8] = new OggExtractor();
        extractors[9] = new PsExtractor();
        extractors[10] = new WavExtractor();
        int i6 = this.amrFlags;
        if (!this.constantBitrateSeekingEnabled) {
            i3 = 0;
        }
        extractors[11] = new AmrExtractor(i3 | i6);
        extractors[12] = new Ac4Extractor();
        if (FLAC_EXTRACTOR_CONSTRUCTOR != null) {
            try {
                extractors[13] = (Extractor) FLAC_EXTRACTOR_CONSTRUCTOR.newInstance(new Object[0]);
            } catch (Exception e) {
                throw new IllegalStateException("Unexpected error creating FLAC extractor", e);
            }
        }
        return extractors;
    }
}
