package com.google.android.exoplayer2.extractor.mp4;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.google.android.exoplayer2.metadata.id3.CommentFrame;
import com.google.android.exoplayer2.metadata.id3.Id3Frame;
import com.google.android.exoplayer2.metadata.id3.InternalFrame;
import com.google.android.exoplayer2.metadata.id3.TextInformationFrame;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.nio.ByteBuffer;

final class MetadataUtil {
    private static final String LANGUAGE_UNDEFINED = "und";
    private static final String MDTA_KEY_ANDROID_CAPTURE_FPS = "com.android.capture.fps";
    private static final int MDTA_TYPE_INDICATOR_FLOAT = 23;
    private static final int PICTURE_TYPE_FRONT_COVER = 3;
    private static final int SHORT_TYPE_ALBUM = Util.getIntegerCodeForString("alb");
    private static final int SHORT_TYPE_ARTIST = Util.getIntegerCodeForString("ART");
    private static final int SHORT_TYPE_COMMENT = Util.getIntegerCodeForString("cmt");
    private static final int SHORT_TYPE_COMPOSER_1 = Util.getIntegerCodeForString("com");
    private static final int SHORT_TYPE_COMPOSER_2 = Util.getIntegerCodeForString("wrt");
    private static final int SHORT_TYPE_ENCODER = Util.getIntegerCodeForString("too");
    private static final int SHORT_TYPE_GENRE = Util.getIntegerCodeForString("gen");
    private static final int SHORT_TYPE_LYRICS = Util.getIntegerCodeForString("lyr");
    private static final int SHORT_TYPE_NAME_1 = Util.getIntegerCodeForString("nam");
    private static final int SHORT_TYPE_NAME_2 = Util.getIntegerCodeForString("trk");
    private static final int SHORT_TYPE_YEAR = Util.getIntegerCodeForString("day");
    private static final String[] STANDARD_GENRES;
    private static final String TAG = "MetadataUtil";
    private static final int TYPE_ALBUM_ARTIST = Util.getIntegerCodeForString("aART");
    private static final int TYPE_COMPILATION = Util.getIntegerCodeForString("cpil");
    private static final int TYPE_COVER_ART = Util.getIntegerCodeForString("covr");
    private static final int TYPE_DISK_NUMBER = Util.getIntegerCodeForString("disk");
    private static final int TYPE_GAPLESS_ALBUM = Util.getIntegerCodeForString("pgap");
    private static final int TYPE_GENRE = Util.getIntegerCodeForString("gnre");
    private static final int TYPE_GROUPING = Util.getIntegerCodeForString("grp");
    private static final int TYPE_INTERNAL = Util.getIntegerCodeForString(InternalFrame.f86ID);
    private static final int TYPE_RATING = Util.getIntegerCodeForString("rtng");
    private static final int TYPE_SORT_ALBUM = Util.getIntegerCodeForString("soal");
    private static final int TYPE_SORT_ALBUM_ARTIST = Util.getIntegerCodeForString("soaa");
    private static final int TYPE_SORT_ARTIST = Util.getIntegerCodeForString("soar");
    private static final int TYPE_SORT_COMPOSER = Util.getIntegerCodeForString("soco");
    private static final int TYPE_SORT_TRACK_NAME = Util.getIntegerCodeForString("sonm");
    private static final int TYPE_TEMPO = Util.getIntegerCodeForString("tmpo");
    private static final int TYPE_TOP_BYTE_COPYRIGHT = 169;
    private static final int TYPE_TOP_BYTE_REPLACEMENT = 253;
    private static final int TYPE_TRACK_NUMBER = Util.getIntegerCodeForString("trkn");
    private static final int TYPE_TV_SHOW = Util.getIntegerCodeForString("tvsh");
    private static final int TYPE_TV_SORT_SHOW = Util.getIntegerCodeForString("sosn");

