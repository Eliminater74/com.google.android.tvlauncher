package com.bumptech.glide.gifdecoder;

import android.graphics.Bitmap;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.primitives.UnsignedBytes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;

public class StandardGifDecoder implements GifDecoder {
    private static final int BYTES_PER_INTEGER = 4;
    @ColorInt
    private static final int COLOR_TRANSPARENT_BLACK = 0;
    private static final int INITIAL_FRAME_POINTER = -1;
    private static final int MASK_INT_LOWEST_BYTE = 255;
    private static final int MAX_STACK_SIZE = 4096;
    private static final int NULL_CODE = -1;
    private static final String TAG = StandardGifDecoder.class.getSimpleName();
    private final GifDecoder.BitmapProvider bitmapProvider;
    @ColorInt
    private final int[] pct;
    @ColorInt
    private int[] act;
    @NonNull
    private Bitmap.Config bitmapConfig;
    private byte[] block;
    private int downsampledHeight;
    private int downsampledWidth;
    private int framePointer;
    private GifHeader header;
    @Nullable
    private Boolean isFirstFrameTransparent;
    private byte[] mainPixels;
    @ColorInt
    private int[] mainScratch;
    private GifHeaderParser parser;
    private byte[] pixelStack;
    private short[] prefix;
    private Bitmap previousImage;
    private ByteBuffer rawData;
    private int sampleSize;
    private boolean savePrevious;
    private int status;
    private byte[] suffix;

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider provider, GifHeader gifHeader, ByteBuffer rawData2) {
        this(provider, gifHeader, rawData2, 1);
    }

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider provider, GifHeader gifHeader, ByteBuffer rawData2, int sampleSize2) {
        this(provider);
        setData(gifHeader, rawData2, sampleSize2);
    }

    public StandardGifDecoder(@NonNull GifDecoder.BitmapProvider provider) {
        this.pct = new int[256];
        this.bitmapConfig = Bitmap.Config.ARGB_8888;
        this.bitmapProvider = provider;
        this.header = new GifHeader();
    }

    public int getWidth() {
        return this.header.width;
    }

    public int getHeight() {
        return this.header.height;
    }

    @NonNull
    public ByteBuffer getData() {
        return this.rawData;
    }

    public int getStatus() {
        return this.status;
    }

    public void advance() {
        this.framePointer = (this.framePointer + 1) % this.header.frameCount;
    }

    public int getDelay(int n) {
        if (n < 0 || n >= this.header.frameCount) {
            return -1;
        }
        return this.header.frames.get(n).delay;
    }

    public int getNextDelay() {
        int i;
        if (this.header.frameCount <= 0 || (i = this.framePointer) < 0) {
            return 0;
        }
        return getDelay(i);
    }

    public int getFrameCount() {
        return this.header.frameCount;
    }

    public int getCurrentFrameIndex() {
        return this.framePointer;
    }

    public void resetFrameIndex() {
        this.framePointer = -1;
    }

    @Deprecated
    public int getLoopCount() {
        if (this.header.loopCount == -1) {
            return 1;
        }
        return this.header.loopCount;
    }

    public int getNetscapeLoopCount() {
        return this.header.loopCount;
    }

    public int getTotalIterationCount() {
        if (this.header.loopCount == -1) {
            return 1;
        }
        if (this.header.loopCount == 0) {
            return 0;
        }
        return this.header.loopCount + 1;
    }

    public int getByteSize() {
        return this.rawData.limit() + this.mainPixels.length + (this.mainScratch.length * 4);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00fd, code lost:
        return null;
     */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized android.graphics.Bitmap getNextFrame() {
        /*
            r9 = this;
            monitor-enter(r9)
            com.bumptech.glide.gifdecoder.GifHeader r0 = r9.header     // Catch:{ all -> 0x00fe }
            int r0 = r0.frameCount     // Catch:{ all -> 0x00fe }
            r1 = 3
            r2 = 1
            if (r0 <= 0) goto L_0x000d
            int r0 = r9.framePointer     // Catch:{ all -> 0x00fe }
            if (r0 >= 0) goto L_0x003d
        L_0x000d:
            java.lang.String r0 = com.bumptech.glide.gifdecoder.StandardGifDecoder.TAG     // Catch:{ all -> 0x00fe }
            boolean r0 = android.util.Log.isLoggable(r0, r1)     // Catch:{ all -> 0x00fe }
            if (r0 == 0) goto L_0x003b
            java.lang.String r0 = com.bumptech.glide.gifdecoder.StandardGifDecoder.TAG     // Catch:{ all -> 0x00fe }
            com.bumptech.glide.gifdecoder.GifHeader r3 = r9.header     // Catch:{ all -> 0x00fe }
            int r3 = r3.frameCount     // Catch:{ all -> 0x00fe }
            int r4 = r9.framePointer     // Catch:{ all -> 0x00fe }
            r5 = 72
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fe }
            r6.<init>(r5)     // Catch:{ all -> 0x00fe }
            java.lang.String r5 = "Unable to decode frame, frameCount="
            r6.append(r5)     // Catch:{ all -> 0x00fe }
            r6.append(r3)     // Catch:{ all -> 0x00fe }
            java.lang.String r3 = ", framePointer="
            r6.append(r3)     // Catch:{ all -> 0x00fe }
            r6.append(r4)     // Catch:{ all -> 0x00fe }
            java.lang.String r3 = r6.toString()     // Catch:{ all -> 0x00fe }
            android.util.Log.d(r0, r3)     // Catch:{ all -> 0x00fe }
        L_0x003b:
            r9.status = r2     // Catch:{ all -> 0x00fe }
        L_0x003d:
            int r0 = r9.status     // Catch:{ all -> 0x00fe }
            r3 = 0
            if (r0 == r2) goto L_0x00da
            int r0 = r9.status     // Catch:{ all -> 0x00fe }
            r4 = 2
            if (r0 != r4) goto L_0x0049
            goto L_0x00da
        L_0x0049:
            r0 = 0
            r9.status = r0     // Catch:{ all -> 0x00fe }
            byte[] r5 = r9.block     // Catch:{ all -> 0x00fe }
            if (r5 != 0) goto L_0x005a
            com.bumptech.glide.gifdecoder.GifDecoder$BitmapProvider r5 = r9.bitmapProvider     // Catch:{ all -> 0x00fe }
            r6 = 255(0xff, float:3.57E-43)
            byte[] r5 = r5.obtainByteArray(r6)     // Catch:{ all -> 0x00fe }
            r9.block = r5     // Catch:{ all -> 0x00fe }
        L_0x005a:
            com.bumptech.glide.gifdecoder.GifHeader r5 = r9.header     // Catch:{ all -> 0x00fe }
            java.util.List<com.bumptech.glide.gifdecoder.GifFrame> r5 = r5.frames     // Catch:{ all -> 0x00fe }
            int r6 = r9.framePointer     // Catch:{ all -> 0x00fe }
            java.lang.Object r5 = r5.get(r6)     // Catch:{ all -> 0x00fe }
            com.bumptech.glide.gifdecoder.GifFrame r5 = (com.bumptech.glide.gifdecoder.GifFrame) r5     // Catch:{ all -> 0x00fe }
            r6 = 0
            int r7 = r9.framePointer     // Catch:{ all -> 0x00fe }
            int r7 = r7 - r2
            if (r7 < 0) goto L_0x0077
            com.bumptech.glide.gifdecoder.GifHeader r8 = r9.header     // Catch:{ all -> 0x00fe }
            java.util.List<com.bumptech.glide.gifdecoder.GifFrame> r8 = r8.frames     // Catch:{ all -> 0x00fe }
            java.lang.Object r8 = r8.get(r7)     // Catch:{ all -> 0x00fe }
            com.bumptech.glide.gifdecoder.GifFrame r8 = (com.bumptech.glide.gifdecoder.GifFrame) r8     // Catch:{ all -> 0x00fe }
            r6 = r8
        L_0x0077:
            int[] r8 = r5.lct     // Catch:{ all -> 0x00fe }
            if (r8 == 0) goto L_0x007e
            int[] r8 = r5.lct     // Catch:{ all -> 0x00fe }
            goto L_0x0082
        L_0x007e:
            com.bumptech.glide.gifdecoder.GifHeader r8 = r9.header     // Catch:{ all -> 0x00fe }
            int[] r8 = r8.gct     // Catch:{ all -> 0x00fe }
        L_0x0082:
            r9.act = r8     // Catch:{ all -> 0x00fe }
            int[] r8 = r9.act     // Catch:{ all -> 0x00fe }
            if (r8 != 0) goto L_0x00ae
            java.lang.String r0 = com.bumptech.glide.gifdecoder.StandardGifDecoder.TAG     // Catch:{ all -> 0x00fe }
            boolean r0 = android.util.Log.isLoggable(r0, r1)     // Catch:{ all -> 0x00fe }
            if (r0 == 0) goto L_0x00aa
            java.lang.String r0 = com.bumptech.glide.gifdecoder.StandardGifDecoder.TAG     // Catch:{ all -> 0x00fe }
            int r1 = r9.framePointer     // Catch:{ all -> 0x00fe }
            r4 = 49
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fe }
            r8.<init>(r4)     // Catch:{ all -> 0x00fe }
            java.lang.String r4 = "No valid color table found for frame #"
            r8.append(r4)     // Catch:{ all -> 0x00fe }
            r8.append(r1)     // Catch:{ all -> 0x00fe }
            java.lang.String r1 = r8.toString()     // Catch:{ all -> 0x00fe }
            android.util.Log.d(r0, r1)     // Catch:{ all -> 0x00fe }
        L_0x00aa:
            r9.status = r2     // Catch:{ all -> 0x00fe }
            monitor-exit(r9)
            return r3
        L_0x00ae:
            boolean r1 = r5.transparency     // Catch:{ all -> 0x00fe }
            if (r1 == 0) goto L_0x00d4
            int[] r1 = r9.act     // Catch:{ all -> 0x00fe }
            int[] r3 = r9.pct     // Catch:{ all -> 0x00fe }
            int[] r8 = r9.act     // Catch:{ all -> 0x00fe }
            int r8 = r8.length     // Catch:{ all -> 0x00fe }
            java.lang.System.arraycopy(r1, r0, r3, r0, r8)     // Catch:{ all -> 0x00fe }
            int[] r1 = r9.pct     // Catch:{ all -> 0x00fe }
            r9.act = r1     // Catch:{ all -> 0x00fe }
            int[] r1 = r9.act     // Catch:{ all -> 0x00fe }
            int r3 = r5.transIndex     // Catch:{ all -> 0x00fe }
            r1[r3] = r0     // Catch:{ all -> 0x00fe }
            int r0 = r5.dispose     // Catch:{ all -> 0x00fe }
            if (r0 != r4) goto L_0x00d4
            int r0 = r9.framePointer     // Catch:{ all -> 0x00fe }
            if (r0 != 0) goto L_0x00d4
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r2)     // Catch:{ all -> 0x00fe }
            r9.isFirstFrameTransparent = r0     // Catch:{ all -> 0x00fe }
        L_0x00d4:
            android.graphics.Bitmap r0 = r9.setPixels(r5, r6)     // Catch:{ all -> 0x00fe }
            monitor-exit(r9)
            return r0
        L_0x00da:
            java.lang.String r0 = com.bumptech.glide.gifdecoder.StandardGifDecoder.TAG     // Catch:{ all -> 0x00fe }
            boolean r0 = android.util.Log.isLoggable(r0, r1)     // Catch:{ all -> 0x00fe }
            if (r0 == 0) goto L_0x00fc
            java.lang.String r0 = com.bumptech.glide.gifdecoder.StandardGifDecoder.TAG     // Catch:{ all -> 0x00fe }
            int r1 = r9.status     // Catch:{ all -> 0x00fe }
            r2 = 42
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00fe }
            r4.<init>(r2)     // Catch:{ all -> 0x00fe }
            java.lang.String r2 = "Unable to decode frame, status="
            r4.append(r2)     // Catch:{ all -> 0x00fe }
            r4.append(r1)     // Catch:{ all -> 0x00fe }
            java.lang.String r1 = r4.toString()     // Catch:{ all -> 0x00fe }
            android.util.Log.d(r0, r1)     // Catch:{ all -> 0x00fe }
        L_0x00fc:
            monitor-exit(r9)
            return r3
        L_0x00fe:
            r0 = move-exception
            monitor-exit(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.StandardGifDecoder.getNextFrame():android.graphics.Bitmap");
    }

    public int read(@Nullable InputStream is, int contentLength) {
        if (is != null) {
            try {
                ByteArrayOutputStream buffer = new ByteArrayOutputStream(contentLength > 0 ? contentLength + 4096 : 16384);
                byte[] data = new byte[16384];
                while (true) {
                    int read = is.read(data, 0, data.length);
                    int nRead = read;
                    if (read == -1) {
                        break;
                    }
                    buffer.write(data, 0, nRead);
                }
                buffer.flush();
                read(buffer.toByteArray());
            } catch (IOException e) {
                Log.w(TAG, "Error reading data from stream", e);
            }
        } else {
            this.status = 2;
        }
        if (is != null) {
            try {
                is.close();
            } catch (IOException e2) {
                Log.w(TAG, "Error closing stream", e2);
            }
        }
        return this.status;
    }

    public void clear() {
        this.header = null;
        byte[] bArr = this.mainPixels;
        if (bArr != null) {
            this.bitmapProvider.release(bArr);
        }
        int[] iArr = this.mainScratch;
        if (iArr != null) {
            this.bitmapProvider.release(iArr);
        }
        Bitmap bitmap = this.previousImage;
        if (bitmap != null) {
            this.bitmapProvider.release(bitmap);
        }
        this.previousImage = null;
        this.rawData = null;
        this.isFirstFrameTransparent = null;
        byte[] bArr2 = this.block;
        if (bArr2 != null) {
            this.bitmapProvider.release(bArr2);
        }
    }

    public synchronized void setData(@NonNull GifHeader header2, @NonNull byte[] data) {
        setData(header2, ByteBuffer.wrap(data));
    }

    public synchronized void setData(@NonNull GifHeader header2, @NonNull ByteBuffer buffer) {
        setData(header2, buffer, 1);
    }

    public synchronized void setData(@NonNull GifHeader header2, @NonNull ByteBuffer buffer, int sampleSize2) {
        if (sampleSize2 > 0) {
            int sampleSize3 = Integer.highestOneBit(sampleSize2);
            this.status = 0;
            this.header = header2;
            this.framePointer = -1;
            this.rawData = buffer.asReadOnlyBuffer();
            this.rawData.position(0);
            this.rawData.order(ByteOrder.LITTLE_ENDIAN);
            this.savePrevious = false;
            Iterator<GifFrame> it = header2.frames.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                } else if (it.next().dispose == 3) {
                    this.savePrevious = true;
                    break;
                }
            }
            this.sampleSize = sampleSize3;
            this.downsampledWidth = header2.width / sampleSize3;
            this.downsampledHeight = header2.height / sampleSize3;
            this.mainPixels = this.bitmapProvider.obtainByteArray(header2.width * header2.height);
            this.mainScratch = this.bitmapProvider.obtainIntArray(this.downsampledWidth * this.downsampledHeight);
        } else {
            StringBuilder sb = new StringBuilder(41);
            sb.append("Sample size must be >=0, not: ");
            sb.append(sampleSize2);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @NonNull
    private GifHeaderParser getHeaderParser() {
        if (this.parser == null) {
            this.parser = new GifHeaderParser();
        }
        return this.parser;
    }

    public synchronized int read(@Nullable byte[] data) {
        this.header = getHeaderParser().setData(data).parseHeader();
        if (data != null) {
            setData(this.header, data);
        }
        return this.status;
    }

    public void setDefaultBitmapConfig(@NonNull Bitmap.Config config) {
        if (config == Bitmap.Config.ARGB_8888 || config == Bitmap.Config.RGB_565) {
            this.bitmapConfig = config;
            return;
        }
        String valueOf = String.valueOf(config);
        String valueOf2 = String.valueOf(Bitmap.Config.ARGB_8888);
        String valueOf3 = String.valueOf(Bitmap.Config.RGB_565);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 41 + String.valueOf(valueOf2).length() + String.valueOf(valueOf3).length());
        sb.append("Unsupported format: ");
        sb.append(valueOf);
        sb.append(", must be one of ");
        sb.append(valueOf2);
        sb.append(" or ");
        sb.append(valueOf3);
        throw new IllegalArgumentException(sb.toString());
    }

    private Bitmap setPixels(GifFrame currentFrame, GifFrame previousFrame) {
        Bitmap bitmap;
        int[] dest = this.mainScratch;
        if (previousFrame == null) {
            Bitmap bitmap2 = this.previousImage;
            if (bitmap2 != null) {
                this.bitmapProvider.release(bitmap2);
            }
            this.previousImage = null;
            Arrays.fill(dest, 0);
        }
        if (previousFrame != null && previousFrame.dispose == 3 && this.previousImage == null) {
            Arrays.fill(dest, 0);
        }
        if (previousFrame != null && previousFrame.dispose > 0) {
            if (previousFrame.dispose == 2) {
                int c = 0;
                if (!currentFrame.transparency) {
                    c = this.header.bgColor;
                    if (currentFrame.lct != null && this.header.bgIndex == currentFrame.transIndex) {
                        c = 0;
                    }
                }
                int downsampledIH = previousFrame.f45ih / this.sampleSize;
                int downsampledIY = previousFrame.f48iy / this.sampleSize;
                int downsampledIW = previousFrame.f46iw / this.sampleSize;
                int downsampledIX = previousFrame.f47ix / this.sampleSize;
                int i = this.downsampledWidth;
                int topLeft = (downsampledIY * i) + downsampledIX;
                int bottomLeft = (i * downsampledIH) + topLeft;
                int left = topLeft;
                while (left < bottomLeft) {
                    int right = left + downsampledIW;
                    for (int pointer = left; pointer < right; pointer++) {
                        dest[pointer] = c;
                    }
                    left += this.downsampledWidth;
                }
            } else if (previousFrame.dispose == 3 && (bitmap = this.previousImage) != null) {
                int i2 = this.downsampledWidth;
                bitmap.getPixels(dest, 0, i2, 0, 0, i2, this.downsampledHeight);
            }
        }
        decodeBitmapData(currentFrame);
        if (currentFrame.interlace || this.sampleSize != 1) {
            copyCopyIntoScratchRobust(currentFrame);
        } else {
            copyIntoScratchFast(currentFrame);
        }
        if (this.savePrevious && (currentFrame.dispose == 0 || currentFrame.dispose == 1)) {
            if (this.previousImage == null) {
                this.previousImage = getNextBitmap();
            }
            Bitmap bitmap3 = this.previousImage;
            int i3 = this.downsampledWidth;
            bitmap3.setPixels(dest, 0, i3, 0, 0, i3, this.downsampledHeight);
        }
        Bitmap result = getNextBitmap();
        int i4 = this.downsampledWidth;
        result.setPixels(dest, 0, i4, 0, 0, i4, this.downsampledHeight);
        return result;
    }

    /* JADX INFO: Multiple debug info for r3v3 byte: [D('byteCurrentColorIndex' byte), D('downsampledIH' int)] */
    /* JADX INFO: Multiple debug info for r4v3 byte: [D('currentColorIndex' int), D('downsampledIY' int)] */
    private void copyIntoScratchFast(GifFrame currentFrame) {
        GifFrame gifFrame = currentFrame;
        int[] dest = this.mainScratch;
        int downsampledIH = gifFrame.f45ih;
        int downsampledIY = gifFrame.f48iy;
        int downsampledIW = gifFrame.f46iw;
        int downsampledIX = gifFrame.f47ix;
        boolean isFirstFrame = this.framePointer == 0;
        int width = this.downsampledWidth;
        byte[] mainPixels2 = this.mainPixels;
        int[] act2 = this.act;
        byte transparentColorIndex = -1;
        int i = 0;
        while (i < downsampledIH) {
            int k = (i + downsampledIY) * width;
            int dx = k + downsampledIX;
            int dlim = dx + downsampledIW;
            if (k + width < dlim) {
                dlim = k + width;
            }
            byte transparentColorIndex2 = transparentColorIndex;
            int sx = gifFrame.f46iw * i;
            int dx2 = dx;
            while (dx2 < dlim) {
                int downsampledIH2 = downsampledIH;
                byte byteCurrentColorIndex = mainPixels2[sx];
                int downsampledIY2 = downsampledIY;
                byte downsampledIY3 = byteCurrentColorIndex & UnsignedBytes.MAX_VALUE;
                if (downsampledIY3 != transparentColorIndex2) {
                    int color = act2[downsampledIY3];
                    if (color != 0) {
                        dest[dx2] = color;
                    } else {
                        transparentColorIndex2 = byteCurrentColorIndex;
                    }
                }
                sx++;
                dx2++;
                downsampledIH = downsampledIH2;
                downsampledIY = downsampledIY2;
            }
            i++;
            transparentColorIndex = transparentColorIndex2;
            gifFrame = currentFrame;
        }
        Boolean bool = this.isFirstFrameTransparent;
        this.isFirstFrameTransparent = Boolean.valueOf((bool != null && bool.booleanValue()) || (this.isFirstFrameTransparent == null && isFirstFrame && transparentColorIndex != -1));
    }

    /* JADX INFO: Multiple debug info for r7v2 java.lang.Boolean: [D('pass' int), D('isFirstFrameTransparent' java.lang.Boolean)] */
    /* JADX INFO: Multiple debug info for r3v6 int: [D('downsampledIH' int), D('line' int)] */
    /* JADX INFO: Multiple debug info for r3v8 int: [D('line' int), D('dlim' int)] */
    private void copyCopyIntoScratchRobust(GifFrame currentFrame) {
        int downsampledIH;
        int downsampledIX;
        int downsampledIW;
        int downsampledIY;
        int pass;
        GifFrame gifFrame = currentFrame;
        int[] dest = this.mainScratch;
        int downsampledIH2 = gifFrame.f45ih / this.sampleSize;
        int downsampledIY2 = gifFrame.f48iy / this.sampleSize;
        int downsampledIW2 = gifFrame.f46iw / this.sampleSize;
        int downsampledIX2 = gifFrame.f47ix / this.sampleSize;
        int iline = 0;
        boolean isFirstFrame = this.framePointer == 0;
        int sampleSize2 = this.sampleSize;
        int downsampledWidth2 = this.downsampledWidth;
        int downsampledHeight2 = this.downsampledHeight;
        byte[] mainPixels2 = this.mainPixels;
        int[] act2 = this.act;
        int pass2 = 1;
        Boolean isFirstFrameTransparent2 = this.isFirstFrameTransparent;
        int i = 0;
        int inc = 8;
        while (i < downsampledIH2) {
            int line = i;
            boolean isFirstFrameTransparent3 = isFirstFrameTransparent2;
            if (gifFrame.interlace) {
                if (iline >= downsampledIH2) {
                    pass = pass2 + 1;
                    downsampledIH = downsampledIH2;
                    if (pass == 2) {
                        iline = 4;
                    } else if (pass == 3) {
                        iline = 2;
                        inc = 4;
                    } else if (pass == 4) {
                        iline = 1;
                        inc = 2;
                    }
                } else {
                    downsampledIH = downsampledIH2;
                    pass = pass2;
                }
                line = iline;
                iline += inc;
                pass2 = pass;
            } else {
                downsampledIH = downsampledIH2;
            }
            int line2 = line + downsampledIY2;
            boolean isNotDownsampling = sampleSize2 == 1;
            if (line2 < downsampledHeight2) {
                int k = line2 * downsampledWidth2;
                int dx = k + downsampledIX2;
                int dlim = dx + downsampledIW2;
                downsampledIY = downsampledIY2;
                if (k + downsampledWidth2 < dlim) {
                    dlim = k + downsampledWidth2;
                }
                downsampledIW = downsampledIW2;
                int sx = i * sampleSize2 * gifFrame.f46iw;
                if (isNotDownsampling) {
                    int sx2 = sx;
                    int dx2 = dx;
                    while (dx2 < dlim) {
                        int downsampledIX3 = downsampledIX2;
                        int averageColor = act2[mainPixels2[sx2] & 255];
                        if (averageColor != 0) {
                            dest[dx2] = averageColor;
                        } else if (isFirstFrame && isFirstFrameTransparent3 == null) {
                            isFirstFrameTransparent3 = true;
                        }
                        sx2 += sampleSize2;
                        dx2++;
                        downsampledIX2 = downsampledIX3;
                    }
                    downsampledIX = downsampledIX2;
                    isFirstFrameTransparent2 = isFirstFrameTransparent3;
                } else {
                    downsampledIX = downsampledIX2;
                    int maxPositionInSource = ((dlim - dx) * sampleSize2) + sx;
                    int sx3 = sx;
                    int dx3 = dx;
                    while (dx3 < dlim) {
                        int dlim2 = dlim;
                        int averageColor2 = averageColorsNear(sx3, maxPositionInSource, gifFrame.f46iw);
                        if (averageColor2 != 0) {
                            dest[dx3] = averageColor2;
                        } else if (isFirstFrame && isFirstFrameTransparent3 == null) {
                            isFirstFrameTransparent3 = true;
                        }
                        sx3 += sampleSize2;
                        dx3++;
                        dlim = dlim2;
                    }
                    isFirstFrameTransparent2 = isFirstFrameTransparent3;
                }
            } else {
                downsampledIY = downsampledIY2;
                downsampledIW = downsampledIW2;
                downsampledIX = downsampledIX2;
                isFirstFrameTransparent2 = isFirstFrameTransparent3;
            }
            i++;
            downsampledIH2 = downsampledIH;
            downsampledIY2 = downsampledIY;
            downsampledIW2 = downsampledIW;
            downsampledIX2 = downsampledIX;
        }
        Boolean isFirstFrameTransparent4 = isFirstFrameTransparent2;
        if (this.isFirstFrameTransparent == null) {
            this.isFirstFrameTransparent = Boolean.valueOf(isFirstFrameTransparent4 == null ? false : isFirstFrameTransparent4.booleanValue());
        }
    }

    @ColorInt
    private int averageColorsNear(int positionInMainPixels, int maxPositionInMainPixels, int currentFrameIw) {
        int alphaSum = 0;
        int redSum = 0;
        int greenSum = 0;
        int blueSum = 0;
        int totalAdded = 0;
        for (int i = positionInMainPixels; i < this.sampleSize + positionInMainPixels; i++) {
            byte[] bArr = this.mainPixels;
            if (i >= bArr.length || i >= maxPositionInMainPixels) {
                break;
            }
            int currentColor = this.act[bArr[i] & 255];
            if (currentColor != 0) {
                alphaSum += (currentColor >> 24) & 255;
                redSum += (currentColor >> 16) & 255;
                greenSum += (currentColor >> 8) & 255;
                blueSum += currentColor & 255;
                totalAdded++;
            }
        }
        for (int i2 = positionInMainPixels + currentFrameIw; i2 < positionInMainPixels + currentFrameIw + this.sampleSize; i2++) {
            byte[] bArr2 = this.mainPixels;
            if (i2 >= bArr2.length || i2 >= maxPositionInMainPixels) {
                break;
            }
            int currentColor2 = this.act[bArr2[i2] & 255];
            if (currentColor2 != 0) {
                alphaSum += (currentColor2 >> 24) & 255;
                redSum += (currentColor2 >> 16) & 255;
                greenSum += (currentColor2 >> 8) & 255;
                blueSum += currentColor2 & 255;
                totalAdded++;
            }
        }
        if (totalAdded == 0) {
            return 0;
        }
        return ((alphaSum / totalAdded) << 24) | ((redSum / totalAdded) << 16) | ((greenSum / totalAdded) << 8) | (blueSum / totalAdded);
    }

    /* JADX WARN: Failed to insert an additional move for type inference into block B:68:0x00c0 */
    /* JADX WARN: Failed to insert an additional move for type inference into block B:75:0x0107 */
    /* JADX WARN: Failed to insert an additional move for type inference into block B:73:0x00c0 */
    /* JADX INFO: additional move instructions added (1) to help type inference */
    /* JADX WARN: Type inference failed for: r4v1, types: [short[]] */
    /* JADX WARN: Type inference failed for: r13v11, types: [short] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void decodeBitmapData(com.bumptech.glide.gifdecoder.GifFrame r28) {
        /*
            r27 = this;
            r0 = r27
            r1 = r28
            if (r1 == 0) goto L_0x000d
            java.nio.ByteBuffer r2 = r0.rawData
            int r3 = r1.bufferFrameStart
            r2.position(r3)
        L_0x000d:
            if (r1 != 0) goto L_0x0018
            com.bumptech.glide.gifdecoder.GifHeader r2 = r0.header
            int r2 = r2.width
            com.bumptech.glide.gifdecoder.GifHeader r3 = r0.header
            int r3 = r3.height
            goto L_0x001c
        L_0x0018:
            int r2 = r1.f46iw
            int r3 = r1.f45ih
        L_0x001c:
            int r2 = r2 * r3
            byte[] r3 = r0.mainPixels
            if (r3 == 0) goto L_0x0025
            int r3 = r3.length
            if (r3 >= r2) goto L_0x002d
        L_0x0025:
            com.bumptech.glide.gifdecoder.GifDecoder$BitmapProvider r3 = r0.bitmapProvider
            byte[] r3 = r3.obtainByteArray(r2)
            r0.mainPixels = r3
        L_0x002d:
            byte[] r3 = r0.mainPixels
            short[] r4 = r0.prefix
            r5 = 4096(0x1000, float:5.74E-42)
            if (r4 != 0) goto L_0x0039
            short[] r4 = new short[r5]
            r0.prefix = r4
        L_0x0039:
            short[] r4 = r0.prefix
            byte[] r6 = r0.suffix
            if (r6 != 0) goto L_0x0043
            byte[] r6 = new byte[r5]
            r0.suffix = r6
        L_0x0043:
            byte[] r6 = r0.suffix
            byte[] r7 = r0.pixelStack
            if (r7 != 0) goto L_0x004f
            r7 = 4097(0x1001, float:5.741E-42)
            byte[] r7 = new byte[r7]
            r0.pixelStack = r7
        L_0x004f:
            byte[] r7 = r0.pixelStack
            int r8 = r27.readByte()
            r9 = 1
            int r10 = r9 << r8
            int r11 = r10 + 1
            int r12 = r10 + 2
            r13 = -1
            int r14 = r8 + 1
            int r15 = r9 << r14
            int r15 = r15 - r9
            r16 = 0
            r5 = r16
        L_0x0066:
            r9 = 0
            if (r5 >= r10) goto L_0x0072
            r4[r5] = r9
            byte r9 = (byte) r5
            r6[r5] = r9
            int r5 = r5 + 1
            r9 = 1
            goto L_0x0066
        L_0x0072:
            byte[] r1 = r0.block
            r17 = r9
            r18 = r9
            r19 = r9
            r20 = r9
            r21 = r9
            r22 = r9
            r23 = r9
            r24 = r9
            r26 = r12
            r12 = r5
            r5 = r24
            r24 = r14
            r14 = r18
            r18 = r15
            r15 = r26
        L_0x0091:
            if (r5 >= r2) goto L_0x0167
            if (r21 != 0) goto L_0x00a2
            int r21 = r27.readBlock()
            if (r21 > 0) goto L_0x00a0
            r9 = 3
            r0.status = r9
            goto L_0x0167
        L_0x00a0:
            r17 = 0
        L_0x00a2:
            byte r9 = r1[r17]
            r9 = r9 & 255(0xff, float:3.57E-43)
            int r9 = r9 << r22
            int r23 = r23 + r9
            int r22 = r22 + 8
            r9 = 1
            int r17 = r17 + 1
            r9 = -1
            int r21 = r21 + -1
            r25 = r20
            r20 = r19
            r19 = r5
            r5 = r22
            r22 = r14
            r14 = r13
            r13 = r12
            r12 = r24
        L_0x00c0:
            if (r5 < r12) goto L_0x014e
            r13 = r23 & r18
            int r23 = r23 >> r12
            int r5 = r5 - r12
            if (r13 != r10) goto L_0x00d5
            int r12 = r8 + 1
            r16 = 1
            int r24 = r16 << r12
            int r18 = r24 + -1
            int r15 = r10 + 2
            r14 = -1
            goto L_0x00c0
        L_0x00d5:
            r16 = 1
            if (r13 != r11) goto L_0x00e9
            r24 = r12
            r12 = r13
            r13 = r14
            r14 = r22
            r9 = 0
            r22 = r5
            r5 = r19
            r19 = r20
            r20 = r25
            goto L_0x0091
        L_0x00e9:
            if (r14 != r9) goto L_0x00f7
            byte r24 = r6[r13]
            r3[r22] = r24
            int r22 = r22 + 1
            int r19 = r19 + 1
            r14 = r13
            r25 = r13
            goto L_0x00c0
        L_0x00f7:
            r24 = r13
            if (r13 < r15) goto L_0x0105
            r9 = r25
            byte r0 = (byte) r9
            r7[r20] = r0
            int r20 = r20 + 1
            r0 = r14
            r13 = r0
            goto L_0x0107
        L_0x0105:
            r9 = r25
        L_0x0107:
            if (r13 < r10) goto L_0x0112
            byte r0 = r6[r13]
            r7[r20] = r0
            int r20 = r20 + 1
            short r13 = r4[r13]
            goto L_0x0107
        L_0x0112:
            byte r0 = r6[r13]
            r0 = r0 & 255(0xff, float:3.57E-43)
            byte r9 = (byte) r0
            r3[r22] = r9
            int r22 = r22 + 1
            int r19 = r19 + 1
        L_0x011d:
            if (r20 <= 0) goto L_0x012a
            int r20 = r20 + -1
            byte r9 = r7[r20]
            r3[r22] = r9
            int r22 = r22 + 1
            int r19 = r19 + 1
            goto L_0x011d
        L_0x012a:
            r9 = 4096(0x1000, float:5.74E-42)
            if (r15 >= r9) goto L_0x0145
            short r9 = (short) r14
            r4[r15] = r9
            byte r9 = (byte) r0
            r6[r15] = r9
            int r15 = r15 + 1
            r9 = r15 & r18
            if (r9 != 0) goto L_0x0143
            r9 = 4096(0x1000, float:5.74E-42)
            if (r15 >= r9) goto L_0x0145
            int r12 = r12 + 1
            int r18 = r18 + r15
            goto L_0x0145
        L_0x0143:
            r9 = 4096(0x1000, float:5.74E-42)
        L_0x0145:
            r14 = r24
            r9 = -1
            r25 = r0
            r0 = r27
            goto L_0x00c0
        L_0x014e:
            r9 = r25
            r0 = 4096(0x1000, float:5.74E-42)
            r16 = 1
            r0 = r27
            r24 = r12
            r12 = r13
            r13 = r14
            r14 = r22
            r22 = r5
            r5 = r19
            r19 = r20
            r20 = r9
            r9 = 0
            goto L_0x0091
        L_0x0167:
            r0 = 0
            java.util.Arrays.fill(r3, r14, r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.gifdecoder.StandardGifDecoder.decodeBitmapData(com.bumptech.glide.gifdecoder.GifFrame):void");
    }

    private int readByte() {
        return this.rawData.get() & UnsignedBytes.MAX_VALUE;
    }

    private int readBlock() {
        int blockSize = readByte();
        if (blockSize <= 0) {
            return blockSize;
        }
        ByteBuffer byteBuffer = this.rawData;
        byteBuffer.get(this.block, 0, Math.min(blockSize, byteBuffer.remaining()));
        return blockSize;
    }

    private Bitmap getNextBitmap() {
        Boolean bool = this.isFirstFrameTransparent;
        Bitmap result = this.bitmapProvider.obtain(this.downsampledWidth, this.downsampledHeight, (bool == null || bool.booleanValue()) ? Bitmap.Config.ARGB_8888 : this.bitmapConfig);
        result.setHasAlpha(true);
        return result;
    }
}
