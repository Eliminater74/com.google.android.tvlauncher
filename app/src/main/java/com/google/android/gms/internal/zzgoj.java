package com.google.android.gms.internal;

import com.google.android.gms.internal.zzgoj.zza;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: GeneratedMessageLite */
public abstract class zzgoj<MessageType extends zzgoj<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzgmp<MessageType, BuilderType> {
    private static Map<Object, zzgoj<?, ?>> zzd = new ConcurrentHashMap();
    protected zzgre zzb = zzgre.zza();
    protected int zzc = -1;

    static <T extends zzgoj<?, ?>> T zza(Class<T> cls) {
        T t = (zzgoj) zzd.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (zzgoj) zzd.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t != null) {
            return t;
        }
        String valueOf = String.valueOf(cls.getName());
        throw new IllegalStateException(valueOf.length() != 0 ? "Unable to get default instance for: ".concat(valueOf) : new String("Unable to get default instance for: "));
    }

    protected static <T extends zzgoj<?, ?>> void zza(Class<T> cls, T t) {
        zzd.put(cls, t);
    }

    protected static Object zza(zzgpt zzgpt, String str, Object[] objArr) {
        return new zzgqh(zzgpt, str, objArr);
    }

    public static <ContainingType extends zzgpt, Type> zzf<ContainingType, Type> zza(ContainingType containingtype, Type type, zzgpt zzgpt, zzgop<?> zzgop, int i, zzgrr zzgrr, Class cls) {
        return new zzf(containingtype, type, zzgpt, new zze(null, 77815057, zzgrr, false, false), cls);
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected static final <T extends com.google.android.gms.internal.zzgoj<T, ?>> boolean zza(T r4, boolean r5) {
        /*
            int r5 = com.google.android.gms.internal.zzgoj.zzg.zzb
            r0 = 0
            java.lang.Object r5 = r4.zza(r5, r0, r0)
            java.lang.Byte r5 = (java.lang.Byte) r5
            byte r5 = r5.byteValue()
            r1 = 1
            if (r5 != r1) goto L_0x0012
            return r1
        L_0x0012:
            r2 = 0
            if (r5 != 0) goto L_0x0016
            return r2
        L_0x0016:
            int r5 = com.google.android.gms.internal.zzgoj.zzg.zza
            java.lang.Boolean r3 = java.lang.Boolean.FALSE
            java.lang.Object r4 = r4.zza(r5, r3, r0)
            if (r4 == 0) goto L_0x0022
            goto L_0x0023
        L_0x0022:
            r1 = 0
        L_0x0023:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgoj.zza(com.google.android.gms.internal.zzgoj, boolean):boolean");
    }

    protected static zzgoq zzn() {
        return zzgom.zzd();
    }

    protected static <E> zzgos<E> zzo() {
        return zzgqg.zzd();
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
    static <T extends com.google.android.gms.internal.zzgoj<T, ?>> T zza(T r2, com.google.android.gms.internal.zzgnk r3, com.google.android.gms.internal.zzgnv r4) throws com.google.android.gms.internal.zzgot {
        /*
            int r0 = com.google.android.gms.internal.zzgoj.zzg.zze
            r1 = 0
            java.lang.Object r2 = r2.zza(r0, r1, r1)
            com.google.android.gms.internal.zzgoj r2 = (com.google.android.gms.internal.zzgoj) r2
            com.google.android.gms.internal.zzgqf r0 = com.google.android.gms.internal.zzgqf.zza()     // Catch:{ IOException -> 0x002e, RuntimeException -> 0x001d }
            com.google.android.gms.internal.zzgql r0 = r0.zza(r2)     // Catch:{ IOException -> 0x002e, RuntimeException -> 0x001d }
            com.google.android.gms.internal.zzgnn r3 = com.google.android.gms.internal.zzgnn.zza(r3)     // Catch:{ IOException -> 0x002e, RuntimeException -> 0x001d }
            r0.zza(r2, r3, r4)     // Catch:{ IOException -> 0x002e, RuntimeException -> 0x001d }
            r2.zzl()     // Catch:{ IOException -> 0x002e, RuntimeException -> 0x001d }
            return r2
        L_0x001d:
            r2 = move-exception
            java.lang.Throwable r3 = r2.getCause()
            boolean r3 = r3 instanceof com.google.android.gms.internal.zzgot
            if (r3 == 0) goto L_0x002d
            java.lang.Throwable r2 = r2.getCause()
            com.google.android.gms.internal.zzgot r2 = (com.google.android.gms.internal.zzgot) r2
            throw r2
        L_0x002d:
            throw r2
        L_0x002e:
            r3 = move-exception
            java.lang.Throwable r4 = r3.getCause()
            boolean r4 = r4 instanceof com.google.android.gms.internal.zzgot
            if (r4 == 0) goto L_0x003e
            java.lang.Throwable r2 = r3.getCause()
            com.google.android.gms.internal.zzgot r2 = (com.google.android.gms.internal.zzgot) r2
            throw r2
        L_0x003e:
            com.google.android.gms.internal.zzgot r4 = new com.google.android.gms.internal.zzgot
            java.lang.String r3 = r3.getMessage()
            r4.<init>(r3)
            com.google.android.gms.internal.zzgot r2 = r4.zza(r2)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgoj.zza(com.google.android.gms.internal.zzgoj, com.google.android.gms.internal.zzgnk, com.google.android.gms.internal.zzgnv):com.google.android.gms.internal.zzgoj");
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
    private static <T extends com.google.android.gms.internal.zzgoj<T, ?>> T zzb(T r6, byte[] r7) throws com.google.android.gms.internal.zzgot {
        /*
            int r0 = com.google.android.gms.internal.zzgoj.zzg.zze
            r1 = 0
            java.lang.Object r6 = r6.zza(r0, r1, r1)
            com.google.android.gms.internal.zzgoj r6 = (com.google.android.gms.internal.zzgoj) r6
            com.google.android.gms.internal.zzgqf r0 = com.google.android.gms.internal.zzgqf.zza()     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            com.google.android.gms.internal.zzgql r0 = r0.zza(r6)     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r3 = 0
            int r4 = r7.length     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            com.google.android.gms.internal.zzgmv r5 = new com.google.android.gms.internal.zzgmv     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r5.<init>()     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r1 = r6
            r2 = r7
            r0.zza(r1, r2, r3, r4, r5)     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r6.zzl()     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            int r7 = r6.zza     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            if (r7 != 0) goto L_0x0026
            return r6
        L_0x0026:
            java.lang.RuntimeException r7 = new java.lang.RuntimeException     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            r7.<init>()     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
            throw r7     // Catch:{ IOException -> 0x0036, IndexOutOfBoundsException -> 0x002c }
        L_0x002c:
            r7 = move-exception
            com.google.android.gms.internal.zzgot r7 = com.google.android.gms.internal.zzgot.zza()
            com.google.android.gms.internal.zzgot r6 = r7.zza(r6)
            throw r6
        L_0x0036:
            r7 = move-exception
            java.lang.Throwable r0 = r7.getCause()
            boolean r0 = r0 instanceof com.google.android.gms.internal.zzgot
            if (r0 == 0) goto L_0x0046
            java.lang.Throwable r6 = r7.getCause()
            com.google.android.gms.internal.zzgot r6 = (com.google.android.gms.internal.zzgot) r6
            throw r6
        L_0x0046:
            com.google.android.gms.internal.zzgot r0 = new com.google.android.gms.internal.zzgot
            java.lang.String r7 = r7.getMessage()
            r0.<init>(r7)
            com.google.android.gms.internal.zzgot r6 = r0.zza(r6)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgoj.zzb(com.google.android.gms.internal.zzgoj, byte[]):com.google.android.gms.internal.zzgoj");
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected static <T extends com.google.android.gms.internal.zzgoj<T, ?>> T zza(T r5, com.google.android.gms.internal.zzgnb r6) throws com.google.android.gms.internal.zzgot {
        /*
            com.google.android.gms.internal.zzgnv r0 = com.google.android.gms.internal.zzgnv.zza()
            com.google.android.gms.internal.zzgoj r5 = zza(r5, r6, r0)
            r6 = 0
            r0 = 1
            r1 = 0
            if (r5 == 0) goto L_0x0057
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            boolean r2 = r2.booleanValue()
            int r3 = com.google.android.gms.internal.zzgoj.zzg.zzb
            java.lang.Object r3 = r5.zza(r3, r1, r1)
            java.lang.Byte r3 = (java.lang.Byte) r3
            byte r3 = r3.byteValue()
            if (r3 != r0) goto L_0x0025
            r3 = 1
            goto L_0x0045
        L_0x0025:
            if (r3 != 0) goto L_0x0029
            r3 = 0
            goto L_0x0045
        L_0x0029:
            int r3 = com.google.android.gms.internal.zzgoj.zzg.zza
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            java.lang.Object r3 = r5.zza(r3, r4, r1)
            if (r3 == 0) goto L_0x0036
            r3 = 1
            goto L_0x0037
        L_0x0036:
            r3 = 0
        L_0x0037:
            if (r2 == 0) goto L_0x0044
            int r2 = com.google.android.gms.internal.zzgoj.zzg.zzc
            if (r3 == 0) goto L_0x003f
            r4 = r5
            goto L_0x0040
        L_0x003f:
            r4 = r1
        L_0x0040:
            r5.zza(r2, r4, r1)
        L_0x0044:
        L_0x0045:
            if (r3 == 0) goto L_0x0048
            goto L_0x0057
        L_0x0048:
            com.google.android.gms.internal.zzgrc r6 = new com.google.android.gms.internal.zzgrc
            r6.<init>(r5)
            com.google.android.gms.internal.zzgot r6 = r6.zza()
            com.google.android.gms.internal.zzgot r5 = r6.zza(r5)
            throw r5
        L_0x0057:
            if (r5 == 0) goto L_0x00a1
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            boolean r2 = r2.booleanValue()
            int r3 = com.google.android.gms.internal.zzgoj.zzg.zzb
            java.lang.Object r3 = r5.zza(r3, r1, r1)
            java.lang.Byte r3 = (java.lang.Byte) r3
            byte r3 = r3.byteValue()
            if (r3 != r0) goto L_0x0072
            r6 = 1
            goto L_0x008f
        L_0x0072:
            if (r3 != 0) goto L_0x0075
            goto L_0x008f
        L_0x0075:
            int r3 = com.google.android.gms.internal.zzgoj.zzg.zza
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            java.lang.Object r3 = r5.zza(r3, r4, r1)
            if (r3 == 0) goto L_0x0081
            r6 = 1
        L_0x0081:
            if (r2 == 0) goto L_0x008e
            int r0 = com.google.android.gms.internal.zzgoj.zzg.zzc
            if (r6 == 0) goto L_0x0089
            r2 = r5
            goto L_0x008a
        L_0x0089:
            r2 = r1
        L_0x008a:
            r5.zza(r0, r2, r1)
        L_0x008e:
        L_0x008f:
            if (r6 == 0) goto L_0x0092
            goto L_0x00a1
        L_0x0092:
            com.google.android.gms.internal.zzgrc r6 = new com.google.android.gms.internal.zzgrc
            r6.<init>(r5)
            com.google.android.gms.internal.zzgot r6 = r6.zza()
            com.google.android.gms.internal.zzgot r5 = r6.zza(r5)
            throw r5
        L_0x00a1:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgoj.zza(com.google.android.gms.internal.zzgoj, com.google.android.gms.internal.zzgnb):com.google.android.gms.internal.zzgoj");
    }

    private static <T extends zzgoj<T, ?>> T zza(T t, zzgnb zzgnb, zzgnv zzgnv) throws zzgot {
        T zza2;
        try {
            zzgnk zzf2 = zzgnb.zzf();
            zza2 = zza(t, zzf2, zzgnv);
            zzf2.zza(0);
            return zza2;
        } catch (zzgot e) {
            throw e.zza(zza2);
        } catch (zzgot e2) {
            throw e2;
        }
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
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected static <T extends com.google.android.gms.internal.zzgoj<T, ?>> T zza(T r5, byte[] r6) throws com.google.android.gms.internal.zzgot {
        /*
            com.google.android.gms.internal.zzgoj r5 = zzb(r5, r6)
            if (r5 == 0) goto L_0x004f
            java.lang.Boolean r6 = java.lang.Boolean.TRUE
            boolean r6 = r6.booleanValue()
            int r0 = com.google.android.gms.internal.zzgoj.zzg.zzb
            r1 = 0
            java.lang.Object r0 = r5.zza(r0, r1, r1)
            java.lang.Byte r0 = (java.lang.Byte) r0
            byte r0 = r0.byteValue()
            r2 = 0
            r3 = 1
            if (r0 != r3) goto L_0x0020
            r2 = 1
            goto L_0x003d
        L_0x0020:
            if (r0 != 0) goto L_0x0023
            goto L_0x003d
        L_0x0023:
            int r0 = com.google.android.gms.internal.zzgoj.zzg.zza
            java.lang.Boolean r4 = java.lang.Boolean.FALSE
            java.lang.Object r0 = r5.zza(r0, r4, r1)
            if (r0 == 0) goto L_0x002f
            r2 = 1
        L_0x002f:
            if (r6 == 0) goto L_0x003c
            int r6 = com.google.android.gms.internal.zzgoj.zzg.zzc
            if (r2 == 0) goto L_0x0037
            r0 = r5
            goto L_0x0038
        L_0x0037:
            r0 = r1
        L_0x0038:
            r5.zza(r6, r0, r1)
        L_0x003c:
        L_0x003d:
            if (r2 == 0) goto L_0x0040
            goto L_0x004f
        L_0x0040:
            com.google.android.gms.internal.zzgrc r6 = new com.google.android.gms.internal.zzgrc
            r6.<init>(r5)
            com.google.android.gms.internal.zzgot r6 = r6.zza()
            com.google.android.gms.internal.zzgot r5 = r6.zza(r5)
            throw r5
        L_0x004f:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgoj.zza(com.google.android.gms.internal.zzgoj, byte[]):com.google.android.gms.internal.zzgoj");
    }

    /* access modifiers changed from: protected */
    public abstract Object zza(int i, Object obj, Object obj2);

    /* access modifiers changed from: protected */
    public abstract Object zzb() throws Exception;

    public String toString() {
        return zzgpw.zza(this, super.toString());
    }

    public int hashCode() {
        if (this.zza != 0) {
            return this.zza;
        }
        this.zza = zzgqf.zza().zza(this).zza(this);
        return this.zza;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!((zzgoj) zza(zzg.zzg, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return false;
        }
        return zzgqf.zza().zza(this).zza(this, (zzgoj) obj);
    }

    /* access modifiers changed from: protected */
    public void zzl() {
        zza(zzg.zzd, (Object) null, (Object) null);
        this.zzb.zzc();
    }

    public final boolean zzm() {
        boolean booleanValue = Boolean.TRUE.booleanValue();
        byte byteValue = ((Byte) zza(zzg.zzb, (Object) null, (Object) null)).byteValue();
        boolean z = true;
        if (byteValue == 1) {
            return true;
        }
        if (byteValue == 0) {
            return false;
        }
        if (zza(zzg.zza, Boolean.FALSE, (Object) null) == null) {
            z = false;
        }
        if (booleanValue) {
            zza(zzg.zzc, z ? this : null, (Object) null);
        }
        return z;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.internal.zzgql.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void
     arg types: [com.google.android.gms.internal.zzgoj, com.google.android.gms.internal.zzgnr]
     candidates:
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, java.lang.Object):boolean
      com.google.android.gms.internal.zzgql.zza(java.lang.Object, com.google.android.gms.internal.zzgrx):void */
    public void zza(zzgnp zzgnp) throws IOException {
        zzgqf.zza().zza((Class) getClass()).zza((Object) this, (zzgrx) zzgnr.zza(zzgnp));
    }

    public int zza() {
        if (this.zzc == -1) {
            this.zzc = zzgqf.zza().zza(this).zzb(this);
        }
        return this.zzc;
    }

    public final /* synthetic */ zzgpu zzp() {
        zza zza2 = (zza) zza(zzg.zzf, (Object) null, (Object) null);
        zza2.zza(this);
        return zza2;
    }

    public final /* synthetic */ zzgpu zzq() {
        return (zza) zza(zzg.zzf, (Object) null, (Object) null);
    }

    public final /* synthetic */ zzgpt zzr() {
        return (zzgoj) zza(zzg.zzg, (Object) null, (Object) null);
    }

    /* compiled from: GeneratedMessageLite */
    public static class zzf<ContainingType extends zzgpt, Type> extends zzgnt<ContainingType, Type> {
        final zzgpt zza;
        final zze zzb;
        private final ContainingType zzc;
        private final Type zzd;

        zzf(ContainingType containingtype, Type type, zzgpt zzgpt, zze zze, Class cls) {
            if (containingtype == null) {
                throw new IllegalArgumentException("Null containingTypeDefaultInstance");
            } else if (zze.zzc == zzgrr.MESSAGE && zzgpt == null) {
                throw new IllegalArgumentException("Null messageDefaultInstance");
            } else {
                this.zzc = containingtype;
                this.zzd = type;
                this.zza = zzgpt;
                this.zzb = zze;
            }
        }
    }

    /* JADX INFO: Failed to restore enum class, 'enum' modifier removed */
    /* compiled from: GeneratedMessageLite */
    public static final class zzg {
        public static final int zza = 1;
        public static final int zzb = 2;
        public static final int zzc = 3;
        public static final int zzd = 4;
        public static final int zze = 5;
        public static final int zzf = 6;
        public static final int zzg = 7;
        public static final int zzh = 8;
        public static final int zzi = 1;
        public static final int zzj = 2;
        public static final int zzk = 1;
        public static final int zzl = 2;
        private static final /* synthetic */ int[] zzm = {zza, zzb, zzc, zzd, zze, zzf, zzg, zzh};
        private static final /* synthetic */ int[] zzn = {zzi, zzj};
        private static final /* synthetic */ int[] zzo = {zzk, zzl};

        /* renamed from: values$50KLMJ33DTMIUPRFDTJMOP9FE1P6UT3FC9QMCBQ7CLN6ASJ1EHIM8JB5EDPM2PR59HKN8P949LIN8Q3FCHA6UIBEEPNMMP9R0 */
        public static int[] m43x126d66cb() {
            return (int[]) zzm.clone();
        }
    }

    /* compiled from: GeneratedMessageLite */
    public static abstract class zzc<MessageType extends zzd<MessageType, BuilderType>, BuilderType extends zzc<MessageType, BuilderType>> extends zza<MessageType, BuilderType> implements zzgpv {
        protected zzc(MessageType messagetype) {
            super(messagetype);
        }

        /* access modifiers changed from: protected */
        public final void zzb() {
            if (this.zzb) {
                super.zzb();
                ((zzd) this.zza).zzd = (zzgoa) ((zzd) this.zza).zzd.clone();
            }
        }

        public final /* synthetic */ zzgoj zzc() {
            return (zzd) zze();
        }

        public final /* synthetic */ zzgpt zze() {
            if (this.zzb) {
                return (zzd) this.zza;
            }
            ((zzd) this.zza).zzd.zzc();
            return (zzd) super.zze();
        }
    }

    /* compiled from: GeneratedMessageLite */
    public static abstract class zzd<MessageType extends zzd<MessageType, BuilderType>, BuilderType extends zzc<MessageType, BuilderType>> extends zzgoj<MessageType, BuilderType> implements zzgpv {
        protected zzgoa<zze> zzd = zzgoa.zza();

        /* access modifiers changed from: protected */
        public final boolean zze() {
            return this.zzd.zzg();
        }

        /* access modifiers changed from: protected */
        public final void zzl() {
            zzgoj.super.zzl();
            this.zzd.zzc();
        }

        /* access modifiers changed from: protected */
        public final zza zzf() {
            return new zza(this, false, null);
        }

        /* access modifiers changed from: protected */
        public final int zzg() {
            return this.zzd.zzh();
        }

        /* compiled from: GeneratedMessageLite */
        public class zza {
            private final Iterator<Map.Entry<zze, Object>> zza;
            private final boolean zzc;
            private Map.Entry<zze, Object> zzb;

            private zza(boolean z) {
                this.zza = zzd.this.zzd.zze();
                if (this.zza.hasNext()) {
                    this.zzb = this.zza.next();
                }
                this.zzc = false;
            }

            /* synthetic */ zza(zzd zzd2, boolean z, zzgok zzgok) {
                this(false);
            }

            public final void zza(int i, zzgnp zzgnp) throws IOException {
                while (true) {
                    Map.Entry<zze, Object> entry = this.zzb;
                    if (entry != null && entry.getKey().zzb < 536870912) {
                        zze key = this.zzb.getKey();
                        if (!this.zzc || key.zzc.zza() != zzgrw.MESSAGE) {
                            zzgoa.zza(key, this.zzb.getValue(), zzgnp);
                        } else {
                            zzgnp.zzb(key.zzb, (zzgpt) this.zzb.getValue());
                        }
                        if (this.zza.hasNext()) {
                            this.zzb = this.zza.next();
                        } else {
                            this.zzb = null;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    /* compiled from: GeneratedMessageLite */
    public static class zzb<T extends zzgoj<T, ?>> extends zzgmr<T> {
        private T zza;

        public zzb(T t) {
            this.zza = t;
        }

        public final /* synthetic */ Object zza(zzgnk zzgnk, zzgnv zzgnv) throws zzgot {
            return zzgoj.zza(this.zza, zzgnk, zzgnv);
        }
    }

    /* compiled from: GeneratedMessageLite */
    static final class zze implements zzgoc<zze> {
        final zzgop<?> zza = null;
        final int zzb = 77815057;
        final zzgrr zzc;
        final boolean zzd;
        final boolean zze;

        zze(zzgop<?> zzgop, int i, zzgrr zzgrr, boolean z, boolean z2) {
            this.zzc = zzgrr;
            this.zzd = false;
            this.zze = false;
        }

        public final int zza() {
            return this.zzb;
        }

        public final zzgrr zzb() {
            return this.zzc;
        }

        public final zzgrw zzc() {
            return this.zzc.zza();
        }

        public final boolean zzd() {
            return false;
        }

        public final boolean zze() {
            return false;
        }

        public final zzgpu zza(zzgpu zzgpu, zzgpt zzgpt) {
            return ((zza) zzgpu).zza((zzgoj) zzgpt);
        }

        public final zzgqa zza(zzgqa zzgqa, zzgqa zzgqa2) {
            throw new UnsupportedOperationException();
        }

        public final /* synthetic */ int compareTo(Object obj) {
            return this.zzb - ((zze) obj).zzb;
        }
    }

    /* compiled from: GeneratedMessageLite */
    public static abstract class zza<MessageType extends zzgoj<MessageType, BuilderType>, BuilderType extends zza<MessageType, BuilderType>> extends zzgmq<MessageType, BuilderType> {
        private final MessageType zzc;
        protected MessageType zza;
        protected boolean zzb = false;

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: MessageType
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
            	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
            */
        protected zza(MessageType r3) {
            /*
                r2 = this;
                r2.<init>()
                r2.zzc = r3
                int r0 = com.google.android.gms.internal.zzgoj.zzg.zze
                r1 = 0
                java.lang.Object r3 = r3.zza(r0, r1, r1)
                com.google.android.gms.internal.zzgoj r3 = (com.google.android.gms.internal.zzgoj) r3
                r2.zza = r3
                r3 = 0
                r2.zzb = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgoj.zza.<init>(com.google.android.gms.internal.zzgoj):void");
        }

        private static void zza(MessageType messagetype, MessageType messagetype2) {
            zzgqf.zza().zza((Object) messagetype).zzb(messagetype, messagetype2);
        }

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: MessageType
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
        protected void zzb() {
            /*
                r3 = this;
                boolean r0 = r3.zzb
                if (r0 == 0) goto L_0x001a
                MessageType r0 = r3.zza
                int r1 = com.google.android.gms.internal.zzgoj.zzg.zze
                r2 = 0
                java.lang.Object r0 = r0.zza(r1, r2, r2)
                com.google.android.gms.internal.zzgoj r0 = (com.google.android.gms.internal.zzgoj) r0
                MessageType r1 = r3.zza
                zza(r0, r1)
                r3.zza = r0
                r0 = 0
                r3.zzb = r0
            L_0x001a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgoj.zza.zzb():void");
        }

        /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
         method: com.google.android.gms.internal.zzgoj.zza(com.google.android.gms.internal.zzgoj, boolean):boolean
         arg types: [MessageType, int]
         candidates:
          com.google.android.gms.internal.zzgoj.zza(com.google.android.gms.internal.zzgoj, com.google.android.gms.internal.zzgnb):T
          com.google.android.gms.internal.zzgoj.zza(com.google.android.gms.internal.zzgoj, byte[]):T
          com.google.android.gms.internal.zzgoj.zza(java.lang.Class, com.google.android.gms.internal.zzgoj):void
          com.google.android.gms.internal.zzgoj.zza(com.google.android.gms.internal.zzgoj, boolean):boolean */
        public final boolean zzm() {
            return zzgoj.zza((zzgoj) this.zza, false);
        }

        /* renamed from: zzc */
        public MessageType zze() {
            if (this.zzb) {
                return this.zza;
            }
            this.zza.zzl();
            this.zzb = true;
            return this.zza;
        }

        /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
            jadx.core.utils.exceptions.JadxRuntimeException: Not class type: MessageType
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
            	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
            */
        public final MessageType zzd() {
            /*
                r7 = this;
                com.google.android.gms.internal.zzgpt r0 = r7.zze()
                com.google.android.gms.internal.zzgoj r0 = (com.google.android.gms.internal.zzgoj) r0
                java.lang.Boolean r1 = java.lang.Boolean.TRUE
                boolean r1 = r1.booleanValue()
                int r2 = com.google.android.gms.internal.zzgoj.zzg.zzb
                r3 = 0
                java.lang.Object r2 = r0.zza(r2, r3, r3)
                java.lang.Byte r2 = (java.lang.Byte) r2
                byte r2 = r2.byteValue()
                r4 = 0
                r5 = 1
                if (r2 != r5) goto L_0x0021
                r4 = 1
                goto L_0x003e
            L_0x0021:
                if (r2 != 0) goto L_0x0024
                goto L_0x003e
            L_0x0024:
                int r2 = com.google.android.gms.internal.zzgoj.zzg.zza
                java.lang.Boolean r6 = java.lang.Boolean.FALSE
                java.lang.Object r2 = r0.zza(r2, r6, r3)
                if (r2 == 0) goto L_0x0030
                r4 = 1
            L_0x0030:
                if (r1 == 0) goto L_0x003d
                int r1 = com.google.android.gms.internal.zzgoj.zzg.zzc
                if (r4 == 0) goto L_0x0038
                r2 = r0
                goto L_0x0039
            L_0x0038:
                r2 = r3
            L_0x0039:
                r0.zza(r1, r2, r3)
            L_0x003d:
            L_0x003e:
                if (r4 == 0) goto L_0x0041
                return r0
            L_0x0041:
                com.google.android.gms.internal.zzgrc r1 = new com.google.android.gms.internal.zzgrc
                r1.<init>(r0)
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgoj.zza.zzd():com.google.android.gms.internal.zzgoj");
        }

        public final BuilderType zza(MessageType messagetype) {
            zzb();
            zza(this.zza, messagetype);
            return this;
        }

        public final /* synthetic */ zzgmq zza() {
            return (zza) clone();
        }

        public final /* synthetic */ zzgpt zzf() {
            zzgoj zzgoj = (zzgoj) zze();
            boolean booleanValue = Boolean.TRUE.booleanValue();
            byte byteValue = ((Byte) zzgoj.zza(zzg.zzb, (Object) null, (Object) null)).byteValue();
            boolean z = false;
            if (byteValue == 1) {
                z = true;
            } else if (byteValue != 0) {
                if (zzgoj.zza(zzg.zza, Boolean.FALSE, (Object) null) != null) {
                    z = true;
                }
                if (booleanValue) {
                    zzgoj.zza(zzg.zzc, z ? zzgoj : null, (Object) null);
                }
            }
            if (z) {
                return zzgoj;
            }
            throw new zzgrc(zzgoj);
        }

        public final /* synthetic */ zzgpt zzr() {
            return this.zzc;
        }

        public /* synthetic */ Object clone() throws CloneNotSupportedException {
            zza zza2 = (zza) ((zzgoj) this.zzc).zza(zzg.zzf, (Object) null, (Object) null);
            zza2.zza((zzgoj) zze());
            return zza2;
        }
    }
}
