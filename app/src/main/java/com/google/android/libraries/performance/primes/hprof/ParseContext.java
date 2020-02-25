package com.google.android.libraries.performance.primes.hprof;

import com.google.android.libraries.performance.primes.hprof.collect.IntIntMap;
import com.google.android.libraries.stitch.util.Preconditions;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public final class ParseContext {
    private static final int OBJECT = 2;
    private final ByteBuffer buffer;
    private final ByteBuffer duplicate;
    private final int idSize;
    private final IntIntMap rootTagSizes;
    private final int[] typeSizes;

    public ParseContext(ByteBuffer buffer2) {
        buffer2.rewind();
        buffer2.order(ByteOrder.BIG_ENDIAN);
        this.buffer = buffer2;
        this.duplicate = buffer2.duplicate();
        do {
        } while (buffer2.get() != 0);
        this.idSize = buffer2.getInt();
        Preconditions.checkState(this.idSize > 0);
        buffer2.getLong();
        this.typeSizes = Hprofs.getTypesSizes(this.idSize);
        this.rootTagSizes = new IntIntMap();
        Hprofs.addRootTagSizes(this.idSize, new ParseContext$$Lambda$0(this));
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0033  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.libraries.performance.primes.hprof.ParseContext prepareContext(java.io.File r9) throws java.io.IOException {
        /*
            r0 = 0
            r1 = 0
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ all -> 0x0028 }
            r2.<init>(r9)     // Catch:{ all -> 0x0028 }
            r0 = r2
            java.nio.channels.FileChannel r2 = r0.getChannel()     // Catch:{ all -> 0x0028 }
            java.nio.channels.FileChannel$MapMode r3 = java.nio.channels.FileChannel.MapMode.READ_ONLY     // Catch:{ all -> 0x0026 }
            r4 = 0
            long r6 = r2.size()     // Catch:{ all -> 0x0026 }
            java.nio.MappedByteBuffer r1 = r2.map(r3, r4, r6)     // Catch:{ all -> 0x0026 }
            com.google.android.libraries.performance.primes.hprof.ParseContext r3 = new com.google.android.libraries.performance.primes.hprof.ParseContext     // Catch:{ all -> 0x0026 }
            r3.<init>(r1)     // Catch:{ all -> 0x0026 }
            r2.close()
            r0.close()
            return r3
        L_0x0026:
            r1 = move-exception
            goto L_0x002c
        L_0x0028:
            r2 = move-exception
            r8 = r2
            r2 = r1
            r1 = r8
        L_0x002c:
            if (r2 == 0) goto L_0x0031
            r2.close()
        L_0x0031:
            if (r0 == 0) goto L_0x0036
            r0.close()
        L_0x0036:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.performance.primes.hprof.ParseContext.prepareContext(java.io.File):com.google.android.libraries.performance.primes.hprof.ParseContext");
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$new$0$ParseContext(int tag, int size) {
        this.rootTagSizes.putIfAbsent(tag, size);
    }

    public ByteBuffer getBuffer() {
        return this.buffer;
    }

    public int getIdSize() {
        return this.idSize;
    }

    public String readString(int stringPosition) {
        Preconditions.checkArgument(stringPosition >= 0);
        int i = this.duplicate.getInt(stringPosition);
        int i2 = this.idSize;
        byte[] bytes = new byte[(i - i2)];
        this.duplicate.position(stringPosition + 4 + i2);
        this.duplicate.get(bytes);
        return new String(bytes, Charset.defaultCharset());
    }

    public int getStringLength(int stringPosition) {
        return this.buffer.getInt(stringPosition) - this.idSize;
    }

    public int getStringBytesPos(int stringPosition) {
        return stringPosition + 4 + this.idSize;
    }

    public int getTypeSize(int type) {
        int typeSize = this.typeSizes[type];
        Preconditions.checkState(typeSize > 0);
        return typeSize;
    }

    public boolean isRootTag(int tag) {
        return this.rootTagSizes.containsKey(tag);
    }

    public int getRootTagSize(int rootTag) {
        return this.rootTagSizes.get(rootTag);
    }

    public void skipBytes(int numBytes) {
        Preconditions.checkArgument(numBytes >= 0);
        int newPosition = this.buffer.position() + numBytes;
        if (newPosition <= this.buffer.limit()) {
            this.buffer.position(newPosition);
            return;
        }
        throw new BufferUnderflowException();
    }

    public int readId() {
        int i = this.idSize;
        if (i == 1) {
            return this.buffer.get();
        }
        if (i == 2) {
            return this.buffer.getShort();
        }
        if (i == 4) {
            return this.buffer.getInt();
        }
        throw new IllegalStateException();
    }

    public int readId(int position) {
        int i = this.idSize;
        if (i == 1) {
            return this.buffer.get(position);
        }
        if (i == 2) {
            return this.buffer.getShort(position);
        }
        if (i == 4) {
            return this.buffer.getInt(position);
        }
        throw new IllegalStateException();
    }

    public byte readByte(int position) {
        return this.buffer.get(position);
    }

    public int readInt(int position) {
        return this.buffer.getInt(position);
    }

    public boolean isObjectType(int type) {
        return type == 2;
    }
}
