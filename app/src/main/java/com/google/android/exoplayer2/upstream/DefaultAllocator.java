package com.google.android.exoplayer2.upstream;

import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.util.Arrays;

public final class DefaultAllocator implements Allocator {
    private static final int AVAILABLE_EXTRA_CAPACITY = 100;
    private final int individualAllocationSize;
    private final byte[] initialAllocationBlock;
    private final Allocation[] singleAllocationReleaseHolder;
    private final boolean trimOnReset;
    private int allocatedCount;
    private Allocation[] availableAllocations;
    private int availableCount;
    private int targetBufferSize;

    public DefaultAllocator(boolean trimOnReset2, int individualAllocationSize2) {
        this(trimOnReset2, individualAllocationSize2, 0);
    }

    public DefaultAllocator(boolean trimOnReset2, int individualAllocationSize2, int initialAllocationCount) {
        boolean z = false;
        Assertions.checkArgument(individualAllocationSize2 > 0);
        Assertions.checkArgument(initialAllocationCount >= 0 ? true : z);
        this.trimOnReset = trimOnReset2;
        this.individualAllocationSize = individualAllocationSize2;
        this.availableCount = initialAllocationCount;
        this.availableAllocations = new Allocation[(initialAllocationCount + 100)];
        if (initialAllocationCount > 0) {
            this.initialAllocationBlock = new byte[(initialAllocationCount * individualAllocationSize2)];
            for (int i = 0; i < initialAllocationCount; i++) {
                this.availableAllocations[i] = new Allocation(this.initialAllocationBlock, i * individualAllocationSize2);
            }
        } else {
            this.initialAllocationBlock = null;
        }
        this.singleAllocationReleaseHolder = new Allocation[1];
    }

    public synchronized void reset() {
        if (this.trimOnReset) {
            setTargetBufferSize(0);
        }
    }

    public synchronized void setTargetBufferSize(int targetBufferSize2) {
        boolean targetBufferSizeReduced = targetBufferSize2 < this.targetBufferSize;
        this.targetBufferSize = targetBufferSize2;
        if (targetBufferSizeReduced) {
            trim();
        }
    }

    public synchronized Allocation allocate() {
        Allocation allocation;
        this.allocatedCount++;
        if (this.availableCount > 0) {
            Allocation[] allocationArr = this.availableAllocations;
            int i = this.availableCount - 1;
            this.availableCount = i;
            allocation = allocationArr[i];
            this.availableAllocations[this.availableCount] = null;
        } else {
            allocation = new Allocation(new byte[this.individualAllocationSize], 0);
        }
        return allocation;
    }

    public synchronized void release(Allocation allocation) {
        this.singleAllocationReleaseHolder[0] = allocation;
        release(this.singleAllocationReleaseHolder);
    }

    public synchronized void release(Allocation[] allocations) {
        if (this.availableCount + allocations.length >= this.availableAllocations.length) {
            this.availableAllocations = (Allocation[]) Arrays.copyOf(this.availableAllocations, Math.max(this.availableAllocations.length * 2, this.availableCount + allocations.length));
        }
        for (Allocation allocation : allocations) {
            Allocation[] allocationArr = this.availableAllocations;
            int i = this.availableCount;
            this.availableCount = i + 1;
            allocationArr[i] = allocation;
        }
        this.allocatedCount -= allocations.length;
        notifyAll();
    }

    public synchronized void trim() {
        int targetAvailableCount = Math.max(0, Util.ceilDivide(this.targetBufferSize, this.individualAllocationSize) - this.allocatedCount);
        if (targetAvailableCount < this.availableCount) {
            if (this.initialAllocationBlock != null) {
                int lowIndex = 0;
                int highIndex = this.availableCount - 1;
                while (lowIndex <= highIndex) {
                    Allocation lowAllocation = this.availableAllocations[lowIndex];
                    if (lowAllocation.data == this.initialAllocationBlock) {
                        lowIndex++;
                    } else {
                        Allocation highAllocation = this.availableAllocations[highIndex];
                        if (highAllocation.data != this.initialAllocationBlock) {
                            highIndex--;
                        } else {
                            this.availableAllocations[lowIndex] = highAllocation;
                            this.availableAllocations[highIndex] = lowAllocation;
                            highIndex--;
                            lowIndex++;
                        }
                    }
                }
                targetAvailableCount = Math.max(targetAvailableCount, lowIndex);
                if (targetAvailableCount >= this.availableCount) {
                    return;
                }
            }
            Arrays.fill(this.availableAllocations, targetAvailableCount, this.availableCount, (Object) null);
            this.availableCount = targetAvailableCount;
        }
    }

    public synchronized int getTotalBytesAllocated() {
        return this.allocatedCount * this.individualAllocationSize;
    }

    public int getIndividualAllocationLength() {
        return this.individualAllocationSize;
    }
}
