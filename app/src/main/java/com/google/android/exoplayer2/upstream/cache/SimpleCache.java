package com.google.android.exoplayer2.upstream.cache;

import android.os.ConditionVariable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.database.DatabaseIOException;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public final class SimpleCache implements Cache {
    private static final int SUBDIRECTORY_COUNT = 10;
    private static final String TAG = "SimpleCache";
    private static final String UID_FILE_SUFFIX = ".uid";
    private static final HashSet<File> lockedCacheDirs = new HashSet<>();
    private static boolean cacheFolderLockingDisabled;
    private static boolean cacheInitializationExceptionsDisabled;
    /* access modifiers changed from: private */
    public final CacheEvictor evictor;
    private final File cacheDir;
    private final CachedContentIndex contentIndex;
    @Nullable
    private final CacheFileMetadataIndex fileIndex;
    private final HashMap<String, ArrayList<Cache.Listener>> listeners;
    private final Random random;
    private final boolean touchCacheSpans;
    private Cache.CacheException initializationException;
    private boolean released;
    private long totalSpace;
    private long uid;

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.upstream.cache.SimpleCache.<init>(java.io.File, com.google.android.exoplayer2.upstream.cache.CacheEvictor, byte[], boolean):void
     arg types: [java.io.File, com.google.android.exoplayer2.upstream.cache.CacheEvictor, ?[OBJECT, ARRAY], int]
     candidates:
      com.google.android.exoplayer2.upstream.cache.SimpleCache.<init>(java.io.File, com.google.android.exoplayer2.upstream.cache.CacheEvictor, com.google.android.exoplayer2.upstream.cache.CachedContentIndex, com.google.android.exoplayer2.upstream.cache.CacheFileMetadataIndex):void
      com.google.android.exoplayer2.upstream.cache.SimpleCache.<init>(java.io.File, com.google.android.exoplayer2.upstream.cache.CacheEvictor, byte[], boolean):void */
    @Deprecated
    public SimpleCache(File cacheDir2, CacheEvictor evictor2) {
        this(cacheDir2, evictor2, (byte[]) null, false);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    @Deprecated
    public SimpleCache(File cacheDir2, CacheEvictor evictor2, @Nullable byte[] secretKey) {
        this(cacheDir2, evictor2, secretKey, secretKey != null);
    }

    @Deprecated
    public SimpleCache(File cacheDir2, CacheEvictor evictor2, @Nullable byte[] secretKey, boolean encrypt) {
        this(cacheDir2, evictor2, null, secretKey, encrypt, true);
    }

    public SimpleCache(File cacheDir2, CacheEvictor evictor2, DatabaseProvider databaseProvider) {
        this(cacheDir2, evictor2, databaseProvider, null, false, false);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SimpleCache(java.io.File r8, com.google.android.exoplayer2.upstream.cache.CacheEvictor r9, @android.support.annotation.Nullable com.google.android.exoplayer2.database.DatabaseProvider r10, @android.support.annotation.Nullable byte[] r11, boolean r12, boolean r13) {
        /*
            r7 = this;
            com.google.android.exoplayer2.upstream.cache.CachedContentIndex r6 = new com.google.android.exoplayer2.upstream.cache.CachedContentIndex
            r0 = r6
            r1 = r10
            r2 = r8
            r3 = r11
            r4 = r12
            r5 = r13
            r0.<init>(r1, r2, r3, r4, r5)
            if (r10 == 0) goto L_0x0015
            if (r13 != 0) goto L_0x0015
            com.google.android.exoplayer2.upstream.cache.CacheFileMetadataIndex r0 = new com.google.android.exoplayer2.upstream.cache.CacheFileMetadataIndex
            r0.<init>(r10)
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            r7.<init>(r8, r9, r6, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.<init>(java.io.File, com.google.android.exoplayer2.upstream.cache.CacheEvictor, com.google.android.exoplayer2.database.DatabaseProvider, byte[], boolean, boolean):void");
    }

    SimpleCache(File cacheDir2, CacheEvictor evictor2, CachedContentIndex contentIndex2, @Nullable CacheFileMetadataIndex fileIndex2) {
        if (lockFolder(cacheDir2)) {
            this.cacheDir = cacheDir2;
            this.evictor = evictor2;
            this.contentIndex = contentIndex2;
            this.fileIndex = fileIndex2;
            this.listeners = new HashMap<>();
            this.random = new Random();
            this.touchCacheSpans = evictor2.requiresCacheSpanTouches();
            this.uid = -1;
            final ConditionVariable conditionVariable = new ConditionVariable();
            new Thread("SimpleCache.initialize()") {
                public void run() {
                    synchronized (SimpleCache.this) {
                        conditionVariable.open();
                        SimpleCache.this.initialize();
                        SimpleCache.this.evictor.onCacheInitialized();
                    }
                }
            }.start();
            conditionVariable.block();
            return;
        }
        String valueOf = String.valueOf(cacheDir2);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 46);
        sb.append("Another SimpleCache instance uses the folder: ");
        sb.append(valueOf);
        throw new IllegalStateException(sb.toString());
    }

    public static synchronized boolean isCacheFolderLocked(File cacheFolder) {
        boolean contains;
        synchronized (SimpleCache.class) {
            contains = lockedCacheDirs.contains(cacheFolder.getAbsoluteFile());
        }
        return contains;
    }

    @Deprecated
    public static synchronized void disableCacheFolderLocking() {
        synchronized (SimpleCache.class) {
            cacheFolderLockingDisabled = true;
            lockedCacheDirs.clear();
        }
    }

    @Deprecated
    public static void disableCacheInitializationExceptions() {
        cacheInitializationExceptionsDisabled = true;
    }

    public static void delete(File cacheDir2, @Nullable DatabaseProvider databaseProvider) {
        if (cacheDir2.exists()) {
            File[] files = cacheDir2.listFiles();
            if (files == null) {
                cacheDir2.delete();
                return;
            }
            if (databaseProvider != null) {
                long uid2 = loadUid(files);
                if (uid2 != -1) {
                    try {
                        CacheFileMetadataIndex.delete(databaseProvider, uid2);
                    } catch (DatabaseIOException e) {
                        StringBuilder sb = new StringBuilder(52);
                        sb.append("Failed to delete file metadata: ");
                        sb.append(uid2);
                        Log.m30w(TAG, sb.toString());
                    }
                    try {
                        CachedContentIndex.delete(databaseProvider, uid2);
                    } catch (DatabaseIOException e2) {
                        StringBuilder sb2 = new StringBuilder(52);
                        sb2.append("Failed to delete file metadata: ");
                        sb2.append(uid2);
                        Log.m30w(TAG, sb2.toString());
                    }
                }
            }
            Util.recursiveDelete(cacheDir2);
        }
    }

    private static long loadUid(File[] files) {
        int length = files.length;
        int i = 0;
        while (i < length) {
            File file = files[i];
            String fileName = file.getName();
            if (fileName.endsWith(UID_FILE_SUFFIX)) {
                try {
                    return parseUid(fileName);
                } catch (NumberFormatException e) {
                    String valueOf = String.valueOf(file);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
                    sb.append("Malformed UID file: ");
                    sb.append(valueOf);
                    Log.m26e(TAG, sb.toString());
                    file.delete();
                }
            } else {
                i++;
            }
        }
        return -1;
    }

    private static long createUid(File directory) throws IOException {
        long uid2 = new SecureRandom().nextLong();
        long uid3 = uid2 == Long.MIN_VALUE ? 0 : Math.abs(uid2);
        String valueOf = String.valueOf(Long.toString(uid3, 16));
        String valueOf2 = String.valueOf(UID_FILE_SUFFIX);
        File hexUidFile = new File(directory, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf));
        if (hexUidFile.createNewFile()) {
            return uid3;
        }
        String valueOf3 = String.valueOf(hexUidFile);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf3).length() + 27);
        sb.append("Failed to create UID file: ");
        sb.append(valueOf3);
        throw new IOException(sb.toString());
    }

    private static long parseUid(String fileName) {
        return Long.parseLong(fileName.substring(0, fileName.indexOf(46)), 16);
    }

    private static synchronized boolean lockFolder(File cacheDir2) {
        synchronized (SimpleCache.class) {
            if (cacheFolderLockingDisabled) {
                return true;
            }
            boolean add = lockedCacheDirs.add(cacheDir2.getAbsoluteFile());
            return add;
        }
    }

    private static synchronized void unlockFolder(File cacheDir2) {
        synchronized (SimpleCache.class) {
            if (!cacheFolderLockingDisabled) {
                lockedCacheDirs.remove(cacheDir2.getAbsoluteFile());
            }
        }
    }

    public synchronized void checkInitialization() throws Cache.CacheException {
        if (!cacheInitializationExceptionsDisabled) {
            if (this.initializationException != null) {
                throw this.initializationException;
            }
        }
    }

    public synchronized long getUid() {
        return this.uid;
    }

    public synchronized void release() {
        if (!this.released) {
            this.listeners.clear();
            removeStaleSpans();
            try {
                this.contentIndex.store();
                unlockFolder(this.cacheDir);
                this.released = true;
            } catch (IOException e) {
                try {
                    Log.m27e(TAG, "Storing index file failed", e);
                } finally {
                    unlockFolder(this.cacheDir);
                    this.released = true;
                }
            }
        }
    }

    public synchronized NavigableSet<CacheSpan> addListener(String key, Cache.Listener listener) {
        Assertions.checkState(!this.released);
        ArrayList<Cache.Listener> listenersForKey = this.listeners.get(key);
        if (listenersForKey == null) {
            listenersForKey = new ArrayList<>();
            this.listeners.put(key, listenersForKey);
        }
        listenersForKey.add(listener);
        return getCachedSpans(key);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0020, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void removeListener(java.lang.String r3, com.google.android.exoplayer2.upstream.cache.Cache.Listener r4) {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.released     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x0007
            monitor-exit(r2)
            return
        L_0x0007:
            java.util.HashMap<java.lang.String, java.util.ArrayList<com.google.android.exoplayer2.upstream.cache.Cache$Listener>> r0 = r2.listeners     // Catch:{ all -> 0x0021 }
            java.lang.Object r0 = r0.get(r3)     // Catch:{ all -> 0x0021 }
            java.util.ArrayList r0 = (java.util.ArrayList) r0     // Catch:{ all -> 0x0021 }
            if (r0 == 0) goto L_0x001f
            r0.remove(r4)     // Catch:{ all -> 0x0021 }
            boolean r1 = r0.isEmpty()     // Catch:{ all -> 0x0021 }
            if (r1 == 0) goto L_0x001f
            java.util.HashMap<java.lang.String, java.util.ArrayList<com.google.android.exoplayer2.upstream.cache.Cache$Listener>> r1 = r2.listeners     // Catch:{ all -> 0x0021 }
            r1.remove(r3)     // Catch:{ all -> 0x0021 }
        L_0x001f:
            monitor-exit(r2)
            return
        L_0x0021:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.upstream.cache.SimpleCache.removeListener(java.lang.String, com.google.android.exoplayer2.upstream.cache.Cache$Listener):void");
    }

    @NonNull
    public synchronized NavigableSet<CacheSpan> getCachedSpans(String key) {
        TreeSet treeSet;
        Assertions.checkState(!this.released);
        CachedContent cachedContent = this.contentIndex.get(key);
        if (cachedContent != null) {
            if (!cachedContent.isEmpty()) {
                treeSet = new TreeSet((Collection) cachedContent.getSpans());
            }
        }
        treeSet = new TreeSet();
        return treeSet;
    }

    public synchronized Set<String> getKeys() {
        Assertions.checkState(!this.released);
        return new HashSet(this.contentIndex.getKeys());
    }

    public synchronized long getCacheSpace() {
        Assertions.checkState(!this.released);
        return this.totalSpace;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.exoplayer2.upstream.cache.SimpleCache.startReadWriteNonBlocking(java.lang.String, long):com.google.android.exoplayer2.upstream.cache.SimpleCacheSpan
     arg types: [java.lang.String, long]
     candidates:
      com.google.android.exoplayer2.upstream.cache.SimpleCache.startReadWriteNonBlocking(java.lang.String, long):com.google.android.exoplayer2.upstream.cache.CacheSpan
      com.google.android.exoplayer2.upstream.cache.Cache.startReadWriteNonBlocking(java.lang.String, long):com.google.android.exoplayer2.upstream.cache.CacheSpan
      com.google.android.exoplayer2.upstream.cache.SimpleCache.startReadWriteNonBlocking(java.lang.String, long):com.google.android.exoplayer2.upstream.cache.SimpleCacheSpan */
    public synchronized SimpleCacheSpan startReadWrite(String key, long position) throws InterruptedException, Cache.CacheException {
        SimpleCacheSpan span;
        Assertions.checkState(!this.released);
        checkInitialization();
        while (true) {
            span = startReadWriteNonBlocking(key, position);
            if (span == null) {
                wait();
            }
        }
        return span;
    }

    @Nullable
    public synchronized SimpleCacheSpan startReadWriteNonBlocking(String key, long position) throws Cache.CacheException {
        Assertions.checkState(!this.released);
        checkInitialization();
        SimpleCacheSpan span = getSpan(key, position);
        if (!span.isCached) {
            CachedContent cachedContent = this.contentIndex.getOrAdd(key);
            if (cachedContent.isLocked()) {
                return null;
            }
            cachedContent.setLocked(true);
            return span;
        } else if (!this.touchCacheSpans) {
            return span;
        } else {
            String fileName = ((File) Assertions.checkNotNull(span.file)).getName();
            long length = span.length;
            long lastTouchTimestamp = System.currentTimeMillis();
            boolean updateFile = false;
            if (this.fileIndex != null) {
                try {
                    this.fileIndex.set(fileName, length, lastTouchTimestamp);
                } catch (IOException e) {
                    Log.m30w(TAG, "Failed to update index with new touch timestamp.");
                }
            } else {
                updateFile = true;
            }
            SimpleCacheSpan newSpan = this.contentIndex.get(key).setLastTouchTimestamp(span, lastTouchTimestamp, updateFile);
            notifySpanTouched(span, newSpan);
            return newSpan;
        }
    }

    public synchronized File startFile(String key, long position, long length) throws Cache.CacheException {
        CachedContent cachedContent;
        File fileDir;
        Assertions.checkState(!this.released);
        checkInitialization();
        cachedContent = this.contentIndex.get(key);
        Assertions.checkNotNull(cachedContent);
        Assertions.checkState(cachedContent.isLocked());
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdirs();
            removeStaleSpans();
        }
        this.evictor.onStartFile(this, key, position, length);
        fileDir = new File(this.cacheDir, Integer.toString(this.random.nextInt(10)));
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        return SimpleCacheSpan.getCacheFile(fileDir, cachedContent.f105id, position, System.currentTimeMillis());
    }

    public synchronized void commitFile(File file, long length) throws Cache.CacheException {
        boolean z = true;
        Assertions.checkState(!this.released);
        if (file.exists()) {
            if (length == 0) {
                file.delete();
                return;
            }
            SimpleCacheSpan span = (SimpleCacheSpan) Assertions.checkNotNull(SimpleCacheSpan.createCacheEntry(file, length, this.contentIndex));
            CachedContent cachedContent = (CachedContent) Assertions.checkNotNull(this.contentIndex.get(span.key));
            Assertions.checkState(cachedContent.isLocked());
            long contentLength = ContentMetadata$$CC.getContentLength$$STATIC$$(cachedContent.getMetadata());
            if (contentLength != -1) {
                if (span.position + span.length > contentLength) {
                    z = false;
                }
                Assertions.checkState(z);
            }
            if (this.fileIndex != null) {
                try {
                    this.fileIndex.set(file.getName(), span.length, span.lastTouchTimestamp);
                } catch (IOException e) {
                    throw new Cache.CacheException(e);
                } catch (IOException e2) {
                    throw new Cache.CacheException(e2);
                }
            }
            addSpan(span);
            this.contentIndex.store();
            notifyAll();
        }
    }

    public synchronized void releaseHoleSpan(CacheSpan holeSpan) {
        Assertions.checkState(!this.released);
        CachedContent cachedContent = this.contentIndex.get(holeSpan.key);
        Assertions.checkNotNull(cachedContent);
        Assertions.checkState(cachedContent.isLocked());
        cachedContent.setLocked(false);
        this.contentIndex.maybeRemove(cachedContent.key);
        notifyAll();
    }

    public synchronized void removeSpan(CacheSpan span) {
        Assertions.checkState(!this.released);
        removeSpanInternal(span);
    }

    public synchronized boolean isCached(String key, long position, long length) {
        boolean z;
        z = true;
        Assertions.checkState(!this.released);
        CachedContent cachedContent = this.contentIndex.get(key);
        if (cachedContent == null || cachedContent.getCachedBytesLength(position, length) < length) {
            z = false;
        }
        return z;
    }

    public synchronized long getCachedLength(String key, long position, long length) {
        CachedContent cachedContent;
        Assertions.checkState(!this.released);
        cachedContent = this.contentIndex.get(key);
        return cachedContent != null ? cachedContent.getCachedBytesLength(position, length) : -length;
    }

    public synchronized void applyContentMetadataMutations(String key, ContentMetadataMutations mutations) throws Cache.CacheException {
        Assertions.checkState(!this.released);
        checkInitialization();
        this.contentIndex.applyContentMetadataMutations(key, mutations);
        try {
            this.contentIndex.store();
        } catch (IOException e) {
            throw new Cache.CacheException(e);
        }
    }

    public synchronized ContentMetadata getContentMetadata(String key) {
        Assertions.checkState(!this.released);
        return this.contentIndex.getContentMetadata(key);
    }

    private SimpleCacheSpan getSpan(String key, long position) {
        SimpleCacheSpan span;
        CachedContent cachedContent = this.contentIndex.get(key);
        if (cachedContent == null) {
            return SimpleCacheSpan.createOpenHole(key, position);
        }
        while (true) {
            span = cachedContent.getSpan(position);
            if (!span.isCached || span.file.exists()) {
                return span;
            }
            removeStaleSpans();
        }
        return span;
    }

    /* access modifiers changed from: private */
    public void initialize() {
        if (this.cacheDir.exists() || this.cacheDir.mkdirs()) {
            File[] files = this.cacheDir.listFiles();
            if (files == null) {
                String valueOf = String.valueOf(this.cacheDir);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 38);
                sb.append("Failed to list cache directory files: ");
                sb.append(valueOf);
                String message = sb.toString();
                Log.m26e(TAG, message);
                this.initializationException = new Cache.CacheException(message);
                return;
            }
            this.uid = loadUid(files);
            if (this.uid == -1) {
                try {
                    this.uid = createUid(this.cacheDir);
                } catch (IOException e) {
                    String valueOf2 = String.valueOf(this.cacheDir);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 28);
                    sb2.append("Failed to create cache UID: ");
                    sb2.append(valueOf2);
                    String message2 = sb2.toString();
                    Log.m27e(TAG, message2, e);
                    this.initializationException = new Cache.CacheException(message2, e);
                    return;
                }
            }
            try {
                this.contentIndex.initialize(this.uid);
                if (this.fileIndex != null) {
                    this.fileIndex.initialize(this.uid);
                    Map<String, CacheFileMetadata> fileMetadata = this.fileIndex.getAll();
                    loadDirectory(this.cacheDir, true, files, fileMetadata);
                    this.fileIndex.removeAll(fileMetadata.keySet());
                } else {
                    loadDirectory(this.cacheDir, true, files, null);
                }
                this.contentIndex.removeEmpty();
                try {
                    this.contentIndex.store();
                } catch (IOException e2) {
                    Log.m27e(TAG, "Storing index file failed", e2);
                }
            } catch (IOException e3) {
                String valueOf3 = String.valueOf(this.cacheDir);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 36);
                sb3.append("Failed to initialize cache indices: ");
                sb3.append(valueOf3);
                String message3 = sb3.toString();
                Log.m27e(TAG, message3, e3);
                this.initializationException = new Cache.CacheException(message3, e3);
            }
        } else {
            String valueOf4 = String.valueOf(this.cacheDir);
            StringBuilder sb4 = new StringBuilder(String.valueOf(valueOf4).length() + 34);
            sb4.append("Failed to create cache directory: ");
            sb4.append(valueOf4);
            String message4 = sb4.toString();
            Log.m26e(TAG, message4);
            this.initializationException = new Cache.CacheException(message4);
        }
    }

    private void loadDirectory(File directory, boolean isRoot, @Nullable File[] files, @Nullable Map<String, CacheFileMetadata> fileMetadata) {
        long lastTouchTimestamp;
        long length;
        File[] fileArr = files;
        Map<String, CacheFileMetadata> map = fileMetadata;
        if (fileArr != null && fileArr.length != 0) {
            for (File file : fileArr) {
                String fileName = file.getName();
                if (isRoot && fileName.indexOf(46) == -1) {
                    loadDirectory(file, false, file.listFiles(), map);
                } else if (!isRoot || (!CachedContentIndex.isIndexFile(fileName) && !fileName.endsWith(UID_FILE_SUFFIX))) {
                    CacheFileMetadata metadata = map != null ? map.remove(fileName) : null;
                    if (metadata != null) {
                        length = metadata.length;
                        lastTouchTimestamp = metadata.lastTouchTimestamp;
                    } else {
                        length = -1;
                        lastTouchTimestamp = -9223372036854775807L;
                    }
                    SimpleCacheSpan span = SimpleCacheSpan.createCacheEntry(file, length, lastTouchTimestamp, this.contentIndex);
                    if (span != null) {
                        addSpan(span);
                    } else {
                        file.delete();
                    }
                }
            }
        } else if (!isRoot) {
            directory.delete();
        }
    }

    private void addSpan(SimpleCacheSpan span) {
        this.contentIndex.getOrAdd(span.key).addSpan(span);
        this.totalSpace += span.length;
        notifySpanAdded(span);
    }

    private void removeSpanInternal(CacheSpan span) {
        CachedContent cachedContent = this.contentIndex.get(span.key);
        if (cachedContent != null && cachedContent.removeSpan(span)) {
            this.totalSpace -= span.length;
            if (this.fileIndex != null) {
                String fileName = span.file.getName();
                try {
                    this.fileIndex.remove(fileName);
                } catch (IOException e) {
                    String valueOf = String.valueOf(fileName);
                    Log.m30w(TAG, valueOf.length() != 0 ? "Failed to remove file index entry for: ".concat(valueOf) : new String("Failed to remove file index entry for: "));
                }
            }
            this.contentIndex.maybeRemove(cachedContent.key);
            notifySpanRemoved(span);
        }
    }

    private void removeStaleSpans() {
        ArrayList<CacheSpan> spansToBeRemoved = new ArrayList<>();
        for (CachedContent cachedContent : this.contentIndex.getAll()) {
            Iterator<SimpleCacheSpan> it = cachedContent.getSpans().iterator();
            while (it.hasNext()) {
                CacheSpan span = it.next();
                if (!span.file.exists()) {
                    spansToBeRemoved.add(span);
                }
            }
        }
        for (int i = 0; i < spansToBeRemoved.size(); i++) {
            removeSpanInternal((CacheSpan) spansToBeRemoved.get(i));
        }
    }

    private void notifySpanRemoved(CacheSpan span) {
        ArrayList<Cache.Listener> keyListeners = this.listeners.get(span.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                ((Cache.Listener) keyListeners.get(i)).onSpanRemoved(this, span);
            }
        }
        this.evictor.onSpanRemoved(this, span);
    }

    private void notifySpanAdded(SimpleCacheSpan span) {
        ArrayList<Cache.Listener> keyListeners = this.listeners.get(span.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                ((Cache.Listener) keyListeners.get(i)).onSpanAdded(this, span);
            }
        }
        this.evictor.onSpanAdded(this, span);
    }

    private void notifySpanTouched(SimpleCacheSpan oldSpan, CacheSpan newSpan) {
        ArrayList<Cache.Listener> keyListeners = this.listeners.get(oldSpan.key);
        if (keyListeners != null) {
            for (int i = keyListeners.size() - 1; i >= 0; i--) {
                ((Cache.Listener) keyListeners.get(i)).onSpanTouched(this, oldSpan, newSpan);
            }
        }
        this.evictor.onSpanTouched(this, oldSpan, newSpan);
    }
}
