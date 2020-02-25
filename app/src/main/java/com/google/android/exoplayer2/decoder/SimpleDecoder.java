package com.google.android.exoplayer2.decoder;

import android.support.annotation.Nullable;

import com.google.android.exoplayer2.util.Assertions;

import java.util.ArrayDeque;

public abstract class SimpleDecoder<I extends DecoderInputBuffer, O extends OutputBuffer, E extends Exception> implements Decoder<I, O, E> {
    private final I[] availableInputBuffers;
    private final O[] availableOutputBuffers;
    private final Thread decodeThread;
    private final Object lock = new Object();
    private final ArrayDeque<I> queuedInputBuffers = new ArrayDeque<>();
    private final ArrayDeque<O> queuedOutputBuffers = new ArrayDeque<>();
    private int availableInputBufferCount;
    private int availableOutputBufferCount;
    private I dequeuedInputBuffer;
    private E exception;
    private boolean flushed;
    private boolean released;
    private int skippedOutputBufferCount;

    protected SimpleDecoder(I[] inputBuffers, O[] outputBuffers) {
        this.availableInputBuffers = inputBuffers;
        this.availableInputBufferCount = inputBuffers.length;
        for (int i = 0; i < this.availableInputBufferCount; i++) {
            this.availableInputBuffers[i] = createInputBuffer();
        }
        this.availableOutputBuffers = outputBuffers;
        this.availableOutputBufferCount = outputBuffers.length;
        for (int i2 = 0; i2 < this.availableOutputBufferCount; i2++) {
            this.availableOutputBuffers[i2] = createOutputBuffer();
        }
        this.decodeThread = new Thread() {
            public void run() {
                SimpleDecoder.this.run();
            }
        };
        this.decodeThread.start();
    }

    /* access modifiers changed from: protected */
    public abstract I createInputBuffer();

    /* access modifiers changed from: protected */
    public abstract O createOutputBuffer();

    /* access modifiers changed from: protected */
    public abstract E createUnexpectedDecodeException(Throwable th);

