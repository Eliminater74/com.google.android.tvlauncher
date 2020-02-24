package com.google.android.libraries.performance.primes.hprof;

import com.google.android.libraries.performance.primes.hprof.collect.IntObjectMap;
import java.util.List;
import java.util.Map;

public final class ParseResult {
    private final IntObjectMap<HprofObject> classInstances;
    private final IntObjectMap<HprofClass> classes;
    private final Map<String, List<HprofObject>> instancesFound;
    private final List<HprofObject> roots;

    ParseResult(IntObjectMap<HprofClass> classes2, IntObjectMap<HprofObject> classInstances2, List<HprofObject> roots2, Map<String, List<HprofObject>> instancesFound2) {
        this.classes = classes2;
        this.classInstances = classInstances2;
        this.roots = roots2;
        this.instancesFound = instancesFound2;
    }

    public IntObjectMap<HprofClass> getClasses() {
        return this.classes;
    }

    public IntObjectMap<HprofObject> getClassInstances() {
        return this.classInstances;
    }

    public List<HprofObject> getRoots() {
        return this.roots;
    }

    public Map<String, List<HprofObject>> getInstancesFound() {
        return this.instancesFound;
    }
}
