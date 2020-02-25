package com.google.common.collect;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;

@GwtIncompatible
@Beta
public final class Interners {
    private Interners() {
    }

    public static InternerBuilder newBuilder() {
        return new InternerBuilder();
    }

    public static <E> Interner<E> newStrongInterner() {
        return newBuilder().strong().build();
    }

    @GwtIncompatible("java.lang.ref.WeakReference")
    public static <E> Interner<E> newWeakInterner() {
        return newBuilder().weak().build();
    }

    public static <E> Function<E, E> asFunction(Interner<E> interner) {
        return new InternerFunction((Interner) Preconditions.checkNotNull(interner));
    }

    public static class InternerBuilder {
        private final MapMaker mapMaker;
        private boolean strong;

        private InternerBuilder() {
            this.mapMaker = new MapMaker();
            this.strong = true;
        }

        public InternerBuilder strong() {
            this.strong = true;
            return this;
        }

        @GwtIncompatible("java.lang.ref.WeakReference")
        public InternerBuilder weak() {
            this.strong = false;
            return this;
        }

        public InternerBuilder concurrencyLevel(int concurrencyLevel) {
            this.mapMaker.concurrencyLevel(concurrencyLevel);
            return this;
        }

        public <E> Interner<E> build() {
            if (!this.strong) {
                this.mapMaker.weakKeys();
            }
            return new InternerImpl(this.mapMaker);
        }
    }

    @VisibleForTesting
    static final class InternerImpl<E> implements Interner<E> {
        @VisibleForTesting
        final MapMakerInternalMap<E, MapMaker.Dummy, ?, ?> map;

        private InternerImpl(MapMaker mapMaker) {
            this.map = MapMakerInternalMap.createWithDummyValues(mapMaker.keyEquivalence(Equivalence.equals()));
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
        public E intern(E r4) {
            /*
                r3 = this;
            L_0x0000:
                com.google.common.collect.MapMakerInternalMap<E, com.google.common.collect.MapMaker$Dummy, ?, ?> r0 = r3.map
                com.google.common.collect.MapMakerInternalMap$InternalEntry r0 = r0.getEntry(r4)
                if (r0 == 0) goto L_0x000f
                java.lang.Object r1 = r0.getKey()
                if (r1 == 0) goto L_0x000f
                return r1
            L_0x000f:
                com.google.common.collect.MapMakerInternalMap<E, com.google.common.collect.MapMaker$Dummy, ?, ?> r1 = r3.map
                com.google.common.collect.MapMaker$Dummy r2 = com.google.common.collect.MapMaker.Dummy.VALUE
                java.lang.Object r1 = r1.putIfAbsent(r4, r2)
                com.google.common.collect.MapMaker$Dummy r1 = (com.google.common.collect.MapMaker.Dummy) r1
                if (r1 != 0) goto L_0x001c
                return r4
            L_0x001c:
                goto L_0x0000
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.collect.Interners.InternerImpl.intern(java.lang.Object):java.lang.Object");
        }
    }

    private static class InternerFunction<E> implements Function<E, E> {
        private final Interner<E> interner;

        public InternerFunction(Interner<E> interner2) {
            this.interner = interner2;
        }

        public E apply(E input) {
            return this.interner.intern(input);
        }

        public int hashCode() {
            return this.interner.hashCode();
        }

        public boolean equals(Object other) {
            if (other instanceof InternerFunction) {
                return this.interner.equals(((InternerFunction) other).interner);
            }
            return false;
        }
    }
}
