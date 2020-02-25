package com.bumptech.glide.gifdecoder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class GifHeaderParser {
    static final int DEFAULT_FRAME_DELAY = 10;
    static final int MIN_FRAME_DELAY = 2;
    private static final int DESCRIPTOR_MASK_INTERLACE_FLAG = 64;
    private static final int DESCRIPTOR_MASK_LCT_FLAG = 128;
    private static final int DESCRIPTOR_MASK_LCT_SIZE = 7;
    private static final int EXTENSION_INTRODUCER = 33;
    private static final int GCE_DISPOSAL_METHOD_SHIFT = 2;
    private static final int GCE_MASK_DISPOSAL_METHOD = 28;
    private static final int GCE_MASK_TRANSPARENT_COLOR_FLAG = 1;
    private static final int IMAGE_SEPARATOR = 44;
    private static final int LABEL_APPLICATION_EXTENSION = 255;
    private static final int LABEL_COMMENT_EXTENSION = 254;
    private static final int LABEL_GRAPHIC_CONTROL_EXTENSION = 249;
    private static final int LABEL_PLAIN_TEXT_EXTENSION = 1;
    private static final int LSD_MASK_GCT_FLAG = 128;
    private static final int LSD_MASK_GCT_SIZE = 7;
    private static final int MASK_INT_LOWEST_BYTE = 255;
    private static final int MAX_BLOCK_SIZE = 256;
    private static final String TAG = "GifHeaderParser";
    private static final int TRAILER = 59;
    private final byte[] block = new byte[256];
    private int blockSize = 0;
    private GifHeader header;
    private ByteBuffer rawData;

    public GifHeaderParser setData(@NonNull ByteBuffer data) {
        reset();
        this.rawData = data.asReadOnlyBuffer();
        this.rawData.position(0);
        this.rawData.order(ByteOrder.LITTLE_ENDIAN);
        return this;
    }

    public GifHeaderParser setData(@Nullable byte[] data) {
        if (data != null) {
            setData(ByteBuffer.wrap(data));
        } else {
            this.rawData = null;
            this.header.status = 2;
        }
        return this;
    }

    public void clear() {
        this.rawData = null;
        this.header = null;
    }

    private void reset() {
        this.rawData = null;
        Arrays.fill(this.block, (byte) 0);
        this.header = new GifHeader();
        this.blockSize = 0;
    }

    @NonNull
    public GifHeader parseHeader() {
        if (this.rawData == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        } else if (err()) {
            return this.header;
        } else {
            readHeader();
            if (!err()) {
                readContents();
                if (this.header.frameCount < 0) {
                    this.header.status = 1;
                }
            }
            return this.header;
        }
    }

    public boolean isAnimated() {
        readHeader();
        if (!err()) {
            readContents(2);
        }
        return this.header.frameCount > 1;
    }

    private void readContents() {
        readContents(Integer.MAX_VALUE);
    }

    private void readContents(int maxFrames) {
        boolean done = false;
        while (!done && !err() && this.header.frameCount <= maxFrames) {
            int code = read();
            if (code == 33) {
                int extensionLabel = read();
                if (extensionLabel == 1) {
                    skip();
                } else if (extensionLabel == 249) {
                    this.header.currentFrame = new GifFrame();
                    readGraphicControlExt();
                } else if (extensionLabel == 254) {
                    skip();
                } else if (extensionLabel != 255) {
                    skip();
                } else {
                    readBlock();
                    StringBuilder app = new StringBuilder();
                    for (int i = 0; i < 11; i++) {
                        app.append((char) this.block[i]);
                    }
                    if (app.toString().equals("NETSCAPE2.0")) {
                        readNetscapeExt();
                    } else {
                        skip();
                    }
                }
            } else if (code == 44) {
                if (this.header.currentFrame == null) {
                    this.header.currentFrame = new GifFrame();
                }
                readBitmap();
            } else if (code != 59) {
                this.header.status = 1;
            } else {
                done = true;
            }
        }
    }

    private void readGraphicControlExt() {
        read();
        int packed = read();
        this.header.currentFrame.dispose = (packed & 28) >> 2;
        boolean z = true;
        if (this.header.currentFrame.dispose == 0) {
            this.header.currentFrame.dispose = 1;
        }
        GifFrame gifFrame = this.header.currentFrame;
        if ((packed & 1) == 0) {
            z = false;
        }
        gifFrame.transparency = z;
        int delayInHundredthsOfASecond = readShort();
        if (delayInHundredthsOfASecond < 2) {
            delayInHundredthsOfASecond = 10;
        }
        this.header.currentFrame.delay = delayInHundredthsOfASecond * 10;
        this.header.currentFrame.transIndex = read();
        read();
    }

    private void readBitmap() {
        this.header.currentFrame.f47ix = readShort();
        this.header.currentFrame.f48iy = readShort();
        this.header.currentFrame.f46iw = readShort();
        this.header.currentFrame.f45ih = readShort();
        int packed = read();
        boolean z = false;
        boolean lctFlag = (packed & 128) != 0;
        int lctSize = (int) Math.pow(2.0d, (double) ((packed & 7) + 1));
        GifFrame gifFrame = this.header.currentFrame;
        if ((packed & 64) != 0) {
            z = true;
        }
        gifFrame.interlace = z;
        if (lctFlag) {
            this.header.currentFrame.lct = readColorTable(lctSize);
        } else {
            this.header.currentFrame.lct = null;
        }
        this.header.currentFrame.bufferFrameStart = this.rawData.position();
        skipImageData();
        if (!err()) {
            this.header.frameCount++;
            this.header.frames.add(this.header.currentFrame);
        }
    }

    private void readNetscapeExt() {
        do {
            readBlock();
            byte[] bArr = this.block;
            if (bArr[0] == 1) {
                this.header.loopCount = ((bArr[2] & 255) << 8) | (bArr[1] & 255);
            }
            if (this.blockSize <= 0) {
                return;
            }
        } while (!err());
    }

    private void readHeader() {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            id.append((char) read());
        }
        if (!id.toString().startsWith("GIF")) {
            this.header.status = 1;
            return;
        }
        readLSD();
        if (this.header.gctFlag && !err()) {
            GifHeader gifHeader = this.header;
            gifHeader.gct = readColorTable(gifHeader.gctSize);
            GifHeader gifHeader2 = this.header;
            gifHeader2.bgColor = gifHeader2.gct[this.header.bgIndex];
        }
    }

    private void readLSD() {
        this.header.width = readShort();
        this.header.height = readShort();
        int packed = read();
        this.header.gctFlag = (packed & 128) != 0;
        this.header.gctSize = (int) Math.pow(2.0d, (double) ((packed & 7) + 1));
        this.header.bgIndex = read();
        this.header.pixelAspect = read();
    }

    @Nullable
    private int[] readColorTable(int nColors) {
        int[] tab = null;
        byte[] c = new byte[(nColors * 3)];
        try {
            this.rawData.get(c);
            tab = new int[256];
            int i = 0;
            int r = 0;
            while (i < nColors) {
                int j = r + 1;
                int j2 = j + 1;
                int j3 = j2 + 1;
                int i2 = i + 1;
                tab[i] = -16777216 | ((c[r] & 255) << 16) | ((c[j] & 255) << 8) | (c[j2] & 255);
                r = j3;
                i = i2;
            }
        } catch (BufferUnderflowException e) {
            if (Log.isLoggable(TAG, 3)) {
                Log.d(TAG, "Format Error Reading Color Table", e);
            }
            this.header.status = 1;
        }
        return tab;
    }

    private void skipImageData() {
        read();
        skip();
    }

    private void skip() {
        int blockSize2;
        do {
            blockSize2 = read();
            this.rawData.position(Math.min(this.rawData.position() + blockSize2, this.rawData.limit()));
        } while (blockSize2 > 0);
    }

    private void readBlock() {
        this.blockSize = read();
        int n = 0;
        if (this.blockSize > 0) {
            int count = 0;
            while (n < this.blockSize) {
                try {
                    count = this.blockSize - n;
                    this.rawData.get(this.block, n, count);
                    n += count;
                } catch (Exception e) {
                    if (Log.isLoggable(TAG, 3)) {
                        int i = this.blockSize;
                        StringBuilder sb = new StringBuilder(76);
                        sb.append("Error Reading Block n: ");
                        sb.append(n);
                        sb.append(" count: ");
                        sb.append(count);
                        sb.append(" blockSize: ");
                        sb.append(i);
                        Log.d(TAG, sb.toString(), e);
                    }
                    this.header.status = 1;
                    return;
                }
            }
        }
    }

    private int read() {
        try {
            return this.rawData.get() & 255;
        } catch (Exception e) {
            this.header.status = 1;
            return 0;
        }
    }

    private int readShort() {
        return this.rawData.getShort();
    }

    private boolean err() {
        return this.header.status != 0;
    }
}