    /* access modifiers changed from: protected */
    @Nullable
    public abstract E decode(I i, O o, boolean z);

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: I
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    protected final void setInitialInputBufferSize(int r5) {
        /*
            r4 = this;
            int r0 = r4.availableInputBufferCount
            I[] r1 = r4.availableInputBuffers
            int r1 = r1.length
            r2 = 0
            if (r0 != r1) goto L_0x000a
            r0 = 1
            goto L_0x000b
        L_0x000a:
            r0 = 0
        L_0x000b:
            com.google.android.exoplayer2.util.Assertions.checkState(r0)
            I[] r0 = r4.availableInputBuffers
            int r1 = r0.length
        L_0x0011:
            if (r2 >= r1) goto L_0x001b
            r3 = r0[r2]
            r3.ensureSpaceForWrite(r5)
            int r2 = r2 + 1
            goto L_0x0011
        L_0x001b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.decoder.SimpleDecoder.setInitialInputBufferSize(int):void");
    }

    public final I dequeueInputBuffer() throws Exception {
        I i;
        I i2;
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkState(this.dequeuedInputBuffer == null);
            if (this.availableInputBufferCount == 0) {
                i = null;
            } else {
                I[] iArr = this.availableInputBuffers;
                int i3 = this.availableInputBufferCount - 1;
                this.availableInputBufferCount = i3;
                i = iArr[i3];
            }
            this.dequeuedInputBuffer = i;
            i2 = this.dequeuedInputBuffer;
        }
        return i2;
    }

    public final void queueInputBuffer(DecoderInputBuffer decoderInputBuffer) throws Exception {
        synchronized (this.lock) {
            maybeThrowException();
            Assertions.checkArgument(decoderInputBuffer == this.dequeuedInputBuffer);
            this.queuedInputBuffers.addLast(decoderInputBuffer);
            maybeNotifyDecodeLoop();
            this.dequeuedInputBuffer = null;
        }
    }

    public final O dequeueOutputBuffer() throws Exception {
        synchronized (this.lock) {
            maybeThrowException();
            if (this.queuedOutputBuffers.isEmpty()) {
                return null;
            }
            O o = (OutputBuffer) this.queuedOutputBuffers.removeFirst();
            return o;
        }
    }

    /* access modifiers changed from: protected */
    public void releaseOutputBuffer(O outputBuffer) {
        synchronized (this.lock) {
            releaseOutputBufferInternal(outputBuffer);
            maybeNotifyDecodeLoop();
        }
    }

    public final void flush() {
        synchronized (this.lock) {
            this.flushed = true;
            this.skippedOutputBufferCount = 0;
            if (this.dequeuedInputBuffer != null) {
                releaseInputBufferInternal(this.dequeuedInputBuffer);
                this.dequeuedInputBuffer = null;
            }
            while (!this.queuedInputBuffers.isEmpty()) {
                releaseInputBufferInternal((DecoderInputBuffer) this.queuedInputBuffers.removeFirst());
            }
            while (!this.queuedOutputBuffers.isEmpty()) {
                ((OutputBuffer) this.queuedOutputBuffers.removeFirst()).release();
            }
        }
    }

    public void release() {
        synchronized (this.lock) {
            this.released = true;
            this.lock.notify();
        }
        try {
            this.decodeThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void maybeThrowException() throws Exception {
        E e = this.exception;
        if (e != null) {
            throw e;
        }
    }

    private void maybeNotifyDecodeLoop() {
        if (canDecodeBuffer()) {
            this.lock.notify();
        }
    }

    /* access modifiers changed from: private */
    public void run() {
        do {
            try {
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        } while (decode());
    }

    /*  JADX ERROR: JadxRuntimeException in pass: MethodInvokeVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Not class type: O
        	at jadx.core.dex.info.ClassInfo.checkClassType(ClassInfo.java:60)
        	at jadx.core.dex.info.ClassInfo.fromType(ClassInfo.java:31)
        	at jadx.core.dex.nodes.DexNode.resolveClass(DexNode.java:143)
        	at jadx.core.dex.nodes.RootNode.resolveClass(RootNode.java:183)
        	at jadx.core.dex.nodes.utils.MethodUtils.processMethodArgsOverloaded(MethodUtils.java:75)
        	at jadx.core.dex.nodes.utils.MethodUtils.collectOverloadedMethods(MethodUtils.java:54)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processOverloaded(MethodInvokeVisitor.java:106)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInvoke(MethodInvokeVisitor.java:99)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.processInsn(MethodInvokeVisitor.java:70)
        	at jadx.core.dex.visitors.MethodInvokeVisitor.visit(MethodInvokeVisitor.java:63)
        */
    private boolean decode() throws java.lang.InterruptedException {
        /*
            r7 = this;
            java.lang.Object r0 = r7.lock
            monitor-enter(r0)
        L_0x0003:
            boolean r1 = r7.released     // Catch:{ all -> 0x0097 }
            if (r1 != 0) goto L_0x0013
            boolean r1 = r7.canDecodeBuffer()     // Catch:{ all -> 0x0097 }
            if (r1 != 0) goto L_0x0013
            java.lang.Object r1 = r7.lock     // Catch:{ all -> 0x0097 }
            r1.wait()     // Catch:{ all -> 0x0097 }
            goto L_0x0003
        L_0x0013:
            boolean r1 = r7.released     // Catch:{ all -> 0x0097 }
            r2 = 0
            if (r1 == 0) goto L_0x001a
            monitor-exit(r0)     // Catch:{ all -> 0x0097 }
            return r2
        L_0x001a:
            java.util.ArrayDeque<I> r1 = r7.queuedInputBuffers     // Catch:{ all -> 0x0097 }
            java.lang.Object r1 = r1.removeFirst()     // Catch:{ all -> 0x0097 }
            com.google.android.exoplayer2.decoder.DecoderInputBuffer r1 = (com.google.android.exoplayer2.decoder.DecoderInputBuffer) r1     // Catch:{ all -> 0x0097 }
            O[] r3 = r7.availableOutputBuffers     // Catch:{ all -> 0x0097 }
            int r4 = r7.availableOutputBufferCount     // Catch:{ all -> 0x0097 }
            r5 = 1
            int r4 = r4 - r5
            r7.availableOutputBufferCount = r4     // Catch:{ all -> 0x0097 }
            r3 = r3[r4]     // Catch:{ all -> 0x0097 }
            boolean r4 = r7.flushed     // Catch:{ all -> 0x0097 }
            r7.flushed = r2     // Catch:{ all -> 0x0097 }
            monitor-exit(r0)     // Catch:{ all -> 0x0097 }
            boolean r0 = r1.isEndOfStream()
            if (r0 == 0) goto L_0x003c
            r0 = 4
            r3.addFlag(r0)
            goto L_0x006a
        L_0x003c:
            boolean r0 = r1.isDecodeOnly()
            if (r0 == 0) goto L_0x0047
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r3.addFlag(r0)
        L_0x0047:
            java.lang.Exception r0 = r7.decode(r1, r3, r4)     // Catch:{ RuntimeException -> 0x0056, OutOfMemoryError -> 0x004e }
            r7.exception = r0     // Catch:{ RuntimeException -> 0x0056, OutOfMemoryError -> 0x004e }
            goto L_0x005d
        L_0x004e:
            r0 = move-exception
            java.lang.Exception r6 = r7.createUnexpectedDecodeException(r0)
            r7.exception = r6
            goto L_0x005e
        L_0x0056:
            r0 = move-exception
            java.lang.Exception r6 = r7.createUnexpectedDecodeException(r0)
            r7.exception = r6
        L_0x005d:
        L_0x005e:
            E r0 = r7.exception
            if (r0 == 0) goto L_0x006a
            java.lang.Object r0 = r7.lock
            monitor-enter(r0)
            monitor-exit(r0)     // Catch:{ all -> 0x0067 }
            return r2
        L_0x0067:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0067 }
            throw r2
        L_0x006a:
            java.lang.Object r6 = r7.lock
            monitor-enter(r6)
            boolean r0 = r7.flushed     // Catch:{ all -> 0x0094 }
            if (r0 == 0) goto L_0x0075
            r3.release()     // Catch:{ all -> 0x0094 }
            goto L_0x008f
        L_0x0075:
            boolean r0 = r3.isDecodeOnly()     // Catch:{ all -> 0x0094 }
            if (r0 == 0) goto L_0x0084
            int r0 = r7.skippedOutputBufferCount     // Catch:{ all -> 0x0094 }
            int r0 = r0 + r5
            r7.skippedOutputBufferCount = r0     // Catch:{ all -> 0x0094 }
            r3.release()     // Catch:{ all -> 0x0094 }
            goto L_0x008f
        L_0x0084:
            int r0 = r7.skippedOutputBufferCount     // Catch:{ all -> 0x0094 }
            r3.skippedOutputBufferCount = r0     // Catch:{ all -> 0x0094 }
            r7.skippedOutputBufferCount = r2     // Catch:{ all -> 0x0094 }
            java.util.ArrayDeque<O> r0 = r7.queuedOutputBuffers     // Catch:{ all -> 0x0094 }
            r0.addLast(r3)     // Catch:{ all -> 0x0094 }
        L_0x008f:
            r7.releaseInputBufferInternal(r1)     // Catch:{ all -> 0x0094 }
            monitor-exit(r6)     // Catch:{ all -> 0x0094 }
            return r5
        L_0x0094:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0094 }
            throw r0
        L_0x0097:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0097 }
            throw r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.decoder.SimpleDecoder.decode():boolean");
    }

    private boolean canDecodeBuffer() {
        return !this.queuedInputBuffers.isEmpty() && this.availableOutputBufferCount > 0;
    }

    private void releaseInputBufferInternal(I inputBuffer) {
        inputBuffer.clear();
        I[] iArr = this.availableInputBuffers;
        int i = this.availableInputBufferCount;
        this.availableInputBufferCount = i + 1;
        iArr[i] = inputBuffer;
    }

    private void releaseOutputBufferInternal(O outputBuffer) {
        outputBuffer.clear();
        O[] oArr = this.availableOutputBuffers;
        int i = this.availableOutputBufferCount;
        this.availableOutputBufferCount = i + 1;
        oArr[i] = outputBuffer;
    }
}
