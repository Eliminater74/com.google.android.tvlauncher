package com.google.android.libraries.gcoreclient.tasks.impl;

import android.support.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.gcoreclient.common.api.support.GcoreExceptionMapper;
import com.google.android.libraries.gcoreclient.tasks.GcoreOnCompleteListener;
import com.google.android.libraries.gcoreclient.tasks.GcoreOnFailureListener;
import com.google.android.libraries.gcoreclient.tasks.GcoreOnSuccessListener;
import com.google.android.libraries.gcoreclient.tasks.GcoreRuntimeExecutionException;
import com.google.android.libraries.gcoreclient.tasks.GcoreTask;
import com.google.common.base.Function;
import java.util.concurrent.Executor;

public final class GcoreTaskImpl<TInput, TResult> implements GcoreTask<TResult> {
    /* access modifiers changed from: private */
    @Nullable
    public final GcoreExceptionMapper gcoreExceptionMapper;
    private final Task<TInput> task;
    /* access modifiers changed from: private */
    public final Function<TInput, TResult> wrapperFunction;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.gms.tasks.Task<T>, com.google.android.gms.tasks.Task] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> com.google.android.libraries.gcoreclient.tasks.GcoreTask<T> wrap(com.google.android.gms.tasks.Task<T> r1) {
        /*
            com.google.common.base.Function r0 = com.google.common.base.Functions.identity()
            com.google.android.libraries.gcoreclient.tasks.GcoreTask r0 = wrap(r1, r0)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.gcoreclient.tasks.impl.GcoreTaskImpl.wrap(com.google.android.gms.tasks.Task):com.google.android.libraries.gcoreclient.tasks.GcoreTask");
    }

    public static <T, V> GcoreTask<V> wrap(Task<T> task2, Function<T, V> wrapperFunction2) {
        return wrap(task2, wrapperFunction2, null);
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.google.android.gms.tasks.Task<T>, com.google.android.gms.tasks.Task] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> com.google.android.libraries.gcoreclient.tasks.GcoreTask<T> wrap(com.google.android.gms.tasks.Task<T> r1, @android.support.annotation.Nullable com.google.android.libraries.gcoreclient.common.api.support.GcoreExceptionMapper r2) {
        /*
            com.google.common.base.Function r0 = com.google.common.base.Functions.identity()
            com.google.android.libraries.gcoreclient.tasks.GcoreTask r0 = wrap(r1, r0, r2)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.gcoreclient.tasks.impl.GcoreTaskImpl.wrap(com.google.android.gms.tasks.Task, com.google.android.libraries.gcoreclient.common.api.support.GcoreExceptionMapper):com.google.android.libraries.gcoreclient.tasks.GcoreTask");
    }

    public static <T, V> GcoreTask<V> wrap(Task<T> task2, Function<T, V> wrapperFunction2, @Nullable GcoreExceptionMapper gcoreExceptionMapper2) {
        return new GcoreTaskImpl(task2, wrapperFunction2, gcoreExceptionMapper2);
    }

    private GcoreTaskImpl(Task<TInput> task2, Function<TInput, TResult> wrapperFunction2, @Nullable GcoreExceptionMapper gcoreExceptionMapper2) {
        this.wrapperFunction = wrapperFunction2;
        this.task = task2;
        this.gcoreExceptionMapper = gcoreExceptionMapper2;
    }

    public boolean isComplete() {
        return this.task.isComplete();
    }

    public boolean isSuccessful() {
        return this.task.isSuccessful();
    }

    public TResult getResult() {
        try {
            return this.wrapperFunction.apply(this.task.getResult());
        } catch (RuntimeExecutionException ree) {
            GcoreExceptionMapper gcoreExceptionMapper2 = this.gcoreExceptionMapper;
            if (gcoreExceptionMapper2 != null) {
                throw new GcoreRuntimeExecutionException(gcoreExceptionMapper2.mapThrowables(ree.getCause()));
            }
            throw new GcoreRuntimeExecutionException(ree.getCause());
        } catch (RuntimeException e) {
            GcoreExceptionMapper gcoreExceptionMapper3 = this.gcoreExceptionMapper;
            if (gcoreExceptionMapper3 != null) {
                throw ((RuntimeException) gcoreExceptionMapper3.mapExceptions(e));
            }
            throw e;
        }
    }

    public Throwable getException() {
        GcoreExceptionMapper gcoreExceptionMapper2 = this.gcoreExceptionMapper;
        if (gcoreExceptionMapper2 != null) {
            return gcoreExceptionMapper2.mapThrowables(this.task.getException());
        }
        return this.task.getException();
    }

    public GcoreTask<TResult> addOnSuccessListener(GcoreOnSuccessListener<? super TResult> listener) {
        this.task.addOnSuccessListener(wrapOnSuccessListener(listener));
        return this;
    }

    public GcoreTask<TResult> addOnFailureListener(GcoreOnFailureListener listener) {
        this.task.addOnFailureListener(wrapOnFailureListener(listener));
        return this;
    }

    public GcoreTask<TResult> addOnSuccessListener(Executor executor, GcoreOnSuccessListener<? super TResult> listener) {
        this.task.addOnSuccessListener(executor, wrapOnSuccessListener(listener));
        return this;
    }

    public GcoreTask<TResult> addOnFailureListener(Executor executor, GcoreOnFailureListener listener) {
        this.task.addOnFailureListener(executor, wrapOnFailureListener(listener));
        return this;
    }

    public GcoreTask<TResult> addOnCompleteListener(GcoreOnCompleteListener<TResult> listener) {
        this.task.addOnCompleteListener(wrapOnCompleteListener(listener));
        return this;
    }

    public GcoreTask<TResult> addOnCompleteListener(Executor executor, GcoreOnCompleteListener<TResult> listener) {
        this.task.addOnCompleteListener(executor, wrapOnCompleteListener(listener));
        return this;
    }

    private OnSuccessListener<TInput> wrapOnSuccessListener(final GcoreOnSuccessListener<? super TResult> listener) {
        return new OnSuccessListener<TInput>() {
            public void onSuccess(TInput result) {
                listener.onSuccess(GcoreTaskImpl.this.wrapperFunction.apply(result));
            }
        };
    }

    private OnFailureListener wrapOnFailureListener(final GcoreOnFailureListener listener) {
        return new OnFailureListener() {
            public void onFailure(Exception exception) {
                if (GcoreTaskImpl.this.gcoreExceptionMapper != null) {
                    listener.onFailure(GcoreTaskImpl.this.gcoreExceptionMapper.mapExceptions(exception));
                } else {
                    listener.onFailure(exception);
                }
            }
        };
    }

    private OnCompleteListener<TInput> wrapOnCompleteListener(final GcoreOnCompleteListener<TResult> listener) {
        return new OnCompleteListener<TInput>() {
            public void onComplete(Task<TInput> task) {
                listener.onComplete(GcoreTaskImpl.wrap(task, GcoreTaskImpl.this.wrapperFunction));
            }
        };
    }
}
