package com.bumptech.glide.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<T, Y> {
    private final Map<T, Y> cache = new LinkedHashMap(100, 0.75f, true);
    private long currentSize;
    private final long initialMaxSize;
    private long maxSize;

    public LruCache(long size) {
        this.initialMaxSize = size;
        this.maxSize = size;
    }

    public synchronized void setSizeMultiplier(float multiplier) {
        if (multiplier >= 0.0f) {
            this.maxSize = (long) Math.round(((float) this.initialMaxSize) * multiplier);
            evict();
        } else {
            throw new IllegalArgumentException("Multiplier must be >= 0");
        }
    }

    /* access modifiers changed from: protected */
    public int getSize(@Nullable Object obj) {
        return 1;
    }

    /* access modifiers changed from: protected */
    public synchronized int getCount() {
        return this.cache.size();
    }

    /* access modifiers changed from: protected */
    public void onItemEvicted(@NonNull Object obj, @Nullable Object obj2) {
    }

    public synchronized long getMaxSize() {
        return this.maxSize;
    }

    public synchronized long getCurrentSize() {
        return this.currentSize;
    }

    public synchronized boolean contains(@NonNull T key) {
        return this.cache.containsKey(key);
    }

    @Nullable
    public synchronized Y get(@NonNull T key) {
        return this.cache.get(key);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: Y
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    @android.support.annotation.Nullable
    public synchronized Y put(@android.support.annotation.NonNull java.lang.Object r7, @android.support.annotation.Nullable java.lang.Object r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            int r0 = r6.getSize(r8)     // Catch:{ all -> 0x003a }
            long r1 = (long) r0     // Catch:{ all -> 0x003a }
            long r3 = r6.maxSize     // Catch:{ all -> 0x003a }
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 < 0) goto L_0x0012
            r6.onItemEvicted(r7, r8)     // Catch:{ all -> 0x003a }
            r1 = 0
            monitor-exit(r6)
            return r1
        L_0x0012:
            if (r8 == 0) goto L_0x001a
            long r1 = r6.currentSize     // Catch:{ all -> 0x003a }
            long r3 = (long) r0     // Catch:{ all -> 0x003a }
            long r1 = r1 + r3
            r6.currentSize = r1     // Catch:{ all -> 0x003a }
        L_0x001a:
            java.util.Map<T, Y> r1 = r6.cache     // Catch:{ all -> 0x003a }
            java.lang.Object r1 = r1.put(r7, r8)     // Catch:{ all -> 0x003a }
            if (r1 == 0) goto L_0x0035
            long r2 = r6.currentSize     // Catch:{ all -> 0x003a }
            int r4 = r6.getSize(r1)     // Catch:{ all -> 0x003a }
            long r4 = (long) r4     // Catch:{ all -> 0x003a }
            long r2 = r2 - r4
            r6.currentSize = r2     // Catch:{ all -> 0x003a }
            boolean r2 = r1.equals(r8)     // Catch:{ all -> 0x003a }
            if (r2 != 0) goto L_0x0035
            r6.onItemEvicted(r7, r1)     // Catch:{ all -> 0x003a }
        L_0x0035:
            r6.evict()     // Catch:{ all -> 0x003a }
            monitor-exit(r6)
            return r1
        L_0x003a:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.util.LruCache.put(java.lang.Object, java.lang.Object):java.lang.Object");
    }

    @Nullable
    public synchronized Y remove(@NonNull Object obj) {
        Y value;
        value = this.cache.remove(obj);
        if (value != null) {
            this.currentSize -= (long) getSize(value);
        }
        return value;
    }

    public void clearMemory() {
        trimToSize(0);
    }

    /* access modifiers changed from: protected */
    public synchronized void trimToSize(long size) {
        while (this.currentSize > size) {
            Iterator<Map.Entry<T, Y>> cacheIterator = this.cache.entrySet().iterator();
            Map.Entry<T, Y> last = cacheIterator.next();
            Y toRemove = last.getValue();
            this.currentSize -= (long) getSize(toRemove);
            T key = last.getKey();
            cacheIterator.remove();
            onItemEvicted(key, toRemove);
        }
    }

    private void evict() {
        trimToSize(this.maxSize);
    }
}
