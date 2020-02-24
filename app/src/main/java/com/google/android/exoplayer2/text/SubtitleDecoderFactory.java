package com.google.android.exoplayer2.text;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.text.cea.Cea608Decoder;
import com.google.android.exoplayer2.text.cea.Cea708Decoder;
import com.google.android.exoplayer2.text.dvb.DvbDecoder;
import com.google.android.exoplayer2.text.pgs.PgsDecoder;
import com.google.android.exoplayer2.text.ssa.SsaDecoder;
import com.google.android.exoplayer2.text.subrip.SubripDecoder;
import com.google.android.exoplayer2.text.ttml.TtmlDecoder;
import com.google.android.exoplayer2.text.tx3g.Tx3gDecoder;
import com.google.android.exoplayer2.text.webvtt.Mp4WebvttDecoder;
import com.google.android.exoplayer2.text.webvtt.WebvttDecoder;
import com.google.android.exoplayer2.util.MimeTypes;

public interface SubtitleDecoderFactory {
    public static final SubtitleDecoderFactory DEFAULT = new SubtitleDecoderFactory() {
        public boolean supportsFormat(Format format) {
            String mimeType = format.sampleMimeType;
            return MimeTypes.TEXT_VTT.equals(mimeType) || MimeTypes.TEXT_SSA.equals(mimeType) || MimeTypes.APPLICATION_TTML.equals(mimeType) || MimeTypes.APPLICATION_MP4VTT.equals(mimeType) || MimeTypes.APPLICATION_SUBRIP.equals(mimeType) || MimeTypes.APPLICATION_TX3G.equals(mimeType) || MimeTypes.APPLICATION_CEA608.equals(mimeType) || MimeTypes.APPLICATION_MP4CEA608.equals(mimeType) || MimeTypes.APPLICATION_CEA708.equals(mimeType) || MimeTypes.APPLICATION_DVBSUBS.equals(mimeType) || MimeTypes.APPLICATION_PGS.equals(mimeType);
        }

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        public SubtitleDecoder createDecoder(Format format) {
            char c;
            String str = format.sampleMimeType;
            switch (str.hashCode()) {
                case -1351681404:
                    if (str.equals(MimeTypes.APPLICATION_DVBSUBS)) {
                        c = 9;
                        break;
                    }
                    c = 65535;
                    break;
                case -1248334819:
                    if (str.equals(MimeTypes.APPLICATION_PGS)) {
                        c = 10;
                        break;
                    }
                    c = 65535;
                    break;
                case -1026075066:
                    if (str.equals(MimeTypes.APPLICATION_MP4VTT)) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1004728940:
                    if (str.equals(MimeTypes.TEXT_VTT)) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 691401887:
                    if (str.equals(MimeTypes.APPLICATION_TX3G)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case 822864842:
                    if (str.equals(MimeTypes.TEXT_SSA)) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 930165504:
                    if (str.equals(MimeTypes.APPLICATION_MP4CEA608)) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case 1566015601:
                    if (str.equals(MimeTypes.APPLICATION_CEA608)) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case 1566016562:
                    if (str.equals(MimeTypes.APPLICATION_CEA708)) {
                        c = 8;
                        break;
                    }
                    c = 65535;
                    break;
                case 1668750253:
                    if (str.equals(MimeTypes.APPLICATION_SUBRIP)) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1693976202:
                    if (str.equals(MimeTypes.APPLICATION_TTML)) {
                        c = 3;
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
                    return new WebvttDecoder();
                case 1:
                    return new SsaDecoder(format.initializationData);
                case 2:
                    return new Mp4WebvttDecoder();
                case 3:
                    return new TtmlDecoder();
                case 4:
                    return new SubripDecoder();
                case 5:
                    return new Tx3gDecoder(format.initializationData);
                case 6:
                case 7:
                    return new Cea608Decoder(format.sampleMimeType, format.accessibilityChannel);
                case 8:
                    return new Cea708Decoder(format.accessibilityChannel, format.initializationData);
                case 9:
                    return new DvbDecoder(format.initializationData);
                case 10:
                    return new PgsDecoder();
                default:
                    throw new IllegalArgumentException("Attempted to create decoder for unsupported format");
            }
        }
    };

    SubtitleDecoder createDecoder(Format format);

    boolean supportsFormat(Format format);
}
