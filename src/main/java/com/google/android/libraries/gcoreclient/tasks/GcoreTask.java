package com.google.android.libraries.gcoreclient.tasks;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.Executor;

public interface GcoreTask<TResult> {
    @NonNull
    GcoreTask<TResult> addOnCompleteListener(@NonNull GcoreOnCompleteListener<TResult> gcoreOnCompleteListener);

    @NonNull
    GcoreTask<TResult> addOnCompleteListener(@NonNull Executor executor, @NonNull GcoreOnCompleteListener<TResult> gcoreOnCompleteListener);

    @NonNull
    GcoreTask<TResult> addOnFailureListener(@NonNull GcoreOnFailureListener gcoreOnFailureListener);

    @NonNull
    GcoreTask<TResult> addOnFailureListener(@NonNull Executor executor, @NonNull GcoreOnFailureListener gcoreOnFailureListener);

    @NonNull
    GcoreTask<TResult> addOnSuccessListener(@NonNull GcoreOnSuccessListener<? super TResult> gcoreOnSuccessListener);

    @NonNull
    GcoreTask<TResult> addOnSuccessListener(@NonNull Executor executor, @NonNull GcoreOnSuccessListener<? super TResult> gcoreOnSuccessListener);

    @Nullable
    Throwable getException();

    TResult getResult();

    boolean isComplete();

    boolean isSuccessful();
}
