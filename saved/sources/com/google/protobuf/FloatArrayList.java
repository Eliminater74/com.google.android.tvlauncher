package com.google.protobuf;

import com.google.protobuf.Internal;
import java.util.Arrays;
import java.util.Collection;
import java.util.RandomAccess;

final class FloatArrayList extends AbstractProtobufList<Float> implements Internal.FloatList, RandomAccess, PrimitiveNonBoxingCollection {
    private static final FloatArrayList EMPTY_LIST = new FloatArrayList(new float[0], 0);
    private float[] array;
    private int size;

    static {
        EMPTY_LIST.makeImmutable();
    }

    public static FloatArrayList emptyList() {
        return EMPTY_LIST;
    }

    FloatArrayList() {
        this(new float[10], 0);
    }

    private FloatArrayList(float[] other, int size2) {
        this.array = other;
        this.size = size2;
    }

    /* access modifiers changed from: protected */
    public void removeRange(int fromIndex, int toIndex) {
        ensureIsMutable();
        if (toIndex >= fromIndex) {
            float[] fArr = this.array;
            System.arraycopy(fArr, toIndex, fArr, fromIndex, this.size - toIndex);
            this.size -= toIndex - fromIndex;
            this.modCount++;
            return;
        }
        throw new IndexOutOfBoundsException("toIndex < fromIndex");
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FloatArrayList)) {
            return super.equals(o);
        }
        FloatArrayList other = (FloatArrayList) o;
        if (this.size != other.size) {
            return false;
        }
        float[] arr = other.array;
        for (int i = 0; i < this.size; i++) {
            if (Float.floatToIntBits(this.array[i]) != Float.floatToIntBits(arr[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int result = 1;
        for (int i = 0; i < this.size; i++) {
            result = (result * 31) + Float.floatToIntBits(this.array[i]);
        }
        return result;
    }

    public Internal.FloatList mutableCopyWithCapacity(int capacity) {
        if (capacity >= this.size) {
            return new FloatArrayList(Arrays.copyOf(this.array, capacity), this.size);
        }
        throw new IllegalArgumentException();
    }

    public Float get(int index) {
        return Float.valueOf(getFloat(index));
    }

    public float getFloat(int index) {
        ensureIndexInRange(index);
        return this.array[index];
    }

    public int size() {
        return this.size;
    }

    public Float set(int index, Float element) {
        return Float.valueOf(setFloat(index, element.floatValue()));
    }

    public float setFloat(int index, float element) {
        ensureIsMutable();
        ensureIndexInRange(index);
        float[] fArr = this.array;
        float previousValue = fArr[index];
        fArr[index] = element;
        return previousValue;
    }

    public void add(int index, Float element) {
        addFloat(index, element.floatValue());
    }

    public void addFloat(float element) {
        addFloat(this.size, element);
    }

    private void addFloat(int index, float element) {
        int i;
        ensureIsMutable();
        if (index < 0 || index > (i = this.size)) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
        float[] fArr = this.array;
        if (i < fArr.length) {
            System.arraycopy(fArr, index, fArr, index + 1, i - index);
        } else {
            float[] newArray = new float[(((i * 3) / 2) + 1)];
            System.arraycopy(fArr, 0, newArray, 0, index);
            System.arraycopy(this.array, index, newArray, index + 1, this.size - index);
            this.array = newArray;
        }
        this.array[index] = element;
        this.size++;
        this.modCount++;
    }

    public boolean addAll(Collection<? extends Float> collection) {
        ensureIsMutable();
        Internal.checkNotNull(collection);
        if (!(collection instanceof FloatArrayList)) {
            return super.addAll(collection);
        }
        FloatArrayList list = (FloatArrayList) collection;
        int i = list.size;
        if (i == 0) {
            return false;
        }
        int i2 = this.size;
        if (Integer.MAX_VALUE - i2 >= i) {
            int newSize = i2 + i;
            float[] fArr = this.array;
            if (newSize > fArr.length) {
                this.array = Arrays.copyOf(fArr, newSize);
            }
            System.arraycopy(list.array, 0, this.array, this.size, list.size);
            this.size = newSize;
            this.modCount++;
            return true;
        }
        throw new OutOfMemoryError();
    }

    public boolean remove(Object o) {
        ensureIsMutable();
        for (int i = 0; i < this.size; i++) {
            if (o.equals(Float.valueOf(this.array[i]))) {
                float[] fArr = this.array;
                System.arraycopy(fArr, i + 1, fArr, i, (this.size - i) - 1);
                this.size--;
                this.modCount++;
                return true;
            }
        }
        return false;
    }

    public Float remove(int index) {
        ensureIsMutable();
        ensureIndexInRange(index);
        float[] fArr = this.array;
        float value = fArr[index];
        int i = this.size;
        if (index < i - 1) {
            System.arraycopy(fArr, index + 1, fArr, index, (i - index) - 1);
        }
        this.size--;
        this.modCount++;
        return Float.valueOf(value);
    }

    private void ensureIndexInRange(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(makeOutOfBoundsExceptionMessage(index));
        }
    }

    private String makeOutOfBoundsExceptionMessage(int index) {
        int i = this.size;
        StringBuilder sb = new StringBuilder(35);
        sb.append("Index:");
        sb.append(index);
        sb.append(", Size:");
        sb.append(i);
        return sb.toString();
    }
}
