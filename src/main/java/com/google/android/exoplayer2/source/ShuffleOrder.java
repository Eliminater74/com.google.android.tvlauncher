package com.google.android.exoplayer2.source;

import java.util.Arrays;
import java.util.Random;

public interface ShuffleOrder {
    ShuffleOrder cloneAndClear();

    ShuffleOrder cloneAndInsert(int i, int i2);

    ShuffleOrder cloneAndRemove(int i, int i2);

    int getFirstIndex();

    int getLastIndex();

    int getLength();

    int getNextIndex(int i);

    int getPreviousIndex(int i);

    public static class DefaultShuffleOrder implements ShuffleOrder {
        private final int[] indexInShuffled;
        private final Random random;
        private final int[] shuffled;

        public DefaultShuffleOrder(int length) {
            this(length, new Random());
        }

        public DefaultShuffleOrder(int length, long randomSeed) {
            this(length, new Random(randomSeed));
        }

        public DefaultShuffleOrder(int[] shuffledIndices, long randomSeed) {
            this(Arrays.copyOf(shuffledIndices, shuffledIndices.length), new Random(randomSeed));
        }

        private DefaultShuffleOrder(int length, Random random2) {
            this(createShuffledList(length, random2), random2);
        }

        private DefaultShuffleOrder(int[] shuffled2, Random random2) {
            this.shuffled = shuffled2;
            this.random = random2;
            this.indexInShuffled = new int[shuffled2.length];
            for (int i = 0; i < shuffled2.length; i++) {
                this.indexInShuffled[shuffled2[i]] = i;
            }
        }

        public int getLength() {
            return this.shuffled.length;
        }

        public int getNextIndex(int index) {
            int shuffledIndex = this.indexInShuffled[index] + 1;
            int[] iArr = this.shuffled;
            if (shuffledIndex < iArr.length) {
                return iArr[shuffledIndex];
            }
            return -1;
        }

        public int getPreviousIndex(int index) {
            int shuffledIndex = this.indexInShuffled[index] - 1;
            if (shuffledIndex >= 0) {
                return this.shuffled[shuffledIndex];
            }
            return -1;
        }

        public int getLastIndex() {
            int[] iArr = this.shuffled;
            if (iArr.length > 0) {
                return iArr[iArr.length - 1];
            }
            return -1;
        }

        public int getFirstIndex() {
            int[] iArr = this.shuffled;
            if (iArr.length > 0) {
                return iArr[0];
            }
            return -1;
        }

        public ShuffleOrder cloneAndInsert(int insertionIndex, int insertionCount) {
            int[] insertionPoints = new int[insertionCount];
            int[] insertionValues = new int[insertionCount];
            for (int i = 0; i < insertionCount; i++) {
                insertionPoints[i] = this.random.nextInt(this.shuffled.length + 1);
                int swapIndex = this.random.nextInt(i + 1);
                insertionValues[i] = insertionValues[swapIndex];
                insertionValues[swapIndex] = i + insertionIndex;
            }
            Arrays.sort(insertionPoints);
            int[] newShuffled = new int[(this.shuffled.length + insertionCount)];
            int indexInOldShuffled = 0;
            int indexInInsertionList = 0;
            for (int i2 = 0; i2 < this.shuffled.length + insertionCount; i2++) {
                if (indexInInsertionList >= insertionCount || indexInOldShuffled != insertionPoints[indexInInsertionList]) {
                    int indexInOldShuffled2 = indexInOldShuffled + 1;
                    newShuffled[i2] = this.shuffled[indexInOldShuffled];
                    if (newShuffled[i2] >= insertionIndex) {
                        newShuffled[i2] = newShuffled[i2] + insertionCount;
                    }
                    indexInOldShuffled = indexInOldShuffled2;
                } else {
                    newShuffled[i2] = insertionValues[indexInInsertionList];
                    indexInInsertionList++;
                }
            }
            return new DefaultShuffleOrder(newShuffled, new Random(this.random.nextLong()));
        }

        public ShuffleOrder cloneAndRemove(int indexFrom, int indexToExclusive) {
            int numberOfElementsToRemove = indexToExclusive - indexFrom;
            int[] newShuffled = new int[(this.shuffled.length - numberOfElementsToRemove)];
            int foundElementsCount = 0;
            int i = 0;
            while (true) {
                int[] iArr = this.shuffled;
                if (i >= iArr.length) {
                    return new DefaultShuffleOrder(newShuffled, new Random(this.random.nextLong()));
                }
                if (iArr[i] < indexFrom || iArr[i] >= indexToExclusive) {
                    int i2 = i - foundElementsCount;
                    int[] iArr2 = this.shuffled;
                    newShuffled[i2] = iArr2[i] >= indexFrom ? iArr2[i] - numberOfElementsToRemove : iArr2[i];
                } else {
                    foundElementsCount++;
                }
                i++;
            }
        }

        public ShuffleOrder cloneAndClear() {
            return new DefaultShuffleOrder(0, new Random(this.random.nextLong()));
        }

        private static int[] createShuffledList(int length, Random random2) {
            int[] shuffled2 = new int[length];
            for (int i = 0; i < length; i++) {
                int swapIndex = random2.nextInt(i + 1);
                shuffled2[i] = shuffled2[swapIndex];
                shuffled2[swapIndex] = i;
            }
            return shuffled2;
        }
    }

    public static final class UnshuffledShuffleOrder implements ShuffleOrder {
        private final int length;

        public UnshuffledShuffleOrder(int length2) {
            this.length = length2;
        }

        public int getLength() {
            return this.length;
        }

        public int getNextIndex(int index) {
            int index2 = index + 1;
            if (index2 < this.length) {
                return index2;
            }
            return -1;
        }

        public int getPreviousIndex(int index) {
            int index2 = index - 1;
            if (index2 >= 0) {
                return index2;
            }
            return -1;
        }

        public int getLastIndex() {
            int i = this.length;
            if (i > 0) {
                return i - 1;
            }
            return -1;
        }

        public int getFirstIndex() {
            return this.length > 0 ? 0 : -1;
        }

        public ShuffleOrder cloneAndInsert(int insertionIndex, int insertionCount) {
            return new UnshuffledShuffleOrder(this.length + insertionCount);
        }

        public ShuffleOrder cloneAndRemove(int indexFrom, int indexToExclusive) {
            return new UnshuffledShuffleOrder((this.length - indexToExclusive) + indexFrom);
        }

        public ShuffleOrder cloneAndClear() {
            return new UnshuffledShuffleOrder(0);
        }
    }
}
