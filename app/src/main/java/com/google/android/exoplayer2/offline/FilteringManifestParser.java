package com.google.android.exoplayer2.offline;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.upstream.ParsingLoadable;

import java.util.List;

public final class FilteringManifestParser<T extends FilterableManifest<T>> implements ParsingLoadable.Parser<T> {
    private final ParsingLoadable.Parser<? extends T> parser;
    @Nullable
    private final List<StreamKey> streamKeys;

    public FilteringManifestParser(ParsingLoadable.Parser<? extends T> parser2, @Nullable List<StreamKey> streamKeys2) {
        this.parser = parser2;
        this.streamKeys = streamKeys2;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: T
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:75)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public T parse(android.net.Uri r3, java.io.InputStream r4) throws java.io.IOException {
        /*
            r2 = this;
            com.google.android.exoplayer2.upstream.ParsingLoadable$Parser<? extends T> r0 = r2.parser
            java.lang.Object r0 = r0.parse(r3, r4)
            com.google.android.exoplayer2.offline.FilterableManifest r0 = (com.google.android.exoplayer2.offline.FilterableManifest) r0
            java.util.List<com.google.android.exoplayer2.offline.StreamKey> r1 = r2.streamKeys
            if (r1 == 0) goto L_0x001c
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x0013
            goto L_0x001c
        L_0x0013:
            java.util.List<com.google.android.exoplayer2.offline.StreamKey> r1 = r2.streamKeys
            java.lang.Object r1 = r0.copy(r1)
            com.google.android.exoplayer2.offline.FilterableManifest r1 = (com.google.android.exoplayer2.offline.FilterableManifest) r1
            goto L_0x001d
        L_0x001c:
            r1 = r0
        L_0x001d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.offline.FilteringManifestParser.parse(android.net.Uri, java.io.InputStream):com.google.android.exoplayer2.offline.FilterableManifest");
    }
}
