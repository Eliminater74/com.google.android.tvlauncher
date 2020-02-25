package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class HeifAtomParsers {
    public static final int TYPE_idat = Util.getIntegerCodeForString("idat");
    public static final int TYPE_iinf = Util.getIntegerCodeForString("iinf");
    public static final int TYPE_iloc = Util.getIntegerCodeForString("iloc");
    public static final int TYPE_iprp = Util.getIntegerCodeForString("iprp");
    public static final int TYPE_iref = Util.getIntegerCodeForString("iref");
    public static final int TYPE_pitm = Util.getIntegerCodeForString("pitm");
    private static final int TYPE_infe = Util.getIntegerCodeForString("infe");
    private static final int TYPE_ipco = Util.getIntegerCodeForString("ipco");
    private static final int TYPE_ipma = Util.getIntegerCodeForString("ipma");
    private static final int TYPE_mime = Util.getIntegerCodeForString("mime");
    private static final int TYPE_url_ = Util.getIntegerCodeForString("url ");

    private HeifAtomParsers() {
    }

    /* JADX INFO: Multiple debug info for r1v8 'constructionMethod'  int: [D('ilocData' com.google.android.exoplayer2.util.ParsableByteArray), D('constructionMethod' int)] */
    public static void parseIloc(Atom.LeafAtom ilocAtom, HeifMetaItem.HeifMetaItemsBuilder builder) throws ParserException {
        long baseOffset;
        int extentCount;
        int version;
        long extentOffset;
        int sizesData;
        ParsableByteArray ilocData;
        int constructionMethod;
        ParsableByteArray ilocData2 = ilocAtom.data;
        int i = 8;
        ilocData2.setPosition(8);
        int version2 = Atom.parseFullAtomVersion(ilocData2.readInt());
        int sizesData2 = ilocData2.readUnsignedShort();
        int offsetSize = sizesData2 >> 12;
        int lengthSize = (sizesData2 >> 8) & 15;
        int baseOffsetSize = (sizesData2 >> 4) & 15;
        int indexSizeV1V2 = sizesData2 & 15;
        int i2 = 2;
        int itemCount = version2 < 2 ? ilocData2.readUnsignedShort() : ilocData2.readUnsignedIntToInt();
        int i3 = 0;
        while (i3 < itemCount) {
            long id = -1;
            if (version2 < i2) {
                id = (long) ilocData2.readUnsignedShort();
            } else if (version2 == i2) {
                id = ilocData2.readUnsignedInt();
            }
            int constructionMethod2 = -1;
            int i4 = 1;
            if (version2 == 1 || version2 == i2) {
                ilocData2.skipBytes(1);
                constructionMethod2 = ilocData2.readUnsignedByte() & 15;
            }
            if (ilocData2.readUnsignedShort() == 0) {
                if (baseOffsetSize == 0) {
                    baseOffset = 0;
                } else if (baseOffsetSize == 4) {
                    baseOffset = ilocData2.readUnsignedInt();
                } else if (baseOffsetSize == i) {
                    baseOffset = ilocData2.readUnsignedLongToLong();
                } else {
                    throw new ParserException();
                }
                int extentCount2 = ilocData2.readUnsignedShort();
                if (extentCount2 <= 1) {
                    int j = 0;
                    while (j < extentCount2) {
                        if ((version2 == i4 || version2 == 2) && indexSizeV1V2 > 0) {
                            ilocData2.skipBytes(indexSizeV1V2);
                        }
                        if (offsetSize == 0) {
                            extentCount = extentCount2;
                            version = version2;
                            extentOffset = 0;
                        } else if (offsetSize == 4) {
                            extentCount = extentCount2;
                            version = version2;
                            extentOffset = ilocData2.readUnsignedInt();
                        } else if (offsetSize == 8) {
                            extentCount = extentCount2;
                            version = version2;
                            extentOffset = ilocData2.readUnsignedLongToLong();
                        } else {
                            throw new ParserException();
                        }
                        if (lengthSize == 0) {
                            throw new ParserException("Container length extents are not supported yet");
                        } else if (lengthSize == 4) {
                            int extentLength = ilocData2.readUnsignedIntToInt();
                            if (constructionMethod2 == 0) {
                                ilocData = ilocData2;
                                constructionMethod = constructionMethod2;
                                sizesData = sizesData2;
                                builder.addExtent(id, baseOffset + extentOffset, extentLength);
                            } else {
                                ilocData = ilocData2;
                                sizesData = sizesData2;
                                constructionMethod = constructionMethod2;
                                if (constructionMethod == 1) {
                                    Assertions.checkState(extentOffset <= 2147483647L);
                                    builder.addItemDataExtent(id, (int) extentOffset, extentLength);
                                } else if (constructionMethod == 2) {
                                    throw new ParserException("Construction method 2 is not supported yet");
                                } else {
                                    StringBuilder sb = new StringBuilder(43);
                                    sb.append("Unexpected construction method: ");
                                    sb.append(constructionMethod);
                                    throw new ParserException(sb.toString());
                                }
                            }
                            j++;
                            constructionMethod2 = constructionMethod;
                            ilocData2 = ilocData;
                            version2 = version;
                            extentCount2 = extentCount;
                            sizesData2 = sizesData;
                            i4 = 1;
                        } else if (lengthSize == 8) {
                            throw new ParserException("Large extent lengths are not supported yet");
                        } else {
                            throw new ParserException();
                        }
                    }
                    i3++;
                    ilocData2 = ilocData2;
                    sizesData2 = sizesData2;
                    i = 8;
                    i2 = 2;
                } else {
                    throw new ParserException("Multiple extents are not supported");
                }
            } else {
                throw new ParserException("Data references outside the current file are not supported");
            }
        }
    }

    public static void parseIref(Atom.LeafAtom irefAtom, HeifMetaItem.HeifMetaItemsBuilder builder) throws ParserException {
        ParsableByteArray irefData = irefAtom.data;
        irefData.setPosition(8);
        if (Atom.parseFullAtomVersion(irefData.readInt()) != 0) {
            throw new ParserException("Unsupported iref box version");
        }
        while (irefData.bytesLeft() > 0) {
            irefData.skipBytes(4);
            int entryType = irefData.readInt();
            int fromItemId = irefData.readUnsignedShort();
            int referenceCount = irefData.readUnsignedShort();
            for (int i = 0; i < referenceCount; i++) {
                builder.addReference((long) fromItemId, irefData.readUnsignedShort(), entryType);
            }
        }
    }

    public static void parseIinf(Atom.LeafAtom iinfAtom, HeifMetaItem.HeifMetaItemsBuilder builder) throws ParserException {
        ParsableByteArray iinfData = iinfAtom.data;
        iinfData.setPosition(8);
        int entryCount = Atom.parseFullAtomVersion(iinfData.readInt()) == 0 ? iinfData.readUnsignedShort() : iinfData.readUnsignedIntToInt();
        for (int i = 0; i < entryCount; i++) {
            int offset = iinfData.getPosition();
            int size = iinfData.readUnsignedIntToInt();
            int type = iinfData.readInt();
            int entryVersion = Atom.parseFullAtomVersion(iinfData.readInt());
            if (type != TYPE_infe) {
                iinfData.skipBytes(size - 12);
            } else if (entryVersion == 2) {
                int itemId = iinfData.readUnsignedShort();
                int itemProtectionIndex = iinfData.readUnsignedShort();
                int itemType = iinfData.readInt();
                String itemName = (String) Assertions.checkNotNull(iinfData.readNullTerminatedString());
                if (itemType == TYPE_mime) {
                    iinfData.readNullTerminatedString();
                    if (iinfData.getPosition() - offset < size) {
                        iinfData.readNullTerminatedString();
                    }
                } else if (itemType == TYPE_url_) {
                    iinfData.readNullTerminatedString();
                }
                builder.setEntry((long) itemId, itemName, itemType, itemProtectionIndex);
            } else {
                throw new ParserException("Expected infe box version to be 2");
            }
        }
    }

    /* JADX INFO: Multiple debug info for r6v4 int: [D('propertyIndex' int), D('associationData' int)] */
    public static void parseIprp(Atom.LeafAtom iprpAtom, HeifMetaItem.HeifMetaItemsBuilder builder) throws ParserException {
        boolean essential;
        int associationData;
        ParsableByteArray iprpData;
        ParsableByteArray iprpData2 = iprpAtom.data;
        iprpData2.setPosition(8);
        List<HeifMetaItem.Property> properties = new ArrayList<>();
        while (iprpData2.bytesLeft() > 0) {
            int size = iprpData2.readUnsignedIntToInt();
            int type = iprpData2.readInt();
            if (type == TYPE_ipco) {
                int remaining = size - 8;
                while (remaining > 0) {
                    int entrySize = iprpData2.readUnsignedIntToInt();
                    int entryType = iprpData2.readInt();
                    byte[] entryData = new byte[(entrySize - 8)];
                    iprpData2.readBytes(entryData, 0, entryData.length);
                    properties.add(new HeifMetaItem.Property(entryType, entryData));
                    remaining -= entrySize;
                }
            } else if (type == TYPE_ipma) {
                int fullAtomInt = iprpData2.readInt();
                int version = Atom.parseFullAtomVersion(fullAtomInt);
                int flags = Atom.parseFullAtomFlags(fullAtomInt);
                int entryCount = iprpData2.readUnsignedIntToInt();
                int i = 0;
                while (i < entryCount) {
                    long id = version < 1 ? (long) iprpData2.readUnsignedShort() : iprpData2.readUnsignedInt();
                    int associationCount = iprpData2.readUnsignedByte();
                    int j = 0;
                    while (j < associationCount) {
                        if ((flags & 1) != 0) {
                            int associationData2 = iprpData2.readUnsignedShort();
                            boolean essential2 = (associationData2 & 32768) != 0;
                            associationData = associationData2 & 32767;
                            essential = essential2;
                        } else {
                            int associationData3 = iprpData2.readUnsignedByte();
                            essential = (associationData3 & 128) != 0;
                            associationData = associationData3 & ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
                        }
                        if (associationData > 0) {
                            iprpData = iprpData2;
                            builder.addProperty(id, (HeifMetaItem.Property) properties.get(associationData - 1), essential);
                        } else {
                            iprpData = iprpData2;
                        }
                        j++;
                        iprpData2 = iprpData;
                    }
                    i++;
                    iprpData2 = iprpData2;
                }
            } else {
                throw new ParserException("Unexpected box in iprp");
            }
            iprpData2 = iprpData2;
        }
    }

    public static long parsePitm(Atom.LeafAtom pitmAtom) throws ParserException {
        ParsableByteArray pitmData = pitmAtom.data;
        pitmData.setPosition(8);
        return Atom.parseFullAtomVersion(pitmData.readInt()) == 1 ? pitmData.readUnsignedInt() : (long) pitmData.readUnsignedShort();
    }

    public static void parseIdat(Atom.LeafAtom idatAtom, HeifMetaItem.HeifMetaItemsBuilder builder) {
        ParsableByteArray data = idatAtom.data;
        data.setPosition(8);
        builder.setItemData(Arrays.copyOfRange(data.data, data.getPosition(), data.limit()));
    }
}
