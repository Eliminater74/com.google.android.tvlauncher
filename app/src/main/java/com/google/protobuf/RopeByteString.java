package com.google.protobuf;

import com.google.common.primitives.UnsignedBytes;
import com.google.protobuf.ByteString;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

final class RopeByteString extends ByteString {
    static final int[] minLengthByDepth = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, ClientAnalytics.LogRequest.LogSource.WALLET_APP_IOS_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.WALLPAPER_PICKER_COUNTERS_VALUE, ClientAnalytics.LogRequest.LogSource.WHISTLEPUNK_ANDROID_PRIMES_VALUE, ClientAnalytics.LogRequest.LogSource.STREAMZ_NEOS_VALUE, 1597, 2584, 4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309, 3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155, 165580141, 267914296, 433494437, 701408733, 1134903170, 1836311903, Integer.MAX_VALUE};
    private static final long serialVersionUID = 1;
    /* access modifiers changed from: private */
    public final ByteString left;
    private final int leftLength;
    /* access modifiers changed from: private */
    public final ByteString right;
    private final int totalLength;
    private final int treeDepth;

    private RopeByteString(ByteString left2, ByteString right2) {
        this.left = left2;
        this.right = right2;
        this.leftLength = left2.size();
        this.totalLength = this.leftLength + right2.size();
        this.treeDepth = Math.max(left2.getTreeDepth(), right2.getTreeDepth()) + 1;
    }

    static ByteString concatenate(ByteString left2, ByteString right2) {
        if (right2.size() == 0) {
            return left2;
        }
        if (left2.size() == 0) {
            return right2;
        }
        int newLength = left2.size() + right2.size();
        if (newLength < 128) {
            return concatenateBytes(left2, right2);
        }
        if (left2 instanceof RopeByteString) {
            RopeByteString leftRope = (RopeByteString) left2;
            if (leftRope.right.size() + right2.size() < 128) {
                return new RopeByteString(leftRope.left, concatenateBytes(leftRope.right, right2));
            } else if (leftRope.left.getTreeDepth() > leftRope.right.getTreeDepth() && leftRope.getTreeDepth() > right2.getTreeDepth()) {
                return new RopeByteString(leftRope.left, new RopeByteString(leftRope.right, right2));
            }
        }
        if (newLength >= minLengthByDepth[Math.max(left2.getTreeDepth(), right2.getTreeDepth()) + 1]) {
            return new RopeByteString(left2, right2);
        }
        return new Balancer().balance(left2, right2);
    }

    private static ByteString concatenateBytes(ByteString left2, ByteString right2) {
        int leftSize = left2.size();
        int rightSize = right2.size();
        byte[] bytes = new byte[(leftSize + rightSize)];
        left2.copyTo(bytes, 0, 0, leftSize);
        right2.copyTo(bytes, 0, leftSize, rightSize);
        return ByteString.wrap(bytes);
    }

    static RopeByteString newInstanceForTest(ByteString left2, ByteString right2) {
        return new RopeByteString(left2, right2);
    }

    public byte byteAt(int index) {
        checkIndex(index, this.totalLength);
        return internalByteAt(index);
    }

    /* access modifiers changed from: package-private */
    public byte internalByteAt(int index) {
        int i = this.leftLength;
        if (index < i) {
            return this.left.internalByteAt(index);
        }
        return this.right.internalByteAt(index - i);
    }

    public int size() {
        return this.totalLength;
    }

    public ByteString.ByteIterator iterator() {
        return new ByteString.AbstractByteIterator() {
            ByteString.ByteIterator current = nextPiece();
            final PieceIterator pieces = new PieceIterator(RopeByteString.this);

            private ByteString.ByteIterator nextPiece() {
                if (this.pieces.hasNext()) {
                    return this.pieces.next().iterator();
                }
                return null;
            }

            public boolean hasNext() {
                return this.current != null;
            }

            public byte nextByte() {
                ByteString.ByteIterator byteIterator = this.current;
                if (byteIterator != null) {
                    byte b = byteIterator.nextByte();
                    if (!this.current.hasNext()) {
                        this.current = nextPiece();
                    }
                    return b;
                }
                throw new NoSuchElementException();
            }
        };
    }

    /* access modifiers changed from: protected */
    public int getTreeDepth() {
        return this.treeDepth;
    }

    /* access modifiers changed from: protected */
    public boolean isBalanced() {
        return this.totalLength >= minLengthByDepth[this.treeDepth];
    }

    public ByteString substring(int beginIndex, int endIndex) {
        int length = checkRange(beginIndex, endIndex, this.totalLength);
        if (length == 0) {
            return ByteString.EMPTY;
        }
        if (length == this.totalLength) {
            return this;
        }
        int i = this.leftLength;
        if (endIndex <= i) {
            return this.left.substring(beginIndex, endIndex);
        }
        if (beginIndex >= i) {
            return this.right.substring(beginIndex - i, endIndex - i);
        }
        return new RopeByteString(this.left.substring(beginIndex), this.right.substring(0, endIndex - this.leftLength));
    }

    /* access modifiers changed from: protected */
    public void copyToInternal(byte[] target, int sourceOffset, int targetOffset, int numberToCopy) {
        int i = sourceOffset + numberToCopy;
        int i2 = this.leftLength;
        if (i <= i2) {
            this.left.copyToInternal(target, sourceOffset, targetOffset, numberToCopy);
        } else if (sourceOffset >= i2) {
            this.right.copyToInternal(target, sourceOffset - i2, targetOffset, numberToCopy);
        } else {
            int leftLength2 = i2 - sourceOffset;
            this.left.copyToInternal(target, sourceOffset, targetOffset, leftLength2);
            this.right.copyToInternal(target, 0, targetOffset + leftLength2, numberToCopy - leftLength2);
        }
    }

    public void copyTo(ByteBuffer target) {
        this.left.copyTo(target);
        this.right.copyTo(target);
    }

    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(toByteArray()).asReadOnlyBuffer();
    }

    public List<ByteBuffer> asReadOnlyByteBufferList() {
        List<ByteBuffer> result = new ArrayList<>();
        PieceIterator pieces = new PieceIterator(this);
        while (pieces.hasNext()) {
            result.add(pieces.next().asReadOnlyByteBuffer());
        }
        return result;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        this.left.writeTo(outputStream);
        this.right.writeTo(outputStream);
    }

    /* access modifiers changed from: package-private */
    public void writeToInternal(OutputStream out, int sourceOffset, int numberToWrite) throws IOException {
        int i = sourceOffset + numberToWrite;
        int i2 = this.leftLength;
        if (i <= i2) {
            this.left.writeToInternal(out, sourceOffset, numberToWrite);
        } else if (sourceOffset >= i2) {
            this.right.writeToInternal(out, sourceOffset - i2, numberToWrite);
        } else {
            int numberToWriteInLeft = i2 - sourceOffset;
            this.left.writeToInternal(out, sourceOffset, numberToWriteInLeft);
            this.right.writeToInternal(out, 0, numberToWrite - numberToWriteInLeft);
        }
    }

    /* access modifiers changed from: package-private */
    public void writeTo(ByteOutput output) throws IOException {
        this.left.writeTo(output);
        this.right.writeTo(output);
    }

    /* access modifiers changed from: package-private */
    public void writeToReverse(ByteOutput output) throws IOException {
        this.right.writeToReverse(output);
        this.left.writeToReverse(output);
    }

    /* access modifiers changed from: protected */
    public String toStringInternal(Charset charset) {
        return new String(toByteArray(), charset);
    }

    public boolean isValidUtf8() {
        int leftPartial = this.left.partialIsValidUtf8(0, 0, this.leftLength);
        ByteString byteString = this.right;
        if (byteString.partialIsValidUtf8(leftPartial, 0, byteString.size()) == 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public int partialIsValidUtf8(int state, int offset, int length) {
        int toIndex = offset + length;
        int i = this.leftLength;
        if (toIndex <= i) {
            return this.left.partialIsValidUtf8(state, offset, length);
        }
        if (offset >= i) {
            return this.right.partialIsValidUtf8(state, offset - i, length);
        }
        int leftLength2 = i - offset;
        return this.right.partialIsValidUtf8(this.left.partialIsValidUtf8(state, offset, leftLength2), 0, length - leftLength2);
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ByteString)) {
            return false;
        }
        ByteString otherByteString = (ByteString) other;
        if (this.totalLength != otherByteString.size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        int thisHash = peekCachedHashCode();
        int thatHash = otherByteString.peekCachedHashCode();
        if (thisHash == 0 || thatHash == 0 || thisHash == thatHash) {
            return equalsFragments(otherByteString);
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: com.google.protobuf.ByteString$LeafByteString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: com.google.protobuf.ByteString$LeafByteString} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean equalsFragments(com.google.protobuf.ByteString r13) {
        /*
            r12 = this;
            r0 = 0
            com.google.protobuf.RopeByteString$PieceIterator r1 = new com.google.protobuf.RopeByteString$PieceIterator
            r2 = 0
            r1.<init>(r12)
            java.lang.Object r3 = r1.next()
            com.google.protobuf.ByteString$LeafByteString r3 = (com.google.protobuf.ByteString.LeafByteString) r3
            r4 = 0
            com.google.protobuf.RopeByteString$PieceIterator r5 = new com.google.protobuf.RopeByteString$PieceIterator
            r5.<init>(r13)
            r2 = r5
            java.lang.Object r5 = r2.next()
            com.google.protobuf.ByteString$LeafByteString r5 = (com.google.protobuf.ByteString.LeafByteString) r5
            r6 = 0
        L_0x001b:
            int r7 = r3.size()
            int r7 = r7 - r0
            int r8 = r5.size()
            int r8 = r8 - r4
            int r9 = java.lang.Math.min(r7, r8)
            if (r0 != 0) goto L_0x0030
            boolean r10 = r3.equalsRange(r5, r4, r9)
            goto L_0x0034
        L_0x0030:
            boolean r10 = r5.equalsRange(r3, r0, r9)
        L_0x0034:
            if (r10 != 0) goto L_0x0039
            r11 = 0
            return r11
        L_0x0039:
            int r6 = r6 + r9
            int r11 = r12.totalLength
            if (r6 < r11) goto L_0x0048
            if (r6 != r11) goto L_0x0042
            r11 = 1
            return r11
        L_0x0042:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            r11.<init>()
            throw r11
        L_0x0048:
            if (r9 != r7) goto L_0x0053
            r0 = 0
            java.lang.Object r11 = r1.next()
            r3 = r11
            com.google.protobuf.ByteString$LeafByteString r3 = (com.google.protobuf.ByteString.LeafByteString) r3
            goto L_0x0054
        L_0x0053:
            int r0 = r0 + r9
        L_0x0054:
            if (r9 != r8) goto L_0x005f
            r4 = 0
            java.lang.Object r11 = r2.next()
            r5 = r11
            com.google.protobuf.ByteString$LeafByteString r5 = (com.google.protobuf.ByteString.LeafByteString) r5
            goto L_0x0060
        L_0x005f:
            int r4 = r4 + r9
        L_0x0060:
            goto L_0x001b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.protobuf.RopeByteString.equalsFragments(com.google.protobuf.ByteString):boolean");
    }

    /* access modifiers changed from: protected */
    public int partialHash(int h, int offset, int length) {
        int toIndex = offset + length;
        int i = this.leftLength;
        if (toIndex <= i) {
            return this.left.partialHash(h, offset, length);
        }
        if (offset >= i) {
            return this.right.partialHash(h, offset - i, length);
        }
        int leftLength2 = i - offset;
        return this.right.partialHash(this.left.partialHash(h, offset, leftLength2), 0, length - leftLength2);
    }

    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(new RopeInputStream());
    }

    public InputStream newInput() {
        return new RopeInputStream();
    }

    private static class Balancer {
        private final ArrayDeque<ByteString> prefixesStack;

        private Balancer() {
            this.prefixesStack = new ArrayDeque<>();
        }

        /* access modifiers changed from: private */
        public ByteString balance(ByteString left, ByteString right) {
            doBalance(left);
            doBalance(right);
            ByteString partialString = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty()) {
                partialString = new RopeByteString(this.prefixesStack.pop(), partialString);
            }
            return partialString;
        }

        private void doBalance(ByteString root) {
            if (root.isBalanced()) {
                insert(root);
            } else if (root instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString) root;
                doBalance(rbs.left);
                doBalance(rbs.right);
            } else {
                String valueOf = String.valueOf(root.getClass());
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
                sb.append("Has a new type of ByteString been created? Found ");
                sb.append(valueOf);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        private void insert(ByteString byteString) {
            int depthBin = getDepthBinForLength(byteString.size());
            int binEnd = RopeByteString.minLengthByDepth[depthBin + 1];
            if (this.prefixesStack.isEmpty() || this.prefixesStack.peek().size() >= binEnd) {
                this.prefixesStack.push(byteString);
                return;
            }
            int binStart = RopeByteString.minLengthByDepth[depthBin];
            ByteString newTree = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty() && this.prefixesStack.peek().size() < binStart) {
                newTree = new RopeByteString(this.prefixesStack.pop(), newTree);
            }
            ByteString newTree2 = new RopeByteString(newTree, byteString);
            while (!this.prefixesStack.isEmpty()) {
                if (this.prefixesStack.peek().size() >= RopeByteString.minLengthByDepth[getDepthBinForLength(newTree2.size()) + 1]) {
                    break;
                }
                newTree2 = new RopeByteString(this.prefixesStack.pop(), newTree2);
            }
            this.prefixesStack.push(newTree2);
        }

        private int getDepthBinForLength(int length) {
            int depth = Arrays.binarySearch(RopeByteString.minLengthByDepth, length);
            if (depth < 0) {
                return (-(depth + 1)) - 1;
            }
            return depth;
        }
    }

    private static final class PieceIterator implements Iterator<ByteString.LeafByteString> {
        private final ArrayDeque<RopeByteString> breadCrumbs;
        private ByteString.LeafByteString next;

        private PieceIterator(ByteString root) {
            if (root instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString) root;
                this.breadCrumbs = new ArrayDeque<>(rbs.getTreeDepth());
                this.breadCrumbs.push(rbs);
                this.next = getLeafByLeft(rbs.left);
                return;
            }
            this.breadCrumbs = null;
            this.next = (ByteString.LeafByteString) root;
        }

        private ByteString.LeafByteString getLeafByLeft(ByteString root) {
            ByteString pos = root;
            while (pos instanceof RopeByteString) {
                RopeByteString rbs = (RopeByteString) pos;
                this.breadCrumbs.push(rbs);
                pos = rbs.left;
            }
            return (ByteString.LeafByteString) pos;
        }

        private ByteString.LeafByteString getNextNonEmptyLeaf() {
            ByteString.LeafByteString result;
            do {
                ArrayDeque<RopeByteString> arrayDeque = this.breadCrumbs;
                if (arrayDeque == null || arrayDeque.isEmpty()) {
                    return null;
                }
                result = getLeafByLeft(this.breadCrumbs.pop().right);
            } while (result.isEmpty());
            return result;
        }

        public boolean hasNext() {
            return this.next != null;
        }

        public ByteString.LeafByteString next() {
            if (this.next != null) {
                ByteString.LeafByteString result = this.next;
                this.next = getNextNonEmptyLeaf();
                return result;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* access modifiers changed from: package-private */
    public Object writeReplace() {
        return ByteString.wrap(toByteArray());
    }

    private void readObject(ObjectInputStream in) throws IOException {
        throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
    }

    private class RopeInputStream extends InputStream {
        private ByteString.LeafByteString currentPiece;
        private int currentPieceIndex;
        private int currentPieceOffsetInRope;
        private int currentPieceSize;
        private int mark;
        private PieceIterator pieceIterator;

        public RopeInputStream() {
            initialize();
        }

        public int read(byte[] b, int offset, int length) {
            if (b == null) {
                throw new NullPointerException();
            } else if (offset >= 0 && length >= 0 && length <= b.length - offset) {
                return readSkipInternal(b, offset, length);
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        public long skip(long length) {
            if (length >= 0) {
                if (length > 2147483647L) {
                    length = 2147483647L;
                }
                return (long) readSkipInternal(null, 0, (int) length);
            }
            throw new IndexOutOfBoundsException();
        }

        private int readSkipInternal(byte[] b, int offset, int length) {
            int bytesRemaining = length;
            while (true) {
                if (bytesRemaining <= 0) {
                    break;
                }
                advanceIfCurrentPieceFullyRead();
                if (this.currentPiece != null) {
                    int count = Math.min(this.currentPieceSize - this.currentPieceIndex, bytesRemaining);
                    if (b != null) {
                        this.currentPiece.copyTo(b, this.currentPieceIndex, offset, count);
                        offset += count;
                    }
                    this.currentPieceIndex += count;
                    bytesRemaining -= count;
                } else if (bytesRemaining == length) {
                    return -1;
                }
            }
            return length - bytesRemaining;
        }

        public int read() throws IOException {
            advanceIfCurrentPieceFullyRead();
            ByteString.LeafByteString leafByteString = this.currentPiece;
            if (leafByteString == null) {
                return -1;
            }
            int i = this.currentPieceIndex;
            this.currentPieceIndex = i + 1;
            return leafByteString.byteAt(i) & UnsignedBytes.MAX_VALUE;
        }

        public int available() throws IOException {
            return RopeByteString.this.size() - (this.currentPieceOffsetInRope + this.currentPieceIndex);
        }

        public boolean markSupported() {
            return true;
        }

        public void mark(int readAheadLimit) {
            this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
        }

        public synchronized void reset() {
            initialize();
            readSkipInternal(null, 0, this.mark);
        }

        private void initialize() {
            this.pieceIterator = new PieceIterator(RopeByteString.this);
            this.currentPiece = this.pieceIterator.next();
            this.currentPieceSize = this.currentPiece.size();
            this.currentPieceIndex = 0;
            this.currentPieceOffsetInRope = 0;
        }

        private void advanceIfCurrentPieceFullyRead() {
            int i;
            if (this.currentPiece != null && this.currentPieceIndex == (i = this.currentPieceSize)) {
                this.currentPieceOffsetInRope += i;
                this.currentPieceIndex = 0;
                if (this.pieceIterator.hasNext()) {
                    this.currentPiece = this.pieceIterator.next();
                    this.currentPieceSize = this.currentPiece.size();
                    return;
                }
                this.currentPiece = null;
                this.currentPieceSize = 0;
            }
        }
    }
}
