package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@GwtCompatible
public class Joiner {
    /* access modifiers changed from: private */
    public final String separator;

    private Joiner(String separator2) {
        this.separator = (String) Preconditions.checkNotNull(separator2);
    }

    private Joiner(Joiner prototype) {
        this.separator = prototype.separator;
    }

    /* renamed from: on */
    public static Joiner m79on(String separator2) {
        return new Joiner(separator2);
    }

    /* renamed from: on */
    public static Joiner m78on(char separator2) {
        return new Joiner(String.valueOf(separator2));
    }

    private static Iterable<Object> iterable(final Object first, final Object second, final Object[] rest) {
        Preconditions.checkNotNull(rest);
        return new AbstractList<Object>() {
            public int size() {
                return rest.length + 2;
            }

            public Object get(int index) {
                if (index == 0) {
                    return first;
                }
                if (index != 1) {
                    return rest[index - 2];
                }
                return second;
            }
        };
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.util.Iterator<?>):A
     arg types: [A, java.util.Iterator<?>]
     candidates:
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Iterable<?>):A
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Object[]):A
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.util.Iterator<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Object[]):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.util.Iterator<?>):A */
    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
        return appendTo((Appendable) appendable, parts.iterator());
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    @com.google.errorprone.annotations.CanIgnoreReturnValue
    public <A extends java.lang.Appendable> A appendTo(A r2, java.util.Iterator<?> r3) throws java.io.IOException {
        /*
            r1 = this;
            com.google.common.base.Preconditions.checkNotNull(r2)
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x002b
            java.lang.Object r0 = r3.next()
            java.lang.CharSequence r0 = r1.toString(r0)
            r2.append(r0)
        L_0x0014:
            boolean r0 = r3.hasNext()
            if (r0 == 0) goto L_0x002b
            java.lang.String r0 = r1.separator
            r2.append(r0)
            java.lang.Object r0 = r3.next()
            java.lang.CharSequence r0 = r1.toString(r0)
            r2.append(r0)
            goto L_0x0014
        L_0x002b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.util.Iterator):java.lang.Appendable");
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Iterable<?>):A
     arg types: [A, java.util.List]
     candidates:
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.util.Iterator<?>):A
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Object[]):A
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.util.Iterator<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Object[]):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Iterable<?>):A */
    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A appendable, Object[] parts) throws IOException {
        return appendTo((Appendable) appendable, (Iterable<?>) Arrays.asList(parts));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Iterable<?>):A
     arg types: [A, java.lang.Iterable<java.lang.Object>]
     candidates:
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.util.Iterator<?>):A
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Object[]):A
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.util.Iterator<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Object[]):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Iterable<?>):A */
    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A appendable, @NullableDecl Object first, @NullableDecl Object second, Object... rest) throws IOException {
        return appendTo((Appendable) appendable, (Iterable<?>) iterable(first, second, rest));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder builder, Iterable<?> parts) {
        return appendTo(builder, parts.iterator());
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.util.Iterator<?>):A
     arg types: [java.lang.StringBuilder, java.util.Iterator<?>]
     candidates:
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Iterable<?>):A
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Object[]):A
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.util.Iterator<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Object[]):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.util.Iterator<?>):A */
    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts) {
        try {
            appendTo((Appendable) builder, parts);
            return builder;
        } catch (IOException impossible) {
            throw new AssertionError(impossible);
        }
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder builder, Object[] parts) {
        return appendTo(builder, (Iterable<?>) Arrays.asList(parts));
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<?>):java.lang.StringBuilder
     arg types: [java.lang.StringBuilder, java.lang.Iterable<java.lang.Object>]
     candidates:
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Iterable<?>):A
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.util.Iterator<?>):A
      com.google.common.base.Joiner.appendTo(java.lang.Appendable, java.lang.Object[]):A
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.util.Iterator<?>):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Object[]):java.lang.StringBuilder
      com.google.common.base.Joiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<?>):java.lang.StringBuilder */
    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder builder, @NullableDecl Object first, @NullableDecl Object second, Object... rest) {
        return appendTo(builder, (Iterable<?>) iterable(first, second, rest));
    }

    public final String join(Iterable<?> parts) {
        return join(parts.iterator());
    }

    public final String join(Iterator<?> parts) {
        return appendTo(new StringBuilder(), parts).toString();
    }

    public final String join(Object[] parts) {
        return join(Arrays.asList(parts));
    }

    public final String join(@NullableDecl Object first, @NullableDecl Object second, Object... rest) {
        return join(iterable(first, second, rest));
    }

    public Joiner useForNull(final String nullText) {
        Preconditions.checkNotNull(nullText);
        return new Joiner(this) {
            /* access modifiers changed from: package-private */
            public CharSequence toString(@NullableDecl Object part) {
                return part == null ? nullText : Joiner.this.toString(part);
            }

            public Joiner useForNull(String nullText) {
                throw new UnsupportedOperationException("already specified useForNull");
            }

            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    public Joiner skipNulls() {
        return new Joiner(this) {
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
                	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
                */
            public <A extends java.lang.Appendable> A appendTo(A r3, java.util.Iterator<?> r4) throws java.io.IOException {
                /*
                    r2 = this;
                    java.lang.String r0 = "appendable"
                    com.google.common.base.Preconditions.checkNotNull(r3, r0)
                    java.lang.String r0 = "parts"
                    com.google.common.base.Preconditions.checkNotNull(r4, r0)
                L_0x000a:
                    boolean r0 = r4.hasNext()
                    if (r0 == 0) goto L_0x0021
                    java.lang.Object r0 = r4.next()
                    if (r0 == 0) goto L_0x0020
                    com.google.common.base.Joiner r1 = com.google.common.base.Joiner.this
                    java.lang.CharSequence r1 = r1.toString(r0)
                    r3.append(r1)
                    goto L_0x0021
                L_0x0020:
                    goto L_0x000a
                L_0x0021:
                    boolean r0 = r4.hasNext()
                    if (r0 == 0) goto L_0x0040
                    java.lang.Object r0 = r4.next()
                    if (r0 == 0) goto L_0x003f
                    com.google.common.base.Joiner r1 = com.google.common.base.Joiner.this
                    java.lang.String r1 = r1.separator
                    r3.append(r1)
                    com.google.common.base.Joiner r1 = com.google.common.base.Joiner.this
                    java.lang.CharSequence r1 = r1.toString(r0)
                    r3.append(r1)
                L_0x003f:
                    goto L_0x0021
                L_0x0040:
                    return r3
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Joiner.C14892.appendTo(java.lang.Appendable, java.util.Iterator):java.lang.Appendable");
            }

            public Joiner useForNull(String nullText) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }

            public MapJoiner withKeyValueSeparator(String kvs) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }

    public MapJoiner withKeyValueSeparator(char keyValueSeparator) {
        return withKeyValueSeparator(String.valueOf(keyValueSeparator));
    }

    public MapJoiner withKeyValueSeparator(String keyValueSeparator) {
        return new MapJoiner(keyValueSeparator);
    }

    /* access modifiers changed from: package-private */
    public CharSequence toString(Object part) {
        Preconditions.checkNotNull(part);
        return part instanceof CharSequence ? (CharSequence) part : part.toString();
    }

    public static final class MapJoiner {
        private final Joiner joiner;
        private final String keyValueSeparator;

        private MapJoiner(Joiner joiner2, String keyValueSeparator2) {
            this.joiner = joiner2;
            this.keyValueSeparator = (String) Preconditions.checkNotNull(keyValueSeparator2);
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):A
         arg types: [A, java.util.Set<java.util.Map$Entry<?, ?>>]
         candidates:
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Map<?, ?>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.util.Map<?, ?>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):A */
        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A appendable, Map<?, ?> map) throws IOException {
            return appendTo((Appendable) appendable, (Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder
         arg types: [java.lang.StringBuilder, java.util.Set<java.util.Map$Entry<?, ?>>]
         candidates:
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Map<?, ?>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.util.Map<?, ?>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder */
        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
            return appendTo(builder, (Iterable<? extends Map.Entry<?, ?>>) map.entrySet());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):A
         arg types: [A, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>]
         candidates:
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Map<?, ?>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.util.Map<?, ?>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):A */
        @CanIgnoreReturnValue
        @Beta
        public <A extends Appendable> A appendTo(A appendable, Iterable<? extends Map.Entry<?, ?>> entries) throws IOException {
            return appendTo((Appendable) appendable, entries.iterator());
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
            	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
            */
        @com.google.errorprone.annotations.CanIgnoreReturnValue
        @com.google.common.annotations.Beta
        public <A extends java.lang.Appendable> A appendTo(A r5, java.util.Iterator<? extends java.util.Map.Entry<?, ?>> r6) throws java.io.IOException {
            /*
                r4 = this;
                com.google.common.base.Preconditions.checkNotNull(r5)
                boolean r0 = r6.hasNext()
                if (r0 == 0) goto L_0x0063
                java.lang.Object r0 = r6.next()
                java.util.Map$Entry r0 = (java.util.Map.Entry) r0
                com.google.common.base.Joiner r1 = r4.joiner
                java.lang.Object r2 = r0.getKey()
                java.lang.CharSequence r1 = r1.toString(r2)
                r5.append(r1)
                java.lang.String r1 = r4.keyValueSeparator
                r5.append(r1)
                com.google.common.base.Joiner r1 = r4.joiner
                java.lang.Object r2 = r0.getValue()
                java.lang.CharSequence r1 = r1.toString(r2)
                r5.append(r1)
            L_0x002e:
                boolean r1 = r6.hasNext()
                if (r1 == 0) goto L_0x0063
                com.google.common.base.Joiner r1 = r4.joiner
                java.lang.String r1 = r1.separator
                r5.append(r1)
                java.lang.Object r1 = r6.next()
                java.util.Map$Entry r1 = (java.util.Map.Entry) r1
                com.google.common.base.Joiner r2 = r4.joiner
                java.lang.Object r3 = r1.getKey()
                java.lang.CharSequence r2 = r2.toString(r3)
                r5.append(r2)
                java.lang.String r2 = r4.keyValueSeparator
                r5.append(r2)
                com.google.common.base.Joiner r2 = r4.joiner
                java.lang.Object r3 = r1.getValue()
                java.lang.CharSequence r2 = r2.toString(r3)
                r5.append(r2)
                goto L_0x002e
            L_0x0063:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Iterator):java.lang.Appendable");
        }

        @CanIgnoreReturnValue
        @Beta
        public StringBuilder appendTo(StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> entries) {
            return appendTo(builder, entries.iterator());
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):A
         arg types: [java.lang.StringBuilder, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>]
         candidates:
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Map<?, ?>):A
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.lang.Iterable<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.StringBuilder, java.util.Map<?, ?>):java.lang.StringBuilder
          com.google.common.base.Joiner.MapJoiner.appendTo(java.lang.Appendable, java.util.Iterator<? extends java.util.Map$Entry<?, ?>>):A */
        @CanIgnoreReturnValue
        @Beta
        public StringBuilder appendTo(StringBuilder builder, Iterator<? extends Map.Entry<?, ?>> entries) {
            try {
                appendTo((Appendable) builder, entries);
                return builder;
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
        }

        public String join(Map<?, ?> map) {
            return join(map.entrySet());
        }

        @Beta
        public String join(Iterable<? extends Map.Entry<?, ?>> entries) {
            return join(entries.iterator());
        }

        @Beta
        public String join(Iterator<? extends Map.Entry<?, ?>> entries) {
            return appendTo(new StringBuilder(), entries).toString();
        }

        public MapJoiner useForNull(String nullText) {
            return new MapJoiner(this.joiner.useForNull(nullText), this.keyValueSeparator);
        }
    }
}
