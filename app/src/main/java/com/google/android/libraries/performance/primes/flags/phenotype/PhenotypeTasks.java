package com.google.android.libraries.performance.primes.flags.phenotype;

public interface PhenotypeTasks {
    Runnable registerAndCommitTask();

    Runnable updateFlagsTask();
}
