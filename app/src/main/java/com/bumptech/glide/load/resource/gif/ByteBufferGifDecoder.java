package com.bumptech.glide.load.resource.gif;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeader;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.bumptech.glide.gifdecoder.StandardGifDecoder;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ImageHeaderParserUtils;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Util;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Queue;

public class ByteBufferGifDecoder implements ResourceDecoder<ByteBuffer, GifDrawable> {
    private static final GifDecoderFactory GIF_DECODER_FACTORY = new GifDecoderFactory();
    private static final GifHeaderParserPool PARSER_POOL = new GifHeaderParserPool();
    private static final String TAG = "BufferGifDecoder";
    private final Context context;
    private final GifDecoderFactory gifDecoderFactory;
    private final GifHeaderParserPool parserPool;
    private final List<ImageHeaderParser> parsers;
    private final GifBitmapProvider provider;

    public ByteBufferGifDecoder(Context context2) {
        this(context2, Glide.get(context2).getRegistry().getImageHeaderParsers(), Glide.get(context2).getBitmapPool(), Glide.get(context2).getArrayPool());
    }

    public ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> parsers2, BitmapPool bitmapPool, ArrayPool arrayPool) {
        this(context2, parsers2, bitmapPool, arrayPool, PARSER_POOL, GIF_DECODER_FACTORY);
    }

    @VisibleForTesting
    ByteBufferGifDecoder(Context context2, List<ImageHeaderParser> parsers2, BitmapPool bitmapPool, ArrayPool arrayPool, GifHeaderParserPool parserPool2, GifDecoderFactory gifDecoderFactory2) {
        this.context = context2.getApplicationContext();
        this.parsers = parsers2;
        this.gifDecoderFactory = gifDecoderFactory2;
        this.provider = new GifBitmapProvider(bitmapPool, arrayPool);
        this.parserPool = parserPool2;
    }

    private static int getSampleSize(GifHeader gifHeader, int targetWidth, int targetHeight) {
        int exactSampleSize = Math.min(gifHeader.getHeight() / targetHeight, gifHeader.getWidth() / targetWidth);
        int sampleSize = Math.max(1, exactSampleSize == 0 ? 0 : Integer.highestOneBit(exactSampleSize));
        if (Log.isLoggable(TAG, 2) && sampleSize > 1) {
            int width = gifHeader.getWidth();
            int height = gifHeader.getHeight();
            StringBuilder sb = new StringBuilder((int) ClientAnalytics.LogRequest.LogSource.CONTEXT_MANAGER_VALUE);
            sb.append("Downsampling GIF, sampleSize: ");
            sb.append(sampleSize);
            sb.append(", target dimens: [");
            sb.append(targetWidth);
            sb.append("x");
            sb.append(targetHeight);
            sb.append("], actual dimens: [");
            sb.append(width);
            sb.append("x");
            sb.append(height);
            sb.append("]");
            Log.v(TAG, sb.toString());
        }
        return sampleSize;
    }

    public boolean handles(@NonNull ByteBuffer source, @NonNull Options options) throws IOException {
        return !((Boolean) options.get(GifOptions.DISABLE_ANIMATION)).booleanValue() && ImageHeaderParserUtils.getType(this.parsers, source) == ImageHeaderParser.ImageType.GIF;
    }

    public GifDrawableResource decode(@NonNull ByteBuffer source, int width, int height, @NonNull Options options) {
        GifHeaderParser parser = this.parserPool.obtain(source);
        try {
            return decode(source, width, height, parser, options);
        } finally {
            this.parserPool.release(parser);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ef  */
    @android.support.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.bumptech.glide.load.resource.gif.GifDrawableResource decode(java.nio.ByteBuffer r23, int r24, int r25, com.bumptech.glide.gifdecoder.GifHeaderParser r26, com.bumptech.glide.load.Options r27) {
        /*
            r22 = this;
            r1 = r22
            java.lang.String r2 = "Decoded GIF from stream in "
            java.lang.String r3 = "BufferGifDecoder"
            long r4 = com.bumptech.glide.util.LogTime.getLogTime()
            r6 = 51
            r7 = 2
            com.bumptech.glide.gifdecoder.GifHeader r0 = r26.parseHeader()     // Catch:{ all -> 0x00e4 }
            int r8 = r0.getNumFrames()     // Catch:{ all -> 0x00e4 }
            if (r8 <= 0) goto L_0x00bf
            int r8 = r0.getStatus()     // Catch:{ all -> 0x00e4 }
            if (r8 == 0) goto L_0x0023
            r9 = r23
            r10 = r27
            goto L_0x00c3
        L_0x0023:
            com.bumptech.glide.load.Option<com.bumptech.glide.load.DecodeFormat> r8 = com.bumptech.glide.load.resource.gif.GifOptions.DECODE_FORMAT     // Catch:{ all -> 0x00e4 }
            r10 = r27
            java.lang.Object r8 = r10.get(r8)     // Catch:{ all -> 0x00bb }
            com.bumptech.glide.load.DecodeFormat r11 = com.bumptech.glide.load.DecodeFormat.PREFER_RGB_565     // Catch:{ all -> 0x00bb }
            if (r8 != r11) goto L_0x0032
            android.graphics.Bitmap$Config r8 = android.graphics.Bitmap.Config.RGB_565     // Catch:{ all -> 0x00bb }
            goto L_0x0034
        L_0x0032:
            android.graphics.Bitmap$Config r8 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ all -> 0x00bb }
        L_0x0034:
            r15 = r24
            r13 = r25
            int r11 = getSampleSize(r0, r15, r13)     // Catch:{ all -> 0x00bb }
            r12 = r11
            com.bumptech.glide.load.resource.gif.ByteBufferGifDecoder$GifDecoderFactory r11 = r1.gifDecoderFactory     // Catch:{ all -> 0x00bb }
            com.bumptech.glide.load.resource.gif.GifBitmapProvider r14 = r1.provider     // Catch:{ all -> 0x00bb }
            r9 = r23
            com.bumptech.glide.gifdecoder.GifDecoder r11 = r11.build(r14, r0, r9, r12)     // Catch:{ all -> 0x00b9 }
            r11.setDefaultBitmapConfig(r8)     // Catch:{ all -> 0x00b9 }
            r11.advance()     // Catch:{ all -> 0x00b9 }
            android.graphics.Bitmap r14 = r11.getNextFrame()     // Catch:{ all -> 0x00b9 }
            r18 = r14
            if (r18 != 0) goto L_0x0079
            boolean r7 = android.util.Log.isLoggable(r3, r7)
            if (r7 == 0) goto L_0x0075
            r19 = r8
            double r7 = com.bumptech.glide.util.LogTime.getElapsedMillis(r4)
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>(r6)
            r14.append(r2)
            r14.append(r7)
            java.lang.String r2 = r14.toString()
            android.util.Log.v(r3, r2)
            goto L_0x0077
        L_0x0075:
            r19 = r8
        L_0x0077:
            r2 = 0
            return r2
        L_0x0079:
            r19 = r8
            com.bumptech.glide.load.resource.UnitTransformation r14 = com.bumptech.glide.load.resource.UnitTransformation.get()     // Catch:{ all -> 0x00b9 }
            com.bumptech.glide.load.resource.gif.GifDrawable r8 = new com.bumptech.glide.load.resource.gif.GifDrawable     // Catch:{ all -> 0x00b9 }
            android.content.Context r6 = r1.context     // Catch:{ all -> 0x00b9 }
            r20 = r11
            r11 = r8
            r21 = r12
            r12 = r6
            r13 = r20
            r15 = r24
            r16 = r25
            r17 = r18
            r11.<init>(r12, r13, r14, r15, r16, r17)     // Catch:{ all -> 0x00b9 }
            r6 = r8
            com.bumptech.glide.load.resource.gif.GifDrawableResource r8 = new com.bumptech.glide.load.resource.gif.GifDrawableResource     // Catch:{ all -> 0x00b9 }
            r8.<init>(r6)     // Catch:{ all -> 0x00b9 }
            boolean r7 = android.util.Log.isLoggable(r3, r7)
            if (r7 == 0) goto L_0x00b8
            double r11 = com.bumptech.glide.util.LogTime.getElapsedMillis(r4)
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r13 = 51
            r7.<init>(r13)
            r7.append(r2)
            r7.append(r11)
            java.lang.String r2 = r7.toString()
            android.util.Log.v(r3, r2)
        L_0x00b8:
            return r8
        L_0x00b9:
            r0 = move-exception
            goto L_0x00e9
        L_0x00bb:
            r0 = move-exception
            r9 = r23
            goto L_0x00e9
        L_0x00bf:
            r9 = r23
            r10 = r27
        L_0x00c3:
            boolean r6 = android.util.Log.isLoggable(r3, r7)
            if (r6 == 0) goto L_0x00e2
            double r6 = com.bumptech.glide.util.LogTime.getElapsedMillis(r4)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r11 = 51
            r8.<init>(r11)
            r8.append(r2)
            r8.append(r6)
            java.lang.String r2 = r8.toString()
            android.util.Log.v(r3, r2)
        L_0x00e2:
            r2 = 0
            return r2
        L_0x00e4:
            r0 = move-exception
            r9 = r23
            r10 = r27
        L_0x00e9:
            boolean r6 = android.util.Log.isLoggable(r3, r7)
            if (r6 == 0) goto L_0x0107
            double r6 = com.bumptech.glide.util.LogTime.getElapsedMillis(r4)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r11 = 51
            r8.<init>(r11)
            r8.append(r2)
            r8.append(r6)
            java.lang.String r2 = r8.toString()
            android.util.Log.v(r3, r2)
        L_0x0107:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.gif.ByteBufferGifDecoder.decode(java.nio.ByteBuffer, int, int, com.bumptech.glide.gifdecoder.GifHeaderParser, com.bumptech.glide.load.Options):com.bumptech.glide.load.resource.gif.GifDrawableResource");
    }

    @VisibleForTesting
    static class GifDecoderFactory {
        GifDecoderFactory() {
        }

        /* access modifiers changed from: package-private */
        public GifDecoder build(GifDecoder.BitmapProvider provider, GifHeader header, ByteBuffer data, int sampleSize) {
            return new StandardGifDecoder(provider, header, data, sampleSize);
        }
    }

    @VisibleForTesting
    static class GifHeaderParserPool {
        private final Queue<GifHeaderParser> pool = Util.createQueue(0);

        GifHeaderParserPool() {
        }

        /* access modifiers changed from: package-private */
        public synchronized GifHeaderParser obtain(ByteBuffer buffer) {
            GifHeaderParser result;
            result = this.pool.poll();
            if (result == null) {
                result = new GifHeaderParser();
            }
            return result.setData(buffer);
        }

        /* access modifiers changed from: package-private */
        public synchronized void release(GifHeaderParser parser) {
            parser.clear();
            this.pool.offer(parser);
        }
    }
}
