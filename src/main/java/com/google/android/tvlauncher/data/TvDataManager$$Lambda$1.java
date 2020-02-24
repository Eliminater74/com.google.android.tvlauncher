package com.google.android.tvlauncher.data;

import java.util.function.Predicate;

final /* synthetic */ class TvDataManager$$Lambda$1 implements Predicate {
    private final TvDataManager arg$1;

    TvDataManager$$Lambda$1(TvDataManager tvDataManager) {
        this.arg$1 = tvDataManager;
    }

    public boolean test(Object obj) {
        return this.arg$1.lambda$onTaskCompleted$1$TvDataManager((Long) obj);
    }
}
