package dagger.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Provider;

public final class SetFactory<T> implements Factory<Set<T>> {
    private static final Factory<Set<Object>> EMPTY_FACTORY = InstanceFactory.create(Collections.emptySet());
    private final List<Provider<Collection<T>>> collectionProviders;
    private final List<Provider<T>> individualProviders;

    private SetFactory(List<Provider<T>> individualProviders2, List<Provider<Collection<T>>> collectionProviders2) {
        this.individualProviders = individualProviders2;
        this.collectionProviders = collectionProviders2;
    }

    public static <T> Factory<Set<T>> empty() {
        return EMPTY_FACTORY;
    }

    public static <T> Builder<T> builder(int individualProviderSize, int collectionProviderSize) {
        return new Builder<>(individualProviderSize, collectionProviderSize);
    }

    /* JADX INFO: Multiple debug info for r2v4 java.util.HashSet: [D('i' int), D('providedValues' java.util.Set<T>)] */
    public Set<T> get() {
        int size = this.individualProviders.size();
        List<Collection<T>> providedCollections = new ArrayList<>(this.collectionProviders.size());
        int c = this.collectionProviders.size();
        for (int i = 0; i < c; i++) {
            Collection<T> providedCollection = (Collection) this.collectionProviders.get(i).get();
            size += providedCollection.size();
            providedCollections.add(providedCollection);
        }
        Set<T> providedValues = DaggerCollections.newHashSetWithExpectedSize(size);
        int c2 = this.individualProviders.size();
        for (int i2 = 0; i2 < c2; i2++) {
            providedValues.add(Preconditions.checkNotNull(this.individualProviders.get(i2).get()));
        }
        int c3 = providedCollections.size();
        for (int i3 = 0; i3 < c3; i3++) {
            for (T element : providedCollections.get(i3)) {
                providedValues.add(Preconditions.checkNotNull(element));
            }
        }
        return Collections.unmodifiableSet(providedValues);
    }

    public static final class Builder<T> {
        static final /* synthetic */ boolean $assertionsDisabled = false;

        static {
            Class<SetFactory> cls = SetFactory.class;
        }

        private final List<Provider<Collection<T>>> collectionProviders;
        private final List<Provider<T>> individualProviders;

        private Builder(int individualProviderSize, int collectionProviderSize) {
            this.individualProviders = DaggerCollections.presizedList(individualProviderSize);
            this.collectionProviders = DaggerCollections.presizedList(collectionProviderSize);
        }

        public Builder<T> addProvider(Provider<? extends T> individualProvider) {
            this.individualProviders.add(individualProvider);
            return this;
        }

        public Builder<T> addCollectionProvider(Provider<? extends Collection<? extends T>> collectionProvider) {
            this.collectionProviders.add(collectionProvider);
            return this;
        }

        public SetFactory<T> build() {
            return new SetFactory<>(this.individualProviders, this.collectionProviders);
        }
    }
}
