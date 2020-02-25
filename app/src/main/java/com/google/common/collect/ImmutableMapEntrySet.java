package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;

import java.io.Serializable;
import java.util.Map;

@GwtCompatible(emulated = true)
abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>> {
    ImmutableMapEntrySet() {
    }

    /* access modifiers changed from: package-private */
    public abstract ImmutableMap<K, V> map();

    public int size() {
        return map().size();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: V
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
    public boolean contains(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof java.util.Map.Entry
            r1 = 0
            if (r0 == 0) goto L_0x0022
            r0 = r5
            java.util.Map$Entry r0 = (java.util.Map.Entry) r0
            com.google.common.collect.ImmutableMap r2 = r4.map()
            java.lang.Object r3 = r0.getKey()
            java.lang.Object r2 = r2.get(r3)
            if (r2 == 0) goto L_0x0021
            java.lang.Object r3 = r0.getValue()
            boolean r3 = r2.equals(r3)
            if (r3 == 0) goto L_0x0021
            r1 = 1
        L_0x0021:
            return r1
        L_0x0022:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.ImmutableMapEntrySet.contains(java.lang.Object):boolean");
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return map().isPartialView();
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public boolean isHashCodeFast() {
        return map().isHashCodeFast();
    }

    public int hashCode() {
        return map().hashCode();
    }

    /* access modifiers changed from: package-private */
    @GwtIncompatible
    public Object writeReplace() {
        return new EntrySetSerializedForm(map());
    }

    static final class RegularEntrySet<K, V> extends ImmutableMapEntrySet<K, V> {
        private final transient ImmutableList<Map.Entry<K, V>> entries;
        private final transient ImmutableMap<K, V> map;

        RegularEntrySet(ImmutableMap<K, V> map2, Map.Entry<K, V>[] entries2) {
            this(map2, ImmutableList.asImmutableList(entries2));
        }

        RegularEntrySet(ImmutableMap<K, V> map2, ImmutableList<Map.Entry<K, V>> entries2) {
            this.map = map2;
            this.entries = entries2;
        }

        /* access modifiers changed from: package-private */
        public ImmutableMap<K, V> map() {
            return this.map;
        }

        /* access modifiers changed from: package-private */
        @GwtIncompatible("not used in GWT")
        public int copyIntoArray(Object[] dst, int offset) {
            return this.entries.copyIntoArray(dst, offset);
        }

        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.entries.iterator();
        }

        /* access modifiers changed from: package-private */
        public ImmutableList<Map.Entry<K, V>> createAsList() {
            return this.entries;
        }
    }

    @GwtIncompatible
    private static class EntrySetSerializedForm<K, V> implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableMap<K, V> map;

        EntrySetSerializedForm(ImmutableMap<K, V> map2) {
            this.map = map2;
        }

        /* access modifiers changed from: package-private */
        public Object readResolve() {
            return this.map.entrySet();
        }
    }
}
