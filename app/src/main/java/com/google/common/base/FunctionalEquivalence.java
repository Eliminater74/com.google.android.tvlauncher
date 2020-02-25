package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.Serializable;

@GwtCompatible
@Beta
final class FunctionalEquivalence<F, T> extends Equivalence<F> implements Serializable {
    private static final long serialVersionUID = 0;
    private final Function<F, ? extends T> function;
    private final Equivalence<T> resultEquivalence;

    FunctionalEquivalence(Function<F, ? extends T> function2, Equivalence<T> resultEquivalence2) {
        this.function = (Function) Preconditions.checkNotNull(function2);
        this.resultEquivalence = (Equivalence) Preconditions.checkNotNull(resultEquivalence2);
    }

    /* access modifiers changed from: protected */
    public boolean doEquivalent(F a, F b) {
        return this.resultEquivalence.equivalent(this.function.apply(a), this.function.apply(b));
    }

    /* access modifiers changed from: protected */
    public int doHash(F a) {
        return this.resultEquivalence.hash(this.function.apply(a));
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof FunctionalEquivalence)) {
            return false;
        }
        FunctionalEquivalence<?, ?> that = (FunctionalEquivalence) obj;
        if (!this.function.equals(that.function) || !this.resultEquivalence.equals(that.resultEquivalence)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.function, this.resultEquivalence);
    }

    public String toString() {
        String valueOf = String.valueOf(this.resultEquivalence);
        String valueOf2 = String.valueOf(this.function);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 13 + String.valueOf(valueOf2).length());
        sb.append(valueOf);
        sb.append(".onResultOf(");
        sb.append(valueOf2);
        sb.append(")");
        return sb.toString();
    }
}
