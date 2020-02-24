package com.google.android.libraries.performance.primes.leak;

import java.util.List;

public interface LeakListener {
    void onBatchComplete(boolean z);

    void onHeapDumpResult(List<LeakInfo> list);

    void onLeaked(String str);

    void onReleased(String str);
}