    static {
        String[] strArr = new String[ClientAnalytics.LogRequest.LogSource.FLYDROID_COUNTERS_VALUE];
        strArr[0] = "Blues";
        strArr[1] = "Classic Rock";
        strArr[2] = "Country";
        strArr[3] = "Dance";
        strArr[4] = "Disco";
        strArr[5] = "Funk";
        strArr[6] = "Grunge";
        strArr[7] = "Hip-Hop";
        strArr[8] = "Jazz";
        strArr[9] = "Metal";
        strArr[10] = "New Age";
        strArr[11] = "Oldies";
        strArr[12] = "Other";
        strArr[13] = "Pop";
        strArr[14] = "R&B";
        strArr[15] = "Rap";
        strArr[16] = "Reggae";
        strArr[17] = "Rock";
        strArr[18] = "Techno";
        strArr[19] = "Industrial";
        strArr[20] = "Alternative";
        strArr[21] = "Ska";
        strArr[22] = "Death Metal";
        strArr[23] = "Pranks";
        strArr[24] = "Soundtrack";
        strArr[25] = "Euro-Techno";
        strArr[26] = "Ambient";
        strArr[27] = "Trip-Hop";
        strArr[28] = "Vocal";
        strArr[29] = "Jazz+Funk";
        strArr[30] = "Fusion";
        strArr[31] = "Trance";
        strArr[32] = "Classical";
        strArr[33] = "Instrumental";
        strArr[34] = "Acid";
        strArr[35] = "House";
        strArr[36] = "Game";
        strArr[37] = "Sound Clip";
        strArr[38] = "Gospel";
        strArr[39] = "Noise";
        strArr[40] = "AlternRock";
        strArr[41] = "Bass";
        strArr[42] = "Soul";
        strArr[43] = "Punk";
        strArr[44] = "Space";
        strArr[45] = "Meditative";
        strArr[46] = "Instrumental Pop";
        strArr[47] = "Instrumental Rock";
        strArr[48] = "Ethnic";
        strArr[49] = "Gothic";
        strArr[50] = "Darkwave";
        strArr[51] = "Techno-Industrial";
        strArr[52] = "Electronic";
        strArr[53] = "Pop-Folk";
        strArr[54] = "Eurodance";
        strArr[55] = "Dream";
        strArr[56] = "Southern Rock";
        strArr[57] = "Comedy";
        strArr[58] = "Cult";
        strArr[59] = "Gangsta";
        strArr[60] = "Top 40";
        strArr[61] = "Christian Rap";
        strArr[62] = "Pop/Funk";
        strArr[63] = "Jungle";
        strArr[64] = "Native American";
        strArr[65] = "Cabaret";
        strArr[66] = "New Wave";
        strArr[67] = "Psychadelic";
        strArr[68] = "Rave";
        strArr[69] = "Showtunes";
        strArr[70] = "Trailer";
        strArr[71] = "Lo-Fi";
        strArr[72] = "Tribal";
        strArr[73] = "Acid Punk";
        strArr[74] = "Acid Jazz";
        strArr[75] = "Polka";
        strArr[76] = "Retro";
        strArr[77] = "Musical";
        strArr[78] = "Rock & Roll";
        strArr[79] = "Hard Rock";
        strArr[80] = "Folk";
        strArr[81] = "Folk-Rock";
        strArr[82] = "National Folk";
        strArr[83] = "Swing";
        strArr[84] = "Fast Fusion";
        strArr[85] = "Bebob";
        strArr[86] = "Latin";
        strArr[87] = "Revival";
        strArr[88] = "Celtic";
        strArr[89] = "Bluegrass";
        strArr[90] = "Avantgarde";
        strArr[91] = "Gothic Rock";
        strArr[92] = "Progressive Rock";
        strArr[93] = "Psychedelic Rock";
        strArr[94] = "Symphonic Rock";
        strArr[95] = "Slow Rock";
        strArr[96] = "Big Band";
        strArr[97] = "Chorus";
        strArr[98] = "Easy Listening";
        strArr[99] = "Acoustic";
        strArr[100] = "Humour";
        strArr[101] = "Speech";
        strArr[102] = "Chanson";
        strArr[103] = "Opera";
        strArr[104] = "Chamber Music";
        strArr[105] = "Sonata";
        strArr[106] = "Symphony";
        strArr[107] = "Booty Bass";
        strArr[108] = "Primus";
        strArr[109] = "Porn Groove";
        strArr[110] = "Satire";
        strArr[111] = "Slow Jam";
        strArr[112] = "Club";
        strArr[113] = "Tango";
        strArr[114] = "Samba";
        strArr[115] = "Folklore";
        strArr[116] = "Ballad";
        strArr[117] = "Power Ballad";
        strArr[118] = "Rhythmic Soul";
        strArr[119] = "Freestyle";
        strArr[120] = "Duet";
        strArr[121] = "Punk Rock";
        strArr[122] = "Drum Solo";
        strArr[123] = "A capella";
        strArr[124] = "Euro-House";
        strArr[125] = "Dance Hall";
        strArr[126] = "Goa";
        strArr[127] = "Drum & Bass";
        strArr[128] = "Club-House";
        strArr[129] = "Hardcore";
        strArr[130] = "Terror";
        strArr[131] = "Indie";
        strArr[132] = "BritPop";
        strArr[133] = "Negerpunk";
        strArr[134] = "Polsk Punk";
        strArr[135] = "Beat";
        strArr[136] = "Christian Gangsta Rap";
        strArr[137] = "Heavy Metal";
        strArr[138] = "Black Metal";
        strArr[139] = "Crossover";
        strArr[140] = "Contemporary Christian";
        strArr[141] = "Christian Rock";
        strArr[142] = "Merengue";
        strArr[143] = "Salsa";
        strArr[144] = "Thrash Metal";
        strArr[145] = "Anime";
        strArr[146] = "Jpop";
        strArr[147] = "Synthpop";
        STANDARD_GENRES = strArr;
    }

