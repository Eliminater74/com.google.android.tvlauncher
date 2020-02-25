package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;
import java.util.List;

@GwtCompatible(serializable = true)
final class ExplicitOrdering<T> extends Ordering<T> implements Serializable {
    private static final long serialVersionUID = 0;
    final ImmutableMap<T, Integer> rankMap;

    ExplicitOrdering(List list) {
        this(Maps.indexMap(list));
    }

    ExplicitOrdering(ImmutableMap immutableMap) {
        this.rankMap = immutableMap;
    }

    public int compare(T left, T right) {
        return rank(left) - rank(right);
    }

    private int rank(T value) {
        Integer rank = this.rankMap.get(value);
        if (rank != null) {
            return rank.intValue();
        }
        throw new Ordering.IncomparableValueException(value);
    }

    public boolean equals(@NullableDecl Object object) {
        if (object instanceof ExplicitOrdering) {
            return this.rankMap.equals(((ExplicitOrdering) object).rankMap);
        }
        return false;
    }

    public int hashCode() {
        return this.rankMap.hashCode();
    }

    public String toString() {
        String valueOf = String.valueOf(this.rankMap.keySet());
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 19);
        sb.append("Ordering.explicit(");
        sb.append(valueOf);
        sb.append(")");
        return sb.toString();
    }
}
