package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;

@GwtCompatible(emulated = true, serializable = true)
final class SingletonImmutableSet<E> extends ImmutableSet<E> {
    final transient E element;
    @LazyInit
    private transient int cachedHashCode;

    SingletonImmutableSet(E element2) {
        this.element = Preconditions.checkNotNull(element2);
    }

    SingletonImmutableSet(E element2, int hashCode) {
        this.element = element2;
        this.cachedHashCode = hashCode;
    }

    public int size() {
        return 1;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: E
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
    public boolean contains(java.lang.Object r2) {
        /*
            r1 = this;
            E r0 = r1.element
            boolean r0 = r0.equals(r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.SingletonImmutableSet.contains(java.lang.Object):boolean");
    }

    public UnmodifiableIterator<E> iterator() {
        return Iterators.singletonIterator(this.element);
    }

    /* access modifiers changed from: package-private */
    public ImmutableList<E> createAsList() {
        return ImmutableList.m108of(this.element);
    }

    /* access modifiers changed from: package-private */
    public boolean isPartialView() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int copyIntoArray(Object[] dst, int offset) {
        dst[offset] = this.element;
        return offset + 1;
    }

    public final int hashCode() {
        int code = this.cachedHashCode;
        if (code != 0) {
            return code;
        }
        int hashCode = this.element.hashCode();
        int code2 = hashCode;
        this.cachedHashCode = hashCode;
        return code2;
    }

    /* access modifiers changed from: package-private */
    public boolean isHashCodeFast() {
        return this.cachedHashCode != 0;
    }

    public String toString() {
        String obj = this.element.toString();
        StringBuilder sb = new StringBuilder(String.valueOf(obj).length() + 2);
        sb.append('[');
        sb.append(obj);
        sb.append(']');
        return sb.toString();
    }
}
