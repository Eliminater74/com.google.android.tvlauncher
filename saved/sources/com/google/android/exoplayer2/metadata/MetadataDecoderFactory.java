package com.google.android.exoplayer2.metadata;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.metadata.emsg.EventMessageDecoder;
import com.google.android.exoplayer2.metadata.icy.IcyDecoder;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.metadata.scte35.SpliceInfoDecoder;
import com.google.android.exoplayer2.util.MimeTypes;

public interface MetadataDecoderFactory {
    public static final MetadataDecoderFactory DEFAULT = new MetadataDecoderFactory() {
        public boolean supportsFormat(Format format) {
            String mimeType = format.sampleMimeType;
            return MimeTypes.APPLICATION_ID3.equals(mimeType) || MimeTypes.APPLICATION_EMSG.equals(mimeType) || MimeTypes.APPLICATION_SCTE35.equals(mimeType) || MimeTypes.APPLICATION_ICY.equals(mimeType);
        }

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public MetadataDecoder createDecoder(Format format) {
            char c;
            String str = format.sampleMimeType;
            switch (str.hashCode()) {
                case -1348231605:
                    if (str.equals(MimeTypes.APPLICATION_ICY)) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1248341703:
                    if (str.equals(MimeTypes.APPLICATION_ID3)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1154383568:
                    if (str.equals(MimeTypes.APPLICATION_EMSG)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1652648887:
                    if (str.equals(MimeTypes.APPLICATION_SCTE35)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                return new Id3Decoder();
            }
            if (c == 1) {
                return new EventMessageDecoder();
            }
            if (c == 2) {
                return new SpliceInfoDecoder();
            }
            if (c == 3) {
                return new IcyDecoder();
            }
            throw new IllegalArgumentException("Attempted to create decoder for unsupported format");
        }
    };

    MetadataDecoder createDecoder(Format format);

    boolean supportsFormat(Format format);
}
