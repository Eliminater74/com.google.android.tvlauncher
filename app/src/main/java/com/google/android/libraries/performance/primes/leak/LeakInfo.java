package com.google.android.libraries.performance.primes.leak;

import java.util.Objects;
import javax.annotation.Nullable;

public final class LeakInfo {
    private final String path;
    private final int retainedHeapSizeBytes;

    public static LeakInfo newInstance(String path2, int retainedHeapSize) {
        return new LeakInfo(path2, retainedHeapSize);
    }

    private LeakInfo(String path2, int retainedHeapSizeBytes2) {
        this.path = path2;
        this.retainedHeapSizeBytes = retainedHeapSizeBytes2;
    }

    public String getPath() {
        return this.path;
    }

    public int getRetainedHeapSizeBytes() {
        return this.retainedHeapSizeBytes;
    }

    public boolean equals(@Nullable Object object) {
        if (!(object instanceof LeakInfo)) {
            return false;
        }
        LeakInfo that = (LeakInfo) object;
        if (!this.path.equals(that.path) || this.retainedHeapSizeBytes != that.retainedHeapSizeBytes) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.path, Integer.valueOf(this.retainedHeapSizeBytes));
    }

    public String toString() {
        return String.format("{path: %s, retainedHeapSizeBytes: %d}", this.path, Integer.valueOf(this.retainedHeapSizeBytes));
    }
}
