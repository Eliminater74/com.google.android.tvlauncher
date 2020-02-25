package com.google.common.base;

import com.google.common.annotations.GwtCompatible;

import java.util.Collections;
import java.util.Set;

@GwtCompatible
final class Present<T> extends Optional<T> {
    private static final long serialVersionUID = 0;
    private final T reference;

    Present(T reference2) {
        this.reference = reference2;
    }

    public boolean isPresent() {
        return true;
    }

    public T get() {
        return this.reference;
    }

    /* renamed from: or */
    public T mo22987or(T defaultValue) {
        Preconditions.checkNotNull(defaultValue, "use Optional.orNull() instead of Optional.or(null)");
        return this.reference;
    }

    /* renamed from: or */
    public Optional<T> mo22985or(Optional<? extends T> secondChoice) {
        Preconditions.checkNotNull(secondChoice);
        return this;
    }

    /* renamed from: or */
    public T mo22986or(Supplier<? extends T> supplier) {
        Preconditions.checkNotNull(supplier);
        return this.reference;
    }

    public T orNull() {
        return this.reference;
    }

    public Set<T> asSet() {
        return Collections.singleton(this.reference);
    }

    public <V> Optional<V> transform(Function<? super T, V> function) {
        return new Present(Preconditions.checkNotNull(function.apply(this.reference), "the Function passed to Optional.transform() must not return null."));
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    public boolean equals(@org.checkerframework.checker.nullness.compatqual.NullableDecl java.lang.Object r4) {
        /*
            r3 = this;
            boolean r0 = r4 instanceof com.google.common.base.Present
            if (r0 == 0) goto L_0x0010
            r0 = r4
            com.google.common.base.Present r0 = (com.google.common.base.Present) r0
            T r1 = r3.reference
            T r2 = r0.reference
            boolean r1 = r1.equals(r2)
            return r1
        L_0x0010:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Present.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return this.reference.hashCode() + 1502476572;
    }

    public String toString() {
        String valueOf = String.valueOf(this.reference);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 13);
        sb.append("Optional.of(");
        sb.append(valueOf);
        sb.append(")");
        return sb.toString();
    }
}
