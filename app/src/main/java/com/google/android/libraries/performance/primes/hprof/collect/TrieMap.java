package com.google.android.libraries.performance.primes.hprof.collect;

import android.support.p001v4.util.SparseArrayCompat;
import com.google.android.libraries.stitch.util.Preconditions;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public final class TrieMap<E> {
    private final Edge<E> head = new Edge<>();

    private static class Edge<E> {
        /* access modifiers changed from: private */
        public SparseArrayCompat<Edge<E>> edges;
        /* access modifiers changed from: private */
        public E value;

        private Edge() {
        }
    }

    public E putIfAbsent(String key, E value) {
        Preconditions.checkArgument(key.length() > 0);
        Preconditions.checkNotNull(value);
        byte[] bytes = key.getBytes(Charset.defaultCharset());
        Edge<E> curr = this.head;
        for (byte b : bytes) {
            if (curr.edges == null) {
                SparseArrayCompat unused = curr.edges = new SparseArrayCompat();
            }
            Edge<E> next = (Edge) curr.edges.get(b);
            if (next == null) {
                next = new Edge<>();
                curr.edges.put(b, next);
            }
            curr = next;
        }
        if (curr.value != null) {
            return curr.value;
        }
        Object unused2 = curr.value = value;
        return null;
    }

    public E get(ByteBuffer buffer, int position, int length) {
        Edge<E> curr = this.head;
        int end = position + length;
        for (int i = position; i < end; i++) {
            int b = buffer.get(i);
            if (curr.edges == null) {
                return null;
            }
            Edge<E> edge = (Edge) curr.edges.get(b);
            Edge<E> next = edge;
            if (edge == null) {
                return null;
            }
            curr = next;
        }
        return curr.value;
    }
}
