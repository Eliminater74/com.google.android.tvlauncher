package com.bumptech.glide.load.resource.bitmap;

import android.support.annotation.NonNull;
import android.support.p001v4.internal.view.SupportMenu;
import android.support.p001v4.view.MotionEventCompat;
import android.util.Log;

import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.util.Preconditions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public final class DefaultImageHeaderParser implements ImageHeaderParser {
    static final int EXIF_MAGIC_NUMBER = 65496;
    static final int EXIF_SEGMENT_TYPE = 225;
    static final int SEGMENT_START_ID = 255;
    private static final int[] BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8};
    private static final int GIF_HEADER = 4671814;
    private static final int INTEL_TIFF_MAGIC_NUMBER = 18761;
    private static final String JPEG_EXIF_SEGMENT_PREAMBLE = "Exif\u0000\u0000";
    static final byte[] JPEG_EXIF_SEGMENT_PREAMBLE_BYTES = JPEG_EXIF_SEGMENT_PREAMBLE.getBytes(Charset.forName("UTF-8"));
    private static final int MARKER_EOI = 217;
    private static final int MOTOROLA_TIFF_MAGIC_NUMBER = 19789;
    private static final int ORIENTATION_TAG_TYPE = 274;
    private static final int PNG_HEADER = -1991225785;
    private static final int RIFF_HEADER = 1380533830;
    private static final int SEGMENT_SOS = 218;
    private static final String TAG = "DfltImageHeaderParser";
    private static final int VP8_HEADER = 1448097792;
    private static final int VP8_HEADER_MASK = -256;
    private static final int VP8_HEADER_TYPE_EXTENDED = 88;
    private static final int VP8_HEADER_TYPE_LOSSLESS = 76;
    private static final int VP8_HEADER_TYPE_MASK = 255;
    private static final int WEBP_EXTENDED_ALPHA_FLAG = 16;
    private static final int WEBP_HEADER = 1464156752;
    private static final int WEBP_LOSSLESS_ALPHA_FLAG = 8;

    private static int parseExifSegment(RandomAccessReader segmentData) {
        ByteOrder byteOrder;
        RandomAccessReader randomAccessReader = segmentData;
        int headerOffsetSize = JPEG_EXIF_SEGMENT_PREAMBLE.length();
        short byteOrderIdentifier = randomAccessReader.getInt16(headerOffsetSize);
        int i = 3;
        if (byteOrderIdentifier == INTEL_TIFF_MAGIC_NUMBER) {
            byteOrder = ByteOrder.LITTLE_ENDIAN;
        } else if (byteOrderIdentifier != MOTOROLA_TIFF_MAGIC_NUMBER) {
            if (Log.isLoggable(TAG, 3)) {
                StringBuilder sb = new StringBuilder(27);
                sb.append("Unknown endianness = ");
                sb.append((int) byteOrderIdentifier);
                Log.d(TAG, sb.toString());
            }
            byteOrder = ByteOrder.BIG_ENDIAN;
        } else {
            byteOrder = ByteOrder.BIG_ENDIAN;
        }
        randomAccessReader.order(byteOrder);
        int firstIfdOffset = randomAccessReader.getInt32(headerOffsetSize + 4) + headerOffsetSize;
        int tagCount = randomAccessReader.getInt16(firstIfdOffset);
        int i2 = 0;
        while (i2 < tagCount) {
            int tagOffset = calcTagOffset(firstIfdOffset, i2);
            int tagType = randomAccessReader.getInt16(tagOffset);
            if (tagType == 274) {
                int formatCode = randomAccessReader.getInt16(tagOffset + 2);
                if (formatCode >= 1 && formatCode <= 12) {
                    int componentCount = randomAccessReader.getInt32(tagOffset + 4);
                    if (componentCount >= 0) {
                        if (Log.isLoggable(TAG, i)) {
                            StringBuilder sb2 = new StringBuilder(94);
                            sb2.append("Got tagIndex=");
                            sb2.append(i2);
                            sb2.append(" tagType=");
                            sb2.append(tagType);
                            sb2.append(" formatCode=");
                            sb2.append(formatCode);
                            sb2.append(" componentCount=");
                            sb2.append(componentCount);
                            Log.d(TAG, sb2.toString());
                        }
                        int byteCount = BYTES_PER_FORMAT[formatCode] + componentCount;
                        if (byteCount <= 4) {
                            int tagValueOffset = tagOffset + 8;
                            if (tagValueOffset < 0 || tagValueOffset > segmentData.length()) {
                                if (Log.isLoggable(TAG, 3)) {
                                    StringBuilder sb3 = new StringBuilder(54);
                                    sb3.append("Illegal tagValueOffset=");
                                    sb3.append(tagValueOffset);
                                    sb3.append(" tagType=");
                                    sb3.append(tagType);
                                    Log.d(TAG, sb3.toString());
                                }
                            } else if (byteCount >= 0 && tagValueOffset + byteCount <= segmentData.length()) {
                                return randomAccessReader.getInt16(tagValueOffset);
                            } else {
                                if (Log.isLoggable(TAG, 3)) {
                                    StringBuilder sb4 = new StringBuilder(59);
                                    sb4.append("Illegal number of bytes for TI tag data tagType=");
                                    sb4.append(tagType);
                                    Log.d(TAG, sb4.toString());
                                }
                            }
                        } else if (Log.isLoggable(TAG, i)) {
                            StringBuilder sb5 = new StringBuilder(71);
                            sb5.append("Got byte count > 4, not orientation, continuing, formatCode=");
                            sb5.append(formatCode);
                            Log.d(TAG, sb5.toString());
                        }
                    } else if (Log.isLoggable(TAG, i)) {
                        Log.d(TAG, "Negative tiff component count");
                    }
                } else if (Log.isLoggable(TAG, 3)) {
                    StringBuilder sb6 = new StringBuilder(37);
                    sb6.append("Got invalid format code = ");
                    sb6.append(formatCode);
                    Log.d(TAG, sb6.toString());
                }
            }
            i2++;
            i = 3;
            randomAccessReader = segmentData;
        }
        return -1;
    }

    private static int calcTagOffset(int ifdOffset, int tagIndex) {
        return ifdOffset + 2 + (tagIndex * 12);
    }

    private static boolean handles(int imageMagicNumber) {
        return (imageMagicNumber & EXIF_MAGIC_NUMBER) == EXIF_MAGIC_NUMBER || imageMagicNumber == MOTOROLA_TIFF_MAGIC_NUMBER || imageMagicNumber == INTEL_TIFF_MAGIC_NUMBER;
    }

    @NonNull
    public ImageHeaderParser.ImageType getType(@NonNull InputStream is) throws IOException {
        return getType(new StreamReader((InputStream) Preconditions.checkNotNull(is)));
    }

    @NonNull
    public ImageHeaderParser.ImageType getType(@NonNull ByteBuffer byteBuffer) throws IOException {
        return getType(new ByteBufferReader((ByteBuffer) Preconditions.checkNotNull(byteBuffer)));
    }

    public int getOrientation(@NonNull InputStream is, @NonNull ArrayPool byteArrayPool) throws IOException {
        return getOrientation(new StreamReader((InputStream) Preconditions.checkNotNull(is)), (ArrayPool) Preconditions.checkNotNull(byteArrayPool));
    }

    public int getOrientation(@NonNull ByteBuffer byteBuffer, @NonNull ArrayPool byteArrayPool) throws IOException {
        return getOrientation(new ByteBufferReader((ByteBuffer) Preconditions.checkNotNull(byteBuffer)), (ArrayPool) Preconditions.checkNotNull(byteArrayPool));
    }

    @NonNull
    private ImageHeaderParser.ImageType getType(Reader reader) throws IOException {
        int firstTwoBytes = reader.getUInt16();
        if (firstTwoBytes == EXIF_MAGIC_NUMBER) {
            return ImageHeaderParser.ImageType.JPEG;
        }
        int firstFourBytes = ((firstTwoBytes << 16) & SupportMenu.CATEGORY_MASK) | (reader.getUInt16() & SupportMenu.USER_MASK);
        if (firstFourBytes == PNG_HEADER) {
            reader.skip(21);
            return reader.getByte() >= 3 ? ImageHeaderParser.ImageType.PNG_A : ImageHeaderParser.ImageType.PNG;
        } else if ((firstFourBytes >> 8) == GIF_HEADER) {
            return ImageHeaderParser.ImageType.GIF;
        } else {
            if (firstFourBytes != RIFF_HEADER) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            reader.skip(4);
            if ((((reader.getUInt16() << 16) & SupportMenu.CATEGORY_MASK) | (reader.getUInt16() & SupportMenu.USER_MASK)) != WEBP_HEADER) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            int fourthFourBytes = (-65536 & (reader.getUInt16() << 16)) | (65535 & reader.getUInt16());
            if ((fourthFourBytes & -256) != VP8_HEADER) {
                return ImageHeaderParser.ImageType.UNKNOWN;
            }
            if ((fourthFourBytes & 255) == 88) {
                reader.skip(4);
                return (reader.getByte() & 16) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
            } else if ((fourthFourBytes & 255) != 76) {
                return ImageHeaderParser.ImageType.WEBP;
            } else {
                reader.skip(4);
                return (reader.getByte() & 8) != 0 ? ImageHeaderParser.ImageType.WEBP_A : ImageHeaderParser.ImageType.WEBP;
            }
        }
    }

    private int getOrientation(Reader reader, ArrayPool byteArrayPool) throws IOException {
        int magicNumber = reader.getUInt16();
        if (!handles(magicNumber)) {
            if (Log.isLoggable(TAG, 3)) {
                StringBuilder sb = new StringBuilder(47);
                sb.append("Parser doesn't handle magic number: ");
                sb.append(magicNumber);
                Log.d(TAG, sb.toString());
            }
            return -1;
        }
        int exifSegmentLength = moveToExifSegmentAndGetLength(reader);
        if (exifSegmentLength == -1) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Failed to parse exif segment length, or exif segment not found");
            }
            return -1;
        }
        byte[] exifData = (byte[]) byteArrayPool.get(exifSegmentLength, byte[].class);
        try {
            return parseExifSegment(reader, exifData, exifSegmentLength);
        } finally {
            byteArrayPool.put(exifData);
        }
    }

    private int parseExifSegment(Reader reader, byte[] tempArray, int exifSegmentLength) throws IOException {
        int read = reader.read(tempArray, exifSegmentLength);
        if (read != exifSegmentLength) {
            if (Log.isLoggable(TAG, 3)) {
                StringBuilder sb = new StringBuilder(81);
                sb.append("Unable to read exif segment data, length: ");
                sb.append(exifSegmentLength);
                sb.append(", actually read: ");
                sb.append(read);
                Log.d(TAG, sb.toString());
            }
            return -1;
        } else if (hasJpegExifPreamble(tempArray, exifSegmentLength)) {
            return parseExifSegment(new RandomAccessReader(tempArray, exifSegmentLength));
        } else {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Missing jpeg exif preamble");
            }
            return -1;
        }
    }

    private boolean hasJpegExifPreamble(byte[] exifData, int exifSegmentLength) {
        boolean result = exifData != null && exifSegmentLength > JPEG_EXIF_SEGMENT_PREAMBLE_BYTES.length;
        if (!result) {
            return result;
        }
        int i = 0;
        while (true) {
            byte[] bArr = JPEG_EXIF_SEGMENT_PREAMBLE_BYTES;
            if (i >= bArr.length) {
                return result;
            }
            if (exifData[i] != bArr[i]) {
                return false;
            }
            i++;
        }
    }

    private int moveToExifSegmentAndGetLength(Reader reader) throws IOException {
        short segmentType;
        int segmentLength;
        long skipped;
        do {
            short segmentId = reader.getUInt8();
            if (segmentId != 255) {
                if (Log.isLoggable(TAG, 3)) {
                    StringBuilder sb = new StringBuilder(24);
                    sb.append("Unknown segmentId=");
                    sb.append((int) segmentId);
                    Log.d(TAG, sb.toString());
                }
                return -1;
            }
            segmentType = reader.getUInt8();
            if (segmentType == 218) {
                return -1;
            }
            if (segmentType == 217) {
                if (Log.isLoggable(TAG, 3)) {
                    Log.d(TAG, "Found MARKER_EOI in exif segment");
                }
                return -1;
            }
            segmentLength = reader.getUInt16() - 2;
            if (segmentType == 225) {
                return segmentLength;
            }
            skipped = reader.skip((long) segmentLength);
        } while (skipped == ((long) segmentLength));
        if (Log.isLoggable(TAG, 3)) {
            StringBuilder sb2 = new StringBuilder(113);
            sb2.append("Unable to skip enough data, type: ");
            sb2.append((int) segmentType);
            sb2.append(", wanted to skip: ");
            sb2.append(segmentLength);
            sb2.append(", but actually skipped: ");
            sb2.append(skipped);
            Log.d(TAG, sb2.toString());
        }
        return -1;
    }

    private interface Reader {
        int getByte() throws IOException;

        int getUInt16() throws IOException;

        short getUInt8() throws IOException;

        int read(byte[] bArr, int i) throws IOException;

        long skip(long j) throws IOException;
    }

    private static final class RandomAccessReader {
        private final ByteBuffer data;

        RandomAccessReader(byte[] data2, int length) {
            this.data = (ByteBuffer) ByteBuffer.wrap(data2).order(ByteOrder.BIG_ENDIAN).limit(length);
        }

        /* access modifiers changed from: package-private */
        public void order(ByteOrder byteOrder) {
            this.data.order(byteOrder);
        }

        /* access modifiers changed from: package-private */
        public int length() {
            return this.data.remaining();
        }

        /* access modifiers changed from: package-private */
        public int getInt32(int offset) {
            if (isAvailable(offset, 4)) {
                return this.data.getInt(offset);
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public short getInt16(int offset) {
            if (isAvailable(offset, 2)) {
                return this.data.getShort(offset);
            }
            return -1;
        }

        private boolean isAvailable(int offset, int byteSize) {
            return this.data.remaining() - offset >= byteSize;
        }
    }

    private static final class ByteBufferReader implements Reader {
        private final ByteBuffer byteBuffer;

        ByteBufferReader(ByteBuffer byteBuffer2) {
            this.byteBuffer = byteBuffer2;
            byteBuffer2.order(ByteOrder.BIG_ENDIAN);
        }

        public int getUInt16() {
            return ((getByte() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (getByte() & 255);
        }

        public short getUInt8() {
            return (short) (getByte() & 255);
        }

        public long skip(long total) {
            int toSkip = (int) Math.min((long) this.byteBuffer.remaining(), total);
            ByteBuffer byteBuffer2 = this.byteBuffer;
            byteBuffer2.position(byteBuffer2.position() + toSkip);
            return (long) toSkip;
        }

        public int read(byte[] buffer, int byteCount) {
            int toRead = Math.min(byteCount, this.byteBuffer.remaining());
            if (toRead == 0) {
                return -1;
            }
            this.byteBuffer.get(buffer, 0, toRead);
            return toRead;
        }

        public int getByte() {
            if (this.byteBuffer.remaining() < 1) {
                return -1;
            }
            return this.byteBuffer.get();
        }
    }

    private static final class StreamReader implements Reader {

        /* renamed from: is */
        private final InputStream f60is;

        StreamReader(InputStream is) {
            this.f60is = is;
        }

        public int getUInt16() throws IOException {
            return ((this.f60is.read() << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) | (this.f60is.read() & 255);
        }

        public short getUInt8() throws IOException {
            return (short) (this.f60is.read() & 255);
        }

        public long skip(long total) throws IOException {
            if (total < 0) {
                return 0;
            }
            long toSkip = total;
            while (toSkip > 0) {
                long skipped = this.f60is.skip(toSkip);
                if (skipped > 0) {
                    toSkip -= skipped;
                } else if (this.f60is.read() == -1) {
                    break;
                } else {
                    toSkip--;
                }
            }
            return total - toSkip;
        }

        public int read(byte[] buffer, int byteCount) throws IOException {
            int toRead = byteCount;
            while (toRead > 0) {
                int read = this.f60is.read(buffer, byteCount - toRead, toRead);
                int read2 = read;
                if (read == -1) {
                    break;
                }
                toRead -= read2;
            }
            return byteCount - toRead;
        }

        public int getByte() throws IOException {
            return this.f60is.read();
        }
    }
}
