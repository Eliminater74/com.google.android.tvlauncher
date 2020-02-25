package com.google.android.libraries.performance.primes.hprof;

import android.support.annotation.VisibleForTesting;

import com.android.ahat.dominators.Dominators;
import com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap;
import com.google.android.libraries.performance.primes.leak.LeakInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class HprofAnalyzer {
    static final List<String> NON_LEAK_CONTAINER = Collections.unmodifiableList(Arrays.asList("boolean", "boolean[]", "boolean[][]", "byte", "byte[]", "byte[][]", "byte[][][]", "char", "char[]", "char[][]", "short", "short[]", "short[][]", "int", "int[]", "int[][]", "int[][][]", "long", "long[]", "long[][]", "float", "float[]", "float[][]", "double", "double[]", "double[][]", "java.lang.Class", "java.lang.Class[]", "java.lang.Class[][]", "java.lang.Byte", "java.lang.Byte[]", "java.lang.Character", "java.lang.Character[]", "java.lang.Boolean", "java.lang.Boolean[]", "java.lang.Short", "java.lang.Short[]", "java.lang.Integer", "java.lang.Integer[]", "java.lang.Long", "java.lang.Long[]", "java.lang.Float", "java.lang.Float[]", "java.lang.Double", "java.lang.Double[]", "java.lang.String", "java.lang.String[]", "java.lang.String[][]", "java.lang.String[][][]"));
    static final List<Integer> NON_LEAK_ROOT_TAGS = Collections.unmodifiableList(Arrays.asList(139, 138, 137, 255, 144));
    private static final String REFERENT_FIELD = "referent";
    private final File hprofFile;
    private final boolean quantifyLeakSizeEnabled;

    public HprofAnalyzer(File hprofFile2, boolean quantifyLeakSizeEnabled2) {
        this.hprofFile = hprofFile2;
        this.quantifyLeakSizeEnabled = quantifyLeakSizeEnabled2;
    }

    public List<LeakInfo> checkTrackedObjectsForLeak(String refClassName) throws IOException {
        ParseContext parseContext = ParseContext.prepareContext(this.hprofFile);
        ParseResult result = HprofParser.parseBuffer(parseContext, NON_LEAK_ROOT_TAGS, getInstancesToExclude(), Collections.singleton(refClassName));
        List<HprofObject> refInstances = result.getInstancesFound().get(refClassName);
        if (refInstances == null) {
            return Collections.emptyList();
        }
        return extractLeaks(parseContext, result, getCandidates(parseContext, result, refInstances));
    }

    private List<HprofObject> getCandidates(ParseContext parseContext, ParseResult result, List<HprofObject> refInstances) {
        List<HprofObject> candidates = new ArrayList<>();
        for (HprofObject refInstance : refInstances) {
            HprofObject value = result.getClassInstances().get(refInstance.getChildValue(parseContext, REFERENT_FIELD));
            if (value != null) {
                String className = "";
                if (value instanceof HprofClassInstance) {
                    className = ((HprofClassInstance) value).clazz.getClassName(parseContext);
                } else if (value instanceof HprofArrayInstance) {
                    className = ((HprofArrayInstance) value).clazz.getClassName(parseContext);
                }
                if (!NON_LEAK_CONTAINER.contains(className)) {
                    candidates.add(value);
                }
            }
        }
        return candidates;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public List<LeakInfo> checkLeakForIds(int... ids) throws IOException {
        ParseContext parseContext = ParseContext.prepareContext(this.hprofFile);
        ParseResult result = HprofParser.parseBuffer(parseContext, NON_LEAK_ROOT_TAGS, getInstancesToExclude(), null);
        List<HprofObject> candidates = new ArrayList<>();
        IntObjectMap.Enumerator<HprofObject> enumerator = result.getClassInstances().enumerator();
        while (enumerator.next()) {
            for (int id : ids) {
                if (id == enumerator.getKey()) {
                    candidates.add(enumerator.getValue());
                }
            }
        }
        return extractLeaks(parseContext, result, candidates);
    }

    private List<LeakInfo> extractLeaks(ParseContext parseContext, ParseResult result, List<HprofObject> candidates) {
        if (candidates.isEmpty()) {
            return Collections.emptyList();
        }
        if (this.quantifyLeakSizeEnabled) {
            new Dominators<>(HprofGraph.newInstance(parseContext, result)).computeDominators(new SuperRoot(result.getRoots()));
        }
        HprofTraverser.addShortestPathParent(parseContext, result);
        List<LeakInfo> leaks = new ArrayList<>();
        for (HprofObject candidate : candidates) {
            if (candidate.parent != null && (candidate instanceof HprofClassInstance)) {
                String path = buildLeakPath(parseContext, candidate);
                int leakRetainedHeapSize = 0;
                if (this.quantifyLeakSizeEnabled) {
                    HprofObject.computeRetainedSizes(candidate, parseContext);
                    leakRetainedHeapSize = candidate.retainedHeapSize;
                }
                leaks.add(LeakInfo.newInstance(path, leakRetainedHeapSize));
            }
        }
        return leaks;
    }

    private String buildLeakPath(ParseContext parseContext, HprofObject object) {
        StringBuilder builder = new StringBuilder();
        HprofObject curr = object;
        builder.append(curr.buildLeakSegment(parseContext, -1));
        while (curr.parent != null) {
            builder.append(10);
            builder.append(curr.parent.buildLeakSegment(parseContext, curr.parent.findChildIndex(parseContext, curr.getId(parseContext))));
            curr = curr.parent;
        }
        return builder.toString();
    }

    private List<String> getInstancesToExclude() {
        return this.quantifyLeakSizeEnabled ? Collections.emptyList() : NON_LEAK_CONTAINER;
    }
}
