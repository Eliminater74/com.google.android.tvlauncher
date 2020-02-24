package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LruBitmapPool implements BitmapPool {
    private static final Bitmap.Config DEFAULT_CONFIG = Bitmap.Config.ARGB_8888;
    private static final String TAG = "LruBitmapPool";
    private final Set<Bitmap.Config> allowedConfigs;
    private long currentSize;
    private int evictions;
    private int hits;
    private final long initialMaxSize;
    private long maxSize;
    private int misses;
    private int puts;
    private final LruPoolStrategy strategy;
    private final BitmapTracker tracker;

    private interface BitmapTracker {
        void add(Bitmap bitmap);

        void remove(Bitmap bitmap);
    }

    LruBitmapPool(long maxSize2, LruPoolStrategy strategy2, Set<Bitmap.Config> allowedConfigs2) {
        this.initialMaxSize = maxSize2;
        this.maxSize = maxSize2;
        this.strategy = strategy2;
        this.allowedConfigs = allowedConfigs2;
        this.tracker = new NullBitmapTracker();
    }

    public LruBitmapPool(long maxSize2) {
        this(maxSize2, getDefaultStrategy(), getDefaultAllowedConfigs());
    }

    public LruBitmapPool(long maxSize2, Set<Bitmap.Config> allowedConfigs2) {
        this(maxSize2, getDefaultStrategy(), allowedConfigs2);
    }

    public long getMaxSize() {
        return this.maxSize;
    }

    public synchronized void setSizeMultiplier(float sizeMultiplier) {
        this.maxSize = (long) Math.round(((float) this.initialMaxSize) * sizeMultiplier);
        evict();
    }

    public synchronized void put(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    if (bitmap.isMutable() && ((long) this.strategy.getSize(bitmap)) <= this.maxSize) {
                        if (this.allowedConfigs.contains(bitmap.getConfig())) {
                            int size = this.strategy.getSize(bitmap);
                            this.strategy.put(bitmap);
                            this.tracker.add(bitmap);
                            this.puts++;
                            this.currentSize += (long) size;
                            if (Log.isLoggable(TAG, 2)) {
                                String valueOf = String.valueOf(this.strategy.logBitmap(bitmap));
                                Log.v(TAG, valueOf.length() != 0 ? "Put bitmap in pool=".concat(valueOf) : new String("Put bitmap in pool="));
                            }
                            dump();
                            evict();
                            return;
                        }
                    }
                    if (Log.isLoggable(TAG, 2)) {
                        String logBitmap = this.strategy.logBitmap(bitmap);
                        boolean isMutable = bitmap.isMutable();
                        boolean contains = this.allowedConfigs.contains(bitmap.getConfig());
                        StringBuilder sb = new StringBuilder(String.valueOf(logBitmap).length() + 78);
                        sb.append("Reject bitmap from pool, bitmap: ");
                        sb.append(logBitmap);
                        sb.append(", is mutable: ");
                        sb.append(isMutable);
                        sb.append(", is allowed config: ");
                        sb.append(contains);
                        Log.v(TAG, sb.toString());
                    }
                    bitmap.recycle();
                    return;
                }
                throw new IllegalStateException("Cannot pool recycled bitmap");
            } catch (Throwable th) {
                throw th;
            }
        } else {
            throw new NullPointerException("Bitmap must not be null");
        }
    }

    private void evict() {
        trimToSize(this.maxSize);
    }

    @NonNull
    public Bitmap get(int width, int height, Bitmap.Config config) {
        Bitmap result = getDirtyOrNull(width, height, config);
        if (result == null) {
            return createBitmap(width, height, config);
        }
        result.eraseColor(0);
        return result;
    }

    @NonNull
    public Bitmap getDirty(int width, int height, Bitmap.Config config) {
        Bitmap result = getDirtyOrNull(width, height, config);
        if (result == null) {
            return createBitmap(width, height, config);
        }
        return result;
    }

    @NonNull
    private static Bitmap createBitmap(int width, int height, @Nullable Bitmap.Config config) {
        return Bitmap.createBitmap(width, height, config != null ? config : DEFAULT_CONFIG);
    }

    @TargetApi(26)
    private static void assertNotHardwareConfig(Bitmap.Config config) {
        if (Build.VERSION.SDK_INT >= 26 && config == Bitmap.Config.HARDWARE) {
            String valueOf = String.valueOf(config);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + ClientAnalytics.LogRequest.LogSource.ANDROID_DIALER_VALUE);
            sb.append("Cannot create a mutable Bitmap with config: ");
            sb.append(valueOf);
            sb.append(". Consider setting Downsampler#ALLOW_HARDWARE_CONFIG to false in your RequestOptions and/or in GlideBuilder.setDefaultRequestOptions");
            throw new IllegalArgumentException(sb.toString());
        }
    }

    @Nullable
    private synchronized Bitmap getDirtyOrNull(int width, int height, @Nullable Bitmap.Config config) {
        Bitmap result;
        assertNotHardwareConfig(config);
        result = this.strategy.get(width, height, config != null ? config : DEFAULT_CONFIG);
        if (result == null) {
            if (Log.isLoggable(TAG, 3)) {
                String valueOf = String.valueOf(this.strategy.logBitmap(width, height, config));
                Log.d(TAG, valueOf.length() != 0 ? "Missing bitmap=".concat(valueOf) : new String("Missing bitmap="));
            }
            this.misses++;
        } else {
            this.hits++;
            this.currentSize -= (long) this.strategy.getSize(result);
            this.tracker.remove(result);
            normalize(result);
        }
        if (Log.isLoggable(TAG, 2)) {
            String valueOf2 = String.valueOf(this.strategy.logBitmap(width, height, config));
            Log.v(TAG, valueOf2.length() != 0 ? "Get bitmap=".concat(valueOf2) : new String("Get bitmap="));
        }
        dump();
        return result;
    }

    private static void normalize(Bitmap bitmap) {
        bitmap.setHasAlpha(true);
        maybeSetPreMultiplied(bitmap);
    }

    @TargetApi(19)
    private static void maybeSetPreMultiplied(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 19) {
            bitmap.setPremultiplied(true);
        }
    }

    public void clearMemory() {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, "clearMemory");
        }
        trimToSize(0);
    }

    @SuppressLint({"InlinedApi"})
    public void trimMemory(int level) {
        if (Log.isLoggable(TAG, 3)) {
            StringBuilder sb = new StringBuilder(29);
            sb.append("trimMemory, level=");
            sb.append(level);
            Log.d(TAG, sb.toString());
        }
        if (level >= 40) {
            clearMemory();
        } else if (level >= 20 || level == 15) {
            trimToSize(getMaxSize() / 2);
        }
    }

    private synchronized void trimToSize(long size) {
        while (this.currentSize > size) {
            Bitmap removed = this.strategy.removeLast();
            if (removed == null) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "Size mismatch, resetting");
                    dumpUnchecked();
                }
                this.currentSize = 0;
                return;
            }
            this.tracker.remove(removed);
            this.currentSize -= (long) this.strategy.getSize(removed);
            this.evictions++;
            if (Log.isLoggable(TAG, 3)) {
                String valueOf = String.valueOf(this.strategy.logBitmap(removed));
                Log.d(TAG, valueOf.length() != 0 ? "Evicting bitmap=".concat(valueOf) : new String("Evicting bitmap="));
            }
            dump();
            removed.recycle();
        }
    }

    private void dump() {
        if (Log.isLoggable(TAG, 2)) {
            dumpUnchecked();
        }
    }

    private void dumpUnchecked() {
        int i = this.hits;
        int i2 = this.misses;
        int i3 = this.puts;
        int i4 = this.evictions;
        long j = this.currentSize;
        long j2 = this.maxSize;
        String valueOf = String.valueOf(this.strategy);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 151);
        sb.append("Hits=");
        sb.append(i);
        sb.append(", misses=");
        sb.append(i2);
        sb.append(", puts=");
        sb.append(i3);
        sb.append(", evictions=");
        sb.append(i4);
        sb.append(", currentSize=");
        sb.append(j);
        sb.append(", maxSize=");
        sb.append(j2);
        sb.append("\nStrategy=");
        sb.append(valueOf);
        Log.v(TAG, sb.toString());
    }

    private static LruPoolStrategy getDefaultStrategy() {
        if (Build.VERSION.SDK_INT >= 19) {
            return new SizeConfigStrategy();
        }
        return new AttributeStrategy();
    }

    @TargetApi(26)
    private static Set<Bitmap.Config> getDefaultAllowedConfigs() {
        Set<Bitmap.Config> configs = new HashSet<>(Arrays.asList(Bitmap.Config.values()));
        if (Build.VERSION.SDK_INT >= 19) {
            configs.add(null);
        }
        if (Build.VERSION.SDK_INT >= 26) {
            configs.remove(Bitmap.Config.HARDWARE);
        }
        return Collections.unmodifiableSet(configs);
    }

    private static class ThrowingBitmapTracker implements BitmapTracker {
        private final Set<Bitmap> bitmaps = Collections.synchronizedSet(new HashSet());

        private ThrowingBitmapTracker() {
        }

        public void add(Bitmap bitmap) {
            if (!this.bitmaps.contains(bitmap)) {
                this.bitmaps.add(bitmap);
                return;
            }
            String valueOf = String.valueOf(bitmap);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 58);
            sb.append("Can't add already added bitmap: ");
            sb.append(valueOf);
            sb.append(" [");
            sb.append(width);
            sb.append("x");
            sb.append(height);
            sb.append("]");
            throw new IllegalStateException(sb.toString());
        }

        public void remove(Bitmap bitmap) {
            if (this.bitmaps.contains(bitmap)) {
                this.bitmaps.remove(bitmap);
                return;
            }
            throw new IllegalStateException("Cannot remove bitmap not in tracker");
        }
    }

    private static final class NullBitmapTracker implements BitmapTracker {
        NullBitmapTracker() {
        }

        public void add(Bitmap bitmap) {
        }

        public void remove(Bitmap bitmap) {
        }
    }
}
