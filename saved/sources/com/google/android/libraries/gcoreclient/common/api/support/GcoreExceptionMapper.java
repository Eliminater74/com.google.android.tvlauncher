package com.google.android.libraries.gcoreclient.common.api.support;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;

public final class GcoreExceptionMapper {
    private static final Map<Class<? extends Throwable>, SystemExceptionSupplier<?>> systemMap = new HashMap();
    private final Map<Class<? extends Throwable>, ExceptionSupplier<?>> map;

    public interface ExceptionSupplier<I extends Throwable> {
        Throwable newException(I i, @Nullable Throwable th);
    }

    interface SystemExceptionSupplier<O extends Throwable> {
        O newException(@Nullable String str, Throwable th);
    }

    static {
        systemMap.put(RuntimeException.class, GcoreExceptionMapper$$Lambda$1.$instance);
        systemMap.put(Error.class, GcoreExceptionMapper$$Lambda$2.$instance);
        systemMap.put(Exception.class, GcoreExceptionMapper$$Lambda$3.$instance);
        systemMap.put(Throwable.class, GcoreExceptionMapper$$Lambda$4.$instance);
        systemMap.put(ExecutionException.class, GcoreExceptionMapper$$Lambda$5.$instance);
        systemMap.put(IllegalStateException.class, GcoreExceptionMapper$$Lambda$6.$instance);
        systemMap.put(IllegalArgumentException.class, GcoreExceptionMapper$$Lambda$7.$instance);
    }

    private static final class UnrecognizedThrowable extends Throwable {
        UnrecognizedThrowable(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static final class UnrecognizedException extends Exception {
        UnrecognizedException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static final class UnrecognizedRuntimeException extends RuntimeException {
        UnrecognizedRuntimeException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private static final class UnrecognizedError extends Error {
        UnrecognizedError(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public GcoreExceptionMapper(Map<Class<? extends Throwable>, ExceptionSupplier<?>> map2) {
        this.map = map2;
    }

    public Exception mapExceptions(Exception t) {
        return (Exception) mapThrowables(t);
    }

    public Throwable mapThrowables(Throwable t) {
        Throwable cause = t.getCause();
        ExceptionSupplier<?> supplier = findSupplier(t);
        boolean needsWrap = supplier != null;
        if (cause != null) {
            Throwable newCause = mapThrowables(cause);
            if (needsWrap) {
                return wrapException(t, newCause, supplier);
            }
            if (newCause == cause) {
                return t;
            }
            return wrapUnrecongizedException(t, newCause);
        } else if (!needsWrap) {
            return t;
        } else {
            return wrapException(t, null, supplier);
        }
    }

    private static Throwable wrapUnrecongizedException(Throwable t, Throwable newCause) {
        return wrapException(t, newCause, new GcoreExceptionMapper$$Lambda$0(findSystemSupplier(t)));
    }

    private static SystemExceptionSupplier<?> findSystemSupplier(Throwable t) {
        for (Class<?> clazz = t.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            SystemExceptionSupplier<?> supplier = systemMap.get(clazz);
            if (supplier != null) {
                return supplier;
            }
        }
        throw new AssertionError("Map contains Throwable, must be found");
    }

    private static <I extends Throwable> Throwable wrapException(I t, @Nullable Throwable newCause, ExceptionSupplier<?> supplier) {
        Throwable wrapped = supplier.newException(t, newCause);
        Class<?> wrappedType = getRuntimeType(wrapped);
        Class<?> originalType = getRuntimeType(t);
        Preconditions.checkState(wrappedType == originalType, "Checked exception type must match: %s:%s, %s:%s", wrapped.getClass(), wrappedType, t.getClass(), originalType);
        if (newCause != wrapped.getCause()) {
            String valueOf = String.valueOf(newCause);
            String valueOf2 = String.valueOf(wrapped.getCause());
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 42 + String.valueOf(valueOf2).length());
            sb.append("Wrapper didn't propagate cause: ");
            sb.append(valueOf);
            sb.append(" but got: ");
            sb.append(valueOf2);
            throw new IllegalArgumentException(sb.toString());
        } else if (newCause != wrapped.getCause()) {
            String valueOf3 = String.valueOf(newCause);
            String valueOf4 = String.valueOf(wrapped.getCause());
            StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf3).length() + 42 + String.valueOf(valueOf4).length());
            sb2.append("Wrapper didn't propagate cause: ");
            sb2.append(valueOf3);
            sb2.append(" but got: ");
            sb2.append(valueOf4);
            throw new IllegalArgumentException(sb2.toString());
        } else if (!Objects.equal(t.getMessage(), wrapped.getMessage())) {
            throw new IllegalArgumentException("Wrapper didn't propagate message");
        } else if (Objects.equal(t.getLocalizedMessage(), wrapped.getLocalizedMessage())) {
            StackTraceElement[] stack = t.getStackTrace();
            if (stack != null) {
                wrapped.setStackTrace(stack);
            }
            return wrapped;
        } else {
            throw new IllegalArgumentException("Wrapper didn't propagate message");
        }
    }

    private static Class<?> getRuntimeType(Throwable t) {
        if (t instanceof RuntimeException) {
            return RuntimeException.class;
        }
        if (t instanceof Exception) {
            return Exception.class;
        }
        if (t instanceof Error) {
            return Error.class;
        }
        return Throwable.class;
    }

    @Nullable
    private <I extends Throwable> ExceptionSupplier<I> findSupplier(Throwable t) {
        for (Class<?> clazz = t.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            ExceptionSupplier<I> supplier = this.map.get(clazz);
            if (supplier != null) {
                return supplier;
            }
        }
        return null;
    }
}
