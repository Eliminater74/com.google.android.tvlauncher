package com.bumptech.glide.load.resource.bitmap;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.bumptech.glide.load.Option;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class VideoDecoder<T> implements ResourceDecoder<T, Bitmap> {
    public static final long DEFAULT_FRAME = -1;
    public static final Option<Integer> FRAME_OPTION = Option.disk("com.bumptech.glide.load.resource.bitmap.VideoBitmapDecode.FrameOption", 2, new Option.CacheKeyUpdater<Integer>() {
        private final ByteBuffer buffer = ByteBuffer.allocate(4);

        public void update(@NonNull byte[] keyBytes, @NonNull Integer value, @NonNull MessageDigest messageDigest) {
            if (value != null) {
                messageDigest.update(keyBytes);
                synchronized (this.buffer) {
                    this.buffer.position(0);
                    messageDigest.update(this.buffer.putInt(value.intValue()).array());
                }
            }
        }
    });
    public static final Option<Long> TARGET_FRAME = Option.disk("com.bumptech.glide.load.resource.bitmap.VideoBitmapDecode.TargetFrame", -1L, new Option.CacheKeyUpdater<Long>() {
        private final ByteBuffer buffer = ByteBuffer.allocate(8);

        public void update(@NonNull byte[] keyBytes, @NonNull Long value, @NonNull MessageDigest messageDigest) {
            messageDigest.update(keyBytes);
            synchronized (this.buffer) {
                this.buffer.position(0);
                messageDigest.update(this.buffer.putLong(value.longValue()).array());
            }
        }
    });
    @VisibleForTesting
    static final int DEFAULT_FRAME_OPTION = 2;
    private static final MediaMetadataRetrieverFactory DEFAULT_FACTORY = new MediaMetadataRetrieverFactory();
    private static final String TAG = "VideoDecoder";
    private final BitmapPool bitmapPool;
    private final MediaMetadataRetrieverFactory factory;
    private final MediaMetadataRetrieverInitializer<T> initializer;

    VideoDecoder(BitmapPool bitmapPool2, MediaMetadataRetrieverInitializer<T> initializer2) {
        this(bitmapPool2, initializer2, DEFAULT_FACTORY);
    }

    @VisibleForTesting
    VideoDecoder(BitmapPool bitmapPool2, MediaMetadataRetrieverInitializer<T> initializer2, MediaMetadataRetrieverFactory factory2) {
        this.bitmapPool = bitmapPool2;
        this.initializer = initializer2;
        this.factory = factory2;
    }

    public static ResourceDecoder<AssetFileDescriptor, Bitmap> asset(BitmapPool bitmapPool2) {
        return new VideoDecoder(bitmapPool2, new AssetFileDescriptorInitializer());
    }

    public static ResourceDecoder<ParcelFileDescriptor, Bitmap> parcel(BitmapPool bitmapPool2) {
        return new VideoDecoder(bitmapPool2, new ParcelFileDescriptorInitializer());
    }

    @Nullable
    private static Bitmap decodeFrame(MediaMetadataRetriever mediaMetadataRetriever, long frameTimeMicros, int frameOption, int outWidth, int outHeight, DownsampleStrategy strategy) {
        Bitmap result = null;
        if (!(Build.VERSION.SDK_INT < 27 || outWidth == Integer.MIN_VALUE || outHeight == Integer.MIN_VALUE || strategy == DownsampleStrategy.NONE)) {
            result = decodeScaledFrame(mediaMetadataRetriever, frameTimeMicros, frameOption, outWidth, outHeight, strategy);
        }
        if (result == null) {
            return decodeOriginalFrame(mediaMetadataRetriever, frameTimeMicros, frameOption);
        }
        return result;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0064  */
    /* JADX WARNING: Removed duplicated region for block: B:20:? A[RETURN, SYNTHETIC] */
    @android.annotation.TargetApi(27)
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static android.graphics.Bitmap decodeScaledFrame(android.media.MediaMetadataRetriever r14, long r15, int r17, int r18, int r19, com.bumptech.glide.load.resource.bitmap.DownsampleStrategy r20) {
        /*
            r7 = r14
            r0 = 18
            java.lang.String r0 = r14.extractMetadata(r0)     // Catch:{ all -> 0x0054 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ all -> 0x0054 }
            r1 = 19
            java.lang.String r1 = r14.extractMetadata(r1)     // Catch:{ all -> 0x0054 }
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ all -> 0x0054 }
            r2 = 24
            java.lang.String r2 = r14.extractMetadata(r2)     // Catch:{ all -> 0x0054 }
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ all -> 0x0054 }
            r8 = r2
            r2 = 90
            if (r8 == r2) goto L_0x002b
            r2 = 270(0x10e, float:3.78E-43)
            if (r8 != r2) goto L_0x0029
            goto L_0x002b
        L_0x0029:
            r9 = r1
            goto L_0x002f
        L_0x002b:
            r2 = r0
            r0 = r1
            r1 = r2
            r9 = r1
        L_0x002f:
            r10 = r18
            r11 = r19
            r12 = r20
            float r1 = r12.getScaleFactor(r0, r9, r10, r11)     // Catch:{ all -> 0x0052 }
            r13 = r1
            float r1 = (float) r0     // Catch:{ all -> 0x0052 }
            float r1 = r1 * r13
            int r5 = java.lang.Math.round(r1)     // Catch:{ all -> 0x0052 }
            float r1 = (float) r9     // Catch:{ all -> 0x0052 }
            float r1 = r1 * r13
            int r6 = java.lang.Math.round(r1)     // Catch:{ all -> 0x0052 }
            r1 = r14
            r2 = r15
            r4 = r17
            android.graphics.Bitmap r1 = r1.getScaledFrameAtTime(r2, r4, r5, r6)     // Catch:{ all -> 0x0052 }
            return r1
        L_0x0052:
            r0 = move-exception
            goto L_0x005b
        L_0x0054:
            r0 = move-exception
            r10 = r18
            r11 = r19
            r12 = r20
        L_0x005b:
            r1 = 3
            java.lang.String r2 = "VideoDecoder"
            boolean r1 = android.util.Log.isLoggable(r2, r1)
            if (r1 == 0) goto L_0x0069
            java.lang.String r1 = "Exception trying to decode frame on oreo+"
            android.util.Log.d(r2, r1, r0)
        L_0x0069:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.resource.bitmap.VideoDecoder.decodeScaledFrame(android.media.MediaMetadataRetriever, long, int, int, int, com.bumptech.glide.load.resource.bitmap.DownsampleStrategy):android.graphics.Bitmap");
    }

    private static Bitmap decodeOriginalFrame(MediaMetadataRetriever mediaMetadataRetriever, long frameTimeMicros, int frameOption) {
        return mediaMetadataRetriever.getFrameAtTime(frameTimeMicros, frameOption);
    }

    public boolean handles(@NonNull T t, @NonNull Options options) {
        return true;
    }

    public Resource<Bitmap> decode(@NonNull T resource, int outWidth, int outHeight, @NonNull Options options) throws IOException {
        Integer frameOption;
        DownsampleStrategy downsampleStrategy;
        Options options2 = options;
        long frameTimeMicros = ((Long) options2.get(TARGET_FRAME)).longValue();
        if (frameTimeMicros >= 0 || frameTimeMicros == -1) {
            Integer frameOption2 = (Integer) options2.get(FRAME_OPTION);
            if (frameOption2 == null) {
                frameOption = 2;
            } else {
                frameOption = frameOption2;
            }
            DownsampleStrategy downsampleStrategy2 = (DownsampleStrategy) options2.get(DownsampleStrategy.OPTION);
            if (downsampleStrategy2 == null) {
                downsampleStrategy = DownsampleStrategy.DEFAULT;
            } else {
                downsampleStrategy = downsampleStrategy2;
            }
            MediaMetadataRetriever mediaMetadataRetriever = this.factory.build();
            try {
                try {
                    this.initializer.initialize(mediaMetadataRetriever, resource);
                    Bitmap result = decodeFrame(mediaMetadataRetriever, frameTimeMicros, frameOption.intValue(), outWidth, outHeight, downsampleStrategy);
                    mediaMetadataRetriever.release();
                    return BitmapResource.obtain(result, this.bitmapPool);
                } catch (RuntimeException e) {
                    e = e;
                    try {
                        throw new IOException(e);
                    } catch (Throwable th) {
                        e = th;
                        mediaMetadataRetriever.release();
                        throw e;
                    }
                }
            } catch (RuntimeException e2) {
                e = e2;
                throw new IOException(e);
            } catch (Throwable th2) {
                e = th2;
                mediaMetadataRetriever.release();
                throw e;
            }
        } else {
            StringBuilder sb = new StringBuilder(83);
            sb.append("Requested frame must be non-negative, or DEFAULT_FRAME, given: ");
            sb.append(frameTimeMicros);
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @VisibleForTesting
    interface MediaMetadataRetrieverInitializer<T> {
        void initialize(MediaMetadataRetriever mediaMetadataRetriever, T t);
    }

    @VisibleForTesting
    static class MediaMetadataRetrieverFactory {
        MediaMetadataRetrieverFactory() {
        }

        public MediaMetadataRetriever build() {
            return new MediaMetadataRetriever();
        }
    }

    private static final class AssetFileDescriptorInitializer implements MediaMetadataRetrieverInitializer<AssetFileDescriptor> {
        private AssetFileDescriptorInitializer() {
        }

        public void initialize(MediaMetadataRetriever retriever, AssetFileDescriptor data) {
            retriever.setDataSource(data.getFileDescriptor(), data.getStartOffset(), data.getLength());
        }
    }

    static final class ParcelFileDescriptorInitializer implements MediaMetadataRetrieverInitializer<ParcelFileDescriptor> {
        ParcelFileDescriptorInitializer() {
        }

        public void initialize(MediaMetadataRetriever retriever, ParcelFileDescriptor data) {
            retriever.setDataSource(data.getFileDescriptor());
        }
    }
}
