package com.google.android.tvlauncher.inputs;

import java.util.Set;
import java.util.function.Predicate;

final /* synthetic */ class CustomInputsManager$RefreshInputList$$Lambda$0 implements Predicate {
    private final Set arg$1;

    CustomInputsManager$RefreshInputList$$Lambda$0(Set set) {
        this.arg$1 = set;
    }

    public boolean test(Object obj) {
        return this.arg$1.contains(((CustomTvInputEntry) obj).getId());
    }
}