    private MetadataUtil() {
    }

    public static Format getFormatWithMetadata(int trackType, Format format, @Nullable Metadata udtaMetadata, @Nullable Metadata mdtaMetadata, GaplessInfoHolder gaplessInfoHolder) {
        if (trackType == 1) {
            if (gaplessInfoHolder.hasGaplessInfo()) {
                format = format.copyWithGaplessInfo(gaplessInfoHolder.encoderDelay, gaplessInfoHolder.encoderPadding);
            }
            if (udtaMetadata != null) {
                return format.copyWithMetadata(udtaMetadata);
            }
            return format;
        } else if (trackType != 2 || mdtaMetadata == null) {
            return format;
        } else {
            for (int i = 0; i < mdtaMetadata.length(); i++) {
                Metadata.Entry entry = mdtaMetadata.get(i);
                if (entry instanceof MdtaMetadataEntry) {
                    MdtaMetadataEntry mdtaMetadataEntry = (MdtaMetadataEntry) entry;
                    if (MDTA_KEY_ANDROID_CAPTURE_FPS.equals(mdtaMetadataEntry.key) && mdtaMetadataEntry.typeIndicator == 23) {
                        try {
                            format = format.copyWithFrameRate(ByteBuffer.wrap(mdtaMetadataEntry.value).asFloatBuffer().get()).copyWithMetadata(new Metadata(mdtaMetadataEntry));
                        } catch (NumberFormatException e) {
                            Log.m30w(TAG, "Ignoring invalid framerate");
                        }
                    }
                }
            }
            return format;
        }
    }

