package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@GwtIncompatible
@Beta
public final class SimpleTimeLimiter implements TimeLimiter {
    private final ExecutorService executor;

    private SimpleTimeLimiter(ExecutorService executor2) {
        this.executor = (ExecutorService) Preconditions.checkNotNull(executor2);
    }

    public static SimpleTimeLimiter create(ExecutorService executor2) {
        return new SimpleTimeLimiter(executor2);
    }

    public <T> T newProxy(T target, Class<T> interfaceType, long timeoutDuration, TimeUnit timeoutUnit) {
        Preconditions.checkNotNull(target);
        Preconditions.checkNotNull(interfaceType);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Preconditions.checkArgument(interfaceType.isInterface(), "interfaceType must be an interface type");
        final T t = target;
        final long j = timeoutDuration;
        final TimeUnit timeUnit = timeoutUnit;
        final Set<Method> findInterruptibleMethods = findInterruptibleMethods(interfaceType);
        return newProxy(interfaceType, new InvocationHandler() {
            public Object invoke(Object obj, final Method method, final Object[] args) throws Throwable {
                return SimpleTimeLimiter.this.callWithTimeout(new Callable<Object>() {
                    public Object call() throws Exception {
                        try {
                            return method.invoke(t, args);
                        } catch (InvocationTargetException e) {
                            throw SimpleTimeLimiter.throwCause(e, false);
                        }
                    }
                }, j, timeUnit, findInterruptibleMethods.contains(method));
            }
        });
    }

    private static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler) {
        return interfaceType.cast(Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[]{interfaceType}, handler));
    }

    /* access modifiers changed from: private */
    public <T> T callWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit, boolean amInterruptible) throws Exception {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<T> future = this.executor.submit(callable);
        if (!amInterruptible) {
            return Uninterruptibles.getUninterruptibly(future, timeoutDuration, timeoutUnit);
        }
        try {
            return future.get(timeoutDuration, timeoutUnit);
        } catch (InterruptedException e) {
            future.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            throw throwCause(e2, true);
        } catch (TimeoutException e3) {
            future.cancel(true);
            throw new UncheckedTimeoutException(e3);
        }
    }

    @CanIgnoreReturnValue
    public <T> T callWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, InterruptedException, ExecutionException {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<T> future = this.executor.submit(callable);
        try {
            return future.get(timeoutDuration, timeoutUnit);
        } catch (InterruptedException | TimeoutException e) {
            future.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            wrapAndThrowExecutionExceptionOrError(e2.getCause());
            throw new AssertionError();
        }
    }

    @CanIgnoreReturnValue
    public <T> T callUninterruptiblyWithTimeout(Callable<T> callable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, ExecutionException {
        Preconditions.checkNotNull(callable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<T> future = this.executor.submit(callable);
        try {
            return Uninterruptibles.getUninterruptibly(future, timeoutDuration, timeoutUnit);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            wrapAndThrowExecutionExceptionOrError(e2.getCause());
            throw new AssertionError();
        }
    }

    public void runWithTimeout(Runnable runnable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException, InterruptedException {
        Preconditions.checkNotNull(runnable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<?> future = this.executor.submit(runnable);
        try {
            future.get(timeoutDuration, timeoutUnit);
        } catch (InterruptedException | TimeoutException e) {
            future.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            wrapAndThrowRuntimeExecutionExceptionOrError(e2.getCause());
            throw new AssertionError();
        }
    }

    public void runUninterruptiblyWithTimeout(Runnable runnable, long timeoutDuration, TimeUnit timeoutUnit) throws TimeoutException {
        Preconditions.checkNotNull(runnable);
        Preconditions.checkNotNull(timeoutUnit);
        checkPositiveTimeout(timeoutDuration);
        Future<?> future = this.executor.submit(runnable);
        try {
            Uninterruptibles.getUninterruptibly(future, timeoutDuration, timeoutUnit);
        } catch (TimeoutException e) {
            future.cancel(true);
            throw e;
        } catch (ExecutionException e2) {
            wrapAndThrowRuntimeExecutionExceptionOrError(e2.getCause());
            throw new AssertionError();
        }
    }

    /* access modifiers changed from: private */
    public static Exception throwCause(Exception e, boolean combineStackTraces) throws Exception {
        Throwable cause = e.getCause();
        if (cause != null) {
            if (combineStackTraces) {
                cause.setStackTrace((StackTraceElement[]) ObjectArrays.concat(cause.getStackTrace(), e.getStackTrace(), StackTraceElement.class));
            }
            if (cause instanceof Exception) {
                throw ((Exception) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw e;
            }
        } else {
            throw e;
        }
    }

    private static Set<Method> findInterruptibleMethods(Class<?> interfaceType) {
        Set<Method> set = Sets.newHashSet();
        for (Method m : interfaceType.getMethods()) {
            if (declaresInterruptedEx(m)) {
                set.add(m);
            }
        }
        return set;
    }

    private static boolean declaresInterruptedEx(Method method) {
        for (Class<?> exType : method.getExceptionTypes()) {
            if (exType == InterruptedException.class) {
                return true;
            }
        }
        return false;
    }

    private void wrapAndThrowExecutionExceptionOrError(Throwable cause) throws ExecutionException {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        } else if (cause instanceof RuntimeException) {
            throw new UncheckedExecutionException(cause);
        } else {
            throw new ExecutionException(cause);
        }
    }

    private void wrapAndThrowRuntimeExecutionExceptionOrError(Throwable cause) {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        }
        throw new UncheckedExecutionException(cause);
    }

    private static void checkPositiveTimeout(long timeoutDuration) {
        Preconditions.checkArgument(timeoutDuration > 0, "timeout must be positive: %s", timeoutDuration);
    }
}
