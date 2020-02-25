package com.google.android.libraries.performance.primes.scenario;

import com.google.android.libraries.performance.primes.NoPiiString;

import java.util.Set;

public interface ScenarioStructureProvider {
    NoPiiString getScenarioName(String str, String str2);

    Set<NoPiiString> getStartEventSetForEndEvent(String str);

    boolean isEndEvent(String str);
}
