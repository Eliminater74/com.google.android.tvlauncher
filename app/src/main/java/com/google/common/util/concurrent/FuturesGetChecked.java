package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
final class FuturesGetChecked {
    private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>() {
        public /* bridge */ /* synthetic */ Object apply(Object obj) {
            return apply((Constructor<?>) ((Constructor) obj));
        }

        public Boolean apply(Constructor<?> input) {
            return Boolean.valueOf(Arrays.asList(input.getParameterTypes()).contains(String.class));
        }
    }).reverse();

    private FuturesGetChecked() {
    }

    @CanIgnoreReturnValue
    static <V, X extends Exception> V getChecked(Future<V> future, Class<X> exceptionClass) throws Exception {
        return getChecked(bestGetCheckedTypeValidator(), future, exceptionClass);
    }

    @CanIgnoreReturnValue
    @VisibleForTesting
    static <V, X extends Exception> V getChecked(GetCheckedTypeValidator validator, Future<V> future, Class<X> exceptionClass) throws Exception {
        validator.validateClass(exceptionClass);
        try {
            return future.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(exceptionClass, e);
        } catch (ExecutionException e2) {
            wrapAndThrowExceptionOrError(e2.getCause(), exceptionClass);
            throw new AssertionError();
        }
    }

    @CanIgnoreReturnValue
    static <V, X extends Exception> V getChecked(Future<V> future, Class<X> exceptionClass, long timeout, TimeUnit unit) throws Exception {
        bestGetCheckedTypeValidator().validateClass(exceptionClass);
        try {
            return future.get(timeout, unit);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw newWithCause(exceptionClass, e);
        } catch (TimeoutException e2) {
            throw newWithCause(exceptionClass, e2);
        } catch (ExecutionException e3) {
            wrapAndThrowExceptionOrError(e3.getCause(), exceptionClass);
            throw new AssertionError();
        }
    }

    private static GetCheckedTypeValidator bestGetCheckedTypeValidator() {
        return GetCheckedTypeValidatorHolder.BEST_VALIDATOR;
    }

    @VisibleForTesting
    static GetCheckedTypeValidator weakSetValidator() {
        return GetCheckedTypeValidatorHolder.WeakSetValidator.INSTANCE;
    }

    @VisibleForTesting
    static GetCheckedTypeValidator classValueValidator() {
        return GetCheckedTypeValidatorHolder.ClassValueValidator.INSTANCE;
    }

    private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable cause, Class<X> exceptionClass) throws Exception {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        } else if (cause instanceof RuntimeException) {
            throw new UncheckedExecutionException(cause);
        } else {
            throw newWithCause(exceptionClass, cause);
        }
    }

    private static boolean hasConstructorUsableByGetChecked(Class<? extends Exception> exceptionClass) {
        try {
            Exception newWithCause = newWithCause(exceptionClass, new Exception());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: X
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
    private static <X extends java.lang.Exception> X newWithCause(java.lang.Class<X> r5, java.lang.Throwable r6) {
        /*
            java.lang.reflect.Constructor[] r0 = r5.getConstructors()
            java.util.List r0 = java.util.Arrays.asList(r0)
            java.util.List r1 = preferringStrings(r0)
            java.util.Iterator r1 = r1.iterator()
        L_0x0010:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x002f
            java.lang.Object r2 = r1.next()
            java.lang.reflect.Constructor r2 = (java.lang.reflect.Constructor) r2
            java.lang.Object r3 = newFromConstructor(r2, r6)
            java.lang.Exception r3 = (java.lang.Exception) r3
            if (r3 == 0) goto L_0x002e
            java.lang.Throwable r1 = r3.getCause()
            if (r1 != 0) goto L_0x002d
            r3.initCause(r6)
        L_0x002d:
            return r3
        L_0x002e:
            goto L_0x0010
        L_0x002f:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.String r2 = java.lang.String.valueOf(r5)
            java.lang.String r3 = java.lang.String.valueOf(r2)
            int r3 = r3.length()
            int r3 = r3 + 82
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>(r3)
            java.lang.String r3 = "No appropriate constructor for exception of type "
            r4.append(r3)
            r4.append(r2)
            java.lang.String r2 = " in response to chained exception"
            r4.append(r2)
            java.lang.String r2 = r4.toString()
            r1.<init>(r2, r6)
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.FuturesGetChecked.newWithCause(java.lang.Class, java.lang.Throwable):java.lang.Exception");
    }

    private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> constructors) {
        return WITH_STRING_PARAM_FIRST.sortedCopy(constructors);
    }

    @NullableDecl
    private static <X> X newFromConstructor(Constructor<X> constructor, Throwable cause) {
        Class<?>[] paramTypes = constructor.getParameterTypes();
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];
            if (paramType.equals(String.class)) {
                params[i] = cause.toString();
            } else if (!paramType.equals(Throwable.class)) {
                return null;
            } else {
                params[i] = cause;
            }
        }
        try {
            return constructor.newInstance(params);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException e) {
            return null;
        }
    }

    @VisibleForTesting
    static boolean isCheckedException(Class<? extends Exception> type) {
        return !RuntimeException.class.isAssignableFrom(type);
    }

    @VisibleForTesting
    static void checkExceptionClassValidity(Class<? extends Exception> exceptionClass) {
        Preconditions.checkArgument(isCheckedException(exceptionClass), "Futures.getChecked exception type (%s) must not be a RuntimeException", exceptionClass);
        Preconditions.checkArgument(hasConstructorUsableByGetChecked(exceptionClass), "Futures.getChecked exception type (%s) must be an accessible class with an accessible constructor whose parameters (if any) must be of type String and/or Throwable", exceptionClass);
    }

    @VisibleForTesting
    interface GetCheckedTypeValidator {
        void validateClass(Class<? extends Exception> cls);
    }

    @VisibleForTesting
    static class GetCheckedTypeValidatorHolder {
        static final String CLASS_VALUE_VALIDATOR_NAME = String.valueOf(GetCheckedTypeValidatorHolder.class.getName()).concat("$ClassValueValidator");
        static final GetCheckedTypeValidator BEST_VALIDATOR = getBestValidator();

        GetCheckedTypeValidatorHolder() {
        }

        static GetCheckedTypeValidator getBestValidator() {
            try {
                return (GetCheckedTypeValidator) Class.forName(CLASS_VALUE_VALIDATOR_NAME).getEnumConstants()[0];
            } catch (Throwable th) {
                return FuturesGetChecked.weakSetValidator();
            }
        }

        @IgnoreJRERequirement
        enum ClassValueValidator implements GetCheckedTypeValidator {
            INSTANCE;

            private static final ClassValue<Boolean> isValidClass = new ClassValue<Boolean>() {
                /* access modifiers changed from: protected */
                public Boolean computeValue(Class<?> type) {
                    FuturesGetChecked.checkExceptionClassValidity(type.asSubclass(Exception.class));
                    return true;
                }
            };

            public void validateClass(Class<? extends Exception> exceptionClass) {
                isValidClass.get(exceptionClass);
            }
        }

        enum WeakSetValidator implements GetCheckedTypeValidator {
            INSTANCE;

            private static final Set<WeakReference<Class<? extends Exception>>> validClasses = new CopyOnWriteArraySet();

            public void validateClass(Class<? extends Exception> exceptionClass) {
                for (WeakReference<Class<? extends Exception>> knownGood : validClasses) {
                    if (exceptionClass.equals(knownGood.get())) {
                        return;
                    }
                }
                FuturesGetChecked.checkExceptionClassValidity(exceptionClass);
                if (validClasses.size() > 1000) {
                    validClasses.clear();
                }
                validClasses.add(new WeakReference(exceptionClass));
            }
        }
    }
}