    @Nullable
    public static Metadata.Entry parseIlstElement(ParsableByteArray ilst) {
        int endPosition = ilst.readInt() + ilst.getPosition();
        int type = ilst.readInt();
        int typeTopByte = (type >> 24) & 255;
        if (typeTopByte == 169 || typeTopByte == 253) {
            int shortType = 16777215 & type;
            if (shortType == SHORT_TYPE_COMMENT) {
                CommentFrame parseCommentAttribute = parseCommentAttribute(type, ilst);
                ilst.setPosition(endPosition);
                return parseCommentAttribute;
            }
            if (shortType != SHORT_TYPE_NAME_1) {
                if (shortType != SHORT_TYPE_NAME_2) {
                    if (shortType != SHORT_TYPE_COMPOSER_1) {
                        if (shortType != SHORT_TYPE_COMPOSER_2) {
                            if (shortType == SHORT_TYPE_YEAR) {
                                TextInformationFrame parseTextAttribute = parseTextAttribute(type, "TDRC", ilst);
                                ilst.setPosition(endPosition);
                                return parseTextAttribute;
                            } else if (shortType == SHORT_TYPE_ARTIST) {
                                TextInformationFrame parseTextAttribute2 = parseTextAttribute(type, "TPE1", ilst);
                                ilst.setPosition(endPosition);
                                return parseTextAttribute2;
                            } else if (shortType == SHORT_TYPE_ENCODER) {
                                TextInformationFrame parseTextAttribute3 = parseTextAttribute(type, "TSSE", ilst);
                                ilst.setPosition(endPosition);
                                return parseTextAttribute3;
                            } else if (shortType == SHORT_TYPE_ALBUM) {
                                TextInformationFrame parseTextAttribute4 = parseTextAttribute(type, "TALB", ilst);
                                ilst.setPosition(endPosition);
                                return parseTextAttribute4;
                            } else if (shortType == SHORT_TYPE_LYRICS) {
                                TextInformationFrame parseTextAttribute5 = parseTextAttribute(type, "USLT", ilst);
                                ilst.setPosition(endPosition);
                                return parseTextAttribute5;
                            } else if (shortType == SHORT_TYPE_GENRE) {
                                TextInformationFrame parseTextAttribute6 = parseTextAttribute(type, "TCON", ilst);
                                ilst.setPosition(endPosition);
                                return parseTextAttribute6;
                            } else if (shortType == TYPE_GROUPING) {
                                TextInformationFrame parseTextAttribute7 = parseTextAttribute(type, "TIT1", ilst);
                                ilst.setPosition(endPosition);
                                return parseTextAttribute7;
                            }
                        }
                    }
                    TextInformationFrame parseTextAttribute8 = parseTextAttribute(type, "TCOM", ilst);
                    ilst.setPosition(endPosition);
                    return parseTextAttribute8;
                }
            }
            TextInformationFrame parseTextAttribute9 = parseTextAttribute(type, "TIT2", ilst);
            ilst.setPosition(endPosition);
            return parseTextAttribute9;
        }
        try {
            if (type == TYPE_GENRE) {
                return parseStandardGenreAttribute(ilst);
            }
            if (type == TYPE_DISK_NUMBER) {
                TextInformationFrame parseIndexAndCountAttribute = parseIndexAndCountAttribute(type, "TPOS", ilst);
                ilst.setPosition(endPosition);
                return parseIndexAndCountAttribute;
            } else if (type == TYPE_TRACK_NUMBER) {
                TextInformationFrame parseIndexAndCountAttribute2 = parseIndexAndCountAttribute(type, "TRCK", ilst);
                ilst.setPosition(endPosition);
                return parseIndexAndCountAttribute2;
            } else if (type == TYPE_TEMPO) {
                Id3Frame parseUint8Attribute = parseUint8Attribute(type, "TBPM", ilst, true, false);
                ilst.setPosition(endPosition);
                return parseUint8Attribute;
            } else if (type == TYPE_COMPILATION) {
                Id3Frame parseUint8Attribute2 = parseUint8Attribute(type, "TCMP", ilst, true, true);
                ilst.setPosition(endPosition);
                return parseUint8Attribute2;
            } else if (type == TYPE_COVER_ART) {
                ApicFrame parseCoverArt = parseCoverArt(ilst);
                ilst.setPosition(endPosition);
                return parseCoverArt;
            } else if (type == TYPE_ALBUM_ARTIST) {
                TextInformationFrame parseTextAttribute10 = parseTextAttribute(type, "TPE2", ilst);
                ilst.setPosition(endPosition);
                return parseTextAttribute10;
            } else if (type == TYPE_SORT_TRACK_NAME) {
                TextInformationFrame parseTextAttribute11 = parseTextAttribute(type, "TSOT", ilst);
                ilst.setPosition(endPosition);
                return parseTextAttribute11;
            } else if (type == TYPE_SORT_ALBUM) {
                TextInformationFrame parseTextAttribute12 = parseTextAttribute(type, "TSO2", ilst);
                ilst.setPosition(endPosition);
                return parseTextAttribute12;
            } else if (type == TYPE_SORT_ARTIST) {
                TextInformationFrame parseTextAttribute13 = parseTextAttribute(type, "TSOA", ilst);
                ilst.setPosition(endPosition);
                return parseTextAttribute13;
            } else if (type == TYPE_SORT_ALBUM_ARTIST) {
                TextInformationFrame parseTextAttribute14 = parseTextAttribute(type, "TSOP", ilst);
                ilst.setPosition(endPosition);
                return parseTextAttribute14;
            } else if (type == TYPE_SORT_COMPOSER) {
                TextInformationFrame parseTextAttribute15 = parseTextAttribute(type, "TSOC", ilst);
                ilst.setPosition(endPosition);
                return parseTextAttribute15;
            } else if (type == TYPE_RATING) {
                Id3Frame parseUint8Attribute3 = parseUint8Attribute(type, "ITUNESADVISORY", ilst, false, false);
                ilst.setPosition(endPosition);
                return parseUint8Attribute3;
            } else if (type == TYPE_GAPLESS_ALBUM) {
                Id3Frame parseUint8Attribute4 = parseUint8Attribute(type, "ITUNESGAPLESS", ilst, false, true);
                ilst.setPosition(endPosition);
                return parseUint8Attribute4;
            } else if (type == TYPE_TV_SORT_SHOW) {
                TextInformationFrame parseTextAttribute16 = parseTextAttribute(type, "TVSHOWSORT", ilst);
                ilst.setPosition(endPosition);
                return parseTextAttribute16;
            } else if (type == TYPE_TV_SHOW) {
                TextInformationFrame parseTextAttribute17 = parseTextAttribute(type, "TVSHOW", ilst);
                ilst.setPosition(endPosition);
                return parseTextAttribute17;
            } else if (type == TYPE_INTERNAL) {
                Id3Frame parseInternalAttribute = parseInternalAttribute(ilst, endPosition);
                ilst.setPosition(endPosition);
                return parseInternalAttribute;
            }
        } finally {
            ilst.setPosition(endPosition);
        }
        String valueOf = String.valueOf(Atom.getAtomTypeString(type));
        Log.m24d(TAG, valueOf.length() != 0 ? "Skipped unknown metadata entry: ".concat(valueOf) : new String("Skipped unknown metadata entry: "));
        ilst.setPosition(endPosition);
        return null;
    }

