package com.google.android.libraries.gcoreclient.clearcut;

public interface GcoreCountersBucketAliasFactory {
    GcoreCountersAlias createBucketAlias(int i);

    GcoreCountersAlias createClippedBucketAlias(int i, int i2, int i3);
}
