package com.google.android.libraries.gcoreclient.clearcut.impl;

import com.google.android.gms.clearcut.Counters;
import com.google.android.libraries.gcoreclient.clearcut.GcoreCountersAlias;
import com.google.android.libraries.gcoreclient.clearcut.GcoreCountersBucketAliasFactory;

final class GcoreCountersBucketAliasFactoryImpl implements GcoreCountersBucketAliasFactory {
    GcoreCountersBucketAliasFactoryImpl() {
    }

    public GcoreCountersAlias createBucketAlias(final int alias) {
        return new GcoreCountersAlias(this) {
            private final Counters.BucketAlias bucketAlias = new Counters.BucketAlias(alias);

            public long alias(long rawKey) {
                return this.bucketAlias.alias(rawKey);
            }

            public int getAlias() {
                return alias;
            }
        };
    }

    public GcoreCountersAlias createClippedBucketAlias(final int alias, final int min, final int max) {
        return new GcoreCountersAlias(this) {
            private final Counters.BucketAlias clippedBucketAlias = new Counters.ClippedBucketAlias(alias, min, max);

            public long alias(long rawKey) {
                return this.clippedBucketAlias.alias(rawKey);
            }

            public int getAlias() {
                return alias;
            }
        };
    }
}