    @Nullable
    public static MdtaMetadataEntry parseMdtaMetadataEntryFromIlst(ParsableByteArray ilst, int endPosition, String key) {
        while (true) {
            int position = ilst.getPosition();
            int atomPosition = position;
            if (position >= endPosition) {
                return null;
            }
            int atomSize = ilst.readInt();
            if (ilst.readInt() == Atom.TYPE_data) {
                int typeIndicator = ilst.readInt();
                int localeIndicator = ilst.readInt();
                int dataSize = atomSize - 16;
                byte[] value = new byte[dataSize];
                ilst.readBytes(value, 0, dataSize);
                return new MdtaMetadataEntry(key, value, localeIndicator, typeIndicator);
            }
            ilst.setPosition(atomPosition + atomSize);
        }
    }

    @Nullable
    private static TextInformationFrame parseTextAttribute(int type, String id, ParsableByteArray data) {
        int atomSize = data.readInt();
        if (data.readInt() == Atom.TYPE_data) {
            data.skipBytes(8);
            return new TextInformationFrame(id, null, data.readNullTerminatedString(atomSize - 16));
        }
        String valueOf = String.valueOf(Atom.getAtomTypeString(type));
        Log.m30w(TAG, valueOf.length() != 0 ? "Failed to parse text attribute: ".concat(valueOf) : new String("Failed to parse text attribute: "));
        return null;
    }

    @Nullable
    private static CommentFrame parseCommentAttribute(int type, ParsableByteArray data) {
        int atomSize = data.readInt();
        if (data.readInt() == Atom.TYPE_data) {
            data.skipBytes(8);
            String value = data.readNullTerminatedString(atomSize - 16);
            return new CommentFrame("und", value, value);
        }
        String valueOf = String.valueOf(Atom.getAtomTypeString(type));
        Log.m30w(TAG, valueOf.length() != 0 ? "Failed to parse comment attribute: ".concat(valueOf) : new String("Failed to parse comment attribute: "));
        return null;
    }

    @Nullable
    private static Id3Frame parseUint8Attribute(int type, String id, ParsableByteArray data, boolean isTextInformationFrame, boolean isBoolean) {
        int value = parseUint8AttributeValue(data);
        if (isBoolean) {
            value = Math.min(1, value);
        }
        if (value < 0) {
            String valueOf = String.valueOf(Atom.getAtomTypeString(type));
            Log.m30w(TAG, valueOf.length() != 0 ? "Failed to parse uint8 attribute: ".concat(valueOf) : new String("Failed to parse uint8 attribute: "));
            return null;
        } else if (isTextInformationFrame) {
            return new TextInformationFrame(id, null, Integer.toString(value));
        } else {
            return new CommentFrame("und", id, Integer.toString(value));
        }
    }

