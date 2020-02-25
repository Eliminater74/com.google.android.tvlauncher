package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bumptech.glide.load.EncodeStrategy;
import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;

import java.io.File;

public class BitmapEncoder implements ResourceEncoder<Bitmap> {
    public static final Option<Bitmap.CompressFormat> COMPRESSION_FORMAT = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionFormat");
    public static final Option<Integer> COMPRESSION_QUALITY = Option.memory("com.bumptech.glide.load.resource.bitmap.BitmapEncoder.CompressionQuality", 90);
    private static final String TAG = "BitmapEncoder";
    @Nullable
    private final ArrayPool arrayPool;

    public BitmapEncoder(@NonNull ArrayPool arrayPool2) {
        this.arrayPool = arrayPool2;
    }

    @Deprecated
    public BitmapEncoder() {
        this.arrayPool = null;
    }

    public /* bridge */ /* synthetic */ boolean encode(@NonNull Object obj, @NonNull File file, @NonNull Options options) {
        return encode((Resource<Bitmap>) ((Resource) obj), file, options);
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0079 A[SYNTHETIC, Splitter:B:31:0x0079] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0089 A[Catch:{ all -> 0x00f5 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00ee  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00fd A[SYNTHETIC, Splitter:B:50:0x00fd] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean encode(@android.support.annotation.NonNull com.bumptech.glide.load.engine.Resource<android.graphics.Bitmap> r19, @android.support.annotation.NonNull java.io.File r20, @android.support.annotation.NonNull com.bumptech.glide.load.Options r21) {
        /*
            r18 = this;
            r1 = r18
            r2 = r21
            java.lang.String r3 = "BitmapEncoder"
            java.lang.Object r0 = r19.get()
            r4 = r0
            android.graphics.Bitmap r4 = (android.graphics.Bitmap) r4
            android.graphics.Bitmap$CompressFormat r5 = r1.getFormat(r4, r2)
            int r0 = r4.getWidth()
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            int r6 = r4.getHeight()
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            java.lang.String r7 = "encode: [%dx%d] %s"
            com.bumptech.glide.util.pool.GlideTrace.beginSectionFormat(r7, r0, r6, r5)
            long r6 = com.bumptech.glide.util.LogTime.getLogTime()     // Catch:{ all -> 0x0106 }
            com.bumptech.glide.load.Option<java.lang.Integer> r0 = com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_QUALITY     // Catch:{ all -> 0x0106 }
            java.lang.Object r0 = r2.get(r0)     // Catch:{ all -> 0x0106 }
            java.lang.Integer r0 = (java.lang.Integer) r0     // Catch:{ all -> 0x0106 }
            int r0 = r0.intValue()     // Catch:{ all -> 0x0106 }
            r8 = r0
            r9 = 0
            r10 = 0
            java.io.FileOutputStream r0 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0068, all -> 0x0060 }
            r11 = r20
            r0.<init>(r11)     // Catch:{ IOException -> 0x005e }
            r10 = r0
            com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool r0 = r1.arrayPool     // Catch:{ IOException -> 0x005e }
            if (r0 == 0) goto L_0x004e
            com.bumptech.glide.load.data.BufferedOutputStream r0 = new com.bumptech.glide.load.data.BufferedOutputStream     // Catch:{ IOException -> 0x005e }
            com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool r12 = r1.arrayPool     // Catch:{ IOException -> 0x005e }
            r0.<init>(r10, r12)     // Catch:{ IOException -> 0x005e }
            r10 = r0
        L_0x004e:
            r4.compress(r5, r8, r10)     // Catch:{ IOException -> 0x005e }
            r10.close()     // Catch:{ IOException -> 0x005e }
            r9 = 1
            r10.close()     // Catch:{ IOException -> 0x005a, all -> 0x007d }
        L_0x0059:
            goto L_0x0082
        L_0x005a:
            r0 = move-exception
            goto L_0x0059
        L_0x005c:
            r0 = move-exception
            goto L_0x0063
        L_0x005e:
            r0 = move-exception
            goto L_0x006b
        L_0x0060:
            r0 = move-exception
            r11 = r20
        L_0x0063:
            r1 = r0
            r16 = r4
            goto L_0x00fb
        L_0x0068:
            r0 = move-exception
            r11 = r20
        L_0x006b:
            r12 = 3
            boolean r12 = android.util.Log.isLoggable(r3, r12)     // Catch:{ all -> 0x00f7 }
            if (r12 == 0) goto L_0x0077
            java.lang.String r12 = "Failed to encode Bitmap"
            android.util.Log.d(r3, r12, r0)     // Catch:{ all -> 0x005c }
        L_0x0077:
            if (r10 == 0) goto L_0x0082
            r10.close()     // Catch:{ IOException -> 0x005a, all -> 0x007d }
            goto L_0x0059
        L_0x007d:
            r0 = move-exception
            r16 = r4
            goto L_0x010b
        L_0x0082:
            r0 = 2
            boolean r0 = android.util.Log.isLoggable(r3, r0)     // Catch:{ all -> 0x00f5 }
            if (r0 == 0) goto L_0x00ee
            java.lang.String r0 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x00f5 }
            int r12 = com.bumptech.glide.util.Util.getBitmapByteSize(r4)     // Catch:{ all -> 0x00f5 }
            double r13 = com.bumptech.glide.util.LogTime.getElapsedMillis(r6)     // Catch:{ all -> 0x00f5 }
            com.bumptech.glide.load.Option<android.graphics.Bitmap$CompressFormat> r15 = com.bumptech.glide.load.resource.bitmap.BitmapEncoder.COMPRESSION_FORMAT     // Catch:{ all -> 0x00f5 }
            java.lang.Object r15 = r2.get(r15)     // Catch:{ all -> 0x00f5 }
            java.lang.String r15 = java.lang.String.valueOf(r15)     // Catch:{ all -> 0x00f5 }
            boolean r1 = r4.hasAlpha()     // Catch:{ all -> 0x00f5 }
            java.lang.String r16 = java.lang.String.valueOf(r0)     // Catch:{ all -> 0x00f5 }
            int r16 = r16.length()     // Catch:{ all -> 0x00f5 }
            int r16 = r16 + 105
            java.lang.String r17 = java.lang.String.valueOf(r15)     // Catch:{ all -> 0x00f5 }
            int r17 = r17.length()     // Catch:{ all -> 0x00f5 }
            int r2 = r16 + r17
            r16 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0104 }
            r4.<init>(r2)     // Catch:{ all -> 0x0104 }
            java.lang.String r2 = "Compressed with type: "
            r4.append(r2)     // Catch:{ all -> 0x0104 }
            r4.append(r0)     // Catch:{ all -> 0x0104 }
            java.lang.String r0 = " of size "
            r4.append(r0)     // Catch:{ all -> 0x0104 }
            r4.append(r12)     // Catch:{ all -> 0x0104 }
            java.lang.String r0 = " in "
            r4.append(r0)     // Catch:{ all -> 0x0104 }
            r4.append(r13)     // Catch:{ all -> 0x0104 }
            java.lang.String r0 = ", options format: "
            r4.append(r0)     // Catch:{ all -> 0x0104 }
            r4.append(r15)     // Catch:{ all -> 0x0104 }
            java.lang.String r0 = ", hasAlpha: "
            r4.append(r0)     // Catch:{ all -> 0x0104 }
            r4.append(r1)     // Catch:{ all -> 0x0104 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0104 }
            android.util.Log.v(r3, r0)     // Catch:{ all -> 0x0104 }
            goto L_0x00f0
        L_0x00ee:
            r16 = r4
        L_0x00f0:
            com.bumptech.glide.util.pool.GlideTrace.endSection()
            return r9
        L_0x00f5:
            r0 = move-exception
            goto L_0x0109
        L_0x00f7:
            r0 = move-exception
            r16 = r4
            r1 = r0
        L_0x00fb:
            if (r10 == 0) goto L_0x0102
            r10.close()     // Catch:{ IOException -> 0x0101 }
            goto L_0x0102
        L_0x0101:
            r0 = move-exception
        L_0x0102:
            throw r1     // Catch:{ all -> 0x0104 }
        L_0x0104:
            r0 = move-exception
            goto L_0x010b
        L_0x0106:
            r0 = move-exception
            r11 = r20
        L_0x0109:
            r16 = r4
        L_0x010b:
            com.bumptech.glide.util.pool.GlideTrace.endSection()
            throw r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.BitmapEncoder.encode(com.bumptech.glide.load.engine.Resource, java.io.File, com.bumptech.glide.load.Options):boolean");
    }

    private Bitmap.CompressFormat getFormat(Bitmap bitmap, Options options) {
        Bitmap.CompressFormat format = (Bitmap.CompressFormat) options.get(COMPRESSION_FORMAT);
        if (format != null) {
            return format;
        }
        if (bitmap.hasAlpha()) {
            return Bitmap.CompressFormat.PNG;
        }
        return Bitmap.CompressFormat.JPEG;
    }

    @NonNull
    public EncodeStrategy getEncodeStrategy(@NonNull Options options) {
        return EncodeStrategy.TRANSFORMED;
    }
}
