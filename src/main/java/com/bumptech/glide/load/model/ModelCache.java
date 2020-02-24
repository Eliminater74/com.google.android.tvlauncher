package com.bumptech.glide.load.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Util;
import java.util.Queue;

public class ModelCache<A, B> {
    private static final int DEFAULT_SIZE = 250;
    private final LruCache<ModelKey<A>, B> cache;

    public ModelCache() {
        this(250);
    }

    public ModelCache(long size) {
        this.cache = new LruCache<ModelKey<A>, B>(this, size) {
            /* access modifiers changed from: protected */
            public void onItemEvicted(@NonNull ModelKey<A> key, @Nullable B b) {
                key.release();
            }
        };
    }

    @Nullable
    public B get(A model, int width, int height) {
        ModelKey<A> key = ModelKey.get(model, width, height);
        B result = this.cache.get(key);
        key.release();
        return result;
    }

    public void put(A model, int width, int height, B value) {
        this.cache.put(ModelKey.get(model, width, height), value);
    }

    public void clear() {
        this.cache.clearMemory();
    }

    @VisibleForTesting
    static final class ModelKey<A> {
        private static final Queue<ModelKey<?>> KEY_QUEUE = Util.createQueue(0);
        private int height;
        private A model;
        private int width;

        static <A> ModelKey<A> get(A model2, int width2, int height2) {
            ModelKey<A> modelKey;
            synchronized (KEY_QUEUE) {
                modelKey = KEY_QUEUE.poll();
            }
            if (modelKey == null) {
                modelKey = new ModelKey<>();
            }
            modelKey.init(model2, width2, height2);
            return modelKey;
        }

        private ModelKey() {
        }

        private void init(A model2, int width2, int height2) {
            this.model = model2;
            this.width = width2;
            this.height = height2;
        }

        public void release() {
            synchronized (KEY_QUEUE) {
                KEY_QUEUE.offer(this);
            }
        }

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: A
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
        public boolean equals(java.lang.Object r5) {
            /*
                r4 = this;
                boolean r0 = r5 instanceof com.bumptech.glide.load.model.ModelCache.ModelKey
                r1 = 0
                if (r0 == 0) goto L_0x0020
                r0 = r5
                com.bumptech.glide.load.model.ModelCache$ModelKey r0 = (com.bumptech.glide.load.model.ModelCache.ModelKey) r0
                int r2 = r4.width
                int r3 = r0.width
                if (r2 != r3) goto L_0x001f
                int r2 = r4.height
                int r3 = r0.height
                if (r2 != r3) goto L_0x001f
                A r2 = r4.model
                A r3 = r0.model
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L_0x001f
                r1 = 1
            L_0x001f:
                return r1
            L_0x0020:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.bumptech.glide.load.model.ModelCache.ModelKey.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            return (((this.height * 31) + this.width) * 31) + this.model.hashCode();
        }
    }
}
