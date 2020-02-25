package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.Set;

@GwtCompatible(emulated = true, serializable = true)
public class HashMultiset<E> extends AbstractMapBasedMultiset<E> {
    @GwtIncompatible
    private static final long serialVersionUID = 0;

    HashMultiset(int distinctElements) {
        super(distinctElements);
    }

    public static <E> HashMultiset<E> create() {
        return create(3);
    }

    public static <E> HashMultiset<E> create(int distinctElements) {
        return new HashMultiset<>(distinctElements);
    }

    public static <E> HashMultiset<E> create(Iterable<? extends E> elements) {
        HashMultiset<E> multiset = create(Multisets.inferDistinctElements(elements));
        Iterables.addAll(multiset, elements);
        return multiset;
    }

    public /* bridge */ /* synthetic */ boolean contains(@NullableDecl Object obj) {
        return super.contains(obj);
    }

    public /* bridge */ /* synthetic */ Set elementSet() {
        return super.elementSet();
    }

    public /* bridge */ /* synthetic */ Set entrySet() {
        return super.entrySet();
    }

    public /* bridge */ /* synthetic */ boolean isEmpty() {
        return super.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public void init(int distinctElements) {
        this.backingMap = new ObjectCountHashMap(distinctElements);
    }
}
