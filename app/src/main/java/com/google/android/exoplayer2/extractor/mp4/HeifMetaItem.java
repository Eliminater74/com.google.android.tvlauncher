package com.google.android.exoplayer2.extractor.mp4;

import android.util.LongSparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class HeifMetaItem {
    public final boolean[] arePropertiesEssential;
    public final byte[][] data;
    public final int[] dependencyIds;
    public final int[] dependencyTypes;
    public final String name;
    public final long[] offsets;
    public final Property[] properties;
    public final int protectionIndex;
    public final int[] sizes;
    public final int type;

    private HeifMetaItem(String name2, int type2, int protectionIndex2, Property[] properties2, boolean[] arePropertiesEssential2, int[] dependencyIds2, int[] dependencyTypes2, long[] offsets2, int[] sizes2, byte[][] data2) {
        this.name = name2;
        this.type = type2;
        this.protectionIndex = protectionIndex2;
        this.properties = properties2;
        this.arePropertiesEssential = arePropertiesEssential2;
        this.dependencyIds = dependencyIds2;
        this.dependencyTypes = dependencyTypes2;
        this.offsets = offsets2;
        this.sizes = sizes2;
        this.data = data2;
    }

    public static final class Property {
        public final byte[] data;
        public final int type;

        public Property(int type2, byte[] data2) {
            this.type = type2;
            this.data = data2;
        }
    }

    public static final class HeifMetaItemsBuilder {
        private final LongSparseArray<Builder> itemBuilders = new LongSparseArray<>();
        private byte[] itemData;

        public HeifMetaItemsBuilder addProperty(long id, Property property, boolean isEssential) {
            getBuilder(id).addProperty(property, isEssential);
            return this;
        }

        public HeifMetaItemsBuilder setEntry(long id, String name, int type, int protectionIndex) {
            getBuilder(id).setEntry(name, type, protectionIndex);
            return this;
        }

        public HeifMetaItemsBuilder addReference(long id, int toId, int type) {
            getBuilder(id).addReference(toId, type);
            return this;
        }

        public HeifMetaItemsBuilder addExtent(long id, long offset, int size) {
            getBuilder(id).addExtent(offset, size);
            return this;
        }

        public HeifMetaItemsBuilder addItemDataExtent(long id, int offset, int size) {
            getBuilder(id).addItemDataExtent(offset, size);
            return this;
        }

        public HeifMetaItemsBuilder setItemData(byte[] itemData2) {
            this.itemData = itemData2;
            return this;
        }

        public LongSparseArray<HeifMetaItem> build() {
            LongSparseArray<HeifMetaItem> result = new LongSparseArray<>();
            for (int i = 0; i < this.itemBuilders.size(); i++) {
                result.put(this.itemBuilders.keyAt(i), this.itemBuilders.valueAt(i).build(this.itemData));
            }
            return result;
        }

        private Builder getBuilder(long id) {
            Builder builder = this.itemBuilders.get(id);
            if (builder != null) {
                return builder;
            }
            Builder builder2 = new Builder();
            this.itemBuilders.put(id, builder2);
            return builder2;
        }
    }

    private static final class Builder {
        private final List<Boolean> arePropertiesEssential = new ArrayList();
        private final List<Integer> dependencyIds = new ArrayList();
        private final List<Integer> dependencyTypes = new ArrayList();
        private final List<Integer> itemDataOffsets = new ArrayList();
        private final List<Integer> itemDataSizes = new ArrayList();
        private final List<Long> offsets = new ArrayList();
        private final List<Property> properties = new ArrayList();
        private final List<Integer> sizes = new ArrayList();
        private String name;
        private int protectionIndex;
        private int type;

        public void setEntry(String name2, int type2, int protectionIndex2) {
            this.name = name2;
            this.type = type2;
            this.protectionIndex = protectionIndex2;
        }

        public void addProperty(Property property, boolean isEssential) {
            this.properties.add(property);
            this.arePropertiesEssential.add(Boolean.valueOf(isEssential));
        }

        public void addReference(int toItem, int type2) {
            this.dependencyIds.add(Integer.valueOf(toItem));
            this.dependencyTypes.add(Integer.valueOf(type2));
        }

        public void addExtent(long offset, int size) {
            this.offsets.add(Long.valueOf(offset));
            this.sizes.add(Integer.valueOf(size));
        }

        public void addItemDataExtent(int offset, int size) {
            this.itemDataOffsets.add(Integer.valueOf(offset));
            this.itemDataSizes.add(Integer.valueOf(size));
        }

        public HeifMetaItem build(byte[] itemData) {
            int count = this.properties.size();
            Property[] properties2 = new Property[count];
            boolean[] arePropertiesEssential2 = new boolean[count];
            for (int i = 0; i < count; i++) {
                properties2[i] = this.properties.get(i);
                arePropertiesEssential2[i] = this.arePropertiesEssential.get(i).booleanValue();
            }
            int count2 = this.dependencyIds.size();
            int[] dependencyIds2 = new int[count2];
            int[] dependencyTypes2 = new int[count2];
            for (int i2 = 0; i2 < count2; i2++) {
                dependencyTypes2[i2] = this.dependencyTypes.get(i2).intValue();
                dependencyIds2[i2] = this.dependencyIds.get(i2).intValue();
            }
            int count3 = this.offsets.size();
            long[] offsets2 = new long[count3];
            int[] sizes2 = new int[count3];
            for (int i3 = 0; i3 < count3; i3++) {
                offsets2[i3] = this.offsets.get(i3).longValue();
                sizes2[i3] = this.sizes.get(i3).intValue();
            }
            int count4 = this.itemDataOffsets.size();
            byte[][] itemDatas = new byte[count4][];
            for (int i4 = 0; i4 < count4; i4++) {
                itemDatas[i4] = Arrays.copyOfRange(itemData, this.itemDataOffsets.get(i4).intValue(), this.itemDataSizes.get(i4).intValue());
            }
            return new HeifMetaItem(this.name, this.type, this.protectionIndex, properties2, arePropertiesEssential2, dependencyIds2, dependencyTypes2, offsets2, sizes2, itemDatas);
        }
    }
}
