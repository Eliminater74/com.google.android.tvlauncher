package dagger.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SetBuilder<T> {
    private static final String SET_CONTRIBUTIONS_CANNOT_BE_NULL = "Set contributions cannot be null";
    private final List<T> contributions;

    private SetBuilder(int estimatedSize) {
        this.contributions = new ArrayList(estimatedSize);
    }

    public static <T> SetBuilder<T> newSetBuilder(int estimatedSize) {
        return new SetBuilder<>(estimatedSize);
    }

    public SetBuilder<T> add(T t) {
        this.contributions.add(Preconditions.checkNotNull(t, SET_CONTRIBUTIONS_CANNOT_BE_NULL));
        return this;
    }

    public SetBuilder<T> addAll(Collection<? extends T> collection) {
        for (T item : collection) {
            Preconditions.checkNotNull(item, SET_CONTRIBUTIONS_CANNOT_BE_NULL);
        }
        this.contributions.addAll(collection);
        return this;
    }

    public Set<T> build() {
        int size = this.contributions.size();
        if (size == 0) {
            return Collections.emptySet();
        }
        if (size != 1) {
            return Collections.unmodifiableSet(new HashSet(this.contributions));
        }
        return Collections.singleton(this.contributions.get(0));
    }
}
