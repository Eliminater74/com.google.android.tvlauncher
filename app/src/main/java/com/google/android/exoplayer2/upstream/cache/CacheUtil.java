package com.google.android.exoplayer2.upstream.cache;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.PriorityTaskManager;
import com.google.android.exoplayer2.util.Util;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class CacheUtil {
    public static final int DEFAULT_BUFFER_SIZE_BYTES = 131072;
    public static final CacheKeyFactory DEFAULT_CACHE_KEY_FACTORY = CacheUtil$$Lambda$0.$instance;

    private CacheUtil() {
    }

    static final /* synthetic */ String lambda$static$0$CacheUtil(DataSpec dataSpec) {
        return dataSpec.key != null ? dataSpec.key : generateKey(dataSpec.uri);
    }

    public static String generateKey(Uri uri) {
        return uri.toString();
    }

    public static Pair<Long, Long> getCached(DataSpec dataSpec, Cache cache, @Nullable CacheKeyFactory cacheKeyFactory) {
        long requestLength;
        DataSpec dataSpec2 = dataSpec;
        String key = buildCacheKey(dataSpec2, cacheKeyFactory);
        long position = dataSpec2.absoluteStreamPosition;
        if (dataSpec2.length != -1) {
            requestLength = dataSpec2.length;
        } else {
            long contentLength = ContentMetadata$$CC.getContentLength$$STATIC$$(cache.getContentMetadata(key));
            requestLength = contentLength == -1 ? -1 : contentLength - position;
        }
        long position2 = position;
        long bytesAlreadyCached = 0;
        long bytesLeft = requestLength;
        while (true) {
            long j = 0;
            if (bytesLeft == 0) {
                break;
            }
            long blockLength = cache.getCachedLength(key, position2, bytesLeft != -1 ? bytesLeft : Long.MAX_VALUE);
            if (blockLength <= 0) {
                blockLength = -blockLength;
                if (blockLength == Long.MAX_VALUE) {
                    break;
                }
            } else {
                bytesAlreadyCached += blockLength;
            }
            position2 += blockLength;
            if (bytesLeft != -1) {
                j = blockLength;
            }
            bytesLeft -= j;
        }
        return Pair.create(Long.valueOf(requestLength), Long.valueOf(bytesAlreadyCached));
    }

    public static void cache(DataSpec dataSpec, Cache cache, @Nullable CacheKeyFactory cacheKeyFactory, DataSource upstream, @Nullable ProgressListener progressListener, @Nullable AtomicBoolean isCanceled) throws IOException, InterruptedException {
        cache(dataSpec, cache, cacheKeyFactory, new CacheDataSource(cache, upstream), new byte[131072], null, 0, progressListener, isCanceled, false);
    }

    public static void cache(DataSpec dataSpec, Cache cache, @Nullable CacheKeyFactory cacheKeyFactory, CacheDataSource dataSource, byte[] buffer, PriorityTaskManager priorityTaskManager, int priority, @Nullable ProgressListener progressListener, @Nullable AtomicBoolean isCanceled, boolean enableEOFException) throws IOException, InterruptedException {
        ProgressNotifier progressNotifier;
        long position;
        long bytesLeft;
        long blockLength;
        DataSpec dataSpec2 = dataSpec;
        ProgressListener progressListener2 = progressListener;
        Assertions.checkNotNull(dataSource);
        Assertions.checkNotNull(buffer);
        if (progressListener2 != null) {
            ProgressNotifier progressNotifier2 = new ProgressNotifier(progressListener2);
            Pair<Long, Long> lengthAndBytesAlreadyCached = getCached(dataSpec, cache, cacheKeyFactory);
            progressNotifier2.init(((Long) lengthAndBytesAlreadyCached.first).longValue(), ((Long) lengthAndBytesAlreadyCached.second).longValue());
            progressNotifier = progressNotifier2;
        } else {
            progressNotifier = null;
        }
        String key = buildCacheKey(dataSpec2, cacheKeyFactory);
        long position2 = dataSpec2.absoluteStreamPosition;
        if (dataSpec2.length != -1) {
            position = position2;
            bytesLeft = dataSpec2.length;
        } else {
            long contentLength = ContentMetadata$$CC.getContentLength$$STATIC$$(cache.getContentMetadata(key));
            position = position2;
            bytesLeft = contentLength == -1 ? -1 : contentLength - position2;
        }
        while (true) {
            long j = 0;
            if (bytesLeft != 0) {
                throwExceptionIfInterruptedOrCancelled(isCanceled);
                long blockLength2 = cache.getCachedLength(key, position, bytesLeft != -1 ? bytesLeft : Long.MAX_VALUE);
                if (blockLength2 > 0) {
                    blockLength = blockLength2;
                } else {
                    long blockLength3 = -blockLength2;
                    blockLength = blockLength3;
                    if (readAndDiscard(dataSpec, position, blockLength3, dataSource, buffer, priorityTaskManager, priority, progressNotifier, isCanceled) < blockLength) {
                        if (enableEOFException && bytesLeft != -1) {
                            throw new EOFException();
                        }
                        return;
                    }
                }
                position += blockLength;
                if (bytesLeft != -1) {
                    j = blockLength;
                }
                bytesLeft -= j;
            } else {
                return;
            }
        }
    }

    private static long readAndDiscard(DataSpec dataSpec, long absoluteStreamPosition, long length, DataSource dataSource, byte[] buffer, PriorityTaskManager priorityTaskManager, int priority, @Nullable ProgressNotifier progressNotifier, AtomicBoolean isCanceled) throws IOException, InterruptedException {
        long resolvedLength;
        long j;
        int i;
        DataSource dataSource2 = dataSource;
        byte[] bArr = buffer;
        ProgressNotifier progressNotifier2 = progressNotifier;
        DataSpec dataSpec2 = dataSpec;
        long positionOffset = absoluteStreamPosition - dataSpec2.absoluteStreamPosition;
        DataSpec dataSpec3 = dataSpec2;
        while (true) {
            if (priorityTaskManager != null) {
                priorityTaskManager.proceed(priority);
            }
            try {
                throwExceptionIfInterruptedOrCancelled(isCanceled);
                try {
                    dataSpec3 = new DataSpec(dataSpec3.uri, dataSpec3.httpMethod, dataSpec3.httpBody, absoluteStreamPosition, dataSpec3.position + positionOffset, -1, dataSpec3.key, dataSpec3.flags);
                    try {
                        resolvedLength = dataSource2.open(dataSpec3);
                        j = -1;
                        break;
                    } catch (PriorityTaskManager.PriorityTooLowException e) {
                    } catch (Throwable th) {
                        th = th;
                        Util.closeQuietly(dataSource);
                        throw th;
                    }
                } catch (PriorityTaskManager.PriorityTooLowException e2) {
                    dataSpec3 = dataSpec3;
                } catch (Throwable th2) {
                    th = th2;
                    Util.closeQuietly(dataSource);
                    throw th;
                }
            } catch (PriorityTaskManager.PriorityTooLowException e3) {
            } catch (Throwable th3) {
                th = th3;
                Util.closeQuietly(dataSource);
                throw th;
            }
            Util.closeQuietly(dataSource);
        }
        if (!(progressNotifier2 == null || resolvedLength == -1)) {
            progressNotifier2.onRequestLengthResolved(positionOffset + resolvedLength);
        }
        long totalBytesRead = 0;
        while (true) {
            if (totalBytesRead == length) {
                break;
            }
            throwExceptionIfInterruptedOrCancelled(isCanceled);
            if (length != j) {
                i = (int) Math.min((long) bArr.length, length - totalBytesRead);
            } else {
                i = bArr.length;
            }
            int bytesRead = dataSource2.read(bArr, 0, i);
            if (bytesRead != -1) {
                totalBytesRead += (long) bytesRead;
                if (progressNotifier2 != null) {
                    progressNotifier2.onBytesCached((long) bytesRead);
                }
                j = -1;
            } else if (progressNotifier2 != null) {
                progressNotifier2.onRequestLengthResolved(positionOffset + totalBytesRead);
            }
        }
        Util.closeQuietly(dataSource);
        return totalBytesRead;
    }

    public static void remove(DataSpec dataSpec, Cache cache, @Nullable CacheKeyFactory cacheKeyFactory) {
        remove(cache, buildCacheKey(dataSpec, cacheKeyFactory));
    }

    public static void remove(Cache cache, String key) {
        for (CacheSpan cachedSpan : cache.getCachedSpans(key)) {
            try {
                cache.removeSpan(cachedSpan);
            } catch (Cache.CacheException e) {
            }
        }
    }

    private static String buildCacheKey(DataSpec dataSpec, @Nullable CacheKeyFactory cacheKeyFactory) {
        return (cacheKeyFactory != null ? cacheKeyFactory : DEFAULT_CACHE_KEY_FACTORY).buildCacheKey(dataSpec);
    }

    private static void throwExceptionIfInterruptedOrCancelled(AtomicBoolean isCanceled) throws InterruptedException {
        if (Thread.interrupted() || (isCanceled != null && isCanceled.get())) {
            throw new InterruptedException();
        }
    }

    public interface ProgressListener {
        void onProgress(long j, long j2, long j3);
    }

    private static final class ProgressNotifier {
        private final ProgressListener listener;
        private long bytesCached;
        private long requestLength;

        public ProgressNotifier(ProgressListener listener2) {
            this.listener = listener2;
        }

        public void init(long requestLength2, long bytesCached2) {
            this.requestLength = requestLength2;
            this.bytesCached = bytesCached2;
            this.listener.onProgress(requestLength2, bytesCached2, 0);
        }

        public void onRequestLengthResolved(long requestLength2) {
            if (this.requestLength == -1 && requestLength2 != -1) {
                this.requestLength = requestLength2;
                this.listener.onProgress(requestLength2, this.bytesCached, 0);
            }
        }

        public void onBytesCached(long newBytesCached) {
            this.bytesCached += newBytesCached;
            this.listener.onProgress(this.requestLength, this.bytesCached, newBytesCached);
        }
    }
}
