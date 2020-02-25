package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@GwtCompatible(emulated = true)
abstract class CollectionFuture<V, C> extends AggregateFuture<V, C> {
    CollectionFuture() {
    }

    static final class ListFuture<V> extends CollectionFuture<V, List<V>> {
        ListFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed) {
            init(new ListFutureRunningState(this, futures, allMustSucceed));
        }

        private final class ListFutureRunningState extends CollectionFuture<V, List<V>>.CollectionFutureRunningState {
            ListFutureRunningState(ListFuture listFuture, ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed) {
                super(futures, allMustSucceed);
            }

            public List<V> combine(List<Optional<V>> values) {
                ArrayList newArrayListWithCapacity = Lists.newArrayListWithCapacity(values.size());
                Iterator<Optional<V>> it = values.iterator();
                while (it.hasNext()) {
                    Optional<V> element = it.next();
                    newArrayListWithCapacity.add(element != null ? element.orNull() : null);
                }
                return Collections.unmodifiableList(newArrayListWithCapacity);
            }
        }
    }

    abstract class CollectionFutureRunningState extends AggregateFuture<V, C>.RunningState {
        private List<Optional<V>> values;

        CollectionFutureRunningState(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed) {
            super(futures, allMustSucceed, true);
            List<Optional<V>> list;
            if (futures.isEmpty()) {
                list = ImmutableList.m107of();
            } else {
                list = Lists.newArrayListWithCapacity(futures.size());
            }
            this.values = list;
            for (int i = 0; i < futures.size(); i++) {
                this.values.add(null);
            }
        }

        /* access modifiers changed from: package-private */
        public abstract C combine(List<Optional<V>> list);

        /* access modifiers changed from: package-private */
        public final void collectOneValue(boolean allMustSucceed, int index, @NullableDecl V returnValue) {
            List<Optional<V>> localValues = this.values;
            if (localValues != null) {
                localValues.set(index, Optional.fromNullable(returnValue));
            } else {
                Preconditions.checkState(allMustSucceed || CollectionFuture.this.isCancelled(), "Future was done before all dependencies completed");
            }
        }

        /* access modifiers changed from: package-private */
        public final void handleAllCompleted() {
            List<Optional<V>> localValues = this.values;
            if (localValues != null) {
                CollectionFuture.this.set(combine(localValues));
            } else {
                Preconditions.checkState(CollectionFuture.this.isDone());
            }
        }

        /* access modifiers changed from: package-private */
        public void releaseResourcesAfterFailure() {
            super.releaseResourcesAfterFailure();
            this.values = null;
        }
    }
}
