package androidx.leanback.widget;

import android.support.annotation.CallSuper;
import android.util.Property;
import androidx.leanback.widget.ParallaxEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Parallax<PropertyT extends Property> {
    private final List<ParallaxEffect> mEffects = new ArrayList(4);
    private float[] mFloatValues = new float[4];
    final List<PropertyT> mProperties = new ArrayList();
    final List<PropertyT> mPropertiesReadOnly = Collections.unmodifiableList(this.mProperties);
    private int[] mValues = new int[4];

    public abstract PropertyT createProperty(String str, int i);

    public abstract float getMaxValue();

    public static class PropertyMarkerValue<PropertyT> {
        private final PropertyT mProperty;

        public PropertyMarkerValue(PropertyT property) {
            this.mProperty = property;
        }

        public PropertyT getProperty() {
            return this.mProperty;
        }
    }

    public static class IntProperty extends Property<Parallax, Integer> {
        public static final int UNKNOWN_AFTER = Integer.MAX_VALUE;
        public static final int UNKNOWN_BEFORE = Integer.MIN_VALUE;
        private final int mIndex;

        public IntProperty(String name, int index) {
            super(Integer.class, name);
            this.mIndex = index;
        }

        public final Integer get(Parallax object) {
            return Integer.valueOf(object.getIntPropertyValue(this.mIndex));
        }

        public final void set(Parallax object, Integer value) {
            object.setIntPropertyValue(this.mIndex, value.intValue());
        }

        public final int getIndex() {
            return this.mIndex;
        }

        public final int getValue(Parallax object) {
            return object.getIntPropertyValue(this.mIndex);
        }

        public final void setValue(Parallax object, int value) {
            object.setIntPropertyValue(this.mIndex, value);
        }

        public final PropertyMarkerValue atAbsolute(int absoluteValue) {
            return new IntPropertyMarkerValue(this, absoluteValue, 0.0f);
        }

        public final PropertyMarkerValue atMax() {
            return new IntPropertyMarkerValue(this, 0, 1.0f);
        }

        public final PropertyMarkerValue atMin() {
            return new IntPropertyMarkerValue(this, 0);
        }

        public final PropertyMarkerValue atFraction(float fractionOfMaxValue) {
            return new IntPropertyMarkerValue(this, 0, fractionOfMaxValue);
        }

        /* renamed from: at */
        public final PropertyMarkerValue mo9825at(int offsetValue, float fractionOfMaxParentVisibleSize) {
            return new IntPropertyMarkerValue(this, offsetValue, fractionOfMaxParentVisibleSize);
        }
    }

    static class IntPropertyMarkerValue extends PropertyMarkerValue<IntProperty> {
        private final float mFactionOfMax;
        private final int mValue;

        IntPropertyMarkerValue(IntProperty property, int value) {
            this(property, value, 0.0f);
        }

        IntPropertyMarkerValue(IntProperty property, int value, float fractionOfMax) {
            super(property);
            this.mValue = value;
            this.mFactionOfMax = fractionOfMax;
        }

        /* access modifiers changed from: package-private */
        public final int getMarkerValue(Parallax source) {
            return this.mFactionOfMax == 0.0f ? this.mValue : this.mValue + Math.round(source.getMaxValue() * this.mFactionOfMax);
        }
    }

    public static class FloatProperty extends Property<Parallax, Float> {
        public static final float UNKNOWN_AFTER = Float.MAX_VALUE;
        public static final float UNKNOWN_BEFORE = -3.4028235E38f;
        private final int mIndex;

        public FloatProperty(String name, int index) {
            super(Float.class, name);
            this.mIndex = index;
        }

        public final Float get(Parallax object) {
            return Float.valueOf(object.getFloatPropertyValue(this.mIndex));
        }

        public final void set(Parallax object, Float value) {
            object.setFloatPropertyValue(this.mIndex, value.floatValue());
        }

        public final int getIndex() {
            return this.mIndex;
        }

        public final float getValue(Parallax object) {
            return object.getFloatPropertyValue(this.mIndex);
        }

        public final void setValue(Parallax object, float value) {
            object.setFloatPropertyValue(this.mIndex, value);
        }

        public final PropertyMarkerValue atAbsolute(float markerValue) {
            return new FloatPropertyMarkerValue(this, markerValue, 0.0f);
        }

        public final PropertyMarkerValue atMax() {
            return new FloatPropertyMarkerValue(this, 0.0f, 1.0f);
        }

        public final PropertyMarkerValue atMin() {
            return new FloatPropertyMarkerValue(this, 0.0f);
        }

        public final PropertyMarkerValue atFraction(float fractionOfMaxParentVisibleSize) {
            return new FloatPropertyMarkerValue(this, 0.0f, fractionOfMaxParentVisibleSize);
        }

        /* renamed from: at */
        public final PropertyMarkerValue mo9812at(float offsetValue, float fractionOfMaxParentVisibleSize) {
            return new FloatPropertyMarkerValue(this, offsetValue, fractionOfMaxParentVisibleSize);
        }
    }

    static class FloatPropertyMarkerValue extends PropertyMarkerValue<FloatProperty> {
        private final float mFactionOfMax;
        private final float mValue;

        FloatPropertyMarkerValue(FloatProperty property, float value) {
            this(property, value, 0.0f);
        }

        FloatPropertyMarkerValue(FloatProperty property, float value, float fractionOfMax) {
            super(property);
            this.mValue = value;
            this.mFactionOfMax = fractionOfMax;
        }

        /* access modifiers changed from: package-private */
        public final float getMarkerValue(Parallax source) {
            return this.mFactionOfMax == 0.0f ? this.mValue : this.mValue + (source.getMaxValue() * this.mFactionOfMax);
        }
    }

    /* access modifiers changed from: package-private */
    public final int getIntPropertyValue(int index) {
        return this.mValues[index];
    }

    /* access modifiers changed from: package-private */
    public final void setIntPropertyValue(int index, int value) {
        if (index < this.mProperties.size()) {
            this.mValues[index] = value;
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public final PropertyT addProperty(String name) {
        int newPropertyIndex = this.mProperties.size();
        PropertyT property = createProperty(name, newPropertyIndex);
        if (property instanceof IntProperty) {
            int size = this.mValues.length;
            if (size == newPropertyIndex) {
                int[] newValues = new int[(size * 2)];
                for (int i = 0; i < size; i++) {
                    newValues[i] = this.mValues[i];
                }
                this.mValues = newValues;
            }
            this.mValues[newPropertyIndex] = Integer.MAX_VALUE;
        } else if (property instanceof FloatProperty) {
            int size2 = this.mFloatValues.length;
            if (size2 == newPropertyIndex) {
                float[] newValues2 = new float[(size2 * 2)];
                for (int i2 = 0; i2 < size2; i2++) {
                    newValues2[i2] = this.mFloatValues[i2];
                }
                this.mFloatValues = newValues2;
            }
            this.mFloatValues[newPropertyIndex] = Float.MAX_VALUE;
        } else {
            throw new IllegalArgumentException("Invalid Property type");
        }
        this.mProperties.add(property);
        return property;
    }

    /* access modifiers changed from: package-private */
    public void verifyIntProperties() throws IllegalStateException {
        if (this.mProperties.size() >= 2) {
            int last = getIntPropertyValue(0);
            int i = 1;
            while (i < this.mProperties.size()) {
                int v = getIntPropertyValue(i);
                if (v < last) {
                    throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is smaller than Property[%d]\"%s\"", Integer.valueOf(i), ((Property) this.mProperties.get(i)).getName(), Integer.valueOf(i - 1), ((Property) this.mProperties.get(i - 1)).getName()));
                } else if (last == Integer.MIN_VALUE && v == Integer.MAX_VALUE) {
                    throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is UNKNOWN_BEFORE and Property[%d]\"%s\" is UNKNOWN_AFTER", Integer.valueOf(i - 1), ((Property) this.mProperties.get(i - 1)).getName(), Integer.valueOf(i), ((Property) this.mProperties.get(i)).getName()));
                } else {
                    last = v;
                    i++;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void verifyFloatProperties() throws IllegalStateException {
        if (this.mProperties.size() >= 2) {
            float last = getFloatPropertyValue(0);
            int i = 1;
            while (i < this.mProperties.size()) {
                float v = getFloatPropertyValue(i);
                if (v < last) {
                    throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is smaller than Property[%d]\"%s\"", Integer.valueOf(i), ((Property) this.mProperties.get(i)).getName(), Integer.valueOf(i - 1), ((Property) this.mProperties.get(i - 1)).getName()));
                } else if (last == -3.4028235E38f && v == Float.MAX_VALUE) {
                    throw new IllegalStateException(String.format("Parallax Property[%d]\"%s\" is UNKNOWN_BEFORE and Property[%d]\"%s\" is UNKNOWN_AFTER", Integer.valueOf(i - 1), ((Property) this.mProperties.get(i - 1)).getName(), Integer.valueOf(i), ((Property) this.mProperties.get(i)).getName()));
                } else {
                    last = v;
                    i++;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final float getFloatPropertyValue(int index) {
        return this.mFloatValues[index];
    }

    /* access modifiers changed from: package-private */
    public final void setFloatPropertyValue(int index, float value) {
        if (index < this.mProperties.size()) {
            this.mFloatValues[index] = value;
            return;
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public final List<PropertyT> getProperties() {
        return this.mPropertiesReadOnly;
    }

    @CallSuper
    public void updateValues() {
        for (int i = 0; i < this.mEffects.size(); i++) {
            this.mEffects.get(i).performMapping(this);
        }
    }

    public List<ParallaxEffect> getEffects() {
        return this.mEffects;
    }

    public void removeEffect(ParallaxEffect effect) {
        this.mEffects.remove(effect);
    }

    public void removeAllEffects() {
        this.mEffects.clear();
    }

    public ParallaxEffect addEffect(PropertyMarkerValue... ranges) {
        ParallaxEffect effect;
        if (ranges[0].getProperty() instanceof IntProperty) {
            effect = new ParallaxEffect.IntEffect();
        } else {
            effect = new ParallaxEffect.FloatEffect();
        }
        effect.setPropertyRanges(ranges);
        this.mEffects.add(effect);
        return effect;
    }
}
