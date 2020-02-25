package com.google.android.exoplayer2.text.dvb;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Region;
import android.util.SparseArray;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Ascii;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class DvbParser {
    private static final int DATA_TYPE_24_TABLE_DATA = 32;
    private static final int DATA_TYPE_28_TABLE_DATA = 33;
    private static final int DATA_TYPE_2BP_CODE_STRING = 16;
    private static final int DATA_TYPE_48_TABLE_DATA = 34;
    private static final int DATA_TYPE_4BP_CODE_STRING = 17;
    private static final int DATA_TYPE_8BP_CODE_STRING = 18;
    private static final int DATA_TYPE_END_LINE = 240;
    private static final int OBJECT_CODING_PIXELS = 0;
    private static final int OBJECT_CODING_STRING = 1;
    private static final int PAGE_STATE_NORMAL = 0;
    private static final int REGION_DEPTH_4_BIT = 2;
    private static final int REGION_DEPTH_8_BIT = 3;
    private static final int SEGMENT_TYPE_CLUT_DEFINITION = 18;
    private static final int SEGMENT_TYPE_DISPLAY_DEFINITION = 20;
    private static final int SEGMENT_TYPE_OBJECT_DATA = 19;
    private static final int SEGMENT_TYPE_PAGE_COMPOSITION = 16;
    private static final int SEGMENT_TYPE_REGION_COMPOSITION = 17;
    private static final String TAG = "DvbParser";
    private static final byte[] defaultMap2To4 = {0, 7, 8, Ascii.f156SI};
    private static final byte[] defaultMap2To8 = {0, 119, -120, -1};
    private static final byte[] defaultMap4To8 = {0, 17, 34, 51, 68, 85, 102, 119, -120, -103, -86, -69, -52, -35, -18, -1};
    private final Canvas canvas;
    private final ClutDefinition defaultClutDefinition;
    private final DisplayDefinition defaultDisplayDefinition;
    private final Paint defaultPaint = new Paint();
    private final Paint fillRegionPaint;
    private final SubtitleService subtitleService;
    private Bitmap bitmap;

    public DvbParser(int subtitlePageId, int ancillaryPageId) {
        this.defaultPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.defaultPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        this.defaultPaint.setPathEffect(null);
        this.fillRegionPaint = new Paint();
        this.fillRegionPaint.setStyle(Paint.Style.FILL);
        this.fillRegionPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        this.fillRegionPaint.setPathEffect(null);
        this.canvas = new Canvas();
        this.defaultDisplayDefinition = new DisplayDefinition(ClientAnalytics.LogRequest.LogSource.WING_OPENSKY_ANDROID_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.BASELINE_IOS_PRIMES_VALUE, 0, ClientAnalytics.LogRequest.LogSource.WING_OPENSKY_ANDROID_PRIMES_VALUE, 0, ClientAnalytics.LogRequest.LogSource.BASELINE_IOS_PRIMES_VALUE);
        this.defaultClutDefinition = new ClutDefinition(0, generateDefault2BitClutEntries(), generateDefault4BitClutEntries(), generateDefault8BitClutEntries());
        this.subtitleService = new SubtitleService(subtitlePageId, ancillaryPageId);
    }

    private static void parseSubtitlingSegment(ParsableBitArray data, SubtitleService service) {
        int segmentType = data.readBits(8);
        int pageId = data.readBits(16);
        int dataFieldLength = data.readBits(16);
        int dataFieldLimit = data.getBytePosition() + dataFieldLength;
        if (dataFieldLength * 8 > data.bitsLeft()) {
            Log.m30w(TAG, "Data field length exceeds limit");
            data.skipBits(data.bitsLeft());
            return;
        }
        switch (segmentType) {
            case 16:
                if (pageId == service.subtitlePageId) {
                    PageComposition current = service.pageComposition;
                    PageComposition pageComposition = parsePageComposition(data, dataFieldLength);
                    if (pageComposition.state == 0) {
                        if (!(current == null || current.version == pageComposition.version)) {
                            service.pageComposition = pageComposition;
                            break;
                        }
                    } else {
                        service.pageComposition = pageComposition;
                        service.regions.clear();
                        service.cluts.clear();
                        service.objects.clear();
                        break;
                    }
                }
                break;
            case 17:
                PageComposition pageComposition2 = service.pageComposition;
                if (pageId == service.subtitlePageId && pageComposition2 != null) {
                    RegionComposition regionComposition = parseRegionComposition(data, dataFieldLength);
                    if (pageComposition2.state == 0) {
                        regionComposition.mergeFrom(service.regions.get(regionComposition.f100id));
                    }
                    service.regions.put(regionComposition.f100id, regionComposition);
                    break;
                }
            case 18:
                if (pageId != service.subtitlePageId) {
                    if (pageId == service.ancillaryPageId) {
                        ClutDefinition clutDefinition = parseClutDefinition(data, dataFieldLength);
                        service.ancillaryCluts.put(clutDefinition.f98id, clutDefinition);
                        break;
                    }
                } else {
                    ClutDefinition clutDefinition2 = parseClutDefinition(data, dataFieldLength);
                    service.cluts.put(clutDefinition2.f98id, clutDefinition2);
                    break;
                }
                break;
            case 19:
                if (pageId != service.subtitlePageId) {
                    if (pageId == service.ancillaryPageId) {
                        ObjectData objectData = parseObjectData(data);
                        service.ancillaryObjects.put(objectData.f99id, objectData);
                        break;
                    }
                } else {
                    ObjectData objectData2 = parseObjectData(data);
                    service.objects.put(objectData2.f99id, objectData2);
                    break;
                }
                break;
            case 20:
                if (pageId == service.subtitlePageId) {
                    service.displayDefinition = parseDisplayDefinition(data);
                    break;
                }
                break;
        }
        data.skipBytes(dataFieldLimit - data.getBytePosition());
    }

    private static DisplayDefinition parseDisplayDefinition(ParsableBitArray data) {
        int verticalPositionMaximum;
        int verticalPositionMinimum;
        int horizontalPositionMaximum;
        int horizontalPositionMinimum;
        data.skipBits(4);
        boolean displayWindowFlag = data.readBit();
        data.skipBits(3);
        int width = data.readBits(16);
        int height = data.readBits(16);
        if (displayWindowFlag) {
            int horizontalPositionMinimum2 = data.readBits(16);
            int horizontalPositionMaximum2 = data.readBits(16);
            int verticalPositionMinimum2 = data.readBits(16);
            verticalPositionMaximum = data.readBits(16);
            horizontalPositionMinimum = horizontalPositionMinimum2;
            horizontalPositionMaximum = horizontalPositionMaximum2;
            verticalPositionMinimum = verticalPositionMinimum2;
        } else {
            horizontalPositionMinimum = 0;
            horizontalPositionMaximum = width;
            verticalPositionMinimum = 0;
            verticalPositionMaximum = height;
        }
        return new DisplayDefinition(width, height, horizontalPositionMinimum, horizontalPositionMaximum, verticalPositionMinimum, verticalPositionMaximum);
    }

    private static PageComposition parsePageComposition(ParsableBitArray data, int length) {
        int timeoutSecs = data.readBits(8);
        int version = data.readBits(4);
        int state = data.readBits(2);
        data.skipBits(2);
        int remainingLength = length - 2;
        SparseArray<PageRegion> regions = new SparseArray<>();
        while (remainingLength > 0) {
            int regionId = data.readBits(8);
            data.skipBits(8);
            remainingLength -= 6;
            regions.put(regionId, new PageRegion(data.readBits(16), data.readBits(16)));
        }
        return new PageComposition(timeoutSecs, version, state, regions);
    }

    /* JADX INFO: Multiple debug info for r24v4 int: [D('foregroundPixelCode' int), D('remainingLength' int)] */
    private static RegionComposition parseRegionComposition(ParsableBitArray data, int length) {
        ParsableBitArray parsableBitArray = data;
        int i = 8;
        int id = parsableBitArray.readBits(8);
        parsableBitArray.skipBits(4);
        boolean fillFlag = data.readBit();
        parsableBitArray.skipBits(3);
        int width = parsableBitArray.readBits(16);
        int height = parsableBitArray.readBits(16);
        int levelOfCompatibility = parsableBitArray.readBits(3);
        int depth = parsableBitArray.readBits(3);
        parsableBitArray.skipBits(2);
        int clutId = parsableBitArray.readBits(8);
        int pixelCode8Bit = parsableBitArray.readBits(8);
        int pixelCode4Bit = parsableBitArray.readBits(4);
        int pixelCode2Bit = parsableBitArray.readBits(2);
        parsableBitArray.skipBits(2);
        SparseArray<RegionObject> regionObjects = new SparseArray<>();
        int foregroundPixelCode = length - 10;
        while (foregroundPixelCode > 0) {
            int objectId = parsableBitArray.readBits(16);
            int objectType = parsableBitArray.readBits(2);
            int objectProvider = parsableBitArray.readBits(2);
            int objectHorizontalPosition = parsableBitArray.readBits(12);
            parsableBitArray.skipBits(4);
            int objectVerticalPosition = parsableBitArray.readBits(12);
            int remainingLength = foregroundPixelCode - 6;
            int foregroundPixelCode2 = 0;
            int backgroundPixelCode = 0;
            if (objectType == 1 || objectType == 2) {
                foregroundPixelCode2 = parsableBitArray.readBits(i);
                backgroundPixelCode = parsableBitArray.readBits(i);
                remainingLength -= 2;
            }
            RegionObject regionObject = r6;
            RegionObject regionObject2 = new RegionObject(objectType, objectProvider, objectHorizontalPosition, objectVerticalPosition, foregroundPixelCode2, backgroundPixelCode);
            regionObjects.put(objectId, regionObject);
            foregroundPixelCode = remainingLength;
            i = 8;
        }
        return new RegionComposition(id, fillFlag, width, height, levelOfCompatibility, depth, clutId, pixelCode8Bit, pixelCode4Bit, pixelCode2Bit, regionObjects);
    }

    private static ClutDefinition parseClutDefinition(ParsableBitArray data, int length) {
        int[] clutEntries;
        int cr;
        int cb;
        int cb2;
        int t;
        int remainingLength;
        ParsableBitArray parsableBitArray = data;
        int i = 8;
        int clutId = parsableBitArray.readBits(8);
        parsableBitArray.skipBits(8);
        int remainingLength2 = length - 2;
        int[] clutEntries2Bit = generateDefault2BitClutEntries();
        int[] clutEntries4Bit = generateDefault4BitClutEntries();
        int[] clutEntries8Bit = generateDefault8BitClutEntries();
        while (remainingLength2 > 0) {
            int entryId = parsableBitArray.readBits(i);
            int entryFlags = parsableBitArray.readBits(i);
            int remainingLength3 = remainingLength2 - 2;
            if ((entryFlags & 128) != 0) {
                clutEntries = clutEntries2Bit;
            } else if ((entryFlags & 64) != 0) {
                clutEntries = clutEntries4Bit;
            } else {
                clutEntries = clutEntries8Bit;
            }
            if ((entryFlags & 1) != 0) {
                t = parsableBitArray.readBits(i);
                int cr2 = parsableBitArray.readBits(i);
                cb2 = parsableBitArray.readBits(i);
                cb = parsableBitArray.readBits(i);
                remainingLength = remainingLength3 - 4;
                cr = cr2;
            } else {
                cr = parsableBitArray.readBits(4) << 4;
                int t2 = parsableBitArray.readBits(2) << 6;
                remainingLength = remainingLength3 - 2;
                cb2 = parsableBitArray.readBits(4) << 4;
                cb = t2;
                t = parsableBitArray.readBits(6) << 2;
            }
            if (t == 0) {
                cr = 0;
                cb2 = 0;
                cb = 255;
            }
            int clutId2 = clutId;
            double d = (double) t;
            int remainingLength4 = remainingLength;
            int[] clutEntries2Bit2 = clutEntries2Bit;
            double d2 = (double) (cr - 128);
            Double.isNaN(d2);
            Double.isNaN(d);
            double d3 = (double) t;
            double d4 = (double) (cb2 - 128);
            Double.isNaN(d4);
            Double.isNaN(d3);
            double d5 = d3 - (d4 * 0.34414d);
            double d6 = (double) (cr - 128);
            Double.isNaN(d6);
            double d7 = (double) t;
            double d8 = (double) (cb2 - 128);
            Double.isNaN(d8);
            Double.isNaN(d7);
            clutEntries[entryId] = getColor((byte) (255 - (cb & 255)), Util.constrainValue((int) (d + (d2 * 1.402d)), 0, 255), Util.constrainValue((int) (d5 - (d6 * 0.71414d)), 0, 255), Util.constrainValue((int) (d7 + (d8 * 1.772d)), 0, 255));
            clutEntries4Bit = clutEntries4Bit;
            clutId = clutId2;
            clutEntries2Bit = clutEntries2Bit2;
            remainingLength2 = remainingLength4;
            i = 8;
        }
        return new ClutDefinition(clutId, clutEntries2Bit, clutEntries4Bit, clutEntries8Bit);
    }

    private static ObjectData parseObjectData(ParsableBitArray data) {
        int objectId = data.readBits(16);
        data.skipBits(4);
        int objectCodingMethod = data.readBits(2);
        boolean nonModifyingColorFlag = data.readBit();
        data.skipBits(1);
        byte[] topFieldData = null;
        byte[] bottomFieldData = null;
        if (objectCodingMethod == 1) {
            data.skipBits(data.readBits(8) * 16);
        } else if (objectCodingMethod == 0) {
            int topFieldDataLength = data.readBits(16);
            int bottomFieldDataLength = data.readBits(16);
            if (topFieldDataLength > 0) {
                topFieldData = new byte[topFieldDataLength];
                data.readBytes(topFieldData, 0, topFieldDataLength);
            }
            if (bottomFieldDataLength > 0) {
                bottomFieldData = new byte[bottomFieldDataLength];
                data.readBytes(bottomFieldData, 0, bottomFieldDataLength);
            } else {
                bottomFieldData = topFieldData;
            }
        }
        return new ObjectData(objectId, nonModifyingColorFlag, topFieldData, bottomFieldData);
    }

    private static int[] generateDefault2BitClutEntries() {
        return new int[]{0, -1, -16777216, -8421505};
    }

    private static int[] generateDefault4BitClutEntries() {
        int[] entries = new int[16];
        entries[0] = 0;
        for (int i = 1; i < entries.length; i++) {
            if (i < 8) {
                entries[i] = getColor(255, (i & 1) != 0 ? 255 : 0, (i & 2) != 0 ? 255 : 0, (i & 4) != 0 ? 255 : 0);
            } else {
                int i2 = i & 1;
                int i3 = ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
                int i4 = i2 != 0 ? ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE : 0;
                int i5 = (i & 2) != 0 ? ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE : 0;
                if ((i & 4) == 0) {
                    i3 = 0;
                }
                entries[i] = getColor(255, i4, i5, i3);
            }
        }
        return entries;
    }

    private static int[] generateDefault8BitClutEntries() {
        int[] entries = new int[256];
        entries[0] = 0;
        for (int i = 0; i < entries.length; i++) {
            int i2 = 255;
            if (i < 8) {
                int i3 = (i & 1) != 0 ? 255 : 0;
                int i4 = (i & 2) != 0 ? 255 : 0;
                if ((i & 4) == 0) {
                    i2 = 0;
                }
                entries[i] = getColor(63, i3, i4, i2);
            } else {
                int i5 = i & ClientAnalytics.LogRequest.LogSource.SAMPLE_SHM_VALUE;
                int i6 = ClientAnalytics.LogRequest.LogSource.FITNESS_GMS_CORE_VALUE;
                int i7 = 85;
                if (i5 == 0) {
                    int i8 = ((i & 1) != 0 ? 85 : 0) + ((i & 16) != 0 ? ClientAnalytics.LogRequest.LogSource.FITNESS_GMS_CORE_VALUE : 0);
                    int i9 = ((i & 2) != 0 ? 85 : 0) + ((i & 32) != 0 ? ClientAnalytics.LogRequest.LogSource.FITNESS_GMS_CORE_VALUE : 0);
                    if ((i & 4) == 0) {
                        i7 = 0;
                    }
                    if ((i & 64) == 0) {
                        i6 = 0;
                    }
                    entries[i] = getColor(255, i8, i9, i7 + i6);
                } else if (i5 != 8) {
                    int i10 = 43;
                    if (i5 == 128) {
                        int i11 = ((i & 1) != 0 ? 43 : 0) + ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE + ((i & 16) != 0 ? 85 : 0);
                        int i12 = ((i & 2) != 0 ? 43 : 0) + ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE + ((i & 32) != 0 ? 85 : 0);
                        if ((i & 4) == 0) {
                            i10 = 0;
                        }
                        int i13 = i10 + ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE;
                        if ((i & 64) == 0) {
                            i7 = 0;
                        }
                        entries[i] = getColor(255, i11, i12, i13 + i7);
                    } else if (i5 == 136) {
                        int i14 = ((i & 1) != 0 ? 43 : 0) + ((i & 16) != 0 ? 85 : 0);
                        int i15 = ((i & 2) != 0 ? 43 : 0) + ((i & 32) != 0 ? 85 : 0);
                        if ((i & 4) == 0) {
                            i10 = 0;
                        }
                        if ((i & 64) == 0) {
                            i7 = 0;
                        }
                        entries[i] = getColor(255, i14, i15, i10 + i7);
                    }
                } else {
                    int i16 = ((i & 1) != 0 ? 85 : 0) + ((i & 16) != 0 ? ClientAnalytics.LogRequest.LogSource.FITNESS_GMS_CORE_VALUE : 0);
                    int i17 = ((i & 2) != 0 ? 85 : 0) + ((i & 32) != 0 ? ClientAnalytics.LogRequest.LogSource.FITNESS_GMS_CORE_VALUE : 0);
                    if ((i & 4) == 0) {
                        i7 = 0;
                    }
                    if ((i & 64) == 0) {
                        i6 = 0;
                    }
                    entries[i] = getColor(ClientAnalytics.LogRequest.LogSource.TAILORMADE_VALUE, i16, i17, i7 + i6);
                }
            }
        }
        return entries;
    }

    private static int getColor(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    private static void paintPixelDataSubBlocks(ObjectData objectData, ClutDefinition clutDefinition, int regionDepth, int horizontalAddress, int verticalAddress, Paint paint, Canvas canvas2) {
        int[] clutEntries;
        if (regionDepth == 3) {
            clutEntries = clutDefinition.clutEntries8Bit;
        } else if (regionDepth == 2) {
            clutEntries = clutDefinition.clutEntries4Bit;
        } else {
            clutEntries = clutDefinition.clutEntries2Bit;
        }
        int[] iArr = clutEntries;
        int i = regionDepth;
        int i2 = horizontalAddress;
        Paint paint2 = paint;
        Canvas canvas3 = canvas2;
        paintPixelDataSubBlock(objectData.topFieldData, iArr, i, i2, verticalAddress, paint2, canvas3);
        paintPixelDataSubBlock(objectData.bottomFieldData, iArr, i, i2, verticalAddress + 1, paint2, canvas3);
    }

    /* JADX INFO: Multiple debug info for r3v17 byte[]: [D('clutMapTable2To8' byte[]), D('clutMapTable2To4' byte[])] */
    private static void paintPixelDataSubBlock(byte[] pixelData, int[] clutEntries, int regionDepth, int horizontalAddress, int verticalAddress, Paint paint, Canvas canvas2) {
        byte[] clutMapTable2ToX;
        byte[] clutMapTable4ToX;
        int i = regionDepth;
        ParsableBitArray data = new ParsableBitArray(pixelData);
        int column = horizontalAddress;
        int line = verticalAddress;
        byte[] clutMapTable2To4 = null;
        byte[] clutMapTable2To8 = null;
        while (data.bitsLeft() != 0) {
            int dataType = data.readBits(8);
            if (dataType != 240) {
                switch (dataType) {
                    case 16:
                        if (i == 3) {
                            clutMapTable2ToX = clutMapTable2To8 == null ? defaultMap2To8 : clutMapTable2To8;
                        } else if (i == 2) {
                            clutMapTable2ToX = clutMapTable2To4 == null ? defaultMap2To4 : clutMapTable2To4;
                        } else {
                            clutMapTable2ToX = null;
                        }
                        column = paint2BitPixelCodeString(data, clutEntries, clutMapTable2ToX, column, line, paint, canvas2);
                        data.byteAlign();
                        continue;
                    case 17:
                        if (i == 3) {
                            clutMapTable4ToX = 0 == 0 ? defaultMap4To8 : null;
                        } else {
                            clutMapTable4ToX = null;
                        }
                        column = paint4BitPixelCodeString(data, clutEntries, clutMapTable4ToX, column, line, paint, canvas2);
                        data.byteAlign();
                        continue;
                    case 18:
                        column = paint8BitPixelCodeString(data, clutEntries, null, column, line, paint, canvas2);
                        continue;
                    default:
                        switch (dataType) {
                            case 32:
                                clutMapTable2To4 = buildClutMapTable(4, 4, data);
                                continue;
                            case 33:
                                clutMapTable2To8 = buildClutMapTable(4, 8, data);
                                continue;
                            case 34:
                                clutMapTable2To8 = buildClutMapTable(16, 8, data);
                                continue;
                            default:
                                continue;
                        }
                }
            } else {
                column = horizontalAddress;
                line += 2;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    /* JADX WARN: Type inference failed for: r4v2, types: [int] */
    /* JADX WARN: Type inference failed for: r4v3, types: [int] */
    /* JADX WARN: Type inference failed for: r4v4, types: [int] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int paint2BitPixelCodeString(com.google.android.exoplayer2.util.ParsableBitArray r14, int[] r15, byte[] r16, int r17, int r18, android.graphics.Paint r19, android.graphics.Canvas r20) {
        /*
            r0 = r14
            r1 = r18
            r8 = r19
            r2 = 0
            r9 = r17
        L_0x0008:
            r3 = 0
            r4 = 0
            r5 = 2
            int r10 = r14.readBits(r5)
            if (r10 == 0) goto L_0x0018
            r3 = 1
            r4 = r10
            r13 = r2
            r11 = r3
            r12 = r4
            goto L_0x0071
        L_0x0018:
            boolean r6 = r14.readBit()
            r7 = 3
            if (r6 == 0) goto L_0x002d
            int r6 = r14.readBits(r7)
            int r3 = r6 + 3
            int r4 = r14.readBits(r5)
            r13 = r2
            r11 = r3
            r12 = r4
            goto L_0x0071
        L_0x002d:
            boolean r6 = r14.readBit()
            if (r6 == 0) goto L_0x0038
            r3 = 1
            r13 = r2
            r11 = r3
            r12 = r4
            goto L_0x0071
        L_0x0038:
            int r6 = r14.readBits(r5)
            if (r6 == 0) goto L_0x006d
            r11 = 1
            if (r6 == r11) goto L_0x0068
            if (r6 == r5) goto L_0x0059
            if (r6 == r7) goto L_0x0049
            r13 = r2
            r11 = r3
            r12 = r4
            goto L_0x0071
        L_0x0049:
            r6 = 8
            int r6 = r14.readBits(r6)
            int r3 = r6 + 29
            int r4 = r14.readBits(r5)
            r13 = r2
            r11 = r3
            r12 = r4
            goto L_0x0071
        L_0x0059:
            r6 = 4
            int r6 = r14.readBits(r6)
            int r3 = r6 + 12
            int r4 = r14.readBits(r5)
            r13 = r2
            r11 = r3
            r12 = r4
            goto L_0x0071
        L_0x0068:
            r3 = 2
            r13 = r2
            r11 = r3
            r12 = r4
            goto L_0x0071
        L_0x006d:
            r2 = 1
            r13 = r2
            r11 = r3
            r12 = r4
        L_0x0071:
            if (r11 == 0) goto L_0x008f
            if (r8 == 0) goto L_0x008f
            if (r16 == 0) goto L_0x007a
            byte r2 = r16[r12]
            goto L_0x007b
        L_0x007a:
            r2 = r12
        L_0x007b:
            r2 = r15[r2]
            r8.setColor(r2)
            float r3 = (float) r9
            float r4 = (float) r1
            int r2 = r9 + r11
            float r5 = (float) r2
            int r2 = r1 + 1
            float r6 = (float) r2
            r2 = r20
            r7 = r19
            r2.drawRect(r3, r4, r5, r6, r7)
        L_0x008f:
            int r9 = r9 + r11
            if (r13 == 0) goto L_0x0093
            return r9
        L_0x0093:
            r2 = r13
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.dvb.DvbParser.paint2BitPixelCodeString(com.google.android.exoplayer2.util.ParsableBitArray, int[], byte[], int, int, android.graphics.Paint, android.graphics.Canvas):int");
    }

    /* JADX WARN: Type inference failed for: r6v0, types: [int] */
    /* JADX WARN: Type inference failed for: r4v2, types: [int] */
    /* JADX WARN: Type inference failed for: r4v3, types: [int] */
    /* JADX WARN: Type inference failed for: r4v4, types: [int] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int paint4BitPixelCodeString(com.google.android.exoplayer2.util.ParsableBitArray r14, int[] r15, byte[] r16, int r17, int r18, android.graphics.Paint r19, android.graphics.Canvas r20) {
        /*
            r0 = r14
            r1 = r18
            r8 = r19
            r2 = 0
            r9 = r17
        L_0x0008:
            r3 = 0
            r4 = 0
            r5 = 4
            int r6 = r14.readBits(r5)
            if (r6 == 0) goto L_0x0019
            r3 = 1
            r4 = r6
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x0089
        L_0x0019:
            boolean r7 = r14.readBit()
            r10 = 3
            if (r7 != 0) goto L_0x0036
            int r6 = r14.readBits(r10)
            if (r6 == 0) goto L_0x002f
            int r3 = r6 + 2
            r4 = 0
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x0089
        L_0x002f:
            r2 = 1
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x0089
        L_0x0036:
            boolean r7 = r14.readBit()
            r11 = 2
            if (r7 != 0) goto L_0x004c
            int r7 = r14.readBits(r11)
            int r3 = r7 + 4
            int r4 = r14.readBits(r5)
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x0089
        L_0x004c:
            int r7 = r14.readBits(r11)
            if (r7 == 0) goto L_0x0084
            r12 = 1
            if (r7 == r12) goto L_0x007e
            if (r7 == r11) goto L_0x006f
            if (r7 == r10) goto L_0x005e
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x0089
        L_0x005e:
            r7 = 8
            int r7 = r14.readBits(r7)
            int r3 = r7 + 25
            int r4 = r14.readBits(r5)
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x0089
        L_0x006f:
            int r7 = r14.readBits(r5)
            int r3 = r7 + 9
            int r4 = r14.readBits(r5)
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x0089
        L_0x007e:
            r3 = 2
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x0089
        L_0x0084:
            r3 = 1
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
        L_0x0089:
            if (r10 == 0) goto L_0x00a7
            if (r8 == 0) goto L_0x00a7
            if (r16 == 0) goto L_0x0092
            byte r2 = r16[r11]
            goto L_0x0093
        L_0x0092:
            r2 = r11
        L_0x0093:
            r2 = r15[r2]
            r8.setColor(r2)
            float r3 = (float) r9
            float r4 = (float) r1
            int r2 = r9 + r10
            float r5 = (float) r2
            int r2 = r1 + 1
            float r6 = (float) r2
            r2 = r20
            r7 = r19
            r2.drawRect(r3, r4, r5, r6, r7)
        L_0x00a7:
            int r9 = r9 + r10
            if (r12 == 0) goto L_0x00ab
            return r9
        L_0x00ab:
            r2 = r12
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.dvb.DvbParser.paint4BitPixelCodeString(com.google.android.exoplayer2.util.ParsableBitArray, int[], byte[], int, int, android.graphics.Paint, android.graphics.Canvas):int");
    }

    /* JADX WARN: Type inference failed for: r6v0, types: [int] */
    /* JADX WARN: Type inference failed for: r4v2, types: [int] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int paint8BitPixelCodeString(com.google.android.exoplayer2.util.ParsableBitArray r14, int[] r15, byte[] r16, int r17, int r18, android.graphics.Paint r19, android.graphics.Canvas r20) {
        /*
            r0 = r14
            r1 = r18
            r8 = r19
            r2 = 0
            r9 = r17
        L_0x0008:
            r3 = 0
            r4 = 0
            r5 = 8
            int r6 = r14.readBits(r5)
            if (r6 == 0) goto L_0x0019
            r3 = 1
            r4 = r6
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x003f
        L_0x0019:
            boolean r7 = r14.readBit()
            r10 = 7
            if (r7 != 0) goto L_0x0033
            int r6 = r14.readBits(r10)
            if (r6 == 0) goto L_0x002d
            r3 = r6
            r4 = 0
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x003f
        L_0x002d:
            r2 = 1
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
            goto L_0x003f
        L_0x0033:
            int r3 = r14.readBits(r10)
            int r4 = r14.readBits(r5)
            r12 = r2
            r10 = r3
            r11 = r4
            r13 = r6
        L_0x003f:
            if (r10 == 0) goto L_0x005d
            if (r8 == 0) goto L_0x005d
            if (r16 == 0) goto L_0x0048
            byte r2 = r16[r11]
            goto L_0x0049
        L_0x0048:
            r2 = r11
        L_0x0049:
            r2 = r15[r2]
            r8.setColor(r2)
            float r3 = (float) r9
            float r4 = (float) r1
            int r2 = r9 + r10
            float r5 = (float) r2
            int r2 = r1 + 1
            float r6 = (float) r2
            r2 = r20
            r7 = r19
            r2.drawRect(r3, r4, r5, r6, r7)
        L_0x005d:
            int r9 = r9 + r10
            if (r12 == 0) goto L_0x0061
            return r9
        L_0x0061:
            r2 = r12
            goto L_0x0008
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.dvb.DvbParser.paint8BitPixelCodeString(com.google.android.exoplayer2.util.ParsableBitArray, int[], byte[], int, int, android.graphics.Paint, android.graphics.Canvas):int");
    }

    private static byte[] buildClutMapTable(int length, int bitsPerEntry, ParsableBitArray data) {
        byte[] clutMapTable = new byte[length];
        for (int i = 0; i < length; i++) {
            clutMapTable[i] = (byte) data.readBits(bitsPerEntry);
        }
        return clutMapTable;
    }

    public void reset() {
        this.subtitleService.reset();
    }

    public List<Cue> decode(byte[] data, int limit) {
        int color;
        PageRegion pageRegion;
        ParsableBitArray dataBitArray = new ParsableBitArray(data, limit);
        while (dataBitArray.bitsLeft() >= 48 && dataBitArray.readBits(8) == 15) {
            parseSubtitlingSegment(dataBitArray, this.subtitleService);
        }
        if (this.subtitleService.pageComposition == null) {
            return Collections.emptyList();
        }
        DisplayDefinition displayDefinition = this.subtitleService.displayDefinition != null ? this.subtitleService.displayDefinition : this.defaultDisplayDefinition;
        if (!(this.bitmap != null && displayDefinition.width + 1 == this.bitmap.getWidth() && displayDefinition.height + 1 == this.bitmap.getHeight())) {
            this.bitmap = Bitmap.createBitmap(displayDefinition.width + 1, displayDefinition.height + 1, Bitmap.Config.ARGB_8888);
            this.canvas.setBitmap(this.bitmap);
        }
        List<Cue> cues = new ArrayList<>();
        SparseArray<PageRegion> pageRegions = this.subtitleService.pageComposition.regions;
        int i = 0;
        while (i < pageRegions.size()) {
            PageRegion pageRegion2 = pageRegions.valueAt(i);
            RegionComposition regionComposition = this.subtitleService.regions.get(pageRegions.keyAt(i));
            int baseHorizontalAddress = pageRegion2.horizontalAddress + displayDefinition.horizontalPositionMinimum;
            int baseVerticalAddress = pageRegion2.verticalAddress + displayDefinition.verticalPositionMinimum;
            ParsableBitArray dataBitArray2 = dataBitArray;
            SparseArray<PageRegion> pageRegions2 = pageRegions;
            this.canvas.clipRect((float) baseHorizontalAddress, (float) baseVerticalAddress, (float) Math.min(regionComposition.width + baseHorizontalAddress, displayDefinition.horizontalPositionMaximum), (float) Math.min(regionComposition.height + baseVerticalAddress, displayDefinition.verticalPositionMaximum), Region.Op.REPLACE);
            ClutDefinition clutDefinition = this.subtitleService.cluts.get(regionComposition.clutId);
            if (clutDefinition == null && (clutDefinition = this.subtitleService.ancillaryCluts.get(regionComposition.clutId)) == null) {
                clutDefinition = this.defaultClutDefinition;
            }
            SparseArray<RegionObject> regionObjects = regionComposition.regionObjects;
            int j = 0;
            while (j < regionObjects.size()) {
                int objectId = regionObjects.keyAt(j);
                RegionObject regionObject = regionObjects.valueAt(j);
                SparseArray<RegionObject> regionObjects2 = regionObjects;
                ObjectData objectData = this.subtitleService.objects.get(objectId);
                if (objectData == null) {
                    objectData = this.subtitleService.ancillaryObjects.get(objectId);
                }
                if (objectData != null) {
                    pageRegion = pageRegion2;
                    paintPixelDataSubBlocks(objectData, clutDefinition, regionComposition.depth, baseHorizontalAddress + regionObject.horizontalPosition, baseVerticalAddress + regionObject.verticalPosition, objectData.nonModifyingColorFlag != 0 ? null : this.defaultPaint, this.canvas);
                } else {
                    pageRegion = pageRegion2;
                }
                j++;
                regionObjects = regionObjects2;
                pageRegion2 = pageRegion;
            }
            if (regionComposition.fillFlag) {
                if (regionComposition.depth == 3) {
                    color = clutDefinition.clutEntries8Bit[regionComposition.pixelCode8Bit];
                } else if (regionComposition.depth == 2) {
                    color = clutDefinition.clutEntries4Bit[regionComposition.pixelCode4Bit];
                } else {
                    color = clutDefinition.clutEntries2Bit[regionComposition.pixelCode2Bit];
                }
                this.fillRegionPaint.setColor(color);
                this.canvas.drawRect((float) baseHorizontalAddress, (float) baseVerticalAddress, (float) (regionComposition.width + baseHorizontalAddress), (float) (regionComposition.height + baseVerticalAddress), this.fillRegionPaint);
            }
            cues.add(new Cue(Bitmap.createBitmap(this.bitmap, baseHorizontalAddress, baseVerticalAddress, regionComposition.width, regionComposition.height), ((float) baseHorizontalAddress) / ((float) displayDefinition.width), 0, ((float) baseVerticalAddress) / ((float) displayDefinition.height), 0, ((float) regionComposition.width) / ((float) displayDefinition.width), ((float) regionComposition.height) / ((float) displayDefinition.height)));
            this.canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            i++;
            dataBitArray = dataBitArray2;
            pageRegions = pageRegions2;
        }
        return cues;
    }

    private static final class SubtitleService {
        public final SparseArray<ClutDefinition> ancillaryCluts = new SparseArray<>();
        public final SparseArray<ObjectData> ancillaryObjects = new SparseArray<>();
        public final int ancillaryPageId;
        public final SparseArray<ClutDefinition> cluts = new SparseArray<>();
        public final SparseArray<ObjectData> objects = new SparseArray<>();
        public final SparseArray<RegionComposition> regions = new SparseArray<>();
        public final int subtitlePageId;
        public DisplayDefinition displayDefinition;
        public PageComposition pageComposition;

        public SubtitleService(int subtitlePageId2, int ancillaryPageId2) {
            this.subtitlePageId = subtitlePageId2;
            this.ancillaryPageId = ancillaryPageId2;
        }

        public void reset() {
            this.regions.clear();
            this.cluts.clear();
            this.objects.clear();
            this.ancillaryCluts.clear();
            this.ancillaryObjects.clear();
            this.displayDefinition = null;
            this.pageComposition = null;
        }
    }

    private static final class DisplayDefinition {
        public final int height;
        public final int horizontalPositionMaximum;
        public final int horizontalPositionMinimum;
        public final int verticalPositionMaximum;
        public final int verticalPositionMinimum;
        public final int width;

        public DisplayDefinition(int width2, int height2, int horizontalPositionMinimum2, int horizontalPositionMaximum2, int verticalPositionMinimum2, int verticalPositionMaximum2) {
            this.width = width2;
            this.height = height2;
            this.horizontalPositionMinimum = horizontalPositionMinimum2;
            this.horizontalPositionMaximum = horizontalPositionMaximum2;
            this.verticalPositionMinimum = verticalPositionMinimum2;
            this.verticalPositionMaximum = verticalPositionMaximum2;
        }
    }

    private static final class PageComposition {
        public final SparseArray<PageRegion> regions;
        public final int state;
        public final int timeOutSecs;
        public final int version;

        public PageComposition(int timeoutSecs, int version2, int state2, SparseArray<PageRegion> regions2) {
            this.timeOutSecs = timeoutSecs;
            this.version = version2;
            this.state = state2;
            this.regions = regions2;
        }
    }

    private static final class PageRegion {
        public final int horizontalAddress;
        public final int verticalAddress;

        public PageRegion(int horizontalAddress2, int verticalAddress2) {
            this.horizontalAddress = horizontalAddress2;
            this.verticalAddress = verticalAddress2;
        }
    }

    private static final class RegionComposition {
        public final int clutId;
        public final int depth;
        public final boolean fillFlag;
        public final int height;

        /* renamed from: id */
        public final int f100id;
        public final int levelOfCompatibility;
        public final int pixelCode2Bit;
        public final int pixelCode4Bit;
        public final int pixelCode8Bit;
        public final SparseArray<RegionObject> regionObjects;
        public final int width;

        public RegionComposition(int id, boolean fillFlag2, int width2, int height2, int levelOfCompatibility2, int depth2, int clutId2, int pixelCode8Bit2, int pixelCode4Bit2, int pixelCode2Bit2, SparseArray<RegionObject> regionObjects2) {
            this.f100id = id;
            this.fillFlag = fillFlag2;
            this.width = width2;
            this.height = height2;
            this.levelOfCompatibility = levelOfCompatibility2;
            this.depth = depth2;
            this.clutId = clutId2;
            this.pixelCode8Bit = pixelCode8Bit2;
            this.pixelCode4Bit = pixelCode4Bit2;
            this.pixelCode2Bit = pixelCode2Bit2;
            this.regionObjects = regionObjects2;
        }

        public void mergeFrom(RegionComposition otherRegionComposition) {
            if (otherRegionComposition != null) {
                SparseArray<RegionObject> otherRegionObjects = otherRegionComposition.regionObjects;
                for (int i = 0; i < otherRegionObjects.size(); i++) {
                    this.regionObjects.put(otherRegionObjects.keyAt(i), otherRegionObjects.valueAt(i));
                }
            }
        }
    }

    private static final class RegionObject {
        public final int backgroundPixelCode;
        public final int foregroundPixelCode;
        public final int horizontalPosition;
        public final int provider;
        public final int type;
        public final int verticalPosition;

        public RegionObject(int type2, int provider2, int horizontalPosition2, int verticalPosition2, int foregroundPixelCode2, int backgroundPixelCode2) {
            this.type = type2;
            this.provider = provider2;
            this.horizontalPosition = horizontalPosition2;
            this.verticalPosition = verticalPosition2;
            this.foregroundPixelCode = foregroundPixelCode2;
            this.backgroundPixelCode = backgroundPixelCode2;
        }
    }

    private static final class ClutDefinition {
        public final int[] clutEntries2Bit;
        public final int[] clutEntries4Bit;
        public final int[] clutEntries8Bit;

        /* renamed from: id */
        public final int f98id;

        public ClutDefinition(int id, int[] clutEntries2Bit2, int[] clutEntries4Bit2, int[] clutEntries8bit) {
            this.f98id = id;
            this.clutEntries2Bit = clutEntries2Bit2;
            this.clutEntries4Bit = clutEntries4Bit2;
            this.clutEntries8Bit = clutEntries8bit;
        }
    }

    private static final class ObjectData {
        public final byte[] bottomFieldData;

        /* renamed from: id */
        public final int f99id;
        public final boolean nonModifyingColorFlag;
        public final byte[] topFieldData;

        public ObjectData(int id, boolean nonModifyingColorFlag2, byte[] topFieldData2, byte[] bottomFieldData2) {
            this.f99id = id;
            this.nonModifyingColorFlag = nonModifyingColorFlag2;
            this.topFieldData = topFieldData2;
            this.bottomFieldData = bottomFieldData2;
        }
    }
}