    @Nullable
    private static TextInformationFrame parseIndexAndCountAttribute(int type, String attributeName, ParsableByteArray data) {
        int atomSize = data.readInt();
        if (data.readInt() == Atom.TYPE_data && atomSize >= 22) {
            data.skipBytes(10);
            int index = data.readUnsignedShort();
            if (index > 0) {
                StringBuilder sb = new StringBuilder(11);
                sb.append(index);
                String value = sb.toString();
                int count = data.readUnsignedShort();
                if (count > 0) {
                    String valueOf = String.valueOf(value);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf).length() + 12);
                    sb2.append(valueOf);
                    sb2.append("/");
                    sb2.append(count);
                    value = sb2.toString();
                }
                return new TextInformationFrame(attributeName, null, value);
            }
        }
        String valueOf2 = String.valueOf(Atom.getAtomTypeString(type));
        Log.m30w(TAG, valueOf2.length() != 0 ? "Failed to parse index/count attribute: ".concat(valueOf2) : new String("Failed to parse index/count attribute: "));
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0014  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001c  */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.google.android.exoplayer2.metadata.id3.TextInformationFrame parseStandardGenreAttribute(com.google.android.exoplayer2.util.ParsableByteArray r5) {
        /*
            int r0 = parseUint8AttributeValue(r5)
            r1 = 0
            if (r0 <= 0) goto L_0x0011
            java.lang.String[] r2 = com.google.android.exoplayer2.extractor.mp4.MetadataUtil.STANDARD_GENRES
            int r3 = r2.length
            if (r0 > r3) goto L_0x0011
            int r3 = r0 + -1
            r2 = r2[r3]
            goto L_0x0012
        L_0x0011:
            r2 = r1
        L_0x0012:
            if (r2 == 0) goto L_0x001c
            com.google.android.exoplayer2.metadata.id3.TextInformationFrame r3 = new com.google.android.exoplayer2.metadata.id3.TextInformationFrame
            java.lang.String r4 = "TCON"
            r3.<init>(r4, r1, r2)
            return r3
        L_0x001c:
            java.lang.String r3 = "MetadataUtil"
            java.lang.String r4 = "Failed to parse standard genre code"
            com.google.android.exoplayer2.util.Log.m30w(r3, r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.extractor.mp4.MetadataUtil.parseStandardGenreAttribute(com.google.android.exoplayer2.util.ParsableByteArray):com.google.android.exoplayer2.metadata.id3.TextInformationFrame");
    }

    @Nullable
    private static ApicFrame parseCoverArt(ParsableByteArray data) {
        int atomSize = data.readInt();
        if (data.readInt() == Atom.TYPE_data) {
            int flags = Atom.parseFullAtomFlags(data.readInt());
            String mimeType = flags == 13 ? "image/jpeg" : flags == 14 ? "image/png" : null;
            if (mimeType == null) {
                StringBuilder sb = new StringBuilder(41);
                sb.append("Unrecognized cover art flags: ");
                sb.append(flags);
                Log.m30w(TAG, sb.toString());
                return null;
            }
            data.skipBytes(4);
            byte[] pictureData = new byte[(atomSize - 16)];
            data.readBytes(pictureData, 0, pictureData.length);
            return new ApicFrame(mimeType, null, 3, pictureData);
        }
        Log.m30w(TAG, "Failed to parse cover art attribute");
        return null;
    }

    @Nullable
    private static Id3Frame parseInternalAttribute(ParsableByteArray data, int endPosition) {
        String domain = null;
        String name = null;
        int dataAtomPosition = -1;
        int dataAtomSize = -1;
        while (data.getPosition() < endPosition) {
            int atomPosition = data.getPosition();
            int atomSize = data.readInt();
            int atomType = data.readInt();
            data.skipBytes(4);
            if (atomType == Atom.TYPE_mean) {
                domain = data.readNullTerminatedString(atomSize - 12);
            } else if (atomType == Atom.TYPE_name) {
                name = data.readNullTerminatedString(atomSize - 12);
            } else {
                if (atomType == Atom.TYPE_data) {
                    dataAtomPosition = atomPosition;
                    dataAtomSize = atomSize;
                }
                data.skipBytes(atomSize - 12);
            }
        }
        if (domain == null || name == null || dataAtomPosition == -1) {
            return null;
        }
        data.setPosition(dataAtomPosition);
        data.skipBytes(16);
        return new InternalFrame(domain, name, data.readNullTerminatedString(dataAtomSize - 16));
    }

    private static int parseUint8AttributeValue(ParsableByteArray data) {
        data.skipBytes(4);
        if (data.readInt() == Atom.TYPE_data) {
            data.skipBytes(8);
            return data.readUnsignedByte();
        }
        Log.m30w(TAG, "Failed to parse uint8 attribute value");
        return -1;
    }
}
