package com.google.android.libraries.performance.primes.hprof;

import com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap;
import com.google.android.libraries.performance.primes.hprof.collect.MergedEnumerable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

final class HprofGraphAnalyzer {
    HprofGraphAnalyzer() {
    }

    static List<HprofObject> findTopNRooted(ParseResult result, int n) {
        PriorityQueue<HprofObject> topNRooted = new PriorityQueue<>(n, new Comparator<HprofObject>() {
            public int compare(HprofObject object, HprofObject object1) {
                return object.retainedHeapSize - object1.retainedHeapSize;
            }
        });
        IntObjectMap.Enumerable<HprofObject> graphEnumerable = MergedEnumerable.merge(result.getClassInstances().enumerator(), result.getClasses().enumerator());
        while (graphEnumerable.next()) {
            HprofObject object = (HprofObject) graphEnumerable.getValue();
            if (object.immediateDominator instanceof SuperRoot) {
                topNRooted.offer(object);
                if (topNRooted.size() > n) {
                    topNRooted.poll();
                }
            }
        }
        return new ArrayList(topNRooted);
    }
}
