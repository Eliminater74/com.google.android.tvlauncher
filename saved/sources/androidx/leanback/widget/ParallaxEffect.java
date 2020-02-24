package androidx.leanback.widget;

import android.animation.PropertyValuesHolder;
import android.support.annotation.RestrictTo;
import android.util.Property;
import androidx.leanback.widget.Parallax;
import androidx.leanback.widget.ParallaxTarget;
import java.util.ArrayList;
import java.util.List;

public abstract class ParallaxEffect {
    final List<Parallax.PropertyMarkerValue> mMarkerValues = new ArrayList(2);
    final List<ParallaxTarget> mTargets = new ArrayList(4);
    final List<Float> mTotalWeights = new ArrayList(2);
    final List<Float> mWeights = new ArrayList(2);

    /* access modifiers changed from: package-private */
    public abstract Number calculateDirectValue(Parallax parallax);

    /* access modifiers changed from: package-private */
    public abstract float calculateFraction(Parallax parallax);

    ParallaxEffect() {
    }

    public final List<Parallax.PropertyMarkerValue> getPropertyRanges() {
        return this.mMarkerValues;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final List<Float> getWeights() {
        return this.mWeights;
    }

    public final void setPropertyRanges(Parallax.PropertyMarkerValue... markerValues) {
        this.mMarkerValues.clear();
        for (Parallax.PropertyMarkerValue markerValue : markerValues) {
            this.mMarkerValues.add(markerValue);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final void setWeights(float... weights) {
        int length = weights.length;
        int i = 0;
        while (i < length) {
            if (weights[i] > 0.0f) {
                i++;
            } else {
                throw new IllegalArgumentException();
            }
        }
        this.mWeights.clear();
        this.mTotalWeights.clear();
        float totalWeight = 0.0f;
        for (float weight : weights) {
            this.mWeights.add(Float.valueOf(weight));
            totalWeight += weight;
            this.mTotalWeights.add(Float.valueOf(totalWeight));
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final ParallaxEffect weights(float... weights) {
        setWeights(weights);
        return this;
    }

    public final void addTarget(ParallaxTarget target) {
        this.mTargets.add(target);
    }

    public final ParallaxEffect target(ParallaxTarget target) {
        this.mTargets.add(target);
        return this;
    }

    public final ParallaxEffect target(Object targetObject, PropertyValuesHolder values) {
        this.mTargets.add(new ParallaxTarget.PropertyValuesHolderTarget(targetObject, values));
        return this;
    }

    public final <T, V extends Number> ParallaxEffect target(Object obj, Property property) {
        this.mTargets.add(new ParallaxTarget.DirectPropertyTarget(obj, property));
        return this;
    }

    public final List<ParallaxTarget> getTargets() {
        return this.mTargets;
    }

    public final void removeTarget(ParallaxTarget target) {
        this.mTargets.remove(target);
    }

    public final void performMapping(Parallax source) {
        if (this.mMarkerValues.size() >= 2) {
            if (this instanceof IntEffect) {
                source.verifyIntProperties();
            } else {
                source.verifyFloatProperties();
            }
            boolean fractionCalculated = false;
            float fraction = 0.0f;
            Number directValue = null;
            for (int i = 0; i < this.mTargets.size(); i++) {
                ParallaxTarget target = this.mTargets.get(i);
                if (target.isDirectMapping()) {
                    if (directValue == null) {
                        directValue = calculateDirectValue(source);
                    }
                    target.directUpdate(directValue);
                } else {
                    if (!fractionCalculated) {
                        fractionCalculated = true;
                        fraction = calculateFraction(source);
                    }
                    target.update(fraction);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final float getFractionWithWeightAdjusted(float fraction, int markerValueIndex) {
        if (this.mMarkerValues.size() < 3) {
            return fraction;
        }
        if (this.mWeights.size() == this.mMarkerValues.size() - 1) {
            List<Float> list = this.mTotalWeights;
            float allWeights = list.get(list.size() - 1).floatValue();
            float fraction2 = (this.mWeights.get(markerValueIndex - 1).floatValue() * fraction) / allWeights;
            if (markerValueIndex >= 2) {
                return fraction2 + (this.mTotalWeights.get(markerValueIndex - 2).floatValue() / allWeights);
            }
            return fraction2;
        }
        float allWeights2 = (float) (this.mMarkerValues.size() - 1);
        float fraction3 = fraction / allWeights2;
        if (markerValueIndex >= 2) {
            return fraction3 + (((float) (markerValueIndex - 1)) / allWeights2);
        }
        return fraction3;
    }

    static final class IntEffect extends ParallaxEffect {
        IntEffect() {
        }

        /* access modifiers changed from: package-private */
        public Number calculateDirectValue(Parallax source) {
            if (this.mMarkerValues.size() != 2) {
                throw new RuntimeException("Must use two marker values for direct mapping");
            } else if (((Parallax.PropertyMarkerValue) this.mMarkerValues.get(0)).getProperty() == ((Parallax.PropertyMarkerValue) this.mMarkerValues.get(1)).getProperty()) {
                int value1 = ((Parallax.IntPropertyMarkerValue) this.mMarkerValues.get(0)).getMarkerValue(source);
                int value2 = ((Parallax.IntPropertyMarkerValue) this.mMarkerValues.get(1)).getMarkerValue(source);
                if (value1 > value2) {
                    int swapValue = value2;
                    value2 = value1;
                    value1 = swapValue;
                }
                Number currentValue = ((Parallax.IntProperty) ((Parallax.PropertyMarkerValue) this.mMarkerValues.get(0)).getProperty()).get(source);
                if (currentValue.intValue() < value1) {
                    return Integer.valueOf(value1);
                }
                if (currentValue.intValue() > value2) {
                    return Integer.valueOf(value2);
                }
                return currentValue;
            } else {
                throw new RuntimeException("Marker value must use same Property for direct mapping");
            }
        }

        /* access modifiers changed from: package-private */
        public float calculateFraction(Parallax source) {
            float fraction;
            int lastIndex = 0;
            int lastValue = 0;
            int lastMarkerValue = 0;
            for (int i = 0; i < this.mMarkerValues.size(); i++) {
                Parallax.IntPropertyMarkerValue k = (Parallax.IntPropertyMarkerValue) this.mMarkerValues.get(i);
                int index = ((Parallax.IntProperty) k.getProperty()).getIndex();
                int markerValue = k.getMarkerValue(source);
                int currentValue = source.getIntPropertyValue(index);
                if (i == 0) {
                    if (currentValue >= markerValue) {
                        return 0.0f;
                    }
                } else if (lastIndex == index && lastMarkerValue < markerValue) {
                    throw new IllegalStateException("marker value of same variable must be descendant order");
                } else if (currentValue == Integer.MAX_VALUE) {
                    return getFractionWithWeightAdjusted(((float) (lastMarkerValue - lastValue)) / source.getMaxValue(), i);
                } else {
                    if (currentValue >= markerValue) {
                        if (lastIndex == index) {
                            fraction = ((float) (lastMarkerValue - currentValue)) / ((float) (lastMarkerValue - markerValue));
                        } else if (lastValue != Integer.MIN_VALUE) {
                            int lastMarkerValue2 = lastMarkerValue + (currentValue - lastValue);
                            fraction = ((float) (lastMarkerValue2 - currentValue)) / ((float) (lastMarkerValue2 - markerValue));
                        } else {
                            fraction = 1.0f - (((float) (currentValue - markerValue)) / source.getMaxValue());
                        }
                        return getFractionWithWeightAdjusted(fraction, i);
                    }
                }
                lastValue = currentValue;
                lastIndex = index;
                lastMarkerValue = markerValue;
            }
            return 1.0f;
        }
    }

    static final class FloatEffect extends ParallaxEffect {
        FloatEffect() {
        }

        /* access modifiers changed from: package-private */
        public Number calculateDirectValue(Parallax source) {
            if (this.mMarkerValues.size() != 2) {
                throw new RuntimeException("Must use two marker values for direct mapping");
            } else if (((Parallax.PropertyMarkerValue) this.mMarkerValues.get(0)).getProperty() == ((Parallax.PropertyMarkerValue) this.mMarkerValues.get(1)).getProperty()) {
                float value1 = ((Parallax.FloatPropertyMarkerValue) this.mMarkerValues.get(0)).getMarkerValue(source);
                float value2 = ((Parallax.FloatPropertyMarkerValue) this.mMarkerValues.get(1)).getMarkerValue(source);
                if (value1 > value2) {
                    float swapValue = value2;
                    value2 = value1;
                    value1 = swapValue;
                }
                Number currentValue = ((Parallax.FloatProperty) ((Parallax.PropertyMarkerValue) this.mMarkerValues.get(0)).getProperty()).get(source);
                if (currentValue.floatValue() < value1) {
                    return Float.valueOf(value1);
                }
                if (currentValue.floatValue() > value2) {
                    return Float.valueOf(value2);
                }
                return currentValue;
            } else {
                throw new RuntimeException("Marker value must use same Property for direct mapping");
            }
        }

        /* access modifiers changed from: package-private */
        public float calculateFraction(Parallax source) {
            float fraction;
            int lastIndex = 0;
            float lastValue = 0.0f;
            float lastMarkerValue = 0.0f;
            for (int i = 0; i < this.mMarkerValues.size(); i++) {
                Parallax.FloatPropertyMarkerValue k = (Parallax.FloatPropertyMarkerValue) this.mMarkerValues.get(i);
                int index = ((Parallax.FloatProperty) k.getProperty()).getIndex();
                float markerValue = k.getMarkerValue(source);
                float currentValue = source.getFloatPropertyValue(index);
                if (i == 0) {
                    if (currentValue >= markerValue) {
                        return 0.0f;
                    }
                } else if (lastIndex == index && lastMarkerValue < markerValue) {
                    throw new IllegalStateException("marker value of same variable must be descendant order");
                } else if (currentValue == Float.MAX_VALUE) {
                    return getFractionWithWeightAdjusted((lastMarkerValue - lastValue) / source.getMaxValue(), i);
                } else {
                    if (currentValue >= markerValue) {
                        if (lastIndex == index) {
                            fraction = (lastMarkerValue - currentValue) / (lastMarkerValue - markerValue);
                        } else if (lastValue != -3.4028235E38f) {
                            float lastMarkerValue2 = lastMarkerValue + (currentValue - lastValue);
                            fraction = (lastMarkerValue2 - currentValue) / (lastMarkerValue2 - markerValue);
                        } else {
                            fraction = 1.0f - ((currentValue - markerValue) / source.getMaxValue());
                        }
                        return getFractionWithWeightAdjusted(fraction, i);
                    }
                }
                lastValue = currentValue;
                lastIndex = index;
                lastMarkerValue = markerValue;
            }
            return 1.0f;
        }
    }
}
